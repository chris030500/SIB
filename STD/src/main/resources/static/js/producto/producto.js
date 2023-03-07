var opcionSeleccionada = "";
var altaProductos = "alta/";
var altaDocumentos = "altaDocumentos/";
var consultaDocumento = "consultaDocumento/";
var inicio = "index";
var actualizarDocumentos = "actualizarDocumentos/";
var bajaDocumentos = "bajaDocumentos/";

var idsAnteriores = " ";

$(document).ready(function() {
	inicioPantalla();
	$("#opciones").change(function() {
		opcionSeleccionada = $("#opciones").val();
		if (opcionSeleccionada > 0) {

			switch (opcionSeleccionada) {
				case "1":
					$("#productoColocacion").show();
					$("#diviKYC").show();
					$("#divdescripcionProducto").show();
					$("#contrato").show();
					$("#productoCaptacion").hide();


					break;
				case "2":
					$("#productoColocacion").hide();
					$("#productoCaptacion").show();
					$("#diviKYC").show();
					$("#divdescripcionProducto").show();
					$("#contrato").show();

					break;
				default:
					break;

			}

		} else {
			$("#productoColocacion").hide();
			$("#productoCaptacion").hide();
			$("#diviKYC").hide();
			$("#divdescripcionProducto").hide();
			$("#contrato").hide();

			swal({
				title: "Error",
				text: "Debe seleccionar una opcion de Busqueda",
				icon: "warning",
			});


		}
	});

	$("#idTodos").click(function() {

		if (this.checked) {
			checkTodo();
		} else {
			limpiarcheck();
		}

	});

	$("#btnCancelar").click(function() {
		$("#modalDocumentos").modal("hide");
		limpiarcheck();
	});

	$("#btnRelDoc").click(function() {
		var ids = "";
		//var ids=Array.from(document.querySelectorAll("#documentos input[type='checkbox']:checked"));

		document.querySelectorAll('#documentos input[type=checkbox]:checked').forEach(function(checkElement) {
			if (checkElement.id != 'idTodos') {
				ids += checkElement.id + " ";
			}
		});

		var listaOpciones = "";
		var listaOpcionesAnteriores = "";
		var separador = "/";
		var urlGuardar = "";

		if (ids.trim().length > 0 && idsAnteriores.trim().length == 0) {
			listaOpciones = ids.trim().split(' ');
			urlGuardar = getAbsolutePath() + altaDocumentos + $("#datosRelDoc").val() + separador + listaOpciones;

			enviarPeticion(urlGuardar);
		} else if (ids.trim().length > 0 && idsAnteriores.trim().length > 0) {
			listaOpciones = ids.trim().split(' ');
			listaOpcionesAnteriores = idsAnteriores.trim().split(' ');

			urlGuardar = getAbsolutePath() + actualizarDocumentos + $("#datosRelDoc").val() + separador + listaOpciones + separador + listaOpcionesAnteriores;

			enviarPeticion(urlGuardar);
		} else if (ids.trim().length == 0 && idsAnteriores.trim().length > 0) {
			listaOpcionesAnteriores = idsAnteriores.trim().split(' ');
			urlGuardar = getAbsolutePath() + bajaDocumentos + $("#datosRelDoc").val() + separador + listaOpcionesAnteriores;
			//baja
			enviarPeticion(urlGuardar);
		} else {
			swal({
				title: "",
				text: "No ha seleccionado ninguna opcion",
				icon: "warning",
				confirmButtonText: 'Aceptar'
			})
		}
	});


});

