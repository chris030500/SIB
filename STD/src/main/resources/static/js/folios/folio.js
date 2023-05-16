var folioID;
var puesto = "buscarPuesto?empleadoID=";
var buscarxArea = "busquedaxArea?areaID=";
var altaFolio = "altaFolio?noficio=";
var buscarObservaciones = "buscarObservaciones?idFolio=";
var datosFolio = "cargarFolio/";
var modFolio = "modificarFolio?noficio=";
var modObservaciones = "modificarObservacion?idObs=";
var altaObservaciones = "altaObservacion?idAreaAtencion=";

var inicio = "folio";



$(document).ready(function() {
	eventosModal();
	inicioTabla();
});

function eventosModal() {
	$("#btnRegistrar").click(function() {

		if (guardarVacio() == false) {
			if (document.getElementById("idFolioSelec").value == 0) {
				guardarFolio(1);
			} else {
				guardarFolio(2);
			}
		} else {
			swal({
				title: "Error",
				text: "Registrar datos obligatorios",
				icon: "warning",
			});
		}
	});
	$("#btnCancelar").click(function() {
		//iniciarCampos();
		limpiarDatosFolio();
		$("#modalFolio").modal("hide");
	});

	$("#btnAgregar").click(function() {

		if (document.getElementById("idObservacionSelect").value > 0) {
			modificarObs();
		} else {
			tblObservacionesLoad();
		}


	});



}


function tblObservacionesLoad() {
	var urlServicio = getAbsolutePath() + altaObservaciones + document.getElementById("idAreaAtencion").value;
	urlServicio = urlServicio + "&idResponsableAtencion=" + document.getElementById("idResponsableAtencion").value;
	urlServicio = urlServicio + "&idFechaVencimiento=" + document.getElementById("idFechaVencimiento").value;
	urlServicio = urlServicio + "&desObservacion=" + document.getElementById("idObservacion").value;
	urlServicio = urlServicio + "&folioID=" + folioID;

	peticionPost(urlServicio, 2, folioID);
}

function modificarObs() {
	var urlServicio = getAbsolutePath() + modObservaciones + document.getElementById("idObservacionSelect").value;
	urlServicio = urlServicio + "&idAreaAtencion=" + document.getElementById("idAreaAtencion").value;
	urlServicio = urlServicio + "&idResponsableAtencion=" + document.getElementById("idResponsableAtencion").value;
	urlServicio = urlServicio + "&idFechaVencimiento=" + document.getElementById("idFechaVencimiento").value;
	urlServicio = urlServicio + "&desObservacion=" + document.getElementById("idObservacion").value;

	peticionPost(urlServicio, 2, folioID);
}

function reiniciarDatos() {
	document.getElementById("idObservacionSelect").value = 0;
	document.getElementById("idAreaAtencion").value = 0;
	document.getElementById("idResponsableAtencion").value = 0;
	document.getElementById("idFechaVencimiento").value = "";
	document.getElementById("idObservacion").value = "";
}


function guardarFolio(proceso) {
	var urlServicio = "";
	switch (proceso) {
		case 1:
			urlServicio = getAbsolutePath() + altaFolio;
			break;
		case 2:
			urlServicio = getAbsolutePath() + modFolio;
			break;
		default:
			urlServicio = getAbsolutePath() + inicio;
			break;
	}
	urlServicio = urlServicio + document.getElementById("noficio").value;
	urlServicio = urlServicio + "&dt_recepcion=" + document.getElementById("idRecepcion").value;
	urlServicio = urlServicio + "&vc_asunto=" + document.getElementById("idAsunto").value;
	urlServicio = urlServicio + "&i_dirigido=" + document.getElementById("idDirigido").value;
	urlServicio = urlServicio + "&vc_puesto=" + document.getElementById("iPuesto").value;
	urlServicio = urlServicio + "&i_tipo_oficio=" + document.getElementById("idTipoOficio").value;
	urlServicio = urlServicio + "&i_organo_emite=" + document.getElementById("idOrganoEmite").value;
	urlServicio = urlServicio + "&dt_registro=" + document.getElementById("idFechaRegistro").value;
	urlServicio = urlServicio + "&i_Plazo=" + document.getElementById("idPlazo").value;
	urlServicio = urlServicio + "&dt_Vencimiento=" + document.getElementById("idVencimiento").value;
	urlServicio = urlServicio + "&i_estatus=" + document.getElementById("idEstatusOficio").value;
	urlServicio = urlServicio + "&vc_ruta=" + "C:";

	peticionPost(urlServicio, 1, 0);
	$("#modalFolio").hide();
}

