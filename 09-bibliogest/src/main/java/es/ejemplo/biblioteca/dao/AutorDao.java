package es.ejemplo.biblioteca.dao;
import es.ejemplo.biblioteca.modelo.Autor;

import es.ejemplo.biblioteca.orm.entity.AutorEntity;
import es.ejemplo.biblioteca.orm.mapper.AutorMapper;
import es.ejemplo.biblioteca.orm.config.OrmFactory;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase AutorDao encargada de conectar la clase Autor y la BBDD
 */
public class AutorDao implements AutoCloseable {
    private static final Logger LOG=Logger.getLogger(AutorDao.class.getName());
    private final EntityManagerFactory managerFactory;
    private final boolean ownFactory;
    
    public AutorDao(String url) {managerFactory=OrmFactory.crear(url);ownFactory=true;}
    
    public AutorDao(EntityManagerFactory factory) {managerFactory=factory;ownFactory=false;}
    private void registrar(String metodo,Exception ex) {LOG.log(Level.WARNING,metodo+" no se pudo completar: "+ex.getMessage());}

    /**
     * Metodo listar que busca todos los autores de la tabla
     * @return lista de Autor
     */
    public List<Autor> listar() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT a FROM AutorEntity AS a ORDER BY a.nombre",
                            AutorEntity.class)
                    .getResultStream()
                    .map(AutorMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listar", ex);
            return List.of();
        }
    }

    /**
     * Metodo que busca por ID
     * @param id el ID del autor
     * @return Un autor o null si no existe
     */
    public Optional<Autor> buscarPorId(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            return Optional.ofNullable(em.find(AutorEntity.class, id))
                    .map(AutorMapper::toDomain);
        } catch (RuntimeException ex) {
            registrar("buscarPorId", ex);
            return Optional.empty();
        }
    }
    
    /**
     * Metodo que inserta un autor nuevo en la tabla
     * @param dato el autor a insertar
     * @return el ID generado o 0 si no se pudo insertar
     */
    public int insertar(Autor dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                AutorEntity entidad = AutorMapper.toEntity(dato);
                em.persist(entidad);
                em.flush(); // Necesario aqui para leer el ID generado
                dato.setIdAutor(entidad.getIdAutor());
                tx.commit();
                return dato.getIdAutor();
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("insertar", ex);
                return 0;
            }
        }
    }
    
    /**
     * Metodo que actualiza los datos de un autor existente
     * @param dato el autor con los datos nuevos
     * @return 1 si se actualizo o 0 si no existe o hubo un error
     */
    public int actualizar(Autor dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                AutorEntity a = em.find(AutorEntity.class, dato.getIdAutor());
                if (a == null) {
                    tx.commit();
                    return 0;
                }
                AutorEntity autorEntityActualizado = AutorMapper.toEntity(dato);
                a.setNombre(autorEntityActualizado.getNombre());
                a.setAnioNacimiento(autorEntityActualizado.getAnioNacimiento());
                a.setNacionalidad(autorEntityActualizado.getNacionalidad());
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
     * Metodo que elimina un autor por ID
     * @param id el ID del autor
     * @return 1 si se elimino o 0 si no existe o hubo un error
     */
    public int eliminar(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                AutorEntity a = em.find(AutorEntity.class, id);
                if (a == null) {
                    tx.commit();
                    return 0;
                }
                em.remove(a);
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
     * Metodo que comprueba que el autor tiene los datos obligatorios
     * @param dato el autor a comprobar
     * @return true si es valido
     */
    private boolean validar(Autor dato) {return dato != null && dato.getNombre()!=null && !dato.getNombre().isBlank();}
    
    /**
     * Metodo que busca los autores de una nacionalidad
     * @param nacionalidad la nacionalidad a buscar
     * @return lista de Autor
     */
    public List<Autor> buscarPorNacionalidad(String nacionalidad) {
        try (var em = managerFactory.createEntityManager()) {
            return (em.createQuery("SELECT a FROM AutorEntity AS a WHERE a.nacionalidad = :nacionalidad",
                    AutorEntity.class)
                    .setParameter("nacionalidad", nacionalidad)
                    .getResultStream()
                    .map(AutorMapper::toDomain).toList());
        }
    }
    
    public long contarLibrosPorAutor(int idAutor) {
        try (var em = managerFactory.createEntityManager()) {
            return (em.createQuery("SELECT COUNT(l) FROM AutorEntity AS a INNER JOIN LibroEntity AS l ON a.idAutor = l.idAutor WHERE a.idAutor = :id",
                            Long.class)
                    .setParameter("id", idAutor)
                    .getSingleResult());
        } catch (RuntimeException ex) {
            registrar("contarLibrosPorAutor", ex);
            return 0;
        }
    }
    
    /**
     * Metodo que cuenta los libros de cada autor, incluidos los que no tienen libros
     * @return lista de filas con id del autor, nombre y numero de libros
     */
    public List<Object[]> autoresConRecuento() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT a.idAutor, a.nombre, COUNT(l) FROM AutorEntity AS a "
                            + "LEFT JOIN LibroEntity AS l ON l.idAutor = a.idAutor "
                            + "GROUP BY a.idAutor, a.nombre ORDER BY a.nombre",
                            Object[].class)
                    .getResultList();
        } catch (RuntimeException ex) {
            registrar("autoresConRecuento", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca los autores cuyos libros han prestado al menos tres socios distintos
     * @return lista de Autor
     */
    public List<Autor> autoresLeidosPorTresSocios() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT a FROM AutorEntity AS a WHERE a.idAutor IN ("
                            + "SELECT l.idAutor FROM PrestamoEntity AS p JOIN p.libro AS l "
                            + "GROUP BY l.idAutor HAVING COUNT(DISTINCT p.idSocio) >= 3) "
                            + "ORDER BY a.nombre",
                            AutorEntity.class)
                    .getResultStream()
                    .map(AutorMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("autoresLeidosPorTresSocios", ex);
            return List.of();
        }
    }

    @Override public void close() {if(ownFactory && managerFactory.isOpen())managerFactory.close();}
}
