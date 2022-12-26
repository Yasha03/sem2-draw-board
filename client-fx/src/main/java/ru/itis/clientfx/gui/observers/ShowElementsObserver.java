package ru.itis.clientfx.gui.observers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.models.Element;

import java.util.ArrayList;
import java.util.List;

public class ShowElementsObserver implements Runnable {

    private Canvas canvas;

    private Gson gson;

    public ShowElementsObserver(Canvas canvas) {
        this.canvas = canvas;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        while (true) {
            if (GuiManager.isUpdateElements()) {
                showElements();
                GuiManager.setUpdateElements(false);
            }
        }
    }

    public void showElements() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        List<Element> elements = App.getConnection().getElements();
        for (Element element : elements) {
            if (element.getType().equals(Element.Type.BRUSH)) {
                List<List<Double>> coords = gson.fromJson(element.getValue(),
                        new TypeToken<ArrayList<ArrayList<Double>>>() {
                        }.getType());
                printBrushElement(coords, element.getSize(), element.getColor());
            } else if (element.getType().equals(Element.Type.ERASER)) {
                List<List<Double>> coords = gson.fromJson(element.getValue(),
                        new TypeToken<ArrayList<ArrayList<Double>>>() {
                        }.getType());
                printEraserElement(coords, element.getSize());
            }
        }
    }

    private void printEraserElement(List<List<Double>> coords, Double size){
        GraphicsContext g = canvas.getGraphicsContext2D();
        for (List<Double> coord : coords) {
            g.clearRect(coord.get(0), coord.get(1), size, size);
        }
    }

    public void printBrushElement(List<List<Double>> coords, Double size, String colorString) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.beginPath();
        g.stroke();
        for (List<Double> coord : coords) {
            g.setFill(Paint.valueOf(colorString));
            g.setStroke(Paint.valueOf(colorString));

            g.lineTo(coord.get(0), coord.get(1));
            g.setLineWidth(size);
            g.stroke();
            g.closePath();
            g.beginPath();
            g.moveTo(coord.get(0), coord.get(1));
        }
        g.stroke();
        g.closePath();
    }
}
