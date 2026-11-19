package aula.tareas.service;

import aula.tareas.api.TareaRequest;

import java.util.List;

public interface ITareaService {

    /**
     * Lista todas las tareas
     * @return Lista de tareas
     */
    public List<TareaRequest> listar();

    /**
     * Busca tarea por id
     * @param id id
     * @return tarea
     */
    public TareaRequest buscar(long id);

    /**
     * Crea tarea
     * @param titulo titulo tarea
     * @return tarea
     */
    public TareaRequest crear(String titulo);

    /**
     * Elimina tarea
     * @param id id tarea
     * @return true / false
     */
    public boolean delete(long id);
}
