// ===================================================================
// Hotel Candelaria — App principal de React
// Login con roles + Nueva Reserva + Reservas + Habitaciones + Recepcionistas
// Se conecta al backend Spring Boot en http://localhost:8080
// ===================================================================
import { useState, useEffect } from "react";
import "./App.css";

const API = "https://pc3-1-rqjh.onrender.com/api";

// Helper: hace fetch y devuelve JSON (con manejo simple de error)
async function pedir(ruta, opciones = {}) {
  const res = await fetch(`${API}${ruta}`, {
    headers: { "Content-Type": "application/json" },
    ...opciones,
  });
  return res.json();
}

// ============================================================
// COMPONENTE RAIZ: decide si mostrar Login o la app
// ============================================================
export default function App() {
  const [usuario, setUsuario] = useState(null); // null = no logueado

  if (!usuario) return <Login onLogin={setUsuario} />;
  return <Panel usuario={usuario} onSalir={() => setUsuario(null)} />;
}

// ============================================================
// LOGIN
// ============================================================
function Login({ onLogin }) {
  const [user, setUser] = useState("");
  const [pass, setPass] = useState("");
  const [error, setError] = useState("");

  async function entrar() {
    setError("");
    try {
      const r = await pedir("/login", {
        method: "POST",
        body: JSON.stringify({ usuario: user, password: pass }),
      });
      if (r.exito) {
        onLogin(r); // guardamos los datos del recepcionista logueado
      } else {
        setError(r.mensaje);
      }
    } catch {
      setError("No se pudo conectar con el servidor. ¿El backend está prendido?");
    }
  }

  return (
    <div className="login-wrap">
      <div className="login-card">
        <div className="login-emblem">HC</div>
        <h1 className="marca">Hotel Candelaria</h1>
        <p className="sub">Acceso recepción</p>

        <div className="campo">
          <label>Usuario</label>
          <input
            value={user}
            onChange={(e) => setUser(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && entrar()}
            placeholder="ej. clopez"
          />
        </div>
        <div className="campo">
          <label>Contraseña</label>
          <input
            type="password"
            value={pass}
            onChange={(e) => setPass(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && entrar()}
            placeholder="••••"
          />
        </div>

        {error && <div className="banner banner-err">{error}</div>}

        <button className="btn btn-primary" onClick={entrar}>
          Iniciar sesión
        </button>

      </div>
    </div>
  );
}

// ============================================================
// PANEL PRINCIPAL (con pestañas según el rol)
// ============================================================
function Panel({ usuario, onSalir }) {
  const esJefe = usuario.rol === "JEFE";
  const [tab, setTab] = useState("nueva");

  const tabs = [
    { id: "nueva", txt: "Nueva reserva" },
    { id: "reservas", txt: esJefe ? "Todas las reservas" : "Mis reservas" },
    { id: "habitaciones", txt: "Habitaciones" },
  ];
  if (esJefe) tabs.push({ id: "recepcionistas", txt: "Recepcionistas" });

  return (
    <div>
      <header className="topbar">
        <span className="marca">Hotel <b>Candelaria</b></span>
        <div className="usuario-box">
          <div className="quien">
            <div className="nom">{usuario.nombreCompleto}</div>
            <span className={`badge ${esJefe ? "badge-jefe" : "badge-recep"}`}>
              {esJefe ? "Jefe de turno" : "Recepción"}
            </span>
          </div>
          <button className="btn btn-ghost" onClick={onSalir}>Salir</button>
        </div>
      </header>

      <nav className="tabs">
        {tabs.map((t) => (
          <button
            key={t.id}
            className={`tab ${tab === t.id ? "activo" : ""}`}
            onClick={() => setTab(t.id)}
          >
            {t.txt}
          </button>
        ))}
      </nav>

      <main className="contenido">
        {tab === "nueva" && <NuevaReserva usuario={usuario} />}
        {tab === "reservas" && <Reservas usuario={usuario} esJefe={esJefe} />}
        {tab === "habitaciones" && <Habitaciones esJefe={esJefe} />}
        {tab === "recepcionistas" && esJefe && <Recepcionistas />}
      </main>
    </div>
  );
}

// ============================================================
// NUEVA RESERVA
// ============================================================
function NuevaReserva({ usuario }) {
  const [habitaciones, setHabitaciones] = useState([]);
  const [msg, setMsg] = useState(null);
  const [form, setForm] = useState({
    dni: "", nombres: "", apellidos: "", telefono: "",
    numeroHabitacion: "", personas: 1, noches: 1, metodoPago: "Efectivo",
  });

  useEffect(() => {
    pedir("/habitaciones").then((h) => {
      setHabitaciones(h);
      if (h.length) set("numeroHabitacion", h[0].numero);
    });
  }, []);

  function set(campo, valor) {
    setForm((f) => ({ ...f, [campo]: valor }));
  }

  async function registrar() {
    setMsg(null);
    const r = await pedir("/reservas", {
      method: "POST",
      body: JSON.stringify({ ...form, recepcionistaDni: usuario.dni }),
    });
    setMsg({ ok: r.exito, txt: r.mensaje });
    if (r.exito) {
      setForm({ ...form, dni: "", nombres: "", apellidos: "", telefono: "" });
    }
  }

  const habSel = habitaciones.find((h) => h.numero === form.numeroHabitacion);
  const subtotal = habSel ? habSel.precio * form.noches : 0;
  const total = subtotal * 1.18;

  return (
    <div>
      <div className="seccion-head">
        <div className="eyebrow">Recepción</div>
        <h2>Registrar nueva reserva</h2>
      </div>

      {msg && (
        <div className={`banner ${msg.ok ? "banner-ok" : "banner-err"}`}>{msg.txt}</div>
      )}

      <div className="panel">
        <div className="grid-2">
          <div className="campo">
            <label>Nombres</label>
            <input value={form.nombres} onChange={(e) => set("nombres", e.target.value)} />
          </div>
          <div className="campo">
            <label>Apellidos</label>
            <input value={form.apellidos} onChange={(e) => set("apellidos", e.target.value)} />
          </div>
          <div className="campo">
            <label>DNI</label>
            <input value={form.dni} onChange={(e) => set("dni", e.target.value)} maxLength={8} />
          </div>
          <div className="campo">
            <label>Teléfono</label>
            <input value={form.telefono} onChange={(e) => set("telefono", e.target.value)} />
          </div>
          <div className="campo">
            <label>Habitación</label>
            <select value={form.numeroHabitacion} onChange={(e) => set("numeroHabitacion", e.target.value)}>
              {habitaciones.map((h) => (
                <option key={h.numero} value={h.numero}>
                  {h.numero} · {h.tipo} · cap {h.capacidad} · S/{h.precio}
                </option>
              ))}
            </select>
          </div>
          <div className="campo">
            <label>Método de pago</label>
            <select value={form.metodoPago} onChange={(e) => set("metodoPago", e.target.value)}>
              <option>Efectivo</option>
              <option>Tarjeta</option>
              <option>Yape</option>
            </select>
          </div>
          <div className="campo">
            <label>N° de personas</label>
            <input type="number" min={1} value={form.personas}
              onChange={(e) => set("personas", Number(e.target.value))} />
          </div>
          <div className="campo">
            <label>Noches</label>
            <input type="number" min={1} value={form.noches}
              onChange={(e) => set("noches", Number(e.target.value))} />
          </div>
        </div>

        <div className="total-card" style={{ marginTop: 18 }}>
          <span className="label">Total a pagar (IGV 18% incluido)</span>
          <span className="monto">S/ {total.toFixed(2)}</span>
        </div>

        <button className="btn btn-gold" onClick={registrar}>Registrar reserva</button>
      </div>
    </div>
  );
}

// ============================================================
// RESERVAS (lista). Jefe ve todas + recaudación; recepción ve las suyas.
// ============================================================
function Reservas({ usuario, esJefe }) {
  const [reservas, setReservas] = useState([]);
  const [total, setTotal] = useState(null);
  const [msg, setMsg] = useState(null);

  function cargar() {
    const ruta = esJefe ? "/reservas" : `/reservas/recepcionista/${usuario.dni}`;
    pedir(ruta).then(setReservas);
    if (esJefe) pedir("/reservas/reporte/total").then((r) => setTotal(r.mensaje));
  }
  useEffect(cargar, []);

  async function eliminar(dni) {
    const r = await pedir(`/reservas/${dni}`, { method: "DELETE" });
    setMsg({ ok: r.exito, txt: r.mensaje });
    cargar();
  }

  return (
    <div>
      <div className="seccion-head">
        <div className="eyebrow">{esJefe ? "Administración" : "Mi turno"}</div>
        <h2>{esJefe ? "Todas las reservas" : "Mis reservas"}</h2>
      </div>

      {esJefe && total && (
        <div className="total-card">
          <span className="label">Recaudación total</span>
          <span className="monto">{total}</span>
        </div>
      )}

      {msg && <div className={`banner ${msg.ok ? "banner-ok" : "banner-err"}`}>{msg.txt}</div>}

      <div className="panel" style={{ padding: 0, overflow: "hidden" }}>
        {reservas.length === 0 ? (
          <div className="vacio">Aún no hay reservas registradas.</div>
        ) : (
          <table className="tabla">
            <thead>
              <tr>
                <th>Huésped</th><th>DNI</th><th>Hab.</th><th>Noches</th>
                <th>Pago</th><th>Total</th>{esJefe && <th>Atendió</th>}<th></th>
              </tr>
            </thead>
            <tbody>
              {reservas.map((r, i) => (
                <tr key={i}>
                  <td>{r.huesped.nombres} {r.huesped.apellidos}</td>
                  <td>{r.huesped.dni}</td>
                  <td>{r.habitacion.numero} <span style={{ color: "var(--muted)" }}>{r.habitacion.tipo}</span></td>
                  <td>{r.noches}</td>
                  <td>{r.metodoPago}</td>
                  <td className="precio">S/ {r.totalPagado.toFixed(2)}</td>
                  {esJefe && <td>{r.recepcionista.nombres}</td>}
                  <td>
                    <button className="btn btn-danger" onClick={() => eliminar(r.huesped.dni)}>
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

// ============================================================
// HABITACIONES (catálogo + CRUD solo para Jefe)
// ============================================================
function Habitaciones({ esJefe }) {
  const [habitaciones, setHabitaciones] = useState([]);
  const [msg, setMsg] = useState(null);
  const [editando, setEditando] = useState(null); // numero que se edita, o null
  const vacio = { numero: "", tipo: "Simple", capacidad: 2, precio: 80 };
  const [form, setForm] = useState(vacio);

  function cargar() { pedir("/habitaciones").then(setHabitaciones); }
  useEffect(cargar, []);

  function set(c, v) { setForm((f) => ({ ...f, [c]: v })); }

  async function guardar() {
    setMsg(null);
    const esEdicion = editando !== null;
    const ruta = esEdicion ? `/habitaciones/${editando}` : "/habitaciones";
    const r = await pedir(ruta, {
      method: esEdicion ? "PUT" : "POST",
      body: JSON.stringify(form),
    });
    setMsg({ ok: r.exito, txt: r.mensaje });
    if (r.exito) { setForm(vacio); setEditando(null); cargar(); }
  }

  async function eliminar(numero) {
    const r = await pedir(`/habitaciones/${numero}`, { method: "DELETE" });
    setMsg({ ok: r.exito, txt: r.mensaje });
    cargar();
  }

  function editar(h) {
    setEditando(h.numero);
    setForm({ numero: h.numero, tipo: h.tipo, capacidad: h.capacidad, precio: h.precio });
  }

  return (
    <div>
      <div className="seccion-head">
        <div className="eyebrow">Catálogo</div>
        <h2>Habitaciones</h2>
        <p className="sub-titulo">{habitaciones.length} habitaciones registradas</p>
      </div>

      {msg && <div className={`banner ${msg.ok ? "banner-ok" : "banner-err"}`}>{msg.txt}</div>}

      {esJefe && (
        <div className="panel" style={{ marginBottom: 22 }}>
          <div className="toolbar">
            <strong>{editando ? `Editando habitación ${editando}` : "Agregar habitación"}</strong>
            {editando && (
              <button className="btn btn-ghost btn-sm" onClick={() => { setForm(vacio); setEditando(null); }}>
                Cancelar edición
              </button>
            )}
          </div>
          <div className="grid-2">
            <div className="campo">
              <label>Número</label>
              <input value={form.numero} disabled={editando !== null}
                onChange={(e) => set("numero", e.target.value)} placeholder="ej. 305" />
            </div>
            <div className="campo">
              <label>Tipo</label>
              <select value={form.tipo} onChange={(e) => set("tipo", e.target.value)}>
                <option>Simple</option><option>Doble</option><option>VIP</option>
                <option>Suite</option><option>Familiar</option>
                <option>Residencial</option><option>Penthouse</option>
              </select>
            </div>
            <div className="campo">
              <label>Capacidad</label>
              <input type="number" min={1} value={form.capacidad}
                onChange={(e) => set("capacidad", Number(e.target.value))} />
            </div>
            <div className="campo">
              <label>Precio por noche (S/)</label>
              <input type="number" min={1} value={form.precio}
                onChange={(e) => set("precio", Number(e.target.value))} />
            </div>
          </div>
          <button className="btn btn-gold" onClick={guardar}>
            {editando ? "Guardar cambios" : "Agregar habitación"}
          </button>
        </div>
      )}

      <div className="panel" style={{ padding: 0, overflow: "hidden" }}>
        <table className="tabla">
          <thead>
            <tr>
              <th>N°</th><th>Tipo</th><th>Capacidad</th><th>Precio</th><th>Estado</th>
              {esJefe && <th></th>}
            </tr>
          </thead>
          <tbody>
            {habitaciones.map((h) => (
              <tr key={h.numero}>
                <td><strong>{h.numero}</strong></td>
                <td>{h.tipo}</td>
                <td>{h.capacidad} pers.</td>
                <td className="precio">S/ {h.precio.toFixed(2)}</td>
                <td>
                  <span className={`pill ${h.estado === "Libre" ? "pill-libre" : "pill-ocupada"}`}>
                    {h.estado}
                  </span>
                </td>
                {esJefe && (
                  <td>
                    <button className="btn btn-ghost btn-sm" onClick={() => editar(h)}>Editar</button>{" "}
                    <button className="btn btn-danger" onClick={() => eliminar(h.numero)}>Eliminar</button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

// ============================================================
// RECEPCIONISTAS (solo Jefe): listar + agregar + editar + eliminar
// ============================================================
function Recepcionistas() {
  const [lista, setLista] = useState([]);
  const [msg, setMsg] = useState(null);
  const [editando, setEditando] = useState(null);
  const vacio = { dni: "", nombres: "", apellidos: "", usuario: "", password: "", rol: "RECEPCIONISTA" };
  const [form, setForm] = useState(vacio);

  function cargar() { pedir("/recepcionistas").then(setLista); }
  useEffect(cargar, []);

  function set(c, v) { setForm((f) => ({ ...f, [c]: v })); }

  async function guardar() {
    setMsg(null);
    const esEdicion = editando !== null;
    const ruta = esEdicion ? `/recepcionistas/${editando}` : "/recepcionistas";
    const r = await pedir(ruta, {
      method: esEdicion ? "PUT" : "POST",
      body: JSON.stringify(form),
    });
    setMsg({ ok: r.exito, txt: r.mensaje });
    if (r.exito) { setForm(vacio); setEditando(null); cargar(); }
  }

  async function eliminar(dni) {
    const r = await pedir(`/recepcionistas/${dni}`, { method: "DELETE" });
    setMsg({ ok: r.exito, txt: r.mensaje });
    cargar();
  }

  function editar(e) {
    setEditando(e.dni);
    setForm({ dni: e.dni, nombres: e.nombres, apellidos: e.apellidos,
      usuario: e.usuario, password: e.password, rol: e.rol });
  }

  return (
    <div>
      <div className="seccion-head">
        <div className="eyebrow">Administración</div>
        <h2>Recepcionistas</h2>
      </div>

      {msg && <div className={`banner ${msg.ok ? "banner-ok" : "banner-err"}`}>{msg.txt}</div>}

      <div className="panel" style={{ marginBottom: 22 }}>
        <div className="toolbar">
          <strong>{editando ? `Editando ${editando}` : "Agregar recepcionista"}</strong>
          {editando && (
            <button className="btn btn-ghost btn-sm" onClick={() => { setForm(vacio); setEditando(null); }}>
              Cancelar edición
            </button>
          )}
        </div>
        <div className="grid-2">
          <div className="campo">
            <label>Nombres</label>
            <input value={form.nombres} onChange={(e) => set("nombres", e.target.value)} />
          </div>
          <div className="campo">
            <label>Apellidos</label>
            <input value={form.apellidos} onChange={(e) => set("apellidos", e.target.value)} />
          </div>
          <div className="campo">
            <label>DNI</label>
            <input value={form.dni} disabled={editando !== null} maxLength={8}
              onChange={(e) => set("dni", e.target.value)} />
          </div>
          <div className="campo">
            <label>Usuario</label>
            <input value={form.usuario} onChange={(e) => set("usuario", e.target.value)} />
          </div>
          <div className="campo">
            <label>Contraseña</label>
            <input value={form.password} onChange={(e) => set("password", e.target.value)} />
          </div>
          <div className="campo">
            <label>Rol</label>
            <select value={form.rol} onChange={(e) => set("rol", e.target.value)}>
              <option value="RECEPCIONISTA">Recepcionista</option>
              <option value="JEFE">Jefe de turno</option>
            </select>
          </div>
        </div>
        <button className="btn btn-gold" onClick={guardar}>
          {editando ? "Guardar cambios" : "Agregar recepcionista"}
        </button>
      </div>

      <div className="panel" style={{ padding: 0, overflow: "hidden" }}>
        <table className="tabla">
          <thead>
            <tr><th>Nombre</th><th>DNI</th><th>Usuario</th><th>Rol</th><th></th></tr>
          </thead>
          <tbody>
            {lista.map((e) => (
              <tr key={e.dni}>
                <td>{e.nombres} {e.apellidos}</td>
                <td>{e.dni}</td>
                <td>{e.usuario}</td>
                <td>
                  <span className={`badge ${e.rol === "JEFE" ? "badge-jefe" : "badge-recep"}`}
                    style={{ color: e.rol === "JEFE" ? "" : "var(--muted)", background: e.rol === "JEFE" ? "" : "var(--line)" }}>
                    {e.rol === "JEFE" ? "Jefe de turno" : "Recepción"}
                  </span>
                </td>
                <td>
                  <button className="btn btn-ghost btn-sm" onClick={() => editar(e)}>Editar</button>{" "}
                  <button className="btn btn-danger" onClick={() => eliminar(e.dni)}>Eliminar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
