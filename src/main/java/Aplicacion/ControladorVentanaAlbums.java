package Aplicacion;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ControladorVentanaAlbums implements Initializable {

    @FXML
    private TableView<TrackClass> trackTableView;

    @FXML
    private TableColumn<TrackClass, String> titleColumn;

    @FXML
    private TableColumn<TrackClass, Integer> durationColumn;

    @FXML
    private Label TituloAlbum;

    private int albumId; // Variable para almacenar el AlbumId
    @FXML

    public void initData(int albumId) {
        this.albumId = albumId; // Asignar el AlbumId recibido
        // Realizar acciones adicionales si es necesario
        System.out.println("AlbumId recibido: " + albumId);
        TituloAlbum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                double labelWidth = TituloAlbum.getWidth();
                if (labelWidth > 0) {
                    double fontSize = labelWidth * 0.1; // Ajusta este valor según lo que necesites
                    TituloAlbum.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
                }
            }
        });
        cargarPistasDesdeBaseDeDatos();
    }
    public void initData2(String name)
    {
        System.out.println("AlbumId recibido: " + name);
        cargarPistasDesdeBaseDeDatos();
        TituloAlbum.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las columnas de la tabla
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Mostrar la duración en minutos y segundos

        durationColumn.setCellValueFactory(new PropertyValueFactory<>("milliseconds"));
        durationColumn.setCellFactory(column -> {
            return new TableCell<TrackClass, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        int minutes = item / 60000;
                        int seconds = (item % 60000) / 1000;
                        setText(String.format("%d:%02d", minutes, seconds));
                    }
                }
            };
        });


    }
    private void cargarPistasDesdeBaseDeDatos() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        ObservableList<TrackClass> trackClasses = FXCollections.observableArrayList();
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<TrackClass> query = session.createQuery("from TrackClass track where track.albumId = :albumId", TrackClass.class);
            query.setParameter("albumId", albumId);
            List<TrackClass> resultList = query.list();
            trackClasses.addAll(resultList);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        trackTableView.setItems(trackClasses);
    }


}
