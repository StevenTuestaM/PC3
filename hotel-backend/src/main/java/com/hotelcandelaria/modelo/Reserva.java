package com.hotelcandelaria.modelo;

import java.io.Serializable;

// La transaccion principal: une recepcionista + huesped + habitacion.
public class Reserva implements Serializable {
    private static final long serialVersionUID = 2L;

    private Empleado recepcionista; // quien atendio (define "mis reservas")
    private Huesped huesped;         // el cliente
    private Habitacion habitacion;   // el cuarto alquilado
    private int noches;              // cuantas noches
    private int personas;            // cuantas personas
    private double subtotal;         // precio x noches
    private double igv;              // 18% del subtotal
    private double totalPagado;      // subtotal + igv
    private String metodoPago;       // Efectivo, Tarjeta, Yape

    public Reserva() {} // Constructor vacio para Jackson

    public Reserva(Empleado recepcionista, Huesped huesped, Habitacion habitacion,
                   int noches, int personas, String metodoPago) {
        this.recepcionista = recepcionista;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.noches = noches;
        this.personas = personas;
        this.metodoPago = metodoPago;
        // Calculo con IGV (igual que en la foto de referencia)
        this.subtotal = habitacion.getPrecio() * noches;
        this.igv = this.subtotal * 0.18;
        this.totalPagado = this.subtotal + this.igv;
    }

    public Empleado getRecepcionista() { return recepcionista; }
    public Huesped getHuesped() { return huesped; }
    public Habitacion getHabitacion() { return habitacion; }
    public int getNoches() { return noches; }
    public int getPersonas() { return personas; }
    public double getSubtotal() { return subtotal; }
    public double getIgv() { return igv; }
    public double getTotalPagado() { return totalPagado; }
    public String getMetodoPago() { return metodoPago; }

    @Override
    public String toString() {
        return "Reserva [" + huesped.obtenerInfo() + " | Hab " + habitacion.getNumero() +
               " | Total S/" + totalPagado + " | Atendio: " + recepcionista.obtenerInfo() + "]";
    }
}
