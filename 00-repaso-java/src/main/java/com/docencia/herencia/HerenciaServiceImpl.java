package com.docencia.herencia;

public class HerenciaServiceImpl implements HerenciaService {

    @Override
    public String obtenerNombreCompleto(Persona persona) {
        if (persona == null) throw new IllegalArgumentException();
        return persona.getNombre()+" "+persona.getApellidos();
    }

    @Override
    public Boolean esAlumno(Persona persona) {
        return persona instanceof Alumno;
    }

    @Override
    public String obtenerDescripcionPersona(Persona persona) {
        return persona.toString();
    }

    @Override
    public Boolean tieneEdadMinima(Persona persona, Integer edadMinima) {
        return (persona.getEdad() >= edadMinima);
    }

    @Override
    public String obtenerTipoPersona(Persona persona) {
        return  persona.getClass().getSimpleName().toUpperCase();
    }
    
}
