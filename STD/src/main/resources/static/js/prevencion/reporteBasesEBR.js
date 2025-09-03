function mostrarTabs() {
	document.querySelectorAll('#pntConf button[data-bs-toggle="tab"]').forEach(tab => {
		tab.addEventListener('shown.bs.tab', function() {
			const target = document.querySelector(this.dataset.bsTarget);
			const forValue = target.getAttribute("for");

			switch (forValue) {
				case "1":
					console.log("Procesar Bases EBR");
					break;
				case "2":
					console.log("Generar Bases EBR");
					break;
				case "3":
					listaHistoricoBases();
					break;
			}
		});
	});
}

function mostrarRangoFechas() {
	const periodoSelect = document.getElementById('periodoSelect');
	const dtBasesEbrDiv = document.getElementById('dtBasesEbrDiv');
	const btnProcesarBasesEBR = document.getElementById('btnProcesarBasesEBR');
	
	btnProcesarBasesEBR.addEventListener('click', () => {
		let selectValue = parseInt(periodoSelect.value);
		let fechaActual = new Date();
		let anioActual = fechaActual.getFullYear();
		let fechaInicio, fechaFin;
		
		console.log(selectValue);
		
		if (selectValue === 0) {
			Swal.fire({
				title: "¡Error!",
				text: "Selecciona una opción válida.",
				icon: "warning"
			});
		} else {
					
			switch (selectValue) {
				case 1:
					fechaInicio = `${anioActual}-01-01`;
					fechaFin = `${anioActual}-06-30`;
					break;
				case 2:
					fechaInicio = `${anioActual}-07-01`;
					fechaFin = `${anioActual}-12-31`;
					break;
				case 3:
					fechaInicio = `${anioActual}-01-01`;
					fechaFin = `${anioActual}-12-31`;
					break;
				case 4:
					dtBasesEbrDiv.classList.remove('d-none');
					fechaInicio = document.getElementById('fechaInicio').value;
					fechaFin = document.getElementById('fechaFin').value;
					break;
			}

			procesarBasesEBR(fechaInicio, fechaFin);
		}
		
	});

	periodoSelect.addEventListener('change', () => {
		if (parseInt(periodoSelect.value) === 4) {
			dtBasesEbrDiv.classList.remove("d-none");
		} else {
			dtBasesEbrDiv.classList.add("d-none");
		}
	});
};

async function procesarBasesEBR(fechaInicio, fechaFin) {
	const url = `${getAbsolutePath()}ejecutarProcesoBasesEbr?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`;

	const spinnerModal = new bootstrap.Modal(document.getElementById('spinnerModal'), {
		backdrop: 'static',
		keyboard: false
	});

	try {
		spinnerModal.show();

		const data = await fetchJSON(url, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ fechaInicio: fechaInicio, fechaFin: fechaFin })
		});
		const idProceso = data.pk_proceso_id;

		// Inicia polling cada 3s
		const interval = setInterval(() => {
			obtenerProcesoBasesEbr(idProceso, interval, spinnerModal);
		}, 3000);

	} catch (err) {
		spinnerModal.hide();
		Swal.fire({
			title: "¡Error!",
			text: `Se ha generado un error: ${err.message}, inténtelo más tarde`,
			icon: "error"
		});
	}
}

async function obtenerProcesoBasesEbr(idProceso, interval, modal) {
	
	const url = `${getAbsolutePath()}obtenerProcesoBasesEbr?idProceso=${idProceso}`;
	
	try {
		const data = await fetchJSON(url);
		
		if (!data.i_estatus_ejecucion) {
			clearInterval(interval);
			modal.hide();

			Swal.fire({
				title: "¡Proceso ejecutado!",
				text: "Ya puedes generar las bases EBR en la siguiente pestaña.",
				icon: "success"
			}).then(() => {
				const nextTab = document.querySelector('#pntConf button[data-bs-toggle="tab"][data-bs-target="#bases"]');
				let tab = new bootstrap.Tab(nextTab);
				tab.show();
				mostrarAlertUltimoProceso();
			});
		}
	} catch (err) {
		modal.hide();
        Swal.fire({
            title: "¡Error!",
            text: `Ocurrió un error al verificar el proceso: ${err.message}`,
            icon: "error"
        });
	}
}

