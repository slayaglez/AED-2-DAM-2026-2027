package aula.tareas.repository;

import aula.tareas.api.TareaRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository("tareaRepositoryJson")
public class TareaRepositoryJson implements ITareaRepository{

    private final AtomicLong secuencia = new AtomicLong();
    private final Path path;
    private final ObjectMapper mapper = new ObjectMapper();
    private List<TareaRequest> tareaRequests;

    public TareaRepositoryJson() {
        this.path = Path.of("data", "tareas.json");
        this.tareaRequests = readAll();
        if(Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public TareaRequest findById(long id) {
        return null;
    }

    @Override
    public List<TareaRequest> findAll() {
        return List.copyOf(tareaRequests);
    }

    @Override
    public TareaRequest create(String titulo) {
        long id = secuencia.incrementAndGet();
        TareaRequest tareaRequest = new TareaRequest(id, titulo);
        tareaRequests.add(tareaRequest);
        saveAll(tareaRequests);
        return tareaRequest;
    }

    @Override
    public boolean delete(long id) {
        boolean remove = tareaRequests.remove(findById(id));
        saveAll(tareaRequests);
        return remove;
    }

    @Override
    public TareaRequest update(TareaRequest tareaRequest) {
        return null;
    }

    private void saveAll(List<TareaRequest> items) {
        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), items);
        } catch (Exception e){
            //futuro logger
            throw new RuntimeException(e);
        }

    }

    public List<TareaRequest> readAll() {
        try {
            return new ArrayList<>(mapper.readValue(
                    path.toFile(), new TypeReference<List<TareaRequest>>() {}));
        } catch (IOException e) {
            return List.of();
        }
    }

}
