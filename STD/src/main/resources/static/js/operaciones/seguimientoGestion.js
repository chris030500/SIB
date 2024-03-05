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
	mostrarAlerta();
	listaClientes();
	// obtenerSucursal();
	// llenarTablaSeguimiento(false);
}
/**
* Fin
*/


/**
 * Validación de las fechas
 */
document.addEventListener("DOMContentLoaded", function() {
	const fechaInicioInput = document.getElementById("fecha-gestion");
	const fechaFinInput = document.getElementById("fecha-cita");
	const errorFecha = document.getElementById("validar-segFecha");

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
 * Lista clientes
 */
var buscarCliente = document.getElementById('buscarCliente');
// var spinner = document.getElementById("spinnerClientes");
var clienteValue;

function listaClientes() {
	// spinner.style.display = "block";
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
			// spinner.style.display = "none";
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
var idOferta;

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
			document.getElementById("ver-cargaSeguimiento").style.display = "none";
			document.getElementById("ver-listaSeguimiento").style.display = "block";
			tablaFiltro.clear().draw();
			$.each(respuesta.lista, function(idx, opt) {

				var btnEditar = "<div class='row'><div class='col-sm-6'>"
					+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='mostrarGestion("
					+ opt.i_clienteid + ", " + opt.pk_id_asignacion_oa + ", " + opt.i_bloqueo
					+ ")' id="
					+ opt.i_clienteid
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var cliente = opt.i_estatus == 1 ? "Activo" : "Inactivo";
				var asignacion = opt.i_bloqueo == 1 ? "Activo" : "Inactivo";

				tablaFiltro.row.add([
					opt.i_clienteid,
					opt.nombreCompleto,
					cliente,
					opt.vc_codigo_oferta,
					opt.vc_cod_referencia,
					asignacion,
					opt.fechainicio,
					opt.fechafin,
					opt.v_valor,
					opt.vc_nombresucursal,
					opt.vc_usuario,
					btnEditar
				]).draw(false);
			});
		},
		error: function(error) {
			//console.log(error.error);
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
	document.getElementById("ver-cargaSeguimiento").style.display = "block";
	document.getElementById("ver-listaSeguimiento").style.display = "none";
	var cliente = $("#buscarCliente").val();
	var referencia = $("#inputReferencia").val();
	var oferta = $("#inputOferta").val();
	// var pattern = /^[0-9]*$/;
	parametroNombre = cliente;
	parametroOferta = oferta;
	parametroReferencia = referencia;

	if (parametroNombre != '' || parametroOferta != '' || parametroReferencia != '') {
		llenarTablaSeguimiento(true);
	} else {
		document.getElementById("ver-cargaSeguimiento").style.display = "none";
		swal("Llenar mínimo un campo para realizar la busqueda", {
			closeOnClickOutside: false,
			icon: "warning",
		});
	}

}
/**
 * Fin
 */


/**
 * Validar la gestión
 */
let inputReferencia = document.getElementById("referencia");
let inputRelacion = document.getElementById("buscar-relacion");
let inputDescripcion = document.getElementById("descripcion-gestion");
let inputFechaCita = document.getElementById("fecha-cita");
let inputFechaGestion = document.getElementById("fecha-gestion");
let inputAccionRealizar = document.getElementById("accionRealizar");

let validarRelacion = document.getElementById("validar-relacion");
let validarDescripcion = document.getElementById("validar-descripcion");
let validarFechaCita = document.getElementById("validar-fechaCita");
let validarFechaGestion = document.getElementById("validar-fechaGestion");


inputRelacion.addEventListener("input", function() {
	validarRelacion.style.display = "none";
});
inputDescripcion.addEventListener("input", function() {
	validarDescripcion.style.display = "none";
});
inputFechaCita.addEventListener("input", function() {
	validarFechaCita.style.display = "none";
});
inputFechaGestion.addEventListener("input", function() {
	validarFechaGestion.style.display = "none";
});

function validarGestion() {
	if (datosRelacion && datosRelacion.v_valor == "MOSTRAR CALENDARIO") {
		inputRelacion.value == '' ? validarRelacion.style.display = 'block' : validarRelacion.style.display = 'none';
		inputDescripcion.value == '' ? validarDescripcion.style.display = 'block' : validarDescripcion.style.display = 'none';
		inputFechaCita.value == '' ? validarFechaCita.style.display = 'block' : validarFechaCita.style.display = 'none';
		inputFechaGestion.value == '' ? validarFechaGestion.style.display = 'block' : validarFechaGestion.style.display = 'none';

		if (inputRelacion.value != '' && inputDescripcion.value != '' && inputFechaCita.value != '' && inputFechaGestion.value != '') {
			const dto = {
				asignacionOfertas: idOferta,
				referencia: inputReferencia.textContent,
				relacionCodigos: datosRelacion.pk_id_relacion_codigo,
				descripcion: inputDescripcion.value,
				fechaCita: inputFechaCita.value,
				fechaGestion: inputFechaGestion.value
			};
			//console.log(dto);
			registrarGestion(dto);
		}
	} else {
		inputRelacion.value == '' ? validarRelacion.style.display = 'block' : validarRelacion.style.display = 'none';
		inputDescripcion.value == '' ? validarDescripcion.style.display = 'block' : validarDescripcion.style.display = 'none';
		inputFechaGestion.value == '' ? validarFechaGestion.style.display = 'block' : validarFechaGestion.style.display = 'none';

		if (inputRelacion.value != '' && inputDescripcion.value != '' && inputFechaGestion.value != '') {
			const dto = {
				asignacionOfertas: idOferta,
				referencia: inputReferencia.textContent,
				relacionCodigos: datosRelacion.pk_id_relacion_codigo,
				descripcion: inputDescripcion.value,
				fechaGestion: inputFechaGestion.value
			};
			//console.log(dto);
			registrarGestion(dto);
		}
	}
}

let gestionesPorAsig;

function listaDeGestiones() {
	$.ajax({
		url: newUrlBase + "lista_gestiones_por_oferta?asignacion=" + idOferta,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			llenarTablaDeGestiones(respuesta.lista);
		}, error: function(error) {
			//console.log(error);
		}
	});
}

function datosDeCliente(id) {
	$.ajax({
		url: newUrlBase + "ver_cliente?cliente=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log(respuesta);
			inputReferencia.textContent = respuesta.cliente[0].vc_cod_referencia;
		}, error: function(error) {
			//console.log(error);
		}
	});
}
/**
 * Fin
 */


