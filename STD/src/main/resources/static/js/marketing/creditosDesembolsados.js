function ocultarMostrar(divEl) {
	const id = $(divEl).attr("id");
	const idDiv = id + 1;
	const opcion = "#pant-tabs" + " #" + idDiv;
	const forValue = $(opcion).attr("for");

	switch (forValue) {
		case "1":
			console.log("Credenciales calixta");
			break;
		case "2":
			listaPlantillas();
			break;
		case "3":
			console.log("Gestión SMS");
			break;
		default:
			break;
	}

	mostrarOcultarDiv(idDiv);
}

function mostrarOcultarDiv(divA) {
	var opcion = document.getElementById(divA);
	if (opcion) {
		opcion.style.display = "block";
	}

	document.querySelectorAll('#pant-tabs div[style*="display: block;"]').forEach(function(checkElement) {
		console.log("mostrar Datos");
		if (divA !== checkElement.id) {
			checkElement.style.display = "none";
		} else {
			console.log(": Mostrar");
		}

	});

}

document.getElementById("credencialesForm").addEventListener("submit", function(event) {
	event.preventDefault(); // Evita que el formulario recargue la página automáticamente

	const form = event.target; // Obtén el formulario que disparó el evento
	const editableInputs = form.querySelectorAll("input:not([readonly])"); // Inputs editables dentro del formulario

	const formData = {};
	editableInputs.forEach(input => {
		formData[input.name] = input.value; // Almacena el valor usando el atributo "name" como clave
	});

	console.log(formData); // Solo para depuración, verifica que tengas los datos correctos

	fetch(form.action, { // Usa el atributo action del formulario
		method: form.method, // Usa el atributo method del formulario
		headers: {
			"Content-Type": "application/json" // Especifica que enviarás datos en formato JSON
		},
		body: JSON.stringify(formData) // Convierte el objeto `formData` a JSON
	})
		.then(response => response.json()) // Procesa la respuesta como JSON
		.then(data => {
			//console.log("Respuesta del servidor:", data);
			swal(
				"¡Guardado exitoso!",
				"Los datos se han guardado correctamente.",
				"success"
			)

			setTimeout(function() {
				swal.close(); // Cierra el SweetAlert
			}, 2000);
			location.reload();
		})
		.catch(error => {
			console.error("Hubo un problema con el fetch:", error);
			swal(
				"¡Error!",
				"Ocurrió un error al guardar las credenciales. Intenta nuevamente.",
				"error"
			)

			setTimeout(function() {
				swal.close(); // Cierra el SweetAlert
			}, 2000);
		});
});

function getAbsolutePath() {

	return window.location.href.substring(0, window.location.href.indexOf("STD/") + 4);

}

function editPlantilla(idSMSConfig) {

	const url = `${getAbsolutePath()}marketing/obtener-plantilla?idSMSConfig=${idSMSConfig}`;

	fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		}
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Error en la respuesta del servidor");
			}
			return response.json();
		})
		.then((data) => {
			//console.log("Configuracion SMS: ", data);
			const texto = data?.mensaje || "No hay texto disponible.";
			const id = data.idConfig;
			const estatus = data.activo;

			document.getElementById("plantilla-texto-enviar").value = texto;
			document.getElementById("id-plantilla-sms").value = id;
			document.getElementById("select-estatus-plantilla").value = estatus === 1 ? 1 : 0;
		})
		.catch((error) => {
			console.error("Error al obtener las configuraciones SMS:", error);
		})
}

function cerrarModal(idModal) {
	const modalElement = document.getElementById(idModal);
	const modal = bootstrap.Modal.getInstance(modalElement);
	modal.hide();
}

function actualizarPlantilla(idSMSConfig, texto, estatus) {

	const url = `${getAbsolutePath()}marketing/guardar-plantilla`;

	const payload = {
		idSMSConfig: Number(idSMSConfig),
		texto: texto,
		estatus: Number(estatus)
	};

	fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(payload),
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Error al actualizar la plantilla");
			}
			return response.json();
		})
		.then((data) => {
			//console.log("Plantilla actualizada: ", data);
			swal("¡Guardado exitoso!", "La plantilla se guardó correctamente.", "success");
			setTimeout(function() {
				swal.close(); // Cierra el SweetAlert
			}, 2000);

			cerrarModal("editPlantillaModal");
			listaPlantillas();
		})
		.catch((error) => {
			console.error("Error al actualizar la plantilla:", error);
			swal("Error", "Ocurrió un problema al guardar la plantilla. Inténtalo de nuevo.", "error");
		});
}

