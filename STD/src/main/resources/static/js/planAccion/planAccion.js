var aceptarObservacion = "aceptarObservacion?idObservacion=";
var rechazarObservacion = "rechazarObservacion?idObservacion=";
var inicioPantalla = "aceptarAsignacion";
var listaObservacion = "listaObservaciones?idFolio=";
var buscarFolio = "buscarFolio?idFolio=";
var buscarActividades = "buscarActividadesPlan?idObservacion=";
var altaActividadPA = "altaActividad?idObservacion=";
var actualizarPA = "modificarPA?idActividad=";

var obsId;

$(document).ready(function() {

	$("#btnAgregar").click(function() {

		if (obsId > 0) {

			if (guardarVacio() == false) {
				iniciarCampos();
				if(document.getElementById("idAct").value == 0){
					guardarActividad();	
				}else{
					actualizarActividad();
				}
			} else {
				swal({
					title: "Alerta",
					text: "Registrar datos obligatorios",
					icon: "warning",
				});
			}

		} else {
			swal({
				title: "Error",
				text: "No ha seleccionado una Observación.",
				icon: "warning",
			});
		}

	});
	
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
	
	
	eventosContadorNombre(); 
	inicioTabla();
});

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

function guardarActividad() {
	var urlServicio = getAbsolutePath() + altaActividadPA + obsId;
	urlServicio= urlServicio+ "&nombreActividad=" + document.getElementById("idNombreActividad").value;
	urlServicio = urlServicio+"&idEncargado=" + document.getElementById("idEncargadoActividad").value;
	urlServicio= urlServicio+"&dtFechaInicio=" + document.getElementById("dtFechaInicio").value;
	urlServicio = urlServicio+"&dtFechaVencimiento=" + document.getElementById("dtFechaVencimiento").value;

	peticionPost(urlServicio);
}

function actualizarActividad() {
	var urlServicio = getAbsolutePath() + actualizarPA + document.getElementById("idAct").value;
	urlServicio= urlServicio+ "&nombreActividad=" + document.getElementById("idNombreActividad").value;
	urlServicio = urlServicio+"&idEncargado=" + document.getElementById("idEncargadoActividad").value;
	urlServicio= urlServicio+"&dtFechaInicio=" + document.getElementById("dtFechaInicio").value;
	urlServicio = urlServicio+"&dtFechaVencimiento=" + document.getElementById("dtFechaVencimiento").value;
	urlServicio = urlServicio+"&confirmaResponsable=" + document.getElementById("idConfirmarActividad").value;

	peticionPost(urlServicio);
}

function iniciarDtosActividad(){
	document.getElementById("idAct").value = 0;
	document.getElementById("idNombreActividad").value = "";
	document.getElementById("idEncargadoActividad").value = 0;
	document.getElementById("dtFechaInicio").value = dtInicio;
	document.getElementById("dtFechaVencimiento").value = dtVencimiento;
	$("#idFinActividad").css("display", "none");
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
				tblActividadesPA(obsId);
				verDetallePA(document.getElementById("idOficios").value)
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
	}else{
		document.getElementById("idNombreActividad").style.borderColor = "";
		validacion = false;
	}
	if (document.getElementById("idEncargadoActividad").value == 0) {
		document.getElementById("idEncargadoActividad").style.borderColor = "#F70707";
		validacion = true;
	}else{
		document.getElementById("idEncargadoActividad").style.borderColor = "";
		validacion = false;
	}
	if (document.getElementById("dtFechaInicio").value.length == 0) {
		document.getElementById("dtFechaInicio").style.borderColor = "#F70707";
		validacion = true;
	}else{
		document.getElementById("dtFechaInicio").style.borderColor = "";
		validacion = false;
	}
	if (document.getElementById("dtFechaVencimiento").value.length == 0) {
		document.getElementById("dtFechaVencimiento").style.borderColor = "#F70707";
		validacion = true;
	}else{
		document.getElementById("dtFechaVencimiento").style.borderColor = "";
		validacion = false;
	}
	/*if (document.getElementById("idConfirmarActividad").value == 0) {
		document.getElementById("idConfirmarActividad").style.borderColor = "#F70707";
		validacion = true;
	}else{
		document.getElementById("idConfirmarActividad").style.borderColor = "";
		validacion = false;
	}*/

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

var tblObservaciones;
var tblPlanAccion;
var tblAdjuntos;
function inicioTabla() {
	tblObservaciones = $("#tblObservaciones").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	tblPlanAccion = $("#tblPlanAccion").DataTable({
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
				+ " onclick='obtActividades(this)' id="
				+ opt.pk_id_observacion
				+ " >"
				+ " <i class='bi bi-ui-checks'></i></button>";
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
			tblObservaciones.row.add([opt.vc_observacion_oficio, opt.vc_area, opt.vc_responsable,
			opt.dt_fecha_asignacion, opt.dt_fecha_vencimiento, opt.vc_descripcion, opt.vc_estatus, (opt.i_confirma == 0) ? botonesAsignacion : botonesAceptado]).draw(false);
			//tblObservaciones.column(0).visible(false);
		});
		setTimeout($.unblockUI, 100);
	});
}

