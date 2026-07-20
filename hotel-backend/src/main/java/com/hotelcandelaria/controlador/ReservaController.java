package com.hotelcandelaria.controlador;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.hotelcandelaria.dto.ReservaRequest;
import com.hotelcandelaria.dto.RespuestaApi;
import com.hotelcandelaria.modelo.*;
import com.hotelcandelaria.servicio.EmpleadoService;
import com.hotelcandelaria.servicio.HabitacionService;
import com.hotelcandelaria.servicio.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    // Inyeccion de los tres servicios que necesita (Spring los pasa solo)
    private final ReservaService servicio;
    private final HabitacionService habitacionService;
    private final EmpleadoService empleadoService;

    public ReservaController(ReservaService servicio,
                             HabitacionService habitacionService,
                             EmpleadoService empleadoService) {
        this.servicio = servicio;
        this.habitacionService = habitacionService;
        this.empleadoService = empleadoService;
    }

    // GET /api/reservas -> todas (para el Jefe)
    @GetMapping
    public List<Reserva> listar() { return servicio.listarTodas(); }

    // GET /api/reservas/recepcionista/{dni} -> SOLO las de ese recepcionista
    @GetMapping("/recepcionista/{dni}")
    public List<Reserva> misReservas(@PathVariable String dni) {
        return servicio.listarPorRecepcionista(dni);
    }

    // GET /api/reservas/{dni} -> buscar por DNI del huesped
    @GetMapping("/{dni}")
    public Reserva buscar(@PathVariable String dni) {
        return servicio.buscar(dni).orElse(null);
    }

    // POST /api/reservas -> registrar
    @PostMapping
    public RespuestaApi registrar(@RequestBody ReservaRequest req) {
        // Validaciones (las mismas de tu Swing)
        if (req.getDni() == null || req.getNombres() == null || req.getApellidos() == null
                || req.getDni().isBlank() || req.getNombres().isBlank() || req.getApellidos().isBlank())
            return new RespuestaApi(false, "Error: Llene todos los campos.");
        if (!req.getNombres().matches("^[\\p{L}\\s]+$") || !req.getApellidos().matches("^[\\p{L}\\s]+$"))
            return new RespuestaApi(false, "Error: Nombres y Apellidos solo deben tener letras.");
        if (!req.getDni().matches("^[0-9]{8}$"))
            return new RespuestaApi(false, "Error: DNI debe tener 8 numeros.");

        // Buscamos el recepcionista logueado (por su DNI)
        Optional<Empleado> recep = empleadoService.buscarPorDni(req.getRecepcionistaDni());
        if (recep.isEmpty())
            return new RespuestaApi(false, "Error: recepcionista no valido.");

        // Buscamos la habitacion elegida en el catalogo dinamico
        Optional<Habitacion> hab = habitacionService.buscar(req.getNumeroHabitacion());
        if (hab.isEmpty())
            return new RespuestaApi(false, "Error: la habitacion no existe.");

        Habitacion habitacion = hab.get();
        if (req.getPersonas() > habitacion.getCapacidad())
            return new RespuestaApi(false, "Error: excede la capacidad del cuarto (" + habitacion.getCapacidad() + ").");

        Huesped huesped = new Huesped(req.getDni(), req.getNombres(), req.getApellidos(), req.getTelefono());
        Reserva reserva = new Reserva(recep.get(), huesped, habitacion,
                req.getNoches(), req.getPersonas(), req.getMetodoPago());

        servicio.insertar(reserva);
        habitacionService.marcarEstado(habitacion.getNumero(), "Ocupada"); // marca el cuarto
        return new RespuestaApi(true, "Reserva registrada. Total: S/ " + reserva.getTotalPagado());
    }

    // DELETE /api/reservas/{dni}
    @DeleteMapping("/{dni}")
    public RespuestaApi eliminar(@PathVariable String dni) {
        // Liberamos el cuarto antes de borrar
        servicio.buscar(dni).ifPresent(r ->
                habitacionService.marcarEstado(r.getHabitacion().getNumero(), "Libre"));
        boolean ok = servicio.eliminar(dni);
        return ok ? new RespuestaApi(true, "Reserva eliminada.")
                  : new RespuestaApi(false, "No se encontro reserva con ese DNI.");
    }

    // GET /api/reservas/reporte/total
    @GetMapping("/reporte/total")
    public RespuestaApi recaudacion() {
        return new RespuestaApi(true, "S/ " + servicio.calcularRecaudacionTotal());
    }
}
