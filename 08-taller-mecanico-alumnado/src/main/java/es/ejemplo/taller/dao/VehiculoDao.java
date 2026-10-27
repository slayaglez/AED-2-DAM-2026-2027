package es.ejemplo.taller.dao;
import es.ejemplo.taller.infra.ConexionBD;
import es.ejemplo.taller.modelo.Vehiculo;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
public class VehiculoDao extends AbstractDao<Vehiculo, String> {
    private final String url;
    public VehiculoDao(String url) { this.url = url; }

    public List<Vehiculo> mapper(ResultSet rs) {
        List<Vehiculo> lista = new ArrayList<>();
        try {
            while(rs.next()){
                lista.add( new Vehiculo(rs.getString(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getInt(4),
                                        rs.getInt(5))
                );
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehiculo> listar() {
        String query = "SELECT matricula, marca, modelo, anio, id_cliente FROM vehiculos";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Vehiculo> buscarPorId(String id) {
        String query = "SELECT matricula, marca, modelo, anio, id_cliente FROM vehiculos WHERE matricula = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, id);
            return mapper(find(ps)).stream().findFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int insertar(Vehiculo dato) {
        if(dato == null || dato.getMatricula() == null || dato.getMatricula().isBlank()) return 0;

        String query = "INSERT INTO vehiculos (matricula, marca, modelo, anio, id_cliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, dato.getMatricula());
            ps.setString(2, dato.getMarca());
            ps.setString(3, dato.getModelo());
            ps.setInt(4, dato.getAnio());
            ps.setInt(5, dato.getIdCliente());

            return create(ps);

        } catch (SQLException e) {
            return 0;
        }
    }
    public int actualizar(Vehiculo dato) {
        String query = "UPDATE vehiculos SET marca = ?, modelo = ?, anio = ?, id_cliente = ? WHERE matricula = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, dato.getMarca());
            ps.setString(2, dato.getModelo());
            ps.setInt(3, dato.getAnio());
            ps.setInt(4, dato.getIdCliente());
            ps.setString(5, dato.getMatricula());

            return create(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int eliminar(String id) {
        String query = "DELETE FROM vehiculos WHERE matricula = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, id);

            return create(ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Vehiculo> buscarPorCliente(Integer idCliente) {
        String query = "SELECT matricula, marca, modelo, anio, id_cliente FROM vehiculos WHERE id_cliente = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, idCliente);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Vehiculo> listarPorMarca(String marca) {
        String query = "SELECT matricula, marca, modelo, anio, id_cliente FROM vehiculos WHERE marca = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, marca);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
