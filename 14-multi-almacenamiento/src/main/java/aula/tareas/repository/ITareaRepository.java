package aula.tareas.repository;

import aula.tareas.modelo.Tarea;
import java.util.List;
import java.util.Optional;

public interface ITareaRepository {

    /**
     * Busca todas las tareas
     * @return Lista de tareas
     */
    List<Tarea> findAll();

    /**
     * Busca una tarea por su id
     * @param id identificador
     * @return Tarea o null si no existe
     */
    Optional<Tarea> findById(long id);

    /**
     * Crea una tarea
     * @param tarea la tarea a crear
     * @return la tarea con su ID
     */
    Tarea create(Tarea tarea);

    /**
     * Actualiza una tarea
     * @param tarea tarea nueva
     * @return Tarea o null si no se pudo
     */
    Optional<Tarea> update(Tarea tarea);

    /**
     * Elimina una tarea por su id
     * @param id identificador
     * @return true / false
     */
    boolean delete(long id);
}
