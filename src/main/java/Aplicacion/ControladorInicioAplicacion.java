package Aplicacion;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControladorInicioAplicacion implements Initializable {

    @FXML
    private Button loginButton;


    @FXML
    private TextField campouser;

    @FXML
    private PasswordField campopsswd;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    @FXML
    private void login(ActionEvent event) {
        String usuario = campouser.getText();
        String contrasena = campopsswd.getText();
        String tipoUsuario = verificarCredenciales(usuario, contrasena);

        if (tipoUsuario != null) {
            try {
                String ventanaFXML = null;
                if (tipoUsuario.equals("empleado")) {
                    ventanaFXML = "VentanaEmpleados.fxml";
                    //Pasar el usuario a la siguiente ventana
                    //ControladorVentanaEmpleados.usuario = usuario;
                } else if (tipoUsuario.equals("cliente")) {
                    ventanaFXML = "VentanaClientes.fxml";
                    //Pasar el usuario a la siguiente ventana
                    Controlador_VentanaCliente.usuario = usuario;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de inicio de sesión");
                    alert.setHeaderText(null);
                    alert.setContentText("Tipo de usuario desconocido.");
                    alert.showAndWait();
                    return;
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(ventanaFXML));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                Stage miStage = (Stage) loginButton.getScene().getWindow();
                miStage.close();
                stage.setResizable(false);
                //Pasar loginId a la siguiente ventana
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
                    String query = "SELECT loginId FROM usuarios WHERE nombre = ? AND contrasena = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, usuario);
                    stmt.setString(2, contrasena);
                    ResultSet rs = stmt.executeQuery();
                    //Pasar el loginId a la siguiente ventana
                    if (rs.next()) {
                        if (tipoUsuario.equals("empleado")) {
                            //ControladorVentanaEmpleados.loginId = rs.getInt("loginId");
                        } else if (tipoUsuario.equals("cliente")) {
                            Controlador_VentanaCliente.loginId = rs.getInt("loginId");
                            System.out.println("LoginId: " + Controlador_VentanaCliente.loginId);
                        }
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
    }

    private String verificarCredenciales(String usuario, String contrasena) {
        String tipoUsuario = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String query = "SELECT tipo FROM usuarios WHERE nombre = ? AND contrasena = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tipoUsuario = rs.getString("tipo");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoUsuario;
    }
}
