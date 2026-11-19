package aula.tareas.repository;

import aula.tareas.api.TareaRequest;
import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Repository("tareaRepositoryCsv")
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
    public TareaRequest findById(long id) {
        return null;
    }

    @Override
    public List<TareaRequest> findAll() {
        if(Files.notExists(path)) return List.of();
        return null;
    }

    @Override
    public TareaRequest create(String titulo) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public TareaRequest update(TareaRequest tareaRequest) {
        return null;
    }
}
