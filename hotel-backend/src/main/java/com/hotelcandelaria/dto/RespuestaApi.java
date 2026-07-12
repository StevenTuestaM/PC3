package com.hotelcandelaria.dto;

// Respuesta generica que mandamos de vuelta a React tras una accion
// (registrar, modificar, eliminar). Reemplaza a los JOptionPane que
// antes mostraban "Reserva registrada con exito" en la ventana Swing.
public class RespuestaApi {
    private boolean exito;    // true si salio bien
    private String mensaje;   // el texto a mostrar en React

    public RespuestaApi(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public boolean isExito() { return exito; }
    public String getMensaje() { return mensaje; }
}
