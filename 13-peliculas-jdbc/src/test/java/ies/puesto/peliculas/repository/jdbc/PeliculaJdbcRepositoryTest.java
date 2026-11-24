package ies.puesto.peliculas.repository.jdbc;

import ies.puesto.peliculas.model.Pelicula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// aqui, como uso la bbdd real, creo una peli nueva al inicio de cada test
// y lo elimino al final de cada test como haces en algunos examenes
class PeliculaJdbcRepositoryTest {

    final String CODIGO_PRUEBA = "PEL999";

    PeliculaJdbcRepository repositorio;

    @BeforeEach
    void preparar() {
        repositorio = new PeliculaJdbcRepository(new PeliculaRowMapper(), new PeliculaEntityMapper());
        repositorio.eliminar(CODIGO_PRUEBA);
    }

    @AfterEach
    void limpiar() {
        repositorio.eliminar(CODIGO_PRUEBA);
    }

    @Test
    void insertarYBuscarPorCodigo() {
        Pelicula pelicula = new Pelicula(CODIGO_PRUEBA, "Pelicula de prueba", 2020, 100);

        assertTrue(repositorio.insertarSiAusente(pelicula));

        Optional<Pelicula> encontrada = repositorio.buscarPorCodigo(CODIGO_PRUEBA);
        assertTrue(encontrada.isPresent());
        assertEquals("Pelicula de prueba", encontrada.get().getNombre());
        assertEquals(2020, encontrada.get().getAnioLanzamiento());
        assertEquals(100, encontrada.get().getMinutosProyeccion());
    }

    @Test
    void buscarTodasIncluyeLaPeliculaInsertada() {
        int antes = repositorio.buscarTodas().size();
        repositorio.insertarSiAusente(new Pelicula(CODIGO_PRUEBA, "Pelicula de prueba", 2020, 100));

        assertEquals(antes + 1, repositorio.buscarTodas().size());
    }

    @Test
    void buscarCodigoQueNoExiste() {
        assertTrue(repositorio.buscarPorCodigo(CODIGO_PRUEBA).isEmpty());
    }

    @Test
    void actualizarPeliculaExistente() {
        repositorio.insertarSiAusente(new Pelicula(CODIGO_PRUEBA, "Titulo viejo", 2020, 100));

        boolean actualizada = repositorio.actualizarSiExiste(
                new Pelicula(CODIGO_PRUEBA, "Titulo nuevo", 2021, 110));

        assertTrue(actualizada);
        assertEquals("Titulo nuevo", repositorio.buscarPorCodigo(CODIGO_PRUEBA).get().getNombre());
    }

    @Test
    void actualizarPeliculaQueNoExiste() {
        assertFalse(repositorio.actualizarSiExiste(new Pelicula(CODIGO_PRUEBA, "Nada", 2020, 100)));
    }

    @Test
    void eliminarPeliculaExistente() {
        repositorio.insertarSiAusente(new Pelicula(CODIGO_PRUEBA, "Pelicula de prueba", 2020, 100));

        assertTrue(repositorio.eliminar(CODIGO_PRUEBA));
        assertTrue(repositorio.buscarPorCodigo(CODIGO_PRUEBA).isEmpty());
    }

    @Test
    void eliminarPeliculaQueNoExiste() {
        assertFalse(repositorio.eliminar(CODIGO_PRUEBA));
    }
}
