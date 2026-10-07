package es.codelearnacademy.filelab.service;

import es.codelearnacademy.filelab.model.Producto;
import es.codelearnacademy.filelab.repository.IProductoRepository;

import java.awt.color.ProfileDataException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProductoService {

    private final IProductoRepository repository;

    public ProductoService(IProductoRepository repository) {
        this.repository = repository;
    }

    public Optional<Producto> maximoPrecio() {

        return repository.findAll().stream().max(Comparator.comparing(Producto::precio));
    }

    public Optional<Producto> minimoPrecio() {

        return repository.findAll().stream().min(Comparator.comparing(Producto::precio));
    }

    public Optional<Producto> maximoStock() {

        return repository.findAll().stream().max(Comparator.comparing(Producto::stock));
    }

    public Optional<Producto> minimoStock() {

        return repository.findAll().stream().min(Comparator.comparing(Producto::stock));
    }

    public int stockTotal() {

        return repository.findAll().stream().mapToInt(Producto::stock).sum();
    }

    public double valorInventario() {

        return repository.findAll().stream().mapToDouble(p -> p.precio() * p.stock()).sum();
    }

    public List<Producto> sinStock() {

        return repository.findAll().stream().filter(p -> p.stock() == 0).toList();
    }

    public List<Producto> buscar(String texto) {

        String textoNormalizado = texto.toLowerCase().trim();
        String regex = ".*"+textoNormalizado+".*";
        return repository.findAll().stream().filter(p -> p.nombre().toLowerCase().matches(regex)).toList();
    }
}
