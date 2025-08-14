// Variables | validacion de la liga absoluta
window.onload = function() {
	//tablaEmpresas();
	//listaEmpresas();
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
let idEmpresaSeleccionada = null;
let idProductoSeleccionado = null;
let tasaAnual = null; // Tasa de interés anual en %
let montoMin = 0;
let montoMax = 0;

// Esta función actualiza el datalist con las empresas
function listaEmpresas() {
	let buscarEmpresa = document.getElementById('buscarEmpresa');
	let opcionEmpresa = document.getElementById('opcionEmpresa');
	let spinner = document.getElementById('spinnerEmpresas');

	if (!buscarEmpresa || !opcionEmpresa || !spinner) {
		console.error("No se encontraron los elementos necesarios en el DOM.");
		return;
	}

	buscarEmpresa.disabled = true;
	spinner.style.display = "inline-block";

	$.ajax({
		url: urlPrincipal + "lista_empresas",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			opcionEmpresa.innerHTML = ''; // Limpia las opciones previas

			// Filtramos las opciones a medida que el usuario escribe
			let filtroTexto = buscarEmpresa.value.toLowerCase();
			respuesta.lista.filter(empresa => empresa.nombre.toLowerCase().includes(filtroTexto)).forEach(function(empresa) {
				let option = document.createElement('option');
				option.value = empresa.nombre;
				option.setAttribute('data-id', empresa.id);
				opcionEmpresa.appendChild(option);
			});

			buscarEmpresa.disabled = false;
			spinner.style.display = "none";
		},
		error: function(error) {
			console.error("Error al obtener empresas:", error);
			spinner.style.display = "none";
		}
	});
}

// Esta función se actualiza para la lista de empleados con el filtro de texto
function listaEmpleados(idEmpresa) {
	let buscarEmpleado = document.getElementById('buscarEmpleado');
	let opcionEmpleado = document.getElementById('opcionEmpleado');
	let spinnerEmpleado = document.getElementById('spinnerEmpleado');

	if (!buscarEmpleado || !opcionEmpleado || !spinnerEmpleado) {
		console.error("No se encontraron los elementos necesarios en el DOM.");
		return;
	}

	buscarEmpleado.disabled = true;
	spinnerEmpleado.style.display = "inline-block";

	$.ajax({
		url: urlPrincipal + "lista_empleados?empresaEmpleado=" + idEmpresa,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			let filtroTexto = buscarEmpleado.value.toLowerCase(); // Filtro según el input
			opcionEmpleado.innerHTML = ""; // Limpiar datalist previo

			respuesta.filter(empleado => empleado.nombreCompleto.toLowerCase().includes(filtroTexto))
				.forEach(empleado => {
					let option = document.createElement('option');
					option.value = empleado.nombreCompleto;
					option.setAttribute('data-id', empleado.clienteId);
					option.setAttribute('data-numEmp', empleado.numeroEmpleado);
					option.setAttribute('data-puesto', empleado.tipoEmpleado);
					opcionEmpleado.appendChild(option);
				});
		},
		error: function(error) {
			console.error("Error al obtener la lista de empleados:", error);
		},
		complete: function() {
			buscarEmpleado.disabled = false;
			spinnerEmpleado.style.display = "none";
		}
	});
}

// Esta función se actualiza para la lista de empleados con el filtro de texto
function listaProductos(idEmpresa, puesto) {
	let buscarProducto = document.getElementById('buscarProducto');
	let opcionProducto = document.getElementById('opcionProducto');
	let spinnerProducto = document.getElementById('spinnerProducto');

	if (!buscarProducto || !opcionProducto || !spinnerProducto) {
		console.error("No se encontraron los elementos necesarios en el DOM.");
		return;
	}

	buscarProducto.disabled = true;
	spinnerProducto.style.display = "inline-block";

	$.ajax({
		url: urlPrincipal + "lista_productos?empresa=" + idEmpresa + "&puesto=" + puesto,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			let filtroTexto = buscarProducto.value.toLowerCase(); // Filtro según el input
			opcionProducto.innerHTML = ""; // Limpiar datalist previo

			respuesta.filter(producto => producto.toLowerCase().includes(filtroTexto))
				.forEach(producto => {
					let option = document.createElement('option');
					option.value = producto; // Como solo es texto, lo asignamos directamente
					opcionProducto.appendChild(option);
				});
		},
		error: function(error) {
			console.error("Error al obtener la lista de productos:", error);
		},
		complete: function() {
			buscarProducto.disabled = false;
			spinnerProducto.style.display = "none";
		}
	});
}

document.getElementById("buscarProducto").addEventListener("input", function() {
	var buscarProductoValor = this.value;
	var productoSeleccionado = Array.from(document.querySelectorAll("#opcionProducto option"))
		.find(option => option.value === buscarProductoValor);

	if (productoSeleccionado && idEmpresaSeleccionada) { // Asegurar que la empresa ya esté seleccionada
		var producto = productoSeleccionado.value;
		idProductoSeleccionado = productoSeleccionado.value;
		listaPlazos(producto, idEmpresaSeleccionada);
		if (document.getElementById('puesto').value) {
			listaMontos(producto, idEmpresaSeleccionada, document.getElementById('puesto').value);
		}

	} else {
		console.warn("No se ha seleccionado una empresa válida antes de elegir el producto.");
	}
});


