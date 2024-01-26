// @ts-nocheck
/**
* Liga absoluta
*/
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/operaciones", "");

window.onload = function() {
	listaDeBusqueda();
}
/**
* Fin
*/
var formulario = document.getElementById("formReporte");
var tipoBusqueda;

function listaDeBusqueda() {
	$.ajax({
		url: newUrlBase + "lista_busqueda",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			listaDeGestiones();
			var busqueda = document.getElementById("selectBusqueda");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				busqueda.add(opcion);
			}
			busqueda.addEventListener("change", function() {
				formulario.style.display = "block";
				tipoBusqueda = busqueda.value;
				buscarSucursal.value = '';
				sucursalValue = '';
				clienteValue = '';
				if (busqueda.value == 53) {
					document.getElementById("verSucursal").style.display = "block";
					document.getElementById("verSucursal").style.visibility = "visible";
					document.getElementById("verCliente").style.display = "none";
					document.getElementById("verCliente").style.visibility = "hidden";
					listaSucursales();
				} else {
					listaClientes();
					document.getElementById("verCliente").style.display = "block";
					document.getElementById("verCliente").style.visibility = "visible";
					document.getElementById("verSucursal").style.display = "none";
					document.getElementById("verSucursal").style.visibility = "hidden";
				}

			});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}

var estatusGestion;

function listaDeGestiones() {
	var busqueda = document.getElementById("selectGestion");
	$.ajax({
		url: newUrlBase + "lista_gestiones",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				busqueda.add(opcion);
			}
			busqueda.addEventListener("change", function() {
				estatusGestion = busqueda.value;
			});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */


/**
 * Generar reporte
 */
function obtenerDatos() {
	var fechaInicio = $('#fechaInicio').val();
	var fechaFin = $('#fechaFin').val();

	if (document.getElementById("selectBusqueda").value == 53) {
		if (sucursalValue != '' && sucursalValue != null && fechaInicio != '' && fechaFin != '' && estatusGestion != undefined) {
			const dto = {
				gestion: tipoBusqueda,
				sucursal: sucursalValue,
				estatusGestion: estatusGestion,
				fechaInicio: fechaInicio,
				fechaFin: fechaFin
			}
			// console.log(dto);
			generarReporte(dto);
		} else {
			// Se ejecuta la validacion
			swal("Falta información por llenar", {
				icon: "warning"
			});
		}
	} else {
		if (clienteValue != "" && clienteValue != null && fechaInicio != '' && fechaFin != '' && estatusGestion != undefined) {
			const dto = {
				tipoBusqueda: tipoBusqueda,
				cliente: clienteValue,
				estatusGestion: estatusGestion,
				fechaInicio: fechaInicio,
				fechaFin: fechaFin
			}
			//console.log(dto);
			generarReporte(dto);
		} else {
			// Se ejecuta la validacion
			swal("Falta información por llenar", {
				icon: "warning"
			});
		}
	}
}
/**
 * Fin
 */


/**
 * Lista sucursales
 */
var buscarSucursal = document.getElementById('buscarSucursal');
var sucursalValue;

function listaSucursales() {
	var buscar = buscarSucursal.value;
	$.ajax({
		url: newUrlBase + "buscar_sucursal?sucursal=" + buscar,
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
		}
	});
}

function actualizarValorSucursal() {
	var opcionSeleccionada = document.querySelector('option[value="' + buscarSucursal.value + '"]');
	sucursalValue = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;
}

buscarSucursal.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	actualizarValorSucursal();
});
/**
 * Fin
 */


/**
 * Lista clientes
 */
var buscarCliente = document.getElementById('buscarCliente');
var spinner = document.getElementById("spinnerClientes");
var clienteValue;

function listaClientes() {
	buscarCliente.disabled = true;
	spinner.style.display = "block";
	var buscar = buscarCliente.value = '';
	$.ajax({
		url: newUrlBase + "buscar_cliente_activo?valor=" + buscar,
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
			spinner.style.display = "none";
		}
	});
}

function actualizarValorSeleccionado() {
	var opcionSeleccionada = document.querySelector('option[value="' + buscarCliente.value + '"]');
	clienteValue = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;
}

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	actualizarValorSeleccionado();
});
/**
 * Fin
 */


/**
 * Validación de las fechas
 */
document.addEventListener("DOMContentLoaded", function() {
	const fechaInicioInput = document.getElementById("fechaInicio");
	const fechaFinInput = document.getElementById("fechaFin");
	const errorFecha = document.getElementById("errorFecha");

	fechaInicioInput.addEventListener("change", function() {
		// Limpiar el mensaje de error cuando cambia la fecha de inicio
		errorFecha.textContent = "";

		// Actualizar el atributo min del input fechaFin para no permitir fechas anteriores a la de inicio
		fechaFinInput.min = fechaInicioInput.value;
	});

	fechaFinInput.addEventListener("change", function() {
		const fechaInicio = new Date(fechaInicioInput.value);
		const fechaFin = new Date(fechaFinInput.value);

		if (fechaFin < fechaInicio) {
			errorFecha.textContent = "La fecha fin no puede ser anterior a la fecha de inicio";
			fechaFinInput.value = ""; // Limpiar el valor si es inválido
		} else {
			errorFecha.textContent = "";
		}
	});
});
/**
 * Fin
 */


/**
 * Generar reporte de gestión
 */
function generarReporte(dto) {
	$.ajax({
		url: newUrlBase + "reporte_gestion?gestion=" + dto.estatusGestion
			+ "&sucursal=" + dto.sucursal
			+ "&cliente=" + dto.cliente
			+ "&fechaInicio=" + dto.fechaInicio
			+ "&fechaFin=" + dto.fechaFin,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {
			var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
			// var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0];
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
/**
 * Fin
 */