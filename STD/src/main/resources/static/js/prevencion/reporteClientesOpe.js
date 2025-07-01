// @ts-nocheck
document.addEventListener("DOMContentLoaded", () => {
  const inputInicio = document.getElementById("fechaInicio");
  const inputFin    = document.getElementById("fechaFin");

  document.getElementById("btnProcesar").addEventListener("click", async () => {
  const respuesta = await fetch("/procesar-reporte", {
    method: "POST"
  });

  if (respuesta.ok) {
    alert("Reporte registrado exitosamente");
    location.reload(); // Actualizas la tabla de descargas
  } else {
    alert("Error al registrar reporte");
  }
});

  const btnProcesarProceso = document.getElementById("btnProcesarProceso");
  btnProcesarProceso.addEventListener("click", async (event) => {
    event.preventDefault();
    const inicio = inputInicio.value;
    const fin    = inputFin.value;

    if (fechasInvalidas(inicio, fin)) return;
    await descargaRptOperacionesAuditoria(inicio, fin);
  });
const btnProcesarAuditoria = document.getElementById("btnProcesarAuditoria");

btnProcesarAuditoria.addEventListener("click", async () => {
    try {
        const response = await fetch("/auditoria/actualiza-peps", {
            method: "POST"
        });

        if (response.ok) {
            alert("SP ejecutado correctamente, ahora puedes generar el reporte.");
            document.getElementById("btnProcesar").disabled = false;
            document.getElementById("btnProcesarProceso").disabled = false;
        } else {
            alert("Error al ejecutar el SP");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Ocurrió un error al procesar la auditoría");
    }
});
});

// Validación de fechas
function fechasInvalidas(inicio, fin) {
  if (!inicio || !fin) {
    alert("Debes seleccionar la fecha de inicio y la fecha de fin.");
    return true;
  }
  if (fin < inicio) {
    alert("La fecha fin no puede ser anterior a la fecha inicio.");
    return true;
  }
  return false;
}

// Descargar reporte clientes
async function descargaRptClientesOpe(inicio, fin) {
  try {
    const res = await fetch("generarRptReporteClientesOpeController", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin })
    });

    if (!res.ok) throw new Error(`Error en la petición: ${res.status}`);

    const data = await res.json();
    const rutaReporte = data[0];
    if (!rutaReporte) throw new Error("No se recibió la ruta del reporte.");

    abrirReporte(rutaReporte);

  } catch (error) {
    console.error("Error al generar el reporte de clientes:", error);
    alert(error.message || "Se produjo un error al generar el reporte.");
  }
}

// Descargar reporte operaciones auditoría
async function descargaRptOperacionesAuditoria(inicio, fin) {
  try {
    const res = await fetch("generarRptOperacionesAuditoria", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin })
    });

    if (!res.ok) throw new Error(`Error en la petición: ${res.status}`);

    const data = await res.json();
    const rutaReporte = data[0];
    if (!rutaReporte) throw new Error("No se recibió la ruta del reporte de operaciones auditoría.");

    abrirReporte(rutaReporte);

  } catch (error) {
    console.error("Error al generar el reporte de operaciones auditoría:", error);
    alert(error.message || "Se produjo un error al generar el reporte de operaciones auditoría.");
  }
}

async function cargarHistorial() {
  const resp = await fetch("/historial-reportes");
  const data = await resp.json();

  const tabla = document.getElementById("tablaDescargas");
  tabla.innerHTML = "";
  data.forEach(item => {
    const fila = 
      <tr>
        <td>${item.id}</td>
        <td>${new Date(item.fechaGeneracion).toLocaleString()}</td>
        <td>${item.usuario}</td>
        <td>${item.nombreArchivo}</td>
        <td>${item.confirmado ? "Sí" : "No"}</td>
      </tr>;
    tabla.innerHTML += fila;
  });
}

document.addEventListener("DOMContentLoaded", cargarHistorial);


//  Función para abrir reporte
function abrirReporte(rutaReporte) {
  var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + rutaReporte;
  window.open(urlReporte);
  window.focus();
}