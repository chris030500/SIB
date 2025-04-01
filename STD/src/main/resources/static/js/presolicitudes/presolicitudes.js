// Variables | validacion de la liga absoluta
window.onload = function() {
	//tablaEmpresas();
	//listaEmpresas();
}

var urlPrincipal = "http://localhost:8090/STD/configuracionSolicitudes/";
let idEmpresaSeleccionada = null;
let idProductoSeleccionado = null;
let tasaAnual = null; // Tasa de interés anual en %

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
			console.log("Lista de empleados recibida:", respuesta);

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

	console.log(idEmpresa)
	console.log(puesto)
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
			console.log("Lista de productos recibida:", respuesta);

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
		console.log("Producto seleccionado:", producto, "Empresa ID:", idEmpresaSeleccionada);
		listaPlazos(producto, idEmpresaSeleccionada);
		if (document.getElementById('puesto').value) {
			listaMontos(producto, idEmpresaSeleccionada, document.getElementById('puesto').value);
		}

	} else {
		console.warn("No se ha seleccionado una empresa válida antes de elegir el producto.");
	}
});


function listaPlazos(producto, idEmpresa) {
	let selectPlazo = document.getElementById("plazoCredito");
	let spinnerPlazo = document.getElementById("spinnerPlazo");

	if (!selectPlazo || !spinnerPlazo) {
		console.error("No se encontraron los elementos del DOM para el select de plazos.");
		return;
	}

	selectPlazo.disabled = true;
	spinnerPlazo.style.display = "inline-block";

	$.ajax({
		url: urlPrincipal + "lista_plazos?producto=" + encodeURIComponent(producto) + "&empresa=" + idEmpresa,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("Plazos recibidos:", respuesta);
			selectPlazo.innerHTML = '<option value="" disabled selected>Seleccione...</option>';

			respuesta.forEach(plazo => {
				let option = document.createElement("option");
				option.value = plazo.split(" ")[0];
				option.textContent = plazo // Si los valores son números de meses
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
		console.log("Empresa seleccionada:", idEmpresaSeleccionada);
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
	selectPuesto.disabled = true;

	$.ajax({
		url: urlPrincipal + "obtener_puestos?empresa=" + idEmpresaSeleccionada, // Endpoint para obtener los puestos
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log("Lista de puestos recibida:", respuesta);

			// Limpiar y volver a llenar el select
			selectPuesto.innerHTML = '<option value="" disabled selected>Seleccione un puesto...</option>';
			respuesta.forEach(puesto => {
				let optionPuesto = document.createElement('option');
				optionPuesto.value = puesto.pkIdTipo; // Suponiendo que cada puesto tiene un ID
				optionPuesto.textContent = puesto.descripcion; // Suponiendo que cada puesto tiene un nombre
				selectPuesto.appendChild(optionPuesto);
			});

			selectPuesto.disabled = false;
		},
		error: function(error) {
			console.error("Error al obtener la lista de puestos:", error);
			selectPuesto.innerHTML = '<option value="" disabled selected>Error al cargar</option>';
		}
	});
}

document.getElementById('buscarEmpleado').addEventListener('change', function() {
	let buscarEmpleado = document.getElementById('buscarEmpleado');
	let opcionEmpleado = document.getElementById('opcionEmpleado');
	let inputNumEmpleado = document.getElementById('numEmpleado'); // Campo de número de empleado
	let inputPuesto = document.getElementById('puesto'); // Campo de puesto

	// Buscar la opción seleccionada
	let selectedOption = Array.from(opcionEmpleado.children).find(option => option.value === buscarEmpleado.value);

	if (selectedOption) {
		// Rellenar los campos con la información de la opción seleccionada
		inputNumEmpleado.value = selectedOption.getAttribute('data-numEmp') || "";
		inputPuesto.value = selectedOption.getAttribute('data-puesto') || "";

		//listaProductos(idEmpresaSeleccionada, selectedOption.getAttribute('data-puesto'));
	} else {
		// Limpiar los campos si no hay selección válida
		inputNumEmpleado.value = "";
		inputPuesto.value = "";
	}
});

function listaMontos(producto, idEmpresa, puesto) {

	// Construir la URL del endpoint con los parámetros
	const url = `http://localhost:8090/STD/configuracionSolicitudes/condiciones?producto=${producto}&empresa=${idEmpresa}&tipoemp=${puesto}`;

	// Hacer la solicitud GET al endpoint
	fetch(url)
		.then(response => {
			if (!response.ok) {
				throw new Error('No se encontraron condiciones para los parámetros especificados.');
			}
			return response.json();
		})
		.then(data => {
			console.log('Condiciones obtenidas:', data);

			// Obtener valores de monto mínimo y máximo de la API
			const montoMin = data.montoMin;
			const montoMax = data.montoMax;
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
			alert(error.message);
		});
}

document.getElementById('puesto').addEventListener('change', function() {
	let puestoSeleccionado = this.value; // Obtén el valor de la opción seleccionada
	console.log('Puesto seleccionado:', puestoSeleccionado);

	listaMontos(idProductoSeleccionado, idEmpresaSeleccionada, puestoSeleccionado);

});

document.getElementById('montoSolicitado').addEventListener('blur', function() {
	const monto = parseFloat(this.value) || 0; // Obtener el monto ingresado
	const plazoInicial = document.getElementById('plazoCredito').value
	const frecuencia = document.getElementById('frecuenciaCredito').value
	const plazo = plazoInicial

	// Llamar a la función de plan de pagos
	const planDePagos = generarPlanPagos(monto, tasaAnual, plazo, frecuencia);

	console.log(planDePagos); // Mostrar en consola para depuración

	// Aquí puedes agregar una función para mostrar los resultados en HTML si es necesario
});


function generarPlanPagos(monto, tasaAnual, plazo, frecuencia) {
	let pagosPorAnio;

	console.log("Monto:", monto);
	console.log("Tasa Anual:", tasaAnual);
	console.log("Plazo:", plazo);
	console.log("Frecuencia:", frecuencia);

	// Determinar el número de pagos por año según la frecuencia
	switch (frecuencia.toLowerCase()) {
		case "mensual":
			pagosPorAnio = 12;
			break;
		case "quincenal":
			pagosPorAnio = 24;
			break;
		case "semanal":
			pagosPorAnio = 52;
			break;
		case "catorcenal":
			pagosPorAnio = 26; // Hay 26 períodos catorcenales en un año
			break;
		case "unico":
			pagosPorAnio = 1; // Un solo pago al final del plazo
			break;
		default:
			console.error("Frecuencia no válida");
			return;
	}

	// Calcular la tasa periódica
	const tasaPeriodica = (tasaAnual / 100) / pagosPorAnio;
	const totalPagos = plazo * (pagosPorAnio / 12); // Ajuste para distintos plazos

	// Calcular la cuota
	let cuotaFija;
	if (frecuencia.toLowerCase() === "pago único") {
		cuotaFija = monto * Math.pow(1 + tasaPeriodica, totalPagos); // Interés compuesto
	} else {
		cuotaFija = monto * (tasaPeriodica / (1 - Math.pow(1 + tasaPeriodica, -totalPagos)));
	}

	cuotaFija = cuotaFija.toFixed(2); // Redondear a 2 decimales

	// Setear el valor en el input de "Monto a descontar"
	const inputMontoDescontar = document.querySelector("#montoDescontar");
	if (inputMontoDescontar) {
		inputMontoDescontar.value = cuotaFija;
	} else {
		console.error("El input #montoDescontar no existe en el DOM");
	}

	return cuotaFija;
}

document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("btnGuardarSolicitud").addEventListener("click", function() {
		guardarSolicitud();
	});
});

function guardarSolicitud() {
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

	const solicitudData = {
		id: parseInt(getInputValue("idSolicitud", 0)), // Tomar el ID si existe
		empresa: getInputValue("buscarEmpresa"),
		empleado: getInputValue("buscarEmpleado"),
		producto: getInputValue("buscarProducto"),
		numEmpleado: getInputValue("numEmpleado"),
		puesto: parseInt(getInputValue("puesto")) || null,
		plazo: parseInt(getInputValue("plazoCredito")) || null,
		frecuencia: getInputValue("frecuenciaCredito"),
		montoSolicitado: parseFloat(getInputValue("montoSolicitado")) || null,
		montoDescontar: parseFloat(getInputValue("montoDescontar")) || null,
		fechaSolicitud: fechaActual
	};

	console.log("📤 Datos a enviar:", solicitudData);

	// Validar que los campos obligatorios estén llenos
	if (!solicitudData.empresa || !solicitudData.empleado || !solicitudData.producto || !solicitudData.numEmpleado) {
		Swal.fire({
			icon: "warning",
			title: "Campos incompletos",
			text: "Por favor, completa todos los campos obligatorios.",
			confirmButtonColor: "#ffc107",
		});
		return;
	}

	// Determinar si se crea o edita la solicitud
	const url = solicitudData.id
		? `http://localhost:8090/STD/configuracionSolicitudes/editarSolicitud/${solicitudData.id}`
		: "http://localhost:8090/STD/configuracionSolicitudes/crearSolicitud";
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
	cargarEmpresasFiltro();

	document.getElementById("btnAnterior").addEventListener("click", function() {
		if (paginaActual > 0) {
			paginaActual--;
			cargarSolicitudes(paginaActual);
		}
	});

	document.getElementById("btnSiguiente").addEventListener("click", function() {
		paginaActual++;
		cargarSolicitudes(paginaActual);
	});
});

