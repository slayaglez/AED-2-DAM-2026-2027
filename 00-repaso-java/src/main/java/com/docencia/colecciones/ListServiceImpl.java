package com.docencia.colecciones;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ListServiceImpl implements ListService {

    @Override
    public List<String> filtrarPalabrasPorLongitud(List<String> palabras, Integer longitudMinima) {
        if(longitudMinima <= 0) throw new IllegalArgumentException();

        List<String> resultado = new ArrayList<>();
        for(String palabra: palabras) {
            if(longitudMinima <= palabra.length()) {
                resultado.add(palabra);
            }
        }
        return resultado;
    }

    @Override
    public List<Integer> ordenarNumerosAscendente(List<Integer> numeros) {
        return numeros.stream().sorted().toList();
    }

    @Override
    public Integer sumarElementosLista(List<Integer> numeros) {
        Integer resultado = numeros.get(0);
        for (int i = 1; i < numeros.size(); i++) {
            resultado += numeros.get(i);
        }
        return resultado;
    }

    @Override
    public Double calcularMediaLista(List<Integer> numeros) {
        if(numeros.isEmpty()) throw new IllegalArgumentException();

        Double resultado = numeros.get(0).doubleValue();
        for (int i = 1; i < numeros.size(); i++) {
            resultado += numeros.get(i);
        }
        return resultado/numeros.size();
    }

    @Override
    public List<Integer> eliminarNumerosDuplicados(List<Integer> numeros) {
        List<Integer> resultado = new ArrayList<>();
        for(Integer numero: numeros) {
            if(!resultado.contains(numero)) {
                resultado.add(numero);
            }
        }
        return resultado;
    }
   
}
