/**
 * js campañas temporales
 */
// @ts-nocheck

var url = "";
$(document).ready(function() {
	subirArchivo();
});


function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function tamFile() {
	const tamMaxFile = 31457280;
	var fileTam = document.getElementById("file");
	fileTam.onchange = function() {
		if (this.files[0].size > tamMaxFile) {
			swal("El archivo seleccionado excede el tamaño maximo permitido (30MB)", {
				closeOnClickOutside: false,
				icon: "warning",
			}).then(resp => {
				this.value = "";
			});
		};
	};
}

function subirArchivo() {


	$("#btnGuardarCampania").click(function(event) {
		event.preventDefault();  // <-- evita envío normal del form
	
		swal({
			title: "Registrar Campaña Temporal",
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
			if (validarDatos() == 0) {
				guardarDtosTmp();
			}
		});



	});

	$("#btnGuardarFile").click(function() {

		if (document.getElementById("file").value.length > 0) {

			var form = $('#formArchivoCampaniaTmp')[0];
			var data = new FormData(form);

			// Si necesitas agregar el parámetro idCmpTmp desde JS, hazlo aquí:
			data.append("idCmpTmp", 1); 
			procesarCampaniaTmp(data)



		} else {

			swal("Es necesario seleccionar un Archivo.", {
				closeOnClickOutside: false,
				icon: "error",
			});
		}

	});
}
//validarDatos
function validarDatos() {
	let pasoValidacion = 0;

	return pasoValidacion;
}
var dtosCampaniaTmp = "registroCmpTmp";
function guardarDtosTmp() {
	const form = document.getElementById('formDatosCampania');
	const formData = new FormData(form);

	// Opcional: deshabilitar botón para evitar doble envío
	document.getElementById('btnGuardarCampania').disabled = true;

	fetch(getAbsolutePath() + dtosCampaniaTmp, {
		method: "POST",
		body: formData
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la solicitud");
			}
			return response.text(); // o .json() si el backend devuelve JSON
		})
		.then(data => {
			console.log("Respuesta del servidor:", data);
			alert("¡Campaña guardada con éxito!");
			form.reset(); // limpiar formulario
		})
		.catch(error => {
			console.error("Error:", error);
			alert("Ocurrió un error al guardar la campaña");
		})
		.finally(() => {
			document.getElementById('btnGuardarCampania').disabled = false;
		});
}


//archivo
var procesarArchivo = "procesarArchivo";
function procesarCampaniaTmp(data) {

	fetch(getAbsolutePath() + procesarArchivo, {
		method: "POST",
		body: data, // FormData que ya incluye el archivo u otros campos
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la respuesta del servidor");
			}
			return response.json(); // O .text() si no es JSON
		})
		.then(data => {
			swal(data[0], {
				closeOnClickOutside: false,
				icon: "success",
			});
		})
		.catch(error => {
			console.error("Error al procesar:", error);
			swal("El tamaño del archivo es mayor al permitido 30MB.", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		})
		.finally(() => {
			setTimeout($.unblockUI, 600);
		});
}


function buscarCampanias(){
	
}



