// @ts-nocheck
/**
 * 
 */
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var urlBase = getAbsolutePath();
console.log("urlBase: " + urlBase);


$(document).ready(function() {
	// Función que se ejecuta al hacer clic en el botón "Cerrar" del modal
	$('#modalClasificacion').on('click', '.modal-footer .btn-secondary', function() {
		// Forzar el cierre del modal antes de recargar la página
		$('#modalClasificacion').modal('hide');
		location.reload();
	});
});

$(document).ready(function() {
	// Función que se ejecuta al hacer clic en el botón "Cerrar" del modal
	$('#modalEditarClasificacion').on('click', '.modal-footer .btn-secondary', function() {
		// Forzar el cierre del modal antes de recargar la página
		$('#modalEditarClasificacion').modal('hide');
		location.reload();
	});
});


function toggleCheckboxAndContent(selectElement) {
	const checkbox = document.getElementById('confMensaje');
	const contentDiv = document.getElementById('contenidoCheckbox');

	if (selectElement.value === '1' || selectElement.value === '2') {
		checkbox.removeAttribute('hidden');
		contentDiv.style.display = 'block';
	} else {
		checkbox.setAttribute('hidden', 'true');
		contentDiv.style.display = 'none';
	}
}



function validarYGuardarClasificacion() {
	const selectBase = document.getElementById('base');
	const nomClasificacionInput = document.getElementById('nomClasificacion');
	const abrevacionInput = document.getElementById('abrevacion');
	const codigoClasificacionInput = document.getElementById('codigoClasificacion');
	const vigenciaClasificacionInput = document.getElementById('vigenciaClasificacion');
	const checkboxConfMensaje = document.getElementById('confMensaje');
	const mensajeClasificacionInput = document.getElementById('mensajeClasificacion');

	const baseValue = selectBase.value;
	const nomClasificacionValue = nomClasificacionInput.value;
	const abrevacionValue = abrevacionInput.value;
	const codigoClasificacionVaue = codigoClasificacionInput.value;
	const vigenciaClasificacionValue = vigenciaClasificacionInput.value;
	const mensajeClasificacionValue = mensajeClasificacionInput.value;



	if (codigoClasificacionVaue.length > 2) {
		// Mostrar mensaje de error
		swal({
			title: "Error",
			text: "El código de clasificación no puede exceder los 2 caracteres.",
			icon: "warning",
		});
		return; // Detener el proceso de guardar cambios
	}

	// Validar la longitud del campo mensajeClasificacion2
	if (mensajeClasificacionValue.length > 200) {
		// Mostrar mensaje de error
		swal({
			title: "Error",
			text: "El mensaje de clasificación no puede exceder los 200 caracteres.",
			icon: "warning",
		});
		return; // Detener el proceso de guardar cambios
	}

	if (baseValue === '3') {
		// Si el valor del select es 3, deshabilitar el checkbox y vaciar el mensaje
		checkboxConfMensaje.disabled = true;
		checkboxConfMensaje.checked = false;
		mensajeClasificacionInput.value = "";
		if (nomClasificacionValue.trim() === '' || abrevacionValue.trim() === ''
			|| codigoClasificacionVaue.trim() === '' || vigenciaClasificacionValue.trim() === '') {
			//alert("El mensaje de clasificación es obligatorio.");
			swal({
				title: "Error, ",
				text: "Existen campos vacíos, favor de validar.",
				icon: "warning",
			});
			return; // Si el mensaje está vacío, se cancela la operación de guardar
		}
	} else {
		// Si el valor del select es diferente a 3, habilitar el checkbox y validar todos los campos
		//checkboxConfMensaje.disabled = false;
		if (mensajeClasificacionValue.trim() === '' || nomClasificacionValue.trim() === '' || abrevacionValue.trim() === ''
			|| codigoClasificacionVaue.trim() === '' || vigenciaClasificacionValue.trim() === '') {
			//alert("El mensaje de clasificación es obligatorio.");
			swal({
				title: "Error, ",
				text: "Existen campos vacíos, favor de validar.",
				icon: "warning",
			});
			return; // Si el mensaje está vacío, se cancela la operación de guardar
		}
		// Aquí puedes agregar más validaciones para los otros campos según tus necesidades
		// Por ejemplo, puedes verificar que los campos Nombre, Abreviación, etc. no estén vacíos
	}

	// Si pasa todas las validaciones, proceder a guardar la clasificación
	//guardarClasificacion(baseValue, nomClasificacionValue, abrevacionValue, codigoClasificacionVaue, checkboxConfMensaje, mensajeClasificacionValue, vigenciaClasificacionValue);

	validarClasificacion(baseValue, nomClasificacionValue, abrevacionValue, codigoClasificacionVaue, checkboxConfMensaje, mensajeClasificacionValue, vigenciaClasificacionValue);
}

