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

## Aclaración IMPORTANTE

A falta de tiempo esta documentación fue generada con IA, mucha investigación y debugging se hizo también con IA, pero **ni una sola línea de código ha sido generada con IA**

Cosas que no me dieron tiempo y me gustaría haber implementado:
- Mockito, aunque lo estuve mirando no me atreví a implementarlo en los test por miedo a romper lo que ya tenía hecho.
- Una mejor documentación, tuve que recurrir a la IA para documentar esto y me hubiera gustado redactarla yo.
- El Profile y application.properties, me seguía dando errores y como se acercaba la entrega lo sustituí por el método que ya conocemos (DriverManager...)
- Muchos más tests, no recuerdo como verificar la cobertura pero me hubiera gustado crear muchos más tests.

También decir que vi casi al final que se pidieran commits. Por suerte todo lo que hago lo hice en un repo pero como me acostumbré a trabajar en la máquina virtual de mi disco duro externo no he hecho commits a lo largo del tiempo. Espero compensarlo haciendo commits por cada capa demostrando que me manejo bien con git y que entiendo el modelo de capas.

Como siempre, seguiré trabajando en el proyecto después de la entrega.

