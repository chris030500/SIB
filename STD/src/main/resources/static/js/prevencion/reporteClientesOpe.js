// @ts-nocheck
var url = "";
var tblHistorico;
 
$(document).ready(function() {
tblHistorico = $("#tblHistorico").DataTable({
    language: {
        processing:     "Procesando...",
        search:         "Buscar:",
        lengthMenu:     "Mostrar _MENU_ registros",
        info:           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
        infoEmpty:      "Mostrando registros del 0 al 0 de un total de 0",
        infoFiltered:   "(filtrado de un total de _MAX_ registros)",
        infoPostFix:    "",
        loadingRecords: "Cargando...",
        zeroRecords:    "No se encontraron resultados",
        emptyTable:     "Ningún dato disponible en esta tabla",
        paginate: {
            first:      "Primero",
            previous:   "Anterior",
            next:       "Siguiente",
            last:       "Último"
        },
        aria: {
            sortAscending:  ": Activar para ordenar la columna de manera ascendente",
            sortDescending: ": Activar para ordenar la columna de manera descendente"
        }
    },
    order: [[0, "desc"]], // Opcional: ordenar por la primera columna en orden descendente
    aoColumnDefs: [{
        bSortable: false,
        aTargets: [-1] // La última columna no es ordenable
    }]
});
 
  iniciaTblHistorico();
});
 
/* -------- SUBIR ARCHIVO -------- */
/*document.getElementById("uploadFormPEP").addEventListener("submit", async e => {
  e.preventDefault();
  const file = document.getElementById("fileUploadPEP").files[0];
  if (!file) { alert("Selecciona un archivo"); return; }
 
  const fd = new FormData();
  fd.append("file", file);
 
  const res = await fetch(e.target.action, { method: "POST", body: fd });
  const text = await res.text();
  alert(text);
});*/
 
document.addEventListener("DOMContentLoaded", () => {
  const btnProcesarAuditoria = document.getElementById("btnProcesarAuditoria");
  const btnProcesarProceso = document.getElementById("btnProcesarProceso");
  const btnProcesar = document.getElementById("btnProcesar");
  const inputInicio = document.getElementById("fechaInicio");
  const inputFin = document.getElementById("fechaFin");
 
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
 
// Evento click del botón para generar el reporte
/*btnProcesar.addEventListener("click", async (event) => {
  event.preventDefault();

  const inicio = inputInicio.value;
  const fin = inputFin.value;

  if (fechasInvalidas(inicio, fin)) return;

  btnProcesar.disabled = true;
  btnProcesar.textContent = "Generando...";

  // Mostrar alerta con spinner
  swal({
    title: "Generando reporte...",
    content: {
      element: "div",
      attributes: {
        innerHTML:
          '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
        style:
          "display: flex; justify-content: center; align-items: center; padding: 20px;",
      },
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false,
  });

  try {
    const res = await fetch(
      `download?fechaInicio=${encodeURIComponent(inicio)}&fechaFin=${encodeURIComponent(fin)}`
    );

    if (!res.ok) throw new Error("Error generando el reporte.");

    // Convertir la respuesta en blob (archivo binario)
    const blob = await res.blob();
    const url = window.URL.createObjectURL(blob);

    // Descargar con nombre dinámico
    const a = document.createElement("a");
    a.href = url;
    a.download = `Reporte_Clientes_Operaciones_${inicio}_a_${fin}.xlsx`;
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);

    swal.close();
    swal({
      title: "¡Éxito!",
      text: "El reporte se descargó correctamente",
      icon: "success",
      button: "Aceptar",
    });
  } catch (error) {
    swal.close();
    console.error("❌ Error al generar el reporte:", error);
    swal({
      title: "Error",
      text: "Se produjo un error al generar el reporte.",
      icon: "error",
      button: "Aceptar",
    });
  } finally {
    btnProcesar.disabled = false;
    btnProcesar.textContent = "Generar Reporte";
  }
});
});*/

 // Evento click del botón para generar el reporte
btnProcesar.addEventListener("click", async (event) => {
  event.preventDefault();

  const inicio = inputInicio.value;
  const fin = inputFin.value;

  if (fechasInvalidas(inicio, fin)) return;

  btnProcesar.disabled = true;
  btnProcesar.textContent = "Generando...";

  // Mostrar alerta con spinner
  swal({
    title: "Generando reporte...",
    content: {
      element: "div",
      attributes: {
        innerHTML:
          '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
        style:
          "display: flex; justify-content: center; align-items: center; padding: 20px;",
      },
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false,
  });

  try {
    const res = await fetch("generarRptReporteClientesOpeController", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin }),
    });

    if (!res.ok) throw new Error("Error generando el reporte.");

    const respuesta = await res.json();
    url = respuesta[0];
    window.open(
      window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0]
    );
    window.focus();

    swal.close();
    swal({
      title: "¡Éxito!",
      text: "El reporte se generó correctamente",
      icon: "success",
      button: "Aceptar",
    });
  } catch (error) {
    swal.close();
    console.error("Error al generar el reporte:", error);
    swal({
      title: "Error",
      text: "Se produjo un error al generar el reporte.",
      icon: "error",
      button: "Aceptar",
    });
  } finally {
    btnProcesar.disabled = false;
    btnProcesar.textContent = "Generar Reporte";
  }
});

