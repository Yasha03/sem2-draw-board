package ru.itis.clientfx.gui.painters;

import javafx.scene.input.MouseEvent;
import ru.itis.models.Element;

public interface Painter {
    public void start(MouseEvent event);

    public void draw(MouseEvent event);

    public Element end(MouseEvent event);
}
