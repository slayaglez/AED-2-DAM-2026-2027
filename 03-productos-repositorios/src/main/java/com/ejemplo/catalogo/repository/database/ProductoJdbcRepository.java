package com.ejemplo.catalogo.repository.database;

import com.ejemplo.catalogo.model.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoJdbcRepository extends AbstractJdbcRepository <Producto, Long>{
    @Override
    public List<Producto> findAll() {
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

    @Override
    public Optional<Producto> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void create(Producto producto) {

    }

    @Override
    public boolean update(Producto producto) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
