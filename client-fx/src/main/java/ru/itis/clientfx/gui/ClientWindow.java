package ru.itis.clientfx.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.clientfx.App;

public class ClientWindow extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        App.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(ClientWindow.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Draw board");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public Stage getMainStage(){
        return mainStage;
    }
}
