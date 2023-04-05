// Variables | para el spinner
var spinner = document.getElementById('spinnerID');
var principal = document.getElementById('principalID');

// Variables | valores para la autorización
var guardarObservaciones;
var guardarTipo;
var guardarRequisition;


// Variable | id solicitud de crédito
var creditoID;

// Variable | id solicitud de envio
var seId;

// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/autorizar", "");

// Variables | vista de datos
var divDatoSolicitud = document.getElementById("datoSolicitud");
var solicitudId = document.getElementById("solicitudCredito");
var divParticipantes = document.getElementById("divParticipantes");
var mostrarSolicitud = document.getElementById("mostrarSolicitudId");
var mostrarBotonAutorizar = document.getElementById("mostrarBotonAutorizarId");
var mostrarBotonRechazar = document.getElementById("mostrarBotonRechazarId");
var disponible = document.getElementById("disponibleID");
var listaID = document.getElementById("listaID");

// Funcion para la busqueda de solicitud de credito
function buscarSolicitudCredito(solicitudCredito) {
	// console.log(newUrlBase);
	// console.log("solicitudCredito: " + solicitudCredito);
	$("#datoSolicitud").val('');
	$("#participantes").val('');
	if (solicitudCredito != '') {
		//console.log("Si se ha seleccionado una opción en usuario");
		$.ajax({
			url: newUrlBase + "buscar_solicitud?solicitud=" + solicitudCredito,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				//console.log(respuesta);
				// console.log("buscar_solicitud");
				if (respuesta == null || respuesta == '') {
					//console.log("Error");
					swal({
						title: "Error",
						text: "La solicitud que ingresaste no existe",
						icon: "warning",
					});
				} else {
					//console.log("Puede continuar");
					//Hacemos visible los campos del detalle de la solicitud
					divDatoSolicitud.style.display = "block";
					//Hacemos referencia a los input para llenar los campos:
					var solicitud = document.getElementById('solicitud');
					var buscarCredito = document.getElementById('buscarCredito');
					var producto = document.getElementById('producto');
					var montoSolicitado = document.getElementById('montoSolicitado');
					var montoAutorizado = document.getElementById('montoAutorizado');
					var plazo = document.getElementById('plazo');
					var tasaNormal = document.getElementById('tasaNormal');
					var estatus = document.getElementById('estatus');
					var Nombreproducto = document.getElementById('Nombreproducto');
					$("#divIdProducto").hide();
					//Asignamos los valores a los campos
					creditoID = solicitudCredito;
					buscarCredito.value = '';
					solicitud.value = respuesta[0].solicitudCreditoID;
					producto.value = respuesta[0].productoCreditoID;
					montoSolicitado.value = regresarMoneda(montoSolicitado, respuesta[0].montoSolicitado);
					montoAutorizado.value = regresarMoneda(montoAutorizado, respuesta[0].montoAutorizado);
					plazo.value = respuesta[0].plazo;
					tasaNormal.value = regresarMoneda(tasaNormal, respuesta[0].tasaFija);
					estatus.value = respuesta[0].estatus;
					Nombreproducto.value = respuesta[0].producto;
					buscarProducto(respuesta[0].productoCreditoID);
				}
			}, error: function() {
				// Vistas
				buscarCredito.value = '';
				divParticipantes.style.display = "none";
				disponible.style.display = "none";
				datoSolicitud.style.display = "none";
				mostrarSolicitud.style.display = "none";
				mostrarBotonAutorizar.style.display = "none";
				mostrarBotonRechazar.style.display = "none";
				listaID.style.display = "none";

				swal({
					title: "Error, no es una solicitud de crédito",
					text: "Favor de ingresar una solicitud de crédito valida",
					icon: "warning",
				});
			}
		});
	} else {
		swal({
			title: "No contiene una solicitud de crédito",
			text: "Favor de ingresar un número de solicitud de crédito.",
			icon: "info",
		});
	}
}



