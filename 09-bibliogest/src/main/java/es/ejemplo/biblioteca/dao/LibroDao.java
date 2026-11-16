package es.ejemplo.biblioteca.dao;
import es.ejemplo.biblioteca.modelo.Libro;

import es.ejemplo.biblioteca.orm.entity.LibroEntity;
import es.ejemplo.biblioteca.orm.mapper.LibroMapper;
import es.ejemplo.biblioteca.orm.config.OrmFactory;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase LibroDao encargada de conectar la clase Libro y la BBDD
 */
public class LibroDao implements AutoCloseable {
    private static final Logger LOG=Logger.getLogger(LibroDao.class.getName());
    private final EntityManagerFactory managerFactory;
    private final boolean ownFactory;
    
    public LibroDao(String url) {managerFactory=OrmFactory.crear(url);ownFactory=true;}
    
    public LibroDao(EntityManagerFactory factory) {managerFactory=factory;ownFactory=false;}
    private void registrar(String metodo, Exception ex) {LOG.log(Level.WARNING,metodo+" no se pudo completar: "+ex.getMessage());}
    
    /**
     * Metodo listar que busca todos los libros de la tabla
     * @return lista de Libro
     */
    public List<Libro> listar() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery("SELECT l FROM LibroEntity AS l order by l.titulo",
                    LibroEntity.class)
                    .getResultStream()
                    .map(LibroMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("listar", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca por ID
     * @param id el ID del libro
     * @return Un libro o vacio si no existe
     */
    public Optional<Libro> buscarPorId(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            return Optional.ofNullable(em.find(LibroEntity.class, id))
                    .map(LibroMapper::toDomain);
        } catch (RuntimeException ex) {
            registrar("buscarPorId", ex);
            return Optional.empty();
        }
    }
    
    /**
     * Metodo que inserta un libro nuevo en la tabla
     * @param dato el libro a insertar
     * @return el ID generado o 0 si no se pudo insertar
     */
    public int insertar(Libro dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                LibroEntity entidad = LibroMapper.toEntity(dato);
                em.persist(entidad);
                em.flush();
                dato.setIdLibro(entidad.getIdLibro());
                tx.commit();
                return dato.getIdLibro();
            } catch (RuntimeException ex) {
                if (tx.isActive()) tx.rollback();
                registrar("insertar", ex);
                return 0;
            }
        }
    }
    
    /**
     * Metodo que actualiza los datos de un libro existente
     * @param dato el libro con los datos nuevos
     * @return 1 si se actualizo o 0 si no existe o hubo un error
     */
    public int actualizar(Libro dato) {
        if (!validar(dato)) return 0;

        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                LibroEntity l = em.find(LibroEntity.class, dato.getIdLibro());
                if (l == null) {
                    tx.commit();
                    return 0;
                }
                LibroEntity entidad = LibroMapper.toEntity(dato);
                l.setTitulo(entidad.getTitulo());
                l.setAnioPublicacion(entidad.getAnioPublicacion());
                l.setGenero(entidad.getGenero());
                l.setIsbn(entidad.getIsbn());
                l.setEjemplares(entidad.getEjemplares());
                l.setIdAutor(entidad.getIdAutor());
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
     * Metodo que elimina un libro por ID
     * @param id el ID del libro
     * @return 1 si se elimino o 0 si no existe o hubo un error
     */
    public int eliminar(Integer id) {
        try (var em = managerFactory.createEntityManager()) {
            var tx = em.getTransaction();
            try {
                tx.begin();
                LibroEntity a = em.find(LibroEntity.class, id);
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
     * Metodo que comprueba que el libro tiene los datos obligatorios
     * @param dato el libro a comprobar
     * @return true si es valido
     */
    private boolean validar(Libro dato) {return dato != null && dato.getTitulo()!=null && !dato.getTitulo().isBlank() && dato.getIsbn()!=null && !dato.getIsbn().isBlank() && dato.getGenero()!=null && dato.getIdAutor()!=null && dato.getEjemplares()!=null && dato.getEjemplares()>=0;}
    
    /**
     * Metodo que busca los libros de un genero
     * @param genero el genero a buscar
     * @return lista de Libro
     */
    public List<Libro> buscarPorGenero(String genero) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT l FROM LibroEntity AS l WHERE l.genero = :genero ORDER BY l.titulo",
                            LibroEntity.class)
                    .setParameter("genero", genero)
                    .getResultStream()
                    .map(LibroMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("buscarPorGenero", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca los libros de un autor
     * @param idAutor el ID del autor
     * @return lista de Libro
     */
    public List<Libro> buscarPorAutor(int idAutor) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT l FROM LibroEntity AS l WHERE l.idAutor = :id ORDER BY l.titulo",
                            LibroEntity.class)
                    .setParameter("id", idAutor)
                    .getResultStream()
                    .map(LibroMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("buscarPorAutor", ex);
            return List.of();
        }
    }
    
    /**
     * Metodo que busca los libros cuyo titulo contiene un texto
     * @param texto el texto a buscar
     * @return lista de Libro
     */
    public List<Libro> buscarPorTitulo(String texto) {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT l FROM LibroEntity AS l WHERE LOWER(l.titulo) LIKE LOWER(:texto) ORDER BY l.titulo",
                            LibroEntity.class)
                    .setParameter("texto", "%" + texto + "%")
                    .getResultStream()
                    .map(LibroMapper::toDomain)
                    .toList();
        } catch (RuntimeException ex) {
            registrar("buscarPorTitulo", ex);
            return List.of();
        }
    }
    
    public List<Libro> listarDisponibles() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Libro> librosDesdeAnio(int anio, String genero) {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    /**
     * Metodo que devuelve los generos distintos de los libros
     * @return lista de generos sin repetir
     */
    public List<String> generos() {
        try (var em = managerFactory.createEntityManager()) {
            return em.createQuery(
                            "SELECT DISTINCT l.genero FROM LibroEntity AS l ORDER BY l.genero",
                            String.class)
                    .getResultList();
        } catch (RuntimeException ex) {
            registrar("generos", ex);
            return List.of();
        }
    }
    
    public List<Libro> nuncaPrestados() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Libro> masPrestadosQueMedia() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Libro> reservadosSinDisponibilidad() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> librosConAutor() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> contarPorGenero() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> prestamosPorGeneroMinimo(long minimo) {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> disponibilidadPorLibro() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }
    
    public List<Object[]> estadisticasGenero() {
        // TODO: implementar con EntityManager, JPQL y rollback cuando proceda.
        return List.of();
    }


    
    @Override public void close() {if(ownFactory && managerFactory.isOpen())managerFactory.close();}
}
