package es.codelearnacademy.filelab.config;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.*;
import java.util.*;

public class PropertiesConfig {
    private final Path path;
    private static Properties props;

    public PropertiesConfig(Path path) {
        this.path = path;
        props = new Properties();
    }

    public Optional<String> get(String key) {
        try (Reader reader = Files.newBufferedReader(path)) {
            props.load(reader);
            return Optional.ofNullable(props.getProperty(key));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOrDefault(String key, String defaultValue) {
        return get(key).orElse(defaultValue);
    }

    public Map<String, String> findAll() {
        Map<String, String> all = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(path)) {
            props.load(reader);
            for (String key : props.stringPropertyNames()) {
                all.put(key, props.getProperty(key));
            }
            return all;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean put(String key, String value) {

        try (Writer writer = Files.newBufferedWriter(path)) {
            props.setProperty(key, value);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean remove(String key) {
        try (Writer writer = Files.newBufferedWriter(path)) {
            props.remove(key);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}