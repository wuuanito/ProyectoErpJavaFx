package Aplicacion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControladorSubVentanaUsuarios implements Initializable {


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

    public void setNombreYApellido(String nombre, String apellido) {

        fieldNombre.setText(nombre);
        fieldApellido.setText(apellido);


    }

    public void rellenarCampos() {

        //consulta a la base de datos para rellenar los campos donde se muestra la informacion del usuario que sea seleccionado






        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String sql = "SELECT * FROM customer WHERE firstName = ? AND lastName = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fieldNombre.getText());
            stmt.setString(2, fieldApellido.getText());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fieldEmpresa.setText(rs.getString("Company"));
                fieldDireccion.setText(rs.getString("Address"));
                fieldCiudad.setText(rs.getString("City"));
                fieldEstado.setText(rs.getString("State"));
                fieldCodigoPostal.setText(rs.getString("PostalCode"));
                fieldPais.setText(rs.getString("Country"));
                fieldEmail.setText(rs.getString("Email"));
                fieldTlfn.setText(rs.getString("Phone"));
                fieldFax.setText(rs.getString("Fax"));
            }
        } catch (SQLException e) {
            e.printStackTrace();



    }
    }

    public void guardarCambios() {
        //guardar los cambios realizados en la base de datos
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String sql = "UPDATE customer SET Company = ?, Address = ?, City = ?, State = ?, PostalCode = ?, Country = ?, Email = ?, Phone = ?, Fax = ? WHERE firstName = ? AND lastName = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fieldEmpresa.getText());
            stmt.setString(2, fieldDireccion.getText());
            stmt.setString(3, fieldCiudad.getText());
            stmt.setString(4, fieldEstado.getText());
            stmt.setString(5, fieldCodigoPostal.getText());
            stmt.setString(6, fieldPais.getText());
            stmt.setString(7, fieldEmail.getText());
            stmt.setString(8, fieldTlfn.getText());
            stmt.setString(9, fieldFax.getText());
            stmt.setString(10, fieldNombre.getText());
            stmt.setString(11, fieldApellido.getText());
            stmt.executeUpdate();

            //mensaje de confirmacion en alerta
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Cambios guardados correctamente");
            alert.showAndWait();

            //cerrar la ventana
            Stage stage = (Stage) btnGuardar.getScene().getWindow();
            stage.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
