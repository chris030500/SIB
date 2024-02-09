var cambiarEstatusRegla="reglas/cambiarEstatusRegla?idRegla=";
var url="";
$(document).ready(function() {
	eventos();
});

function getAbsolutePath() {
	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);
}

function eventos(){
	/*
	// Lista de valores posibles
    const valoresPosibles = ["#MONTO", "#TEL_SUC", "#COD_REFERENCIA"];

    // Obtén el textarea
    const textarea = document.getElementById("txtContenido");

	
	textarea.addEventListener("input", function() {
      // Obtén el valor actual del textarea
      const valorActual = textarea.value.toLowerCase();

      // Filtra los valores posibles que coincidan con la entrada del usuario
      const valoresFiltrados = valoresPosibles.filter(valor => valor.toLowerCase().includes(valorActual));

      // Muestra la lista de valores en el textarea
      textarea.value = textarea.value +valoresFiltrados.join("\n");
    });*/
}

function updateEstatusRegla(idRegla){
	Swal.fire({
		 title: '<span style="color: #007BFF;"><i class="fas fa-question-circle"></i> Actualizar Estatus.</span>',
  		 html: '<div id="swal-input-container" class="form-group"><select class="form-control" id="swal-select" placeholder="Seleccione Estatus"><option value="1">ACTIVO</option><option value="2">INACTIVO</option></select></div>',
  		 showCancelButton: true,
  confirmButtonText: "Acepto",
  cancelButtonText: "Cancelar",
  preConfirm: () => {
    const selectedValue = document.getElementById('swal-select').value;
    if (!selectedValue) {
      Swal.showValidationMessage("Seleccione una opción válida.");
    } else {
      return selectedValue;
    }
  }
		}).then(resultado => {
			
			if (resultado.value) {				
       			url = getAbsolutePath()+cambiarEstatusRegla+idRegla+"&estatus="+resultado.value;
				peticionPostRegla(url,1);
      		}
	});
}

function peticionPostRegla(urlServicio, servicio) {
	$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
		Swal.fire({
				title: '<span style="color: #007BFF;"><i class="fa-solid fa-gear"></i> Procesando Información.',
				html: '<span class="spinner-border"></span>',
				timer: 500, // tiempo en milisegundos,
				icon: 'info',
				timerProgressBar: true,
				showConfirmButton: false
		});
		},
		complete: function() {
			setTimeout($.unblockUI, 100);
		},
		success: function(respuesta) {
			swal("",respuesta[0],"success").then(resp => {
				if (servicio == 1) {
					obtTblReglas();
				}
				if (servicio == 2 || servicio == 3) {
					$("#regParams").modal("hide");
					limpiarDatosParams();
					obtTblParamsAsig(idReglaParams);
				}
				if(servicio == 3){
					idParametro=0;
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
			var botones = "";
			console.log("registros tblParamsAsig");
				var btnEditarParams = " <button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle=''"
					+ " title='Actualizar Parametros' data-bs-target='' aria-controls='table-datatable' type='button'"
					+ " onclick='mostrarFormularioParams("
					+ opt.pk_parms_regla
					+ ")' id="
					+ opt.pk_parms_regla
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-pen-to-square'></i></button>";
			
			botones = botones+btnEditarParams;
			tblParamsAsig.row.add([opt.vc_desc_asignado,opt.vc_desc_cliente,opt.vc_desc_cartera,opt.i_prioridad,opt.vc_usuario_registro,opt.dt_fecha_registro,(opt.vc_usuario_mod == null?'----':opt.vc_usuario_mod),(opt.dt_fecha_mod == null?'--/--/--':opt.dt_fecha_mod),opt.i_porcentaje,opt.vc_estatus,botones]).draw(false);
			 //tblParamsAsig.row.add([opt.pk_id_regla,opt.vc_nombre_regla,opt.i_dias_vigencia,(opt.i_estatus == 1?'ACTIVO':'INACTIVO'),(opt.i_usuario_mod == null?'----':opt.i_usuario_mod.vc_nombre_usuario),(opt.dt_fecha_mod == null?'--/--/--':opt.dt_fecha_mod),botones]).draw(false);
		 });
	 });
}

function cerrarModalParams(tipoModal) {
	
	switch(tipoModal){
		case 1:
			limpiarDatosParams();
			idReglaParams=0;
			$("#altaLiqRenovados").modal("hide");
			break;
		case 2:
			limpiarDatosParams();
			idParametro=0;
			$("#regParams").modal("hide");
			break;
		default:
			break;
	}
		
}

function guardarModalParams() {
		if(guardarVacioParams() == 0){
			console.log("registrar datos");
			
		  Swal.fire({
			  	title: '<span style="color: #007BFF;"><i class="fa-solid fa-gear"></i>Procesando Información.',
				html: '<span class="spinner-border"></span>',
				timer: 1000, // tiempo en milisegundos,
				icon: 'info',
				timerProgressBar: true,
				showConfirmButton: false
		  }).then((result) => {
			  if (result.dismiss === Swal.DismissReason.timer) {
				  if(idParametro == 0){
					  servicioParams(2);//alta de registro 
				  }else{
					  servicioParams(3);//actualizacion de registro 
				  }
			  }
			
		  });
			
			
		}else{
			swal("!Alerta¡", "Registrar datos obligatorios","error");
		}
		
}
var registroParams="reglas/registroParams?idRegla=";
var actualizaParams="reglas/actualizaParams?idParams=";
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
			url = getAbsolutePath()+actualizaParams+idParametro;
			url= url + "&slAsignar="+$("#slAsignar").val();
			url= url + "&porcRegistros="+$("#porcRegistros").val();
			url= url + "&tipoCliente="+$("#tipoCliente").val();
			url= url + "&slcTipoCartera="+$("#slcTipoCartera").val();
			url= url + "&tipoPrioridad="+$("#tipoPrioridad").val();
			url= url + "&slEstatus="+$("#slEstatus").val();
			
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

function mostrarFormularioParams(accion){
	switch(accion){
		case 0://alta
			$("#regParams").modal("show");
			break;
		default://editar
			editarParametro(accion);
			$("#regParams").modal("show");
			break;
	}	
	
}
var idParametro=0;
var obtParametro="reglas/obtParametro?idParametros=";
function editarParametro(id){
	idParametro = id;
	url = getAbsolutePath()+obtParametro+id;
	obtParams(url);
}

function obtParams(urlObt){
	
		Swal.fire({
				title: '<span style="color: #007BFF;"><i class="fa-solid fa-gear"></i> Consultando Información.',
				html: '<span class="spinner-border"></span>',
				timer: 1000, // tiempo en milisegundos,
				icon: 'info',
				timerProgressBar: true,
				showConfirmButton: false
		}).then((result) => {
			if (result.dismiss === Swal.DismissReason.timer) {	
				$.getJSON(url, {}, function(data) {
					setTimeout($.unblockUI, 1000);
					cargarDatosParams(data);
				});
			}
		});
	
}

function cargarDatosParams(data) {
	
	$("#slAsignar").val(data.i_tipo_asignado);
	$("#porcRegistros").val(data.i_porcentaje);
	$("#tipoCliente").val(data.i_tipo_cliente);
	$("#slcTipoCartera").val(data.i_tipo_cartera);
	$("#tipoPrioridad").val(data.i_prioridad);
	$("#slEstatus").val(data.i_estatus);
	
}
