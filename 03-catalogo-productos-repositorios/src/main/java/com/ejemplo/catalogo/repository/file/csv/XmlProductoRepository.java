package com.ejemplo.catalogo.repository.file.csv;

import com.ejemplo.catalogo.model.Producto;
import com.ejemplo.catalogo.model.ProductosDocument;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

public class XmlProductoRepository extends AbstractFileRepository<Producto, Long> implements IProductoRepository {
    private final XmlMapper mapper;


    public XmlProductoRepository(Path path) {
        super(path);
        list = readAll();
        mapper = new XmlMapper();
    }

    @Override
    public void writeAll(List<Producto> productos) {
        try {
            ProductosDocument productosDocument = new ProductosDocument();
            productosDocument.setProductos(productos);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(getPath().toFile(), productosDocument);

        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo guardar " + getPath(), e);
        } finally {
        }
    }

    @Override
    public List<Producto> readAll() {

        try {
            ProductosDocument productosDocument = mapper.readValue(getPath().toFile(), ProductosDocument.class);
            list.clear();
            list.addAll(productosDocument.getProductos());
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo cargar " + getPath(), e);
        }
        return list;
    }
}
