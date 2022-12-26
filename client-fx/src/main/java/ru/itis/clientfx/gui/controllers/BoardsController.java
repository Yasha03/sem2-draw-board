package ru.itis.clientfx.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.itis.clientfx.App;
import ru.itis.clientfx.connection.SocketConnection;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.clientfx.gui.observers.ShowBoardsObserver;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Account;
import ru.itis.models.Board;
import ru.itis.serializers.BoardSerializer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class BoardsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createBoardButton;

    @FXML
    private Button exitToMenuButton;

    @FXML
    private FlowPane flowPaneBorders;

    @FXML
    private Label loginLabel;

    private BoardSerializer boardSerializer;



    @FXML
    void initialize() {
        this.boardSerializer = new BoardSerializer();
        SocketConnection connection = App.getConnection();
        Account user = connection.getUser();

        loginLabel.setText(user.getLogin());

        createBoardButton.setOnAction( e -> {createBoardRequest();});

        getAllBoardsRequest();
        Platform.runLater(this::showAllBoards);


    }

    private void showAllBoards() {
        ShowBoardsObserver showBoardsObserver = new ShowBoardsObserver(flowPaneBorders);
        new Thread(showBoardsObserver).start();
    }


    private void getAllBoardsRequest(){
        Message messageSend = Message.builder()
                .type(MessageTypes.GET_ALL_BOARD)
                .build();
        try {
            App.getConnection().sendMessage(messageSend);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: 25.12.2022
        }
    }

    private void createBoardRequest() {
        Board board = Board.builder()
                .creatorId(App.getConnection().getUser().getId())
                .build();

        Message messageSend = Message.builder()
                .type(MessageTypes.CREATE_BOARD)
                .data(boardSerializer.serialize(board))
                .build();
        try {
            App.getConnection().sendMessage(messageSend);
        } catch (IOException e) {
            e.printStackTrace();  // TODO: 25.12.2022
        }
    }
}
