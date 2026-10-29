package es.ejemplo.taller.infra;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {

    private ConexionBD() {}

    /**
     * Devuelve una conexión abierta cuya responsabilidad de cierre tiene el llamador.
     * Si la ruta, el archivo o el esquema no son válidos, lanza SQLException.
     */
    public static Connection abrir(String url) throws SQLException {

        if(url == null || url.isBlank()){ throw new SQLException("La ruta a la BBDD es inválida"); }

        Path path = null;

        if(url.startsWith("jdbc:sqlite:")) {
            path = Path.of(url.substring(12));
        } else {
            path = Path.of(url);
        }

        if(!Files.exists(path)) { throw new SQLException("La ruta a la BBDD es inválida"); }

        return DriverManager.getConnection(url);
    }
}
