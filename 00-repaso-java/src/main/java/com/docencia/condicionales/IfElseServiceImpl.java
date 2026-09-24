package com.docencia.condicionales;

public class IfElseServiceImpl implements IfElseService {

    @Override
    public String clasificarEdad(Integer edad) {
        if(edad < 0) throw new IllegalArgumentException();
        if(edad<18) return "MENOR";
        return "ADULTO";
    }

    @Override
    public String evaluarNota(Integer nota) {
        if(nota>=5) return "APROBADO";
        return "SUSPENSO";
    }

    @Override
    public Boolean puedeAcceder(Boolean activo, Integer edad) {
        return(activo && edad<=18);
    }

    @Override
    public String clasificarTemperatura(Double temperatura) {
        if(temperatura >= 30.0) return "CALOR";
        return "FRÍO";
    }

    @Override
    public String calcularResultadoComparacion(Integer primero, Integer segundo) {
        if(primero.equals(segundo)) return "IGUALES";
        return "DIFERENTES";
    }
    
}
