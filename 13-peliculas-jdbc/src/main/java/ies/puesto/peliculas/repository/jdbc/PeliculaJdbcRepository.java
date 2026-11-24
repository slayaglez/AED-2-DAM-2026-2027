package ies.puesto.peliculas.repository.jdbc;

import ies.puesto.peliculas.model.Pelicula;
import ies.puesto.peliculas.repository.PeliculaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PeliculaJdbcRepository implements PeliculaRepository {

    private final String jdbcUrl = "jdbc:sqlite:data/peliculas.sqlite3";
    private final PeliculaRowMapper rowMapper;
    private final PeliculaEntityMapper entityMapper;

    PeliculaJdbcRepository(PeliculaRowMapper rowMapper, PeliculaEntityMapper entityMapper) {
        this.rowMapper = rowMapper;
        this.entityMapper = entityMapper;
        validarUrl(null);
    }

    public void validarUrl(String jdbcUrl) {
        String url = this.jdbcUrl;
        if (jdbcUrl != null) {
            url = jdbcUrl;
        }
        url = url.replace("jdbc:sqlite:", "");
        if (!Files.exists(Path.of(url))) {
            throw new IllegalStateException("No existe la base de datos " + url
                    + ". Arranca la aplicacion desde la carpeta del pom.xml");
        }
    }

    @Override
    public List<Pelicula> buscarTodas() {
        String query = "SELECT pelicula_codigo, titulo_texto, estreno_anio, duracion_minutos "
                + "FROM peliculas ORDER BY pelicula_codigo";
        List<Pelicula> peliculas = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            int fila = 0;
            while (rs.next()) {
                peliculas.add(entityMapper.aPelicula(rowMapper.mapRow(rs, fila++)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peliculas;
    }

    @Override
    public Optional<Pelicula> buscarPorCodigo(String identificador) {
        String query = "SELECT pelicula_codigo, titulo_texto, estreno_anio, duracion_minutos "
                + "FROM peliculas WHERE pelicula_codigo = ?";

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, identificador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(entityMapper.aPelicula(rowMapper.mapRow(rs, 0)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean insertarSiAusente(Pelicula pelicula) {
        String query = "INSERT INTO peliculas (pelicula_codigo, titulo_texto, estreno_anio, duracion_minutos) "
                + "VALUES (?, ?, ?, ?) ON CONFLICT(pelicula_codigo) DO NOTHING";
        PeliculaEntity fila = entityMapper.aEntity(pelicula);

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, fila.getCodigoFila());
            ps.setString(2, fila.getTituloFila());
            ps.setInt(3, fila.getAnioFila());
            ps.setInt(4, fila.getMinutosFila());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean actualizarSiExiste(Pelicula pelicula) {
        String query = "UPDATE peliculas SET titulo_texto = ?, estreno_anio = ?, duracion_minutos = ? "
                + "WHERE pelicula_codigo = ?";
        PeliculaEntity fila = entityMapper.aEntity(pelicula);

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, fila.getTituloFila());
            ps.setInt(2, fila.getAnioFila());
            ps.setInt(3, fila.getMinutosFila());
            ps.setString(4, fila.getCodigoFila());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminar(String identificador) {
        String query = "DELETE FROM peliculas WHERE pelicula_codigo = ?";

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, identificador);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
