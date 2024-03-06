package Aplicacion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controlador_VentanaEmpleados implements Initializable {
    @FXML
    private Label InfoUsuario;
    public static String usuario;
    public static int loginId;
    @FXML
    private HBox hboxClientes;

    @FXML
    private Button salir;

    @FXML
    private HBox hboxCanciones;

    @FXML
    private HBox hboxEmpleados;

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
                hboxClientes.getScene().setCursor(Cursor.HAND);
                //Borrar el panel actual
                TripaAncho.getChildren().clear();

                // Cargar el nuevo panel desde su archivo FXML
                FXMLLoader nuevoLoader = new FXMLLoader(getClass().getResource("SubVentanaClientes.fxml"));
                AnchorPane nuevoPanel = nuevoLoader.load();
                // Añadir el nuevo panel a la derecha del panel actual
                TripaAncho.getChildren().add(nuevoPanel);





            } catch (Exception e) {
                e.printStackTrace();
            }


    }
    @FXML
    private void restaurarFondoYCursorMusicaCLientes() {
        // Restaurar el fondo del HBox al color original
        hboxClientes.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxClientes.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void cambiarFormaCursorCLientes() {
        hboxClientes.getScene().setCursor(Cursor.HAND);
        hboxClientes.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorCLientes() {
        hboxClientes.getScene().setCursor(Cursor.DEFAULT);
        hboxClientes.setStyle("-fx-background-color: transparent;");

    }
    @FXML
    private void restaurarFondoYCursorEmpleados() {
        // Restaurar el fondo del HBox al color original
        hboxEmpleados.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxEmpleados.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void cambiarFormaCursorEmpleados() {
        hboxEmpleados.getScene().setCursor(Cursor.HAND);
        hboxEmpleados.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorEmpleados() {
        hboxEmpleados.getScene().setCursor(Cursor.DEFAULT);
        hboxEmpleados.setStyle("-fx-background-color: transparent;");

    }

    @FXML
    private void restaurarFondoYCursorCanciones() {
        // Restaurar el fondo del HBox al color original
        hboxCanciones.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxCanciones.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void cambiarFormaCursorCanciones() {
        hboxCanciones.getScene().setCursor(Cursor.HAND);
        hboxCanciones.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorCanciones() {
        hboxEmpleados.getScene().setCursor(Cursor.DEFAULT);
        hboxEmpleados.setStyle("-fx-background-color: transparent;");

    }
    public void abrirsubventanaEmpleados() {
        try {
            hboxEmpleados.getScene().setCursor(Cursor.HAND);
            //Borrar el panel actual
            TripaAncho.getChildren().clear();

            // Cargar el nuevo panel desde su archivo FXML
            FXMLLoader nuevoLoader = new FXMLLoader(getClass().getResource("SubVentanaEmpleados.fxml"));
            AnchorPane nuevoPanel = nuevoLoader.load();
            // Añadir el nuevo panel a la derecha del panel actual
            TripaAncho.getChildren().add(nuevoPanel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirTracks(MouseEvent mouseEvent) {
        try {
            hboxCanciones.getScene().setCursor(Cursor.HAND);
            //Borrar el panel actual
            TripaAncho.getChildren().clear();

            // Cargar el nuevo panel desde su archivo FXML
            FXMLLoader nuevoLoader = new FXMLLoader(getClass().getResource("SubVentanaCanciones.fxml"));
            AnchorPane nuevoPanel = nuevoLoader.load();
            // Añadir el nuevo panel a la derecha del panel actual
            TripaAncho.getChildren().add(nuevoPanel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cerrarVentana(MouseEvent mouseEvent) {
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicio.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setTitle("Inicio");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