function validarClasificacion(baseValue, nomClasificacionValue, abrevacionValue, codigoClasificacionVaue, checkboxConfMensaje, mensajeClasificacionValue, vigenciaClasificacionValue) {


	$.ajax({
		url: urlBase + "validarClasificacion?base=" + encodeURIComponent(baseValue) +
			"&nomClasificacion=" + encodeURIComponent(nomClasificacionValue) +
			"&codigoClasificacion=" + encodeURIComponent(codigoClasificacionVaue) +
			"&clasificacionId=" + encodeURIComponent(clasificacionId),
		type: "GET",
		success: function(respuesta) {
			console.log("Respuesta: " + respuesta);

			if (respuesta == 'Ya existe un registro similar en la clasificacion de bases') {
				swal({
					title: "Ya existe un registro similar en la clasificacion de bases, favor de validar el código de clasificación y /o nombre de la clasificación",
					icon: "info",
				});
				/*.then((result) => {
					window.location.reload();
				});*/
			} else if (respuesta == 'continuar') {
				guardarClasificacion(baseValue, nomClasificacionValue, abrevacionValue, codigoClasificacionVaue, checkboxConfMensaje, mensajeClasificacionValue, vigenciaClasificacionValue);
			}
			/*swal({
				title: "Registro existoso",
				icon: "success",
			}).then((result) => {
				window.location.reload();
			});*/

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest: " + XMLHttpRequest);
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);
			/*swal({
				title: "Error, ",
				text: "Se presento un error al intentar guardar el registro de la clasificación, favor de contactar al administrador del sistema.",
				icon: "warning",
			});*/
		}
	});
}

function guardarClasificacion(baseValue, nomClasificacionValue, abrevacionValue, codigoClasificacionVaue, checkboxConfMensaje, mensajeClasificacionValue, vigenciaClasificacionValue) {


	if (baseValue === 3) {
		mensajeClasificacionValue.value = '';
	}
	console.log("baseValue: " + baseValue);
	console.log("nomClasificacionValue: " + nomClasificacionValue);
	console.log("abrevacionValue: " + abrevacionValue);
	console.log("codigoClasificacionVaue: " + codigoClasificacionVaue);
	console.log("checkboxConfMensaje.checked: " + checkboxConfMensaje.checked);
	console.log("mensajeClasificacionValue: " + mensajeClasificacionValue);
	console.log("vigenciaClasificacionValue: " + vigenciaClasificacionValue);

	$.ajax({
		url: urlBase + "guardarClasificacion?base=" + encodeURIComponent(baseValue) +
			"&nomClasificacion=" + encodeURIComponent(nomClasificacionValue) +
			"&abrevacion=" + encodeURIComponent(abrevacionValue) +
			"&codigoClasificacion=" + encodeURIComponent(codigoClasificacionVaue) +
			"&vigenciaClasificacion=" + encodeURIComponent(vigenciaClasificacionValue) +
			"&mensajeClasificacion=" + encodeURIComponent(mensajeClasificacionValue) +
			"&confMensaje=" + encodeURIComponent(checkboxConfMensaje.checked),
		type: "POST",
		success: function(respuesta) {
			//console.log("Respuesta: " + respuesta);
			swal({
				title: "Registro existoso",
				icon: "success",
			}).then((result) => {
				window.location.reload();
			});

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			/*console.log("XMLHttpRequest: " + XMLHttpRequest);
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);*/
			swal({
				title: "Error, ",
				text: "Se presento un error al intentar guardar el registro de la clasificación, favor de contactar al administrador del sistema.",
				icon: "warning",
			});
		}
	});
}

// Captura el evento de clic en el botón de editar
$(document).on('click', '.btn-info', function() {
	// Obtén el ID de la clasificación desde el atributo data
	var clasificacionId = $(this).data('clasificacion-id');
	console.log("clasificacionId: " + clasificacionId);

	// Realiza una petición AJAX para obtener los detalles de la clasificación seleccionada
	$.ajax({
		type: 'GET',
		url: urlBase + 'obtenerDetallesClasificacion?id=' + clasificacionId,
		success: function(data) {
			// Llena los campos del modal con los datos obtenidos
			console.log("data: " + JSON.stringify(data));
			$('#base2').val(data.baseId.pk_id_base);
			$('#nomClasificacion2').val(data.nombreClasificacion);
			$('#abrevacion2').val(data.abreviacionClasificacion);
			$('#codigoClasificacion2').val(data.codigoClasificacion);
			$('#vigenciaClasificacion2').val(data.vigenciaClasificacion);
			$('#idClasificacion').val(data.idClasificacion);

			/*console.log("mensajeClasificacion: " + data.mensajeClasificacion);
			console.log("requiereConfiguracionMensaje: " + data.requiereConfiguracionMensaje);*/

			// Verifica si se requiere configurar el mensaje y actualiza el checkbox y el campo de mensaje
			if (data.requiereConfiguracionMensaje) {
				$('#confMensaje2').prop('checked', true);
				$('#mensajeClasificacion2').val(data.mensajeClasificacion);
			} else {
				$('#confMensaje2').prop('checked', false);
				$('#mensajeClasificacion2').val('');
			}

			// Abre el modal
			$('#modalEditarClasificacion').modal('show');
			toggleMensajeClasificacionEdit();
		},
		error: function() {
			swal({
				text: "Error al obtener los detalles de la clasificación.",
				icon: "warning",
			});
			//alert('Error al obtener los detalles de la clasificación.');
		}
	});
});


