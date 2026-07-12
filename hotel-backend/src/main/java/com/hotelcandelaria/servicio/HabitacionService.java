package com.hotelcandelaria.servicio;

import com.hotelcandelaria.modelo.Habitacion;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;   // ESTRUCTURA: ArrayList para el catalogo de cuartos
import java.util.List;
import java.util.Optional;

// Servicio que administra el CATALOGO de habitaciones (CRUD completo).
@Service
public class HabitacionService {

    // ArrayList: lista principal de habitaciones (acceso por indice al editar)
    private List<Habitacion> habitaciones = new ArrayList<>();
    private static final String ARCHIVO = "habitaciones.dat";

    // @PostConstruct: Spring corre esto AUTOMATICAMENTE al arrancar.
    // Carga del archivo; si no existe, precarga el catalogo de ejemplo.
    @PostConstruct
    public void iniciar() {
        cargarDatos();
        if (habitaciones.isEmpty()) {
            precargarCatalogo();
            serializar();
        }
    }

    // Catalogo igual al de la foto de referencia
    private void precargarCatalogo() {
        habitaciones.add(new Habitacion("101", "Simple", 2, 80.0, "Libre"));
        habitaciones.add(new Habitacion("102", "Simple", 2, 80.0, "Libre"));
        habitaciones.add(new Habitacion("103", "Doble", 2, 120.0, "Libre"));
        habitaciones.add(new Habitacion("104", "Doble", 2, 120.0, "Libre"));
        habitaciones.add(new Habitacion("201", "VIP", 4, 180.0, "Libre"));
        habitaciones.add(new Habitacion("202", "VIP", 4, 180.0, "Libre"));
        habitaciones.add(new Habitacion("203", "VIP", 4, 180.0, "Libre"));
        habitaciones.add(new Habitacion("301", "Suite", 5, 280.0, "Libre"));
        habitaciones.add(new Habitacion("302", "Suite", 6, 280.0, "Libre"));
        habitaciones.add(new Habitacion("303", "Suite", 6, 280.0, "Libre"));
        habitaciones.add(new Habitacion("401", "Familiar", 6, 220.0, "Libre"));
        habitaciones.add(new Habitacion("402", "Familiar", 6, 220.0, "Libre"));
        habitaciones.add(new Habitacion("501", "Residencial", 10, 400.0, "Libre"));
        habitaciones.add(new Habitacion("502", "Residencial", 10, 400.0, "Libre"));
        habitaciones.add(new Habitacion("601", "Penthouse", 8, 550.0, "Libre"));
        habitaciones.add(new Habitacion("602", "Penthouse", 8, 550.0, "Libre"));
    }

    public List<Habitacion> listar() { return habitaciones; }

    // BUSCAR con LAMBDA: filtramos por numero de habitacion
    public Optional<Habitacion> buscar(String numero) {
        return habitaciones.stream()
                .filter(h -> h.getNumero().equalsIgnoreCase(numero)) // LAMBDA
                .findFirst();
    }

    // CREATE: agregar una habitacion nueva (valida que no se repita el numero)
    public boolean agregar(Habitacion h) {
        if (buscar(h.getNumero()).isPresent()) return false; // ya existe
        habitaciones.add(h);
        serializar();
        return true;
    }

    // UPDATE: modificar una habitacion existente
    public boolean modificar(String numero, Habitacion datos) {
        for (int i = 0; i < habitaciones.size(); i++) {
            if (habitaciones.get(i).getNumero().equalsIgnoreCase(numero)) {
                datos.setNumero(numero); // el numero no cambia (es la llave)
                habitaciones.set(i, datos);
                serializar();
                return true;
            }
        }
        return false;
    }

    // DELETE con LAMBDA: removeIf
    public boolean eliminar(String numero) {
        boolean ok = habitaciones.removeIf(h -> h.getNumero().equalsIgnoreCase(numero)); // LAMBDA
        if (ok) serializar();
        return ok;
    }

    // Cambia el estado (Libre/Ocupada) cuando se reserva
    public void marcarEstado(String numero, String estado) {
        buscar(numero).ifPresent(h -> { h.setEstado(estado); serializar(); }); // LAMBDA
    }

    private void serializar() {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            o.writeObject(new ArrayList<>(habitaciones));
        } catch (IOException e) { System.out.println("Error guardar hab: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File f = new File(ARCHIVO);
        if (f.exists()) {
            try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(f))) {
                habitaciones = (ArrayList<Habitacion>) i.readObject();
            } catch (Exception e) { System.out.println("Error cargar hab: " + e.getMessage()); }
        }
    }
}