function listaPlazos(producto, idEmpresa) {
	let container = document.getElementById("plazoCreditoContainer");
	let spinnerPlazo = document.getElementById("spinnerPlazo");
	const selectFrecuencia = document.getElementById("frecuenciaCredito");

	if (!container || !spinnerPlazo) {
		console.error("No se encontraron los elementos del DOM para el campo de plazo.");
		return;
	}

	// Caso especial: producto "NOMINA PRESTACIONES"
	if (producto.trim().toUpperCase() === "NOMINA PRESTACIONES") {
		selectFrecuencia.value = "UNICO"; // O "DIARIA" si así está definido en tu <option>
		selectFrecuencia.disabled = true; // Opcional: desactiva el select para evitar que lo cambien
		// Reemplaza el select por un input number
		container.innerHTML = `
			<input type="number" class="form-control" id="plazoCredito" placeholder="Plazo en días" min="1">
		`;
		spinnerPlazo.style.display = "none";
		return;
	}

	// Si no es "NOMINA PRESTACIONES", usar el select
	container.innerHTML = `
		<select class="form-select" id="plazoCredito" disabled>
			<option value="" disabled selected>Seleccione...</option>
		</select>
	`;

	let selectPlazo = document.getElementById("plazoCredito");
	selectPlazo.disabled = true;
	spinnerPlazo.style.display = "inline-block";

	$.ajax({
		url: urlPrincipal + "lista_plazos?producto=" + encodeURIComponent(producto) + "&empresa=" + idEmpresa,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			selectPlazo.innerHTML = '<option value="" disabled selected>Seleccione...</option>';

			respuesta.forEach(plazo => {
				let option = document.createElement("option");
				option.value = plazo.split(" ")[0];
				option.textContent = plazo;
				selectPlazo.appendChild(option);
			});

			selectPlazo.disabled = false;
		},
		error: function(error) {
			console.error("Error al obtener la lista de plazos:", error);
		},
		complete: function() {
			spinnerPlazo.style.display = "none";
		}
	});
}




$(document).ready(function() {
	$('#solicitudModal').on('shown.bs.modal', function() {
		$('#frecuenciaCredito').val('0');
		listaEmpresas();
		//document.getElementById('idSolicitud').value = null;
		//llenarSelectPuestos();
	});
});


// Detectar cuando el usuario selecciona una opción del datalist
document.getElementById("buscarEmpresa").addEventListener("input", function() {
	var buscarEmpresaValor = this.value;
	var empresaSeleccionada = Array.from(document.querySelectorAll("#opcionEmpresa option"))
		.find(option => option.value === buscarEmpresaValor);

	if (empresaSeleccionada) {
		idEmpresaSeleccionada = empresaSeleccionada.getAttribute("data-id"); // Guardamos el ID
		listaEmpleados(idEmpresaSeleccionada);
		listaProductos(idEmpresaSeleccionada, 1111);
		llenarSelectPuestos(idEmpresaSeleccionada);
	} else {
		idEmpresaSeleccionada = null; // Reiniciar si no se selecciona nada
	}
});



function llenarSelectPuestos(idEmpresaSeleccionada) {
	let selectPuesto = document.getElementById('puesto');

	if (!selectPuesto) {
		console.error("No se encontró el select de puesto en el DOM.");
		return;
	}

	// Mostrar mensaje de "Cargando..."
	selectPuesto.innerHTML = '<option value="" disabled selected>Cargando...</option>';

	$.ajax({
		url: urlPrincipal + "obtener_puestos?empresa=" + idEmpresaSeleccionada,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// Limpiar el select
			selectPuesto.innerHTML = '';

			if (respuesta.length > 0) {
				// Opción inicial por defecto
				let opcionInicial = document.createElement('option');
				opcionInicial.value = '';
				opcionInicial.textContent = 'Seleccione un puesto...';
				opcionInicial.disabled = true;
				opcionInicial.selected = true;
				selectPuesto.appendChild(opcionInicial);

				// Agregar los puestos
				respuesta.forEach(puesto => {
					let optionPuesto = document.createElement('option');
					optionPuesto.value = puesto.pkIdTipo;
					optionPuesto.textContent = puesto.descripcion;
					selectPuesto.appendChild(optionPuesto);
				});
			} else {
				// Si no hay puestos disponibles
				let optionPuesto = document.createElement('option');
				optionPuesto.value = '0';
				optionPuesto.textContent = 'TODOS';
				optionPuesto.selected = true;
				selectPuesto.appendChild(optionPuesto);
				selectPuesto.value = '0';
			}
		},
		error: function(error) {
			console.error("Error al obtener la lista de puestos:", error);
			selectPuesto.innerHTML = '<option value="" disabled selected>Error al cargar</option>';
		}
	});
}


