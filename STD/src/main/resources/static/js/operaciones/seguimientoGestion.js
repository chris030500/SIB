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

var sucursalSeleccionadaS = document.getElementById("idSucursalInicial");
var permisoUsuario = document.getElementById("idPermisoUsuario");
window.onload = function() {
	mostrarAlerta();
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

	var buscar = sucursalSeleccionadaS.value
	$.ajax({
		url: newUrlBase + "buscar_cliente_activo_sucursal?idSucursal=" + buscar,
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

	if (sucursalSeleccionadaS.value != 0) {


		if (condicion) {
			servicioURL = "parametros_seguimiento?nombre=" + parametroNombre + "&oferta=" + parametroOferta + "&referencia=" + parametroReferencia + "&sucursal=" + sucursalSeleccionadaS.value
		} else {
			servicioURL = "lista_seguimiento?sucursal=" + sucursalSeleccionadaS.value + "&permiso=" + permisoUsuario.value;
		}

		Swal.fire({
			title: 'Consultando informacion..',
			html: 'Espere Por Favor...',
			allowOutsideClick: false,
			showConfirmButton: false,
			onBeforeOpen: () => {
				Swal.showLoading();
			}
		});

		$.ajax({
			url: newUrlBase + servicioURL,
			type: "GET",
			dataType: "JSON",
			async: true, // Configurar la llamada AJAX como asíncrona
			success: function(respuesta) {

				if (respuesta.lista == null) {
					swal({
						text: respuesta.error,
						icon: 'error',
						buttons: ["Cancelar", "Confirmar"],
					}).then(resultado => {
						if (resultado) {
							llenarTablaSeguimiento(false);
						}
					});

				} else {
					Swal.close();
					console.log(respuesta);
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
							opt.fechainicio,
							opt.fechafin,
							opt.v_valor,
							opt.vc_nombresucursal,
							opt.vc_usuario,
							btnEditar
						]).draw(false);
					});

				}
			},
			error: function(error) {
				//console.log(error.error);
			}
		});

	} else {
		swal("No cuenta con los permisos de consulta.", {
			closeOnClickOutside: false,
			icon: "error",
		});

		document.getElementById("filtroSeguimiento").style.display = "none";
	}
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
let selectPart = [];
let inputReferencia = document.getElementById("referencia");
let inputRelacion = document.getElementById("buscar-relacion");
let inputDescripcion = document.getElementById("descripcion-gestion");
let inputFechaCita = document.getElementById("fecha-cita");
let inputFechaGestion = document.getElementById("fecha-gestion");
let inputAccionRealizar = document.getElementById("accionRealizar");
let inputTelfono = document.getElementById("telefono");

