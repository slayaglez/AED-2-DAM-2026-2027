package es.codelearnacademy.filelab.repository;

import java.util.List;
import java.util.Optional;

/** Contrato CRUD generico. Ninguna operacion publica propaga IOException. */
public interface IRepository<T, ID> {

    /**
     * Devuelve toda la lista de T
     * @return Lista de T
     */
    List<T> findAll();

    /**
     * Devuelve un objeto por su ID
     * @param id identificador del objeto
     * @return Objeto T
     */
    Optional<T> findById(ID id);

    /**
     * Crea un objeto T
     * @param entity objeto T
     * @return boolean
     */
    boolean create(T entity);

    /**
     * Actualiza un objeto T
     * @param entity objeto T
     * @return boolean
     */
    boolean update(T entity);

    /**
     * Elimina un objeto T
     * @param id identificador del objeto T
     * @return boolean
     */
    boolean delete(ID id);
}
