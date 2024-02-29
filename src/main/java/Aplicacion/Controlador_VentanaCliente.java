package Aplicacion;

import com.itextpdf.kernel.colors.*;
import com.itextpdf.layout.element.Paragraph;

import com.itextpdf.layout.properties.TextAlignment;
import javafx.beans.property.SimpleObjectProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import java.io.File;


public class Controlador_VentanaCliente implements Initializable {

    public static String usuario;
    public static int loginId;
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

    @FXML HBox hboxMusicaGuardada;


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

    @FXML
    private Button exportarPdf;

    @FXML
    private ComboBox<String> HistorialCompras;

    @FXML
    private Button salir;

    @FXML
    private CheckBox ModoOscuro;

    @FXML
    private BorderPane borderPane;

    private Configuracion configuracion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        InfoUsuario.setText(usuario);

        // Cargar la configuración al iniciar la ventana
        configuracion = Configuracion.cargarConfiguracion();
        // Establecer el estado del checkbox basado en la configuración cargada
        ModoOscuro.setSelected(configuracion.isModoOscuro());
        // Aplicar el estilo según la configuración cargada
        cambiarEstilo(configuracion.isModoOscuro());

        // Agregar un listener al checkbox para cambiar la configuración y el estilo al seleccionar/deseleccionar
        ModoOscuro.selectedProperty().addListener((observable, oldValue, newValue) -> {
            configuracion.setModoOscuro(newValue);
            cambiarEstilo(newValue);
            // Guardar la configuración al cambiar el estado del checkbox
            configuracion.guardarConfiguracion();
        });
        fillComboBox();

    }
    private void cambiarEstilo(boolean modoOscuro) {
        if (modoOscuro) {
            borderPane.getStylesheets().clear();
            borderPane.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        } else {
            borderPane.getStylesheets().clear();
            borderPane.getStylesheets().add(getClass().getResource("modoclaro.css").toExternalForm());
        }
    }


    public void cerrarSesion(){
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PantallaInicio.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setTitle("Inicio");
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void fillComboBox() {
        ObservableList<String> options = FXCollections.observableArrayList();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String query = "SELECT InvoiceDate FROM Invoice WHERE CustomerId = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(loginId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("InvoiceDate"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        HistorialCompras.setItems(options);

        //seleccionar el id de la compra
        HistorialCompras.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println("Item seleccionado: " + t1);
            }
        });



    }

    @FXML
    private void MostrarGeneros() {
        fillComboBox();


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
        fillComboBox();

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
    private void MostrarMusicaGuardada(){
        fillComboBox();


        hboxMusicaGuardada.setStyle("-fx-background-color: #E0E0E0;");
        LabelAlbums.setText("Musica Guardada");

        hboxPlaylist.getScene().setCursor(Cursor.HAND);
        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get("TrackId")));
        titleColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get("Name")));

        Connection conn = null;
        ObservableList<Object> data = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
            String query = "SELECT Track.TrackId, Track.Name AS TrackName\n" +
                    "FROM Invoice\n" +
                    "JOIN InvoiceLine ON Invoice.InvoiceId = InvoiceLine.InvoiceId\n" +
                    "JOIN Customer ON Invoice.CustomerId = Customer.CustomerId\n" +
                    "JOIN Track ON InvoiceLine.TrackId = Track.TrackId\n" +
                    "WHERE Customer.CustomerId = '" + loginId + "';";

            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            data = FXCollections.observableArrayList();

            while (rs.next()) {
                int TrackId = rs.getInt("TrackId");
                String Name = rs.getString("TrackName"); // Corrected column name to TrackName
                Map<String, Object> row = new HashMap<>();
                row.put("TrackId", TrackId);
                row.put("Name", Name);
                data.add(row);
            }

            tableView.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void exportarAPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                // Crear el título del documento PDF
                Paragraph title = new Paragraph("Fecha de factura: " + HistorialCompras.getValue() + "\n\n" + "Historial de compras")
                        .setFontSize(24)
                        .setFontColor(ColorConstants.BLACK)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(20);
                document.add(title);

                // Crear la tabla en el documento PDF
                Table pdfTable = new Table(new float[]{2, 4, 3}).useAllAvailableWidth();

                // Configurar el encabezado de la tabla
                pdfTable.addHeaderCell("Nº").setBold();
                pdfTable.addHeaderCell("Cancion").setBold();
                pdfTable.addHeaderCell("Precio").setBold();

                double total = 0.0; // Variable para almacenar el precio total

                // Realizar la consulta a la base de datos
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root");
                     PreparedStatement ps = conn.prepareStatement("SELECT Track.TrackId,Track.Name, InvoiceLine.UnitPrice FROM InvoiceLine JOIN Track ON InvoiceLine.TrackId = Track.TrackId JOIN Invoice ON InvoiceLine.InvoiceId = Invoice.InvoiceId WHERE Invoice.CustomerId = ? AND Invoice.InvoiceDate = ?")) {
                    ps.setString(1, String.valueOf(loginId));
                    ps.setString(2, HistorialCompras.getValue());
                    try (ResultSet rs = ps.executeQuery()) {
                        // Agregar las filas a la tabla del PDF
                        while (rs.next()) {
                            pdfTable.addCell(rs.getString("TrackId"));
                            pdfTable.addCell(rs.getString("Name"));
                            pdfTable.addCell(rs.getString("UnitPrice")+ " €");

                            // Sumar el precio al total
                            total += rs.getDouble("UnitPrice");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // Agregar la fila del total al final de la tabla
                pdfTable.addFooterCell("Total:").setBold();
                pdfTable.addFooterCell("").setBold();
                pdfTable.addFooterCell(String.valueOf(total)+" €").setBold();

                // Agregar la tabla al documento
                document.add(pdfTable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void MostrarPlaylist() {
        fillComboBox();


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

            fillComboBox();


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
                        showAlbumDetails(album);
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
    @FXML
    private void restaurarFondoYCursorMusicaGuardada() {
        // Restaurar el fondo del HBox al color original
        hboxMusicaGuardada.setStyle("-fx-background-color: transparent;");

        // Restaurar el cursor al predeterminado
        hboxMusicaGuardada.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void cambiarFormaCursorMusicaGuardada() {
        hboxMusicaGuardada.getScene().setCursor(Cursor.HAND);
        hboxMusicaGuardada.setStyle("-fx-background-color: #E0E0E0;");

    }

    @FXML
    private void restaurarFormaCursorMusicaGuardada() {
        hboxMusicaGuardada.getScene().setCursor(Cursor.DEFAULT);
        hboxMusicaGuardada.setStyle("-fx-background-color: transparent;");

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


    private static boolean ventanaComprasAbierta = false;

    @FXML
    private void abrirVentanaCompras() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaCompras.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Compras");
            stage.setScene(scene);
            stage.show();
            // Pasar customerId a la ventana de compras
            ControladorVentanaCompras controller = loader.getController();
            controller.initData(loginId);

            // Marcar la ventana como abierta
            ventanaComprasAbierta = true;

            // Manejar el evento de cierre de la ventana
            stage.setOnCloseRequest(windowEvent -> {
                // Llamar al método para cerrar la ventana
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}