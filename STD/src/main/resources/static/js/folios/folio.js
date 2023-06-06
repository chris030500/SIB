var folioID;
var puesto = "buscarPuesto?empleadoID=";
var buscarxArea = "busquedaxArea?areaID=";
var altaFolio = "altaFolio?noficio=";
var buscarObservaciones = "buscarObservaciones?idFolio=";
var datosFolio = "cargarFolio/";
var modFolio = "modificarFolio?id=";
var modObservaciones = "modificarObservacion?idObs=";
var altaObservaciones = "altaObservacion?idAreaAtencion=";


var inicio = "folio";
var fechaVencimientoObs;


$(document).ready(function() {

	iniciarfechas();
	eventosModal();
	eventosContadorObservacion();
	eventosContadorOficio();
	eventosContadorAsunto();
	inicioTabla();
});



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

function eventosContadorObservacion() {
	const mensaje = document.getElementById('idObservacion');
	const contador = document.getElementById('contador');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}
function eventosContadorOficio() {
	const mensaje = document.getElementById('noficio');
	const contador = document.getElementById('conNoficio');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}
function eventosContadorAsunto() {
	const mensaje = document.getElementById('idAsunto');
	const contador = document.getElementById('contIdAsunto');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function iniciarfechas() {
	var hoyfecha = new Date();
	
		switch (hoyfecha.getDay()) {
					case 0:
						hoyfecha.setUTCDate(hoyfecha.getUTCDate() + 1);
						dia = dia + 0;
						break;
					case 6:
						hoyfecha.setUTCDate(hoyfecha.getUTCDate() + 2);
						dia = dia + 0;
						break;
				}
	
	const hoy_fecha = hoyfecha.toISOString().substring(0, 10);


	document.querySelectorAll("input[type='date'][min='hoy']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "min" */
			elemento.min = hoy_fecha;
		});

	document.querySelectorAll("input[type='date'][max='hoy']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "max" */
			elemento.max = hoy_fecha;
		});

	document.querySelectorAll("input[type='date'][for='fechaActual']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "max" */
			elemento.value = hoy_fecha;
		});

}

function validarFinSemana(fecha, tFecha) {
	var correcta = true;	
	if(fecha.getDay() == 0 || fecha.getDay() == 6){
		var correcta = false;
		switch (tFecha) {
			case 1:
				document.getElementById("idFechaRegistro").style.borderColor = "#F70707";
				break;
			case 2:
				document.getElementById("idVencimiento").style.borderColor = "#F70707";
				break;
		}
	}else{
		switch (tFecha) {
			case 1:
				document.getElementById("idFechaRegistro").style.borderColor = "";
				break;
			case 2:
				document.getElementById("idVencimiento").style.borderColor = "";
				break;
		}
	}

	return correcta;
}

/***Actualizar fechas 
 */
function calcularPlazo(opcion, fechaI, fechaF) {
	var plazo = document.getElementById("idPlazo").value;
	switch (opcion) {
		case 1:
			var dia = 0;
			while (Date.parse(fechaI) <= Date.parse(fechaF)) {
				switch (fechaI.getDay()) {
					case 0:
						fechaI.setUTCDate(fechaI.getUTCDate() + 1);
						dia = dia + 0;
						break;
					case 6:
						fechaI.setUTCDate(fechaI.getUTCDate() + 2);
						dia = dia + 0;
						break;
					default:
						fechaI.setUTCDate(fechaI.getUTCDate() + 1);
						dia = dia + 1;
				}
			}
			document.getElementById("idPlazo").value = dia;
			fechaI = fechaF;
			break;
		case 2:
			for (var i = 0; i < plazo; i++) {
				fechaI.setUTCDate(fechaI.getUTCDate() + 1);
				switch (fechaI.getDay()) {
					case 0:
						fechaI.setUTCDate(fechaI.getUTCDate() + 1);
						break;
					case 6:
						fechaI.setUTCDate(fechaI.getUTCDate() + 2);
						break;
				}
			}
			break;
	}
	return fechaI;
}

function calcularVencimiento(opcion) {
	var fechaInicioD = new Date(document.getElementById("idFechaRegistro").value.replaceAll("-", "/"));
	var fechaFinD = new Date(document.getElementById("idVencimiento").value.replaceAll("-", "/"));
	switch (opcion) {
		case 1:
			if (validarFinSemana(fechaInicioD, 1) == true && validarFinSemana(fechaFinD, 2) == true) {
				fechaVen = calcularPlazo(1, fechaInicioD, fechaFinD);
			}else{
				swal({
					title: "Error",
					text: "Seleccionar dia habil",
					icon: "warning",
				});
			}

			break;
		case 2:
			if (validarFinSemana(fechaInicioD, 1) == true && validarFinSemana(fechaFinD, 2)== true) {
				if (document.getElementById("idPlazo").value > 0) {
					fechaVen = calcularPlazo(2, fechaInicioD, fechaFinD);
				}
			}else{
				swal({
					title: "Error",
					text: "Seleccionar dia habil",
					icon: "warning",
				});
			}

			break;
	}

	document.querySelectorAll("input[type='date'][for='vencimientoFolio']")
		.forEach(elemento => {
			elemento.value = fechaVen.toISOString().substring(0, 10);
		});

}

