package com.ejemplo.catalogo.repository.database;

import com.ejemplo.catalogo.repository.file.IRepository;

import java.util.List;

public abstract class AbstractJdbcRepository <T, ID> extends DataBaseInit implements IRepository<T, ID> {
    public AbstractJdbcRepository(String url) {
        super(url);
    }

    public List<Producto> readAll() {

    }
}
