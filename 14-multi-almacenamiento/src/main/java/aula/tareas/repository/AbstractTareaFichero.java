package aula.tareas.repository;

import aula.tareas.modelo.Tarea;

import java.util.List;
import java.util.Optional;

/** CRUD compartido por los formatos de fichero, con lectura del fichero en cada operación. */
abstract class AbstractTareaFichero implements ITareaRepository {

    private final List<Tarea> tareas = readAll();

    abstract List<Tarea> readAll();
    abstract void writeAll(List<Tarea> tareas);

    public synchronized List<Tarea> findAll() {
        return readAll();
    }

    public synchronized Optional<Tarea> findById(long id) {
        return findAll().stream().filter(t -> t.identificador() == id).findFirst();
    }

    public synchronized Tarea create(Tarea tarea) {
        tareas.add(tarea);
        writeAll(tareas);
        return tarea;
    }

    public synchronized Optional<Tarea> update(Tarea tarea) {
        if(!tareas.remove(tarea)) return Optional.empty();
        tareas.add(tarea);
        writeAll(tareas);
        return Optional.ofNullable(tareas.getLast());
    }

    public synchronized boolean delete(long id) {
        return tareas.removeIf(t -> t.identificador() == id);
    }
}
