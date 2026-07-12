package com.hotelcandelaria.modelo; // Nuevo paquete (antes era hotelcandelaria)

import java.io.Serializable; // Lo mantenemos para poder guardar en el archivo .dat

// Clase ABSTRACTA: igualita a tu version original. La herencia y el
// polimorfismo se quedan intactos, eso es lo bonito de migrar a Spring:
// tu logica POO no se toca.
public abstract class Persona implements Serializable {
    private static final long serialVersionUID = 1L; // Version del archivo binario

    protected String dni;       // protected: los hijos lo usan directo
    protected String nombres;   // Nombres de la persona
    protected String apellidos; // Apellidos de la persona

    // Constructor vacio: Jackson (la libreria que arma el JSON) lo necesita
    // para reconstruir objetos cuando React manda datos. No borrar.
    public Persona() {}

    public Persona(String dni, String nombres, String apellidos) { // Constructor padre
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    // Getters: ahora son OBLIGATORIOS porque Jackson los usa para convertir
    // el objeto a JSON y mandarselo a React. Sin getter, ese campo no viaja.
    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }

    public abstract String obtenerInfo(); // Polimorfismo: cada hijo lo programa
}
