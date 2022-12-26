package ru.itis.clientfx;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.itis.clientfx.connection.ConnectionListener;
import ru.itis.clientfx.connection.SocketConnection;
import ru.itis.clientfx.gui.ClientWindow;
import ru.itis.clientfx.gui.GuiManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class App {
    private static Stage stage;

    private static SocketConnection connection;

    private static GuiManager guiManager;

    public static void main(String[] args) {
        try {
            connection = new SocketConnection(InetAddress.getLocalHost(), 2031);
        } catch (UnknownHostException e) {
            e.printStackTrace(); // TODO
        }
        guiManager = new GuiManager();
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
