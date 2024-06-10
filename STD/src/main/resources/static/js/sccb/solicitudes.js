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
					$('#btnClean').show();
					
					
					break;
				case "2":
					$("#divCredito").hide();
					$('#divCliente').show();
					$('#divName').hide();
					$('#btnBuscar').show();
					$('#btnClean').show();
					
					break;
				case "3":
					$("#divCredito").hide();
					$('#divCliente').hide();
					$('#divName').show();
					$('#btnBuscar').show();
					$('#btnClean').show();
					
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
				
				option += "<option value='" + participantes[i].vc_nombre_cliente+ "," + participantes[i].i_credito_id + "'>" + participantes[i].vc_nombre_cliente + "|" + participantes[i].vc_rol_participante + "|" + participantes[i].i_credito_id + "</option>";
				
				$("select#selectName").empty().append(option);
			}
		}
	});
}

function getUrlSolicitudes() {
	var idCredito;
	var noCliente;
	var nomCliente;
	var select;
	var valuesSelect;
	
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
			select = $("#selectName").val();
			valuesSelect = select.toString().split(',');
			nomCliente = valuesSelect[0];
			idCredito = valuesSelect[1];
			
			console.log(nomCliente);
			console.log(idCredito);
			
			url = getAbsolutePath() + "getByNomClienteSolicitudes?nomCliente=" + nomCliente + "&idCredito=" + Number(idCredito);
			break;
	}
	
	return url;
}

function cleanView() {
	cleanTable();
	cleanSolicitud();
	
	$("#divTable").hide();
	$("#divDatosSolicitud").hide();
}

function cleanTable() {
	$("#solicitudes > tbody").remove();
}

