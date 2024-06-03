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
					break;
				case "2": 
					$("#divTipoBusqueda").show();
					$("#divCredito").hide();
					$("#divName").hide();
					$("#btnBuscar").hide();
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
				var tbody = "<tbody>"
				+ "<tr>"
				+ "<td>" + res.i_expediente_id + "</td>"
				+ "<td>" + res.vc_tipo_expediente + "</td>"
				+ "<td>" + res.dt_fecha_creacion + "</td>"
				+ "<td>" + res.i_solicitud_id + "</td>"
				+ "<td>" + res.i_credito_id + "</td>"
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
	var divTitle = "Detalle de Expediente de Crédito";
	
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
					var rows = "<tr>" 
					+ "<td>"+ opt.vc_tipo_requisito + "</td>"
					+ "<td>" + opt.vc_tipo_comprobante + "</td>"
					+ "<td>" + opt.vc_estatus_entregado + "</td>"
					+ "<td>" + opt.dt_fecha_digitalizacion + "</td>"
					+ "<td>" + opt.dt_valido_desde + "</td>"
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
				var tbody = "<tbody>"
				+ "<tr>"
				+ "<td>" + res.i_expediente_id + "</td>"
				+ "<td>" + res.vc_tipo_expediente + "</td>"
				+ "<td>" + res.dt_fecha_creacion + "</td>"
				+ "<td></td>"
				+ "</tr>"
				+ "</tbody>";
				
				$("#afiliacion").append(tbody);
			}
		}
	}); 
	
}

function buscarDatos(){
	
	if (expOptionSelected == "1") {
		buscarExpedienteAfiliacion();
	}
	
	if (expOptionSelected == "2") {
		buscarExpedienteCredito();
		getDetalleExpedienteCredito();
	}
}