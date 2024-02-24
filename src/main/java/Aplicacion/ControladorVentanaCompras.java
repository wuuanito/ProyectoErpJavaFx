package Aplicacion;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControladorVentanaCompras implements Initializable {

    @FXML
    private TableView<Object> tableView;

    @FXML
    private TableColumn<Map<String, Object>, Object> nombreColum;

    @FXML
    private TableColumn<Map<String, Object>, Object> precioColum;
    @FXML
    private TableView<Object> tableviewprecio;

    @FXML
    private TableColumn<Map<String, Object>, Object> columnaNombre;

    @FXML
    private TableColumn<Map<String, Object>, Object> precioColumna1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nombreColum.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("Name"));
        precioColum.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("UnitPrice"));
        columnaNombre.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("Name")));
        precioColumna1.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("UnitPrice")));


        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query Q = session.createQuery("select Name from TrackClass Name");
            List<TrackClass> results = Q.list();
            for (TrackClass track : results) {
                tableView.getItems().add(track);
            }
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();

        }



    }

    public void meterCancionesAlaOtraTabla() {


        Map<String, Object> selectedSong = (Map<String, Object>) tableView.getSelectionModel().getSelectedItem();

        // Verificar si se seleccionó un elemento antes de agregarlo a tableviewprecio
        if (selectedSong != null) {
            // Agregar el elemento seleccionado a tableviewprecio
            tableviewprecio.getItems().add(selectedSong);


        }
    }
}