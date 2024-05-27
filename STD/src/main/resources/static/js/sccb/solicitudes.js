var optionSelected = "";
var url = "";
var tblSolicitudes;

$(document).ready(function() {
	defaultView();
	initDataTable();
	
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
			nomCliente = $("#nameBusqueda").val();
			url = getAbsolutePath() + "getByNomClienteSolicitudes?nomCliente=" + nomCliente;
			break;
	}
	
	return url;
}

function initDataTable() {
		tblSolicitudes = $("#solicitudes").DataTable({
			"language": {
            	"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json",
        	},
			'aoColumnDefs': [{
				'bSortable': false,
				'aTargets': [-1] /* 1st one, start by the right */
			}],
		});
}

function createTableSolicitudes() {
	
	$("#solicitudes").remove();
	
	var tableSolicitudes = 
				"<table class='table table-bordered table-striped table-sm' style='width: 100%' id='solicitudes'>"
					+ "<thead style='background-color: #0D6EFD;'>"
						+ "<tr class='text-white' style='font-size: small; text-align: center;'>"
							+ "<th>Solcitud</th>"
							+ "<th>Acuerdo</th>"
							+ "<th>Nombre</th>"
							+ "<th>Cliente</th>"
							+ "<th>Rol</th>"
							+ "<th>Centro</th>"
							+ "<th>Etapa</th>"
							+ "<th>Estatus</th>"
							+ "<th>Monto</th>"
							+ "<th>Recepción</th>"
							+ "<th>Inicio trámite</th>"
							+ "<th>Autorización</th>"
							+ "<th>Desembolso</th>"
						+ "</tr>"
					+ "</thead>"
					+ "<tbody id='tbodySolicitudes'>"
						
					+ "</tbody>"
				+ "</table>";
				
	$("#divTable").append(tableSolicitudes);
}

