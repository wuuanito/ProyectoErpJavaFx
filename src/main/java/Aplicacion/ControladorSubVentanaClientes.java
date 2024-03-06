package Aplicacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControladorSubVentanaClientes implements Initializable {


    @FXML
    private TableView<Object> tableView;

    @FXML
    private TableColumn<Map<String, Object>, Object> tableNum;

    @FXML
    private TableColumn<Map<String, Object>, Object> tableNombre;
    @FXML
    private TableColumn<Map<String, Object>, Object> tableApellido;

    @FXML
    private Button nuevo;

    @FXML
    private Button modificar;

    @FXML
    private Button eliminar;


    @FXML
    private Label labelSeleccionado;

    @FXML
    private Label nombreSeleccionad;

    @FXML
    private Label apellidoSeleccionado;

    @FXML
    private TextField searchField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MostrarClientes();


    }

    @FXML
    public void MostrarClientes() {

        tableNum.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("customerId"));
        tableNombre.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("firstName"));
        tableApellido.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("lastName"));

        SessionFactory sf = HibernateUtil.getSessionFactory();
        ObservableList<Object> objtlist = null;
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query Q = session.createQuery("from Em where hide = 0");
            List<Em> lista = Q.list();
            objtlist = FXCollections.observableArrayList(lista);
            tableView.setItems(objtlist);
        } catch (Exception e) {
            e.printStackTrace();

        }
        tableView.setRowFactory(tv -> {
            TableRow<Object> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Object obj = row.getItem();
                    if (obj instanceof Em) {
                        Em cliente = (Em) obj;
                        System.out.println("Cliente: " + cliente.getFirstName() + " " + cliente.getLastName());
                        labelSeleccionado.setVisible(true);
                        nombreSeleccionad.setText(cliente.getFirstName());
                        apellidoSeleccionado.setText(cliente.getLastName());

                    }
                }
            });
            return row;
        });
        FilteredList<Object> filteredData = new FilteredList<>(objtlist, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (cliente instanceof Em) {
                    Em custoObj = (Em) cliente;
                    if (custoObj.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(custoObj.getCustomerId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });


    }

    @FXML
    public void mostrarVentanaModificado() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubVentanaUsuarios.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Usuario");
            stage.setScene(scene);

            // Obtener el controlador de la otra ventana
            ControladorSubVentanaUsuarios controller = fxmlLoader.getController();

            // Obtener los datos del nombre y apellido seleccionados
            String nombre = nombreSeleccionad.getText();
            String apellido = apellidoSeleccionado.getText();

            // Pasar los datos a la otra ventana
            controller.setNombreYApellido(nombre, apellido);

            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

    @FXML
    public void eliminarusuario() throws SQLException {
        Object seleccionado = tableView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Em cliente = (Em) seleccionado;

            //Esperar confirmacion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("Eliminar cliente");
            alert.setContentText("¿Estás seguro de que quieres eliminar el cliente " + cliente.getFirstName() + " " + cliente.getLastName() + "?");
            ButtonType buttonTypeYes = new ButtonType("Sí");
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait();
            if (alert.getResult() == buttonTypeYes) {
                cliente.setHide(1);
                SessionFactory sf = HibernateUtil.getSessionFactory();
                try (Session session = sf.openSession()) {
                    Transaction tx = session.beginTransaction();
                    session.update(cliente);
                    tx.commit();

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");

                    String query = "UPDATE usuarios SET hide = 1 WHERE nombre = ?";
                    try {
                        PreparedStatement stmt = conn.prepareStatement(query);
                        String nombre = cliente.getFirstName();
                        String apellido = cliente.getLastName();

                        char primeraLetraNombre = nombre.charAt(0);

                        String nombreCorto = primeraLetraNombre + apellido;
                        stmt.setString(1, nombreCorto);
                        stmt.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                MostrarClientes();
            }


        }
    }


}

@FXML
    public void nuevoUsuario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubVentanaNuevoUsuario.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Creacion de usuario");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}