function verDetalle(idFolio) {
	if(idFolio.value > 0){
		$.blockUI({ message: 'Espere un momento...' });
		var urlServicio = getAbsolutePath() + buscarFolio + idFolio.value;
		$.getJSON(urlServicio, {}, function(data) {
		$('#idAsunto').val(data.vc_asunto);
		$('#dtRecibo').val(data.dt_fecha_recepcion);
		$('#dtVencimiento').val(data.dt_fecha_vencimiento);
		pintartblObservaciones();
		setTimeout($.unblockUI, 100);
	});	
	}else {
			tblObservaciones.clear().draw();
			swal({
				title: "",
				text: "Seleccione un Oficio Valido.",
				icon: "warning",
			});
	}
}
var obtFolios = "obtFolios?estatus=";
function consultaDatos(idEstatus){
	iniciarPantalla();
	if(idEstatus.value > 0){
			$.blockUI({ message: 'Espere un momento...' });
			var urlServicio = getAbsolutePath() + obtFolios + idEstatus.value;
		
			$.getJSON(urlServicio, {}, function(data) {
				if(data.length > 0){
					$.each(data, function(idx, opt) {
						$("#idOficios").prepend("<option value=" + opt.pkFolio + " text=" + opt.folio + ">" + opt.folio + "</opcion>");
					});
				}
				$("#idOficios").prepend("<option value='0' text='Seleccione' selected='selected'>Seleccione</opcion>");
				setTimeout($.unblockUI, 100);
			});
	}else{
		
		$("#idOficios").prepend("<option value='0' text='Seleccione' selected='selected'>Seleccione</opcion>");
		swal({
			title: "",
			text: "Seleccione un estatus Valido.",
			icon: "warning",
		});
	}
}

function iniciarPantalla(){
	$("#idOficios").empty();
	reiniciarSeccionObservaciones();
	reiniciarSeccionPlanAccion();
}

function reiniciarSeccionObservaciones(){
	tblObservaciones.clear().draw();
	$('#idAsunto').val('');
	$('#dtRecibo').val('');
	$('#dtVencimiento').val('');
	
}
function reiniciarSeccionPlanAccion(){
	tblPlanAccion.clear().draw();
	document.querySelector('#lblSeleccion').innerText = "";
	obsId = 0;
	$('#dtFechaInicio').val('');
	$('#dtFechaVencimiento').val('');
	$('#dtFechaInicio').attr("min","fechaInicio");
	$('#dtFechaInicio').attr("max","fechaVencimiento");
	$('#dtFechaVencimiento').attr("min","fechaInicio");
	$('#dtFechaVencimiento').attr("max","fechaVencimiento");
	
}


function verDetallePA(id) {
	if(id > 0){
		$.blockUI({ message: 'Espere un momento...' });
		var urlServicio = getAbsolutePath() + buscarFolio + id;
		$.getJSON(urlServicio, {}, function(data) {
		$('#idAsunto').val(data.vc_asunto);
		$('#dtRecibo').val(data.dt_fecha_recepcion);
		$('#dtVencimiento').val(data.dt_fecha_vencimiento);
		pintartblObservaciones();
		setTimeout($.unblockUI, 100);
	});	
	}else {
			tblObservaciones.clear().draw();
			swal({
				title: "",
				text: "Seleccione un Oficio Valido.",
				icon: "warning",
			});
	}
}


function tblActividadesPA(id){
	var urlServicio = getAbsolutePath() + buscarActividades + id;
	obtenerActPlanAccion(urlServicio);	
}

function obtActividades(idObs) {
	document.querySelector('#lblSeleccion').innerText = $(idObs).parents("tr").find("td").eq(5).html();
	obsId = $(idObs).attr("id");
	var urlServicio = getAbsolutePath() + buscarActividades + obsId;
	obtenerActPlanAccion(urlServicio);
	
	asignarFechasObservacion($(idObs).parents("tr").find("td").eq(3).html(),$(idObs).parents("tr").find("td").eq(4).html());
}