/**
 * Registrar gestion
 */
function registrarGestion(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "registro_seguimiento",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			swal("Se registró la gestión", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					bloqueoAsignacion = respuesta.valor;
					validarBotonRegistro(respuesta.valor);
					listaDeGestiones();
					limpiarVariablesModal();
				});
		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de guardar los datos", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */


/**
 * funciones del modal
 */
let bloqueoAsignacion;

function mostrarGestion(id, asignacion, bloqueo) {
	bloqueoAsignacion = bloqueo;
	document.getElementById("numCliente").textContent = id;
	idOferta = asignacion;
	$('#modal-seguimiento').modal('show');
	listaDeGestiones();
	datosDeCliente(id);
	buscarRelacion();
	validarBotonRegistro(bloqueo);
}


function cerrarModal(valor) {
	$('#modal-' + valor).modal('hide');
	llenarTablaSeguimiento(true);
	limpiarVariablesModal();
}

function limpiarVariablesModal() {
	const campos = ['buscar-relacion', 'descripcion-gestion', 'fecha-cita', 'fecha-gestion', 'accionRealizar'];
	const alertas = ['validar-relacion', 'validar-descripcion', 'validar-fechaCita', 'validar-fechaGestion'];
	campos.forEach(campo => {
		document.getElementById(`${campo}`).value = '';
	});
	alertas.forEach(alerta => {
		document.getElementById(`${alerta}`).style.display = 'none';
	});
	document.getElementById("vistaAccionRealizar").style.display = "none";
	document.getElementById("accionRealizar").textContent = '';
	searchSucursal.value = '';
}
/**
 * Fin
 */


/**
 * Evento click para vaciar
 */
document.addEventListener('click', function(event) {
	var modal = document.getElementById('modal-seguimiento');
	// Verificar si el clic no está dentro de la modal
	if (modal && !modal.contains(event.target)) {
		limpiarVariablesModal();
	}
});
/**
 * Fin
 */



