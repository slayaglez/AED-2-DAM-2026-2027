package aula.tareas.controller;

import aula.tareas.service.ITareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xml/tareas")
public class TareaControllerXml extends AbstractTareaController implements ITareaController{

    public TareaControllerXml(@Qualifier("tareaServiceXml") ITareaService servicio) {
        super(servicio);
    }
}
