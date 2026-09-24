package com.docencia.numeros;

/** Servicio para practicar operaciones de Math. */
public interface MathService {

    /**
     * Calcula el area de un circulo
     * @param radio Double radio del circulo
     * @return area del circulo
     */
    Double calcularAreaCirculo(Double radio);

    /**
     * Calcula la potencia de un numero
     * @param base Integer base
     * @param exponente Integer exponente
     * @return base elevada al exponente
     */
    Integer calcularPotencia(Integer base, Integer exponente);

    /**
     * Redondea un numero hacia arriba
     * @param numero Double numero
     * @return numero redondeado hacia arriba
     */
    Integer redondearHaciaArriba(Double numero);

    /**
     * Calcula la raiz cuadrada de un numero
     * @param numero Double numero
     * @return raiz cuadrada del numero
     */
    Double calcularRaizCuadrada(Double numero);

    /**
     * Obtiene el valor absoluto de un numero
     * @param numero Integer numero
     * @return valor absoluto del numero
     */
    Integer obtenerValorAbsoluto(Integer numero);
}