package es.codelearnacademy.filelab.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Infraestructura CRUD reutilizable. Proporcionada por el docente. */
public abstract class AbstractFileRepository<T, ID> implements IRepository<T, ID> {

    protected abstract ID getId(T entity);

    // Los errores de E/S solo se declaran en los metodos protegidos internos.
    protected abstract List<T> readAll() ;

    protected abstract void writeAll(List<T> entities);

    @Override
    public List<T> findAll() {
        return readAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return readAll().stream().filter(t -> getId(t).equals(id)).findFirst();
    }

    @Override
    public boolean create(T entity) {
        List<T> all = findAll();
        if(all.contains(entity)){
            return false;
        }

        all.add(entity);
        writeAll(all);
        return true;
    }

    @Override
    public boolean update(T entity) {
        List<T> all = findAll();

        for (int i = 0; i < all.size(); i++) {
            if(getId(entity).equals(getId(all.get(i)))){
                all.set(i, entity);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(ID id) {
        List<T> all = findAll();
        if(all.removeIf(t -> getId(t).equals(id))){
            writeAll(all);
            return true;
        }
        return false;
    }
}
