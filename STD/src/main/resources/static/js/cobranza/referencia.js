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
	datosHistorial();
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
			datosHistorial();
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
 * Historial de Busqueda
 */
var mostarHrl = document.getElementById("verHrlId");
var tablaHrl = document.getElementById("historialId");
var tbodyHrl = tablaHrl.querySelector("tbody");
var dtoHrl;
const resultadoHrl = 5;
let actualHrl = 1;

function datosHistorial() {
	$.ajax({
		url: newUrlBase + "historial",
		type: "GET",
		dataType: "JSON",
		success: function(resp) {
			if (resp.length > 0) {
				mostarHrl.style.display = "block";
				dtoHrl = resp;
				inicioHrl();
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

function inicioHrl() {
	tbodyHrl.innerHTML = ""; // Limpiar la tabla antes de mostrar los nuevos datos
	const inicio = (actualHrl - 1) * resultadoHrl;
	const fin = inicio + resultadoHrl;
	const datos = dtoHrl.slice(inicio, fin);
	datos.forEach(function(element) {
		var rows = document.createElement("tr");

		var cell1 = document.createElement("td");
		cell1.textContent = element.pk_id_consulta_rp;
		//cell1.style.textAlign = "justify";
		cell1.style.fontSize = "11px";
		rows.appendChild(cell1);

		var cell2 = document.createElement("td");
		cell2.textContent = element.vc_nombre_usuario;
		cell2.style.fontSize = "11px";
		rows.appendChild(cell2);

		var cell3 = document.createElement("td");
		cell3.textContent = element.dt_fecha_registro;
		cell3.style.fontSize = "11px";
		rows.appendChild(cell3);

		var cell4 = document.createElement("td");
		cell4.textContent = element.vc_parametro_busqueda;
		cell4.style.fontSize = "11px";
		rows.appendChild(cell4);

		var cell5 = document.createElement("td");
		cell5.textContent = element.vc_valor_parametro;
		//cell5.style.textAlign = "justify";
		cell5.style.fontSize = "11px";
		rows.appendChild(cell5);

		var cell6 = document.createElement("td");
		cell6.textContent = element.i_numero_cliente;
		cell6.style.fontSize = "11px";
		rows.appendChild(cell6);

		var cell7 = document.createElement("td");
		cell7.textContent = element.vc_nombre_cliente;
		cell7.style.fontSize = "11px";
		rows.appendChild(cell7);

		var cell8 = document.createElement("td");
		cell8.textContent = element.i_numero_cuenta_principal_1;
		cell8.style.fontSize = "11px";
		rows.appendChild(cell8);

		var cell14 = document.createElement("td");
		cell14.textContent = element.i_numero_cuenta_principal_2;
		cell14.style.fontSize = "11px";
		rows.appendChild(cell14);

		var cell10 = document.createElement("td");
		cell10.textContent = element.i_numero_credito;
		cell10.style.fontSize = "11px";
		rows.appendChild(cell10);

		var cell11 = document.createElement("td");
		cell11.textContent = element.vc_producto_credito;
		cell11.style.fontSize = "11px";
		rows.appendChild(cell11);

		var cell12 = document.createElement("td");
		cell12.textContent = element.i_referencia_banco_credito;
		cell12.style.fontSize = "11px";
		rows.appendChild(cell12);

		var cell13 = document.createElement("td");
		var contenido = element.vc_nombre_banco_credito;
		var textoRecortado = contenido.substring(0, 13);
		cell13.textContent = textoRecortado;
		cell13.style.fontSize = "11px";
		rows.appendChild(cell13);

		tbodyHrl.appendChild(rows);
	});
	botonesHrl();
}

// Botones del Paginado
function botonesHrl() {
	const paginationContainer = document.getElementById("paginadoId");
	paginationContainer.innerHTML = ""; // Limpiar los botones de paginación
	const totalPages = Math.ceil(dtoHrl.length / resultadoHrl);
	for (let i = 1; i <= totalPages; i++) {
		const button = document.createElement("button");
		button.textContent = i;
		// Establecer los estilos en línea usando la propiedad style
		button.style.backgroundColor = "#85929E";
		button.style.border = "none";
		button.style.color = "white";
		button.style.padding = "8px 16px";
		button.style.margin = "2px";
		// Aplicar estilos al botón activo
		if (i === actualHrl) {
			button.style.backgroundColor = "#007bff";
		}
		// Agrega más estilos si es necesario
		button.addEventListener("click", function() {
			actualHrl = i;
			inicioHrl();
			botonesHrl();
		});
		paginationContainer.appendChild(button);
	}
}

/**
 * Fin
 */



/**
 * Reporte Historial
 */
function generarReporteHrl() {
	$.ajax({
		url: newUrlBase + "reporte_historial",
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
			var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/")+4) + respuesta[0];

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
			var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/")+4) + respuesta[0];

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