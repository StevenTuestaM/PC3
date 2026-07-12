package com.hotelcandelaria.controlador;

import com.hotelcandelaria.dto.*;
import com.hotelcandelaria.modelo.Cargo;
import com.hotelcandelaria.modelo.Empleado;
import com.hotelcandelaria.servicio.EmpleadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmpleadoController {

    private final EmpleadoService servicio;
    public EmpleadoController(EmpleadoService servicio) { this.servicio = servicio; }

    // ===== LOGIN: POST /api/login =====
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        Optional<Empleado> emp = servicio.login(req.getUsuario(), req.getPassword());
        if (emp.isEmpty()) {
            return new LoginResponse(false, "Usuario o contrasena incorrectos.");
        }
        Empleado e = emp.get();
        LoginResponse resp = new LoginResponse(true, "Bienvenido " + e.getNombres());
        resp.setNombreCompleto(e.getNombres() + " " + e.getApellidos());
        resp.setDni(e.getDni());
        resp.setUsuario(e.getUsuario());
        resp.setRol(e.getRol());
        return resp;
    }

    // ===== Listar recepcionistas: GET /api/recepcionistas =====
    @GetMapping("/recepcionistas")
    public List<Empleado> listar() { return servicio.listar(); }

    // ===== Agregar (solo Jefe): POST /api/recepcionistas =====
    @PostMapping("/recepcionistas")
    public RespuestaApi agregar(@RequestBody EmpleadoRequest req) {
        if (req.getDni() == null || !req.getDni().matches("^[0-9]{8}$"))
            return new RespuestaApi(false, "DNI debe tener 8 digitos.");
        if (req.getUsuario() == null || req.getUsuario().isBlank())
            return new RespuestaApi(false, "El usuario no puede estar vacio.");

        Cargo cargo = "JEFE".equals(req.getRol())
                ? new Cargo(2, "Jefe de Turno")
                : new Cargo(1, "Recepcionista");

        Empleado e = new Empleado(req.getDni(), req.getNombres(), req.getApellidos(),
                cargo, req.getUsuario(), req.getPassword(), req.getRol());

        boolean ok = servicio.agregar(e);
        return ok ? new RespuestaApi(true, "Recepcionista agregado.")
                  : new RespuestaApi(false, "Ese usuario ya existe.");
    }

    // ===== Modificar: PUT /api/recepcionistas/{dni} =====
    @PutMapping("/recepcionistas/{dni}")
    public RespuestaApi modificar(@PathVariable String dni, @RequestBody EmpleadoRequest req) {
        Cargo cargo = "JEFE".equals(req.getRol())
                ? new Cargo(2, "Jefe de Turno")
                : new Cargo(1, "Recepcionista");
        Empleado e = new Empleado(req.getDni(), req.getNombres(), req.getApellidos(),
                cargo, req.getUsuario(), req.getPassword(), req.getRol());
        boolean ok = servicio.modificar(dni, e);
        return ok ? new RespuestaApi(true, "Recepcionista modificado.")
                  : new RespuestaApi(false, "No se encontro ese recepcionista.");
    }

    // ===== Eliminar: DELETE /api/recepcionistas/{dni} =====
    @DeleteMapping("/recepcionistas/{dni}")
    public RespuestaApi eliminar(@PathVariable String dni) {
        boolean ok = servicio.eliminar(dni);
        return ok ? new RespuestaApi(true, "Recepcionista eliminado.")
                  : new RespuestaApi(false, "No se encontro ese recepcionista.");
    }
}
