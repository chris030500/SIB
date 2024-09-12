// @ts-nocheck
/**
*
*/
$(document).ready(function() {
	tablaProcesos();
});

function tablaProcesos() {
	let valor = $('#listaHistProcesoEPRC').val();
	let listaObtenida = JSON.parse(valor);

	pintarTabla(listaObtenida, tblProcesoGeneradorEPRC);
}

function submitFormEPRC(idEvento) {

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

				fetch(form.action, {
					method: 'POST',
					body: formData
				})
					.then(response => {
						if (!response.ok) {
							throw new Error('Network response was not ok');
						}

						return response.json();
					})
					.then(data => {

						document.getElementById('listaHistProcesoEPRC').value = data.listaHistProceso;
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



					});

			}
		});
	}
}

function submitBtnEPRC(idBotton, num) {

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

	// Usa fetch para enviar una solicitud POST con los datos del formulario
	fetch(form.action, {
		method: 'POST',
		body: formData
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}

			return response.json();
		})
		.then(data => {
			//if(data.listaCatalogo.length > 0){
			//   pintarTabla(data.listaCatalogo,tblConfiguracion);
			//}
			mostrarAlertar(data.message, "success");


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
			mostrarAlertar(error.message || "Ocurrió un error al procesar la solicitud.", "danger");
			// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
		})
		.finally(() => {
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
}

// Función para validar si un campo está vacío
function mayorCero(value) {
	return value.trim() > 0;
}

function limpiarAlerta() {
	// Obtener el contenedor de alertas
	const alertPlaceholder = document.getElementById('liveAlertPlaceholderEPRC');

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
	const alertPlaceholder = document.getElementById('liveAlertPlaceholderEPRC');


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

	// Usa fetch para enviar una solicitud POST con los datos del formulario
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
			mostrarAlertar(data.message, "danger");
			// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
		})
		.finally(() => {
			// Ocultar el overlay con el spinner y habilitar los elementos del formulario
			$button.html('<i class="fa-sharp fa-solid fa-file-arrow-down"></i>');
		});
}

document.getElementById("uploadForm").addEventListener("submit", function(event) {
	event.preventDefault(); // Prevenir el envío predeterminado del formulario

	const formData = new FormData();
	const fileInput = document.getElementById("fileUpload");
	const idFolio = "12345"; // Aquí debes poner el valor real de idFolio

	// Verifica si se ha seleccionado un archivo
	if (fileInput.files.length > 0) {

		const fileName = fileInput.files[0].name; // Obtener el nombre del archivo
		const fileExtension = fileName.split('.').pop().toLowerCase(); // Obtener la extensión del archivo

		// Nombres de archivo permitidos
		const validFileNames = ["layout_garantias.xls", "layout_cambio_etapa.xls", "layout_garantias.xlsx", "layout_cambio_etapa.xlsx"];
		// Extensiones permitidas
		const validExtensions = ["xlsx", "xls"];

		// Verificar si el archivo tiene una extensión válida
		if (!validExtensions.includes(fileExtension) || !validFileNames.includes(fileName)) {
			swal("El archivo debe ser un documento Excel con extensión .xlsx o .xls y tener uno de los nombres permitidos: 'layout_garantias.xls', 'layout_cambio_etapa.xls'.", {
				closeOnClickOutside: false,
				icon: "error",
			});
			return; // Salir de la función si el archivo no es válido
		}

		swal({
			title: "Procesando información.",
			content: {
				element: "div",
				attributes: {
					innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
					style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
				},
			},
			timer: 4000, // tiempo en milisegundos
			buttons: false,
			closeOnClickOutside: false,
			closeOnEsc: false,
		}).then((result) => {
			// Este código se ejecutará después de que el temporizador haya terminado

			for (let i = 0; i < fileInput.files.length; i++) {
				formData.append("file", fileInput.files[i]);
			}

			formData.append("idFolio", idFolio);

			fetch(getAbsolutePath() + "subirArchivoEPRC", {
				method: "POST",
				body: formData,
			})
				.then(response => response.json())
				.then(data => {
					if (data.length > 0) {
						swal(data[0], {
							closeOnClickOutside: false,
							icon: "success",
						}).then(resp => {
							adjuntarFiles();
						});
					} else {
						swal("No se recibió respuesta del servidor.", {
							closeOnClickOutside: false,
							icon: "warning",
						});
					}
				})
				.catch(error => {
					console.error("Error al subir el archivo:", error);

					swal("Ocurrió un error al subir el archivo.", {
						closeOnClickOutside: false,
						icon: "warning",
					});
				});

		});

	} else {
		swal("Es necesario seleccionar un Archivo.", {
			closeOnClickOutside: false,
			icon: "error",
		});
	}

});

var listarArchivos = "listaArchivosEPRC";
function adjuntarFiles() {
	url = "";
	url = getAbsolutePath() + listarArchivos;
	obtListadoArchivos(url);
	document.getElementById("fileUpload").value = "";
}

function obtListadoArchivos(url) {
	
	console.log(url);
	// Mostrar SweetAlert con spinner
	swal({
		title: "Consultando información",
		content: {
			element: "div",
			attributes: {
				innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
				style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
			},
		},
		buttons: false,
		closeOnClickOutside: false,
		closeOnEsc: false,
	});

	$.getJSON(url, {}, function(data) {
		// Ocultar SweetAlert después de cargar los datos
		swal.close();

		const fileList = document.getElementById("fileList");
		fileList.innerHTML = ""; // Limpiar la lista antes de agregar los elementos

		if (data.length > 0) {
			data.forEach(function(archivo) {
				const li = document.createElement("li");
				li.className = "list-group-item d-flex justify-content-between align-items-center";

				const fileInfo = document.createElement("div");
				fileInfo.innerHTML = `
                    <strong>${archivo.nombreArchivo}</strong><br>
                    <small><i class="fas fa-calendar-alt"></i> Creado: ${archivo.fechaCreacion}</small><br>
                    <small><i class="fas fa-edit"></i> Modificado: ${archivo.fechaModifacion}</small>
                `;

				const processButton = document.createElement("button");
				processButton.className = "btn btn-success btn-sm";
				processButton.innerHTML = `<i class="fas fa-cogs"></i> Procesar`;
				processButton.onclick = function() {
					// Aquí puedes agregar la lógica para procesar el archivo
					// Aquí se llama al backend para procesar el archivo
					const fileName = archivo.nombreArchivo;

					// Mostrar una alerta de procesamiento
					swal({
						title: `Procesando ${fileName}`,
						content: {
							element: "div",
							attributes: {
								innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
								style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
							},
						},
						buttons: false,
						closeOnClickOutside: false,
						closeOnEsc: false,
					});

					fetch(getAbsolutePath() + 'procesarArchivo', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json'
						},
						body: JSON.stringify({ nombreArchivo: fileName })
					})
						.then(response => response.json())
						.then(data => {
							swal.close();
							if (data.success) {
								swal("Éxito", "El archivo se procesó correctamente.", "success");
							} else {
								swal("Error", "Hubo un problema al procesar el archivo.", "error");
							}
						})
						.catch(error => {
							swal.close();
							swal("Error", "No se pudo procesar el archivo.", "error");
							console.error('Error al procesar el archivo:', error);
						});
				};

				li.appendChild(fileInfo);
				li.appendChild(processButton);
				fileList.appendChild(li);
			});

		} else {
			const li = document.createElement("li");
			li.className = "list-group-item";
			li.textContent = "No hay archivos subidos.";
			fileList.appendChild(li);
		}
	}).fail(function() {
		swal("Error", "No se pudo obtener la lista de archivos.", "error");
	});
}


