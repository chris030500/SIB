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
	listaTelefonos();
	listaCodigosAccion();
	listaCodigosResultado();
	listaDeRelaciones();
}
/**
 * Fin
 */



/**
 * Cambio de tabs
 */
function cambiarContenido(opcion, elemento) {
	// Obtener todos los elementos de pestañas
	var pestanas = document.querySelectorAll('.nav-link');

	// Remover la clase activa de todas las pestañas
	pestanas.forEach(function(item) {
		item.classList.remove('active');
	});

	// Agregar la clase activa a la pestaña clicada
	elemento.classList.add('active');

	// Puedes realizar acciones basadas en la opción seleccionada
	// En este ejemplo, simplemente mostramos un mensaje diferente
	var tabTel = document.getElementById('telefonos');
	var tabCod = document.getElementById('codigos');

	if (opcion === 'telefonos') {
		tabTel.style.display = "block";
		tabCod.style.display = "none"
	} else if (opcion === 'codigos') {
		tabTel.style.display = "none";
		tabCod.style.display = "block";
	}
}

/**
 * Fin
 */



/**
 * Formulario telefonos
 */
var formTelefono = document.getElementById("telefono");
var formExtension = document.getElementById("extension");
var formComentarios = document.getElementById("comentarios");
var formSucursal = document.getElementById("searchInput");
var formCorreo = document.getElementById("correoElectronico");
//
var errorTelefono = document.getElementById("errorTelefono");
var soloTelefono = document.getElementById("soloTelefono");
var errorExtension = document.getElementById("errorExtension");
var soloExtension = document.getElementById("soloExtension");
var errorSucursal = document.getElementById("errorSucursal");
var minimoUno = document.getElementById("minimoUno");

// Agrega un evento de entrada al campo de texto
formTelefono.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	errorTelefono.style.display = "none";
	soloTelefono.style.display = "none";
});
formExtension.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	errorExtension.style.display = "none";
	soloExtension.style.display = "none";
});
formSucursal.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	errorSucursal.style.display = "none";
});
formCorreo.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	minimoUno.style.display = "none";
});

function validarFormTelefonos() {
	// Asignamos los valores a las variables
	var telefono = $('#telefono').val();
	var extension = $('#extension').val();
	var comentarios = $('#comentarios').val();
	var sucursal = $('#searchInput').val();
	// Asignamos las validaciones
	var pattern = /^[0-9]*$/;

	telefono == "" ? errorTelefono.style.display = "block" : errorTelefono.style.display = "none";
	extension == "" ? errorExtension.style.display = "block" : errorExtension.style.display = "none";
	sucursal == "" ? errorSucursal.style.display = "block" : errorSucursal.style.display = "none";
	soloTelefono.style.display = pattern.test(telefono) && telefono.length === 10 ? "none" : "block";
	soloExtension.style.display = pattern.test(extension) && extension.length === 3 ? "none" : "block";

	datosChips == "" ? minimoUno.style.display = "block" : minimoUno.style.display = "none";

	// Si los valores son diferentes de vacio
	if (telefono != "" && extension != "" && sucursal != "") {
		if (pattern.test(telefono) && telefono.length === 10) {
			if (pattern.test(extension) && extension.length === 3) {
				// Se llena el DTO
				const dto = {
					telefono: telefono,
					extension: extension,
					correoElectronico: datosChips,
					comentarios: comentarios,
					nombreSucursal: idSucursal
				};
				agregarTelefono(dto);
			}
		}
	}
}



var formTelefonoE = document.getElementById("telefonoE");
var formExtensionE = document.getElementById("extensionE");
var formComentariosE = document.getElementById("comentariosE");
var formCorreoE = document.getElementById("correoElectronicoE");
//
var alertaTel = document.getElementById("alertaTel");
var alertaTel1 = document.getElementById("alertaTel1");
var alertaExt = document.getElementById("alertaExt");
var alertaExt1 = document.getElementById("alertaExt1");

// Agrega un evento de entrada al campo de texto
formTelefonoE.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	alertaTel.style.display = "none";
	alertaTel1.style.display = "none";
});
formExtensionE.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	alertaExt.style.display = "none";
	alertaExt1.style.display = "none";
});
formCorreoE.addEventListener("input", function() {
	// Oculta el elemento <p> cuando se está escribiendo en el campo de texto
	minimoUnoE.style.display = "none";
});

function validarEditarTel(id) {
	// Asignamos los valores a las variables
	var telefono = $('#telefonoE').val();
	var extension = $('#extensionE').val();
	var comentarios = $('#comentariosE').val();
	// Asignamos las validaciones
	var pattern = /^[0-9]*$/;

	telefono == "" ? alertaTel.style.display = "block" : alertaTel.style.display = "none";
	extension == "" ? alertaExt.style.display = "block" : alertaExt.style.display = "none";
	alertaTel1.style.display = pattern.test(telefono) && telefono.length === 10 ? "none" : "block";
	alertaExt1.style.display = pattern.test(extension) && extension.length === 3 ? "none" : "block";

	datosChipsE == "" ? minimoUnoE.style.display = "block" : minimoUnoE.style.display = "none";

	// Si los valores son diferentes de vacio
	if (telefono != "" && extension != "") {
		if (pattern.test(telefono) && telefono.length === 10) {
			if (pattern.test(extension) && extension.length === 3) {
				// Se llena el DTO
				const dto = {
					telefono: telefono,
					extension: extension,
					correoElectronico: datosChipsE,
					comentarios: comentarios
				};
				editarTelefono(id, dto);
				// Si los valores estan vacios    
			}
		}
	}
}

