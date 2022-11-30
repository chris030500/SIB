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

//Funcion para la busqueda de solicitud de credito
function buscarSolicitudCredito(solicitudCredito) {
	console.log("solicitudCredito: " + solicitudCredito);
	var usuarioPregunta = $('input:checkbox[name=usuarioPregunta]:checked').val();
	var divDatoSolicitud = document.getElementById("datoSolicitud");
	console.log("Usuario pregunta: " + usuarioPregunta);
	//document.getElementById('datoSolicitud').innerHTML = '';
	//document.getElementById('participantes').innerHTML = '';
	$("#datoSolicitud").val('');
	$("#participantes").val('');
	//$("#Puesto").val('');

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
			if (usuarioPregunta == 1) {
				console.log("Si es nuevo usuario");
			} else if (usuarioPregunta == 2) {
				console.log("No es nuevo usuario");
				//const respuesta = undefined;
				$.ajax({
					url: "http://localhost:8090/buscar_solicitud?solicitud=" + solicitudCredito,
					type: "GET",
					dataType: "JSON",
					success: function(respuesta) {
						/*console.log(respuesta);
						console.log(respuesta.length);*/
						console.log("Respuesta: " + JSON.stringify(respuesta));
						//console.log("Resultado: " + respuesta[0].resultado);
						if (respuesta == null || respuesta == '') {
							console.log("Error");
							swal({
								title: "Error",
								text: "La solicitud que ingresaste no existe",
								icon: "warning",
							});
						} else {
							console.log("Puede continuar");
							if (respuesta[0].resultado == 1) {
								console.log("Puede continuar para buscar si el producto está configurado");
								//Hacemos visible los campos del detalle de la solicitud
								divDatoSolicitud.style.display = "block";

								//Hacemos referencia a los input para llenar los campos:
								var solicitud = document.getElementById('solicitud');
								var producto = document.getElementById('producto');
								var montoSolicitado = document.getElementById('montoSolicitado');
								var montoAutorizado = document.getElementById('montoAutorizado');
								var plazo = document.getElementById('plazo');
								var tasaNormal = document.getElementById('tasaNormal');
								var estatus = document.getElementById('estatus');

								//Asignamos los valores a los campos
								solicitud.value = respuesta[0].solicitudCreditoID;
								producto.value = respuesta[0].productoCreditoID;
								montoSolicitado.value = respuesta[0].montoSolicitado;
								montoAutorizado.value = respuesta[0].montoAutorizado;
								plazo.value = respuesta[0].plazo;
								tasaNormal.value = respuesta[0].tasaFija;
								estatus.value = respuesta[0].estatus;

								buscarProducto(respuesta[0].productoCreditoID);

							} else if (respuesta[0].resultado == 0) {
								swal({
									title: "Error",
									text: respuesta[0].mensajeError,
									icon: "warning",
								});
							}
						}

					}
				});
			}


		}

	} else {

		swal({
			title: "No contiene una solicitud de crédito!",
			text: "Favor de ingresar un número de solicitud de crédito.",
			icon: "info",
		});
	}

}

//Funcion para buscar si el producto ya se encuentra configurado
function buscarProducto(producto) {
	$.ajax({
		url: "http://localhost:8090/buscar_catProducto?producto=" + producto,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log(respuesta);
			console.log(respuesta.length);*/
			console.log("Respuesta buscarProducto: " + respuesta);
			if (respuesta == 1) {
				console.log("Producto ya configurado");

				//Asignamos el valor de la solicitud para poder traer los participantes
				var solicitud = document.getElementById('solicitud').value;
				//console.log("Traer solicitud: " + solicitud);
				//Llamamos la funcion para obtener los participantes
				traerParticipantes(solicitud)
				buscarDocumentos(producto)
				//Agregamos la petición para obtener los participantes
			} else if (respuesta == 0) {
				document.getElementById('solicitud').value = "";
				document.getElementById('producto').value = "";
				document.getElementById('montoSolicitado').value = "";
				document.getElementById('montoAutorizado').value = "";
				document.getElementById('plazo').value = "";
				document.getElementById('tasaNormal').value = "";
				document.getElementById('estatus').value = "";
				document.getElementById("datoSolicitud").style.display = "none";
				$('#participantes > tbody').empty();
				swal({
					title: "El producto: " + producto + " no está configurado",
					text: "Favor de configurar el producto",
					icon: "warning",
				});
			}
		}
	});
}

//Funcion para obtener los participantes de la solicitud de credito
function traerParticipantes(solicitud) {
	console.log("Entro en traer participantes");
	console.log("Solicitud: " + solicitud);
	var divParticipantes = document.getElementById("divParticipantes");
	$.ajax({
		url: "http://localhost:8090/trae_participantes?solicitud=" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
			//respuesta.forEach(element => console.log(element));
			divParticipantes.style.display = "block";
			$('#participantes > tbody').empty();
			$.each(respuesta, function(idx, opt) {
				//alert('Estoy recorriendo el registro numero: ' + idx);
				var rows = "<tr>"
					+ "<td>" + opt.nombreCompleto + "</td>"
					+ "<td>" + opt.rol + "</td>"
					+ "<td>" + opt.telefonoCelular + "</td>"
					+ "<td>" + opt.correoElectronico + "</td>"
					+ "</tr>";
				$('#participantes').append(rows)
				/*$('#participantes').append('<tr><td>' + opt.nombreCompleto + '</td><td>'
					+  + '</td><td>' + opt.telefonoCelular + '</td><td>' + opt.correoElectronico + '</td></tr>');*/
				//console.log(opt.nickname + ' ' + opt.email + ' ' + opt.rango);
			});
		}
	});
}

function buscarDocumentos(producto) {
	console.log("Entro en buscarDocumentos")
	console.log("Producto: " + producto);
	var divDocumentos = document.getElementById("divDocumentos");
	var divFechas = document.getElementById("divFechas");
	$.ajax({
		url: "http://localhost:8090/buscar_documento?producto=" + producto,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
			//respuesta.forEach(element => console.log(element));
			divDocumentos.style.display = "block";
			divFechas.style.display = "block";
			$('#tablaDocumentos > tbody').empty();
			$.each(respuesta, function(idx, opt) {
        		console.log("idx: " + idx);
        		console.log("Nombre documento: " +opt.nombreDocumento);
				//alert('Estoy recorriendo el registro numero: ' + idx);
				var rows = `<tr>
		             <td>
		               <input type="text" name="nombre${opt.nombreDocumento}" id="nombre${opt.nombreDocumento}" value="nombre${opt.nombreDocumento}" readonly>
		             </td>
		             <td>
		             	<input class="form-control" type="file" id="formFile${idx}" value="formFile${idx}" accept=".pdf">
		               
		             </td>
		             <td>
		             	<button type="button" class="btn btn-danger"
							onclick="eliminarFila(${idx})">Eliminar</button>
		             </td>
		           </tr>`;
				$('#tablaDocumentos').append(rows)
				/*$('#participantes').append('<tr><td>' + opt.nombreCompleto + '</td><td>'
					+  + '</td><td>' + opt.telefonoCelular + '</td><td>' + opt.correoElectronico + '</td></tr>');*/
				//console.log(opt.nickname + ' ' + opt.email + ' ' + opt.rango);
			});
		}
	});
}

//funcion que ayuda solamente tener marcada una opción (Si es nuevo usuario o no lo es)
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
