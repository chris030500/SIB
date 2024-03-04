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
					swal(
						'¡Guardado exitoso!',
						"Los datos se han guardado correctamente.",
						"success"
					)

					setTimeout(function() {
						swal.close(); // Cierra el SweetAlert
					}, 2000);
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

        var opcionesGuardadas = opcionesSeleccionadas.value.trim();

        if (opcionesGuardadas !== '') {
            opcionesGuardadas += '; ';
        }
        opcionesGuardadas += selectedOption;

        opcionesSeleccionadas.value = opcionesGuardadas;

        console.log(opcionesGuardadas);
    }
}

function borrarOpcion(elemento) {
    if (!elemento || !elemento.parentNode) {
        console.error('Elemento o elemento padre no encontrado');
        return;
    }

    var contenedorId = elemento.parentNode.getAttribute('data-contenedor-id'); // Obtener ID del contenedor desde el padre del botón

    if (!contenedorId) {
        console.error('ID del contenedor no encontrado');
        return;
    }

    var opcionBorrada = elemento.parentNode.textContent.trim().replace('X', ''); // Eliminar la X de la opción
    elemento.parentNode.remove();

    if (!opcionesSeleccionadas) {
        console.error('Campo oculto no encontrado');
        return;
    }

    var opciones = document.querySelectorAll(`#${contenedorId} > .list-group-item`);
    var opcionesActualizadas = Array.from(opciones).map(opcion => opcion.textContent.trim().replace('X', '')).join('; ');

    opcionesSeleccionadas.value = opcionesActualizadas;

    console.log(opcionesActualizadas);
}
