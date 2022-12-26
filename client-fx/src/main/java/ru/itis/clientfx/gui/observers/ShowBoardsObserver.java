package ru.itis.clientfx.gui.observers;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.models.Board;

import java.util.List;

public class ShowBoardsObserver implements Runnable{

    private FlowPane flowPane;

    public ShowBoardsObserver(FlowPane flowPaneBorders){
        this.flowPane = flowPaneBorders;
    }

    @Override
    public void run() {
        while (true) {
            if(GuiManager.isUpdateBoards()) {
                List<Board> boards = App.getConnection().getBoards();
                Platform.runLater( () -> {flowPane.getChildren().clear();});
                for (Board board : boards) {
                    Platform.runLater(() -> {flowPane.getChildren().add(createVBoxBoardElement(board));});
                }
                GuiManager.setUpdateBoards(false);
            }
        }
    }

    private VBox createVBoxBoardElement(Board board) {
        VBox root = new VBox();
        root.setStyle("-fx-font-size: 16px");
        root.setStyle("-fx-text-fill: #fff");
        root.setStyle("-fx-font-weight: 700");
        root.setPadding(new Insets(10, 10, 10, 10));


        root.prefHeight(120);
        root.prefWidth(90);
        root.setStyle("-fx-background-color: #7ED37F");

        Label labelName = new Label(board.getId().toString().substring(0, 8));
        root.getChildren().add(labelName);

        root.setOnMouseClicked( e -> {
            App.getGuiManager().changeBoard(board);
        });

        return root;
    }
}
