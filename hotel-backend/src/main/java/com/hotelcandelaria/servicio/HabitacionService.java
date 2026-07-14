package com.hotelcandelaria.servicio;

import com.hotelcandelaria.modelo.Habitacion;
import com.hotelcandelaria.repositorio.HabitacionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // ESTRUCTURA: ArrayList
import java.util.List;
import java.util.Optional;

// CRUD del catalogo de habitaciones, ahora sobre SQL Server.
@Service
public class HabitacionService {

    private final HabitacionRepository repo;
    public HabitacionService(HabitacionRepository repo) { this.repo = repo; }

    public List<Habitacion> listar() {
        return new ArrayList<>(repo.findAll()); // resultados en un ArrayList
    }

    public Optional<Habitacion> buscar(String numero) { return repo.findById(numero); }

    public boolean agregar(Habitacion h) {
        if (repo.existsById(h.getNumero())) return false; // ya existe
        repo.save(h);
        return true;
    }

    public boolean modificar(String numero, Habitacion datos) {
        if (!repo.existsById(numero)) return false;
        datos.setNumero(numero);
        repo.save(datos);
        return true;
    }

    public boolean eliminar(String numero) {
        if (!repo.existsById(numero)) return false;
        repo.deleteById(numero);
        return true;
    }

    // Cambia el estado (Libre/Ocupada) con LAMBDA (ifPresent)
    public void marcarEstado(String numero, String estado) {
        repo.findById(numero).ifPresent(h -> { // LAMBDA
            h.setEstado(estado);
            repo.save(h);
        });
    }
}
