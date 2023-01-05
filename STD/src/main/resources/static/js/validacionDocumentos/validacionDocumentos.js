/**
 * 
 */
//Funcion para buscar la solicitud de credito
function buscarSolicitudCredito() {
	var solicitud = document.getElementById('i_no_solicitud_core').value;
	console.log("Entro en buscar producto");
	console.log("Solicitud: " + solicitud);
	$.ajax({
		url: "/buscar_solicitud?solicitud=" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log(respuesta);
			console.log(respuesta.length);*/

			var producto = document.getElementById('producto');
			var montoSolicitado = document.getElementById('montoSolicitado');
			var montoAutorizado = document.getElementById('montoAutorizado');
			var plazo = document.getElementById('plazo');
			var tasaNormal = document.getElementById('tasaNormal');
			var estatus = document.getElementById('estatus');
			var Nombreproducto = document.getElementById('Nombreproducto');
			var solicitudEnvio = document.getElementById('solicitudEnvio').value;
			//$("#divSolicitudEnvio").hide();
			producto.value = respuesta[0].productoCreditoID;
			montoSolicitado.value = respuesta[0].montoSolicitado;
			montoAutorizado.value = respuesta[0].montoAutorizado;
			plazo.value = respuesta[0].plazo;
			tasaNormal.value = respuesta[0].tasaFija;
			estatus.value = respuesta[0].estatus;
			Nombreproducto.value = respuesta[0].producto;
			traerParticipantes(solicitud);
			obtenerArchivosSolicitud(solicitudEnvio);
		}
	});
}

//Funcion para obtener los participantes de la solicitud de credito
function traerParticipantes(solicitud) {
	console.log("Entro en traer participantes");
	console.log("Solicitud: " + solicitud);
	var divParticipantes = document.getElementById("divParticipantes");
	$.ajax({
		url: "/trae_participantes?solicitud=" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
			//respuesta.forEach(element => console.log(element));
			divParticipantes.style.display = "block";
			$('#participantes > tbody').empty();
			$.each(respuesta, function(idx, opt) {
				//alert('Estoy recorriendo el registro numero: ' + idx);
				var rows = "<tr>"
					+ "<td>" + opt.nombreCompleto + "</td>"
					+ "<td>" + opt.rol + "</td>"
					+ "<td>" + opt.telefonoCelular + "</td>"
					+ "<td>" + opt.correoElectronico + "</td>"
					+ "</tr>";
				$('#participantes').append(rows)

			});
		}
	});
}

function obtenerArchivosSolicitud(solicitud) {
	console.log("Entro en obtenerArchivosSolicitud");
	var divDocumentos = document.getElementById("divDocumentos");
	$.ajax({
		url: "/doctEnvio/obtener_archivos?solicitud=" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
			//respuesta.forEach(element => console.log(element));
			divDocumentos.style.display = "block";
			$('#tablaDocumentos > tbody').empty();
			$.each(respuesta, function(idx, opt) {
				console.log("idx: " + idx);
				console.log("Id del documento: " + JSON.stringify(opt));
				var rows = `<tr>
		             <td>
		               <input type="text" name="${opt.nombreArchivo}" id="${opt.nombreArchivo}" value="${opt.nombreArchivo}" readonly>
		             </td>
		             <td>
		             	
							<button  class="btn btn-success" tabindex="0" 
									title="Ver" onclick="visualizarArchivo(${opt.idDoctoEnvio})"
									aria-controls="table-datatable" type="button">
									<i class="bi bi-eye-fill"></i>
							</button>
		               
		             </td>
		             <td>
		             
		             	
		             	<div>
		             	
					      <input type="radio" id="aprobacion1" name="aprobacion" value="1" />
					      <label for="contactChoice1">Aprobar</label>
					
					      <input type="radio" id="aprobacion2" name="aprobacion" value="0" />
					      <label for="contactChoice2">Desaprobar</label>
					
					    </div>
						
		             </td>
		           </tr>`;
				$('#tablaDocumentos').append(rows)

			});
		}
	});

}

function buscarDoctoEnvio(idDoctEnvio) {
	console.log("Entro en buscarDoctoEnvio");
	console.log("idDoctEnvio: " + idDoctEnvio);
	$.ajax({
		url: "/doctEnvio/obtener_doctEnvio/" + idDoctEnvio,
		async: false,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log("respuesta: " + JSON.stringify(respuesta));
			console.log("url:" + respuesta.vc_ruta_docto);*/
			//console.log("url: " + respuesta.vc_ruta_docto);
			urlDocto = respuesta.vc_ruta_docto;
			//return url;
		},
		error: function() {
			console.log("Error");
			swal({
				icon: 'warning',
				title: "No se encontro el archivo."
			});
		}
	});
	return urlDocto;
}

function visualizarArchivo(idDoctoEnvio) {
	console.log("Entro en visualizarArchivo");
	var solicitudEnvio = document.getElementById('solicitudEnvio').value;
	console.log("Solicitud envio: " + solicitudEnvio);
	console.log("idDoctoEnvio: " + idDoctoEnvio);
	var urlDocto = buscarDoctoEnvio(idDoctoEnvio);
	//console.log("Datos de doctoEnvio: " + urlDocto);
	//console.log("Datos de doctoEnvio: "+ buscarDoctoEnvio(idDoctoEnvio));
	window.open("/solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto, '_blank');
	//window.location.href = "/solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto;
	/*$.ajax({
		url: "/solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto,
		type: "GET",
		dataType: "JSON",
		success: function(response) {
			console.log("Success");
			//console.log("respuesta: " + respuesta);
			
			//window.open(respuesta);
			//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
			//respuesta.forEach(element => console.log(element));
			//console.log("Repuesta: " + JSON.stringify(respuesta));
		}
	});*/
}

