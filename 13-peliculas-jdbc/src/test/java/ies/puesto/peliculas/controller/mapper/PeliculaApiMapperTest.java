package ies.puesto.peliculas.controller.mapper;

import ies.puesto.peliculas.controller.api.PeliculaEdicionRequest;
import ies.puesto.peliculas.controller.api.PeliculaRequest;
import ies.puesto.peliculas.controller.api.PeliculaResponse;
import ies.puesto.peliculas.model.Pelicula;
import ies.puesto.peliculas.service.modelo.DatosCreacionPelicula;
import ies.puesto.peliculas.service.modelo.DatosEdicionPelicula;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaApiMapperTest {

    PeliculaApiMapper mapper = new PeliculaApiMapper();

    @Test
    void requestADatosCreacion() {
        PeliculaRequest peticion = new PeliculaRequest("PEL777", "Totoro", 1988, 86);

        DatosCreacionPelicula datos = mapper.aDatosCreacion(peticion);

        assertEquals("PEL777", datos.referenciaSolicitada());
        assertEquals("Totoro", datos.nombreSolicitado());
        assertEquals(1988, datos.anioSolicitado());
        assertEquals(86, datos.minutosSolicitados());
    }

    @Test
    void edicionRequestADatosEdicion() {
        PeliculaEdicionRequest peticion = new PeliculaEdicionRequest("Totoro", 1988, 86);

        DatosEdicionPelicula datos = mapper.aDatosEdicion(peticion);

        assertEquals("Totoro", datos.nombreSolicitado());
        assertEquals(1988, datos.anioSolicitado());
        assertEquals(86, datos.minutosSolicitados());
    }

    @Test
    void peliculaARespuesta() {
        Pelicula pelicula = new Pelicula("PEL777", "Totoro", 1988, 86);

        PeliculaResponse respuesta = mapper.aRespuesta(pelicula);

        assertEquals("PEL777", respuesta.codigo());
        assertEquals("Totoro", respuesta.titulo());
        assertEquals(1988, respuesta.anioEstreno());
        assertEquals(86, respuesta.duracionMinutos());
    }

    @Test
    void listaDePeliculasAListaDeRespuestas() {
        List<Pelicula> peliculas = List.of(
                new Pelicula("PEL777", "Totoro", 1988, 86),
                new Pelicula("PEL778", "Chihiro", 2001, 125));

        List<PeliculaResponse> respuestas = mapper.aRespuestas(peliculas);

        assertEquals(2, respuestas.size());
        assertEquals("PEL778", respuestas.get(1).codigo());
    }
}
