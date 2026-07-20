package com.hotelcandelaria.controlador;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.hotelcandelaria.dto.HabitacionRequest;
import com.hotelcandelaria.dto.RespuestaApi;
import com.hotelcandelaria.modelo.Habitacion;
import com.hotelcandelaria.servicio.HabitacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CRUD del catalogo de habitaciones.
@RestController
@RequestMapping("/api/habitaciones")
@CrossOrigin(origins = "*")
public class HabitacionController {

    private final HabitacionService servicio;
    public HabitacionController(HabitacionService servicio) { this.servicio = servicio; }

    // GET /api/habitaciones -> listar todas
    @GetMapping
    public List<Habitacion> listar() { return servicio.listar(); }

    // POST /api/habitaciones -> agregar
    @PostMapping
    public RespuestaApi agregar(@RequestBody HabitacionRequest req) {
        if (req.getNumero() == null || req.getNumero().isBlank())
            return new RespuestaApi(false, "El numero de habitacion es obligatorio.");
        if (req.getPrecio() <= 0)
            return new RespuestaApi(false, "El precio debe ser mayor a 0.");

        Habitacion h = new Habitacion(req.getNumero(), req.getTipo(),
                req.getCapacidad(), req.getPrecio(), "Libre");
        boolean ok = servicio.agregar(h);
        return ok ? new RespuestaApi(true, "Habitacion agregada.")
                  : new RespuestaApi(false, "Ya existe una habitacion con ese numero.");
    }

    // PUT /api/habitaciones/{numero} -> modificar
    @PutMapping("/{numero}")
    public RespuestaApi modificar(@PathVariable String numero, @RequestBody HabitacionRequest req) {
        Habitacion h = new Habitacion(numero, req.getTipo(),
                req.getCapacidad(), req.getPrecio(), "Libre");
        boolean ok = servicio.modificar(numero, h);
        return ok ? new RespuestaApi(true, "Habitacion modificada.")
                  : new RespuestaApi(false, "No se encontro esa habitacion.");
    }

    // DELETE /api/habitaciones/{numero} -> eliminar
    @DeleteMapping("/{numero}")
    public RespuestaApi eliminar(@PathVariable String numero) {
        boolean ok = servicio.eliminar(numero);
        return ok ? new RespuestaApi(true, "Habitacion eliminada.")
                  : new RespuestaApi(false, "No se encontro esa habitacion.");
    }
}
