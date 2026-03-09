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
$(document).on("click", "#btnVistoBueno", function () {

  swal({
    title: "Visto bueno",
    text: "Seleccione el tipo de reporte",
    content: {
      element: "div",
      attributes: {
        innerHTML: `
          <label>
            <input type="checkbox" id="chkClientes"> Clientes
          </label><br>
          <label>
            <input type="checkbox" id="chkOperaciones"> Operaciones
          </label>
        `
      }
    },
    buttons: {
      cancel: "Cancelar",
      confirm: {
        text: "Registrar",
        closeModal: false
      }
    }
  }).then(async (ok) => {

    if (!ok) return;

    const clientes    = document.getElementById("chkClientes").checked;
    const operaciones = document.getElementById("chkOperaciones").checked;

    if (!clientes && !operaciones) {
      swal("Atención", "Debe seleccionar al menos un reporte", "warning");
      return;
    }

    let tipoReporte = "";

    if (clientes && operaciones) {
      tipoReporte = "AMBOS";
    } else if (clientes) {
      tipoReporte = "CLIENTES";
    } else if (operaciones) {
      tipoReporte = "OPERACIONES";
    }

    try {

      const fechaInicioOper = $("#fechaInicioMigracion").val();
      const fechaFinOper    = $("#fechaFinMigracion").val();
      const fechaCorteClientes = $("#fechaFinMigracion").val();
      const anio = fechaInicioOper.substring(0, 4);

      const res = await fetch("ejecutarVistoBueno", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({
          anio,
          fechaInicioOper,
          fechaFinOper,
          fechaCorteClientes,
          tipoReporte
        })
      });

      const data = await res.json();

      swal(
        data.tipo === "success" ? "¡Éxito!" : "Error",
        data.mensaje,
        data.tipo
      );

      if (data.tipo === "success") {
        await cargarEstadoProceso();
        iniciaTblHistorico();
      }

    } catch (e) {
      swal("Error", "No se pudo registrar el visto bueno", "error");
    }

  });
});
// ==========================================================
//  Validar archivo Excel antes de subirlo
// ==========================================================
document.getElementById("uploadFormPEP").addEventListener("submit", async e => {
  e.preventDefault();
 
  const input = document.getElementById("fileUploadPEP");
  const files = input.files;
 
  if (!files || files.length === 0) {
    swal("Debes seleccionar al menos un archivo.", { icon: "warning" });
    return;
  }
 
  const fd = new FormData();
  for (let i = 0; i < files.length; i++) {
    fd.append("file", files[i]);
  }
 
  swal({
    title: "Procesando archivo(s)...",
    content: {
      element: "div",
      attributes: {
        innerHTML:
          '<div class="spinner-border text-primary" role="status"></div>',
        style:
          "display:flex;justify-content:center;align-items:center;padding:20px;",
      },
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false,
  });
 
  try {
    const res = await fetch(
      getAbsolutePath() + "reporteClientesOpe/uploadPEP",
      {
        method: "POST",
        body: fd,
      }
    );
 
    let respuesta;
 
    if (res.ok) {
      respuesta = await res.json();
    } else {
      const texto = await res.text();
      throw new Error(texto || "Error interno del servidor");
    }
 
    swal.close();
 
    swal({
      title: "¡Éxito!",
      text: respuesta[0],
      icon: "success",
      button: "Aceptar",
    });
 
  } catch (error) {
    swal.close();
    console.error("❌ Error en upload PEP:", error);
 
    swal({
      title: "Error",
      text: error.message || "Se presentó un error, intente más tarde.",
      icon: "error",
      button: "Aceptar",
    });
  }
});
 
 
function ocultarTodoProceso() {
  $("#btnProcesarAuditoriaClientes").hide();
  $("#btnGenerarClientes").hide();
  $("#btnGenerarOperaciones").hide();
  $("#bloqueVistoBueno").hide();
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
 
async function ejecutarVistoBueno() {
 
  const fechaInicioOper = $("#fechaInicio").val() || $("#fechaInicioMigracion").val();
  const fechaFinOper    = $("#fechaFin").val()    || $("#fechaFinMigracion").val();
  const fechaCorteClientes = $("#fechaFinMigracion").val();
 
  if (!fechaInicioOper || !fechaFinOper || !fechaCorteClientes) {
    swal("Error", "Debe seleccionar un rango de fechas válido.", "error");
    return;
  }
 
  const anio = fechaInicioOper.substring(0, 4);
 
  const confirmar = await swal({
    title: "¿Desea registrar el visto bueno global?",
    text: "Esto consolidará los reportes de CLIENTES y OPERACIONES en el histórico.",
    icon: "warning",
    buttons: ["Cancelar", "Sí, continuar"],
    dangerMode: true
  });
 
  if (!confirmar) return;
 
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
 
  try {
 
    const res = await fetch("ejecutarVistoBueno", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({
        anio,
        fechaInicioOper,
        fechaFinOper,
        fechaCorteClientes
      })
    });
 
    if (!res.ok) {
      throw new Error("Respuesta HTTP inválida");
    }
 
    let data;
    try {
      data = await res.json();
    } catch {
      data = { tipo: "error", mensaje: "Respuesta inválida del servidor" };
    }
 
    swal.close();
 
    swal({
      title: data.tipo === "success" ? "¡Éxito!" : "Error",
      text: data.mensaje,
      icon: data.tipo,
      button: "Aceptar"
    });
 
    if (data.tipo === "success") {
 
      // Persistir estado del proceso
      await fetch("/STD/prevencion/estado-proceso/vobo", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ fechaCorte: fechaCorteClientes })
      });
 
      iniciaTblHistorico();
      await cargarEstadoProceso();
    }
 
  } catch (err) {
 
    swal.close();
    console.error("Error en fetch:", err);
 
    swal({
      title: "Atención",
      text: "El visto bueno se registró, pero ocurrió un problema al actualizar la interfaz.",
      icon: "warning"
    });
  }
}
 
 
 
