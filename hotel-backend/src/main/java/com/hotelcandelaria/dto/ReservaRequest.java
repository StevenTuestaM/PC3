package com.hotelcandelaria.dto;

// Datos del formulario de nueva reserva que manda React.
public class ReservaRequest {
    private String dni;             // DNI del huesped
    private String nombres;
    private String apellidos;
    private String telefono;
    private String recepcionistaDni; // quien la registra (el logueado)
    private String numeroHabitacion; // que cuarto (del catalogo dinamico)
    private int personas;
    private int noches;
    private String metodoPago;

    public ReservaRequest() {}
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRecepcionistaDni() { return recepcionistaDni; }
    public void setRecepcionistaDni(String d) { this.recepcionistaDni = d; }
    public String getNumeroHabitacion() { return numeroHabitacion; }
    public void setNumeroHabitacion(String n) { this.numeroHabitacion = n; }
    public int getPersonas() { return personas; }
    public void setPersonas(int personas) { this.personas = personas; }
    public int getNoches() { return noches; }
    public void setNoches(int noches) { this.noches = noches; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
