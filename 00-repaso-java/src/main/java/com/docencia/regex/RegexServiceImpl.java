package com.docencia.regex;

public class RegexServiceImpl implements RegexService {

    @Override
    public Boolean validarDni(String dni) {
        if(dni==null) return false;
        String regex = "[0-9]{8}[A-Z]";
        return dni.matches(regex);
    }

    @Override
    public Boolean validarEmail(String email) {
        String regex = "[a-z]+@[a-z]+\\.[a-z]+";
        return email.matches(regex);
    }

    @Override
    public Boolean validarTelefono(String telefono) {
        String regex = "6[0-9]{8}";
        return telefono.matches(regex);
    }

    @Override
    public Boolean validarNombre(String nombre) {
        String regex = "([a-zA-Záéíóú]{2,})(\\s([a-zA-Záéíóú]{2,}))?";
        return nombre.matches(regex);
    }

    @Override
    public Boolean validarCodigoPostal(String codigoPostal) {
        String regex = "[0-9]{5}";
        return codigoPostal.matches(regex);
    }

    @Override
    public Boolean validarMatricula(String matricula) {
        String regex = "[0-9]{4}[A-Z]{3}";
        return matricula.matches(regex);
    }
    
}
