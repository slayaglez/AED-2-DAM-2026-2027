package aula.tareas.service;

import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;

public class TareaServiceJdbc extends AbstractTareaService{

    public TareaServiceJdbc(@Qualifier("tareaRepositoryJdbc") ITareaRepository repository) {
        super(repository);
    }

}