var dtInicio;
var dtVencimiento;
function asignarFechasObservacion(fecha1, fecha2){
	var fechaInicio = new Date(fecha1.replaceAll("-", "/")).toISOString().substring(0, 10);
	var fechaVencimiento = new Date(fecha2.replaceAll("-", "/")).toISOString().substring(0, 10);
	
	document.querySelectorAll("input[type='date'][min='fechaInicio']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "min" */
			elemento.min = fechaInicio;
			$('#dtFechaInicio').val(fechaInicio);
			//document.getElementById("dtFechaInicio").value = fechaInicio;
	});
	dtInicio = fechaInicio;
	
	document.querySelectorAll("input[type='date'][max='fechaVencimiento']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "min" */
			elemento.max = fechaVencimiento;
			$('#dtFechaVencimiento').val(fechaVencimiento);
			//document.getElementById("dtFechaVencimiento").value = fechaVencimiento;
	});
	dtVencimiento = fechaVencimiento
}

function obtenerActPlanAccion(url) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 100);
		tblPlanAccion.clear().draw();
		$.each(data, function(idx, opt) {
			var botones = " <button class='btn btn-info' tabindex='0'"
				+ " title='Editar Observacion' type='button'"
				+ " onclick='editarActividad("
				+ opt.id_planaccion
				+ ")' id="
				+ opt.id_planaccion
				+ " >"
				+ " <i class='bi bi-pencil-square'></i>";
			tblPlanAccion.row.add([opt.id_planaccion, opt.nombreActividad, opt.nombreResponsable,
			opt.dtFechaInicio, opt.dtFechaVencimiento, opt.estatus,opt.vc_estatus_responsable, botones]).draw(false);
			tblPlanAccion.column(0).visible(false);
		});
		setTimeout($.unblockUI, 100);
		mnsExisteRegistros(tblPlanAccion.rows().count());
	});
	
}

function mnsExisteRegistros(tam){
	if(tam > 0){
			swal({
				title: "Informacion.",
				text: "Se consultaron las Actividades del Plan de Accion",
				icon: "success",
				confirmButtonText: 'Aceptar'
			});
	}else{
				swal({
				title: "Informacion.",
				text: "El Plan de Accion no cuenta con actividades registradas",
				icon: "success",
				confirmButtonText: 'Aceptar'
			});
	}
}

var editarActividadPA = "editarActividad?idActividad=";
function editarActividad(idPA){
	var url = getAbsolutePath() + editarActividadPA+idPA;
	datosActividad(url);
}

function datosActividad(url){
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 100); 
		document.getElementById("idNombreActividad").value = data.vc_desc_actividad;
		document.getElementById("idEncargadoActividad").value = data.i_responsable;
		document.getElementById("dtFechaInicio").value = data.dt_fecha_inicio;
		document.getElementById("dtFechaVencimiento").value = data.dt_fecha_vencimiento;
		if(data.i_confirmacion_encargado == 2){
			$("#idFinActividad").css("display", "none");
		}else if(data.i_confirmacion_encargado == 1){
			$("#idFinActividad").css("display", "block");
			document.getElementById("idConfirmarActividad").value = data.i_confirmacion_responsable;
		}
		document.getElementById("idAct").value = data.pk_id_planaccion;
	});
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
				+ " title='Descargar Adjunto' type='button'"
				+ " onclick='verPDF("
				+ opt.pk_id_archivo
				+ ")' id="
				+ opt.pk_id_archivo
				+ " >"
				+ " <i class='bi bi-download'></i></button>";
			tblAdjuntos.row.add([opt.pk_id_archivo, opt.vc_nombre_docto, botones]).draw(false);
			tblAdjuntos.column(0).visible(false);
		});
		setTimeout($.unblockUI, 100);
		mnstblAdjuntos(tblAdjuntos.rows().count());
	});
}

function verPDF(id) {
console.log(""+id);
	var servicioPDF = "buscarPDF?idArchivo=";
	var urlArchivo = getAbsolutePath() + servicioPDF + id;

	window.open(urlArchivo);
	window.focus();

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

function eventosContadorNombre() {
	const mensaje = document.getElementById('idNombreActividad');
	const contador = document.getElementById('contNombre');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
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

function asignarArchivo(archivo) {

	if (document.getElementById(archivo.id).value > 0) {
		var opcionesArchivo = document.getElementById(archivo.id);
		var extension = opcionesArchivo.options[opcionesArchivo.selectedIndex].id;
		 document.getElementById("file").value = "";
		$('#file').attr("accept", extension);
		$('#file').attr("disabled", false);
	} else {
		swal({
			title: "Informacion.",
			text: "Se requiere seleccionar una opcion valida",
			icon: "success",
			confirmButtonText: 'Aceptar'
		});

		var opcionesArchivo = document.getElementById(archivo.id);
		var extension = opcionesArchivo.options[opcionesArchivo.selectedIndex].id;
		document.getElementById("file").value = "";
		$('#file').attr("accept", "");
		$('#file').attr("disabled", true);

	}
}
