package com.ejemplo.catalogo.repository.file.csv;

import com.ejemplo.catalogo.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IRepository {

    /**
     * Busca todos los productos
     * @return Lista de productos
     */
    List<Producto> findAll();

    /**
     * Funcion que obtiene un producto por su id
     * @param id id
     * @return Optional producto
     */
    Optional<Producto> findById(long id);

    /**
     * Crea un producto
     * @param producto producto
     */
    void create(Producto producto);

    /**
     * Update
     * @param producto
     * @return boolean
     */
    boolean update(Producto producto);

    /**
     * Detele
     * @param id
     * @return boolean
     */
    boolean delete(long id);
}
