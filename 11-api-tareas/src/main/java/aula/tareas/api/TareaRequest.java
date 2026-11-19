package aula.tareas.api;

public class TareaRequest {

    private long id;
    private String titulo;

    public TareaRequest(long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public TareaRequest() {
    }

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
