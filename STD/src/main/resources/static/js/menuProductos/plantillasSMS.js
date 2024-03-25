// @ts-nocheck
var buscarClasificacion="plantillas/obtClasificacion?idBase=";
var registrarPlantilla="plantillas/registrarPlantillas?nombrePlantilla=";
var modPlantilla="plantillas/modPlantilla?idPlantilla=";
var buscarPlantilla="plantillas/obtPlantilla?idPlantilla=";
var url="";
$(document).ready(function() {
	limpiarDatosPlantilla();
	eventosConNombre();
	eventosContenido();
});

function eventoCerrar() {
		limpiarDatosPlantilla();
		$("#altaPlantilla").modal("hide");
		idPlantillaMod=0;
}

function eventoGuardar(){
	/**
	 * validar datos. 
	 */
	
	if(guardarVacioPlantilla() == 0){
		//servicio registro plantilla
		
		if (idPlantillaMod == 0){
			serviciosSMS(1);
		}
		if(idPlantillaMod >0){
			serviciosSMS(2);	
		}	

	}else{
		swal("!Alerta¡", "Registrar datos obligatorios","error");
	}
	
}

function getAbsolutePath() {
	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);
}


function serviciosSMS(servicio){
	var urlServicio="";
	switch(servicio){
		case 1:
				urlServicio = getAbsolutePath() + registrarPlantilla;
				urlServicio = urlServicio + encodeURIComponent(document.getElementById("inpNombrePlantilla").value);
				urlServicio = urlServicio +"&slcCampania="+ $("#slcCampania").val();
				urlServicio = urlServicio +"&slcClasificacion="+ $("#slcClasificacion").val();
				urlServicio = urlServicio +"&txtContenido="+ encodeURIComponent(document.getElementById("txtContenido").value);
				urlServicio = urlServicio +"&slcEstatus="+ $("#slcEstatus").val();
			break;
		case 2:
				//var modPlantilla="plantillas/modPlantilla?idPlantilla=";
				urlServicio = getAbsolutePath() + modPlantilla+idPlantillaMod;
				urlServicio = urlServicio +"&nombrePlantilla="+ encodeURIComponent(document.getElementById("inpNombrePlantilla").value);
				urlServicio = urlServicio +"&slcCampania="+ $("#slcCampania").val();
				urlServicio = urlServicio +"&slcClasificacion="+ $("#slcClasificacion").val();
				urlServicio = urlServicio +"&txtContenido="+ encodeURIComponent(document.getElementById("txtContenido").value);
				urlServicio = urlServicio +"&slcEstatus="+ $("#slcEstatus").val();
			break;
		default:
			break;
	}
	
	peticionPost(urlServicio, servicio);
	
}

function peticionPost(urlServicio, servicio) {
	
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
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
		},
		complete: function() {
		},
		success: function(respuesta) {
			swal("",respuesta.mensaje,respuesta.tipo).then(resp => {

				if (servicio == 1) {
					//limpiarDatosPlantilla();
					obtTblPlantillas();
				
				}
				
				if (servicio == 2) {
					obtTblPlantillas();
					//limpiarDatosPlantilla();
					//idPlantillaMod=0;
					//$("#altaPlantilla").modal("hide");
				}
				
				
				setTimeout($.unblockUI, 100);
			});


		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("Error","Se presento un error, intente mas tarde.", "error");
		}
	});
}


function limpiarDatosPlantilla(){
	$("#inpNombrePlantilla").val('');
	$("#slcCampania").val(0);
	$("#slcClasificacion").empty();
	$("#slcClasificacion").prepend("<option value=0  selected='selected'>Seleccione</opcion>")
	$("#txtContenido").val('');
	$("#slcEstatus").val(0);
}

function obtDatosClass(idClass) {
	if (idClass.value > 0) {
		url = getAbsolutePath()+buscarClasificacion+idClass.value;
		
		listaClasifacion(url,0);
	} else {
		swal({
			title: "",
			text: "Seleccione un tipo de Campaña.",
			icon: "warning",
		});
		
		$("#slcClasificacion").empty();
		$("#slcClasificacion").prepend("<option value=0  selected='selected'>Seleccione</opcion>");
	}
}

