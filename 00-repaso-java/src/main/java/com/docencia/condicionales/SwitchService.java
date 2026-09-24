package com.docencia.condicionales;
/** Servicio para practicar switch. */
public interface SwitchService {

    /**
     * Obtiene el nombre del dia de la semana
     * @param numeroDia Integer numero del dia (1-7)
     * @return nombre del dia
     */
    String obtenerNombreDia(Integer numeroDia);

    /**
     * Calcula el descuento segun el tipo de cliente
     * @param tipoCliente String tipo de cliente (VIP o NORMAL)
     * @param importe Double importe de la compra
     * @return descuento aplicado al importe
     */
    Double calcularDescuentoPorTipo(String tipoCliente, Double importe);

    /**
     * Obtiene el mensaje segun el estado
     * @param estado String estado del elemento
     * @return si el elemento esta activo o inactivo
     */
    String obtenerMensajeEstado(String estado);

    /**
     * Obtiene los dias de un mes
     * @param mes Integer numero del mes (1-12)
     * @return numero de dias del mes
     */
    Integer obtenerDiasDelMes(Integer mes);

    /**
     * Obtiene la categoria de un producto
     * @param codigo String codigo del producto
     * @return categoria del producto
     */
    String obtenerCategoriaProducto(String codigo);
}