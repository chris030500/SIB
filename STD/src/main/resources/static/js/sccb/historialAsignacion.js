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

function getUrlDatosGenerales() {
	var idCredito;
	
	switch (optionSelected) {
		case "1":
			idCredito = $("#creditoBusqueda").val();
			break;
		case "2":
			idCredito = $("#selectName").val();	
			break;
		}
		
		url = getAbsolutePath() + "getByIdCreditoDatosGenerales?idCredito=" + Number(idCredito);
	
	return url;
}

function buscarDatosGenerales() {
	var divDatosGenerales = $("#divTableGenerales");
	
	$.ajax({
		url: getUrlDatosGenerales(),
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res);
			
			if($.isEmptyObject(res)) {
				console.log("Error");
				divDatosGenerales.hide();
			}
			else {
				divDatosGenerales.show();
				var tbody = "<tbody id='tbodyDatosGenerales'>" + "</tbody>";
				$("#datosGenerales").append(tbody);
				
				var idCredito = (res.CREDITO_ID != null) ? res.CREDITO_ID : "";
				var rangoBanda = (res.RANGO_BANDA != null) ? res.RANGO_BANDA : "";
				var importe = (res.IMPORTE != null) ? "$" + res.IMPORTE.toLocaleString() : "";
				var saldo = (res.SALDO != null && res.SALDO > 0) ? "$" + res.SALDO.toLocaleString() : "0";
				var atraso = (res.ATRASO != null && res.ATRASO > 0) ? "$" + res.ATRASO.toLocaleString() : "0";
				var intencionalidad = (res.INTENCION != null) ? res.INTENCION : ""; 
				var fechaCartera = (res.FECHA_CARTERA != null) ? res.FECHA_CARTERA : "";
				var enConbranzaJudicial = (res.CJ != null) ? res.CJ : "";
				var adeudoTotal = "0";
				
				var row =  "<tr>" 
					+ "<td>" + idCredito + "</td>"
					+ "<td>" + rangoBanda + "</td>"
					+ "<td>" + importe + "</td>"
					+ "<td>" + saldo + "</td>"
					+ "<td>" + atraso + "</td>"
					+ "<td>" + adeudoTotal + "</td>"
					+ "<td>" + fechaCartera + "</td>"
					+ "<td>" + intencionalidad + "</td>"
					+ "<td>" + enConbranzaJudicial + "</td>"
					+ "</tr>";
					
					$("#tbodyDatosGenerales").append(row);
			}
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			divDatosGenerales.hide();
			swal({
				title: "Error, ",
						text: "No se encuentra en Cobranza.",
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
	
	$.ajax({
		url: getUrlParticipantes(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.participantes);
			
			if (res.participantes.length === 0) {
				console.log("Error");
				divParticipantes.hide();
			}
			else {
				divParticipantes.show();
				var tbody = "<tbody id='tbodyParticipantes'></tbody>";
				$("#participantes").append(tbody);
				
				$.each(res.participantes, function (idx, opt) {
					var noCliente = (opt.i_no_cliente != null) ? opt.i_no_cliente : "";
					var nombreCiente = (opt.vc_nombre_cliente != null) ? opt.vc_nombre_cliente : "";
					var rolParticipante = (opt.vc_rol_participante != null) ? opt.vc_rol_participante : "";
					var tipologiaCliente = (opt.vc_tipologia_cliente != null) ? opt.vc_tipologia_cliente : "";
					
					var row = "<tr>"
					+ "<td>" + noCliente + "</td>"
					+ "<td>" + nombreCiente + "</td>"
					+ "<td>" + rolParticipante + "</td>"
					+ "<td>" + tipologiaCliente + "</td>"
					+ "</tr>";
					
					$("#tbodyParticipantes").append(row);
				});
				
			}
		},	
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			divParticipantes.hide();
			swal({
				title: "Error, ",
						text: "No se encuentra en Cobranza.",
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

	$.ajax({
		url: getUrlExpedientesNegociacion(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.historialAsig);
			divExpedienteNegociacion.show();
			
			if (res.historialAsig.length === 0) {
				console.log("Error");
				divExpedienteNegociacion.hide();
			}
			else { 
				divExpedienteNegociacion.show();
				var tbody = "<tbody id='tbodyExpedientesNegociacion'></tbody>";
				$("#expedientesNegociacion").append(tbody);
				$.each(res.historialAsig, function (idx, opt) {
					var expedienteId = (opt.i_expediente_id != null) ? opt.i_expediente_id : "";
					var tipoExpediente = (opt.vc_tipo_expediente != null) ? opt.vc_tipo_expediente : "";
					var numExpJudicial = (opt.vc_num_exp_judicial != null) ? opt.vc_num_exp_judicial : "";
					var negociadorAsignado = (opt.i_negociador_asignado != null && opt.vc_nombre_negociador != null) ? opt.i_negociador_asignado + " - " + opt.vc_nombre_negociador  : "";
					var estatus = (opt.vc_estatus != null) ? opt.vc_estatus : "";
					var fechaAsigReasig = (opt.dt_fecha_asig_reasig != null) ? opt.dt_fecha_asig_reasig : "";
					var nomEmpAsigReasig = (opt.vc_nom_emp_asig_reasig != null) ? opt.vc_nom_emp_asig_reasig : "";
					var fechaRegistro = (opt.dt_fecha_registro != null) ? opt.dt_fecha_registro : "";
					var fechaCierre = (opt.dt_fecha_cierre != null) ? opt.dt_fecha_cierre : "";
					var empleadoCreo = (opt.i_empleado_creo != null && opt.vc_nombre_creador != null) ? opt.i_empleado_creo + " - " + opt.vc_nombre_creador : "";
					
					var row = "<tr>"
					+ "<td><div style='width: 80px;'>" + expedienteId + "</div></td>"
					+ "<td><div style='width: 120px;'>" + tipoExpediente + "</div></td>"
					+ "<td><div style='width: 80px;'>" + numExpJudicial + "</div></td>"
					+ "<td><div style='width: 200px;'>" + negociadorAsignado + "</div></td>"
					+ "<td><div style='width: 80px;'>" + estatus + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaAsigReasig + "</div></td>"
					+ "<td><div style='width: 200px;'>" + nomEmpAsigReasig + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaRegistro + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaCierre + "</div></td>"
					+ "<td><div style='width: 200px;'>" + empleadoCreo + "</div></td>"
					+ "</tr>";
					
					$("#tbodyExpedientesNegociacion").append(row);
				});
			}
			
		}, 	
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			divExpedienteNegociacion.hide();
			swal({
				title: "Error, ",
						text: "No se encuentra en Cobranza.",
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
	
	$.ajax({
		url: getUrlDetalleExp(),
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res);
			if ($.isEmptyObject(res)) {
				console.log("Error");
				divExpDetalle.hide();
			}
			else {
				var noExp = "<p>Expediente(s) de Negociación #" + res.i_expediente_id + "</p>";
				$("#noExpediente").append(noExp);
				divExpDetalle.show();
				var importe = (res.fl_importe != null) ? "<p>$" + res.fl_importe.toLocaleString() + "</p>" : "";
				var saldoActual = (res.fl_saldo != null && res.fl_saldo > 0) ? "<p>$" + res.fl_saldo.toLocaleString() + "</p>" : "0";
				var diasMora = (res.i_dias_mora != null) ? "<p>" + res.i_dias_mora + "</p>" : "";
				var inicioCredito = (res.dt_fecha_inicio != null) ? "<p>" + res.dt_fecha_inicio + "</p>" : "";
				var interesNormal = (res.fl_interes_normal != null && res.fl_interes_normal > 0) ? "<p>$" + res.fl_interes_normal.toLocaleString() + "</p>" : "0";
				var banda = (res.fl_interes_normal != null) ? "<p>" + res.vc_rango_banda + "</p>" : "";
				var tasaIntNormal = (res.fl_tasa_normal != null) ? "<p>" + res.fl_tasa_normal + "%</p>" : "";
				var IVAIntNormal = (res.fl_iva != null && res.fl_iva > 0) ? "<p>$" + res.fl_iva.toLocaleString() + "</p>" : "0";
				var facturasImpagadas = (res.i_facturas != null) ? "<p>" + res.i_facturas + "</p>" : "";
				var tasaIntMoratorio = (res.fl_tasa_moratoria != null) ? "<p>" + res.fl_tasa_moratoria + "%</p>" : "";
				var interesMoratorio = (res.fl_interes_morat != null && res.fl_interes_morat > 0) ? "<p>$" + res.fl_interes_morat.toLocaleString() + "</p>" : "0";
				var calificacion = (res.vc_calificacion != null) ? "<p>" + res.vc_calificacion + "</p>" : "";
				var plazo = (res.i_plazo != null) ? "<p>" + res.i_plazo + "</p>" : "";
				var IVAintMoratorio = (res.fl_iva_interes_morat != null && res.fl_iva_interes_morat > 0) ? "<p>$" + res.fl_iva_interes_morat.toLocaleString() + "</p>" : "0";
				var intencionalidad = (res.vc_intencionalidad != null) ? "<p>" + res.vc_intencionalidad + "</p>" : "";
				var frecuenciaPago = (res.vc_frecuencia != null) ? "<p>" + res.vc_frecuencia + "</p>" : "";
				var cmsnPagadas = (res.vc_frecuencia != null) ? "<p>" + res.fl_comision + "</p>" : "";
				var etapa = (res.vc_etapa != null) ? "<p>" + res.vc_etapa + "</p>" : "";
				var vencimiento = (res.dt_fecha_vencimiento != null) ? "<p>" + res.dt_fecha_vencimiento + "</p>" : "";
				var IVAcmsnPag = "<p>0" + "</p>";
				var despacho = (res.vc_despacho != null) ? "<p>" + res.vc_despacho + "</p>" : "";
				var centroCredito = (res.vc_nombre_centro != null) ?  "<p>" + res.vc_nombre_centro + "</p>" : "";
				var gastos = (res.fl_gastos != null && res.fl_gastos > 0) ? "<p>$" + res.fl_gastos.toLocaleString() + "</p>": "0";
				var negociadorAsig = (res.vc_negociador != null) ? "<p>" + res.vc_negociador + "</p>" : "";
				var tipoCredito = (res.vc_tipo_credito != null) ? "<p>" + res.vc_tipo_credito + "</p>" : "";
				var atrasoCapital = (res.fl_atraso != null && res.fl_atraso > 0) ? "<p>$" + res.fl_atraso.toLocaleString() + "</p>" : "0";
				var analista = (res.vc_nom_analista != null) ? "<p>" + res.vc_nom_analista + "</p>" : "";
				var tarifa = (res.vc_tarifa != null) ? "<p>" + res.vc_tarifa + "</p>" : "";
				var atrasoTotal = "<p>0" + "</p>"
				var ultMovto = (res.dt_fecha_ult_movto != null) ? "<p>" + res.dt_fecha_ult_movto + "</p>" : "";
				
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
			divExpDetalle.hide();
			swal({
				title: "Error, ",
						text: "No se encuentra en Cobranza.",
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