package ies.puesto.peliculas.repository.jdbc;

import ies.puesto.peliculas.model.Pelicula;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaEntityMapperTest {

    PeliculaEntityMapper mapper = new PeliculaEntityMapper();

    @Test
    void peliculaAEntity() {
        Pelicula pelicula = new Pelicula("PEL777", "Totoro", 1988, 86);

        PeliculaEntity fila = mapper.aEntity(pelicula);

        assertEquals("PEL777", fila.getCodigoFila());
        assertEquals("Totoro", fila.getTituloFila());
        assertEquals(1988, fila.getAnioFila());
        assertEquals(86, fila.getMinutosFila());
    }

    @Test
    void entityAPelicula() {
        PeliculaEntity fila = new PeliculaEntity("PEL777", "Totoro", 1988, 86);

        Pelicula pelicula = mapper.aPelicula(fila);

        assertEquals("PEL777", pelicula.getIdentificador());
        assertEquals("Totoro", pelicula.getNombre());
        assertEquals(1988, pelicula.getAnioLanzamiento());
        assertEquals(86, pelicula.getMinutosProyeccion());
    }
}