document.getElementById('buscarEmpleado').addEventListener('input', function() {
	let buscarEmpleado = document.getElementById('buscarEmpleado');
	// Convertir a mayúsculas
	buscarEmpleado.value = buscarEmpleado.value.toUpperCase();
	let opcionEmpleado = document.getElementById('opcionEmpleado');
	let inputNumEmpleado = document.getElementById('numEmpleado'); // Campo de número de empleado
	let inputPuesto = document.getElementById('puesto'); // Campo de puesto

	// Buscar la opción seleccionada
	let selectedOption = Array.from(opcionEmpleado.children).find(option => option.value === buscarEmpleado.value);

	if (selectedOption) {
		// Rellenar los campos con la información de la opción seleccionada
		inputNumEmpleado.value = selectedOption.getAttribute('data-numEmp') || "";
		inputPuesto.value = selectedOption.getAttribute('data-puesto') || "";
		console.log(inputPuesto.value);
		if (inputPuesto.value == "") {
			inputPuesto.value = 0;
		}

		//listaProductos(idEmpresaSeleccionada, selectedOption.getAttribute('data-puesto'));
	} else {
		// Limpiar los campos si no hay selección válida
		inputNumEmpleado.value = "";
		inputPuesto.value = "";
	}
});

function listaMontos(producto, idEmpresa, puesto) {
	var urlMonto = "";
	urlMonto = urlPrincipal + `condiciones?producto=${producto}&empresa=${idEmpresa}&tipoemp=${puesto ?? 0}`;

	// Hacer la solicitud GET al endpoint
	fetch(urlMonto)
		.then(response => {
			if (!response.ok) {
				throw new Error('No se encontraron condiciones para los parámetros especificados.');
			}
			return response.json();
		})
		.then(data => {

			// Obtener valores de monto mínimo y máximo de la API
			montoMin = data.montoMin;
			montoMax = data.montoMax;
			tasaAnual = data.tasa;

			// Buscar el input por ID
			const montoInput = document.getElementById('montoSolicitado');

			if (montoInput) {
				montoInput.value = montoMin; // Asigna el valor mínimo
				montoInput.min = montoMin;   // Establece el mínimo permitido
				montoInput.max = montoMax;   // Establece el máximo permitido

				// Validación cuando el usuario salga del input
				montoInput.addEventListener('blur', function() {
					let valor = parseFloat(montoInput.value);

					if (valor < montoMin || valor > montoMax) {
						Swal.fire({
							icon: 'error',
							title: 'Monto inválido',
							text: `El monto permitido es de $ ${montoMin} y hasta $ ${montoMax}`,
							confirmButtonColor: '#3085d6',
						}).then(() => {
							montoInput.value = montoMin; // Restablece el valor mínimo
						});
					}

				});
			}
		})
		.catch(error => {
			console.error('Error al obtener las condiciones:', error);
			console.error(error.message);
		});
}

document.getElementById('puesto').addEventListener('change', function() {
	let puestoSeleccionado = this.value; // Obtén el valor de la opción seleccionada

	listaMontos(idProductoSeleccionado, idEmpresaSeleccionada, puestoSeleccionado);

});

/*
function actualizarMonto(){
		const monto = parseFloat(document.getElementById('montoSolicitado').value) || 0;
		const plazoInicial = document.getElementById('plazoCredito').value;
		const frecuencia = document.getElementById('frecuenciaCredito').value;
		const plazo = plazoInicial;

		// Llamar a la función de plan de pagos
		generarPlanPagos(monto, tasaAnual, plazo, frecuencia);

}*/

function actualizarPP(montoSol) {
	const plazoInicial = document.getElementById('plazoCredito').value
	const frecuencia = document.getElementById('frecuenciaCredito').value
	const plazo = plazoInicial

	// Llamar a la función de plan de pagos
	const planDePagos = generarPlanPagos(montoSol, tasaAnual, plazo, frecuencia);

	// Aquí puedes agregar una función para mostrar los resultados en HTML si es necesario
}

document.getElementById('montoSolicitado').addEventListener('change', function() {

	const monto = parseFloat(this.value) || 0; // Obtener el monto ingresado
	actualizarPP(monto);


});

document.getElementById('montoSolicitado').addEventListener('blur', function() {
	const monto = parseFloat(this.value) || 0; // Obtener el monto ingresado
	actualizarPP(monto);
});

document.getElementById('montoSolicitado').addEventListener('input', function() {
	const monto = parseFloat(this.value) || 0; // Obtener el monto ingresado
	actualizarPP(monto);
});



