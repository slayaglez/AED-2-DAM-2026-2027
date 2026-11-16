package es.ejemplo.cinefest.dao;

import es.ejemplo.cinefest.modelo.Entrada;
import es.ejemplo.cinefest.orm.config.OrmFactory;
import es.ejemplo.cinefest.orm.entity.EntradaEntity;
import es.ejemplo.cinefest.orm.mapper.EntradaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

import static es.ejemplo.cinefest.orm.config.OrmFactory.crear;


public class EntradaDao {

    private final EntityManagerFactory emf;

    public EntradaDao(String jdbcUrl) {
        this.emf = crear(jdbcUrl);
    }
   
    // Usar transacciones y rollback en operaciones de escritura.
    public List<Entrada> listar() {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            return em.createQuery("SELECT e FROM Entrada e ORDER BY e.fecha", EntradaEntity.class)
                    .getResultStream()
                    .map(EntradaMapper::desdeEntidad).toList();

        } catch (Exception ex) {
            return List.of();
        }
    }

    public Optional<Entrada> buscarPorId(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            return Optional.ofNullable(em.find(EntradaEntity.class, id)).map(EntradaMapper::desdeEntidad);
        }
    }
    public int insertar(Entrada dato) {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            EntradaEntity entity = EntradaMapper.aEntidad(dato);
            em.persist(entity);
            em.flush();
            dato.setIdEntrada(entity.getIdEntrada());
            tx.commit();
            return entity.getIdEntrada();
        } catch (Exception ex) {
            return 0;
        }
    }
    public int actualizar(Entrada dato) {
        try(EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            EntradaEntity entity = EntradaMapper.aEntidad(dato);
            em.merge(entity);
            tx.commit();
            return 1;
        } catch (Exception ex) {
            return 0;
        }
    }
    public int eliminar(int id) {
        // TODO: implementar conforme al contrato del README.md.
        return 0;
    }
    public List<Object[]> entradasConDetalles() {
        // TODO: implementar conforme al contrato del README.md.
        return List.of();
    }
    public List<Object[]> entradasPorEspectador(int idEspectador) {
        // TODO: implementar conforme al contrato del README.md.
        return List.of();
    }
    public List<Object[]> ventasPorDia() {
        // TODO: implementar conforme al contrato del README.md.
        return List.of();
    }
    public int cancelarEntrada(int idEntrada) {
        // TODO: implementar conforme al contrato del README.md.
        return 0;
    }

}
