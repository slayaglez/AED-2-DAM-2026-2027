package es.codelearnacademy.filelab.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.codelearnacademy.filelab.model.Producto;
import es.codelearnacademy.filelab.repository.AbstractFileRepository;
import es.codelearnacademy.filelab.repository.IProductoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductoJsonRepository
        extends AbstractFileRepository<Producto, Long>
        implements IProductoRepository {

    private final Path path;
    private final ObjectMapper mapper;

    public ProductoJsonRepository(Path path) {
        this(path, new ObjectMapper());
    }

    public ProductoJsonRepository(Path path, ObjectMapper mapper) {
        this.path = path;
        this.mapper = mapper;
    }

    @Override
    protected Long getId(Producto producto) {
        return producto.id();
    }

    @Override
    protected List<Producto> readAll() throws IOException {
        File archivo = path.toFile();
        if (!archivo.exists()){
            return new ArrayList<>();
        }

        return mapper.readValue(archivo, new TypeReference<List<Producto>>() {});
    }

    @Override
    protected void writeAll(List<Producto> productos) throws IOException {
        File archivo = path.toFile();
        if (productos == null){
            throw new IllegalArgumentException();
        }

        mapper.writeValue(archivo, productos);
    }
}