//Funcion para buscar si el producto ya se encuentra configurado
function buscarProducto(producto) {
	$.ajax({
		url: newUrlBase + "buscar_catProducto?producto=" + producto,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log(respuesta);
			console.log(respuesta.length);*/
			//console.log("Respuesta buscarProducto: " + respuesta);
			if (respuesta == 1) {
				// Consultar la solicitud de envio
				mostrarSolicitudEnvio(creditoID);
				//console.log("Producto ya configurado");
				//Asignamos el valor de la solicitud para poder traer los participantes
				var solicitud = document.getElementById('solicitud').value;
				//console.log("Traer solicitud: " + solicitud);
				//Llamamos la funcion para obtener los participantes
				traerParticipantes(solicitud)
				//buscarDocumentos(producto)
				//Agregamos la petición para obtener los participantes
			} else if (respuesta == 0) {
				// Vistas
				divParticipantes.style.display = "none";
				disponible.style.display = "none";
				datoSolicitud.style.display = "none";
				mostrarSolicitud.style.display = "none";
				mostrarBotonAutorizar.style.display = "none";
				mostrarBotonRechazar.style.display = "none";
				listaID.style.display = "none";

				document.getElementById('solicitud').value = "";
				document.getElementById('producto').value = "";
				document.getElementById('montoSolicitado').value = "";
				document.getElementById('montoAutorizado').value = "";
				document.getElementById('plazo').value = "";
				document.getElementById('tasaNormal').value = "";
				document.getElementById('estatus').value = "";
				document.getElementById("datoSolicitud").style.display = "none";
				document.getElementById("Nombreproducto").value = "";
				$('#participantes > tbody').empty();
				swal({
					title: "El producto: " + producto + " no está configurado",
					text: "Favor de configurar el producto",
					icon: "warning",
				});
			}
		}
	});
}



//Funcion para obtener los participantes de la solicitud de credito
function traerParticipantes(solicitud) {
	//console.log("Entro en traer participantes");
	//console.log("Solicitud: " + solicitud);
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



// Función que muestra la solicitud de envio
function mostrarSolicitudEnvio(solicitudId) {
	$.ajax({
		url: newUrlBase + "datos_solicitud?solicitudId=" + solicitudId + "&accion=autorizar",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log(JSON.stringify(respuesta));
			if (respuesta.pk_id_solicitud_envio == 0) {
				disponible.style.display = "block"
				// Mostrar datos de envio
				mostrarSolicitud.style.display = "none";
				// Botón autorizar
				mostrarBotonAutorizar.style.display = "none";
				// Botón rechazar
				mostrarBotonRechazar.style.display = "none";
			} else {
				disponible.style.display = "none"
				// Mostrar datos de envio
				// Mostrar datos de envio
				mostrarSolicitud.style.display = "block";
				// Botón autorizar
				mostrarBotonAutorizar.style.display = "block";
				// Botón rechazar
				mostrarBotonRechazar.style.display = "block";
			}

			//envioID = respuesta.pk_id_solicitud_envio;

			//Hacemos referencia a los input para llenar los campos:
			var envio = document.getElementById("envioID");
			var fechaRegistro = document.getElementById('fechaRegistro');
			var fechaCancelacion = document.getElementById('fechaCancelacion');
			var horaCancelacion = document.getElementById('horaCancelacion');

			// Cambiar el input a texto si no viene nula la fecha de registro
			if (respuesta.dt_fecha_registro == null) {
				fechaRegistro.type = 'text';
			}

			// Cambiar el input a texto si no viene nula la fecha de cancelación
			if (respuesta.dt_fecha_cancelacion == null) {
				fechaCancelacion.type = 'text';
				horaCancelacion.type = 'text';
			}

			// Asignamos los valores a los campos
			seId = respuesta.pk_id_solicitud_envio;
			envio.value = respuesta.pk_id_solicitud_envio;
			fechaRegistro.value = respuesta.dt_fecha_registro != null ? respuesta.dt_fecha_registro : 'Sin registro';
			fechaCancelacion.value = respuesta.dt_fecha_cancelacion != null ? respuesta.dt_fecha_cancelacion : 'Sin registro';
			horaCancelacion.value = respuesta.vc_hora_cancelacion != null ? respuesta.vc_hora_cancelacion : 'Sin registro';

			// Guardamos el campo requisitionID para enviarlo al ws
			guardarRequisition = respuesta.vc_requisition_id != null ? respuesta.vc_requisition_id : null;

			// Mostrar solicitudes canceladas
			listaSolicitudes(solicitudId);

		}, error: function(error) {
			// console.log(error);
		}
	});
}



// Función que muestra la alerta para poder actualizar la solicitud
function alertaActualizarSolicitud(tipo) {
	//console.log(envioID);
	swal({
		title: '¿Deseas ' + tipo + ' la solicitud?',
		text: "Se actualizará la solicitud actual",
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				observacionesSolicitud(tipo);
			} else { }
		});
}



