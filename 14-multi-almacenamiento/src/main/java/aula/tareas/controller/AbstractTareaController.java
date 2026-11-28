package aula.tareas.controller;

import aula.tareas.api.TareaRequest;
import aula.tareas.api.TareaResponse;
import aula.tareas.mapper.TareaMapperApi;
import aula.tareas.modelo.Tarea;
import aula.tareas.service.ITareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractTareaController implements ITareaController{

    private final ITareaService servicio;

    protected AbstractTareaController(ITareaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<TareaResponse> listar() {
        return servicio.listar().stream().map(TareaMapperApi::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> buscar(@PathVariable long id) {
        return servicio.buscar(id)
                .map(tarea -> ResponseEntity.ok(TareaMapperApi.toResponse(tarea)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest request) {
        Tarea creada = servicio.crear(request);
        URI location = URI.create("/tareas/" + creada.identificador());
        return ResponseEntity.created(location).body(TareaMapperApi.toResponse(creada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponse> actualizar(@PathVariable long id, @Valid @RequestBody TareaRequest request) {
        return servicio.actualizar(id, request)
                .map(tarea -> ResponseEntity.ok(TareaMapperApi.toResponse(tarea)))
                .orElseThrow(() -> new NoSuchElementException("Tarea " + id + " no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        if (!servicio.eliminar(id)) {
            throw new NoSuchElementException("Tarea " + id + " no encontrada");
        }
        return ResponseEntity.noContent().build();
    }
}
