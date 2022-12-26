module ru.itis.clientfx {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires javafx.graphics;
    requires lombok;
    requires javafx.swing;

    requires com.google.gson;
    requires commons.collections4;


    requires protocol;

    opens ru.itis.clientfx.gui.controllers to com.google.gson, javafx.fxml;

    exports ru.itis.clientfx.gui;
}