function validarFechas(fecha1, fecha2) {
	var fechaInicio = Date.parse(fecha1);
	var fechaFin = Date.parse(fecha2);
	var result = false;

	if (fechaInicio > fechaFin) {
		result = true;
	}

	if (fechaFin < fechaInicio) {
		result = true;
	}

	if (result == true) {
		document.getElementById("idFechaRegistro").style.borderColor = "#F70707";
		document.getElementById("idVencimiento").style.borderColor = "#F70707";
	} else {
		document.getElementById("idFechaRegistro").style.borderColor = "";
		document.getElementById("idVencimiento").style.borderColor = "";
	}

	return result;
}


function eventosModal() {
	$("#btnRegistrar").click(function() {

		if (validarFechas(document.getElementById("idFechaRegistro").value, document.getElementById("idVencimiento").value) == false) {
			if (guardarVacio() == 0) {
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
		} else {
			swal({
				title: "Error",
				text: "Fecha de Registro y/o Fecha de Vencimiento son Incorrectas",
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

		if (validacionesAgregarObs(document.getElementById("idFechaVencimiento").value) == false) {
			if (document.getElementById("idObservacionSelect").value > 0) {
				modificarObs();
			} else {
				tblObservacionesLoad();
			}
		} else {
			swal({
				title: "Error",
				text: "Fecha de vencimiento de la observacion es mayor a la fecha de vencimiento del folio.",
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


}

var buscarArchivos = "buscarArchivos?idFolio=";
function adjuntarFiles(id) {
	document.getElementById("idFolioArchivo").value = id;
	var urlServicio = getAbsolutePath() + buscarArchivos + id;
	obtDtostblAdjuntos(urlServicio);
	document.getElementById("file").value = "";

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

function verPDF(id) {

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

var agregarArchivos = "subirArchivo?idFolio=";
function addArchivos(data) {
	$.ajax({
		async: false,
		url: getAbsolutePath() + agregarArchivos + document.getElementById("idFolioArchivo").value,
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
				adjuntarFiles(document.getElementById("idFolioArchivo").value);
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

function validacionesAgregarObs(fecha1) {
	var result = false;
	if (Date.parse(fecha1) > Date.parse(fechaVencimientoObs)) {
		document.getElementById("idFechaVencimiento").style.borderColor = "#F70707";
		result = true;
	} else {
		document.getElementById("idFechaVencimiento").style.borderColor = "";
		result = false
	}
	return result;
}

function tblObservacionesLoad() {
	if (seleccionoFolio() == 0) {
		if  (datosObservacionValidos() == 0) {
			var urlServicio = getAbsolutePath() + altaObservaciones + document.getElementById("idAreaAtencion").value;
			urlServicio = urlServicio + "&idResponsableAtencion=" + document.getElementById("idResponsableAtencion").value;
			urlServicio = urlServicio + "&idFechaVencimiento=" + document.getElementById("idFechaVencimiento").value;
			urlServicio = urlServicio + "&desObservacion=" + document.getElementById("idObservacion").value;
			urlServicio = urlServicio + "&folioID=" + folioID;
			urlServicio = urlServicio + "&obsOficio=" + document.getElementById("obsOficio").value;
			peticionPost(urlServicio, 2, folioID);
		} else {
			swal({
				title: "Alerta",
				text: "Todos los datos son obligatorios.",
				icon: "warning",
			});
		}

	} else {
		swal({
			title: "Alerta",
			text: "Debe seleccionar un Oficio para relacionar la Observacion",
			icon: "warning",
		});
	}

}
function seleccionoFolio() {
	var correcto = 0;

	if (folioID != undefined) {
		document.getElementById("lblSeleccion").style.borderColor = "";
		correcto = 0;
	} else {
		document.getElementById("lblSeleccion").style.borderColor = "#F70707";
		correcto = 1;
	}

	return correcto;
}

function datosObservacionValidos() {
	var correcto = 0;

	if (document.getElementById("idAreaAtencion").value > 0) {
		document.getElementById("idAreaAtencion").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("idAreaAtencion").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}

	if (document.getElementById("idResponsableAtencion").value > 0) {
		correcto = correcto + 0;
		document.getElementById("idResponsableAtencion").style.borderColor = "";
	} else {
		correcto = correcto + 1;
		document.getElementById("idResponsableAtencion").style.borderColor = "#F70707";
	}

	if (document.getElementById("idFechaVencimiento").value.length > 0) {
		correcto = correcto + 0;
		document.getElementById("idFechaVencimiento").style.borderColor = "";
	} else {
		correcto = correcto + 1;
		document.getElementById("idFechaVencimiento").style.borderColor = "#F70707";
	}

	if (document.getElementById("idObservacion").value.length > 0)  {
		correcto = correcto + 0;
		document.getElementById("idObservacion").style.borderColor = "";
	} else {
		correcto = correcto + 1;
		document.getElementById("idObservacion").style.borderColor = "#F70707";
	}
	if (document.getElementById("obsOficio").value > 0)  {
		correcto = correcto + 0;
		document.getElementById("obsOficio").style.borderColor = "";
	} else {
		correcto = correcto + 1;
		document.getElementById("obsOficio").style.borderColor = "#F70707";
	}

	return correcto;
}

function modificarObs() {
	if (seleccionoFolio() == 0)  {
		if (datosObservacionValidos() == 0)  {
			var asignacion = (document.getElementById("idReasignacion").value == 2) ? "&reasignar=1" : "&reasignar=0";
			var urlServicio = getAbsolutePath() + modObservaciones + document.getElementById("idObservacionSelect").value;
			urlServicio = urlServicio + "&idAreaAtencion=" + document.getElementById("idAreaAtencion").value;
			urlServicio = urlServicio + "&idResponsableAtencion=" + document.getElementById("idResponsableAtencion").value;
			urlServicio = urlServicio + "&idFechaVencimiento=" + document.getElementById("idFechaVencimiento").value;
			urlServicio = urlServicio + "&desObservacion=" + document.getElementById("idObservacion").value;
			urlServicio = urlServicio + asignacion;
			urlServicio = urlServicio + "&obsOficio=" + document.getElementById("obsOficio").value;
			
			peticionPost(urlServicio, 2, folioID);
		} else {
			swal({
				title: "Alerta",
				text: "Todos los datos son obligatorios.",
				icon: "warning",
			});
		}
	} else {
		swal({
			title: "Alerta",
			text: "Debe seleccionar un Oficio para relacionar la Observacion",
			icon: "warning",
		});
	}

}

function reiniciarDatos() {
	document.getElementById("idObservacionSelect").value = 0;
	document.getElementById("idAreaAtencion").value = 0;
	document.getElementById("idResponsableAtencion").value = 0;
	document.getElementById("idFechaVencimiento").value = fechaVencimientoObs;
	document.getElementById("idObservacion").value = "";
	document.getElementById("idObservacionSelect").value = "0";
	document.getElementById("idReasignacion").value = "0";
	document.getElementById("obsOficio").value = 0;
}


function guardarFolio(proceso) {
	var urlServicio = "";
	switch (proceso) {
		case 1:
			urlServicio = getAbsolutePath() + altaFolio;
			break;
		case 2:
			urlServicio = getAbsolutePath() + modFolio+ document.getElementById("idUnico").value + "&noficio=";
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

	$("#modalFolio").hide();
	peticionPost(urlServicio, 1, 0);

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
	var validacion = 0;

	if (document.getElementById("noficio").value.length == 0) {
		document.getElementById("noficio").style.borderColor = "#F70707";
		validacion = validacion+ 1;
	} else {
		document.getElementById("noficio").style.borderColor = '';
		validacion = validacion+ 0;
	}

	if (document.getElementById("idRecepcion").value.length == 0) {
		document.getElementById("idRecepcion").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("idRecepcion").style.borderColor = '';
		validacion = validacion+ 0;
	}
	if (document.getElementById("idAsunto").value.length == 0) {
		document.getElementById("idAsunto").style.borderColor = "#F70707";
		validacion = validacion+ 1;
	} else {
		document.getElementById("idAsunto").style.borderColor = '';
		validacion = validacion + 0;
	}

	if (document.getElementById("idDirigido").value == '0') {
		document.getElementById("idDirigido").style.borderColor = "#F70707";
		validacion = validacion+ 1;
	} else {
		document.getElementById("idDirigido").style.borderColor = '';
		validacion = validacion+ 0;
	}

	if (document.getElementById("iPuesto").value.length == 0) {
		document.getElementById("iPuesto").style.borderColor = "#F70707";
		validacion = validacion+ 1;
	} else {
		document.getElementById("iPuesto").style.borderColor = '';
		validacion = validacion+ 0;
	}
	if (document.getElementById("idTipoOficio").value == '0') {
		document.getElementById("idTipoOficio").style.borderColor = "#F70707";
		validacion = validacion+ 1;
	} else {
		document.getElementById("idTipoOficio").style.borderColor = '';
		validacion = validacion+ 0;
	}

	if (document.getElementById("idOrganoEmite").value == '0') {
		document.getElementById("idOrganoEmite").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("idOrganoEmite").style.borderColor = '';
		validacion = validacion+ 0;
	}

	if (document.getElementById("idFechaRegistro").value.length == 0) {
		document.getElementById("idFechaRegistro").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("idFechaRegistro").style.borderColor = '';
		validacion = validacion + 0;
	}

	if (document.getElementById("idPlazo").value == 0) {
		document.getElementById("idPlazo").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("idPlazo").style.borderColor = '';
		validacion = validacion + 0;
	}

	if (document.getElementById("idVencimiento").value.length == 0) {
		document.getElementById("idVencimiento").style.borderColor = "#F70707";
		validacion = validacion + 1;
	} else {
		document.getElementById("idVencimiento").style.borderColor = '';
		validacion = validacion + 0;
	}
	//if (document.getElementById("idEstatusOficio").value.length == 0) {
	//document.getElementById("idEstatusOficio").style.borderColor = "#F70707";
	//validacion = true;
	//} else {
	//document.getElementById("idEstatusOficio").style.borderColor = '';
	//validacion = false;
	//}

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
	if (idArea.value > 0) {
		var buscarEmpleados = getAbsolutePath() + buscarxArea + idArea.value;
		listaResponsable(buscarEmpleados, 0);
	} else {
		swal({
			title: "",
			text: "Seleccione una Area de Atencion Valida.",
			icon: "warning",
		});
	}

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
	reiniciarDatos();
	document.querySelector('#lblSeleccion').innerText = $(idFolio).parents("tr").find("td").eq(1).html() + "-" + $(idFolio).parents("tr").find("td").eq(2).html();
	asignarFechaVencimiento(idFolio);
	folioID = $(idFolio).parents("tr").find("td").eq(0).html();
	var urlServicio = getAbsolutePath() + buscarObservaciones + folioID;
	obtenerDatostblObservaciones(urlServicio);
}


function asignarFechaVencimiento(fechaV) {

	const hoy = new Date();

	var fecha1 = $(fechaV).parents("tr").find("td").eq(4).html();
	var fecha = $(fechaV).parents("tr").find("td").eq(5).html();
	var fechaVencimiento = new Date(fecha.replaceAll("-", "/")).toISOString().substring(0, 10);
	var fechaRegistro;
	if (hoy > Date.parse(fecha1)) {
		fechaRegistro = hoy.toISOString().substring(0, 10);
	} else {
		fechaRegistro = new Date(fecha1.replaceAll("-", "/")).toISOString().substring(0, 10);
	}

	document.querySelectorAll("input[type='date'][for='fechaVObservacion']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "min" */
			elemento.max = fechaVencimiento;
			document.getElementById("idFechaVencimiento").value = fechaVencimiento;
		});
	document.querySelectorAll("input[type='date'][for='fechaVObservacion']")
		.forEach(elemento => {
			/* A cada elemento encontrado le asignamos el atributo "min" */
			elemento.min = fechaRegistro;
		});
	fechaVencimientoObs = fechaVencimiento;
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
			tblObservacion.row.add([opt.i_observacion_oficio,opt.vc_area, opt.vc_responsable,
			opt.dt_fecha_asignacion, opt.dt_fecha_vencimiento, opt.vc_descripcion, opt.vc_estatus, opt.vc_confirma, opt.vc_desc_rechazo, botones]).draw(false);
			//tblObservacion.column(0).visible(false);
		});
		setTimeout($.unblockUI, 100);
		mnsExisteRegistros(tblObservacion.rows().count());
	});
}

function mnsExisteRegistros(num) {
	if (num > 0) {
		swal({
			title: "Informacion.",
			text: "Se consultaron las observaciones del Oficio.",
			icon: "success",
			confirmButtonText: 'Aceptar'
		})
	} else {
		swal({
			title: "Informacion.",
			text: "El Oficio no cuenta con observaciones registradas",
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
var tblObservacion;
var tblOficio;
var tblAdjuntos;
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
function editarFolio(id) {

	document.getElementById("idFolioSelec").value = id;

	var url = getAbsolutePath() + datosFolio + id;
	obtenerFolio(url);
}
function obtenerFolio(url) {
	$.blockUI({ message: 'Espere un momento...' });
	$.getJSON(url, {}, function(data) {
		setTimeout($.unblockUI, 100);
		cargarDatos(data);
	});
}
function cargarDatos(data) {

	document.getElementById("idUnico").value = data.pk_id_folio;
	document.getElementById("noficio").value = data.vc_folio;
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
	document.getElementById("idEstatusOficio").value = 1;
	iniciarfechas();
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
		document.getElementById("idReasignacion").value = data.i_confirma;
		document.getElementById("obsOficio").value = data.i_observacion_oficio;
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



