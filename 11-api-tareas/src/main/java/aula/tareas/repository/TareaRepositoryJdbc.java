package aula.tareas.repository;

import aula.tareas.api.TareaRequest;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("tareaRepositoryJdbc")
public class TareaRepositoryJdbc implements ITareaRepository {

    private final String jdbcUrl = "jdbc:sqlite:data/tareas.sqlite3";

    public TareaRepositoryJdbc() {
        validarUrl(null);
    }

    public TareaRepositoryJdbc(String jdbcUrl) {
        validarUrl(jdbcUrl);
    }

    public void validarUrl(String jdbcUrl){
        String url = this.jdbcUrl;
        if (jdbcUrl != null) {
            url = jdbcUrl;
        }
        url = url.replace("jdbc:sqlite:", "");
        if (!Files.exists(Path.of(url))) {
            try {
                Files.createFile(Path.of(url));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public TareaRequest findById(long id) {
        String query = "SELECT * FROM tareas_jdbc WHERE id = ?";

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                 return new TareaRequest(
                        rs.getLong("id"),
                        rs.getString("titulo"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<TareaRequest> findAll() {
        String query = "SELECT * FROM tareas_jdbc ORDER BY id";
        List<TareaRequest> tareas = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tareas.add(new TareaRequest(
                        rs.getLong("id"),
                        rs.getString("titulo")));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return tareas;
    }

    @Override
    public TareaRequest create(String titulo) {
        String query = "INSERT INTO tareas_jdbc (titulo) VALUES (?)";

        try (Connection c = DriverManager.getConnection(jdbcUrl);
             PreparedStatement ps = c.prepareStatement(query);
             ) {

            ps.setString(1, titulo);
            ps.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public TareaRequest update(TareaRequest tareaRequest) {
        return null;
    }
}
