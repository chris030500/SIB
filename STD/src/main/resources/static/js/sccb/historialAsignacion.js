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
				
				var rangoBanda = (res.vc_rango_banda != null) ? res.vc_rango_banda : "";
				var importe = (res.fl_importe != null) ? "$" + res.fl_importe + ".00" : "";
				var saldo = (res.fl_saldo != null) ? "$" + res.fl_saldo : "";
				var atraso = (res.fl_atraso != null) ? "$" + res.fl_atraso : "0";
				var intencionalidad = (res.vc_intencionalidad != null) ? res.vc_intencionalidad : ""; 
				var fechaCartera = (res.dt_fecha_cartera != null) ? res.dt_fecha_cartera : "";
				var enConbranzaJudicial = (res.vc_en_cobranza_judicial != null) ? res.vc_en_cobranza_judicial : "";
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
					+ "<td>" + expedienteId + "</td>"
					+ "<td>" + tipoExpediente + "</td>"
					+ "<td>" + numExpJudicial + "</td>"
					+ "<td>" + negociadorAsignado + "</td>"
					+ "<td>" + estatus + "</td>"
					+ "<td>" + fechaAsigReasig + "</td>"
					+ "<td>" + nomEmpAsigReasig + "</td>"
					+ "<td>" + fechaRegistro + "</td>"
					+ "<td>" + fechaCierre + "</td>"
					+ "<td>" + empleadoCreo + "</td>"
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
				var importe = (res.fl_importe != null) ? "<p>$" + res.fl_importe + "</p>" : "";
				var saldoActual = (res.fl_saldo != null) ? "<p>$" + res.fl_saldo + "</p>" : "";
				var diasMora = (res.i_dias_mora != null) ? "<p>" + res.i_dias_mora + "</p>" : "";
				var inicioCredito = (res.dt_fecha_inicio != null) ? "<p>" + res.dt_fecha_inicio + "</p>" : "";
				var interesNormal = (res.fl_interes_normal != null) ? "<p>" + res.fl_interes_normal + "</p>" : "";
				var banda = (res.fl_interes_normal != null) ? "<p>" + res.vc_rango_banda + "</p>" : "";
				var tasaIntNormal = (res.fl_tasa_normal != null) ? "<p>" + res.fl_tasa_normal + "%</p>" : "";
				var IVAIntNormal = (res.fl_iva != null) ? "<p>" + res.fl_iva + "</p>" : "";
				var facturasImpagadas = (res.i_facturas != null) ? "<p>" + res.i_facturas + "</p>" : "";
				var tasaIntMoratorio = (res.fl_tasa_moratoria != null) ? "<p>" + res.fl_tasa_moratoria + "%</p>" : "";
				var interesMoratorio = (res.fl_interes_morat != null) ? "<p>" + res.fl_interes_morat + "</p>" : "";
				var calificacion = (res.vc_calificacion != null) ? "<p>" + res.vc_calificacion + "</p>" : "";
				var plazo = (res.i_plazo != null) ? "<p>" + res.i_plazo + "</p>" : "";
				var IVAintMoratorio = (res.fl_iva_interes_morat != null) ? "<p>" + res.fl_iva_interes_morat + "</p>" : "";
				var intencionalidad = (res.vc_intencionalidad != null) ? "<p>" + res.vc_intencionalidad + "</p>" : "";
				var frecuenciaPago = (res.vc_frecuencia != null) ? "<p>" + res.vc_frecuencia + "</p>" : "";
				var cmsnPagadas = (res.vc_frecuencia != null) ? "<p>" + res.fl_comision + "</p>" : "";
				var etapa = (res.vc_etapa != null) ? "<p>" + res.vc_etapa + "</p>" : "";
				var vencimiento = (res.dt_fecha_vencimiento != null) ? "<p>" + res.dt_fecha_vencimiento + "</p>" : "";
				var IVAcmsnPag = "<p>0" + "</p>";
				var despacho = (res.vc_despacho != null) ? "<p>" + res.vc_despacho + "</p>" : "";
				var centroCredito = (res.vc_nombre_centro != null) ?  "<p>" + res.vc_nombre_centro + "</p>" : "";
				var gastos = (res.fl_gastos != null) ? "<p>" + res.fl_gastos + "</p>": "";
				var negociadorAsig = (res.vc_negociador != null) ? "<p>" + res.vc_negociador + "</p>" : "";
				var tipoCredito = (res.vc_tipo_credito != null) ? "<p>" + res.vc_tipo_credito + "</p>" : "";
				var atrasoCapital = (res.fl_atraso != null) ? "<p>$" + res.fl_atraso + "</p>" : "";
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