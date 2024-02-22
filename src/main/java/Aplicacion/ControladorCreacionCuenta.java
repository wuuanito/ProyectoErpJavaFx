package Aplicacion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

public class ControladorCreacionCuenta {

    @FXML
    private TextField nombreUsuario;

    @FXML
    private PasswordField contrauno;

    @FXML
    private PasswordField contrados;

    @FXML
    private Button btonRegistro;





    @FXML
    private void registrarUsuario(ActionEvent event) {

        // Obtener los datos de los campos de texto
        String nombre = nombreUsuario.getText().trim();
        String tipo = "cliente";
        String contra1 = contrauno.getText();
        String contra2 = contrados.getText();

        // Validar que la contraseña cumple con los requisitos
        if (!PasswordValidator.validarContrasena(contra1)) {

            mostrarAlerta("Error Contraseña", "La contraseña debe tener al menos 8 caracteres, incluyendo al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.");
            return;
        } else {

        }

        // Validar que el usuario no existe
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String query = "SELECT nombre FROM usuarios WHERE nombre = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, nombre);
            if (ps.executeQuery().next()) {
                System.out.println("El usuario ya existe");

                mostrarAlerta("Error Nombre de Usuario", "Usuario ya existe, por favor elija otro nombre de usuario.");
                nombreUsuario.clear();
            } else {


                // Insertar nuevo usuario en la base de datos
                if (Objects.equals(contra1, contra2)) {
                    String sentencia = "INSERT INTO usuarios (nombre, tipo, contrasena) VALUES (?, ?, ?)";
                    PreparedStatement psInsert = conn.prepareStatement(sentencia);
                    psInsert.setString(1, nombre);
                    psInsert.setString(2, tipo);
                    psInsert.setString(3, contra1);
                    psInsert.executeUpdate();
                    System.out.println("Usuario registrado");

                    // Volver a la ventana de inicio de sesión
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicio.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                    Stage miStage = (Stage) btonRegistro.getScene().getWindow();
                    miStage.close();
                    stage.setResizable(false);
                } else {
                    System.out.println("Las contraseñas no coinciden");
                    mostrarAlerta("Error Contraseña", "Las contraseñas no coinciden.");
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static class PasswordValidator {
        public static boolean validarContrasena(String contrasena) {
            // Verificar si la contraseña cumple con los requisitos
            Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
            Matcher matcher = pattern.matcher(contrasena);
            return matcher.matches();
        }
    }
}
