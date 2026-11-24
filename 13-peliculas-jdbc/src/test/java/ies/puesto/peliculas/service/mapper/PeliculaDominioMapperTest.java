package ies.puesto.peliculas.service.mapper;

import ies.puesto.peliculas.model.Pelicula;
import ies.puesto.peliculas.service.modelo.DatosCreacionPelicula;
import ies.puesto.peliculas.service.modelo.DatosEdicionPelicula;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaDominioMapperTest {

    PeliculaDominioMapper mapper = new PeliculaDominioMapper();

    @Test
    void datosCreacionAPelicula() {
        DatosCreacionPelicula datos = new DatosCreacionPelicula("PEL777", "Totoro", 1988, 86);

        Pelicula pelicula = mapper.aPelicula(datos);

        assertEquals("PEL777", pelicula.getIdentificador());
        assertEquals("Totoro", pelicula.getNombre());
        assertEquals(1988, pelicula.getAnioLanzamiento());
        assertEquals(86, pelicula.getMinutosProyeccion());
    }

    @Test
    void datosEdicionAPeliculaUsaElCodigoRecibido() {
        DatosEdicionPelicula datos = new DatosEdicionPelicula("Totoro", 1988, 86);

        Pelicula pelicula = mapper.aPelicula("PEL777", datos);

        assertEquals("PEL777", pelicula.getIdentificador());
        assertEquals("Totoro", pelicula.getNombre());
        assertEquals(1988, pelicula.getAnioLanzamiento());
        assertEquals(86, pelicula.getMinutosProyeccion());
    }
}