// Evento click del botón para generar proceso
btnProcesarProceso.addEventListener("click", async (event) => {
  event.preventDefault();

  const inicio = inputInicio.value;
  const fin = inputFin.value;

  if (fechasInvalidas(inicio, fin)) return;

  btnProcesarProceso.disabled = true;
  btnProcesarProceso.textContent = "Procesando...";

  swal({
    title: "Procesando reporte...",
    content: {
      element: "div",
      attributes: {
        innerHTML:
          '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
        style:
          "display: flex; justify-content: center; align-items: center; padding: 20px;",
      },
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false,
  });

  try {
    const res = await fetch("generarRptOperacionesAuditoria", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin }),
    });

    if (!res.ok) throw new Error("Error generando el reporte.");

    const respuesta = await res.json();
    url = respuesta[0];
    window.open(
      window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0]
    );
    window.focus();

    swal.close();
    swal({
      title: "¡Éxito!",
      text: "El reporte se generó correctamente",
      icon: "success",
      button: "Aceptar",
    });
  } catch (error) {
    swal.close();
    console.error("Error al generar el reporte:", error);
    swal({
      title: "Error",
      text: "Se produjo un error al generar el reporte.",
      icon: "error",
      button: "Aceptar",
    });
  } finally {
    btnProcesarProceso.disabled = false;
    btnProcesarProceso.textContent = "Generar Proceso";
  }
});
});
 
// Evento para cuando se selecciona "No"
$('#infoNo').on('change', function() {
  if ($(this).is(':checked')) {
    swal({
      title: "¡Listo!",
      text: "El visto bueno se canceló.",
      icon: "warning",
      buttons: {
        confirm: {
          text: "Regresar a la pantalla Cargar Archivos",
          value: true,
          visible: true,
          className: "",
          closeModal: true
        }
      }
    }).then(() => {
      $('#archivos-tab').click();
    });
  }
});
 
