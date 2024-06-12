var expOptionSelected = "";
var searchOptionSelected = "";
var url = "";

$(document).ready(function () {
	
	defaultView();
	
	$("#opcionesTipoExpediente").change(function () {
		
		expOptionSelected = $("#opcionesTipoExpediente").val();
		
		console.log(expOptionSelected);
		
		if (expOptionSelected > 0) {
			
			switch (expOptionSelected) {
				case "1":
					$("#divTipoBusqueda").show();
					$("#divCredito").hide();
					$("#divName").hide();
					$("#btnBuscar").hide();
					$("#btnClean").hide();
					$("#optIdCredito").hide();
					$("#optNombre").show();
					break;
				case "2": 
					$("#divTipoBusqueda").show();
					$("#divCredito").hide();
					$("#divName").hide();
					$("#btnBuscar").hide();
					$("#btnClean").hide();
					$("#optIdCredito").show();
					$("#optNombre").hide();
					break;
			}
		}
	});
	
	$("#opcionesTipoBusqueda").change(function () {
		
		searchOptionSelected = $("#opcionesTipoBusqueda").val();
		
		if (searchOptionSelected > 0) {
			switch (searchOptionSelected) {
				
				case "1" :
					$("#divTipoBusqueda").show();
					$("#divCredito").show();
					$("#divName").hide();
					$("#btnBuscar").show();
					$("#btnClean").show();
					break;
				case "2":
					$("#divTipoBusqueda").show();
					$("#divCredito").hide();
					$("#divName").show();
					$("#btnBuscar").show();
					$("#btnClean").show();
					break;
			}
		}
	});
	
	$('.solo-numero').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function defaultView() {
	$("#divMainTable").hide();
	$("#divTblCredito").hide();
	$("#divTblAfiliacion").hide();
	$("#divDetalleExpCredito").hide();
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function buscarNombre(name) {
	
	var selectName = $("#selectName");
	var option = "";
	
	url = getAbsolutePath() + "getNamesExpedienteAfiliacion?nombre=" + name;
	console.log(name);
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function(res) {
			console.log(res.nombresAfiliacion);
			var nombresAfiliacion = res.nombresAfiliacion;
			console.log(nombresAfiliacion.length);
			for(var i = 0; i < nombresAfiliacion.length; i++) {
				
				option += "<option value='" + nombresAfiliacion[i].nombre_cliente + "'>" + nombresAfiliacion[i].nombre_cliente + "</option>";
				
				$("select#selectName").empty().append(option);
			}
		}
	});
}

function buscarExpedienteCredito() {
	var idCredito = $("#creditoBusqueda").val();
	var divMainTable = $("#divMainTable");
	
	console.log(typeof(idCredito));
	
	url = getAbsolutePath() + "getExpedienteCreditoByIdCredito?idCredito=" + Number(idCredito);
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res);
			if (res == null || res == '') {
				console.log("Error");
				divMainTable.hide();	
			}
			else {
				divMainTable.show();
				$("#divTblCredito").css("display", "block");
				
				var idExpediente = (res.i_expediente_id != null) ? res.i_expediente_id : "";
				var tipoExpediente = (res.vc_tipo_expediente != null) ? res.vc_tipo_expediente : "";
				var fechaCreacion = (res.dt_fecha_creacion != null) ? res.dt_fecha_creacion : "";
				var idSolicitud = (res.i_solicitud_id != null) ? res.i_solicitud_id : "";
				var idCredito = (res.i_credito_id != null) ? res.i_credito_id : "";
				
				var tbody = "<tbody>"
				+ "<tr>"
				+ "<td>" + idExpediente + "</td>"
				+ "<td>" + tipoExpediente + "</td>"
				+ "<td>" + fechaCreacion + "</td>"
				+ "<td>" + idSolicitud + "</td>"
				+ "<td>" + idCredito + "</td>"
				+ "<td>ACTIVO</td>"
				+ "</tr>"
				+ "</tbody>";
				
				$("#credito").append(tbody);
			}
		}
	}); 
}

