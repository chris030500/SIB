// @ts-nocheck
/**
 ////////////////----DES-25-0014 Campanias----////////////////
////////////////----viviana.zamudio-------------------------////////////////
 */
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");
/**
 * Fin
 */
/**
* Lista clientes
*/
var buscarCliente = document.getElementById('buscarCampania');
var spinner = document.getElementById("spinnerClientes");
var clienteValue = 0;
var timerId;


// Función para realizar la llamada al servicio después de un retraso
function debouncedListaClientes() {
	clearTimeout(timerId); // Limpiar el temporizador anterior, si existe
	timerId = setTimeout(buscarCampanias, 500); // Establecer un nuevo temporizador
}



/**
 * Fin
 */

function buscarCampanias() {
    var buscar = buscarCliente.value;
    //console.log(newUrlBase);
	$.ajax({
		url: newUrlBase + "credito/buscarCampanias?valor=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionCampania');
			opciones.innerHTML = '';
			respuesta.lista.forEach(function(cliente) {
				
				//console.log(cliente);
				var option = document.createElement('option');
				option.setAttribute('data-value', cliente.vc_tipo_campania);
				
				option.value = cliente.vc_tipo_campania;
				opciones.appendChild(option);
			});
			if (respuesta.lista[0]) {
				clienteValue = respuesta.lista[0].vc_tipo_campania; // Aquí asumo que deseas guardar el primer valor de i_clienteid                
			}
			//console.log(clienteValue);
			//console.log(opciones.innerHTML);
		}
	});
}

buscarCliente.addEventListener('input', debouncedListaClientes);

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	buscarCampanias();
});

const input = document.getElementById('buscarCampania');
const datalist = document.getElementById('opcionCampania');
const divDetalle = document.getElementById('detalleCampania');

input.addEventListener('input', function () {
    const valor = input.value;
    let coincidencia = false;

    // Recorremos todas las opciones del datalist
    for (let option of datalist.options) {
        if (option.value === valor) {
            coincidencia = true;
            break;
        }
    }

    // Si hay coincidencia, mostramos el div
    if (coincidencia) {
        divDetalle.style.display = 'block';
    } else {
        divDetalle.style.display = 'none';
    }
});
