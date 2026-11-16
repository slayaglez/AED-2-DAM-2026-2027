package es.ejemplo.biblioteca.dao;
import es.ejemplo.biblioteca.modelo.Socio;

import es.ejemplo.biblioteca.orm.entity.SocioEntity;
import es.ejemplo.biblioteca.orm.mapper.SocioMapper;
import es.ejemplo.biblioteca.orm.config.OrmFactory;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase SocioDao encargada de conectar la clase Socio y la BBDD
 */
public class SocioDao implements AutoCloseable {
    private static final Logger LOG=Logger.getLogger(SocioDao.class.getName());
    private final EntityManagerFactory managerFactory;
    private final boolean ownFactory;
    
    public SocioDao(String url) {managerFactory=OrmFactory.crear(url);ownFactory=true;}
    
    public SocioDao(EntityManagerFactory factory) {managerFactory=factory;ownFactory=false;}
    private void registrar(String metodo,Exception ex) {LOG.log(Level.WARNING,metodo+" no se pudo completar: "+ex.getMessage());}
    
    /**
     * Metodo listar que busca todos los socios de la tabla
     * @return lista de Socio
     */
    public List<Socio> listar() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM SocioEntity AS s ORDER BY s.nombre",
                            SocioEntity.class)
                    .getResultStream()
                    .map(SocioMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listar", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca por ID
     * @param id el ID del socio
     * @return Un socio o vacio si no existe
     */
    public Optional<Socio> buscarPorId(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            return Optional.ofNullable(em.find(SocioEntity.class, id))
                    .map(SocioMapper::toDomain);
        } catch (RuntimeException ex) {
            registrar("buscarPorId", ex);
            return Optional.empty();
        }
    }
    
    /**
     * Metodo que inserta un socio nuevo en la tabla
     * @param dato el socio a insertar
     * @return el ID generado o 0 si no se pudo insertar
     */
    public int insertar(Socio dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                SocioEntity entidad = SocioMapper.toEntity(dato);
                em.persist(entidad);
                em.flush(); // Necesario aqui para leer el ID generado
                dato.setIdSocio(entidad.getIdSocio());
                tx.commit();
                return dato.getIdSocio();
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("insertar", ex);
                return 0;
            }
        }
    }
    
    /**
     * Metodo que actualiza los datos de un socio existente
     * @param dato el socio con los datos nuevos
     * @return 1 si se actualizo o 0 si no existe o hubo un error
     */
    public int actualizar(Socio dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                SocioEntity s = em.find(SocioEntity.class, dato.getIdSocio());
                if (s == null) {
                    tx.commit();
                    return 0;
                }
                SocioEntity entidad = SocioMapper.toEntity(dato);
                s.setNombre(entidad.getNombre());
                s.setEmail(entidad.getEmail());
                s.setTelefono(entidad.getTelefono());
                s.setFechaAlta(entidad.getFechaAlta());
                s.setActivo(entidad.getActivo());
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
     * Metodo que elimina un socio por ID
     * @param id el ID del socio
     * @return 1 si se elimino o 0 si no existe o hubo un error
     */
    public int eliminar(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                SocioEntity s = em.find(SocioEntity.class, id);
                if (s == null) {
                    tx.commit();
                    return 0;
                }
                em.remove(s);
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
     * Metodo que comprueba que el socio tiene los datos obligatorios
     * @param dato el socio a comprobar
     * @return true si es valido
     */
    private boolean validar(Socio dato) {return dato != null && dato.getNombre()!=null && !dato.getNombre().isBlank() && dato.getEmail()!=null && !dato.getEmail().isBlank() && dato.getFechaAlta()!=null && dato.getActivo()!=null;}
    
    /**
     * Metodo que busca los socios activos
     * @return lista de Socio
     */
    public List<Socio> listarActivos() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM SocioEntity AS s WHERE s.activo = true ORDER BY s.nombre",
                            SocioEntity.class)
                    .getResultStream()
                    .map(SocioMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarActivos", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca un socio por su email
     * @param email el email del socio
     * @return Un socio o vacio si no existe
     */
    public Optional<Socio> buscarPorEmail(String email) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery("SELECT s FROM SocioEntity AS s WHERE s.email = :email",
                            SocioEntity.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst()
                    .map(SocioMapper::toDomain);
        } catch (RuntimeException ex) {
            registrar("buscarPorEmail", ex);
            return Optional.empty();
        }
    }
    
    /**
     * Metodo que busca los socios con algun prestamo sin devolver cuya fecha limite ya ha pasado
     * @param fecha la fecha de referencia (yyyy-MM-dd)
     * @return lista de Socio
     */
    public List<Socio> listarConPrestamosVencidos(String fecha) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM SocioEntity AS s WHERE s.idSocio IN ("
                            + "SELECT p.idSocio FROM PrestamoEntity AS p "
                            + "WHERE p.fechaDevolucion IS NULL AND p.fechaLimite < :fecha) "
                            + "ORDER BY s.nombre",
                            SocioEntity.class)
                    .setParameter("fecha", fecha)
                    .getResultStream()
                    .map(SocioMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listarConPrestamosVencidos", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca los socios que nunca han tenido un prestamo
     * @return lista de Socio
     */
    public List<Socio> sociosSinPrestamos() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM SocioEntity AS s WHERE NOT EXISTS ("
                            + "SELECT p FROM PrestamoEntity AS p WHERE p.idSocio = s.idSocio) "
                            + "ORDER BY s.nombre",
                            SocioEntity.class)
                    .getResultStream()
                    .map(SocioMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("sociosSinPrestamos", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que devuelve los socios con mas prestamos
     * @param limite el numero maximo de socios
     * @return lista de filas con id del socio, nombre y numero de prestamos
     */
    public List<Object[]> rankingSocios(int limite) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT s.idSocio, s.nombre, COUNT(p) FROM PrestamoEntity AS p JOIN p.socio AS s "
                            + "GROUP BY s.idSocio, s.nombre ORDER BY COUNT(p) DESC, s.nombre",
                            Object[].class)
                    .setMaxResults(limite)
                    .getResultList();
        } catch (RuntimeException ex) {
            registrar("rankingSocios", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que devuelve el historial de prestamos de un socio
     * @param idSocio el ID del socio
     * @return lista de filas con id del prestamo, titulo del libro, fecha de prestamo, fecha limite y fecha de devolucion
     */
    public List<Object[]> historialSocio(int idSocio) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT p.idPrestamo, l.titulo, p.fechaPrestamo, p.fechaLimite, p.fechaDevolucion "
                            + "FROM PrestamoEntity AS p JOIN p.libro AS l "
                            + "WHERE p.idSocio = :id ORDER BY p.fechaPrestamo",
                            Object[].class)
                    .setParameter("id", idSocio)
                    .getResultList();
        } catch (RuntimeException ex) {
            registrar("historialSocio", ex);
            return List.of();
        }
    }


    
    @Override public void close() {if(ownFactory && managerFactory.isOpen())managerFactory.close();}
}
