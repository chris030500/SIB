$(document).ready(function() {
	$('.solo-numero').on('input', function() {
		this.value = this.value.replace(/[^0-9]/g, '');
	});
});


function verReporte() {
	if (datoVacio() == false) {
		if (valRangos() == false) {
			var servicioReporte = "report/registrarDatosCliente?nombre=";
			var separador = "/";
			var nombreCliente = document.getElementById("inputNombre").value;
			var direccion = document.getElementById("inputDireccion").value;
			var capital = document.getElementById("inputCapital").value;
			var tasaOrdinaria = document.getElementById("inputTasaOrdinaria").value;
			var tasaMoratoria = document.getElementById("inputTasaMoratoria").value;
			var cat = document.getElementById("cat").value;
			var reca = document.getElementById("reca").value;
			var solicitud = document.getElementById("inputSolicitud").value;

			var servicioReporte = servicioReporte + nombreCliente + "&direccion=" + direccion + "&capital=" + capital + "&tasaOrdinaria=" + tasaOrdinaria + "&tasaMoratoria=" + tasaMoratoria + "&cat=" + cat + "&reca=" + reca + "&solicitud=" + solicitud;
			var urlGuardar = getAbsolutePath() + servicioReporte;

			$.ajax({
				url: urlGuardar,
				type: "POST",
				dataType: "JSON",
				success: function(respuesta) {

					if (respuesta == null || respuesta == '') {
						// console.log("Error");
						swal({
							title: "Error",
							text: "La solicitud que ingresaste no existe",
							icon: "warning",
						});
					} else {

						var servicioReporte = "report/contratoSTD/";
						var separador = "/";
						/*var nombreCliente = document.getElementById("inputNombre").value;
						var direccion = document.getElementById("inputDireccion").value;
						var capital = document.getElementById("inputCapital").value;
						var tasaOrdinaria = document.getElementById("inputTasaOrdinaria").value;
						var tasaMoratoria = document.getElementById("inputTasaMoratoria").value;*/


						var urlReporte = getAbsolutePath() + servicioReporte + respuesta.pk_id_contrato + separador + respuesta.i_no_solicitud;
						window.open(urlReporte, '_blank');
						window.focus();
					}

				}, error: function(XMLHttpRequest, textStatus, errorThrown) {
					swal({
						title: "Error, ",
						text: "---",
						icon: "warning",
					});
				}
			});
		} else {
			swal({
				title: "Error",
				text: "Datos incorrectos en CAT",
				icon: "warning",
			});
		}
	} else {
		swal({
			title: "Error",
			text: "Todos los datos son obligatorios",
			icon: "warning",
		});
	}
}

function datoVacio() {
	var val = false;

	if (document.getElementById("inputNombre").value.length == 0) {

		document.getElementById("inputNombre").style.borderColor = "#F70707";
		val = true;
	} else if (document.getElementById("inputDireccion").value.length == 0) {
		document.getElementById("inputDireccion").style.borderColor = "#F70707";
		val = true;
	} else if (document.getElementById("inputCapital").value.length == 0) {
		document.getElementById("inputCapital").style.borderColor = "#F70707";
		val = true;
	} else if (document.getElementById("inputTasaOrdinaria").value.length == 0) {
		document.getElementById("inputTasaOrdinaria").style.borderColor = "#F70707";
		val = true;
	} else if (document.getElementById("inputTasaMoratoria").value.length == 0) {
		document.getElementById("inputTasaMoratoria").style.borderColor = "#F70707";
		val = true;
	} else if (document.getElementById("cat").value.length == 0) {
		document.getElementById("cat").style.borderColor = "#F70707";
		val = true;
	}

	return val
}

function valRangos() {
	var val = false;
	if (document.getElementById("cat").value <= 0) {
		val = true;
	}
	return val;
}




function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function buscarDatosSolicitud() {

	var solicitudCredito = document.getElementById('inputSolicitud').value;


	if (solicitudCredito.length = 0) {
		// console.log("ingresar solicud");
		swal("Precaucion", "Ingrese una solicitud.", "info");
	} else {
		// console.log("Si se ha seleccionado una opción en usuario");
		$.ajax({
			url: getAbsolutePath() + "report/buscaDatos/" + solicitudCredito,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				// console.log(respuesta);

				if (respuesta == null || respuesta == '') {
					// console.log("Error");
					swal({
						title: "Error",
						text: "La solicitud que ingresaste no existe",
						icon: "warning",
					});
				} else {
					if (respuesta.length > 0) {
						mostrarProducto(respuesta);
					} else {
						swal({
							title: "Error",
							text: respuesta[0].mensajeError,
							icon: "warning",
						});
					}
				}

			}, error: function(XMLHttpRequest, textStatus, errorThrown) {
				swal({
					title: "Error, No hay registro.",
					text: "No se encontro informacion para los parametro ingresados.",
					icon: "warning",
				});
			}
		});
	}
}

function buscaCliente(solicitudCreditoID) {

	$.ajax({
		url: getAbsolutePath() + "report/buscaCliente/" + solicitudCreditoID,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			if (respuesta == null || respuesta == '') {
				// console.log("Error");
				swal({
					title: "Error",
					text: "Datos del cliente no encontrados",
					icon: "warning",
				});
			} else {


				//Asignamos los valores a los campos
				inputNombre.value = respuesta[0].nombreCompleto;
				inputDireccion.value = respuesta[0].direccionCompleta;

				valAnterior.value = "" + inputSolicitud.value + "|" + inputNombre.value + "|" + inputDireccion.value + "|" + inputCapital.value + "|" + inputTasaOrdinaria.value + "|" + inputTasaMoratoria.value;

			}

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, No hay registro.",
				text: "No se encontro informacion para los parametro ingresados.",
				icon: "warning",
			});
		}
	});

}


/**
		Función para validar si el producto cuenta con un contrato valido
 */

// Validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
// console.log(urlPrincipal, "urlPrincipal");
var newUrlBase = urlPrincipal.replace("/contrato", "");
// console.log("newUrlBASE: " + newUrlBase)
var contrato;
function mostrarProducto(datos) {
	$.ajax({
		url: newUrlBase + "unico_producto?numero=" + datos[0].productoCreditoID,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			contrato = respuesta;
			if (contrato) {
				// console.log('entró');
				//Asignamos los valores a los campos
				inputSolicitud.value = datos[0].solicitudCreditoID;
				inputCapital.value = datos[0].montoAutorizado;
				inputTasaOrdinaria.value = datos[0].tasaFija;
				inputTasaMoratoria.value = datos[0].tasaMoratoria;
				reca.value = datos[0].registroReca;
				buscaCliente(datos[0].solicitudCreditoID);
			} else {
				swal({
					text: "El producto de la solicitud no cuenta con un contrato valido",
					icon: "warning",
				});
			}
		}, error: function(error) {
			// console.log(error);
		}
	})
}