function generarPlanPagos(monto, tasaAnual, plazo, frecuencia) {
	let diasPorPago;
	let pagosPorAnio;
	let totalPagos;

	switch (frecuencia.toLowerCase()) {
		case "4":
			diasPorPago = 30;
			pagosPorAnio = 12;
			totalPagos = plazo;
			break;
		case "3":
			diasPorPago = 15;
			pagosPorAnio = 24;
			totalPagos = plazo * 2;
			break;
		case "1":
			diasPorPago = 7;
			pagosPorAnio = 52;
			totalPagos = Math.round((plazo * 52) / 12);
			break;
		case "2":
			diasPorPago = 14;
			pagosPorAnio = Math.round(360 / 14); // ≈26
			totalPagos = Math.round((plazo * pagosPorAnio) / 12);
			break;
		case "5":
			diasPorPago = plazo;
			break;
		default:
			console.error("Frecuencia no válida");
			return;
	}

	// PAGO ÚNICO
	if (frecuencia.toLowerCase() === "unico") {
		const tasaAnualDecimal = tasaAnual / 100;
		const interes = (monto * tasaAnualDecimal * plazo) / 360;
		const iva = interes * 0.16;
		const total = monto + interes + iva;
		const cuotaFija = total.toFixed(2);

		const inputMontoDescontar = document.querySelector("#montoDescontar");
		if (inputMontoDescontar) inputMontoDescontar.value = cuotaFija;

		return cuotaFija;
	}

	// PAGO PERIÓDICO
	const tasaPeriodica = (tasaAnual / 100) / pagosPorAnio;

	// Cuota sin IVA
	const cuotaSinIVA = monto * (tasaPeriodica / (1 - Math.pow(1 + tasaPeriodica, -totalPagos)));
	const totalSinIVA = cuotaSinIVA * totalPagos;
	const interesTotal = totalSinIVA - monto;
	const ivaTotal = interesTotal * 0.16;
	const totalConIVA = totalSinIVA + ivaTotal;

	// Calcular cuota ideal con decimales completos
	const cuotaExacta = totalConIVA / totalPagos;

	// Redondear cuota a 2 decimales
	const cuotaRedondeada = parseFloat(cuotaExacta.toFixed(2));

	// Calcular el total redondeado que se pagará si todas las cuotas son iguales
	const totalRedondeado = cuotaRedondeada * totalPagos;

	// Diferencia por redondeo
	const diferencia = totalConIVA - totalRedondeado;

	// Ajustar la diferencia repartida entre todas las cuotas
	const ajustePorCuota = diferencia / totalPagos;

	// Cuota final ajustada para que sumen exactamente el total
	const cuotaFinal = parseFloat((cuotaRedondeada + ajustePorCuota).toFixed(2));

	const inputMontoDescontar = document.querySelector("#montoDescontar");
	if (inputMontoDescontar) inputMontoDescontar.value = cuotaFinal;

	return cuotaFinal;
}

document.addEventListener("DOMContentLoaded", function() {
	limpiarModal();
	document.getElementById("btnGuardarSolicitud").addEventListener("click", function() {
		guardarSolicitud();
	});

});

// Evento para pasar a mayúsculas el textarea de comentarios
document.getElementById('comentariosSolicitud').addEventListener('input', function () {
    this.value = this.value.toUpperCase();
});

function limpiarModal() {
	const modal = document.getElementById('solicitudModal');

	modal.addEventListener('show.bs.modal', function() {
		// Limpia todos los inputs, selects y textareas dentro del modal
		const inputs = modal.querySelectorAll('input, select, textarea');

		inputs.forEach(input => {
			if (input.type === 'checkbox' || input.type === 'radio') {
				input.checked = false;
			} else {
				input.value = '';
			}
		});
	});
}


