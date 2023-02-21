// Variable | id solicitud de envio
var envioID;

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
	console.log(newUrlBase);
	//console.log("solicitudCredito: " + solicitudCredito);
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
				console.log("buscar_solicitud");
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
					envioID = solicitudCredito;
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
				mostrarSolicitudEnvio(envioID);
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
			envio.value = respuesta.pk_id_solicitud_envio;
			fechaRegistro.value = respuesta.dt_fecha_registro != null ? respuesta.dt_fecha_registro : 'Sin registro';
			fechaCancelacion.value = respuesta.dt_fecha_cancelacion != null ? respuesta.dt_fecha_cancelacion : 'Sin registro';
			horaCancelacion.value = respuesta.vc_hora_cancelacion != null ? respuesta.vc_hora_cancelacion : 'Sin registro';

			// Mostrar solicitudes canceladas
			listaSolicitudes(solicitudId);

		}, error: function(error) {
			console.log(error);
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
			if (observaciones) {
				swal("Solicitud actualizada", {
					icon: "success",
				})
					.then((value) => {
						window.location.reload();
					});
				actualizarSolicitud(observaciones, tipo);
			} else if (observaciones == "") {
				// Mandamos alerta de que no hay texto
				swal("Debes de agregar las observaciones para poder " + tipo, {
					icon: "info",
					closeOnClickOutside: false,
				});

			} else { }
		});
}



// Función que autoriza la solicitud
function actualizarSolicitud(observacion, tipo) {
	$.ajax({
		url: newUrlBase + "autorizar_la_solicitud?tipoEstatus=" + tipo + "&envioID=" + envioID + "&observaciones=" + observacion,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//
		}, error: function() {
			//
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
			console.log(error);
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





