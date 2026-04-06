// @ts-nocheck
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
					$("#btnClean").show();
					break;
				
				case "2":
					$("#divCredito").hide();
					$("#divName").show();
					$("#btnBuscar").show();
					$("#btnClean").show();
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
			if (participantes.length === 0) {
				swal({
					title: "Error, ",
					text: "El nombre que ingreso no es válido.",
					icon: "warning",
				});
				cleanScreen();
			}
			else {
				for(var i = 0; i < participantes.length; i++) {
				
					option += "<option value='" + participantes[i].i_credito_id + "'>" + participantes[i].vc_nombre_cliente + "|" + participantes[i].vc_rol_participante + "|" + participantes[i].i_credito_id + "</option>";
				
					$("select#selectName").empty().append(option);
				
				}
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
				var adeudoTotal = (res.ADEUDO_TOTAL != null && res.ADEUDO_TOTAL > 0) ? "$" + res.ADEUDO_TOTAL.toLocaleString() : "0";
				
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
	$("#tdImporte div > p").remove();
	$("#tdSaldoActual div > p").remove();
	$("#tdDiasMora div > p").remove();
	$("#tdFechaInicio div > p").remove();
	$("#tdInteresNormal div > p").remove();
	$("#tdBanda div > p").remove();
	$("#tdTasaInteresNormal div > p").remove();
	$("#tdIVAInteresNormal div > p").remove();
	$("#tdFacturasImpaagadas div > p").remove();
	$("#tdTasaIntMoratorio div > p").remove();
	$("#tdIntMoratorio div > p").remove();
	$("#tdCalificacion div > p").remove();
	$("#tdPlazo div > p").remove();
	$("#tdIVAIntMoratorio div > p").remove();
	$("#tdIntencionalidad div > p").remove();
	$("#tdFrecuenciaPago div > p").remove();
	$("#tdCmsnPagadas div > p").remove();
	$("#tdEtapa div > p").remove();
	$("#tdVencimiento div > p").remove();
	$("#tdIvaCmsnPag div > p").remove();
	$("#tdDespacho div > p").remove();
	$("#tdCentroCredito div > p").remove();
	$("#tdGastos div > p").remove();
	$("#tdNegociador div > p").remove();
	$("#tdTipoCredito div > p").remove();
	$("#tdAtrasoCapital div > p").remove();
	$("#tdAnalista div > p").remove();
	$("#tdTarifa div > p").remove();
	$("#tdAtrasoTotal div > p").remove();
	$("#tdUltimoMovto div > p").remove();
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
				var noExp = (res.i_expediente_id != null) ? "<p>Expediente(s) de Negociación #" + res.i_expediente_id + "</p>" : "<p>Expediente(s) de Negociación #0</p>";
				$("#noExpediente").append(noExp);
				divExpDetalle.show();
				var importe = (res.fl_importe != null) ? "<p>$" + res.fl_importe.toLocaleString() + "</p>" : "<p></p>";
				var saldoActual = (res.fl_saldo != null && res.fl_saldo > 0) ? "<p>$" + res.fl_saldo.toLocaleString() + "</p>" : "<p>0</p>";
				var diasMora = (res.i_dias_mora != null) ? "<p>" + res.i_dias_mora + "</p>" : "<p></p>";
				var inicioCredito = (res.dt_fecha_inicio != null) ? "<p>" + res.dt_fecha_inicio + "</p>" : "<p></p>";
				var interesNormal = (res.fl_interes_normal != null && res.fl_interes_normal > 0) ? "<p>$" + res.fl_interes_normal.toLocaleString() + "</p>" : "<p>0</p>";
				var banda = (res.vc_rango_banda != null) ? "<p>" + res.vc_rango_banda + "</p>" : "<p></p>";
				var tasaIntNormal = (res.fl_tasa_normal != null) ? "<p>" + res.fl_tasa_normal + "%</p>" : "<p></p>";
				var IVAIntNormal = (res.fl_iva != null && res.fl_iva > 0) ? "<p>$" + res.fl_iva.toLocaleString() + "</p>" : "<p>0</p>";
				var facturasImpagadas = (res.i_facturas != null) ? "<p>" + res.i_facturas + "</p>" : "<p></p>";
				var tasaIntMoratorio = (res.fl_tasa_moratoria != null) ? "<p>" + res.fl_tasa_moratoria + "%</p>" : "<p></p>";
				var interesMoratorio = (res.fl_interes_morat != null && res.fl_interes_morat > 0) ? "<p>$" + res.fl_interes_morat.toLocaleString() + "</p>" : "<p>0</p>";
				var calificacion = (res.vc_calificacion != null) ? "<p>" + res.vc_calificacion + "</p>" : "<p></p>";
				var plazo = (res.i_plazo != null) ? "<p>" + res.i_plazo + "</p>" : "<p></p>";
				var IVAintMoratorio = (res.fl_iva_interes_morat != null && res.fl_iva_interes_morat > 0) ? "<p>$" + res.fl_iva_interes_morat.toLocaleString() + "</p>" : "<p>0</p>";
				var intencionalidad = (res.vc_intencionalidad != null) ? "<p>" + res.vc_intencionalidad + "</p>" : "<p></p>";
				var frecuenciaPago = (res.vc_frecuencia != null) ? "<p>" + res.vc_frecuencia + "</p>" : "<p></p>";
				var cmsnPagadas = (res.vc_frecuencia != null) ? "<p>" + res.fl_comision + "</p>" : "<p></p>";
				var etapa = (res.vc_etapa != null) ? "<p>" + res.vc_etapa + "</p>" : "<p></p>";
				var vencimiento = (res.dt_fecha_vencimiento != null) ? "<p>" + res.dt_fecha_vencimiento + "</p>" : "<p></p>";
				var IVAcmsnPag = "<p>0" + "</p>";
				var despacho = (res.vc_despacho != null) ? "<p>" + res.vc_despacho + "</p>" : "<p></p>";
				var centroCredito = (res.vc_nombre_centro != null) ?  "<p>" + res.vc_nombre_centro + "</p>" : "<p></p>";
				var gastos = (res.fl_gastos != null && res.fl_gastos > 0) ? "<p>$" + res.fl_gastos.toLocaleString() + "</p>": "<p>0</p>";
				var negociadorAsig = (res.vc_negociador != null) ? "<p>" + res.vc_negociador + "</p>" : "<p></p>";
				var tipoCredito = (res.vc_tipo_credito != null) ? "<p>" + res.vc_tipo_credito + "</p>" : "<p></p>";
				var atrasoCapital = (res.fl_atraso != null && res.fl_atraso > 0) ? "<p>$" + res.fl_atraso.toLocaleString() + "</p>" : "<p>0</p>";
				var analista = (res.vc_nom_analista != null) ? "<p>" + res.vc_nom_analista + "</p>" : "<p></p>";
				var tarifa = (res.vc_tarifa != null) ? "<p>" + res.vc_tarifa + "</p>" : "<p></p>";
				var atrasoTotal = (res.fl_atraso != null && res.fl_atraso > 0) ? "<p>$" + res.fl_atraso.toLocaleString() + "</p>" : "<p>0</p>";
				var ultMovto = (res.dt_fecha_ult_movto != null) ? "<p>" + res.dt_fecha_ult_movto + "</p>" : "<p></p>";
				
				$("#tdImporte > div").append(importe);
				$("#tdSaldoActual > div").append(saldoActual);
				$("#tdDiasMora > div").append(diasMora);
				$("#tdFechaInicio > div").append(inicioCredito);
				$("#tdInteresNormal > div").append(interesNormal);
				$("#tdBanda > div").append(banda);
				$("#tdTasaInteresNormal > div").append(tasaIntNormal);
				$("#tdIVAInteresNormal > div").append(IVAIntNormal);
				$("#tdFacturasImpaagadas > div").append(facturasImpagadas);
				$("#tdTasaIntMoratorio > div").append(tasaIntMoratorio);
				$("#tdIntMoratorio > div").append(interesMoratorio);
				$("#tdCalificacion > div").append(calificacion);
				$("#tdPlazo > div").append(plazo);
				$("#tdIVAIntMoratorio > div").append(IVAintMoratorio);
				$("#tdIntencionalidad > div").append(intencionalidad);
				$("#tdFrecuenciaPago > div").append(frecuenciaPago);
				$("#tdCmsnPagadas > div").append(cmsnPagadas);
				$("#tdEtapa > div").append(etapa);
				$("#tdVencimiento > div").append(vencimiento);
				$("#tdIvaCmsnPag > div").append(IVAcmsnPag);
				$("#tdDespacho > div").append(despacho);
				$("#tdCentroCredito > div").append(centroCredito);
				$("#tdGastos > div").append(gastos);
				$("#tdNegociador > div").append(negociadorAsig);
				$("#tdTipoCredito > div").append(tipoCredito);
				$("#tdAtrasoCapital > div").append(atrasoCapital);
				$("#tdAnalista > div").append(analista);
				$("#tdTarifa > div").append(tarifa);
				$("#tdAtrasoTotal > div").append(atrasoTotal);
				$("#tdUltimoMovto > div").append(ultMovto);
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

function cleanScreen() {
	cleanTables();
	$("#creditoBusqueda").val("");
	$("#nameBusqueda").val("");
	$("#selectName > option").remove();
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
	if (optionSelected === "1") {
		if ($("#creditoBusqueda").val() != "") {
			cleanTables();
			buscarDatosGenerales();
			showDetalleExpediente();
			buscarParticipantes();
			buscarExpedientesNegociacion();	
		}
		if ($("#creditoBusqueda").val() === "") {
			swal({
				title: "Error, ",
				text: "Debe de ingresar un no. de crédito.",
				icon: "warning",
			});
		}
	}
	
	if (optionSelected === "2") {
		if ($("#nameBusqueda").val() != "") {
			cleanTables();
			buscarDatosGenerales();
			showDetalleExpediente();
			buscarParticipantes();
			buscarExpedientesNegociacion();	
		}
		if ($("#nameBusqueda").val() === "") {
			swal({
				title: "Error, ",
				text: "Debe de ingresar un nombre.",
				icon: "warning",
			});
		}
	}
}