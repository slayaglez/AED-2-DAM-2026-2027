package es.codelearnacademy.filelab.validation;

import es.codelearnacademy.filelab.model.Producto;

public final class ProductoValidator {

    private ProductoValidator() {
    }

    public static void validar(Producto producto) {
        if(producto == null){throw new IllegalArgumentException("Producto inválido");}
        if(producto.id() <= 0){throw new IllegalArgumentException("Producto inválido");}
        if(producto.nombre() == null){throw new IllegalArgumentException("Producto inválido");}
        if(producto.nombre().isBlank()){throw new IllegalArgumentException("Producto inválido");}
        if(producto.precio() < 0){throw new IllegalArgumentException("Producto inválido");}
        if(producto.stock() < 0){throw new IllegalArgumentException("Producto inválido");}
    }
}
