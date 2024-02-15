package Aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

public class InicioAplicacion extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(InicioAplicacion.class.getResource("PantallaInicio.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Inicio");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);

            SessionFactory sf =  HibernateUtil.getSessionFactory();

            try (Session session = sf.openSession()) {

                Transaction tx = session.beginTransaction();
                Query Q = session.createQuery("select billingCountry, sum(total) from InvoiceClass group by billingCountry order by sum(total) desc");

                Iterator I = Q.iterate();

                while(I.hasNext())
                {
                    Object [] resul = (Object[]) I.next();
                    System.out.printf("Pais: %s Suma: %f%n",(String)resul[0],(BigDecimal)resul[1]);
                }

                tx.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

    } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}