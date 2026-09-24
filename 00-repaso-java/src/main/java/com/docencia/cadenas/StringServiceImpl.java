package com.docencia.cadenas;

public class StringServiceImpl implements StringService {

    @Override
    public String normalizarTexto(String texto) {
        if  (texto == null) {throw new IllegalArgumentException();}
        return texto.toLowerCase().strip();
    }

    @Override
    public Boolean esPalindromo(String texto) {
        texto = texto.replaceAll(" ", "");
        for (int i = 0; i < texto.length()/2; i++) {
            if (texto.charAt(i) != texto.charAt(texto.length()-i-1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Integer contarVocales(String texto) {
        int contador = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == 'a' || texto.charAt(i) == 'e' || texto.charAt(i) == 'i' || texto.charAt(i) == 'u' || texto.charAt(i) == 'o'){
                contador++;
            }
        }
        return contador;
    }

    @Override
    public String extraerIniciales(String nombreCompleto) {
        String iniciales = "";
        boolean enPalabra = false;
        for (int i = 0; i < nombreCompleto.length(); i++) {
            if(nombreCompleto.charAt(i) != ' ' && !enPalabra){
                iniciales += nombreCompleto.charAt(i);
                enPalabra = true;
            }
            if(nombreCompleto.charAt(i) == ' '){
                enPalabra = false;
            }
        }
        return iniciales;
    }

    @Override
    public String invertirTexto(String texto) {
        String invertido = "";
        for (int i = 0; i < texto.length(); i++) {
            invertido += texto.charAt(texto.length() - i - 1);
        }
        return invertido;
    }

    @Override
    public Boolean contieneSoloLetras(String texto) {
        return texto.matches("[a-zA-Z]+");
    }
    
}
