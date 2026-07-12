package com.hotelcandelaria.dto;

// Lo que el backend responde si el login es correcto: los datos del
// recepcionista logueado (React los guarda para saber quien esta dentro).
public class LoginResponse {
    private boolean exito;
    private String mensaje;
    private String nombreCompleto;
    private String dni;
    private String usuario;
    private String rol; // "JEFE" o "RECEPCIONISTA"

    public LoginResponse(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public boolean isExito() { return exito; }
    public String getMensaje() { return mensaje; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String n) { this.nombreCompleto = n; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String u) { this.usuario = u; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