// Evento para el botón "Visto Bueno"
document.getElementById("btnVistoBueno").addEventListener("click", function() {
  var ValorSi = $('#infoSi').is(':checked');
 
  if (ValorSi) {
    swal({
      title: "¡Éxito!",
      text: "Visto bueno registrado correctamente.",
      icon: "success",
      button: "Aceptar",
    }).then(() => {
      ejecutarVistoBueno();
    });
  } else {
    swal("Debes seleccionar la opción 'Sí' para continuar.", {
      icon: "warning",
    });
  }
});
 
 
 
 
// ==========================================================
//  Validar archivo Excel antes de subirlo
// ==========================================================
async function subirArchivoPEP() {

  const input = document.getElementById("fileUploadPEP");
  const files = input.files;

  if (!files || files.length === 0) {
    swal("Advertencia", "Debes seleccionar un archivo.", "warning");
    return;
  }

  const fd = new FormData();
  for (let i = 0; i < files.length; i++) {
    fd.append("file", files[i]);
  }

  swal({
    title: "Procesando archivo...",
    content: {
      element: "div",
      attributes: {
        innerHTML: '<div class="spinner-border text-primary"></div>',
        style: "display:flex;justify-content:center;padding:20px;"
      }
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false
  });

  try {
    const res = await fetch("/STD/prevencion/reporteClientesOpe/uploadPEP", {
      method: "POST",
      body: fd
    });

    if (!res.ok) {
      throw new Error(await res.text());
    }

    const data = await res.json(); // 👈 YA NO XML

    swal.close();

    swal({
      title: "¡Éxito!",
      text: data[0],
      icon: "success"
    });

    input.value = "";

  } catch (err) {
    swal.close();
    swal("Error", err.message || "Error al subir archivo", "error");
  }
}

 
 
 
function confirmar(valor) {
  fetch(`/prevencion/reporteClientesOpe/confirmar?confirmado=${valor}`, { method: "POST" })
    .then(r => {
      if (r.redirected) window.location.href = r.url;
      else return r.text().then(alert);
    });
}

function esFinDeMes(fechaStr) {
    // formato: yyyy-MM-dd
    const fecha = new Date(fechaStr + "T00:00:00");

    const anio = fecha.getFullYear();
    const mes = fecha.getMonth(); // 0–11
    const dia = fecha.getDate();

    const ultimoDia = new Date(anio, mes + 1, 0).getDate();

    return dia === ultimoDia;
}
 
function ejecutarVistoBueno() {
const fechaInicioOper = $("#fechaInicio").val() || $("#fechaInicioMigracion").val();
const fechaFinOper    = $("#fechaFin").val()    || $("#fechaFinMigracion").val();
const fechaCorteClientes = document.getElementById("fechaFinMigracion").value;

if (!fechaInicioOper || !fechaFinOper || !fechaCorteClientes) {
  swal("Error", "Debe seleccionar un rango de fechas válido.", "error");
  return;
}


  const anio = fechaInicioOper.substring(0, 4);  // ← CALCULA AÑO AUTOMÁTICAMENTE

  swal({
    title: "¿Desea registrar el visto bueno global?",
    text: "Esto consolidará los reportes de CLIENTES y OPERACIONES en el histórico.",
    icon: "warning",
    buttons: ["Cancelar", "Sí, continuar"],
    dangerMode: true,
  }).then((willExecute) => {
    if (!willExecute) return;

    swal({
      title: "Registrando visto bueno...",
      content: {
        element: "div",
        attributes: {
          innerHTML: '<div class="spinner-border text-primary"></div>',
          style: "display:flex;justify-content:center;padding:20px;"
        }
      },
      buttons: false,
      closeOnClickOutside: false,
      closeOnEsc: false
    });

fetch("ejecutarVistoBueno", {
  method: "POST",
  headers: { "Content-Type": "application/x-www-form-urlencoded" },
  body: new URLSearchParams({
    anio: fechaInicioOper.substring(0, 4),
    fechaInicioOper: fechaInicioOper,
    fechaFinOper: fechaFinOper,
    fechaCorteClientes: fechaCorteClientes
  })
})


    .then(res => res.json())
    .then(data => {
      swal.close();
      swal({
        title: data.tipo === "success" ? "¡Éxito!" : "Error",
        text: data.mensaje,
        icon: data.tipo,
        button: "Aceptar"
      }).then(() => {
        if (data.tipo === "success") iniciaTblHistorico();
      });
    })
    .catch(err => {
      swal.close();
      swal("Error", "No se pudo registrar el visto bueno. Intente más tarde.", "error");
      console.error("Error en fetch:", err);
    });
  });
}

var btnsROpera = "";
var btnsRCientes = "";
var cargatblHistorico = "obtHistoricoProcesos";
 
function iniciaTblHistorico() {
  url = getAbsolutePath() + "obtHistoricoProcesos";
  $.getJSON(url, {}, function (data) {
    tblHistorico.clear().draw();

    if (!data || data.length === 0) {
      console.warn("No se encontraron registros en el historial.");
      $("#sinResultados").removeClass("d-none");
      return;
    } else {
      $("#sinResultados").addClass("d-none");
    }

    $.each(data, function (idx, opt) {
      const tipo = opt.tipoReporte ? opt.tipoReporte.toUpperCase() : "DESCONOCIDO";

      // Mostrar rango adecuado según el tipo
      let rangoFechas = "";
      if (tipo === "CLIENTES") {
    const fechaFin = opt.fechaFin ? opt.fechaFin.split("T")[0] : "-";
    rangoFechas =
        `<span class="text-success fw-bold">Hasta: ${fechaFin}</span>`;
     } else if (tipo === "OPERACIONES") {
        const ini = opt.fechaInicio ? opt.fechaInicio.split("T")[0] : "-";
        const fin = opt.fechaFin ? opt.fechaFin.split("T")[0] : "-";
        rangoFechas = `<span class="text-primary fw-bold">${ini} a ${fin}</span>`;
      } else {
        rangoFechas = `<span class="text-muted">Sin rango definido</span>`;
      }

      const usuario = opt.i_usuario?.vc_nombre_usuario || "Desconocido";
      const confirmado = opt.estatus === 1 ? "Sí" : "No";
      const fechaVobo = opt.fechaGeneracionvobo ? opt.fechaGeneracionvobo.split("T")[0] : "-";

      //  Mostrar solo el botón del tipo correspondiente
      let btn = "";
      if (tipo === "CLIENTES") {
        btn = `<button class='btn btn-success btn-sm' title='Descargar histórico de clientes'
                 onclick='generarHistoricoClientes(${opt.id})'>
                 <i class='bi bi-file-earmark-person'></i>
               </button>`;
      } else if (tipo === "OPERACIONES") {
        btn = `<button class='btn btn-primary btn-sm' title='Descargar histórico de operaciones'
                 onclick='generarHistoricoOperaciones(${opt.id})'>
                 <i class='bi bi-file-earmark-diff'></i>
               </button>`;
      } else {
        btn = `<button class='btn btn-secondary btn-sm' disabled title='Tipo desconocido'>
                 <i class='bi bi-question-circle'></i>
               </button>`;
      }

      tblHistorico.row.add([
        opt.fechaGeneracion ? opt.fechaGeneracion.split("T")[0] : "-",
        tipo,
        rangoFechas,
        usuario,
        confirmado,
        fechaVobo,
        btn
      ]).draw(false);
    });
  });
}


 
function generarHistoricoOperaciones(idBuscarHO) {
  fetch("generarRptHistoricoOperaciones", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ id: idBuscarHO })
  })
  .then(res => res.json())
  .then(respuesta => {
    const url = respuesta[0];
    window.open(window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + url);
    window.focus();
  })
  .catch(error => {
    console.error("Error al generar el histórico de operaciones:", error);
    alert("Se produjo un error al generar el reporte.");
  });
}
 
