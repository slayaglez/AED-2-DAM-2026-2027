package ies.puesto.peliculas.service;

import ies.puesto.peliculas.model.Pelicula;
import ies.puesto.peliculas.repository.PeliculaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// un repositorio de mentira que guarda las peliculas en una lista en memoria
class RepositorioFalso implements PeliculaRepository {

    List<Pelicula> peliculas = new ArrayList<>();

    @Override
    public List<Pelicula> buscarTodas() {
        return peliculas;
    }

    @Override
    public Optional<Pelicula> buscarPorCodigo(String identificador) {
        for (Pelicula p : peliculas) {
            if (p.getIdentificador().equals(identificador)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean insertarSiAusente(Pelicula pelicula) {
        if (buscarPorCodigo(pelicula.getIdentificador()).isPresent()) {
            return false;
        }
        peliculas.add(pelicula);
        return true;
    }

    @Override
    public boolean actualizarSiExiste(Pelicula pelicula) {
        if (eliminar(pelicula.getIdentificador())) {
            peliculas.add(pelicula);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminar(String identificador) {
        return peliculas.removeIf(p -> p.getIdentificador().equals(identificador));
    }
}
