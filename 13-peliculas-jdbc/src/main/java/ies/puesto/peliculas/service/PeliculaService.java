package ies.puesto.peliculas.service;

import ies.puesto.peliculas.model.Pelicula;
import ies.puesto.peliculas.repository.PeliculaRepository;
import ies.puesto.peliculas.service.mapper.PeliculaDominioMapper;
import ies.puesto.peliculas.service.modelo.DatosCreacionPelicula;
import ies.puesto.peliculas.service.modelo.DatosEdicionPelicula;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository repository;
    private final PeliculaDominioMapper dominioMapper;

    public PeliculaService(PeliculaRepository repository, PeliculaDominioMapper dominioMapper) {
        this.repository = repository;
        this.dominioMapper = dominioMapper;
    }

    public List<Pelicula> listar() {
        return repository.buscarTodas();
    }

    public Optional<Pelicula> obtener(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    public Optional<Pelicula> crear(DatosCreacionPelicula datos) {
        Pelicula pelicula = dominioMapper.aPelicula(datos);
        if (repository.insertarSiAusente(pelicula)) {
            return Optional.of(pelicula);
        }
        return Optional.empty();
    }

    public Optional<Pelicula> actualizar(String codigo, DatosEdicionPelicula datos) {
        Pelicula pelicula = dominioMapper.aPelicula(codigo, datos);
        if (repository.actualizarSiExiste(pelicula)) {
            return Optional.of(pelicula);
        }
        return Optional.empty();
    }

    public boolean eliminar(String codigo) {
        return repository.eliminar(codigo);
    }
}
