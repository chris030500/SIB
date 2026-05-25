// @ts-nocheck
var optionSelected = "";
var url = "";

$(document).ready(function () {
	$("#opciones").change(function () {
		optionSelected = $("#opciones").val();
		
		if (optionSelected > 0) {
			switch (optionSelected) {
				case "1":
					$("#divCredito").show();
					$("#divCliente").hide();
					$("#divName").hide();
					$("#btnBuscar").show();
					break;
				case "2":
					$("#divCredito").hide();
					$("#divCliente").show();
					$("#divName").hide();
					break;
				case "3":
					$("#divCredito").hide();
					$("#divCliente").hide();
					$("#divName").show();
					break;
			}
			$("#btnBuscar").show();
			$("#btnClean").show();
		} else {
			Swal.fire({
				title: "Error",
				text: "Debe seleccionar una opción de búsqueda.",
				icon: "warning",
				allowOutsideClick: false
			});
		}
	});
	
	$('.solo-numero').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});

function buscarNombre(name) {
	var selectName = $("#selectName");
	var option = "";
	
	url = getAbsolutePath() + "getCostumerNamesPagares?nombre=" + name;
	console.log(name);
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function(res) {
			console.log(res.nombresPagares);
			var nombres = res.nombresPagares;
			console.log(nombres.length);
			if (nombres.length === 0) {
				Swal.fire({
					title: "Error",
					text: "El nombre que ingresó no es válido.",
					icon: "warning",
					allowOutsideClick: false
				});
				cleanView();
			} else {
				for (var i = 0; i < nombres.length; i++) {
					option += "<option value='" + nombres[i].no_cliente + "," + nombres[i].credito_id + "'>" + nombres[i].nombre_cliente + "|" + nombres[i].rol + "|" + nombres[i].credito_id + "</option>";
					$("select#selectName").empty().append(option);
				}
			}
		}
	});
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function getUrl() {
	var select;
	var valueSelect;
	var idCredito;
	var noCliente;
	
	switch (optionSelected) {
		case "1": 
			idCredito = $("#creditoBusqueda").val();
			url = getAbsolutePath() + "getPagaresByIdCredito?idCredito=" + Number(idCredito);
			break;
		case "2":
			noCliente = $("#clienteBusqueda").val();
			url = getAbsolutePath() + "getPagaresByNoCliente?noCliente=" + Number(noCliente);
			break;
		case "3":
			select = $("#selectName").val();
			valueSelect = select.toString().split(',');
			noCliente = valueSelect[0];
			idCredito = valueSelect[1];
			url = getAbsolutePath() + "getPagaresByNoClienteAndIdCredito?noCliente=" + Number(noCliente) + "&idCredito=" + Number(idCredito);
			break;
	}
	
	console.log(url);
	return url;
}

function cleanView() {
	cleanTable();
	$("#creditoBusqueda").val("");
	$("#clienteBusqueda").val("");
	$("#nameBusqueda").val("");
	$("#selectName > option").remove();
}

function cleanTable() {
	$("#divPagares").hide();
	$("#pagares > tbody").remove();
}

