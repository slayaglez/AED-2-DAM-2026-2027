package aula.tareas.repository;

import aula.tareas.modelo.Tarea;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository()
public class TareaRepository implements ITareaRepository {
    private final AtomicLong secuencia = new AtomicLong();
    private final ConcurrentSkipListMap<Long, Tarea> tareas = new ConcurrentSkipListMap<>();

    public List<Tarea> findAll() {
        return List.copyOf(tareas.values());
    }

    public Tarea findById(long id) {
        return tareas.get(id);
    }

    public Tarea create(String titulo) {
        long id = secuencia.incrementAndGet();
        var tarea = new Tarea(id, titulo.strip());
        tareas.put(id, tarea);
        return tarea;
    }

    public Tarea update(Tarea tarea){
        Tarea oldTarea = findById(tarea.id());
        if(oldTarea == null) return null;
        return null;
    }

    public boolean delete(long id) {
        Tarea deleted = tareas.remove(id);
        return deleted != null;
    }

}
