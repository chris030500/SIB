//console.log("Entro en carga de documentos");
function sumarDias(fecha, dias) {
	fecha = new Date(fecha);
	fecha.setDate(fecha.getDate() + dias);

	//fecha = formatDate(fecha);
	return fecha;
}


//Funcion para activar Fecha y hora de cancelación
function activarFechaHoraCancelación() {
	document.getElementById("fCancelacion").value = "";
	console.log("Entro en fecha cancelación");
	$("#fCancelacion").attr("readonly", false);
	$("#hCancelacion").attr("readonly", false);
	console.log("Fecha envío: " + fEnvio.value);
	fCancelacion.min = sumarDias(fEnvio.value, 1).toISOString().split("T")[0];
	fCancelacion.max = sumarDias(fCancelacion.min, 10).toISOString().split("T")[0];
}


function agregarFila() {
	var numTr = $('#tablaDocumentos tbody tr').length + 1;
	console.log("numTrAdd: " + numTr);
	$('#tablaDocumentos tbody tr:last')
		.after(`<tr>
             <td>
               <input type="text" name="nombre${numTr}" id="nombre${numTr}" value="nombre${numTr}" readonly>
             </td>
             <td>
             	<input class="form-control" type="file" name="file" id="formFile${numTr}" value="formFile${numTr}" accept=".pdf">
               
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
			$.ajax({
				url: "/buscar_solicitud?solicitud=" + solicitudCredito,
				type: "GET",
				dataType: "JSON",
				success: function(respuesta) {
					/*console.log(respuesta);
					console.log(respuesta.length);*/
					//console.log("Respuesta: " + JSON.stringify(respuesta));
					console.log("")
					console.log("Respuesta estatus: " + respuesta.status)
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
							var Nombreproducto = document.getElementById('Nombreproducto');
							$("#divIdProducto").hide();
							//Asignamos los valores a los campos
							solicitud.value = respuesta[0].solicitudCreditoID;
							producto.value = respuesta[0].productoCreditoID;
							montoSolicitado.value = respuesta[0].montoSolicitado;
							montoAutorizado.value = respuesta[0].montoAutorizado;
							plazo.value = respuesta[0].plazo;
							tasaNormal.value = respuesta[0].tasaFija;
							estatus.value = respuesta[0].estatus;
							Nombreproducto.value = respuesta[0].producto;
							buscarProducto(respuesta[0].productoCreditoID);
							/*if (usuarioPregunta == 1) {
								console.log("Si es nuevo cliente");
							} else if (usuarioPregunta == 2) {
								console.log("No es nuevo c");
								//const respuesta = undefined;

							}*/

						} else if (respuesta[0].resultado == 0) {
							swal({
								title: "Error",
								text: respuesta[0].mensajeError,
								icon: "warning",
							});
						}
					}

				}, error: function() {
					swal({
						title: "Error, no es una solicitud de crédito",
						text: "Favor de ingresar una solicitud de crédito",
						icon: "warning",
					});
				}
			});

		}

	}
	else {

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
				document.getElementById("Nombreproducto").value = "";
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

			});
		}
	});
}

function buscarDocumentos(producto) {
	console.log("Entro en buscarDocumentos")
	//console.log("Producto: " + producto);
	var divDocumentos = document.getElementById("divDocumentos");
	var divFechas = document.getElementById("divFechas");
	var usuarioNuevo = $('input:checkbox[name=usuarioPregunta]:checked').val();
	$("#fEnvio").val("");
	$("#fCancelacion").val("");
	console.log("Usuario Nuevo: " + usuarioNuevo);


	//fEnvio.max = sumarDias(new Date().toISOString().split("T")[0], 10).toISOString().split("T")[0];
	fMax = sumarDias(new Date().toISOString().split("T")[0], 10).toISOString().split("T")[0];
	fMin = new Date().toISOString().split("T")[0];
	console.log("fmin: " + fMin);

	document.getElementById("fEnvio").min = fMin;
	document.getElementById("fEnvio").max = fMax;
	n = new Date();
	//Año
	y = n.getFullYear();
	//Mes
	m = n.getMonth() + 1;
	//Día
	d = n.getDate();

	if (usuarioNuevo == 1) {
		console.log("Es usuario nuevo");
		$.ajax({
			url: "/buscar_documento_clienteNuevo?producto=" + producto,
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
					console.log("Nombre documento: " + opt.nombreDocumento);
					console.log("Id del documento: " + JSON.stringify(opt));
					var rows = `<tr>
		             <td>
		               <input type="text" name="${opt.nombreDocumento}" id="${opt.nombreDocumento}" value="${opt.nombreDocumento}" readonly>
		             </td>
		             <td>
		             	
						<input type="file" name="file"  id="${opt.idRelProdDoct}" class="archivo"/> 
		               
		             </td>
		             <td>
		             
		             	
		             	<button class="btn btn-danger" tabindex="0" 
								title="Eliminar" onclick="eliminarFila(${idx})"
								aria-controls="table-datatable" type="button">
								<i class="bi bi-trash"></i>
						</button>
						
		             </td>
		           </tr>`;
					$('#tablaDocumentos').append(rows)

				});
			}, error: function() {
				swal({
					title: "Error",
					text: "No sé encontrarón documentos asociados",
					icon: "warning",
				});
			}
		});
	} else if (usuarioNuevo == 2) {
		$.ajax({
			url: "/buscar_documento?producto=" + producto,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				divDocumentos.style.display = "block";
				divFechas.style.display = "block";
				$('#tablaDocumentos > tbody').empty();
				$.each(respuesta, function(idx, opt) {
					console.log("idx: " + idx);
					console.log("Nombre documento: " + opt.nombreDocumento);
					console.log("Id del documento: " + JSON.stringify(opt));
					//alert('Estoy recorriendo el registro numero: ' + idx);
					var rows = `<tr>
		             <td>
		               <input type="text" name="${opt.nombreDocumento}" id="${opt.nombreDocumento}" value="${opt.nombreDocumento}" readonly>
		             </td>
		             <td>
		             	
		             		<input type="file" name="file" id="${opt.idRelProdDoct}" class="archivo"/> 
		             		
		               
		             </td>
		             <td >
		             	<div id="tdBotones" class="dt-buttons">
		             	
		             		<button class="btn btn-danger" tabindex="0" 
								title="Eliminar" onclick="eliminarFila(${idx})"
								aria-controls="table-datatable" type="button">
								<i class="bi bi-trash"></i>
							</button>
		             		
								
						</div>
		             </td>
		           </tr>`;
					$('#tablaDocumentos').append(rows)

				});
			}, error: function() {
				swal({
					title: "Error",
					text: "No sé encontrarón documentos asociados",
					icon: "warning",
				});
			}
		});
	}
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



function obtenerNombreDoct(nombre) {
	console.log("Obtener el nombre del documento subido" + nombre);

	var button = `<button class="btn btn-info" tabindex="0" 
								title="Asignar coordenadas"
								aria-controls="table-datatable" type="button">
								<i class="bi bi-file-person-fill"></i>
							</button>`;
	$('#tdBotones').append(button);
	$('#tdBotones').remove(button);
	//console.log("ID del documento: " );
}

//Funcion para dar de alta la solicitud
function altaSolicitud() {
	console.log("Entro en altaSolicitud");
	var inputs = $('input[name^=file]');
	/*var countI = inputs.length;
	console.log("Files" + countI);
	console.log("files: " +  document.getElementById("fileLoader").files[0]);*/
	if (document.getElementById("fEnvio").value != null && document.getElementById("fCancelacion").value) {
		//console.log("No ha seleccionado un archivo");
		console.log("Puede continuar");
		//console.log("Ha seleccionado un archivo");
		//Vamos a obtener los datos necesarios para llenar la solicitud de envio

		var solicitud = document.getElementById('solicitud').value;
		var producto = document.getElementById('producto').value;
		var estatus = 1;
		var comentarioValidacion = document.getElementById('comentarioValidacion').value;
		var fEnvio = document.getElementById('fEnvio').value;
		var hEnvio = document.getElementById('hEnvio').value;
		var fCancelacion = document.getElementById('fCancelacion').value;
		var hCancelacion = document.getElementById('hCancelacion').value;
		console.log("noSolicitud: " + solicitud);
		console.log("productoId: " + producto);
		console.log("estatus: " + estatus);
		console.log("obserReticion: " + comentarioValidacion);
		console.log("fechaEnvio: " + fEnvio);
		console.log("horaEnvio: " + hEnvio);
		console.log("fechaCancelacion: " + fCancelacion);
		console.log("horaCancelacion:" + hCancelacion);

		$.ajax({
			url: "/solicitudEnvio/guardarSolicitudEnvio?solicitud=" + solicitud + "&producto=" + producto +
				"&comentarioValidacion=" + comentarioValidacion + "&fEnvio=" + fEnvio + "&hEnvio=" + hEnvio + "&fCancelacion=" + fCancelacion + "&hCancelacion=" + hCancelacion,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				/*console.log("Entro en success de get");
				console.log("Respuesta: " +respuesta);*/
				console.log("Se guardo la contraseña en historial contraseña: ");
				swal({
					icon: "success",
					title: "Registro de solicitud exitoso.",
					showConfirmButton: false

				});
				uploadFile();

			},
			error: function(respuesta) {
				//console.log("Entro en error");
				swal({
					icon: "warning",
					title: "Error: " + respuesta.status,
					showConfirmButton: false
				});

			}
		});
	} else {
		swal({
			title: "Error",
			text: "Favor de llenar los campos de fecha de envio y/o fecha de cancelación",
			icon: "warning",
		});
		//console.log("Favor de llenar los campos de fecha de envio y/o fecha de cancelación");
	}
}

//handle the button click event here.
$(document).ready(function() {
	$(".upload-me").on("click", handleUpload); //on click, call handleUpload method
});

// Function para obtener los documentos del campo file
function uploadFile() {
	console.log("Lenght: " + document.getElementsByClassName("archivo").length);
	/*var d = document.getElementsByClassName("archivo");
	console.log("Archivo: " +  JSON.stringify(d));*/
	var solicitud = document.getElementById("solicitudCredito").value;
	var longitud = document.getElementsByClassName("archivo").length;
	console.log(document.getElementsByClassName("archivo"));
	console.log("Solicitud de credito: " + solicitud);
	var idSolicitud = obtenerIdSolicitud(solicitud);
	uploadFile2();
	for (i = 0; i < longitud; i++) {
		var documento = document.getElementsByClassName("archivo")[i].id;
		var n = document.getElementsByClassName("archivo")[i].files[0].name;
		console.log("documento: " + documento);
		console.log("Files: " + JSON.stringify(n));
		registrarDoctEnvio(solicitud, documento, n);
	}

}
function registrarDoctEnvio(solicitud, documento, nombreDocumento) {
	console.log("Entro en registrarDoctEnvio");
	$.ajax({
		url: "/doctEnvio?solicitud=" + solicitud + "&documento=" + documento + "&nombreDocumento=" + nombreDocumento,
		type: "POST",
		dataType: 'json',
	})
		.then(function(result) {
			console.log("Result: " + JSON.stringify(result));
			/*console.log(result.value);
			console.log(result.name);
			console.log(result.action);*/
		});
}

function obtenerIdSolicitud(solicitud) {
	console.log("Entro en obtenerIdSolicitud");
	$.ajax({
		url: "/solicitudEnvio/obtenerSolicitudFecha/" + solicitud,
		type: "GET",
		success: function(data) {
			console.log("Exitoso");
			//console.log("Data: " + JSON.stringify(data));
			//console.log("Id solicitud: " + data[0].pk_id_solicitud_envio);
			return data[0].pk_id_solicitud_envio;
		},
		error: function(data) {
			//console.log("Error");
			swal({
				icon: "warning",
				title: "Error: " + respuesta.status,
				showConfirmButton: false
			});
		}
	});
}

function uploadFile2() {
	//var url = "YOUR-BASE-URL-HERE"; Like http://localhost:8081
	var form = $('#form-upload')[0]; //get the form containing the files
	console.log("form: " + form.value);
	var data = new FormData(form);
	console.log("Data: " + JSON.stringify(data));
	$.ajax({
		url: "/guardarPDFServidor/upload2",
		type: "POST",
		enctype: 'multipart/form-data',
		data: data, //pass the form data
		processData: false,
		contentType: false,
		success: function(data) {
			console.log("Successfully uploaded " + data);
		},
		error: function(data) {
			//console.log("Data: " + JSON.stringify(data));
			//console.log("upload failed");
			swal({
				icon: "warning",
				title: "Error: " + data.status,
				showConfirmButton: false
			});
		}
	});
}

