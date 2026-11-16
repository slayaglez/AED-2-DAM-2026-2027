package es.ejemplo.biblioteca.dao;
import es.ejemplo.biblioteca.modelo.Prestamo;

import es.ejemplo.biblioteca.orm.entity.PrestamoEntity;
import es.ejemplo.biblioteca.orm.mapper.PrestamoMapper;
import es.ejemplo.biblioteca.orm.config.OrmFactory;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase PrestamoDao encargada de conectar la clase Prestamo y la BBDD
 */
public class PrestamoDao implements AutoCloseable {
    private static final Logger LOG=Logger.getLogger(PrestamoDao.class.getName());
    private final EntityManagerFactory managerFactory;
    private final boolean ownFactory;
    
    public PrestamoDao(String url) {managerFactory=OrmFactory.crear(url);ownFactory=true;}
    
    public PrestamoDao(EntityManagerFactory factory) {managerFactory=factory;ownFactory=false;}
    private void registrar(String metodo,Exception ex) {LOG.log(Level.WARNING,metodo+" no se pudo completar: "+ex.getMessage());}
    
    /**
     * Metodo listar que busca todos los prestamos de la tabla
     * @return lista de Prestamo
     */
    public List<Prestamo> listar() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM PrestamoEntity AS p ORDER BY p.fechaPrestamo",
                            PrestamoEntity.class)
                    .getResultStream()
                    .map(PrestamoMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listar", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca por ID
     * @param id el ID del prestamo
     * @return Un prestamo o vacio si no existe
     */
    public Optional<Prestamo> buscarPorId(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            return Optional.ofNullable(em.find(PrestamoEntity.class, id))
                    .map(PrestamoMapper::toDomain);
        } catch (RuntimeException ex) {
            registrar("buscarPorId", ex);
            return Optional.empty();
        }
    }
    
    /**
     * Metodo que inserta un prestamo nuevo en la tabla
     * @param dato el prestamo a insertar
     * @return el ID generado o 0 si no se pudo insertar
     */
    public int insertar(Prestamo dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                PrestamoEntity entidad = PrestamoMapper.toEntity(dato);
                em.persist(entidad);
                em.flush(); // Necesario aqui para leer el ID generado
                dato.setIdPrestamo(entidad.getIdPrestamo());
                tx.commit();
                return dato.getIdPrestamo();
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("insertar", ex);
                return 0;
            }
        }
    }
    
    /**
     * Metodo que actualiza los datos de un prestamo existente
     * @param dato el prestamo con los datos nuevos
     * @return 1 si se actualizo o 0 si no existe o hubo un error
     */
    public int actualizar(Prestamo dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                PrestamoEntity p = em.find(PrestamoEntity.class, dato.getIdPrestamo());
                if (p == null) {
                    tx.commit();
                    return 0;
                }
                PrestamoEntity entidad = PrestamoMapper.toEntity(dato);
                p.setIdSocio(entidad.getIdSocio());
                p.setIdLibro(entidad.getIdLibro());
                p.setFechaPrestamo(entidad.getFechaPrestamo());
                p.setFechaLimite(entidad.getFechaLimite());
                p.setFechaDevolucion(entidad.getFechaDevolucion());
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
     * Metodo que elimina un prestamo por ID
     * @param id el ID del prestamo
     * @return 1 si se elimino o 0 si no existe o hubo un error
     */
    public int eliminar(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                PrestamoEntity p = em.find(PrestamoEntity.class, id);
                if (p == null) {
                    tx.commit();
                    return 0;
                }
                em.remove(p);
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
     * Metodo que comprueba que el prestamo tiene los datos obligatorios
     * @param dato el prestamo a comprobar
     * @return true si es valido
     */
    private boolean validar(Prestamo dato) {return dato != null && dato.getIdSocio()!=null && dato.getIdLibro()!=null && dato.getFechaPrestamo()!=null && dato.getFechaLimite()!=null && dato.getFechaLimite().compareTo(dato.getFechaPrestamo())>=0 && (dato.getFechaDevolucion()==null || dato.getFechaDevolucion().compareTo(dato.getFechaPrestamo())>=0);}
    
    /**
     * Metodo que busca los prestamos de un socio
     * @param idSocio el ID del socio
     * @return lista de Prestamo
     */
    public List<Prestamo> listarPorSocio(int idSocio) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM PrestamoEntity AS p WHERE p.idSocio = :id ORDER BY p.fechaPrestamo",
                            PrestamoEntity.class)
                    .setParameter("id", idSocio)
                    .getResultStream()
                    .map(PrestamoMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarPorSocio", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca los prestamos que aun no se han devuelto
     * @return lista de Prestamo
     */
    public List<Prestamo> listarActivos() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM PrestamoEntity AS p WHERE p.fechaDevolucion IS NULL ORDER BY p.fechaPrestamo",
                            PrestamoEntity.class)
                    .getResultStream()
                    .map(PrestamoMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarActivos", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca los prestamos sin devolver cuya fecha limite ya ha pasado
     * @param fecha la fecha de referencia (yyyy-MM-dd)
     * @return lista de Prestamo
     */
    public List<Prestamo> listarVencidos(String fecha) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT p FROM PrestamoEntity AS p WHERE p.fechaDevolucion IS NULL AND p.fechaLimite < :fecha "
                            + "ORDER BY p.fechaLimite",
                            PrestamoEntity.class)
                    .setParameter("fecha", fecha)
                    .getResultStream()
                    .map(PrestamoMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarVencidos", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que cuenta los prestamos de un libro
     * @param idLibro el ID del libro
     * @return numero de prestamos o 0 si hubo un error
     */
    public long contarPrestamosPorLibro(int idLibro) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery("SELECT COUNT(p) FROM PrestamoEntity AS p WHERE p.idLibro = :id",
                            Long.class)
                    .setParameter("id", idLibro)
                    .getSingleResult();
        } catch (RuntimeException ex) {
            registrar("contarPrestamosPorLibro", ex);
            return 0;
        }
    }

    /**
     *
     * @return
     */
    public List<Object[]> prestamosConSocioYLibro() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> rankingMensual(String anioMes, int limite) {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }

    
    public int registrarPrestamo(int idSocio, int idLibro, String inicio, String limite) {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return 0;
    }
    
    public int devolver(int id, String fecha) {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return 0;
    }

    
    @Override public void close() {if(ownFactory && managerFactory.isOpen())managerFactory.close();}
}
