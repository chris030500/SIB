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
			listaSucursales();
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
				if (busqueda.value == 53) {
					document.getElementById("verSucursal").style.display = "block";
					document.getElementById("verSucursal").style.visibility = "visible";
					document.getElementById("verCliente").style.display = "none";
					document.getElementById("verCliente").style.visibility = "hidden";
				} else {
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
	console.log(sucursalValue + ", " + fechaInicio + ", " + fechaFin + ", " + estatusGestion);
	if (sucursalValue != "" && fechaInicio != undefined && fechaFin != undefined && estatusGestion != undefined) {
		const dto = {
			tipoBusqueda: tipoBusqueda,
			sucursal: sucursalValue,
			cliente: "",
			estatusGestion: estatusGestion,
			fechaInicio: fechaInicio,
			fechaFin: fechaFin
		}
		console.log(dto);
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
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
	var buscar = document.getElementById('buscarSucursal').value;
	$.ajax({
		url: newUrlBase + "buscar_sucursal?sucursal=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionSucursal');
			opciones.innerHTML = '';
			respuesta.sucursal.forEach(function(sucursal) {
				var option = document.createElement('option');
				option.value = sucursal.vc_nombresucursal;
				opciones.appendChild(option);
			});
		}
	});
}

buscarSucursal.addEventListener('input', function() {
	sucursalValue = this.value;
});
/**
 * Fin
 */