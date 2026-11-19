package aula.tareas.service;

import java.util.List;

import aula.tareas.api.TareaRequest;
import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tareaServiceCsv")
public class TareaServiceCsv implements ITareaService{
    private final ITareaRepository repository;

    public TareaServiceCsv(@Qualifier("tareaRepositoryCsv") ITareaRepository repository) {
        this.repository = repository;
    }

    public List<TareaRequest> listar() {
        return repository.findAll();
    }

    public TareaRequest buscar(long id) {
        return repository.findById(id);
    }

    public TareaRequest crear(String titulo) {
        if  (titulo == null || titulo.isBlank()) return null;
        return repository.create(titulo);
    }

    public boolean delete(long id) {
        return repository.delete(id);
    }
}