function generarHistoricoClientes(idBuscarHO) {
  fetch("generarRptHistoricoClientes", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ id: idBuscarHO })
  })
  .then(res => res.json())
  .then(respuesta => {
    const url = respuesta[0];
    window.open(window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + url);
    window.focus();
  })
  .catch(error => {
    console.error("Error al generar el histórico de clientes:", error);
    alert("Se produjo un error al generar el reporte.");
  });
}
 
const idMigracion = 1;
const BASE_URL = "/STD/prevencion";

async function cargarEstadoInicial() {
    try {
        const res = await fetch(`${BASE_URL}/obtener-migracion/${idMigracion}`);
        if (!res.ok) throw new Error("No se pudo obtener el estado de la migración");
        const data = await res.json();

        const checkbox = document.getElementById("estatusSwitch");
        const label = document.getElementById("labelEstado");

        // 🔴 AQUÍ CAMBIA INACTIVO / ACTIVO
        checkbox.checked = data.estatus === 1;
        label.innerText = data.estatus === 1 ? "Activo" : "Inactivo";

        const btnEjecutar = document.getElementById("btnEjecutarMigracion");
        if (btnEjecutar) {
            const enEjecucion = data.ejecuta === 1;
            btnEjecutar.disabled = enEjecucion;
            btnEjecutar.classList.toggle("disabled", enEjecucion);
        }

        mostrarMensajeMigracion(data);

    } catch (error) {
        console.error("Error al cargar estado inicial:", error);
    }
}


async function activarMigracion() {
  const fechaInicio = $("#fechaInicioMigracion").val();
  const fechaFin = $("#fechaFinMigracion").val();

  if (!fechaFin) {
    swal("Fecha requerida", "Debe seleccionar la fecha de corte.", "warning");
    return;
  }
  if (!fechaInicio) {
    swal("Fecha requerida", "Debe seleccionar la fecha inicio.", "warning");
    return;
  }

  // 1) Guardar en TBL_CONFIG_MIGRACIONES_AOP (Migraciones)
  await fetch(`/STD/prevencion/activar-ejecucion/1`, {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ fechaInicio, fechaFin })
  });

  // 2) Crear proceso en TBL_PLD_ESTADO_PROCESO (necesitas endpoint, abajo te lo pongo)
  await fetch("/STD/prevencion/estado-proceso/iniciar", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ fechaCorte: fechaFin })
  });

  // Mostrar botón amarillo
  $("#btnProcesarAuditoriaClientes").removeClass("d-none");
}


