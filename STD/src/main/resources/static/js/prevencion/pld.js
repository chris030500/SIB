// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/prevencion", "");
// Obtener el elemento listas
var catListas = document.getElementById("miListas");
// Obtener el elemento estatus
var catEstatus = document.getElementById("miEstatus");
// Variables | para los estatus
var etiquetaLista = document.getElementById("etiquetaLista");
var etiquetaEstatus = document.getElementById("etiquetaEstatus");
var alertLista = document.getElementById("alertLista");
var alertEstatus = document.getElementById("alertEstatus");
var idLista;
var verEstatus = document.getElementById("verEstatus");
var verAgregar = document.getElementById("verAgregar");
//
var valorLista;
var valorEstatus;
// Tabla relaciones
var table = document.getElementById("tablaExclusion");
var tbody = table.querySelector("tbody");
// Tabla bitacora
var tablaBitacora = document.getElementById("tablaBitacora");
var tbodyBitacora = tablaBitacora.querySelector("tbody");
//
var botonGenerar = document.getElementById("botonGenerar");
const fileInput = document.getElementById('inputArchivo');
//
var datosNull = "\u0000";
var datosVacios = "               ";
var datosFecha = "________";
var datosNa = "";
//
var spinner = document.getElementById("spinnerId");
var verInfo = document.getElementById("verInfo");
// Variables | paginado exclusion
const resultadosPorPaginaExclusion = 5; // Número de resultados por página
let paginaActualExclusion = 1; // Página actual
var paginadoExclusion;
// Variables | paginado bitacora
const resultadosPorPaginaBitacora = 5; // Número de resultados por página
let paginaActualBitacora = 1; // Página actual
var paginadoBitacora;
// Variables | datos bitacora
var noRegistros;
var noExcluidos;
var noFinales;
var nomArchivo = "FormatoTerceros.txt";
var vcEstatus = "OK";
var vcComentarios = "Se realizó el filtrado de datos";
var tDuracion;
// tiempo
var proInicio;
var proFin;
// rol
var nombreRol;
var relacionliEs = document.getElementById("relacionliEs");
var cargaArchivo = document.getElementById("cargaArchivo");
var thAccion = document.getElementById("thAccion");

// Inicio de página
window.onload = function() {
	spinner.style.display = "block";
	relacionliEs.style.display = "none";
	thAccion.style.display = "none";
	cargaArchivo.style.display = "none";
	obtenerRol();
	setTimeout(() => {
		catalogoListas();
		verTablaExclusion();
		verTablaBitacora();
		spinner.style.display = "none";
		verInfo.style.display = "none";
		verEstatus.style.display = "none";
		verAgregar.style.display = "none";
		alertLista.style.display = "none";
		alertEstatus.style.display = "none";
		botonGenerar.style.display = "none";
		verInfo.style.display = "block";

	}, 2000); // Tiempo de simulación en milisegundos

}

// Obtener rol
function obtenerRol() {
	$.ajax({
		url: newUrlBase + "mostral_rol",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			nombreRol = resp.nombreRol;
			console.log(nombreRol);
			if (nombreRol == "AUX") {
				relacionliEs.style.display = "none";
				thAccion.style.display = "none";
				cargaArchivo.style.display = "block";
			} else if (nombreRol == "CONS") {
				relacionliEs.style.display = "none";
				cargaArchivo.style.display = "none";
				thAccion.style.display = "none";
			} else if (nombreRol == "GES") {
				relacionliEs.style.display = "block";
				thAccion.style.display = "block";
				cargaArchivo.style.display = "block";
			}
		},
		error: function(error) {
			// console.log(error);
		}
	});
}

// catalogo de listas
function catalogoListas() {
	$.ajax({
		url: newUrlBase + "catalogo_listas",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			for (var i = 0; i < resp.length; i++) {
				// Crear una opción para cada elemento en el JSON
				var opcion = document.createElement("option");
				opcion.value = resp[i].pk_id_lista; // Valor de la opción
				opcion.text = resp[i].vc_lista; // Texto de la opción
				// Agregar la opción al select
				catListas.add(opcion);
			}
			// Agregar un evento de cambio al select
			catListas.addEventListener("change", function() {
				// Mostrar texto seleccionado
				var selectedOption = catListas.options[catListas.selectedIndex];
				var selectedText = selectedOption.text;
				// Obtener el valor seleccionado
				var valorSeleccionado = catListas.value;
				valorLista = valorSeleccionado;
				etiquetaLista.textContent = selectedText;
				idLista = catListas.value;
				// Mostrar estatus
				alertLista.style.display = "block";
				alertEstatus.style.display = "none";
				catalogoEstatus();
				verEstatus.style.display = "block";
			});
		},
		error: function(error) {
			//console.log(error);
		}
	});
}

