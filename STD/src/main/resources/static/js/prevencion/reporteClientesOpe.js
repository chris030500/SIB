
// @ts-nocheck
/* -------- SUBIR ARCHIVO -------- */
document.getElementById("uploadFormPEP").addEventListener("submit", async e => {
  e.preventDefault();
  const file = document.getElementById("fileUploadPEP").files[0];
  if (!file) { alert("Selecciona un archivo"); return; }

  const fd = new FormData();
  fd.append("file", file);           //  << nombre EXACTO que espera el controller

  const res  = await fetch(e.target.action, { method: "POST", body: fd });
  const text = await res.text();
  alert(text);
});


document.addEventListener("DOMContentLoaded", () => {
  const btnProcesar = document.getElementById("btnProcesar");
  const inputInicio = document.getElementById("fechaInicio");
  const inputFin    = document.getElementById("fechaFin");

  // Validación simple de fechas
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

  // Evento click del botón
  btnProcesar.addEventListener("click", async (event) => {
    event.preventDefault(); // ← importante si el botón está en un formulario

    const inicio = inputInicio.value;
    const fin    = inputFin.value;

    if (fechasInvalidas(inicio, fin)) return;
	descargaRptClientesOpe(inicio, fin);
  
  });
});


async function descargaRptClientesOpe(inicio, fin) {
  try {
    const res = await fetch("generarRptReporteClientesOpeController", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin })
    });

    if (!res.ok) {
      throw new Error(`Error en la petición: ${res.status}`);
    }

    // obtener repuesta
    const data = await res.json();        
    console.log('Array JSON:', data);

    // obtener el primer elemento de la respuesta
    const rutaReporte = data[0];
    if (!rutaReporte) {
      throw new Error("No se recibió la ruta del reporte.");
    }

    // 3️⃣ Abrir el reporte en ventana nueva
    var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + rutaReporte;

    window.open(urlReporte);
    window.focus();

  } catch (error) {
    console.error("Error al generar el reporte:", error);
    alert(error.message || "Se produjo un error al generar el reporte.");
  }
}


// -----------------------------------------------------------------------------------------------------------


// --------------------------------------------------------------------------------------------------------------

//document.getElementById("programado").addEventListener("change", e => {
  //  document.getElementById("horaProgDiv").style.display = e.target.value === "true" ? "block" : "none";
//});

/*document.getElementById("formProceso").addEventListener("submit", function(e) {
    e.preventDefault();
    const data = {
        fechaInicio: document.getElementById("fechaInicio").value,
        fechaFin: document.getElementById("fechaFin").value,
       // programado: document.getElementById("programado").value === "true",
        horaEjecucion: document.getElementById("horaEjecucion").value
    };
    fetch("/prevencion/reporteClientesOpe/guardarFechas", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    }).then(r => r.text()).then(alert);
});*/


function confirmar(valor) {
    fetch(`/prevencion/reporteClientesOpe/confirmar?confirmado=${valor}`, { method: "POST" })
        .then(r => {
            if (r.redirected) window.location.href = r.url;
            else return r.text().then(alert);
        });
}

/* -------- AJUSTE EN TABLA DESCARGAS (id correcto) -------- */
function cargarDescargas() {
  fetch("/prevencion/reporteClientesOpe/descargas")
    .then(r => r.json())
    .then(data => {
      const tbody = document.getElementById("tablaReportesGenerados");   // ← id real
      tbody.innerHTML = "";
      data.forEach(row => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${row.fechaGeneracion}</td>
          <td>${row.rangoFechas||""}</td>
          <td>${row.usuario||""}</td>
          <td>${row.nombreArchivo}</td>
          <td>${row.confirmado?"Sí":"No"}</td>
          <td><a class='btn btn-sm btn-primary'
                 href='/prevencion/reporteClientesOpe/descargar/${row.id}'>Descargar</a></td>`;
        tbody.appendChild(tr);
      });
    });
}
document.getElementById("descargas-tab")
        .addEventListener("click", cargarDescargas);