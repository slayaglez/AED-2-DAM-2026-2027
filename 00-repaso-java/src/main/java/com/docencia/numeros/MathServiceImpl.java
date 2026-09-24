package com.docencia.numeros;

public class MathServiceImpl implements MathService {

    @Override
    public Double calcularAreaCirculo(Double radio) {
        if(radio <= 0) throw new IllegalArgumentException();
        return Math.PI * Math.pow(radio, 2);
    }

    @Override
    public Integer calcularPotencia(Integer base, Integer exponente) {
        return (int) Math.pow(base, exponente);
    }

    @Override
    public Integer redondearHaciaArriba(Double numero) {
        return (int) Math.ceil(numero);
    }

    @Override
    public Double calcularRaizCuadrada(Double numero) {
        return Math.sqrt(numero);
    }

    @Override
    public Integer obtenerValorAbsoluto(Integer numero) {
        return Math.abs(numero);
    }
    
}