let validarRelacion = document.getElementById("validar-relacion");
let validarDescripcion = document.getElementById("validar-descripcion");
let validarFechaCita = document.getElementById("validar-fechaCita");
let validarFechaGestion = document.getElementById("validar-fechaGestion");
let validarTelefono = document.getElementById("validar-telefono");



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

		if (selectPart[0] == "LLAMADA") {
			inputTelfono.value == '' ? validarTelefono.style.display = 'block' : validarTelefono.style.display = 'none';
		}

		if (selectPart[0] == "LLAMADA") {
			if (inputRelacion.value != '' && inputDescripcion.value != '' && inputFechaCita.value != '' && inputFechaGestion.value != '' && inputTelfono.value != '') {
				const dto = {
					asignacionOfertas: idOferta,
					referencia: inputReferencia.textContent,
					relacionCodigos: datosRelacion.pk_id_relacion_codigo,
					descripcion: inputDescripcion.value,
					fechaCita: inputFechaCita.value,
					fechaGestion: inputFechaGestion.value,
					telefonoLlamada: inputTelfono.value
				};
				//console.log(dto);
				registrarGestion(dto);
			}
		} else {
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
		}

	} else {
		inputRelacion.value == '' ? validarRelacion.style.display = 'block' : validarRelacion.style.display = 'none';
		inputDescripcion.value == '' ? validarDescripcion.style.display = 'block' : validarDescripcion.style.display = 'none';
		inputFechaGestion.value == '' ? validarFechaGestion.style.display = 'block' : validarFechaGestion.style.display = 'none';

		if (selectPart[0] == "LLAMADA") {
			inputTelfono.value == '' ? validarTelefono.style.display = 'block' : validarTelefono.style.display = 'none';
		}

		if (selectPart[0] == "LLAMADA") {

			if (inputRelacion.value != '' && inputDescripcion.value != '' && inputFechaGestion.value != '' && inputTelfono.value != '') {
				const dto = {
					asignacionOfertas: idOferta,
					referencia: inputReferencia.textContent,
					relacionCodigos: datosRelacion.pk_id_relacion_codigo,
					descripcion: inputDescripcion.value,
					fechaGestion: inputFechaGestion.value,
					telefonoLlamada: inputTelfono.value
				};
				//console.log(dto);
				registrarGestion(dto);
			}
		} else {
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
	console.log(dto)
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
					llenarTablaSeguimiento(false);
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
	limpiarVariablesModal();
}

function limpiarVariablesModal() {
	const campos = ['buscar-relacion', 'descripcion-gestion', 'fecha-cita', 'fecha-gestion', 'accionRealizar', 'telefono'];
	const alertas = ['validar-relacion', 'validar-descripcion', 'validar-fechaCita', 'validar-fechaGestion', 'validar-telefono'];
	campos.forEach(campo => {
		document.getElementById(`${campo}`).value = '';
	});
	alertas.forEach(alerta => {
		document.getElementById(`${alerta}`).style.display = 'none';
	});
	document.getElementById("vistaAccionRealizar").style.display = "none";
	document.getElementById("vistaTelfonoLlamada").style.display = "none";
	document.getElementById("accionRealizar").textContent = '';
	//searchSucursal.value = '';
	if (document.getElementById('buscarSucursal')) {
		document.getElementById('buscarSucursal').value = '';
	}
	if (document.getElementById('buscarSucursalVencidas')) {
		document.getElementById('buscarSucursalVencidas').value = '';
	}

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
let datosRelacionModal;

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
	selectPart = selectedValue.split(" ");

	console.log(selectPart[0]);
	if (almacen.listaRelaciones && almacen.listaRelaciones.length > 0) {
		//
		datosRelacion = almacen.listaRelaciones.find(item => item.relacion_codigos == selectedValue);
		//console.log(datosRelacion);
		if (datosRelacion != undefined) {
			document.getElementById("vistaAccionRealizar").style.display = "block";
			inputAccionRealizar.textContent = datosRelacion.v_valor;
			if (selectPart[0] == "LLAMADA") {
				document.getElementById("vistaTelfonoLlamada").style.display = "block";
			} else {
				document.getElementById("vistaTelfonoLlamada").style.display = "none";
			}
		} else {
			document.getElementById("vistaAccionRealizar").style.display = "none";
			document.getElementById("vistaTelfonoLlamada").style.display = "none";
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
		var btnEditar = "<button style='margin-right: 10px; background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary btn-icon btn-editar'"
			+ " onclick='editarSegGestion("
			+ opt.pk_id_seguimiento_gestion
			+ ")' id='btnEditar"
			+ opt.pk_id_seguimiento_gestion
			+ "'><i class='bi bi-pencil'></i></button>";

		var btnCancelar = "<button style='margin-right: 10px;' class='btn btn-danger btn-icon btn-cancelar'"
			+ " onclick='cancelarSegGestion("
			+ opt.pk_id_seguimiento_gestion
			+ ")' id='btnCancelar"
			+ opt.pk_id_seguimiento_gestion
			+ "'><i class='bi bi-x'></i></button>";

		var btns = "<div class='btn-group'>" + btnEditar + btnCancelar + "</div>";

		tablaGestion.row.add([
			opt.relacionCodigosModel.accionRealizar.v_valor,
			opt.relacionCodigosModel.codigoAccionModel.vc_descripcion + "(" + opt.relacionCodigosModel.codigoAccionModel.vc_codigo + ")",
			opt.relacionCodigosModel.codigoResultadoModel.vc_descripcion + "(" + opt.relacionCodigosModel.codigoResultadoModel.vc_codigo + ")",
			opt.vc_descripcion,
			opt.dt_fecha_registro,
			opt.dt_fecha_gestion_realizada,
			btns
		]).draw(false);

	});
}
/**
 * Fin
 */


/**
 * Mostrar datos de la gestión para poder editar
 */

let inputFechaCitaModal = document.getElementById("fecha-cita-modal");
let inputFechaGestionModal = document.getElementById("fecha-gestion-modal");
let inputRelacionModal = document.getElementById("buscar-relacion-modal");
let inputDescripcionModal = document.getElementById("descripcion-gestion-modal");
let inputAccionRealizarModal = document.getElementById("accionRealizarModal");
let inputTelefonoLlamadaModal = document.getElementById("telefono-modal");
let idGestion;

function editarSegGestion(id) {
	idGestion = id;
	$.ajax({
		url: newUrlBase + "datos_gestion?idGestion=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			$('#modal-editar').modal('show');
			// Ahora asignamos los valores a los elementos del modal
			datosRelacionModal = almacen.listaRelaciones.find(item => item.pk_id_relacion_codigo == respuesta.model.relacionCodigosModel.pk_id_relacion_codigo);
			let relacionPart = datosRelacionModal.relacion_codigos.split(" ")
			console.log(parseInt(respuesta.model.i_telefono))
			if (relacionPart[0] == 'LLAMADA') {
				document.getElementById("vistaTelfonoLlamadaModal").style.display = "block";
				inputTelefonoLlamadaModal.value = respuesta.model.i_telefono;
			} else {
				document.getElementById("vistaTelfonoLlamadaModal").style.display = "none";
				inputTelefonoLlamadaModal.value = '';
			}
			if (datosRelacionModal != undefined) {
				document.getElementById("vistaAccionRealizarModal").style.display = "block";
				inputAccionRealizarModal.textContent = datosRelacionModal.v_valor;
			} else {
				document.getElementById("vistaAccionRealizarModal").style.display = "none";
				inputAccionRealizarModal.textContent = '';
			}
			//
			if (datosRelacionModal && datosRelacionModal.v_valor == "MOSTRAR CALENDARIO") {
				document.getElementById('fecha-cita-modal').disabled = false;
			} else {
				document.getElementById('fecha-cita-modal').disabled = true;
				inputFechaCitaModal.value = '';
			}
			inputRelacionModal.value = datosRelacionModal.relacion_codigos;
			inputFechaGestionModal.value = respuesta.model.dt_fecha_gestion_realizada;
			inputFechaCitaModal.value = respuesta.model.dt_fecha_cita;
			inputDescripcionModal.value = respuesta.model.vc_descripcion
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
var sucursalCitaVencidas = document.getElementById("nombreSucursalCitaVencidas");
var nombreSucursalSeguimiento = document.getElementById("nombreSucursalSeguimiento");


var idBusquedaInicial = 0;

function todosLosEstatus() {
	$.ajax({
		url: newUrlBase + "todos_los_estatus?idSucursal=" + sucursalSeleccionadaS.value,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			document.getElementById("NombreSucursalS").value = respuesta.lista[0].vc_nombresucursal;
			sucursalActual.textContent = document.getElementById("NombreSucursalS").value;
			sucursalCitaVencidas.textContent = document.getElementById("NombreSucursalS").value;
			nombreSucursalSeguimiento.textContent = document.getElementById("NombreSucursalS").value;
			sucursalSeleccionadaS.value = respuesta.lista[0].i_id_sucursal;
			//idBusquedaInicial = respuesta.lista[0].i_id_sucursal;
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
	// Crear arrays para almacenar los labels y data
	var etiquetas = [];
	var datos = [];

	// Llenar los arrays con los datos correspondientes
	for (var i = 0; i < data.length; i++) {
		etiquetas.push(labels[i]);
		datos.push(data[i]);
	}

	// Crear un array de objetos para almacenar los conjuntos de datos
	var datasetsA = [];

	// Iterar sobre los datos y crear un conjunto de datos para cada etiqueta
	for (var i = 0; i < etiquetas.length; i++) {
		var dataset = {
			label: etiquetas[i], // Etiqueta para el conjunto de datos
			data: [datos[i]],
			backgroundColor: getRandomColor(), // Color de fondo aleatorio
			borderColor: 'rgba(75, 192, 192, 1)', // Color del borde fijo o personalizado
			borderWidth: 1
		};
		datasetsA.push(dataset);
	}
	labels = ["REGISTROS EN GESTION POR SUCURSAL"];
	// Crear el objeto final con la estructura deseada
	var chartData = {
		labels: labels,
		datasets: datasetsA
	};

	// Definir el ancho de la barra
	var barWidth = 30; // Ajusta el valor según tus necesidades
	// Configuración de la gráfica
	var options = {

		scales: {
			x: {
				barThickness: barWidth // Define el ancho de la barra
			},
			y: {
				beginAtZero: true,
				min: 1
			}
		}
	};

	// Crear la instancia de la gráfica de barras
	var ctx = document.getElementById('myChart').getContext('2d');
	myChart = new Chart(ctx, {
		type: 'bar',
		data: chartData,
		options: options
	});
}
/**
 * Fin
 */




// Función para obtener colores aleatorios
function getRandomColor() {
	var letters = '0123456789ABCDEF';
	var color = '#';
	for (var i = 0; i < 6; i++) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}


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
	listaClientes();
	llenarTablaSeguimiento(false);
}

function cerrarModalEditar() {
	$('#modal-editar').modal('hide');
}

function mostrarAlerta() {
	todosLosEstatus();
	$('#modal-alerta').modal('show');
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



var searchSucursal = document.getElementById('buscarSucursal');
searchSucursal.addEventListener('change', function() {
	// Obtener el valor seleccionado del input
	var valorSeleccionado = $(this).val();

	if (valorSeleccionado.trim().length > 0) {
		// Obtener el id asociado al valor seleccionado
		var idSeleccionado = $('#opcionSucursal option[value="' + valorSeleccionado + '"]').attr('id');


		// Limpiar datos de la gráfica
		listaDeEstatus = [];
		listaDeTotales = [];
		// Destruir la instancia de la gráfica
		if (myChart) {
			myChart.destroy();
		}
		// Llamar a la función todosLosEstatus con idSucursal
		sucursalSeleccionadaS.value = idSeleccionado;
		todosLosEstatus();
	}

});


/*var searchSucursal = document.getElementById('buscarSucursal');
searchSucursal.addEventListener('input', function() {
	buscarSucursales(); // Llamar a la función buscarSucursales cada vez que se ingrese un nuevo valor en el input de búsqueda
});*/

/**
 * Fin
 */

var searchInputModal = document.getElementById('buscar-relacion-modal');
searchInputModal.addEventListener('input', buscarRelacion);

searchInputModal.addEventListener('change', function() {
	let selectedValue = searchInputModal.value;
	if (almacen.listaRelaciones && almacen.listaRelaciones.length > 0) {
		//
		datosRelacionModal = almacen.listaRelaciones.find(item => item.relacion_codigos == selectedValue);
		console.log(datosRelacionModal);
		let relacionPart = datosRelacionModal.relacion_codigos.split(" ")
		if (relacionPart[0] == 'LLAMADA') {
			document.getElementById("vistaTelfonoLlamadaModal").style.display = "block";
			inputTelefonoLlamadaModal.value = '';
		} else {
			document.getElementById("vistaTelfonoLlamadaModal").style.display = "none";
			inputTelefonoLlamadaModal.value = '';
		}
		if (datosRelacionModal != undefined) {
			document.getElementById("vistaAccionRealizarModal").style.display = "block";
			inputAccionRealizarModal.textContent = datosRelacionModal.v_valor;
		} else {
			document.getElementById("vistaAccionRealizarModal").style.display = "none";
			inputAccionRealizarModal.textContent = '';
		}
		//
		if (datosRelacionModal && datosRelacionModal.v_valor == "MOSTRAR CALENDARIO") {
			document.getElementById('fecha-cita-modal').disabled = false;
		} else {
			document.getElementById('fecha-cita-modal').disabled = true;
			inputFechaCitaModal.value = '';
		}
	} else {
		document.getElementById('fecha-cita').disabled = false;
		document.getElementById('validar-fechaCita').style.display = 'none';
	}
});


function editarSegGestionG() {

	console.log(datosRelacionModal)

	let inputFechaCitaModal = document.getElementById("fecha-cita-modal");
	let inputFechaGestionModal = document.getElementById("fecha-gestion-modal");
	let inputRelacionModal = document.getElementById("buscar-relacion-modal");
	let inputDescripcionModal = document.getElementById("descripcion-gestion-modal");
	let inputAccionRealizarModal = document.getElementById("accionRealizarModal");
	let inputTelefonoModal = document.getElementById("telefono-modal");

	$.ajax({
		url: newUrlBase + "guardar_gestion?idGestion=" + idGestion + "&fechaCita=" + inputFechaCitaModal.value + "&fechaGestion=" + inputFechaGestionModal.value + "&codigoRelacion=" + datosRelacionModal.pk_id_relacion_codigo + "&descripcion=" + inputDescripcionModal.value + "&telefono=" + inputTelefonoModal.value,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal("Se edito la gestión", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					console.log(respuesta)
					listaDeGestiones()
					cerrarModalEditar()
				});


		}
	});
}

function cancelarSegGestion(gestionID) {
	var tituloAlerta = "";
	var textoAlerta = "";
	var confirmButton = "";


	tituloAlerta = "¿Estás seguro de eliminar este seguimiento?";
	textoAlerta = "¡El seguimiento se eliminara y no sera mostrado en este listado!";
	confirmButton = "Eliminar";

	console.log(newUrlBase)

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
				url: newUrlBase + "inactivar_gestion?idGestion=" + gestionID,
				type: "GET",
				success: function(response) {
					swal(
						"¡Procesado!",
						"El seguimiento ha sido eliminado.",
						"success"
					)
					listaDeGestiones();
				},
				error: function(xhr, status, error) {
					// Aquí puedes manejar cualquier error que ocurra durante la solicitud AJAX
					console.error("Error al enviar los datos: " + error);
				}
			});
		}
	})
}

