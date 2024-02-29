package Aplicacion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_VentanaEmpleados implements Initializable {
    @FXML
    private Label InfoUsuario;
    public static String usuario;
    public static int loginId;

    @FXML
    private AnchorPane TripaAncho;
@FXML
private Button boton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InfoUsuario.setText(usuario);
    }

    public void abrirsubventanaCanciones() {

            try {
                // Cargar el nuevo panel desde su archivo FXML
                FXMLLoader nuevoLoader = new FXMLLoader(getClass().getResource("SubVentanaCanciones.fxml"));
                AnchorPane nuevoPanel = nuevoLoader.load();
                // Añadir el nuevo panel a la derecha del panel actual
                TripaAncho.getChildren().add(nuevoPanel);



            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
