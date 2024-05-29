var optionSelected = "";
var url = "";

$(document).ready(function() {
	
	defaultView();
	
	$("#opciones").change(function() {
		optionSelected = $("#opciones").val();
		
		if (optionSelected > 0) {
			switch (optionSelected) {
				case "1":
					$("#divCredito").show();
					$("#divName").hide();
					$("#btnBuscar").show();
					break;
				
				case "2":
					$("#divCredito").hide();
					$("#divName").show();
					$("#btnBuscar").show();
					break;	
			}
		} else {
			swal({
				title: "Error",
				text: "Debe seleccionar una opción de búsqueda.",
				icon: "warning",
			});
		}
	});
	
	$('.solo-numero').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
	
});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function defaultView() {
	$("#divCredito").hide();
	$("#divName").hide();
	$("#btnBuscar").hide();
	$("#divTableGenerales").hide();
	$("#divParticipantes").hide();
	$("#divExpedientesNegociacion").hide();
	$("#divExpedienteDetalle").hide();
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
				
				option += "<option value='" + participantes[i].i_credito_id + "'>" + participantes[i].vc_nombre_cliente + "|" + participantes[i].vc_rol_participante + "|" + participantes[i].i_credito_id + "</option>";
				
				$("select#selectName").empty().append(option);
			}
		}
	});
	
}

function getUrlDatosGenerales(idCredito) {
	
	switch (optionSelected) {
		case "1":
			idCredito = $("#creditoBusqueda").val();
			break;
		case "2":
			idCredito = $("#selectName").val();	
			break;
		}
		
		url = getAbsolutePath() + "getByIdCreditoDetalleExpediente?idCredito=" + Number(idCredito);
	
	return url;
}

function buscarDatosGenerales() {
	
	var idCredito;
	var divDatosGenerales = $("#divTableGenerales");
	
	if (optionSelected == "1") {
		idCredito = $("#creditoBusqueda").val();
	}
	if (optionSelected == "2") {
		idCredito = $("#selectName").val();
	}
	
	console.log(idCredito);
	
	$.ajax({
		url: getUrlDatosGenerales(idCredito),
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res);
			
			if(res == null || res == '') {
				console.log("Error");
				divDatosGenerales.hide();
			}
			else {
				divDatosGenerales.show();
				var tbody = "<tbody id='tbodyDatosGenerales'>" + "</tbody>";
				$("#datosGenerales").append(tbody);
				var row =  "<tr>" 
					+ "<td>" + idCredito + "</td>"
					+ "<td>" + res.vc_rango_banda + "</td>"
					+ "<td>" + res.fl_importe + "</td>"
					+ "<td>" + res.fl_saldo + "</td>"
					+ "<td>" + res.fl_atraso + "</td>"
					+ "<td>" + 0.00 + "</td>"
					+ "<td>" + res.dt_fecha_cartera + "</td>"
					+ "<td>No</td>"
					+ "<td>" + res.vc_en_cobranza_judicial + "</td>"
					+ "</tr>";
					
					$("#tbodyDatosGenerales").append(row);
			}
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
			});
		}
	});
}

function getUrlParticipantes() {
	var idCredito;
	if (optionSelected > 0) {
		switch (optionSelected) {
			case "1":
				idCredito = $("#creditoBusqueda").val();
			break;
			
			case "2":
				idCredito = $("#selectName").val();
				break;
		}
		console.log(idCredito);
		url = getAbsolutePath() + "getByIdCreditoParticipantes?idCredito=" + Number(idCredito);
	}
	
	return url;
}

function buscarParticipantes() {
	var divParticipantes = $("#divParticipantes");
	divParticipantes.hide();
	
	$.ajax({
		url: getUrlParticipantes(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.participantes);
			
			if (res == null || res == '') {
				console.log("Error");
				divParticipantes.hide();
			}
			else {
				divParticipantes.show();
				var tbody = "<tbody id='tbodyParticipantes'></tbody>";
				$("#participantes").append(tbody);
				
				$.each(res.participantes, function (idx, opt) {
					var row = "<tr>"
					+ "<td>" + opt.i_no_cliente + "</td>"
					+ "<td>" + opt.vc_nombre_cliente + "</td>"
					+ "<td>" + opt.vc_rol_participante + "</td>"
					+ "<td>" + opt.vc_tipologia_cliente + "</td>"
					+ "</tr>";
					
					$("#tbodyParticipantes").append(row);
				});
				
			}
		},	
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
			});
		}
	});
}

