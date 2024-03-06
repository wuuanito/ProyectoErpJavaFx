package Aplicacion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladorSubVentanaNuevoUsuarios implements Initializable {

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField fieldApellido;

    @FXML
    private TextField fieldCiudad;

    @FXML
    private TextField fieldCodigoPostal;

    @FXML
    private TextField fieldDireccion;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldEmpresa;

    @FXML
    private TextField fieldEstado;

    @FXML
    private TextField fieldFax;

    @FXML
    private TextField fieldNombre;

    @FXML
    private TextField fieldPais;

    @FXML
    private TextField fieldTlfn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void CrearUsuario() {
        String nombre = fieldNombre.getText();
        String apellido = fieldApellido.getText();
        String direccion = fieldDireccion.getText();
        String ciudad = fieldCiudad.getText();
        String estado = fieldEstado.getText();
        String pais = fieldPais.getText();
        String codigoPostal = fieldCodigoPostal.getText();
        String tlfn = fieldTlfn.getText();
        String fax = fieldFax.getText();
        String email = fieldEmail.getText();
        String empresa = fieldEmpresa.getText();
try {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
    String query = "INSERT INTO customer (FirstName, LastName, Address, City, State, Country, PostalCode, Phone, Fax, Email, Company,Hide) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt = conn.prepareStatement(query);

    stmt.setString(1, nombre);
    stmt.setString(2, apellido);
    stmt.setString(3, direccion);
    stmt.setString(4, ciudad);
    stmt.setString(5, estado);
    stmt.setString(6, pais);
    stmt.setString(7, codigoPostal);
    stmt.setString(8, tlfn);
    stmt.setString(9, fax);
    stmt.setString(10, email);
    stmt.setString(11, empresa);
    stmt.setInt(12, 0);

    //Alerta de que se ha creado el usuario

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Usuario creado");
    alert.setHeaderText("Usuario creado con éxito");
    alert.setContentText("El usuario " + nombre + " " + apellido + " ha sido creado con éxito");
    alert.showAndWait();

    //Insertar en la tabla usuarios de la base de datos

    Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
    String query2 = "INSERT INTO usuarios (nombre,tipo,contrasena,loginId,hide) VALUES (?, ?, ?, ?,?)";
    PreparedStatement stmt2 = conn2.prepareStatement(query2);

    nombre =fieldNombre.getText();
     apellido = fieldApellido.getText();

    char primeraLetraNombre = nombre.charAt(0);

    String nombreCorto = primeraLetraNombre + apellido;
    stmt2.setString(1, nombreCorto);
    stmt2.setString(2, "cliente");
    stmt2.setString(3, "1234");
    stmt2.setInt(4, Math.toIntExact(Math.round(Math.random()*1000000)));
    stmt2.setInt(5, 0);





    stmt.executeUpdate();
    stmt2.executeUpdate();

    //Alerta Mostrando el login
    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    alert2.setTitle("Usuario creado");
    alert2.setHeaderText("Usuario creado con éxito");
    alert2.setContentText("El usuario " + nombreCorto + " ha sido creado con éxito y su contraseña es 1234");

    alert2.showAndWait();
} catch (SQLException e) {
    throw new RuntimeException(e);
}
    }
}