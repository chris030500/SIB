var cambiarEstatusRegla="reglas/cambiarEstatusRegla?idRegla=";
var url="";
$(document).ready(function() {
	eventos();
});

function getAbsolutePath() {
	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);
}

function eventos(){
	$("input").keydown(function() {
 		 return false
	});
}

function updateEstatusRegla(idRegla){
	Swal.fire({
		title: "¿Desea continuar? \nSeleccionar una opcion valida.",
		input: 'select',
      	inputOptions: {
        '1': 'ACTIVO',
        '0': 'INACTIVO'
        },
        inputPlaceholder: 'Seleccione',
		showCancelButton: true,
		confirmButtonText: "Acepto",
		cancelButtonText: "Cancelar",
		}).then(resultado => {
			
			if (resultado.value) {
       			url = getAbsolutePath()+cambiarEstatusRegla+idRegla+"&estatus="+resultado.value;
				peticionPostRegla(url,1);
      		}else{
				   Swal.fire("!Alerta¡","Se requiere seleccionar una opcion valida", "error");
			}
	});
}

function peticionPostRegla(urlServicio, servicio) {
	$.ajax({
		async: false,
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: '<span class="spinner-border"></span>' });
		},
		complete: function() {
			setTimeout($.unblockUI, 100);
		},
		success: function(respuesta) {
			swal("",respuesta[0],"success").then(resp => {

				if (servicio == 1) {
					obtTblReglas();
				}
				if (servicio == 2) {
					obtTblParamsAsig(idReglaParams);
				}
				setTimeout($.unblockUI, 100);
			});


		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("Error","Se presento un error, intente mas tarde.", "error");
		}
	});
}

var obtTblParams="reglas/obtRegistrosParams?idRegla=";
var idReglaParams = 0;
function obtTblParamsAsig(regla){
	 idReglaParams = regla;
	 url = getAbsolutePath()+obtTblParams+regla;
	 $.getJSON(url, {}, function(data) {
		 tblParamsAsig.clear().draw();
		 $.each(data, function(idx, opt) {
			console.log("registros tblParamsAsig");
			 //tblParamsAsig.row.add([opt.pk_id_regla,opt.vc_nombre_regla,opt.i_dias_vigencia,(opt.i_estatus == 1?'ACTIVO':'INACTIVO'),(opt.i_usuario_mod == null?'----':opt.i_usuario_mod.vc_nombre_usuario),(opt.dt_fecha_mod == null?'--/--/--':opt.dt_fecha_mod),botones]).draw(false);
		 });
	 });
}

function cerrarModalParams() {
		limpiarDatosParams();
		idReglaParams=0;
		$("#altaLiqRenovados").modal("hide");
}

function guardarModalParams() {
		if(guardarVacioParams() == 0){
			console.log("registrar datos");
			
		  Swal.fire({
				title: 'Procesando.....',
				html: '<span class="spinner-border"></span>',
				timer: 1000, // tiempo en milisegundos
				timerProgressBar: true,
				showConfirmButton: false
		  }).then((result) => {
			  if (result.dismiss === Swal.DismissReason.timer) {
				  servicioParams(2);//alta de registro 
			  }
			
		  });
			
			
		}else{
			swal("!Alerta¡", "Registrar datos obligatorios","error");
		}
		
}
var registroParams="reglas/registroParams?idRegla=";
function servicioParams(opcPeticion){
	
	switch(opcPeticion){
		case 2:
			url = getAbsolutePath()+registroParams+idReglaParams;
			url= url + "&slAsignar="+$("#slAsignar").val();
			url= url + "&porcRegistros="+$("#porcRegistros").val();
			url= url + "&tipoCliente="+$("#tipoCliente").val();
			url= url + "&slcTipoCartera="+$("#slcTipoCartera").val();
			url= url + "&tipoPrioridad="+$("#tipoPrioridad").val();
			url= url + "&slEstatus="+$("#slEstatus").val();
			break;
		case 3:
			break;
		default:
			break;
	}
	peticionPostRegla(url,opcPeticion);
	

}

function guardarVacioParams(){
	var validacion = 0;
	
	if (document.getElementById("slAsignar").value == '0') {
		document.getElementById("slAsignar").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("slAsignar").style.borderColor = '';
		validacion = validacion + 0;
	}
	if(document.getElementById("porcRegistros").value.length == 0){
		document.getElementById("porcRegistros").style.borderColor = "#F70707";
		validacion = validacion + 1;
	}else{
		document.getElementById("porcRegistros").style.borderColor = '';
		validacion = validacion + 0;
	}
	if (document.getElementById("tipoCliente").value == '0') {
		document.getElementById("tipoCliente").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("tipoCliente").style.borderColor = '';
		validacion = validacion + 0;
	}
	if (document.getElementById("slcTipoCartera").value == '0') {
		document.getElementById("slcTipoCartera").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("slcTipoCartera").style.borderColor = '';
		validacion = validacion + 0;
	}
	if (document.getElementById("tipoPrioridad").value == '0') {
		document.getElementById("tipoPrioridad").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("tipoPrioridad").style.borderColor = '';
		validacion = validacion + 0;
	}
	if (document.getElementById("slEstatus").value == '0') {
		document.getElementById("slEstatus").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("slEstatus").style.borderColor = '';
		validacion = validacion + 0;
	}
	
	return validacion;
}

function limpiarDatosParams(){
	$("#slAsignar").val(0);
	$("#porcRegistros").val('1');
	$("#tipoCliente").val(0);
	$("#slcTipoCartera").val(0);
	$("#tipoPrioridad").val(0);
	$("#slEstatus").val(0);
}

