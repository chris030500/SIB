// @ts-nocheck
var botones = "";
function getAbsolutePath() {
	return window.location.href.substring(0, window.location.href.indexOf("STD/") + 4);
}

window.onload = function() {
	listaProcesos();
	listaClientes();
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/menuProductos", "");

var sms = [];
var mens = [];

var obtDatosMensajeSMS = "mensajes/obtSMS";
function listaMensajes() {
	url = getAbsolutePath() + obtDatosMensajeSMS;
	$.getJSON(url, {}, function(data) {
		tblMensajesGenerados.clear().draw();
		$.each(data, function(idx, opt) {

			var botonesActivar = "";
			var botonesActivar = " <button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' "
				+ " title='Actualizar Estatus'  type='button'"
				+ " onclick='actualizarEstatus("
				+ opt.pk_id_plantilla
				+ ")' id="
				+ opt.pk_id_plantilla
				+ " data-tooltip='Add'>"
				+ ((opt.i_activo == 1) ? "<i class='bi bi-unlock-fill'></i></button>" : "<i class='bi bi-lock-fill'></i></button>")
				+ "&nbsp;";
			var botonesEditar = "";
			var botonesEditar = "<button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle='modal'"
				+ " title='Editar Registro' data-bs-target='#altaPlantilla' aria-controls='table-datatable' type='button'"
				+ " onclick='editarPlantilla("
				+ opt.pk_id_plantilla
				+ ")' id="
				+ opt.pk_id_plantilla
				+ " data-tooltip='Add'>"
				+ " <i class='fa-solid fa-pen-to-square'></i></button>";

			botones = botonesActivar + botonesEditar;



			tblMensajesGenerados.row.add([opt.clienteID, opt.nombreCompleto, opt.nombreBase, opt.nombreClasificacion, opt.telefonoCelular, opt.descMensaje, (opt.estMensaje == 1 ? 'ACTIVO' : 'INACTIVO'), botones]).draw(false);
		});
	});
}

// Función para mostrar u ocultar los campos de búsqueda según la opción seleccionada
function toggleSearchFields() {
	var filterType = document.getElementById("filterType").value;
	if (filterType === "client") {
		document.getElementById("clientSearchField").style.display = "block";
		document.getElementById("branchSearchField").style.display = "none";
		document.getElementById("buscarSucursal").value = "";
		listaClientes();
	} else if (filterType === "branch") {
		document.getElementById("clientSearchField").style.display = "none";
		document.getElementById("branchSearchField").style.display = "block";
		document.getElementById("buscarCliente").value = "";
		listaSucursales();
	} else {
		document.getElementById("clientSearchField").style.display = "none";
		document.getElementById("branchSearchField").style.display = "none";
	}
}

// Llamar a la función toggleSearchFields() cuando se cambie la opción del select
document.getElementById("filterType").addEventListener("change", toggleSearchFields);

var buscarCliente = document.getElementById('buscarCliente');
var spinnerClie = document.getElementById("spinnerClientes");
var clienteValue;

function listaClientes() {
	buscarCliente.disabled = true;
	spinnerClie.style.display = "block";
	var buscar = buscarCliente.value = '';
	$.ajax({
		url: urlPrincipal.replace("/operaciones", "") + "buscar_cliente_activo?valor=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionCliente');
			opciones.innerHTML = '';
			respuesta.lista.forEach(function(cliente) {
				var option = document.createElement('option');
				option.setAttribute('data-value', cliente.i_clienteid);
				option.value = cliente.vc_nombrecompleto;
				opciones.appendChild(option);
			});

			// Aquí, después de agregar las opciones, actualiza el valor seleccionado
			actualizarValorSeleccionado();
			buscarCliente.disabled = false;
			spinnerClie.style.display = "none";
		}
	});
}

var buscarSucursal = document.getElementById('buscarSucursal');
var sucursalValue;

function listaSucursales() {
	var buscar = buscarSucursal.value;
	$.ajax({
		url: urlPrincipal.replace("/operaciones", "") + "buscar_sucursal?sucursal=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionSucursal');
			opciones.innerHTML = '';
			respuesta.sucursal.forEach(function(sucursal) {
				var option = document.createElement('option');
				option.setAttribute('data-value', sucursal.pk_id_sucursal);
				option.value = sucursal.vc_nombresucursal;
				opciones.appendChild(option);
			});

			// Aquí, después de agregar las opciones, actualiza el valor seleccionado
			actualizarValorSucursal();
			buscarSucursal.disabled = false;
		}
	});
}

