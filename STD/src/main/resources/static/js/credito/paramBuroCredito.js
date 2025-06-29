/**
 * juan.trejo
 */
// @ts-nocheck

function toggleMopMaximo() {
	const tipo = document.querySelector('input[name="tipoEvaluacion"]:checked').value;
	const mopMaxRow = document.getElementById('mopMaximoRow');

	if (tipo === '1') {
		mopMaxRow.style.display = 'none';
	} else {
		mopMaxRow.style.display = 'flex'; // usa 'flex' o 'block' según el diseño
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
  });

  // inputs numéricos
  document.getElementById("usoMaximo").value    = param.usoMaximo  ?? 0;
  document.getElementById("mopMaximo").value    = param.mobMaximo  ?? 0;
  document.getElementById("bcsMinimo").value    = param.bcsMinimo  ?? 0;
  document.getElementById("iccMinimo").value    = param.iccMinimo  ?? 0;

  // select
  document.getElementById("idTipoArchivo").value = param.riesgoMaximo  ?? 0;

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
function guardarParametrosBC(){
	const form = document.getElementById('formParametrosBC');
	const formData = new FormData(form);

	// Opcional: deshabilitar botón para evitar doble envío
	document.getElementById('btnGuardarParamBC').disabled = true;

	fetch(getAbsolutePath() + dtosGuardarParamBC, {
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
			console.log("Error:", error);
			alert("Ocurrió un error al guardar la campaña");
		})
		.finally(() => {
			document.getElementById('btnGuardarParamBC').disabled = false;
		});
}


