package com.hotelcandelaria.modelo;

import jakarta.persistence.*;

// @Entity -> tabla "reservas". Se relaciona con las otras tablas mediante
// @ManyToOne (muchas reservas pueden apuntar a un mismo recepcionista,
// huesped o habitacion). Eso crea las LLAVES FORANEAS en SQL Server.
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // FK -> empleados
    private Empleado recepcionista;

    @ManyToOne // FK -> huespedes
    private Huesped huesped;

    @ManyToOne // FK -> habitaciones
    private Habitacion habitacion;

    private int noches;
    private int personas;
    private double subtotal;
    private double igv;
    private double totalPagado;
    private String metodoPago;

    public Reserva() {}

    public Reserva(Empleado recepcionista, Huesped huesped, Habitacion habitacion,
                   int noches, int personas, String metodoPago) {
        this.recepcionista = recepcionista;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.noches = noches;
        this.personas = personas;
        this.metodoPago = metodoPago;
        this.subtotal = habitacion.getPrecio() * noches;
        this.igv = this.subtotal * 0.18;
        this.totalPagado = this.subtotal + this.igv;
    }

    public Long getId() { return id; }
    public Empleado getRecepcionista() { return recepcionista; }
    public void setRecepcionista(Empleado e) { this.recepcionista = e; }
    public Huesped getHuesped() { return huesped; }
    public void setHuesped(Huesped h) { this.huesped = h; }
    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion h) { this.habitacion = h; }
    public int getNoches() { return noches; }
    public int getPersonas() { return personas; }
    public double getSubtotal() { return subtotal; }
    public double getIgv() { return igv; }
    public double getTotalPagado() { return totalPagado; }
    public String getMetodoPago() { return metodoPago; }
}
