package com.hotelcandelaria.repositorio;

import com.hotelcandelaria.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository te da GRATIS: save(), findAll(), deleteById(), etc.
// Spring genera el SQL solo. Los metodos de abajo Spring los "adivina"
// por el nombre (findBy... -> SELECT ... WHERE ...).
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByUsuarioAndPassword(String usuario, String password); // para el login
    Optional<Empleado> findByDni(String dni);
    boolean existsByUsuario(String usuario);
}
