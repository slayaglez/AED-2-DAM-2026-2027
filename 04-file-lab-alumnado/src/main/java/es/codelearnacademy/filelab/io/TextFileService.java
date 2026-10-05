package es.codelearnacademy.filelab.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextFileService {

    public boolean escribir(Path path, String contenido) {
        try {
            Files.writeString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String leer(Path path) {
        throw new UnsupportedOperationException("Función no implementada");
    }

    public boolean escribirLineas(Path path, List<String> lineas) {
        throw new UnsupportedOperationException("Función no implementada");
    }

    public List<String> leerLineas(Path path) {
        throw new UnsupportedOperationException("Función no implementada");
    }

    public boolean anexar(Path path, String contenido) {
        throw new UnsupportedOperationException("Función no implementada");
    }
}
