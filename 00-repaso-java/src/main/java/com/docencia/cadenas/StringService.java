package com.docencia.cadenas;

/** Servicio para practicar operaciones con cadenas. */
public interface StringService {

    /**
     * Normaliza un texto a minusculas y sin espacios en los extremos
     * @param texto String texto
     * @return texto normalizado
     */
    String normalizarTexto(String texto);

    /**
     * Comprueba si un texto es palindromo ignorando espacios
     * @param texto String texto
     * @return si es palindromo o no
     */
    Boolean esPalindromo(String texto);

    /**
     * Cuenta las vocales de un texto
     * @param texto String texto
     * @return numero de vocales
     */
    Integer contarVocales(String texto);

    /**
     * Extrae las iniciales de un nombre completo
     * @param nombreCompleto String nombre completo
     * @return iniciales de cada palabra
     */
    String extraerIniciales(String nombreCompleto);

    /**
     * Invierte un texto
     * @param texto String texto
     * @return texto invertido
     */
    String invertirTexto(String texto);

    /**
     * Comprueba si un texto contiene solo letras
     * @param texto String texto
     * @return si contiene solo letras o no
     */
    Boolean contieneSoloLetras(String texto);
}