var buscarActividades = "buscarActividadesxEmpleado?idFolio=";
var seguimientoAlta = "altaSeguimiento?idActividad=";
var buscarSeguimiento = "obtSeguimiento?idActividad=";
var seguimiento = "obtenerID?idSeguimiento=";
var modificarSeguimiento = "modSeguimiento?idSeguimiento=";
var obsId = 0;

$(document).ready(function() {
	$("#btnCerrar").click(function() {
		$("#modalAdjuntos").modal("hide");
		document.getElementById("idFolioArchivo").value = 0;
		tblAdjuntos.clear().draw();
	});
	$("#btnGuardarFile").click(function() {
		if (document.getElementById("file").value.length > 0) {
			var form = $('#adjuntoArchivoOficio')[0];
			var data = new FormData(form);
			addArchivos(data)
		} else {
			swal({
				title: "Informacion.",
				text: "Es necesario seleccionar un Archivo.",
				icon: "Warning",
				confirmButtonText: 'Aceptar'
			})
		}

	});
	eventosContadorSeguimiento();
	eventosBotones();
	inicioTabla();
});

function eventosContadorSeguimiento() {
	const mensaje = document.getElementById('idtxtSeguimiento');
	const contador = document.getElementById('contSeguimiento');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

var tblObservaciones;
var tblPlanAccion;
var tblAdjuntos;
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
	
	tblAdjuntos = $("#tblAdjuntos").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]

	});
	
	
}

function verActUpload(id) {
	if (id > 0) {
		
		$.blockUI({ message: 'Espere un momento...' });
		var urlServicio = getAbsolutePath() + buscarActividades + id;
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
		});
	}
}



function verActividades(idFolio) {
	if (idFolio.value > 0) {
		
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
				verActUpload(document.getElementById("idOficios").value);
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

function tamFile() {
	const tamMaxFile = 31457280;
	var fileTam = document.getElementById("file");
	fileTam.onchange = function() {
		if (this.files[0].size > tamMaxFile) {
			swal({
				title: "",
				text: "El archivo seleccionado excede el tamaño maximo permitido (30MB)",
				icon: "warning",
				confirmButtonText: 'Aceptar'
			}).then(resp => {
				this.value = "";
			});
		};
	};
}

var buscarArchivos = "buscarArchivos?idFolio=";
function adjuntarFiles(){ 
	if(document.getElementById("idOficios").value > 0){
		var urlServicio = getAbsolutePath() + buscarArchivos + document.getElementById("idOficios").value;
		obtDtostblAdjuntos(urlServicio);
		document.getElementById("file").value = "";
	}else{
		$("#modalAdjuntos").modal("hide");
		swal({
			title: "Atencion.",
			text: "Seleccionar un oficio valido.",
			icon: "warning",
			confirmButtonText: 'Aceptar'
		});
		
	}
}

function obtDtostblAdjuntos(url) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		tblAdjuntos.clear().draw();
		$.each(data, function(idx, opt) {
			var botones = " <button class='btn btn-info' tabindex='0'"
				+ " title='Ver PDF' type='button'"
				+ " onclick='verPDF("
				+ opt.pk_id_archivo
				+ ")' id="
				+ opt.pk_id_archivo
				+ " >"
				+ " <i class='bi bi-file-earmark-pdf-fill'></i>";
			tblAdjuntos.row.add([opt.pk_id_archivo, opt.vc_nombre_docto, botones]).draw(false);
			tblAdjuntos.column(0).visible(false);
		});
		setTimeout($.unblockUI, 100);
		mnstblAdjuntos(tblAdjuntos.rows().count());
	});
}
function mnstblAdjuntos(num) {
	if (num > 0) {
		swal({
			title: "Informacion.",
			text: "Lista de Archivos del Oficio.",
			icon: "success",
			confirmButtonText: 'Aceptar'
		})
	} else {
		swal({
			title: "Informacion.",
			text: "El Oficio no cuenta con Archivos registrados",
			icon: "success",
			confirmButtonText: 'Aceptar'
		})
	}
}

function verPDF(id) {

	var servicioPDF = "buscarPDF?idArchivo=";
	var urlArchivo = getAbsolutePath() + servicioPDF + id;

	window.open(urlArchivo);
	window.focus();

}
var agregarArchivos = "subirArchivo?idFolio=";
function addArchivos(data) {
	
	$.ajax({
		async: false,
		url: getAbsolutePath() + agregarArchivos + document.getElementById("idOficios").value,
		type: "POST",
		enctype: 'multipart/form-data',
		data: data, //pass the form data
		processData: false,
		contentType: false,
		beforeSend: function() {
			$.blockUI({ message: '<H2>Espere un momento...</H2>' });
		},
		success: function(data) {
			swal({
				title: "",
				text: data[0],
				icon: "success",
				confirmButtonText: 'Aceptar'
			}).then(resp => {
				adjuntarFiles(document.getElementById("idOficios").value);
			});
		},
		error: function(data) {
			swal({
				title: "Alerta",
				text: "El tamaño del archivo es mayor al permitirdo 30MB.",
				icon: "warning",
			});
		},
		complete: function() {
			setTimeout($.unblockUI, 600);
		}
	});

}

