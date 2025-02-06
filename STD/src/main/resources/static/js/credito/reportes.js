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
	var url = urlPrincipal + "listaClientes?valor=" + buscar;
	
	fetch(url)
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la petición: " + response.statusText);
			}
			return response.json();
		})
		.then(res => {
			//console.log(res);
			var opciones = document.getElementById('customerOption');
			opciones.innerHTML = '';
			
			if (res.length > 0) {
				res.forEach(cliente => {
					var option = document.createElement('option');
					option.setAttribute('data-value', cliente.idCliente);
					option.value = cliente.clienteInfo;
					opciones.appendChild(option);
				});
				
				clienteValue = res[0].idCliente;
				//console.log(clienteValue);
			} else {
				console.warn("No se encontraron clientes.");
			}
		})
		.catch(error => {
			console.log("Error al obtener clientes:", error);
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
	var url = urlPrincipal + "listaCelClientes?valor=" + buscar;
	
		fetch(url)
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la petición: " + response.statusText);
			}
			return response.json();
		})
		.then(res => {
			console.log(res);
			var opciones = document.getElementById('cellphoneOption');
			opciones.innerHTML = '';
			
			if (res.length > 0) {
				res.forEach(cellphone => {
					var option = document.createElement('option');
					option.setAttribute('data-value', cellphone.telCelular);
					option.value = cellphone.telCelular;
					opciones.appendChild(option);
				});
				
				celularValue = res[0].telCelular;
				console.log(celularValue);
			} else {
				console.warn("No se encontraron celulares.");
			}
		})
		.catch(error => {
			console.log("Error al obtener celulares:", error);
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

