package aula.tareas.controller;

import aula.tareas.api.TareaRequest;
import aula.tareas.api.TareaResponse;
import aula.tareas.service.ITareaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/csv/tareas")
public class TareaControllerCsv {
    private final ITareaService servicio;

    public TareaControllerCsv(@Qualifier("tareaServiceCsv") ITareaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las tareas")
    public List<TareaResponse> listar() {
        // TODO: Exponer el listado y convertir las tareas en respuestas.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarea por su id")
    public ResponseEntity<TareaResponse> buscar(@PathVariable long id) {
        // TODO: Exponer la consulta por ID y gestionar la ausencia de la tarea.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    @PostMapping
    @Operation(summary = "Crear una tarea dada dicha tarea")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest request) {
        // TODO: Exponer el alta, validar el cuerpo y componer la respuesta HTTP.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarea dado su id y la nueva tarea")
    public ResponseEntity<TareaResponse> actualizar(@PathVariable long id, @Valid @RequestBody TareaRequest request) {
        // TODO: Exponer la actualización y validar la petición.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarea dado su id")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        // TODO: Exponer el borrado y comunicar el resultado.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}
