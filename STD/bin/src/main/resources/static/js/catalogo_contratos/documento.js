// Función para validar el formulario
function validarContrato() {
	// Asignamos los valores a las variables
	var contrato = $('#contrato').val();
	var descripcion = $('#descripcion').val();
	var estatus = $('input:radio[name=estatus]:checked').val();
	// Si los valores son diferentes de vacio
	if (contrato != "" && descripcion != "") {
		// Se llena el DTO
		const dto = {
			vc_contrato: contrato,
			vc_descripcion: descripcion,
			i_estatus: estatus
		};

		agregarContrato(dto);

		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}



// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}



// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/catalogo_contratos", "");



// Función para agregar el nuevo contrato
function agregarContrato(dto) {

	var enviar = JSON.stringify(dto);

	$.ajax({
		url: newUrlBase + "agregar_contrato",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: 'application/json; charset=utf-8',
		success: function(respuesta) {
			//console.log(respuesta);
			swal("Se guardó el contrato correctamente", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					window.location.reload();
				});
		}, error: function(error) {
			// console.log(error);
			swal({
				title: "Aviso",
				closeOnClickOutside: false,
				text: "Hubo un error al momento de guardar los datos",
				icon: "warning",
			});
		}
	})
}



// Función para mostrar la lista de contratos activos
window.addEventListener('load', function() {
	$.ajax({
		url: newUrlBase + "lista_contratos?todo=0",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log(respuesta);

			$('#catalogo > tbody').empty();

			$.each(respuesta, function(idx, opt) {

				var estatus;
				var acciones;

				if (opt.i_estatus == 1) {
					estatus = "<td><i title='Activo' style='color: green' class='bi bi-check-circle-fill'></i></td>";
					acciones = "<td><button class='btn btn-warning' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' onclick='mostrarContratoPorID(" + opt.pk_id_contrato + ")' data-tooltip='Refresh'><i class='bi bi-pencil'></i></button>  <button class='btn btn-danger' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' onclick='alertaActualizar(" + JSON.stringify(opt) + ")' data-tooltip='Refresh'><i class='bi bi-trash3'></i></button></td>";
				} else if (opt.i_estatus == 0) {
					estatus = "<td><i title='Inactivo' style='color: red' class='bi bi-x-circle-fill'></i></td>";
					acciones = "<td><button class='btn btn-warning' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' onclick='mostrarContratoPorID(" + opt.pk_id_contrato + ")' data-tooltip='Refresh'><i class='bi bi-pencil'></i></button></td>";
				}

				var rows = "<tr >"
					+ "<td>" + opt.pk_id_contrato + "</td>"
					+ "<td>" + opt.vc_contrato + "</td>"
					+ "<td>" + opt.vc_descripcion + "</td>"
					+ estatus
					+ acciones
					+ "</tr>";

				$('#catalogo').append(rows)

			});
		}, error: function(error) {
			// console.log(error);
		}
	});
});



// Funcion para desmarcar una opción del combo de radio
function desmarcar() {
	var activo = document.getElementById("activo");
	var inactivo = document.getElementById("inactivo");
	activo.onclick = function() {
		if (activo.checked != false) {
			inactivo.checked = null;
		}
	}
	inactivo.onclick = function() {
		if (activo.checked != false) {
			inactivo.checked = null;
		}
	}
}



// Función para avisar sobre la actualización del estatus
function alertaActualizar(dto) {
	var estatus;
	if (dto.i_estatus == 1) {
		estatus = "inactivar";
	} else if (dto.i_estatus == 0) {
		estatus = "activar";
	}

	console.log(dto);
	swal({
		text: '¿Deseas ' + estatus + ' el contrato ' + dto.vc_contrato + '?',
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				actualizarEstatus(dto.pk_id_contrato, dto.i_estatus)
			} else { }
		});
}



// Función para actualizar el estatus
function actualizarEstatus(idContrato, estatus) {
	$.ajax({
		url: newUrlBase + "actualizar_estatus?idContrato=" + idContrato + "&estatus=" + estatus,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log(respuesta);
			swal("Se actualizó el contrato correctamente", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					window.location.reload();
				});
		}, error: function(error) {
			// console.log(error);
			swal({
				title: "Aviso",
				closeOnClickOutside: false,
				text: "Hubo un error al momento de actualizar los datos",
				icon: "warning",
			});
		}
	});
}



