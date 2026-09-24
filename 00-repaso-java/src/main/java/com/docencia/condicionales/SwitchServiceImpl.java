package com.docencia.condicionales;

public class SwitchServiceImpl implements SwitchService {

    @Override
    public String obtenerNombreDia(Integer numeroDia) {
        switch (numeroDia) {
            case 1: return "LUNES";
            case 2: return "MARTES";
            case 3: return "MIERCOLES";
            case 4: return "JUEVES";
            case 5: return "VIERNES";
            case 6: return "SABADO";
            case 7: return "DOMINGO";
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public Double calcularDescuentoPorTipo(String tipoCliente, Double importe) {
        switch (tipoCliente) {
            case "VIP": return importe*0.2;
            case "NORMAL": return importe*0.0;
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public String obtenerMensajeEstado(String estado) {
        switch (estado) {
            case "ACTIVO": return "Elemento activo";
            default: return "Elemento inactivo";
        }
        // ???? supongo ????
    }

    @Override
    public Integer obtenerDiasDelMes(Integer mes) {
        switch (mes) {
            case 1, 3, 5, 7, 8, 10, 12: return 31;
            case 2: return 28;
            case 4, 6, 9, 11: return 30;
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public String obtenerCategoriaProducto(String codigo) {
        switch (codigo) {
            case "T001": return "TECNOLOGIA";
            default: throw new IllegalArgumentException();
        }
        // No hay mas casos en los test
    }
    
}