function toggleMensajeClasificacionEdit() {
	//console.log("clasificacionId: " + clasificacionId);
	var checkbox = document.getElementById('confMensaje2');
	var mensajeInput = document.getElementById('mensajeClasificacion2');

	console.log("checkbox2: " + checkbox.value);
	console.log("mensajeInput2: " + mensajeInput.value);
	if (checkbox.checked) {
		mensajeInput.style.display = 'block';
		mensajeInput.disabled = false;
	} else {
		mensajeInput.style.display = 'none';
		mensajeInput.disabled = true;
	}
}

function toggleMensajeClasificacion() {
	//console.log("clasificacionId: " + clasificacionId);
	var checkbox = document.getElementById('confMensaje');
	var mensajeInput = document.getElementById('mensajeClasificacion');

	/*console.log("checkbox: " + checkbox.value);
	console.log("mensajeInput: " + mensajeInput.value);*/
	if (checkbox.checked) {
		mensajeInput.style.display = 'block';
		mensajeInput.disabled = false;
	} else {
		mensajeInput.style.display = 'none';
		mensajeInput.disabled = true;
	}
}


// Llamamos a la función al cargar la página para configurar el estado inicial
toggleMensajeClasificacion();
toggleMensajeClasificacionEdit();

function validarClasificacionEditar() {

	console.log("validarClasificacionEditar");
	const selectBaseEdit = document.getElementById('base2');
	const nomClasificacionInputEdit = document.getElementById('nomClasificacion2');
	const abrevacionInputEdit = document.getElementById('abrevacion2');
	const codigoClasificacionInputEdit = document.getElementById('codigoClasificacion2');
	const vigenciaClasificacionInputEdit = document.getElementById('vigenciaClasificacion2');
	const checkboxConfMensajeEdit = document.getElementById('confMensaje2');
	const mensajeClasificacionInputEdit = document.getElementById('mensajeClasificacion2');
	const idClasificacionInputEdit = document.getElementById('idClasificacion');

	const baseEditValue = selectBaseEdit.value;
	const nomClasificacionEditValue = nomClasificacionInputEdit.value;
	const abrevacionEditValue = abrevacionInputEdit.value;
	const codigoClasificacionEditVaue = codigoClasificacionInputEdit.value;
	const vigenciaClasificacionEditValue = vigenciaClasificacionInputEdit.value;
	const mensajeClasificacionEditValue = mensajeClasificacionInputEdit.value;
	const idClasificacionInputEditValue = idClasificacionInputEdit.value;

	if (codigoClasificacionEditVaue.length > 2) {
		// Mostrar mensaje de error
		swal({
			title: "Error",
			text: "El código de clasificación no puede exceder los 2 caracteres.",
			icon: "warning",
		});
		return; // Detener el proceso de guardar cambios
	}

	// Validar la longitud del campo mensajeClasificacion2
	if (mensajeClasificacionEditValue.length > 200) {
		// Mostrar mensaje de error
		swal({
			title: "Error",
			text: "El mensaje de clasificación no puede exceder los 200 caracteres.",
			icon: "warning",
		});
		return; // Detener el proceso de guardar cambios
	}


	$.ajax({
		url: urlBase + "validarClasificacion?base=" + encodeURIComponent(baseEditValue) +
			"&nomClasificacion=" + encodeURIComponent(nomClasificacionEditValue) +
			"&codigoClasificacion=" + encodeURIComponent(codigoClasificacionEditVaue) +
			"&clasificacionId=" + encodeURIComponent(idClasificacionInputEditValue),
		type: "GET",
		success: function(respuesta) {
			console.log("Respuesta: " + respuesta);

			if (respuesta == 'Ya existe un registro similar en la clasificacion de bases') {
				swal({
					title: "Ya existe un registro similar en la clasificacion de bases, favor de validar el código de clasificación y /o nombre de la clasificación",
					icon: "info",
				});
				/*.then((result) => {
					window.location.reload();
				});*/
			} else if (respuesta == 'continuar') {
				guardarClasificacionEditar(baseEditValue, nomClasificacionEditValue, abrevacionEditValue, codigoClasificacionEditVaue, checkboxConfMensajeEdit, mensajeClasificacionEditValue, vigenciaClasificacionEditValue, idClasificacionInputEditValue);
			}
			/*swal({
				title: "Registro existoso",
				icon: "success",
			}).then((result) => {
				window.location.reload();
			});*/

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest: " + XMLHttpRequest);
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);
			/*swal({
				title: "Error, ",
				text: "Se presento un error al intentar guardar el registro de la clasificación, favor de contactar al administrador del sistema.",
				icon: "warning",
			});*/
		}
	});
}

