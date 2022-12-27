package ru.itis.clientfx.gui.observers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.clientfx.gui.painters.BrushPainter;
import ru.itis.clientfx.gui.painters.EraserPainter;
import ru.itis.clientfx.gui.painters.LinePainter;
import ru.itis.clientfx.gui.painters.SquarePainter;
import ru.itis.models.Element;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
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
                BrushPainter brushPainter = new BrushPainter(canvas, element.getSize(), (Color) Paint.valueOf(element.getColor()));
                brushPainter.deserialize(coords);
            } else if (element.getType().equals(Element.Type.ERASER)) {
                List<List<Double>> coords = gson.fromJson(element.getValue(),
                        new TypeToken<ArrayList<ArrayList<Double>>>() {
                        }.getType());
                EraserPainter eraserPainter = new EraserPainter(canvas, element.getSize());
                eraserPainter.deserialize(coords);
            } else if (element.getType().equals(Element.Type.IMAGE)) {
                printImageElement(element.getValue());
            } else if (element.getType().equals(Element.Type.LINE)){
                List<List<Double>> coords = gson.fromJson(element.getValue(),
                        new TypeToken<ArrayList<ArrayList<Double>>>() {
                        }.getType());
                LinePainter linePainter = new LinePainter(canvas, element.getSize(), (Color) Paint.valueOf(element.getColor()));
                linePainter.deserialize(coords);
            } else if(element.getType().equals(Element.Type.SQUARE)){
                List<List<Double>> coords = gson.fromJson(element.getValue(),
                        new TypeToken<ArrayList<ArrayList<Double>>>() {
                        }.getType());
                SquarePainter squarePainter = new SquarePainter(canvas, element.getSize(), (Color) Paint.valueOf(element.getColor()));
                squarePainter.deserialize(coords);
            }
        }
    }

    private void printImageElement(String value) {
        List<String> arr = gson.fromJson(value, new TypeToken<ArrayList<String>>() {
        }.getType());
        double x = Double.parseDouble(arr.get(0));
        double y = Double.parseDouble(arr.get(1));
        String imageBase64 = arr.get(2);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] input = decoder.decode(imageBase64);
        Image img = new Image(new ByteArrayInputStream(input));
        canvas.getGraphicsContext2D().drawImage(img, x, y, img.getWidth(), img.getHeight());
    }

}
