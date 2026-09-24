package com.docencia.herencia;

public class Alumno extends Persona {
    private final String expediente;

    public Alumno(String nombre, String apellidos, Integer edad, String expediente) {
        super(nombre, apellidos, edad);
        this.expediente = expediente;
    }

    public String getExpediente() { return expediente; }

    @Override
    public String toString() {
        return "ALUMNO: " + super.toString();
    }
}
