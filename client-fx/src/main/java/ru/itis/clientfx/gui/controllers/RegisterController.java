package ru.itis.clientfx.gui.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.itis.clientfx.App;
import ru.itis.clientfx.connection.SocketConnection;
import ru.itis.clientfx.gui.ClientWindow;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.RegisterForm;
import ru.itis.serializers.RegisterFormSerializer;


public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerSubmit;

    @FXML
    private Button toLoginScene;

    @FXML
    void initialize() {
        toLoginScene.setOnAction( e -> {
            Stage mainStage = App.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource("start.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                mainStage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        registerSubmit.setOnAction( e -> {
            RegisterForm registerForm = RegisterForm.builder()
                    .login(loginField.getText())
                    .email(emailField.getText())
                    .password(passwordField.getText())
                    .build();

            RegisterFormSerializer formSerializer = new RegisterFormSerializer();

            Message message = Message.builder()
                    .type(MessageTypes.REGISTER_FORM)
                    .data(formSerializer.serialize(registerForm))
                    .build();

            try {
                SocketConnection connection = App.getConnection();
                connection.sendMessage(message);
            } catch (UnknownHostException ex) {
                ex.printStackTrace(); // TODO
            } catch (IOException ioException) {
                ioException.printStackTrace(); // TODO
            }

        });

    }

}
