package com.hotelcandelaria.modelo;

import jakarta.persistence.*;

// @Entity -> crea la tabla "huespedes". Hereda id/dni/nombres/apellidos
// de Persona (gracias a @MappedSuperclass) y agrega telefono.
@Entity
@Table(name = "huespedes")
public class Huesped extends Persona {

    private String telefono;

    public Huesped() {}

    public Huesped(String dni, String nombres, String apellidos, String telefono) {
        super(dni, nombres, apellidos);
        this.telefono = telefono;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String obtenerInfo() { return nombres + " " + apellidos; }
}
