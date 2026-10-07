package es.codelearnacademy.filelab.csv;

import es.codelearnacademy.filelab.model.Producto;
import es.codelearnacademy.filelab.repository.AbstractFileRepository;
import es.codelearnacademy.filelab.repository.IProductoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductoCsvRepository
        extends AbstractFileRepository<Producto, Long>
        implements IProductoRepository {

    private final Path path;

    public ProductoCsvRepository(Path path) {
        this.path = path;
    }

    @Override
    protected Long getId(Producto producto) {
        return producto.id();
    }

    @Override
    protected List<Producto> readAll() throws IOException {
        List<Producto> resultado = new ArrayList<>();
        CSVFormat formato = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
             CSVParser parser = formato.parse(reader)) {
            for (CSVRecord registro : parser) {
                resultado.add(new Producto(
                        Long.parseLong(registro.get("id")),
                        registro.get("nombre"),
                        Double.parseDouble(registro.get("precio")),
                        Integer.parseInt(registro.get("stock"))));
            }
            return resultado;
        }
    }

    @Override
    protected void writeAll(List<Producto> productos) throws IOException {
        CSVFormat formato = CSVFormat.DEFAULT.builder()
                .setHeader("id", "nombre", "precio", "stock")
                .get();

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, formato)) {
            for (Producto p : productos) printer.printRecord(p.id(), p.nombre(), p.precio(), p.stock());
        }
    }
}
