package Aplicacion;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControladorSubVentanaCanciones implements Initializable {
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

    @FXML
    private Button nuevo;

    @FXML
    private Button modificar;

    @FXML
    private Button eliminar;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MostrarCanciones();

    }


    @FXML
    public void MostrarCanciones(){
        tableNum.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("trackId"));
        tableNombre.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("Name"));

        SessionFactory sf =  HibernateUtil.getSessionFactory();
        ObservableList<Object> objtlist=null;
        try(Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query Q = session.createQuery("from TrackClass ");
            List<TrackClass> lista = Q.list();
            objtlist = FXCollections.observableArrayList(lista);
            tableView.setItems(objtlist);
        } catch (Exception e) {
            e.printStackTrace();

        }
        FilteredList<Object> filteredData = new FilteredList<>(objtlist, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (cliente instanceof Em) {
                    TrackClass custoObj = (TrackClass) cliente;
                    if (custoObj.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(custoObj.getTrackId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });

    }

    @FXML
    public void seleccionarCancion(){
        Object seleccionado = tableView.getSelectionModel().getSelectedItem();
        if(seleccionado != null){
            labelSeleccionado.setText("Cancion seleccionada");
            nombreSeleccionad.setText(((TrackClass) seleccionado).getName());
            apellidoSeleccionado.setText(((TrackClass) seleccionado).getComposer());
        }
    }

    @FXML
    public void botonAlbums(){

        tableNum.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("albumId"));
        tableNombre.setCellValueFactory( new PropertyValueFactory<Map<String,Object>,Object>("title"));

        SessionFactory sf =  HibernateUtil.getSessionFactory();
        ObservableList<Object> objtlist=null;
        try(Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query Q = session.createQuery("from AlbumClass ");
            List<AlbumClass> lista = Q.list();
            objtlist = FXCollections.observableArrayList(lista);
            tableView.setItems(objtlist);
        } catch (Exception e) {
            e.printStackTrace();


            FilteredList<Object> filteredData = new FilteredList<>(objtlist, p -> true);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(cliente -> {
                    // Si el texto de búsqueda está vacío, mostrar todos los elementos
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (cliente instanceof Em) {
                        AlbumClass custoObj = (AlbumClass) cliente;
                        if (custoObj.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        } else if (String.valueOf(custoObj.getAlbumId()).toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                    }
                    return false;
                });
                tableView.setItems(filteredData);
            });        }

    }

    @FXML
    public void botonPlaylist(){


        Connection conn = null;
        ObservableList<Object> data = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String query = "SELECT playlistId, name FROM playlist";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            data = FXCollections.observableArrayList();

            // Configura las columnas de la tabla
            tableNum.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get("playlistId")));
            tableNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get("name")));

            while (rs.next()) {
                int playlistId = rs.getInt("playlistId");
                String name = rs.getString("name");
                Map<String, Object> row = new HashMap<>();
                row.put("playlistId", playlistId);
                row.put("name", name);
                data.add(row);
            }
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void botonTracks(ActionEvent event) {

MostrarCanciones();
    }
}
