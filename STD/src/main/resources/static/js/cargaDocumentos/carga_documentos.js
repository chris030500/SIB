var productoNomina = document.getElementById('productoNomina').value;

// Variables | para el spinner
var spinner = document.getElementById('spinnerID');
var principal = document.getElementById('principalID');
//console.log("Entro en carga de documentos");
/*function sumarDias(fecha, dias) {
	//console.log("sumar dias dias: " + dias);
	var fechaNueva = new Date(fecha);
	fechaNueva.setDate(fechaNueva.getDate() + dias);

	//fecha = formatDate(fecha);
	//console.log("Fecha: " + fechaNueva);
	return fechaNueva;
}*/

function sumarDias(fecha, dias) {
	fecha.setDate(fecha.getDate() + dias);
	return fecha;
}

function agregarFila() {
	var numTr = $('#tablaDocumentos tbody tr').length + 1;
	//console.log("numTrAdd: " + numTr);
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

function eliminarFila(id) {
	var numTr = $('#tablaDocumentos tbody tr').length;
	//console.log("numTr: " + numTr);
	if (numTr <= 1) {
		//alert('No se puede eliminar el encabezado');
		swal({
			icon: "warning",
			title: "No se puede eliminar",
			showConfirmButton: false

		});
	} else {
		$("#fila" + id).remove();
		//table.deleteRow(numTr);
	}

}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

const diferenciaDias = function obtenerDiferenciaDias() {
	var urlBase = getAbsolutePath();
	//console.log("obtenerDiferencia: " + urlBase);
	var dias = 0;
	$.ajax({
		url: urlBase + "obtenerDiferenciaDias",
		async: false,
		type: "GET"
	}).done(function(respuesta) {
		//console.log("respuesta obtenerDiferencia : " + respuesta);
		dias = respuesta
		return dias;
	});
	return dias;
}

//Funcion para activar Fecha y hora de cancelación
function activarFechaHoraCancelación() {
	var envio = document.getElementById("fEnvio").value;
	//console.log("Fecha envio en activar: " + envio);
	if (envio == "" || envio == null) {
		$("#fCancelacion").attr("readonly", true);
		$("#hCancelacion").attr("readonly", true);
		swal({
			title: "Error",
			text: "Favor de seleccionar un día valido en fecha envío",
			icon: "warning",
		});
	} else {
		//document.getElementById("fCancelacion").value = "";
		$("#fCancelacion").attr("readonly", false);
		$("#hCancelacion").attr("readonly", false);
		document.getElementById("fCancelacion").min = document.getElementById("fEnvio").value;
		document.getElementById("fCancelacion").max = document.getElementById("fEnvio").max;
	}

}

//Funcion para la busqueda de solicitud de credito
function buscarSolicitudCredito(solicitudCredito) {
	var urlBase = getAbsolutePath();
	//console.log("urlGuardar: " + urlBase);
	//console.log("solicitudCredito: " + solicitudCredito);
	var usuarioPregunta = $('input:checkbox[name=usuarioPregunta]:checked').val();
	var divDatoSolicitud = document.getElementById("datoSolicitud");
	//console.log("Usuario pregunta: " + usuarioPregunta);
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
			//console.log("Si se ha seleccionado una opción en usuario");
			$.ajax({
				url: urlBase + "buscar_solicitud?solicitud=" + solicitudCredito,
				type: "GET",
				dataType: "JSON",
				success: function(respuesta) {
					//console.log(respuesta);
					/*console.log(respuesta.length);*/
					//console.log("Respuesta: " + JSON.stringify(respuesta));
					//console.log("")
					//console.log("Respuesta estatus: " + respuesta.status)
					//console.log("Resultado: " + respuesta[0].resultado);
					if (respuesta == null || respuesta == '') {
						//console.log("Error");
						swal({
							title: "Error",
							text: "La solicitud que ingresaste no existe",
							icon: "warning",
						});
					} else {
						//console.log("Puede continuar");
						//if (respuesta[0].resultado == 1) {
						//console.log("Puede continuar para buscar si el producto está configurado");
						//console.log("Resultado: " + JSON.stringify(respuesta));
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
						var productoNomina = document.getElementById('productoNomina');
						var clienteId = document.getElementById('clienteId');
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
						productoNomina.value = respuesta[0].productoNomina;
						clienteId.value = respuesta[0].clienteId;
						buscarProducto(respuesta[0].productoCreditoID);

						/*if (usuarioPregunta == 1) {
							console.log("Si es nuevo cliente");
						} else if (usuarioPregunta == 2) {
							console.log("No es nuevo c");
							//const respuesta = undefined;

						}*/

						/*} else if (respuesta[0].resultado == 0) {
							swal({
								title: "Error",
								text: respuesta[0].mensajeError,
								icon: "warning",
							});
						}*/
					}

				}, error: function() {
					swal({
						title: "Error, no es una solicitud de crédito",
						text: "Favor de ingresar una solicitud de crédito valida",
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
	var urlBase = getAbsolutePath();
	$.ajax({
		url: urlBase + "buscar_catProducto?producto=" + producto,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log(respuesta);
			console.log(respuesta.length);*/
			//console.log("Respuesta buscarProducto: " + respuesta);
			if (respuesta == 1) {
				//console.log("Producto ya configurado");

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
	var urlBase = getAbsolutePath();
	/*console.log("Entro en traer participantes");
	console.log("Solicitud: " + solicitud);*/
	var divParticipantes = document.getElementById("divParticipantes");
	$.ajax({
		url: urlBase + "trae_participantes?solicitud=" + solicitud,
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
	//console.log("Entro en buscarDocumentos")
	//console.log("Producto: " + producto);
	var divDocumentos = document.getElementById("divDocumentos");
	var divFechas = document.getElementById("divFechas");
	var usuarioNuevo = $('input:checkbox[name=usuarioPregunta]:checked').val();
	/*$("#fEnvio").val("");
	$("#fCancelacion").val("");*/
	//console.log("Usuario Nuevo: " + usuarioNuevo);
	var d = diferenciaDias();
	//console.log("Dias: " + d);
	//var d = 10;
	//fEnvio.max = sumarDias(new Date().toISOString().split("T")[0], 10).toISOString().split("T")[0];
	var fe = new Date();
	//var fMax = sumarDias(new Date().toISOString().split("T")[0], d);
	//console.log("fe: " + fe);
	var fMax = sumarDias(fe, d).toISOString().split("T")[0];
	var fMin = new Date().toISOString().split("T")[0];
	//console.log("fMax: " + fMax);
	//console.log("fmin: " + fMin);

	document.getElementById("fEnvio").min = fMin;
	document.getElementById("fEnvio").max = fMax;
	n = new Date();
	//Año
	y = n.getFullYear();
	//Mes
	m = n.getMonth() + 1;
	//Día
	d = n.getDate();
	var urlBase = getAbsolutePath();
	if (usuarioNuevo == 1) {
		/*console.log("Es usuario nuevo");
		console.log("producto : " + producto);*/
		$.ajax({
			url: urlBase + "buscar_documento_clienteNuevo?producto=" + producto,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				//console.log("Respuesta de participantes: " + JSON.stringify(respuesta));
				//respuesta.forEach(element => console.log(element));
				divDocumentos.style.display = "block";
				divFechas.style.display = "block";
				$('#tablaDocumentos > tbody').empty();
				$.each(respuesta, function(idx, opt) {
					/*console.log("idx: " + idx);
					console.log("Nombre documento: " + opt.nombreDocumento);
					console.log("Id del documento: " + JSON.stringify(opt));*/
					var rows = `<tr id="fila${idx}">
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
		//console.log("Entro en usuario nuevo = 2");
		$.ajax({
			url: urlBase + "buscar_documento_clienteNuevo?producto=" + producto,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				//console.log("Respuesta usuario nuevo = 2: " + JSON.stringify(respuesta));
				if (respuesta == null || respuesta == '') {
					//console.log("Viene vacío la lista");
					var solicitud = document.getElementById('solicitud').value;
					swal({
						title: "Error",
						text: "No sé encontrarón documentos asociados al producto: " + producto + " en la solicitud: " + solicitud,
						icon: "warning",
					}).then((result) => {
						window.location.replace(urlBase + "carga_documentos");
					});
				} else {
					//console.log("Viene con datos la lista de documentos");
					divDocumentos.style.display = "block";
					divFechas.style.display = "block";
					$('#tablaDocumentos > tbody').empty();
					$.each(respuesta, function(idx, opt) {
						/*console.log("idx: " + idx);
						console.log("Nombre documento: " + opt.nombreDocumento);
						console.log("Id del documento: " + JSON.stringify(opt));*/
						//alert('Estoy recorriendo el registro numero: ' + idx);
						var rows = `<tr id="fila${idx}">
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
				}

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
	//console.log("Obtener el nombre del documento subido" + nombre);

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
	/*spinner.style.display = 'block';
	principal.style.display = 'none';*/
	//console.log("Entro en altaSolicitud");
	var urlBase = getAbsolutePath();
	//var inputs = $('input[name^=file]');
	var solicitud = document.getElementById('solicitud').value;
	if (document.getElementById("fEnvio").value != null && document.getElementById("fCancelacion").value) {
		if ((document.getElementById("fCancelacion").value > document.getElementById("fEnvio").value) ||
			((document.getElementById("fCancelacion").value == document.getElementById("fEnvio").value
				&& document.getElementById("hEnvio").value < document.getElementById("hCancelacion").value))) {
			var fechaCancelacion = document.getElementById("fCancelacion").value;
			console.log("Fe Cancelación:" + fechaCancelacion);
			var numTr = $('#tablaDocumentos tbody tr').length;
			console.log("num: " + numTr);

			var miArray = new Array(numTr);
			var conteo = 0;
			//For que nos ayuda a validar la carga de archivos
			for (i = 0; i < numTr; i++) {
				var numTr1 = $('#tablaDocumentos tbody tr').value;
				console.log("numTr1: " + numTr1);
				//var documento = document.getElementsByClassName("archivo")[i].id;
				var n = document.getElementsByClassName("archivo")[i].files[0];
				console.log("n : " + n);
				if (n === undefined) {
					miArray[i] = false;
					//console.log("No tiene nombre");
				} else {
					miArray[i] = true;
					//console.log("Si tiene nombre");
				}

			}

			for (i = 0; i < numTr; i++) {
				if (miArray[i] === true) {
					conteo = conteo + 1;
				}
			}

			if (conteo == numTr) {


				var longitud = document.getElementsByClassName("archivo").length;
				var miArray2 = new Array(longitud);
				var conteo2 = 0;
				//For para validar el nombre de los documentos
				for (i = 0; i < longitud; i++) {
					//var documento = document.getElementsByClassName("archivo")[i].id;
					var n = document.getElementsByClassName("archivo")[i].files[0].name;
					/*console.log("documento: " + documento);
					console.log("n: " + n);*/
					//console.log("n includes: " + n.includes("AnexosContrato") ? 'Si' : 'No' );
					//Sentencia que evalua si el nombre del archivo es aceptado o no
					if (n.includes("AnexosContrato") || n.includes("Contrato_Apertura")
						|| n.includes("Pagare") || n.includes("PlandePagos")) {
						miArray2[i] = true;
						//console.log("Si tiene nombre correcto");
					} else {
						miArray2[i] = false;
						//console.log("No tiene nombre correcto");
					}
					/*console.log("documento uploadFile: " + documento);
					console.log("Files: " + JSON.stringify(n));*/

				}

				for (i = 0; i < longitud; i++) {
					if (miArray2[i] === true) {
						conteo2 = conteo2 + 1;
					}
				}
				console.log("conteo2 = " + conteo2);
				console.log("longitud: " + longitud);
				if (conteo2 == longitud) {
					/*console.log("Los nombres de los documentos coinciden");
					console.log("Ejecuta el guardado de la solicitud");*/
					//Ejecuta el guardado de la solicitud ya que todos los documentos cumplan con el nombre establecido
					$.ajax({
						async: false,
						url: urlBase + "solicitudEnvio/validarSolicitudCredito/" + solicitud,
						type: "GET",
						dataType: "JSON",
						success: function(respuesta) {
							/*console.log("Respuesta validarCoreSolicitudEnvio: " + JSON.stringify(respuesta));
							console.log("Respuesta 1: " + JSON.stringify(respuesta[0]));*/
							//console.log("RESPUESTA: " + JSON.stringify(respuesta));
							var r;
							if (JSON.stringify(respuesta) == "[]") {
								r = null;
							} else {
								r = JSON.stringify(respuesta[0].estatus);
							}
							//var r = JSON.stringify(respuesta[0].estatus);
							//console.log("r: " + r);
							if (r == 1) {
								swal({
									title: "Solicitud en proceso de: ",
									text: "Autorizacion de documentos",
									icon: "info",
								});
								//console.log("No puede continuar debido a que se encuentra en solicitud de autorizacion de documentos");
							} else if (r == 3) {
								swal({
									title: "Solicitud en proceso de: ",
									text: "Autorización de documentos para firma digital",
									icon: "info",
								});
								//console.log("No puede continuar debido a que se encuentra en autorización de documentos para firma digital");
							} else if (r == 2 || r == 4 || r == 5 || r == 7 || r == null || r == 6) {
								//console.log("Puede realizar el registro");
								var producto = document.getElementById('producto').value;
								//var estatus = 1;
								var comentarioValidacion = document.getElementById('comentarioValidacion').value;
								var fEnvio = document.getElementById('fEnvio').value;
								var hEnvio = document.getElementById('hEnvio').value;
								var fCancelacion = document.getElementById('fCancelacion').value;
								var hCancelacion = document.getElementById('hCancelacion').value;
								var productoNomina = document.getElementById('productoNomina').value;
								console.log("Fecha cancelación: " + fCancelacion);
								/*console.log("noSolicitud: " + solicitud);
								console.log("productoId: " + producto);
								console.log("estatus: " + estatus);
								console.log("obserReticion: " + comentarioValidacion);
								console.log("fechaEnvio: " + fEnvio);
								console.log("horaEnvio: " + hEnvio);
								console.log("fechaCancelacion: " + fCancelacion);
								console.log("horaCancelacion:" + hCancelacion);
								console.log("productoNomina: " + productoNomina);*/
								var solicitudCredito = document.getElementById("solicitudCredito").value;
								var clienteId = document.getElementById("clienteId").value;
								$.ajax({
									async: false,
									url: urlBase + "solicitudEnvio/guardarSolicitudEnvio?solicitud=" + solicitud + "&producto=" + producto +
										"&comentarioValidacion=" + comentarioValidacion +
										"&fEnvio=" + fEnvio + "&hEnvio=" + hEnvio + "&fCancelacion=" + fCancelacion +
										"&hCancelacion=" + hCancelacion,
									type: "POST",
									dataType: "JSON",
									success: function(respuesta) {
										//console.log("Respuesta: " + JSON.stringify(respuesta));
										var idEnvio = JSON.stringify(respuesta);
										/*console.log("Entro en success de get");
										console.log("Respuesta: " +respuesta);*/
										//console.log("Se guardo la solicitud de envio: ");
										/*swal({
											icon: "success",
											title: "Registro de solicitud exitoso.",
											showConfirmButton: false
						
										});*/
										uploadFile2(solicitudCredito, clienteId, idEnvio);
										uploadFile(fCancelacion, productoNomina, idEnvio);
										setTimeout(() => spinner.style.display = 'none', 3000);
										swal({
											icon: "success",
											title: "Registro de solicitud exitoso.",
											showConfirmButton: false

										}).then((result) => {
											window.location.replace(urlBase + "carga_documentos");
										});

									},
									error: function(respuesta) {
										//console.log("Entro en error");
										/*spinner.style.display = 'none';
										principal.style.display = 'block';*/
										swal({
											icon: "warning",
											title: "Error: " + respuesta.status,
											showConfirmButton: false
										});

									}
								});
							}
						}, error: function(respuesta) {
							/*spinner.style.display = 'none';
							principal.style.display = 'block';*/
							//console.log("Respuesta error validarCoreSolicitudEnvio: " + JSON.stringify(respuesta));
							swal({
								title: "Error validarCoreSolicitudEnvio",
								text: JSON.stringify(respuesta),
								icon: "warning",
							});
						}
					});
				} else {
					console.log("Favor de revisar los nombres de los archivos");
					swal({
						title: "Error, nombres de archivos incorrectos",
						text: "Favor de revisar los nombres de los archivos",
						icon: "warning",
					});
				}

				/*console.log("Todos los valores son true");
				console.log("Ejecuta para registrar los documentos");*/

			} else {
				//console.log("No todos los valores son true");
				swal({
					title: "Error, sin archivos seleccionados",
					text: "Favor de revisar la carga de archivos",
					icon: "warning",
				});
			}


		} else {
			/*spinner.style.display = 'none';
			principal.style.display = 'block';*/
			swal({
				title: "Error",
				text: "Favor de revisar la fecha y hora de cancelación",
				icon: "warning",
			});
		}

	} else {
		/*spinner.style.display = 'none';
		principal.style.display = 'block';*/
		swal({
			title: "Error",
			text: "Favor de llenar los campos de fecha de envio y/o fecha de cancelación",
			icon: "warning",
		});
	}
}

function validarSolicitudCore(solicitud) {
	var urlBase = getAbsolutePath();
	//console.log("Entro en validarCoreSolicitudEnvio");
	//var solicitudCore = document.getElementById('solicitud').value;
	$.ajax({
		async: false,
		url: urlBase + "solicitudEnvio/validarSolicitudCredito/" + solicitud,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			/*console.log("Respuesta validarCoreSolicitudEnvio: " + JSON.stringify(respuesta));
			console.log("Respuesta 1: " + JSON.stringify(respuesta[0]));*/
			return JSON.stringify(respuesta[0]);
		}, error: function(respuesta) {
			//console.log("Respuesta error validarCoreSolicitudEnvio: " + JSON.stringify(respuesta));
			swal({
				title: "Error en validarCoreSolicitudEnvio",
				text: JSON.stringify(respuesta),
				icon: "warning",
			});
		}
	});
}


// Function para obtener los documentos del campo file
function uploadFile(fCancelacion, productoNomina, idEnvio) {
	//console.log("uploadFile: ");

	var productoNomina = document.getElementById('productoNomina').value;
	//var fCancelacion = document.getElementById('fCancelacion').value;
	//var productoNomina = "N";
	//var fCancelacion = "2023-01-20";
	console.log("Fecha de cancelación: " + fCancelacion);
	/*console.log("fCancelacion: " + fCancelacion);
	console.log("productoNomina" + productoNomina);
	console.log("Lenght: " + document.getElementsByClassName("archivo").length);*/
	/*var d = document.getElementsByClassName("archivo");
	console.log("Archivo: " +  JSON.stringify(d));*/
	var solicitud = document.getElementById("solicitudCredito").value;
	var clienteId = document.getElementById("clienteId").value;
	var longitud = document.getElementsByClassName("archivo").length;
	/*console.log(document.getElementsByClassName("archivo"));
	console.log("Solicitud de credito: " + solicitud);*/
	//var idSolicitud = obtenerIdSolicitud(solicitud);
	//uploadFile2(solicitud, clienteId);
	for (i = 0; i < longitud; i++) {
		var documento = document.getElementsByClassName("archivo")[i].id;
		var n = document.getElementsByClassName("archivo")[i].files[0].name;


		registrarDoctEnvio(solicitud, documento, n, fCancelacion, productoNomina, clienteId, idEnvio);

		/*console.log("documento uploadFile: " + documento);
		console.log("Files: " + JSON.stringify(n));*/

	}

}

function registrarDoctEnvio(solicitud, documento, nombreDocumento, fCancelacion, productoNomina, clienteId, idEnvio) {
	console.log("Entro en registrarDoctEnvio");
	console.log("fCancelacion: " + fCancelacion);
	console.log("productoNomina: " + productoNomina);
	var urlBase = getAbsolutePath();
	$.ajax({
		url: urlBase + "doctEnvio?solicitud=" + solicitud + "&documento=" + documento +
			"&nombreDocumento=" + nombreDocumento + "&fCancelacion=" + fCancelacion +
			"&productoNomina=" + productoNomina + "&clienteId=" + clienteId +
			"&idEnvio=" + idEnvio,
		type: "POST",
		dataType: 'json',
	})
		.then(function(result) {
			/*console.log("Result: " + JSON.stringify(result));
			console.log("Registro realizado con exito");*/
			/*console.log(result.value);
			console.log(result.name);
			console.log(result.action);*/
		});
}


function obtenerIdSolicitud(solicitud) {
	//console.log("Entro en obtenerIdSolicitud");
	var urlBase = getAbsolutePath();
	$.ajax({
		url: urlBase + "solicitudEnvio/obtenerSolicitudFecha/" + solicitud,
		type: "GET",
		success: function(data) {
			//console.log("Exitoso obtenerIdSolicitud");

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



function uploadFile2(solicitudCredito, clienteId, idEnvio) {
	//var url = "YOUR-BASE-URL-HERE"; Like http://localhost:8081
	var urlBase = getAbsolutePath();
	var form = $('#form-upload')[0]; //get the form containing the files
	/*console.log("SolicitudCore: " + solicitudCredito);
	console.log("clienteId: " + clienteId);
	console.log("form: " + form.value);*/
	var data = new FormData(form);
	//console.log("Data: " + JSON.stringify(data));
	$.ajax({
		async: false,
		url: urlBase + "guardarPDFServidor/upload2?solicitudCredito=" + solicitudCredito + "&clienteId=" + clienteId
			+ "&idEnvio=" + idEnvio,
		type: "POST",
		enctype: 'multipart/form-data',
		data: data, //pass the form data
		processData: false,
		contentType: false,
		success: function(data) {
			console.log("Carga de archivos exitosa " + data);
		},
		error: function(data) {
			console.log("Data: " + JSON.stringify(data));
			console.log("Error: " + data.response.status);
			if (data.response.status == 500) {
				swal({
					icon: "warning",
					title: "No se pudo guardar el archivo en el disco seleccionado.",
					text: data.status,
					showConfirmButton: false
				});
			}
			//console.log("upload failed");
			swal({
				icon: "warning",
				title: "Error: " + data.status,
				showConfirmButton: false
			});
		}
	});
}



