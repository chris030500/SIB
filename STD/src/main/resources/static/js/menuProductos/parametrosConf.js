// @ts-nocheck
/**
*
*/

var id = "";
var idDiv = "";
var opcion = "";
function ocultarMostrar(divEl) {
	id = $(divEl).attr("id");
	idDiv = id + 1;
	opcion = "#pant-tabs" + " #" + idDiv;
	switch ($(opcion).attr("for")) {
		case "1":
			console.log("Parametros");
			break;
		case "2":
			obtTblReglas();
			break;
		case "3":
			obtTblPlantillas();
			break;
		case "5":
			obtTblClienteB();
			break;
		default:
			break;
	}

	mostrarOcultarDiv(idDiv);
}

function ocultarMostrarExclusiones(divExclusiones) {
	id = $(divExclusiones).attr("id");
	idDiv = id + 1;
	opcion = "#exclusiones-tabs" + " #" + idDiv;
	switch ($(opcion).attr("for")) {
		case "7":
			console.log("Municipios con cobertura");
			listaMunicipiosConCobertura();
			break;
		case "8":
			console.log("Convenios");
			break;
		case "9":
			console.log("Puestos");
			break;
	}
	
	mostrarOcultarDivExclusiones(idDiv);
	
}

function mostrarOcultarDivExclusiones(divA) {
	var opcion = document.getElementById(divA);
	if (opcion != null) {
		opcion.style.display = "block";
	}

	document.querySelectorAll('#exclusiones-tabs div[style*="display: block;"]').forEach(function(checkElement) {
		console.log("mostrar Datos");
		if (divA != (checkElement.id)) {
			var opcion2 = document.getElementById(checkElement.id);
			if (opcion != null) {
				opcion2.style.display = "none";
			}
		} else {
			console.log(": Mostrar");
		}

	});

}

function mostrarOcultarDiv(divA) {
	var opcion = document.getElementById(divA);
	if (opcion != null) {
		opcion.style.display = "block";
	}

	document.querySelectorAll('#pant-tabs div[style*="display: block;"]').forEach(function(checkElement) {
		console.log("mostrar Datos");
		if (divA != (checkElement.id)) {
			var opcion2 = document.getElementById(checkElement.id);
			if (opcion != null) {
				opcion2.style.display = "none";
			}
		} else {
			console.log(": Mostrar");
		}

	});

}

const val = document.getElementsByName('diasVencer')[0];

if (val) {
	val.addEventListener('input', function() {
		let value = parseFloat(this.value);

		// Verificar si el valor es menor que 1 o si está vacío
		if (isNaN(value) || value < 1) {
			this.value = 1;
		}
	});
} else {
	console.error('No se encontró ningún elemento con el nombre "diasVencer"');
}

const value = document.getElementsByName('vigenciaGestion')[0];

if (value) {
	value.addEventListener('input', function() {
		let value = parseFloat(this.value);

		// Verificar si el valor es menor que 1 o si está vacío
		if (isNaN(value) || value < 1) {
			this.value = 1;
		}
	});
} else {
	console.error('No se encontró ningún elemento con el nombre "vigenciaGestion"');
}


document.addEventListener('DOMContentLoaded', function() {
	const form = document.getElementById('configuracionForm');

	form.addEventListener('submit', function(event) {

		event.preventDefault();

		fetch(form.action, {
			method: form.method,
			body: new FormData(form)
		})
			.then(response => {
				if (response.ok) {
					var divPantTabs = document.getElementById('params-tab1');
					var inputSearch = divPantTabs.querySelectorAll('.table-bordered input[type="search"]');

					// Iterar sobre los elementos input search y establecer el valor a una cadena vacía
					inputSearch.forEach(function(searchInput) {
   						 searchInput.value = '';
					});
					swal(
						'¡Guardado exitoso!',
						"Los datos se han guardado correctamente.",
						"success"
					)

					setTimeout(function() {
						swal.close(); // Cierra el SweetAlert
					}, 2000);
					location.reload();
				} else {
					swal(
						'Error!',
						"Hubo un problema al guardar los datos.",
						"error"
					)

					setTimeout(function() {
						swal.close(); // Cierra el SweetAlert
					}, 2000);
				}
			})
			.catch(error => {
				console.error('Error:', error);
				swal(
					'Error!',
					"Hubo un problema al guardar los datos.",
					"error"
				)

				setTimeout(function() {
					swal.close(); // Cierra el SweetAlert
				}, 2000);
			});
	});
});

