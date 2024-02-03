/**
 ////////////////----DES-24-0002 Historico de Operaciones----////////////////
////////////////----viviana.zamudio-------------------------////////////////
 */
var rptOA = "reporteHistoricoPLD?idReporte="
/* revisar la ruta rptOA porque a esta ruta se va para ejecutar */
  var url="";
  var mensajeError = "Datos incorrectos, favor de validar.";
  function valVacio(fecha1,fecha2){
   	var result = false;
   	var fechaInicio = Date.parse(fecha1);
	var fechaFin = Date.parse(fecha2);

	if (fechaInicio > fechaFin) {
  		mensajeError = "Fecha inicio no debe ser mayor a fecha fin.";
		result = true;
	}

	if (fechaFin < fechaInicio) {
  		mensajeError = "Fecha fin no debe ser menor a fecha fin.";
		result = true;
	}
	
	if (fecha1.length == 0) {
		result = true;
	}
	if (fecha2.length == 0) {
		result = true;
	}

    // @ts-ignore
    if(document.getElementById("idReportePLD").value > 0){
       	document.getElementById("idReportePLD").style.borderColor = "";
       	// @ts-ignore
       	url = url + rptOA+document.getElementById("idReportePLD").value;
       	result = false;
    }else{
     	mensajeError = "Debe seleccionar un reporte.";
     	document.getElementById("idReportePLD").style.borderColor = "#F70707";
     	result = true;
    }  

	if (result == true) {
		document.getElementById("dtFechaInicio").style.borderColor = "#F70707";
		document.getElementById("dtFechaFin").style.borderColor = "#F70707";
	} else {
		document.getElementById("dtFechaInicio").style.borderColor = "";
		document.getElementById("dtFechaFin").style.borderColor = "";
		// @ts-ignore
		url = url + "&dtFechaInicio="+document.getElementById("dtFechaInicio").value+"&dtFechaFin="+document.getElementById("dtFechaFin").value+"&nombreReporteSelect="+document.getElementById("idReportePLD").value;
	}
   	return result;
  }
  
function generarReporte(){

   	// @ts-ignore
   	if(valVacio(document.getElementById("dtFechaInicio").value,document.getElementById("dtFechaFin").value) == false){
     	peticionPost(url);
     	
    }else{
     
     	swal({
				title: "",
				text: mensajeError,
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
				
				console.log(urlReporte);
				//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
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