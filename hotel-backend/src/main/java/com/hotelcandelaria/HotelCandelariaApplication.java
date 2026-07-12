package com.hotelcandelaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ====================================================================
// EL PUNTO DE ARRANQUE. Reemplaza al "public static void main" que
// abria la ventana Swing. Ahora, en vez de abrir una ventana, levanta
// un SERVIDOR WEB (Tomcat) en el puerto 8080 esperando peticiones.
//
// @SpringBootApplication activa toda la magia de Spring: busca tus
// @Service, @RestController, etc. y los conecta automaticamente.
// ====================================================================
@SpringBootApplication
public class HotelCandelariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelCandelariaApplication.class, args);
        System.out.println(">>> Hotel Candelaria API corriendo en http://localhost:8080/api/reservas");
    }
}
