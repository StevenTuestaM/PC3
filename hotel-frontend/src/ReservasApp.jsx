// ====================================================================
// EJEMPLO de componente React que se conecta a tu backend Spring Boot.
// Pegalo en tu proyecto React (src/) y ajusta el diseño a tu gusto.
// Tu backend debe estar corriendo en http://localhost:8080
// ====================================================================
import { useState, useEffect } from "react";

const API = "http://localhost:8080/api/reservas"; // URL de tu backend

export default function ReservasApp() {
  const [reservas, setReservas] = useState([]); // lista que pintamos en pantalla
  const [form, setForm] = useState({            // datos del formulario
    dni: "", nombres: "", apellidos: "", telefono: "",
    recepcionistaIndex: 0, tipoHabIndex: 0,
    personas: 1, noches: 1, metodoPago: "Efectivo",
  });

  // Al cargar la pagina, traemos todas las reservas (GET)
  useEffect(() => { cargarReservas(); }, []);

  function cargarReservas() {
    fetch(API)                       // llama GET /api/reservas
      .then((res) => res.json())     // convierte la respuesta a objeto JS
      .then((data) => setReservas(data)); // la pintamos
  }

  // Registrar una reserva (POST)
  function registrar() {
    fetch(API, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),    // mandamos el formulario como JSON
    })
      .then((res) => res.json())
      .then((resp) => {
        alert(resp.mensaje);         // mostramos el mensaje del backend
        cargarReservas();            // refrescamos la lista
      });
  }

  // Eliminar por DNI (DELETE)
  function eliminar(dni) {
    fetch(`${API}/${dni}`, { method: "DELETE" })
      .then((res) => res.json())
      .then((resp) => { alert(resp.mensaje); cargarReservas(); });
  }

  // Helper para actualizar el formulario
  function set(campo, valor) {
    setForm({ ...form, [campo]: valor });
  }

  return (
    <div style={{ maxWidth: 700, margin: "0 auto", fontFamily: "Arial" }}>
      <h1>Hotel Candelaria</h1>

      <h2>Registrar reserva</h2>
      <input placeholder="DNI" value={form.dni}
        onChange={(e) => set("dni", e.target.value)} />
      <input placeholder="Nombres" value={form.nombres}
        onChange={(e) => set("nombres", e.target.value)} />
      <input placeholder="Apellidos" value={form.apellidos}
        onChange={(e) => set("apellidos", e.target.value)} />
      <input placeholder="Telefono" value={form.telefono}
        onChange={(e) => set("telefono", e.target.value)} />

      <select value={form.tipoHabIndex}
        onChange={(e) => set("tipoHabIndex", Number(e.target.value))}>
        <option value={0}>305 - Familiar</option>
        <option value={1}>202 - Matrimonial</option>
      </select>

      <input type="number" placeholder="Personas" value={form.personas}
        onChange={(e) => set("personas", Number(e.target.value))} />
      <input type="number" placeholder="Noches" value={form.noches}
        onChange={(e) => set("noches", Number(e.target.value))} />

      <select value={form.metodoPago}
        onChange={(e) => set("metodoPago", e.target.value)}>
        <option>Efectivo</option>
        <option>Tarjeta</option>
        <option>Yape</option>
      </select>

      <button onClick={registrar}>REGISTRAR</button>

      <h2>Reservas registradas</h2>
      <ul>
        {reservas.map((r, i) => (
          <li key={i}>
            {r.huesped.nombres} {r.huesped.apellidos} — Hab {r.habitacion.numeroHabitacion} —
            S/{r.totalPagado}
            <button onClick={() => eliminar(r.huesped.dni)}>X</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
