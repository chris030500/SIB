document.addEventListener("DOMContentLoaded", function () {
  inicializarFechas();
  cargarEventosInterfaz();
  getReporteJasper();
  cargarHistorialArchivosPEP();
});


// INICIALIZAR FECHAS
function inicializarFechas() {
  const hoy = new Date().toISOString().split("T")[0];
  document.getElementById("fechaInicio").value = hoy;
  document.getElementById("fechaFin").value = hoy;
}

// VERIFICAR ESTADO DEL REPORTE
//function verificarEstadoReporte() {
//  fetch("/estadoReporte")
//    .then((response) => response.json())
 //   .then((data) => {
  //    document.getElementById("btnProcesar").disabled = !data.reporteDisponible;
  //  });
//}

// EVENTOS UI

function cargarEventosInterfaz() {
 document.getElementById("btnSubirArchivo").addEventListener("click", subirArchivoPEP);
  document.getElementById("btnGuardarRango").addEventListener("click", guardarFechas);
  document.getElementById("btnProcesar").addEventListener("click", generarReporte);
}

// SUBIR ARCHIVO PEP
function subirArchivoPEP() {
  const input = document.getElementById("archivoPEP");
  const archivo = input.files[0];

  const mensaje = document.getElementById("mensajePEP");
  mensaje.classList.add("d-none");
  mensaje.classList.remove("alert-success", "alert-danger");

  if (!archivo) {
    mostrarMensaje("Debe seleccionar un archivo.", false);
    return;
  }

  if (!/^peps_2025_.*\.xlsx$/.test(archivo.name)) {
    mostrarMensaje("El archivo debe llamarse: peps_2025_*.xlsx", false);
    return;
  }

  const formData = new FormData();
  formData.append("archivo", archivo);

  fetch("/subirArchivoPEP", {
    method: "POST",
    body: formData
  })
    .then(res => res.json())
    .then(data => {
      if (data.success) {
        mostrarMensaje("Archivo cargado exitosamente.", true);
        document.getElementById("archivoCargado").value = "true";
      } else {
        mostrarMensaje("Error al subir el archivo: " + data.message, false);
      }
    })
    .catch(() => {
      mostrarMensaje("Ocurrió un error en la carga del archivo.", false);
    });
}

function mostrarMensaje(texto, exito) {
  const mensaje = document.getElementById("mensajePEP");
  mensaje.textContent = texto;
  mensaje.classList.remove("d-none");
  mensaje.classList.add(exito ? "alert-success" : "alert-danger");
}

//CARGAR HISTORIAL
function cargarHistorialArchivosPEP() {
  const lista = document.getElementById("listaArchivosPEP");
  lista.innerHTML = "";

  fetch("/historialArchivosPEP")
    .then(res => res.json())
    .then(data => {
      if (data.length === 0) {
        lista.innerHTML = '<li class="list-group-item text-muted">No hay archivos cargados.</li>';
        return;
      }

      data.forEach(item => {
        const li = document.createElement("li");
        li.classList.add("list-group-item", "d-flex", "justify-content-between", "align-items-center");
        li.innerHTML = `
          <span>${item.nombre} - ${item.fechaSubida} - ${item.usuario}</span>
          <a href="${item.urlDescarga}" class="btn btn-sm btn-outline-secondary" download>Descargar</a>
        `;
        lista.appendChild(li);
      });
    })
    .catch(() => {
      lista.innerHTML = '<li class="list-group-item text-danger">Error al cargar el historial.</li>';
    });
}



// GUARDAR FECHAS
function guardarFechas() {
  const fechaInicio = document.getElementById("fechaInicio").value;
  const fechaFin = document.getElementById("fechaFin").value;

  const aviso = document.getElementById("avisoRangoFechas");
  const error = document.getElementById("errorRangoFechas");

  aviso.classList.add("d-none");
  error.classList.add("d-none");

  const hoy = new Date();
  const haceDosAnios = new Date();
  haceDosAnios.setFullYear(hoy.getFullYear() - 2);

  if (new Date(fechaInicio) < haceDosAnios) {
    error.classList.remove("d-none");
    return;
  }
  
  /*const archivoCargado = document.getElementById("archivoCargado").value;
  if (archivoCargado !== "true") {
    alert("Debe cargar el archivo PEP antes de continuar.");
    return;
  }*/

  fetch("/guardarFechas", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ fechaInicio, fechaFin })
  })
    .then(res => res.json())
    .then(data => {
      if (data.success) {
        mostrarAvisoRango(true);
        document.getElementById("btnProcesar").disabled = false;
      } else {
        alert("Error al guardar fechas.");
      }
    });
  aviso.classList.remove("d-none");
}