async function cambiarEstatus(checkbox) {
    const estatus = checkbox.checked ? 1 : 0;
    const label = document.getElementById("labelEstado");

    try {
        const res = await fetch(`${BASE_URL}/cambiar-estatus`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `id=${idMigracion}&estatus=${estatus}`
        });

        if (res.ok) {
            // 🔴 AQUÍ CAMBIA INACTIVO / ACTIVO
            label.innerText = estatus === 1 ? "Activo" : "Inactivo";

            Swal.fire({
                title: "¡Éxito!",
                text: `Estatus cambiado a ${estatus === 1 ? "Activo" : "Inactivo"}`,
                icon: "success",
                timer: 2000,
                showConfirmButton: false
            });
        } else {
            Swal.fire("Error", "No se pudo actualizar el estatus", "error");
            checkbox.checked = !checkbox.checked; // rollback
        }
    } catch (error) {
        console.error("Error:", error);
        Swal.fire("Error", "Falló la conexión con el servidor", "error");
        checkbox.checked = !checkbox.checked; // rollback
    }
}





 window.onload = cargarEstadoInicial;
// Para cargar mensaje de resultado en pantalla
function renderAvisoMigracion(data) {

    const mensaje = document.getElementById("mensajeResultado");
    if (!mensaje) return;

    let texto = "";
    let clase = "text-muted";

    if (data.ejecuta === 1) {
        texto = "⏳ Migración en proceso…";
        clase = "text-warning";
    } 
    else if (data.mensajeResultado) {
        texto = `✔ ${data.mensajeResultado}`;
        clase = "text-success";
    }

    if (data.cron) {
        texto += ` (Siguiente ejecución: ${data.cron})`;
    }

    mensaje.className = `mt-2 fw-bold ${clase}`;
    mensaje.innerText = texto;
    mensaje.style.display = "block";
}


//  Función para mostrar/ocultar opciones según el tipo de reporte seleccionado
function mostrarOpcionesPorTipo() {
  const tipo = document.getElementById("tipoReporte").value;
  const bloqueClientes = document.getElementById("bloqueClientes");
  const bloqueOperaciones = document.getElementById("bloqueOperaciones");
  const btnClientes = document.getElementById("btnProcesarClientes");
  const btnOperaciones = document.getElementById("btnProcesarOperaciones");
  const bloqueVistoBueno = document.getElementById("bloqueVistoBueno"); // nuevo

  // Reiniciar visibilidad
  bloqueClientes.style.display = "none";
  bloqueOperaciones.style.display = "none";
  bloqueVistoBueno.style.display = "none"; // Ocultar por defecto
  btnClientes.disabled = true;
  btnOperaciones.disabled = true;

  if (tipo === "clientes") {
    bloqueClientes.style.display = "block";
    btnClientes.disabled = false;
    bloqueVistoBueno.style.display = "none"; // ocultar pregunta y botón
  } else if (tipo === "operaciones") {
    bloqueOperaciones.style.display = "block";
    btnOperaciones.disabled = false;
    bloqueVistoBueno.style.display = "block"; // mostrar solo para operaciones
  }
}

// ========================================================
//  Generar reporte de CLIENTES filtrado por año
// ========================================================
// Función completa para generar reporte de clientes
async function generarReporteClientes() {

  swal({
    title: "Generando reporte de clientes...",
    content: {
      element: "div",
      attributes: {
        innerHTML: '<div class="spinner-border text-primary"></div>',
        style: "display:flex;justify-content:center;padding:20px;"
      }
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false
  });

  try {
    const res = await fetch("generarRptClientesPorFechaCorte", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({
        fechaCorte: document.getElementById("fechaFinMigracion").value
      })
    });

    if (!res.ok) throw new Error();

    const respuesta = await res.json();
    const url = respuesta[0];

    const base = window.location.href.substring(
      0,
      window.location.href.indexOf("STD/") + 4
    );

    window.open(base + url);
    window.focus();

    swal.close();

    // =========================
    //  FLUJO DE CONFIRMACIÓN
    // =========================
    swal("¡Éxito!", "Reporte de clientes generado.", "success")
    .then(() => {

      swal({
        title: "¿El reporte de clientes se generó correctamente?",
        icon: "warning",
        buttons: {
          cancel: "No",
          confirm: {
            text: "Sí",
            value: true
          }
        }
      }).then((esCorrecto) => {

        if (!esCorrecto) {
          swal({
            title: "Validación cancelada",
            text: "Puede regresar a la pantalla de Cargar Archivos.",
            icon: "info",
            button: "Regresar"
          }).then(() => {
            $('#archivos-tab').click();
          });
          return;
        }

        swal({
          title: "¿Está seguro de que el reporte es correcto?",
          text: "Esta acción registrará el visto bueno.",
          icon: "warning",
          buttons: {
            cancel: "Cancelar",
            confirm: {
              text: "Sí, confirmar",
              value: true
            }
          },
          dangerMode: true
        }).then((confirmarFinal) => {

          if (!confirmarFinal) return;

          fetch("/STD/prevencion/confirmar-reporte", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              tipo: "CLIENTES",
              fechaInicio: $("#fechaInicioMigracion").val(),
              fechaFin: $("#fechaFinMigracion").val()
            })
          })
          .then(() => {
            swal({
              title: "Visto bueno registrado",
              text: "El reporte pasó al histórico.",
              icon: "success",
              button: "Aceptar"
            }).then(() => {
              iniciaTblHistorico();
              cargarEstadoProceso();
            });
          });
        });
      });
    });

  } catch (e) {
    swal.close();
    swal("Error", "No se pudo generar el reporte.", "error");
    console.error(e);
  }
}





