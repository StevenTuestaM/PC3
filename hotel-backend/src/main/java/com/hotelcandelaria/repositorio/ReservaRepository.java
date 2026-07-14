package com.hotelcandelaria.repositorio;

import com.hotelcandelaria.modelo.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // findBy + propiedad anidada (recepcionista.dni) -> usa el guion bajo
    List<Reserva> findByRecepcionista_Dni(String dni); // "mis reservas"
    List<Reserva> findByHuesped_Dni(String dni);       // buscar por huesped
}