// catalogo de estatus
function catalogoEstatus() {
	verAgregar.style.display = "none";
	while (catEstatus.options.length > 0) {
		catEstatus.remove(0);
	}
	etiquetaEstatus.textContent = "";
	$.ajax({
		url: newUrlBase + "catalogo_estatus?idLista=" + idLista,
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			for (var i = 0; i < resp.length; i++) {
				// Crear una opción para cada elemento en el JSON
				var opcion = document.createElement("option");
				opcion.value = resp[i].pk_id_estatus; // Valor de la opción
				opcion.text = resp[i].vc_estatus == "" ? "Vacío" : resp[i].vc_estatus; // Texto de la opción
				// Agregar la opción al select
				catEstatus.add(opcion);
			}
			// Crea una opción vacía
			var emptyOption = document.createElement("option");
			emptyOption.text = "Estatus";
			emptyOption.value = "";
			emptyOption.disabled = true;
			emptyOption.selected = true;
			// Agrega la opción vacía al principio del <select>
			catEstatus.insertBefore(emptyOption, catEstatus.firstChild);
			// Crea una opción para todos
			var todosOption = document.createElement("option");
			todosOption.text = "Todos";
			todosOption.value = 0;
			catEstatus.add(todosOption, 1);
			// Agregar un evento de cambio al select
			catEstatus.addEventListener("change", function() {
				// Mostrar texto seleccionado
				var selectedOption = catEstatus.options[catEstatus.selectedIndex];
				var selectedText = selectedOption.text;
				// Obtener el valor seleccionado
				var valorSeleccionado = catEstatus.value;
				etiquetaEstatus.textContent = selectedText;
				valorEstatus = valorSeleccionado;
				alertEstatus.style.display = "block";
				verAgregar.style.display = "block";
			});
		},
		error: function(error) {
			//console.log(error.responseJSON.message);
		}
	});
}

// Datos de la tabla exclusion
function verTablaExclusion() {
	$.ajax({
		url: newUrlBase + "exclusiones",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			paginadoExclusion = resp;
			empiezaPaginadoExclusion();
		},
		error: function(error) {
			// console.log(error.responseJSON.message);
		}
	});
}

// Empiza paginado exclusion
function empiezaPaginadoExclusion() {
	tbody.innerHTML = ""; // Limpiar la tabla antes de mostrar los nuevos datos
	const inicio = (paginaActualExclusion - 1) * resultadosPorPaginaExclusion;
	const fin = inicio + resultadosPorPaginaExclusion;
	const datosPaginados = paginadoExclusion.slice(inicio, fin);
	console.log(nombreRol);
	// Iterar sobre los datos del JSON y crear las filas de la tabla
	for (var i = 0; i < datosPaginados.length; i++) {
		var row = document.createElement("tr");

		var listaCell = document.createElement("td");
		listaCell.textContent = datosPaginados[i].vc_lista;
		listaCell.style.textAlign = "center";
		row.appendChild(listaCell);

		var estatusCell = document.createElement("td");
		estatusCell.textContent = datosPaginados[i].vc_estatus == "" ? "Vacío" : datosPaginados[i].vc_estatus;
		estatusCell.style.textAlign = "center";
		row.appendChild(estatusCell);

		if (nombreRol == "GES") {
			var celdaAccion = document.createElement("td");
			var botonEliminar = document.createElement("button");
			botonEliminar.textContent = "x";
			botonEliminar.classList.add("btn", "btn-danger"); // Agregar las clases de Bootstrap
			botonEliminar.setAttribute("onclick", "alertaEliminar(" + datosPaginados[i].pk_id_exclusion + ")");
			botonEliminar.style.borderRadius = "50%";
			botonEliminar.style.width = "30px";
			botonEliminar.style.height = "30px";
			botonEliminar.style.padding = "0px";
			botonEliminar.title = "Eliminar"
			celdaAccion.style.textAlign = "center";
			celdaAccion.appendChild(botonEliminar);
			row.appendChild(celdaAccion);
		}

		tbody.appendChild(row);
	}
	botonesPaginacionExclusion();
}