// Función para agregar las observaciones de la actualización de la solicitud
function observacionesSolicitud(tipo) {
	swal("Observaciones al " + tipo, {
		content: "input",
		buttons: ["Salir", "Aceptar"],
		closeOnClickOutside: false,
	})
		.then((observaciones) => {
			if (observaciones && observaciones.length <= 200) {

				// Llamamos a nuestro spinner
				spinner.style.display = 'block';
				principal.style.display = 'none';

				guardarObservaciones = observaciones;
				guardarTipo = tipo;

				if (tipo == "rechazar") {
					actualizarSolicitud(guardarObservaciones, guardarTipo);
				} else if (tipo == "autorizar") {
					// 1er paso, llamar la autenticacion del provedor (NA-AT)
					autenticacion();
				}

			} else if (observaciones == "") {
				// Mandamos alerta de que no hay texto
				swal("Debes agregar observaciones para poder " + tipo, {
					icon: "info",
					closeOnClickOutside: false,
				});

			} else if (observaciones.length > 200) {
				// Mandamos una alerta
				swal("No debes de exceder más de 200 carácteres!", {
					icon: "info",
					closeOnClickOutside: false,
				});
			} else { }
		});
}



// Función que autoriza la solicitud
function actualizarSolicitud(observacion, tipo) {
	$.ajax({
		url: newUrlBase + "autorizar_la_solicitud?tipoEstatus=" + tipo + "&envioID=" + seId + "&observaciones=" + observacion,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			console.log(respuesta);

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal("Solicitud actualizada", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					window.location.reload();
				});
		}, error: function() {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';
		}
	});
}

// Función que muestra las solicitudes autorizadas
function listaSolicitudes(solicitudID) {
	$.ajax({
		url: newUrlBase + "solicitudes_autorizadas?solicitudID=" + solicitudID + "&accion=autorizar",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			//console.log(respuesta);

			// validar si hay solicitudes en la respuesta
			if (respuesta.length == 0) {
				listaID.style.display = "none";
			} else {
				listaID.style.display = "block";
			}

			$('#listaSolicitudesID > tbody').empty();

			$.each(respuesta, function(idx, opt) {

				// Validar fecha, observaciones y estatus
				var fecha;
				var estatus;
				var observaciones;

				if (opt.i_estatus == 7) {

					fecha = opt.dt_fecha_cancelacion;
					estatus = "Rechazada";
					observaciones = opt.vc_obser_cancelacion;

				} else if (opt.i_estatus == 6) {

					fecha = opt.dt_fecha_autorizacion;
					estatus = "Autorizada";
					observaciones = opt.vc_obser_autoriza;
				}

				var rows = "<tr >"
					+ "<td>" + opt.pk_id_solicitud_envio + "</td>"
					+ "<td>" + fecha + "</td>"
					+ "<td>" + observaciones + "</td>"
					+ "<td>" + estatus + "</td>"
					+ "</tr>";
				$('#listaSolicitudesID').append(rows)

			});

		}, error: function(error) {
			// console.log(error);
		}
	})
}



/**
--------- Funciones que se pueden reutilizar en otra clase ---------
 */


// Función que regresar el valor númerico a formato de moneda nacional
function regresarMoneda(nombre, valor) {
	//console.log(valor);
	return document.getElementById(nombre.id).innerHTML = new Intl.NumberFormat('es-MX').format(valor);
}



/**
--------- Funciones de WS ---------
 */

//Funcion para obtener las credenciales para Autenticarse con el proveedor (NA-AT)
function autenticacion() {
	$.ajax({
		async: false,
		url: newUrlBase + "catConfWs/obtenerParamWSAuten",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var usuario = respuesta[0];
			var password = respuesta[1];
			var userType = respuesta[2];
			// 2do paso, hacer conexión con el api de NA-AT
			conexionApi(usuario, password, userType);
		},
		error: function(respuesta) {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal({
				icon: "info",
				title: "Hubo un error con la autenticación",
				showConfirmButton: false
			});
		}
	});
}

