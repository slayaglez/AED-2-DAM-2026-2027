package aula.tareas.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

public record TareaEntityJson(@JsonAlias("id") Long idTarea, @JsonAlias("titulo") String nombreTarea) {
}