// Botones paginado Exclusion
function botonesPaginacionExclusion() {
	const paginationContainer = document.getElementById("pagination-exclusion");
	paginationContainer.innerHTML = ""; // Limpiar los botones de paginación
	const totalPages = Math.ceil(paginadoExclusion.length / resultadosPorPaginaExclusion);
	for (let i = 1; i <= totalPages; i++) {
		const button = document.createElement("button");
		button.textContent = i;
		// Establecer los estilos en línea usando la propiedad style
		button.style.backgroundColor = "#85929E";
		button.style.border = "none";
		button.style.color = "white";
		button.style.margin = "2px";
		button.style.padding = "8px 16px";
		// Aplicar estilos al botón activo
		if (i === paginaActualExclusion) {
			button.style.backgroundColor = "#007bff";
		}
		// Agrega más estilos si es necesario
		button.addEventListener("click", function() {
			paginaActualExclusion = i;
			empiezaPaginadoExclusion();
			botonesPaginacionExclusion();
		});
		paginationContainer.appendChild(button);
	}
}


// Agregar exclusion
function agregarExclusion() {
	// Se llena el DTO
	const dto = {
		lista: valorLista,
		estatus: valorEstatus
	};
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "agregar_exclusion",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: 'application/json; charset=utf-8',
		success: function(resp) {
			if (resp == "CONTINUE") {
				swal({
					title: "Aviso",
					closeOnClickOutside: false,
					text: "Ya existe registrada esta exclusion",
					icon: "warning",
				});
			} else {
				swal("Exclusión agregada", {
					closeOnClickOutside: false,
					icon: "success",
				})
					.then((value) => {
						window.location.reload();
					});
			}
		}, error: function(error) {
			// console.log(error);
			swal({
				title: "Aviso",
				closeOnClickOutside: false,
				text: "Hubo un error al momento de guardar los datos",
				icon: "warning",
			});
		}
	});
}

// Alerta para eliminar exclusion
function alertaEliminar(idExclusion) {
	swal({
		title: '¿Deseas eliminar la exclusión?',
		closeOnClickOutside: false,
		icon: "warning",
		buttons: ["Salir", "Aceptar"],
		confirmButtonColor: '#3498DB',
	})
		.then((willDelete) => {
			if (willDelete) {
				eliminarExclusion(idExclusion);
			} else { }
		});
}

// Eliminar exclusion
function eliminarExclusion(idExclusion) {
	$.ajax({
		url: newUrlBase + "eliminar_exclusion?idExclusion=" + idExclusion,
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			swal("Exclusión eliminada", {
				closeOnClickOutside: false,
				icon: "success",
			})
				.then((value) => {
					window.location.reload();
				});
		},
		error: function(error) {
			// console.log(error.responseJSON.message);
			swal({
				title: "Aviso",
				closeOnClickOutside: false,
				text: "Hubo un error al momento de elimiar los datos",
				icon: "warning",
			});
		}
	});
}

// Limpiar valores
function limpiarValores() {
	verEstatus.style.display = "none";
	verAgregar.style.display = "none";
	while (catEstatus.options.length > 0) {
		catEstatus.remove(0);
	}
	alertEstatus.style.display = "none";
}

// Datos tabla bitacora
function verTablaBitacora() {
	$.ajax({
		url: newUrlBase + "datos_bitacora",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			paginadoBitacora = resp;
			empiezaPaginadoBitacora();
		},
		error: function(error) {
			// console.log(error.responseJSON.message);
		}
	});
}

