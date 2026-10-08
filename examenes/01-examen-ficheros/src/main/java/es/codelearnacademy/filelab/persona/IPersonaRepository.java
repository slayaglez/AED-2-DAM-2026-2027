package es.codelearnacademy.filelab.persona;
import es.codelearnacademy.filelab.model.Persona;
import es.codelearnacademy.filelab.repository.IRepository;
import java.util.List;

/**
 * Repositorio para consultas en la clase persona
 */
public interface IPersonaRepository extends IRepository<Persona,String> {

    /**
     * Repositorio que consultas o consulta personas o persona que cumplan con al menos la edad minima
     * @param edad edad de las personas
     * @return Lista de personas o sin resultados si no
     */
    default List<Persona> findByEdadMinima(int edad) {
        List<Persona> all = findAll();
        return all.stream().filter(p -> p.edad() >= edad).toList();
    }

    /**
     * Devuelve persona / personas que esten activa en estado repositorio repositorios
     * @param activo boolean true false
     * @return Lista de personas activa o vacio si no
     */
    default List<Persona> findByActivo(boolean activo) {
        List<Persona> all = findAll();
        return all.stream().filter(p -> p.activo() == (activo)).toList();
    }
}