var btnsROpera = "";
var btnsRCientes = "";
var cargatblHistorico = "obtHistoricoProcesos";
 
function iniciaTblHistorico() {

  url = "/STD/prevencion/obtHistoricoProcesos";

  $.getJSON(url, function (data) {

    tblHistorico.clear().draw();

    if (!data || data.length === 0) {
      console.warn("No se encontraron registros en el historial.");
      $("#sinResultados").removeClass("d-none");
      return;
    }

    $("#sinResultados").addClass("d-none");

    $.each(data, function (idx, opt) {

      const tipo = opt.tipoReporte ? opt.tipoReporte.toUpperCase() : "DESCONOCIDO";

      let rangoFechas = "";

      if (tipo === "CLIENTES") {
        const fechaFin = opt.fechaFin ? opt.fechaFin.split("T")[0] : "-";
        rangoFechas = `<span class="text-success fw-bold">Hasta: ${fechaFin}</span>`;
      } 
      else if (tipo === "OPERACIONES") {
        const ini = opt.fechaInicio ? opt.fechaInicio.split("T")[0] : "-";
        const fin = opt.fechaFin ? opt.fechaFin.split("T")[0] : "-";
        rangoFechas = `<span class="text-primary fw-bold">${ini} a ${fin}</span>`;
      }

      const usuario = opt.i_usuario?.vc_nombre_usuario || "Desconocido";
      const confirmado = opt.estatus === 1 ? "Sí" : "No";
      const fechaVobo = opt.fechaGeneracionvobo ? opt.fechaGeneracionvobo.split("T")[0] : "-";

      tblHistorico.row.add([
        opt.fechaGeneracion ? opt.fechaGeneracion.split("T")[0] : "-",
        tipo,
        rangoFechas,
        usuario,
        confirmado,
        fechaVobo,
        ""
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

        // Guardar estado global para usarlo en renderProcesoUI
        estadoMigracionGlobal = data;

        const checkbox = document.getElementById("estatusSwitch");
        const label = document.getElementById("labelEstado");

        if (checkbox && label) {
            checkbox.checked = data.estatus === 1;
            label.innerText = data.estatus === 1 ? "Activo" : "Inactivo";
        }

        // Control de botones y mensaje
        renderEstadoMigracion(data);
        renderAvisoMigracion(data);

    } catch (error) {
        console.error("Error al cargar estado inicial:", error);
    }
}
 
 
async function activarMigracion() {
 
    const inputInicio = document.getElementById("fechaInicioMigracion");
    const inputFin    = document.getElementById("fechaFinMigracion");
 
    const fechaInicio = inputInicio ? inputInicio.value : "";
    const fechaFin    = inputFin ? inputFin.value : "";
 
    if (!fechaFin) {
        swal("Fecha requerida", "Debe seleccionar la fecha fin.", "warning");
        return;
    }
 
    const btn = document.getElementById("btnEjecutarMigracion");
 
    // 🔒 Bloqueo inmediato
    btn.disabled = true;
    btn.classList.add("disabled");
 
    try {
        const res = await fetch(`${BASE_URL}/activar-ejecucion/${idMigracion}`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                fechaInicio,
                fechaFin
            })
        });
 
        if (!res.ok) {
            throw new Error("Error backend");
        }
 
        // ✅ ALERTA SIN ROMPER EJECUCIÓN
        swal({
            title: "Migración activada",
            text: "La ejecución fue programada correctamente.",
            icon: "success",
            timer: 2000
        });
 
        // 🔄 Releer estado REAL (ejecuta = 1)
        cargarEstadoInicial();
 
    } catch (error) {
        console.error(error);
 
        // 🔓 SOLO revertir si FALLA el backend
        btn.disabled = false;
        btn.classList.remove("disabled");
 
        swal("Error", "No se pudo activar la migración.", "error");
    }
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
 
 
// renderEstadoMigracion(data);
// renderAvisoMigracion(data);
 
 
 // window.onload = cargarEstadoInicial;
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
    else if (data.ejecuta === 2) {
        texto = data.mensajeResultado || "❌ Error en migración.";
        clase = "text-danger";
    }
    else if (data.ejecuta === 0 && data.mensajeResultado) {
        texto = `✔ ${data.mensajeResultado}`;
        clase = "text-success";
    }

    if (data.cron) {
        texto += ` (Programada: ${data.cron})`;
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

    // 1️⃣ Generar reporte CLIENTES
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

    // 2️⃣ Abrir Excel
    window.open(base + url);
    window.focus();

    // 3️⃣ 🔥 MARCAR CLIENTES_DESCARGADO 🔥
    await fetch("/STD/prevencion/estado-proceso/clientes-descargado", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" }
    });

    // 4️⃣ Reconstruir UI desde BD
    await cargarEstadoProceso();

    swal.close();
    swal("¡Éxito!", "Reporte de clientes generado.", "success");

  } catch (e) {
    swal.close();
    swal("Error", "No se pudo generar el reporte.", "error");
  }
}

 
 
 
// ========================================================
//  Procesar Auditoría de CLIENTES (llama SP en backend)
// ========================================================
async function procesarAuditoriaClientes() {
  const fechaCorte = document.getElementById("fechaFinMigracion").value;
 
  if (!fechaCorte) {
    swal("Debe seleccionar la fecha de corte.", { icon: "warning" });
    return;
  }
 
  if (fechaCorte < "2024-01-01") {
    swal("La fecha debe ser del año 2024 en adelante.", { icon: "error" });
    return;
  }
 
  if (!esFinDeMes(fechaCorte)) {
    swal("La fecha seleccionada NO es el último día del mes.", { icon: "error" });
    return;
  }
 
  swal({
    title: "Procesando auditoría...",
    content: {
      element: "div",
      attributes: { innerHTML: '<div class="spinner-border text-primary"></div>' }
    },
    buttons: false
  });
 
  try {
    const res = await fetch("procesarAuditoria", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ fechaCorte })
    });
 
    const data = await res.json();
 
    swal.close();
 
    swal({
      title: data.tipo === "success" ? "¡Éxito!" : "Error",
      text: data.mensaje,
      icon: data.tipo,
      button: "Aceptar"
    });
 
    // ✅ PERSISTIR ESTADO: AUDITORIA_OK
    if (data.tipo === "success") {
      await fetch("/STD/prevencion/estado-proceso/auditoria-ok", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ fechaCorte })
      });
 
      // 🔄 Reconstruir UI desde BD
      await cargarEstadoProceso();
    }
 
  } catch (error) {
    swal.close();
    swal("Ocurrió un error al procesar la auditoría", { icon: "error" });
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
      body: new URLSearchParams({ fechaInicio, fechaFin })
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
 
    // ✅ PERSISTIR ESTADO: OPERACIONES_DESCARGADO
    await fetch("/STD/prevencion/estado-proceso/operaciones-descargado", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({
        fechaCorte: fechaFin
      })
    });
 
    // 🔄 Reconstruir UI desde BD
    await cargarEstadoProceso();
 
  } catch (e) {
    swal.close();
    swal("Error", "No se pudo generar el reporte.", "error");
  }
}
 
 
 
