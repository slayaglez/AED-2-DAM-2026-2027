package aula.tareas.controller;

import aula.tareas.api.TareaRequest;
import aula.tareas.api.TareaResponse;
import aula.tareas.service.ITareaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/xml/tareas")
public class TareaControllerXml extends AbstractTareaController implements ITareaController{

    public TareaControllerXml(@Qualifier("tareaServiceXml") ITareaService servicio) {
        super(servicio);
    }
}
