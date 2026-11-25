package aula.tareas.service;

import aula.tareas.api.TareaRequest;
import aula.tareas.modelo.Tarea;
import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class TareaServiceXml extends AbstractTareaService{

    public TareaServiceXml(@Qualifier("tareaRepositoryXml") ITareaRepository repository) {
        super(repository);
    }
}