function renderEstadoMigracion(data) {

    if (!data) return;

    const btnEjecutar = document.getElementById("btnEjecutarMigracion");
    const btnAuditoria = document.getElementById("btnProcesarAuditoriaClientes");

    const estado = data.ejecuta; // 0,1,2

    if (btnEjecutar) {

        // 🔵 EN EJECUCIÓN → SOLO DESHABILITAR
        if (estado === 1) {
            btnEjecutar.style.display = "inline-block";
            btnEjecutar.disabled = true;
            btnEjecutar.classList.remove("btn-danger");
            btnEjecutar.classList.add("btn-secondary");
            btnEjecutar.innerHTML = '<i class="fas fa-clock"></i> EN PROCESO';
        }

        // 🟢 FINALIZADO OK
        else if (estado === 0 || estado === null) {
            btnEjecutar.style.display = "inline-block";
            btnEjecutar.disabled = false;
            btnEjecutar.classList.remove("btn-danger", "btn-secondary");
            btnEjecutar.classList.add("btn-outline-primary");
            btnEjecutar.innerHTML = '<i class="fas fa-play"></i> EJECUTAR';
        }

        // 🟡 ERROR → REINTENTAR
        else if (estado === 2) {
            btnEjecutar.style.display = "inline-block";
            btnEjecutar.disabled = false;
            btnEjecutar.classList.remove("btn-outline-primary", "btn-secondary");
            btnEjecutar.classList.add("btn-danger");
            btnEjecutar.innerHTML = '<i class="fas fa-exclamation-triangle"></i> REINTENTAR';
        }
    }

    // 🟡 BOTÓN AMARILLO (Procesar Auditoría)
    if (btnAuditoria) {

    const enProceso = data.ejecuta === 1;
    const hayError  = data.ejecuta === 2;

    if (!enProceso && !hayError && data.ejecuta === 0) {
        btnAuditoria.style.display = "inline-block";
    } else {
        btnAuditoria.style.display = "none";
    }
}
}
 
