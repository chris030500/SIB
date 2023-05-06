var aceptarObservacion = "aceptarObservacion?idObservacion=";
var rechazarObservacion = "rechazarObservacion?idObservacion=";
var inicioPantalla = "aceptarAsignacion";

$(document).ready(function() {
	
	inicioTabla();
});

var tblObservaciones;
var tblPlanAccion;
function inicioTabla(){
	 $("#tblObservaciones").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	$("#tblPlanAccion").DataTable({
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
	var idObservacion = $(btnID).parents("tr").find("td").eq(0).html();
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
	var idObservacion = $(btnID).parents("tr").find("td").eq(0).html();
	var estatus = 1;
	var descripcion = "";
	
	Swal.fire({
        title: "Acepta Atender la Observacion.",
        showCancelButton: true,
        confirmButtonText: "Guardar",
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
	$.blockUI({ message: 'Espere un momento...' });
	$.postJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 500); 
	});
	
	pintartblObservaciones();
}
function pintartblObservaciones(){
	var urlInicio = getAbsolutePath()+inicioPantalla
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(urlInicio, {}, function(data) {
		 	setTimeout($.unblockUI, 500); 
		tblObservaciones.clear().draw();
		$.each(data, function(idx, opt) {
				var botonesAceptado =" <button class='btn btn-info' tabindex='0'"
						+ " title='Editar Observacion' type='button'"
						+ " onclick='obtActividades("
						+ opt.pk_id_observacion
						+ ")' id="
						+ opt.pk_id_observacion 
						+" >"
						+ " <i class='bi bi-pencil-square'></i>";
				var botonesAsignacion = "<button class='btn btn-info' tabindex='0' "
									 +" title='Acepto' type='button'"
									 +" th:onclick='okObservacion("
									 + opt.pk_id_observacion
									 + ")' >"
									 +" <i class='bi bi-check-circle-fill'></i>"
									 +" </button>"
									 "<button class='btn btn-info' tabindex='0' "
									 +" title='No Acepto' type='button'"
									 +" th:onclick='koObservacion("
									 + opt.pk_id_observacion
									 + ")' >"
									 +" <i class='bi bi-x-circle-fill'></i>"
									 +" </button>";
		
						tblObservacion.row.add([opt.pk_id_observacion,opt.i_area,opt.i_responsable,
						opt.dt_fecha_asignacion,opt.dt_fecha_vencimiento,opt.vc_descripcion,opt.i_estatus,(opt.i_confirma = 0)?botonesAsignacion:botonesAceptado]).draw(false);
		});
		
	});
}
function obtActividades(idObservacion){
	console.log("id:"+idObservacion);
}


