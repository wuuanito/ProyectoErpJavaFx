package Aplicacion;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class Controlador_VentanaCliente implements Initializable {

    public static String usuario;
    @FXML
    private Label LabelAlbums;
    @FXML
    private HBox MenuAlbums;
    @FXML
    private HBox hboxGeneros;

    @FXML
    private HBox hboxArtistas;
    @FXML
    private HBox hboxPlaylist;



    @FXML
    private TableView<Object> tableView;

    @FXML
    private TableColumn<Map<String, Object>, Object> idColumn;

    @FXML
    private TableColumn<Map<String, Object>, Object> titleColumn;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea areaText;

    @FXML
    private Label InfoUsuario;

    @FXML
    private ListView<?> suggestionsListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        InfoUsuario.setText(usuario);

        //Al cerrar la ventana salga dos opciones
        //1. Cerrar la ventana




    }

    @FXML
    private void MostrarGeneros() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("genreId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("name"));
        hboxGeneros.setStyle("-fx-background-color: #E0E0E0;");
        LabelAlbums.setText("Generos");

        hboxGeneros.getScene().setCursor(Cursor.HAND);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        ObservableList<Object> objectList = null;
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("select genre from GenreClass genre");
            List<GenreClass> resultList = query.list();
            objectList = FXCollections.observableArrayList(resultList);
            tableView.setItems(objectList);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FilteredList<Object> filteredData = new FilteredList<>(objectList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(name -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (name instanceof GenreClass) {
                    GenreClass genreObj = (GenreClass) name;
                    if (genreObj.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(genreObj.getName()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });
    }
    @FXML
    private void MostrarArtistas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("artistId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("name"));
        hboxArtistas.setStyle("-fx-background-color: #E0E0E0;");
        LabelAlbums.setText("Artistas");

        hboxArtistas.getScene().setCursor(Cursor.HAND);
        SessionFactory sf = HibernateUtil.getSessionFactory();
        ObservableList<Object> objectList = null;
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("select artist from ArtistClass artist");
            List<ArtistClass> resultList = query.list();
            objectList = FXCollections.observableArrayList(resultList);
            tableView.setItems(objectList);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FilteredList<Object> filteredData = new FilteredList<>(objectList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(name -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (name instanceof ArtistClass) {
                    ArtistClass artistObj = (ArtistClass) name;
                    if (artistObj.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(artistObj.getName()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });
    }
    @FXML
    private void MostrarPlaylist() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("playlistId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("name"));
        hboxPlaylist.setStyle("-fx-background-color: #E0E0E0;");
        LabelAlbums.setText("Playlist");

        hboxPlaylist.getScene().setCursor(Cursor.HAND);

        //Conectar con la BDD con sql

        Connection conn = null;
        ObservableList<Object> data = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String query = "SELECT playlistId, name FROM playlist";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            data = FXCollections.observableArrayList();

            // Configura las columnas de la tabla
            idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get("playlistId")));
            titleColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get("name")));

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
        FilteredList<Object> filteredData = new FilteredList<>(data, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(row -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (row instanceof Map) {
                    Map<String, Object> rowData = (Map<String, Object>) row;
                    // Comprueba si alguna de las columnas contiene el texto de búsqueda
                    for (Object value : rowData.values()) {
                        if (value != null && value.toString().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });

    }





    @FXML
    private void MostrarAlbums() {
        MenuAlbums.setStyle("-fx-background-color: #E0E0E0;");
        MenuAlbums.getScene().setCursor(Cursor.HAND);
        idColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("albumId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Map<String, Object>, Object>("title"));

        SessionFactory sf = HibernateUtil.getSessionFactory();
        ObservableList<Object> albumList = null;
        try (Session session = sf.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("select album from AlbumClass album");
            List<AlbumClass> resultList = query.list();
            albumList = FXCollections.observableArrayList((List<Object>) (List<?>) resultList); // Conversión de lista para que coincida con el tipo ObservableList<Object>
            tableView.setItems(albumList);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LabelAlbums.setText("Albums");

        FilteredList<Object> filteredData = new FilteredList<>(albumList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(album -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (album instanceof AlbumClass) {
                    AlbumClass albumObj = (AlbumClass) album;
                    if (albumObj.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(albumObj.getAlbumId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });

        tableView.setRowFactory(tv -> {
            TableRow<Object> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Object obj = row.getItem();
                    if (obj instanceof AlbumClass) {
                        AlbumClass album = (AlbumClass) obj;
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
                    }
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
    @FXML
    private void restaurarFondoYCursorGeneros() {
        // Restaurar el fondo del HBox al color original
        hboxGeneros.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxGeneros.getScene().setCursor(Cursor.DEFAULT);
    }
    @FXML
    private void cambiarFormaCursorGeneros() {
        hboxGeneros.getScene().setCursor(Cursor.HAND);
        hboxGeneros.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorGeneros() {
        hboxGeneros.getScene().setCursor(Cursor.DEFAULT);
        hboxGeneros.setStyle("-fx-background-color: transparent;");

    }
    @FXML
    private void restaurarFondoYCursorArtistas() {
        // Restaurar el fondo del HBox al color original
        hboxArtistas.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxArtistas.getScene().setCursor(Cursor.DEFAULT);
    }
    @FXML
    private void cambiarFormaCursorArtistas() {
        hboxArtistas.getScene().setCursor(Cursor.HAND);
        hboxArtistas.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorArtistas() {
        hboxArtistas.getScene().setCursor(Cursor.DEFAULT);
        hboxArtistas.setStyle("-fx-background-color: transparent;");

    }
    @FXML
    private void restaurarFondoYCursorPlaylist() {
        // Restaurar el fondo del HBox al color original
        hboxPlaylist.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxPlaylist.getScene().setCursor(Cursor.DEFAULT);
    }
    @FXML
    private void cambiarFormaCursorPlaylist() {
        hboxPlaylist.getScene().setCursor(Cursor.HAND);
        hboxPlaylist.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorPlaylist() {
        hboxPlaylist.getScene().setCursor(Cursor.DEFAULT);
        hboxPlaylist.setStyle("-fx-background-color: transparent;");

    }
    private void showAlbumDetails(AlbumClass album) {
        try {
            int albumId = album.getAlbumId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaAlbums.fxml"));
            Parent root = loader.load();
            ControladorVentanaAlbums controller = loader.getController();
            controller.initData(albumId);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Lista de Pistas");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
