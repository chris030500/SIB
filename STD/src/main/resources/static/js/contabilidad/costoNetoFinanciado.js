// @ts-nocheck
/**
*
*/
$(document).ready(function() {
	tablaProcesos();
});

function tablaProcesos() {
	let valor = $('#listaHistProceso').val();
	let listaObtenida = JSON.parse(valor);

	pintarTabla(listaObtenida, tblProcesoGeneradorCNF);
}

function submitForm(idEvento) {

	const selectedValue = document.querySelector('input[name="rbtnDefault"]:checked').id;

	if (selectedValue === 'rbtnNo') {
		swal("Requiere validar la información que carga por layout para procesar nuevamente.", {
			closeOnClickOutside: false,
			icon: 'warning',
		});
	} else if (selectedValue === 'rbtnSi') {
		swal({
			title: "¿La información del reporte es correcta?",
			text: "Confirmar el proceso, para inactivar los procesos anteriores de este cierre de mes.",
			icon: 'warning',
			buttons: ["Cancelar", "Confirmar"],
		}).then(resultado => {
			if (resultado) {
				const $button = $(idEvento);

				// Selecciona el formulario más cercano al botón clickeado
				const $form = $(idEvento).closest('form');

				// Obtén el ID del formulario
				const formId = $form.attr('id');

				// Obtén el formulario
				const form = document.getElementById(formId);

				// Crea un objeto FormData con el formulario
				const formData = new FormData(form);


				$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
				 bloquearPantalla(1);
				$("button").prop("disabled", true);
				fetch(form.action, {
					method: 'POST',
					body: formData
				})
					.then(response => {
						if (!response.ok) {
							throw new Error('Error, comuniquese con el administrador o intente mas tarde.');
						}

						return response.json();
					})
					.then(data => {

						document.getElementById('listaHistProceso').value = data.listaHistProceso;
						mostrarAlertar(data.message, "success");

					})
					.catch(error => {
						console.log('Error:', error);
						mostrarAlertar(data.message, "danger");
					})
					.finally(() => {
						// Ocultar el overlay con el spinner y habilitar los elementos del formulario
						$button.html('Guardar');
						tablaProcesos();
						$("button").prop("disabled", false);
						 bloquearPantalla(2);
					});

			}
		});
	}
}

function submitBtn(idBotton, num) {

	const $button = $(idBotton);

	// Selecciona el formulario más cercano al botón clickeado
	const $form = $(idBotton).closest('form');

	// Obtén el ID del formulario
	const formId = $form.attr('id');

	// Obtén el formulario
	const form = document.getElementById(formId);

	// Crea un objeto FormData con el formulario
	const formData = new FormData(form);

	// Variable para llevar el seguimiento de si hay campos vacíos
	//let enviardatos = validarDatos(formData);

	//if (!enviardatos) {
	// Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
	//Array.from(form.elements).forEach(element => element.disabled = true);
	$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
	$("button").prop("disabled", true);
	   bloquearPantalla(1);
	// Usa fetch para enviar una solicitud POST con los datos del formulario
	fetch(form.action, {
		method: 'POST',
		body: formData
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Error, comuniquese con el administrador o intente mas tarde.');
			}

			return response.json();
		})
		.then(data => {
			//if(data.listaCatalogo.length > 0){
			//   pintarTabla(data.listaCatalogo,tblConfiguracion);
			//}
			mostrarAlertar(data.message, "success",num);

			switch (num) {
				case 1:
					//$button.html('<i class="fa-solid fa-circle-play"></i>');
					break;
				case 2:
					if (data.urlReporte.length > 0) {
						window.open(window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + data.urlReporte);
						window.focus();
					}
					break;
				default:
					break;
			}

			//console.log('Success:', data);
			// Maneja la respuesta aquí (por ejemplo, muestra un mensaje al usuario)
		})
		.catch(error => {
			console.log('Error:', error);
			mostrarAlertarGastos("Error al mostrar información en pantalla, favor de comunicarse con el administrador del sistema.","danger",num);
			// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
		})
		.finally(() => {
			   bloquearPantalla(2);
			// Ocultar el overlay con el spinner y habilitar los elementos del formulario
			switch (num) {
				case 1:
					$button.html('<i class="fa-solid fa-circle-play"></i>');
					break;
				case 2:
					$button.html('<i class="fa-sharp fa-solid fa-file-arrow-down"></i>');
					break;
				default:
					break;
			}
				 $("button").prop("disabled", false);
		});
}


