package com.hotelcandelaria.dto;

// Datos para agregar o modificar una habitacion desde la web.
public class HabitacionRequest {
    private String numero;
    private String tipo;
    private int capacidad;
    private double precio;

    public HabitacionRequest() {}
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
