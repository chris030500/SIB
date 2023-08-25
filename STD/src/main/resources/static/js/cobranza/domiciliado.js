/**
 * Liga absoluta
 */

// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/cobranza", "");
/**
 * Fin
 */

/**
 * Pasos para obtener la cuenta CLABE del archivo
 */
const inputArchivo = document.getElementById('idArchivo');
var cuentasObjeto = {}; // Cambiamos el array por un objeto

inputArchivo.addEventListener('change', function(event) {
	cuentasObjeto = {};
	const archivo = event.target.files[0];
	if (archivo) {
		const lector = new FileReader();
		lector.onload = function(e) {
			const contenido = e.target.result;
			if (validarEncabezado(contenido)) {
				obtenerValores(contenido);
				imprimirCuentas();
				botonGenerar.style.display = "block";
			} else {
				// Realiza alguna acción en caso de encabezados inválidos
				spinner.style.display = "none";
				principal.style.display = "block";
				botonGenerar.style.display = "none";
				swal("El encabezado no es válido o el archivo no es de tipo .txt", {
					closeOnClickOutside: false,
					icon: "warning",
				});
				// Puedes restablecer el valor del campo de entrada de archivos si lo deseas
				inputArchivo.value = '';
			}
		};
		lector.readAsText(archivo);
	}
});

// Función para validar el encabezado del archivo
function validarEncabezado(archivoContenido) {
	// Dividir el contenido del archivo en líneas
	const lineas = archivoContenido.split('\n');

	if (lineas.length === 0) {
		// console.log("El archivo está vacío.");
		// Realiza alguna acción en caso de encabezados inválidos
		spinner.style.display = "none";
		principal.style.display = "block";
		botonGenerar.style.display = "none";
		swal("El archivo está vacío", {
			closeOnClickOutside: false,
			icon: "warning",
		});
		// Puedes restablecer el valor del campo de entrada de archivos si lo deseas
		inputArchivo.value = '';
		return false;
	}

	const primeraLinea = lineas[0]; // Tomar la primera línea
	const lineaSinEspacios = primeraLinea.replace(/\s/g, ''); // Eliminar espacios en blanco
	// console.log(lineaSinEspacios.length);
	return lineaSinEspacios.length === 87;
}

// Función para obtener el valor de la cuenta CLABE
function obtenerValores(datos) {
	const lineas = datos.trim().split('\n');
	cuentasObjeto.clabes = []; // Creamos un arreglo para almacenar las CLABES
	for (let i = 1; i < lineas.length; i++) {
		const linea = lineas[i];
		const posicionPuntoA = linea.indexOf('Punto A') + 133;
		const valorPuntoA = linea.substring(posicionPuntoA, posicionPuntoA + 18);
		cuentasObjeto.clabes.push(valorPuntoA.trim()); // Agregamos la CLABE al arreglo
	}
}


// Función para imprimir todas las CLABES en el objeto cuentas
function imprimirCuentas() {
	// console.log("Lista de CLABES en el objeto cuentasObjeto:");

	const clabesJSON = cuentasObjeto.clabes.map((clabe) => {
		return {
			clabe: clabe
		};
	});

	datosCuentaClabe(clabesJSON);
}
/**
 * Fin
 */


/**
 * Obtenemos los datos de la cuenta CLABE
 */

var informacionClabes;

function datosCuentaClabe(clabes) {
	$.ajax({
		url: newUrlBase + "recibir_clabe",
		type: 'POST',
		data: JSON.stringify(clabes), // Convierte la lista en una cadena JSON
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		success: function(resp) {
			informacionClabes = resp;
			// console.log(informacionClabes);
		},
		error: function(error) {
			// console.log(error);
		}
	});
}
/**
 * Fin
 */


/**
 * Generar el nuevo layout (Banorte)
 */

