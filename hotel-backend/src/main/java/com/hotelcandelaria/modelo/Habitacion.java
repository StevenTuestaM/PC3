package com.hotelcandelaria.modelo;

import java.io.Serializable;

// La habitacion fisica. Ahora tiene ESTADO (Libre / Ocupada) y los datos
// directos (tipo, capacidad, precio) para poder agregarlas/editarlas desde
// la web sin depender de clases fijas.
public class Habitacion implements Serializable {
    private static final long serialVersionUID = 2L;

    private String numero;     // numero de puerta (ej "305"), es la llave unica
    private String tipo;       // Simple, Doble, VIP, Suite, Familiar, etc.
    private int capacidad;     // cuantas personas entran
    private double precio;     // precio por noche en soles
    private String estado;     // "Libre" u "Ocupada"

    public Habitacion() {} // Constructor vacio para Jackson

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
