// @ts-nocheck
var url = "";
var expOptionSelected = "";
var searchOptionSelected = "";

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
		if (divA !== checkElement.id) {
			checkElement.style.display = "none";
		}
	});
}

function getAbsolutePath() {
	return window.location.href.substring(0, window.location.href.indexOf("STD/") + 4);
}

function cerrarModal(idModal) {
	const modalElement = document.getElementById(idModal);
	if (!modalElement) return;

	const modal = bootstrap.Modal.getInstance(modalElement) || new bootstrap.Modal(modalElement);
	modal.hide();
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
			const texto = data?.mensaje || "No hay texto disponible.";
			const id = data.idConfig;
			const estatus = data.activo;

			const plantillaTexto = document.getElementById("plantilla-texto-enviar");
			const plantillaId = document.getElementById("id-plantilla-sms");
			const plantillaEstatus = document.getElementById("select-estatus-plantilla");

			if (plantillaTexto) plantillaTexto.value = texto;
			if (plantillaId) plantillaId.value = id;
			if (plantillaEstatus) plantillaEstatus.value = estatus === 1 ? 1 : 0;
		})
		.catch((error) => {
			console.error("Error al obtener las configuraciones SMS:", error);
		});
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
			Swal.fire({
				icon: "success",
				title: "¡Guardado exitoso!",
				text: "La plantilla se guardó correctamente.",
				allowOutsideClick: false
			}).then(() => {
				cerrarModal("editPlantillaModal");
				listaPlantillas();
			});
		})
		.catch((error) => {
			console.error("Error al actualizar la plantilla:", error);
			Swal.fire({
				icon: "error",
				title: "Error",
				text: "Ocurrió un problema al guardar la plantilla. Inténtalo de nuevo.",
				allowOutsideClick: false
			});
		});
}

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
			const idProceso = data.idProceso;
			const idConfigMensual = data.idConfigMensual;
			const tipoCredito = data.tipoCredito;
			const mes = data.nomMes;
			const mensajeSMS = data.mensaje;
			const numSMS = data.numSMS;
			const limiteSMS = data.limiteSMS;
			const smsEnviados = data.smsEnviados;

			const idGestion = document.getElementById("id-gestion-sms");
			const idConfig = document.getElementById("id-config-mensual");
			const mesGestion = document.getElementById("mes-gestion");
			const tipoCreditoGestion = document.getElementById("tipo-credito-gestion");
			const smsGestion = document.getElementById("sms-gestion");
			const selectLimite = document.getElementById("select-limite-sms");
			const divMaximoSMS = document.getElementById("divMaximoSMS");
			const maximoSms = document.getElementById("maximo-sms");
			const maxSmsAnterior = document.getElementById("max-sms-anterior");

			if (idGestion) idGestion.value = idProceso;
			if (idConfig) idConfig.value = idConfigMensual;
			if (mesGestion) mesGestion.value = mes;
			if (tipoCreditoGestion) tipoCreditoGestion.value = tipoCredito;
			if (smsGestion) smsGestion.value = mensajeSMS;

			switch (limiteSMS) {
				case 0:
					if (selectLimite) selectLimite.value = 0;
					if (divMaximoSMS) divMaximoSMS.hidden = true;
					if (maximoSms) maximoSms.value = smsEnviados;
					if (maxSmsAnterior) maxSmsAnterior.value = smsEnviados;
					break;
				case 1:
					if (selectLimite) selectLimite.value = 1;
					if (divMaximoSMS) divMaximoSMS.hidden = false;
					if (maximoSms) maximoSms.value = numSMS;
					if (maxSmsAnterior) maxSmsAnterior.value = numSMS;
					break;
				case null:
					if (selectLimite) selectLimite.value = "";
					if (divMaximoSMS) divMaximoSMS.hidden = true;
					break;
			}
		})
		.catch((error) => {
			console.error("Error al obtener las configuraciones SMS:", error);
		});
}

