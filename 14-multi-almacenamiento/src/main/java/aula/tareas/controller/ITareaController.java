package aula.tareas.controller;

import aula.tareas.api.TareaRequest;
import aula.tareas.api.TareaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITareaController {

    @Operation(summary = "Obtener todas las tareas")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "El listado de tareas se obtuvo con éxito")
    )
    public List<TareaResponse> listar();

    @Operation(summary = "Obtener una tarea")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La tarea se obtuvo con éxito"),
            @ApiResponse(responseCode = "400", description = "El id no es valido"),
            @ApiResponse(responseCode = "404", description = "La tarea no se encontró")
    })
    public ResponseEntity<TareaResponse> buscar(long id);

    @Operation(summary = "Crear una tarea")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La tarea se creó con éxito"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la petición no es válido"),
            @ApiResponse(responseCode = "409", description = "Ya existe una tarea con ese código")
    })
    public ResponseEntity<TareaResponse> crear(TareaRequest request);

    @Operation(summary = "Actualiza una tarea")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La tarea se actualizó con éxito"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la petición no es válido"),
            @ApiResponse(responseCode = "404", description = "Ya existe una tarea con ese código")
    })
    public ResponseEntity<TareaResponse> actualizar(long id, TareaRequest request);


    @Operation(summary = "Eliminar una tarea")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La tarea se creó con éxito"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la petición no es válido"),
            @ApiResponse(responseCode = "404", description = "Ya existe una pelicula con ese código")
    })
    public ResponseEntity<Void> eliminar(long id);

}