var botonGenerar = document.getElementById("idBotonGenerar");
var contenidoEntrada;
var contenidoSalida;
var numeroRegistro = 0;
var numeroMonto = 0;
// Crear un nuevo objeto de fecha
var fechaActual = new Date();
// Obtener el día del mes actual
var diaMesActual = fechaActual.getDate();

// Leer el archivo .txt
inputArchivo.addEventListener('change', (event) => {
	if (inputArchivo.files.length > 0) {
		spinner.style.display = "block";
		principal.style.display = "none";
		botonGenerar.style.display = "none";
		const file = event.target.files[0];
		const fileName = file.name;
		const fileExtension = fileName.split('.').pop();

		if (fileExtension === 'txt') {
			const reader = new FileReader();

			reader.onload = function(event) {
				// console.log('entró');
				spinner.style.display = "none";
				principal.style.display = "block";

				const content = event.target.result;
				contenidoEntrada = content.toString(); // Convertir el contenido a string

				// La variable 'contenidoEntrada' contiene todos los caracteres del archivo de entrada
				// console.log(contenidoEntrada);
			};

			reader.readAsText(file); // Leer el archivo como texto
		} else {
			spinner.style.display = "none";
			principal.style.display = "block";
			botonGenerar.style.display = "none";
			// El archivo seleccionado no es un archivo .txt
			swal("Por favor, selecciona un archivo .txt", {
				closeOnClickOutside: false,
				icon: "warning",
			});
			// Puedes restablecer el valor del campo de entrada de archivos si lo deseas
			inputArchivo.value = '';
		}
	} else {
		botonGenerar.style.display = "none";
	}
});


// Se genera el nuevo archivo (layout)
document.getElementById('nuevoLayout').addEventListener('click', () => {
	numeroMonto = 0;
	numeroRegistro = 0;
	spinner.style.display = "block";
	principal.style.display = "none";
	if (inputArchivo.files.length > 0) {
		const originalFile = inputArchivo.files[0];

		const reader = new FileReader();
		reader.onload = (event) => {
			const originalData = event.target.result;

			// Convertir los datos originales a una cadena de texto
			const originalText = new TextDecoder().decode(originalData);

			// Separar el texto en líneas
			const lines = originalText.split('\n');

			// Función para aplicar cambios en líneas específicas a partir de la segunda línea
			const applyPositionBasedChangesFromSecondLine = (lines, posicionAddCredito, posicionRemoveCredito, posicionAddRfc, posicionRemoveRfc) => {
				// Realizar cambios en líneas específicas
				for (let i = 1; i < lines.length; i++) { // Comenzar desde la segunda línea
					// Agregar credito
					lines[i] = lines[i].substring(0, posicionAddCredito) + informacionClabes[i - 1].creditoId + lines[i].substring(posicionAddCredito);
					// Eliminar espacio credito
					lines[i] = lines[i].substring(0, posicionRemoveCredito) + lines[i].substring(posicionRemoveCredito + 12);
					// Agregar rfc
					lines[i] = lines[i].substring(0, posicionAddRfc) + informacionClabes[i - 1].rfcOficial + lines[i].substring(posicionAddRfc);
					// Eliminar espacio rfc
					lines[i] = lines[i].substring(0, posicionRemoveRfc) + lines[i].substring(posicionRemoveRfc + 13);

					// Obtener la suma de los importes
					const startIndex = 99;
					const numberOfCharacters = 15;
					const importe = lines[i].substr(startIndex, numberOfCharacters);
					// console.log(importe);
					// Convertir a entero
					const totalImporte = parseInt(importe, 10);
					// console.log(totalImporte);
					numeroMonto = numeroMonto + totalImporte;
				}
				return lines.join('\n');
			};

			// Definir las posiciones de caracteres a agregar y quitar
			const posicionAddCredito = 59;
			const posicionRemoveCredito = 94;
			const posicionAddRfc = 114;
			const posicionRemoveRfc = 127;

			// Aplicar cambios desde la segunda línea basados en posiciones de caracteres
			const modifiedText = applyPositionBasedChangesFromSecondLine(lines, posicionAddCredito, posicionRemoveCredito, posicionAddRfc, posicionRemoveRfc);
			contenidoSalida = modifiedText;
			// Obtener el numero de registros
			numeroRegistro = lines.slice(1).length;
			// console.log(numeroRegistro);
			// console.log(numeroMonto);

			// Convertir la cadena de texto modificada nuevamente a un ArrayBuffer
			const modifiedData = new TextEncoder().encode(modifiedText);

			// Crear un Blob nuevo con los datos modificados
			const modifiedBlob = new Blob([modifiedData], { type: originalFile.type });

			// Crear un enlace temporal y simular un clic para descargar automáticamente
			const downloadLink = document.createElement('a');
			downloadLink.href = URL.createObjectURL(modifiedBlob);
			downloadLink.download = prefijoArchivo + diaMesActual + ".pag";
			downloadLink.style.display = 'none'; // Ocultar el enlace
			document.body.appendChild(downloadLink);
			downloadLink.click();
			document.body.removeChild(downloadLink);

			swal({
				title: '¿El archivo se generó de manera correcta?',
				text: "En caso de que si, dar en Aceptar para actualizar la bitacora",
				closeOnClickOutside: false,
				icon: "warning",
				buttons: ["Recargar", "Aceptar"],
				confirmButtonColor: '#3498DB',
			})
				.then((save) => {
					if (save) {
						mandarValores();
					} else {
						window.location.reload();
					}
				});
		};

		reader.readAsArrayBuffer(originalFile);

		spinner.style.display = "none";
		principal.style.display = "block";

	}
});

