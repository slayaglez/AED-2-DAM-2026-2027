package com.docencia.regex;

/**
 * Servicio para practicar expresiones regulares.
 */
public interface RegexService {

    /**
     * Valida si el DNI tiene 8 numeros y una letra mayuscula.
     * @param dni cadena a validar.
     * @return true si cumple el patron o false si no cumple o es null.
     */
    Boolean validarDni(String dni);

    /**
     * Valida si el email tiene letras minusculas, un arroba y un punto.
     * @param email cadena a validar.
     * @return true si cumple el formato simplificado en minusculas.
     */
    Boolean validarEmail(String email);

    /**
     * Valida si el telefono empieza por 6 y tiene 9 digitos en total.
     * @param telefono cadena a validar.
     * @return true si es un numero movil valido.
     */
    Boolean validarTelefono(String telefono);

    /**
     * Valida si es un nombre simple o compuesto de minimo 2 letras por palabra.
     * @param nombre cadena a validar.
     * @return true si coincide con uno o dos nombres sin caracteres especiales.
     */
    Boolean validarNombre(String nombre);

    /**
     * Valida si el codigo postal consta de exactamente 5 digitos.
     * @param codigoPostal cadena a validar.
     * @return true si contiene 5 numeros.
     */
    Boolean validarCodigoPostal(String codigoPostal);

    /**
     * Valida si la matricula tiene 4 numeros seguidos de 3 letras mayusculas.
     * @param matricula cadena a validar.
     * @return true si cumple el patron de vehiculo europeo.
     */
    Boolean validarMatricula(String matricula);
}
