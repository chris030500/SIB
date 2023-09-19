/**
 * Metodos a ejecutar en la pantalla de Ofertas Automaticas.
 */
//inicio de la pagina
$(document).ready(function() {
	btnsModal();
	eventcontNomClas();
	eventcontAbrevia();
	eventmensajeClasificacion();
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
function iniciaTblParametros(){
 	url = getAbsolutePath() + cargatblParametros;
 	$.getJSON(url, {}, function(data) {
   	tblConfiguracion.clear().draw();
   		$.each(data, function(idx, opt) {
      		var btnsActualizar = " <button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
				+ " title='Actualizar Parametros' data-bs-target='#' aria-controls='table-datatable' type='button'"
				+ " onclick='editarParametros("
				+ opt.pk_id_base
				+ ")' id="
				+ opt.pk_id_base
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-tools'></i></button>";
				
			var btnEjecutar= " <button class='btn btn-success' tabindex='0' "
				+ " title='Ejecutar Proceso' aria-controls='table-datatable' type='button'"
				+ " onclick='ejecutarProceso("
				+ opt.pk_id_base
				+ ")' id="
				+ opt.pk_id_base
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-file-play-fill'></i></button>";
      		
      		tblConfiguracion.row.add([opt.vc_nombre_base, opt.vc_dia_inicio_proceso, opt.vc_dia_fin_proceso,opt.vc_hora_inicio_proceso,opt.vc_hora_fin_proceso,opt.vc_periodo_ejecucion_proceso,opt.vc_ruta_proceso, btnsActualizar,btnEjecutar]).draw(false);

   		});
  	});
 	
}

var obtClasificacion = "obtClasificacion";
function iniciaTblClasificacion(){
	 url = getAbsolutePath() + obtClasificacion;
	 $.getJSON(url, {}, function(data) {
   		tblclasificacionBaseTable.clear().draw();
   		$.each(data, function(idx, opt) {
			var btnsEditar = " <button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
				+ " title='Editar' data-bs-target='#' aria-controls='table-datatable' type='button'"
				+ " onclick='editarClasificacion("
				+ opt.pk_id_clasificacion
				+ ")' id="
				+ opt.pk_id_clasificacion
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-pencil-square'></i></button>";
				
			var btnsActivar = " <button class='btn btn-success' tabindex='0' data-bs-toggle='tooltip'"
				+ " title='Activar Clasificacion' aria-controls='table-datatable' type='button' data-tooltip='Refresh'"
				+ " onclick='altaClasificacion("
				+ opt.pk_id_clasificacion
				+ ")' id="
				+ opt.pk_id_clasificacion
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-check2-circle'></i></button>";
			
			
			var btnBaja= " <button class='btn btn-warning' tabindex='0' data-bs-toggle='tooltip'"
				+ " title='Baja Clasificacion' aria-controls='table-datatable' type='button' data-tooltip='Refresh'"
				+ " onclick='bajaClasificacion("
				+ opt.pk_id_clasificacion
				+ ")' id="
				+ opt.pk_id_clasificacion
				+ " data-tooltip='Add'>"
				+ " <i class='bi bi-x-circle'></i></button>";
				
			var todosBtns = "<div class='dt-buttons' style='text-align: center;'>"
			+ btnsEditar
			+ btnsActivar
			+ btnBaja
			+ "</div>" 		
				
				
				tblclasificacionBaseTable.row.add([opt.fk_id_base.vc_nombre_base,opt.vc_nombre_clasificacion ,opt.vc_abreviacion_clasificacion
				,opt.vc_codigo_clasificacion,opt.i_vigencia_clasificacion,(opt.i_estatus_clasificacion==1)?'ACTIVO':'INACTIVO', todosBtns]).draw(false);

				   
		});
   	 });
}




var obtParametros = "consultaParametros?idBase=";
function editarParametros(idBase){
 limpiarParametros();
 url = getAbsolutePath() + obtParametros + idBase;
 peticionParametros(url);
}

function peticionParametros(url){
 	$.getJSON(url, {}, function(data) {
		modalParametros(data);
	});
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
 	document.getElementById("mPeriodoEjecucion").value =  data.vc_periodo_ejecucion_proceso;
 	
 	if(data.pk_id_base == idBaseFidelidad){
  		document.getElementById('lblRuta').style.display = 'block';
   		document.getElementById('rutaArchivo').style.display = 'block';
   		document.getElementById("rutaArchivo").value =  data.vc_ruta_proceso;
  	}else{
    	document.getElementById('lblRuta').style.display = 'none';
    	document.getElementById('rutaArchivo').style.display = 'none';
   }
   $("#modalParametros").modal("show");
 }

function limpiarParametros(){
 	document.getElementById("dComienzoProceso").value = "";
 	document.getElementById("dFinalProceso").value = "";
 	document.getElementById("hComienzoProceso").value = "";
 	document.getElementById("hFinalProceso").value = "";
 	document.getElementById("mPeriodoEjecucion").value =  "";
 	document.getElementById("rutaArchivo").value =  "";
}
var url = "";
function btnsModal(){
 	$("#btnCancelar").click(function() {
   		basesID = 0;
		limpiarParametros();
		$("#modalParametros").modal("hide");
	});
	
	$("#btnCerrar").click(function() {
   		limpiarDatosClasificacion();
   		$("#modalClasificacion").modal("hide");
	});
	
	
	$("#btnActualizarParams").click(function() {
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
 	});
 	
 	$("#btnRegistrarClasif").click(function() {
  		altaDtosClasificacion();
 	});
 	
}

function validarDatosParams(){
   var correcto = 0;

   if(document.getElementById("dComienzoProceso").value.length > 0 && document.getElementById("dComienzoProceso").value > 0){
   		url = url + "&dComienzoProceso="+ encodeURIComponent(document.getElementById('dComienzoProceso').value);
   		document.getElementById("dComienzoProceso").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("dComienzoProceso").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("dFinalProceso").value.length > 0 && document.getElementById("dFinalProceso").value > 0){
   		url = url + "&dFinalProceso="+ encodeURIComponent(document.getElementById('dFinalProceso').value);
   		document.getElementById("dFinalProceso").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("dFinalProceso").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("hComienzoProceso").value.length > 0){
   		url = url + "&hComienzoProceso="+ document.getElementById('hComienzoProceso').value;
   		document.getElementById("hComienzoProceso").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("hComienzoProceso").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("hFinalProceso").value.length > 0 ){
   		url = url + "&hFinalProceso="+ document.getElementById('hFinalProceso').value;
   		document.getElementById("hFinalProceso").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("hFinalProceso").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("mPeriodoEjecucion").value.length > 0 && document.getElementById("mPeriodoEjecucion").value > 0){
   		url = url + "&mPeriodoEjecucion="+ encodeURIComponent(document.getElementById('mPeriodoEjecucion').value);
   		document.getElementById("mPeriodoEjecucion").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("mPeriodoEjecucion").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   
   if(basesID == idBaseFidelidad){
    	if(document.getElementById("rutaArchivo").value.length > 0){
   			url = url + "&rutaArchivo="+ encodeURIComponent(document.getElementById('rutaArchivo').value);
   			document.getElementById("rutaArchivo").style.borderColor = "";
   			correcto = correcto + 0;
   		}else{
    		document.getElementById("rutaArchivo").style.borderColor = "#F70707";
    		correcto = correcto + 1;
   		}
   }else{
    	url = url + "&rutaArchivo="+ encodeURIComponent(document.getElementById('rutaArchivo').value);
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

function peticionPost(metodo,metodoUrl){
 		$.ajax({
		url: metodoUrl,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 100);
		},
		success: function(respuesta) {
   			url = "";
			swal({
				title: "",
				text: respuesta[0],
				icon: "success",
				confirmButtonText: 'Aceptar'
			}).then(resp => {

				if (metodo == 1) {
     				iniciaTblParametros();
     				limpiarParametros();
		
				}else if(metodo == 2){
					iniciaTblClasificacion();
					limpiarDatosClasificacion();
				}
				setTimeout($.unblockUI, 100);
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

//clasificacion 

function limpiarDatosClasificacion(){
	
	document.getElementById("base").value = 0;
	document.getElementById("nomClasificacion").value = '';
	document.getElementById("abrevacion").value = '';
	document.getElementById("codigoClasificacion").value = '';
	document.getElementById("vigenciaClasificacion").value = 1;
	document.getElementById("mensajeClasificacion").value = '';
	document.getElementById("mensajeClasificacion").style.display = "none";
	document.getElementById("contMns").style.display = "none";
	$("#confMensaje").prop('checked', false);

	
}

function checkMns(){

	if( $('#confMensaje').prop('checked') ) {
			eventmensajeClasificacion(); 
			document.getElementById("mensajeClasificacion").value = '';
			document.getElementById("mensajeClasificacion").style.display = "block";
			document.getElementById("contMns").style.display = "block";
	}else{
			eventmensajeClasificacion();
			document.getElementById("mensajeClasificacion").value = '';
			document.getElementById("mensajeClasificacion").style.display = "none";
			document.getElementById("contMns").style.display = "none";
	}
}

//altaClasificacion
function validarVacioClasificacion(){
	var correcto = 0;
	url = "";
	if(document.getElementById("base").value > 0){
   		url = "altaClasificacion?idBase=" + encodeURIComponent(document.getElementById("base").value);
   		document.getElementById("base").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("base").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   
   if(document.getElementById("nomClasificacion").value.length > 0 ){
   		url = url + "&nomClasificacion="+ encodeURIComponent(document.getElementById("nomClasificacion").value);
   		document.getElementById("nomClasificacion").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("nomClasificacion").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("abrevacion").value.length > 0 ){
   		url = url + "&abrevacion="+ encodeURIComponent(document.getElementById("abrevacion").value);
   		document.getElementById("abrevacion").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("abrevacion").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("codigoClasificacion").value.length > 0 ){
   		url = url + "&codigoClasificacion="+ encodeURIComponent(document.getElementById("codigoClasificacion").value);
   		document.getElementById("codigoClasificacion").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("codigoClasificacion").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   if(document.getElementById("vigenciaClasificacion").value > 0 ){
   		url = url + "&vigenciaClasificacion="+ encodeURIComponent(document.getElementById("vigenciaClasificacion").value);
   		document.getElementById("vigenciaClasificacion").style.borderColor = "";
   		correcto = correcto + 0;
   }else{
    	document.getElementById("vigenciaClasificacion").style.borderColor = "#F70707";
    	correcto = correcto + 1;
   }
   
   if( $('#confMensaje').prop('checked') ) {
	   
	   if(document.getElementById("mensajeClasificacion").value.length > 0){
		   	url = url + "&mensajeClasificacion="+ encodeURIComponent(document.getElementById("mensajeClasificacion").value);
   			document.getElementById("mensajeClasificacion").style.borderColor = "";
   			correcto = correcto + 0;
	   }else{
		   	document.getElementById("mensajeClasificacion").style.borderColor = "#F70707";
    		correcto = correcto + 1;  
	   }
   }else{
		url = url + "&mensajeClasificacion="+ encodeURIComponent(document.getElementById("mensajeClasificacion").value);
   		document.getElementById("mensajeClasificacion").style.borderColor = "";
   		correcto = correcto + 0;   
   }
   	
   return correcto;
	
}

function altaDtosClasificacion(){
	
	if(validarVacioClasificacion() == 0){
		console.log(getAbsolutePath()+url);
		$("#modalClasificacion").modal("hide");
		peticionPost(2,getAbsolutePath()+url);
	}else{
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
function mayusculas(comp){
	comp.value = comp.value.toUpperCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").trim();
	
}


function editarClasificacion(idClasi){
	var obtClasificacion = "consultaClasificacion?idClass=";
	limpiarDatosClasificacion();
	url="";
	url = getAbsolutePath() + obtClasificacion + idClasi;
	peticionClasificacion(url);
}

function peticionClasificacion(ruta){
	 $.getJSON(url, {}, function(data) {
		modalClass(data);
	});
}
var idClasificacion=0;
function modalClass(data){
				   
	
	document.getElementById("base").value = data.fk_id_base.pk_id_base;
	document.getElementById("nomClasificacion").value = data.vc_nombre_clasificacion;
	document.getElementById("abrevacion").value = data.vc_abreviacion_clasificacion;
	document.getElementById("codigoClasificacion").value = data.vc_codigo_clasificacion;
	document.getElementById("vigenciaClasificacion").value = data.i_vigencia_clasificacion;
	document.getElementById("mensajeClasificacion").value = data.vc_mensaje_clasificacion;
	idClasificacion=data.pk_id_clasificacion;
	
	$("#modalClasificacion").modal("show");
}



