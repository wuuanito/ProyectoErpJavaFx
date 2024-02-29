package Aplicacion;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ControladorVentanaCompras implements Initializable {
    private static final SessionFactory sf = new Configuration().configure().buildSessionFactory();
    private FilteredList<Object> filteredData;


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

    private static int customerId;

    @FXML
    private TextField buscador;
    private boolean ventanaComprasAbierta = false;

    @FXML
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                // Llamar al método para cerrar la ventana
                cerrarVentana();
            }
        });

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
        ObservableList<Object> objectList = FXCollections.observableArrayList();

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("FROM TrackClass");
        List<TrackClass> results = query.list();
        objectList.addAll(results);
        tx.commit();
        session.close();

        filteredData = new FilteredList<>(objectList, p -> true);

        buscador.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(name -> {
                // Si el texto de búsqueda está vacío, mostrar todos los elementos
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (name instanceof TrackClass) {
                    TrackClass objTrack = (TrackClass) name;
                    if (objTrack.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(objTrack.getName()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
            tableView.setItems(filteredData);
        });


    }


    //Funcionalidad del boton comprar que se encuentra en la ventana de compras
    public void comprar() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chinook", "root", "root")) {
            // Deshabilitar el modo de autocommit
            connection.setAutoCommit(false);

            // Crear la factura
            String createInvoiceQuery = "INSERT INTO invoice (CustomerId, InvoiceDate, Total) VALUES (?, ?, ?)";
            try (PreparedStatement createInvoiceStmt = connection.prepareStatement(createInvoiceQuery, Statement.RETURN_GENERATED_KEYS)) {
                createInvoiceStmt.setInt(1, customerId);
                createInvoiceStmt.setDate(2, Date.valueOf(LocalDate.now()));
                BigDecimal totalAmount = calculateTotal();
                createInvoiceStmt.setBigDecimal(3, totalAmount);
                createInvoiceStmt.executeUpdate();

                // Obtener el ID de la factura recién creada
                ResultSet generatedKeys = createInvoiceStmt.getGeneratedKeys();
                int invoiceId = -1;
                if (generatedKeys.next()) {
                    invoiceId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la factura generada.");
                }

// Insertar las líneas de factura
                String createInvoiceLineQuery = "INSERT INTO invoiceline (InvoiceId, TrackId, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
                try (PreparedStatement createInvoiceLineStmt = connection.prepareStatement(createInvoiceLineQuery, Statement.RETURN_GENERATED_KEYS)) {
                    for (Object item : tableviewprecio.getItems()) {
                        if (item instanceof TrackClass) {
                            TrackClass track = (TrackClass) item;
                            createInvoiceLineStmt.setInt(1, invoiceId);
                            createInvoiceLineStmt.setInt(2, track.getTrackId());
                            createInvoiceLineStmt.setBigDecimal(3, track.getUnitPrice());
                            createInvoiceLineStmt.setInt(4, 1); // Suponiendo que la cantidad siempre es 1
                            createInvoiceLineStmt.executeUpdate();

                            // Obtener el ID de la línea de factura recién creada (opcional)
                            ResultSet lineGeneratedKeys = createInvoiceLineStmt.getGeneratedKeys();
                            int invoiceLineId = -1;
                            if (lineGeneratedKeys.next()) {
                                invoiceLineId = lineGeneratedKeys.getInt(1);
                                // Puedes usar invoiceLineId si necesitas el ID de la línea de factura para algo más
                            }
                        }
                    }
                }
            }


connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Realizando compra");
        alert.setHeaderText("Procesando...");

        // Hacer la alerta redimensionable
        alert.setResizable(true);

        // Crear ProgressBar
        ProgressBar progressBar = new ProgressBar();

        // Animación para llenar la barra progresivamente en 4 segundos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new javafx.animation.KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(4), new javafx.animation.KeyValue(progressBar.progressProperty(), 1))

        );

        timeline.setOnFinished(event -> {
            // Cuando la animación termina, cambiar el título y el encabezado de la alerta
            alert.setTitle("Compra realizada");
            alert.setHeaderText("¡Compra realizada con éxito!");
            tableView.getScene().getWindow().hide();

        });

        timeline.play();

        // Personalizar el diseño del cuadro de diálogo
        VBox content = new VBox();
        content.setSpacing(10);

        // Usar un StackPane para que el ProgressBar llene todo el espacio disponible
        StackPane progressBarContainer = new StackPane();
        progressBarContainer.getChildren().add(progressBar);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.setMaxHeight(Double.MAX_VALUE);
        StackPane.setAlignment(progressBar, javafx.geometry.Pos.CENTER);

        content.getChildren().addAll(progressBarContainer);
        alert.getDialogPane().setContent(content);

        // Definir el tamaño de la alerta
        alert.getDialogPane().setPrefSize(400, 200);

        alert.showAndWait();
    }

    // Método para calcular el total de la factura
    private BigDecimal calculateTotal() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Object item : tableviewprecio.getItems()) {
            if (item instanceof TrackClass) {
                TrackClass track = (TrackClass) item;
                totalAmount = totalAmount.add(track.getUnitPrice());
            }
        }
        return totalAmount;
    }




    public int initData(int loginId) {
        customerId = loginId;
        return customerId;

    }


    public void setVentanaComprasAbierta(boolean ventanaComprasAbierta) {
        this.ventanaComprasAbierta = ventanaComprasAbierta;
    }

    public void cerrarVentana() {
        // Aquí puedes establecer la variable ventanaComprasAbierta como false
        setVentanaComprasAbierta(false);
    }
}