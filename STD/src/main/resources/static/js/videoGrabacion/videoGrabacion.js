var servicioReporte = "solicitudes/";
var buscarCliente = "buscarCliente/"
var opcionSeleccionada = "";
var mensajeVal="";

$(document).ready(function() {
	inicioPantalla();


	$("#opciones").change(function() {
		opcionSeleccionada = $("#opciones").val();
		if (opcionSeleccionada > 0) {

			switch (opcionSeleccionada) {
				case "1":
					$("#divCliente").show();
					$("#divSolicitud").hide();
					$("#divFechas").hide();
					
					break;
				case "2":
					$("#divCliente").hide();
					$("#divSolicitud").show();
					$("#divFechas").hide();
					break;
				case "3":
					$("#divCliente").hide();
					$("#divSolicitud").hide();
					$("#divFechas").show();
					break;
				default:

					break;

			}

		} else {
			swal({
				title: "Error",
				text: "Debe seleccionar una opcion de Busqueda",
				icon: "warning",
			});
		}
	});

});


function inicioPantalla() {
	$("#divCliente").hide();
	$("#divSolicitud").hide();
	$("#divFechas").hide();
	$("#divResultados").hide();
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}



function validarVacio(){
	var validacion = true;
	switch(opcionSeleccionada){
		case "1":
			if(document.getElementById("clienteBusqueda").value.length ==0){
				document.getElementById("clienteBusqueda").style.borderColor = "#F70707";
				validacion = false;	
			}
				
			break;
		case "2":
			if(document.getElementById("solicitudBusqueda").value.length ==0){
				document.getElementById("solicitudBusqueda").style.borderColor = "#F70707";
				validacion = false;	
			}
			break;
		case "3":
			if(document.getElementById("fechaInicio").value.length ==0 || document.getElementById("fechaFin").value.length ==0){
				document.getElementById("fechaInicio").style.borderColor = "#F70707";
				document.getElementById("fechaFin").style.borderColor = "#F70707";
				validacion = false;	
			}
			break;
		default:
			validacion = true;
			break;
	}
	return validacion;
}

function urlServicio(){
	var separador = "/";
	var urlGuardar = getAbsolutePath();
	//urlGuardar=urlGuardar.replace("videoGrabacion/", "");
	switch(opcionSeleccionada){
		case "1":
			urlGuardar = urlGuardar+buscarCliente+document.getElementById("clienteBusqueda").value;
			break;
		case "2":
			urlGuardar = urlGuardar+document.getElementById("solicitudBusqueda").value;
			break;	
		case "3":
			urlGuardar = urlGuardar+document.getElementById("fechaInicio").value+separador+document.getElementById("fechaFin").value;
			break;
		default:
			break;		
	}
	

	return urlGuardar;
}

function buscarDatos() {
	
	if(!validarVacio()){
			swal({
				title: "Error",
				text: "Debe Ingresar datos para realizar la busqueda.",
				icon: "warning",
			});
	}else {
	var divResultados = document.getElementById("divResultados");
	$.ajax({
		url: urlServicio(),
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			if (respuesta == null || respuesta == '') {
				console.log("Error");
				swal({
					title: "Error",
					text: "Los parametros ingresados no generan resultados",
					icon: "warning",
				});
				divResultados.style.display = "none";
			} else {
								
				divResultados.style.display = "block";
				$('#resultados > tbody').empty();
				$.each(respuesta, function(idx, opt) {
					//alert('Estoy recorriendo el registro numero: ' + idx);
					var rows = "<tr>"
						+ "<td>" + opt.pk_id_solicitud_envio + "</td>"
						+"<td>"+opt.clienteID+"</td>"
						+"<td>"+opt.solicitudID+"</td>"
						+ "<td>" + opt.estatus + "</td>"
						+ "<td>" + opt.producto + "</td>"
						+ "<td>" + opt.fechaRegistro + "</td>"
						+ "<td>" + opt.fechaAutorizacion + "</td>"
						+ "<td>" + opt.fechaCancelacion + "</td>"
						+ "<td>" + opt.fechaEnvio + "</td>"
						+ "<td>"
						+ "<button class='btn btn-warning' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top'"
						+ "title='Ver Documentos' aria-controls='table-datatable' type='button'"
						+ "data-tooltip='Show filters' th:onclick='verDocumentos()' id="
						+ opt.pk_id_solicitud_envio 
						+">"
						+ "<i class='bi-filetype-pdf'></i></td>"
						+ "</tr>";
					$('#resultados').append(rows)
				});


			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "---",
				icon: "warning",
			});
		}
	});
	
	}
}