function getUrlExpedientesNegociacion() {
	var idCredito;
	if (optionSelected > 0) {
		switch (optionSelected) {
			case "1":
				idCredito = $("#creditoBusqueda").val();
				break;
			
			case "2": 
				idCredito = $("#selectName").val();
				break;
		}
		url = getAbsolutePath() + "getByIdCreditoHistorialAsignacion?idCredito=" + Number(idCredito);
	}
	
	return url;
}

function buscarExpedientesNegociacion() {
	var divExpedienteNegociacion = $("#divExpedientesNegociacion");
	divExpedienteNegociacion.hide();

	$.ajax({
		url: getUrlExpedientesNegociacion(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.historialAsig);
			divExpedienteNegociacion.show();
			
			if (res == null || res == '') {
				console.log("Error");
				divExpedienteNegociacion.hide();
			}
			else { 
				divExpedienteNegociacion.show();
				var tbody = "<tbody id='tbodyExpedientesNegociacion'></tbody>";
				$("#expedientesNegociacion").append(tbody);
				$.each(res.historialAsig, function (idx, opt) {
					var row = "<tr>"
					+ "<td>" + opt.i_expediente_id + "</td>"
					+ "<td>" + opt.vc_tipo_expediente + "</td>"
					+ "<td>" + opt.vc_num_exp_judicial + "</td>"
					+ "<td>" + opt.i_negociador_asignado + " - " + opt.vc_nombre_negociador + "</td>"
					+ "<td>" + opt.vc_estatus + "</td>"
					+ "<td>" + opt.dt_fecha_asig_reasig + "</td>"
					+ "<td>" + opt.vc_nom_emp_asig_reasig + "</td>"
					+ "<td>" + opt.dt_fecha_registro + "</td>"
					+ "<td>" + opt.dt_fecha_cierre + "</td>"
					+ "<td>" + opt.i_empleado_creo + " - " + opt.vc_nombre_creador + "</td>"
					+ "</tr>";
					
					$("#tbodyExpedientesNegociacion").append(row);
				});
			}
			
		}, 	
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
			});
		}
		
	});
}

function cleanDetailsExp() {
	$("#tdImporte > p").remove();
	$("#tdSaldoActual > p").remove();
	$("#tdDiasMora > p").remove();
	$("#tdFechaInicio > p").remove();
	$("#tdInteresNormal > p").remove();
	$("#tdBanda > p").remove();
	$("#tdTasaInteresNormal > p").remove();
	$("#tdIVAInteresNormal > p").remove();
	$("#tdFacturasImpaagadas > p").remove();
	$("#tdTasaIntMoratorio > p").remove();
	$("#tdIntMoratorio > p").remove();
	$("#tdCalificacion > p").remove();
	$("#tdPlazo > p").remove();
	$("#tdIVAIntMoratorio > p").remove();
	$("#tdIntencionalidad > p").remove();
	$("#tdFrecuenciaPago > p").remove();
	$("#tdCmsnPagadas > p").remove();
	$("#tdEtapa > p").remove();
	$("#tdVencimiento > p").remove();
	$("#tdIvaCmsnPag > p").remove();
	$("#tdDespacho > p").remove();
	$("#tdCentroCredito > p").remove();
	$("#tdGastos > p").remove();
	$("#tdNegociador > p").remove();
	$("#tdTipoCredito > p").remove();
	$("#tdAtrasoCapital > p").remove();
	$("#tdAnalista > p").remove();
	$("#tdTarifa > p").remove();
	$("#tdAtrasoTotal > p").remove();
	$("#tdUltimoMovto > p").remove();
	$("#noExpediente > p").remove();
}

function getUrlDetalleExp() {
	var idCredito;
	if (optionSelected > 0) {
		switch (optionSelected) {
			case "1":
				idCredito = $("#creditoBusqueda").val();
				break;
			
			case "2": 
				idCredito = $("#selectName").val();
				break;
		}
		url = getAbsolutePath() + "getByIdCreditoDetalleExpediente?idCredito=" + Number(idCredito);
	}
	
	return url;
}

