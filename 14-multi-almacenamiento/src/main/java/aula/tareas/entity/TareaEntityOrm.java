package aula.tareas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas_orm")
public class TareaEntityOrm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea;

    @Column(nullable = false, length = 100)
    private String nombreTarea;

    public TareaEntityOrm(Long idTarea, String nombreTarea) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }
}
