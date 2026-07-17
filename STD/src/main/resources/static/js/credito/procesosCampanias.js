// @ts-nocheck
/**
 ////////////////----DES-25-0014 Campanias----////////////////
////////////////----viviana.zamudio-------------------------////////////////
 */
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
 
// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");
var mensajeError = "Datos incorrectos, favor de validar.";
 
function valVacio() {
    
    url = "";
    var result = false;
    var contador = 0;
    
    
    if(document.getElementById("FechaFin").value < document.getElementById("FechaInicio").value){
        mensajeError = "Fecha fin no debe ser menor a fecha inicio.";
        document.getElementById("FechaFin").style.borderColor = "#F70707";
        contador = contador + 1;
        result = true;
    }
    
    if(document.getElementById("FechaInicio").value > document.getElementById("FechaFin").value){
        mensajeError = "Fecha inicio no debe ser mayor a fecha fin.";
        document.getElementById("FechaInicio").style.borderColor = "#F70707";
        contador = contador + 1;
        result = true;
    }
    
    if(contador == 0){
        document.getElementById("FechaInicio").style.borderColor = "";
        document.getElementById("FechaFin").style.borderColor = "";
        // @ts-ignore
    
        result = false;
    }else{
        
        result = true;
    }
    return result;
}
 
function buscarProcesosCampanias() {
 
    if (valVacio() === false) {
 
        const select = document.getElementById("Campania");
        const seleccionProd = Array.from(select.selectedOptions);
 
        if (seleccionProd.length === 0) {
 
            Swal.fire({
                title: "Advertencia",
                text: "Debe seleccionar al menos una campaña.",
                icon: "warning"
            });
 
            return;
        }
 
        const fechaInicio = document.getElementById("FechaInicio").value;
        const fechaFin = document.getElementById("FechaFin").value;
 
        const selectedCam = Array.from(select.selectedOptions)
            .map(option => option.value);
 
        const campaniasSelect = selectedCam.join(",");
 
        $.ajax({
            url: newUrlBase + "credito/api/procesosCampanias",
            type: "GET",
            dataType: "json",
 
            data: {
                fechaInicio: fechaInicio,
                fechaFin: fechaFin,
                campania: campaniasSelect
            },
 
            success: function (data) {
 
                const tbody = $("#tablaProcesosBody");
                tbody.empty();
 
                if (!data.lista || data.lista.length === 0) {
                    tbody.append(
                        '<tr>' +
                            '<td colspan="7" class="text-center">' +
                                "No se encontraron resultados." +
                            "</td>" +
                        "</tr>"
                    );
 
                    return;
                }
 
                const formatearFechas = fechaStr => {
                    if (!fechaStr || fechaStr === "Sin fecha") {
                        return fechaStr || "";
                    }
 
                    const fecha = new Date(
                        String(fechaStr).replace(" ", "T")
                    );
 
                    if (isNaN(fecha.getTime())) {
                        return fechaStr;
                    }
 
                    return fecha.toLocaleDateString("es-ES", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit"
                    });
                };
 
                data.lista.forEach(function (item) {
 
                    const fila = `
                        <tr>
                            <td>
                                <input
                                    type="checkbox"
                                    name="seleccion"
                                    value="${item.pk_id_proceso_campania}">
                            </td>
                            <td>${formatearFechas(item.dt_fecha_extraccion)}</td>
                            <td>${item.vc_nombre_usuario || ""}</td>
                            <td>${item.vc_tipo_campania || ""}</td>
                            <td>${formatearFechas(item.dt_fecha_inicio_vigencia)}</td>
                            <td>${formatearFechas(item.dt_fecha_fin_vigencia)}</td>
                            <td>${item.vc_estatus || ""}</td>
                        </tr>
                    `;
 
                    tbody.append(fila);
                });
 
                $("#tablaProcesosBody")
                    .off(
                        "change",
                        "input[type='checkbox'][name='seleccion']"
                    )
                    .on(
                        "change",
                        "input[type='checkbox'][name='seleccion']",
                        function () {
                            if (this.checked) {
                                $("input[type='checkbox'][name='seleccion']")
                                    .not(this)
                                    .prop("checked", false);
                            }
                        }
                    );
            },
 
            error: function (err) {
                console.error(
                    "Error al obtener procesos campañas:",
                    err
                );
 
                let mensaje =
                    "Ocurrió un error al consultar los procesos de campaña.";
 
                if (
                    err.responseJSON &&
                    err.responseJSON.error
                ) {
                    console.error(
                        "Detalle del error del backend:",
                        err.responseJSON.error
                    );
 
                    mensaje =
                        "Error del servidor: " +
                        err.responseJSON.error;
                } else {
                    console.error(
                        "Respuesta completa:",
                        err.responseText
                    );
                }
 
                Swal.fire({
                    title: "Error",
                    text: mensaje,
                    icon: "error"
                });
            }
        });
 
    } else {
 
        Swal.fire({
            title: "Advertencia",
            text: mensajeError,
            icon: "warning"
        });
    }
}
 
//funcion para llenar combo opcion multiple de campañas
document.addEventListener("DOMContentLoaded", function() {
    //console.log("url:", newUrlBase);
    $.ajax({
        url: newUrlBase + 'credito/api/catTipoCampania',
        type: 'GET',
        success: function(data) {
            //console.log("Datos recibidos:", data);
            let select = $('#Campania');
            select.empty();
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_tipo_campania + '">' + item.vc_tipo_campania + '</option>');
            });
            
            // Marcar seleccionados
            //select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar tipos campañas', err);
        }
    });
});
 
function generarReportProcesosCampanias() {
    
    const seleccionados = document.querySelectorAll('input[name="seleccion"]:checked');
      if (seleccionados.length > 0) {
        
        const ids = [];
        document.querySelectorAll('input[name="seleccion"]:checked').forEach(cb => {
              ids.push(cb.value);
        });
        console.log("IDs seleccionados:", ids);
        
        ///////////////////////////////
        var urlServicio;
        //var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
        //urlServicio = newUrlBase + "credito/reporteProcesosCampania?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&campania="+ids;
        urlServicio = newUrlBase + "credito/reporteProcesosCampania?proceso="+ids;
        //console.log("urlServicio:", urlServicio);
        //urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
        
        $.ajax({
        url: urlServicio,
        type: "POST",
        dataType: "JSON",
        beforeSend: function() {
            $.blockUI({ message: 'Espere un momento...' });
        },
        complete: function() {
            setTimeout($.unblockUI, 500);
        },
        success: function(respuesta) {
 
            //var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];
            
            var base = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4);
            //var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
 
            //console.log(urlReporte);
            //urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
            //window.open(urlReporte);
             // Abrir cada reporte
            respuesta.forEach(function(url) {
                var urlReporte = base + url;
                window.open(urlReporte);
            });
            window.focus();
 
        }, error: function(XMLHttpRequest, textStatus, errorThrown) {
            Swal.fire({
                title: "Error",
                text: "Se presentó un error. Intente más tarde.",
                icon: "error"
            });
        }
        });
        //////////////
        
      } else {
        
        Swal.fire({
            title: "Advertencia",
            text: "Debes seleccionar al menos una fila.",
            icon: "warning"
        });
      }
    
  }