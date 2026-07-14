package com.hotelcandelaria.modelo;

import jakarta.persistence.*;

// @Entity -> tabla "habitaciones". El numero de cuarto es la llave primaria.
@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id // el numero es unico, lo usamos como llave (no autoincremental)
    private String numero;

    private String tipo;
    private int capacidad;
    private double precio;
    private String estado; // "Libre" u "Ocupada"

    public Habitacion() {}

    public Habitacion(String numero, String tipo, int capacidad, double precio, String estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precio = precio;
        this.estado = estado;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
