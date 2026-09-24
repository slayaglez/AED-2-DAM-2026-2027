package com.docencia.condicionales;
public interface IfElseService {

    /**
     * Clasifica mayores de edad
     * @param edad Integer edad
     * @return si es mayor de edad o no
     */
    String clasificarEdad(Integer edad);
    /**
     * Evalua si una nota esta aprobada
     * @param nota Integer nota
     * @return si esta aprobado o suspenso
     */
    String evaluarNota(Integer nota);

    /**
     * Comprueba si un usuario puede acceder
     * @param activo Boolean si el usuario esta activo
     * @param edad Integer edad del usuario
     * @return si puede acceder o no
     */
    Boolean puedeAcceder(Boolean activo, Integer edad);

    /**
     * Clasifica una temperatura en calor o frio
     * @param temperatura Double temperatura
     * @return si hace calor o frio
     */
    String clasificarTemperatura(Double temperatura);

    /**
     * Compara dos numeros
     * @param primero Integer primer numero
     * @param segundo Integer segundo numero
     * @return si son iguales o diferentes
     */
    String calcularResultadoComparacion(Integer primero, Integer segundo);
}
