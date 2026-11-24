package ies.puesto.peliculas.repository;

import ies.puesto.peliculas.model.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaRepository {

    /**
     * Obtiene todas las peliculas guardadas.
     *
     * @return lista con todas las peliculas, vacia si no hay ninguna
     */
    List<Pelicula> buscarTodas();

    /**
     * Busca una pelicula por su codigo.
     *
     * @param identificador codigo de la pelicula
     * @return la pelicula si existe, o un Optional vacio si no existe
     */
    Optional<Pelicula> buscarPorCodigo(String identificador);

    /**
     * Guarda una pelicula nueva.
     *
     * @param pelicula pelicula a guardar
     * @return true si se ha guardado, false si no
     */
    boolean insertarSiAusente(Pelicula pelicula);

    /**
     * Actualiza los datos de una pelicula si ya existe.
     *
     * @param pelicula pelicula con los datos nuevos
     * @return true si se ha actualizado, false si no existe
     */
    boolean actualizarSiExiste(Pelicula pelicula);

    /**
     * Elimina una pelicula por su codigo.
     *
     * @param identificador codigo de la pelicula
     * @return true si se ha eliminado, false si no existe
     */
    boolean eliminar(String identificador);
}
