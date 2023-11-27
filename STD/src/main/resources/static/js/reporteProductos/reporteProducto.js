/**
 * 
 */
  var rptOA = "reporteOA?idReporte="
  var url="";
  function valVacio(fecha1,fecha2){
   	var result = false;
   	var fechaInicio = Date.parse(fecha1);
	var fechaFin = Date.parse(fecha2);

	if (fechaInicio > fechaFin) {
		result = true;
	}

	if (fechaFin < fechaInicio) {
		result = true;
	}
	
	if (fecha1.length == 0) {
		result = true;
	}
	if (fecha2.length == 0) {
		result = true;
	}
	

	
    if(document.getElementById("idReporteProducto").value > 0){
       	document.getElementById("idReporteProducto").style.borderColor = "";
       	url = url + rptOA+document.getElementById("idReporteProducto").value;
       	result = false;
    }else{
     	document.getElementById("idReporteProducto").style.borderColor = "#F70707";
     	result = true;
    }  

	if (result == true) {
		document.getElementById("dtFechaInicio").style.borderColor = "#F70707";
		document.getElementById("dtFechaFin").style.borderColor = "#F70707";
	} else {
		document.getElementById("dtFechaInicio").style.borderColor = "";
		document.getElementById("dtFechaFin").style.borderColor = "";
		url = url + "&dtFechaInicio="+document.getElementById("dtFechaInicio").value+"&dtFechaFin="+document.getElementById("dtFechaFin").value;
	}
   	return result;
  }
  
function generarReporte(){
   	if(valVacio(document.getElementById("dtFechaInicio").value,document.getElementById("dtFechaFin").value) == false){
     	peticionPost(url);
    }else{
     	swal({
				title: "",
				text: "Fechas Incorrectas. Verificar datos.",
				icon: "warning",
		});
    }	
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

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}
  