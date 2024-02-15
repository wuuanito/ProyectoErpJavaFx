module Aplicacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;
    requires java.naming;

    requires org.controlsfx.controls;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;



    opens Aplicacion to org.hibernate.orm.core, javafx.fxml;

 exports Aplicacion;

}

