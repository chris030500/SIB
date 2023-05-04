/*var altaRoles = "altaRoles?nombreRol=";
var updateRoles = "updateRoles?idRol=";
var cargarRolEditar = "cargarRol/";
var modificarRoles = "modificarRoles?idRol=";
var inicio = "index";
*/
var folioID; 
var puesto = "buscarPuesto?empleadoID=";
var buscarxArea = "busquedaxArea?areaID=";
var altaFolio = "altaFolio?noficio=";
var buscarObservaciones = "buscarObservaciones?idFolio=";
var datosFolio = "cargarFolio/";



$(document).ready(function() {
	eventosModal();
	inicioTabla();
});

function eventosModal(){
	$("#btnRegistrar").click(function() {
		
		if(guardarVacio() == false){
			iniciarCampos();
			guardarFolio();
		}else{
			swal({
					title:"Error",
					text: "Registrar datos obligatorios",
					icon: "warning",
				});
		}
	});
	$("#btnCancelar").click(function() {
		 iniciarCampos();
		 limpiarDatosFolio();
		$("#modalFolio").modal("hide");
	});
	
	$("#btnAgregar").click(function() {
		tblObservacionesLoad()
	});


	
}

var areaID;
var responsableID;
var fechaVencimiento;
var desObservacion;
var altaObservaciones = "altaObservacion?idAreaAtencion="
function tblObservacionesLoad(){
	var areaID = document.getElementById("idAreaAtencion").value;
	var responsableID ="&idResponsableAtencion="+ document.getElementById("idResponsableAtencion").value;
	var fechaVencimiento = "&idFechaVencimiento="+document.getElementById("idFechaVencimiento").value;
	var desObservacion = "&desObservacion="+document.getElementById("idObservacion").value;
	
	
	var urlServicio = getAbsolutePath() + altaObservaciones+areaID+responsableID+fechaVencimiento+desObservacion+"&folioID="+folioID;
	peticionPost(urlServicio,2,folioID);	
}



function guardarFolio(){

	var noFicio = document.getElementById("noficio").value;
	var idRecepcion = "&dt_recepcion="+document.getElementById("idRecepcion").value;
	var idAsunto = "&vc_asunto="+document.getElementById("idAsunto").value;
	var idDirigido = "&i_dirigido="+document.getElementById("idDirigido").value;
	var iPuesto = "&vc_puesto="+document.getElementById("iPuesto").value;
	var idTipoOficio = "&i_tipo_oficio="+document.getElementById("idTipoOficio").value;
	var idOrganoEmite = "&i_organo_emite="+document.getElementById("idOrganoEmite").value;
	var idFechaRegistro = "&dt_registro="+document.getElementById("idFechaRegistro").value;
	var idPlazo = "&i_Plazo="+document.getElementById("idPlazo").value;
	var idVencimiento = "&dt_Vencimiento="+document.getElementById("idVencimiento").value;
	var idEstatusOficio = "&i_estatus="+document.getElementById("idEstatusOficio").value;
	var idRutaOficio = "&vc_ruta="+"C:";
	//document.getElementById("idRutaOficio").value;
	
	var urlServicio = getAbsolutePath() + altaFolio+noFicio+idRecepcion+idAsunto+idDirigido
	    +iPuesto+idTipoOficio+idOrganoEmite+idFechaRegistro+idPlazo+idVencimiento+idEstatusOficio+idRutaOficio;
	
	$("#modalFolio").hide();
	peticionPost(urlServicio,1,0);
}

function peticionPost(urlServicio,num,numFolio){
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
					swal({
						title: "",
						text: respuesta[0],
						icon: "success",
						confirmButtonText: 'Aceptar'
					}).then(resp => {
						if(num == 2){
								//obtener registros y cargar datos en tabla observaciones
								var url = getAbsolutePath()+buscarObservaciones+numFolio;
								obtenerDatostblObservaciones(url);
						}
						//window.location.href = getAbsolutePath()+inicio;
					});
					

				}, error: function(XMLHttpRequest, textStatus, errorThrown) {
					swal({
						title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
					});
				}
			});
}