async function mostrarAlertUltimoProceso() {
    const msgProceso = document.getElementById('msgProcesoEbr');
    const msgVobo = document.getElementById('msgVoboBasesEbr');
    const url = `${getAbsolutePath()}obtenerLastProcesoBasesEbr`;

    try {
        const data = await fetchJSON(url);

        if (data) {
            const rango = `${data.dt_fecha_inicio} - ${data.dt_fecha_fin}`;
            msgProceso.innerText = `Último periodo de generación de información EBR: ${rango}`;
            msgProceso.classList.remove('d-none');

            msgVobo.classList.remove('alert-info', 'alert-warning');
            if (data.i_estatus_vobo) {
                msgVobo.innerText = `Vobo de las bases EBR del periodo: ${rango}`;
                msgVobo.classList.add('alert-info');
            } else {
                msgVobo.innerText = `Pendiente el vobo de las bases EBR del periodo: ${rango}`;
                msgVobo.classList.add('alert-warning');
            }
            msgVobo.classList.remove('d-none');
        }
    } catch (err) {
        Swal.fire({
            title: "¡Error!",
            text: `No se pudo obtener el último proceso: ${err.message}`,
            icon: "error"
        });
    }
}

function mostrarVoboDiv() {
	const radios = document.querySelectorAll('input[name="info_correcta"]');
	radios.forEach(radio => {
		radio.addEventListener("change", function() {
			let voboDiv = document.getElementById('voboDiv');

			if (this.value === "si") {
				voboDiv.style.display = "flex";
			} else {
				voboDiv.style.display = "none";
				Swal.fire({
					text: "Intente procesar nuevamente las bases EBR",
					icon: "warning"
				});
			}
		})
	});
}

async function ejecutarVoBoBasesEBR() {
    const url = `${getAbsolutePath()}voBoBasesEbr`;

    const voboModalEl = document.getElementById('voboModal');
    const voboModal = bootstrap.Modal.getInstance(voboModalEl);
    
    if (voboModal) {
        voboModal.hide();
    }

    try {
        const response = await fetch(url, { method: 'POST' });
        if (!response.ok) {
            throw new Error(`Error en la petición: ${response.status}`);
        }
        const data = await response.text();

        Swal.fire({
            title: "¡Visto Bueno exitoso!",
            text: data,
            icon: "success"
        }).then(() => {
			const nextTab = document.querySelector('#pntConf button[data-bs-toggle="tab"][data-bs-target="#historico"]');
			let tab = new bootstrap.Tab(nextTab);
			tab.show();
            mostrarAlertUltimoProceso();
        });

    } catch (err) {
        Swal.fire({
            title: "¡Error!",
            text: `Se ha generado un error: ${err.message}, inténtelo más tarde`,
            icon: "error"
        });
    }
}

function generarBasesEBR(tipoReporte) {
	
	const urlCompleta = getAbsolutePath() + "generarBasesEBR?tipoReporte=" + Number(tipoReporte); 
    	
	peticionPost(urlCompleta);

}

function generarHistoricoEBR(idProceso, tipoReporte) {
	
	const urlCompleta = `${getAbsolutePath()}generarHistoricoBasesEBR?idProceso=${Number(idProceso)}&tipoReporte=${tipoReporte}`; 
    	
	peticionPost(urlCompleta);
}

