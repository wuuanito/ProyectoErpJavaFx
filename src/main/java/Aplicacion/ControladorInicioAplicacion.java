package Aplicacion;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorInicioAplicacion implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Text enlaceCrearCuenta;  // Agrega el Text que representa el enlace

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Asocia un evento al hacer clic en el enlace

    }

    @FXML
    private void login(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaClientes.fxml"));
            Parent root = loader.load();
            Controlador_VentanaCliente controlador = loader.getController();
            Scene scene = new Scene(root);

            // Crear la animación de desvanecimiento
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage miStage = (Stage) loginButton.getScene().getWindow();
            miStage.close();
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
