var optionSelected = "";
var url = "";
var tblSolicitudes;

$(document).ready(function() {
	defaultView();
	
	$("#opciones").change(function() {
		optionSelected = $('#opciones').val();
		console.log(optionSelected);
		if(optionSelected > 0) {
			switch(optionSelected) {
				case "1": 
					$("#divCredito").show();
					$('#divCliente').hide();
					$('#divName').hide();
					$('#btnBuscar').show();
					
					
					break;
				case "2":
					$("#divCredito").hide();
					$('#divCliente').show();
					$('#divName').hide();
					$('#btnBuscar').show();
					
					break;
				case "3":
					$("#divCredito").hide();
					$('#divCliente').hide();
					$('#divName').show();
					$('#btnBuscar').show();
					
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
	$('#divCliente').hide();
	$('#divName').hide();
	$('#btnBuscar').hide();
	$('#divTable').hide();
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

function getUrlSolicitudes() {
	var idCredito;
	var noCliente;
	var nomCliente;
	
	switch(optionSelected) {
		case "1": 
			idCredito = $("#creditoBusqueda").val();
			url = getAbsolutePath() + "getByIdCreditoSolicitudes?idCredito=" + Number(idCredito);
			break;
		case "2":
			noCliente = $("#clienteBusqueda").val();
			url = getAbsolutePath() + "getByNoClienteSolicitudes?noCliente=" + Number(noCliente);
			break;
		case "3":	
			nomCliente = $("#selectName").val();
			url = getAbsolutePath() + "getByNomClienteSolicitudes?nomCliente=" + nomCliente;
			break;
	}
	
	return url;
}

function cleanTable() {
	$("#solicitudes > tbody").remove();
}

function buscarDatos() {
	cleanTable();
	var divTable = $('#divTable');
	divTable.hide();
	
	console.log(getUrlSolicitudes());
	
	$.ajax({
		url: getUrlSolicitudes(),
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res.solicitudes);
			if(res == null || res == '') {
				console.log("Error");
				divTable.hide();
			} else {
				divTable.show();
				var tbody = "<tbody></tbody>";
				$("#solicitudes").append(tbody);
				$.each(res.solicitudes, function(idx, opt) {
					var fechaRecepcion = (opt.dt_fecha_recepcion != null) ? opt.dt_fecha_recepcion : "";
					var fechaInicioTramite = (opt.dt_inicio_tramite != null) ? opt.dt_fecha_inicio_tramite : "";
					var fechaAutorizacion = (opt.dt_fecha_autorizacion != null) ? opt.dt_fecha_autorizacion : "";
					var fechaDesembolso = (opt.dt_fecha_desembolso != null) ? opt.dt_fecha_desembolso : "";
					var rows = "<tr>"
						+ "<td style='width: 300px;' onclick='showSolicitud(" + opt.i_solicitud_id + ")'>" + opt.i_solicitud_id + "</td>"
						+ "<td style='width: 300px;'>" + opt.i_credito_id + "</td>"
						+ "<td style='width: 400px;'>" + opt.vc_nombre_cliente + "</td>"
						+ "<td style='width: 300px;'>" + opt.i_no_cliente + "</td>"
						+ "<td style='width: 300px;'>" + opt.vc_rol + "</td>"
						+ "<td style='width: 300px;'>" + opt.vc_nombre_centro + "</td>"
						+ "<td style='width: 300px;'>" + opt.vc_etapa + "</td>"
						+ "<td style='width: 300px;'>" + opt.vc_estatus + "</td>"
						+ "<td style='width: 300px;'>" + "$" + opt.fl_monto + ".00" + "</td>"
						+ "<td style='width: 350px;'>" + fechaRecepcion + "</td>"
						+ "<td style='width: 300px;'>" + fechaInicioTramite + "</td>"
						+ "<td style='width: 300px;'>" + fechaAutorizacion + "</td>"
						+ "<td style='width: 300px;'>" + fechaDesembolso + "</td>"
						+ "</tr>";
					$("#solicitudes > tbody").append(rows);
					
					
				});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
			});
		}
	});	
}

function cleanSolicitud() {
	
	$("#pFolio").remove();
	$("#pAcCredito").remove();
	$("#pAcDepRespaldo").remove();
	$("#pAcOperativa").remove();
	$("#pRecibe").remove();
	$("#pCaptura").remove();
	$("#pFechaRecepcion").remove();
	$("#pEtapaRecepcion").remove();
	$("#pFechaResultado").remove();
	$("#pEstatusResultado").remove();
	$("#pCentro").remove();
	$("#pFechaInicioTramite").remove();
	$("#pFechaAutorizacion").remove();
	$("#pFechaDesembolso").remove();
	$("#pFechaReconsideracion").remove();
	$("#pMontoAvaluo").remove();
	$("#tdRow1").remove();
	$("#tdRow2").remove();
	$("#tdRow3").remove();
	$("#tdRow4").remove();
	$("#pObservaciones").remove();
	$("#pObservacionesCC").remove();
}

function showSolicitud(parametroSolicitud) {
	
	cleanSolicitud();
	
	var divDatosSolicitud = $("#divDatosSolicitud");
	divDatosSolicitud.hide();
	
	console.log(parametroSolicitud);
	url = getAbsolutePath() + "getDatosSolicitud?idSolicitud=" + parametroSolicitud;
	
	console.log(url);
	
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res);
			if(res == null || res == '') {
				console.log("Error");
				divDatosSolicitud.hide();
			} else {
				divDatosSolicitud.show();
				var folio = (res.i_solicitud_id != null) ? "<p id='pFolio'>" + res.i_solicitud_id + "</p>" : "<p id='pFolio'></p>";
				var acCredito = (res.i_credito_id != null) ? "<p id='pAcCredito'>" + res.i_credito_id + "</p>" : "<p id='pAcCredito'></p>";
				var acDepRespaldo = (res.i_ac_dep_respaldo != null) ? "<p id='pAcDepRespaldo'>" + res.i_ac_dep_respaldo + "</p>" : "<p id='pAcDepRespaldo'>0</p>";
				var acOperativa = (res.i_acuerdo_operativa != null) ? "<p id='pAcOperativa'>" + res.i_acuerdo_operativa + "</p>" : "<p id='pAcOperativa'>0</p>";
				var recibe = (res.i_empleado_recibe_id != null) ? "<p id='pRecibe'>" + res.i_empleado_recibe_id + "</p>" : "<p id='pRecibe'>0</p>";
				var captura = (res.i_empleado_captura_id != null) ? "<p id='pCaptura' class='card-text' style='font-weight: bold; font-size: small;'> Captura: " + res.i_empleado_captura_id + "</p>" : "<p id='pCaptura' class='card-text' style='font-weight: bold; font-size: small;'> Captura: 0 </p>"; 
				var fechaRecepcion = (res.dt_fecha_recepcion != null) ? "<p id='pFechaRecepcion'>" + res.dt_fecha_recepcion + "</p>" : "<p id='pFechaRecepcion'></p>";
				var etapaRecepcion = (res.vc_descripcion_etapa != null) ? "<p id='pEtapaRecepcion'>" + res.vc_descripcion_etapa + "</p>" : "<p id='pEtapaRecepcion'></p>";
				var fechaResultado = (res.dt_fecha_resultado != null) ? "<p id='pFechaResultado'>" + res.dt_fecha_resultado + "</p>" : "<p id='pFechaResultado'></p>";
				var estatusResultado = (res.vc_descripcion_estatus != null) ? "<p id='pEstatusResultado'>" + res.vc_descripcion_estatus + "</p>" : "<p id='pEstatusResultado'></p>";
				var centro = (res.i_centro_id != null) ? "<p id='pCentro' class='card-text' style='font-weight: bold; font-size: small;'>Centro: " + res.i_centro_id + " " + res.vc_nombre_centro + "</p>" : "<p id='pCentro' class='card-text' style='font-weight: bold; font-size: small;'>Centro: 0</p>";
				var fechaInicioTramite = (res.dt_fecha_inicio_tramite != null) ? "<p id='pFechaInicioTramite'>" + res.dt_fecha_inicio_tramite + "</p>" : "<p id='pFechaInicioTramite'></p>";
				var fechaAutorizacion = (res.dt_fecha_autorizacion != null) ?  "<p id='pFechaAutorizacion'>" + res.dt_fecha_autorizacion + "</p>" : "<p id='pFechaAutorizacion'></p>";
				var fechaDesembolso = (res.dt_fecha_desembolso != null) ? "<p id='pFechaDesembolso'>" + res.dt_fecha_desembolso + "</p>" : "<p id='pFechaDesembolso'></p>";
				var fechaReconsideracion = (res.dt_fecha_reconsideracion != null) ? "<p id='pFechaReconsideracion'>" + res.dt_fecha_reconsideracion + "</p>" : "<p id='pFechaReconsideracion'></p>";
				var montoAvaluo = (res.i_valor_avaluo != null) ? "<p id='pMontoAvaluo'>" + "$" + res.i_valor_avaluo + "</p>" : "<p id='pMontoAvaluo'></p>";
				var observaciones = (res.vc_observaciones != null) ? "<p id='pObservaciones'>" + res.vc_observaciones + "</p>" : "<p id='pObservaciones'></p>";
				var observacionesCC = (res.vc_observaciones_cc != null) ? "<p id='pObservacionesCC'>" + res.vc_observaciones_cc + "</p>" : "<p id='pObservacionesCC'></p>";
				
				var noCliente = (res.i_no_cliente != null) ? res.i_no_cliente : "";
				var nombreTarifa = (res.vc_nombre_tarifa != null) ? res.vc_nombre_tarifa : "";
				var numeroTantos = (res.i_numero_tantos != null) ? res.i_numero_tantos : "";
				var tasaNormal = (res.fl_tasa_normal != null) ? res.fl_tasa_normal + "%" : "";
				var nombreCompleto = (res.vc_nombre_completo != null) ? res.vc_nombre_completo : "";
				var importeCrediito = (res.i_importe_credito != null) ? "$" + res.i_importe_credito + ".00" : "";
				var depEnRespaldo = (res.i_dep_en_respaldo != null) ? "$" + res.i_dep_en_respaldo + ".00" : "";
				var tasaMoratoria = (res.i_tasa_moratoria != null) ? res.i_tasa_moratoria + "%" : "";
				var descripcionCliente = (res.vc_descripcion_cliente != null) ? res.vc_descripcion_cliente : "";
				var plazo = (res.i_plazo != null) ? res.i_plazo : "";
				var csmnImpCredito = (res.i_cmsn_imp_credito != null) ? res.i_cmsn_imp_credito : "";
				var numAbonosDiferidos = (res.i_num_abonos_diferidos != null) ? res.i_num_abonos_diferidos : "";
				var nombreDestino = (res.vc_nombre_destino != null) ? res.vc_nombre_destino : "";
				var descFrecuencia = (res.vc_descripcion_frecuencia != null) ? res.vc_descripcion_frecuencia : "";
				var cmsnAseFin = (res.fl_cmsn_ase_fin != null) ? res.fl_cmsn_ase_fin : "";
				var revisionVehicular = (res.i_revision_vehicular != null) ? res.i_revision_vehicular : "";
				var analista = (res.i_empleado_analiza_id != null) ? res.i_empleado_analiza_id : "";
				
				var rowDatosDeCredito = 	"<tr id='tdRow1' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Cliente</td>"
								+ "<td style='width: 12.5%;'>" + noCliente + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tarifa</td>"
								+ "<td style='width: 12.5%;'>" + nombreTarifa + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Número de tantos</td>"
								+ "<td style='width: 12.5%;'>" + numeroTantos + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tasa Normal</td>"
								+ "<td style='width: 12.5%;'>" + tasaNormal + "</td>"
							 + "</tr>" 
							 + "<tr id='tdRow2' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Nombre</td>"
								+ "<td style='width: 12.5%;'>" + nombreCompleto + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Monto(####.##)</td>"
								+ "<td style='width: 12.5%;'>" + importeCrediito + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Dep. en Respaldo</td>"
								+ "<td style='width: 12.5%;'>" + depEnRespaldo + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tasa Moratoria</td>"
								+ "<td style='width: 12.5%;'>" + tasaMoratoria + "</td>"
							 + "</tr>" 
							 + "<tr id='tdRow3' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tipo Cliente</td>"
								+ "<td style='width: 12.5%;'>" + descripcionCliente + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Plazo</td>"
								+ "<td style='width: 12.5%;'>" + plazo + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Comisión por impago de Crédito</td>"
								+ "<td style='width: 12.5%;'>" + csmnImpCredito + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Abonos Diferidos</td>"
								+ "<td style='width: 12.5%;'>" + numAbonosDiferidos + "</td>"
							 + "</tr>" 
							  + "<tr id='tdRow4' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Destino</td>"
								+ "<td style='width: 12.5%;'>" + nombreDestino + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Frecuencia</td>"
								+ "<td style='width: 12.5%;'>" + descFrecuencia + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Comisión Asesoría Financiera</td>"
								+ "<td style='width: 12.5%;'>" + cmsnAseFin + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Revisión Vehicular</td>"
								+ "<td style='width: 12.5%;'>" + revisionVehicular + "</td>"
							 + "</tr>"
							 + "<tr id='tdRow4' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Analista</td>"
								+ "<td style='width: 12.5%;'>" + analista + "</td>"
							 + "</tr>";
				
				
				$("#folioSolicitud").append(folio);
				$("#acCredito").append(acCredito);
				$("#acDepRespaldo").append(acDepRespaldo);
				$("#acOperativa").append(acOperativa);
				$("#recibe").append(recibe);
				$("#captura").append(captura);
				$("#fechaRecepcion").append(fechaRecepcion);
				$("#etapaRecepcion").append(etapaRecepcion);
				$("#fechaResultado").append(fechaResultado);
				$("#estatusResultado").append(estatusResultado);
				$("#centro").append(centro);
				$("#fechaInicioTramite").append(fechaInicioTramite);
				$("#fechaAutorizacion").append(fechaAutorizacion);
				$("#analista").append(analista);
				$("#fechaDesembolso").append(fechaDesembolso);
				$("#fechaReconsideracion").append(fechaReconsideracion);
				$("#montoAvaluo").append(montoAvaluo);
		
				$("#tblDatosCredito").append(rowDatosDeCredito);
				$("#observacionesRes").append(observaciones);
				$("#observacionesCC").append(observacionesCC);
				
				
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
			});
		}
	});	
}
