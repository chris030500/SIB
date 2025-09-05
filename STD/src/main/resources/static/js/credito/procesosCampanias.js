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
    
    if (valVacio() == false) {
		
		let select = document.getElementById("Campania");
		let seleccionProd = Array.from(select.selectedOptions);
		
		
		
		
		if (seleccionProd.length === 0) {

			swal({
				title: "",
				text: "Debe seleccionar al menos un producto.",
				icon: "warning",
			});
			
		}else{
			console.log("entra a todo ok");
			let fechaInicio = document.getElementById("FechaInicio").value;
			let fechaFin = document.getElementById("FechaFin").value;
			//let tipoCampania = seleccionProd[0].value; // Si solo se permite una
			
			const selectedCam = Array.from(select.selectedOptions).map(option => option.value);
			//let campanias = Array.from(document.getElementById("Campania").selectedOptions).map(opt => opt.value);
			let camaniasSelect = selectedCam.join(",");
			
			$.ajax({
			    url: newUrlBase + 'credito/api/procesosCampanias',
			    type: 'GET',
			    data: {
			        fechaInicio: fechaInicio,
			        fechaFin: fechaFin,
			        Campania: camaniasSelect
			    },
			    success: function(data) {
					console.log("data: " + data);
			        //let tbody = document.querySelector("tablaProcesosBody");
			        let tbody = $("#tablaProcesosBody");
			        tbody.empty();

					if (data.length === 0) {
						tbody.append('<tr><td colspan="3">No se encontraron resultados.</td></tr>');
					} else {
						data.forEach(function(item) {
							let fila = `<tr>
								<td><input type="checkbox" name="seleccion" value="${item.pk_id_proceso_campania}"></td>
								<td>${item.dt_fecha_extraccion || ''}</td>
								<td>${item.i_user_reg?.vc_nombre_usuario || ''}</td>
								<td>${item.tipoCampania?.vc_tipo_campania || ''}</td>
								<td>${item.dt_fecha_inicio_vigencia || ''}</td>
								<td>${item.dt_fecha_fin_vigencia || ''}</td>
								<td>${item.estatusCampania?.vc_estatus || ''}</td>
							</tr>`;
							tbody.append(fila);
						});
					}
				},
			    error: function(err) {
			        console.error("Error al obtener procesos campañas:", err);
			    }
			});
		}
	} else {

		swal({
			title: "",
			text: mensajeError,
			icon: "warning",
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
    	//console.log("IDs seleccionados:", ids);
    	
    	///////////////////////////////
    	var urlServicio;
    	//var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
    	urlServicio = newUrlBase + "credito/reporteProcesosCampania?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&campania="+ids;
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

			var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];
			//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];

			//console.log(urlReporte);
			//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
			window.open(urlReporte);
			window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
		});
    	//////////////
    	
  	} else {
    	
    	swal({
			title: "",
			text: "Debes seleccionar al menos una fila.",
			icon: "warning",
		});
  	}
    
  }