function limpiarFormTelefonos() {
	document.getElementById('telefono').value = '';
	document.getElementById('extension').value = '';
	document.getElementById('searchInput').value = '';
	document.getElementById('comentarios').value = '';
	document.getElementById('correoElectronico').value = '';
	errorTelefono.style.display = "none";
	errorExtension.style.display = "none";
	errorSucursal.style.display = "none";
	soloTelefono.style.display = "none";
	soloExtension.style.display = "none";
	document.getElementById('minimoUno').style.display = "none";
	document.getElementById('soloCorreos').style.display = "none";
}

// Agregar evento al documento para detectar clics
document.addEventListener('click', function(event) {
	var modal = document.getElementById('telefonoModal'); // Reemplaza 'miModal' con el ID de tu modal

	// Verificar si el clic no está dentro de la modal
	if (modal && !modal.contains(event.target)) {
		limpiarFormTelefonos();
	}
});
/**
 * Fin
 */



/**
 * Buscar sucursal
 */
var idSucursal; // Variable para almacenar el valor del campo de búsqueda

function handleSearch() {
	var searchTerm = document.getElementById('searchInput').value;
	$.ajax({
		url: newUrlBase + "buscar_sucursal?sucursal=" + searchTerm,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionesDatalist = document.getElementById('opciones');
			opcionesDatalist.innerHTML = '';
			respuesta.sucursal.forEach(function(sucursal) {
				var option = document.createElement('option');
				option.value = sucursal.vc_nombresucursal;
				opcionesDatalist.appendChild(option);
			});
		}
	});
}

var searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', handleSearch);

searchInput.addEventListener('input', function() {
	idSucursal = this.value;
	//console.log(idSucursal);
});
/**
 * Fin
 */



/**
 * Lista de telefonos
 */
function listaTelefonos() {
	$.ajax({
		url: newUrlBase + "lista_telefonos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			vistaTablaTel.clear().draw();
			$.each(respuesta.telefonos, function(idx, opt) {

				var btnEditar = "<div class='row'><div class='col-sm-6'>"
					+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='abrirTelefono("
					+ opt.pk_id_telefono
					+ ")' id="
					+ opt.pk_id_telefono
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var btnEstatus = "<div class='col-sm-6'><button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Vigencia' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='vigenciaTelefono("
					+ opt.pk_id_telefono + ', ' + opt.i_estatus
					+ ")' id="
					+ opt.pk_id_telefono
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-unlock'></i></button></div></div>";

				var vigente = opt.i_estatus == 1 ? "Si" : "No";

				var validarCorreo = opt.vc_correo_electronico.length > 30 ? opt.vc_correo_electronico.substring(0, 30) + '...' : opt.vc_correo_electronico;
				var validarDireccion = opt.fk_id_sucursal.vc_direccioncompleta.length > 30 ? opt.fk_id_sucursal.vc_direccioncompleta.substring(0, 30) + '...' : opt.fk_id_sucursal.vc_direccioncompleta;

				vistaTablaTel.row.add([
					opt.pk_id_telefono,
					opt.fk_id_sucursal.vc_nombresucursal,
					validarDireccion,
					opt.vc_telefono,
					opt.vc_extension,
					validarCorreo,
					opt.vc_comentarios,
					vigente,
					btnEditar + btnEstatus
				]).draw(false);
			});

			//console.log(telefonos);
		},
		error: function(error) {
			//console.log(error);
		}
	});
}
/**
 * Fin
 */



/**
 * Registro de telefono
 */
function agregarTelefono(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "registrar_telefono",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('telefonoModal').style.display = 'none';
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaTelefonos();
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
 * Cerrar modal
 */
// Función para cerrar el modal
function cerrarModal() {
	$('#editarTelModal').modal('hide');
	limpiarAlertas();
}

function limpiarAlertas() {
	alertaTel.style.display = "none";
	alertaTel1.style.display = "none";
	alertaExt.style.display = "none";
	alertaExt1.style.display = "none";
	document.getElementById('correoElectronicoE').value = '';
	document.getElementById('minimoUnoE').style.display = "none";
	document.getElementById('soloCorreosE').style.display = "none";
}

document.addEventListener('click', function(event) {
	var modal = document.getElementById('editarTelModal'); // Reemplaza 'miModal' con el ID de tu modal

	// Verificar si el clic no está dentro de la modal
	if (modal && !modal.contains(event.target)) {
		limpiarAlertas();
	}
});
/**
 * Fin
 */



/** 
 * Editar Telefono
 */
function editarTelefono(id, dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "editar_telefono?idTelefono=" + id,
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('editarTelModal').style.display = 'none';
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaTelefonos();
				});
		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de actualizar los datos", {
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
 * Buscar teléfono
 */
var idTel;