function guardarSolicitud() {

	document.querySelectorAll('#solicitudModal input, #solicitudModal button, #solicitudModal select, #solicitudModal textarea')
		.forEach(el => el.disabled = true);
	const fechaActual = new Date().toISOString().split("T")[0];

	// Función auxiliar para obtener valores de inputs de manera segura
	function getInputValue(id, defaultValue = null) {
		const input = document.getElementById(id);
		if (!input) {
			console.error(`⚠️ Error: No se encontró el input con id '${id}'`);
			return defaultValue;
		}
		return input.value.trim() || defaultValue;
	}

	const esRenovacion = document.getElementById("checkNomiprestamo")?.checked ? "SI" : "NO";

	/*	let montoDescontarValue = getInputValue("montoDescontar");
	let montoDescontarParsed = parseFloat(montoDescontarValue);
	if (isNaN(montoDescontarParsed)) {
		// Si no hay valor, intenta obtener el texto visible (por ejemplo, de un <span> o similar)
		const montoDOM = document.querySelector("#montoDescontar")?.textContent?.trim();
		montoDescontarParsed = parseFloat(montoDOM) || null;
	}*/


	const solicitudData = {
		id: parseInt(getInputValue("idSolicitud", 0)), // Tomar el ID si existe
		empresa: getInputValue("buscarEmpresa"),
		empleado: getInputValue("buscarEmpleado"),
		producto: getInputValue("buscarProducto"),
		numEmpleado: getInputValue("numEmpleado"),
		puesto: parseInt(getInputValue("puesto")) || 0,
		plazo: parseInt(getInputValue("plazoCredito")) || null,
		frecuencia: getInputValue("frecuenciaCredito"),
		montoSolicitado: parseFloat(getInputValue("montoSolicitado")) || null,
		montoDescontar: (() => {
			const val = parseFloat(document.getElementById("montoDescontar")?.value.trim());
			return isNaN(val) ? null : val;
		})(),
		fechaSolicitud: fechaActual,
		renovacion: esRenovacion,
		comentarios: getInputValue("comentariosSolicitud")
	};



	let validacion = 0;
	if (!solicitudData.empresa) {
		document.getElementById("buscarEmpresa").classList.add("is-invalid");
		validacion = 1;
	} else {
		document.getElementById("buscarEmpresa").classList.remove("is-invalid");
		validacion = 0;
	}
	if (!solicitudData.empleado) {
		document.getElementById("buscarEmpleado").classList.add("is-invalid");
		validacion = 1;
	} else {
		document.getElementById("buscarEmpleado").classList.remove("is-invalid");
		validacion = 0;
	}

	if (!solicitudData.producto) {
		document.getElementById("buscarProducto").classList.add("is-invalid");
		validacion = 1;
	} else {
		document.getElementById("buscarProducto").classList.remove("is-invalid");
		validacion = 0;
	}

	if (!solicitudData.numEmpleado) {
		document.getElementById("numEmpleado").classList.add("is-invalid");
		validacion = 1;
	} else {
		document.getElementById("numEmpleado").classList.remove("is-invalid");
		validacion = 0;
	}

	if (!solicitudData.montoSolicitado) {
		document.getElementById("montoSolicitado").classList.add("is-invalid");
		validacion = 1;
	} else {
		document.getElementById("montoSolicitado").classList.remove("is-invalid");
		validacion = 0;
	}



	if (!solicitudData.montoDescontar) {
		document.getElementById("montoDescontar").classList.add("is-invalid");
		validacion = 1;
	} else {
		document.getElementById("montoDescontar").classList.remove("is-invalid");
		validacion = 0;
	}

	if (validacion == 1) {
		Swal.fire({
			icon: "warning",
			title: "Campos incompletos",
			text: "Por favor, completa todos los campos obligatorios.",
			confirmButtonColor: "#ffc107",
		}).then(() => {
			// Este bloque se ejecuta después de que el usuario da clic en "Aceptar"
			document.querySelectorAll('#solicitudModal input, #solicitudModal button, #solicitudModal select, #solicitudModal textarea')
				.forEach(el => el.disabled = false);
		});

		return;
	}

	// Validar campos obligatorios
	/*if (!solicitudData.empresa || !solicitudData.empleado || !solicitudData.producto || !solicitudData.numEmpleado || !solicitudData.montoSolicitado || !solicitudData.montoDescontar) {
		Swal.fire({
			icon: "warning",
			title: "Campos incompletos",
			text: "Por favor, completa todos los campos obligatorios.",
			confirmButtonColor: "#ffc107",
		});
		return;
	}*/

	const url = solicitudData.id
		? urlPrincipal + `editarSolicitud/${solicitudData.id}`
		: urlPrincipal + "crearSolicitud";
	const metodo = solicitudData.id ? "PUT" : "POST";

	fetch(url, {
		method: metodo,
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(solicitudData)
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error al guardar la solicitud");
			}
			return response.json();
		})
		.then(data => {
			Swal.fire({
				icon: "success",
				title: "Éxito",
				text: `Solicitud ${solicitudData.id ? "actualizada" : "guardada"} correctamente`,
				confirmButtonColor: "#3085d6",
			}).then(() => {
				resetearModal();
				cargarSolicitudes(paginaActual);
			});

			document.querySelectorAll('#solicitudModal input, #solicitudModal button, #solicitudModal select, #solicitudModal textarea')
				.forEach(el => el.disabled = false);
		})
		.catch(error => {
			console.error("❌ Error:", error);
			Swal.fire({
				icon: "error",
				title: "Error",
				text: "No se pudo guardar la solicitud",
				confirmButtonColor: "#d33",
			});
		});
}



// 🔥 Función para resetear el formulario y cerrar el modal
function resetearModal() {
	// Resetear formulario
	document.getElementById("solicitudForm").reset();

	// Ocultar el modal correctamente
	let modalElement = document.getElementById("solicitudModal");
	let modalInstance = bootstrap.Modal.getInstance(modalElement);

	if (modalInstance) {
		modalInstance.hide();
	}

	document.getElementById('idSolicitud').value = null;

	// Asegurar que se eliminen los backdrops residuales
	// Eliminar los backdrops residuales y restaurar el estado
	setTimeout(() => {
		let backdrops = document.querySelectorAll('.modal-backdrop');
		backdrops.forEach(backdrop => backdrop.remove());

		// Restaurar el estado del body
		document.body.classList.remove('modal-open');
		document.body.style.overflow = '';
	}, 300);
}


let paginaActual = 0;
const tamañoPagina = 10; // Registros por página

document.addEventListener("DOMContentLoaded", function() {
	cargarSolicitudes(paginaActual);

	//cargarEmpresasFiltro();

	/*document.getElementById("btnAnterior").addEventListener("click", function() {
		if (paginaActual > 0) {
			paginaActual--;
			cargarSolicitudes(paginaActual);
		}
	});

	document.getElementById("btnSiguiente").addEventListener("click", function() {
		paginaActual++;
		cargarSolicitudes(paginaActual);
	});*/
});

