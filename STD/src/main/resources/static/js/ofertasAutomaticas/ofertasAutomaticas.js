// @ts-nocheck
/**
 * Metodos a ejecutar en la pantalla de Ofertas Automaticas.
 */
//inicio de la pagina
var procesoModificacion = 0;
$(document).ready(function() {
	btnsModal();
	eventcontNomClas();
	//eventcontAbrevia();
	eventmensajeClasificacion();
	cargatblProcesoG();
	iniciaTblParametros();
	iniciaTblClasificacion();
});

/** Contadores de registros de caracteres */

function eventcontNomClas() {
	const mensaje = document.getElementById('nomClasificacion');
	const contador = document.getElementById('contNomClas');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function eventcontAbrevia() {
	const mensaje = document.getElementById('abrevacion');
	const contador = document.getElementById('contAbrevia');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function eventmensajeClasificacion() {
	const mensaje = document.getElementById('mensajeClasificacion');
	const contador = document.getElementById('contMns');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}
/**Termino de contadores */

var url = "";
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var cargatblParametros = "obtParametros";
function iniciaTblParametros() {
	url = getAbsolutePath() + cargatblParametros;
	$.getJSON(url, {}, function(data) {
		tblConfiguracion.clear().draw();
		$.each(data, function(idx, opt) {

			if (opt.i_subir_archivo == 0) {
				var btnsActualizar = " <button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Parametros' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='editarParametros("
					+ opt.pk_id_base
					+ ")' id="
					+ opt.pk_id_base
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-tools'></i></button>";

				var btnEjecutar = " <button class='btn btn-success' tabindex='0' "
					+ " title='Ejecutar Proceso' aria-controls='table-datatable' type='button'"
					+ " onclick='ejecutarProceso("
					+ opt.pk_id_base
					+ ")' id="
					+ opt.pk_id_base
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-file-play-fill'></i></button>";

				if (opt.i_boton_historico == 0) {
					var btnHistorico = "";
				} else {
					var btnHistorico = " <button class='btn btn-warning' tabindex='0' "
						+ " title='Mostrar Detalle' aria-controls='table-datatable' type='button'"
						+ " onclick='obtenerTabla("
						+ opt.pk_id_base
						+ ")' id="
						+ opt.pk_id_base
						+ " data-tooltip='Add'>"
						+ " <i class='bi bi-archive-fill'></i></button>";
				}


				tblConfiguracion.row.add([opt.vc_nombre_base, opt.vc_dia_inicio_proceso, opt.vc_dia_fin_proceso, opt.vc_hora_inicio_proceso, opt.vc_hora_fin_proceso, opt.vc_periodo_ejecucion_proceso, opt.vc_ruta_proceso, btnsActualizar, btnEjecutar, btnHistorico]).draw(false);

			} else {

				var btnsArchivo = "<button class='btn btn-info' tabindex='0' data-bs-toggle='modal'"
					+ " title='Adjuntar Archivos' data-bs-target='#modalAdjuntos' aria-controls='table-datatable' type='button'"
					+ " onclick='adjuntarFiles("
					+ opt.pk_id_base
					+ ")' id="
					+ opt.pk_id_base
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-files'></i></button>";


				var btnsActualizar = " <button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Parametros' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='editarParametros("
					+ opt.pk_id_base
					+ ")' id="
					+ opt.pk_id_base
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-tools'></i></button>";

				var btnEjecutar = " <button class='btn btn-success' tabindex='0' "
					+ " title='Ejecutar Proceso' aria-controls='table-datatable' type='button'"
					+ " onclick='ejecutarProceso("
					+ opt.pk_id_base
					+ ")' id="
					+ opt.pk_id_base
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-file-play-fill'></i></button>";

				if (opt.i_boton_historico == 0) {
					var btnHistorico = "";
				} else {
					var btnHistorico = " <button class='btn btn-warning' tabindex='0' "
						+ " title='Mostrar Detalle' aria-controls='table-datatable' type='button'"
						+ " onclick='obtenerTabla("
						+ opt.pk_id_base
						+ ")' id="
						+ opt.pk_id_base
						+ " data-tooltip='Add'>"
						+ " <i class='bi bi-archive-fill'></i></button>";
				}

				tblConfiguracion.row.add([opt.vc_nombre_base, opt.vc_dia_inicio_proceso, opt.vc_dia_fin_proceso, opt.vc_hora_inicio_proceso, opt.vc_hora_fin_proceso, opt.vc_periodo_ejecucion_proceso, btnsArchivo, btnsActualizar, btnEjecutar, btnHistorico]).draw(false);

			}


		});
	});

}

var listarArchivos = "listaArchivos?idBase=";
function adjuntarFiles(idBase) {
	url = "";
	url = getAbsolutePath() + listarArchivos + idBase;
	obtListadoArchivos(url);
	document.getElementById("file").value = "";
}

function obtListadoArchivos(ulrServicio){
	// Mostrar SweetAlert con spinner
    Swal.fire({
      title: 'Espere un momento por Favor...',
      allowOutsideClick: false,
      onBeforeOpen: () => {
        Swal.showLoading();
      }
    });
	
	$.getJSON(url, {}, function(data) {
		tblAdjuntos.clear().draw();
		$.each(data, function(idx, opt) {

			var botones = " <button class='btn btn-info' tabindex='0'"
				+ " title='Ver Archivo' type='button'"
				+ " onclick='verArchivo(this)'>"
				+ " <i class='bi bi-download'></i></button>";
			tblAdjuntos.row.add([opt.nombreArchivo, opt.pesoArchivo, opt.fechaCreacion, opt.fechaModifacion, opt.rutaArchivo, botones]).draw(false);
		});
		Swal.close();
		//setTimeout($.unblockUI, 100);
		//tblAdjuntos.column(4).visible(false);
	});
}

var abrirArchivo = "verArchivo?ruta=";
function verArchivo(dtosArchivo) {
	$(dtosArchivo).parents("tr").find("td").eq(4).html();
	url = "";
	url = getAbsolutePath() + abrirArchivo + encodeURIComponent($(dtosArchivo).parents("tr").find("td").eq(4).html()) + "&nombre=" + encodeURIComponent($(dtosArchivo).parents("tr").find("td").eq(0).html());
	window.open(url);
	window.focus();

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

var agregarArchivos = "subirArchivo?idBase=2";
function addArchivos(data) {
	$.ajax({
		async: false,
		url: getAbsolutePath() + agregarArchivos,
		type: "POST",
		enctype: 'multipart/form-data',
		data: data, //pass the form data
		processData: false,
		contentType: false,
		beforeSend: function() {
			$.blockUI({ message: '<H2>Espere un momento...</H2>' });
		},
		success: function(data) {
			swal("", data[0],"success").then(resp => {
				adjuntarFiles(2);
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




function ejecutarProceso(idBaseEjecutar) {

	Swal.fire({
		title: "¿Esta seguro de ejecutar el proceso manual?",
		showCancelButton: true,
		confirmButtonText: "Acepto",
		cancelButtonText: "Cancelar",
	}).then(resultado => {
		if (resultado.value) {
			url = "";
			url = "ejecutarProceso?idBase=" + idBaseEjecutar;
			peticionPost(3, getAbsolutePath() + url);
		}
	});

}

var obtClasificacion = "obtClasificacion";
function iniciaTblClasificacion(){
	 url = getAbsolutePath() + obtClasificacion;
	 $.getJSON(url, {}, function(data) {
   		tblclasificacionBaseTable.clear().draw();
   		$.each(data, function(idx, opt) {
			var btnsEditar = " <button class='btn btn-primary btn-sm' tabindex='0' data-bs-toggle='modal'"
				+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
				+ " onclick='editarClasificacion("
				+ opt.pk_id_clasificacion
				+ ")' id="
				+ opt.pk_id_clasificacion
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-pencil-square'></i></button>";
				
			var btnsActivar = " <button class='btn btn-success btn-sm' tabindex='0' data-bs-toggle='tooltip'"
				+ " title='Activar Clasificacion' aria-controls='table-datatable' type='button' data-tooltip='Refresh'"
				+ " onclick='activarClasificacion(this)' id="
				+ opt.pk_id_clasificacion
				+ " for="
				+ opt.i_estatus_clasificacion
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-check2-circle'></i></button>";
			
			
			var btnBaja= " <button class='btn btn-warning btn-sm' tabindex='0' data-bs-toggle='tooltip'"
				+ " title='Baja Clasificacion' aria-controls='table-datatable' type='button' data-tooltip='Refresh'"
				+ " onclick='bajaClasificacion(this)' id="
				+ opt.pk_id_clasificacion
				+ " for="
				+ opt.i_estatus_clasificacion
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-x-circle'></i></button>";

			var todosBtns = "<div class='dt-buttons' style='text-align: center;'>"
				+ btnsEditar
				+ btnsActivar
				+ btnBaja
				+ "</div>"


			tblclasificacionBaseTable.row.add([opt.fk_id_base.vc_nombre_base, opt.vc_nombre_clasificacion, opt.vc_abreviacion_clasificacion
				, opt.vc_codigo_clasificacion, opt.i_vigencia_clasificacion, (opt.i_estatus_clasificacion == 1) ? 'ACTIVO' : 'INACTIVO', todosBtns]).draw(false);


		});
	});
}


var obtTabla = "consultaTabla?idBase=";
function obtenerTabla(idBase) {
	url = getAbsolutePath() + obtTabla + idBase;
	peticionHistorico(url);
}

var obtParametros = "consultaParametros?idBase=";
function editarParametros(idBase) {
	limpiarParametros();
	url = getAbsolutePath() + obtParametros + idBase;
	peticionParametros(url);
}

function peticionHistorico(url) {
	$.getJSON(url, {}, function(data) {
		modalHistorico(data);
	});
}

function peticionParametros(url) {
	$.getJSON(url, {}, function(data) {
		modalParametros(data);
	});
}

function modalHistorico(data) {
	tblDetalle.clear().draw();
	$.each(data, function(idx, opt) {
		if (opt.fechaCancelacion == '1900-01-01') {
			var cancelacion = 'No se encuentra cancelada';
		} else {
			var cancelacion = opt.fechaCancelacion;
		}
		tblDetalle.row.add([opt.tipoCampaniaId, opt.tipoCampania, opt.fechaRegistro, opt.fechaAutorizacion, cancelacion]).draw(false);
	});

	$("#modalDetalles").modal("show");
}

var idBaseFidelidad = 2;
var basesID = 0;
function modalParametros(data) {
	console.log(data);
	basesID = data.pk_id_base;
	document.getElementById("dComienzoProceso").value = data.vc_dia_inicio_proceso;
	document.getElementById("dFinalProceso").value = data.vc_dia_fin_proceso;
	document.getElementById("hComienzoProceso").value = data.vc_hora_inicio_proceso;
	document.getElementById("hFinalProceso").value = data.vc_hora_fin_proceso;
	document.getElementById("mPeriodoEjecucion").value = data.vc_periodo_ejecucion_proceso;


	if (data.pk_id_base == idBaseFidelidad) {
		document.getElementById('lblRuta').style.display = 'block';
		document.getElementById('rutaArchivo').style.display = 'block';
		document.getElementById("rutaArchivo").value = data.vc_ruta_proceso;
	} else {
		document.getElementById('lblRuta').style.display = 'none';
		document.getElementById('rutaArchivo').style.display = 'none';
	}
	$("#modalParametros").modal("show");
}

function limpiarParametros() {
	document.getElementById("dComienzoProceso").value = "";
	document.getElementById("dFinalProceso").value = "";
	document.getElementById("hComienzoProceso").value = "";
	document.getElementById("hFinalProceso").value = "";
	document.getElementById("mPeriodoEjecucion").value = "";
	document.getElementById("rutaArchivo").value = "";
	
}
var url = "";
function btnsModal(){
 	$("#btnCancelar").click(function() {
   		basesID = 0;
   		procesoGestionID = 0;
		limpiarParametros();
		$("#modalParametros").modal("hide");
	});

	$("#btnCerrar").click(function() {
		idClasificacion = 0;
		limpiarDatosClasificacion();
		$("#modalClasificacion").modal("hide");
	});
	
	
	$("#btnActualizarParams").click(function() {
		
		if(basesID > 0){
			url = "actualizarParametro?idBase="+basesID;
  			if(validarDatosParams() == 0){
     			if(validarFechas() && validarHoras()){
        			console.log(getAbsolutePath()+url);
        			$("#modalParametros").modal("hide");
        			peticionPost(1,getAbsolutePath()+url);
       			}
    		}else{
      			swal({
					title: "Error, ",
					text: "Existen campos vacíos, favor de validar.",
					icon: "warning",
					});
     		}
		}else if( procesoGestionID> 0){
			url = "actualizarParametroGestion?procesoGestionID="+procesoGestionID;
			if(validarDatosParams() == 0){
     			if(validarFechas() && validarHoras()){
        			console.log(getAbsolutePath()+url);
        			$("#modalParametros").modal("hide");
        			peticionPost(4,getAbsolutePath()+url);
       			}
    		}else{
      			swal({
					title: "Error, ",
					text: "Existen campos vacíos, favor de validar.",
					icon: "warning",
					});
     		}
		}
	});

	$("#btnRegistrarClasif").click(function() {

		switch (procesoModificacion) {
			case 1:
				idClasificacion = 0;
				dtosClasificacion();
				procesoModificacion = 0;
				break;
			case 2:
				dtosClasificacion();
				procesoModificacion = 0;
				break;
		}
	});

	$("#btnGuardarFile").click(function() {


		if (document.getElementById("file").value.length > 0) {
			Swal.fire({
				title: 'Procesando.....',
				text: 'Espere un momento..',
				timer: 4000, // tiempo en milisegundos
				timerProgressBar: true,
				showConfirmButton: false
			}).then((result) => {
				// Este código se ejecutará después de que el temporizador haya terminado
				if (result.dismiss === Swal.DismissReason.timer) {
					var form = $('#adjuntoArchivoOficio')[0];
					var data = new FormData(form);
					addArchivos(data)
				}
			});

		} else {
			Swal.fire({
     		 title:'Es necesario seleccionar un Archivo.',
      		 html: '<i class="bi bi-exclamation-triangle-fill text-warning" style="font-size: 3em;"></i>',
      		 icon:null,
      		 showConfirmButton: true
    		});
			
		}

	});

	$("#btnCerrarAdjunto").click(function() {
		$("#modalAdjuntos").modal("hide");
		tblAdjuntos.clear().draw();
	});


}
function validarDatosParams() {
	var correcto = 0;

	if (document.getElementById("dComienzoProceso").value.length > 0 && document.getElementById("dComienzoProceso").value > 0) {
		url = url + "&dComienzoProceso=" + encodeURIComponent(document.getElementById('dComienzoProceso').value);
		document.getElementById("dComienzoProceso").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("dComienzoProceso").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("dFinalProceso").value.length > 0 && document.getElementById("dFinalProceso").value > 0) {
		url = url + "&dFinalProceso=" + encodeURIComponent(document.getElementById('dFinalProceso').value);
		document.getElementById("dFinalProceso").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("dFinalProceso").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("hComienzoProceso").value.length > 0) {
		url = url + "&hComienzoProceso=" + document.getElementById('hComienzoProceso').value;
		document.getElementById("hComienzoProceso").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("hComienzoProceso").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("hFinalProceso").value.length > 0) {
		url = url + "&hFinalProceso=" + document.getElementById('hFinalProceso').value;
		document.getElementById("hFinalProceso").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("hFinalProceso").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("mPeriodoEjecucion").value.length > 0 && document.getElementById("mPeriodoEjecucion").value > 0) {
		url = url + "&mPeriodoEjecucion=" + encodeURIComponent(document.getElementById('mPeriodoEjecucion').value);
		document.getElementById("mPeriodoEjecucion").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("mPeriodoEjecucion").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}

	if (basesID == idBaseFidelidad) {
		if (document.getElementById("rutaArchivo").value.length > 0) {
			url = url + "&rutaArchivo=" + encodeURIComponent(document.getElementById('rutaArchivo').value);
			document.getElementById("rutaArchivo").style.borderColor = "";
			correcto = correcto + 0;
		} else {
			document.getElementById("rutaArchivo").style.borderColor = "#F70707";
			correcto = correcto + 1;
		}
	} else {
		url = url + "&rutaArchivo=" + encodeURIComponent(document.getElementById('rutaArchivo').value);
	}

	return correcto;
}

function validarFechas() {
	var dComienzoCampana = parseInt(document.getElementById('dComienzoProceso').value);
	var dFinalCampana = parseInt(document.getElementById('dFinalProceso').value);

	if (dComienzoCampana >= dFinalCampana) {
		swal({
			title: "Error, ",
			text: "El día que comienza el proceso debe ser menor al día que termina el proceso.",
			icon: "warning",
		});		
		
		return false;
	}
	return true;
}

function validarHoras() {
	console.log("Entro en validarHoras");
	var hComienzoCampana = document.getElementById('hComienzoProceso').value;
	var hFinalCampana = document.getElementById('hFinalProceso').value;

	console.log("hComienzoProceso: " + hComienzoProceso);
	console.log("hFinalProceso: " + hFinalProceso);

	if (hComienzoCampana === hFinalCampana) {
		console.log("Las horas son iguales");
		swal({
			title: "Advertencia",
			text: "Las horas de inicio y finalización son iguales, favor de agregar horas diferentes.",
			icon: "warning",
		});
		return false; // Indica que la validación falló
	}

	if (hComienzoCampana >= hFinalCampana) {
		console.log("Las hora de inicio es menor");
		swal({
			title: "Error",
			text: "La hora de inicio debe ser menor que la hora de fin del proceso.",
			icon: "error",
		});
		return false; // Indica que la validación falló
	}

	return true; // Indica que la validación fue exitosa
}
//enviar actualizacion

function peticionPost(metodo, metodoUrl) {
	$.ajax({
		url: metodoUrl,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			//$.blockUI({ message: '<span style="color: #007BFF;"><i class="fa-solid fa-gear"></i>Procesando Información.' });
			$.blockUI({
      message: '<h4>Procesando Información...</h4><div class="spinner-border" style="width: 3rem; height: 3rem;" role="status"></div> ',
      css: {
        border: 'none',
        padding: '15px',
        backgroundColor: '#fff',
        '-webkit-border-radius': '10px',
        '-moz-border-radius': '10px',
        opacity: .8,
        color: '#000'
      }
    });
		},
		complete: function() {
			setTimeout($.unblockUI, 300);
		},
		success: function(respuesta) {
   			url = "";
			Swal.fire("",respuesta[0],"success").then(resp => {

				if (metodo == 1) {
					iniciaTblParametros();
					limpiarParametros();

				} else if (metodo == 2) {
					iniciaTblClasificacion();
					limpiarDatosClasificacion();
				}else if(metodo == 3){
					//proceso manual Bases de ofertas automaticas. 
				}else if(metodo == 4){
					cargatblProcesoG();
					limpiarParametros();	
				}
			});


		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("Error","Se presento un error, intente mas tarde.", "error");
		}
	});
}

//clasificacion 

function limpiarDatosClasificacion() {
	procesoModificacion = 1;
	document.getElementById("base").value = 0;
	document.getElementById("nomClasificacion").value = '';
	document.getElementById("abrevacion").value = 0;
	document.getElementById("codigoClasificacion").value = '';
	document.getElementById("vigenciaClasificacion").value = 1;
	document.getElementById("mensajeClasificacion").value = '';
	//document.getElementById("mensajeClasificacion").style.display = "none";
	//document.getElementById("contMns").style.display = "none";
	//$("#confMensaje").prop('checked', false);


}

function checkMns() {

	if ($('#confMensaje').prop('checked')) {
		eventmensajeClasificacion();
		document.getElementById("mensajeClasificacion").value = '';
		document.getElementById("mensajeClasificacion").style.display = "block";
		document.getElementById("contMns").style.display = "block";
	} else {
		eventmensajeClasificacion();
		document.getElementById("mensajeClasificacion").value = '';
		document.getElementById("mensajeClasificacion").style.display = "none";
		document.getElementById("contMns").style.display = "none";
	}
}

//altaClasificacion
function validarVacioClasificacion() {
	var correcto = 0;
	url = "";
	if (document.getElementById("base").value > 0) {
		if (idClasificacion > 0) {
			url = "modClasificacion?idClass=" + encodeURIComponent(idClasificacion);
			url = url + "&idBase=" + encodeURIComponent(document.getElementById("base").value);
		} else {
			url = "altaClasificacion?idBase=" + encodeURIComponent(document.getElementById("base").value);
		}

		document.getElementById("base").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("base").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}

	if (document.getElementById("nomClasificacion").value.length > 0) {
		url = url + "&nomClasificacion=" + encodeURIComponent(document.getElementById("nomClasificacion").value);
		document.getElementById("nomClasificacion").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("nomClasificacion").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("abrevacion").value > 0) {
		url = url + "&abrevacion=" + encodeURIComponent(datoAbrevacion());
		document.getElementById("abrevacion").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("abrevacion").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("codigoClasificacion").value.length > 0) {
		url = url + "&codigoClasificacion=" + encodeURIComponent(document.getElementById("codigoClasificacion").value);
		document.getElementById("codigoClasificacion").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("codigoClasificacion").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}
	if (document.getElementById("vigenciaClasificacion").value > 0) {
		url = url + "&vigenciaClasificacion=" + encodeURIComponent(document.getElementById("vigenciaClasificacion").value);
		document.getElementById("vigenciaClasificacion").style.borderColor = "";
		correcto = correcto + 0;
	} else {
		document.getElementById("vigenciaClasificacion").style.borderColor = "#F70707";
		correcto = correcto + 1;
	}

	if (iMensaje == 2) {

		if (document.getElementById("mensajeClasificacion").value.length > 0) {
			url = url + "&mensajeClasificacion=" + encodeURIComponent(document.getElementById("mensajeClasificacion").value);
			document.getElementById("mensajeClasificacion").style.borderColor = "";
			correcto = correcto + 0;
		} else {
			document.getElementById("mensajeClasificacion").style.borderColor = "#F70707";
			correcto = correcto + 1;
		}
	} else {
		url = url + "&mensajeClasificacion=" + encodeURIComponent(document.getElementById("msnCampania").value);
		document.getElementById("mensajeClasificacion").style.borderColor = "";
		correcto = correcto + 0;
	}

	return correcto;

}

function datoAbrevacion() {
	var textoAbrevacion = "";
	var select = document.getElementById("abrevacion");
	var textoAbrevacion = select.options[select.selectedIndex].text;

	console.log("" + textoAbrevacion);
	return textoAbrevacion;
}

function dtosClasificacion() {

	if (validarVacioClasificacion() == 0) {
		console.log(getAbsolutePath() + url);
		$("#modalClasificacion").modal("hide");
		peticionPost(2, getAbsolutePath() + url);
	} else {
		swal({
			title: "Error, ",
			text: "Existen campos vacíos o el valor seleccionado es incorrecto, favor de validar.",
			icon: "warning",
		});
	}
}

/**
 * Mayusculas
 */
function mayusculas(comp) {
	comp.value = comp.value.toUpperCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "");

}


function editarClasificacion(idClasi) {

	var obtClasificacion = "consultaClasificacion?idClass=";
	limpiarDatosClasificacion();
	url = "";
	url = getAbsolutePath() + obtClasificacion + idClasi;
	peticionClasificacion(url);
	procesoModificacion = 2;
}

function peticionClasificacion(ruta) {
	$.getJSON(url, {}, function(data) {
		modalClass(data);
	});
}
var idClasificacion = 0;
function modalClass(data) {

	document.getElementById("base").value = data.fk_id_base.pk_id_base;
	document.getElementById("nomClasificacion").value = data.vc_nombre_clasificacion;
	document.getElementById("codigoClasificacion").value = data.vc_codigo_clasificacion;
	document.getElementById("vigenciaClasificacion").value = data.i_vigencia_clasificacion;
	document.getElementById("mensajeClasificacion").value = data.vc_mensaje_clasificacion;
	opcionSelect(document.getElementById("abrevacion"), data.vc_abreviacion_clasificacion);
	mostrarMensajeMod();
	idClasificacion = data.pk_id_clasificacion;
	$("#modalClasificacion").modal("show");
}

function opcionSelect(idSelect, datoBuscar) {

	for (var i = 0; i < idSelect.options.length; i++) {
		console.log("texto" + idSelect.options[i].text);
		if (idSelect.options[i].text == datoBuscar) {
			// Asigna el valor de la opción correspondiente al <select>
			idSelect.value = idSelect.options[i].value;
			break; // Puedes detener la iteración una vez que se ha encontrado la coincidencia.
		}
	}
}

function activarClasificacion(idClass) {

	var estatus = $(idClass).attr('for');
	if (estatus == 0) {
		Swal.fire({
			title: "¿Se activara la Clasificación?",
			showCancelButton: true,
			confirmButtonText: "Acepto",
			cancelButtonText: "Cancelar",
		}).then(resultado => {
			if (resultado.value) {
				url = "";
				url = "activarClasificacion?idClass=" + $(idClass).attr('id');
				peticionPost(2, getAbsolutePath() + url);
			}
		});
	} else {
		swal({
			title: "Alerta",
			text: "La Clasificación esta Activa.",
			icon: "warning",
		});
	}
}

function bajaClasificacion(idClass) {
	var estatus = $(idClass).attr('for');
	if (estatus == 1) {
		Swal.fire({
			title: "¿Se inactivara la Clasificación?",
			showCancelButton: true,
			confirmButtonText: "Acepto",
			cancelButtonText: "Cancelar",
		}).then(resultado => {
			if (resultado.value) {
				url = "";
				url = "bajaClasificacion?idClass=" + $(idClass).attr('id');
				peticionPost(2, getAbsolutePath() + url);
			}
		});
	} else {
		swal({
			title: "Alerta ",
			text: "La Clasificación esta inactiva.",
			icon: "warning",
		});
	}

}
var iMensaje = 0;
function mostrarMensaje(idB) {

	var idSelect = document.getElementById($(idB).attr('id'));
	var opcionSeleccionada = idSelect.options[idSelect.selectedIndex];
	iMensaje = opcionSeleccionada.getAttribute("for");

	if (iMensaje == 1) {
		document.getElementById("mnsCampania").style.display = "block";
	} else {
		document.getElementById("mnsCampania").style.display = "none";
	}

	if (iMensaje == 2) {
		document.getElementById("contenidoCheckbox").style.display = "block";
	} else {
		document.getElementById("contenidoCheckbox").style.display = "none";
	}

}

function mostrarCodigo(idCodigo) {
	var idSelect = document.getElementById($(idCodigo).attr('id'));
	var opcionSeleccionada = idSelect.options[idSelect.selectedIndex];
	var codigo = opcionSeleccionada.getAttribute("for");

	document.getElementById("codigoClasificacion").value = codigo;
	console.log("" + codigo);
}

function mostrarMensajeMod() {

	var idSelect = document.getElementById($(document.getElementById("base")).attr('id'));
	var opcionSeleccionada = idSelect.options[idSelect.selectedIndex];
	iMensaje = opcionSeleccionada.getAttribute("for");

	if (iMensaje == 1) {
		document.getElementById("mnsCampania").style.display = "block";
	} else {
		document.getElementById("mnsCampania").style.display = "none";
	}

	if (iMensaje == 2) {
		document.getElementById("contenidoCheckbox").style.display = "block";
	} else {
		document.getElementById("contenidoCheckbox").style.display = "none";
	}

}

/***
 * Cambios fase 2
 */

var cargatblProcesoGestion = "obtProceso";
function cargatblProcesoG(){
 	url = getAbsolutePath() + cargatblProcesoGestion;
 	$.getJSON(url, {}, function(data) {
   	tblProcesoGestion.clear().draw();
   		$.each(data, function(idx, opt) {
			   
				if(opt.i_visible == 1){
					var btnsActualizar = " <button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Parametros' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='editarDatosProceso("
					+ opt.pk_id_gestion
					+ ")' id="
					+ opt.pk_id_gestion
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-tools'></i></button>";
				
					var btnEjecutar= " <button class='btn btn-success' tabindex='0' "
					+ " title='Ejecutar Proceso' aria-controls='table-datatable' type='button'"
					+ " onclick='ejecutarDatosProceso("
					+ opt.pk_id_gestion
					+ ")' id="
					+ opt.pk_id_gestion
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-file-play-fill'></i></button>";

      				tblProcesoGestion.row.add([opt.vc_nombre_proceso, opt.vc_dia_inicio_proceso, opt.vc_dia_fin_proceso,opt.vc_hora_inicio_proceso,opt.vc_hora_fin_proceso,opt.vc_periodo_ejecucion_proceso, btnsActualizar+btnEjecutar]).draw(false);
	
				}
	
		
   		});
  	}); 	
}


var obtDatosProceso = "obtDatosProceso?idBase=";
function editarDatosProceso(idBase){
 limpiarParametros();
 url = getAbsolutePath() + obtDatosProceso + idBase;
 peticionDatosProceso(url);
}

function peticionDatosProceso(url){
 	$.getJSON(url, {}, function(data) {
		modalGestion(data);
	});
}


var procesoGestionID = 0;
function modalGestion(data) {
 	console.log(data);
 	procesoGestionID = data.pk_id_gestion;
 	document.getElementById("dComienzoProceso").value = data.vc_dia_inicio_proceso;
 	document.getElementById("dFinalProceso").value = data.vc_dia_fin_proceso;
 	document.getElementById("hComienzoProceso").value = data.vc_hora_inicio_proceso;
 	document.getElementById("hFinalProceso").value = data.vc_hora_fin_proceso;
 	document.getElementById("mPeriodoEjecucion").value =  data.vc_periodo_ejecucion_proceso;
 	document.getElementById('rutaArchivo').value = "";
 	document.getElementById('lblRuta').style.display = 'none';
    document.getElementById('rutaArchivo').style.display = 'none';
 	
    $("#modalParametros").modal("show");
 }


function ejecutarDatosProceso(idProceso){
	
	Swal.fire({
		title: "¿Esta seguro de ejecutar el proceso manual?",
		showCancelButton: true,
		confirmButtonText: "Acepto",
		cancelButtonText: "Cancelar",
	}).then(resultado => {
			if (resultado.value) {
					url= "";
					url = "ejecutarDatosProceso?idProceso="+idProceso;
					peticionPost(3,getAbsolutePath()+url);
			}
	});

}

