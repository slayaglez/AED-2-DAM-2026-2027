package aula.tareas.entity;

public class TareaEntity {

    private long id;
    private String titulo;



    public TareaEntity(long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public TareaEntity() { }

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
