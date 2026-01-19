/**
 * js campañas temporales
 */
// @ts-nocheck

var url = "";
const opcionCampania = document.getElementById("opcionCampania");

$(document).ready(function() {
	subirArchivo();
});

opcionCampania.addEventListener("input", function () {
  this.value = this.value.toUpperCase();
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
		// Este código se ejecutará después de que el temporizador haya terminado
		if (validarDatos() == 0) {
			guardarDtosTmp();
		}
	});

	$("#btnGuardarFile").click(function() {

		if (document.getElementById("file").value.length > 0) {

			var form = $('#formArchivoCampaniaTmp')[0];
			var data = new FormData(form);

			// Opcional: deshabilitar botón para evitar doble envío
			document.getElementById('btnGuardarFile').disabled = true;
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

	swal({
		title: 'Procesando solicitud',
		text: 'Por favor espera',
		allowOutsideClick: false,
		buttons: false,
		closeOnClickOutside: false,
		icon: 'info',
	});

	fetch(getAbsolutePath() + dtosCampaniaTmp, {
		method: "POST",
		body: formData
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la solicitud");
			}
			return response.json(); // o .json() si el backend devuelve JSON
		})
		.then(data => {
			console.log("Respuesta del servidor:", data);

			// Leer mensaje y tipo desde el JSON recibido
			const mensaje = data.mensaje;
			const tipo = data.tipo;
			swal("", mensaje, tipo);
		})
		.catch(error => {
			console.log("Error:", error);
			alert("Ocurrió un error al guardar la campaña");
		})
		.finally(() => {
			swal.close(); // cerrar el "Cargando..."
			document.getElementById('btnGuardarCampania').disabled = false;
		});
}


//archivo
var procesarArchivo = "procesarArchivo";
function procesarCampaniaTmp(data) {

	swal({
		title: 'Procesando solicitud',
		text: 'Por favor espera',
		allowOutsideClick: false,
		buttons: false,
		closeOnClickOutside: false,
		icon: 'info',
	});

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
			console.log("Respuesta del servidor:", data);

			// Leer mensaje y tipo desde el JSON recibido
			const mensaje = data.mensaje;
			const tipo = data.tipo;
			swal("", mensaje, tipo);

		})
		.catch(error => {
			console.log("Error:", error);
			alert("Ocurrió un error");
		})
		.finally(() => {
			swal.close(); // cerrar el "Cargando..."
			document.getElementById('btnGuardarFile').disabled = false;
		});
}

const mapaCampanias = new Map();
let valorBusqueda = "";
var buscarCampaniasActivas = "buscarCampaniasTemporales?valorBusqueda=";
function buscarCampaniasTemporales() {
	valorBusqueda = document.getElementById("buscarCampaniaTemporales").value;
	if (valorBusqueda != 0) {
		if (mapaCampanias.has(valorBusqueda)) {
			obtDatosCampaTemporales();

		} else {
			limpiarFormulario();
			buscarCampaniasT(valorBusqueda);
		}
	}else{
		limpiarFormulario();
	}



}

function buscarCampaniasT(valorBusqueda) {


	fetch(getAbsolutePath() + buscarCampaniasActivas + encodeURIComponent(valorBusqueda), {
		method: "GET",
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la respuesta del servidor");
			}
			return response.json();
		})
		.then(data => {
			console.log("Respuesta recibida:", data);

			const datalist = document.getElementById("opcionCampania");
			datalist.innerHTML = ''; // limpiar anteriores
			mapaCampanias.clear();
			if (Array.isArray(data)) {
				data.forEach(campania => {
					const option = document.createElement("option");
					option.value = campania.vc_tipo_campania; // ajusta si se requiere id
					datalist.appendChild(option);
					mapaCampanias.set(campania.vc_tipo_campania, campania.pk_id_tipo_campania); // guarda el id
				});
			} else {
				swal("La respuesta no contiene una lista válida de campañas", {
					closeOnClickOutside: false,
					icon: "warning",
				});
			}
		})
		.catch(error => {
			console.error("Error en la búsqueda:", error);
			swal("Ocurrió un error al buscar campañas activas", {
				closeOnClickOutside: false,
				icon: "error",
			});
		})
		.finally(() => {
			// Por si estás usando bloqueo de UI

			setTimeout($.unblockUI, 600);
		});
}

function obtenerIdCampaniaSeleccionada() {
	const nombreSeleccionado = document.getElementById("buscarCampaniaTemporales").value;
	const idCampania = mapaCampanias.get(nombreSeleccionado);

	if (idCampania !== undefined) {
		console.log("ID de la campaña seleccionada:", idCampania);
		document.getElementById("idCampaniaSeleccionada").value = idCampania;
		return idCampania;
	} else {
		console.warn("No se encontró el ID para la campaña seleccionada");
		document.getElementById("idCampaniaSeleccionada").value = "";
		return null;
	}
}

var obtParamCampaniasTmp = "obtParamCampaniasTmp?valorCampaniaID=";
function obtDatosCampaTemporales() {
	console.log("------" + getAbsolutePath() + obtParamCampaniasTmp + obtenerIdCampaniaSeleccionada());
	fetch(getAbsolutePath() + obtParamCampaniasTmp + obtenerIdCampaniaSeleccionada(), {
		method: "GET",
	}).then(response => {
		if (!response.ok || response.status === 204) {
			swal("No se encontraron parámetros para esta campaña", { icon: "warning" });
			return null;
		} else {
			return response.json();
		}
	})
		.then(data => {
			if (!data) return;

			llenarFormulario(data);

		})
		.catch(error => {
			console.log("Error en la búsqueda:", error);
			swal("Ocurrió un error al buscar campañas activas", {
				closeOnClickOutside: false,
				icon: "error",
			});
		})
		.finally(() => {
			setTimeout($.unblockUI, 600);
		});
}

function llenarFormulario(param) {
	document.getElementById("nombreCampania").value = param.vc_tipo_campania;
	document.getElementById("descripcionCampania").value = param.vc_tipo_campania;
	document.getElementById("vigenciaMeses").value = param.i_vigencia_campania;
	document.getElementById("fechaInicio").value = param.dt_fecha_registro;
	document.getElementById("fechaFin").value = param.dt_fecha_registro;
	document.getElementById("fechaRegistro").value = param.dt_fecha_registro;
	const select = document.getElementById("estatusCampania");
	const valueToSet = param.i_estatus;

	Array.from(select.options).forEach(option => {
		if (option.value == valueToSet) {
			option.selected = true;
		}
	});
	document.getElementById("fechaEnvioMC").value = param.dt_fecha_registro;
	document.getElementById("checkRecurrente").checked = param.i_tipo_campania === 1;

}

function limpiarFormulario() {
	// hidden
	document.getElementById("idCampaniaSeleccionada").value = "";
	document.getElementById("formDatosCampania").reset();
}

