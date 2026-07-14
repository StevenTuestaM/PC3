package com.hotelcandelaria.repositorio;

import com.hotelcandelaria.modelo.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

// La llave es String (el numero de habitacion)
public interface HabitacionRepository extends JpaRepository<Habitacion, String> {
}
