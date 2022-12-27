package ru.itis.clientfx;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.itis.clientfx.connection.ConnectionListener;
import ru.itis.clientfx.connection.SocketConnection;
import ru.itis.clientfx.gui.ClientWindow;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.exceptions.LostConnectionException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class App {
    private static Stage stage;

    private static SocketConnection connection;

    private static GuiManager guiManager;

    public static void main(String[] args) {
        guiManager = new GuiManager();
        try {
            connection = new SocketConnection(InetAddress.getLocalHost(), 2031);
        } catch (UnknownHostException e) {
            guiManager.showError("Ошибка соединения", "Неправильно подобран адрес или порт");
        } catch (LostConnectionException e) {
            guiManager.showError("Ошибка соединения", "Соединение с сервером разорвано");
        }
        ConnectionListener connectionListener = new ConnectionListener(connection, guiManager);
        new Thread(connectionListener).start();
        ClientWindow window = new ClientWindow();
        Application.launch(window.getClass(), args);

    }

    public static Stage getStage(){
        return stage;
    }

    public static SocketConnection getConnection() {return connection; };

    public static void setStage(Stage stage) {
        App.stage = stage;
    }

    public static GuiManager getGuiManager() {
        return guiManager;
    }
}
