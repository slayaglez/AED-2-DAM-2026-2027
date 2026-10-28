package es.ejemplo.gestion.config;

import es.ejemplo.gestion.exception.AplicationException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

/**
 * Abre una conexion en la base SQLite indicada por el constructor.
 */
public class ConexionBD {
    private final String url;

    public ConexionBD(String url) {
        if (url == null || url.isBlank()) {
            throw new AplicationException("La url de la base de datos no puede estar vacia");
        }

        String urlNormalizada = url;

        if(url.matches("^jdbc:sqlite:.*$")) {
            urlNormalizada = url.substring(12);
        }

        if (!Files.exists(Path.of(urlNormalizada))){
            throw new AplicationException("La base de datos no existe");
        }
        this.url = url;
    }

    public Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new AplicationException("No se pudo abrir la conexion: " + e.getMessage(), e);
        }
    }
}