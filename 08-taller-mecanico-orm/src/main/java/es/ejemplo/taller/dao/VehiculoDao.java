package es.ejemplo.taller.dao;
import es.ejemplo.taller.infra.ConexionBD;
import es.ejemplo.taller.modelo.Vehiculo;
import es.ejemplo.taller.orm.config.OrmFactory;
import es.ejemplo.taller.orm.entity.ClienteEntity;
import es.ejemplo.taller.orm.entity.VehiculoEntity;
import es.ejemplo.taller.orm.mapper.ClienteMapper;
import es.ejemplo.taller.orm.mapper.VehiculoMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
public class VehiculoDao extends AbstractDao<Vehiculo, String> {
    private final String url;
    private final EntityManagerFactory managerFactory;
    public VehiculoDao(String url) {
        this.url = url;
        this.managerFactory = OrmFactory.crear(url);
    }

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
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "select v from VehiculoEntity v order by v.matricula",
                            VehiculoEntity.class)
                    .getResultStream()
                    .map(VehiculoMapper::toDomain)
                    .toList();
        }
    }
    public Optional<Vehiculo> buscarPorId(String id) {
        try (var em = managerFactory.createEntityManager()) {
            return Optional.ofNullable(em.find(VehiculoEntity.class, id))
                    .map(VehiculoMapper::toDomain);
        }
    }
    public int insertar(Vehiculo vehiculo) {
        if (vehiculo == null) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                VehiculoEntity entidad = VehiculoMapper.toEntity(vehiculo);
                em.persist(entidad);
                em.flush(); // Necesario aquí para leer inmediatamente el ID generado
                vehiculo.setMatricula(entidad.getMatricula());
                tx.commit();
                return 1;
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                return 0;
            }
        }

    }
    public int actualizar(Vehiculo vehiculo) {
        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                VehiculoEntity v = em.find(VehiculoEntity.class, vehiculo.getMatricula());
                if (v == null) {
                    tx.commit();
                    return 0;
                }
                VehiculoEntity vehiculoEntityActualizado = VehiculoMapper.toEntity(vehiculo);
                v.setMarca(vehiculoEntityActualizado.getMarca());
                v.setModelo(vehiculoEntityActualizado.getModelo());
                v.setAnio(vehiculoEntityActualizado.getAnio());
                tx.commit(); // Hibernate detecta el cambio
                return 1;
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                throw ex;
            }
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

    public List<Vehiculo> buscarPorCliente(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "select v from VehiculoEntity v where v.idCliente = :id",
                            VehiculoEntity.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .map(VehiculoMapper::toDomain).toList();
        }
    }

    public List<Vehiculo> listarPorMarca(String marca) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "select v from VehiculoEntity v where v.marca = :marca",
                            VehiculoEntity.class)
                    .setParameter("marca", marca)
                    .getResultStream()
                    .map(VehiculoMapper::toDomain).toList();
        }
    }
}
