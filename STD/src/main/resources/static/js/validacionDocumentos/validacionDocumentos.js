/**
 * 
 */
//Ejecución de evento para mostrar loader y terminar de mostrarlo cuando la pantalla se haya cargado por completo.
window.addEventListener("load", function() {
	const loader = document.getElementById("loader");
	loader.classList.add("hide-loader");
});

const tiempoEspera = 60000;

//Funcion que obtiene la url absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}


var urlBase = getAbsolutePath();
var newUrlBase = urlBase.replace("solicitudEnvio/verSolicitud/", "");

// Variables | para el spinner
/*var spinner = document.getElementById('spinnerID');
var principal = document.getElementById('principalID');*/

//Funcion que muestra mensaje de advertencia sobre las fechas de los documentos
function mensaje() {
	$.ajax({
		url: urlBase + "mensajeValidaFecha",
		async: false,
		type: "GET",
		success: function(respuesta) {
			mensaje = respuesta;
			//console.log("mensaje: " + mensaje);
			document.getElementById('mensaje').innerHTML = mensaje;
		}
	});
}

//Funcion para obtener la url del WS
function WS_IAD() {
	//var urlBase = getAbsolutePath();
	var ws1 = "";
	//console.log("URL WS: " + newUrlBase + "conexionWS_IAD");
	$.ajax({
		url: newUrlBase + "conexionWS_IAD",
		async: false,
		type: "GET",
		dataType: "text",
		success: function(respuesta) {

			console.log("Respuesta :" + respuesta);
			ws1 = respuesta;
			return ws1;
		},
		error: function(respuesta) {
			console.log("Error");
			//console.log("Respuesta: " + respuesta.responseText);
			ws1 = respuesta;

			swal({
				icon: 'warning',
				title: "No se encontro la conexión con WS IAD."
			});
			return ws1;
		}
	});

	return ws1;
}

//Funcion para validar si exite conexión con el WS del API FAD
function conexionWS_IAD() {
	//var urlBase = getAbsolutePath();
	/*spinner.style.display = 'block';
	principal.style.display = 'none';*/
	var ws = WS_IAD();
	var respuestaWs = "";
	console.log("URL WSCONEXION: " + ws + "conexionWS");
	loader.classList.remove("hide-loader");
	$.ajax({
		url: ws + "/conexionWS",
		async: false,
		type: "GET",
		dataType: "text",
		beforeSend: function() {
			//document.getElementById('btnRelDoc').disabled = true;
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			//document.getElementById('btnRelDoc').disabled = false;
			setTimeout($.unblockUI, 5000);
			//$.unblockUI();
		},
		success: function(respuesta) {
			/*console.log("respuesta: " + JSON.stringify(respuesta));
			console.log("url:" + respuesta.vc_ruta_docto);*/
			//console.log("url: " + respuesta.vc_ruta_docto);
			//urlWS = JSON.stringify(respuesta);
			console.log("Respuesta :" + JSON.stringify(respuesta));
			respuestaWs = respuesta;
			return respuestaWs;
		},
		error: function(respuesta) {
			console.log("Error EN  WSCONEXION");
			//console.log("Respuesta: " + respuesta.responseText);
			console.log(JSON.stringify(respuesta));
			respuestaWs = JSON.stringify(respuesta);
			if (respuesta.status == 0) {
				respuestaWs = 0;
			}
			/*spinner.style.display = 'none';
			principal.style.display = 'block';*/
			return respuestaWs;

		}


	});

	return respuestaWs;
}




//Funcion para buscar la solicitud de credito
function buscarSolicitudCredito() {

	var solicitud = document.getElementById('i_no_solicitud_core').value;
	mensaje();
	/*console.log("urlBase: " + urlBase);
	console.log("newURL: " + newUrlBase);
	console.log("Entro en buscar producto");
	console.log("Solicitud: " + solicitud);*/
	$.ajax({
		url: newUrlBase + "buscar_solicitud?solicitud=" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log(respuesta);
			console.log(respuesta.length);*/
			//console.log("respuesta buscarSolicitudCredito" + JSON.stringify(respuesta));
			if (respuesta == null || respuesta == '') {
				//console.log("Error");
				swal({
					title: "Error",
					text: "La solicitud que ingresaste no existe",
					icon: "warning",
				});
			} else {
				var solicitudEnvio = document.getElementById('solicitudEnvio').value;
				var producto = document.getElementById('producto');
				var montoSolicitado = document.getElementById('montoSolicitado');
				var montoAutorizado = document.getElementById('montoAutorizado');
				var plazo = document.getElementById('plazo');
				var tasaNormal = document.getElementById('tasaNormal');
				var estatus = document.getElementById('estatus');
				var Nombreproducto = document.getElementById('Nombreproducto');
				var clienteId = document.getElementById('clienteId');
				//var solicitudEnvio = document.getElementById('solicitudEnvio').value;
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
				datosSoliEnvio(solicitudEnvio);
				clienteId.value = respuesta[0].clienteId;

			}

		}
	});
}

