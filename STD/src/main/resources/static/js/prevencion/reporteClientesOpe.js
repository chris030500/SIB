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

  // Evento click del botón para ejecutar la auditoría
 /* btnProcesarAuditoria.addEventListener("click", async (event) => {
   event.preventDefault(); // Evita que se envíe el formulario

    const inicio = inputInicio.value;
    const fin = inputFin.value;

    if (fechasInvalidas(inicio, fin)) return;

    try {
      // Solicita al backend ejecutar el SP para procesar la auditoría
      const res = await fetch("/prevencion/auditoria/actualiza-peps", {
        method: "POST",
      });

      if (!res.ok) throw new Error("Error al ejecutar la auditoría.");

      // Habilitar el botón para generar el reporte si todo fue bien
      btnProcesar.disabled = false;
      alert("Auditoría procesada con éxito. Ahora puedes generar el reporte.");
    } catch (error) {
      console.error("Error al ejecutar la auditoría:", error);
      alert("Se produjo un error al ejecutar la auditoría.");
    }
  });*/

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
 
     if (!res.ok) throw new Error("Error obteniendo la URL del reporte.");
 
	 const respuesta = await res.json();
	 const url = respuesta[0];
	 window.open(window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0]);
	 window.focus();
    } catch (error) {
      console.error("Error al generar el reporte:", error);
      alert("Se produjo un error al generar el reporte.");
    }
  });

// -----------------------------------------------------------------------------------------------------------
 
  btnProcesarProceso.addEventListener("click", async (event) => {
    event.preventDefault(); // ← importante si el botón está en un formulario
 
    const inicio = inputInicio.value;
    const fin    = inputFin.value;
 
    if (fechasInvalidas(inicio, fin)) return;
 
    try {
      const res = await fetch("generarRptOperacionesAuditoria", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ fechaInicio: inicio, fechaFin: fin })
      });
 
      if (!res.ok) throw new Error("Error generando el reporte.");
 
     if (!res.ok) throw new Error("Error obteniendo la URL del reporte.");
 
	 const respuesta = await res.json();
	 const url = respuesta[0];
	 window.open(window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0]);
	 window.focus();
    } catch (error) {
      console.error("Error al generar el reporte:", error);
      alert("Se produjo un error al generar el reporte.");
    }
  });
  
});
 
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
					var url= "";
					url = "ejecutarDatosProceso";
function peticionPost() {
  var metodo = 0;
	
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
		url: getAbsolutePath()+url,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
		
		},
		complete: function() {
			swal.close();
		},
		success: function(respuesta) {
   			url = "";			
			swal("",respuesta.mensaje,respuesta.tipo).then(resp => {

				if (metodo == 1) {
					iniciaTblParametros();
					limpiarParametros();

				} else if (metodo == 2) {
					iniciaTblClasificacion();
					limpiarDatosClasificacion();
				}else if(metodo == 3){
					//proceso manual Bases de ofertas automaticas. 
					if(respuesta.envio == "1" ){
						correosAsignacion();
					}
				}else if(metodo == 4){
					cargatblProcesoG();
					limpiarParametros();	
				}else if(metodo == 5){
		
					window.open(window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta.reporte);
					window.focus();
				}
			});


		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("Se presento un error, intente mas tarde.", {
				closeOnClickOutside: false,
				icon: "error",
			});
		}
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