package es.codelearnacademy.filelab.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public record Producto(long id, String nombre, double precio, int stock) {
}
