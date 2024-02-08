$(document).ready(function() {

});
var urlCliente="";
var actualizarClienteB = "clientes/cambiarEstatus?idBloqueo=";
function cambiarEstatusCliente(idEstatus){
	console.log("proceso"+idEstatus);
	var estatus = $(idEstatus).attr("for");
	var idB = $(idEstatus).attr("id");
	
	console.log(""+$(idEstatus).attr("id"));
	if(idEstatus == 2){
		Swal.fire({
		title: "Motivo de Desbloqueo.",
		input: "textarea",
		showCancelButton: true,
		confirmButtonText: "Guardar",
		cancelButtonText: "Cancelar",
		inputValidator: descripcion => {
			// Si el valor es válido, debes regresar undefined. Si no, una cadena
			if (!descripcion) {
				return "Por favor escribe el motivo para rechazar";
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
	}else{
		urlCliente = getAbsolutePath()+actualizarClienteB+idB;
		urlCliente = urlCliente+"&motivoBloqueo="+"NA";
		urlCliente = urlCliente+"&estatus="+estatus;
				
		peticionPostCliente(1,urlCliente);
	}
	
}

function peticionPostCliente(metodo,metodoUrl){
 		$.ajax({
		url: metodoUrl,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			//$.blockUI({ message: '<span style="color: #007BFF;"><i class="fa-solid fa-gear"></i>Procesando Información.' });
			$.blockUI({
      message: '<h4>Procesando Información...</h4><div class="spinner-border" style="width: 3rem; height: 3rem;" role="status"></div> ',
      css: {
        border: 'none',
        padding: '15px',
        backgroundColor: '#fff',
        '-webkit-border-radius': '10px',
        '-moz-border-radius': '10px',
        opacity: .8,
        color: '#000'
      }
    });
		},
		complete: function() {
			setTimeout($.unblockUI, 300);
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
