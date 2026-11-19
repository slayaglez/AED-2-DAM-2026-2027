package aula.tareas.repository;

import aula.tareas.api.TareaRequest;

import java.util.List;

public interface ITareaRepository {
    public TareaRequest findById(long id);
    public List<TareaRequest> findAll();
    public TareaRequest create(String titulo);
    public boolean delete(long id);
    public TareaRequest update(TareaRequest tareaRequest);
}
