package ru.itis.clientfx.gui.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.ClientWindow;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Account;
import ru.itis.serializers.AccountSerializer;

public class StartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private Button loginSubmit;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button toRegisterScene;

    private AccountSerializer accountSerializer;

    @FXML
    void initialize() {
        this.accountSerializer = new AccountSerializer();

        toRegisterScene.setOnAction( e -> {
            Stage mainStage = App.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource("register.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                mainStage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        loginSubmit.setOnAction( e -> {
            Account accountSend = Account.builder()
                    .email(emailField.getText())
                    .password(passwordField.getText())
                    .build();
            Message message = Message.builder()
                    .type(MessageTypes.LOGIN_FORM)
                    .data(accountSerializer.serialize(accountSend))
                    .build();
            try {
                App.getConnection().sendMessage(message);
            } catch (IOException ex) {
                ex.printStackTrace(); // TODO
            }
        });
    }

}