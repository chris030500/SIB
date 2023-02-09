// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/cancelacion", "");

// Variable ID solicitud de envio
var envioID;

// Variable | requisitionID
var guardarRequisition;

// Variable | observaciones
var observacionesCancelacion;

// Variables | para el spinner
var spinner = document.getElementById('spinnerID');
var principal = document.getElementById('principalID');

// Funcion para la busqueda de solicitud de credito
function buscarSolicitudCredito(solicitudCredito) {
	//console.log("solicitudCredito: " + solicitudCredito);
	var divDatoSolicitud = document.getElementById("datoSolicitud");
	var solicitudId = document.getElementById("solicitudCredito");
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
				if (respuesta == null || respuesta == '') {
					//console.log("Error");
					swal({
						title: "Error",
						text: "La solicitud que ingresaste no existe",
						icon: "warning",
					});
				} else {
					//console.log("Puede continuar");
					if (respuesta[0].resultado == 1) {
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
						// Consultar la solicitud de envio
						mostrarSolicitudEnvio(solicitud.value);
					} else if (respuesta[0].resultado == 0) {
						swal({
							title: "Error",
							text: respuesta[0].mensajeError,
							icon: "warning",
						});
					}
				}
			}, error: function() {
				swal({
					title: "Error, no es una solicitud de crédito",
					text: "Favor de ingresar una solicitud de crédito",
					icon: "warning",
				});
			}
		});
	} else {
		swal({
			title: "No contiene una solicitud de crédito!",
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
				//console.log("Producto ya configurado");
				//Asignamos el valor de la solicitud para poder traer los participantes
				var solicitud = document.getElementById('solicitud').value;
				//console.log("Traer solicitud: " + solicitud);
				//Llamamos la funcion para obtener los participantes
				traerParticipantes(solicitud)
				//buscarDocumentos(producto)
				//Agregamos la petición para obtener los participantes
			} else if (respuesta == 0) {
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
				title: "Error: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}

// Función que manda la alerta para proceder con la eliminación de la solicitud
function alertaEliminarSolicitud() {
	//console.log("solicitud: " + solicitud);
	swal({
		title: '¿Deseas cancelar la solicitud?',
		text: "Se cancelará la solicitud actual",
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				alertaMotivoEliminacion();
			} else { }
		});
}


// Función para mandar el motivo por el cual se esta cancelando la solicitud | manera manual
function alertaMotivoEliminacion() {
	swal("Observaciones al cancelar", {
		content: "input",
		closeOnClickOutside: false,
		buttons: ["Salir", "Aceptar"],
	})
		.then((value) => {
			if (value) {
				// Llamamos a nuestro spinner
				spinner.style.display = 'block';
				principal.style.display = 'none';
				// guardamos las observaciones en una variable
				observacionesCancelacion = value;
				// 1er paso, llamar la autenticacion del provedor (NA-AT)
				autenticacion();
			} else if (value == "") {
				// Mandamos una alerta
				swal("Debes de agregar las observaciones para poder cancelar", {
					icon: "info",
					closeOnClickOutside: false,
				});
			} else { }
		});
}


// Función para eliminar la solicitud
function eliminarSolicitud() {
	// Llamamos a nuestro spinner
	spinner.style.display = 'none';
	principal.style.display = 'block';
	$.ajax({
		url: newUrlBase + "cancelar_solicitud_envio?envioID=" + envioID + "&motivo=" + observacionesCancelacion,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal("Solicitud cancelada", {
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

			//console.log('error');

			// Mandamos una alerta
			swal("Hubo un error", {
				icon: "error",
				closeOnClickOutside: false,
			});
		}
	});
}


// Función que regresar el valor númerico a formato de moneda nacional
function regresarMoneda(nombre, valor) {
	//console.log(valor);
	return document.getElementById(nombre.id).innerHTML = new Intl.NumberFormat('es-MX').format(valor);
}


// Función que muestra la solicitud de envio
function mostrarSolicitudEnvio(solicitudId) {
	var mostrarSolicitud = document.getElementById("mostrarSolicitudId");
	var botonCancelarId = document.getElementById("botonCancelarId");
	var disponible = document.getElementById("diponibleID");

	$.ajax({
		url: newUrlBase + "datos_solicitud?solicitudId=" + solicitudId + "&accion=cancelar",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			if (respuesta.pk_id_solicitud_envio == 0) {
				// Mostramos la alerta
				disponible.style.display = "block"
				// No mostramos los datos de la solicitud
				mostrarSolicitud.style.display = "none";
				// No mostramos el boton de cancelar
				botonCancelarId.style.display = "none";
			} else {
				// No mostramos la alerta
				disponible.style.display = "none"
				// Mostramos los datos de la solicitud
				mostrarSolicitud.style.display = "block";
				// Si la solicitud está activa, mostrar botón
				botonCancelarId.style.display = "block";
			}

			// Mostrar solicitudes canceladas
			solicitudesCanceladas(solicitudId);

			envioID = respuesta.pk_id_solicitud_envio;

			//Hacemos referencia a los input para llenar los campos:
			var envio = document.getElementById("envioID");
			var fechaAutorizacion = document.getElementById('fechaAutorizacion');
			var quienAutorizo = document.getElementById('quienAutorizo');
			var fechaRegistro = document.getElementById('fechaRegistro');
			var fechaCancelacion = document.getElementById('fechaCancelacion');
			var horaCancelacion = document.getElementById('horaCancelacion');

			// Cambiar el input a texto si no viene nula la fecha de actualización
			if (respuesta.dt_fecha_autorizacion == null) {
				fechaAutorizacion.type = 'text';
			}

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
			envio.value = respuesta.pk_id_solicitud_envio;
			fechaAutorizacion.value = respuesta.dt_fecha_autorizacion != null ? respuesta.dt_fecha_autorizacion : 'Sin registro';
			quienAutorizo.value = respuesta.empleado_autorizo != null ? respuesta.empleado_autorizo : 'Sin registro';
			fechaRegistro.value = respuesta.dt_fecha_registro != null ? respuesta.dt_fecha_registro : 'Sin registro';
			fechaCancelacion.value = respuesta.dt_fecha_cancelacion != null ? respuesta.dt_fecha_cancelacion : 'Sin registro';
			horaCancelacion.value = respuesta.vc_hora_cancelacion != null ? respuesta.vc_hora_cancelacion : 'Sin registro';

			// Guardamos el campo requisitionID para enviarlo al ws de cancelación
			guardarRequisition = respuesta.vc_requisition_id != null ? respuesta.vc_requisition_id : null;
			// console.log(guardarRequisition);

		}, error: function(error) {
			// console.log(error);
		}
	});
}

// Función que muestra las solicitudes canceladas
function solicitudesCanceladas(solicitudID) {

	var canceladasID = document.getElementById("canceladasID");

	$.ajax({
		url: newUrlBase + "solicitudes_autorizadas?solicitudID=" + solicitudID + "&accion=cancelar",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			//console.log(respuesta);

			// validar si hay solicitudes en la respuesta
			if (respuesta.length == 0) {
				canceladasID.style.display = "none";
			} else {
				canceladasID.style.display = "block";
			}

			$('#solicitudesCanceladasID > tbody').empty();

			$.each(respuesta, function(idx, opt) {

				// Validar el tipo de cancelación
				var estatus;

				if (opt.i_estatus == 4) {
					estatus = "Manual";
				} else if (opt.i_estatus == 5) {
					estatus = "Automática";
				}

				var rows = "<tr >"
					+ "<td>" + opt.pk_id_solicitud_envio + "</td>"
					+ "<td>" + opt.dt_fecha_cancelacion + "</td>"
					+ "<td>" + opt.vc_obser_cancelacion + "</td>"
					+ "<td>" + estatus + "</td>"
					+ "</tr>";

				$('#solicitudesCanceladasID').append(rows)

			});

		}, error: function(error) {
			// console.log(error);
		}
	})
}


/**
	Conexión con WS
 */

function WS_IAD() {
	//var urlBase = getAbsolutePath();
	var ws1 = "";
	// console.log("URL WS: " + newUrlBase + "conexionWS_IAD");
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


//Funcion para realizar la autenticacion del user con el proveedor (NA-AT)
function conexionApi(usuario, password, userType) {
	// obtenemos la direccion de ws
	var webService = WS_IAD();
	// console.log("conexionApi");
	var solicitudEnvio = envioID;
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
			// console.log("Respuesta de conexionApi: " + JSON.stringify(respuesta));
			resultadoLogin = true;

			if (JSON.stringify(respuesta).includes("error")) {
				banderaRespuesta = 0;
				// console.log("Tiene error");
				// 3er paso, guardar el proceso de autenticacion a nivel sistema
				guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
				swal({
					icon: "info",
					//title: "Error Conexión: " + respuesta.error_description,
					title: "Hubo un error",
					showConfirmButton: false

				});
			} else {
				banderaRespuesta = 1;
				var token = respuesta.access_token;
				// console.log("No tiene error");
				// 3er paso, guardar el proceso de autenticacion a nivel sistema
				guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
				// 4to paso, realizar el proceso de cancelacion a la api del proveedor (NA-AT)
				wsCancelarFirma(token);
			}
		},
		error: function(respuesta) {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';
			resultadoLogin = false;
			banderaRespuesta = 0;
			// console.log("Error de conexionApi");
			// console.log("Respuesta: " + JSON.stringify(respuesta));
			// 3er paso, guardar el proceso de autenticacion a nivel sistema
			guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta);
			swal({
				icon: "info",
				title: "Hubo un error",
				//title: "Error conexionApi: " + JSON.stringify(respuesta),
				showConfirmButton: false

			});
		}
	});
}

