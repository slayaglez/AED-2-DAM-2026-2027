package com.ejemplo.catalogo.repository.database;

import org.sqlite.util.StringUtils;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseInit {
    String url = "jdbc:sqlite:data/app.db";
    Path path;

    public DataBaseInit(String url) {

        if (url == null || url.isEmpty()) {
            url = "data/app.db";
        }

        String sql = """
                CREATE TABLE IF NOT EXIST producto (
                id INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                precio REAL NOT NULL);
                """;

        try (Connection c = DriverManager.getConnection(url);
             Statement st = c.createStatement()) {
             st.execute(sql);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error creando esquema", e);
        }

        path = Path.of(url);

        try (
                Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Conectado a SQLite");
        } catch (
                SQLException e) {
            throw new RepositoryException("Error conectando con SQLite", e);
        }
    }
}
