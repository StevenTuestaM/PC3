package com.hotelcandelaria.servicio;

import com.hotelcandelaria.modelo.Cargo;
import com.hotelcandelaria.modelo.Empleado;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;   // ESTRUCTURA: ArrayList para los recepcionistas
import java.util.List;
import java.util.Optional;

// Servicio de recepcionistas: login + CRUD (alta, baja, modificacion).
@Service
public class EmpleadoService {

    private List<Empleado> empleados = new ArrayList<>();
    private static final String ARCHIVO = "empleados.dat";

    @PostConstruct
    public void iniciar() {
        cargarDatos();
        if (empleados.isEmpty()) {
            precargar();
            serializar();
        }
    }

    // Usuarios de ejemplo (datos de la foto de referencia).
    // Contrasena por defecto de todos: 1234
    private void precargar() {
        Cargo recep = new Cargo(1, "Recepcionista");
        Cargo jefe = new Cargo(2, "Jefe de Turno");
        empleados.add(new Empleado("78451236", "Kenny", "Huacani", recep, "SHOW", "1234", "RECEPCIONISTA"));
        empleados.add(new Empleado("65432187", "Manuela", "Segunda", recep, "OÑO", "1234", "RECEPCIONISTA"));
        empleados.add(new Empleado("74123698", "Makanaky", "La Realeza", jefe, "GAAA", "1234", "JEFE"));
    }

    // LOGIN con LAMBDA: busca un empleado cuyo usuario Y password coincidan.
    public Optional<Empleado> login(String usuario, String password) {
        return empleados.stream()
                .filter(e -> e.getUsuario().equals(usuario) && e.getPassword().equals(password)) // LAMBDA
                .findFirst();
    }

    public List<Empleado> listar() { return empleados; }

    // Buscar por DNI (para asociar la reserva a su recepcionista)
    public Optional<Empleado> buscarPorDni(String dni) {
        return empleados.stream()
                .filter(e -> e.getDni().equals(dni)) // LAMBDA
                .findFirst();
    }

    public boolean agregar(Empleado e) {
        boolean usuarioRepetido = empleados.stream()
                .anyMatch(x -> x.getUsuario().equals(e.getUsuario())); // LAMBDA
        if (usuarioRepetido) return false;
        empleados.add(e);
        serializar();
        return true;
    }

    public boolean modificar(String dni, Empleado datos) {
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getDni().equals(dni)) {
                empleados.set(i, datos);
                serializar();
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String dni) {
        boolean ok = empleados.removeIf(e -> e.getDni().equals(dni)); // LAMBDA
        if (ok) serializar();
        return ok;
    }

    private void serializar() {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            o.writeObject(new ArrayList<>(empleados));
        } catch (IOException e) { System.out.println("Error guardar emp: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File f = new File(ARCHIVO);
        if (f.exists()) {
            try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(f))) {
                empleados = (ArrayList<Empleado>) i.readObject();
            } catch (Exception e) { System.out.println("Error cargar emp: " + e.getMessage()); }
        }
    }
}
