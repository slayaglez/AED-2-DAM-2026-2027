package es.ejemplo.taller.dao;

import es.ejemplo.taller.infra.ConexionBD;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AbstractDao<T, ID>{
    public AbstractDao(){}

    // Estilo 1
    ResultSet find(PreparedStatement ps) {
        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    int create(PreparedStatement ps) {
        try {
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }

    }

    int insert(PreparedStatement ps) {
        try {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

    // Estilo 2
    ResultSet findAll(String query, String url) {
        try {
            Connection c = ConexionBD.abrir(url);
            PreparedStatement ps = c.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ResultSet findById(String query, ID id, String url) {
        try {
            Connection c = ConexionBD.abrir(url);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setObject(1, id);
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    int create(String query, T obj, String url) {
        if (obj == null) return 0;
        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            Field[] campos = obj.getClass().getDeclaredFields();
            for (int i = 1; i < campos.length; i++) {
                campos[i].setAccessible(true);
                ps.setObject(i, campos[i].get(obj));
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException | IllegalAccessException e) {
            return 0;
        }
    }

    int update(String query, T obj, String url) {
        if (obj == null) return 0;
        try (Connection c = ConexionBD.abrir(url);){
            PreparedStatement ps = c.prepareStatement(query);

            Field[] campos = obj.getClass().getDeclaredFields();
            for (Field campo : campos) campo.setAccessible(true);
            ps.setObject(campos.length, campos[0].get(obj));
            for (int i = 1; i < campos.length; i++) {
                ps.setObject(i, campos[i].get(obj));
            }

            return ps.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            return 0;
        }
    }

}
