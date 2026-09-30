package com.ejemplo.catalogo.configuration;

import com.ejemplo.catalogo.repository.file.IRepository;
import com.ejemplo.catalogo.repository.file.JsonRepository;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppConfiguration {
    private static Path path;
    private static Properties props;
    static IRepository repository;

    public static void main(String[] args) {
        path = Path.of("data", "app.properties");
        props = new Properties();

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(props.getProperty("storage.format")+" "+props.getProperty("storage.fichero", "no existe"));
        props.setProperty("app.name", "Filelab");
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            props.store(writer, "Hola");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (props.getProperty("storage.format").equals("json")){
            repository = new JsonRepository(Path.of(props.getProperty("storage.path")));
        }
    }

}