// Función para traer todos los contratos
function todosLosContratos() {
	var value = document.getElementById("checkLista").checked;
	//console.log(value);
	$.ajax({
		url: newUrlBase + "lista_contratos?todo=" + value,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log(respuesta);

			$('#catalogo > tbody').empty();

			$.each(respuesta, function(idx, opt) {

				var estatus;
				var acciones;

				if (opt.i_estatus == 1) {
					estatus = "<td><i title='Activo' style='color: green' class='bi bi-check-circle-fill'></i></td>";
					acciones = "<td><button class='btn btn-warning' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' onclick='mostrarContratoPorID(" + opt.pk_id_contrato + ")' data-tooltip='Refresh'><i class='bi bi-pencil'></i></button>  <button class='btn btn-danger' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' onclick='alertaActualizar(" + JSON.stringify(opt) + ")' data-tooltip='Refresh'><i class='bi bi-trash3'></i></button></td>";
				} else if (opt.i_estatus == 0) {
					estatus = "<td><i title='Inactivo' style='color: red' class='bi bi-x-circle-fill'></i></td>";
					acciones = "<td><button class='btn btn-warning' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' onclick='mostrarContratoPorID(" + opt.pk_id_contrato + ")' data-tooltip='Refresh'><i class='bi bi-pencil'></i></button>  <button class='btn btn-danger' tabindex='0' data-bs-toggle='tooltip' data-bs-placement='top' aria-controls='table-datatable' type='button' disabled='true' data-tooltip='Refresh'><i class='bi bi-trash3'></i></button></td>";
				}

				var rows = "<tr >"
					+ "<td>" + opt.pk_id_contrato + "</td>"
					+ "<td>" + opt.vc_contrato + "</td>"
					+ "<td>" + opt.vc_descripcion + "</td>"
					+ estatus
					+ acciones
					+ "</tr>";

				$('#catalogo').append(rows)

			});
		}, error: function(error) {
			// console.log(error);
		}
	});
}



// variables | campos del formulario de editar
var contrato = document.getElementById('idContrato');
var descripcion = document.getElementById('idDescripcion');
var estado;
var pkContrato;
var valorEstatus;
// Función para traer un contrato en específico
function mostrarContratoPorID(idContrato) {
	$.ajax({
		url: newUrlBase + "mostrar_contrato?idContrato=" + idContrato,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// console.log(respuesta);

			// Mostrar el modal de editar
			$('#modal-editar').modal('show');

			// Validar si el estatus está activo o inactivo
			var actualizarEstatus = document.getElementById('idActualizarEstatus');
			var estatusActivo = document.getElementById('idEstatusActivo');
			if (respuesta.i_estatus == 1) {
				actualizarEstatus.style.display = "none";
				estatusActivo.style.display = "block";
				estado = "activo";
			} else {
				actualizarEstatus.style.display = "block";
				estatusActivo.style.display = "none";
				estado = "noActivo";
			}

			// Llenar los campos del formulario de editar
			contrato.value = respuesta.vc_contrato;
			descripcion.value = respuesta.vc_descripcion;
			pkContrato = idContrato;
			valorEstatus = respuesta.i_estatus;

		},
		error: function(error) {
			// console.log(error);
		}
	})
}



// Función para cerrar el modal
function cerrarModal() {
	$('#modal-editar').modal('hide');
}



// Función para validar el formulario de editar
function formularioEditar(estado) {
	// Si los valores son diferentes de vacio
	if (contrato != "" && descripcion != "") {
		// Asignamos los valores a las variables
		var contrato = $('#idContrato').val();
		var descripcion = $('#idDescripcion').val();
		var estatus = $('input:radio[name=idEstatus]:checked').val();
		var dto;

		if (estado == "activo") {
			// Se llena el DTO
			dto = {
				pk_id_contrato: pkContrato,
				vc_contrato: contrato,
				vc_descripcion: descripcion,
				i_estatus: valorEstatus
			};

		} else if (estado == "noActivo") {
			// Se llena el DTO
			dto = {
				pk_id_contrato: pkContrato,
				vc_contrato: contrato,
				vc_descripcion: descripcion,
				i_estatus: estatus
			};
		}

		editarContrato(dto);

		// Si los valores estan vacios
	} else {
		// Se ejecuta la validacion
		swal("Falta información por llenar", {
			icon: "warning"
		});
	}
}



// Función para actualizar el contrato
function editarContrato(dto) {

	var enviar = JSON.stringify(dto);

	$.ajax({
		url: newUrlBase + "editar_contrato",
		data: enviar,
		type: "PUT",
		dataType: "JSON",
		contentType: 'application/json; charset=utf-8',
		success: function(respuesta) {
			//console.log(respuesta);
			swal("Se actualizó el contrato correctamente", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					window.location.reload();
				});
		}, error: function(error) {
			// console.log(error);
			swal({
				title: "Aviso",
				closeOnClickOutside: false,
				text: "Hubo un error al momento de guardar los datos",
				icon: "warning",
			});
		}
	})

}



//CONTADOR DE CARACTERES PARA LOS CAMPOS
var contratoLimit = 55;
var descripcionLimit = 200;
var contratoEditarLimit = 55;
var descripcionEditarLimit = 200;
$(function() {
	$("#contrato").on("input", function() {
		//al cambiar el texto del #contrato
		var init = $('#contrato').val().length;
		total_characters = (contratoLimit - init);

		$('#contador1').html(total_characters + " caracteres restantes");
	});
	$("#descripcion").on("input", function() {
		//al cambiar el texto del #descripcion
		var init = $('#descripcion').val().length;
		total_characters = (descripcionLimit - init);

		$('#contador2').html(total_characters + " caracteres restantes");
	});

	$("#idContrato").on("input", function() {
		//al cambiar el texto del #idContrato
		var init = $('#idContrato').val().length;
		total_characters = (contratoEditarLimit - init);

		$('#contador3').html(total_characters + " caracteres restantes");
	});
	$("#idDescripcion").on("input", function() {
		//al cambiar el texto del #idDescripcion
		var init = $('#idDescripcion').val().length;
		total_characters = (descripcionEditarLimit - init);

		$('#contador4').html(total_characters + " caracteres restantes");
	});
});