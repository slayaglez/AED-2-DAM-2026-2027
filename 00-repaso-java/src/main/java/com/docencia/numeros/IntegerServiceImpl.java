package com.docencia.numeros;

import java.util.ArrayList;
import java.util.List;

public class IntegerServiceImpl implements IntegerService {

    @Override
    public Boolean esPar(Integer numero) {
        if (numero == null) throw new IllegalArgumentException();
        return numero % 2 == 0;
    }

    @Override
    public Integer sumarDigitos(Integer numero) {
        int numeroAux = Math.abs(numero);
        int suma = 0;
        while (numeroAux > 0) {
            suma += numeroAux % 10;
            numeroAux /= 10;
        }
        return suma;
    }

    @Override
    public Integer convertirTextoAEntero(String texto) {
        return Integer.parseInt(texto);
    }

    @Override
    public Boolean esNumeroPrimo(Integer numero) {
        if (numero < 2) return false;
        for (int i = 2; i * i <= numero; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    @Override
    public Integer calcularFactorial(Integer numero) {
        if (numero < 0) throw new IllegalArgumentException();
        int resultado = 1;
        for (int i = 2; i <= numero; i++) {
            resultado *= i;
        }
        return resultado;
    }
    
}
