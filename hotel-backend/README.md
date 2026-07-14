# Hotel Candelaria — Backend Spring Boot + SQL Server (SSMS)

Version conectada a TU SQL Server (el mismo donde tienes Northwind), usando
autenticacion de Windows (sin usuario ni contrasena).

===================================================================
DATOS DE CONEXION YA CONFIGURADOS
===================================================================
  Servidor: localhost\SQLEXPRESS
  Base de datos: hotel_candelaria (ya la creaste en SSMS)
  Autenticacion: de Windows (tu cuenta KENNY\kenny), SIN contrasena

===================================================================
1. BACKEND (Spring Boot)
===================================================================

1. Abre la carpeta hotel-candelaria-spring en NetBeans (proyecto Maven).
2. La primera vez baja las librerias de JPA y el driver de SQL Server
   (necesitas internet).
3. Corre HotelCandelariaApplication (Shift+F6).
4. JPA crea las tablas solo en hotel_candelaria y precarga los datos de
   ejemplo (recepcionistas y catalogo de habitaciones).
5. Prueba en el navegador: http://localhost:8080/api/habitaciones

Para VER las tablas: abre SSMS, expande hotel_candelaria > Tablas.
Deberias ver: empleados, habitaciones, huespedes, reservas.
(Si no aparecen de inmediato, click derecho en "Tablas" > Actualizar).

===================================================================
2. SI TE SALE ERROR DE CONEXION (autenticacion de Windows)
===================================================================

La autenticacion de Windows a veces necesita un archivo adicional
llamado sqljdbc_auth.dll para funcionar. Si al correr el backend ves
un error mencionando "authentication" o "Integrated Security":

  1. Busca el archivo sqljdbc_auth.dll — viene dentro del .jar del
     driver que Maven descargo, en tu carpeta:
     C:\Users\kenny\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\
     (busca dentro del .jar, carpeta auth\x64\)
  2. Cópialo a: C:\Windows\System32
  3. Vuelve a correr el backend.

  Alternativa mas simple si esto da problemas: cambiar a autenticacion
  SQL Server con usuario y contrasena en vez de Windows. Avisame y te
  reconfiguro application.properties para esa opcion.

===================================================================
3. FRONTEND (React) — sin cambios
===================================================================

En hotel-frontend/src reemplaza:
  - src/App.jsx  <- ejemplo-react/App.jsx
  - src/App.css  <- ejemplo-react/App.css

Corre: npm run dev   y abre http://localhost:5173

===================================================================
4. USUARIOS DE PRUEBA (contrasena: 1234)
===================================================================
  jramirez / 1234  -> Jefe de turno
  clopez   / 1234  -> Recepcion
  mtorres  / 1234  -> Recepcion

===================================================================
5. CHULETA PARA LA SUSTENTACION
===================================================================

Que cambio vs MySQL: solo el "conector" (driver) y la cadena de
conexion. El codigo Java (entidades @Entity, repositorios JPA,
servicios con ArrayList/LinkedList/lambdas) es IDENTICO. Eso demuestra
que la arquitectura por capas funciona con cualquier base de datos sin
tocar la logica de negocio.

ArrayList, LinkedList, Lambdas: se mantienen igual que en las
versiones anteriores (ver README anterior o la Chuleta de Sustentacion).

Autenticacion de Windows: en vez de usuario/contrasena, SQL Server usa
la sesion de Windows para validar el acceso. Es comun en entornos
corporativos/educativos donde el servidor y el cliente estan en la
misma red de dominio.
