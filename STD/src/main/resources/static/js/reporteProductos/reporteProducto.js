// @ts-nocheck
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
     	Swal.fire("", "Fechas Incorrectas. Verificar datos.", "warning");
    }	
  }
  
  
  function peticionPost(urlServicio) {
    
    
	Swal.fire({
	    title: "Procesando información.",
	    html: `
	        <div style="display: flex; justify-content: center; align-items: center; padding: 20px;">
	            <div class="spinner-border text-primary" role="status">
	                <span class="sr-only">Cargando...</span>
	            </div>
	        </div>
	    `,
	    showConfirmButton: false,
	    allowOutsideClick: false,
	    allowEscapeKey: false
	});
    
	$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {

			// Cerrar ventana de procesamiento
			Swal.close();
			
			Swal.fire({
			    title: "",
			    text: respuesta[0],
			    icon: "success",
			    allowOutsideClick: false,
			    confirmButtonText: "Aceptar"
			}).then(resp => {

				if (resp.isConfirmed) {
					window.open(window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[1]);
					window.focus();
				}
				
			});

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			// Cerrar ventana de carga
			Swal.close();
			Swal.fire({
			    title: "Error",
			    text: "Se presentó un error, intente más tarde.",
			    icon: "warning",
			    confirmButtonText: "Aceptar"
			});
		}
	});
}
  