var obtDatosProcesos = "mensajes/obtProcesos"
function listaProcesos() {
	url = getAbsolutePath() + obtDatosProcesos;
	$.getJSON(url, {}, function(data) {
		var select = $('#process'); // Seleccionar el elemento select por su ID

		// Limpiar opciones existentes
		select.empty();

		// Agregar opción predeterminada
		select.append('<option value="">Seleccionar proceso</option>');

		// Iterar sobre los datos recibidos y agregar opciones al select
		$.each(data, function(index, proceso) {
			// Crear una nueva opción con el nombre del proceso y su ID
			var option = $('<option>', {
				value: proceso.procesoID, // Suponiendo que el ID del proceso está en la propiedad "id"
				text: proceso.tipoBase // Suponiendo que el nombre del proceso está en la propiedad "nombre"
			});

			// Agregar la opción al select
			select.append(option);
		});
	});
}


function actualizarValorSeleccionado() {
	var opcionSeleccionada = document.querySelector('option[value="' + buscarCliente.value + '"]');
	clienteValue = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;
}

function actualizarValorSucursal() {
	var opcionSeleccionada = document.querySelector('option[value="' + buscarSucursal.value + '"]');
	sucursalValue = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;
}

buscarSucursal.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	actualizarValorSucursal();
});

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	actualizarValorSeleccionado();
});

var parametroCliente;
var parametroSucu;
var parametroProce;

var cliente = "";
var sucu = "";
function validarBusqueda() {
	document.getElementById("ver-cargaSeguimiento").style.display = "block";
	document.getElementById("ver-listaSeguimiento").style.display = "none";
	var cliente = $("#buscarCliente").val();
	var sucu = $("#buscarSucursal").val();
	var proce = $("#process").val();
	parametroCliente = cliente;
	parametroSucu = sucu;
	parametroProce = proce;
	llenarTablaSeguimiento();
}

function llenarTablaSeguimiento() {

	servicioURL = "mensajes/obtSMS?cliente=" + parametroCliente + "&sucur=" + parametroSucu + "&proce=" + parametroProce

	$.ajax({
		url: getAbsolutePath() + servicioURL,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			document.getElementById("ver-cargaSeguimiento").style.display = "none";
			document.getElementById("ver-listaSeguimiento").style.display = "block";
			tblMensajesGenerados.clear().draw();;
			$.each(respuesta.lista, function(idx, opt) {
				var botonesEditar = "<button class='btn btn-primary btn-sm' tabindex='0' type='button' onclick='editarMensaje(" + opt.mensajeID + ", \"" + opt.descMensaje + "\")'><i class='fas fa-edit'></i></button>";
				var botonesEnviar = "<button class='btn btn-success btn-sm' tabindex='0' title='Enviar SMS' type='button' onclick='enviarSMS(" + opt.telefonoCelular + ", \"" + opt.descMensaje + ", \"" + opt.mensajeID +"\")'><i class='fas fa-paper-plane'></i></button>";
				var botonesInactivar = "<button class='btn btn-danger btn-sm' tabindex='0' title='Inactivar SMS' type='button' onclick='inactivarSMS(" + opt.mensajeID + ", 0)'><i class='fas fa-ban'></i></button>";
				var botonesActivar = "<button class='btn btn-success btn-sm' tabindex='0' title='Activar SMS' type='button' onclick='inactivarSMS(" + opt.mensajeID + ", 1)'><i class='fas fa-check'></i></button>";
				var botones = "";

				if (opt.estMensaje == 1) {
					botones = botonesEditar + " " + botonesEnviar + " " + botonesInactivar;
				}
				if (opt.estMensaje == 0) {
					botones = botonesEditar + " " + botonesActivar;
				}
				if (opt.estMensaje == 2) {
					botones = "El mensaje ya se envio";
				}

				// Agregar el telefono y el mensaje a los arrays correspondientes
				var mensaje = {
					telefono: opt.telefonoCelular,
					mensaje: opt.descMensaje,
					idMensaje: opt.mensajeID
				};

				sms.push(mensaje);

				tblMensajesGenerados.row.add([opt.clienteID, opt.nombreCompleto, opt.nombreBase, opt.nombreClasificacion, opt.telefonoCelular, opt.descMensaje, (opt.estMensaje == 0 ? 'INACTIVO' : opt.estMensaje == 1 ? 'ACTIVO' : 'ENVIADO'), botones]).draw(false);
			});
		},
		error: function(error) {
			//console.log(error.error);
		}
	});
}

function editarMensaje(id, mensaje) {
	$('#editarMensajeModal').modal('show');
	$('#descMensaje').val(mensaje);
	$('#mensajeID').val(id);
}

function cerrarMensaje() {
	$('#editarMensajeModal').modal('hide');
}