function mostrarAvisoRango(valido) {
  document.getElementById("avisoRangoFechas")?.classList.toggle("d-none", !valido);
  document.getElementById("errorRangoFechas")?.classList.toggle("d-none", valido);
}

// GENERAR REPORTE
function generarReporteClientesOpe() {
  const url = getAbsolutePath() + "generarRptReporteClientesOpe";

  // Reemplaza estos valores por los que tomes desde tus inputs
  const fechaInicio = document.getElementById("fechaInicio").value;
  const fechaFin = document.getElementById("fechaFin").value;

  const formData = new URLSearchParams();
  formData.append("FechaInicio", fechaInicio);
  formData.append("FechaFin", fechaFin);

  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: formData.toString()
  })
    .then(response => response.json())
    .then(data => {
      if (Array.isArray(data) && data.length > 0) {
        const reporteURL = data[0];
        window.open(reporteURL, "_blank"); // Abrir el reporte en una nueva pestaña
      } else {
        alert("No se recibió la URL del reporte.");
      }
    })
    .catch(error => {
      console.error("Error al generar el reporte:", error);
      alert("Error al generar el reporte.");
    });
}


function generarReporte() {
  const btn = document.getElementById("btnProcesar");
  const texto = document.getElementById("progresoTexto");

  bloquearInterfaz(true);
  texto.textContent = "Procesando reporte...";

  fetch("/generarReporte", { method: "POST" })
    .then(res => res.json())
    .then(data => {
      if (data.success) {
        texto.textContent = "Reporte generado correctamente.";
        document.getElementById("btnDescargar").disabled = false;
        document.getElementById("infoSi").disabled = false;
        document.getElementById("infoNo").disabled = false;
        document.getElementById("mensajeAviso").classList.remove("d-none");
      } else {
        texto.textContent = "Error al generar reporte.";
      }
    })
    .catch(() => {
      texto.textContent = "Error en el procesamiento.";
    })
    .finally(() => {
      bloquearInterfaz(false);
    });
}


// CONFIRMAR REPORTE DESDE BOTONES
function confirmarManual(opcion) {
  if (opcion === 'si') {
    fetch("/confirmarReporte", {
      method: "POST"
    })
      .then(res => res.json())
      .then(data => {
        if (data.success) {
          alert("Reporte confirmado. Disponible en pestaña de descargas.");
          actualizarTablaDescargas();
          new bootstrap.Tab(document.querySelector('#descargas-tab')).show();
        } else {
          alert("Error al confirmar reporte.");
        }
      });
  } else {
    new bootstrap.Tab(document.querySelector('#archivos-tab')).show();
  }
  document.getElementById("confirmacionBloque").classList.add("d-none");
}

// ACTUALIZAR TABLA DE DESCARGAS
function actualizarTablaDescargas() {
  fetch("/reportesDescargas")
    .then(res => res.json())
    .then(data => {
      const tabla = document.getElementById("tablaDescargas");
      tabla.innerHTML = "";

      const añoActual = new Date().getFullYear();

      data.reportes.forEach(reporte => {
        const fecha = new Date(reporte.fecha);
        if (fecha.getFullYear() === añoActual) {
          const fila = document.createElement("tr");
          fila.innerHTML = `
            <td>${reporte.nombre}</td>
            <td>${reporte.fecha}</td>
            <td><a href="/descargarReporte?id=${reporte.id}" class="btn btn-sm btn-outline-primary">Descargar</a></td>
          `;
          tabla.appendChild(fila);
        }
      });
    });
}

// BLOQUEAR UI
function bloquearInterfaz(bloquear) {
  ["btnProcesar", "btnSubirArchivo", "btnGuardarFechas"].forEach(id => {
    const btn = document.getElementById(id);
    if (btn) btn.disabled = bloquear;
  });
}

document.addEventListener('DOMContentLoaded', () => {
  getReporteJasper();
});
