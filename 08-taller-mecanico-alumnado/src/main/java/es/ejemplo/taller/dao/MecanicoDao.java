package es.ejemplo.taller.dao;
import es.ejemplo.taller.infra.ConexionBD;
import es.ejemplo.taller.modelo.Cliente;
import es.ejemplo.taller.modelo.Mecanico;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
public class MecanicoDao extends AbstractDao<Mecanico, Integer> {
    private final String url;
    public MecanicoDao(String url) { this.url = url; }

    public List<Mecanico> mapper(ResultSet rs) {
        List<Mecanico> lista = new ArrayList<>();
        try {
            while(rs.next()){
                lista.add( new Mecanico(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBigDecimal(4))
                );
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Mecanico> listar() {
        String query = "SELECT id_mecanico, nombre, especialidad, tarifa_hora FROM mecanicos";
        ResultSet rs = null;
        try {
            rs = findAll(query, url);
            return mapper(rs);
        } finally {
            closeConnection(rs);
        }
    }

    public Optional<Mecanico> buscarPorId(Integer id) {
        String query = "SELECT id_mecanico, nombre, especialidad, tarifa_hora FROM mecanicos WHERE id_mecanico = ?";
        return mapper(findById(query, id, url)).stream().findFirst();
    }

    public int insertar(Mecanico dato) {
        if (dato == null || dato.getEspecialidad() == null || dato.getEspecialidad().isBlank()) return 0;
        String query = "INSERT INTO mecanicos (nombre, especialidad, tarifa_hora) VALUES (?, ?, ?)";
        return create(query, dato, url);
    }

    public int actualizar(Mecanico dato) {
        String query = "UPDATE mecanicos SET nombre = ?, especialidad = ?, tarifa_hora = ? WHERE id_mecanico = ?";
        return update(query, dato, url);
    }

    public int eliminar(Integer id) {
        if (id == null) return 0;
        String query = "DELETE FROM mecanicos WHERE id_mecanico = ?";
        try (Connection c = ConexionBD.abrir(url); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<Mecanico> buscarPorEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) return List.of();
        String query = "SELECT id_mecanico, nombre, especialidad, tarifa_hora FROM mecanicos WHERE especialidad = ?";
        try (Connection c = ConexionBD.abrir(url); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, especialidad);
            return mapper(ps.executeQuery());
        } catch (SQLException e) {
            return List.of();
        }
    }
}
