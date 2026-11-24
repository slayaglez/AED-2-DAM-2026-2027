package aula.tareas.mapper;

import aula.tareas.api.TareaRequest;
import aula.tareas.entity.TareaEntity;
import aula.tareas.model.Tarea;

public class TareaMapperJdbc {


    static public Tarea toDomain(TareaRequest tareaRequest){
        return new Tarea(tareaRequest.getId(), tareaRequest.getTitulo());
    }

    static public TareaRequest toDomain(TareaEntity tareaEntity) {
        return new TareaRequest(tareaEntity.getId(), tareaEntity.getTitulo());
    }

    static public TareaEntity toEntity(TareaRequest tareaRequest) {
        return new  TareaEntity(tareaRequest.getId(), tareaRequest.getTitulo());
    }


}