function obtenerEstatus(estatus) {
	switch (estatus) {
		case 1: return "En Revision";
		case 2: return "Enviada";
		case 3: return "Aprobada";
		case 4: return "Rechazada";
		case 5: return "Aceptada";
		case 0: return "Cancelada";
		default: return "Desconocido";
	}
}


function cargarSolicitudes(pagina) {
	// Mostrar spinner
	//document.getElementById("spinner").style.display = "block";

	fetch(urlPrincipal + `listarSolicitudes`)
		.then(response => response.json())
		.then(data => {
			//let tabla = document.getElementById("tablaSolicitudes");
			//tabla.innerHTML = ""; // Limpiar tabla antes de agregar nuevas filas

			const solicitudes = data.contenido; // Cambiado
			const permiso = data.permiso; // Puedes usar esto si quieres condicionar algo
			const botonExcel = document.getElementById("contenedorBotonExcel");

			console.log("Permiso:", permiso); // Diagnóstico

			if (permiso === true) {
				botonExcel.classList.remove("d-none");
				botonExcel.classList.add("d-flex");
			} else {
				botonExcel.classList.remove("d-flex");
				botonExcel.classList.add("d-none");
			}
			const registrosTotalesSol = [];
			tablaSolicitudes.clear().draw();
			$.each(solicitudes, function(idx, opt) {
				// Botón editar
				let btnEditar = `<button class='btn btn-warning btn-sm btn-editar' title='Editar Solicitud' data-id='${opt.id}' data-tooltip='Editar'>
        <i class='fas fa-edit'></i></button>`;

				// Botón aprobar/cancelar si estatus === 1
				let btnAprobarCancelar = '';
				if (opt.estatus === 1) {
					btnAprobarCancelar = `<button class='btn btn-primary btn-sm btn-aprobar-cancelar' title='Aprobar o Cancelar' data-tooltip='Aprobar/Cancelar'
            onclick="aprobarOCancelarSolicitud(${opt.id}, '${opt.empresa}')">
            <i class='fas fa-check-circle'></i> / <i class='fas fa-times-circle'></i>
        </button>`;
				}

				// Botón PDF si estatus === 3
				let btnPdf = '';
				if (opt.estatus === 3) {
					btnPdf = `<button class='btn btn-success btn-sm btn-pdf' title='Ver PDF' data-id='${opt.id}' data-tooltip='Ver PDF'>
            <i class='fas fa-file-pdf'></i>
        </button>`;
				}

				// Concatenar botones
				let acciones = '';
				if (permiso) {
					acciones = btnEditar + btnAprobarCancelar;
				}
				acciones += btnPdf;

				// Agregar fila a DataTable
				registrosTotalesSol.push([
					opt.id,
					opt.empleado,
					opt.numEmpleado,
					opt.fechaSolicitud,
					opt.empresa,
					opt.montoSolicitado,
					opt.montoDescontar,
					opt.usuarioReg.vc_nombre_usuario,
					opt.sucursal,
					obtenerEstatus(opt.estatus),
					acciones
				]);
			});

			// Add all rows at once
			tablaSolicitudes.rows.add(registrosTotalesSol).draw();

			/*
						let filas = solicitudes.map((solicitud) => {
							return `<tr>
								<td>${solicitud.id}</td>
								<td>${solicitud.empleado}</td>
								<td>${solicitud.fechaSolicitud}</td>
								<td>${solicitud.empresa}</td>
								<td>$ ${solicitud.montoSolicitado}</td>
								<td>$ ${solicitud.montoDescontar}</td>
								<td>${solicitud.usuarioReg.vc_nombre_usuario}</td>
								<td>${solicitud.sucursal}</td>
								<td>${obtenerEstatus(solicitud.estatus)}</td>
								<td>
									${permiso ? `
										<button class="btn btn-warning btn-sm btn-editar" data-id="${solicitud.id}">
											<i class="fas fa-edit"></i>
										</button>
										${solicitud.estatus === 1 ? `
											<button class="btn btn-primary btn-sm btn-aprobar-cancelar"
												onclick="aprobarOCancelarSolicitud(${solicitud.id}, '${solicitud.empresa}')">
												<i class="fas fa-check-circle"></i> / <i class="fas fa-times-circle"></i>
											</button>` : ''}
									` : ''}
								    
									${solicitud.estatus === 3 ? `
										<button class="btn btn-success btn-sm btn-pdf" data-id="${solicitud.id}">
											<i class="fas fa-file-pdf"></i>
										</button>` : ''}
								</td>
							</tr>`;
						}).join('');
			
			
						tabla.innerHTML = filas;
			
						// Actualizar paginación
						document.getElementById("paginaActual").innerText = `Página ${pagina + 1}`;
						document.getElementById("btnAnterior").disabled = pagina === 0;
						document.getElementById("btnSiguiente").disabled = data.contenido.last; // Cambiado
			*/





			// Ocultar spinner
			//document.getElementById("spinner").style.display = "none";

			// (Opcional) Si quieres condicionar algún botón con permiso:
			if (permiso) {
				console.log("Tiene permiso de aprobación.");
				// document.getElementById("btnAprobarMasivo").style.display = "inline-block";
			} else {
				console.log("No tiene permiso.");
				// document.getElementById("btnAprobarMasivo").style.display = "none";
			}
		})
		.catch(error => {
			console.error("Error al cargar las solicitudes:", error);
			//document.getElementById("spinner").style.display = "none";
		});
}