function guardarClasificacionEditar(baseValue, nomClasificacionValue, abrevacionValue, codigoClasificacionVaue, checkboxConfMensaje, mensajeClasificacionValue, vigenciaClasificacionValue, idClasificacionInputEditValue) {


	if (baseValue === 3) {
		mensajeClasificacionValue.value = '';
	}
	console.log("baseValue: " + baseValue);
	console.log("nomClasificacionValue: " + nomClasificacionValue);
	console.log("abrevacionValue: " + abrevacionValue);
	console.log("codigoClasificacionVaue: " + codigoClasificacionVaue);
	console.log("checkboxConfMensaje.checked: " + checkboxConfMensaje.checked);
	console.log("mensajeClasificacionValue: " + mensajeClasificacionValue);
	console.log("vigenciaClasificacionValue: " + vigenciaClasificacionValue);

	$.ajax({
		url: urlBase + "guardarClasificacion?base=" + encodeURIComponent(baseValue) +
			"&nomClasificacion=" + encodeURIComponent(nomClasificacionValue) +
			"&abrevacion=" + encodeURIComponent(abrevacionValue) +
			"&codigoClasificacion=" + encodeURIComponent(codigoClasificacionVaue) +
			"&vigenciaClasificacion=" + encodeURIComponent(vigenciaClasificacionValue) +
			"&mensajeClasificacion=" + encodeURIComponent(mensajeClasificacionValue) +
			"&confMensaje=" + encodeURIComponent(checkboxConfMensaje.checked) +
			"&id=" + encodeURIComponent(idClasificacionInputEditValue),
		type: "POST",
		success: function(respuesta) {
			//console.log("Respuesta: " + respuesta);
			swal({
				title: "Registro existoso",
				icon: "success",
			}).then((result) => {
				window.location.reload();
			});

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			/*console.log("XMLHttpRequest: " + XMLHttpRequest);
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);*/
			swal({
				title: "Error, ",
				text: "Se presento un error al intentar guardar el registro de la clasificación, favor de contactar al administrador del sistema.",
				icon: "warning",
			});
		}
	});
}

function bajaClasificacion(clasificacion){
	console.log("clasificacion: " + clasificacion);
	console.log("urlBase: " + urlBase);
	estatusBaja = 0;
	$.ajax({
		url: urlBase + "cambiarEstatusClasificacion?id=" + encodeURIComponent(clasificacion) +
			"&estatus=" + encodeURIComponent(estatusBaja),
		type: "GET",
		success: function(respuesta) {
			console.log("Respuesta: " + respuesta);

			swal({
				title: "Se ha dado de baja la clasificación",
				icon: "success",
			}).then((result) => {
				window.location.reload();
			});

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest: " + XMLHttpRequest);
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);
			swal({
				title: "Error, ",
				text: "No se puede dar de baja debido a que el estatus es inactivo.",
				icon: "warning",
			});
		}
	});
}

function altaClasificacion(clasificacion){
	console.log("clasificacion: " + clasificacion);
	console.log("urlBase: " + urlBase);
	estatusAlta = 1;
	$.ajax({
		url: urlBase + "cambiarEstatusClasificacion?id=" + encodeURIComponent(clasificacion) +
			"&estatus=" + encodeURIComponent(estatusAlta),
		type: "GET",
		success: function(respuesta) {
			//console.log("Respuesta: " + respuesta);
			console.log("Respuesta: " + JSON.stringify(respuesta));
			swal({
				title: "Se ha dado de alta la clasificación",
				icon: "success",
			}).then((result) => {
				window.location.reload();
			});

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest: " + XMLHttpRequest);
			console.log("textStatus: " + textStatus);
			console.log("errorThrown: " + errorThrown);
			swal({
				title: "Error, ",
				text: "No se puede dar de alta debido a que el estatus es activo.",
				icon: "warning",
			});
		}
	});
}

