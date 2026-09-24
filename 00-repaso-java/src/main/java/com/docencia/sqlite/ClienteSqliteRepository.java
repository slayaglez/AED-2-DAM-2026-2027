package com.docencia.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.List;

public class ClienteSqliteRepository implements ClienteRepository {
    private final String url;

    public ClienteSqliteRepository(String databasePath) {
        this.url = "jdbc:sqlite:" + databasePath;
        crearTablaSiNoExiste();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente (dni TEXT PRIMARY KEY, nombre TEXT NOT NULL, email TEXT NOT NULL, ciudad TEXT NOT NULL)";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            throw new RuntimeException("No se pudo crear la tabla cliente", exception);
        }
    }

    @Override
    public Boolean save(Cliente cliente) {
        try (Connection connection = getConnection();
             PreparedStatement sentencia = connection
                     .prepareStatement("INSERT INTO cliente VALUES (?, ?, ?, ?)")) {
            sentencia.setString(1, cliente.getDni());
            sentencia.setString(2, cliente.getNombre());
            sentencia.setString(3, cliente.getEmail());
            sentencia.setString(4, cliente.getCiudad());
            return sentencia.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error creando cliente" + e);
            return false;
        }
    }

    @Override
    public Cliente findByDni(String dni) {

        try (Connection connection = getConnection();
             PreparedStatement sentencia = connection.prepareStatement("SELECT * FROM cliente WHERE dni = ?")) {
             sentencia.setString(1, dni);
            ResultSet resultado = sentencia.executeQuery();

            if(!resultado.next()) {
                throw new IllegalStateException("El cliente no existe");
            }

            String nombre = resultado.getString("nombre");
            String email = resultado.getString("email");
            String ciudad = resultado.getString("ciudad");

            return new Cliente(dni, nombre, email, ciudad);

        } catch (Exception e) {
            System.err.println("Error buscando clientes" + e);
            return null;
        }
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement sentencia = connection.prepareStatement("SELECT * FROM cliente")) {
            ResultSet resultado = sentencia.executeQuery();

            while (resultado.next()) {
                String dni = resultado.getString("dni");
                String nombre = resultado.getString("nombre");
                String email = resultado.getString("email");
                String ciudad = resultado.getString("ciudad");
                clientes.add(new Cliente(dni, nombre, email, ciudad));
            }
            return clientes;

        } catch (Exception e) {
            System.err.println("Error buscando clientes" + e);
            return null;
        }
    }

    @Override
    public Boolean update(Cliente cliente) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Boolean deleteByDni(String dni) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByDni'");
    }

    @Override
    public List<Cliente> findByCiudad(String ciudad) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCiudad'");
    }

    
}
