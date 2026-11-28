package aula.tareas.controller;

import aula.tareas.api.TareaRequest;
import aula.tareas.api.TareaResponse;
import aula.tareas.mapper.TareaMapperApi;
import aula.tareas.service.ITareaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/csv/tareas")
public class TareaControllerCsv extends AbstractTareaController implements ITareaController{

    public TareaControllerCsv(@Qualifier("tareaServiceCsv") ITareaService servicio) {
        super(servicio);
    }
}
