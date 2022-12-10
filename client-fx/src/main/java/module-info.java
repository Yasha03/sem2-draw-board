module ru.itis.clientfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ru.itis.clientfx to javafx.fxml;
    exports ru.itis.clientfx;
}