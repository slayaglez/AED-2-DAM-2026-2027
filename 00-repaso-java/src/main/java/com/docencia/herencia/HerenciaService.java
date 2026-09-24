package com.docencia.herencia;

/** Servicio para practicar herencia. */
public interface HerenciaService {

    /**
     * Obtiene el nombre completo de una persona
     * @param persona Persona persona
     * @return nombre y apellidos separados por un espacio
     */
    String obtenerNombreCompleto(Persona persona);

    /**
     * Comprueba si una persona es un alumno
     * @param persona Persona persona
     * @return si es alumno o no
     */
    Boolean esAlumno(Persona persona);

    /**
     * Obtiene la descripcion de una persona
     * @param persona Persona persona
     * @return descripcion de la persona
     */
    String obtenerDescripcionPersona(Persona persona);

    /**
     * Comprueba si una persona tiene al menos una edad
     * @param persona Persona persona
     * @param edadMinima Integer edad minima
     * @return si alcanza la edad minima o no
     */
    Boolean tieneEdadMinima(Persona persona, Integer edadMinima);

    /**
     * Obtiene el tipo de persona segun su clase
     * @param persona Persona persona
     * @return nombre de la clase en mayusculas
     */
    String obtenerTipoPersona(Persona persona);
}