package aula.tareas.controller;

import aula.tareas.service.ITareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orm/tareas")
public class TareaControllerOrm extends AbstractTareaController implements ITareaController{

    public TareaControllerOrm(@Qualifier("tareaServiceOrm") ITareaService servicio) {
        super(servicio);
    }
}