// ========================================================
//  Procesar Auditoría de CLIENTES (llama SP en backend)
// ========================================================
async function procesarAuditoriaClientes() {
  const fechaInicio = $("#fechaInicioMigracion").val();
  const fechaFin    = $("#fechaFinMigracion").val();
  const fechaCorte  = fechaFin; // corte = fechaFin

  if (!fechaInicio || !fechaFin) {
    swal("Fechas requeridas", "Debe seleccionar fecha inicio y fecha corte.", "warning");
    return;
  }

  swal({ title:"Procesando auditoría de clientes...", buttons:false, closeOnClickOutside:false, closeOnEsc:false });

  try {
    const res = await fetch("/STD/prevencion/procesarAuditoria", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaCorte, fechaInicio, fechaFin })
    });

    const data = await res.json();

    if (data.tipo !== "success") {
      swal("Error", data.mensaje || "Error al procesar auditoría", "error");
      return;
    }

    swal("¡Éxito!", data.mensaje, "success").then(async () => {
      await cargarEstadoProceso();
      iniciaTblHistorico(); // ya estarán insertadas las 2 filas en histórico con estatus 0
    });

  } catch (e) {
    console.error(e);
    swal("Error", "Falló la conexión o el proceso tardó demasiado.", "error");
  }
}


// ========================================================
//  Generar reporte de OPERACIONES filtrado por rango de fechas
// ========================================================
async function generarReporteOperaciones() {

    const fechaInicio = document.getElementById("fechaInicioMigracion").value;
    const fechaFin    = document.getElementById("fechaFinMigracion").value;

    if (!fechaInicio || !fechaFin) {
        swal("Error", "Debe seleccionar fecha inicio y fecha fin.", "warning");
        return;
    }

    swal({
        title: "Generando reporte de operaciones...",
        content: {
            element: "div",
            attributes: {
                innerHTML: '<div class="spinner-border text-primary"></div>',
                style: "display:flex;justify-content:center;padding:20px;"
            }
        },
        buttons: false,
        closeOnClickOutside: false,
        closeOnEsc: false
    });

    try {
        const res = await fetch("generarRptOperacionesAuditoria", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                fechaInicio,
                fechaFin
            })
        });

        if (!res.ok) throw new Error();

        const respuesta = await res.json();
        const url = respuesta[0];

        const base = window.location.href.substring(
            0,
            window.location.href.indexOf("STD/") + 4
        );

        window.open(base + url);
        window.focus();

        swal.close();
        swal("¡Éxito!", "Reporte de operaciones generado.", "success");

    } catch (e) {
        swal.close();
        swal("Error", "No se pudo generar el reporte.", "error");
    }
}

// ==============================
//  ESTADO PROCESO -> TABLA
// ==============================
async function cargarEstadoProceso() {
  const res = await fetch("/STD/prevencion/estado-proceso");
  const p = await res.json();

console.log("ESTADO PROCESO:", p);
  // No hay proceso o ya finalizó
  if (!p || p.estatus === 0) {
    $("#btnProcesarAuditoriaClientes").addClass("d-none");
    $("#bloqueTablaProceso").addClass("d-none");
    return;
  }

  // Proceso activo pero auditoría no OK
  if (p.ejecutado && !p.auditoriaOk) {
    $("#btnProcesarAuditoriaClientes").removeClass("d-none");
    $("#bloqueTablaProceso").addClass("d-none");
    return;
  }

  // Auditoría OK -> tabla
  if (p.auditoriaOk) {
    $("#btnProcesarAuditoriaClientes").addClass("d-none");
    renderTablaProceso(p);
  }
}