function aprobarOCancelarSolicitud(id, empresa) {
	swal.fire({
		title: '¿Qué deseas hacer con esta solicitud?',
		text: `ID de solicitud: ${id}`,
		showDenyButton: true,
		showCancelButton: true,
		confirmButtonText: 'Aprobar',
		denyButtonText: `Cancelar`,
		cancelButtonText: 'Cerrar'
	}).then((result) => {
		if (result.isConfirmed) {
			// Lógica para aprobar
			fetch(urlPrincipal + `aprobarSolicitud/${id}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					usuario: 'admin123',
					observacion: ''
				})
			})
				.then(res => {
					if (!res.ok) throw new Error("Error al aprobar la solicitud");
					return res.text();
				})
				.then(() => {
					// Enviar correo después de aprobar
					return fetch(urlPrincipal + `envioCorreo/${empresa}`, {
						method: 'POST'
					});
				})
				.then(res => {
					if (!res.ok) throw new Error("Error al enviar el correo");
					return res.text();
				})
				.then(() => {
					Swal.fire('¡Aprobada!', 'La solicitud fue aprobada y se envió el correo.', 'success');
					cargarSolicitudes(0);
				})
				.catch(error => {
					console.error(error);
					Swal.fire('Error', 'No se pudo aprobar la solicitud o enviar el correo.', 'error');
				});
		} else if (result.isDenied) {
			// Lógica para cancelar
			fetch(urlPrincipal + `cancelarSolicitud/${id}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					usuario: 'admin123',
					observacion: ''
				})
			})
				.then(res => res.text())
				.then(resp => {
					Swal.fire('Cancelada', 'La solicitud fue cancelada.', 'info');
					cargarSolicitudes(0);
				})
				.catch(error => {
					console.error(error);
					Swal.fire('Error', 'No se pudo cancelar la solicitud.', 'error');
				});
		}
	});
}

// Manejo de eventos de botones (Event Delegation)
document.addEventListener("click", function(event) {
	if (event.target.closest(".btn-pdf")) {
		let id = event.target.closest(".btn-pdf").getAttribute("data-id");
		generarReporte(id);
	}
});

$(document).on("click", ".btn-editar", function() {
	var solicitudId = $(this).data("id"); // Obtener el ID de la solicitud

	// Obtener la solicitud por ID
	$.ajax({
		url: urlPrincipal + `solicitud/${solicitudId}`,
		type: "GET",
		success: function(dataSolicitud) {

			// Obtener la empresa asociada a la solicitud
			$.ajax({
				url: urlPrincipal + `empresa/${dataSolicitud.empresa}`,
				type: "GET",
				success: function(dataEmpresa) {
					idEmpresaSeleccionada = dataEmpresa.id
					$("#solicitudModal").modal("show");
					// Ejecutar todas las funciones necesarias antes de abrir el modal
					Promise.all([
						listaEmpresas(),
						listaEmpleados(dataEmpresa.id),
						listaProductos(dataEmpresa.id, 1111),
						llenarSelectPuestos(dataEmpresa.id),
						listaPlazos(dataSolicitud.producto, idEmpresaSeleccionada),
						listaMontos(dataSolicitud.producto, idEmpresaSeleccionada, dataSolicitud.puesto)
					]).then(() => {

						// Llenar los campos con la información de la solicitud
						$("#idSolicitud").val(dataSolicitud.id);
						$("#numEmpleado").val(dataSolicitud.numEmpleado);
						//$("#plazoCredito").val(dataSolicitud.plazo);
						$("#frecuenciaCredito").val(dataSolicitud.frecuencia);
						$("#montoDescontar").val(dataSolicitud.montoDescontar);

						// Seleccionar el puesto en el select (con retraso para asegurarnos de que ya está cargado)
						setTimeout(() => {
							$("#puesto").val(dataSolicitud.puesto).change();
							$("#plazoCredito").val(dataSolicitud.plazo).change();
							$("#montoSolicitado").val(dataSolicitud.montoSolicitado);
						}, 500);

						// 🛠 **Forzar actualización de los campos tipo search**
						setTimeout(() => {
							actualizarDatalist("#buscarEmpresa", dataEmpresa.nombre);
							actualizarDatalist("#buscarEmpleado", dataSolicitud.empleado);
							actualizarDatalist("#buscarProducto", dataSolicitud.producto);

							// Mostrar campo renovación solo si el producto es NOMIPRESTAMO
							if (dataSolicitud.producto.toUpperCase() === "NOMIPRESTAMO") {
								$("#checkboxNomiPrestamoRow").show();
								if (dataSolicitud.renovacion === "SI") {
									$("#checkNomiprestamo").prop("checked", true);
								} else {
									$("#checkNomiprestamo").prop("checked", false);
								}
							} else {
								$("#checkboxNomiPrestamoRow").hide();
								$("#checkNomiprestamo").prop("checked", false); // Limpiar si no aplica
							}

							// Comentarios
							$("#comentariosSolicitud").val(dataSolicitud.comentarios || "");
						}, 300);

						// Ahora sí, abrir el modal
						$("#solicitudModal").modal("show");
					}).catch(error => {
						console.error("Error al cargar datos:", error);
						console.error("Hubo un problema al cargar la información.");
					});

				},
				error: function() {
					console.error("Error al obtener la empresa");
				}
			});
		},
		error: function() {
			console.error("Error al obtener la solicitud");
		}
	});
});

