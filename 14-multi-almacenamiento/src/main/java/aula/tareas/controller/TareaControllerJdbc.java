package aula.tareas.controller;

import aula.tareas.service.ITareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jdbc/tareas")
public class TareaControllerJdbc extends AbstractTareaController implements ITareaController{

    public TareaControllerJdbc(@Qualifier("tareaServiceJdbc") ITareaService servicio) {
        super(servicio);
    }
}