function enviarPeticion(urlGuardar) {

	$.ajax({
		url: urlGuardar,
		type: "POST",
		dataType: "JSON",
		success: function(respuesta) {
			$("#modalDocumentos").modal("hide");
			swal({
				title: "",
				text: respuesta[0],
				icon: "success",
				confirmButtonText: 'Aceptar'
			}).then(resp => {
				limpiarcheck();
			});

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}

function limpiarcheck() {

	document.querySelectorAll('#documentos input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = false;
	});
}

function checkTodo() {

	document.querySelectorAll('#documentos input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = true;
	});
}


function inicioPantalla() {
	$("#productoColocacion").hide();
	$("#productoCaptacion").hide();
	$("#diviKYC").hide();
	$("#divdescripcionProducto").hide();
	$("#contrato").hide();
}

function validarVacio() {
	opcionSeleccionada = $("#opciones").val();
	if (opcionSeleccionada > 0) {
		validaciónNueva(opcionSeleccionada, true);
	} else {
		validaciónNueva(opcionSeleccionada, false);
	}
}


function validaciónNueva(opcionSeleccionada, valor) {
	var textoKYC = document.getElementById("iKYC").value;
	var textoDescripcion = document.getElementById("descripcionProducto").value;
	if (valor) {
		switch (opcionSeleccionada) {
			case "1":
				if ($("#opcionesColocacion").val() == '0') {
					swal({
						title: "Error",
						text: "Debe seleccionar el producto a registrar.",
						icon: "warning",
					});
				} else {
					if ($("#iKYC").val() == '') {
						swal({
							title: "Error, Debe asignar un identificador KYC.",
							text: "Máximo 40 caracteres",
							icon: "warning",
						});
					} else if (textoKYC.length > 40) {
						swal({
							title: "Error, valida el número de caracteres del identificador KYC.",
							text: "Máximo 40 caracteres",
							icon: "warning",
						});
					}
					else if ($("#descripcionProducto").val() == '') {
						swal({
							title: "Error, Debe asignar una descripción al producto.",
							text: "Máximo 30 caracteres",
							icon: "warning",
						});
					} else if (textoDescripcion.length > 30) {
						swal({
							title: "Error, valida el número de caracteres de la descripción del producto.",
							text: "Máximo 30 caracteres",
							icon: "warning",
						});
					}
					else if ($("#opcionesContrato").val() == '0') {
						swal({
							title: "Error",
							text: "Debe seleccionar el contrato a registrar.",
							icon: "warning",
						});

					} else {
						altaProducto();
					}
				}
				break;
			case "2":
				if ($("#opcionesCaptacion").val() == '0') {
					swal({
						title: "Error",
						text: "Debe seleccionar el producto a registrar.",
						icon: "warning",
					});
				} else {
					if ($("#iKYC").val() == '') {
						swal({
							title: "Error, Debe asignar un identificador KYC.",
							text: "Máximo 40 caracteres",
							icon: "warning",
						});
					} else if (textoKYC.length > 40) {
						swal({
							title: "Error, valida el número de caracteres del identificador KYC.",
							text: "Máximo 40 caracteres",
							icon: "warning",
						});
					}
					else if ($("#descripcionProducto").val() == '') {
						swal({
							title: "Error, Debe asignar una descripción al producto.",
							text: "Máximo 30 caracteres",
							icon: "warning",
						});
					} else if (textoDescripcion.length > 30) {
						swal({
							title: "Error, valida el número de caracteres de la descripción del producto.",
							text: "Máximo 30 caracteres",
							icon: "warning",
						});
					} else if ($("#opcionesContrato").val() == '0') {
						swal({
							title: "Error",
							text: "Debe seleccionar el contrato a registrar.",
							icon: "warning",
						});

					} else {
						altaProducto();
					}
				}
				break;
			default:
				swal({
					title: "Error",
					text: "Debe seleccionar el producto a registrar.",
					icon: "warning",
				});
				break;
		}
	} else {
		swal({
			title: "Error",
			text: "Debe seleccionar el tipo de producto a registrar.",
			icon: "warning",
		});
	}

}

function altaProducto() {

	$.ajax({
		url: urlServicio(),
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			swal({
				title: "",
				text: respuesta[0],
				icon: "success",
				confirmButtonText: 'Aceptar'
			}).then(resp => {
				window.location.href = getAbsolutePath() + inicio;
			});
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});


}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function urlServicio() {
	var separador = "/";
	var urlGuardar = getAbsolutePath();
	switch (opcionSeleccionada) {
		case "1":
			urlGuardar = urlGuardar + altaProductos + $("#opcionesColocacion").val()
				+ separador + $('#opcionesColocacion option:selected').html()
				+ separador + opcionSeleccionada
				+ separador + $("#opcionesContrato").val()
				+ separador + $("#iKYC").val()
				+ separador + $("#descripcionProducto").val();
			console.log("UrlGuardar:" + urlGuardar);
			break;
		case "2":
			urlGuardar = urlGuardar + altaProductos + $("#opcionesCaptacion").val()
				+ separador + $('#opcionesCaptacion option:selected').html()
				+ separador + opcionSeleccionada
				+ separador + $("#opcionesContrato").val()
				+ separador + $("#iKYC").val()
				+ separador + $("#descripcionProducto").val();
			console.log("UrlGuardar:" + urlGuardar);
			break;
		default:
			break;
	}

	return urlGuardar;
}

function documentos(btnID) {

	document.querySelector('#lblTitulo').innerText = $(btnID).parents("tr").find("td").eq(2).html();
	$("#datosRelDoc").val($(btnID).parents("tr").find("td").eq(0).html());

	var urlGuardar = getAbsolutePath() + consultaDocumento + $(btnID).parents("tr").find("td").eq(0).html();

	$.ajax({
		url: urlGuardar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			idsAnteriores = "";
			for (var i in respuesta) {
				document.querySelectorAll('#documentos input[type=checkbox]').forEach(function(checkElement) {

					if (checkElement.id != 'idTodos') {
						if (checkElement.id == respuesta[i].fk_documento) {
							idsAnteriores += checkElement.id + " ";
							checkElement.checked = true;
						}
					}
				});
			}

			$("#modalDocumentos").modal("show");

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});

}