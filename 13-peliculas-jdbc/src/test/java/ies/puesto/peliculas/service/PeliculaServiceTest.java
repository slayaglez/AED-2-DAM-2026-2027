package ies.puesto.peliculas.service;

import ies.puesto.peliculas.model.Pelicula;
import ies.puesto.peliculas.service.mapper.PeliculaDominioMapper;
import ies.puesto.peliculas.service.modelo.DatosCreacionPelicula;
import ies.puesto.peliculas.service.modelo.DatosEdicionPelicula;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaServiceTest {

    RepositorioFalso repositorio;
    PeliculaService servicio;

    @BeforeEach
    void preparar() {
        repositorio = new RepositorioFalso();
        servicio = new PeliculaService(repositorio, new PeliculaDominioMapper());
    }

    @Test
    void listarSinPeliculasDevuelveListaVacia() {
        assertTrue(servicio.listar().isEmpty());
    }

    @Test
    void crearPeliculaNueva() {
        DatosCreacionPelicula datos = new DatosCreacionPelicula("PEL001", "Totoro", 1988, 86);

        Optional<Pelicula> resultado = servicio.crear(datos);

        assertTrue(resultado.isPresent());
        assertEquals("Totoro", resultado.get().getNombre());
        assertEquals(1, servicio.listar().size());
    }

    @Test
    void crearPeliculaRepetidaDevuelveVacio() {
        DatosCreacionPelicula datos = new DatosCreacionPelicula("PEL001", "Totoro", 1988, 86);
        servicio.crear(datos);

        Optional<Pelicula> resultado = servicio.crear(datos);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerPeliculaExistente() {
        servicio.crear(new DatosCreacionPelicula("PEL001", "Totoro", 1988, 86));

        assertTrue(servicio.obtener("PEL001").isPresent());
    }

    @Test
    void obtenerPeliculaQueNoExiste() {
        assertTrue(servicio.obtener("PEL999").isEmpty());
    }

    @Test
    void actualizarPeliculaExistente() {
        servicio.crear(new DatosCreacionPelicula("PEL001", "Totoro", 1988, 86));

        Optional<Pelicula> resultado = servicio.actualizar("PEL001",
                new DatosEdicionPelicula("Mi vecino Totoro", 1988, 90));

        assertTrue(resultado.isPresent());
        assertEquals("Mi vecino Totoro", servicio.obtener("PEL001").get().getNombre());
        assertEquals(90, servicio.obtener("PEL001").get().getMinutosProyeccion());
    }

    @Test
    void actualizarPeliculaQueNoExiste() {
        Optional<Pelicula> resultado = servicio.actualizar("PEL999",
                new DatosEdicionPelicula("Nada", 2000, 100));

        assertTrue(resultado.isEmpty());
    }

    @Test
    void eliminarPeliculaExistente() {
        servicio.crear(new DatosCreacionPelicula("PEL001", "Totoro", 1988, 86));

        assertTrue(servicio.eliminar("PEL001"));
        assertTrue(servicio.listar().isEmpty());
    }

    @Test
    void eliminarPeliculaQueNoExiste() {
        assertFalse(servicio.eliminar("PEL999"));
    }
}