//Funcion para realizar la busqueda del documento
function datosSoliEnvio(solicitudEnvio) {
	//var urlBase = getAbsolutePath();
	console.log("Entro en datosSoliEnvio");
	console.log("solicitudEnvio: " + solicitudEnvio);
	$.ajax({
		url: newUrlBase + "solicitudEnvio/solicitud?solicitud=" + solicitudEnvio,
		async: false,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("respuesta: " + JSON.stringify(respuesta));
			//console.log("i_estatus: " + respuesta.i_estatus);
			var estatus = respuesta.i_estatus;
			if (estatus != 1) {

			} else {
				var btnVoBo = document.getElementById("btnVoBo");
				btnVoBo.style.display = "block";
			}
		},
		error: function() {
			//console.log("Error");
			swal({
				icon: 'warning',
				title: "No se encontro el archivo."
			});
		}
	});
}

//Funcion para obtener los participantes de la solicitud de credito
function traerParticipantes(solicitud) {
	//var urlBase = getAbsolutePath();
	/*console.log("Entro en traer participantes");
	console.log("Solicitud: " + solicitud);*/
	var divParticipantes = document.getElementById("divParticipantes");
	$.ajax({
		url: newUrlBase + "trae_participantes?solicitud=" + solicitud,
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

//Funcion para obtener los archivos de la solicitud
function obtenerArchivosSolicitud(solicitud) {
	//var urlBase = getAbsolutePath();
	//console.log("Entro en obtenerArchivosSolicitud");
	var divDocumentos = document.getElementById("divDocumentos");
	$.ajax({
		url: newUrlBase + "doctEnvio/obtener_archivos?solicitud=" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
			if (respuesta == '' || respuesta == null) {
				console.log("solicitud: " + solicitud);
				//console.log("La respuesta es vacía");
				const miDiv = document.querySelector("#divParticipantes");

				// Crear un nuevo elemento <p>
				const nuevoElemento = document.createElement("h5");
				nuevoElemento.style.cssText = 'text-align: center; font-weight: bold; color: red;';
				// Establecer el contenido del nuevo elemento
				nuevoElemento.textContent = "Ya se realizó la validación de documentos";

				// Agregar el nuevo elemento debajo del elemento existente
				miDiv.appendChild(nuevoElemento);
			} else {
				divDocumentos.style.display = "block";
				$('#tablaDocumentos > tbody').empty();
				$.each(respuesta, function(idx, opt) {
					console.log("respuesta: " + JSON.stringify(respuesta));
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
			//respuesta.forEach(element => console.log(element));


		}
	});

}


//Funcion para realizar la busqueda del documento
function buscarDoctoEnvio(idDoctEnvio) {
	//var urlBase = getAbsolutePath();
	/*console.log("Entro en buscarDoctoEnvio");
	console.log("idDoctEnvio: " + idDoctEnvio);*/
	$.ajax({
		url: newUrlBase + "doctEnvio/obtener_doctEnvio/" + idDoctEnvio,
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
			//console.log("Error");
			swal({
				icon: 'warning',
				title: "No se encontro el archivo."
			});
		}
	});
	return urlDocto;
}

//Funcion para visualizar el archivo que se seleccione
function visualizarArchivo(idDoctoEnvio) {
	//var urlBase = getAbsolutePath();
	//console.log("Entro en visualizarArchivo");
	var solicitudEnvio = document.getElementById('solicitudEnvio').value;
	/*console.log("Solicitud envio: " + solicitudEnvio);
	console.log("idDoctoEnvio: " + idDoctoEnvio);*/
	var urlDocto = buscarDoctoEnvio(idDoctoEnvio);
	window.open(newUrlBase + "solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto, '_blank');
	//window.location.href = "/solicitudEnvio/visualizarArchivo/?nombreArchivo=" + urlDocto;

}

//Funcion para activar el campo de observaciones
function agregarObservacion(id) {
	//console.log("Entro en agregarObservacion");
	var idComentario = "comentario" + id;
	var comentario = document.getElementById(idComentario);
	//console.log("idComentario: " + idComentario);
	comentario.style.display = "block";
	//console.log("Comentario textArea: " + JSON.stringify(comentario));

}

//Funcion que desactiva el campo de observaciones
function sinObservacion(id) {
	//console.log("Entro en sinObservacion");
	var idComentario = "comentario" + id;
	var comentario = document.getElementById(idComentario);
	//console.log("idComentario: " + idComentario);
	comentario.style.display = "none";
}

/*function pageLoaded() {
  let loaderSection = document.querySelector('.loader-section');
  loaderSection.classList.add('loaded');
}

document.onload = pageLoaded();*/

/*function VoBoDocumentos() {
	principal.style.display = 'none';
	spinner.style.display = 'block';
	document.getElementById('btnVoBoDocumentos').disabled = true;

	VoBoDocumentos1();
}*/

const loader = document.getElementById("loader");

function handleClick() {
	// muestra el loader
	/*document.getElementById('loader').style.display = 'block';
	document.getElementById('principalID').style.display = 'none';*/
	setTimeout(function() {
		document.getElementById('spanCargando').style.display = 'block';
		document.getElementById('spanGuardar').style.display = 'none';
	}, 0);
	document.getElementById('btnVoBoDocumentos').disabled = true;
	loader.classList.remove("hide-loader");
	// ejecuta la función que incluye la petición AJAX
	//VoBoDocumentos();
}

// agrega el evento click al botón
document.getElementById('btnVoBoDocumentos').addEventListener('click', function() {
	handleClick();
	VoBoDocumentos();
});
//document.getElementById('btnVoBoDocumentos').addEventListener('click', handleClick);

//Funcion para realizar el VoBo de los documentos
function VoBoDocumentos() {

	/*document.getElementById('btnVoBoDocumentos').disabled = true;
	principal.style.display = 'none';
	spinner.style.display = 'block';*/
	/*console.log("Spinner: " + spinner.style.display);
	console.log("principal: " + principal.style.display);*/
	setTimeout(function() {
		document.getElementById('spanCargando').style.display = 'none';
		document.getElementById('spanGuardar').style.display = 'block';
		/*document.getElementById('loader').style.display = 'none';
		document.getElementById('principalID').style.display = 'block';*/
	}, 3000);

	var banderaVoBo = false;
	//console.log("e: " + e);

	//var urlBase = getAbsolutePath();
	/*console.log("Si hay conexión con API");*/
	var idTr = document.querySelectorAll('.contarTr');
	//console.log("contarTr: " + idTr.length);
	var miArray = new Array(idTr.length);
	var conteo = 0;
	//console.log("Array: " + miArray.length);
	var estatusVoBo = 0;
	for (var i = 0; i < idTr.length; i++) {
		var tr = idTr[i].id
		//console.log("El id del tr es: " + tr);
		var aprobacion = $('input[name="' + tr + '"]:checked').val();
		//console.log("Aprobacion = " + aprobacion);
		if (aprobacion != null) {
			//console.log("Aprobación: " + i + " " + aprobacion);
			if (aprobacion == 1) {
				//console.log("la aprobación es valida (1)");
				miArray[i] = true;
			} else if (aprobacion == 0) {
				miArray[i] = false;
				//console.log("la aprobación es invalida (0)");
			}
			banderaVoBo = true;

		} else if (aprobacion === 'undefined' || aprobacion === undefined || aprobacion === null) {
			//console.log("Favor de llenar todos los campos");
			//miArray[i] = false;
			swal({
				icon: "info",
				title: "Favor de llenar todos los campos de aprobación",
				showConfirmButton: false

			});
			document.getElementById('btnVoBoDocumentos').disabled = false;
			/*document.getElementById('loader').style.display = 'none';
			document.getElementById('principalID').style.display = 'block';*/
			document.getElementById('spanCargando').style.display = 'none';
			document.getElementById('spanGuardar').style.display = 'block';
			loader.classList.add("hide-loader");

			/*spinner.style.display = 'none';
			principal.style.display = 'block';*/
		}

	}
	//console.log("miArray: " + miArray);
	var indefinidos = 0;
	for (i = 0; i < miArray.length; i++) {
		console.log("miArray" + i + " = " + miArray[i]);
		if (miArray[i] === true) {
			conteo = conteo + 1;
		} else if (miArray[i] === undefined || miArray[i] === 'undefined') {
			indefinidos = indefinidos + 1;
		}
	}
	//console.log("conteo: " + conteo);
	if (conteo == miArray.length) {
		//console.log("Todos los valores son true");
		var conexion = conexionWS_IAD();
		//console.log("Conexion: " + conexion);
		if (conexion == 1) {
			//console.log("Si hay conexión con API");
			estatusVoBo = 1;
			for (var i = 0; i < idTr.length; i++) {
				var tr = idTr[i].id
				var aprobacion = $('input[name="' + tr + '"]:checked').val();
				validar_doctEnvio(aprobacion, tr);
			}
			validarArchivosSolicitud(estatusVoBo);
		} else if (conexion == 0) {
			swal({
				icon: 'warning',
				title: "No se encontro conexion con WS."
			});
			document.getElementById('btnVoBoDocumentos').disabled = false;
			/*spinner.style.display = 'none';
			principal.style.display = 'block';*/
			document.getElementById('spanCargando').style.display = 'none';
			document.getElementById('spanGuardar').style.display = 'block';
			loader.classList.add("hide-loader");
		}

	} else if (indefinidos > 0) {
		//console.log("No ha seleccionado todos los campos de VoBo");
	} else {
		for (var i = 0; i < idTr.length; i++) {
			var tr = idTr[i].id
			var aprobacion = $('input[name="' + tr + '"]:checked').val();
			validar_doctEnvio(aprobacion, tr);
			if (aprobacion == 0) {
				//miArray[i] = false;
				//console.log("la aprobación es invalida (0)");
				var idComentario = "comentario" + tr;
				var comentario = document.getElementById(idComentario).value;
				//console.log("Comentario: " + comentario);
				$.ajax({
					async: false,
					url: newUrlBase + "hallazgoSolicitud/guardarHallazgo?idDoctoEnvio=" + tr + "&comentario=" + comentario,
					type: "POST",
					dataType: "JSON",
					beforeSend: function() {
						//document.getElementById('btnRelDoc').disabled = true;
						$.blockUI({ message: 'Espere un momento...' });
					},
					complete: function() {
						//document.getElementById('btnRelDoc').disabled = false;
						setTimeout($.unblockUI, 3000);
						//$.unblockUI();
					},
					success: function(respuesta) {
						console.log("La respuesta es correcta");
					},
					error: function(respuesta) {
						console.log("Error: " + JSON.stringify(respuesta));
						/*spinner.style.display = 'none';
						principal.style.display = 'block';*/
						//console.log("Error: " + respuesta.status);
					}
				});


			}
		}
		var solicitudCore = document.getElementById('i_no_solicitud_core').value;
		estatusVoBo = 2;
		mensaje = "Se revisaron los documentos de la solicitud de crédito: " + solicitudCore + " Favor de corregirlos y cargarlos en un nuevo proceso.";
		validarArchivosSolicitud(estatusVoBo);
		//envioMensaje(mensaje, indEnvio, solicitudEnvio);
	}

}

function validar_doctEnvio(aprobacion, tr) {
	$.ajax({
		async: false,
		url: newUrlBase + "doctEnvio/validar_doctEnvio?estatus=" + aprobacion + "&idDoctoEnvio=" + tr,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("validar_doctEnvio: ");
			console.log("Respuesta: " + JSON.stringify(respuesta));
		},
		error: function(respuesta) {
			//console.log("Respuesta: " + JSON.stringify(respuesta));
			document.getElementById('btnVoBoDocumentos').disabled = false;

			swal({
				icon: "info",
				title: "Error: " + JSON.stringify(respuesta),
				showConfirmButton: false

			});
			document.getElementById('spanCargando').style.display = 'none';
			document.getElementById('spanGuardar').style.display = 'block';
			loader.classList.add("hide-loader");
			/*spinner.style.display = 'none';
			principal.style.display = 'block';*/
		}
	});
}

//Funcion para validar los archivos de la solicitud
function validarArchivosSolicitud(estatusVoBo) {
	/*spinner.style.display = 'block';
	principal.style.display = 'none';*/
	console.log("Entro validarArchivosSolicitud");
	//var urlBase = getAbsolutePath();
	var idSolicitud = document.getElementById('solicitudEnvio').value;
	console.log("Solicitud envio: " + solicitudEnvio);
	//var estatus;
	$.ajax({
		async: false,
		url: newUrlBase + "solicitudEnvio/validacionArchivoSolicitud?solicitud=" + idSolicitud,
		type: "GET",
		dataType: "JSON",
		beforeSend: function() {
			//document.getElementById('btnRelDoc').disabled = true;
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			//document.getElementById('btnRelDoc').disabled = false;
			setTimeout($.unblockUI, 5000);
			//$.unblockUI();
		},
		success: function(respuesta) {
			if (estatusVoBo == 1) {
				//console.log("Todos los archivos son correctos");
				//console.log("Respuesta de solicitudEnvio/validacionArchivoSolicitud?: " + respuesta);
				//validarArchivoPDFyXML(idSolicitud, respuesta);
				obtenerParamWSAuten(respuesta, idSolicitud);
			} else if (estatusVoBo == 2) {
				//console.log("Existen archivos erroneos");
				//var idSolicitud = solicitudEnvio;
				//console.log("Respuesta de solicitudEnvio/validacionArchivoSolicitud?: " + respuesta);
				var estatus = respuesta;
				//console.log("Estatus: " + estatus);
				validarSoliEnvio(estatus, idSolicitud);
			}

			//validarSoliEnvio(respuesta, solicitudEnvio);
		},
		error: function(respuesta) {
			//console.log("Respuesta: " + JSON.stringify(respuesta));
			swal({
				icon: "info",
				title: "Error: " + JSON.stringify(respuesta),
				showConfirmButton: false

			});
			document.getElementById('btnVoBoDocumentos').disabled = false;
			/*spinner.style.display = 'none';
			principal.style.display = 'block';*/
			document.getElementById('spanCargando').style.display = 'none';
			document.getElementById('spanGuardar').style.display = 'block';
			loader.classList.add("hide-loader");

		}
	});

}

function validarArchivoPDFyXML(idSolicitud, respuesta) {
	var resValidacionArchivo = respuesta;
	var soliEnvio = document.getElementById('solicitudEnvio').value;
	console.log("soliEnvio:" + soliEnvio);
	var solicitudCredito = document.getElementById('i_no_solicitud_core').value;
	var clienteId = document.getElementById('clienteId').value;
	console.log("solicitudCredito:" + solicitudCredito);
	console.log("clienteId:" + clienteId);
	$.ajax({
		async: false,
		url: newUrlBase + "archivos/validarArchivoPDFyXML?soliEnvio=" + soliEnvio + "&solicitudCredito=" + solicitudCredito + "&clienteId=" + clienteId,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var archivos = respuesta;
			console.log("respuesta: " + JSON.stringify(archivos));
			var archivosRequeridos = JSON.stringify(archivos);
			const tiempoEspera = 60000;
			function validarArchivos() {
				const promesas = archivosRequeridos.map(archivo => {
					return fetch(archivo)
						.then(response => {
							if (!response.ok) {
								throw new Error('Archivo no encontrado');
							}
						})
						.catch(error => {
							throw error;
						});
				});

				return Promise.all(promesas)
					.then(() => true)
					.catch(() => false);
			}

			function esperarYValidarArchivos() {
				validarArchivos()
					.then(archivosValidos => {
						if (!archivosValidos) {
							console.log('Algunos archivos no existen. Esperando ' + tiempoEspera + ' milisegundos...');
							setTimeout(esperarYValidarArchivos, tiempoEspera);
						} else {
							console.log('Todos los archivos existen.');
							obtenerParamWSAuten(resValidacionArchivo, idSolicitud);
							
						}
					})
					.catch(error => {
						console.error('Error al validar archivos:', error);
					});
			}

			esperarYValidarArchivos();
		},
		error: function(respuesta) {

		}
	});
}



//Funcion para validar la solicitud de envio
function validarSoliEnvio(estatus, idSolicitud, idConfWS, jsonEnvio, respuestaAuth, banderaRespuesta, token, estatusVoBo) {
	/*spinner.style.display = 'none';
	principal.style.display = 'block';*/
	//console.log("validarSoliEnvio url: " + newUrlBase + "solicitudEnvio/validarSoliEnvio/" + estatus + "/" + idSolicitud + "/" + solicitudCore);
	//console.log("idSolicitud: " + idSolicitud);
	//console.log("estatus: " + estatus);
	var solicitudCore = document.getElementById('i_no_solicitud_core').value;
	var solicitudEnvio = document.getElementById('solicitudEnvio').value;
	var idProducto = document.getElementById('producto').value;
	//console.log("producto: " + idProducto);
	var indEnvio = 0;
	var mensaje = "";
	//var urlBase = getAbsolutePath();
	$.ajax({
		async: false,
		url: newUrlBase + "solicitudEnvio/validarSoliEnvio/" + estatus + "/" + idSolicitud + "/" + solicitudCore + "/" + idProducto,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta validarSoliEnvio: " + respuesta);
			// Todos los documentos fueron aprobados
			if (estatus === 1) {
				//obtenerParamWSAuten();
				//Ejecutamos la notificación.
				mensaje = "Se han validado los documentos de la solicitud de crédito: " + solicitudCore;
				resultadoLogin = true;
				envioMensaje(mensaje, indEnvio, idSolicitud);
				guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuestaAuth, banderaRespuesta);
				obtenerArchivo(solicitudEnvio, token);
				swal({
					icon: "success",
					title: "Revisión de documentos exitosa",
					showConfirmButton: false

				}).then((result) => {
					window.location.replace(newUrlBase + "solicitudEnvio/solicitudes");
				});

			} else {
				// Si la respuesta es diferente de 1 significa que existen documentos a corregir
				mensaje = "Se revisaron los documentos de la solicitud de crédito: " + idSolicitud + " Favor de corregirlos y cargarlos en un nuevo proceso.";
				envioMensaje(mensaje, indEnvio, idSolicitud);
				swal({
					icon: "success",
					title: "Validación correcta, favor de corregir y cargar en un nuevo proceso los documentos.",
					text: "solicitud: " + solicitudCore,
					showConfirmButton: false

				}).then((result) => {
					window.location.replace(newUrlBase + "solicitudEnvio/solicitudes");
				});
			}

		},
		error: function(respuesta) {
			//console.log("Respuesta error validarSoliEnvio: " + JSON.stringify(respuesta));
			//console.log("Error: " + respuesta.status);
			if (respuesta.status === 500) {
				//console.log("Salio error 500");
				swal({
					icon: "info",
					title: "Error: ",
					text: "Falló la solicitud: " + idSolicitud,
					showConfirmButton: false

				}).then((result) => {
					window.location.replace(newUrlBase + "solicitudEnvio/solicitudes");
				});
			} else if (respuesta.status === 400) {
				swal({
					icon: "info",
					title: "Error, falló la solicitud",
					text: "Error en parametros enviados ",
					showConfirmButton: false

				}).then((result) => {
					window.location.replace(newUrlBase + "solicitudEnvio/solicitudes");
				});
			} else {
				swal({
					icon: "info",
					title: "Error, falló la solicitud",
					text: idSolicitud,
					showConfirmButton: false

				}).then((result) => {
					window.location.replace(newUrlBase + "solicitudEnvio/solicitudes");
				});
			}

		}
	});
}

function envioMensaje(mensaje, indEnvio, sEnvio) {
	//var urlBase = getAbsolutePath();
	$.ajax({
		async: false,
		url: newUrlBase + "notificacion?mensaje=" + mensaje +
			"&indEnvio=" + indEnvio + "&sEnvio=" + sEnvio,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
		},
		error: function(respuesta) {

		}
	});
}

