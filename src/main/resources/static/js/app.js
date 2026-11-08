
const API_URL = "/api";

const formulario = document.getElementById("citaForm");
const selectorServicio = document.getElementById("servicio");
const mensaje = document.getElementById("mensaje");
const campoFecha = document.getElementById("fecha");

// Evitar seleccionar fechas anteriores a hoy
const hoy = new Date();
const fechaLocal = [
    hoy.getFullYear(),
    String(hoy.getMonth() + 1).padStart(2, "0"),
    String(hoy.getDate()).padStart(2, "0")
].join("-");

campoFecha.min = fechaLocal;

// Cargar servicios desde PostgreSQL
async function cargarServicios() {
    try {
        const respuesta = await fetch(`${API_URL}/servicios`);

        if (!respuesta.ok) {
            throw new Error("No se pudieron cargar los servicios");
        }

        const servicios = await respuesta.json();

        servicios.forEach(servicio => {
            const opcion = document.createElement("option");

            opcion.value = servicio.id;
            opcion.textContent =
                `${servicio.nombre} - Q${servicio.precio} (${servicio.duracionMinutos} min)`;

            selectorServicio.appendChild(opcion);
        });

    } catch (error) {
        mensaje.textContent = "Error al cargar los servicios.";
        console.error(error);
    }
}

// Registrar una cita
formulario.addEventListener("submit", async function(event) {
    event.preventDefault();

    mensaje.textContent = "Registrando cita...";

    const cita = {
        nombreCliente: document.getElementById("nombre").value.trim(),
        telefono: document.getElementById("telefono").value.trim(),
        fecha: campoFecha.value,
        hora: document.getElementById("hora").value,
        servicio: {
            id: Number(selectorServicio.value)
        }
    };

    if (!cita.servicio.id) {
        mensaje.textContent = "Selecciona un servicio.";
        return;
    }

    try {
        const respuesta = await fetch(`${API_URL}/citas`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(cita)
        });

        if (!respuesta.ok) {
            const error = await respuesta.text();
            throw new Error(error || "No se pudo registrar la cita");
        }

        mensaje.textContent = "¡Tu cita fue reservada correctamente!";
        formulario.reset();
        campoFecha.min = fechaLocal;

    } catch (error) {
        mensaje.textContent = error.message;
        console.error(error);
    }
});

// Iniciar la página
cargarServicios();
