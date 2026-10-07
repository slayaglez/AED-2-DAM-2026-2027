package com.ejemplo.catalogo.repository.database;

import com.ejemplo.catalogo.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrudSQL extends DataBaseInit {
    public CrudSQL(String url) {
        super(url);
    }

    public List<Producto> readAll() {
        String sql = "SELECT id, nombre, precio FROM producto ORDER BY id";
        List<Producto> productos = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")));
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    return productos;
    }

    public Optional<Producto> readById() {
        String sql = "SELECT nombre, precio FROM producto WHERE id = ?";
        List<Producto> productos = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) {
                throw new IllegalArgumentException("El producto no existe");
            }

            return Optional.of(new Producto(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio")));

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean create(Producto producto) {
        String sql = "INSERT INTO producto(id, nombre, precio) VALUES (?, ?, ?)";

        try (Connection c = DriverManager.getConnection(url);
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, producto.id());
            ps.setString(2, producto.nombre());
            ps.setDouble(3, producto.precio());
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ? WHERE id = ?";

        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(3, producto.id());
            ps.setString(1, producto.nombre());
            ps.setDouble(2, producto.precio());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";

        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