/**
 * Fin
 */


/**
 * Spinner | pantalla de carga 
 * */

var spinner = document.getElementById("spinnerId");
var principal = document.getElementById("principalId");

/**
 * Fin
 */


/**
 * Obtener la lista de bancos activos
 *  */
var catalogoBancos = document.getElementById("catalogoId");
var vistaCarga = document.getElementById("cargaId");
var valorSeleccionado;

window.onload = function() {
	$.ajax({
		url: newUrlBase + "lista_bancos",
		type: 'GET',
		dataType: 'json',
		success: function(resp) {
			// console.log(resp);
			for (var i = 0; i < resp.length; i++) {
				var opcion = document.createElement("option");
				opcion.value = resp[i].pk_id_banco;
				opcion.text = resp[i].vc_nombre;
				catalogoBancos.add(opcion);
			}
			catalogoBancos.addEventListener("change", function() {
				valorSeleccionado = catalogoBancos.value;
				if (valorSeleccionado != null | valorSeleccionado != undefined) {
					vistaCarga.style.display = "block";
				}
			})
		},
		error: function(error) {
			// console.log(error);
		}
	});
	mostrarBitacora();
	obtenerPrefijo();
}

/**
 * Fin
 *  */


/**
 * Mandar los valore del archivo
 *  */

function mandarValores() {
	const dto = {
		entrada: contenidoEntrada,
		salida: contenidoSalida,
		banco: parseInt(valorSeleccionado),
		cantidadRegistro: numeroRegistro,
		totalMonto: numeroMonto
	}
	var enviar = JSON.stringify(dto);
	// console.log(dto);
	$.ajax({
		url: newUrlBase + "valores_layout",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(resp) {
			// console.log(resp);
			if (resp == "OK") {
				swal("Filtro realizado", {
					closeOnClickOutside: false,
					text: "Bitacora actualizada",
					icon: "success",
				})
					.then((value) => {
						window.location.reload();
					});
			}
		}, error: function(error) {
			// console.log(error);
		}
	});
}

/**
 * Fin
 *  */


/**
 * Mostrar la bitacora
 *  */
