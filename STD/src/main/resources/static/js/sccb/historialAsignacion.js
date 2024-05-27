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

function buscarExpedientesNegociacion() {
	var idCredito = $("#creditoBusqueda").val();
	var divExpedienteNegociacion = $("#divExpedientesNegociacion");

	console.log(idCredito);
	
	url = getAbsolutePath() + "getByIdCreditoHistorialAsignacion?idCredito=" + Number(idCredito);
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.historialAsig);
			divExpedienteNegociacion.show();
			
			if (res == null || res == '') {
				console.log("Error");
			}
			else { 
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
			
		}
	});
}

function buscarDatos() {
	
	buscarExpedientesNegociacion();
}