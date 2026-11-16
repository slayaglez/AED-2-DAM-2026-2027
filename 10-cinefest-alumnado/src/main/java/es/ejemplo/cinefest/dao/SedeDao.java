package es.ejemplo.cinefest.dao;

import es.ejemplo.cinefest.modelo.Sede;
import es.ejemplo.cinefest.orm.config.OrmFactory;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class SedeDao {

    public SedeDao(String jdbcUrl) {  }

    // Usar transacciones y rollback en operaciones de escritura.
    public List<Sede> listar() {
        // TODO: implementar conforme al contrato del README.md.
        return List.of();
    }
    public Optional<Sede> buscarPorId(int id) {
        // TODO: implementar conforme al contrato del README.md.
        return Optional.empty();
    }
    public int insertar(Sede dato) {
        // TODO: implementar conforme al contrato del README.md.
        return 0;
    }
    public int actualizar(Sede dato) {
        // TODO: implementar conforme al contrato del README.md.
        return 0;
    }
    public int eliminar(int id) {
        // TODO: implementar conforme al contrato del README.md.
        return 0;
    }
    public List<Object[]> salasPorSede(int idSede) {
        // TODO: implementar conforme al contrato del README.md.
        return List.of();
    }
    public List<Object[]> aforoTotalPorSede() {
        // TODO: implementar conforme al contrato del README.md.
        return List.of();
    }
    
}