function getPagares() {
	var divPagares = $("#divPagares");
	divPagares.hide();
	cleanTable();
	
	$.ajax({
		url: getUrl(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.pagares);
			if (res.pagares.length === 0) {
				console.log("Error");
				divPagares.hide();
				Swal.fire({
					title: "Error",
					text: "No se encuentra el registro de pagaré.",
					icon: "warning",
					allowOutsideClick: false
				});
			} else {
				divPagares.show();
				var tbody = "<tbody></tbody>";
				$("#pagares").append(tbody);
				$.each(res.pagares, function(idx, opt) {
					var tipoDocumento = (opt.vc_tipo_documento != null) ? opt.vc_tipo_documento : "";
					var nombreCentroUbicacion = (opt.vc_nombre_centro_ubicacion != null) ? opt.vc_nombre_centro_ubicacion : "";
					var noCliente = (opt.i_no_cliente != null) ? opt.i_no_cliente : "";
					var idCredito = (opt.i_credito_id != null) ? opt.i_credito_id : "";
					var importeCredito = (opt.fl_importe_credito != null) ? opt.fl_importe_credito.toLocaleString() : "";
					var fechaInicioCredito = (opt.dt_fecha_inicio_credito != null) ? opt.dt_fecha_inicio_credito : "";
					var estatusCredito = (opt.vc_estatus_credito != null) ? opt.vc_estatus_credito : "";
					var fechaEvento = (opt.dt_fecha_evento != null) ? opt.dt_fecha_evento : "";
					var nombreSolicitante = (opt.vc_nombre_solicitante != null) ? opt.vc_nombre_solicitante : ""; 
					var nombreCentroSolicitante = (opt.vc_nombre_centro_solicitante != null) ? opt.vc_nombre_centro_solicitante : "";
					var nombreDestino = (opt.vc_nombre_destino != null) ? opt.vc_nombre_destino : "";
					var nombreAsignado = (opt.vc_nombre_asignado != null) ? opt.vc_nombre_asignado : "";
					var etapaCustodia = (opt.vc_etapa_custodia != null) ? opt.vc_etapa_custodia : "";
					var tipoDevolucion = (opt.vc_tipo_devolucion != null) ? opt.vc_tipo_devolucion : "";
					
					var rows = "<tr>"
					+ "<td><div style='width: 120px;'>" + tipoDocumento + "</div></td>"
					+ "<td><div style='width: 120px;'>" + nombreCentroUbicacion + "</div></td>"
					+ "<td><div style='width: 70px;'>" + noCliente + "</div></td>"
					+ "<td><div style='width: 70px;'>" + idCredito + "</div></td>"
					+ "<td><div style='width: 100px;'>" + "$" + importeCredito + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaInicioCredito + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusCredito + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaEvento + "</div></td>"
					+ "<td><div style='width: 200px;'>" + nombreSolicitante + "</div></td>"
					+ "<td><div style='width: 120px;'>" + nombreCentroSolicitante + "</div></td>"
					+ "<td><div style='width: 200px;'>" + nombreDestino + "</div></td>"
					+ "<td><div style='width: 200px;'>" + nombreAsignado + "</div></td>"
					+ "<td><div style='width: 100px;'>" + etapaCustodia + "</div></td>"
					+ "<td><div style='width: 100px;'>" + tipoDevolucion + "</div></td>"
					+ "</tr>";
				
					$("#pagares > tbody").append(rows);
				});
			}
		}, 
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			Swal.fire({
				title: "Error",
				text: "Se presentó un error, intente más tarde.",
				icon: "warning",
				allowOutsideClick: false
			});
		}
	});
}

function buscarDatos() {
	if (optionSelected === "1") {
		if ($("#creditoBusqueda").val() != "") {
			getPagares();
		}
		if ($("#creditoBusqueda").val() === "") {
			Swal.fire({
				title: "Error",
				text: "Debe ingresar un no. de crédito.",
				icon: "warning",
				allowOutsideClick: false
			});
		}
	}
	if (optionSelected === "2") {
		if ($("#clienteBusqueda").val() != "") {
			getPagares();
		}
		if ($("#clienteBusqueda").val() === "") {
			Swal.fire({
				title: "Error",
				text: "Debe ingresar un no. de cliente.",
				icon: "warning",
				allowOutsideClick: false
			});
		}
	}
	if (optionSelected === "3") {
		if ($("#nameBusqueda").val() != "") {
			getPagares();
		}
		if ($("#nameBusqueda").val() === "") {
			Swal.fire({
				title: "Error",
				text: "Debe ingresar un nombre.",
				icon: "warning",
				allowOutsideClick: false
			});
		}
	}
}