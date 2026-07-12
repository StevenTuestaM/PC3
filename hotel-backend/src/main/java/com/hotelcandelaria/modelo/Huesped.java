package com.hotelcandelaria.modelo;

// Huesped HEREDA de Persona (igual que antes). El "extends" es la herencia.
public class Huesped extends Persona {
    private String telefono; // Dato exclusivo del cliente

    public Huesped() {} // Constructor vacio para Jackson

    public Huesped(String dni, String nombres, String apellidos, String telefono) {
        super(dni, nombres, apellidos); // Mandamos lo basico al padre Persona
        this.telefono = telefono;       // Guardamos el telefono aca
    }

    public String getTelefono() { return telefono; } // Getter para el JSON

    @Override // POLIMORFISMO: sobreescribimos el metodo del padre
    public String obtenerInfo() {
        return nombres + " " + apellidos; // El cliente solo muestra su nombre
    }
}
