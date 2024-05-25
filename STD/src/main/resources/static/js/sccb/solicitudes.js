var optionSelected = "";
var url = "";
var tblSolicitudes;

$(document).ready(function() {
	defaultView();
	startTable();
	
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

function startTable() {
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
	$('#divDatosSolicitud').hide();
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

function buscarDatos() {
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
					var rows = "<tr>"
						+ "<td class='text-center'>" + opt.i_solicitud_id + "</td>"
						+ "<td class='text-center'>" + opt.i_credito_id + "</td>"
						+ "<td class='text-center'>" + opt.vc_nombre_cliente + "</td>"
						+ "<td class='text-center'>" + opt.i_no_cliente + "</td>"
						+ "<td class='text-center'>" + opt.vc_rol + "</td>"
						+ "<td class='text-center'>" + opt.vc_nombre_centro + "</td>"
						+ "<td class='text-center'>" + opt.vc_etapa + "</td>"
						+ "<td class='text-center'>" + opt.vc_estatus + "</td>"
						+ "<td class='text-center'>" + opt.fl_monto + "</td>"
						+ "<td class='text-center'>" + opt.dt_fecha_recepcion + "</td>"
						+ "<td class='text-center'>" + opt.dt_fecha_inicio_tramite + "</td>"
						+ "<td class='text-center'>" + opt.dt_fecha_autorizacion + "</td>"
						+ "<td class='text-center'>" + opt.dt_fecha_desembolso + "</td>"
						+ "</tr>";
					$('#solicitudes').append(rows);
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