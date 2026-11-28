package aula.tareas.service;

import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tareaServiceOrm")
public class TareaServiceOrm extends AbstractTareaService{

    public TareaServiceOrm(@Qualifier("tareaRepositoryOrm") ITareaRepository repository) {
        super(repository);
    }

}
