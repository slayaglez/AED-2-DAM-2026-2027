package aula.tareas.service;

import aula.tareas.api.TareaRequest;
import aula.tareas.modelo.Tarea;
import aula.tareas.repository.ITareaRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public class TareaServiceJson extends AbstractTareaService{

    public TareaServiceJson(@Qualifier("tareaRepositoryJson") ITareaRepository repository) {
        super(repository);
    }

}
