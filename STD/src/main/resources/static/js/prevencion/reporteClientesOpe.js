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
document.getElementById("uploadFormPEP").addEventListener("submit", async e => {
  e.preventDefault();

  const input = document.getElementById("fileUploadPEP");
  const file = input.files[0];

  //  Validar si se seleccionó un archivo
  if (!file) {
    swal("Debes seleccionar un archivo.", { icon: "warning" });
    return;
  }

  //  Validar extensión
  const extension = file.name.split('.').pop().toLowerCase();
  if (!["xls", "xlsx"].includes(extension)) {
    swal("Solo se permiten archivos Excel (.xls o .xlsx).", { icon: "error" });
    input.value = ""; // limpia el input
    return;
  }

  //  Leer y validar encabezados del archivo
  const encabezadosValidos = ["ClienteID", "nombre_completo"];

  try {
    const data = await file.arrayBuffer();
    const workbook = XLSX.read(data, { type: "array" });
    const primeraHoja = workbook.SheetNames[0];
    const hoja = workbook.Sheets[primeraHoja];
    const encabezados = XLSX.utils.sheet_to_json(hoja, { header: 1 })[0]; // primera fila

    // Normalizar nombres (elimina espacios y minúsculas)
    const headersNormalizados = encabezados.map(h =>
      h ? h.toString().trim().toLowerCase() : ""
    );

    const requeridosNormalizados = encabezadosValidos.map(h => h.toLowerCase());

    const faltantes = requeridosNormalizados.filter(h => !headersNormalizados.includes(h));

    if (faltantes.length > 0) {
      swal({
        title: "Error en encabezados",
        text: `El archivo no contiene las columnas requeridas: ${faltantes.join(", ")}`,
        icon: "error"
      });
      input.value = "";
      return;
    }

    //  Si todo está correcto, procesar envío
    const fd = new FormData();
    fd.append("file", file);

    swal({
      title: "Procesando archivo...",
      content: {
        element: "div",
        attributes: {
          innerHTML: '<div class="spinner-border text-primary" role="status"></div>',
          style: "display:flex;justify-content:center;padding:20px;"
        }
      },
      buttons: false,
      closeOnClickOutside: false,
      closeOnEsc: false
    });

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
        button: "Aceptar"
      });
    } else {
      swal({
        title: "Error",
        text: respuesta[0] || "Ocurrió un error inesperado.",
        icon: "error",
        button: "Aceptar"
      });
    }

  } catch (error) {
    console.error("❌ Error al validar archivo Excel:", error);
    swal("Error al leer el archivo. Asegúrate de que no esté dañado.", { icon: "error" });
  }
});

 
 
 
function confirmar(valor) {
  fetch(`/prevencion/reporteClientesOpe/confirmar?confirmado=${valor}`, { method: "POST" })
    .then(r => {
      if (r.redirected) window.location.href = r.url;
      else return r.text().then(alert);
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
      const tipoReporte = $("#tipoReporte").val();
      const fechaInicio = $("#fechaInicio").val();
      const fechaFin = $("#fechaFin").val();
      const anio = $("#anioReporte").val();

      // Validación condicional
      if (tipoReporte === "operaciones") {
        if (!fechaInicio || !fechaFin) {
          swal("Debe seleccionar fecha inicio y fecha fin.", {
            icon: "error",
            closeOnClickOutside: false,
          });
          return;
        }
      } else if (tipoReporte === "clientes") {
        if (!anio) {
          swal("Debe seleccionar el año del reporte de clientes.", {
            icon: "error",
            closeOnClickOutside: false,
          });
          return;
        }
      }

      swal({
        title: "Procesando información.",
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

      let url = "";
      let data = {};

      //  Si es CLIENTES, no se mandan fechas
      if (tipoReporte === "clientes") {
        url = getAbsolutePath() + "ejecutarVistoBuenoClientes";
        data = { anio: anio };
      } else {
        url = getAbsolutePath() + "ejecutarVistoBueno";
        data = { fechaInicio: fechaInicio, fechaFin: fechaFin };
      }

      $.ajax({
        url: url,
        type: "POST",
        dataType: "JSON",
        data: data,
        complete: function () {
          swal.close();
        },
        success: function (respuesta) {
          swal("", respuesta.mensaje || respuesta.mensaje || "Operación completada.", respuesta.tipo || "success")
            .then(() => {
              iniciaTblHistorico();
            });
        },
        error: function () {
          swal("Se presentó un error, intente más tarde.", {
            icon: "error",
            closeOnClickOutside: false,
          });
        },
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
        + " title='Reporte Historico de operaciones' data-bs-target='#' aria-controls='table-datatable' type='button'"
        + " onclick='generarHistoricoOperaciones(" + opt.id + ")' id=" + opt.id + ">"
        + " <i class='bi bi-file-earmark-diff'></i></button>";
 
      btnsRCientes = " <button class='btn btn-success btn-sm' tabindex='0'"
        + " title='Reporte Historico de clientes' aria-controls='table-datatable' type='button'"
        + " onclick='generarHistoricoClientes(" + opt.id + ")' id=" + opt.id + ">"
        + " <i class='bi bi-file-earmark-person'></i></button>";
 
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
 
const idMigracion = 1;
const BASE_URL = "/STD/prevencion";

async function cargarEstadoInicial() {
    try {
        const res = await fetch(`${BASE_URL}/obtener-migracion/${idMigracion}`);
        if (!res.ok) throw new Error("No se pudo obtener el estado de la migración");
        const data = await res.json();

        const checkbox = document.getElementById("estatusSwitch");
        const label = document.getElementById("labelEstado");
        checkbox.checked = data.estatus === 1;
        label.innerText = data.estatus === 1 ? "Activo" : "Inactivo";

        const btnEjecutar = document.getElementById("btnEjecutarMigracion");
        if (btnEjecutar) {
            const enEjecucion = data.ejecuta === 1;
            btnEjecutar.disabled = enEjecucion;
            btnEjecutar.classList.toggle("disabled", enEjecucion);
        }

        // Agrega esta línea para mostrar el mensaje siempre
        mostrarMensajeMigracion(data);

    } catch (error) {
        console.error("Error al cargar estado inicial:", error);
    }
}


async function activarMigracion() {
    const btn = document.getElementById("btnEjecutarMigracion");

    if (btn.disabled) return;

    btn.disabled = true;
    btn.classList.add("disabled");

    try {
        const res = await fetch(`${BASE_URL}/activar-ejecucion/${idMigracion}`, {
            method: "POST"
        });

        if (res.ok) {
            Swal.fire({
                title: "¡Éxito!",
                text: "Migración programada correctamente.",
                icon: "success",
                timer: 2000,
                showConfirmButton: false
            });

            // Re-consultar para obtener mensaje actualizado
            setTimeout(async () => {
                const estado = await fetch(`${BASE_URL}/obtener-migracion/${idMigracion}`);
                if (estado.ok) {
                    const data = await estado.json();
                    mostrarMensajeMigracion(data);
                }
                cargarEstadoInicial(); // También actualiza estados visuales
            }, 2100);

        } else {
            Swal.fire("Error", "No se pudo programar la migración", "error");
            btn.disabled = false;
            btn.classList.remove("disabled");
        }

    } catch (error) {
        console.error("→ Error de conexión:", error);
        Swal.fire("Error", "Falló la conexión con el servidor", "error");
        btn.disabled = false;
        btn.classList.remove("disabled");
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
            label.innerText = estatus === 1 ? "Activo" : "Inactivo";
            Swal.fire({ title: "¡Éxito!", text: `Estatus cambiado a ${estatus === 1 ? 'Activo' : 'Inactivo'}`, icon: "success", timer: 2000, showConfirmButton: false });
        } else {
            Swal.fire("Error", "No se pudo actualizar el estatus", "error");
            checkbox.checked = !checkbox.checked;
        }
    } catch (error) {
        console.error("Error:", error);
        Swal.fire("Error", "Falló la conexión con el servidor", "error");
        checkbox.checked = !checkbox.checked;
    }
}

 window.onload = cargarEstadoInicial;
// Para cargar mensaje de resultado en pantalla
function mostrarMensajeMigracion(data) {
    const mensaje = document.getElementById("mensajeResultado");
    if (!mensaje) return;

    mensaje.innerText = "";
    if (data.mensajeResultado) {
        mensaje.innerText = data.mensajeResultado;
    }
    if (data.cron) {
        mensaje.innerText += ` (Siguiente ejecución: ${data.cron})`;
    }
    mensaje.style.display = "block";
}

//  Función para mostrar/ocultar opciones según el tipo de reporte seleccionado
function mostrarOpcionesPorTipo() {
  const tipo = document.getElementById("tipoReporte").value;
  const bloqueClientes = document.getElementById("bloqueClientes");
  const bloqueOperaciones = document.getElementById("bloqueOperaciones");
  const btnClientes = document.getElementById("btnProcesarClientes");
  const btnOperaciones = document.getElementById("btnProcesarOperaciones");

  // Reiniciar visibilidad
  bloqueClientes.style.display = "none";
  bloqueOperaciones.style.display = "none";
  btnClientes.disabled = true;
  btnOperaciones.disabled = true;

  if (tipo === "clientes") {
    bloqueClientes.style.display = "block";
    btnClientes.disabled = false;
  } else if (tipo === "operaciones") {
    bloqueOperaciones.style.display = "block";
    btnOperaciones.disabled = false;
  }
}

// ========================================================
//  Generar reporte de CLIENTES filtrado por año
// ========================================================
async function generarReporteClientes() {
  const anio = document.getElementById("anioReporte") ? document.getElementById("anioReporte").value : null;

  if (!anio) {
    swal("Debe seleccionar un año antes de generar el reporte.", { icon: "warning" });
    return;
  }

  swal({
    title: "Generando reporte de clientes...",
    content: {
      element: "div",
      attributes: {
        innerHTML: '<div class="spinner-border text-primary" role="status"></div>',
        style: "display:flex;justify-content:center;padding:20px;"
      }
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false
  });

  try {
    // Cambiado al endpoint real
    const res = await fetch("generarRptClientesPorAnio", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ anio })
    });

    if (!res.ok) throw new Error("Error generando el reporte.");

    const respuesta = await res.json();
    const url = respuesta[0];

    // Abrir el archivo generado en una nueva pestaña
    window.open(
      window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + url
    );
    window.focus();

    swal.close();
    swal({
      title: "¡Éxito!",
      text: "El reporte de clientes se generó correctamente.",
      icon: "success",
      button: "Aceptar"
    });
  } catch (error) {
    console.error("❌ Error al generar el reporte de clientes:", error);
    swal.close();
    swal({
      title: "Error",
      text: "Ocurrió un problema al generar el reporte de clientes.",
      icon: "error",
      button: "Aceptar"
    });
  }
}

// ========================================================
//  Procesar Auditoría de CLIENTES (llama SP en backend)
// ========================================================
async function procesarAuditoriaClientes() {
  const anio = document.getElementById("anioReporte")?.value;

  if (!anio) {
    swal("Debe seleccionar un año antes de procesar la auditoría.", { icon: "warning" });
    return;
  }

  swal({
    title: "Procesando auditoría de clientes...",
    content: {
      element: "div",
      attributes: {
        innerHTML: '<div class="spinner-border text-primary" role="status"></div>',
        style: "display:flex;justify-content:center;padding:20px;"
      }
    },
    buttons: false,
    closeOnClickOutside: false,
    closeOnEsc: false
  });

  try {
    //  Este endpoint corresponde al @PostMapping("/procesarAuditoriaClientes")
    const res = await fetch("procesarAuditoriaClientes", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ anio })
    });

    if (!res.ok) throw new Error("Error procesando auditoría.");

    const data = await res.json();
    swal.close();

    if (data.tipo === "success") {
      swal({
        title: "¡Éxito!",
        text: data.mensaje,
        icon: "success",
        button: "Aceptar"
      });
    } else {
      swal({
        title: "Error",
        text: data.mensaje,
        icon: "error",
        button: "Aceptar"
      });
    }
  } catch (error) {
    console.error("❌ Error al procesar auditoría de clientes:", error);
    swal.close();
    swal({
      title: "Error",
      text: "Ocurrió un problema al procesar la auditoría.",
      icon: "error",
      button: "Aceptar"
    });
  }
}

// ========================================================
//  Generar reporte de OPERACIONES filtrado por rango de fechas
// ========================================================
async function generarReporteOperaciones() {
  const fechaInicio = document.getElementById("fechaInicio")?.value;
  const fechaFin = document.getElementById("fechaFin")?.value;

  if (!fechaInicio || !fechaFin) {
    swal("Debe seleccionar un rango de fechas antes de generar el reporte.", { icon: "warning" });
    return;
  }

  swal({
    title: "Generando reporte de operaciones...",
    content: {
      element: "div",
      attributes: {
        innerHTML: '<div class="spinner-border text-primary" role="status"></div>',
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

    if (!res.ok) throw new Error("Error generando el reporte.");

    const respuesta = await res.json();
    const url = respuesta[0];

    // Abrir el reporte Jasper generado
    window.open(
      window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + url
    );
    window.focus();

    swal.close();
    swal({
      title: "¡Éxito!",
      text: "El reporte de operaciones se generó correctamente.",
      icon: "success",
      button: "Aceptar"
    });
  } catch (error) {
    console.error("❌ Error al generar el reporte de operaciones:", error);
    swal.close();
    swal({
      title: "Error",
      text: "Ocurrió un problema al generar el reporte de operaciones.",
      icon: "error",
      button: "Aceptar"
    });
  }
}


