package com.ejemplo.catalogo.repository.file.csv;

import com.ejemplo.catalogo.model.Producto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository implements IRepository{

    private Path path;
    List<Producto> productos = new ArrayList<>();

    public AbstractRepository(Path path) {
        if (this.path == null) {
            throw new RuntimeException("El path es null");
        }
        this.path = this.path;
        if (Files.notExists(this.path)) {
            try {
                Files.createFile(this.path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        productos = load();
    }

    public abstract List<Producto> load();
    public abstract void saveAll(List<Producto> productos);

    @Override
    public Optional<Producto> findById(long id){
        return productos.stream().filter(p -> p.id() == id).findFirst();
    }

    public void create(Producto producto) {
        if (producto == null || producto.id() < 0) throw new IllegalArgumentException();
        if (productos.stream().anyMatch(p -> p.id() == producto.id()))
            throw new IllegalArgumentException("Id duplicado: " + producto.id());
        productos.add(producto);
        saveAll(productos);
    }

    @Override
    public boolean update(Producto producto) {
        if(producto == null || producto.id() < 0){
            return false;
        }

        //! La clase Producto al ser un Record no tiene equals()
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).id() == producto.id()) {
                productos.set(i, producto);
                saveAll(productos);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        boolean removed = productos.removeIf(p -> p.id() == id);
        if (removed) saveAll(productos);
        return removed;
    }

    public Path getPath() {
        return path;
    }

}