/**
 * cambios para agregar la tabla de vencidas y citas. 
 * 
 */

function mostrarDiv(divId) {
	// Ocultar todos los divs
	document.querySelectorAll('.tab-pane').forEach(div => {
		div.style.display = "none";
		//div.classList.remove('show', 'active');
	});
	if (divId == 'vencidasCita') {
		listaconsultarCitaVencidos(document.getElementById("idSucursalInicial").value);
	}

	// Mostrar el div seleccionado
	const selectedDiv = document.getElementById(divId);
	selectedDiv.style.display = "block";
}

var searchSucursalVencidas = document.getElementById('buscarSucursalVencidas');
searchSucursalVencidas.addEventListener('change', function() {
	// Obtener el valor seleccionado del input
	var valorSeleccionado = $(this).val();

	if (valorSeleccionado.trim().length > 0) {
		// Obtener el id asociado al valor seleccionado
		var idSelec = $('#opcionSucursalVencidas option[value="' + valorSeleccionado + '"]').attr('id');

		sucursalCitaVencidas.textContent = valorSeleccionado;

		listaconsultarCitaVencidos(idSelec);
	}
});

var buscarClientesSucursal = document.getElementById('buscarSucursalSeguimiento');
buscarClientesSucursal.addEventListener('change', function() {
	// Obtener el valor seleccionado del input
	var valorSeleccionado = $(this).val();

	if (valorSeleccionado.trim().length > 0) {
		// Obtener el id asociado al valor seleccionado
		var idSelec = $('#opcionSucursalVencidas option[value="' + valorSeleccionado + '"]').attr('id');

		nombreSucursalSeguimiento.textContent = valorSeleccionado;

		sucursalSeleccionadaS.value = idSelec;
		listaClientes();
		llenarTablaSeguimiento(false);
	}

});

