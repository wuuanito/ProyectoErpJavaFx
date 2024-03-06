package Aplicacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControladorSubVentanaEmpleados implements Initializable {


    @FXML
    private TableView<Object> tableView;

    @FXML
    private TableColumn<Map<String, Object>, Object> tableNum;

    @FXML
    private TableColumn<Map<String, Object>, Object> tableNombre;
    @FXML
    private TableColumn<Map<String, Object>, Object> tableApellido;


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

        MostrarEmpleados();



    }

    @FXML
    public void MostrarEmpleados(){

        tableNum.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("employeeId"));
        tableNombre.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("firstName"));
        tableApellido.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("lastName"));

        SessionFactory sf =  HibernateUtil.getSessionFactory();
        ObservableList<Object> objtlist=null;
        try(Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query Q = session.createQuery("from EmployeeClass ");
            List<EmployeeClass> lista = Q.list();
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
                    if (obj instanceof EmployeeClass) {
                        EmployeeClass empleado = (EmployeeClass) obj;
                        labelSeleccionado.setVisible(true);
                        nombreSeleccionad.setText(empleado.getFirstName());
                        apellidoSeleccionado.setText(empleado.getLastName());

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

                if (cliente instanceof EmployeeClass) {
                    EmployeeClass custoObj = (EmployeeClass) cliente;
                    if (custoObj.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(custoObj.getEmployeeId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });


    }


}