function getDetalleExpedienteCredito() {
	
	var idCredito = $("#creditoBusqueda").val();
	var divDetalleExpediente = $("#divDetalleExpediente");
	var divTitle = "<p>Detalle de Expediente de Crédito</p>";
	
	$("#titleDetalleExpediente").append(divTitle);
	
	url = getAbsolutePath() + "getDetalleExpedienteCredito?idCredito=" + Number(idCredito);
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function (res) {
			if (res == null || res == '') {
				console.log("Error");
				divDetalleExpediente.hide();
			}
			else {
				console.log(res.imagenes);
				divDetalleExpediente.show();
				$("#divDetalleExpCredito").css("display", "block");
				var tbody = "<tbody id='tbodyDetalleExpediente'></tbody>";
				$("#detalleCredito").append(tbody);
				
				$.each(res.imagenes, function(idx, opt) {
					
					var tipoRequisito = (opt.tipo_requisito != null) ? opt.tipo_requisito : "";
					var tipoComprobante = (opt.tipo_comprobante != null) ? opt.tipo_comprobante : "";
					var estatusEntregado = (opt.estatus_entregado != null) ? opt.estatus_entregado : "";
					var fechaDigitalizacion = (opt.fecha_digitalizacion != null) ? opt.fecha_digitalizacion : "";
					var validoDesde = (opt.valido_desde != null) ? opt.valido_desde : "";
					var validoHasta = (opt.valido_hasta != null) ? opt.valido_hasta : "";
					var estatusDigitalizacion = (opt.estatus_digitalizacion != null) ? opt.estatus_digitalizacion : "";
					var nombreCliente = (opt.nombre_cliente != null) ? opt.nombre_cliente : "";
					var rol = (opt.rol_participante != null) ? opt.rol_participante : "";
					var imagen = opt.imagen.replace(/\{0\}/g,"");
					var imagenUrl = imagen.replace(/\\/g, "\\\\");

					
					var rows = "<tr>" 
					+ "<td><div style='width: 200px;'>"+ tipoRequisito + "</div></td>"
					+ "<td><div style='width: 200px;'>" + tipoComprobante + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusEntregado + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoDesde + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoHasta + "</div></td>"
					+ "<td><div style='width: 70px;'>"+ estatusDigitalizacion + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaDigitalizacion + "</div></td>"
					+ "<td><div style='width: 200px;'>" + nombreCliente + "</div></td>"
					+ "<td><div style='width: 80px;'>" + rol + "</div></td>"
					+ "<td>"
					+ "<div style='width: 70px;'>"
					+ "<button type='button' class='btn btn-secondary' onclick='showImage(\"" + imagenUrl + "\")'>"
					+ "<i class='bi bi-camera'></i>"
					+ "</button>"
					+ "</div>"
					+ "</td>"
					+ "</tr>";
					
					$("#tbodyDetalleExpediente").append(rows);
				});
			}
		}
	});
}

function buscarExpedienteAfiliacion() {
	var nomCliente = $("#selectName").val();
	var divMainTable = $("#divMainTable");
	
	console.log(nomCliente);
	
	url = getAbsolutePath() + "getExpedienteAfiliacionByName?nomCliente=" + nomCliente;
	
		$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			if (res == null || res == '') {
				console.log("Error");
				divMainTable.hide();
			}
			else {
				console.log(res);
				divMainTable.show();
				$("#divTblAfiliacion").css("display", "block");
				
				var idExpediente = (res.i_expediente_id != null) ? res.i_expediente_id : "";
				var tipoExpediente = (res.vc_tipo_expediente != null) ? res.vc_tipo_expediente : "";
				var fechaCreacion = (res.dt_fecha_creacion != null) ? res.dt_fecha_creacion : "";
				
				var tbody = "<tbody>"
				+ "<tr>"
				+ "<td>" + idExpediente + "</td>"
				+ "<td>" + tipoExpediente + "</td>"
				+ "<td>" + fechaCreacion + "</td>"
				+ "<td>ACTIVO</td>"
				+ "</tr>"
				+ "</tbody>";
				
				$("#afiliacion").append(tbody);
			}
		}
	}); 
	
}

