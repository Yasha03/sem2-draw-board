package ru.itis.clientfx.gui.custom;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.itis.clientfx.App;

public class ModalWindow {
    public static void newWindow(Alert.AlertType alertType, String title, String info){
        Stage window = new Stage();

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.initOwner(App.getStage());
        alert.show();
    }
}
