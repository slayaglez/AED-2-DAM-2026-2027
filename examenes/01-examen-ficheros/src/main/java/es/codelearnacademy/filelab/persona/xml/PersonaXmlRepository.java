package es.codelearnacademy.filelab.persona.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import es.codelearnacademy.filelab.demo.crud.XmlCrudDemo;
import es.codelearnacademy.filelab.model.Persona;
import es.codelearnacademy.filelab.model.Producto;
import es.codelearnacademy.filelab.persona.IPersonaRepository;
import es.codelearnacademy.filelab.repository.AbstractFileRepository;

import java.nio.file.*;
import java.io.*;
import java.util.*;

public class PersonaXmlRepository extends AbstractFileRepository<Persona, String> implements IPersonaRepository {
    private final Path path;
    private final ObjectMapper mapper;

    public PersonaXmlRepository(Path path) {
        this(path, new XmlMapper());
    }

    public PersonaXmlRepository(Path path, XmlMapper mapper) {
        this.path = path;
        this.mapper = mapper;
    }

    @Override
    protected String getId(Persona p) {
        return p.dni();
    }

    @Override
    protected List<Persona> readAll() {

        Path parent = path.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return mapper.readValue(path.toFile(), DocumentoPersonas.class).getPersonas();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeAll(List<Persona> personas) {
        Path parent = path.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), new DocumentoPersonas(personas));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
