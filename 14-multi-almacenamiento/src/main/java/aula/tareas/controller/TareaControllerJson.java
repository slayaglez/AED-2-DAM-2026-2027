package aula.tareas.controller;

import aula.tareas.service.ITareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/json/tareas")
public class TareaControllerJson extends AbstractTareaController implements ITareaController{

    public TareaControllerJson(@Qualifier("tareaServiceJson") ITareaService servicio) {
        super(servicio);
    }
}
