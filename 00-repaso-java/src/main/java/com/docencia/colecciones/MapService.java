package com.docencia.colecciones;

import java.util.List;
import java.util.Map;

/** Servicio para practicar Map. */
public interface MapService {

    /**
     * Cuenta cuantas veces aparece cada palabra
     * @param palabras List lista de palabras
     * @return mapa con cada palabra y su numero de apariciones
     */
    Map<String, Integer> contarFrecuenciaPalabras(List<String> palabras);

    /**
     * Obtiene el valor asociado a una clave
     * @param mapa Map mapa donde buscar
     * @param clave String clave a buscar
     * @return valor de la clave, o 0 si no existe
     */
    Integer obtenerValorPorClave(Map<String, Integer> mapa, String clave);

    /**
     * Calcula la media de los numeros de cada categoria
     * @param datos Map categorias con su lista de numeros
     * @return mapa con cada categoria y su media
     */
    Map<String, Double> calcularMediaPorCategoria(Map<String, List<Integer>> datos);

    /**
     * Obtiene la clave con el valor mas alto
     * @param mapa Map mapa donde buscar
     * @return clave con el mayor valor
     */
    String obtenerClaveConMayorValor(Map<String, Integer> mapa);

    /**
     * Filtra las entradas con valor mayor o igual al minimo
     * @param mapa Map mapa a filtrar
     * @param minimo Integer valor minimo
     * @return mapa con las entradas que cumplen el minimo
     */
    Map<String, Integer> filtrarPorValorMinimo(Map<String, Integer> mapa, Integer minimo);
}