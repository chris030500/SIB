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
//
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
// Tabla
var table = document.getElementById("tablaExclusion");
var tbody = table.querySelector("tbody");
//
var tablaBitacora = document.getElementById("tablaBitacora");
var tbodyBitacora = table.querySelector("tbodyBitacora");

// Inicio de página
window.onload = function() {
	verEstatus.style.display = "none";
	verAgregar.style.display = "none";
	alertLista.style.display = "none";
	alertEstatus.style.display = "none";
	catalogoListas();
	datosTabla();
	//verTablaBitacora();
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
				opcion.text = resp[i].vc_estatus; // Texto de la opción
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

// Datos de la tabla
function datosTabla() {
	$.ajax({
		url: newUrlBase + "exclusiones",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			console.log(resp);
			// Iterar sobre los datos del JSON y crear las filas de la tabla
			for (var i = 0; i < resp.length; i++) {
				var row = document.createElement("tr");

				var listaCell = document.createElement("td");
				listaCell.textContent = resp[i].vc_lista;
				row.appendChild(listaCell);

				var estatusCell = document.createElement("td");
				estatusCell.textContent = resp[i].vc_estatus;
				row.appendChild(estatusCell);

				var celdaAccion = document.createElement("td");
				var botonEliminar = document.createElement("button");
				botonEliminar.textContent = "x";
				botonEliminar.classList.add("btn", "btn-danger"); // Agregar las clases de Bootstrap
				botonEliminar.setAttribute("onclick", "alertaEliminar(" + resp[i].pk_id_exclusion + ")");
				celdaAccion.appendChild(botonEliminar);
				row.appendChild(celdaAccion);

				tbody.appendChild(row);
			}
		},
		error: function(error) {
			// console.log(error.responseJSON.message);
		}
	});
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
			console.log(error.responseJSON.message);
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
			console.log(resp);
			// Iterar sobre los datos del JSON y crear las filas de la tabla
			for (var i = 0; i < resp.length; i++) {
				var row = document.createElement("tr");

				var archivoCell = document.createElement("td");
				archivoCell.textContent = resp[i].vc_nombre_archivo;
				row.appendChild(archivoCell);

				var fechaCell = document.createElement("td");
				fechaCell.textContent = resp[i].dt_fecha;
				row.appendChild(fechaCell);

				var registroCell = document.createElement("td");
				registroCell.textContent = resp[i].i_numero_registro;
				row.appendChild(registroCell);

				var excluidosCell = document.createElement("td");
				excluidosCell.textContent = resp[i].i_registros_excluidos;
				row.appendChild(excluidosCell);

				var finalCell = document.createElement("td");
				finalCell.textContent = resp[i].i_registro_final;
				row.appendChild(finalCell);

				var duracionCell = document.createElement("td");
				duracionCell.textContent = resp[i].vc_duracion;
				row.appendChild(duracionCell);

				var estatusCell = document.createElement("td");
				estatusCell.textContent = resp[i].vc_estatus;
				row.appendChild(estatusCell);

				var comentarioCell = document.createElement("td");
				comentarioCell.textContent = resp[i].vc_comentarios;
				row.appendChild(comentarioCell);

				tbodyBitacora.appendChild(row);
			}
		},
		error: function(error) {
			// console.log(error.responseJSON.message);
		}
	});
}