// **Función para actualizar el `datalist` después de asignar un valor**
function actualizarDatalist(selector, valor) {
	let input = $(selector);
	input.val(""); // Limpia el campo para evitar que el evento sea ignorado
	setTimeout(() => {
		input.val(valor).trigger("input").trigger("focus"); // Dispara eventos manualmente
	}, 100);
}

// Escuchar el evento de cierre del modal
const modalElement = document.getElementById('solicitudModal');
modalElement.addEventListener('hidden.bs.modal', resetearModal);

function cargarEmpresasFiltro() {
	const spinner = document.getElementById("spinnerEmpresas");
	const listaEmpresas = document.getElementById("listaEmpresas"); // Datalist

	spinner.style.display = "inline-block"; // Mostrar spinner mientras carga

	fetch(urlPrincipal + "lista_empresas")
		.then(response => {
			if (!response.ok) {
				throw new Error("Error al obtener las empresas");
			}
			return response.json();
		})
		.then(data => {

			if (!data.lista || !Array.isArray(data.lista)) {
				throw new Error("'lista' no es un array válido.");
			}

			listaEmpresas.innerHTML = ""; // Limpiar opciones previas

			data.lista.forEach(empresa => {
				let option = document.createElement("option");
				option.value = empresa.nombre; // Usa "nombre" según la estructura de la API
				listaEmpresas.appendChild(option);
			});

			spinner.style.display = "none"; // Ocultar spinner al finalizar
		})
		.catch(error => {
			console.error("Error al cargar las empresas:", error);
			spinner.style.display = "none"; // Ocultar spinner si hay error
		});
}

document.getElementById("uploadExcelButton").addEventListener("click", function() {
	let fileInput = document.getElementById("excelFile");
	let file = fileInput.files[0];

	if (!file) {
		Swal.fire({
			icon: "error",
			title: "Archivo no seleccionado",
			text: "Por favor selecciona un archivo antes de subirlo.",
		});
		return;
	}

	let formData = new FormData();
	formData.append("file", file);

	// Mostrar alerta de carga con tiempo máximo
	Swal.fire({
		title: "Subiendo archivo...",
		text: "Por favor espera mientras se procesa.",
		allowOutsideClick: false,
		allowEscapeKey: false,
		didOpen: () => {
			Swal.showLoading();
		}
	});

	// Cerrar Swal después de 15 segundos si no hay respuesta
	let swalTimeout = setTimeout(() => {
		Swal.close();
	}, 15000);

	fetch(urlPrincipal + "upload", {
		method: "POST",
		body: formData
	})
		.then(response => response.text())
		.then(data => {
			clearTimeout(swalTimeout); // Cancelar el cierre automático si ya hay respuesta
			console.log("Respuesta del backend:", data);

			Swal.close(); // Cerrar el loading de Swal

			// Mostrar mensaje de éxito
			Swal.fire({
				icon: "success",
				title: "Archivo subido correctamente",
				text: data,
			}).then(() => {
				// Limpiar input
				fileInput.value = "";

				// Recargar datos
				cargarSolicitudes(paginaActual);
			});
		})
		.catch(error => {
			clearTimeout(swalTimeout); // Cancelar el cierre automático si ya hay error
			console.error("Error al subir el archivo:", error);

			Swal.close(); // Cerrar el loading de Swal

			// Mostrar mensaje de error
			Swal.fire({
				icon: "error",
				title: "Error al subir el archivo",
				text: "Hubo un problema al procesar el archivo.",
			});
		});
});


function generarReporte(id) {

	$.ajax({
		url: `${urlPrincipal}reporte_gestion/${id}`, // Corregido
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {
			if (respuesta.length > 0) {
				let urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0];
				window.open(urlReporte, "_blank");
			} else {
				swal({
					title: "Error",
					text: "No se recibió una URL válida.",
					icon: "warning",
				});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error",
				text: "Se presentó un error, intente más tarde.",
				icon: "warning",
			});
		}
	});
}

document.getElementById("buscarProducto").addEventListener("change", function() {
	const valor = this.value.trim().toUpperCase();
	const checkboxRow = document.getElementById("checkboxNomiPrestamoRow");

	if (valor === "NOMIPRESTAMO") {
		checkboxRow.style.display = "block";
	} else {
		checkboxRow.style.display = "none";
		document.getElementById("checkNomiprestamo").checked = false;
	}
});




















