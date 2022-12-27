package ru.itis.clientfx.gui.controllers;


import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import org.apache.commons.collections4.CollectionUtils;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.clientfx.gui.observers.ShowElementsObserver;
import ru.itis.clientfx.gui.painters.*;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Element;
import ru.itis.serializers.BoardSerializer;
import ru.itis.serializers.ElementSerializer;

import javax.imageio.ImageIO;

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

    @FXML
    private ChoiceBox<String> chooseTool;

    private String currentTool;

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
        currentTool = "brush";
        chooseTool.setOnAction( e -> {
            currentTool = chooseTool.getValue();
        });

        exitToMenuButton.setOnAction(e -> {
            App.getGuiManager().showMainPage();
        });


        BrushPainter brushPainter = new BrushPainter(mainCanvas, Double.parseDouble(brushSize.getText()), colorPicker.getValue());
        EraserPainter eraserPainter = new EraserPainter(mainCanvas, Double.parseDouble(brushSize.getText()));
        LinePainter linePainter = new LinePainter(mainCanvas, Double.parseDouble(brushSize.getText()), colorPicker.getValue());
        SquarePainter squarePainter = new SquarePainter(mainCanvas, Double.parseDouble(brushSize.getText()), colorPicker.getValue());

        mainCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {

            if (!eraser.isSelected()) {
                switch (currentTool){
                    case "brush" -> {
                        brushPainter.start(event);
                        brushPainter.setSize(Double.parseDouble(brushSize.getText()));
                        brushPainter.setColor(colorPicker.getValue());
                    }
                    case "line" -> {
                        linePainter.start(event);
                        linePainter.setSize(Double.parseDouble(brushSize.getText()));
                        linePainter.setColor(colorPicker.getValue());
                    }
                    case "square" -> {
                        squarePainter.start(event);
                        squarePainter.setSize(Double.parseDouble(brushSize.getText()));
                        squarePainter.setColor(colorPicker.getValue());
                    }
                }
            } else {
                eraserPainter.setSize(Double.parseDouble(brushSize.getText()));
            }
        });

        mainCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            Double size = Double.parseDouble(brushSize.getText());
            if (eraser.isSelected()) {
                eraserPainter.draw(event);
            } else {
                switch (currentTool){
                    case "brush" -> {
                        brushPainter.draw(event);
                    }
                    case "line" -> {
                        linePainter.draw(event);
                    }
                    case "square" -> {
                        squarePainter.draw(event);
                    }
                }
            }
        });

        mainCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            Element element = null;
            if (eraser.isSelected()) {
                element = eraserPainter.end(event);
            } else {
                switch (currentTool){
                    case "brush" -> {
                        element = brushPainter.end(event);
                    }
                    case "line" -> {
                        element = linePainter.end(event);
                    }
                    case "square" -> {
                        element = squarePainter.end(event);
                    }
                }
            }
            addElementRequest(element);
            App.getConnection().getElements().add(element);
        });
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void handleDragDropped(DragEvent event) throws IOException {
        List<File> files = event.getDragboard().getFiles();
        Image img = new Image(new FileInputStream(files.get(0)));

        mainCanvas.getGraphicsContext2D().drawImage(img, event.getX(), event.getY(),
                img.getWidth(), img.getHeight());

        int width = (int) img.getWidth();
        int height = (int) img.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        SwingFXUtils.fromFXImage(img, bufferedImage);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", output);

        Base64.Encoder encoder = Base64.getEncoder();
        String imageBase64 = encoder.encodeToString(output.toByteArray());

        List<String> finalList = new ArrayList<>();

        finalList.add(String.valueOf(Math.round(event.getX())));
        finalList.add(String.valueOf(Math.round(event.getY())));

        finalList.add(imageBase64);

        Element imageElement = Element.builder()
                .id(UUID.randomUUID())
                .creatorId(App.getConnection().getUser().getId())
                .boardId(App.getConnection().getCurrentBoard().getId())
                .type(Element.Type.IMAGE)
                .size(0.0)
                .value(gson.toJson(finalList))
                .color(Color.rgb(0, 0, 0).toString())
                .build();
        App.getConnection().getElements().add(imageElement);
        addElementRequest(imageElement);
    }

    public void showAllElement() {
        ShowElementsObserver showElementsObserver = new ShowElementsObserver(mainCanvas);
        new Thread(showElementsObserver).start();
    }

    private void addElementRequest(Element element) {
        Message messageSend = Message.builder()
                .type(MessageTypes.ADD_ELEMENT)
                .data(elementSerializer.serialize(element))
                .build();
        try {
            App.getConnection().sendMessage(messageSend);
        } catch (IOException e) {
            App.getGuiManager().showError("Ошибка отправки", "Не удалось отправить элемент");
        }
    }

    public void loadOldElementsRequest() {
        Message message = Message.builder()
                .type(MessageTypes.GET_ALL_ELEMENT_BY_BOARD)
                .data(boardSerializer.serialize(App.getConnection().getCurrentBoard()))
                .build();
        try {
            App.getConnection().sendMessage(message);
        } catch (IOException e) {
            App.getGuiManager().showError("Ошибка получения", "Не удалось получить элементы доски");
        }
    }

    public void onExit() {
        Platform.exit();
    }

}
