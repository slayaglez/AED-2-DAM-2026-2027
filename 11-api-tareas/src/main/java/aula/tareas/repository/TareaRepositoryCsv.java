package aula.tareas.repository;

import aula.tareas.modelo.Tarea;
import org.apache.commons.csv.CSVFormat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TareaRepositoryCsv implements ITareaRepository {

    private Path path;
    private final CSVFormat inputFormat = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build();
    private final CSVFormat outputFormat = CSVFormat.DEFAULT.builder()
            .setHeader("id", "titulo")
            .build();

    public TareaRepositoryCsv() {
        path = Path.of("data", "tareas.csv");
    }

    @Override
    public Tarea findById(long id) {
        return null;
    }

    @Override
    public List<Tarea> findAll() {
        if(Files.notExists(path)) return List.of();
        return null;
    }

    @Override
    public Tarea create(String titulo) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Tarea update(Tarea tarea) {
        return null;
    }
}