//Funcion para obtener las credenciales para Autenticarse con el proveedor
function obtenerParamWSAuten(validacion, solicitudEnvio) {
	//var urlBase = getAbsolutePath();
	$.ajax({
		async: false,
		url: newUrlBase + "catConfWs/obtenerParamWSAuten",
		type: "GET",
		dataType: "JSON",
		beforeSend: function() {
			//document.getElementById('btnRelDoc').disabled = true;
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			//document.getElementById('btnRelDoc').disabled = false;
			setTimeout($.unblockUI, 5000);
			//$.unblockUI();
		},
		success: function(respuesta) {
			var usuario = respuesta[0];
			var password = respuesta[1];
			var userType = respuesta[2];
			conexionApi(usuario, password, userType, validacion, solicitudEnvio);
		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
			document.getElementById('btnVoBoDocumentos').disabled = false;
			document.getElementById('spanCargando').style.display = 'none';
			document.getElementById('spanGuardar').style.display = 'block';
			loader.classList.add("hide-loader");
		}
	});
}

//Establecemos la base de la conexion del API (WS_IAD)
//var ws = "http://localhost:8091";

//Funcion para realizar la autenticacion del user con el proveedor
function conexionApi(usuario, password, userType, validacion, solicitudEnvio) {
	var ws = WS_IAD();
	//console.log("conexionApi");
	//var solicitudEnvio = document.getElementById('solicitudEnvio').value;
	var resultadoLogin = false;
	var idConfWS = 1;
	/*var jsonEnvio = new Object();
	jsonEnvio.grant_type = userType;
	jsonEnvio.username = usuario;
	jsonEnvio.password = password;*/
	var username = "username:" + usuario;
	var password2 = "password:" + password;
	var grandType = "grandType:" + userType;

	var jsonEnvio = [username, password2, grandType];
	var banderaRespuesta = 0;
	$.ajax({
		async: false,
		url: ws + "/autenticacion/autenticacion?usernameBody=" + usuario + "&passwordBody=" + password +
			"&grant_typeBody=" + userType,
		type: "GET",
		dataType: "JSON",
		beforeSend: function() {
			//document.getElementById('btnRelDoc').disabled = true;
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			//document.getElementById('btnRelDoc').disabled = false;
			setTimeout($.unblockUI, 5000);
			//$.unblockUI();
		},
		success: function(respuesta) {
			//console.log("Respuesta de conexionApi: " + JSON.stringify(respuesta));
			resultadoLogin = true;

			if (JSON.stringify(respuesta).includes("error")) {
				banderaRespuesta = 0;
				//console.log("Tiene error");
				guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
				swal({
					icon: "info",
					title: "Error de conexión: " + respuesta.error_description,
					showConfirmButton: false

				});
			} else {
				banderaRespuesta = 1;
				var token = respuesta.access_token;
				var respuestaAuth = respuesta;
				//console.log("TOKEN: " + token);
				//console.log("No tiene error");
				validarSoliEnvio(validacion, solicitudEnvio, idConfWS, jsonEnvio, respuestaAuth, banderaRespuesta, token);
				//guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
				//obtenerArchivo(solicitudEnvio, token);
			}

			//console.log("Respuesta accesstoken: " + accessToken);
			//console.log("resultadoLogin: " + resultadoLogin);

			//guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
			//obtenerArchivo(solicitudEnvio, token);
			/*console.log("json: " +json);
			console.log("Respuesta data: "+ json[8] );*/
		},
		error: function(respuesta) {
			resultadoLogin = false;
			banderaRespuesta = 0;
			//console.log("Error de conexionApi");
			//console.log("Respuesta: " + JSON.stringify(respuesta));
			guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
			swal({
				icon: "warning",
				title: "Error de conexion Api ",
				showConfirmButton: false

			});
			document.getElementById('btnVoBoDocumentos').disabled = false;
			document.getElementById('spanCargando').style.display = 'none';
			document.getElementById('spanGuardar').style.display = 'block';
			loader.classList.add("hide-loader");
			//loader.classList.add("hide-loader");
		}
	});
}

