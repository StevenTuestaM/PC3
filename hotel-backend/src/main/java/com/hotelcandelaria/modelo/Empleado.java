package com.hotelcandelaria.modelo;

import jakarta.persistence.*;

// @Entity -> crea la tabla "empleados". Guarda los datos de acceso y el rol.
// (El cargo lo guardamos como texto simple para no complicar el modelo.)
@Entity
@Table(name = "empleados")
public class Empleado extends Persona {

    private String usuario;
    private String password; // academico: texto plano (ver nota README)
    private String rol;      // "JEFE" o "RECEPCIONISTA"
    private String cargo;    // "Recepcionista" o "Jefe de Turno"

    public Empleado() {}

    public Empleado(String dni, String nombres, String apellidos,
                    String usuario, String password, String rol, String cargo) {
        super(dni, nombres, apellidos);
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.cargo = cargo;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public String obtenerInfo() { return nombres + " " + apellidos + " (" + cargo + ")"; }
}
