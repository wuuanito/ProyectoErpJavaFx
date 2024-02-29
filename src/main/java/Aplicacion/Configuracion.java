package Aplicacion;

import java.io.*;
import java.util.Properties;
public class Configuracion {
    private boolean modoOscuro;
    private static final String ARCHIVO_CONFIGURACION = "config.properties";

    public Configuracion(boolean modoOscuro) {
        this.modoOscuro = modoOscuro;
    }

    public boolean isModoOscuro() {
        return modoOscuro;
    }

    public void setModoOscuro(boolean modoOscuro) {
        this.modoOscuro = modoOscuro;
    }

    // Método para cargar la configuración desde un archivo
    public static Configuracion cargarConfiguracion() {
        Properties properties = new Properties();
        boolean modoOscuro = false; // Valor predeterminado
        try (InputStream inputStream = new FileInputStream(ARCHIVO_CONFIGURACION)) {
            properties.load(inputStream);
            modoOscuro = Boolean.parseBoolean(properties.getProperty("modoOscuro", "false"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Configuracion(modoOscuro);
    }

    // Método para guardar la configuración en un archivo
    public void guardarConfiguracion() {
        Properties properties = new Properties();
        properties.setProperty("modoOscuro", Boolean.toString(modoOscuro));
        try (OutputStream outputStream = new FileOutputStream(ARCHIVO_CONFIGURACION)) {
            properties.store(outputStream, "Configuracion de la Aplicacion");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
