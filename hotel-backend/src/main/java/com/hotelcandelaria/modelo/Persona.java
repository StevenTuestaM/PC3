package com.hotelcandelaria.modelo;

import jakarta.persistence.*;

// Clase ABSTRACTA. Con @MappedSuperclass, sus atributos (id, dni, nombres,
// apellidos) se HEREDAN como columnas en las tablas de los hijos (Huesped y
// Empleado), pero Persona en si NO crea su propia tabla.
// Asi mantenemos tu herencia y polimorfismo, y ademas mapea a la BD.
@MappedSuperclass
public abstract class Persona {

    @Id // llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremental
    protected Long id;

    protected String dni;
    protected String nombres;
    protected String apellidos;

    public Persona() {}

    public Persona(String dni, String nombres, String apellidos) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public abstract String obtenerInfo(); // polimorfismo
}
