package aula.tareas.repository;

import aula.tareas.modelo.Tarea;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

abstract class AbstractTareaFichero implements ITareaRepository {

    protected final Path path;

    public AbstractTareaFichero(Path path) {
        this.path = path;
    }

    public synchronized List<Tarea> findAll() {
        return readAll();
    }

    public synchronized Optional<Tarea> findById(long id) {
        return findAll().stream().filter(t -> t.identificador() == id).findFirst();
    }

    public synchronized Tarea create(Tarea tarea) {
        List<Tarea> tareas = readAll();
        tareas.add(tarea);
        writeAll(tareas);
        return tarea;
    }

    public synchronized Optional<Tarea> update(Tarea tarea) {
        List<Tarea> tareas = readAll();
        if (!tareas.removeIf(t -> t.identificador().equals(tarea.identificador()))) return Optional.empty();
        tareas.add(tarea);
        writeAll(tareas);
        return Optional.of(tarea);
    }

    public synchronized boolean delete(long id) {
        List<Tarea> tareas = readAll();
        if (!tareas.removeIf(t -> t.identificador() == id)) return false;
        writeAll(tareas);
        return true;
    }

    abstract List<Tarea> readAll();
    abstract void writeAll(List<Tarea> tareas);
}
