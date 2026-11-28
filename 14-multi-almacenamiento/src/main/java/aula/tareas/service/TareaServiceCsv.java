package aula.tareas.service;

import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tareaServiceCsv")
public class TareaServiceCsv extends AbstractTareaService{

    public TareaServiceCsv(@Qualifier("tareaRepositoryCsv") ITareaRepository repository) {
        super(repository);
    }

}
