package aula.tareas.repository;

import aula.tareas.modelo.Tarea;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("tareaRepositoryCsv")
public class TareaRepositoryCsv extends AbstractTareaFichero {
    private final Path path;

    public TareaRepositoryCsv() {
        this(Path.of("data", "tarea.csv"));
    }

    public TareaRepositoryCsv(Path path) {
        this.path = path;
    }

    private static final CSVFormat ENTRADA = CSVFormat.DEFAULT.builder()
            .setHeader().setSkipHeaderRecord(true).get();
    private static final CSVFormat SALIDA = CSVFormat.DEFAULT.builder()
            .setHeader("id", "titulo").get();


    @Override
    List<Tarea> readAll() {
        if (Files.notExists(path)) {
            return new ArrayList<>();
        }
        List<Tarea> result = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
             CSVParser parser = ENTRADA.parse(reader)) {
            for (CSVRecord row : parser) {
                result.add(new Tarea(
                        Long.parseLong(row.get("id")),
                        row.get("titulo")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    void writeAll(List<Tarea> tareas) {
        Path parent = path.getParent();
        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(writer, SALIDA)) {
                for (Tarea t : tareas) {
                    printer.printRecord(t.identificador(), t.nombre());
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
