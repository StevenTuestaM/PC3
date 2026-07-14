package com.hotelcandelaria.servicio;

import com.hotelcandelaria.modelo.Huesped;
import com.hotelcandelaria.modelo.Reserva;
import com.hotelcandelaria.repositorio.HuespedRepository;
import com.hotelcandelaria.repositorio.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;   // ESTRUCTURA 1: ArrayList
import java.util.LinkedList;  // ESTRUCTURA 2: LinkedList
import java.util.List;
import java.util.Optional;

// Servicio de reservas. Ahora persiste en SQL Server via repositorios, pero
// SEGUIMOS usando ArrayList, LinkedList y lambdas para procesar en memoria.
@Service
public class ReservaService {

    private final ReservaRepository reservaRepo;
    private final HuespedRepository huespedRepo;

    public ReservaService(ReservaRepository reservaRepo, HuespedRepository huespedRepo) {
        this.reservaRepo = reservaRepo;
        this.huespedRepo = huespedRepo;
    }

    // ESTRUCTURA 2: LinkedList -> log de actividad en memoria. Insertamos al
    // INICIO con addFirst() (instantaneo en LinkedList, lento en ArrayList).
    private final LinkedList<String> historialActividad = new LinkedList<>();

    // CREATE
    public void insertar(Reserva r) {
        // Reutilizamos el huesped si ya existe (por DNI); si no, lo guardamos.
        Huesped h = r.getHuesped();
        Huesped gestionado = huespedRepo.findByDni(h.getDni())
                .orElseGet(() -> huespedRepo.save(h)); // LAMBDA (proveedor)
        r.setHuesped(gestionado);

        reservaRepo.save(r); // INSERT en SQL Server
        historialActividad.addFirst("NUEVA reserva -> " + gestionado.getDni());
    }

    // READ: buscar por DNI del huesped
    public Optional<Reserva> buscar(String dni) {
        List<Reserva> encontradas = reservaRepo.findByHuesped_Dni(dni);
        return encontradas.isEmpty() ? Optional.empty() : Optional.of(encontradas.get(0));
    }

    // READ: "MIS reservas" -> filtra por el recepcionista logueado
    public List<Reserva> listarPorRecepcionista(String dniRecep) {
        return reservaRepo.findByRecepcionista_Dni(dniRecep);
    }

    // DELETE: borra todas las reservas de ese huesped
    public boolean eliminar(String dni) {
        List<Reserva> reservas = reservaRepo.findByHuesped_Dni(dni);
        if (reservas.isEmpty()) return false;
        reservaRepo.deleteAll(reservas);
        historialActividad.addFirst("ELIMINADA reserva -> " + dni);
        return true;
    }

    // READ ALL: lo metemos en un ArrayList nuevo (ESTRUCTURA 1)
    public List<Reserva> listarTodas() {
        return new ArrayList<>(reservaRepo.findAll());
    }

    // EXTRA con LAMBDA/STREAM: recaudacion total
    public double calcularRecaudacionTotal() {
        return reservaRepo.findAll().stream()
                .mapToDouble(r -> r.getTotalPagado()) // LAMBDA
                .sum();
    }

    public List<String> obtenerHistorial() { return historialActividad; }
}