function WS_IAD() {
	var ws1 = "";
	$.ajax({
		url: newUrlBase + "conexionWS_IAD",
		async: false,
		type: "GET",
		dataType: "text",
		success: function(respuesta) {
			ws1 = respuesta;
			return ws1;
		},
		error: function(respuesta) {
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



//Funcion para realizar la autenticacion con el proveedor (NA-AT)
function conexionApi(usuario, password, userType) {
	// obtenemos la direccion de ws
	var webService = WS_IAD();
	var solicitudEnvio = seId;
	var resultadoLogin = false;
	var idConfWS = 1;
	var username = "username:" + usuario;
	var password2 = "password:" + password;
	var grandType = "grandType:" + userType;

	var jsonEnvio = [username, password2, grandType];
	var banderaRespuesta = 0;
	$.ajax({
		async: false,
		url: webService + "/autenticacion/autenticacion?usernameBody=" + usuario + "&passwordBody=" + password +
			"&grant_typeBody=" + userType,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			resultadoLogin = true;

			if (JSON.stringify(respuesta).includes("error")) {

				banderaRespuesta = 0;
				// 3er paso, guardar el proceso de autenticacion a nivel sistema
				guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);

				swal({
					icon: "info",
					title: "Hubo un error con la conexión en la autenticación",
					showConfirmButton: false
				});

			} else {

				banderaRespuesta = 1;
				var token = respuesta.access_token;
				// 3er paso, guardar el proceso de autenticacion a nivel sistema
				guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
				// 4to paso, recuperar la url de descarga
				recuperarUrl(token);
			}
		},
		error: function(respuesta) {

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			resultadoLogin = false;
			banderaRespuesta = 0;
			// 3er paso, guardar el proceso de autenticacion a nivel sistema
			guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
			swal({
				icon: "info",
				title: "Hubo un error con la conexión de la autenticación",
				showConfirmButton: false
			});
		}
	});
}



//Funcion para guardar la Autenticacion
function guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta) {
	var datosConexionApi = JSON.stringify(respuesta);
	var token = respuesta.access_token;
	var scope = respuesta.scope;
	var jti = respuesta.jti;
	var tpProceso = "DESCARGA"
	$.ajax({
		async: false,
		url: newUrlBase + "wsAutenticacionUser/guardarAutenticacion?resultadoLogin=" + resultadoLogin + "&solicitudEnvio="
			+ solicitudEnvio + "&idConfWS=" + idConfWS + "&jsonEnvio=" + jsonEnvio + "&tpProceso=" + tpProceso,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			var idWSAutenticacion = respuesta.pk_id_ws_autenticacion;
			if (banderaRespuesta == 0) {
				// 5to paso, guardar la respuesta del proceso de autenticacion a nivel sistema
				guardarRespAutenticacion(datosConexionApi, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta);
			} else if (banderaRespuesta == 1) {
				// 5to paso, guardar la respuesta del proceso de autenticacion a nivel sistema
				guardarRespAutenticacion(datosConexionApi, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta);
			}
		},
		error: function(respuesta) {

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal({
				icon: "info",
				title: "Hubo un error al guardar la autenticación",
				showConfirmButton: false
			});
		}
	});
}



//Funcion para guardar la respuesta de la autenticacion
function guardarRespAutenticacion(respuesta, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta) {
	var jsonRespuesta = respuesta.trim();
	var jsonFinal = jsonRespuesta.replace(/[{{}}]/g, "");
	var tpProceso = "DESCARGA";
	if (banderaRespuesta == 0) {
		$.ajax({
			async: false,
			url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
				"&idWSAutenticacion=" + idWSAutenticacion,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				var autenUser = idWSAutenticacion;
				var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
				// 6to paso, guardar la bitacora a nivel sistema
				guardarBitacoraWS(autenUser, resAuten, tpProceso, solicitudEnvio);
			},
			error: function(respuesta) {
				swal({
					icon: "info",
					title: "Hubo un error al guardar la respuesta de la autenticación",
					showConfirmButton: false
				});
			}
		});
	} else if (banderaRespuesta == 1) {
		$.ajax({
			async: false,
			url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal + "&jti=" + jti +
				"&scope=" + scope + "&token=" + token + "&idWSAutenticacion=" + idWSAutenticacion,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				var autenUser = idWSAutenticacion;
				var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
				// 5to paso, guardar la bitacora a nivel sistema
				guardarBitacoraWS(autenUser, resAuten, tpProceso, solicitudEnvio);
			},
			error: function(respuesta) {
				swal({
					icon: "info",
					title: "Hubo un error al guardar la respuesta de la autenticación",
					showConfirmButton: false
				});
			}
		});
	}
}



//Funcion para guardar la Bitacora
function guardarBitacoraWS(autenUser, resAuten, tpProceso, soliEnvio) {
	$.ajax({
		async: false,
		url: newUrlBase + "bitacoraConsumo?autenUser=" + autenUser + "&resAuten=" + resAuten +
			"&tpProceso=" + tpProceso + "&soliEnvio=" + soliEnvio,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			// Se termina el proceso.
		},
		error: function(respuesta) {

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal({
				icon: "info",
				title: "Hubo un error al guardar la bitacora",
				showConfirmButton: false
			});
		}
	});
}


