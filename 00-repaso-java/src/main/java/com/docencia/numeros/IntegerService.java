package com.docencia.numeros;

/** Servicio para practicar operaciones con enteros. */
public interface IntegerService {

    /**
     * Comprueba si un numero es par
     * @param numero Integer numero
     * @return si es par o no
     */
    Boolean esPar(Integer numero);

    /**
     * Suma los digitos de un numero
     * @param numero Integer numero
     * @return suma de sus digitos
     */
    Integer sumarDigitos(Integer numero);

    /**
     * Convierte un texto a numero entero
     * @param texto String texto a convertir
     * @return numero entero
     */
    Integer convertirTextoAEntero(String texto);

    /**
     * Comprueba si un numero es primo
     * @param numero Integer numero
     * @return si es primo o no
     */
    Boolean esNumeroPrimo(Integer numero);

    /**
     * Calcula el factorial de un numero
     * @param numero Integer numero
     * @return factorial del numero
     */
    Integer calcularFactorial(Integer numero);
}