function filaProceso(tipo, descargado, vobo) {
  const esClientes = tipo === "CLIENTES";

  const onDescargar = esClientes ? "descargarClientes()" : "descargarOperaciones()";
  const onVobo      = esClientes ? "vistoBuenoClientes()" : "vistoBuenoOperaciones()";

  let html = `
    <tr>
      <td>${tipo}</td>
      <td>
        <button class="btn btn-sm btn-primary" onclick="${onDescargar}">
          Descargar
        </button>
  `;

  // Solo aparece DESPUÉS de descargar y si aún no tiene VoBo
  if (descargado && !vobo) {
    html += `
      <button class="btn btn-sm btn-success ms-2" onclick="${onVobo}">
        Visto bueno
      </button>
    `;
  }

  html += `
      </td>
    </tr>
  `;
  return html;
}

// ==============================
//  ACCIONES: DESCARGAR -> FLAG
// ==============================
async function descargarClientes() {
  await generarReporteClientes();

  const r = await fetch("estado-proceso/accion", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ accion: "CLIENTES_DESCARGADO" })
  });

  if (!r.ok) {
    swal("Error", await r.text(), "error");
    return;
  }

  await cargarEstadoProceso(); 
}


async function descargarOperaciones() {
  await generarReporteOperaciones();

  const r = await fetch("estado-proceso/accion", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ accion: "OPERACIONES_DESCARGADO" })
  });

  if (!r.ok) {
    swal("Error", await r.text(), "error");
    return;
  }

  await cargarEstadoProceso(); 
}

function registrarVobo() {

  const clientes = $("#chkClientes").is(":checked");
  const operaciones = $("#chkOperaciones").is(":checked");

  if (!clientes && !operaciones) {
    Swal.fire("Atención", "Seleccione al menos un reporte", "warning");
    return;
  }

  const fechaInicioOper = $("#fechaInicioMigracion").val();
  const fechaFinOper    = $("#fechaFinMigracion").val();
  const fechaCorteCli   = $("#fechaFinMigracion").val(); // misma fecha corte

  Swal.fire({
    title: "¿Registrar visto bueno?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Sí"
  }).then(res => {

    if (!res.isConfirmed) return;

    fetch("/STD/prevencion/vobo-auditoria", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({
        clientes,
        operaciones,
        fechaInicioOper,
        fechaFinOper,
        fechaCorteClientes: fechaCorteCli
      })
    })
    .then(r => {
      if (!r.ok) throw new Error();
      Swal.fire("Éxito", "Visto bueno registrado", "success");
      $("#btnVobo").prop("disabled", true);
      iniciaTblHistorico();
    })
    .catch(() => {
      Swal.fire("Error", "No se pudo registrar el visto bueno", "error");
    });
  });
}



function confirmarReporteClientes(idHistorial) {

  console.log("ID HISTORIAL A CONFIRMAR:", idHistorial);

  Swal.fire({
    title: '¿El reporte se generó correctamente?',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: 'Sí',
    cancelButtonText: 'No'
  }).then(result => {

    if (!result.isConfirmed) return;

    Swal.fire({
      title: '¿Está seguro de confirmar?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, confirmar'
    }).then(confirmFinal => {

      if (!confirmFinal.isConfirmed) return;

      fetch("/STD/prevencion/confirmar-reporte", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({
          idHistorial: idHistorial   // ✅ AHORA SÍ
        })
      })
      .then(res => {
        if (!res.ok) throw new Error();
        return res.text();
      })
      .then(() => {
        Swal.fire("Visto bueno registrado", "El reporte pasó al histórico", "success");
        iniciaTblHistorico();
      })
      .catch(() => {
        Swal.fire("Error", "No se pudo registrar el visto bueno", "error");
      });
    });
  });
}


function confirmarVoboFinal() {

  console.log("ID HISTORIAL A ENVIAR:", idHistorialSeleccionado);

  fetch("/STD/prevencion/confirmar-reporte", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({
      idHistorial: idHistorialSeleccionado
    })
  })
  .then(res => {
    if (!res.ok) throw new Error("Error HTTP");
    return res.text();
  })
  .then(() => {
    Swal.fire("Visto bueno registrado", "El reporte pasó al histórico", "success");
    iniciaTblHistorico();
  })
  .catch(err => {
    console.error(err);
    Swal.fire("Error", "No se pudo registrar el visto bueno", "error");
  });
}