var contenedorId = "";
var contenedor = "";
var opcionesSeleccionadasId = "";
var opcionesSeleccionadas = "";
var opcionesGuardadas="";

function previoGuardar(event){

	var divPantTabs = document.getElementById('params-tab1');
	var inputSearch = divPantTabs.querySelectorAll('.table-bordered input[type="search"]');
	var inputHidden = divPantTabs.querySelectorAll('.table-bordered input[type="hidden"]');

	// Iterar sobre los elementos input search
	inputSearch.forEach(function(searchInput) {
    // Obtener el ID del input search
    var searchInputId = searchInput.getAttribute('data-opciones-id');

    // Encontrar el elemento correspondiente en la lista de input hidden
    var hiddenInput = Array.from(inputHidden).find(function(hidden) {
        return hidden.id === searchInputId;
    });

    // Verificar si se encontró un elemento coincidente
    if (hiddenInput) {
        // Realizar la actualización, por ejemplo, asignar el valor del input hidden al input search
        searchInput.value = hiddenInput.value;
    }
});
	
}



function handleSearchInput(input) {
    contenedorId = input.getAttribute('data-contenedor-id');
    contenedor = document.getElementById(contenedorId);

    if (!input || !contenedor) {
        console.error('Input o contenedor no encontrado');
        return;
    }

    var selectedOption = input.value.trim();

    if (selectedOption !== '') {
        var nuevaOpcion = document.createElement('div');
        nuevaOpcion.className = 'list-group-item list-group-item-action d-flex justify-content-between align-items-center';
        nuevaOpcion.innerHTML = `
            ${selectedOption}
            <button type="button" class="btn btn-sm btn-danger ms-2" onclick="borrarOpcion(this)">X</button>
        `;

        nuevaOpcion.setAttribute('data-contenedor-id', contenedorId); // Guardar ID del contenedor en la opción

        contenedor.appendChild(nuevaOpcion);
        input.value = '';

        opcionesSeleccionadasId = input.getAttribute('data-opciones-id');
        opcionesSeleccionadas = document.getElementById(opcionesSeleccionadasId);

        if (!opcionesSeleccionadas) {
            console.error('Campo oculto no encontrado');
            return;
        }

        opcionesGuardadas = opcionesSeleccionadas.value.trim();

        if (opcionesGuardadas !== '') {
            opcionesGuardadas += '; ';
        }
        opcionesGuardadas += selectedOption;

        opcionesSeleccionadas.value = opcionesGuardadas.replace(/;;/g, ';').replace(/^;/, '');;

        console.log(opcionesGuardadas);
    }
}

function borrarOpcion(elemento) {
		
	borrarCargaInicial(elemento);
    var contenedorId = elemento.parentNode.getAttribute('data-contenedor-id'); // Obtener ID del contenedor desde el padre del botón
    elemento.parentNode.remove();

    var opciones = document.querySelectorAll(`#${contenedorId} > .list-group-item`);
    var opcionesActualizadas = Array.from(opciones).map(opcion => opcion.textContent.trim().replace('X', '')).join('; ');

    opcionesSeleccionadas.value = opcionesActualizadas;

}

function borrarCargaInicial(elemento){

		var opcionesId = $(elemento).closest('.col-sm-12').find('input[type="search"]').data('opciones-id');
		var valorSpan = $(elemento).siblings('span').text();
		
		// Obtén las opciones seleccionadas
		var opciones = document.getElementById(opcionesId).value;

		// Elimina el valor del span de las opciones seleccionadas
		opciones = opciones.replace(valorSpan, '');

		// Actualiza el valor del input hidden con las opciones modificadas
		document.getElementById(opcionesId).value = opciones.trim().replace(/;;/g, ';').replace(/^;/, '');
}