function recuperarUrl(token) {
	var webService = WS_IAD();
	$.ajax({
		url: webService + "/ws_url_descarga",
		type: "GET",
		success: function(respuesta) {
			console.log(respuesta);
			// 4to paso, mandar a llamar el proceso de descarga con el proveedor (NA-AT)
			wsDescargaZip(token, respuesta);
		},
		error: function(error) {

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal({
				icon: "info",
				title: "Hubo un error al recupear el api",
				showConfirmButton: false
			});

		}
	});
}



// Función principal de la descarga del zip en WS
function wsDescargaZip(token, credenciales) {

	var resultadoLogin = false;
	var idConfWS = 5;
	var requisision = guardarRequisition;
	var jsonEnvio = [requisision];
	var header = "Bearer " + token;

	const dto = {
		idValidation: requisision
	}

	var parametros = JSON.stringify(dto);


	// Mandamos a llamar el servicio de ws
	axios({
		url: credenciales, // URL del servidor para descargar el archivo
		method: 'POST',
		responseType: 'blob', // Tipo de respuesta esperado
		data: parametros,
		headers: {
			'Authorization': header,
			'Content-Type': 'application/json'
		},
	}).then(response => {
		const url = window.URL.createObjectURL(new Blob([response.data])); // Crea una URL de objeto a partir de la respuesta
		const link = document.createElement('a');
		link.href = url;
		link.setAttribute('download', 'videograbacion.zip'); // Establece el nombre de archivo de descarga
		document.body.appendChild(link);
		link.click(); // Hace clic en el enlace
		document.body.removeChild(link); // Elimina el enlace del DOM
		window.URL.revokeObjectURL(url); // Libera recursos de memoria

		if (response.status == 200) {

			const respuesta = {
				success: true,
				error: null,
				code: null,
			}

			// Si la respuesta es true
			resultadoLogin = true;
			// 7mo paso, guardamos la autenticacion de la descarga
			guardarWSAuteDescarga(resultadoLogin, idConfWS, jsonEnvio, respuesta);
			// 8vo paso, mandamos a llamar al metodo de autorizar a nivel sistema
			actualizarSolicitud(guardarObservaciones, guardarTipo);

		} else {

			const respuesta = {
				success: false,
				error: "No se pudo realizar la descarga del archivo",
				code: null,
			}

			// Si la respuesta es true
			resultadoLogin = true;
			// 7mo paso, guardamos la autenticacion de la descarga
			guardarWSAuteDescarga(resultadoLogin, idConfWS, jsonEnvio, respuesta);
			// 8vo paso, mandamos a llamar al metodo de autorizar a nivel sistema
			actualizarSolicitud(guardarObservaciones, guardarTipo);
		}
	});

}


//Funcion para guardar la autenticacion de la descarga del zip
function guardarWSAuteDescarga(resultadoLogin, idConfWS, jsonEnvio, resJson) {
	var tpProceso = "DESCARGA";
	var respuestaEnvioSoli = JSON.stringify(resJson);
	$.ajax({
		async: false,
		url: newUrlBase + "wsAutenticacionUser/guardarAutenticacion?resultadoLogin=" + resultadoLogin + "&solicitudEnvio="
			+ seId + "&idConfWS=" + idConfWS + "&jsonEnvio=" + jsonEnvio + "&tpProceso=" + tpProceso,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			var idWSAutenticacion = respuesta.pk_id_ws_autenticacion;
			// 9no paso, guardar la respuesta de autenticacaion del proceso de descarga a nivel sistema
			guardarRespAutenDescarga(respuestaEnvioSoli, idWSAutenticacion);
		},
		error: function(error) {

			// console.log(error);

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal({
				icon: "info",
				title: "Hubo un error al guardar la autenticación",
				showConfirmButton: false
			});
		}
	});
}



//Funcion para guardar la respuesta de la autenticacion de la descarga del zip
function guardarRespAutenDescarga(respuestaEnvioSoli, idWSAutenticacion) {
	var jsonRespuesta = respuestaEnvioSoli.trim();
	var jsonFinal = jsonRespuesta.replace(/[{{}}]/g, "");
	var tpProceso = "DESCARGA";
	$.ajax({
		async: false,
		url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
			"&idWSAutenticacion=" + idWSAutenticacion + "&requisitionId=" + guardarRequisition,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			var autenUser = idWSAutenticacion;
			var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
			// 10mo paso, guardamos la bitacora del proceso de descarga a nivel sistema
			guardarBitacoraWS(autenUser, resAuten, tpProceso, seId);
		},
		error: function(error) {

			console.log(error);

			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';

			swal({
				icon: "info",
				title: "Hubo un error al guardar la respuesta de la autenticación",
				showConfirmButton: false
			});
		}
	});
}