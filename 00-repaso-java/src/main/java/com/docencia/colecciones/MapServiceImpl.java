package com.docencia.colecciones;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapServiceImpl implements MapService {

    @Override
    public Map<String, Integer> contarFrecuenciaPalabras(List<String> palabras) {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        List<String> palabrasAux = new ArrayList<>();
        for (String palabra : palabras) {
            if(!resultado.containsKey(palabra)){
                resultado.put(palabra, 1);
            } else {
                resultado.put(palabra, resultado.get(palabra) + 1);
            }
        }
        return resultado;
    }

    @Override
    public Integer obtenerValorPorClave(Map<String, Integer> mapa, String clave) {
        return  mapa.containsKey(clave) ? mapa.get(clave) : 0;
    }

    @Override
    public Map<String, Double> calcularMediaPorCategoria(Map<String, List<Integer>> datos) {
        Map<String, Double> resultado = new LinkedHashMap<>();

        // Entry es una entrada del MapList
        for (Map.Entry<String, List<Integer>> mapa : datos.entrySet()) {
            Double media = 0.0;
            for (Integer numero : mapa.getValue()) {
                media += numero;
            }
            resultado.put(mapa.getKey(), media/mapa.getValue().size());
        }
        return resultado;
    }

    @Override
    public String obtenerClaveConMayorValor(Map<String, Integer> mapa) {
        if(mapa.isEmpty()) throw new IllegalArgumentException();

        Integer mayor = 0;
        String claveMayor = "";
        for (Map.Entry<String, Integer> mapita : mapa.entrySet()) {
            if (mapita.getValue() > mayor) {
                mayor = mapita.getValue();
                claveMayor = mapita.getKey();
            }
        }
        return claveMayor;
    }

    @Override
    public Map<String, Integer> filtrarPorValorMinimo(Map<String, Integer> mapa, Integer minimo) {
        Map<String, Integer> resultado = new LinkedHashMap<>();

        String claveMayor = "";
        for (Map.Entry<String, Integer> mapita : mapa.entrySet()) {
            if (mapita.getValue() >= minimo) {
                resultado.put(mapita.getKey(), mapita.getValue());
            }
        }
        return resultado;
    }
    
}
