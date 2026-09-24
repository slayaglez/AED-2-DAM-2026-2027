package com.docencia.colecciones;

import java.util.List;
import java.util.Set;

/** Servicio para practicar Set. */
public interface SetService {

    /**
     * Obtiene los elementos sin repetir
     * @param elementos List lista de elementos
     * @return conjunto con los elementos unicos
     */
    Set<String> obtenerElementosUnicos(List<String> elementos);

    /**
     * Comprueba si un conjunto contiene un valor
     * @param elementos Set conjunto donde buscar
     * @param valor String valor a buscar
     * @return si el valor esta en el conjunto o no
     * @throws IllegalArgumentException si el valor es null
     */
    Boolean contieneElemento(Set<String> elementos, String valor);

    /**
     * Une dos conjuntos
     * @param primero Set primer conjunto
     * @param segundo Set segundo conjunto
     * @return conjunto con los elementos de ambos
     */
    Set<String> unirConjuntos(Set<String> primero, Set<String> segundo);

    /**
     * Obtiene los elementos comunes a dos conjuntos
     * @param primero Set primer conjunto
     * @param segundo Set segundo conjunto
     * @return conjunto con los elementos que estan en ambos
     */
    Set<String> intersectarConjuntos(Set<String> primero, Set<String> segundo);

    /**
     * Obtiene los elementos del primer conjunto que no estan en el segundo
     * @param primero Set primer conjunto
     * @param segundo Set segundo conjunto
     * @return conjunto con la diferencia
     */
    Set<String> restarConjuntos(Set<String> primero, Set<String> segundo);
}