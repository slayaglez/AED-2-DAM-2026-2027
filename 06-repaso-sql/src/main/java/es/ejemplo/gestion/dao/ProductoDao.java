package es.ejemplo.gestion.dao;

import es.ejemplo.gestion.config.ConexionBD;
import es.ejemplo.gestion.exception.AplicationException;
import es.ejemplo.gestion.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDao extends AbstractDao {

    public ProductoDao(String url) {
        super(url);
    }

    @Override
    Producto mapToClass(ResultSet rs) {
        try{
            return new Producto(rs.getInt(1), rs.getString(2), rs.getDouble(3) );
        } catch (SQLException e) {
            throw new AplicationException("Errorr");
        }
    }

    private List<Producto> leerProductos(PreparedStatement sentencia) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        try (ResultSet rs = sentencia.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio")));
            }
        }
        return lista;
    }

    private void validar(Producto p) {
        if (p.getId() <= 0) throw new AplicationException("El id debe ser positivo");
        if (p.getNombre() == null || p.getNombre().isBlank())
            throw new AplicationException("El nombre no puede estar vacío");
        if (p.getPrecio() < 0) throw new AplicationException("El precio no puede ser negativo");
    }

    public List<Producto> listar() {
        String query = "SELECT id, nombre, precio FROM productos ORDER BY id";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            return leerProductos(sentencia);
        } catch (SQLException e) {
            throw new AplicationException("Error listando productos", e);
        }
    }

    public Optional<Producto> buscarPorId(int id) {
        String query = "SELECT id, nombre, precio FROM productos WHERE id = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setInt(1, id);
            return leerProductos(sentencia).stream().findFirst();
        } catch (SQLException e) {
            throw new AplicationException("Error buscando el producto " + id, e);
        }
    }

    public boolean insertar(Producto p) {
        validar(p);
        if (buscarPorId(p.getId()).isPresent()) {
            throw new AplicationException("Ya existe un producto con id " + p.getId());
        }
        String query = "INSERT INTO productos (id, nombre, precio) VALUES (?, ?, ?)";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setInt(1, p.getId());
            sentencia.setString(2, p.getNombre());
            sentencia.setDouble(3, p.getPrecio());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AplicationException("Error insertando el producto " + p.getId(), e);
        }
    }

    public boolean actualizar(Producto p) {
        validar(p);
        String query = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, p.getNombre());
            sentencia.setDouble(2, p.getPrecio());
            sentencia.setInt(3, p.getId());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AplicationException("Error actualizando el producto " + p.getId(), e);
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM productos WHERE id = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AplicationException("Error eliminando el producto " + id, e);
        }
    }

    public List<Producto> buscarPorNombre(String texto) {
        String query = "SELECT id, nombre, precio FROM productos WHERE LOWER(nombre) LIKE ? ORDER BY precio, id";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, "%" + texto.toLowerCase() + "%");
            return leerProductos(sentencia);
        } catch (SQLException e) {
            throw new AplicationException("Error buscando productos por nombre", e);
        }
    }

    public List<Producto> buscarPorPrecio(double minimo, double maximo) {
        String query = "SELECT id, nombre, precio FROM productos WHERE precio >= ? AND precio <= ? ORDER BY precio, id";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setDouble(1, minimo);
            sentencia.setDouble(2, maximo);
            return leerProductos(sentencia);
        } catch (SQLException e) {
            throw new AplicationException("Error buscando productos por precio", e);
        }
    }

    public int contar() {
        return listar().size();
    }

    public double precioMedio() {
        List<Producto> lista = listar();
        if (lista.isEmpty()) {
            return 0;
        }
        double media = 0.0;
        for (Producto p : lista){
            media += p.getPrecio();
        }
        return media/lista.size();
    }
}

//public class ProductoDao extends ConexionBD{
//    private ConexionBD conexionBD;
//
//    public ProductoDao(String url) {
//        super(url);
//    }
//
//    public List<Producto> buscarConsulta(String query) {
//        Connection conexionDB = obtenerConexion();
//        List<Producto> lista = new ArrayList<>();
//        ResultSet rs;
//        try {
//            PreparedStatement sentencia = conexionDB.prepareStatement(query);
//            rs = sentencia.executeQuery();
//            while(rs.next()){
//                lista.add(new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio")));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return lista;
//    }
//
//    public boolean ejecutarConsulta(String query){
//        Connection conexionDB = obtenerConexion();
//        try {
//            PreparedStatement sentencia = conexionDB.prepareStatement(query);
//            return sentencia.execute();
//        } catch (SQLException e) {
//            return false;
//        }
//    }
//
//    private void validar(Producto p) {
//        if (p.getId() <= 0) throw new AplicationException("El id debe ser positivo");
//        if (p.getNombre() == null || p.getNombre().isBlank()) throw new AplicationException("El nombre no puede estar vacío");
//        if (p.getPrecio() < 0) throw new AplicationException("El precio no puede ser negativo");
//    }
//
//    public List<Producto> listar() {
//        String query = "SELECT id, nombre, precio FROM productos;";
//        return buscarConsulta(query);
//    }
//
//    public Optional<Producto> buscarPorId(int id) {
//        String query = "SELECT id, nombre, precio FROM productos WHERE id = " + id; //! Pregunta 1
//        return buscarConsulta(query).stream().findFirst();
//    }
//
//    public boolean insertar(Producto p) {
//        validar(p);
//        String query = "INSERT INTO producto (id, nombre, precio) VALUES ( "+ p.getId() + ", " + p.getNombre() + ", " + p.getPrecio() + ")";
//        return ejecutarConsulta(query);
//    }
//
//    public boolean actualizar(Producto p) {
//        String query = "UPDATE producto " +
//                "SET " + "id="+p.getId() + ", nombre="+p.getNombre() + ", precio="+p.getPrecio() +
//                " WHERE id = "+p.getId();
//        return ejecutarConsulta(query);
//    }
//
//    public boolean eliminar(int id) {
//        String query = "DELETE FROM producto WHERE id = "+id;
//        return ejecutarConsulta(query);
//    }
//
//    public List<Producto> buscarPorNombre(String texto) {
//        String query = "SELECT id, nombre, precio FROM productos WHERE nombre = " + texto;
//        return buscarConsulta(query);
//    }
//
//    public List<Producto> buscarPorPrecio(double minimo, double maximo) {
//        String query = "SELECT id, nombre, precio FROM productos WHERE precio >= " + minimo + "AND precio <= " + maximo;
//        return buscarConsulta(query);
//    }
//
//    public int contar() {
//        return listar().size();
//    }
//
//    public double precioMedio() {
//        List<Producto> lista = listar();
//        double media = 0.0;
//        for (Producto p : lista){
//            media += p.getPrecio();
//        }
//        return media/lista.size();
//    }
//}
