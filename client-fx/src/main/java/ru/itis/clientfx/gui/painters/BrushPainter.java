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

public class BrushPainter implements Painter{

    private Canvas canvas;

    private GraphicsContext g;

    private List<List<Double>> arrDraw;

    private Double size;

    private Color color;

    private Gson gson;

    public BrushPainter(Canvas canvas, Double size, Color color){
        this.canvas = canvas;
        this.g = canvas.getGraphicsContext2D();
        this.size = size;
        this.color = color;
        this.arrDraw = new ArrayList<>();
        this.gson = new Gson();
    }

    @Override
    public void start(MouseEvent event) {
        g.beginPath();
        g.moveTo(event.getX(), event.getY());
        g.stroke();
    }

    @Override
    public void draw(MouseEvent event) {
        g.setFill(color);
        g.setStroke(color);

        g.lineTo(event.getX(), event.getY());
        g.setLineWidth(size);
        g.stroke();
        g.closePath();
        g.beginPath();

        List<Double> coordinatesArr = new ArrayList<>();
        coordinatesArr.add((double) Math.round(event.getX()));
        coordinatesArr.add((double) Math.round(event.getY()));
        arrDraw.add(coordinatesArr);
        g.moveTo(event.getX(), event.getY());
    }

    @Override
    public Element end(MouseEvent event) {
        g.lineTo(event.getX(), event.getY());
        g.stroke();
        g.closePath();

        Element element = Element.builder()
                .id(UUID.randomUUID())
                .creatorId(App.getConnection().getUser().getId())
                .boardId(App.getConnection().getCurrentBoard().getId())
                .type(Element.Type.BRUSH)
                .size(size)
                .value(gson.toJson(arrDraw))
                .color(color.toString())
                .build();
        this.arrDraw = new ArrayList<>();
        return element;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
