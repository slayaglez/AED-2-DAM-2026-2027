package aula.tareas.repository;

import aula.tareas.entity.TareaEntityXml;
import aula.tareas.mapper.TareaMapperXml;
import aula.tareas.modelo.Tarea;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("tareaRepositoryXml")
public class TareaRepositoryXml extends AbstractTareaFichero {
    private final Path path;
    private final XmlMapper mapper = XmlMapper.builder()
            .defaultUseWrapper(false)
            .build();

    public TareaRepositoryXml() {
        this(Path.of("data", "tarea.xml"));
    }

    public TareaRepositoryXml(Path path) {
        this.path = path;
    }

    @JsonRootName("tareas")
    public static class DocumentoXml {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "tarea")
        private List<TareaEntityXml> tareas;

        public DocumentoXml() {}

        public DocumentoXml(List<TareaEntityXml> tareas) {
            this.tareas = tareas;
        }

        public List<TareaEntityXml> getTareas() {
            return tareas;
        }

        public void setTareas(List<TareaEntityXml> tareas) {
            this.tareas = tareas;
        }
    }

    @Override
    List<Tarea> readAll() {
        try {
            if (Files.notExists(path) || Files.size(path) == 0) {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DocumentoXml data = mapper.readValue(path.toFile(), DocumentoXml.class);
        List<Tarea> tareas = data.tareas.stream().map(TareaMapperXml::toDomain).toList();
        return new ArrayList<>(tareas);
    }

    @Override
    void writeAll(List<Tarea> tareas) {
        Path parent = path.getParent();
        List<TareaEntityXml> tareasXml = tareas.stream().map(TareaMapperXml::toEntity).toList();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(path.toFile(), new DocumentoXml(tareasXml));
    }
}
