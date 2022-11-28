fEnvio.min = new Date().toISOString().split("T")[0];
fCancelacion.min = new Date().toISOString().split("T")[0];

function agregarFila() {
	var numTr = $('#tablaDocumentos tbody tr').length + 1;
	console.log("numTrAdd: " + numTr);
	$('#tablaDocumentos tbody tr:last')
		.after(`<tr>
             <td>
               <input type="text" name="nombre${numTr}" id="nombre${numTr}" value="nombre${numTr}" readonly>
             </td>
             <td>
             	<input class="form-control" type="file" id="formFile${numTr}" value="formFile${numTr}" accept=".pdf">
               
             </td>
           </tr>`);
}


function eliminarFila() {
	const table = document.getElementById('tablaDocumentos');
	var numTr = $('#tablaDocumentos tbody tr').length;

	if (numTr <= 1) {
		//alert('No se puede eliminar el encabezado');
		swal({
			icon: "warning",
			title: "No se puede eliminar",
			showConfirmButton: false

		});
	} else {
		table.deleteRow(numTr);
	}

}

function buscarSolicitudCredito(solicitudCredito) {
	console.log("solicitudCredito: " + solicitudCredito);
	var usuarioPregunta = $('input:checkbox[name=usuarioPregunta]:checked').val();
	console.log("Usuario pregunta: " + usuarioPregunta);

	if (solicitudCredito != '') {

		if (usuarioPregunta === undefined) {
			swal({
				title: "No ha seleccionado una opción",
				text: "Favor de ingresar si el usuario es nuevo o no.",
				icon: "info",
			});
		}
		else {
			console.log("Si se ha seleccionado una opción en usuario");
			$.ajax({
				url: "http://localhost:8090/buscar_solicitud?solicitud=" + solicitudCredito,
				type: "GET",
				dataType: "JSON",
				success: function(respuesta) {
					/*console.log(respuesta);
					console.log(respuesta.length);*/
					console.log("Respuesta: " + JSON.stringify(respuesta));

				}
			});
		}

	} else {

		swal({
			title: "No contiene una solicitud de crédito!",
			text: "Favor de ingresar un número de solicitud de crédito.",
			icon: "info",
		});
	}

}

function buscarProducto(producto) {

}


function desmarcar() {

	var usuarioNuevo = document.getElementById("usuarioNuevo");
	var noUsuarioNuevo = document.getElementById("noUsuarioNuevo");
	usuarioNuevo.onclick = function() {
		if (usuarioNuevo.checked != false) {
			noUsuarioNuevo.checked = null;
		}
	}
	noUsuarioNuevo.onclick = function() {
		if (noUsuarioNuevo.checked != false) {
			usuarioNuevo.checked = null;
		}
	}
}