function listaClasifacion(url,valor) {
	$.getJSON(url, {}, function(data) {
		$("#slcClasificacion").empty();
		$.each(data, function(idx, opt) {
			if (valor == opt.pk_id_clasificacion) {
				$("#slcClasificacion").prepend("<option value=" + opt.pk_id_clasificacion + " text=" + opt.vc_abreviacion_clasificacion + " selected='selected'>" + opt.vc_abreviacion_clasificacion + "</opcion>")
			} else {
				$("#slcClasificacion").prepend("<option value=" + opt.pk_id_clasificacion + " text=" + opt.vc_abreviacion_clasificacion + ">" + opt.vc_abreviacion_clasificacion + "</opcion>")
			}
		});
	});
}


function eventosConNombre() {
	const mensaje = document.getElementById('inpNombrePlantilla');
	const contador = document.getElementById('contNomPlant');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function eventosContenido() {
	const mensaje = document.getElementById('txtContenido');
	const contador = document.getElementById('contador');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}


function guardarVacioPlantilla() {
	var validacion = 0;
	
	if(document.getElementById("inpNombrePlantilla").value.length == 0){
		document.getElementById("inpNombrePlantilla").style.borderColor = "#F70707";
		validacion = validacion + 1;
	}else{
		document.getElementById("inpNombrePlantilla").style.borderColor = '';
		validacion = validacion + 0;
	}
	
	if (document.getElementById("slcCampania").value == '0') {
		document.getElementById("slcCampania").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("slcCampania").style.borderColor = '';
		validacion = validacion + 0;
	}
	
	if (document.getElementById("slcClasificacion").value == '0') {
		document.getElementById("slcClasificacion").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("slcClasificacion").style.borderColor = '';
		validacion = validacion + 0;
	}
	
	if(document.getElementById("txtContenido").value.length == 0){
		document.getElementById("txtContenido").style.borderColor = "#F70707";
		validacion = validacion + 1;
	}else{
		document.getElementById("txtContenido").style.borderColor = '';
		validacion = validacion + 0;
	}
	if (document.getElementById("slcEstatus").value == '0') {
		document.getElementById("slcEstatus").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("slcEstatus").style.borderColor = '';
		validacion = validacion + 0;
	}

	return validacion;
}

var idPlantillaMod=0;
function editarPlantilla(idEditar){
	console.log("Plantillas Editar"+idEditar);
	url = getAbsolutePath()+buscarPlantilla+idEditar;
	obtPlantilla(url);

	idPlantillaMod=idEditar;
	
	mensajeBanner();
	
}

function mensajeBanner(){
		// Mostrar el mensaje de procesamiento utilizando Swal
    Swal.fire({
        title: 'Consultando Informacion...',
        html: 'Espere por favor...',
        allowOutsideClick: false,
        showConfirmButton: false,
        onBeforeOpen: () => {
            Swal.showLoading();
        }
    });
}

function obtPlantilla(urlObt){
	
		$.getJSON(url, {}, function(data) {
					cargarDatos(data);
					Swal.close();
		});
		
}

function cargarDatos(data) {

	document.getElementById("inpNombrePlantilla").value = data.vc_nombre_plantilla;
	document.getElementById("slcCampania").value = data.fk_ID_BASE.pk_id_base;
	document.getElementById("txtContenido").value = data.vc_contenido;
	document.getElementById("slcEstatus").value = data.i_activo;
	//llenar los datos de la lista de clasificaciones
	url = getAbsolutePath()+buscarClasificacion+data.fk_ID_BASE.pk_id_base;
	listaClasifacion(url,data.fk_ID_CLASIFICACION.pk_id_clasificacion);
	
	
}

function actualizarEstatus(idEstatus){
	//var buscarPlantilla="plantillas/obtPlantilla?idPlantilla=";
	console.log("Plantillas actualizar estatus"+idEstatus);
	//url = getAbsolutePath()+buscarPlantilla+idEstatus;
}