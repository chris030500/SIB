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
	timerId = setTimeout(listaClientes, 500); // Establecer un nuevo temporizador
}


buscarCliente.addEventListener('input', debouncedListaClientes);

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	listaClientes();
});
/**
 * Fin
 */

function buscarCampanias() {
    const valor = document.getElementById('buscarCampania').value;

    if (valor.length >= 2) {
        fetch('/credito/buscarCampanias?valor=' + encodeURIComponent(valor))
            .then(response => response.json())
            .then(data => {
                console.log("Respuesta recibida:", data); // 👈 clave

                const datalist = document.getElementById('opcionCampania');
                datalist.innerHTML = '';

                data.forEach(campania => {
                    const option = document.createElement('option');
                    option.value = campania.tipoCampania; // ajusta el campo si es necesario
                    datalist.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al buscar campañas:", error);
            });
    }
}

