# Hotel Candelaria — App web (React + Spring Boot)

Sistema de reservas con login por roles, catalogo de habitaciones con CRUD
completo, gestion de recepcionistas y cada recepcionista viendo solo sus
reservas.

--- hola

## IMPORTANTE antes de correr (leer esto)

Si ya habias corrido la version anterior, BORRA los archivos .dat que esten en
la carpeta del proyecto (reservas.dat, habitaciones.dat, empleados.dat si
existen). El formato de datos cambio y los viejos ya no sirven. Al borrarlos,
el sistema arranca limpio y precarga el catalogo y los usuarios de ejemplo.

---

## 1. Backend (Spring Boot)

1. Abre la carpeta hotel-candelaria-spring en NetBeans (proyecto Maven).
2. Corre HotelCandelariaApplication.java (clic derecho -> Run File / Shift+F6).
3. Espera el mensaje de Tomcat en el puerto 8080.
4. Prueba en el navegador: http://localhost:8080/api/habitaciones
   debe mostrar el catalogo en JSON.

## 2. Frontend (React)

En tu proyecto React (hotel-frontend), reemplaza DOS archivos dentro de src:

- src/App.jsx  <- copia el contenido de ejemplo-react/App.jsx
- src/App.css  <- copia el contenido de ejemplo-react/App.css

Luego corre (en otra ventana, con el backend prendido):

    npm run dev

Abre http://localhost:5173. Veras la pantalla de login.

---

## 3. Usuarios de prueba (contrasena: 1234)

| Usuario   | Contrasena | Rol           | Permisos                              |
|-----------|------------|---------------|---------------------------------------|
| jramirez  | 1234       | Jefe de turno | Ve TODO, gestiona cuartos y personal  |
| clopez    | 1234       | Recepcion     | Solo sus reservas, registra reservas  |
| mtorres   | 1234       | Recepcion     | Solo sus reservas, registra reservas  |

Entra como jramirez para ver todas las funciones. Entra como clopez para ver
la vista limitada de recepcion.

---

## 4. Que puede hacer cada rol

Recepcionista (clopez, mtorres):
- Registrar nuevas reservas (se guardan a su nombre).
- Ver SOLO sus propias reservas.
- Ver el catalogo de habitaciones (solo lectura).

Jefe de turno (jramirez):
- Todo lo anterior, y ademas:
- Ver TODAS las reservas + la recaudacion total.
- Agregar / editar / eliminar habitaciones.
- Agregar / editar / eliminar recepcionistas.

---

## 5. Chuleta para defender ORALMENTE

ArrayList -> almacen principal en los 3 servicios. Acceso rapido por indice.

LinkedList -> historialActividad en ReservaService. addFirst() al inicio,
instantaneo en LinkedList y lento en ArrayList.

Lambda / Stream -> en todos los servicios:
- Login: filter(e -> e.getUsuario().equals(u) && e.getPassword().equals(p))
- Mis reservas: filter(r -> r.getRecepcionista().getDni().equals(dni))
- Eliminar: removeIf(...)
- Recaudacion: mapToDouble(...).sum()

Login con roles -> el backend devuelve el rol y React muestra u oculta
funciones segun ese rol.

Nota tecnica honesta: el login es de nivel academico (contrasenas en texto
plano en memoria, sin tokens ni cifrado). Para un sistema real se usaria hash
de contrasenas y autenticacion con tokens (Spring Security + JWT).

---

## 6. Siguiente paso (opcional): base de datos real

Hoy se guarda en archivos .dat. Para usar MySQL o SQL Server con JPA, las
clases del modelo se vuelven @Entity y los servicios usan JpaRepository.
