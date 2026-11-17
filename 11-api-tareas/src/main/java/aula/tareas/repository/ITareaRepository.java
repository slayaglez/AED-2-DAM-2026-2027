package aula.tareas.repository;

import aula.tareas.modelo.Tarea;

import java.util.List;

public interface ITareaRepository {
    public Tarea findById(long id);
    public List<Tarea> findAll();
    public Tarea create(String titulo);
    public boolean delete(long id);
    public Tarea update(Tarea tarea);
}