// Empieza paginado bitacora
function empiezaPaginadoBitacora() {
	tbodyBitacora.innerHTML = ""; // Limpiar la tabla antes de mostrar los nuevos datos
	const inicio = (paginaActualBitacora - 1) * resultadosPorPaginaBitacora;
	const fin = inicio + resultadosPorPaginaBitacora;
	const datosPaginados = paginadoBitacora.slice(inicio, fin);
	// Iterar sobre los datos del JSON y crear las filas de la tabla
	for (var i = 0; i < datosPaginados.length; i++) {
		var rows = document.createElement("tr");

		var archivoCell = document.createElement("td");
		archivoCell.textContent = datosPaginados[i].nombre_archivo;
		rows.appendChild(archivoCell);

		var fechaCell = document.createElement("td");
		fechaCell.textContent = datosPaginados[i].fecha;
		rows.appendChild(fechaCell);

		var registroCell = document.createElement("td");
		registroCell.textContent = datosPaginados[i].numero_registro;
		registroCell.style.textAlign = "center";
		rows.appendChild(registroCell);

		var excluidosCell = document.createElement("td");
		excluidosCell.textContent = datosPaginados[i].registros_excluidos;
		excluidosCell.style.textAlign = "center";
		rows.appendChild(excluidosCell);

		var finalCell = document.createElement("td");
		finalCell.textContent = datosPaginados[i].registro_final;
		finalCell.style.textAlign = "center";
		rows.appendChild(finalCell);

		var duracionCell = document.createElement("td");
		duracionCell.textContent = datosPaginados[i].duracion;
		rows.appendChild(duracionCell);

		var estatusCell = document.createElement("td");
		estatusCell.textContent = datosPaginados[i].estatus;
		estatusCell.style.textAlign = "center";
		rows.appendChild(estatusCell);

		var comentarioCell = document.createElement("td");
		comentarioCell.textContent = datosPaginados[i].comentarios;
		rows.appendChild(comentarioCell);

		var usuarioCell = document.createElement("td");
		usuarioCell.textContent = datosPaginados[i].usuario;
		rows.appendChild(usuarioCell);

		tbodyBitacora.appendChild(rows);
	}
	botonesPaginacionBitacora();
}

// Paginado Exclusion
function botonesPaginacionBitacora() {
	const paginationContainer = document.getElementById("pagination-bitacora");
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
			empiezaPaginadoBitacora();
			botonesPaginacionBitacora();
		});
		paginationContainer.appendChild(button);
	}
}

// Leer el archivo .txt
fileInput.addEventListener('change', (event) => {
	spinner.style.display = "block";
	verInfo.style.display = "none";
	botonGenerar.style.display = "none";
	const file = event.target.files[0];
	const fileName = file.name;
	const fileExtension = fileName.split('.').pop();
	if (fileExtension === 'txt') {
		// Ejemplo de uso
		const archivoSeleccionado = fileInput.files[0];
		leerArchivo(archivoSeleccionado);
	} else {
		spinner.style.display = "none";
		verInfo.style.display = "block";
		botonGenerar.style.display = "none";
		// El archivo seleccionado no es un archivo .txt
		swal("Por favor, selecciona un archivo .txt", {
			closeOnClickOutside: false,
			icon: "warning",
		});
		// Puedes restablecer el valor del campo de entrada de archivos si lo deseas
		fileInput.value = '';
	}
});

