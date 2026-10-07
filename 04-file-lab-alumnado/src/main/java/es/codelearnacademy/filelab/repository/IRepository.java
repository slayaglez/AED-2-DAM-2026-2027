package es.codelearnacademy.filelab.repository;

import java.util.List;
import java.util.Optional;

/**
 * Contrato CRUD comun para cualquier repositorio.
 *
 * @param <T>  tipo de la entidad almacenada
 * @param <ID> tipo del identificador de la entidad
 */
public interface IRepository<T, ID> {

    /**
     * Obtiene todas las entidades almacenadas.
     *
     * @return lista con todas las entidades, o una lista vacia si no hay ninguna
     */
    List<T> findAll();

    /**
     * Busca una entidad por su identificador.
     *
     * @param id identificador de la entidad buscada
     * @return la entidad encontrada, o Optional.empty() si no existe
     */
    Optional<T> findById(ID id);

    /**
     * Crea una nueva entidad.
     *
     * @param entity entidad que se quiere crear
     * @return Boolean
     */
    boolean create(T entity);

    /**
     * Sustituye una entidad existente por la recibida, localizandola por su identificador.
     *
     * @param entity entidad con los nuevos datos
     * @return Boolean
     */
    boolean update(T entity);

    /**
     * Elimina la entidad cuyo identificador coincida con el recibido.
     *
     * @param id identificador de la entidad que se quiere eliminar
     * @return Boolean
     */
    boolean delete(ID id);
}
