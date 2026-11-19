package aula.tareas.repository;

import aula.tareas.api.TareaRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Repository("tareaRepositoryXml")
public class TareaRepositoryXml implements ITareaRepository {

    private final Path path = Path.of("data", "tareas.xml");
    private final XmlMapper mapper = new XmlMapper();   // ← única diferencia real con el JSON
    private final List<TareaRequest> tareaRequests;
    private final AtomicLong secuencia;

    public TareaRepositoryXml() {
        this.tareaRequests = readAll();
        long max = tareaRequests.stream().mapToLong(TareaRequest::getId).max().orElse(0);
        this.secuencia = new AtomicLong(max);
    }

    @Override
    public TareaRequest findById(long id) {
        return null;
    }

    @Override
    public List<TareaRequest> findAll() {
        return List.copyOf(this.tareaRequests);
    }

    @Override
    public TareaRequest create(String titulo) {
        TareaRequest tareaRequest = new TareaRequest(secuencia.incrementAndGet(), titulo);
        this.tareaRequests.add(tareaRequest);
        saveAll();
        return tareaRequest;
    }

    @Override
    public boolean delete(long id) {
        boolean removed = this.tareaRequests.removeIf(t -> t.getId() == id);
        if (removed) {
            saveAll();
        }
        return removed;
    }

    @Override
    public TareaRequest update(TareaRequest tareaRequest) {
        return null;
    }

    private void saveAll() {
        try {
            Files.createDirectories(path.getParent());
            mapper.writer().withRootName("tareas")
                    .withDefaultPrettyPrinter()
                    .writeValue(path.toFile(), tareaRequests);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TareaRequest> readAll() {
        try {
            if (Files.notExists(path) || Files.size(path) == 0) {
                return new ArrayList<>();
            }
            return new ArrayList<>(mapper.readValue(path.toFile(), new TypeReference<List<TareaRequest>>() {}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
