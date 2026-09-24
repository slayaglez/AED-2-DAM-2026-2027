package com.docencia.colecciones;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SetServiceImpl implements SetService {

    @Override
    public Set<String> obtenerElementosUnicos(List<String> elementos) {
        Set<String> elementosUnicos = new LinkedHashSet<>();
        elementosUnicos.addAll(elementos);
        return elementosUnicos;
    }

    @Override
    public Boolean contieneElemento(Set<String> elementos, String valor) {
        if(valor == null) throw new IllegalArgumentException();
        return elementos.contains(valor);
    }

    @Override
    public Set<String> unirConjuntos(Set<String> primero, Set<String> segundo) {
        Set<String> resultado = new LinkedHashSet<>();
        resultado.addAll(primero);
        resultado.addAll(segundo);
        return resultado;
    }

    @Override
    public Set<String> intersectarConjuntos(Set<String> primero, Set<String> segundo) {
        Set<String> resultado = new LinkedHashSet<>(primero);
        resultado.retainAll(segundo);
        return resultado;
    }

    @Override
    public Set<String> restarConjuntos(Set<String> primero, Set<String> segundo) {
        Set<String> resultado = new LinkedHashSet<>(primero);
        resultado.removeAll(segundo);
        return resultado;
    }
    
}
