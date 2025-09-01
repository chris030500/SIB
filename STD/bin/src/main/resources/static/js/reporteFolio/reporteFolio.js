var altaCSO = "altaBusqueda?iTodos=";

$(document).ready(function() {
	$("#btnGenerarReporte").click(function() {
		if (validarFechas(document.getElementById("dtFechaInicio").value, document.getElementById("dtFechaFin").value) == false) {
			if (document.getElementById('ckTodos').checked == true) {
					registrarBusqueda();
			} else if (document.getElementById('ckTodos').checked == false) {
				
				registrarBusqueda();
				/*if (valVacio() == false) {
					registrarBusqueda();
				}else {
					swal({
						title: "Error",
						text: "Se requiere seleccionar una opcion valida.",
						icon: "warning",
					});
				}*/
			}
		} else {
			swal({
				title: "Error",
				text: "Validar el rango de fechas ingresadas.",
				icon: "warning",
			});
		}
	});
});

function registrarBusqueda(){
	var urlSevicio = getAbsolutePath() + altaCSO + ((document.getElementById('ckTodos').checked == true)?1:2);
	urlSevicio = urlSevicio + "&dtFechaInicio="+document.getElementById("dtFechaInicio").value;	
	urlSevicio = urlSevicio + "&dtFechaFin="+document.getElementById("dtFechaFin").value;
	if(document.getElementById('ckTodos').checked == true){
		urlSevicio = urlSevicio + "&iEstatus=0";
		urlSevicio = urlSevicio + "&iEmite=0";
		urlSevicio = urlSevicio + "&iTipo=0";
		urlSevicio = urlSevicio + "&idOficio=0";
	}else{
		urlSevicio = urlSevicio + "&iEstatus="+((document.getElementById("iEstatus").value> 0)?document.getElementById("iEstatus").value:0);
		urlSevicio = urlSevicio + "&iEmite="+((document.getElementById("iEmite").value > 0)?document.getElementById("iEmite").value:0);
		urlSevicio = urlSevicio + "&iTipo="+((document.getElementById("iOficio").value>0)?document.getElementById("iOficio").value:0);
		urlSevicio = urlSevicio + "&idOficio="+((document.getElementById("nrOficio").value>0)?document.getElementById("nrOficio").value:0);
	}
	peticionPost(urlSevicio);

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

				var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];
		
				window.open(urlReporte);
				window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}



function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function validarFechas(fecha1, fecha2) {
	var result = false;
	var fechaInicio = Date.parse(fecha1);
	var fechaFin = Date.parse(fecha2);

	if (fechaInicio > fechaFin) {
		result = true;
	}

	if (fechaFin < fechaInicio) {
		result = true;
	}

	if (fecha1.length == 0) {
		result = true;
	}
	if (fecha2.length == 0) {
		result = true;
	}

	if (result == true) {
		document.getElementById("dtFechaInicio").style.borderColor = "#F70707";
		document.getElementById("dtFechaFin").style.borderColor = "#F70707";
	} else {
		document.getElementById("dtFechaInicio").style.borderColor = "";
		document.getElementById("dtFechaFin").style.borderColor = "";
	}

	return result;
}

function valVacio() {
	var result = false;

	if (document.getElementById("iOficio").value == 0) {
		document.getElementById("iOficio").style.borderColor = "#F70707";
		result = true;
	} else {
		document.getElementById("iOficio").style.borderColor = "";
		result = false;
	}

	if (document.getElementById("iEmite").value == 0) {
		document.getElementById("iEmite").style.borderColor = "#F70707";
		result = true;
	} else {
		document.getElementById("iEmite").style.borderColor = "";
		result = false;
	}

	if (document.getElementById("iEstatus").value == 0) {
		document.getElementById("iEstatus").style.borderColor = "#F70707";
		result = true;
	} else {
		document.getElementById("iEstatus").style.borderColor = "";
		result = false;
	}

	return result;
}


function opcionTodos() {
	if (document.getElementById('ckTodos').checked == false) {
		$('#divParamBusqueda *').prop('disabled', false);
	} else if (document.getElementById('ckTodos').checked == true) {
		$('#divParamBusqueda *').prop('disabled', true);
	}
}




/*var buscarActividades = "buscarActividadesxEmpleado?idFolio=";
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
var tblPlanAccion;*/
/*
function inicioTabla() {
	tblPlanAccion = $("#tblPlanAccion").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1]
		}]
	});
	tblSeguimiento = $("#tblSeguimiento").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1]
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
				tblPlanAccion.row.add([opt.nombreActividad, opt.nombreResponsable,
				opt.dtFechaInicio, opt.dtFechaVencimiento, opt.estatus, botones]).draw(false);

			});
			setTimeout($.unblockUI, 100);
			mnsExisteRegistros(tblPlanAccion.rows().count());
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

function mnsExisteRegistros(tam){
		if(tam > 0){
			swal({
				title: "Informacion.",
				text: "Se consultaron las Actividades del Plan de Accion",
				icon: "success",
				confirmButtonText: 'Aceptar'
			})
	}else{
				swal({
				title: "Informacion.",
				text: "El Plan de Accion no cuenta con actividades registradas",
				icon: "success",
				confirmButtonText: 'Aceptar'
			})
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

	id = $(idAct).attr("id");
	//$(idAct).parents("tr").find("td").eq(0).html();
	nombre = $(idAct).parents("tr").find("td").eq(0).html();
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
					+ " onclick='editarSeguimiento(this)'"
					+ " id="
					+ opt.id_seguimiento
					+ " >"
					+ " <i class='bi bi-pencil-square'></i>";
				tblSeguimiento.row.add([opt.nombreActividad, opt.desc_actividad,
				opt.dtFechaRegistro, opt.estatus, botones]).draw(false);

			});
			setTimeout($.unblockUI, 100);
			msnExisteSeguimiento(tblSeguimiento.rows().count());
		});
	} else {
		swal({
			title: "",
			text: "Seleccione una actividad del Plan de Accion",
			icon: "warning",
		});
	}
}
function msnExisteSeguimiento(tam){
	if(tam > 0){
			swal({
				title: "Informacion.",
				text: "Se consulta el seguimiento de la Actividad del Plan de Accion.",
				icon: "success",
				confirmButtonText: 'Aceptar'
			});
	}else{
			swal({
				title: "Informacion.",
				text: "No hay registro de seguimiento de Actividades del Plan de Accion.",
				icon: "success",
				confirmButtonText: 'Aceptar'
			});
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
	obsId = $(idSeguimiento).attr("id");
	var urlServicio = getAbsolutePath() + seguimiento + obsId;
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(urlServicio, {}, function(data) {
		setTimeout($.unblockUI, 100);
		$('#idtxtSeguimiento').val(data.vc_desc_seguimiento);
		document.getElementById("idFinActividad").value = data.i_confirmacion_responsable;
	});
	setTimeout($.unblockUI, 100);
}
*/