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
					$("#optIdCredito").hide();
					$("#optNombre").show();
					break;
				case "2": 
					$("#divTipoBusqueda").show();
					$("#divCredito").hide();
					$("#divName").hide();
					$("#btnBuscar").hide();
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
					break;
				case "2":
					$("#divTipoBusqueda").show();
					$("#divCredito").hide();
					$("#divName").show();
					$("#btnBuscar").show();
					break;
			}
		}
	});
});

function defaultView() {
	$("#divMainTable").hide();
	$("#divTblCredito").hide();
	$("#divTblAfiliacion").hide();
	$("#divDetalleExpediente").hide();
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function buscarNombre(name) {
	
	var selectName = $("#selectName");
	var option = "";
	
	url = getAbsolutePath() + "getByNameParticipantes?nombre=" + name;
	console.log(name);
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function(res) {
			console.log(res.participantes);
			var participantes = res.participantes;
			console.log(participantes.length);
			for(var i = 0; i < participantes.length; i++) {
				
				option += "<option value='" + participantes[i].vc_nombre_cliente + "'>" + participantes[i].vc_nombre_cliente + "|" + participantes[i].vc_rol_participante + "|" + participantes[i].i_credito_id + "</option>";
				
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
				+ "<td></td>"
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
				var tbody = "<tbody id='tbodyDetalleExpediente'></tbody>";
				$("#detalleExpediente").append(tbody);
				
				$.each(res.imagenes, function(idx, opt) {
					
					var tipoRequisito = (opt.vc_tipo_requisito != null) ? opt.vc_tipo_requisito : "";
					var tipoComprobante = (opt.vc_tipo_comprobante != null) ? opt.vc_tipo_comprobante : "";
					var estatusEntregado = (opt.vc_estatus_entregado != null) ? opt.vc_estatus_entregado : "";
					var fechaDigitalizacion = (opt.dt_fecha_digitalizacion != null) ? opt.dt_fecha_digitalizacion : "";
					var validoDesde = (opt.dt_valido_desde != null) ? opt.dt_valido_desde : "";
					
					var rows = "<tr>" 
					+ "<td>"+ tipoRequisito + "</td>"
					+ "<td>" + tipoComprobante + "</td>"
					+ "<td>" + estatusEntregado + "</td>"
					+ "<td>" + fechaDigitalizacion + "</td>"
					+ "<td>" + validoDesde + "</td>"
					+ "<td></td>"
					+ "<td></td>"
					+ "<td></td>"
					+ "<td> <i class='bi bi-camera'></i> </td>"
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
				+ "<td></td>"
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
				var tbody = "<tbody id='tbodyAfiliacion'></tbody>";
				$("#detalleExpediente").append(tbody);
				
				$.each(res.imagenes, function(idx, opt) {
					var tipoRequisito = (opt.vc_tipo_requisito != null) ? opt.vc_tipo_requisito : "";
					var tipoComprobante = (opt.vc_tipo_comprobante != null) ? opt.vc_tipo_comprobante : "";
					var estatusEntregado = (opt.vc_estatus_entregado != null) ? opt.vc_estatus_entregado : "";
					var fechaDigitalizacion = (opt.dt_fecha_digitalizacion != null) ? opt.dt_fecha_digitalizacion : "";
					var validoDesde = (opt.dt_valido_desde != null) ? opt.dt_valido_desde : "";
					
					var rows = "<tr>"
					+ "<td>" + tipoRequisito + "</td>"
					+ "<td>" + tipoComprobante + "</td>"
					+ "<td>" + estatusEntregado + "</td>"
					+ "<td>" + fechaDigitalizacion + "</td>"
					+ "<td>" + validoDesde + "</td>"
					+ "<td></td>"
					+ "<td></td>"
					+ "<td></td>"
					+ "<td><i class='bi bi-camera'></i> </td>"
					+ "</tr>";
					
					$("#tbodyAfiliacion").append(rows);
				});
			}
			
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