package Aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.attribute.FileAttribute;

public class InicioAplicacion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(InicioAplicacion.class.getResource("PantallaInicio.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Inicio");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
