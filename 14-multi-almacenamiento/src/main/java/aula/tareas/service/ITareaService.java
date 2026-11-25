package aula.tareas.service;

import aula.tareas.api.TareaRequest;
import aula.tareas.modelo.Tarea;
import java.util.List;
import java.util.Optional;

public interface ITareaService {
    /**
     * Busca todas las tareas
     * @return Lista de tareas
     */
    List<Tarea> listar();

    /**
     * Busca una tarea por su id
     * @param id identificador
     * @return Tarea o null si no existe
     */
    Optional<Tarea> buscar(long id);

    /**
     * Crea una tarea
     * @param request la tarea a crear
     * @return la tarea con su ID
     */
    Tarea crear(TareaRequest request);

    /**
     * Actualiza una tarea
     * @param id identificador
     * @param request tarea nueva
     * @return Tarea o null si no se pudo
     */
    Optional<Tarea> actualizar(long id, TareaRequest request);

    /**
     * Elimina una tarea por su id
     * @param id identificador
     * @return true / false
     */
    boolean eliminar(long id);
}
