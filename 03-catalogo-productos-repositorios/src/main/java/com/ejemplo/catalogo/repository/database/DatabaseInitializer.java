package com.ejemplo.catalogo.repository.database;

import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseInitializer {
    String url; //data/app.db
    Path path;
    //File/Files

    public DatabaseInitializer(String url) {

        if (url == null || url.isBlank()) {
            url = "data/app.db";
        }
        path = Path.of(url);
        if (!Files.exists(path)) {

        }
    }
}
