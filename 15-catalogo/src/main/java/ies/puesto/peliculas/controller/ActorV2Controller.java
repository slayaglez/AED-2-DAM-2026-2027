package ies.puesto.peliculas.controller;


import ies.puesto.peliculas.controller.api.ActorEdicionRequest;
import ies.puesto.peliculas.controller.api.ActorRequest;
import ies.puesto.peliculas.controller.api.ActorResponse;
import ies.puesto.peliculas.mapper.ActorMapper;
import ies.puesto.peliculas.model.Actor;
import ies.puesto.peliculas.service.ActorService;
import ies.puesto.peliculas.service.IActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@Tag(name="Actores - V2")
@RestController
@RequestMapping("api/v2/actores")
public class ActorV2Controller {

    private final ActorService servicio;

    public ActorV2Controller(ActorService servicio) {
        this.servicio = servicio;
    }

    @Operation(summary = "Editar actor")
    @ApiResponse(responseCode = "201", description = "Edición completada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Edición inválida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object")))
    @ApiResponse(responseCode = "404", description = "Recurso inexistente", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class)))
    @PutMapping("/{idActor}")
    public ActorResponse editar(@PathVariable @Pattern(regexp = "^PAS[0-9]{6}$") String idActor,
                                @RequestBody @Valid ActorEdicionRequest actorRequest) {
        Actor actor = servicio.editar(idActor, ActorMapper.toDomain(actorRequest));
        return ActorMapper.toApi(actor);
    }

    @Operation(summary = "Eliminar actor")
    @ApiResponse(responseCode = "204", description = "Eliminación completada", content = @Content)
    @ApiResponse(responseCode = "400", description = "Eliminación inválida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object")))
    @ApiResponse(responseCode = "404", description = "Recurso inexistente", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class)))
    @DeleteMapping("/{idActor}")
    public void eliminar(@PathVariable @Pattern(regexp = "^PAS[0-9]{6}$") String idActor){
        servicio.eliminar(idActor);
    }
}
