package Aplicacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.*;

public class Controlador_VentanaCliente implements Initializable {

    @FXML
    private Label LabelAlbums;
    @FXML
    private HBox MenuAlbums;

    @FXML
    private TableView<AlbumClass> tableView;

    @FXML
    private TableColumn<AlbumClass, Integer> idColumn;

    @FXML
    private TableColumn<AlbumClass, String> titleColumn;

    @FXML
    private TextArea areaText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SessionFactory sf =  HibernateUtil.getSessionFactory();

        try (Session session = sf.openSession()) {

            Transaction tx = session.beginTransaction();
            Query Q = session.createQuery("select albumId,title from AlbumClass");

            Iterator I = Q.iterate();

            while(I.hasNext())

            {
                Object [] resul = (Object[]) I.next();
                System.out.printf("Id: %d Titulo: %s%n",(Integer)resul[0],(String)resul[1]);
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void cambiarFondoYCursor() {
        // Cambiar el fondo del HBox a un color gris suave
        MenuAlbums.setStyle("-fx-background-color: #E0E0E0;");

        // Cambiar el cursor a la forma de selección
        MenuAlbums.getScene().setCursor(Cursor.HAND);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("albumId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Aquí ejecuta tu consulta HQL y obtén los resultados
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("select album from AlbumClass album");
            List<AlbumClass> resultList = query.list();
            ObservableList<AlbumClass> albumList = FXCollections.observableArrayList(resultList);

            // Agregar los resultados al TableView
            tableView.setItems(albumList);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void restaurarFondoYCursor() {
        // Restaurar el fondo del HBox al color original
        MenuAlbums.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        MenuAlbums.getScene().setCursor(Cursor.DEFAULT);
    }
    @FXML
    private void cambiarFormaCursor() {
        MenuAlbums.getScene().setCursor(Cursor.HAND);
        MenuAlbums.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursor() {
        MenuAlbums.getScene().setCursor(Cursor.DEFAULT);
        MenuAlbums.setStyle("-fx-background-color: transparent;");

    }

}
