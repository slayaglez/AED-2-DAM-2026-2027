package aula.tareas.repository;

import aula.tareas.api.TareaRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository("tareaRepository")
public class TareaRepository implements ITareaRepository {
    private final AtomicLong secuencia = new AtomicLong();
    private final ConcurrentSkipListMap<Long, TareaRequest> tareas = new ConcurrentSkipListMap<>();

    public List<TareaRequest> findAll() {
        return List.copyOf(tareas.values());
    }

    public TareaRequest findById(long id) {
        return tareas.get(id);
    }

    public TareaRequest create(String titulo) {
        long id = secuencia.incrementAndGet();
        var tarea = new TareaRequest(id, titulo.strip());
        tareas.put(id, tarea);
        return tarea;
    }

    public TareaRequest update(TareaRequest tareaRequest){
        TareaRequest oldTareaRequest = findById(tareaRequest.getId());
        if(oldTareaRequest == null) return null;
        return null;
    }

    public boolean delete(long id) {
        TareaRequest deleted = tareas.remove(id);
        return deleted != null;
    }

}
