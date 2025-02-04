var clienteValue = 0;
var celularValue = "";
var timerId;
var mensajeError = "Datos incorrectos, favor de validar.";

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

function resetVariables() {
	clienteValue = 0;
	celularValue = "";
}

function cleanFields() {
	  const fieldsToClear = [
    	"customerOption",
    	"searchCustomer",
    	"cellphoneOption",
    	"searchCellphone",
    	"dtFechaInicio",
    	"dtFechaFin"
  	];
  	
  	fieldsToClear.forEach(id => {
		  const field = document.getElementById(id);
		  field.value = "";
	});
}

function validateDates(startDate, endDate) {

  var result = false;
  
  if(endDate < startDate) {
    mensajeError = "Fecha fin no debe ser menor a fecha inicio.";
    $("#dtFechaFin").css("border-color", "#F70707");
    result = true;
  }
  
  if(startDate > endDate) {
    mensajeError = "Fecha inicio no debe ser mayor a fecha fin.";
    $("#dtFechaInicio").css("border-color", "#F70707");
    result = true;
  }
  
  return result;
}

function generarReporte() {
	if (validateDates($("#dtFechaInicio").val(), $("#dtFechaFin").val()) == false) {
    	urlCompleta = getAbsolutePath() + "generarRptConsumoSMS?fechaInicio=" + $("#dtFechaInicio").val()
    	+ "&fechaFin=" + $("#dtFechaFin").val() 
    	+ "&idCliente=" + clienteValue
    	+ "&telCelular=" + celularValue;
    	
		peticionPost(urlCompleta);
		
	} else {

		swal({
			title: "",
			text: mensajeError,
			icon: "warning",
		});
	}

}

function peticionPost(urlServicio) {
    // Bloqueo de UI (simulación)
    document.body.style.cursor = "wait";

    fetch(urlServicio, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error en la petición");
        }
        return response.json();
    })
    .then(respuesta => {
        let baseUrl = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4);
        let urlReporte = baseUrl + respuesta[0];
        window.open(urlReporte);
        window.focus();
        
        cleanFields();
        resetVariables();
    })
    .catch(error => {
        swal({
            title: "Error",
            text: "Se presentó un error, intente más tarde.",
            icon: "warning"
        });
    })
    .finally(() => {
        // Desbloqueo de UI (simulación)
        setTimeout(() => document.body.style.cursor = "default", 500);
    });
}

