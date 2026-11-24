package aula.tareas.repository;

import aula.tareas.api.TareaRequest;
import aula.tareas.entity.TareaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tareaRepositoryOrm")
public class TareaRepositoryOrm implements ITareaRepository {

    private final TareaRepositoryJpa repository;

    public TareaRepositoryOrm(TareaRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public TareaRequest findById(long id) {
        return null;
    }

    @Override
    public List<TareaRequest> findAll() {
        return List.of();
    }

    @Override
    public TareaRequest create(String titulo) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public TareaRequest update(TareaRequest tareaRequest) {
        return null;
    }
}
