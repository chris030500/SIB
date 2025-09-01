// @ts-nocheck

$(document).ready(function() {

});
var urlCliente="";
var actualizarClienteB = "clientes/cambiarEstatus?idBloqueo=";
function cambiarEstatusCliente(idEstatus){
	console.log("proceso"+idEstatus);
	var estatus = $(idEstatus).attr("for");
	var idB = $(idEstatus).attr("id");
	
		Swal.fire({
		title: (estatus == 1)?"Motivo de Bloqueo.":"Motivo de Desbloqueo.",
		input: "textarea",
		showCancelButton: true,
		confirmButtonText: "Guardar",
		cancelButtonText: "Cancelar",
		inputValidator: descripcion => {
			// Si el valor es válido, debes regresar undefined. Si no, una cadena
			if (!descripcion) {
				return (estatus == 1)?"Por favor escribe el motivo de Bloqueo":"Por favor escribe el motivo de Desbloqueo";
			} else {
				return undefined;
			}
		}
		})
		.then(resultado => {
			if (resultado.value) {
				let motivoRechazo = resultado.value;
				urlCliente = getAbsolutePath()+actualizarClienteB+idB;
				urlCliente = urlCliente+"&motivoBloqueo="+encodeURIComponent(motivoRechazo);
				urlCliente = urlCliente+"&estatus="+estatus;
				
				peticionPostCliente(1,urlCliente);
			}
		});
	
}

function peticionPostCliente(metodo,metodoUrl){
	
		Swal.fire({
    		title: 'Procesando..',
    		html: 'Espere Por Favor...',
    		allowOutsideClick: false,
    		showConfirmButton: false,
    		onBeforeOpen: () => {
      			Swal.showLoading();
    		}
  		});
 		$.ajax({
		url: metodoUrl,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
	    
		},
		complete: function() {
			
		},
		success: function(respuesta) {
   			url = "";
			Swal.fire("",respuesta[0],"success").then(resp => {

				if (metodo == 1) {
     				obtTblClienteB();
				}
			});


		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("Error","Se presento un error, intente mas tarde.", "error");
		}
	});
}

function getAbsolutePath() {
	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);
}
