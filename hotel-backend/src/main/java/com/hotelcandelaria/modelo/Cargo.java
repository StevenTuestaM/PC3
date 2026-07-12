package com.hotelcandelaria.modelo;

import java.io.Serializable;

// Catalogo de puestos de trabajo (Recepcionista, etc.)
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idCargo;       // ID del puesto
    private String nombreCargo; // Nombre del puesto

    public Cargo() {} // Constructor vacio para Jackson

    public Cargo(int idCargo, String nombreCargo) {
        this.idCargo = idCargo;
        this.nombreCargo = nombreCargo;
    }

    public int getIdCargo() { return idCargo; }
    public String getNombreCargo() { return nombreCargo; }
}
