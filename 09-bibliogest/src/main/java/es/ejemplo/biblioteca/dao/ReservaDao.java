package es.ejemplo.biblioteca.dao;
import es.ejemplo.biblioteca.modelo.Reserva;

import es.ejemplo.biblioteca.orm.entity.ReservaEntity;
import es.ejemplo.biblioteca.orm.mapper.ReservaMapper;
import es.ejemplo.biblioteca.orm.config.OrmFactory;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase ReservaDao encargada de conectar la clase Reserva y la BBDD
 */
public class ReservaDao implements AutoCloseable {
    private static final Logger LOG=Logger.getLogger(ReservaDao.class.getName());
    private final EntityManagerFactory managerFactory;
    private final boolean ownFactory;
    
    public ReservaDao(String url) {managerFactory=OrmFactory.crear(url);ownFactory=true;}
    
    public ReservaDao(EntityManagerFactory factory) {managerFactory=factory;ownFactory=false;}
    private void registrar(String metodo,Exception ex) {LOG.log(Level.WARNING,metodo+" no se pudo completar: "+ex.getMessage());}
    
    /**
     * Metodo listar que busca todas las reservas de la tabla
     * @return lista de Reserva
     */
    public List<Reserva> listar() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT r FROM ReservaEntity AS r ORDER BY r.fechaReserva",
                            ReservaEntity.class)
                    .getResultStream()
                    .map(ReservaMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listar", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca por ID
     * @param id el ID de la reserva
     * @return Una reserva o vacio si no existe
     */
    public Optional<Reserva> buscarPorId(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            return Optional.ofNullable(em.find(ReservaEntity.class, id))
                    .map(ReservaMapper::toDomain);
        } catch (RuntimeException ex) {
            registrar("buscarPorId", ex);
            return Optional.empty();
        }
    }
    
    /**
     * Metodo que inserta una reserva nueva en la tabla
     * @param dato la reserva a insertar
     * @return el ID generado o 0 si no se pudo insertar
     */
    public int insertar(Reserva dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                ReservaEntity entidad = ReservaMapper.toEntity(dato);
                em.persist(entidad);
                em.flush(); // Necesario aqui para leer el ID generado
                dato.setIdReserva(entidad.getIdReserva());
                tx.commit();
                return dato.getIdReserva();
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("insertar", ex);
                return 0;
            }
        }
    }
    
    /**
     * Metodo que actualiza los datos de una reserva existente
     * @param dato la reserva con los datos nuevos
     * @return 1 si se actualizo o 0 si no existe o hubo un error
     */
    public int actualizar(Reserva dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                ReservaEntity r = em.find(ReservaEntity.class, dato.getIdReserva());
                if (r == null) {
                    tx.commit();
                    return 0;
                }
                ReservaEntity entidad = ReservaMapper.toEntity(dato);
                r.setIdSocio(entidad.getIdSocio());
                r.setIdLibro(entidad.getIdLibro());
                r.setFechaReserva(entidad.getFechaReserva());
                r.setEstado(entidad.getEstado());
                tx.commit();
                return 1;
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("actualizar", ex);
                return 0;
            }
        }
    }
    
    /**
     * Metodo que elimina una reserva por ID
     * @param id el ID de la reserva
     * @return 1 si se elimino o 0 si no existe o hubo un error
     */
    public int eliminar(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                ReservaEntity r = em.find(ReservaEntity.class, id);
                if (r == null) {
                    tx.commit();
                    return 0;
                }
                em.remove(r);
                tx.commit();
                return 1;
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("eliminar", ex);
                return 0;
            }
        }
    }
    /**
     * Metodo que comprueba que la reserva tiene los datos obligatorios
     * @param dato la reserva a comprobar
     * @return true si es valido
     */
    private boolean validar(Reserva dato) {return dato != null && dato.getIdSocio()!=null && dato.getIdLibro()!=null && dato.getFechaReserva()!=null && dato.getEstado()!=null && (dato.getEstado().equals("PENDIENTE") || dato.getEstado().equals("ATENDIDA") || dato.getEstado().equals("CANCELADA"));}
    
    /**
     * Metodo que busca las reservas pendientes
     * @return lista de Reserva
     */
    public List<Reserva> listarPendientes() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT r FROM ReservaEntity AS r WHERE r.estado = 'PENDIENTE' ORDER BY r.fechaReserva",
                            ReservaEntity.class)
                    .getResultStream()
                    .map(ReservaMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarPendientes", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca las reservas de un socio
     * @param idSocio el ID del socio
     * @return lista de Reserva
     */
    public List<Reserva> buscarPorSocio(int idSocio) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT r FROM ReservaEntity AS r WHERE r.idSocio = :id ORDER BY r.fechaReserva",
                            ReservaEntity.class)
                    .setParameter("id", idSocio)
                    .getResultStream()
                    .map(ReservaMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("buscarPorSocio", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca las reservas de un libro
     * @param idLibro el ID del libro
     * @return lista de Reserva
     */
    public List<Reserva> listarPorLibro(int idLibro) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT r FROM ReservaEntity AS r WHERE r.idLibro = :id ORDER BY r.fechaReserva",
                            ReservaEntity.class)
                    .setParameter("id", idLibro)
                    .getResultStream()
                    .map(ReservaMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarPorLibro", ex);
            return List.of();
        }
    }
    
    public long contarReservasPendientes() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return 0;
    }
    
    public List<Object[]> reservasConDetalles() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> prioridadReservas() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }


    
    @Override public void close() {if(ownFactory && managerFactory.isOpen())managerFactory.close();}
}
