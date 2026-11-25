package aula.tareas.repository;

import aula.tareas.modelo.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("tareaRepositoryJson")
public class TareaRepositoryJson extends AbstractTareaFichero {
    private final Path path;
    private final JsonMapper mapper;

    @Autowired
    public TareaRepositoryJson(JsonMapper mapper) {
        this(Path.of("data", "tarea.json"), mapper);
    }

    public TareaRepositoryJson(Path path, JsonMapper mapper) {
        this.path = path;
        this.mapper = mapper;
    }

    @Override
    List<Tarea> readAll() {
        try {
            if (Files.notExists(path) || Files.size(path) == 0) {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(mapper.readValue(
                path.toFile(), new TypeReference<List<Tarea>>() {}));
    }

    @Override
    void writeAll(List<Tarea> tareas) {
        Path parent = path.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), tareas);
    }
}