function guardarCambios() {
	var mensajeID = document.getElementById("mensajeID").value;
	var descMensaje = document.getElementById("descMensaje").value;
	servicioURL = "mensajes/editSMS?id=" + mensajeID + "&mensaje=" + descMensaje

	$.ajax({
		url: getAbsolutePath() + servicioURL,
		type: "GET",
		success: function(response) {
			swal(
				"¡Aplicado!",
				"El mensaje se actualizo correctamente.",
				"success"
			);

			// Aquí puedes manejar la respuesta del servidor si es necesario
			$('#editarMensajeModal').modal('hide');
			llenarTablaSeguimiento();
		},
		error: function(xhr, status, error) {
			// Aquí puedes manejar cualquier error que ocurra durante la solicitud AJAX
			console.error("Error al enviar los datos: " + error);
		}
	});
}

function inactivarSMS(mensajeID, estatus) {
	servicioURL = "mensajes/inactivarSMS?id=" + mensajeID + "&estatus=" + estatus
	var tituloAlerta = "";
	var textoAlerta = "";
	var confirmButton = "";

	if (estatus === 0) {
		tituloAlerta = "¿Estás seguro de inactivar el mensaje?";
		textoAlerta = "¡El mensaje se inactivará y no sera tomado encuenta para el envio masivo!";
		confirmButton = "Inactivar";
	} else if (estatus === 1) {
		tituloAlerta = "¿Estás seguro de activar el mensaje?";
		textoAlerta = "¡El mensaje se activará y sera tomado encuenta para el envio masivo!";
		confirmButton = "Activar";
	}

	swal({
		title: tituloAlerta,
		text: textoAlerta,
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancelar',
		confirmButtonText: confirmButton,
	}).then((result) => {
		if (result.value) {
			$.ajax({
				url: getAbsolutePath() + servicioURL,
				type: "GET",
				success: function(response) {
					swal(
						"¡Procesado!",
						"El estatus del mensaje ha sido actualizado.",
						"success"
					)
					llenarTablaSeguimiento();
				},
				error: function(xhr, status, error) {
					// Aquí puedes manejar cualquier error que ocurra durante la solicitud AJAX
					console.error("Error al enviar los datos: " + error);
				}
			});
		}
	})
}

function enviarSMS(telefono, mensaje, mensajeID) {
	servicioURL = "mensajes/sendSMSMasivo"
	var tituloAlerta = "<div style='text-align:center'><i class='fas fa-paper-plane'></i></div><br><div style='text-align:center'>¿Estás seguro de enviar el mensaje?</div>";
	var textoAlerta = "¡El mensaje se enviará y no será posible cancelar el envío!";
	var confirmButton = "Enviar";

	var mensaje = {
		telefono: telefono,
		mensaje: mensaje,
		idMensaje: mensajeID
	};

	mens.push(mensaje);

	swal({
		title: tituloAlerta,
		text: textoAlerta,
		showCancelButton: true,
		confirmButtonColor: '#28a745',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancelar',
		confirmButtonText: confirmButton,
	}).then((result) => {
		if (result.value) {
			console.log(mens)
			$.ajax({
				url: getAbsolutePath() + servicioURL,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(mens),
				success: function(response) {
					swal(
						"¡Enviado!",
						"El mensaje ha sido enviado.",
						"success"
					)
					llenarTablaSeguimiento();
				},
				error: function(xhr, status, error) {
					// Aquí puedes manejar cualquier error que ocurra durante la solicitud AJAX
					console.error("Error al enviar los datos: " + error);
				}
			});
		}
	})
}

function enviarMasivo() {
	servicioURL = "mensajes/sendSMSMasivo"
	var tituloAlerta = "<div style='text-align:center'><i class='fas fa-paper-plane'></i></div><br><div style='text-align:center'>¿Estás seguro de enviar todos los mensajes?</div>";
	var textoAlerta = "¡Los mensajes se enviarán de manera masiva y no será posible cancelar el envío!";
	var confirmButton = "Enviar";

	swal({
		title: tituloAlerta,
		text: textoAlerta,
		showCancelButton: true,
		confirmButtonColor: '#28a745',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancelar',
		confirmButtonText: confirmButton,
	}).then((result) => {
		if (result.value) {
			console.log(sms)
			$.ajax({
				url: getAbsolutePath() + servicioURL,
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(sms),
				success: function(response) {
					swal(
						"¡Enviado!",
						"El mensaje ha sido enviado.",
						"success"
					)
					llenarTablaSeguimiento();
				},
				error: function(xhr, status, error) {
					// Aquí puedes manejar cualquier error que ocurra durante la solicitud AJAX
					console.error("Error al enviar los datos: " + error);
				}
			});
		}
	})
}

function downloadReport() {
	
	servicioURL = "mensajes/reporteSMS?cliente=" + parametroCliente + "&sucur=" + parametroSucu + "&proce=" + parametroProce
	
	$.ajax({
		url: getAbsolutePath() + servicioURL,
		type: "GET",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0];

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
}





