
const API = "/api/citas";
let todasLasCitas = [];

async function cargarCitas() {
    const fecha = document.getElementById("filtroFecha").value;
    const url = fecha ? `${API}/fecha/${fecha}` : API;

    try {
        const respuesta = await fetch(url);

        if (!respuesta.ok) {
            throw new Error("No se pudieron cargar las citas");
        }

        todasLasCitas = await respuesta.json();
        mostrarCitas(todasLasCitas);

    } catch (error) {
        document.getElementById("mensaje").textContent = error.message;
    }
}

function mostrarCitas(citas) {
    const tabla = document.getElementById("tablaCitas");
    const estadoSeleccionado = document.getElementById("filtroEstado").value;

    const citasFiltradas = estadoSeleccionado
        ? citas.filter(cita => cita.estado === estadoSeleccionado)
        : citas;

    tabla.innerHTML = "";

    document.getElementById("totalCitas").textContent = citas.length;

    document.getElementById("citasPendientes").textContent =
        citas.filter(cita => cita.estado === "PROGRAMADA").length;

    document.getElementById("citasAtendidas").textContent =
        citas.filter(cita => cita.estado === "ATENDIDA").length;

    const ingresos = citas
        .filter(cita => cita.estado === "PROGRAMADA")
        .reduce((total, cita) => total + Number(cita.precio || 0), 0);

    document.getElementById("ingresosEsperados").textContent =
        `Q${ingresos.toFixed(2)}`;

    if (citasFiltradas.length === 0) {
        tabla.innerHTML =
            `<tr><td colspan="8">No hay citas registradas.</td></tr>`;
        return;
    }

    citasFiltradas.forEach(cita => {
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${cita.nombreCliente}</td>
            <td>${cita.telefono}</td>
            <td>${cita.servicio?.nombre || "Servicio"}</td>
            <td>${cita.fecha}</td>
            <td>${cita.hora}</td>
            <td>Q${cita.precio}</td>
            <td>${cita.estado}</td>
            <td>
                <button onclick="cambiarEstado(${cita.id}, 'ATENDIDA')">
                    Atendida
                </button>
                <button onclick="cambiarEstado(${cita.id}, 'CANCELADA')">
                    Cancelar
                </button>
                <button onclick="eliminarCita(${cita.id})">
                    Eliminar
                </button>
            </td>
        `;

        tabla.appendChild(fila);
    });
}

async function cambiarEstado(id, estado) {
    try {
        const respuesta = await fetch(
            `${API}/${id}/estado?estado=${estado}`,
            { method: "PATCH" }
        );

        if (!respuesta.ok) {
            throw new Error("No se pudo cambiar el estado");
        }

        await cargarCitas();

    } catch (error) {
        alert(error.message);
    }
}

async function eliminarCita(id) {
    if (!confirm("¿Deseas eliminar esta cita?")) return;

    try {
        const respuesta = await fetch(`${API}/${id}`, {
            method: "DELETE"
        });

        if (!respuesta.ok) {
            throw new Error("No se pudo eliminar la cita");
        }

        await cargarCitas();

    } catch (error) {
        alert(error.message);
    }
}

function mostrarTodas() {
    document.getElementById("filtroFecha").value = "";
    document.getElementById("filtroEstado").value = "";
    cargarCitas();
}

cargarCitas();
