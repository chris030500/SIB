// @ts-nocheck
document.addEventListener("DOMContentLoaded", () => {
  const inputInicio = document.getElementById("fechaInicio");
  const inputFin    = document.getElementById("fechaFin");

  const btnProcesar = document.getElementById("btnProcesar");
  btnProcesar.addEventListener("click", async (event) => {
    event.preventDefault();
    const inicio = inputInicio.value;
    const fin    = inputFin.value;

    if (fechasInvalidas(inicio, fin)) return;
    await descargaRptClientesOpe(inicio, fin);
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

    btnProcesarAuditoria.addEventListener("click", async (event) => {
        event.preventDefault();

        try {
            const response = await fetch("/reporteClientesOpe/procesarAuditoria", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            const result = await response.text();
            if (result === "OK") {
                alert("Se procesó correctamente el reporte de auditoría");
            } else {
                alert("Error al procesar: " + result);
            }
        } catch (error) {
            alert("Error en la petición: " + error);
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

//  Función para abrir reporte
function abrirReporte(rutaReporte) {
  var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + rutaReporte;
  window.open(urlReporte);
  window.focus();
}