// @ts-nocheck
/**
 * Liga absoluta
 */
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/cobranza", "");
/**
 * Fin
 */



/**
 * Funcion principal
 */
var paginaCompleta = document.getElementById("paginaId");
var paginaCarga = document.getElementById("spinnerId");

window.onload = function() {
	role();
	tipoDeBusqueda();
}
/**
 * Fin
 */



/**
 * Obtener la opción de busqueda
 */
var opcionBusqueda = document.getElementById("opcionesId");
var opcionSeleccionada;
var inputBuscar = document.getElementById("buscadorId");

function tipoDeBusqueda() {
	const opcion1 = document.createElement("option");
	const opcion2 = document.createElement("option");
	const opcion3 = document.createElement("option");
	opcion1.value = 1;
	opcion1.text = "Núm Cliente";
	opcionBusqueda.add(opcion1);
	opcion2.value = 2;
	opcion2.text = "Nombre Cliente";
	opcionBusqueda.add(opcion2);
	opcion3.value = 3;
	opcion3.text = "Núm Crédito";
	opcionBusqueda.add(opcion3);
	opcionBusqueda.addEventListener("change", function() {
		opcionSeleccionada = opcionBusqueda.value;
		parametroBus = opcionBusqueda.options[opcionBusqueda.selectedIndex].text;
		if (opcionSeleccionada == 1) {
			limitarCaracteres(inputBuscar, 9)
			// Agrega un controlador de eventos para el evento "input"
			inputBuscar.addEventListener("input", procesoLimite);
		} else {
			inputBuscar.removeEventListener("input", procesoLimite);
		}
	});
}
/**
 * Fin
 */



/**
 * Buscar al Cliente
 */