var tablaProceso = document.getElementById("bitacoraId");
var tbodyProceso = tablaProceso.querySelector("tbody");
var paginadoBitacora;
const resultadosPorPaginaBitacora = 5; // Número de resultados por página
let paginaActualBitacora = 1; // Página actual

// mostrar la tabla
function mostrarBitacora() {
	$.ajax({
		url: newUrlBase + "bitacora",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			paginadoBitacora = resp;
			inicioPaginado();
		},
		error: function(error) {
			// console.log(error.responseJSON.message);
		}
	});
}

// Inicia el paginado de la bitacora
function inicioPaginado() {
	tbodyProceso.innerHTML = ""; // Limpiar la tabla antes de mostrar los nuevos datos
	const inicio = (paginaActualBitacora - 1) * resultadosPorPaginaBitacora;
	const fin = inicio + resultadosPorPaginaBitacora;
	const datosPaginados = paginadoBitacora.slice(inicio, fin);
	// Iterar sobre los datos del JSON y crear las filas de la tabla
	for (var i = 0; i < datosPaginados.length; i++) {
		var rows = document.createElement("tr");

		var numCell = document.createElement("td");
		numCell.textContent = datosPaginados[i].pk_id_proceso;
		numCell.style.textAlign = "center";
		rows.appendChild(numCell);

		var nombreCell = document.createElement("td");
		nombreCell.textContent = datosPaginados[i].vc_nombre;
		nombreCell.style.textAlign = "center";
		rows.appendChild(nombreCell);

		var cantidadRegistroCell = document.createElement("td");
		cantidadRegistroCell.textContent = datosPaginados[i].i_cantidad_registro;
		cantidadRegistroCell.style.textAlign = "center";
		rows.appendChild(cantidadRegistroCell);

		var totalMontoCell = document.createElement("td");
		totalMontoCell.textContent = datosPaginados[i].i_total_monto;
		totalMontoCell.style.textAlign = "center";
		rows.appendChild(totalMontoCell);

		var fechaCell = document.createElement("td");
		fechaCell.textContent = datosPaginados[i].dt_fecha_carga;
		fechaCell.style.textAlign = "center";
		rows.appendChild(fechaCell);

		var usuarioCell = document.createElement("td");
		usuarioCell.textContent = datosPaginados[i].vc_nombre_usuario;
		usuarioCell.style.textAlign = "center";
		rows.appendChild(usuarioCell);

		tbodyProceso.appendChild(rows);
	}
	botonesPaginacionBitacora();
}

// Botones del Paginado
function botonesPaginacionBitacora() {
	const paginationContainer = document.getElementById("paginadoId");
	paginationContainer.innerHTML = ""; // Limpiar los botones de paginación
	const totalPages = Math.ceil(paginadoBitacora.length / resultadosPorPaginaBitacora);
	for (let i = 1; i <= totalPages; i++) {
		const button = document.createElement("button");
		button.textContent = i;
		// Establecer los estilos en línea usando la propiedad style
		button.style.backgroundColor = "#85929E";
		button.style.border = "none";
		button.style.color = "white";
		button.style.padding = "8px 16px";
		button.style.margin = "2px";
		// Aplicar estilos al botón activo
		if (i === paginaActualBitacora) {
			button.style.backgroundColor = "#007bff";
		}
		// Agrega más estilos si es necesario
		button.addEventListener("click", function() {
			paginaActualBitacora = i;
			inicioPaginado();
			botonesPaginacionBitacora();
		});
		paginationContainer.appendChild(button);
	}
}

/**
 * Fin
 */


/**
 * Obtener prefijo del archivo
 *  */
var prefijoArchivo;
// mostrar la tabla
function obtenerPrefijo() {
	$.ajax({
		url: newUrlBase + "prefijo_archivo",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			prefijoArchivo = resp.prefijoArchivo;
		},
		error: function(error) {
			console.log(error);
		}
	});
}

/**
 * Fin
 */