/**
 * Buscar relación de códigos
 */
let datosRelacion;
let almacen;

function buscarRelacion() {
	let searchTerm = document.getElementById('buscar-relacion').value;
	$.ajax({
		url: newUrlBase + "listaRelaciones_activas?valor=" + searchTerm,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log(respuesta)
			almacen = respuesta;
			const opcionesDatalist = document.getElementById('opciones-relacion');
			opcionesDatalist.innerHTML = '';
			respuesta.listaRelaciones.forEach(function(valor) {
				const option = document.createElement('option');
				option.value = valor.relacion_codigos;
				opcionesDatalist.appendChild(option);
			});
		}
	});
}

var searchInput = document.getElementById('buscar-relacion');
searchInput.addEventListener('input', buscarRelacion);

searchInput.addEventListener('change', function() {
	let selectedValue = searchInput.value;
	if (almacen.listaRelaciones && almacen.listaRelaciones.length > 0) {
		//
		datosRelacion = almacen.listaRelaciones.find(item => item.relacion_codigos == selectedValue);
		//console.log(datosRelacion);
		if (datosRelacion != undefined) {
			document.getElementById("vistaAccionRealizar").style.display = "block";
			inputAccionRealizar.textContent = datosRelacion.v_valor;
		} else {
			document.getElementById("vistaAccionRealizar").style.display = "none";
			inputAccionRealizar.textContent = '';
		}
		//
		if (datosRelacion && datosRelacion.v_valor == "MOSTRAR CALENDARIO") {
			document.getElementById('fecha-cita').disabled = false;
			document.getElementById('validar-fechaCita').style.display = 'none';
		} else {
			document.getElementById('fecha-cita').disabled = true;
			document.getElementById('validar-fechaCita').style.display = 'none';
			inputFechaCita.value = '';
		}
	} else {
		document.getElementById('fecha-cita').disabled = false;
		document.getElementById('validar-fechaCita').style.display = 'none';
	}
});
/**
 * Fin
 */


/**
 * seguimiento de gestiones por asignacion de oferta auto.
 */
function llenarTablaDeGestiones(datos) {
	tablaGestion.clear().draw();
	$.each(datos, function(idx, opt) {

		var btnEditar = "<div class='row'><div class='col-sm-6'>"
			+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary'"
			+ " title='Editar' type='button'"
			+ " onclick='edtiarSegGestion("
			+ opt.pk_id_seguimiento_gestion
			+ ")' id="
			+ opt.pk_id_seguimiento_gestion
			+ " data-tooltip='Add'>"
			+ " <i class='bi bi-pencil'></i></button></div>";

		tablaGestion.row.add([
			opt.relacionCodigosModel.accionRealizar.v_valor,
			opt.relacionCodigosModel.codigoAccionModel.vc_descripcion + "(" + opt.relacionCodigosModel.codigoAccionModel.vc_codigo + ")",
			opt.relacionCodigosModel.codigoResultadoModel.vc_descripcion + "(" + opt.relacionCodigosModel.codigoResultadoModel.vc_codigo + ")",
			opt.vc_descripcion,
			opt.dt_fecha_registro,
			opt.dt_fecha_gestion_realizada,
			//btnEditar
		]).draw(false);

	});
}
/**
 * Fin
 */


/**
 * Mostrar datos de la gestión para poder editar
 */
function edtiarSegGestion(id) {
	$.ajax({
		url: newUrlBase + "datos_gestion?idGestion=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log(respuesta.model);
			/*inputDescripcion.value = respuesta.model.vc_descripcion;
			inputFechaCita.value = respuesta.model.dt_fecha_cita;
			inputFechaGestion.value = respuesta.model.dt_fecha_gestion_realizada;*/
		}
	});
}
/**
 * Fin
 */


/**
 * Bloquear botón de registro
 */

function validarBotonRegistro(bloqueo) {
	if (bloqueo === 2) {
		document.getElementById("form-registro").style.display = 'none';
	} else if (bloqueo === 1) {
		document.getElementById("form-registro").style.display = 'block';
	}
}

/**
 * Fin
 */



/**
 * Estatus de las gestiones
 */
var listaDeEstatus = [];
var listaDeTotales = [];
var sucursalActual = document.getElementById("nombreSucursal");

