package aula.tareas.entity;

import jakarta.persistence.*;

//@Entity(name = "tareas_orm")
public class TareaEntityOrm {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Column(nullable = false)
    private String titulo;

    public TareaEntityOrm(long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public TareaEntityOrm() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
