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
	listaClientes();
	llenarTablaSeguimiento(false);
}
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
	spinner.style.display = "block";
	var buscar = buscarCliente.value;
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
 * Llenar tabla de seguimiento
 */
var servicioURL;

function llenarTablaSeguimiento(condicion) {

	if (condicion) {
		servicioURL = "parametros_seguimiento?nombre=" + parametroNombre + "&oferta=" + parametroOferta + "&referencia=" + parametroReferencia
	} else {
		servicioURL = "lista_seguimiento";
	}

	$.ajax({
		url: newUrlBase + servicioURL,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			tablaFiltro.clear().draw();
			$.each(respuesta.lista, function(idx, opt) {

				var btnEditar = "<div class='row'><div class='col-sm-6'>"
					+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='modalEditar("
					+ opt.i_clienteid
					+ ")' id="
					+ opt.i_clienteid
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var btnEstatus = "<div class='col-sm-6'><button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Vigencia' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='estatusGestion("
					+ opt.i_clienteid + ', ' + opt.i_estatus
					+ ")' id="
					+ opt.i_clienteid
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-unlock'></i></button></div></div>";

				var vigente = opt.i_estatus == 1 ? "Activo" : "Inactivo";

				tablaFiltro.row.add([
					opt.i_clienteid,
					opt.nombreCompleto,
					opt.vc_codigo_oferta,
					opt.vc_cod_referencia,
					vigente,
					opt.fechainicio,
					opt.fechafin,
					opt.v_valor,
					opt.vc_nombresucursal,
					"usuario",
					btnEditar + btnEstatus
				]).draw(false);
			});
		},
		error: function(error) {
			console.log(error.error);
		}
	});
}
/**
 * Fin
 */


/**
 * Validar parametros de busqueda
 */
var parametroNombre;
var parametroOferta;
var parametroReferencia;

function validarBusqueda() {
	var cliente = $("#buscarCliente").val();
	var referencia = $("#inputReferencia").val();
	var oferta = $("#inputOferta").val();
	// var pattern = /^[0-9]*$/;
	parametroNombre = cliente;
	parametroOferta = oferta;
	parametroReferencia = referencia;
	llenarTablaSeguimiento(true);
}
/**
 * Fin
 */