function todosLosEstatus(id) {
	$.ajax({
		url: newUrlBase + "todos_los_estatus?idSucursal=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			sucursalActual.textContent = respuesta.lista[0].vc_nombresucursal;
			respuesta.lista.forEach(function(elemento) {
				listaDeEstatus.push(elemento.v_valor);
				listaDeTotales.push(elemento.total_registros);
			});
			// Llamar a la función para crear la gráfica después de obtener los datos
			createChart(listaDeEstatus, listaDeTotales);
		}
	});
}
/**
 * Fin
 */



/**
 * Gráfica
 */
var myChart;

function createChart(labels, data) {
	// Datos de ejemplo para la gráfica
	var data = {
		labels: labels,
		datasets: [{
			label: labels,
			backgroundColor: 'rgba(75, 192, 192, 0.2)',
			borderColor: 'rgba(75, 192, 192, 1)',
			borderWidth: 1,
			data: data
		}]
	};

	// Configuración de la gráfica
	var options = {
		scales: {
			y: {
				beginAtZero: true
			}
		}
	};

	// Crear la instancia de la gráfica de barras
	var ctx = document.getElementById('myChart').getContext('2d');
	myChart = new Chart(ctx, {
		type: 'bar',
		data: data,
		options: options
	});
}
/**
 * Fin
 */



/**
 * Modal de alertas
 */
function cerrarModalAlertas(valor) {
	$('#modal-' + valor).modal('hide');
	// Limpiar datos de la gráfica
	listaDeEstatus = [];
	listaDeTotales = [];
	// Destruir la instancia de la gráfica
	if (myChart) {
		myChart.destroy();
	}
}

function mostrarAlerta() {
	$('#modal-alerta').modal('show');
	todosLosEstatus("");
}

document.addEventListener('click', function(event) {
	var modal = document.getElementById('modal-alerta');
	// Verificar si el clic no está dentro de la modal
	if (modal && !modal.contains(event.target)) {
		// Limpiar datos de la gráfica
		listaDeEstatus = [];
		listaDeTotales = [];
		// Destruir la instancia de la gráfica
		if (myChart) {
			myChart.destroy();
		}
	}
});
/**
 * Fin
 */



/**
 * Obtener la sucursal del usuario logueado
 */
function obtenerSucursal() {
	$.ajax({
		url: newUrlBase + "obtener_sucursal_empleado",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log(respuesta.lista);
			respuesta.lista.forEach(function(elemento) {
				listaDeEstatus.push(elemento.v_valor);
				listaDeTotales.push(elemento.total_registros);
			});

			// Llamar a la función para crear la gráfica después de obtener los datos
			createChart();
		}
	});
}
/**
 * Fin
 */



/**
 * Buscar sucursal
 */
var idSucursal; // Variable para almacenar el valor del campo de búsqueda

function buscarSucursales() {
	// Limpiar datos de la gráfica
	listaDeEstatus = [];
	listaDeTotales = [];
	// Destruir la instancia de la gráfica
	if (myChart) {
		myChart.destroy();
	}
	var searchSucursal = document.getElementById('buscarSucursal').value;
	$.ajax({
		url: newUrlBase + "buscar_sucursal?sucursal=" + searchSucursal,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionSucursal = document.getElementById('opcionSucursal');
			opcionSucursal.innerHTML = '';
			respuesta.sucursal.forEach(function(sucursal) {
				var option = document.createElement('option');
				option.value = sucursal.vc_nombresucursal;
				opcionSucursal.appendChild(option);
				console.log(sucursal.pk_id_sucursal);
				console.log(sucursal.vc_nombresucursal);
			});

			if (respuesta.sucursal[0]) {
				idSucursal = respuesta.sucursal[0].pk_id_sucursal; // Aquí asumo que deseas guardar el primer valor de pk_id_sucursal                
			}

			// Llamar a la función todosLosEstatus con idSucursal
			todosLosEstatus(idSucursal);
		}
	});
}

var searchSucursal = document.getElementById('buscarSucursal');
searchSucursal.addEventListener('input', function() {
	buscarSucursales(); // Llamar a la función buscarSucursales cada vez que se ingrese un nuevo valor en el input de búsqueda
});

/**
 * Fin
 */