function getDetalleExpedienteAfiliacion() {
	
	var nomCliente = $("#selectName").val();
	var divDetalleExpediente = $("#divDetalleExpediente");
	var divTitle = "<p>Detalle de Expediente de Afiliación</p>";
	
	$("#titleDetalleExpediente").append(divTitle);
	
	url = getAbsolutePath() + "getDetalleExpedienteAfiliacion?nomCliente=" + nomCliente;
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			if (res == null || res == '') {
				console.log("Error");
				divDetalleExpediente.hide();
			}
			else {
				divDetalleExpediente.show();
				$("#divDetalleExpAfiliacion").css("display", "block")
				var tbody = "<tbody id='tbodyAfiliacion'></tbody>";
				$("#detalleAfiliacion").append(tbody);
				
				$.each(res.imagenes, function(idx, opt) {
					var tipoRequisito = (opt.vc_tipo_requisito != null) ? opt.vc_tipo_requisito : "";
					var tipoComprobante = (opt.vc_tipo_comprobante != null) ? opt.vc_tipo_comprobante : "";
					var estatusEntregado = (opt.vc_estatus_entregado != null) ? opt.vc_estatus_entregado : "";
					var fechaDigitalizacion = (opt.dt_fecha_digitalizacion != null) ? opt.dt_fecha_digitalizacion : "";
					var validoDesde = (opt.dt_valido_desde != null) ? opt.dt_valido_desde : "";
					var validoHasta = (opt.dt_valido_hasta != null) ? opt.dt_valido_hasta : "";
					var estatusDigitalizacion = (opt.vc_estatus_digitalizacion != null) ? opt.vc_estatus_digitalizacion : "";
					var imagen = opt.vc_imagen.replace(/\{0\}/g,"");
					var imagenUrl = imagen.replace(/\\/g, "\\\\");
					var rows = "<tr>"
					+ "<td><div style='width: 200px;'>" + tipoRequisito + "</div></td>"
					+ "<td><div style='width: 200px;'>" + tipoComprobante + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusEntregado + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoDesde + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoHasta + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusDigitalizacion + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaDigitalizacion + "</div></td>"
					+ "<td>"
					+ "<div style='width: 70px;'>"
					+ "<button type='button' class='btn btn-secondary' onclick='showImage(\"" + imagenUrl + "\")'>"
					+ "<i class='bi bi-camera'></i>"
					+ "</button>"
					+ "</div>"
					+ "</td>"
					+ "</tr>";
					
					$("#tbodyAfiliacion").append(rows);
				});
			}
			
		}
	});
}


function showImage(imagen) {
	
	$("#visorImagenes").modal("show");
	
	$.ajax({
		url: getAbsolutePath() + "getImage?ruta=" + imagen,
		type: "GET",
		xhrFields: {
			responseType: "blob"
		},
		success: function (res) {
			var urlImage = URL.createObjectURL(res);
			
			$("#imagen").attr("src", urlImage);
		},
		error: function () {
			console.log("Error al obtener la imagen");
		}
	});
    
}

function cleanTables() {
	$("#credito > tbody").remove();
	$("#tbodyDetalleExpediente").remove();
	$("#afiliacion > tbody").remove();
	$("#tbodyAfiliacion").remove();
	$("#titleDetalleExpediente > p").remove();
	
	$("#divTblCredito").css("display", "none");
	$("#divTblAfiliacion").css("display", "none");
	$("#divDetalleExpCredito").css("display", "none");
	$("#divDetalleExpAfiliacion").css("display", "none");
	$("#divMainTable").hide();
	$("#divDetalleExpediente").hide();
}

function buscarDatos() {
	
	cleanTables();
	
	if (expOptionSelected == "1") {
		buscarExpedienteAfiliacion();
		getDetalleExpedienteAfiliacion();
	}
	
	if (expOptionSelected == "2") {
		buscarExpedienteCredito();
		getDetalleExpedienteCredito();
	}
}