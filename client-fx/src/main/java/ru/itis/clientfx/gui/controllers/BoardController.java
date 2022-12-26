package ru.itis.clientfx.gui.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.clientfx.gui.observers.ShowElementsObserver;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Element;
import ru.itis.serializers.BoardSerializer;
import ru.itis.serializers.ElementSerializer;

public class BoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField brushSize;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private CheckBox eraser;

    @FXML
    private Button exitToMenuButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private ScrollPane mainScrollPane;

    private List<List<Double>> arrDraw;

    private ElementSerializer elementSerializer;

    private BoardSerializer boardSerializer;

    private Gson gson;

    @FXML
    void initialize() {
        this.elementSerializer = new ElementSerializer();
        this.boardSerializer = new BoardSerializer();
        this.gson = new Gson();
        loginLabel.setText(App.getConnection().getUser().getLogin());

        mainCanvas.setStyle("-fx-background-color: #dedede");

        GraphicsContext g = mainCanvas.getGraphicsContext2D();

        loadOldElementsRequest();
        Platform.runLater(this::showAllElement);

        exitToMenuButton.setOnAction( e -> {App.getGuiManager().showMainPage();});

        arrDraw = new ArrayList<>();

        mainCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(!eraser.isSelected()) {
                g.beginPath();
                g.moveTo(event.getX(), event.getY());
                g.stroke();
            }
        });

        mainCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            Double size = Double.parseDouble(brushSize.getText());
            if(eraser.isSelected()){
                g.clearRect(event.getX(), event.getY(), size, size);
                List<Double> coordinatesArr = new ArrayList<>();
                coordinatesArr.add((double) Math.round(event.getX()));
                coordinatesArr.add((double) Math.round(event.getY()));
                arrDraw.add(coordinatesArr);
            }else {
                g.setFill(colorPicker.getValue());
                g.setStroke(colorPicker.getValue());

                g.lineTo(event.getX(), event.getY());
                g.setLineWidth(size);
                g.stroke();
                g.closePath();
                g.beginPath();

                List<Double> coordinatesArr = new ArrayList<>();
                coordinatesArr.add((double) Math.round(event.getX()));
                coordinatesArr.add((double) Math.round(event.getY()));
                arrDraw.add(coordinatesArr);

//            g.fillOval(event.getX(), event.getY(), Double.parseDouble(brushSize.getText()), Double.parseDouble(brushSize.getText()));

                g.moveTo(event.getX(), event.getY());
            }
        });

        mainCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            Element element;
            if(eraser.isSelected()){
                element = Element.builder()
                        .id(UUID.randomUUID())
                        .creatorId(App.getConnection().getUser().getId())
                        .boardId(App.getConnection().getCurrentBoard().getId())
                        .type(Element.Type.ERASER)
                        .size(Double.parseDouble(brushSize.getText()))
                        .value(gson.toJson(arrDraw))
                        .color(colorPicker.getValue().toString())
                        .build();
            }else {
                g.lineTo(event.getX(), event.getY());
                g.stroke();
                g.closePath();

                element = Element.builder()
                        .id(UUID.randomUUID())
                        .creatorId(App.getConnection().getUser().getId())
                        .boardId(App.getConnection().getCurrentBoard().getId())
                        .type(Element.Type.BRUSH)
                        .size(Double.parseDouble(brushSize.getText()))
                        .value(gson.toJson(arrDraw))
                        .color(colorPicker.getValue().toString())
                        .build();
            }
            addElementRequest(element);
            App.getConnection().getElements().add(element);
            arrDraw = new ArrayList<>();
        });

        /*mainCanvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if(eraser.isSelected()){
                g.clearRect(x, y, size, size);
            }else{
                System.out.println(x + " | " + y );
                System.out.println("Color: " + colorPicker.getValue());
                g.setFill(colorPicker.getValue());
                g.fillRect(x, y, size, size);
            }
        });*/
    }

    public void showAllElement(){
        ShowElementsObserver showElementsObserver = new ShowElementsObserver(mainCanvas);
        new Thread(showElementsObserver).start();
    }

    private void addElementRequest(Element element){
        Message messageSend = Message.builder()
                .type(MessageTypes.ADD_ELEMENT)
                .data(elementSerializer.serialize(element))
                .build();
        try {
            App.getConnection().sendMessage(messageSend);
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }

    public void loadOldElementsRequest(){
        Message message = Message.builder()
                .type(MessageTypes.GET_ALL_ELEMENT_BY_BOARD)
                .data(boardSerializer.serialize(App.getConnection().getCurrentBoard()))
                .build();
        try {
            App.getConnection().sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace(); // todo
        }
    }

    public void onExit(){
        Platform.exit();
    }

}

