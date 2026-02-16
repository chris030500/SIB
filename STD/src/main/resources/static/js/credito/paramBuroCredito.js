/**
 * juan.trejo
 */
// @ts-nocheck

function toggleMopMaximo() {
	const tipo = document.querySelector('input[name="tipoEvaluacion"]:checked').value;
	const mopMaxRow = document.getElementById('mopMaximoRow');
	const usoMaximo = document.getElementById('usoMaximo');
	
	if (tipo === '1') {
		mopMaxRow.style.display = 'none';
		usoMaximo.style.display = 'none';
	} else {
		mopMaxRow.style.display = 'flex'; // usa 'flex' o 'block' según el diseño
		usoMaximo.style.display = 'flex'; // usa 'flex' o 'block' según el diseño
	}
}

// En caso de recarga con valor seleccionado
window.onload = function() {
	const checked = document.querySelector('input[name="tipoEvaluacion"]:checked');
	if (checked) toggleMopMaximo();
};

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

const mapaCampanias = new Map();
let valorBusqueda = "";
var buscarCampaniasActivas = "buscarCampaniasActivas?valorBusqueda=";
function buscarCampanias() {
	valorBusqueda = document.getElementById("buscarCampania").value;


	// Si el valor coincide con una opción exacta, no hagas búsqueda
	if (mapaCampanias.has(valorBusqueda)) {
		obtDatosBuro();

	} else {
		limpiarFormulario();
		buscarCampaniasA(valorBusqueda);
	}

}

function buscarCampaniasA(valorBusqueda) {


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




let valorCampaniaID = "";
var obtParamCampaniasBC = "obtParamCampaniasBC?valorCampaniaID=";
function obtDatosBuro() {
	console.log("------" + getAbsolutePath() + obtParamCampaniasBC + obtenerIdCampaniaSeleccionada());
	fetch(getAbsolutePath() + obtParamCampaniasBC + obtenerIdCampaniaSeleccionada(), {
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
	// hidden
	document.getElementById("idCampaniaSeleccionada").value = param.idCampania.pk_id_tipo_campania;
	document.getElementById("idRegistroParam").value = param.idParametro;

	// radio
	const tipoEval = Number(param.tipoEvaluacion);
	const radios = document.getElementsByName("tipoEvaluacion");
	Array.from(radios).forEach(radio => {
		radio.checked = Number(radio.value) === tipoEval;
		radio.disabled = true;
	});

	// inputs numéricos
	document.getElementById("usoMaximo").value = param.usoMaximo ?? 0;
	document.getElementById("mopMaximo").value = param.mobMaximo ?? 0;
	document.getElementById("bcsMinimo").value = param.bcsMinimo ?? 0;
	document.getElementById("iccMinimo").value = param.iccMinimo ?? 0;

	// select
	//document.getElementById("idTipoArchivo").value = param.riesgoMaximo ?? 0;
	const seleccion = (param.riesgoMaximo ?? "").split(",");

	// Recorremos las opciones del select y las marcamos si están en la lista
	const selectElement = document.getElementById("idTipoArchivo");
	Array.from(selectElement.options).forEach(option => {
	if (seleccion.includes(option.value)) {
		option.selected = true;
	}
	});
}

function limpiarFormulario() {
	// hidden
	document.getElementById("idCampaniaSeleccionada").value = "";
	document.getElementById("idRegistroParam").value = "";
	document.getElementById("formParametrosBC").reset();
}

function obtenerIdCampaniaSeleccionada() {
	const nombreSeleccionado = document.getElementById("buscarCampania").value;
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

var dtosGuardarParamBC = "registroParamBC"
function guardarParametrosBC() {

	//if (validarCampo() > 1) {
		const form = document.getElementById('formParametrosBC');
		const formData = new FormData(form);

		// Opcional: deshabilitar botón para evitar doble envío
		document.getElementById('btnGuardarParamBC').disabled = true;


		swal({
			title: 'Procesando solicitud',
			text: 'Por favor espera',
			allowOutsideClick: false,
			buttons: false,
			closeOnClickOutside: false,
			icon: 'info',
		});

		fetch(getAbsolutePath() + dtosGuardarParamBC, {
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
				document.getElementById('btnGuardarParamBC').disabled = false;
			});
	/*} else {
		swal("Hay datos requeridos", {
			icon: "warning",
			closeOnClickOutside: false,
		});
	}*/


}
function validarCampo() {
	var pasaRegistro = 1;

	const radios = document.getElementsByName("tipoEvaluacion");

	const seleccionado = Array.from(radios).find(radio => radio.checked);
	
	if(document.getElementById("buscarCampania").value.trim() === ""){
		document.getElementById("buscarCampania").classList.add("is-invalid"); 
	
    pasaRegistro = 0;
	}else{
		document.getElementById("buscarCampania").classList.remove("is-invalid");
			if (seleccionado) {
		const valor = Number(seleccionado.value); // ← será 1 o 2

		if (valor === 1) {
			if (document.getElementById("usoMaximo").value <= 0 || document.getElementById("usoMaximo").value > 100) {
				document.getElementById("usoMaximo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("usoMaximo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}


			if (document.getElementById("bcsMinimo").value <= 0 || document.getElementById("bcsMinimo").value > 100) {
				document.getElementById("bcsMinimo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("bcsMinimo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}


			if (document.getElementById("iccMinimo").value <= 0 || document.getElementById("iccMinimo").value > 100) {
				document.getElementById("iccMinimo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("iccMinimo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}

			if (document.getElementById("idTipoArchivo").value == 0) {
				document.getElementById("idTipoArchivo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("idTipoArchivo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}


		} else if (valor === 2) {
			if (document.getElementById("usoMaximo").value <= 0 || document.getElementById("usoMaximo").value > 100) {
				document.getElementById("usoMaximo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("usoMaximo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}

			if (document.getElementById("mopMaximo").value <= 0 || document.getElementById("mopMaximo").value > 100) {
				document.getElementById("mopMaximo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("mopMaximo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}

			if (document.getElementById("bcsMinimo").value <= 0 || document.getElementById("bcsMinimo").value > 100) {
				document.getElementById("bcsMinimo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("bcsMinimo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}


			if (document.getElementById("iccMinimo").value <= 0 || document.getElementById("iccMinimo").value > 100) {
				document.getElementById("iccMinimo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("iccMinimo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}

			if (document.getElementById("idTipoArchivo").value == 0) {
				document.getElementById("idTipoArchivo").classList.add("is-invalid");
				pasaRegistro = 0;
			} else {
				document.getElementById("idTipoArchivo").classList.remove("is-invalid");
				pasaRegistro = 1;
			}
		}
	} else {
		swal("Debes seleccionar una opción de tipo de evaluación", {
			icon: "warning",
			closeOnClickOutside: false,
		});
		pasaRegistro = 0;
	}
	}
	


	// inputs numéricos
	/* document.getElementById("usoMaximo").value    = param.usoMaximo  ?? 0;
	 document.getElementById("mopMaximo").value    = param.mobMaximo  ?? 0;
	 document.getElementById("bcsMinimo").value    = param.bcsMinimo  ?? 0;
	 document.getElementById("iccMinimo").value    = param.iccMinimo  ?? 0;*/

	// select
	// document.getElementById("idTipoArchivo").value = param.riesgoMaximo  ?? 0;


	return pasaRegistro;
}