//Funcion para guardar la Autenticacion del User LOGIN
function guardarWSAuteUsu(resultadoLogin, solicitudEnvio, idConfWS, jsonEnvio, respuesta, banderaRespuesta) {
	var datosConexionApi = JSON.stringify(respuesta);
	var token = respuesta.access_token;
	var scope = respuesta.scope;
	var jti = respuesta.jti;
	var tpProceso = "CANCELACION"
	// console.log("guardarWSAuteUsu");
	$.ajax({
		async: false,
		url: newUrlBase + "wsAutenticacionUser/guardarAutenticacion?resultadoLogin=" + resultadoLogin + "&solicitudEnvio="
			+ solicitudEnvio + "&idConfWS=" + idConfWS + "&jsonEnvio=" + jsonEnvio + "&tpProceso=" + tpProceso,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			var idWSAutenticacion = respuesta.pk_id_ws_autenticacion;
			// console.log("idWSAutenticacion: " + idWSAutenticacion);
			if (banderaRespuesta == 0) {
				// console.log("Es falso");
				// 5to paso, guardar la respuesta del proceso de autenticacion a nivel sistema
				guardarRespAutenticacion(datosConexionApi, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta);
			} else if (banderaRespuesta == 1) {
				// console.log("Es true");
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
				title: "Error guardarWSAuteUsu: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}

//Funcion para gardar la Respuesta de la autenticacion LOGIN
function guardarRespAutenticacion(respuesta, token, scope, jti, idWSAutenticacion, solicitudEnvio, banderaRespuesta) {
	// console.log("guardarRespAutenticacion");
	var jsonRespuesta = respuesta.trim();
	var jsonFinal = jsonRespuesta.replace(/[{{}}]/g, "");
	var tpProceso = "CANCELACION";
	// console.log("Bandera: " + banderaRespuesta);
	if (banderaRespuesta == 0) {
		// console.log("Solo registra jsonFinal, idWSAutenticacion");
		$.ajax({
			async: false,
			url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
				"&idWSAutenticacion=" + idWSAutenticacion,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				var autenUser = idWSAutenticacion;
				var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
				// 6to paso, guardar la bitacora del proceso de autenticacion a nivel sistema
				// ---------
				// 11ro paso, guardar la bitacora del proceso de cancelacion a nivel sistema
				guardarBitacoraWS(autenUser, resAuten, tpProceso, solicitudEnvio);
			},
			error: function(respuesta) {
				// Llamamos a nuestro spinner
				spinner.style.display = 'none';
				principal.style.display = 'block';
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
				// Llamamos a nuestro spinner
				spinner.style.display = 'none';
				principal.style.display = 'block';
				swal({
					icon: "info",
					title: "Error guardarRespAutenticacion: " + JSON.stringify(respuesta),
					showConfirmButton: false
				});
			}
		});
	}
}


//Funcion para guardar en Bitacora Consumo
function guardarBitacoraWS(autenUser, resAuten, tpProceso, soliEnvio) {
	// console.log("Ejecutando guardarBitacoraWS");
	$.ajax({
		async: false,
		url: newUrlBase + "bitacoraConsumo?autenUser=" + autenUser + "&resAuten=" + resAuten +
			"&tpProceso=" + tpProceso + "&soliEnvio=" + soliEnvio,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log("Respuesta guardarBitacoraWS: " + JSON.stringify(respuesta));
		},
		error: function(respuesta) {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';
			swal({
				icon: "info",
				title: "Error guardarBitacoraWS: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}


// Función principal de cancelacion por ws
function wsCancelarFirma(token) {
	// obtenemos la direccion de ws
	var webService = WS_IAD();
	var resultadoLogin = false;
	var idConfWS = 4;
	var requisision = "requisisionId:" + guardarRequisition;
	var jsonEnvio = [requisision];
	// Mandamos a llamar el servicio de ws
	$.ajax({
		url: webService + "/ws_cancelar_firma?requisitionID=" + guardarRequisition + "&tokenAutorizacion=" + token,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// Mostramos la respuesta
			// console.log(respuesta);

			if (respuesta.success) {
				// Si la respuesta es true
				resultadoLogin = true;
				// 7mo paso, guardamos la autenticacion de la cancelacion
				guardarWSAuteCancelacion(resultadoLogin, idConfWS, jsonEnvio, respuesta);
				// 8vo paso, Mandamos a llamar el servicio para cancelar a nivel sistema
				eliminarSolicitud();
			} else {
				// Si la respuesta es false

				// Llamamos a nuestro spinner
				spinner.style.display = 'none';
				principal.style.display = 'block';

				swal("Hubo un error", {
					icon: "error",
					closeOnClickOutside: false,
				});
				resultadoLogin = false;
				// 7mo paso, guardamos la autenticacion de la cancelacion
				guardarWSAuteCancelacion(resultadoLogin, idConfWS, jsonEnvio, respuesta);
			}

		}, error: function(error) {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';
			// Mostramos el error
			// console.log(error);
			// Mandamos una alerta
			swal("Hubo un error", {
				icon: "error",
				closeOnClickOutside: false,
			});
		}
	});
}



//Funcion para guardar la autenticacion de cancelacion de documento a firmar
function guardarWSAuteCancelacion(resultadoLogin, idConfWS, jsonEnvio, resJson) {
	//console.log("Ejecutando guardarWSAuteCancelacion: " + JSON.stringify(resJson));
	var tpProceso = "CANCELACION";
	var respuestaEnvioSoli = JSON.stringify(resJson);
	$.ajax({
		async: false,
		url: newUrlBase + "wsAutenticacionUser/guardarAutenticacion?resultadoLogin=" + resultadoLogin + "&solicitudEnvio="
			+ envioID + "&idConfWS=" + idConfWS + "&jsonEnvio=" + jsonEnvio + "&tpProceso=" + tpProceso,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta agregarRequisitionId: " + JSON.stringify(respuesta));
			var idWSAutenticacion = respuesta.pk_id_ws_autenticacion;
			// 9no paso, guardar la respuesta de autenticacaion del proceso de cancelación a nivel sistema
			guardarRespAutenCancelacion(respuestaEnvioSoli, idWSAutenticacion);
			// agregarRequisitionId(requisitionId, solicitudEnvio);
		},
		error: function(respuesta) {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';
			swal({
				icon: "info",
				title: "Error: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}


//Funcion para guardar la Respuesta de la autenticacion de cancelacion de documento a firmar
function guardarRespAutenCancelacion(respuestaEnvioSoli, idWSAutenticacion) {
	var jsonRespuesta = respuestaEnvioSoli.trim();
	var jsonFinal = jsonRespuesta.replace(/[{{}}]/g, "");
	var tpProceso = "CANCELACION";
	//console.log("jsonRespuesta:" + jsonFinal);
	$.ajax({
		async: false,
		url: newUrlBase + "wsResAutenticacionUser/guardarRespAutenticacion?jsonRespuesta=" + jsonFinal +
			"&idWSAutenticacion=" + idWSAutenticacion + "&requisitionId=" + guardarRequisition,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log("Respuesta de guardarRespAutenticacion: " + JSON.stringify(respuesta));
			var autenUser = idWSAutenticacion;
			var resAuten = respuesta.pk_id_respuesta_autenticacion_seq;
			// agregarRequisitionId(requisitionId, solicitudEnvio);
			// 10mo paso, guardamos la bitacora del proceso de cancelación a nivel sistema
			guardarBitacoraWS(autenUser, resAuten, tpProceso, envioID);

		},
		error: function(respuesta) {
			// Llamamos a nuestro spinner
			spinner.style.display = 'none';
			principal.style.display = 'block';
			swal({
				icon: "info",
				title: "Error guardarRespAutenticacion: " + JSON.stringify(respuesta),
				showConfirmButton: false
			});
		}
	});
}