function iniciarCampos(){
	document.getElementById("noficio").style.borderColor = '';
	document.getElementById("idRecepcion").style.borderColor = '';
	document.getElementById("idAsunto").style.borderColor = '';
	document.getElementById("idDirigido").style.borderColor = '';
	document.getElementById("iPuesto").style.borderColor = '';
	document.getElementById("idTipoOficio").style.borderColor = '';
	document.getElementById("idOrganoEmite").style.borderColor = '';
	document.getElementById("idFechaRegistro").style.borderColor = '';
	document.getElementById("idPlazo").style.borderColor = '';
	document.getElementById("idVencimiento").style.borderColor = '';
	document.getElementById("idEstatusOficio").style.borderColor = '';
	document.getElementById("idRutaOficio").style.borderColor = '';
}


function guardarVacio(){
	var validacion = false;
	
	iniciarCampos();
	/*if(document.getElementById("idUnico").value.length == 0){
		document.getElementById("idUnico").style.borderColor = "#F70707";
		validacion = true;
	}*/
	
	if(document.getElementById("noficio").value.length == 0){
		document.getElementById("noficio").style.borderColor = "#F70707";
		validacion = true;
	}
	
	if(document.getElementById("idRecepcion").value.length == 0){
		document.getElementById("idRecepcion").style.borderColor = "#F70707";
		validacion = true;
	}
	
	if(document.getElementById("idAsunto").value.length == 0){
		document.getElementById("idAsunto").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idDirigido").value.length == 0){
		document.getElementById("idDirigido").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("iPuesto").value.length == 0){
		document.getElementById("iPuesto").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idTipoOficio").value.length == 0){
		document.getElementById("idTipoOficio").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idOrganoEmite").value.length == 0){
		document.getElementById("idOrganoEmite").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idFechaRegistro").value.length == 0){
		document.getElementById("idFechaRegistro").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idPlazo").value.length == 0){
		document.getElementById("idPlazo").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idVencimiento").value.length == 0){
		document.getElementById("idVencimiento").style.borderColor = "#F70707";
		validacion = true;
	}
	if(document.getElementById("idEstatusOficio").value.length == 0){
		document.getElementById("idEstatusOficio").style.borderColor = "#F70707";
		validacion = true;
	}
	/*if(document.getElementById("idRutaOficio").value.length == 0){
		document.getElementById("idRutaOficio").style.borderColor = "#F70707";
		validacion = true;
	}*/
	
	
	return validacion;
}


function obtenerPuesto(idEmpleado){
	console.log(idEmpleado.value);
	var urlServicio = getAbsolutePath() + puesto+idEmpleado.value;
	$.getJSON(urlServicio, {}, function(data) {
		$('#iPuesto').val(data[0].descripcionPuesto);
	});
		
}

function obtEmpleadoArea(idArea){
	console.log(idArea.value);
	var buscarEmpleados = getAbsolutePath()+ buscarxArea+idArea.value;
	listaResponsable(buscarEmpleados);
	/*$.getJSON(buscarEmpleados, {}, function(data) {
		$("#idResponsableAtencion").empty();
			$.each(data, function(idx, opt) {
				var opciones = "<option value="+opt.empleadoID+" text="+opt.nombreCompleto+" >"+opt.nombreCompleto+"</opcion>";
				$('#idResponsableAtencion').append(opciones);

			});
		
		$('#iPuesto').val(data[0].descripcionPuesto);
	});*/
	
}
function listaResponsable(url){
		$.getJSON(url, {}, function(data) {
		$("#idResponsableAtencion").empty();
			$.each(data, function(idx, opt) {
				var opciones = "<option value="+opt.empleadoID+" text="+opt.nombreCompleto+" >"+opt.nombreCompleto+"</opcion>";
				$('#idResponsableAtencion').append(opciones);

			});
		
		$('#iPuesto').val(data[0].descripcionPuesto);
	});
}

