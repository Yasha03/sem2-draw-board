package ru.itis.clientfx.gui.painters;

import com.google.gson.Gson;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ru.itis.clientfx.App;
import ru.itis.models.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SquarePainter implements Painter{

    private Canvas canvas;

    private GraphicsContext g;

    private List<List<Double>> arrDraw;

    private List<Double> startCoord;

    private List<Double> endCoord;

    private Double size;

    private Color color;

    private Gson gson;

    public SquarePainter(Canvas canvas, Double size, Color color){
        this.canvas = canvas;
        this.g = canvas.getGraphicsContext2D();
        this.size = size;
        this.color = color;
        this.arrDraw = new ArrayList<>();
        this.startCoord = new ArrayList<>();
        this.endCoord = new ArrayList<>();
        this.gson = new Gson();
    }

    @Override
    public void start(MouseEvent event) {
        startCoord.add(event.getX());
        startCoord.add(event.getY());
    }

    @Override
    public void draw(MouseEvent event) {

    }

    @Override
    public Element end(MouseEvent event) {

        endCoord.add(event.getX());
        endCoord.add(event.getY());
        arrDraw.add(startCoord);
        arrDraw.add(endCoord);

        deserialize(arrDraw);
        Element element = Element.builder()
                .id(UUID.randomUUID())
                .creatorId(App.getConnection().getUser().getId())
                .boardId(App.getConnection().getCurrentBoard().getId())
                .type(Element.Type.SQUARE)
                .size(size)
                .value(gson.toJson(arrDraw))
                .color(color.toString())
                .build();
        this.arrDraw = new ArrayList<>();
        this.startCoord = new ArrayList<>();
        this.endCoord = new ArrayList<>();
        return element;
    }

    public void deserialize(List<List<Double>> coords){
        g.setFill(color);
        g.setStroke(color);
        g.setLineWidth(size);
        List<Double> start = coords.get(0);
        List<Double> end = coords.get(1);

        g.strokeLine(start.get(0), start.get(1), end.get(0), start.get(1));
        g.strokeLine(end.get(0), start.get(1), end.get(0), end.get(1));
        g.strokeLine(end.get(0), end.get(1), start.get(0), end.get(1));
        g.strokeLine(start.get(0), end.get(1), start.get(0), start.get(1));
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
