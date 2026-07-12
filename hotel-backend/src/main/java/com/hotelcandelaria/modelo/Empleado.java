package com.hotelcandelaria.modelo;

// Empleado HEREDA de Persona. Ahora ademas guarda los datos de acceso
// (usuario, contrasena) y su ROL, para el login con permisos.
public class Empleado extends Persona {
    private Cargo cargo;       // Recepcionista / Jefe de Turno (catalogo)
    private String usuario;    // con que nombre inicia sesion
    private String password;   // su clave (academico: texto plano, ver nota README)
    private String rol;        // "JEFE" o "RECEPCIONISTA" -> define permisos

    public Empleado() {} // Constructor vacio para Jackson

    public Empleado(String dni, String nombres, String apellidos, Cargo cargo,
                    String usuario, String password, String rol) {
        super(dni, nombres, apellidos);
        this.cargo = cargo;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override // POLIMORFISMO: muestra nombre y cargo
    public String obtenerInfo() {
        return nombres + " " + apellidos + " (" + cargo.getNombreCargo() + ")";
    }
}