// Convertir archivo a DTO
function convertirArchivo() {
	// Obtenemos el tiempo de inicio
	proInicio = performance.now();
	spinner.style.display = "block";
	verInfo.style.display = "none";
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var contenido = e.target.result;
		var lineas = contenido.split('\n');
		var registros = [];
		// Obtener los 3 primeros registros
		for (var i = 1; i < lineas.length; i++) {
			var campos = lineas[i].split('	');
			var registro = {
				idqeq: campos[0] = campos[0] == datosNull || campos[0] == datosVacios || campos[0] == datosFecha ? datosNa : campos[0],
				nombre: campos[1] = campos[1] == datosNull || campos[1] == datosVacios || campos[1] == datosFecha ? datosNa : campos[1],
				paterno: campos[2] = campos[2] == datosNull || campos[2] == datosVacios || campos[2] == datosFecha ? datosNa : campos[2],
				materno: campos[3] = campos[3] == datosNull || campos[3] == datosVacios || campos[3] == datosFecha ? datosNa : campos[3],
				curp: campos[4] = campos[4] == datosNull || campos[4] == datosVacios || campos[4] == datosFecha ? datosNa : campos[4],
				rfc: campos[5] = campos[5] == datosNull || campos[5] == datosVacios || campos[5] == datosFecha ? datosNa : campos[5],
				fecnac: campos[6] = campos[6] == datosNull || campos[6] == datosVacios || campos[6] == datosFecha ? datosNa : campos[6],
				lista: campos[7] = campos[7] == datosNull || campos[7] == datosVacios || campos[7] == datosFecha ? datosNa : campos[7],
				estatus: campos[8] = campos[8] == datosNull || campos[8] == datosVacios || campos[8] == datosFecha ? datosNa : campos[8],
				dependencia: campos[9] = campos[9] == datosNull || campos[9] == datosVacios || campos[9] == datosFecha ? datosNa : campos[9],
				puesto: campos[10] = campos[10] == datosNull || campos[10] == datosVacios || campos[10] == datosFecha ? datosNa : campos[10],
				iddispo: campos[11] = campos[11] == datosNull || campos[11] == datosVacios || campos[11] == datosFecha ? datosNa : campos[11],
				curpok: campos[12] = campos[12] == datosNull || campos[12] == datosVacios || campos[12] == datosFecha ? datosNa : campos[12],
				idrel: campos[13] = campos[13] == datosNull || campos[13] == datosVacios || campos[13] == datosFecha ? datosNa : campos[13],
				parentesco: campos[14] = campos[14] == datosNull || campos[14] == datosVacios || campos[14] == datosFecha ? datosNa : campos[14],
				razonsoc: campos[15] = campos[15] == datosNull || campos[15] == datosVacios || campos[15] == datosFecha ? datosNa : campos[15],
				rfcmoral: campos[16] = campos[16] == datosNull || campos[16] == datosVacios || campos[16] == datosFecha ? datosNa : campos[16],
				issste: campos[17] = campos[17] == datosNull || campos[17] == datosVacios || campos[17] == datosFecha ? datosNa : campos[17],
				imss: campos[18] = campos[18] == datosNull || campos[18] == datosVacios || campos[18] == datosFecha ? datosNa : campos[18],
				ingresos: campos[19] = campos[19] == datosNull || campos[19] == datosVacios || campos[19] == datosFecha ? datosNa : campos[19],
				nomcomp: campos[20] = campos[20] == datosNull || campos[20] == datosVacios || campos[20] == datosFecha ? datosNa : campos[20],
				apellidos: campos[21] = campos[21] == datosNull || campos[21] == datosVacios || campos[21] == datosFecha ? datosNa : campos[21],
				entidad: campos[22] = campos[22] == datosNull || campos[22] == datosVacios || campos[22] == datosFecha ? datosNa : campos[22],
				genero: campos[23]
			};
			registros.push(registro);
		}
		// numero registros
		noRegistros = registros.length;
		filtroExclusiones(registros);
	};
	reader.readAsText(file, 'windows-1252'); // Especificar la codificación ANSI (windows-1252)
}

// Filtro de exclusiones
function filtroExclusiones(data) {
	const datosFiltrados = data.filter(item => {
		return !paginadoExclusion.some(exclusion =>
			item.lista === exclusion.vc_lista && item.estatus === exclusion.vc_estatus
		);
	});
	// console.log(datosFiltrados);
	// Uso de la función para convertir encabezados a mayúscula
	const listaDatosMayuscula = convertirEncabezadosAMayuscula(datosFiltrados);
	// numero de finales
	noFinales = datosFiltrados.length;
	// numero de excluidos
	noExcluidos = data.length - datosFiltrados.length;
	generarArchivo(listaDatosMayuscula);
}

// Función para convertir los encabezados a mayúscula
function convertirEncabezadosAMayuscula(datos) {
	const encabezadosMayuscula = Object.keys(datos[0]).map(encabezado => encabezado.toUpperCase());
	const datosConEncabezadosMayuscula = datos.map(obj => {
		const newObj = {};
		for (let key in obj) {
			const newKey = encabezadosMayuscula.find(header => header === key.toUpperCase());
			newObj[newKey] = obj[key];
		}
		return newObj;
	});
	return datosConEncabezadosMayuscula;
}

// Generar archivo
function generarArchivo(data) {
	// Uso de las funciones para convertir y guardar el archivo
	const datosEnTexto = convertirListaATexto(data);
	guardarArchivoTXT(datosEnTexto, nomArchivo);
}


// Función para convertir una lista de objetos en formato de tabla con tabulaciones
function convertirListaATexto(datos) {
	const header = Object.keys(datos[0]).join('\t'); // Cabecera con nombres de columnas
	const filas = datos.map(obj => Object.values(obj).join('\t')); // Datos de cada fila
	return header + '\n' + filas.join('\n');
}