function buscarDatos() {
	
	createTableSolicitudes();
	
	var divTable = $('#divTable');
	
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
				tblSolicitudes.clear().draw();
				$.each(res.solicitudes, function(idx, opt) {
					var fechaRecepcion = (opt.dt_fecha_recepcion != null) ? opt.dt_fecha_recepcion : "";
					var fechaInicioTramite = (opt.dt_inicio_tramite != null) ? opt.dt_fecha_inicio_tramite : "";
					var fechaAutorizacion = (opt.dt_fecha_autorizacion != null) ? opt.dt_fecha_autorizacion : "";
					var fechaDesembolso = (opt.dt_fecha_desembolso != null) ? opt.dt_fecha_desembolso : "";
					var rows = "<tr>"
						+ "<td class='text-center' onclick='showSolicitud(" + opt.i_solicitud_id + ")'>" + opt.i_solicitud_id + "</td>"
						+ "<td class='text-center'>" + opt.i_credito_id + "</td>"
						+ "<td class='text-center'>" + opt.vc_nombre_cliente + "</td>"
						+ "<td class='text-center'>" + opt.i_no_cliente + "</td>"
						+ "<td class='text-center'>" + opt.vc_rol + "</td>"
						+ "<td class='text-center'>" + opt.vc_nombre_centro + "</td>"
						+ "<td class='text-center'>" + opt.vc_etapa + "</td>"
						+ "<td class='text-center'>" + opt.vc_estatus + "</td>"
						+ "<td class='text-center'>" + opt.fl_monto + "</td>"
						+ "<td class='text-center'>" + fechaRecepcion + "</td>"
						+ "<td class='text-center'>" + fechaInicioTramite + "</td>"
						+ "<td class='text-center'>" + fechaAutorizacion + "</td>"
						+ "<td class='text-center'>" + fechaDesembolso + "</td>"
						+ "</tr>";
					$('#tbodySolicitudes').append(rows);
					
					
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
	$("#pAnalista").remove();
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
				var acDepRespaldo = (res.i_ac_dep_respaldo != null) ? "<p id='pAcDepRespaldo'>" + res.i_ac_dep_respaldo + "</p>" : "<p id='pAcDepRespaldo'></p>";
				var acOperativa = (res.i_acuerdo_operativa != null) ? "<p id='pAcOperativa'>" + res.i_acuerdo_operativa + "</p>" : "<p id='pAcOperativa'></p>";
				var recibe = (res.i_empleado_recibe_id != null) ? "<p id='pRecibe'>" + res.i_empleado_recibe_id + "</p>" : "<p id='pRecibe'></p>";
				var captura = (res.i_empleado_captura_id != null) ? "<p id='pCaptura' class='card-text' style='font-weight: bold; font-size: small;'> Captura: " + res.i_empleado_captura_id + "</p>" : "<p id='pCaptura' class='card-text' style='font-weight: bold; font-size: small;'> Captura: 0 </p>"; 
				var fechaRecepcion = (res.dt_fecha_recepcion != null) ? "<p id='pFechaRecepcion'>" + res.dt_fecha_recepcion + "</p>" : "<p id='pFechaRecepcion'></p>";
				var etapaRecepcion = (res.vc_descripcion_etapa != null) ? "<p id='pEtapaRecepcion'>" + res.vc_descripcion_etapa + "</p>" : "<p id='pEtapaRecepcion'></p>";
				var fechaResultado = (res.dt_fecha_resultado != null) ? "<p id='pFechaResultado'>" + res.dt_fecha_resultado + "</p>" : "<p id='pFechaResultado'></p>";
				var estatusResultado = (res.vc_descripcion_estatus != null) ? "<p id='pEstatusResultado'>" + res.vc_descripcion_estatus + "</p>" : "<p id='pEstatusResultado'></p>";
				var centro = (res.i_centro_id != null) ? "<p id='pCentro' class='card-text' style='font-weight: bold; font-size: small;'>Centro: " + res.i_centro_id + " " + res.vc_nombre_centro + "</p>" : "<p id='pCentro' class='card-text' style='font-weight: bold; font-size: small;'>Centro: 0</p>";
				var fechaInicioTramite = (res.dt_fecha_inicio_tramite != null) ? "<p id='pFechaInicioTramite'>" + res.dt_fecha_inicio_tramite + "</p>" : "<p id='pFechaInicioTramite'></p>";
				var fechaAutorizacion = (res.dt_fecha_autorizacion != null) ?  "<p id='pFechaAutorizacion'>" + res.dt_fecha_autorizacion + "</p>" : "<p id='pFechaAutorizacion'></p>";
				var analista = (res.i_empleado_analiza_id != null) ? "<p id='pAnalista' class='card-text' style='font-weight: bold; font-size: small;'>Analista: " + res.i_empleado_analiza_id + "</p>" : "<p id='pAnalista' class='card-text' style='font-weight: bold; font-size: small;'>Analista: 0</p>";
				var fechaDesembolso = (res.dt_fecha_desembolso != null) ? "<p id='pFechaDesembolso'>" + res.dt_fecha_desembolso + "</p>" : "<p id='pFechaDesembolso'></p>";
				var fechaReconsideracion = (res.dt_fecha_reconsideracion != null) ? "<p id='pFechaReconsideracion'>" + res.dt_fecha_reconsideracion + "</p>" : "<p id='pFechaReconsideracion'></p>";
				var montoAvaluo = (res.i_valor_avaluo != null) ? "<p id='pMontoAvaluo'>" + res.i_valor_avaluo + "</p>" : "<p id='pMontoAvaluo'></p>";
				var observaciones = (res.vc_observaciones != null) ? "<p id='pObservaciones'>" + res.vc_observaciones + "</p>" : "<p id='pObservaciones'></p>";
				var observacionesCC = (res.vc_observaciones_cc != null) ? "<p id='pObservacionesCC'>" + res.vc_observaciones_cc + "</p>" : "<p id='pObservacionesCC'></p>";
				
				var rowDatosDeCredito = 	"<tr id='tdRow1' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Cliente</td>"
								+ "<td style='width: 12.5%;'>" + res.i_no_cliente + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tarifa</td>"
								+ "<td style='width: 12.5%;'>" + res.vc_nombre_tarifa + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Número de tantos</td>"
								+ "<td style='width: 12.5%;'>" + res.i_numero_tantos + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tasa Normal</td>"
								+ "<td style='width: 12.5%;'>" + res.fl_tasa_normal + "</td>"
							 + "</tr>" 
							 + "<tr id='tdRow2' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Nombre</td>"
								+ "<td style='width: 12.5%;'>" + res.vc_nombre_completo + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Monto(####.##)</td>"
								+ "<td style='width: 12.5%;'>" + res.i_importe_credito + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Dep. en Respaldo</td>"
								+ "<td style='width: 12.5%;'>" + res.i_dep_en_respaldo + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tasa Moratoria</td>"
								+ "<td style='width: 12.5%;'>" + res.i_tasa_moratoria + "</td>"
							 + "</tr>" 
							 + "<tr id='tdRow3' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Tipo Cliente</td>"
								+ "<td style='width: 12.5%;'>" + res.vc_descripcion_cliente + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Plazo</td>"
								+ "<td style='width: 12.5%;'>" + res.i_plazo + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>COmisión por impago de Crédito</td>"
								+ "<td style='width: 12.5%;'>" + res.i_cmsn_imp_credito + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Abonos Diferidos</td>"
								+ "<td style='width: 12.5%;'>" + res.i_num_abonos_diferidos + "</td>"
							 + "</tr>" 
							  + "<tr id='tdRow4' style='font-size: small; text-align: center; word-wrap: break-word;'>" 
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Destino</td>"
								+ "<td style='width: 12.5%;'>" + res.vc_nombre_destino + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Frecuencia</td>"
								+ "<td style='width: 12.5%;'>" + res.vc_descripcion_frecuencia + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>COmisión Asesoría Financiera</td>"
								+ "<td style='width: 12.5%;'>" + res.fl_cmsn_ase_fin + "</td>"
								+ "<td class='text-white' style='background-color: #0D6EFD; font-weight: bold; width: 12.5%;'>Revisión Vehicular</td>"
								+ "<td style='width: 12.5%;'>" + res.i_revision_vehicular + "</td>"
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