function buscarDatos() {
	cleanTable();
	var divTable = $('#divTable');
	var divDatosSolicitud = $("#divDatosSolicitud");
	divTable.hide();
	divDatosSolicitud.hide();
	
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
					var fechaRecepcion = (opt.fecha_recepcion != null) ? opt.fecha_recepcion : "";
					var fechaInicioTramite = (opt.fecha_inicio_tramite != null) ? opt.fecha_inicio_tramite : "";
					var fechaAutorizacion = (opt.fecha_autorizacion != null) ? opt.fecha_autorizacion : "";
					var fechaDesembolso = (opt.fecha_desembolso != null) ? opt.fecha_desembolso : "";
					var rows = "<tr>"
						+ "<td onclick='showSolicitud(" + opt.solicitud_id + ")'><div style='width: 80px;'>" + opt.solicitud_id + "</div></td>"
						+ "<td><div style='width: 70px;'>" + opt.credito_id + "</div></td>"
						+ "<td><div style='width: 200px;'>" + opt.nombre_cliente + "</div></td>"
						+ "<td><div style='width: 70px;'>" + opt.no_cliente + "</div></td>"
						+ "<td><div style='width: 70px;'>" + opt.rol + "</div></td>"
						+ "<td><div style='width: 100px;'>" + opt.nombre_centro + "</div></td>"
						+ "<td><div style='width: 70px;'>" + opt.etapa + "</div></td>"
						+ "<td><div style='width: 70px;'>" + opt.estatus + "</div></td>"
						+ "<td><div style='width: 100px;'>" + "$" +  opt.monto.toLocaleString() + "</div></td>"
						+ "<td><div style='width: 100px;'>" + fechaRecepcion + "</div></td>"
						+ "<td><div style='width: 100px;'>" + fechaInicioTramite + "</div></td>"
						+ "<td><div style='width: 100px;'>" + fechaAutorizacion + "</div></td>"
						+ "<td><div style='width: 100px;'>" + fechaDesembolso + "</div></td>"
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
	$("#tdRow5").remove();
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
				var folio = (res.solicitud_id != null) ? "<p id='pFolio'>" + res.solicitud_id + "</p>" : "<p id='pFolio'></p>";
				var acCredito = (res.credito_id != null) ? "<p id='pAcCredito'>" + res.credito_id + "</p>" : "<p id='pAcCredito'></p>";
				var acDepRespaldo = (res.ac_dep_respaldo != null) ? "<p id='pAcDepRespaldo'>" + res.ac_dep_respaldo + "</p>" : "<p id='pAcDepRespaldo'>0</p>";
				var acOperativa = (res.acuerdo_operativa != null) ? "<p id='pAcOperativa'>" + res.acuerdo_operativa + "</p>" : "<p id='pAcOperativa'>0</p>";
				var recibe = (res.nombre_empleado_recibe != null) ? "<p id='pRecibe'>" + res.nombre_empleado_recibe + "</p>" : "<p id='pRecibe'>0</p>";
				var captura = (res.nombre_empleado_captura != null) ? "<p id='pCaptura' class='card-text' style='font-weight: bold; font-size: small;'> Captura: " + res.nombre_empleado_captura + "</p>" : "<p id='pCaptura' class='card-text' style='font-weight: bold; font-size: small;'> Captura: 0 </p>"; 
				var fechaRecepcion = (res.fecha_recepcion != null) ? "<p id='pFechaRecepcion'>" + res.fecha_recepcion + "</p>" : "<p id='pFechaRecepcion'></p>";
				var etapaRecepcion = (res.desc_etapa != null) ? "<p id='pEtapaRecepcion'>" + res.desc_etapa + "</p>" : "<p id='pEtapaRecepcion'></p>";
				var fechaResultado = (res.fecha_resultado != null) ? "<p id='pFechaResultado'>" + res.fecha_resultado + "</p>" : "<p id='pFechaResultado'></p>";
				var estatusResultado = (res.desc_estatus != null) ? "<p id='pEstatusResultado'>" + res.desc_estatus + "</p>" : "<p id='pEstatusResultado'></p>";
				var centro = (res.centro_id != null) ? "<p id='pCentro' class='card-text' style='font-weight: bold; font-size: small;'>Centro: " + res.centro_id + " " + res.nombre_centro + "</p>" : "<p id='pCentro' class='card-text' style='font-weight: bold; font-size: small;'>Centro: 0</p>";
				var fechaInicioTramite = (res.fecha_inicio_tramite != null) ? "<p id='pFechaInicioTramite'>" + res.fecha_inicio_tramite + "</p>" : "<p id='pFechaInicioTramite'></p>";
				var fechaAutorizacion = (res.fecha_autorizacion != null) ?  "<p id='pFechaAutorizacion'>" + res.fecha_autorizacion + "</p>" : "<p id='pFechaAutorizacion'></p>";
				var fechaDesembolso = (res.fecha_desembolso != null) ? "<p id='pFechaDesembolso'>" + res.fecha_desembolso + "</p>" : "<p id='pFechaDesembolso'></p>"
				var fechaReconsideracion = (res.fecha_reconsideracion != null) ? "<p id='pFechaReconsideracion'>" + res.fecha_reconsideracion + "</p>" : "<p id='pFechaReconsideracion'></p>";
				var montoAvaluo = (res.valor_avaluo != null && res.valor_avaluo > 0) ? "<p id='pMontoAvaluo'>" + "$" + res.valor_avaluo + "</p>" : "<p id='pMontoAvaluo'>0</p>";
				var observaciones = (res.observaciones != null) ? "<p id='pObservaciones'>" + res.observaciones + "</p>" : "<p id='pObservaciones'></p>";
				var observacionesCC = (res.observaciones_cc != null) ? "<p id='pObservacionesCC'>" + res.observaciones_cc + "</p>" : "<p id='pObservacionesCC'></p>";
				
				var noCliente = (res.no_cliente != null) ? res.no_cliente : "";
				var nombreTarifa = (res.nombre_producto != null) ? res.nombre_producto : "";
				var numeroTantos = (res.num_tantos != null) ? res.num_tantos : "";
				var tasaNormal = (res.tasa_normal != null) ? res.tasa_normal + "%" : "";
				var nombreCompleto = (res.nombre_completo != null) ? res.nombre_completo : "";
				var importeCredito = (res.importe_credito != null) ? res.importe_credito : "";
				var depEnRespaldo = "";
				var tasaMoratoria = (res.tasa_morat != null) ? res.tasa_morat + "%" : "";
				var descripcionCliente = (res.desc_cliente != null) ? res.desc_cliente : "";
				var plazo = (res.plazo != null) ? res.plazo : "";
				var csmnImpCredito = "";
				var numAbonosDiferidos = (res.num_abonos_diferidos != null) ? res.num_abonos_diferidos : "";
				var nombreDestino = "";
				var descFrecuencia = "";
				var cmsnAseFin = (res.cmsn_ase_fin != null && res.cmsn_ase_fin > 0) ? res.cmsn_ase_fin : "0";
				var analista = (res.nombre_empleado_analiza != null) ? res.nombre_empleado_analiza : "";
				
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
								+ "<td style='width: 12.5%;'>$" + importeCredito.toLocaleString() + "</td>"
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
