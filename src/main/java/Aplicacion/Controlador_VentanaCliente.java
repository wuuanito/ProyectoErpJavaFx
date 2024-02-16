package Aplicacion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
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
    private TextField searchField;

    @FXML
    private TextArea areaText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("albumId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

    }
    @FXML
    private void cambiarFondoYCursor() {
        MenuAlbums.setStyle("-fx-background-color: #E0E0E0;");

        MenuAlbums.getScene().setCursor(Cursor.HAND);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("albumId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        SessionFactory sf = HibernateUtil.getSessionFactory();
        ObservableList<AlbumClass> albumList = null;
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("select album from AlbumClass album");
            List<AlbumClass> resultList = query.list();
            albumList = FXCollections.observableArrayList(resultList);

            tableView.setItems(albumList);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FilteredList<AlbumClass> filteredData = new FilteredList<>(albumList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(album -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (album.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(album.getAlbumId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
        });
            tableView.setItems(filteredData);

    }
    );
        // Configurar el manejador de eventos de clic en la tabla
        tableView.setRowFactory(tv -> {
            TableRow<AlbumClass> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    AlbumClass album = row.getItem();
                    showAlbumDetails(album);
                }
            });
            return row;
        });
        tableView.setRowFactory(tv -> {
            TableRow<AlbumClass> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) { // Verificar si se ha hecho clic una vez en la fila
                    AlbumClass album = row.getItem();
                    int albumId = album.getAlbumId();
                    System.out.println("AlbumId seleccionado: " + albumId);
                    try {
                        // Cargar el archivo FXML de la ventana de lista de pistas
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaAlbums.fxml"));
                        Parent root = loader.load();

                        // Obtener el controlador de la ventana de lista de pistas
                        ControladorVentanaAlbums controller = loader.getController();

                        // Pasar la lista de pistas al controlador
                        controller.initData(albumId);
                         //pasar el nombre del album
                        controller.initData2(album.getTitle());




                        // Crear una nueva escena con el contenido cargado desde el archivo FXML
                        Scene scene = new Scene(root);

                        // Crear una nueva ventana y mostrarla
                        Stage stage = new Stage();
                        stage.setTitle("Lista de Pistas");
                        stage.setScene(scene);
                        stage.show();
                        stage.setResizable(false);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Luego puedes pasar este albumId a donde lo necesites, por ejemplo, a la ventana VentanaAlbums.fxml
                }
            });
            return row;
        });
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
    private void showAlbumDetails(AlbumClass album) {
        try {
            int albumId = album.getAlbumId();
            // Cargar el archivo FXML de la ventana de lista de pistas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaAlbums.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana de lista de pistas
            ControladorVentanaAlbums controller = loader.getController();

            // Pasar la lista de pistas al controlador
            controller.initData(albumId);




            // Crear una nueva escena con el contenido cargado desde el archivo FXML
            Scene scene = new Scene(root);

            // Crear una nueva ventana y mostrarla
            Stage stage = new Stage();
            stage.setTitle("Lista de Pistas");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