// Función para crear y guardar el archivo en formato ANSI
function guardarArchivoTXT(texto, nombreArchivo) {
	const blob = new Blob([new Uint8Array([0xEF, 0xBB, 0xBF]), texto], { type: 'text/plain;charset=utf-8' });
	const url = URL.createObjectURL(blob);
	const a = document.createElement('a');
	a.href = url;
	a.download = nombreArchivo;
	document.body.appendChild(a);
	a.click();
	URL.revokeObjectURL(url);
	document.body.removeChild(a);
	spinner.style.display = "none";
	verInfo.style.display = "block";
	// Obtenemos el tiempo de finalización
	proFin = performance.now();
	// Calculamos la diferencia para obtener el tiempo transcurrido
	const tiempoTranscurrido = proFin - proInicio;
	tDuracion = convertirMilisegundosAHora(tiempoTranscurrido);
	guardarBitacora();
}

// Función para validar los encabezados
function validarEncabezados(encabezados) {
	// Definir los encabezados válidos
	const encabezadosValidos = ['IDQEQ', 'NOMBRE', 'PATERNO', 'MATERNO', 'CURP', 'RFC', 'FECNAC', 'LISTA', 'ESTATUS', 'DEPENDENCIA',
		'PUESTO', 'IDDISPO', 'CURPOK', 'IDREL', 'PARENTESCO', 'RAZONSOC', 'RFCMORAL', 'ISSSTE', 'IMSS', 'INGRESOS',
		'NOMCOMP', 'APELLIDOS', 'ENTIDAD', 'GENERO'];

	// Comprobar si los encabezados son válidos
	for (let i = 0; i < encabezados.length; i++) {
		if (!encabezadosValidos.includes(encabezados[i])) {
			return false; // Encabezado inválido encontrado
		}
	}
	return true; // Todos los encabezados son válidos
}

// Función para leer el archivo
function leerArchivo(archivo) {
	const lector = new FileReader();
	lector.onload = function(evento) {
		const contenido = evento.target.result;
		const lineas = contenido.split('\n');
		if (lineas.length > 0) {
			const encabezados = lineas[0].trim().split('\t');
			if (validarEncabezados(encabezados)) {
				// Continúa con el procesamiento del archivo
				spinner.style.display = "none";
				verInfo.style.display = "block";
				botonGenerar.style.display = "block";
			} else {
				spinner.style.display = "none";
				verInfo.style.display = "block";
				// Realiza alguna acción en caso de encabezados inválidos
				swal("Los encabezados no son válidos", {
					closeOnClickOutside: false,
					icon: "warning",
				});
			}
		} else {
			// console.log('El archivo está vacío.');
		}
	};
	lector.readAsText(archivo);
}

// Medir tiempo
function convertirMilisegundosAHora(milisegundos) {
	const fecha = new Date(milisegundos);
	const horas = fecha.getUTCHours().toString().padStart(2, '0');
	const minutos = fecha.getUTCMinutes().toString().padStart(2, '0');
	const segundos = fecha.getUTCSeconds().toString().padStart(2, '0');
	return `${horas}:${minutos}:${segundos}`;
}

// Guardar bitacora
function guardarBitacora() {
	const dto = {
		nombre_archivo: nomArchivo,
		numero_registro: noRegistros,
		registros_excluidos: noExcluidos,
		registro_final: noFinales,
		duracion: tDuracion,
		estatus: vcEstatus,
		comentarios: vcComentarios,
	}
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "agregar_bitacora",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: 'application/json; charset=utf-8',
		success: function(resp) {
			// console.log(resp);
			swal("Bitacora actualizada", {
				closeOnClickOutside: false,
				text: "Si usted guardó el archivo podrá consultar el respaldo en el siguiente repositorio: " + resp.nombreRol,
				icon: "success",
			})
				.then((value) => {
					crearCopia();
					window.location.reload();
				});
		}, error: function(error) {
			// console.log(error);
			swal({
				title: "Aviso",
				closeOnClickOutside: false,
				text: "Hubo un error al momento de guardar los datos",
				icon: "warning",
			});
		}
	});
}


// Crear copia
function crearCopia() {
	$.ajax({
		url: newUrlBase + "crear_copia",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			console.log(resp);
		},
		error: function(error) {
			console.log(error);
		}
	});
}