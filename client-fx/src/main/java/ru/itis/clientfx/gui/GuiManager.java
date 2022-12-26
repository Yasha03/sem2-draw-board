package ru.itis.clientfx.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.custom.ModalWindow;
import ru.itis.models.Board;

import java.io.IOException;

public class GuiManager {

    private static boolean updateBoards = false;

    private static boolean updateElements = false;


    public void showErrorRegister(String errorInfo) {
        Platform.runLater( () -> {
            ModalWindow.newWindow(Alert.AlertType.ERROR,"Ошибка регистрации", errorInfo);
        });
    }

    public void showErrorLogin(String errorInfo) {
        Platform.runLater( () -> {
            ModalWindow.newWindow(Alert.AlertType.ERROR,"Ошибка вход", errorInfo);
        });
    }

    public void showMainPage(){
        Platform.runLater( () -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource("boards.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                App.getStage().setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); // TODO
            }
        });
    }

    public void changeBoard(Board board){
        try {
            App.getConnection().setCurrentBoard(board);
            FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource("board.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            App.getStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }

    synchronized public static boolean isUpdateBoards() {
        return updateBoards;
    }

    synchronized public static void setUpdateBoards(boolean updateBoards) {
        GuiManager.updateBoards = updateBoards;
    }

    synchronized public static boolean isUpdateElements() {
        return updateElements;
    }

    synchronized public static void setUpdateElements(boolean updateElements) {
        GuiManager.updateElements = updateElements;
    }
}
