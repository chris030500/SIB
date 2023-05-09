var aceptarObservacion = "aceptarObservacion?idObservacion=";
var rechazarObservacion = "rechazarObservacion?idObservacion=";
var inicioPantalla = "aceptarAsignacion";
var listaObservacion = "listaObservaciones?idFolio=";
var buscarFolio = "buscarFolio?idFolio="

$(document).ready(function() {
	
	inicioTabla();
});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var tblObservaciones;
var tblPlanAccion;
function inicioTabla(){
	 tblObservaciones = $("#tblObservaciones").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	tblPlanAccion =$("#tblPlanAccion").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
}


function koObservacion(btnID) {
	var idObservacion = btnID;
	var estatus = 2;
	
	Swal.fire({
        title: "Motivo del rechazo para no atender la observación.",
        input:"textarea",
		//text: $(btnID).parents("tr").find("td").eq(1).html(),
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
			let descRechazo = resultado.value;
			actualizacionAsignacion(idObservacion,descRechazo,estatus);
			
        }
    });
}

function okObservacion(btnID) {
	console.log(""+btnID);
	var idObservacion = btnID;
	var estatus = 1;
	var descripcion = "";
	
	Swal.fire({
        title: "¿Acepta Atender la Observacion?",
        showCancelButton: true,
        confirmButtonText: "Si",
        cancelButtonText: "Cancelar",
    })
    .then(resultado => {
        if (resultado.value) {
			actualizacionAsignacion(idObservacion,descripcion,estatus);
        }
    });
}
function actualizacionAsignacion(id,desc,estatus){
	var urlServicio = "";
	if(estatus == 1){
		urlServicio = getAbsolutePath() + aceptarObservacion+id;
	}else{
		urlServicio = getAbsolutePath() + rechazarObservacion+id+"&vc_motivo="+desc;
	}
	peticion(urlServicio);
}

function peticion(url){
			$.ajax({
				url: url,
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
						//window.location.href = getAbsolutePath()+inicioPantalla;
						pintartblObservaciones();
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
function pintartblObservaciones(){
	var urlInicio = getAbsolutePath()+listaObservacion+document.getElementById("idOficios").value;
	$.getJSON(urlInicio, {}, function(data) {
		tblObservaciones.clear().draw();
		$.each(data, function(idx, opt) {
		
			var botonesAceptado = "";
			var botonesAsignacion = "";
				botonesAceptado = " <button class='btn btn-info' tabindex='0'"
						+ " title='Registrar Actividad PA' type='button'"
						+ " onclick='obtActividades("
						+ opt.pk_id_observacion
						+ ")' id="
						+ opt.pk_id_observacion 
						+" >"
						+ " <i class='bi bi-ui-checks'></i>";
				botonesAsignacion = "<button class='btn btn-info' tabindex='0' "
									 +" title='Acepto' type='button'"
									 +" onclick='okObservacion("
									 + opt.pk_id_observacion
									 + ")' >"
									 +" <i class='bi bi-check-circle-fill'></i>"
									 +" </button>";
								
				botonesAsignacion = botonesAsignacion +"<button class='btn btn-info' tabindex='0' "
									 +" title='No Acepto' type='button'"
									 +" onclick='koObservacion("
									 + opt.pk_id_observacion
									 + ")' >"
									 +" <i class='bi bi-x-circle-fill'></i>"
									 +" </button>";
						console.log(""+botonesAceptado);
						console.log(""+botonesAsignacion);
						tblObservaciones.row.add([opt.pk_id_observacion,opt.i_area,opt.i_responsable,
						opt.dt_fecha_asignacion,opt.dt_fecha_vencimiento,opt.vc_descripcion,opt.i_estatus,(opt.i_confirma == 0)?botonesAsignacion:botonesAceptado]).draw(false);
		
			
		});
				 	
	});
}

function obtActividades(idObservacion){
	console.log("id:"+idObservacion);
}

function verDetalle(idFolio){
	console.log(idFolio.value);
	var urlServicio = getAbsolutePath() + buscarFolio+idFolio.value;
	$.getJSON(urlServicio, {}, function(data) {
				$('#idAsunto').val(data.vc_asunto);
				$('#dtRecibo').val(data.dt_fecha_recepcion);
				$('#dtVencimiento').val(data.dt_fecha_vencimiento);
				pintartblObservaciones();
	});	
	
}


