package es.ejemplo.taller.dao;
import es.ejemplo.taller.infra.ConexionBD;
import es.ejemplo.taller.modelo.Reparacion;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
public class ReparacionDao extends AbstractDao<Reparacion, Integer> {
    private final String url;
    public ReparacionDao(String url) { this.url = url; }

    public List<Reparacion> mapper(ResultSet rs) {
        List<Reparacion> lista = new ArrayList<>();
        try {
            while(rs.next()){
                lista.add( new Reparacion(rs.getInt(1),
                                          rs.getString(2),
                                          rs.getInt(3),
                                          rs.getString(4),
                                          rs.getString(5),
                                          rs.getBigDecimal(6),
                                          rs.getString(7))
                );
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reparacion> listar() {
        String query = "SELECT id_reparacion, matricula, id_mecanico, descripcion, fecha, horas_trabajo, estado FROM reparaciones";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Reparacion> buscarPorId(Integer id) {
        String query = "SELECT id_reparacion, matricula, id_mecanico, descripcion, fecha, horas_trabajo, estado FROM reparaciones WHERE id_reparacion = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            return mapper(find(ps)).stream().findFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int insertar(Reparacion dato) {
        if(dato == null) return 0;

        String query = "INSERT INTO reparaciones (matricula, id_mecanico, descripcion, fecha, horas_trabajo, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dato.getMatricula());
            ps.setInt(2, dato.getIdMecanico());
            ps.setString(3, dato.getDescripcion());
            ps.setString(4, dato.getFecha());
            ps.setBigDecimal(5, dato.getHorasTrabajo());
            ps.setString(6, dato.getEstado());

            return insert(ps);

        } catch (SQLException e) {
            return 0;
        }
    }
    public int actualizar(Reparacion dato) {
        String query = "UPDATE reparaciones SET matricula = ?, id_mecanico = ?, descripcion = ?, fecha = ?, horas_trabajo = ?, estado = ? WHERE id_reparacion = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, dato.getMatricula());
            ps.setInt(2, dato.getIdMecanico());
            ps.setString(3, dato.getDescripcion());
            ps.setString(4, dato.getFecha());
            ps.setBigDecimal(5, dato.getHorasTrabajo());
            ps.setString(6, dato.getEstado());
            ps.setInt(7, dato.getIdReparacion());

            return create(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int eliminar(Integer id) {
        String query = "DELETE FROM reparaciones WHERE id_reparacion = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);

            return create(ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Reparacion> buscarPorVehiculo(String valor) {
        String query = "SELECT id_reparacion, matricula, id_mecanico, descripcion, fecha, horas_trabajo, estado FROM reparaciones WHERE matricula = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, valor);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Reparacion> buscarPorMecanico(Integer valor) {
        String query = "SELECT id_reparacion, matricula, id_mecanico, descripcion, fecha, horas_trabajo, estado FROM reparaciones WHERE id_mecanico = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, valor);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Reparacion> listarPendientes() {
        String query = "SELECT id_reparacion, matricula, id_mecanico, descripcion, fecha, horas_trabajo, estado FROM reparaciones WHERE estado = 'PENDIENTE'";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
