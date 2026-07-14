package com.hotelcandelaria.servicio;

import com.hotelcandelaria.modelo.Empleado;
import com.hotelcandelaria.repositorio.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // ESTRUCTURA: ArrayList
import java.util.List;
import java.util.Optional;

// Login + CRUD de recepcionistas, sobre SQL Server.
@Service
public class EmpleadoService {

    private final EmpleadoRepository repo;
    public EmpleadoService(EmpleadoRepository repo) { this.repo = repo; }

    // LOGIN: Spring genera el SELECT ... WHERE usuario=? AND password=?
    public Optional<Empleado> login(String usuario, String password) {
        return repo.findByUsuarioAndPassword(usuario, password);
    }

    public List<Empleado> listar() { return new ArrayList<>(repo.findAll()); }

    public Optional<Empleado> buscarPorDni(String dni) { return repo.findByDni(dni); }

    public boolean agregar(Empleado e) {
        if (repo.existsByUsuario(e.getUsuario())) return false;
        repo.save(e);
        return true;
    }

    public boolean modificar(String dni, Empleado datos) {
        Optional<Empleado> existente = repo.findByDni(dni);
        if (existente.isEmpty()) return false;
        Empleado e = existente.get();      // mantenemos el id de la BD
        e.setNombres(datos.getNombres());
        e.setApellidos(datos.getApellidos());
        e.setUsuario(datos.getUsuario());
        e.setPassword(datos.getPassword());
        e.setRol(datos.getRol());
        e.setCargo(datos.getCargo());
        repo.save(e);
        return true;
    }

    public boolean eliminar(String dni) {
        Optional<Empleado> e = repo.findByDni(dni);
        if (e.isEmpty()) return false;
        repo.delete(e.get());
        return true;
    }
}
