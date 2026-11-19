package aula.tareas.service;

import aula.tareas.api.TareaRequest;
import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tareaServiceJson")
public class TareaServiceJson implements ITareaService {
    private final ITareaRepository repository;

    public TareaServiceJson(@Qualifier("tareaRepositoryJson") ITareaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TareaRequest> listar() {
        return repository.findAll();
    }

    @Override
    public TareaRequest buscar(long id) {
        return repository.findById(id);
    }

    @Override
    public TareaRequest crear(String titulo) {
        if  (titulo == null || titulo.isBlank()) return null;
        return repository.create(titulo);
    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }
}