function renderProcesoUI(estado) {

  if (!estado) return;

  // Botón amarillo SOLO si:
  // - proceso ejecutado
  // - migración NO esté en proceso
  if (estado.ejecutado && estadoMigracionGlobal?.ejecuta !== 1) {
      $("#btnProcesarAuditoriaClientes").show();
  } else {
      $("#btnProcesarAuditoriaClientes").hide();
  }

  if (estado.auditoriaOk) {
    $("#btnGenerarClientes").show();
    $("#btnGenerarOperaciones").show();
  }

  if (estado.operacionesDescargado && !estado.operacionesVobo) {
    $("#bloqueVistoBueno").show();
  }
}
 
 
 
document.getElementById("btnEjecutarMigracion")
  .addEventListener("click", async function () {

    const btn = this;

    const fechaCorte = $("#fechaFinMigracion").val();

    if (!fechaCorte) {
      swal("Debe seleccionar la fecha de corte.", { icon: "warning" });
      return;
    }

    // 🔒 BLOQUEO INMEDIATO
    btn.disabled = true;
    btn.classList.remove("btn-outline-primary");
    btn.classList.add("btn-secondary");
    btn.innerHTML = '<i class="fas fa-clock"></i> EN PROCESO';

    swal({
      title: "Iniciando proceso...",
      content: {
        element: "div",
        attributes: {
          innerHTML: '<div class="spinner-border text-primary"></div>'
        }
      },
      buttons: false,
      closeOnClickOutside: false,
      closeOnEsc: false
    });

    try {

      // ACTIVAR MIGRACIÓN
      const activar = await fetch(`/STD/prevencion/activar-ejecucion/${idMigracion}`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({
          fechaInicio: $("#fechaInicioMigracion").val() || "",
          fechaFin: fechaCorte
        })
      });

      if (!activar.ok) throw new Error("Error activando migración");

      // INICIAR PROCESO PLD
      const ejecutar = await fetch("/STD/prevencion/estado-proceso/ejecutar", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({
          fechaInicio: $("#fechaInicioMigracion").val(),
          fechaCorte:  $("#fechaFinMigracion").val()
        })
      });

      if (!ejecutar.ok) throw new Error("Error ejecutando proceso");

      swal.close();

      // 🔄 Reconstruir estado real desde BD
      await cargarEstadoInicial();
      await cargarEstadoProceso();

    } catch (e) {

      swal.close();

      // 🔓 SOLO si hubo error
      btn.disabled = false;
      btn.classList.remove("btn-secondary");
      btn.classList.add("btn-outline-primary");
      btn.innerHTML = '<i class="fas fa-play"></i> EJECUTAR';

      swal("Error", "No se pudo iniciar el proceso.", "error");
      console.error(e);
    }
});
 
