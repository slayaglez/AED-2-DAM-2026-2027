package aula.tareas.service;

import aula.tareas.api.TareaRequest;
import aula.tareas.mapper.TareaMapperServicio;
import aula.tareas.modelo.Tarea;
import aula.tareas.repository.ITareaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractTareaService implements ITareaService {

    private final ITareaRepository repository;

    public AbstractTareaService(ITareaRepository repository) {
        this.repository = repository;
    }

    public List<Tarea> listar() {
        return repository.findAll();
    }


    public Optional<Tarea> buscar(long id) {
        if(id <= 0) return Optional.empty();
        return repository.findById(id);
    }


    public Tarea crear(TareaRequest request) {
        return repository.create(TareaMapperServicio.toDomain(request));
    }


    public Optional<Tarea> actualizar(long id, TareaRequest request) {
        if (id <= 0) return Optional.empty();
        return repository.update(TareaMapperServicio.toDomain(request));
    }


    public boolean eliminar(long id) {
        if (id <= 0) return false;
        return repository.delete(id);
    }
}
