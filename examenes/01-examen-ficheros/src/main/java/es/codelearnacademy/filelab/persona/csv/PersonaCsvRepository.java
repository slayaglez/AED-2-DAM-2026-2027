package es.codelearnacademy.filelab.persona.csv;

import es.codelearnacademy.filelab.model.Persona;
import es.codelearnacademy.filelab.model.Producto;
import es.codelearnacademy.filelab.persona.IPersonaRepository;
import es.codelearnacademy.filelab.repository.AbstractFileRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class PersonaCsvRepository extends AbstractFileRepository<Persona, String> implements IPersonaRepository {
    private final Path path;

    private final CSVFormat inputFormat = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build();
    private final CSVFormat outputFormat = CSVFormat.DEFAULT.builder()
            .setHeader("dni", "nombre", "email", "edad", "activo")
            .build();

    public PersonaCsvRepository(Path path) {
        this.path = path;
    }

    @Override
    protected String getId(Persona p) {
        return p.dni();
    }

    @Override
    protected List<Persona> readAll() {
        if (Files.notExists(path)) return new ArrayList<>();
        List<Persona> result = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
             CSVParser parser = inputFormat.parse(reader)) {
            for (CSVRecord row : parser) {
                result.add(new Persona(
                        (row.get("dni")),
                        row.get("nombre"),
                        row.get("email"),
                        Integer.parseInt(row.get("edad")),
                        Boolean.getBoolean(row.get("activo"))));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
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

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, outputFormat)) {
            for (Persona p : personas) printer.printRecord(p.dni(), p.nombre(), p.email(), p.edad(), p.activo());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