function obtenerEstatus(estatus) {
	switch (estatus) {
		case 1: return "En Proceso";
		case 2: return "Enviado";
		case 3: return "Aprobada";
		case 4: return "Rechazada";
		case 0: return "Cancelada";
		default: return "Desconocido";
	}
}


function cargarSolicitudes(pagina) {
	// Mostrar spinner
	document.getElementById("spinner").style.display = "block";

	fetch(`http://localhost:8090/STD/configuracionSolicitudes/listarSolicitudes?page=${pagina}&size=${tamañoPagina}`)
		.then(response => response.json())
		.then(data => {
			let tabla = document.getElementById("tablaSolicitudes");
			tabla.innerHTML = ""; // Limpiar tabla antes de agregar nuevas filas

			let filas = data.content.map((solicitud) => {
				return `<tr>
                    <td>${solicitud.id}</td>
                    <td>${solicitud.empleado}</td>
                    <td>${solicitud.fechaSolicitud}</td>
                    <td>${solicitud.empresa}</td>
                    <td>$ ${solicitud.montoSolicitado}</td>
                    <td>$ ${solicitud.montoDescontar}</td>
                    <td>${obtenerEstatus(solicitud.estatus)}</td>
                    <td>
                        <button class="btn btn-warning btn-sm btn-editar" data-id="${solicitud.id}">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn btn-danger btn-sm"><i class="fas fa-trash"></i></button>
                    </td>
                </tr>`;
			}).join('');

			tabla.innerHTML = filas;

			// Actualizar paginación
			document.getElementById("paginaActual").innerText = `Página ${pagina + 1}`;
			document.getElementById("btnAnterior").disabled = pagina === 0;
			document.getElementById("btnSiguiente").disabled = data.last;

			// Ocultar spinner
			document.getElementById("spinner").style.display = "none";
		})
		.catch(error => {
			console.error("Error al cargar las solicitudes:", error);
			document.getElementById("spinner").style.display = "none";
		});
}