function listaconsultarCitaVencidos(idSucursalCitaVencidos) {

	if (idSucursalCitaVencidos) {
		document.getElementById("ver-tbl-buscarCita").style.display = "none";
		document.getElementById("ver-buscarCita").style.display = "block";

		$.ajax({
			url: newUrlBase + "buscar_citas_vencidos?idSucursalCitaVencidos=" + idSucursalCitaVencidos,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				console.log(respuesta);
				cargarTablaAlertas(respuesta.listaCitasVencidas);
				document.getElementById("ver-tbl-buscarCita").style.display = "block";
				document.getElementById("ver-buscarCita").style.display = "none";
			}, error: function(error) {
				//console.log(error);
			}
		});
	}


}

function cargarTablaAlertas(datos) {

	var btnConfirmar = "";

	tblAlertas.clear().draw();
	$.each(datos, function(idx, opt) {

		if (opt.i_estatus != 2 && opt.vc_tipo_alerta == 'GESTION CON CITA') {
			btnConfirmar = "<div class='row'><div class='col-sm-6'>"
				+ "<button class='btn btn-success'"
				+ " title='Confirmar Gestión' type='button'"
				+ " onclick='segGestionRealizado("
				+ opt.i_pk_id_seguimiento
				+ ")' id="
				+ opt.i_pk_asignacion
				+ " data-tooltip='Confirmar Gestión'>"
				+ " <i class='fas fa-check-circle'></i></button></div>";
		} else {
			btnConfirmar = "Gestion Realizada"
		}

		tblAlertas.row.add([
			opt.i_cliente,
			opt.vc_nombre_cliente,
			opt.vc_campania,
			opt.vc_clasificacion,
			opt.vc_tipo_alerta,
			opt.vc_estatus,
			opt.vc_descripcion_cita
			, btnConfirmar
		]).draw(false);

	});

}

function segGestionRealizado(gestionID) {
	$.ajax({
		url: newUrlBase + "gestion_realizada?idGestion=" + gestionID,
		type: "GET",
		success: function(response) {
			swal(
				"¡Procesado!",
				"El seguimiento ha sido realizado.",
				"success"
			)
			mostrarDiv('vencidasCita');
		},
		error: function(xhr, status, error) {
			// Aquí puedes manejar cualquier error que ocurra durante la solicitud AJAX
			console.error("Error al enviar los datos: " + error);
		}
	});

}




