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
				var rows = `<tr class="contarTr" id="${opt.idDoctoEnvio}">
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
		             	
					      <input type="radio" id="aprobacion1" name="${opt.idDoctoEnvio}" onclick="sinObservacion(${opt.idDoctoEnvio})" value="1" />
					      <label for="contactChoice1">Aprobar</label>
					
					      <input type="radio" id="aprobacion2" name="${opt.idDoctoEnvio}" onclick="agregarObservacion(${opt.idDoctoEnvio})"value="0" />
					      <label for="contactChoice2">Desaprobar</label>
						
						  <textarea name="textarea" rows="3" id="comentario${opt.idDoctoEnvio}" cols="50" style="display:none" ></textarea>
						  
					    </div>
						
		             </td>
		           </tr>`;
				$('#tablaDocumentos').append(rows);

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
	window.open("/solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto, '_blank');
	//window.location.href = "/solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto;

}

function agregarObservacion(id) {
	console.log("Entro en agregarObservacion");
	var idComentario = "comentario" + id;
	var comentario = document.getElementById(idComentario);
	console.log("idComentario: " + idComentario);
	comentario.style.display = "block";
	//console.log("Comentario textArea: " + JSON.stringify(comentario));

}

function sinObservacion(id) {
	console.log("Entro en sinObservacion");
	var idComentario = "comentario" + id;
	var comentario = document.getElementById(idComentario);
	console.log("idComentario: " + idComentario);
	comentario.style.display = "none";
}


function VoBoDocumentos() {
	var banderaVoBo = false;

	var idTr = document.querySelectorAll('.contarTr');
	console.log("contarTr: " + idTr.length);
	var miArray = new Array(idTr.length);
	var conteo = 0;
	console.log("Array: " + miArray.length);
	for (var i = 0; i < idTr.length; i++) {
		var tr = idTr[i].id
		console.log("El id del tr es: " + tr);
		var aprobacion = $('input[name="' + tr + '"]:checked').val();
		if (aprobacion != null) {
			console.log("Aprobación: " + i + " " + aprobacion);
			if (aprobacion == 1) {
				console.log("la aprobación es valida (1)");
			} else if (aprobacion == 0) {
				console.log("la aprobación es invalida (0)");
				var idComentario = "comentario" + tr;
				var comentario = document.getElementById(idComentario).value;
				console.log("Comentario: " + comentario);
				$.ajax({
					async: false,
					url: "/hallazgoSolicitud/guardarHallazgo?idDoctoEnvio=" + tr + "&comentario=" + comentario,
					type: "POST",
					dataType: "JSON",
					success: function(respuesta) {
						console.log("La respuesta es correcta");
					},
					error: function(respuesta) {
						console.log("Error: " + JSON.stringify(respuesta));
						//console.log("Error: " + respuesta.status);
					}
				});
			}
			banderaVoBo = true;
			miArray[i] = true;

			$.ajax({
				async: false,
				url: "/doctEnvio/validar_doctEnvio?estatus=" + aprobacion + "&idDoctoEnvio=" + tr,
				type: "POST",
				dataType: "JSON",
				success: function(respuesta) {
					console.log("Respuesta: " + JSON.stringify(respuesta));
				},
				error: function(respuesta) {
					//console.log("Respuesta: " + JSON.stringify(respuesta));
					swal({
						icon: "info",
						title: "Error: " + JSON.stringify(respuesta),
						showConfirmButton: false

					});
				}
			});
		} else {
			//console.log("Favor de llenar todos los campos");
			miArray[i] = false;
			swal({
				icon: "info",
				title: "Favor de llenar todos los campos de aprobación",
				showConfirmButton: false

			});
		}

	}
	//console.log("miArray: " + miArray);

	for (i = 0; i < miArray.length; i++) {
		if (miArray[i] === true) {
			conteo = conteo + 1;
		}
	}

	if (conteo == miArray.length) {
		console.log("Todos los valores son true");
		validarArchivosSolicitud();
	}
	//console.log("Numero de documentos: " + numTr);
}

function validarArchivosSolicitud() {
	console.log("Entro validarArchivosSolicitud");
	var solicitudEnvio = document.getElementById('solicitudEnvio').value;
	console.log("Solicitud envio: " + solicitudEnvio);
	//var estatus;
	$.ajax({
		async: false,
		url: "/solicitudEnvio/validacionArchivoSolicitud?solicitud=" + solicitudEnvio,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("Respuesta de solicitudEnvio/validacionArchivoSolicitud?: " + respuesta);
			validarSoliEnvio(respuesta, solicitudEnvio);
		},
		error: function(respuesta) {
			//console.log("Respuesta: " + JSON.stringify(respuesta));
			swal({
				icon: "info",
				title: "Error: " + JSON.stringify(respuesta),
				showConfirmButton: false

			});
		}
	});

}

function validarSoliEnvio(estatus, idSolicitud) {
	var solicitudCore = document.getElementById('i_no_solicitud_core').value;
	$.ajax({
		async: false,
		url: "/solicitudEnvio/validarSoliEnvio/" + estatus + "/" + idSolicitud + "/"+solicitudCore,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("Respuesta validarSoliEnvio: " + respuesta);
			// Todos los documentos fueron aprobados
			if (respuesta === 1) {
				swal({
					icon: "success",
					title: "Revisión de documentos exitosa",
					showConfirmButton: false
					//Generamos el archivo PDF y XML
					

				}).then((result) => {
					window.location.replace("/solicitudEnvio/solicitudes");
				});

			} else {
				// Si la respuesta es diferente de 1 significa que existe un documento a corregir
				swal({
					icon: "success",
					title: "Validación correcta",
					text: "solicitud: " + solicitudCore,
					showConfirmButton: false

				});
			}

		},
		error: function(respuesta) {
			//console.log("Respuesta: " + JSON.stringify(respuesta));
			console.log("Error: " + respuesta.status);
			if (respuesta.status === 500) {
				console.log("Salio error 500");
				swal({
					icon: "info",
					title: "Error " + respuesta.status + ":",
					text: "No se encontro la solicitud: " + idSolicitud,
					showConfirmButton: false

				});
			}
			swal({
				icon: "info",
				title: "Error: " + respuesta.status,
				showConfirmButton: false

			});
		}
	});
}

function generarArchivoPDF(solicitud){
	console.log("GenerarArchivoPDF");
	
}