function validarInformacion(value) {
	valorPar = value;
	// Expresión regular para permitir solo dígitos numéricos
	var regex = /^[0-9]+$/;

	const dto = {
		opcion: opcionSeleccionada,
		valor: value
	}

	if (opcionSeleccionada != undefined) {
		if (value != "") {
			if (opcionSeleccionada == 2) {
				buscarCliente(dto);
			} else {
				if (regex.test(value)) {
					if (opcionSeleccionada == 1) {
						inputBuscar.removeEventListener("input", procesoLimite);
					}
					buscarCliente(dto);
				} else {
					swal("Solo valores númericos", {
						closeOnClickOutside: false,
						icon: "warning",
					});
				}
			}
		} else {
			swal("El campo 'Buscar cliente' está vacío", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	} else {
		swal("Selecciona el tipo de busqueda", {
			closeOnClickOutside: false,
			icon: "warning",
		});
	}
}

function buscarCliente(datos) {
	paginaCompleta.style.display = "none";
	paginaCarga.style.display = "block";
	var enviar = JSON.stringify(datos);
	$.ajax({
		url: newUrlBase + "obtener_cliente",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(resp) {
			// console.log(resp);
			opcionSeleccionada = undefined;
			opcionBusqueda.value = "";
			inputBuscar.value = "";
			infoCliente.style.display = "block";
			mostrarCtaPrincipal.style.display = "block";
			mostrarCredito.style.display = "block";
			paginaCompleta.style.display = "block";
			paginaCarga.style.display = "none";
			registros = [];
			if (resp.length == 0) {
				infoCliente.style.display = "none";
				mostrarCtaPrincipal.style.display = "none";
				mostrarCredito.style.display = "none"
				swal("No se encontró el cliente", {
					closeOnClickOutside: false,
					icon: "warning",
				});
			} else {
				clienteId = resp[0].i_cliente_id;
				mostrarInfoCliente(resp);
			}
		}, error: function(error) {
			paginaCompleta.style.display = "none";
			paginaCarga.style.display = "block";
			swal("Se identifico un error", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */



/**
 * Limitar caracteres
 */
function procesoLimite() {
	// Verifica la longitud del valor del campo de entrada
	if (inputBuscar.value.length > 9) {
		// Si se supera el límite de 9 caracteres, recorta el valor
		inputBuscar.value = inputBuscar.value.slice(0, 9);
	}
}

function limitarCaracteres(input, maxLength) {
	if (input.value.length > maxLength) {
		input.value = input.value.slice(0, maxLength); // Corta el valor si excede la longitud máxima
	}
}
/**
 * Fin
 */



/**
 * Mostrar info del cliente
 */
var infoCliente = document.getElementById("mostrarDatosId");
var infoNumCliente = document.getElementById("numClienteId");
var infoNomCliente = document.getElementById("nomClienteId");

function mostrarInfoCliente(cliente) {
	infoNumCliente.value = cliente[0].i_cliente_id;
	infoNomCliente.value = cliente[0].vc_nombre_cliente;
	validarDatosCtaP(cliente);
	validarDatosCredito(cliente);
	mandarConsulta(cliente);
}
/**
 * Fin
 */



/**
 * Mostrar cuenta principal
 */
var tablaCtaPrincipal = document.getElementById("ctaPrincipalId");
var tbodyCtaPrincipal = tablaCtaPrincipal.querySelector("tbody");
var mostrarCtaPrincipal = document.getElementById("mostrarCtaPrincipalId");

function validarDatosCtaP(ctaPrincipal) {
	var resultado = true;
	tbodyCtaPrincipal.innerHTML = ""; // Limpiar la tabla antes de mostrar los nuevos datos
	for (let i = 1; i < ctaPrincipal.length; i++) {
		if (ctaPrincipal[i].i_referencia_cta_principal_1 == ctaPrincipal[i - 1].i_referencia_cta_principal_1 && ctaPrincipal[i].i_referencia_cta_principal_2 == ctaPrincipal[i - 1].i_referencia_cta_principal_2) {
			resultado = false;
		}
	}
	if (resultado) {
		ctaPrincipal.forEach(function(element) {
			llenarTablaCtaP(element);
		});
	} else {
		llenarTablaCtaP(ctaPrincipal[0]);
	}
}

function llenarTablaCtaP(element) {
	//
	var rows = document.createElement("tr");
	// Núm Cuenta Principal
	var cellNumCtaP = document.createElement("td");
	cellNumCtaP.textContent = element.i_cuenta_aho_id;
	cellNumCtaP.style.textAlign = "justify";
	cellNumCtaP.style.fontSize = "11px";
	rows.appendChild(cellNumCtaP);
	// Referencia 1
	var cellRef1 = document.createElement("td");
	cellRef1.textContent = element.i_referencia_cta_principal_1;
	cellRef1.style.textAlign = "justify";
	cellRef1.style.fontSize = "11px";
	rows.appendChild(cellRef1);
	// Referencia 2
	var cellRef2 = document.createElement("td");
	cellRef2.textContent = element.i_referencia_cta_principal_2;
	cellRef2.style.textAlign = "justify";
	cellRef2.style.fontSize = "11px";
	rows.appendChild(cellRef2);
	// Banco
	var cellBanco = document.createElement("td");
	var contenido = element.i_institucion_id;
	var textoRecortado = contenido.substring(0, 13);
	cellBanco.textContent = textoRecortado;
	cellBanco.style.textAlign = "justify";
	cellBanco.style.fontSize = "11px";
	rows.appendChild(cellBanco);
	//
	tbodyCtaPrincipal.appendChild(rows);
}

/**
 * Fin
 */



/**
 * Mostrar crédito
 */
var tablaCredito = document.getElementById("creditoId");
var tbodyCredito = tablaCredito.querySelector("tbody");
var mostrarCredito = document.getElementById("mostrarCreditoId");

function validarDatosCredito(credito) {
	tbodyCredito.innerHTML = ""; // Limpiar la tabla antes de mostrar los nuevos datos
	credito.forEach(function(element) {
		llenarTablaCredito(element);
	});

}

function llenarTablaCredito(element) {
	//
	var rows = document.createElement("tr");
	// Núm Credito
	var cellNumCrd = document.createElement("td");
	cellNumCrd.textContent = element.i_credito_id;
	cellNumCrd.style.textAlign = "justify";
	cellNumCrd.style.fontSize = "11px";
	rows.appendChild(cellNumCrd);
	// Nombre Producto
	var cellNomProd = document.createElement("td");
	cellNomProd.textContent = element.vc_producto_credito;
	cellNomProd.style.textAlign = "justify";
	cellNomProd.style.fontSize = "11px";
	rows.appendChild(cellNomProd);
	// Referencia
	var cellRef = document.createElement("td");
	cellRef.textContent = element.i_referencia_credito;
	cellRef.style.textAlign = "justify";
	cellRef.style.fontSize = "11px";
	rows.appendChild(cellRef);
	// Banco
	var cellBanco = document.createElement("td");
	contenido = element.i_institucion_id;
	var textoRecortado = contenido.substring(0, 13);
	cellBanco.textContent = textoRecortado;
	cellBanco.style.textAlign = "justify";
	cellBanco.style.fontSize = "11px";
	rows.appendChild(cellBanco);
	//
	tbodyCredito.appendChild(rows);
}

/**
 * Fin
 */



/**
 * Datos de la Consulta
 */
var valorPar;
var parametroBus;
var clienteId;
var registros = [];

function mandarConsulta(datos) {
	datos.forEach(function(element) {
		const dto = {
			parametroBusqueda: parametroBus,
			valorParametro: valorPar,
			numeroCliente: element.i_cliente_id
		};
		registros.push(dto);
	});

	var enviar = JSON.stringify(registros);

	$.ajax({
		url: newUrlBase + "recibir_consulta",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		success: function(resp) {
			if (resp == null) {
				swal("Se identifico un error", {
					closeOnClickOutside: false,
					icon: "warning",
				});
			}
		}, error: function(error) {
			swal("Se identifico un error", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}
/** 
 * Fin
 */



/**
 * Reporte Historial
 */
function validarFechas() {
	if (document.getElementById("dtFechaInicio").value != "" && document.getElementById("dtFechaFin").value != "") {
		generarReporteHrl(document.getElementById("dtFechaInicio").value, document.getElementById("dtFechaFin").value);
	} else {
		swal({
			text: "Hay fechas vacias",
			icon: "warning",
		});
	}
}

function generarReporteHrl(inicio, fin) {
	$.ajax({
		url: newUrlBase + "reporte_historial?inicio=" + inicio + "&fin=" + fin,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			// var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
			var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0];

			window.open(urlReporte);
			window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}

/**
 * Fin
 */



/**
 * Exportar Busqueda
 */
function exportarBusqueda() {
	$.ajax({
		url: newUrlBase + "exportar_cliente?clienteId=" + clienteId,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			// var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
			var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0];

			window.open(urlReporte);
			window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */



/**
 * Mostrar role
 */
var tipoRole;
var exportarClientes = document.getElementById("exportar");
var reporteHistorial = document.getElementById("reporte");

function role() {
	$.ajax({
		url: newUrlBase + "mostrar_role",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			tipoRole = resp[0];
			if (tipoRole == "ROLE_COBRANZA_RP_GENEAR_REP") {
				exportarClientes.style.display = "block";
				reporteHistorial.style.display = "block";
			}
		},
		error: function(error) {
			// console.log(error);
			swal("Hubo un error", {
				closeOnClickOutside: false,
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */