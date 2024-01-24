package Aplicacion;

import Controlador.VentanaClientes;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmpleadoCliente implements Initializable {

    @FXML
    private SplitPane splitPane1;

    @FXML
    private ImageView imageView1;

    @FXML
    private SplitPane splitPane2;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private ImageView imageView4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }



    @FXML
    private void login (ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelClienteBasic.fxml"));
            Parent root = loader.load();
            VentanaClientes controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e ->controlador.closeWindow() );
            Stage miStage = (Stage) loginButton.getScene().getWindow();
            miStage.close();
            stage.setResizable(false);
            stage.setTitle("Clientes");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