document.getElementById("select-tipo-credito").addEventListener("change", function() {
	const selectedValue = this.value; // Obtiene el valor de la opción seleccionada
	const divTblGestionSMS = document.getElementById("div-tblGestionSMS");
	//console.log("Opción seleccionada:", selectedValue);

	switch (selectedValue) {
		case "N":
		case "O":
			divTblGestionSMS.hidden = false;
			listaGestiones(0, selectedValue);
			break;
		case "":
			divTblGestionSMS.hidden = false;
			listaGestiones(1, selectedValue);
			break;
		case "0":
			console.log("No es una opción válida.");
			divTblGestionSMS.hidden = true;
	}

});

document.getElementById("plantillasForm").addEventListener("submit", function(event) {
	event.preventDefault(); // Evita la recarga de la página

	const idSMSConfig = document.getElementById("id-plantilla-sms").value;
	const texto = document.getElementById("plantilla-texto-enviar").value;
	const estatus = document.getElementById("select-estatus-plantilla").value;

	// Validaciones
	if (!texto.trim()) {
		swal("Error", "El texto del mensaje no puede estar vacío.", "error");
		return;
	}

	if (estatus === "") {
		swal("Error", "Por favor seleccionar un estatus válido.", "error");
		return;
	}

	// Llama a la función para actualizar la plantilla
	actualizarPlantilla(idSMSConfig, texto, estatus);
});

document.getElementById("select-limite-sms").addEventListener("change", function() {
	const selectedValue = this.value; // Obtiene el valor de la opción seleccionada
	const divMaximoSMS = document.getElementById("divMaximoSMS");
	//console.log("Opción seleccionada:", selectedValue);

	switch (selectedValue) {
		case "":
			console.log("No es una opción válida.");
			break;
		case "1":
			divMaximoSMS.hidden = false;
			break;
		case "0":
			divMaximoSMS.hidden = true;
	}

});

function editProcess(idSMSProcess) {

	const url = `${getAbsolutePath()}marketing/obtener-gestion?idSMSProcess=${idSMSProcess}`;

	fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		}
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Error en la respuesta del servidor");
			}
			return response.json();
		})
		.then((data) => {
			console.log("Gestión SMS: ", data);
			const idProceso = data.idProceso;
			const idConfigMensual = data.idConfigMensual
			const tipoCredito = data.tipoCredito;
			const mes = data.nomMes
			const mensajeSMS = data.mensaje;
			const numSMS = data.numSMS;
			const limiteSMS = data.limiteSMS;

			document.getElementById("id-gestion-sms").value = idProceso;
			document.getElementById("id-config-mensual").value = idConfigMensual;
			document.getElementById("mes-gestion").value = mes;
			document.getElementById("tipo-credito-gestion").value = tipoCredito;
			document.getElementById("sms-gestion").value = mensajeSMS;
			document.getElementById("maximo-sms").value = numSMS;
			document.getElementById("max-sms-anterior").value = numSMS;

			switch (limiteSMS) {
				case 0: 
					document.getElementById("select-limite-sms").value = 0;
					document.getElementById("divMaximoSMS").hidden = true;
					break;
				case 1:
					document.getElementById("select-limite-sms").value = 1;
					document.getElementById("divMaximoSMS").hidden = false;
					break;
				case null:
					document.getElementById("select-limite-sms").value = "";
					document.getElementById("divMaximoSMS").hidden = true;
					
			}
		})
		.catch((error) => {
			console.error("Error al obtener las configuraciones SMS:", error);
		})
}

