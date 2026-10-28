package es.ejemplo.gestion.dao;

import es.ejemplo.gestion.config.ConexionBD;
import es.ejemplo.gestion.exception.AplicationException;
import es.ejemplo.gestion.model.Producto;
import es.ejemplo.gestion.model.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VehiculoDao extends AbstractDao<Vehiculo> {

    public VehiculoDao(String url) {
        super(url);
    }

    @Override
    Vehiculo mapToClass(ResultSet rs) {
        return null;  // TODO
    }

    private List<Vehiculo> leerVehiculos(PreparedStatement sentencia) throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        try (ResultSet rs = sentencia.executeQuery()) {
            while (rs.next()) {
                lista.add(new Vehiculo(rs.getString("matricula"), rs.getString("marca"),
                        rs.getString("modelo"), rs.getInt("anio")));
            }
        }
        return lista;
    }

    private void validar(Vehiculo v) {
        String matricula = v.getMatricula();
        if (matricula == null || matricula.isBlank())
            throw new AplicationException("La matrícula no puede estar vacía");
        if (!matricula.equals(matricula.trim().toUpperCase()))
            throw new AplicationException("La matrícula debe estar en mayúsculas y sin espacios");
        if (v.getMarca() == null || v.getMarca().isBlank())
            throw new AplicationException("La marca no puede estar vacía");
        if (v.getModelo() == null || v.getModelo().isBlank())
            throw new AplicationException("El modelo no puede estar vacío");
        if (v.getAnio() < 1886) throw new AplicationException("El año no puede ser anterior a 1886");
    }

    public List<Vehiculo> listar() {
        String query = "SELECT matricula, marca, modelo, anio FROM vehiculos ORDER BY matricula";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            return leerVehiculos(sentencia);
        } catch (SQLException e) {
            throw new AplicationException("Error listando vehículos", e);
        }
    }

    public Optional<Vehiculo> buscarPorMatricula(String matricula) {
        String query = "SELECT matricula, marca, modelo, anio FROM vehiculos WHERE matricula = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, matricula);
            return leerVehiculos(sentencia).stream().findFirst();
        } catch (SQLException e) {
            throw new AplicationException("Error buscando el vehículo " + matricula, e);
        }
    }

    public boolean insertar(Vehiculo v) {
        validar(v);
        if (buscarPorMatricula(v.getMatricula()).isPresent()) {
            throw new AplicationException("Ya existe un vehículo con matrícula " + v.getMatricula());
        }
        String query = "INSERT INTO vehiculos (matricula, marca, modelo, anio) VALUES (?, ?, ?, ?)";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, v.getMatricula());
            sentencia.setString(2, v.getMarca());
            sentencia.setString(3, v.getModelo());
            sentencia.setInt(4, v.getAnio());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AplicationException("Error insertando el vehículo " + v.getMatricula(), e);
        }
    }

    public boolean actualizar(Vehiculo v) {
        validar(v);
        String query = "UPDATE vehiculos SET marca = ?, modelo = ?, anio = ? WHERE matricula = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, v.getMarca());
            sentencia.setString(2, v.getModelo());
            sentencia.setInt(3, v.getAnio());
            sentencia.setString(4, v.getMatricula());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AplicationException("Error actualizando el vehículo " + v.getMatricula(), e);
        }
    }

    public boolean eliminar(String matricula) {
        String query = "DELETE FROM vehiculos WHERE matricula = ?";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, matricula);
            return sentencia.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AplicationException("Error eliminando el vehículo " + matricula, e);
        }
    }

    public List<Vehiculo> buscarPorMarca(String texto) {
        String query = "SELECT matricula, marca, modelo, anio FROM vehiculos WHERE LOWER(marca) = LOWER(?) ORDER BY matricula";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setString(1, texto);
            return leerVehiculos(sentencia);
        } catch (SQLException e) {
            throw new AplicationException("Error buscando vehículos por marca", e);
        }
    }

    public List<Vehiculo> buscarPorAnio(int desde, int hasta) {
        String query = "SELECT matricula, marca, modelo, anio FROM vehiculos WHERE anio >= ? AND anio <= ? ORDER BY anio, matricula";
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query)) {
            sentencia.setInt(1, desde);
            sentencia.setInt(2, hasta);
            return leerVehiculos(sentencia);
        } catch (SQLException e) {
            throw new AplicationException("Error buscando vehículos por año", e);
        }
    }

    public int contar() {
        return listar().size();
    }

    public Map<String, Integer> contarPorMarca() {
        String query = "SELECT marca, COUNT(*) AS total FROM vehiculos GROUP BY marca ORDER BY marca";
        Map<String, Integer> conteo = new LinkedHashMap<>();
        try (Connection con = obtenerConexion();
             PreparedStatement sentencia = con.prepareStatement(query);
             ResultSet rs = sentencia.executeQuery()) {
            while (rs.next()) {
                conteo.put(rs.getString("marca"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            throw new AplicationException("Error contando vehículos por marca", e);
        }
        return conteo;
    }
}