function abrirTelefono(id) {
	contenedorChipsE.innerHTML = "";
	var sucursal = document.getElementById('sucursalE');
	var direccion = document.getElementById('direccionE');
	var telefono = document.getElementById('telefonoE');
	var extension = document.getElementById('extensionE');
	// var correo = document.getElementById('correoElectronicoE');
	var comentarios = document.getElementById('comentariosE');
	idTel = id;

	$.ajax({
		url: newUrlBase + "buscar_telefono?idTelefono=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// Mostrar el modal de editar
			$('#editarTelModal').modal('show');

			sucursal.value = respuesta.telefono.fk_id_sucursal.vc_nombresucursal;
			direccion.value = respuesta.telefono.fk_id_sucursal.vc_direccioncompleta;
			telefono.value = respuesta.telefono.vc_telefono;
			extension.value = respuesta.telefono.vc_extension;
			// correo.value = respuesta.telefono.vc_correo_electronico;
			vistaDeCorreos(respuesta.telefono.vc_correo_electronico);
			comentarios.value = respuesta.telefono.vc_comentarios;

		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de mostrar la información", {
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
 * Cambiar estatus teléfono
 */
// Función para avisar sobre la actualización del estatus
function vigenciaTelefono(id, vigente) {
	var estatus = null;
	//console.log(id);
	estatus = vigente == 1 ? 0 : 1;
	//console.log(estatus);
	swal({
		text: '¿Cambiar la vigencia del teléfono?',
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				cambiarEstatusTelefono(id, estatus);
			} else { }
		});
}

function cambiarEstatusTelefono(id, estatus) {
	$.ajax({
		url: newUrlBase + "vigencia_telefono?idTelefono=" + id + "&estatus=" + estatus,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaTelefonos();
				});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de actualizar los datos",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */


// ******************************************************************************************** //
/**
 * Lista de códigos de acción
 */
function listaCodigosAccion() {
	$.ajax({
		url: newUrlBase + "lista_codigosAccion",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			vistaTablaCA.clear().draw();
			$.each(respuesta.lista, function(idx, opt) {

				var btnEditar = "<div class='row'><div class='col-sm-2'></div><div class='col-sm-4'>"
					+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='abrirAccion("
					+ opt.pk_id_codigo_accion
					+ ")' id="
					+ opt.pk_id_codigo_accion
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var btnEstatus = "<div class='col-sm-4'><button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Estatus' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='estatusAccion("
					+ opt.pk_id_codigo_accion + ', ' + opt.i_estatus
					+ ")' id="
					+ opt.pk_id_codigo_accion
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-unlock'></i></button></div><div class='col-sm-2'></div>";

				var estatus = opt.i_estatus == 1 ? "Activo" : "Inactivo";

				vistaTablaCA.row.add([
					opt.pk_id_codigo_accion,
					opt.vc_codigo,
					opt.vc_descripcion,
					estatus,
					btnEditar + btnEstatus
				]).draw(false);
			});

			//console.log(telefonos);
		},
		error: function(error) {
			//console.log(error);
		}
	})
}
/**
 * Fin
 */



/**
 * Formulario código accion
 */
function limpiarFormAccion() {
	document.getElementById('nombreAccion').value = '';
	document.getElementById('descripcionAccion').value = '';
}

// Función para validar el formulario
function validarFormAccion() {
	// Asignamos los valores a las variables
	var nombreAccion = $('#nombreAccion').val();
	var descripcionAccion = $('#descripcionAccion').val();
	// Si los valores son diferentes de vacio
	if (nombreAccion != "" && descripcionAccion != "") {
		// Se llena el DTO
		const dto = {
			codigo: nombreAccion,
			descripcion: descripcionAccion
		};
		agregarAccion(dto);
		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function agregarAccion(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "registro_codigosAccion",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('modal-codigoAccion').style.display = 'none';
			$('#modal-codigoAccion').modal('hide');
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaCodigosAccion();
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

function cerrarModalAccion() {
	$('#modalEditar-codigoAccion').modal('hide');
}

function validarEditarAccion(id) {
	// Asignamos los valores a las variables
	var nombreAccionE = $('#nombreAccionE').val();
	var descripcionAccionE = $('#descripcionAccionE').val();
	// Si los valores son diferentes de vacio
	if (nombreAccionE != "" && descripcionAccionE != "") {
		// Se llena el DTO
		const dto = {
			codigo: nombreAccionE,
			descripcion: descripcionAccionE
		};

		editarAccion(id, dto);

		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function editarAccion(id, dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "editar_codigoAccion?idCodigoAccion=" + id,
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('modalEditar-codigoAccion').style.display = 'none';
			$('#modalEditar-codigoAccion').modal('hide');
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaCodigosAccion();
				});
		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de actualizar los datos", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}

var idAccion;

function abrirAccion(id) {
	var nombreAccionE = document.getElementById('nombreAccionE');
	var descripcionAccionE = document.getElementById('descripcionAccionE');
	idAccion = id;

	$.ajax({
		url: newUrlBase + "buscar_codigoAccion?idCodigoAccion=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// Mostrar el modal de editar
			$('#modalEditar-codigoAccion').modal('show');

			nombreAccionE.value = respuesta.codigoAccion.vc_codigo;
			descripcionAccionE.value = respuesta.codigoAccion.vc_descripcion;

		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de mostrar la información", {
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
 * Cambiar estatus código de acción
 */
// Función para avisar sobre la actualización del estatus
function estatusAccion(id, valor) {
	var estatus = null;
	//console.log(id);
	estatus = valor == 1 ? 0 : 1;
	//console.log(estatus);
	swal({
		text: '¿Cambiar el estatus del código de acción?',
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				cambiarEstatusAccion(id, estatus);
			} else { }
		});
}

function cambiarEstatusAccion(id, estatus) {
	$.ajax({
		url: newUrlBase + "estatus_codigoAccion?idCodigoAccion=" + id + "&estatus=" + estatus,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaCodigosAccion();
				});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de actualizar los datos",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */



// ******************************************************************************************** //
/**
 * Lista de códigos de resultado
 */
function listaCodigosResultado() {
	$.ajax({
		url: newUrlBase + "lista_codigosResultado",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			vistaTablaCR.clear().draw();
			$.each(respuesta.lista, function(idx, opt) {

				var btnEditar = "<div class='row'><div class='col-sm-2'></div><div class='col-sm-4'>"
					+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='abrirResultado("
					+ opt.pk_id_codigo_resultado
					+ ")' id="
					+ opt.pk_id_codigo_resultado
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var btnEstatus = "<div class='col-sm-4'><button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Estatus' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='estatusResultado("
					+ opt.pk_id_codigo_resultado + ', ' + opt.i_estatus
					+ ")' id="
					+ opt.pk_id_codigo_resultado
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-unlock'></i></button></div><div class='col-sm-2'></div>";

				var estatus = opt.i_estatus == 1 ? "Activo" : "Inactivo";

				vistaTablaCR.row.add([
					opt.pk_id_codigo_resultado,
					opt.vc_codigo,
					opt.vc_descripcion,
					estatus,
					btnEditar + btnEstatus
				]).draw(false);
			});

			//console.log(respuesta.lista);
		},
		error: function(error) {
			//console.log(error);
		}
	})
}
/**
 * Fin
 */



/**
 * Formulario código resultado
 */
function limpiarFormResultado() {
	document.getElementById('nombreResultado').value = '';
	document.getElementById('descripcionResultado').value = '';
}

// Función para validar el formulario
function validarFormResultado() {
	// Asignamos los valores a las variables
	var nombreResultado = $('#nombreResultado').val();
	var descripcionResultado = $('#descripcionResultado').val();
	// Si los valores son diferentes de vacio
	if (nombreResultado != "" && descripcionResultado != "") {
		// Se llena el DTO
		const dto = {
			codigo: nombreResultado,
			descripcion: descripcionResultado
		};
		agregarResultado(dto);
		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function agregarResultado(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "registro_codigoResultado",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('modal-codigoResultado').style.display = 'none';
			$('#modal-codigoResultado').modal('hide');
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaCodigosResultado();
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

function cerrarModalResultado() {
	$('#modalEditar-codigoResultado').modal('hide');
}

function validarEditarResultado(id) {
	// Asignamos los valores a las variables
	var nombreResultadoE = $('#nombreResultadoE').val();
	var descripcionResultadoE = $('#descripcionResultadoE').val();
	// Si los valores son diferentes de vacio
	if (nombreResultadoE != "" && descripcionResultadoE != "") {
		// Se llena el DTO
		const dto = {
			codigo: nombreResultadoE,
			descripcion: descripcionResultadoE
		};

		editarResultado(id, dto);

		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function editarResultado(id, dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "editar_codigoResultado?idCodigoResultado=" + id,
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('modalEditar-codigoResultado').style.display = 'none';
			$('#modalEditar-codigoResultado').modal('hide');
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaCodigosResultado();
				});
		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de actualizar los datos", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}

var idResultado;

function abrirResultado(id) {
	var nombreResultadoE = document.getElementById('nombreResultadoE');
	var descripcionResultadoE = document.getElementById('descripcionResultadoE');
	idResultado = id;

	$.ajax({
		url: newUrlBase + "buscar_codigoResultado?idCodigoResultado=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// Mostrar el modal de editar
			$('#modalEditar-codigoResultado').modal('show');

			nombreResultadoE.value = respuesta.codigoResultado.vc_codigo;
			descripcionResultadoE.value = respuesta.codigoResultado.vc_descripcion;

		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de mostrar la información", {
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
 * Cambiar estatus código resultado
 */
// Función para avisar sobre la actualización del estatus
function estatusResultado(id, valor) {
	var estatus = null;
	//console.log(id);
	estatus = valor == 1 ? 0 : 1;
	//console.log(estatus);
	swal({
		text: '¿Cambiar el estatus del código de resultado?',
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				cambiarEstatusResultado(id, estatus);
			} else { }
		});
}

function cambiarEstatusResultado(id, estatus) {
	$.ajax({
		url: newUrlBase + "estatus_codigoResultado?idCodigoResultado=" + id + "&estatus=" + estatus,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaCodigosResultado();
				});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de actualizar los datos",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */


// ******************************************************************************************** //

/**
 * Modal para agregar relación
 */
// Función para abrir el modal y ejecutar el método automático
function abrirModalRelacion() {
	$('#modal-agregarRelacion').modal('show');
	catalogoCodigoA();
	catalogoCodigoR();
	catalogoAccionRealizar();
	catalogoBloqueo();
	catalogoGestion();
	catalogoTipoGestion();
}

function cerrarModalRelacionA(elemento) {
	$('#modal-agregarRelacion').modal('hide');
}

function cerrarModalRelacionB(elemento) {
	idRelacion = "";
	$('#modal-editarRelacion').modal('hide');
}
/**
 * Fin
 */



/**
 * Catálogos
 */
function catalogoCodigoA() {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	document.getElementById("selectAccion").innerHTML = '';
	document.getElementById("selectAccion").add(opcion);
	$.ajax({
		url: newUrlBase + "lista_codigosA_activos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionBusqueda = document.getElementById("selectAccion");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id_codigo_accion;
				opcion.text = respuesta.lista[i].vc_descripcion + " (" + respuesta.lista[i].vc_codigo + ")";
				opcionBusqueda.add(opcion);
			}
			opcionBusqueda.addEventListener("change", function() {
				valorCodigoA = opcionBusqueda.value;
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

function catalogoCodigoR() {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	document.getElementById("selectResultado").innerHTML = '';
	document.getElementById("selectResultado").add(opcion);
	$.ajax({
		url: newUrlBase + "lista_codigosR_activos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionBusqueda = document.getElementById("selectResultado");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id_codigo_resultado;
				opcion.text = opcion.text = respuesta.lista[i].vc_descripcion + " (" + respuesta.lista[i].vc_codigo + ")";
				opcionBusqueda.add(opcion);
			}
			opcionBusqueda.addEventListener("change", function() {
				valorCodigoR = opcionBusqueda.value;
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

function catalogoAccionRealizar() {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.selected = true;
	document.getElementById("selectRealizar").innerHTML = '';
	document.getElementById("selectRealizar").add(opcion);
	$.ajax({
		url: newUrlBase + "lista_accionRealizar",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionBusqueda = document.getElementById("selectRealizar");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				opcionBusqueda.add(opcion);
			}
			opcionBusqueda.addEventListener("change", function() {
				valorAccionR = opcionBusqueda.value;
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

function catalogoBloqueo() {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.selected = true;
	document.getElementById("selectBloqueo").innerHTML = '';
	document.getElementById("selectBloqueo").add(opcion);
	$.ajax({
		url: newUrlBase + "lista_bloqueo",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionBusqueda = document.getElementById("selectBloqueo");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				opcionBusqueda.add(opcion);
			}
			opcionBusqueda.addEventListener("change", function() {
				valorBloqueo = opcionBusqueda.value;
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


function catalogoGestion() {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	document.getElementById("selectGestion").innerHTML = '';
	document.getElementById("selectGestion").add(opcion);
	$.ajax({
		url: newUrlBase + "lista_gestionVigente",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionBusqueda = document.getElementById("selectGestion");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				opcionBusqueda.add(opcion);
			}
			opcionBusqueda.addEventListener("change", function() {
				valorAccionR = opcionBusqueda.value;
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


function catalogoTipoGestion() {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	document.getElementById("tipoGestion").innerHTML = '';
	document.getElementById("tipoGestion").add(opcion);
	$.ajax({
		url: newUrlBase + "lista_tipoGestion",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opcionBusqueda = document.getElementById("tipoGestion");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				opcionBusqueda.add(opcion);
			}
			opcionBusqueda.addEventListener("change", function() {
				valorAccionR = opcionBusqueda.value;
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
 * Registrar relación
 */
function validarFormRelacion() {
	var formulario = document.getElementById("formRelacion");
	var valorCodigoA = formulario.querySelector("#selectAccion").value;
	var valorCodigoR = formulario.querySelector("#selectResultado").value;
	var valorAccionR = formulario.querySelector("#selectRealizar").value;
	var valorBloqueo = formulario.querySelector("#selectBloqueo").value;
	var valorGestion = formulario.querySelector("#selectGestion").value;
	var valorTipoGestion = formulario.querySelector("#tipoGestion").value;

	valorAccionR = valorAccionR == "Elige una opción" ? 0 : valorAccionR;
	valorBloqueo = valorBloqueo == "Elige una opción" ? 0 : valorBloqueo;
	valorTipoGestion = valorTipoGestion == "Elige una opción" ? 0 : valorTipoGestion;

	if (valorCodigoA != "Elige una opción" && valorCodigoR != "Elige una opción" && valorGestion != "Elige una opción") {
		//console.log("entró");
		const dto = {
			idCodigoAccion: valorCodigoA,
			idCodigoResultado: valorCodigoR,
			idAccionRealizar: valorAccionR,
			ultimaGestion: valorGestion,
			idBloqueo: valorBloqueo,
			idTipoGes: valorTipoGestion
		}
		// console.log(dto);
		agregarRelacion(dto);
	} else {
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function agregarRelacion(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "agregar_relacionCodigo",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('modal-agregarRelacion').style.display = 'none';
			$('#modal-agregarRelacion').modal('hide');
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaDeRelaciones();
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
 * Lista de relaciones
 */
function listaDeRelaciones() {
	$.ajax({
		url: newUrlBase + "lista_relacionCodigos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			vistaTablaRel.clear().draw();
			$.each(respuesta.lista, function(idx, opt) {
				
				var btnEditar = "<div class='row'><div class='col-sm-6'>"
					+ "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='abrirRelacion("
					+ opt.pk_id_relacion_codigo
					+ ")' id="
					+ opt.pk_id_relacion_codigo
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var btnEstatus = "<div class='col-sm-6'><button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Estatus' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='vigenciaRelacion("
					+ opt.pk_id_relacion_codigo + ', ' + opt.i_estatus
					+ ")' id="
					+ opt.pk_id_relacion_codigo
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-unlock'></i></button></div></div>";

				var vigente = opt.i_estatus == 1 ? "Activo" : "Inactivo";
				var tipo = opt.i_tipo_gestion != null ? opt.i_tipo_gestion.v_valor : "";

				let accionR = opt.accionRealizar != null ? opt.accionRealizar.v_valor : "";
				let listaB = opt.bloqueoListas != null ? opt.bloqueoListas.v_valor : "";

				vistaTablaRel.row.add([
					opt.pk_id_relacion_codigo,
					opt.codigoAccionModel.vc_descripcion + " (" + opt.codigoAccionModel.vc_codigo + ")",
					opt.codigoResultadoModel.vc_descripcion + " (" + opt.codigoResultadoModel.vc_codigo + ")",
					accionR,
					opt.ultimaGestion.v_valor,
					listaB,
					vigente,
					tipo,
					btnEditar + btnEstatus
				]).draw(false);
			});
		},
		error: function(error) {
			//console.log(error);
		}
	});
}
/**
 * Fin
 */


/**
 * Abrir relación para editar
 */
var idRelacion;

function abrirRelacion(id) {
	idRelacion = id;
	var selectCodigoA = document.getElementById("selectAccionE");
	var selectCodigoR = document.getElementById("selectResultadoE");
	var selectAccionR = document.getElementById("selectRealizarE");
	var selectGestion = document.getElementById("selectGestionE");
	var selectBloqueo = document.getElementById("selectBloqueoE");
	var selectTipoGes = document.getElementById("tipoGestionE");
	$.ajax({
		url: newUrlBase + "buscar_relacionCodigo?idRelacion=" + id,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			$('#modal-editarRelacion').modal('show');
			//console.log(respuesta.relacion);
			var relacion = respuesta.relacion;
			catalogoCodigoAe(selectCodigoA, relacion);
			catalogoCodigoRe(selectCodigoR, relacion);
			catalogoRealizarE(selectAccionR, relacion);
			catalogoGestionE(selectGestion, relacion);
			catalogoBloqueoE(selectBloqueo, relacion);
			catalogoTipoGes(selectTipoGes, relacion);
		}, error: function(error) {
			//console.log(error);
			swal("Hubo un error al momento de mostrar la información", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}

function catalogoCodigoAe(select, relacion) {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	select.innerHTML = '';
	select.add(opcion);
	$.ajax({
		url: newUrlBase + "lista_codigosA_activos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id_codigo_accion;
				opcion.text = respuesta.lista[i].vc_descripcion + " (" + respuesta.lista[i].vc_codigo + ")";
				select.add(opcion);
				// Selecciona la sucursal actual
				if (respuesta.lista[i].pk_id_codigo_accion === relacion.codigoAccionModel.pk_id_codigo_accion) {
					opcion.selected = true;
				}
			}
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}

function catalogoCodigoRe(select, relacion) {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	select.innerHTML = '';
	select.add(opcion);
	$.ajax({
		url: newUrlBase + "lista_codigosR_activos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id_codigo_resultado;
				opcion.text = respuesta.lista[i].vc_descripcion + " (" + respuesta.lista[i].vc_codigo + ")";
				select.add(opcion);
				// Selecciona la sucursal actual
				if (respuesta.lista[i].pk_id_codigo_resultado === relacion.codigoResultadoModel.pk_id_codigo_resultado) {
					opcion.selected = true;
				}
			}
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}

function catalogoRealizarE(select, relacion) {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.selected = true;
	select.innerHTML = '';
	select.add(opcion);
	$.ajax({
		url: newUrlBase + "lista_accionRealizar",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				select.add(opcion);
				// Selecciona la sucursal actual
				if (relacion.accionRealizar != null) {
					if (respuesta.lista[i].pk_id === relacion.accionRealizar.pk_id) {
						opcion.selected = true;

						// Aquí obtenemos el valor seleccionado
						var valorSeleccionado = respuesta.lista[i].pk_id;

						if (valorSeleccionado == '78' || valorSeleccionado == '72') {
							document.getElementById('selectBloqueoE').value = "79";
							document.getElementById('selectBloqueoE').disabled = true; // CORRECCIÓN AQUÍ
						}
					}
				}
			}
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}

function catalogoGestionE(select, relacion) {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.disabled = true;
	opcion.selected = true;
	select.innerHTML = '';
	select.add(opcion);
	$.ajax({
		url: newUrlBase + "lista_gestionVigente",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				select.add(opcion);
				// Selecciona la sucursal actual
				if (respuesta.lista[i].pk_id === relacion.ultimaGestion.pk_id) {
					opcion.selected = true;
				}
			}
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}

function catalogoBloqueoE(select, relacion) {
	const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.selected = true;
	select.innerHTML = '';
	select.add(opcion);
	$.ajax({
		url: newUrlBase + "lista_bloqueo",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				select.add(opcion);
				// Selecciona la sucursal actual
				if (relacion.bloqueoListas != null) {
					if (respuesta.lista[i].pk_id === relacion.bloqueoListas.pk_id) {
						opcion.selected = true;
					}
				}
			}
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

function catalogoTipoGes(select, relacion) {
const opcion = document.createElement("option");
	opcion.text = "Elige una opción";
	opcion.selected = true;
	select.innerHTML = '';
	select.add(opcion);
	$.ajax({
		url: newUrlBase + "lista_tipoGes",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				select.add(opcion);
				// Selecciona la sucursal actual
				if (relacion.bloqueoListas != null) {
					if (respuesta.lista[i].pk_id === relacion.i_tipo_gestion.pk_id) {
						opcion.selected = true;
					}
				}
			}
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
 * Editar relación
 */
function validarFormRelacionE() {
	var formulario = document.getElementById("formEditarRelacion");
	var valorCodigoA = formulario.querySelector("#selectAccionE").value;
	var valorCodigoR = formulario.querySelector("#selectResultadoE").value;
	var valorAccionR = formulario.querySelector("#selectRealizarE").value;
	var valorBloqueo = formulario.querySelector("#selectBloqueoE").value;
	var valorGestion = formulario.querySelector("#selectGestionE").value;
	var valorTipoGes = formulario.querySelector("#tipoGestionE").value;

	valorAccionR = valorAccionR == "Elige una opción" ? 0 : valorAccionR;
	valorBloqueo = valorBloqueo == "Elige una opción" ? 0 : valorBloqueo;

	const dto = {
		idCodigoAccion: valorCodigoA,
		idCodigoResultado: valorCodigoR,
		idAccionRealizar: valorAccionR,
		ultimaGestion: valorGestion,
		idBloqueo: valorBloqueo,
		idTipoGes: valorTipoGes
	}

	// console.log(dto);
	editarRelacion(dto);
}

function editarRelacion(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "editar_relacionCodigo?idRelacion=" + idRelacion,
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(respuesta) {
			document.getElementById('modal-editarRelacion').style.display = 'none';
			$('#modal-editarRelacion').modal('hide');
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaDeRelaciones();
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
 * Cambiar estatus teléfono
 */
function vigenciaRelacion(id, vigente) {
	var estatus = null;
	estatus = vigente == 1 ? 0 : 1;
	swal({
		text: '¿Cambiar el estatus de la relación?',
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				cambiarEstatusRelacion(id, estatus);
			} else { }
		});
}

function cambiarEstatusRelacion(id, estatus) {
	$.ajax({
		url: newUrlBase + "estatus_Relacion?idRleacion=" + id + "&estatus=" + estatus,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal(respuesta.mensaje, {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					listaDeRelaciones();
				});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de actualizar los datos",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */

/**
 * Agregar correos
 */
var contenedorChips = document.getElementById('chipsContainer');
var totalChips = 0;
var datosChips = "";

function agregarChip() {
	// Obtener el valor del primer input
	var texto = document.getElementById('correoElectronico').value;

	// Crear un nuevo elemento chip
	var nuevoChip = document.createElement('span');
	nuevoChip.className = 'badge badge-primary mr-2';
	nuevoChip.innerHTML = `
        ${texto}
        <button type="button" class="btn-close" aria-label="Close" onclick="eliminarChip(this)">
            <span aria-hidden="true">&times;</span>
        </button>`;
	nuevoChip.style.color = 'black';

	// Agregar el chip al contenedor
	contenedorChips.appendChild(nuevoChip);

	// Actualizar la variable de cadena
	actualizarDatosChips();

	// Limpiar el input
	document.getElementById('correoElectronico').value = "";

	// Obtener el total de chips en el contenedor
	totalChips = contenedorChips.childElementCount;

	// Imprimir el total de chips en la consola o hacer lo que desees con este valor
	//console.log("Total de chips: " + totalChips);
	//console.log(datosChips);

	// Deshabilitar el botón después de agregar un chip
	document.getElementById('agregarCorreo').disabled = true;
}

function eliminarChip(button) {
	// Obtener el elemento del chip y eliminarlo
	var chip = button.closest('.badge');
	chip.remove();

	// Actualizar la variable de cadena
	actualizarDatosChips();

	// Habilitar el botón después de eliminar un chip si el campo de entrada no está vacío
	verificarInput();
	//console.log(datosChips);
}

function actualizarDatosChips() {
	// Inicializar la variable de cadena
	datosChips = "";
	// Obtener todos los chips
	var chips = contenedorChips.getElementsByClassName('badge');
	// Recorrer los chips y agregar sus textos a la variable de cadena
	for (var i = 0; i < chips.length; i++) {
		// Obtener el texto del chip, quitar caracteres no deseados y espacios en blanco
		var chipText = chips[i].textContent.trim().replace(/[×;\s]/g, '');
		datosChips += chipText;
		if (i < chips.length - 1) {
			datosChips += ";";
		}
	}
}


function verificarInput() {
	var inputValue = document.getElementById('correoElectronico').value;
	var agregarCorreoButton = document.getElementById('agregarCorreo');
	var soloCorreosMessage = document.getElementById('soloCorreos');

	// Verificar si el valor ingresado no es un correo electrónico válido
	if (inputValue.trim() !== "" && !validateEmail(inputValue)) {
		soloCorreosMessage.style.display = 'block';
		agregarCorreoButton.disabled = true;
	} else {
		soloCorreosMessage.style.display = 'none';
		agregarCorreoButton.disabled = inputValue.trim() === "";
	}
}

function validateEmail(email) {
	// Utiliza una expresión regular para verificar el formato del correo electrónico
	var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return emailRegex.test(email);
}

/**
 * Fin
 */


/**
 * Editar correos del telefono
 */
var contenedorChipsE = document.getElementById('chipsContainerE');
var totalChipsE = 0;
var datosChipsE = "";

function agregarChipE() {
	// Obtener el valor del primer input
	var texto = document.getElementById('correoElectronicoE').value;

	// Crear un nuevo elemento chip
	var nuevoChip = document.createElement('span');
	nuevoChip.className = 'badge badge-primary mr-2';
	nuevoChip.innerHTML = `
        ${texto}
        <button type="button" class="btn-close" aria-label="Close" onclick="eliminarChipE(this)">
            <span aria-hidden="true">&times;</span>
        </button>`;
	nuevoChip.style.color = 'black';

	// Agregar el chip al contenedor
	contenedorChipsE.appendChild(nuevoChip);

	// Actualizar la variable de cadena
	actualizarDatosChipsE();

	// Limpiar el input
	document.getElementById('correoElectronicoE').value = "";

	// Obtener el total de chips en el contenedor
	totalChipsE = contenedorChipsE.childElementCount;

	// Imprimir el total de chips en la consola o hacer lo que desees con este valor
	//console.log("Total de chips: " + totalChips);
	//console.log(datosChips);

	// Deshabilitar el botón después de agregar un chip
	document.getElementById('agregarCorreoE').disabled = true;

	console.log(datosChipsE);
	console.log(totalChipsE);
}

function eliminarChipE(button) {
	// Obtener el elemento del chip y eliminarlo
	var chip = button.closest('.badge');
	chip.remove();

	// Actualizar la variable de cadena
	actualizarDatosChipsE();

	// Habilitar el botón después de eliminar un chip si el campo de entrada no está vacío
	verificarInputE();

	totalChipsE = contenedorChipsE.childElementCount;

	//console.log(datosChips);
	console.log(datosChipsE);
	console.log(totalChipsE);
}

function actualizarDatosChipsE() {
	// Inicializar la variable de cadena
	datosChipsE = "";
	// Obtener todos los chips
	var chips = contenedorChipsE.getElementsByClassName('badge');
	// Recorrer los chips y agregar sus textos a la variable de cadena
	for (var i = 0; i < chips.length; i++) {
		// Obtener el texto del chip, quitar caracteres no deseados y espacios en blanco
		var chipText = chips[i].textContent.trim().replace(/[×;\s]/g, '');
		datosChipsE += chipText;
		if (i < chips.length - 1) {
			datosChipsE += ";";
		}
	}
}


function verificarInputE() {
	var inputValue = document.getElementById('correoElectronicoE').value;
	var agregarCorreoButton = document.getElementById('agregarCorreoE');
	var soloCorreosMessage = document.getElementById('soloCorreosE');

	// Verificar si el valor ingresado no es un correo electrónico válido
	if (inputValue.trim() !== "" && !validateEmail(inputValue)) {
		soloCorreosMessage.style.display = 'block';
		agregarCorreoButton.disabled = true;
	} else {
		soloCorreosMessage.style.display = 'none';
		agregarCorreoButton.disabled = inputValue.trim() === "";
	}
}
function vistaDeCorreos(lista) {
	// Usar el método split para dividir la cadena en un array
	var arrayDeRegistros = lista.split(";");
	// Itera sobre el array y crea un chip para cada registro
	arrayDeRegistros.forEach(function(registro) {
		// Crear un nuevo elemento chip
		var nuevoChip = document.createElement('span');
		nuevoChip.className = 'badge badge-primary mr-2';
		nuevoChip.innerHTML = `
        ${registro}
        <button type="button" class="btn-close" aria-label="Close" onclick="eliminarChipE(this)">
            <span aria-hidden="true">&times;</span>
        </button>`;
		nuevoChip.style.color = 'black';
		// Agregar el chip al contenedor
		contenedorChipsE.appendChild(nuevoChip);

		totalChipsE = contenedorChipsE.childElementCount;

		// Actualizar la variable de cadena
		actualizarDatosChipsE();
	});
}

// Seleccionar el elemento select por su ID
const selectElement = document.getElementById('selectRealizarE');
const selectElement2 = document.getElementById('selectBloqueoE');

// Agregar un evento 'change' al elemento select
selectElement.addEventListener('change', function(event) {
	// Obtener el valor seleccionado del select
	const selectedValue = event.target.value;

	if (selectedValue == '78' || selectedValue == '72') {
		selectElement2.value = "79";
		selectElement2.disabled = true; // CORRECCIÓN AQUÍ
	} else {
		selectElement2.value = "''";
		selectElement2.disabled = false; // Asegúrate de desbloquearlo si no cumple la condición
	}
});
/**
 * Fin
 */

// Seleccionar el elemento select por su ID
const selectElement3 = document.getElementById('selectRealizar');
const selectElement4 = document.getElementById('selectBloqueo');

// Agregar un evento 'change' al elemento select
selectElement3.addEventListener('change', function(event) {
	// Obtener el valor seleccionado del select
	const selectedValue = event.target.value;

	if (selectedValue == '78' || selectedValue == '72') {
		selectElement4.value = "79";	
		selectElement4.disabled = true; // CORRECCIÓN AQUÍ
	} else {
		selectElement4.value = "Elige una opción";
		selectElement4.disabled = false; // Asegúrate de desbloquearlo si no cumple la condición
	}
});
/**
 * Fin
 */