function showDetalleExpediente() {
	var divExpDetalle = $("#divExpedienteDetalle");
	divExpDetalle.hide();
	
	$.ajax({
		url: getUrlDetalleExp(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res);
			if (res == null || res =='') {
				console.log("Error");
				divExpDetalle.hide();
			}
			else {
				var noExp = "<p>Expediente(S) de Negociación #" + res.i_expediente_id + "</p>";
				$("#noExpediente").append(noExp);
				divExpDetalle.show();
				var importe = "<p>" + res.fl_importe + "</p>";
				var saldoActual = "<p>" + res.fl_saldo + "</p>";
				var diasMora = "<p>" + res.i_dias_mora + "</p>";
				var inicioCredito = "<p>" + res.dt_fecha_inicio + "</p>";
				var interesNormal = "<p>" + res.fl_interes_normal + "</p>";;
				var banda = "<p>" + res.vc_rango_banda + "</p>";
				var tasaIntNormal = "<p>" + res.fl_tasa_normal + "</p>";
				var IVAIntNormal = "<p>" + res.fl_iva + "</p>";
				var facturasImpagadas = "<p>" + res.i_facturas + "</p>";
				var tasaIntMoratorio = "<p>" + res.fl_tasa_moratoria + "</p>";
				var interesMoratorio = "<p>" + res.fl_interes_morat + "</p>";
				var calificacion = "<p>" + res.vc_calificacion + "</p>";
				var plazo = "<p>" + res.i_plazo + "</p>";
				var IVAintMoratorio = "<p>" + res.fl_iva_interes_morat + "</p>";
				var intencionalidad = "<p>" + res.vc_intencionalidad + "</p>";
				var frecuenciaPago = "<p>" + res.vc_frecuencia + "</p>";
				var cmsnPagadas = "<p>" + res.fl_comision + "</p>";
				var etapa = "<p>" + res.vc_etapa + "</p>";
				var vencimiento = "<p>" + res.dt_fecha_vencimiento + "</p>";
				var IVAcmsnPag = "<p>" + "</p>";
				var despacho = "<p>" + res.vc_despacho + "</p>";;
				var centroCredito = "<p>" + res.vc_nombre_centro + "</p>";
				var gastos = "<p>" + res.fl_gastos + "</p>";;
				var negociadorAsig = "<p>" + res.vc_negociador + "</p>";
				var tipoCredito = "<p>" + res.vc_tipo_credito + "</p>";
				var atrasoCapital = "<p>" + res.fl_atraso + "</p>";
				var analista = "<p>" + res.vc_nom_analista + "</p>";
				var tarifa = "<p>" + res.vc_tarifa + "</p>";
				var atrasoTotal = "<p>" + "</p>"
				var ultMovto = "<p>" + res.dt_fecha_ult_movto + "</p>";
				
				$("#tdImporte").append(importe);
				$("#tdSaldoActual").append(saldoActual);
				$("#tdDiasMora").append(diasMora);
				$("#tdFechaInicio").append(inicioCredito);
				$("#tdInteresNormal").append(interesNormal);
				$("#tdBanda").append(banda);
				$("#tdTasaInteresNormal").append(tasaIntNormal);
				$("#tdIVAInteresNormal").append(IVAIntNormal);
				$("#tdFacturasImpaagadas").append(facturasImpagadas);
				$("#tdTasaIntMoratorio").append(tasaIntMoratorio);
				$("#tdIntMoratorio").append(interesMoratorio);
				$("#tdCalificacion").append(calificacion);
				$("#tdPlazo").append(plazo);
				$("#tdIVAIntMoratorio").append(IVAintMoratorio);
				$("#tdIntencionalidad").append(intencionalidad);
				$("#tdFrecuenciaPago").append(frecuenciaPago);
				$("#tdCmsnPagadas").append(cmsnPagadas);
				$("#tdEtapa").append(etapa);
				$("#tdVencimiento").append(vencimiento);
				$("#tdIvaCmsnPag").append(IVAcmsnPag);
				$("#tdDespacho").append(despacho);
				$("#tdCentroCredito").append(centroCredito);
				$("#tdGastos").append(gastos);
				$("#tdNegociador").append(negociadorAsig);
				$("#tdTipoCredito").append(tipoCredito);
				$("#tdAtrasoCapital").append(atrasoCapital);
				$("#tdAnalista").append(analista);
				$("#tdTarifa").append(tarifa);
				$("#tdAtrasoTotal").append(atrasoTotal);
				$("#tdUltimoMovto").append(ultMovto);
			}
		}, 	
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
			});
		}
	});

	
}

function cleanTables() {
	
	$("#divTableGenerales").hide();
	$("#divParticipantes").hide();
	$("#divExpedientesNegociacion").hide();
	$("#divExpedienteDetalle").hide();
	$("#tbodyDatosGenerales").remove();
	$("#tbodyParticipantes").remove();
	$("#tbodyExpedientesNegociacion").remove();
	cleanDetailsExp();
}

function buscarDatos() {
	cleanTables();
	buscarDatosGenerales();
	showDetalleExpediente();
	buscarParticipantes();
	buscarExpedientesNegociacion();
}