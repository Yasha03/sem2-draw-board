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

public class EraserPainter implements Painter{

    private Canvas canvas;

    private GraphicsContext g;

    private List<List<Double>> arrDraw;

    private Double size;

    private Gson gson;

    public EraserPainter(Canvas canvas, Double size){
        this.canvas = canvas;
        this.g = canvas.getGraphicsContext2D();
        this.size = size;
        this.arrDraw = new ArrayList<>();
        this.gson = new Gson();
    }

    @Override
    public void start(MouseEvent event) {

    }

    @Override
    public void draw(MouseEvent event) {
        g.clearRect(event.getX(), event.getY(), size, size);
        List<Double> coordinatesArr = new ArrayList<>();
        coordinatesArr.add((double) Math.round(event.getX()));
        coordinatesArr.add((double) Math.round(event.getY()));
        arrDraw.add(coordinatesArr);
    }

    @Override
    public Element end(MouseEvent event) {
        Element element = Element.builder()
                .id(UUID.randomUUID())
                .creatorId(App.getConnection().getUser().getId())
                .boardId(App.getConnection().getCurrentBoard().getId())
                .type(Element.Type.ERASER)
                .size(size)
                .value(gson.toJson(arrDraw))
                .color(Color.rgb(0, 0, 0).toString())
                .build();
        this.arrDraw = new ArrayList<>();
        return element;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
