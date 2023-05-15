var buscarActividades = "buscarActividadesxEmpleado?idFolio=";
var seguimientoAlta = "altaSeguimiento?idActividad=";
var buscarSeguimiento = "obtSeguimiento?idActividad=";
var seguimiento = "obtenerID?idSeguimiento=";
var modificarSeguimiento = "modSeguimiento?idSeguimiento=";
var obsId = 0;

$(document).ready(function() {
	eventosBotones();
	inicioTabla();
});

var tblObservaciones;
var tblPlanAccion;
function inicioTabla() {
	tblPlanAccion = $("#tblPlanAccion").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	tblSeguimiento = $("#tblSeguimiento").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});

}

function verActividades(idFolio) {
	if (idFolio.value > 0) {
		console.log(idFolio.value);
		$.blockUI({ message: 'Espere un momento...' });
		var urlServicio = getAbsolutePath() + buscarActividades + idFolio.value;
		$.getJSON(urlServicio, {}, function(data) {
			tblPlanAccion.clear().draw();
			$.each(data, function(idx, opt) {
				var botones = " <button class='btn btn-info' tabindex='0'"
					+ " title='Ver Seguimiento' type='button'"
					+ " onclick='verSeguimiento(this)'"
					+ " id="
					+ opt.id_planaccion
					+ " >"
					+ " <i class='bi bi-ui-checks'></i>";
				tblPlanAccion.row.add([opt.id_planaccion, opt.nombreActividad, opt.nombreResponsable,
				opt.dtFechaInicio, opt.dtFechaVencimiento, opt.estatus, botones]).draw(false);

			});
			setTimeout($.unblockUI, 100);
		});
	} else {
		tblPlanAccion.clear().draw();
		swal({
			title: "",
			text: "Seleccione un Oficio Valido.",
			icon: "warning",
		});
	}
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function guardarVacio() {
	var validacion = false;

	if (document.getElementById("idtxtSeguimiento").value.length == 0) {
		document.getElementById("idtxtSeguimiento").style.borderColor = "#F70707";
		validacion = true;
	}else{
		document.getElementById("idtxtSeguimiento").style.borderColor = '';
		validacion = false;
	}
	if (document.getElementById("idFinActividad").value == 0) {
		document.getElementById("idFinActividad").style.borderColor = "#F70707";
		validacion = true;
	}else{
		document.getElementById("idFinActividad").style.borderColor = '';
		validacion = false;
	}

	return validacion;
}

function eventosBotones() {

	$("#btnGuardar").click(function() {

		if (guardarVacio() == false) {
			if (document.getElementById("idActividad").value > 0) {
				if (obsId == 0) {
					var urlServicio = getAbsolutePath() + seguimientoAlta + document.getElementById("idActividad").value;
					urlServicio = urlServicio + "&desc_seguimiento=" + document.getElementById("idtxtSeguimiento").value;
					urlServicio = urlServicio + "&estatus=" + document.getElementById("idFinActividad").value;
					peticionPost(urlServicio);
				} else {
					var urlServicio = getAbsolutePath() + modificarSeguimiento + obsId;
					urlServicio = urlServicio + "&desc_seguimiento=" + document.getElementById("idtxtSeguimiento").value;
					urlServicio = urlServicio + "&estatus=" + document.getElementById("idFinActividad").value;
					peticionPost(urlServicio);
				}
			} else {
				swal({
					title: "",
					text: "Seleccione una actividad del Plan de Accion",
					icon: "warning",
				});
			}

		} else {
			swal({
				title: "Alerta",
				text: "Registrar datos obligatorios",
				icon: "warning",
			});
		}



	});
}

var id;
var nombre;
function verSeguimiento(idAct) {

	id = $(idAct).parents("tr").find("td").eq(0).html();
	nombre = $(idAct).parents("tr").find("td").eq(1).html();
	$('#idActividad').val(id);
	$('#idNombreActividad').val(nombre);
	buscaSeguimiento();
}

function buscaSeguimiento() {
	if (document.getElementById("idActividad").value > 0) {
		var url = getAbsolutePath() + buscarSeguimiento + document.getElementById("idActividad").value;
		$.blockUI({ message: 'Espere un momento...' });
		$.getJSON(url, {}, function(data) {
			tblSeguimiento.clear().draw();
			$.each(data, function(idx, opt) {
				var botones = " <button class='btn btn-info' tabindex='0'"
					+ " title='Editar Seguimiento' type='button'"
					+ " onclick='editarSeguimiento("
					+ opt.id_seguimiento
					+ ")'"
					+ " id="
					+ opt.id_seguimiento
					+ " >"
					+ " <i class='bi bi-pencil-square'></i>";
				tblSeguimiento.row.add([opt.id_seguimiento, opt.nombreActividad, opt.desc_actividad,
				opt.dtFechaRegistro, opt.estatus, botones]).draw(false);

			});
			setTimeout($.unblockUI, 100);
		});
	} else {
		swal({
			title: "",
			text: "Seleccione una actividad del Plan de Accion",
			icon: "warning",
		});
		
		
			/*+ " <button class='btn btn-info' tabindex='0'"
					+ " title='Actualizar Estatus' type='button'"
					+ " onclick='actEstatus("
					+ opt.id_seguimiento
					+ ")'"
					+ " id="
					+ opt.id_seguimiento
					+ " >"
					+ " <i class='bi bi-arrow-repeat'></i>"*/
		
	}
}
function peticionPost(url) {
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
				iniciarAltaSeguimiento();
				buscaSeguimiento();
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

function iniciarAltaSeguimiento() {
	obsId = 0;
	document.getElementById("idtxtSeguimiento").value = "";
	document.getElementById("idFinActividad").value = 0;
}

function editarSeguimiento(idSeguimiento) {
	obsId = idSeguimiento;
	var urlServicio = getAbsolutePath() + seguimiento + obsId;
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(urlServicio, {}, function(data) {
		setTimeout($.unblockUI, 100);
		$('#idtxtSeguimiento').val(data.vc_desc_seguimiento);
		document.getElementById("idFinActividad").value = data.i_confirmacion_responsable;
	});
	setTimeout($.unblockUI, 100);
}



/*
function guardarActividad() {
	var urlServicio = getAbsolutePath() + altaActividadPA + obsId;
	urlServicio = urlServicio + "&nombreActividad=" + document.getElementById("idNombreActividad").value;
	urlServicio = urlServicio + "&idEncargado=" + document.getElementById("idEncargadoActividad").value;
	urlServicio = urlServicio + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value;
	urlServicio = urlServicio + "&dtFechaVencimiento=" + document.getElementById("dtFechaVencimiento").value;

	peticionPost(urlServicio);
}

function actualizarActividad() {
	var urlServicio = getAbsolutePath() + actualizarPA + document.getElementById("idAct").value;
	urlServicio = urlServicio + "&nombreActividad=" + document.getElementById("idNombreActividad").value;
	urlServicio = urlServicio + "&idEncargado=" + document.getElementById("idEncargadoActividad").value;
	urlServicio = urlServicio + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value;
	urlServicio = urlServicio + "&dtFechaVencimiento=" + document.getElementById("dtFechaVencimiento").value;

	peticionPost(urlServicio);
}

function iniciarDtosActividad() {
	document.getElementById("idAct").value = 0;
	document.getElementById("idNombreActividad").value = "";
	document.getElementById("idEncargadoActividad").value = 0;
	document.getElementById("dtFechaInicio").value = "";
	document.getElementById("dtFechaVencimiento").value = "";
	document.getElementById("idEstatus").value = 0;
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
			swal({
				title: "",
				text: respuesta[0],
				icon: "success",
				confirmButtonText: 'Aceptar'
			}).then(resp => {
				iniciarDtosActividad();
				obtActividades(obsId);
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



function guardarVacio() {
	var validacion = false;

	if (document.getElementById("idNombreActividad").value.length == 0) {
		document.getElementById("idNombreActividad").style.borderColor = "#F70707";
		validacion = true;
	}
	if (document.getElementById("idEncargadoActividad").value == 0) {
		document.getElementById("idEncargadoActividad").style.borderColor = "#F70707";
		validacion = true;
	}
	if (document.getElementById("dtFechaInicio").value.length == 0) {
		document.getElementById("dtFechaInicio").style.borderColor = "#F70707";
		validacion = true;
	}
	if (document.getElementById("dtFechaVencimiento").value.length == 0) {
		document.getElementById("dtFechaVencimiento").style.borderColor = "#F70707";
		validacion = true;
	}

	return validacion;
}

function iniciarCampos() {
	document.getElementById("idNombreActividad").style.borderColor = '';
	document.getElementById("idEncargadoActividad").style.borderColor = '';
	document.getElementById("dtFechaInicio").style.borderColor = '';
	document.getElementById("dtFechaVencimiento").style.borderColor = '';
}



function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}




function koObservacion(btnID) {
	var idObservacion = btnID;
	var estatus = 2;

	Swal.fire({
		title: "Motivo del rechazo para no atender la observación.",
		input: "textarea",
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
				actualizacionAsignacion(idObservacion, descRechazo, estatus);

			}
		});
}

function okObservacion(btnID) {
	console.log("" + btnID);
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
				actualizacionAsignacion(idObservacion, descripcion, estatus);
			}
		});
}
function actualizacionAsignacion(id, desc, estatus) {
	var urlServicio = "";
	if (estatus == 1) {
		urlServicio = getAbsolutePath() + aceptarObservacion + id;
	} else {
		urlServicio = getAbsolutePath() + rechazarObservacion + id + "&vc_motivo=" + desc;
	}
	peticion(urlServicio);
}

function peticion(url) {
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
function pintartblObservaciones() {
	var urlInicio = getAbsolutePath() + listaObservacion + document.getElementById("idOficios").value;
	$.blockUI({ message: 'Espere un momento...' });
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
				+ " >"
				+ " <i class='bi bi-ui-checks'></i>";
			botonesAsignacion = "<button class='btn btn-info' tabindex='0' "
				+ " title='Acepto' type='button'"
				+ " onclick='okObservacion("
				+ opt.pk_id_observacion
				+ ")' >"
				+ " <i class='bi bi-check-circle-fill'></i>"
				+ " </button>";

			botonesAsignacion = botonesAsignacion + "<button class='btn btn-info' tabindex='0' "
				+ " title='No Acepto' type='button'"
				+ " onclick='koObservacion("
				+ opt.pk_id_observacion
				+ ")' >"
				+ " <i class='bi bi-x-circle-fill'></i>"
				+ " </button>";
			console.log("" + botonesAceptado);
			console.log("" + botonesAsignacion);
			tblObservaciones.row.add([opt.pk_id_observacion, opt.i_area, opt.i_responsable,
			opt.dt_fecha_asignacion, opt.dt_fecha_vencimiento, opt.vc_descripcion, opt.i_estatus, (opt.i_confirma == 0) ? botonesAsignacion : botonesAceptado]).draw(false);


		});
		setTimeout($.unblockUI, 100);
	});
}



function obtActividades(idObs) {
	obsId = idObs;
	var urlServicio = getAbsolutePath() + buscarActividades + idObs;
	obtenerActPlanAccion(urlServicio);
}

function obtenerActPlanAccion(url) {
	console.log("");
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 100);
		tblPlanAccion.clear().draw();
		$.each(data, function(idx, opt) {
			var botones = " <button class='btn btn-info' tabindex='0'"
				+ " title='Editar Observacion' type='button'"
				+ " onclick='editarActividad("
				+ opt.pk_id_planaccion
				+ ")' id="
				+ opt.pk_id_planaccion
				+ " >"
				+ " <i class='bi bi-pencil-square'></i>";
			tblPlanAccion.row.add([opt.pk_id_planaccion, opt.vc_desc_actividad, opt.i_responsable,
			opt.dt_fecha_inicio, opt.dt_fecha_vencimiento, opt.i_estatus, opt.i_confirmacion_encargado, botones]).draw(false);

		});
	});*/
/*
"<button class='btn btn-info' tabindex='0'"
			+ " title='Actualizar Estatus'type='button'"
			+ " onclick='estatusObservacion(this)' id="
			+ opt.pk_id_planaccion
			+ " data-tooltip='Add' >"
			+ " <i class='bi bi-bookmark-x-fill'></i>"*/
/*}

var editarActividadPA = "editarActividad?idActividad=";
function editarActividad(idPA) {
	var url = getAbsolutePath() + editarActividadPA + idPA;
	datosActividad(url);
}

function datosActividad(url) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 100);
		document.getElementById("idNombreActividad").value = data.vc_desc_actividad;
		document.getElementById("idEncargadoActividad").value = data.i_responsable;
		document.getElementById("dtFechaInicio").value = data.dt_fecha_inicio;
		document.getElementById("dtFechaVencimiento").value = data.dt_fecha_vencimiento;
		document.getElementById("idEstatus").value = data.i_estatus;
		document.getElementById("idAct").value = data.pk_id_planaccion;
	});
}*/