// ==============================
//  ACCIONES: VOBO -> FLAG + BD
// ==============================
async function vistoBuenoClientes() {
  const fechaInicio = $("#fechaInicioMigracion").val();
  const fechaFin    = $("#fechaFinMigracion").val();

  const r = await fetch("/STD/prevencion/vobo", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ tipo: "CLIENTES", fechaInicio, fechaFin })
  });

  if (!r.ok) {
    swal("Error", await r.text(), "error");
    return;
  }

  await cargarEstadoProceso();
  iniciaTblHistorico();
}


async function vistoBuenoOperaciones() {
  const fechaInicio = $("#fechaInicioMigracion").val();
  const fechaFin    = $("#fechaFinMigracion").val();

  const r = await fetch("/STD/prevencion/vobo", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ tipo: "OPERACIONES", fechaInicio, fechaFin })
  });

  if (!r.ok) {
    swal("Error", await r.text(), "error");
    return;
  }

  await cargarEstadoProceso();
  iniciaTblHistorico();
}





function renderTablaProceso(p) {

  // Si NO hay proceso, ocultar
  if (!p || p.estatus === 0) {
    $("#bloqueTablaProceso").addClass("d-none");
    return;
  }

  // Mostrar tabla aunque no esté todo validado
  $("#bloqueTablaProceso").removeClass("d-none");

  const tbody = $("#tblProceso tbody");
  tbody.empty();

  tbody.append(filaProceso("CLIENTES", p.clientesDescargado, p.clientesVobo));
  tbody.append(filaProceso("OPERACIONES", p.operacionesDescargado, p.operacionesVobo));
}




async function voboClientes() {
  await fetch("/STD/prevencion/vobo", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: new URLSearchParams({ tipo: "CLIENTES" })
  });

  await cargarEstadoProceso();
  iniciaTblHistorico(); // para que ya se refleje si aplica
}

/** SweetAlert reutilizable */
function confirmarVoboSweetAlert(tipo) {
  return swal({
    title: `¿Registrar visto bueno de ${tipo}?`,
    text: "Esto moverá el reporte al histórico.",
    icon: "warning",
    buttons: ["Cancelar", "Sí, continuar"],
    dangerMode: true
  }).then(v => !!v);
}



function renderEstadoMigracion(data) {

    // ===== SWITCH + TEXTO =====
    const checkbox = document.getElementById("estatusSwitch");
    const label = document.getElementById("labelEstado");

    if (checkbox && label) {
        const activo = data.estatus === 1;

        checkbox.checked = activo;
        label.innerText = activo ? "Activo" : "Inactivo";
        label.classList.toggle("text-success", activo);
        label.classList.toggle("text-muted", !activo);
    }

    // ===== BOTÓN EJECUTAR =====
    const btnEjecutar = document.getElementById("btnEjecutarMigracion");
    if (btnEjecutar) {
        const enEjecucion = data.ejecuta === 1;

        btnEjecutar.disabled = enEjecucion;

        btnEjecutar.classList.toggle("btn-outline-primary", !enEjecucion);
        btnEjecutar.classList.toggle("btn-secondary", enEjecucion);

        btnEjecutar.innerHTML = enEjecucion
            ? '<i class="fas fa-clock"></i> EN PROCESO'
            : '<i class="fas fa-play"></i> EJECUTAR';
    }

    // ===== MENSAJE =====
    renderAvisoMigracion(data);
}

//setInterval(() => {
   // cargarEstadoInicial();
//}, 20000); // cada 20 segundos


function existeElemento(id) {
    return document.getElementById(id) !== null;
}

document.addEventListener("DOMContentLoaded", () => {

    const switchEstatus = document.getElementById("estatusSwitch");

    if (switchEstatus) {
        switchEstatus.addEventListener("change", function () {
            cambiarEstatus(this);
        });
    }

});

function ocultarTodoProceso() {
  $("#btnProcesarAuditoriaClientes").addClass("d-none");
  $("#bloqueTablaProceso").addClass("d-none");
}


document.addEventListener("DOMContentLoaded", () => {
    const btnEjecutar = document.getElementById("btnEjecutarMigracion");
    if (btnEjecutar) {
        btnEjecutar.addEventListener("click", activarMigracion);
    }
});

document.addEventListener("DOMContentLoaded", async () => {
  ocultarTodoProceso();
  await cargarEstadoProceso();   // lee de BD y pinta botón/tabla
  iniciaTblHistorico();
});






