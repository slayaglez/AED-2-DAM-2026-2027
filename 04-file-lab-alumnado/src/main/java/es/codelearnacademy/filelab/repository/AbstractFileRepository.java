package es.codelearnacademy.filelab.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractFileRepository<T, ID> implements IRepository<T, ID> {

    @Override
    public List<T> findAll() {
        try {
            return readAll();
        } catch (IOException e) {
            return List.of();
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            return readAll().stream().filter(t -> getId(t).equals(id)).findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean create(T entity) {
        List<T> all = findAll();
        all.add(entity);
        try {
            writeAll(all);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean update(T entity) {
        try {
            List<T> all = readAll();
            for (int i = 0; i < all.size(); i++) {
                if (getId(all.get(i)).equals(getId(entity))) {
                    all.set(i, entity);
                    writeAll(all);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean delete(ID id) {
        List<T> all = findAll();

        try {
            all.removeIf(t -> getId(t).equals(id));
            writeAll(all);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    protected abstract ID getId(T entity);

    protected abstract List<T> readAll() throws IOException;

    protected abstract void writeAll(List<T> entities) throws IOException;
}
