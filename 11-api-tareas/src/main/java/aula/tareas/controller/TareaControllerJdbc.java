package aula.tareas.controller;


import aula.tareas.api.TareaRequest;
import aula.tareas.service.ITareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/jdbc/tareas")
@Tag(name="Tareas JDBC", description = "Operaciones CRUD de tareas en JSON")
public class TareaControllerJdbc {

    private final ITareaService servicio;

    public TareaControllerJdbc(@Qualifier("tareaServiceJdbc") ITareaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las tareas desde una BBDD")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "El listado de tareas se obtuvo con éxito")
    )
    List<TareaRequest> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener la tarea por su id desde una BBDD")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La tarea se obtuvo con éxito"),
            @ApiResponse(responseCode = "404", description = "La tarea no se encontró")
    })
    ResponseEntity<TareaRequest> buscar(@PathVariable long id) {
        return ResponseEntity.ofNullable(servicio.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Crear una tarea desde una BBDD")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La tarea se creó con éxito"),
            @ApiResponse(responseCode = "400", description = "La tarea ya existe")
    })
    ResponseEntity<TareaRequest> crear(@Valid @RequestBody TareaControllerCsv.NuevaTarea peticion) {
        TareaRequest tareaRequest = servicio.crear(peticion.titulo());
        return ResponseEntity.created(URI.create("/api/json/tareas/" + tareaRequest.getId())).body(tareaRequest);
    }

    @PutMapping("/{id}")
    ResponseEntity<TareaRequest> update (@PathVariable long id, @Valid @RequestBody TareaControllerCsv.NuevaTarea peticion) {
        return null;
    }

}
