var clienteValue = 0;
var celularValue = 0;
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

// Función para realizar la llamada al servicio después de un retraso
function debouncedListaCelClientes() {
	clearTimeout(timerId); 
	timerId = setTimeout(listaCelClientes, 500);
}

function listaCelClientes() {
	var buscar = document.getElementById('searchCellphone').value;
	var urlPrincipal = getAbsolutePath();
	//var newUrlBase = urlPrincipal.replace("/credito", "");
	
	$.ajax({
		url: urlPrincipal + "listaCelClientes?valor=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log(respuesta);
			var opciones = document.getElementById('cellphoneOption');
			opciones.innerHTML = '';
			respuesta.lista.forEach(function(cellphone) {
				var option = document.createElement('option');
				option.setAttribute('data-value', cellphone.telCelular);
				option.value = cellphone.telCelular;
				opciones.appendChild(option);
			});
			if (respuesta.lista[0]) {
				celularValue = respuesta.lista[0].telCelular;              
			}
			console.log(celularValue);
		}
	});
}

document.getElementById('searchCellphone').addEventListener('input', debouncedListaCelClientes);

document.getElementById('searchCellphone').addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	listaCelClientes();
});