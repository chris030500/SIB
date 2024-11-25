var productoSelected = "";
var idProducto = "";
var isCliente = false;
var isProducto = false;
var clienteValue = 0;
var mensajeError = "Datos incorrectos, favor de validar.";
var urlCompleta;
var buscarCliente = document.getElementById('searchCustomer');
var timerId;

function customerSearchSelect() {
  var select = document.getElementById('selectBusquedaCliente');
  
  select.addEventListener('change', () => {
    var selectValue = parseInt(select.value);
    console.log(selectValue);
    
    var div = document.getElementById('divCustomerSearch');
    
    if (selectValue > 0) {
      switch (selectValue) {
        case 1:
          div.style.display = "block";
          break;
        case 2:
          div.style.display = "none";
          clienteValue = 0;
          isCliente = true;
          break;
      }
    }
  });
}

function getProduct(idSelect) {
  var select = document.getElementById(idSelect);
  
  select.addEventListener('change', () => {
    var selectedOptions = Array.from(select.selectedOptions);
    var values = selectedOptions.map(option => option.value);
    
    idProducto = values.join(',');
    isProducto = true;
    
    console.log('Valores seleccionados: ', idProducto);
  });
}

function typeProductSelect() {
  var select = document.getElementById('selectTipoProducto');
  
  select.addEventListener('change', () => {
    var selectValue = parseInt(select.value);
    console.log(selectValue);
    
    var divProductosColocacion = document.getElementById('divProductosColocacion');
    var divProductoCaptacion = document.getElementById('divProductosCaptacion');
    
    if (selectValue > 0) {
   
      switch (selectValue) {
        case 1:
          divProductosColocacion.style.display = "block";
          divProductoCaptacion.style.display = "none";
          getProduct('selectColocacion');
          break;
        case 2:
          divProductosColocacion.style.display = "none";
          divProductoCaptacion.style.display = "block";  
          getProduct('selectCaptacion');
          break;        
        case 3:
          divProductosColocacion.style.display = "none";
          divProductoCaptacion.style.display = "none";
          idProducto = "0";
          isProducto = true;
          break;
      }
      
    }
    
  });
}

window.onload = function(){
  var fecha = new Date(); //Fecha actual
  var mes = fecha.getMonth()+1; //obteniendo mes
  var dia = fecha.getDate(); //obteniendo dia
  var ano = fecha.getFullYear(); //obteniendo año
  if(dia<10)
    dia='0'+dia; //agrega cero si el menor de 10
  if(mes<10)
    mes='0'+mes //agrega cero si el menor de 10
  document.getElementById('startDate').value=ano+"-"+mes+"-"+dia;
  document.getElementById('endDate').value=ano+"-"+mes+"-"+dia;
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function validateDates(startDate, endDate) {

  var result = false;
  
  if(endDate < startDate) {
    mensajeError = "Fecha fin no debe ser menor a fecha inicio.";
    $("#endDate").css("border-color", "#F70707");
    result = true;
  }
  
  if(startDate > endDate) {
    mensajeError = "Fecha inicio no debe ser mayor a fecha fin.";
    $("#startDate").css("border-color", "#F70707");
    result = true;
  }
  
  return result;
}

function generarReporte() {

	if (validateDates($("#startDate").val(), $("#endDate").val()) == false) {
    	urlCompleta = getAbsolutePath() + "generarRptOperacionesInusuales?fechaInicio=" + $("#startDate").val()
    	+ "&fechaFin=" + $("#endDate").val() 
    	+ "&noCliente=" + clienteValue
    	+ "&idProducto=" + idProducto 
    	+ "&isCliente=" + isCliente 
    	+ "&isProducto=" + isProducto;
    	
		peticionPost(urlCompleta);
		
	} else {

		swal({
			title: "",
			text: mensajeError,
			icon: "warning",
		});
	}

}

function cleanView() {
  const divsToHide = [
    "divProductosColocacion",
    "divProductosCaptacion",
    "divCustomerSearch"
  ];

  divsToHide.forEach(id => {
    const div = document.getElementById(id);
    if (div) {
      div.style.display = "none";
    }
  });
  
  cleanFields();
  cleanVariables();
}

function cleanVariables() {
  idProducto = "";
  clienteValue = 0;
  isCliente = false;
  isProducto = false;
}

function cleanFields() {
  const fieldsToClear = [
    "selectTipoProducto",
    "selectColocacion",
    "selectCaptacion",
    "selectBusquedaCliente",
    "customerOption",
    "searchCustomer"
  ];
  
    // Iterar sobre cada ID y limpiar su valor
  fieldsToClear.forEach(id => {
    const field = document.getElementById(id); // Obtener el campo por su ID
    if (field) {
      // Si es un <select>, asignar "0"; si no, asignar cadena vacía
      field.value = field.tagName === "SELECT" ? "0" : "";
    }
  });
}

function peticionPost(urlServicio) {
	$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];
			window.open(urlReporte);
			window.focus();
			
			cleanView();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}

// Función para realizar la llamada al servicio después de un retraso
function debouncedListaClientes() {
	clearTimeout(timerId); 
	timerId = setTimeout(listaClientes, 500);
}

function listaClientes() {
	var buscar = buscarCliente.value;
	var urlPrincipal = getAbsolutePath();
	var newUrlBase = urlPrincipal.replace("/prevencion", "");
	
	$.ajax({
		url: newUrlBase + "lista_numero_cliente?valor=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('customerOption');
			opciones.innerHTML = '';
			respuesta.lista.forEach(function(cliente) {
				var option = document.createElement('option');
				option.setAttribute('data-value', cliente.i_clienteid);
				option.value = cliente.clienteinfo;
				opciones.appendChild(option);
			});
			if (respuesta.lista[0]) {
				clienteValue = respuesta.lista[0].i_clienteid;   
				isCliente = true;           
			}
			console.log(clienteValue);
		}
	});
}

buscarCliente.addEventListener('input', debouncedListaClientes);

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	listaClientes();
});

document.addEventListener('DOMContentLoaded', () => {
  typeProductSelect();
  customerSearchSelect();
});
