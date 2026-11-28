package aula.tareas.service;

import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;

public class TareaServiceJson extends AbstractTareaService{

    public TareaServiceJson(@Qualifier("tareaRepositoryJson") ITareaRepository repository) {
        super(repository);
    }

}
