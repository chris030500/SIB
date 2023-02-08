/**
 * 
 */

$(document).ready(function() {
	var table = $('#solicitudesTable').DataTable({

		"responsive": true,
		"sAjaxSource": "/solicitudEnvio/verSolicitudes",
		"sAjaxDataProp": "",
		"order": [[0, "desc"]],
		"aoColumns": [
			{ "mData": "i_no_solicitud_core" },
			{ "mData": "dt_fecha_envio" },
			{ "mData": "vc_hora_envio" },
			{ "mData": "dt_fecha_cancelacion" },
			{ "mData": "vc_hora_cancelacion" },
			{ "mData": "vc_obser_peticion" },
			{ "mData": "i_estatus",
				"render": function(data, type, row, meta){
					if(data == 1){
						return "SOLICITUD DE AUTORIZACION DE DOCUMENTOS";
					} else if (data == 2){
						return "RECHAZO DE DOCUMENTOS PARA FIRMA DIGITAL";
					} else if (data == 3){
						return "AUTORIZACION DE DOCUMENTOS PARA FIRMA DIGITAL";
					} else if (data == 4){
						return "CANCELACION INDIVIDUAL DE DOCUMENTOS PARA FIRMA DIGITAL";
					} else if (data == 5){
						return "CANCELACION MASIVA DE DOCUMENTOS PARA FIRMA DIGITAL";
					} else if (data == 6){
						return "DESCARGA DE DOCUMENTOS FIRMADOS DIGITALMENTE";
					} else if (data == 7){
						return "AUTORIZACION DE FIRMA DIGITAL ";
					} else if (data == 8){
						return "RECHAZO DE FIRMA DIGITAL";
					}
					//console.log("data render: " + data);
				}
			}
		],
		"columnDefs": [{
			targets: [7],
			data: null,
			render: function(data, type, row) {
				let btn = document.createElement("button");
				btn.setAttribute("type", "submit")
				btn.setAttribute("onclick", "mostrarSolicitud(this)");
				btn.setAttribute("id", data.pk_id_solicitud_envio);
				btn.setAttribute("value", data.pk_id_solicitud_envio);
				btn.classList.add("btn");
				btn.classList.add("btn-primary");
				btn.innerHTML = "Ver";
				return btn.outerHTML;
			}
		}

		]
	})
});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function mostrarSolicitud(button) {
	var urlBase = getAbsolutePath();
	console.log("Entro en mostrarSolicitud");
	console.log("Datos: " + button.value);
	console.log("urlBase: " +urlBase);
	var data = button.value;
	$.ajax({
		url: urlBase+"verSolicitud/"+data,
		type: "GET",
		success: function(respuesta) {
			window.location.assign(urlBase+"verSolicitud/"+data);
			//buscarProducto(data);
		},
		error: function(respuesta) {
			console.log("Entro en error");
			swal({
				icon: 'warning',
				title: "No se encontro la solicitud",
				showConfirmButton: false

			});

		}
		
		
	});

}

