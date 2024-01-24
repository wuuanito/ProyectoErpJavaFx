module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javax.persistence;
    requires java.sql;

    opens Aplicacion to javafx.fxml;
    exports Aplicacion;
    exports Controlador;
    opens Controlador to javafx.fxml;
}