function actualizarConfigSMSMensual(idSMSConfigMensual, limiteSMS, maxSMS) {

	const url = `${getAbsolutePath()}marketing/actualizar-configuracion-mensual`;

	const payload = {
		idSMSConfigMensual: Number(idSMSConfigMensual),
		limiteSMS: Number(limiteSMS),
		maxSMS: Number(maxSMS)
	};

	const selectedValue = document.getElementById("select-tipo-credito").value;

	fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(payload),
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Error al actualizar la gestión del mensaje.");
			}
			return response.json();
		})
		.then((data) => {
			//console.log("Plantilla actualizada: ", data);
			swal("¡Guardado exitoso!", "La gestión del mensaje se guardó correctamente.", "success");
			setTimeout(function() {
				swal.close(); // Cierra el SweetAlert
			}, 2000);

			cerrarModal("editProcessModal");
			//console.log("Select value: " + selectedValue);
			switch (selectedValue) {
				case "N":
				case "O":
					listaGestiones(0, selectedValue);
					break;
				case "":
					listaGestiones(1, selectedValue);
					break;
			}
		})
		.catch((error) => {
			console.error("Error al actualizar la gestión del mensaje:", error);
			swal("Error", "Ocurrió un problema al guardar la gestión del mensaje. Inténtalo de nuevo.", "error");
		});
}

document.getElementById("gestionSMSForm").addEventListener("submit", function(event) {
	event.preventDefault(); // Evita la recarga de la página

	const idSMSConfigMensual = document.getElementById("id-config-mensual").value;
	const limiteSMS = document.getElementById("select-limite-sms").value;
	const maxSMS = document.getElementById("maximo-sms").value;
	const maxSMSAnterior = document.getElementById("max-sms-anterior").value;

	// Validaciones
	if (limiteSMS === "") {
		swal("Error", "Por favor seleccionar una opción válida.", "error");
		return;
	}
	
	if (maxSMS < maxSMSAnterior) {
		swal("Error", "El máximo SMS ingresado es menor al prevíamente registrado.", "error");
		return;
	}

	actualizarConfigSMSMensual(idSMSConfigMensual, limiteSMS, maxSMS);
});

function modalProcess(idProcess, estatus, month) {

	const tittleModal = document.getElementById("process-modal-tittle");
	const textModal = document.getElementById("process-modal-text");

	if (estatus == 4) {
		tittleModal.innerText = "Pausar proceso de envío SMS";
		textModal.innerHTML = "¿Estás seguro de pausar el envío de SMS para el mes de <strong>" + month + "</strong>?<br>Una vez realizando está acción se dentendrá el envío de SMS.";
		getModalForm(idProcess, estatus);
	}

	if (estatus == 6) {
		tittleModal.innerText = "Reanudar proceso de envío de SMS";
		textModal.innerHTML = "¿Estás seguro de reanudar el envío de SMS el mes de <strong>" + month + "</strong>?<br>Una vez realizando está acción se dentonará el envío de SMS.";
		getModalForm(idProcess, estatus);
	}
}

function actualizarProceso(idSMSProceso, estatus) {

	const url = `${getAbsolutePath()}marketing/actualizar-estatus-gestion`;

	const payload = {
		idSMSProceso: Number(idSMSProceso),
		estatus: Number(estatus)
	};

	const selectedValue = document.getElementById("select-tipo-credito").value;

	fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(payload),
	})
		.then((response) => {
			if (!response.ok) {
				throw new Error("Error al actualizar el estatus del proceso.");
			}
			return response.json();
		})
		.then((data) => {
			//console.log("Proceso actualizado: ", data);
			cerrarModal("statusProcessModal");
			switch (selectedValue) {
				case "N":
				case "O":
					listaGestiones(0, selectedValue);
					break;
				case "":
					listaGestiones(1, selectedValue);
					break;
			}
		})
		.catch((error) => {
			console.error("Error al actualizar el proceso SMS:", error);
			swal("Error", "Ocurrió un problema al actualizar el proceso de envío. Inténtalo de nuevo.", "error");
		});
}

function getModalForm(idSMSProceso, estatus) {
	document.getElementById("changeStatusProcessForm").addEventListener("submit", function(event) {
		event.preventDefault(); // Evita la recarga de la página

		// Llama a la función para actualizar la plantilla
		actualizarProceso(idSMSProceso, estatus);
	});
}

document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("div-tblGestionSMS").hidden = true;
})





