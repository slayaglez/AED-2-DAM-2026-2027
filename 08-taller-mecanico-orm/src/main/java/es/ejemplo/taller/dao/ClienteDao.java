package es.ejemplo.taller.dao;
import es.ejemplo.taller.infra.ConexionBD;
import es.ejemplo.taller.modelo.Cliente;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
public class ClienteDao extends AbstractDao<Cliente, Integer> {
    private final String url;
    public ClienteDao(String url) { this.url = url; }

    public List<Cliente> mapper(ResultSet rs) {
        List<Cliente> lista = new ArrayList<>();
        try {
            while(rs.next()){
                lista.add( new Cliente(rs.getInt(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4))
                );
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> listar() {
        String query = "SELECT id_cliente, nombre, email, telefono FROM clientes";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            return mapper(find(ps));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Cliente> buscarPorId(Integer id) {
        String query = "SELECT id_cliente, nombre, email, telefono FROM clientes WHERE id_cliente = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            return mapper(find(ps)).stream().findFirst();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int insertar(Cliente dato) {
        if(dato == null || dato.getNombre() == null || dato.getNombre().isBlank()) return 0;

        String query = "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dato.getNombre());
            ps.setString(2, dato.getEmail());
            ps.setString(3, dato.getTelefono());

            return insert(ps);

        } catch (SQLException e) {
            return 0;
        }
    }
    public int actualizar(Cliente dato) {
        String query = "UPDATE clientes SET nombre = ?, email = ?, telefono= ? WHERE id_cliente = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, dato.getNombre());
            ps.setString(2, dato.getEmail());
            ps.setString(3, dato.getTelefono());
            ps.setInt(4, dato.getIdCliente());

            return create(ps);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int eliminar(Integer id) {
        String query = "DELETE FROM clientes WHERE id_cliente = ?";

        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);

            return create(ps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