function actualizarConfigSMSMensual(idSMSConfigMensual, limiteSMS, maxSMS) {
	const url = `${getAbsolutePath()}marketing/actualizar-configuracion-mensual`;

	const payload = {
		idSMSConfigMensual: Number(idSMSConfigMensual),
		limiteSMS: Number(limiteSMS),
		maxSMS: Number(maxSMS)
	};

	const selectTipoCredito = document.getElementById("select-tipo-credito");
	const selectedValue = selectTipoCredito ? selectTipoCredito.value : "";

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
			Swal.fire({
				icon: "success",
				title: "¡Guardado exitoso!",
				text: "La gestión del mensaje se guardó correctamente.",
				allowOutsideClick: false
			}).then(() => {
				cerrarModal("editProcessModal");

				switch (selectedValue) {
					case "N":
					case "O":
						listaGestiones(0, selectedValue);
						break;
					case "":
						listaGestiones(1, selectedValue);
						break;
				}
			});
		})
		.catch((error) => {
			console.error("Error al actualizar la gestión del mensaje:", error);
			Swal.fire({
				icon: "error",
				title: "Error",
				text: "Ocurrió un problema al guardar la gestión del mensaje. Inténtalo de nuevo.",
				allowOutsideClick: false
			});
		});
}

function modalProcess(idProcess, estatus, month) {
	const tittleModal = document.getElementById("process-modal-tittle");
	const textModal = document.getElementById("process-modal-text");

	if (estatus == 4) {
		if (tittleModal) tittleModal.innerText = "Pausar proceso de envío SMS";
		if (textModal) textModal.innerHTML = "¿Estás seguro de pausar el envío de SMS para el mes de <strong>" + month + "</strong>?<br>Una vez realizando está acción se detendrá el envío de SMS.";
		getModalForm(idProcess, estatus);
	}

	if (estatus == 6) {
		if (tittleModal) tittleModal.innerText = "Reanudar proceso de envío de SMS";
		if (textModal) textModal.innerHTML = "¿Estás seguro de reanudar el envío de SMS el mes de <strong>" + month + "</strong>?<br>Una vez realizando está acción se detonará el envío de SMS.";
		getModalForm(idProcess, estatus);
	}
}

function actualizarProceso(idSMSProceso, estatus) {
	const url = `${getAbsolutePath()}marketing/actualizar-estatus-gestion`;

	const payload = {
		idSMSProceso: Number(idSMSProceso),
		estatus: Number(estatus)
	};

	const selectTipoCredito = document.getElementById("select-tipo-credito");
	const selectedValue = selectTipoCredito ? selectTipoCredito.value : "";

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
			Swal.fire({
				icon: "error",
				title: "Error",
				text: "Ocurrió un problema al actualizar el proceso de envío. Inténtalo de nuevo.",
				allowOutsideClick: false
			});
		});
}

function getModalForm(idSMSProceso, estatus) {
	const form = document.getElementById("changeStatusProcessForm");
	if (!form) return;

	const nuevoForm = form.cloneNode(true);
	form.parentNode.replaceChild(nuevoForm, form);

	nuevoForm.addEventListener("submit", function(event) {
		event.preventDefault();
		actualizarProceso(idSMSProceso, estatus);
	});
}

