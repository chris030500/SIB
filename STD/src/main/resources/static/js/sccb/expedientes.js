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
				$("#credito").css("display", "block");
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

function buscarDatos(){
	buscarExpedienteCredito();
}