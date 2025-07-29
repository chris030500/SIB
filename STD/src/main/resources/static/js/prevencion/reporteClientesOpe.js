// @ts-nocheck
var url = "";
var tblHistorico;
 
$(document).ready(function() {
  tblHistorico = $("#tblHistorico").DataTable({
    "language": {
      "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
    },
    'aoColumnDefs': [{
      'bSortable': false,
      'aTargets': [-1]
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
  btnProcesar.addEventListener("click", async (event) => {
    event.preventDefault();
 
    const inicio = inputInicio.value;
    const fin = inputFin.value;
 
    if (fechasInvalidas(inicio, fin)) return;
 
    try {
      const res = await fetch("generarRptReporteClientesOpeController", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin }),
      });
 
      if (!res.ok) throw new Error("Error generando el reporte.");
 
      const respuesta = await res.json();
      url = respuesta[0];
      window.open(window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0]);
      window.focus();
    } catch (error) {
      console.error("Error al generar el reporte:", error);
      alert("Se produjo un error al generar el reporte.");
    }
  });
 
  // Evento click del botón para generar proceso
  btnProcesarProceso.addEventListener("click", async (event) => {
    event.preventDefault();
 
    const inicio = inputInicio.value;
    const fin = inputFin.value;
 
    if (fechasInvalidas(inicio, fin)) return;
 
    try {
      const res = await fetch("generarRptOperacionesAuditoria", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin })
      });
 
      if (!res.ok) throw new Error("Error generando el reporte.");
 
      const respuesta = await res.json();
      url = respuesta[0];
      window.open(window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0]);
      window.focus();
    } catch (error) {
      console.error("Error al generar el reporte:", error);
      alert("Se produjo un error al generar el reporte.");
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
        innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
        style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
      },
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false,
  });

  try {
    const res = await fetch(getAbsolutePath() + "reporteClientesOpe/uploadPEP", {
      method: "POST",
      body: fd
    });

    const respuesta = await res.json(); 
    swal.close();

    if (res.ok && Array.isArray(respuesta)) {
      swal({
        title: "¡Éxito!",
        text: respuesta[0],
        icon: "success",
        button: "Aceptar",
      }).then(() => {
       
      });
    } else {
      swal({
        title: "Error",
        text: respuesta[0] || "Ocurrió un error inesperado.",
        icon: "error",
        button: "Aceptar",
      });
    }

  } catch (error) {
    swal.close();
    console.error("Error en fetch:", error);
    swal("Se presentó un error, intente más tarde.", {
      icon: "error",
      closeOnClickOutside: false,
    });
  }
});

 
 
function confirmar(valor) {
  fetch(`/prevencion/reporteClientesOpe/confirmar?confirmado=${valor}`, { method: "POST" })
    .then(r => {
      if (r.redirected) window.location.href = r.url;
      else return r.text().then(alert);
    });
}
 
 
 
