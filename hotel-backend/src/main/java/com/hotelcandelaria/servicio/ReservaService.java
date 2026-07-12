package com.hotelcandelaria.servicio;

import com.hotelcandelaria.modelo.Reserva;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;   // ESTRUCTURA 1: lista principal de reservas
import java.util.LinkedList;  // ESTRUCTURA 2: historial (insercion al inicio)
import java.util.List;
import java.util.Optional;

// Este es tu antiguo "Contenedor", ahora como servicio de Spring.
@Service
public class ReservaService {

    // -------- ESTRUCTURA 1: ArrayList (almacen principal) --------
    private List<Reserva> listaReservas = new ArrayList<>();

    // -------- ESTRUCTURA 2: LinkedList (log de actividad) --------
    // Insertamos al inicio con addFirst(): instantaneo en LinkedList,
    // lento en ArrayList. Ese es el argumento para defenderlo oralmente.
    private LinkedList<String> historialActividad = new LinkedList<>();

    private static final String ARCHIVO = "reservas.dat";

    @PostConstruct
    public void iniciar() { cargarDatos(); }

    // CREATE
    public void insertar(Reserva r) {
        listaReservas.add(r);
        historialActividad.addFirst("NUEVA reserva -> " + r.getHuesped().getDni());
        serializar();
    }

    // READ con LAMBDA: buscar por DNI del huesped
    public Optional<Reserva> buscar(String dni) {
        return listaReservas.stream()
                .filter(r -> r.getHuesped().getDni().equals(dni)) // LAMBDA
                .findFirst();
    }

    // READ con LAMBDA: "MIS reservas" -> filtra por el DNI del recepcionista.
    // Esta es la clave del login: cada quien ve solo las suyas.
    public List<Reserva> listarPorRecepcionista(String dniRecep) {
        return listaReservas.stream()
                .filter(r -> r.getRecepcionista().getDni().equals(dniRecep)) // LAMBDA
                .toList();
    }

    // UPDATE
    public boolean modificar(String dni, Reserva nueva) {
        for (int i = 0; i < listaReservas.size(); i++) {
            if (listaReservas.get(i).getHuesped().getDni().equals(dni)) {
                listaReservas.set(i, nueva);
                historialActividad.addFirst("MODIFICADA reserva -> " + dni);
                serializar();
                return true;
            }
        }
        return false;
    }

    // DELETE con LAMBDA
    public boolean eliminar(String dni) {
        boolean ok = listaReservas.removeIf(r -> r.getHuesped().getDni().equals(dni)); // LAMBDA
        if (ok) {
            historialActividad.addFirst("ELIMINADA reserva -> " + dni);
            serializar();
        }
        return ok;
    }

    public List<Reserva> listarTodas() { return listaReservas; }

    // EXTRA con LAMBDA: recaudacion total
    public double calcularRecaudacionTotal() {
        return listaReservas.stream()
                .mapToDouble(r -> r.getTotalPagado()) // LAMBDA
                .sum();
    }

    public List<String> obtenerHistorial() { return historialActividad; }

    private void serializar() {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            o.writeObject(new ArrayList<>(listaReservas));
        } catch (IOException e) { System.out.println("Error guardar: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File f = new File(ARCHIVO);
        if (f.exists()) {
            try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(f))) {
                listaReservas = (ArrayList<Reserva>) i.readObject();
            } catch (Exception e) { System.out.println("Error cargar: " + e.getMessage()); }
        }
    }
}
