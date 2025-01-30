var clienteValue = 0;
var timerId;

// Función para realizar la llamada al servicio después de un retraso
function debouncedListaClientes() {
	clearTimeout(timerId); 
	timerId = setTimeout(listaClientes, 500);
}

function listaClientes() {
	var buscar = document.getElementById('searchCustomer').value;
	var urlPrincipal = getAbsolutePath();
	//var newUrlBase = urlPrincipal.replace("/credito", "");
	
	$.ajax({
		url: urlPrincipal + "listaClientes?valor=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log(respuesta);
			var opciones = document.getElementById('customerOption');
			opciones.innerHTML = '';
			respuesta.lista.forEach(function(cliente) {
				var option = document.createElement('option');
				option.setAttribute('data-value', cliente.idCliente);
				option.value = cliente.clienteInfo;
				opciones.appendChild(option);
			});
			if (respuesta.lista[0]) {
				clienteValue = respuesta.lista[0].idCliente;              
			}
			console.log(clienteValue);
		}
	});
}

document.getElementById('searchCustomer').addEventListener('input', debouncedListaClientes);

document.getElementById('searchCustomer').addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	listaClientes();
});