document.addEventListener("DOMContentLoaded", function() {
	const divTblGestionSMS = document.getElementById("div-tblGestionSMS");
	if (divTblGestionSMS) {
		divTblGestionSMS.hidden = true;
	}

	const credencialesForm = document.getElementById("credencialesForm");
	if (credencialesForm) {
		credencialesForm.addEventListener("submit", function(event) {
			event.preventDefault();

			const form = event.target;
			const editableInputs = form.querySelectorAll("input:not([readonly])");

			const formData = {};
			editableInputs.forEach(input => {
				formData[input.name] = input.value;
			});

			fetch(form.action, {
				method: form.method,
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(formData)
			})
				.then(response => response.json())
				.then(data => {
					Swal.fire({
						icon: "success",
						title: "¡Guardado exitoso!",
						text: "Los datos se han guardado correctamente.",
						allowOutsideClick: false
					}).then(() => {
						location.reload();
					});
				})
				.catch(error => {
					console.error("Hubo un problema con el fetch:", error);
					Swal.fire({
						icon: "error",
						title: "¡Error!",
						text: "Ocurrió un error al guardar las credenciales. Intenta nuevamente.",
						allowOutsideClick: false
					});
				});
		});
	}

	const selectTipoCredito = document.getElementById("select-tipo-credito");
	if (selectTipoCredito) {
		selectTipoCredito.addEventListener("change", function() {
			const selectedValue = this.value;
			const divTblGestionSMS = document.getElementById("div-tblGestionSMS");
			if (!divTblGestionSMS) return;

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
					divTblGestionSMS.hidden = true;
					break;
			}
		});
	}

	const plantillasForm = document.getElementById("plantillasForm");
	if (plantillasForm) {
		plantillasForm.addEventListener("submit", function(event) {
			event.preventDefault();

			const idSMSConfig = document.getElementById("id-plantilla-sms")?.value || "";
			const texto = document.getElementById("plantilla-texto-enviar")?.value || "";
			const estatus = document.getElementById("select-estatus-plantilla")?.value || "";

			if (!texto.trim()) {
				Swal.fire({
					icon: "error",
					title: "Error",
					text: "El texto del mensaje no puede estar vacío.",
					allowOutsideClick: false
				});
				return;
			}

			if (estatus === "") {
				Swal.fire({
					icon: "error",
					title: "Error",
					text: "Por favor seleccionar un estatus válido.",
					allowOutsideClick: false
				});
				return;
			}

			actualizarPlantilla(idSMSConfig, texto, estatus);
		});
	}

	const selectLimiteSms = document.getElementById("select-limite-sms");
	if (selectLimiteSms) {
		selectLimiteSms.addEventListener("change", function() {
			const selectedValue = this.value;
			const divMaximoSMS = document.getElementById("divMaximoSMS");
			if (!divMaximoSMS) return;

			switch (selectedValue) {
				case "":
					break;
				case "1":
					divMaximoSMS.hidden = false;
					break;
				case "0":
					divMaximoSMS.hidden = true;
					break;
			}
		});
	}

	const gestionSMSForm = document.getElementById("gestionSMSForm");
	if (gestionSMSForm) {
		gestionSMSForm.addEventListener("submit", function(event) {
			event.preventDefault();

			const idSMSConfigMensual = document.getElementById("id-config-mensual")?.value || "";
			const limiteSMS = document.getElementById("select-limite-sms")?.value || "";
			const maxSMS = document.getElementById("maximo-sms")?.value || "";
			const maxSMSAnterior = document.getElementById("max-sms-anterior")?.value || "";

			if (limiteSMS === "") {
				Swal.fire({
					icon: "error",
					title: "Error",
					text: "Por favor seleccionar una opción válida.",
					allowOutsideClick: false
				});
				return;
			}

			if (limiteSMS === "1") {
				if (Number(maxSMS) < Number(maxSMSAnterior)) {
					Swal.fire({
						icon: "error",
						title: "Error",
						text: "El máximo SMS ingresado es menor al previamente registrado.",
						allowOutsideClick: false
					});
					return;
				}

				if (Number(maxSMS) === Number(maxSMSAnterior)) {
					Swal.fire({
						icon: "error",
						title: "Error",
						text: "El máximo SMS ingresado es igual al previamente registrado.",
						allowOutsideClick: false
					});
					return;
				}
			}

			actualizarConfigSMSMensual(idSMSConfigMensual, limiteSMS, maxSMS);
		});
	}

	const maximoSms = document.getElementById("maximo-sms");
	if (maximoSms) {
		maximoSms.addEventListener("keydown", function(e) {
			if (e.key === "-" || e.key === "e") {
				e.preventDefault();
			}
		});
	}
});

$(document).ready(function () {
	$("#visorImagenes").on("hidden.bs.modal", function() {
		$(".zoomContainer").remove();
	});
});