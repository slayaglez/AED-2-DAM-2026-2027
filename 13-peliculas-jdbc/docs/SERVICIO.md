# Recorrido de las peticiones

## POST `/api/v1/jdbc/peliculas`

### 1. Clases por las que pasa

Petición: `PeliculaJdbcController` → `PeliculaService` → `PeliculaJdbcRepository` → tabla `peliculas`.

Respuesta: la misma `Pelicula` vuelve del servicio al controlador, que la convierte en `PeliculaResponse`.

### 2. Mapper en cada frontera

| Frontera | Mapper | Conversión |
|---|---|---|
| Controlador → Servicio | `PeliculaApiMapper` | `PeliculaRequest` → `DatosCreacionPelicula` |
| Servicio → Dominio | `PeliculaDominioMapper` | `DatosCreacionPelicula` → `Pelicula` |
| Repositorio → Fila | `PeliculaEntityMapper` | `Pelicula` → `PeliculaEntity` |
| Servicio → Respuesta | `PeliculaApiMapper` | `Pelicula` → `PeliculaResponse` |

### 3. Nombre de cada dato

| JSON | Servicio | Dominio | Entidad | Columna |
|---|---|---|---|---|
| `codigo` | `referenciaSolicitada` | `identificador` | `codigoFila` | `pelicula_codigo` |
| `titulo` | `nombreSolicitado` | `nombre` | `tituloFila` | `titulo_texto` |
| `anioEstreno` | `anioSolicitado` | `anioLanzamiento` | `anioFila` | `estreno_anio` |
| `duracionMinutos` | `minutosSolicitados` | `minutosProyeccion` | `minutosFila` | `duracion_minutos` |

### 4. Código HTTP

- `400`: Spring rechaza el cuerpo si no cumple las validaciones de `PeliculaRequest`.
- `201`: `insertarSiAusente` devuelve `true`, el servicio devuelve un `Optional` con la película y el controlador responde `created`.
- `409`: el servicio devuelve un `Optional` vacío y el controlador responde con un `ProblemDetail`.

## GET `/api/v1/jdbc/peliculas/{codigo}`

### 1. Clases por las que pasa

Petición: `PeliculaJdbcController` → `PeliculaService` → `PeliculaJdbcRepository` → tabla `peliculas`.

Respuesta: `ResultSet` → `PeliculaRowMapper` → `PeliculaEntityMapper` → `PeliculaService` → `PeliculaJdbcController`.

### 2. Mapper en cada frontera

| Frontera | Mapper | Conversión |
|---|---|---|
| Fila → Entidad | `PeliculaRowMapper` | `ResultSet` → `PeliculaEntity` |
| Entidad → Dominio | `PeliculaEntityMapper` | `PeliculaEntity` → `Pelicula` |
| Dominio → Respuesta | `PeliculaApiMapper` | `Pelicula` → `PeliculaResponse` |

El código llega como `String` por la URL y no pasa por ningún mapper.

### 3. Nombre de cada dato

Los nombres son los mismos que en el POST, en sentido inverso: de la columna `pelicula_codigo` a `codigoFila`, `identificador` y, por último, `codigo` en el JSON.

### 4. Código HTTP

- `400`: el código de la URL no cumple `^PEL[0-9]{3}$`.
- `200`: el repositorio encuentra la fila y el servicio devuelve un `Optional` con la película.
- `404`: no hay fila, el `Optional` está vacío y el controlador responde con un `ProblemDetail`.

