package ies.puesto.peliculas.controller;

import ies.puesto.peliculas.controller.api.PeliculaEdicionRequest;
import ies.puesto.peliculas.controller.api.PeliculaRequest;
import ies.puesto.peliculas.controller.api.PeliculaResponse;
import ies.puesto.peliculas.controller.mapper.PeliculaApiMapper;
import ies.puesto.peliculas.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/jdbc/peliculas")
@Tag(name = "Peliculas JDBC", description = "Operaciones CRUD de peliculas en JSON")
public class PeliculaJdbcController {

    private final PeliculaService servicio;
    private final PeliculaApiMapper apiMapper;

    public PeliculaJdbcController(PeliculaService servicio, PeliculaApiMapper apiMapper) {
        this.servicio = servicio;
        this.apiMapper = apiMapper;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las peliculas desde una BBDD")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "El listado de peliculas se obtuvo con éxito")
    )
    public List<PeliculaResponse> listar() {
        return apiMapper.aRespuestas(servicio.listar());
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Obtener la pelicula por su codigo desde una BBDD")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La pelicula se obtuvo con éxito"),
            @ApiResponse(responseCode = "400", description = "El codigo no es valido"),
            @ApiResponse(responseCode = "404", description = "La pelicula no se encontró")
    })
    public ResponseEntity<?> obtener(@PathVariable @Pattern(regexp = "^PEL[0-9]{3}$") String codigo) {
        return servicio.obtener(codigo)
                .<ResponseEntity<?>>map(pelicula -> ResponseEntity.ok(apiMapper.aRespuesta(pelicula)))
                .orElseGet(() -> noEncontrada(codigo));
    }

    @PostMapping
    @Operation(summary = "Crear una pelicula en una BBDD")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "La pelicula se creó con éxito"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la peticion no es valido"),
            @ApiResponse(responseCode = "409", description = "Ya existe una pelicula con ese codigo")
    })
    public ResponseEntity<?> crear(@Valid @RequestBody PeliculaRequest peticion) {
        return servicio.crear(apiMapper.aDatosCreacion(peticion))
                .<ResponseEntity<?>>map(pelicula -> ResponseEntity
                        .created(URI.create("/api/v1/jdbc/peliculas/" + pelicula.getIdentificador()))
                        .body(apiMapper.aRespuesta(pelicula)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                                "Ya existe una pelicula con codigo " + peticion.codigo())));
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Actualizar una pelicula en una BBDD")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "La pelicula se actualizó con éxito"),
            @ApiResponse(responseCode = "400", description = "El codigo o el cuerpo no son validos"),
            @ApiResponse(responseCode = "404", description = "La pelicula no se encontró")
    })
    public ResponseEntity<?> actualizar(@PathVariable @Pattern(regexp = "^PEL[0-9]{3}$") String codigo,
                                        @Valid @RequestBody PeliculaEdicionRequest peticion) {
        return servicio.actualizar(codigo, apiMapper.aDatosEdicion(peticion))
                .<ResponseEntity<?>>map(pelicula -> ResponseEntity.ok(apiMapper.aRespuesta(pelicula)))
                .orElseGet(() -> noEncontrada(codigo));
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar una pelicula de una BBDD")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "La pelicula se eliminó con éxito"),
            @ApiResponse(responseCode = "400", description = "El codigo no es valido"),
            @ApiResponse(responseCode = "404", description = "La pelicula no se encontró")
    })
    public ResponseEntity<?> eliminar(@PathVariable @Pattern(regexp = "^PEL[0-9]{3}$") String codigo) {
        if (servicio.eliminar(codigo)) {
            return ResponseEntity.noContent().build();
        }
        return noEncontrada(codigo);
    }

    private ResponseEntity<?> noEncontrada(String codigo) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        "No existe la pelicula con codigo " + codigo));
    }
}
