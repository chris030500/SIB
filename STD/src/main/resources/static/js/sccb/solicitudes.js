var optionSelected = "";
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
}

function getUrlSolicitudes() {
	var url = getAbsolutePath();
	var idCredito = $("#creditoBusqueda").val();
	var noCliente = $("#clienteBusqueda").val();
	var nomCliente = $("#nameBusqueda").val();
	
	console.log(typeof(Number(idCredito)));
	
	
	url = url + "getSolicitudes?idCredito=" + Number(idCredito)
	
	return url;
}

function buscarDatos() {
	var divTable = document.getElementById("divTable");
	
	console.log(getUrlSolicitudes());
	
	$.ajax({
		url: getUrlSolicitudes(),
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res.solicitudes);
			/*if(res == null || res == '') {
				console.log("Error");
				divTable.style.display = "none";
			} else {
				divTable.style.display = "block";
				$('#solicitudes > tbody').empty();
				$.each(res, function(idx, opt) {
					var rows = "<tr>";
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
			} */
		},
	});
}