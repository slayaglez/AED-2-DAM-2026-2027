package aula.tareas.service;

import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;

public class TareaServiceXml extends AbstractTareaService{

    public TareaServiceXml(@Qualifier("tareaRepositoryXml") ITareaRepository repository) {
        super(repository);
    }
}