function observaciones(idFolio){
	folioID = $(idFolio).parents("tr").find("td").eq(0).html();
	var urlServicio = getAbsolutePath()+buscarObservaciones+folioID;
	obtenerDatostblObservaciones(urlServicio);
}

function obtenerDatostblObservaciones(url){
		$.blockUI({ message: 'Espere un momento...' });
		$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 500); 
		tblObservacion.clear().draw();
		$.each(data, function(idx, opt) {
				var botones ="<button class='btn btn-info' tabindex='0'"
						+ " title='Actualizar Estatus'type='button'"
						+ " onclick='estatusObservacion(this)' id="
						+ opt.pk_id_observacion 
						+" data-tooltip='Add' >"
						+ " <i class='bi bi-bookmark-x-fill'></i>"
						+ " <button class='btn btn-info' tabindex='0'"
						+ " title='Editar Observacion' type='button'"
						+ " onclick='editarObservacion("
						+ opt.pk_id_observacion
						+ ")' id="
						+ opt.pk_id_observacion 
						+" >"
						+ " <i class='bi bi-pencil-square'></i>";
						tblObservacion.row.add([opt.pk_id_observacion,opt.i_area,opt.i_responsable,
						opt.dt_fecha_asignacion,opt.dt_fecha_vencimiento,opt.vc_descripcion,opt.i_estatus,botones]).draw(false);
		});
	});
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
var tblObservacion;
function inicioTabla(){
	$("#tblOficio").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
tblObservacion = $("#tblObservacion").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		scrollX: true,
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
}
function editarFolio(id){
	var url = getAbsolutePath() + datosFolio+id;
	obtenerFolio(url);
}
function obtenerFolio(url){
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 500); 
		cargarDatos(data);

	});
}
function cargarDatos(data) {
		
	document.getElementById("noficio").value = data.i_folio;
	document.getElementById("idRecepcion").value = data.dt_fecha_recepcion;
	document.getElementById("idAsunto").value = data.vc_asunto;
	document.getElementById("idDirigido").value = data.i_dirigido;
	document.getElementById("iPuesto").value = data.vc_puesto;
	document.getElementById("idTipoOficio").value = data.i_tipo_oficio;
	document.getElementById("idOrganoEmite").value = data.i_organo_emite;
	document.getElementById("idFechaRegistro").value = data.dt_fecha_registro;
	document.getElementById("idPlazo").value = data.dt_fecha_registro;
	document.getElementById("idVencimiento").value = data.dt_fecha_vencimiento;
	document.getElementById("idEstatusOficio").value = data.i_estatus;
	//document.getElementById("idRutaOficio").value = data.vc_ruta_oficio;
}

function limpiarDatosFolio(){
			
	document.getElementById("noficio").value = '';
	document.getElementById("idRecepcion").value = '';
	document.getElementById("idAsunto").value = '';
	document.getElementById("idDirigido").value = 0;
	document.getElementById("iPuesto").value = '';
	document.getElementById("idTipoOficio").value = 0;
	document.getElementById("idOrganoEmite").value = 0;
	document.getElementById("idFechaRegistro").value = '';
	document.getElementById("idPlazo").value = '';
	document.getElementById("idVencimiento").value = '';
	document.getElementById("idEstatusOficio").value = '';
	//document.getElementById("idRutaOficio").value = data.vc_ruta_oficio;
}

var datosObservacion = "cargarObservacion/";
function editarObservacion(idObservacion){
		var url = getAbsolutePath() + datosObservacion+idObservacion;
		obtenerObservacion(url);
}
function obtenerObservacion(url){
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 500); 
		cargarDatosObservacion(data);

	});
}
function cargarDatosObservacion(data){
	document.getElementById("idAreaAtencion").value = data.i_area;
	var url = getAbsolutePath()+ buscarxArea+data.i_area;
	listaResponsable(url)
	document.getElementById("idResponsableAtencion").value = data.i_responsable;
	document.getElementById("idFechaVencimiento").value = data.dt_fecha_vencimiento;
	document.getElementById("idObservacion").value = data.vc_descripcion
}