function pintarTabla(listaDatos, tablaPintar) {
	const registrosTotales = [];


	tablaPintar.clear().draw();
	$.each(listaDatos, function(idx, opt) {

		var btnsReporte = " <button name='btnHist' class='btn btn-success btn-sm' tabindex='0' data-bs-toggle='modal'"
			+ " title='Actualizar' data-bs-target='#' aria-controls='table-datatable' type='button'"
			+ " onclick='btnReporteHistorico(this)' id="
			+ opt.proceso
			+ " data-tooltip='Add'>"
			+ " <i class='fa-sharp fa-solid fa-file-arrow-down'></i></button>";

		registrosTotales.push([
			opt.mes_proceso,
			opt.fecha_corte,
			opt.usuario_vobo,
			opt.fecha_registro_vobo,
			btnsReporte
		]);
	});
	tablaPintar.rows.add(registrosTotales).draw();

}
/*
function validarDatos(formData) {
	let esVacio = false;
	let txtError = "";
	let tipoError = "";
	// Recorrer todos los campos del formulario
	for (let [key, value] of formData.entries()) {
		if (!mayorCero(value)) {
			esVacio = true;
			txtError += `El campo ${key} no puede estar vacío<br>`;

			// Mostrar mensaje de error (puedes personalizar esto según tus necesidades)
			//alert(`El campo ${key} no puede estar vacío`);
			// Puedes agregar una clase de error al campo vacío, por ejemplo:
			//$(`#${key}`).addClass('error');
		}

	}
	tipoError = "warning";
	if (esVacio) {
		mostrarAlertar(txtError, tipoError);
	} else {
		limpiarAlerta();
	}

	return esVacio;
}*/

// Función para validar si un campo está vacío
function mayorCero(value) {
	return value.trim() > 0;
}

function limpiarAlerta() {
	// Obtener el contenedor de alertas
	const alertPlaceholder = document.getElementById('liveAlertPlaceholder');

	// Limpiar el contenedor de alertas
	alertPlaceholder.innerHTML = '';
}

function cerrarModal(button) {

	let modal = button.closest('.modal');
	let modalId = modal.getAttribute('id');
	$('#' + modalId).modal('hide');

}

function toggleProcessingButton(isProcessing) {
	const button = document.getElementById('processingButton');

	if (isProcessing) {
		button.disabled = true;
		button.innerHTML = `
            <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
            <span class="sr-only">Loading...</span>
        `;
	} else {
		button.disabled = false;
		button.innerHTML = `
            Enviar
        `;
	}
}

function mostrarAlertar(msnError, tipoError) {
	limpiarAlerta();

	// Obtener el contenedor de alertas
	const alertPlaceholder = document.getElementById('liveAlertPlaceholder');


	// Función para añadir una alerta al contenedor
	const appendAlert = (message, type) => {
		const wrapper = document.createElement('div');
		wrapper.innerHTML = [
			`<div class="alert alert-${type} alert-dismissible" role="alert">`,
			`   <div>${message}</div>`,
			'   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="cerrarAlerta"></button>',
			'</div>'
		].join('');
		alertPlaceholder.append(wrapper);
	};

	appendAlert(msnError, tipoError);

}

function btnReporteHistorico(idBotton) {

	const $button = $(idBotton);

	// Selecciona el formulario más cercano al botón clickeado
	const $form = $(idBotton).closest('form');

	// Obtén el ID del formulario
	const formId = $form.attr('id');

	// Obtén el formulario
	const form = document.getElementById(formId);

	// Captura el valor del ID del botón
	var procesoId = $button.attr('id');

	// Crea el objeto de datos para enviar
	var data = {
		procesoId: procesoId
	};

	// Variable para llevar el seguimiento de si hay campos vacíos
	//let enviardatos = validarDatos(formData);

	//if (!enviardatos) {
	// Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
	//Array.from(form.elements).forEach(element => element.disabled = true);
	$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
	 bloquearPantalla(1);
	// Usa fetch para enviar una solicitud POST con los datos del formulario
	
	console.log(form.action)
	console.log(JSON.stringify(data))
	fetch(form.action, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data) // Stringifica el objeto de datos
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Error de comunicación. Intente mas tarde');
			}

			return response.json();
		})
		.then(data => {
			//if(data.listaCatalogo.length > 0){
			//   pintarTabla(data.listaCatalogo,tblConfiguracion);
			//}
			mostrarAlertar(data.message, "success");
			window.open(window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + data.urlReporte);
			window.focus();

		})
		.catch(error => {
			console.log('Error:', error);
			mostrarAlertar(data.message, "danger");
			// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
		})
		.finally(() => {
			// Ocultar el overlay con el spinner y habilitar los elementos del formulario
			$button.html('<i class="fa-sharp fa-solid fa-file-arrow-down"></i>');
			bloquearPantalla(2);
		});
}

