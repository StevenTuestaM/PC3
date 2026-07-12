package com.hotelcandelaria.dto;

// Datos para agregar o modificar un recepcionista desde la web (solo el Jefe).
public class EmpleadoRequest {
    private String dni;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String password;
    private String rol; // "JEFE" o "RECEPCIONISTA"

    public EmpleadoRequest() {}
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