function peticionPost(urlServicio, num, numFolio) {
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

				if (num == 2) {
					//obtener registros y cargar datos en tabla observaciones
					var url = getAbsolutePath() + buscarObservaciones + numFolio;
					obtenerDatostblObservaciones(url);
					reiniciarDatos();
				} else if (num == 1) {
					//cargar datos de la tabla de folios
					document.getElementById("idFolioSelec").value == 0;
					window.location.href = getAbsolutePath() + inicio;
				}
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

	if (document.getElementById("noficio").value.length == 0) {
		document.getElementById("noficio").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("noficio").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("idRecepcion").value.length == 0) {
		document.getElementById("idRecepcion").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idRecepcion").style.borderColor = '';
		validacion = false;
	}
	if (document.getElementById("idAsunto").value.length == 0) {
		document.getElementById("idAsunto").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idAsunto").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("idDirigido").value.length == 0) {
		document.getElementById("idDirigido").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idDirigido").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("iPuesto").value.length == 0) {
		document.getElementById("iPuesto").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("iPuesto").style.borderColor = '';
		validacion = false;
	}
	if (document.getElementById("idTipoOficio").value.length == 0) {
		document.getElementById("idTipoOficio").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idTipoOficio").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("idOrganoEmite").value.length == 0) {
		document.getElementById("idOrganoEmite").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idOrganoEmite").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("idFechaRegistro").value.length == 0) {
		document.getElementById("idFechaRegistro").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idFechaRegistro").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("idPlazo").value.length == 0) {
		document.getElementById("idPlazo").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idPlazo").style.borderColor = '';
		validacion = false;
	}

	if (document.getElementById("idVencimiento").value.length == 0) {
		document.getElementById("idVencimiento").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idVencimiento").style.borderColor = '';
		validacion = false;
	}
	if (document.getElementById("idEstatusOficio").value.length == 0) {
		document.getElementById("idEstatusOficio").style.borderColor = "#F70707";
		validacion = true;
	} else {
		document.getElementById("idEstatusOficio").style.borderColor = '';
		validacion = false;
	}

	return validacion;
}


function obtenerPuesto(idEmpleado) {
	console.log(idEmpleado.value);
	var urlServicio = getAbsolutePath() + puesto + idEmpleado.value;
	$.getJSON(urlServicio, {}, function(data) {
		$('#iPuesto').val(data[0].descripcionPuesto);
	});
}

function obtEmpleadoArea(idArea) {
	console.log(idArea.value);
	var buscarEmpleados = getAbsolutePath() + buscarxArea + idArea.value;
	listaResponsable(buscarEmpleados, 0);
}
function listaResponsable(url, valor) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		$("#idResponsableAtencion").empty();
		$.each(data, function(idx, opt) {
			if (valor == opt.empleadoID) {
				$("#idResponsableAtencion").prepend("<option value=" + opt.empleadoID + " text=" + opt.nombreCompleto + " selected='selected'>" + opt.nombreCompleto + "</opcion>")
			} else {
				$("#idResponsableAtencion").prepend("<option value=" + opt.empleadoID + " text=" + opt.nombreCompleto + ">" + opt.nombreCompleto + "</opcion>")
			}
		});
		setTimeout($.unblockUI, 100);
	});
}

function observaciones(idFolio) {
	folioID = $(idFolio).parents("tr").find("td").eq(0).html();
	var urlServicio = getAbsolutePath() + buscarObservaciones + folioID;
	obtenerDatostblObservaciones(urlServicio);
}

function obtenerDatostblObservaciones(url) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		tblObservacion.clear().draw();
		$.each(data, function(idx, opt) {
			var botones = " <button class='btn btn-info' tabindex='0'"
				+ " title='Editar Observacion' type='button'"
				+ " onclick='editarObservacion("
				+ opt.pk_id_observacion
				+ ")' id="
				+ opt.pk_id_observacion
				+ " >"
				+ " <i class='bi bi-pencil-square'></i>";
			tblObservacion.row.add([opt.pk_id_observacion, opt.vc_area, opt.vc_responsable,
			opt.dt_fecha_asignacion, opt.dt_fecha_vencimiento, opt.vc_descripcion, opt.vc_estatus,opt.vc_confirma,opt.vc_desc_rechazo, botones]).draw(false);
			tblObservacion.column(0).visible(false);
		});
		setTimeout($.unblockUI, 100);
		if (tblObservacion.rows().count() > 0) {
			swal({
				title: "Informacion.",
				text: "Se consultaron las observaciones del Folio",
				icon: "success",
				confirmButtonText: 'Aceptar'
			})
		} else {

			swal({
				title: "Informacion.",
				text: "El folio no cuenta con observaciones registradas",
				icon: "success",
				confirmButtonText: 'Aceptar'
			})
		}

	});
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
var tblObservacion;
var tblOficio;
function inicioTabla() {

	tblOficio = $("#tblOficio").DataTable({
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
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
}
function editarFolio(id) {

	document.getElementById("idFolioSelec").value = id;

	var url = getAbsolutePath() + datosFolio + id;
	obtenerFolio(url);
}
function obtenerFolio(url) {
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
	document.getElementById("idPlazo").value = data.i_plazo_atencion;
	document.getElementById("idVencimiento").value = data.dt_fecha_vencimiento;
	document.getElementById("idEstatusOficio").value = data.i_estatus;
	//document.getElementById("idRutaOficio").value = data.vc_ruta_oficio;
}

function limpiarDatosFolio() {

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
function editarObservacion(idObservacion) {
	document.getElementById("idObservacionSelect").value = idObservacion;
	var url = getAbsolutePath() + datosObservacion + idObservacion;
	obtenerObservacion(url);

}
function obtenerObservacion(url) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		document.getElementById("idAreaAtencion").value = data.i_area;
		document.getElementById("idFechaVencimiento").value = data.dt_fecha_vencimiento;
		document.getElementById("idObservacion").value = data.vc_descripcion;
		listaResponsable(getAbsolutePath() + buscarxArea + data.i_area, data.i_responsable);
		setTimeout($.unblockUI, 100);
	});
	setTimeout($.unblockUI, 100);
	swal({
		title: "Informacion.",
		text: "Se consulto la informacion de la observacion",
		icon: "success",
		confirmButtonText: 'Aceptar'
	})
}

