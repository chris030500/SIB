var tipoProductoSelected = "";
var productoSelected = "";
var idProducto = 0;
var isCliente = false;
var isProducto = false;
var clienteValue = 0;

$(document).ready(function() {
  $("#selectTipoProducto").change(function(){
    
    cleanFields();
    defaultView();
  
    tipoProductoSelected = $("#selectTipoProducto").val();
    
   if(tipoProductoSelected > 0) {
     switch(tipoProductoSelected) {
       case "1":
         $("#divProductosColocacion").show();
         $("#divProductosCaptacion").hide();
         
         break;
       case "2":
         $("#divProductosColocacion").hide();
         $("#divProductosCaptacion").show();
         break;
       case "3":
         $("#divProductosColocacion").hide();
         $("#divProductosCaptacion").hide();
         isProducto = true;
         break;
     }
   }    
  });
  getCheck();
});

function defaultView() {
   $("#divProductosColocacion").hide();
   $("#divProductosCaptacion").hide();
}

function 

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

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/prevencion", "");
var url = "";
var mensajeError = "Datos incorrectos, favor de validar.";
var urlCompleta;

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
  
  if(isCliente == false && isProducto == false) {
    mensajeError = "Debes de seleccionar un filtro de búsqueda por tipo producto o cliente.";
    result = true;
  }
  
  productType();
  
  return result;
}

function getCheck() {
  var checkbox = document.getElementById('customerCheck');
  var divCustomerSearch = document.getElementById('divCustomerSearch');
  var isChecked = checkbox.checked; 
  
  checkbox.addEventListener('change', () => {
    if (isChecked == false) {
      isCliente = true;
      divCustomerSearch.style.display = "none";
      isChecked = true;
      console.log(isChecked);
    } else {
      clienteValue = 0;
      divCustomerSearch.style.display = "block";
      isChecked = false;
      console.log(isChecked);
    }
  });
}

function getProduct(select) {
  
  var selectValue = document.getElementById(select).value;;
  
  if(selectValue > 0) {
    idProducto = selectValue;
    isProducto = true;
  }
}

function productType(){
  var select = "";
  
  switch(tipoProductoSelected) {
    case "1":
      select = "selectColocacion";
      
      getProduct(select);
      break;
    case "2":
      select = "selectCaptacion";

      getProduct(select);
      break;
  }
  
}

function generarReporte() {

	if (validateDates($("#startDate").val(), $("#endDate").val()) == false) {
    	urlCompleta = urlPrincipal + "generarRptOperacionesInusuales?fechaInicio=" + $("#startDate").val()
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
  $("#selectTipoProducto").val("0");
  
  cleanFields();
  defaultView();
}

function cleanVariables() {
  idProducto = 0;
  clienteValue = 0;
  isCliente = false;
  isProducto = false;
}

function cleanFields() {
  $("#selectColocacion").val("0");
  $("#selectCaptacion").val("0");
  $("#customerOption").val("");
  $("#searchCustomer").val("");
  
  cleanVariables();
}

function peticionPost(urlServicio) {
	//console.log(urlServicio);
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

/**
* Lista clientes
*/
var buscarCliente = document.getElementById('searchCustomer');
var timerId;


// Función para realizar la llamada al servicio después de un retraso
function debouncedListaClientes() {
	clearTimeout(timerId); 
	timerId = setTimeout(listaClientes, 500);
}

function listaClientes() {
	var buscar = buscarCliente.value;
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
