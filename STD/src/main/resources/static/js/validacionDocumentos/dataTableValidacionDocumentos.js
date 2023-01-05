/**
 * 
 */

$(document).ready(function() {
	var table = $('#solicitudesTable').DataTable({

		"responsive": true,
		"sAjaxSource": "/solicitudEnvio/verSolicitudes",
		"sAjaxDataProp": "",
		"order": [[0, "asc"]],
		"aoColumns": [
			{ "mData": "i_no_solicitud_core" },
			{ "mData": "dt_fecha_envio" },
			{ "mData": "vc_hora_envio" },
			{ "mData": "dt_fecha_cancelacion" },
			{ "mData": "vc_hora_cancelacion" },
			{ "mData": "vc_obser_peticion" }
		],
		"columnDefs": [{
			targets: [6],
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

function mostrarSolicitud(button) {
	console.log("Entro en mostrarSolicitud");
	console.log("Datos: " + button.value);
	var data = button.value;
	$.ajax({
		url: "/solicitudEnvio/verSolicitud/"+data,
		type: "GET",
		success: function(respuesta) {
			window.location.assign("/solicitudEnvio/verSolicitud/"+data);
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