function peticionPost(urlServicio) {
	$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];

			window.open(urlReporte);
			window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			Swal.fire({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}

function onclickBotonesHistoricoBases() {
	// Delegación de eventos para los botones
	$('#tblHistoricoBases tbody').on('click', 'button', function() {
		const tr = $(this).closest('tr');
		const rowData = tblHistoricoBases.row(tr).data();
		const pkId = rowData[0];

		if ($(this).hasClass('base-cliente')) {
			generarHistoricoEBR(pkId, 1);
		} else if ($(this).hasClass('base-operaciones')) {
			generarHistoricoEBR(pkId, 2);
		} else if ($(this).hasClass('base-remesas')) {
			generarHistoricoEBR(pkId, 3);
		} else if ($(this).hasClass('base-desembolsos')) {
			generarHistoricoEBR(pkId, 4);
		}
	});
}

async function fetchJSON(url, options = {}) {
	try {
		const response = await fetch(url, options);

		if (!response.ok) {
			throw new Error(`Error HTTP: ${response.status}`);
		}

		return await response.json();
	} catch (err) {
		console.error(`fetchJSON error: ${err.message}`);
		throw err;
	}
}

document.getElementById("uploadTipoEmpresaForm").addEventListener("submit", function(e) {
	e.preventDefault();

	const url = `${getAbsolutePath()}uploadTipoEmpresa`;
	const procesoDiv = document.getElementById('procesoDiv');

	let fileInput = document.getElementById("tipoEmpresaFile");
	let file = fileInput.files[0];

	if (!file) {
		Swal.fire({
			icon: "warning",
			title: "Archivo no seleccionado",
			text: "Por favor selecciona un archivo antes de subirlo.",
		});
		return;
	}

	let allowedExtensions = /(\.xls|\.xlsx)$/i;
	if (!allowedExtensions.exec(file.name)) {
		Swal.fire({
			icon: "error",
			title: "Formato no válido",
			text: "El archivo debe ser Excel (.xls o .xlsx).",
		});
		fileInput.value = "";
		return;
	}

	let expectedName = "tipo_empresas.xlsx";
	let expectedName2 = "tipo_empresas.xls";

	if (file.name !== expectedName && file.name !== expectedName2) {
		Swal.fire({
			icon: "warning",
			title: "Nombre incorrecto",
			text: `El archivo debe llamarse exactamente: ${expectedName}.xls o ${expectedName2}.xlsx`,
		});
		fileInput.value = "";
		return;
	}

	// Crear FormData y añadir archivo
	let formData = new FormData();
	formData.append("file", file);

	// Mostrar alerta de carga
	Swal.fire({
		title: "Subiendo archivo...",
		text: "Por favor espera mientras se procesa.",
		allowOutsideClick: false,
		allowEscapeKey: false,
		didOpen: () => {
			Swal.showLoading();
		}
	});

	// Timeout de seguridad
	let swalTimeout = setTimeout(() => {
		Swal.close();
	}, 15000);

	// Enviar al backend
	fetch(url, {
		method: 'POST',
		body: formData
	})
		.then(response => {
			clearTimeout(swalTimeout);
			Swal.close();
			return response.text().then(text => ({
				ok: response.ok,
				status: response.status,
				body: text
			}));
		})
		.then(({ ok, status, body }) => {
			if (ok) {
				Swal.fire({
					icon: "success",
					title: "Archivo subido correctamente",
					text: body,
				}).then(() => {
					procesoDiv.classList.remove('d-none');
				});
			} else {
				Swal.fire({
					icon: "error",
					title: `Error (${status})`,
					text: body,
				}).then(() => {
					fileInput.value = ""; // Limpiar input
				});
			}
		})
		.catch(error => {
			clearTimeout(swalTimeout);
			console.error("Error al subir el archivo:", error);

			Swal.close();
			Swal.fire({
				icon: "error",
				title: "Error al subir el archivo",
				text: "Hubo un problema al procesar el archivo.",
			});
		});
});

document.addEventListener("DOMContentLoaded", function () {
	mostrarTabs();
	mostrarVoboDiv();
	mostrarRangoFechas();
	mostrarAlertUltimoProceso();
	onclickBotonesHistoricoBases();
});