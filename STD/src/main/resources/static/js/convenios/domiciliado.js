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
var newUrlBase = urlPrincipal.replace("/convenios", "");
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
	console.log(lineaSinEspacios.length);
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
	console.log("Lista de CLABES en el objeto cuentasObjeto:");

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
			console.log(informacionClabes);
		},
		error: function(error) {
			console.log(error);
		}
	});
}
/**
 * Fin
 */


/**
 * Generar el nuevo layout
 */

var botonGenerar = document.getElementById("idBotonGenerar");

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
			// si es un archivo .txt
			spinner.style.display = "none";
			principal.style.display = "block";
			botonGenerar.style.display = "block";
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


document.getElementById('nuevoLayout').addEventListener('click', () => {
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

			// Convertir la cadena de texto modificada nuevamente a un ArrayBuffer
			const modifiedData = new TextEncoder().encode(modifiedText);

			// Crear un Blob nuevo con los datos modificados
			const modifiedBlob = new Blob([modifiedData], { type: originalFile.type });

			// Crear un enlace temporal y simular un clic para descargar automáticamente
			const downloadLink = document.createElement('a');
			downloadLink.href = URL.createObjectURL(modifiedBlob);
			downloadLink.download = 'layout banorte';
			downloadLink.style.display = 'none'; // Ocultar el enlace
			document.body.appendChild(downloadLink);
			downloadLink.click();
			document.body.removeChild(downloadLink);

			swal("Filtro realizado", {
				closeOnClickOutside: false,
				text: "Se concluyo el filtrado del layout",
				icon: "success",
			})
				.then((value) => {
					window.location.reload();
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