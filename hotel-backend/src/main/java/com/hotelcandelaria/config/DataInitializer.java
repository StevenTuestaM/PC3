package com.hotelcandelaria.config;

import com.hotelcandelaria.modelo.Empleado;
import com.hotelcandelaria.modelo.Habitacion;
import com.hotelcandelaria.repositorio.EmpleadoRepository;
import com.hotelcandelaria.repositorio.HabitacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component + CommandLineRunner: Spring ejecuta este "run" UNA vez al
// arrancar. Si las tablas estan vacias, precarga los datos de ejemplo
// (catalogo de habitaciones + los 3 recepcionistas). Si ya hay datos,
// no toca nada (para no duplicar al reiniciar).
@Component
public class DataInitializer implements CommandLineRunner {

    private final EmpleadoRepository empleadoRepo;
    private final HabitacionRepository habitacionRepo;

    public DataInitializer(EmpleadoRepository e, HabitacionRepository h) {
        this.empleadoRepo = e;
        this.habitacionRepo = h;
    }

    @Override
    public void run(String... args) {
        // ---- Recepcionistas de ejemplo (contrasena: 1234) ----
        if (empleadoRepo.count() == 0) {
            empleadoRepo.save(new Empleado("78451236", "Carlos", "Lopez", "clopez", "1234", "RECEPCIONISTA", "Recepcionista"));
            empleadoRepo.save(new Empleado("65432187", "Maria", "Torres", "mtorres", "1234", "RECEPCIONISTA", "Recepcionista"));
            empleadoRepo.save(new Empleado("74123698", "Jose", "Ramirez", "jramirez", "1234", "JEFE", "Jefe de Turno"));
            System.out.println(">>> Recepcionistas de ejemplo creados.");
        }

        // ---- Catalogo de habitaciones (igual a la foto de referencia) ----
        if (habitacionRepo.count() == 0) {
            habitacionRepo.save(new Habitacion("101", "Simple", 2, 80.0, "Libre"));
            habitacionRepo.save(new Habitacion("102", "Simple", 2, 80.0, "Libre"));
            habitacionRepo.save(new Habitacion("103", "Doble", 2, 120.0, "Libre"));
            habitacionRepo.save(new Habitacion("104", "Doble", 2, 120.0, "Libre"));
            habitacionRepo.save(new Habitacion("201", "VIP", 4, 180.0, "Libre"));
            habitacionRepo.save(new Habitacion("202", "VIP", 4, 180.0, "Libre"));
            habitacionRepo.save(new Habitacion("203", "VIP", 4, 180.0, "Libre"));
            habitacionRepo.save(new Habitacion("301", "Suite", 5, 280.0, "Libre"));
            habitacionRepo.save(new Habitacion("302", "Suite", 6, 280.0, "Libre"));
            habitacionRepo.save(new Habitacion("303", "Suite", 6, 280.0, "Libre"));
            habitacionRepo.save(new Habitacion("401", "Familiar", 6, 220.0, "Libre"));
            habitacionRepo.save(new Habitacion("402", "Familiar", 6, 220.0, "Libre"));
            habitacionRepo.save(new Habitacion("501", "Residencial", 10, 400.0, "Libre"));
            habitacionRepo.save(new Habitacion("502", "Residencial", 10, 400.0, "Libre"));
            habitacionRepo.save(new Habitacion("601", "Penthouse", 8, 550.0, "Libre"));
            habitacionRepo.save(new Habitacion("602", "Penthouse", 8, 550.0, "Libre"));
            System.out.println(">>> Catalogo de habitaciones creado.");
        }
    }
}
