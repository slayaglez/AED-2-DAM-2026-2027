package aula.tareas.controller;

import aula.tareas.service.ITareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController("tareaControllerCsv")
@RequestMapping("/api/csv/tareas")
public class TareaControllerCsv extends AbstractTareaController implements ITareaController{

    public TareaControllerCsv(@Qualifier("tareaServiceCsv") ITareaService servicio) {
        super(servicio);
    }
}
