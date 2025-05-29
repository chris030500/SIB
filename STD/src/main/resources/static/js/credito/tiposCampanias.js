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
	//alert("entra");
    const input = document.getElementById('buscarCampania');
    const valor = input.value;

    if (valor.length >= 2) {
        $.ajax({
            url: newUrlBase + "buscarCampanias?valor=" + encodeURIComponent(valor),
            type: "GET",
            dataType: "JSON",
            success: function(respuesta) {
                const datalist = document.getElementById('opcionCampania');
                datalist.innerHTML = '';

                if (respuesta.length === 0) {
                    const option = document.createElement('option');
                    option.value = 'No se encontraron campañas';
                    datalist.appendChild(option);
                } else {
                    respuesta.forEach(function(campania) {
                        const option = document.createElement('option');
                        option.setAttribute('data-value', campania.idCampania); // cambia este campo según tu modelo
                        option.value = campania.tipoCampania; // este también
                        datalist.appendChild(option);
                    });
                }
            },
            error: function(error) {
                console.error("Error en la búsqueda AJAX:", error);
            }
        });
    }
}