function peticionPost() {
  url = "ejecutarDatosProceso";
 
  swal({
    title: "Procesando información.",
    content: {
      element: "div",
      attributes: {
        innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
        style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
      },
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false,
  });
 
  $.ajax({
    url: getAbsolutePath() + url,
    type: "POST",
    dataType: "JSON",
    complete: function() {
      swal.close();
    },
    success: function(respuesta) {
      swal("", respuesta.mensaje, respuesta.tipo).then(() => {
        url = "";
      });
    },
    error: function(XMLHttpRequest, textStatus, errorThrown) {
      swal("Se presentó un error, intente más tarde.", {
        icon: "error",
        closeOnClickOutside: false,
      });
    }
  });
}
 
 
function ejecutarVistoBueno() {
  swal({
    title: "¿Validó correctamente?",
    text: "Confirme si desea ejecutar el visto bueno.",
    icon: "warning",
    buttons: {
      cancel: {
        text: "No",
        value: false,
        visible: true,
        closeModal: true,
      },
      confirm: {
        text: "Sí",
        value: true,
        visible: true,
        closeModal: false,
      },
    },
    dangerMode: true,
  }).then((willExecute) => {
    if (willExecute) {
      swal({
        title: "Procesando información.",
        content: {
          element: "div",
          attributes: {
            innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
            style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
          },
        },
        buttons: false,
        closeOnClickOutside: false,
        closeOnEsc: false,
      });
 
      url = "ejecutarVistoBueno";
 
      $.ajax({
        url: getAbsolutePath() + url,
        type: "POST",
        dataType: "JSON",
        complete: function() {
          swal.close();
        },
        success: function(respuesta) {
          swal("", respuesta.mensaje, respuesta.tipo).then(() => {
            iniciaTblHistorico();
            url = "";
          });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
          swal("Se presentó un error, intente más tarde.", {
            icon: "error",
            closeOnClickOutside: false,
          });
        }
      });
    }
  });
}
 
var btnsROpera = "";
var btnsRCientes = "";
var cargatblHistorico = "obtHistoricoProcesos";
 
function iniciaTblHistorico() {
  url = getAbsolutePath() + cargatblHistorico;
  $.getJSON(url, {}, function(data) {
    tblHistorico.clear().draw();
    $.each(data, function(idx, opt) {
 
      btnsROpera = " <button class='btn btn-primary btn-sm' tabindex='0' data-bs-toggle='modal'"
        + " title='Actualizar Parametros' data-bs-target='#' aria-controls='table-datatable' type='button'"
        + " onclick='generarHistoricoOperaciones(" + opt.id + ")' id=" + opt.id + ">"
        + " <i class='bi bi-tools'></i></button>";
 
      btnsRCientes = " <button class='btn btn-success btn-sm' tabindex='0'"
        + " title='Ejecutar Proceso' aria-controls='table-datatable' type='button'"
        + " onclick='generarHistoricoClientes(" + opt.id + ")' id=" + opt.id + ">"
        + " <i class='bi bi-file-play-fill'></i></button>";
 
      tblHistorico.row.add([
        opt.fechaGeneracion,
        opt.i_usuario.vc_nombre_usuario,
        opt.i_usuario.vc_nombre_usuario,
        opt.fechaGeneracionvobo,
        btnsROpera + btnsRCientes
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

const idMigracion = 1; // puede hacerlo dinámico si se necesita

async function cargarEstadoInicial() {
    try {
        const res = await fetch(`/STD/migraciones/obtener-migracion/${idMigracion}`);
        if (!res.ok) throw new Error("No se pudo obtener el estado de la migración");
        const data = await res.json();

        const checkbox = document.getElementById("estatusSwitch");
        const label = document.getElementById("labelEstado");

        checkbox.checked = data.estatus === 1;
        label.innerText = data.estatus === 1 ? "Activo" : "Inactivo";

        // Si quieres mostrar mensaje de migración también
        if (data.mensajeResultado) {
            document.getElementById("mensajeResultado").innerText = data.mensajeResultado;
        }

    } catch (error) {
        console.error("Error al cargar estado inicial:", error);
    }
}

async function cambiarEstatus(checkbox) {
    const estatus = checkbox.checked ? 1 : 0;

    try {
        const res = await fetch("/STD/migraciones/cambiar-estatus", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `id=${idMigracion}&estatus=${estatus}`
        });

        if (res.ok) {
            document.getElementById("labelEstado").innerText = estatus === 1 ? "Activo" : "Inactivo";
            Swal.fire("¡Éxito!", `Estatus cambiado a ${estatus === 1 ? 'Activo' : 'Inactivo'}`, "success");
        } else {
            Swal.fire("Error", "No se pudo actualizar el estatus", "error");
            checkbox.checked = !checkbox.checked; // revertir visualmente
        }
    } catch (error) {
        console.error("Error al cambiar estatus:", error);
        Swal.fire("Error", "Falló la conexión con el servidor", "error");
        checkbox.checked = !checkbox.checked;
    }
}

window.onload = cargarEstadoInicial;

// Para cargar mensaje de resultado en pantalla
async function cargarMensajeMigracion() {
    try {
        const res = await fetch('/STD/migraciones/obtener-migracion/1');
        const data = await res.json();
        if (data) {
            // Mostrar mensaje resultado
            if (data.mensajeResultado) {
                document.getElementById("mensajeResultado").innerText = data.mensajeResultado;
            }

            // Mostrar estatus como activo/inactivo
            const checkbox = document.getElementById("estatusSwitch");
            checkbox.checked = data.estatus === 1;
            document.getElementById("labelEstado").innerText = data.estatus === 1 ? "Activo" : "Inactivo";
        }
    } catch (err) {
        console.error("Error al cargar datos de migración", err);
    }
}

window.onload = cargarMensajeMigracion;