//Funcion para guardar la Autenticacion del User LOGIN
function guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta) {
	//var urlBase = getAbsolutePath();
	var datosConexionApi = JSON.stringify(respuesta);
	//var datosConexionApi = respuesta;
	//var datosConexionApi = JSON.parse(respuesta);
	var token = respuesta.access_token;
	var scope = respuesta.scope;
	var jti = respuesta.jti;
	var tpProceso = "ENVIO DE DOCUMENTOS";
	/*console.log("guardarWSAuteUsu");
	console.log("token: " + token);
	console.log("scope: " + scope);
	console.log("jti: " + jti);*/
	//console.log("datosConexionApi en guardarWSAuteUsu: "+ datosConexionApi);
	$.ajax({
		async: false,
		url: newUrlBase + "wsAutenticacionUser/guardarAutenticacion?resultadoLogin=" + resultadoLogin + "&solicitudEnvio="
			+ solicitudEnvio + "&idConfWS=" + idConfWS + "&jsonEnvio=" + jsonEnvio + "&tpProceso=" + tpProceso,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			//document.getElementById('btnRelDoc').disabled = true;
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			//document.getElementById('btnRelDoc').disabled = false;
			setTimeout($.unblockUI, 5000);
			//$.unblockUI();
		},
		success: function(respuesta) {
			//console.log("Respuesta de guardarWSAuteUsu: " + JSON.stringify(respuesta));
			var idWSAutenticacion = respuesta.pk_id_ws_autenticacion;
			//console.log("idWSAutenticacion: " + idWSAutenticacion);
			if (banderaRespuesta == 0) {
				//console.log("Es falso");
				guardarRespAutenticacion(datosConexionApi, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta);
			} else if (banderaRespuesta == 1) {
				//console.log("Es true");
				guardarRespAutenticacion(datosConexionApi, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta);
				//obtenerArchivo(solicitudEnvio, token);
			}
			//guardarRespAutenticacion(datosConexionApi, token, scope, jti, idWSAutenticacion, solicitudEnvio);
			//obtenerArchivo(solicitudEnvio, token);

		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error guardarWSAuteUsu: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}

//Funcion para guardar la Respuesta de la autenticacion LOGIN
function guardarRespAutenticacion(respuesta, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta) {
	/*console.log("idWSAutenticacion: " +idWSAutenticacion);
	console.log("solicitudEnvio: " + solicitudEnvio);*/
	//console.log("guardarRespAutenticacion");

	//var urlBase = getAbsolutePath();
	var jsonRespuesta = respuesta.trim();
	//var json = jsonRespuesta.replace(/["']/g, "");
	var jsonFinal = jsonRespuesta.replace(/[{{}}]/g, "");
	var tpProceso = "ENVIO DE DOCUMENTOS";
	//console.log("jsonRespuesta" + jsonFinal);
	//console.log("Bandera: " + banderaRespuesta);
	if (banderaRespuesta == 0) {
		//console.log("Solo registra jsonFinal, idWSAutenticacion");
		$.ajax({
			async: false,
			url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
				"&idWSAutenticacion=" + idWSAutenticacion,
			type: "POST",
			dataType: "JSON",
			beforeSend: function() {
				//document.getElementById('btnRelDoc').disabled = true;
				$.blockUI({ message: 'Espere un momento...' });
			},
			complete: function() {
				//document.getElementById('btnRelDoc').disabled = false;
				setTimeout($.unblockUI, 5000);
				//$.unblockUI();
			},
			success: function(respuesta) {
				//console.log("Respuesta de guardarRespAutenticacion: " + JSON.stringify(respuesta));
				var autenUser = idWSAutenticacion;
				var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
				guardarBitacoraWS(autenUser, resAuten, tpProceso, solicitudEnvio);
			},
			error: function(respuesta) {
				swal({
					icon: "info",
					title: "Error guardarRespAutenticacion: " + JSON.stringify(respuesta),
					showConfirmButton: false
				});
			}
		});
	} else if (banderaRespuesta == 1) {
		$.ajax({
			async: false,
			url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
				"&jti=" + jti + "&scope=" + scope + "&token=" + token + "&idWSAutenticacion=" + idWSAutenticacion,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				//console.log("Respuesta de guardarRespAutenticacion: " + JSON.stringify(respuesta));
				var autenUser = idWSAutenticacion;
				var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
				guardarBitacoraWS(autenUser, resAuten, tpProceso, solicitudEnvio);
			},
			error: function(respuesta) {
				swal({
					icon: "info",
					title: "Error guardarRespAutenticacion: " + JSON.stringify(respuesta),
					showConfirmButton: false
				});
			}
		});
	}


}
/*function envioDoctFirma(sEnvio,accessToken){
	console.log("Ejecutando envioDoctFirma");
	$.ajax({
		async: false,
		url: ws + "/envio/DocumentoFirma?sEnvio="+sEnvio+"&accessToken="+accessToken,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta){
			//console.log("Respuesta envioDoctFirma: " + JSON.stringify(respuesta));
			obtenerArchivo(soliEnvio, token);
		},
		erro: function(respuesta){
			swal({
				icon: "info",
				title: "Error envioDoctFirma: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}*/

//Funcion para guardar en Bitacora Consumo
function guardarBitacoraWS(autenUser, resAuten, tpProceso, soliEnvio) {
	//console.log("Ejecutando guardarBitacoraWS");
	//var urlBase = getAbsolutePath();
	$.ajax({
		async: false,
		url: newUrlBase + "bitacoraConsumo?autenUser=" + autenUser + "&resAuten=" + resAuten +
			"&tpProceso=" + tpProceso + "&soliEnvio=" + soliEnvio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			//document.getElementById('btnRelDoc').disabled = true;
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			//document.getElementById('btnRelDoc').disabled = false;
			setTimeout($.unblockUI, 5000);
			//$.unblockUI();
		},
		success: function(respuesta) {
			//console.log("Respuesta guardarBitacoraWS: " + JSON.stringify(respuesta));
		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error guardarBitacoraWS: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}

//Funcion para recuperar los documentos xml, pdf y generacion de hash del pdf
function obtenerArchivo(soliEnvio, token) {
	//var urlBase = getAbsolutePath();
	//console.log("Ejecutando obtenerArchivo");
	$.ajax({
		async: false,
		url: newUrlBase + "archivos/obtenerArchivos?soliEnvio=" + soliEnvio,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("Respuesta obtenerArchivo");
			console.log("Respuesta obtenerArchivo: " + JSON.stringify(respuesta))
			var xml = respuesta[0];
			var pdf = respuesta[1];
			var hash = respuesta[2];
			envioDocFirmaAPI(xml, pdf, hash, token, soliEnvio);
		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error obtenerArchivo: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}


function envioDocFirmaAPI(xml, pdf, hash, token, soliEnvio) {

	//console.log("Ejecutando envioDocFirmaAPI");
	var ws = WS_IAD();
	var idConfWS = 2;
	var archivoXML = "xml:" + xml;
	var archivoPDF = "pdf:" + pdf;
	var hashPDF = "hash:" + hash;
	var accessToken = "token:" + token;
	var jsonEnvio = [archivoXML, archivoPDF, hashPDF, accessToken];
	var resultadoLogin = false;
	/*console.log("envioDocFirmaAPI xml: " + xml);
	console.log("envioDocFirmaAPI pdf: " + pdf);
	console.log("envioDocFirmaAPI hash: " + hash);
	console.log("envioDocFirmaAPI token: " + token);
	console.log("envioDocFirmaAPI: " + ws);*/
	$.ajax({
		async: false,
		url: ws + "/envio/DocumentoFirma?xml=" + xml + "&pdf=" + pdf + "&hash=" + hash + "&accessToken=" + token,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			//var resultadoLogin = true;
			console.log("Respuesta success envioDocFirmaAPI: " + JSON.stringify(respuesta));
			if (JSON.stringify(respuesta).includes("error")) {
				resultadoLogin = false;
				//console.log("Tiene error envioDocFirmaAPI");
				guardarWSAuteUsuEnvDoctFirma(resultadoLogin, soliEnvio, idConfWS, jsonEnvio, respuesta);
				swal({
					icon: "info",
					title: "Error Conexión: " + respuesta.error,
					showConfirmButton: false

				});
			} else {
				//console.log("Resultado true envioDocFirmaAPI");
				resultadoLogin = true;
				//console.log("Respuesta true envioDocFirmaAPI: " + JSON.stringify(respuesta));
				guardarWSAuteUsuEnvDoctFirma(resultadoLogin, soliEnvio, idConfWS, jsonEnvio, respuesta);
			}

		},
		error: function(respuesta) {
			//console.log("Entro en error envioDocFirmaAPI");
			resultadoLogin = false;
			//console.log("Respuesta error envioDocFirmaAPI: " + JSON.stringify(respuesta));
			swal({
				icon: "info",
				title: "Error al enviar los documentos a firmar en el API: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
			guardarWSAuteUsuEnvDoctFirma(resultadoLogin, soliEnvio, idConfWS, jsonEnvio, respuesta);
		}
	});
}

function agregarRequisitionId(requisitionId, soliEnvio) {
	//console.log("Ejecutando agregarRequisitionId");
	//console.log("requisitionId: " + requisitionId);
	//var urlBase = getAbsolutePath();
	$.ajax({
		async: false,
		url: newUrlBase + "solicitudEnvio/agregarRequisitionId?requisitionId=" + requisitionId + "&soliEnvio=" + soliEnvio,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta agregarRequisitionId: " + JSON.stringify(respuesta));
		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error agregarRequisitionId: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}

//Funcion para guardar la autenticacion user de envio de documentos a firmar
function guardarWSAuteUsuEnvDoctFirma(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, resJson) {

	//console.log("Ejecutando guardarWSAuteUsuEnvDoctFirma: " + JSON.stringify(resJson));
	var tpProceso = "ENVIO DE DOCUMENTOS";
	var respuestaEnvioSoli = JSON.stringify(resJson);
	console.log("respuestaEnvioSoli: " + respuestaEnvioSoli);
	if (respuestaEnvioSoli.includes("requisitionId")) {
		//console.log("Si tiene requisition ID");
		var requisitionId = resJson.data.requisitionId;
	} else {
		//console.log("No tiene requisition id")
		var requisitionId = null;
	}

	//var urlBase = getAbsolutePath();
	$.ajax({
		async: false,
		url: newUrlBase + "wsAutenticacionUser/guardarAutenticacion?resultadoLogin=" + resultadoLogin + "&solicitudEnvio="
			+ solicitudEnvio + "&idConfWS=" + idConfWS + "&jsonEnvio=" + jsonEnvio + "&tpProceso=" + tpProceso,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta agregarRequisitionId: " + JSON.stringify(respuesta));
			var idWSAutenticacion = respuesta.pk_id_ws_autenticacion;
			if (requisitionId != null) {
				guardarRespAutenticacionEnvDoctFirma(respuestaEnvioSoli, idWSAutenticacion, solicitudEnvio, requisitionId);
				agregarRequisitionId(requisitionId, solicitudEnvio);
			} else {
				guardarRespAutenticacionEnvDoctFirma(respuestaEnvioSoli, idWSAutenticacion, solicitudEnvio, requisitionId);
			}

		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error agregarRequisitionId: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}

//Funcion para guardar la Respuesta de la autenticacion envio de documentos a firmar
function guardarRespAutenticacionEnvDoctFirma(respuestaEnvioSoli, idWSAutenticacion, solicitudEnvio, requisitionId) {
	/*console.log("Ejecutando guardarRespAutenticacionEnvDoctFirma");
	console.log("RESPUESTA guardarRespAutenticacionEnvDoctFirma: " + respuestaEnvioSoli);
	console.log("idWSAutenticacion: " + idWSAutenticacion);
	console.log("solicitudEnvio: " + solicitudEnvio);*/
	//var urlBase = getAbsolutePath();
	var jsonRespuesta = respuestaEnvioSoli.trim();
	//var requisitionId = respuestaEnvioSoli.data;
	//var json = jsonRespuesta.replace(/["']/g, "");
	var jsonFinal = jsonRespuesta.replace(/[{{}}]/g, "");
	//console.log("success: " +respuestaEnvioSoli[0]);
	//console.log("requisitionId: " + requisitionId);
	var tpProceso = "ENVIO DE DOCUMENTOS";
	//console.log("jsonRespuesta:" + jsonFinal);
	$.ajax({
		async: false,
		url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
			"&idWSAutenticacion=" + idWSAutenticacion + "&requisitionId=" + requisitionId,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta de guardarRespAutenticacion: " + JSON.stringify(respuesta));
			var autenUser = idWSAutenticacion;
			var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
			agregarRequisitionId(requisitionId, solicitudEnvio);
			guardarBitacoraWS(autenUser, resAuten, tpProceso, solicitudEnvio);

		},
		error: function(respuesta) {
			swal({
				icon: "info",
				title: "Error guardarRespAutenticacion: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}



