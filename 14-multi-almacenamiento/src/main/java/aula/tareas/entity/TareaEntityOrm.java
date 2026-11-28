package aula.tareas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas_orm")
public class TareaEntityOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long idTarea;

    @Column(nullable = false, length = 100)
    private final String nombreTarea;

    public TareaEntityOrm(Long idTarea, String nombreTarea) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

}
