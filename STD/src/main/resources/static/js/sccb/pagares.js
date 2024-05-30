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


function buscarDatos() {
	var divPagares = $("#divPagares");
	var idCredito = $("#creditoBusqueda").val();
	
	url = getAbsolutePath() + "getPagaresByIdCredito?idCredito=" + Number(idCredito);
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function (res) {
			console.log(res.pagares);
			divPagares.show();
			var tbody = "<tbody></tbody>";
			$("#pagares").append(tbody);
			$.each(res.pagares, function(idx, opt) {
				var rows = "<tr>"
				+ "<td>Ver</td>"
				+ "<td>" + opt.vc_tipo_documento + "</td>"
				+ "<td>" + opt.vc_nombre_centro_ubicacion + "</td>"
				+ "<td>" + opt.i_no_cliente + "</td>"
				+ "<td>" + opt.i_credito_id + "</td>"
				+ "<td>" + "$" + opt.fl_importe_credito + ".00" + "</td>"
				+ "<td>" + opt.dt_fecha_inicio_credito + "</td>"
				+ "<td>" + opt.vc_estatus_credito + "</td>"
				+ "<td>" + opt.dt_fecha_evento + "</td>"
				+ "<td>" + opt.vc_nombre_solicitante + "</td>"
				+ "<td>" + opt.vc_nombre_centro_solicitante + "</td>"
				+ "<td>" + opt.vc_nombre_destino + "</td>"
				+ "<td>" + opt.vc_nombre_asignado + "</td>"
				+ "<td>" + opt.vc_etapa_custodia + "</td>"
				+ "<td>" + "</td>"
				+ "<td>" + "</td>"
				+ "<td>" + opt.vc_tipo_devolucion + "</td>"
				+ "</tr>";
				
				$("#pagares > tbody").append(rows);
			});
		}
	});
}
