package com.hotelcandelaria.repositorio;

import com.hotelcandelaria.modelo.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByDni(String dni); // para reutilizar el huesped si ya existe
}
