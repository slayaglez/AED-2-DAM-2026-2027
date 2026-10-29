package es.ejemplo.taller.dao;
import es.ejemplo.taller.infra.ConexionBD;
import es.ejemplo.taller.modelo.PiezaReparacion;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
public class PiezaReparacionDao extends AbstractDao<PiezaReparacion, Integer> {
    private final String url;
    public PiezaReparacionDao(String url) { this.url = url; }

    public List<PiezaReparacion> mapper(ResultSet rs) {
        List<PiezaReparacion> lista = new ArrayList<>();
        try {
            while(rs.next()){
                lista.add( new PiezaReparacion(rs.getInt(1),
                                               rs.getInt(2),
                                               rs.getString(3),
                                               rs.getInt(4),
                                               rs.getBigDecimal(5))
                );
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PiezaReparacion> listar() {
        String query = "SELECT id_pieza, id_reparacion, nombre, cantidad, precio_unitario FROM piezas_reparacion";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<PiezaReparacion> buscarPorId(Integer id) {
        String query = "SELECT id_pieza, id_reparacion, nombre, cantidad, precio_unitario FROM piezas_reparacion WHERE id_pieza = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            return mapper(find(ps)).stream().findFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int insertar(PiezaReparacion dato) {
        if(dato == null) return 0;

        String query = "INSERT INTO piezas_reparacion (id_reparacion, nombre, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dato.getIdReparacion());
            ps.setString(2, dato.getNombre());
            ps.setInt(3, dato.getCantidad());
            ps.setBigDecimal(4, dato.getPrecioUnitario());

            return insert(ps);

        } catch (SQLException e) {
            return 0;
        }
    }
    public int actualizar(PiezaReparacion dato) {
        String query = "UPDATE piezas_reparacion SET id_reparacion = ?, nombre = ?, cantidad = ?, precio_unitario = ? WHERE id_pieza = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, dato.getIdReparacion());
            ps.setString(2, dato.getNombre());
            ps.setInt(3, dato.getCantidad());
            ps.setBigDecimal(4, dato.getPrecioUnitario());
            ps.setInt(5, dato.getIdPieza());

            return create(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int eliminar(Integer id) {
        String query = "DELETE FROM piezas_reparacion WHERE id_pieza = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);

            return create(ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<PiezaReparacion> listarPorReparacion(Integer id) {
        String query = "SELECT id_pieza, id_reparacion, nombre, cantidad, precio_unitario FROM piezas_reparacion WHERE id_reparacion = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public BigDecimal calcularCostePiezas(Integer id) {
        String query = "SELECT SUM(cantidad * precio_unitario) FROM piezas_reparacion WHERE id_reparacion = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = find(ps);
            BigDecimal total = rs.next() ? rs.getBigDecimal(1) : null;
            return total == null ? BigDecimal.valueOf(0) : total;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
