package Aplicacion;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ControladorVentanaCompras implements Initializable {

    @FXML
    private TableView<Object> tableView;

    @FXML
    private Label TotalPrecio;

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
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        tableView.setRowFactory(tv -> {
            TableRow<Object> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Object obj = row.getItem();
                    if (obj instanceof TrackClass) {
                        TrackClass track = (TrackClass) obj;

                        // Agregar los datos de la track a la tableviewprecio
                        TableColumn<Object, String> nombreColumnaPrecio = new TableColumn<>("Nombre");
                        nombreColumnaPrecio.setCellValueFactory(new PropertyValueFactory<>("Name"));
                        TableColumn<Object, Double> precioColumnaPrecio = new TableColumn<>("Precio");
                        precioColumnaPrecio.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
                        tableviewprecio.getColumns().setAll(nombreColumnaPrecio, precioColumnaPrecio);

                        // Añadir el track a la tableviewprecio
                        tableviewprecio.getItems().add(track);

                        // Calcular la suma total de los UnitPrice
                        BigDecimal currentTotal = total.get();
                        currentTotal = currentTotal.add(track.getUnitPrice());
                        total.set(currentTotal);

                        // Mostrar el total en el Label TotalPrecio
                        TotalPrecio.setText(currentTotal.toString()+" €");
                    }
                }
            });
            return row;
        });

//



    }



}