$(document).on("click", ".btn-editar", function() {
	var solicitudId = $(this).data("id"); // Obtener el ID de la solicitud

	// Obtener la solicitud por ID
	$.ajax({
		url: `http://localhost:8090/STD/configuracionSolicitudes/solicitud/${solicitudId}`,
		type: "GET",
		success: function(dataSolicitud) {
			console.log("Solicitud obtenida:", dataSolicitud);

			// Obtener la empresa asociada a la solicitud
			$.ajax({
				url: `http://localhost:8090/STD/configuracionSolicitudes/empresa/${dataSolicitud.empresa}`,
				type: "GET",
				success: function(dataEmpresa) {
					console.log("Empresa obtenida:", dataEmpresa);
					idEmpresaSeleccionada = dataEmpresa.id

					// Ejecutar todas las funciones necesarias antes de abrir el modal
					Promise.all([
						listaEmpresas(),
						listaEmpleados(dataEmpresa.id),
						listaProductos(dataEmpresa.id, 1111),
						llenarSelectPuestos(dataEmpresa.id),
						listaPlazos(dataSolicitud.producto, idEmpresaSeleccionada),
						listaMontos(dataSolicitud.producto, idEmpresaSeleccionada, dataSolicitud.puesto)
					]).then(() => {
						console.log("Todos los datos cargados");

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
							console.log("Puesto seleccionado:", $("#puesto").val());
							console.log("Plazo seleccionado:", dataSolicitud.plazo);
						}, 500);

						// 🛠 **Forzar actualización de los campos tipo search**
						setTimeout(() => {
							actualizarDatalist("#buscarEmpresa", dataEmpresa.nombre);
							actualizarDatalist("#buscarEmpleado", dataSolicitud.empleado);
							actualizarDatalist("#buscarProducto", dataSolicitud.producto);
						}, 300);

						// Ahora sí, abrir el modal
						$("#solicitudModal").modal("show");
					}).catch(error => {
						console.error("Error al cargar datos:", error);
						alert("Hubo un problema al cargar la información.");
					});

				},
				error: function() {
					alert("Error al obtener la empresa");
				}
			});
		},
		error: function() {
			alert("Error al obtener la solicitud");
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
			console.log("Empresas obtenidas:", data); // Verificar estructura en consola

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

	console.log("Inicia lectura de archivo");

	if (!file) {
		Swal.fire({
			icon: "warning",
			title: "Archivo no seleccionado",
			text: "Por favor selecciona un archivo antes de subirlo.",
		});
		return;
	}

	let formData = new FormData();
	formData.append("file", file);

	console.log("Archivo seleccionado:", file.name);
	console.log("Tipo:", file.type);
	console.log("Tamaño:", file.size);

	// Muestra el loading mientras se sube el archivo
	Swal.fire({
		title: "Subiendo archivo...",
		text: "Por favor espera mientras se procesa.",
		allowOutsideClick: false,
		allowEscapeKey: false,
		didOpen: () => {
			Swal.showLoading();
		}
	});

	// Hacer la solicitud fetch de manera asíncrona
	fetch("http://localhost:8090/STD/configuracionSolicitudes/upload", {
		method: "POST",
		body: formData
	})
		.then(response => response.text())
		.then(data => {
			console.log("Respuesta del backend:", data);

			// Cerrar el loading de Swal
			Swal.close();

			// Mostrar un mensaje de éxito
			Swal.fire({
				icon: "success",
				title: "Archivo subido correctamente",
				text: data,
			}).then(() => {
				// Cerrar el modal de carga de archivo (Bootstrap modal)
				var uploadModal = document.getElementById('uploadExcelModal');
				var modal = bootstrap.Modal.getInstance(uploadModal); // Obtener la instancia del modal
				modal.hide(); // Cerrar el modal

				// Limpiar el input después de subir el archivo
				fileInput.value = "";

				// Eliminar los backdrops residuales y restaurar el estado
				setTimeout(() => {
					let backdrops = document.querySelectorAll('.modal-backdrop');
					backdrops.forEach(backdrop => backdrop.remove());

					// Restaurar el estado del body
					document.body.classList.remove('modal-open');
					document.body.style.overflow = '';
					
					uploadModal.style.display = 'block'
					uploadModal.style.display = 'none'
					
				}, 300);


				cargarSolicitudes(paginaActual);
			});
		})
		.catch(error => {
			console.error("Error al subir el archivo:", error);

			// Cerrar el loading de Swal
			Swal.close();

			// Mostrar un mensaje de error
			Swal.fire({
				icon: "error",
				title: "Error al subir el archivo",
				text: "Hubo un problema al procesar el archivo.",
			});
		});
});





















