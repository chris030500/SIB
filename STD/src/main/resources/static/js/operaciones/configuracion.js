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
		tabCod.style.display = "block"
	}
}

/**
 * Fin
 */



/**
 * Formulario telefonos
 */
// Función para validar el formulario
function validarFormTelefonos() {
	// Asignamos los valores a las variables
	var telefono = $('#telefono').val();
	var extension = $('#extension').val();
	var comentarios = $('#comentarios').val();
	// Si los valores son diferentes de vacio
	if (telefono != "" && extension != "" && comentarios != "" && idSucursal != "") {
		// Se llena el DTO
		const dto = {
			telefono: telefono,
			extension: extension,
			comentarios: comentarios,
			nombreSucursal: idSucursal
		};

		agregarTelefono(dto);

		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function validarEditarTel(id) {
	// Asignamos los valores a las variables
	var telefono = $('#telefonoE').val();
	var extension = $('#extensionE').val();
	var comentarios = $('#comentariosE').val();
	// Si los valores son diferentes de vacio
	if (telefono != "" && extension != "" && comentarios != "") {
		// Se llena el DTO
		const dto = {
			telefono: telefono,
			extension: extension,
			comentarios: comentarios
		};

		editarTelefono(id, dto);

		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}

function limpiarFormTelefonos() {
	document.getElementById('telefono').value = '';
	document.getElementById('extension').value = '';
	document.getElementById('comentarios').value = '';
	document.getElementById('searchInput').value = '';
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
var telefonos = [];

function listaTelefonos() {
	$.ajax({
		url: newUrlBase + "lista_telefonos",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			telefonos = respuesta.telefonos;

			vistaTablaTel.clear().draw();
			$.each(telefonos, function(idx, opt) {

				var btnEditar = "<div class='row'><div class='col-sm-6'>"
					+ "<button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='abrirTelefono("
					+ opt.pk_id_telefono
					+ ")' id="
					+ opt.pk_id_telefono
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil'></i></button></div>";

				var btnEstatus = "<div class='col-sm-6'><button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Vigencia' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='vigenciaTelefono("
					+ opt.pk_id_telefono + ', ' + opt.i_estatus
					+ ")' id="
					+ opt.pk_id_telefono
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-unlock'></i></button></div></div>";
					
					var vigente = opt.i_estatus == 1 ? "Si" : "No";

				vistaTablaTel.row.add([
					opt.pk_id_telefono,
					opt.fk_id_sucursal.vc_nombresucursal,
					opt.fk_id_sucursal.vc_direccioncompleta,
					opt.vc_telefono,
					opt.vc_extension,
					opt.vc_comentarios,
					vigente,
					btnEditar + btnEstatus
				]).draw(false);
			});

			//console.log(telefonos);
		},
		error: function(error) {
			// console.log(error);
		}
	})
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
					window.location.reload();
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
}
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
					window.location.reload();
				});
		}, error: function(error) {
			// console.log(error);
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
	var sucursal = document.getElementById('sucursalE');
	var direccion = document.getElementById('direccionE');
	var telefono = document.getElementById('telefonoE');
	var extension = document.getElementById('extensionE');
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
					window.location.reload();
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