document.addEventListener("DOMContentLoaded", () => {
 
  const btnAuditoria = document.getElementById("btnProcesarAuditoriaClientes");
 
  if (!btnAuditoria) {
    console.warn("⚠️ No existe btnProcesarAuditoriaClientes en el DOM");
    return;
  }
 
  btnAuditoria.addEventListener("click", async function () {
    await procesarAuditoriaClientes();
  });
 
});
 
 
async function cargarEstadoProceso() {
 
  const res = await fetch("/STD/prevencion/estado-proceso");
 
  // 🔴 NO HAY PROCESO ACTIVO → estado final válido
  if (res.status === 204 || res.status === 404) {
    ocultarTodoProceso();
    return;
  }
 
  if (!res.ok) return;
 
  const estado = await res.json();
 
  // 🔹 RECONSTRUIR FECHAS (si existen)
  if (estado?.fechaInicio) {
    $("#fechaInicioMigracion").val(estado.fechaInicio);
  }
 
  if (estado?.fechaCorte) {
    $("#fechaFinMigracion").val(estado.fechaCorte);
  }
 
  ocultarTodoProceso();
  renderProcesoUI(estado);
}
 
 
 
 
 
$(document).ready(async () => {
 
  // 1️⃣ Cargar estado de migración (switch + botón)
  await cargarEstadoInicial();
 
  // 2️⃣ Reconstruir proceso PLD desde BD
  await cargarEstadoProceso();
 
  // 3️⃣ Cargar histórico
  iniciaTblHistorico();
});
 
 
 
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
