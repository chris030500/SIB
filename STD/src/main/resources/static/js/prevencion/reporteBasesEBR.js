function mostrarTabs() {
	document.querySelectorAll('#pntConf button[data-bs-toggle="tab"]').forEach(tab => {
		tab.addEventListener('shown.bs.tab', function() {
			const target = document.querySelector(this.dataset.bsTarget);
			const forValue = target.getAttribute("for");

			switch (forValue) {
				case "1":
					console.log("Procesar Bases EBR");
					actualizarEstadoProcesoEBR(1);
					break;
				case "2":
					console.log("Generar Bases EBR");
					actualizarEstadoProcesoEBR(2);
					break;
				case "3":
					actualizarEstadoProcesoEBR(3);
					listaHistoricoBases();
					break;
			}
		});
	});
}

function mostrarRangoFechas() {
	const auditoriasSelect = document.getElementById('auditoriasSelect');
	const btnProcesarBasesEBR = document.getElementById('btnProcesarBasesEBR');

	btnProcesarBasesEBR.addEventListener('click', function () {
		let selectValue = parseInt(auditoriasSelect.value, 10);

		if (isNaN(selectValue) || selectValue === 0) {
			Swal.fire({
				title: "¡Error!",
				text: "Selecciona una opción válida.",
				icon: "warning"
			});
			return;
		}
		
		procesarBasesEBR(selectValue);
	});
}

function procesarBasesEBR(anioAuditoria) {
	const url = getAbsolutePath() + "ejecutarProcesoBasesEbr";
	const spinner = document.getElementById('spinnerEBR');
	const btnProcesarBasesEBR = document.getElementById('btnProcesarBasesEBR');
	
	btnProcesarBasesEBR.disabled = true;

	spinner.classList.remove('d-none');

	fetchJSON(url, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ anioAuditoria: anioAuditoria })
	})
		.then(function (response) {
			if (!response || !response.data || !response.data.procesoId) {
				throw new Error("Respuesta inválida del servidor");
			}

			let idProceso = response.data.procesoId;

			// Inicia polling cada 3s
			const interval = setInterval(function () {
				obtenerProcesoBasesEbr(idProceso, interval, spinner);
			}, 3000);
		})
		.catch(function (err) {
			console.error("Error en procesarBasesEBR:", err);
			Swal.fire({
				title: "¡Error!",
				text: err.message + ". Inténtelo más tarde.",
				icon: "error"
			});
			
			spinner.classList.add('d-none');
            btnProcesarBasesEBR.disabled = false;
		});
}

function obtenerProcesoBasesEbr(idProceso, interval, spinner) {
	const tabBases = document.querySelector('#bases-tab');
	const tabHistorico = document.querySelector('#historico-tab');
	
	var url = getAbsolutePath() + "obtenerProcesoBasesEbr?idProceso=" + idProceso;

	fetchJSON(url)
		.then(function (data) {
			if (data.estatusProceso === 1 || data.estatusProceso === 2) {
				if (spinner) spinner.style.display = "block";
				tabBases.disabled = true;
    			tabHistorico.disabled = true;
			}
			
			if (data.estatusProceso === 3) {
				clearInterval(interval);
				if (spinner) spinner.style.display = "none";

				Swal.fire({
					title: "¡Proceso ejecutado!",
					text: "Ya puedes generar las bases EBR en la siguiente pestaña.",
					icon: "success"
				}).then(function () {
					var nextTab = document.querySelector('#pntConf button[data-bs-toggle="tab"][data-bs-target="#bases"]');
					var tab = new bootstrap.Tab(nextTab);
					tab.show();
					actualizarEstadoProcesoEBR(1);
				});
			}

			if (data.estatusProceso === 4) {
				clearInterval(interval);
				if (spinner) spinner.style.display = "none";

				Swal.fire({
					title: "¡Error!",
					text: "Se generó un error al procesar las bases, inténtalo nuevamente.",
					icon: "error"
				}).then(function () {
					actualizarEstadoProcesoEBR(1);
				});
			}
		})
		.catch(function (err) {
			console.error("Error en obtenerProcesoBasesEbr:", err);
			if (spinner) spinner.style.display = "none";

			Swal.fire({
				title: "¡Error!",
				text: "Ocurrió un error al verificar el proceso: " + err.message,
				icon: "error"
			});
		});
}

function actualizarVobo(idProceso, estatusVobo) {
	const url = `${getAbsolutePath()}actualizarVobo/${idProceso}`;
	
    fetch(url, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ estatusVobo: estatusVobo })
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al actualizar estatus");
        return response.text();
    })
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.error(error);
    });
}

function actualizarProcesoEbr() {
    var url = getAbsolutePath() + 'obtenerLastProcesoBasesEbr';

    fetchJSON(url)
        .then(function (data) {
            let idProceso = data.procesoId;
            actualizarVobo(idProceso, 0);
        })
        .catch(function (err) {
            console.log('No se encuentra último proceso de ejecución.');
        });
}

function actualizarEstadoProcesoEBR(tab) {
    
    const btnProcesarBasesEBR = document.getElementById('btnProcesarBasesEBR');
    const msg = document.getElementById('msgProcesoEbr');
    const btnVistoBueno = document.getElementById('btnVistoBueno');
    const voboSi = document.getElementById('voboSi');
    const voboNo = document.getElementById('voboNo');
    const btnBaseClientesEbr = document.getElementById('btnBaseClientesEbr');
    const btnBaseOperacionesEbr = document.getElementById('btnBaseOperacionesEbr');
    const btnBaseRemesasEbr = document.getElementById('btnBaseRemesasEbr');
    const btnBaseDesembolsosEbr = document.getElementById('btnBaseDesembolsosEbr');
    const infoCorrectaDiv = document.getElementById('infoCorrectaDiv');
    const tabBases = document.querySelector('#bases-tab');
	const tabHistorico = document.querySelector('#historico-tab');
    
    const url = getAbsolutePath() + 'obtenerLastProcesoBasesEbr';

    fetchJSON(url)
        .then(function (data) {
            if (!data) return;

            let anio = data.anioAuditoria;
            let fechaEjecucion = data.fechaEjecucion;
            
            infoCorrectaDiv.classList.remove('d-none');
            msg.classList.remove('alert-info', 'alert-warning', 'alert-danger', 'd-none');

 			// --- Pestaña 1: estado general del proceso ---
			if (tab === 1) {
				if (data.estatusProceso === 2) {
					msg.innerText = 'Aún se está procesando la generación de información EBR del ' + anio + " ejecutado a las: " + fechaEjecucion;
					msg.classList.add('alert-warning');
					btnProcesarBasesEBR.disabled = true;
					tabBases.disabled = true;
    				tabHistorico.disabled = true;
				} else if (data.estatusProceso === 3) {
					msg.innerText = 'Última ejecución de generación de información EBR ' + anio + ": " + fechaEjecucion;
					msg.classList.add('alert-info');
					btnProcesarBasesEBR.disabled = false;
					tabBases.disabled = false;
    				tabHistorico.disabled = false;
				} else if (data.estatusProceso === 4) {
					msg.innerText = 'Se intentó procesar la generación de información EBR del ' + anio + ": " + fechaEjecucion;
					msg.classList.add('alert-danger');
					tabBases.disabled = false;
    				tabHistorico.disabled = false;
				}
			}
			
			// --- Pestaña 2: VoBo ---
			if (tab === 2) {
				
				btnBaseClientesEbr.disabled = false;
				btnBaseOperacionesEbr.disabled = false;
				btnBaseRemesasEbr.disabled = false;
				btnBaseDesembolsosEbr.disabled = false;
				
				if (data.estatusVobo === true) {
					msg.innerText = 'VoBo de las bases EBR del periodo ' + anio;
					msg.classList.add('alert-info');
					voboSi.checked = true;
					voboSi.disabled = true;
					voboNo.disabled = true;
					btnVistoBueno.disabled = true;
				} else if (data.estatusVobo === false) {
					msg.innerText = 'VoBo pendiente de las bases EBR del periodo ' + anio;
					msg.classList.add('alert-warning');
					voboNo.checked = true;
					voboSi.disabled = false;
					voboNo.disabled = false;
					btnVistoBueno.disabled = false;
				} else if (data.estatusProceso === 4) {
					msg.classList.add('d-none');
					btnProcesarBasesEBR.disabled = false;
					btnBaseClientesEbr.disabled = true;
					btnBaseOperacionesEbr.disabled = true;
					btnBaseRemesasEbr.disabled = true;
					btnBaseDesembolsosEbr.disabled = true;
					infoCorrectaDiv.classList.add('d-none');
				}
				else {
					msg.classList.add('d-none');
					voboNo.checked = false;
					voboSi.disabled = false;
					voboNo.disabled = false;
					btnVistoBueno.disabled = false;
				}
			}
			
			if (tab === 3) {
				msg.classList.add('d-none');
			}
        })
        .catch(function (err) {
            console.log('No se encuentra último proceso de ejecución.');
        });
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
				actualizarProcesoEbr();
				Swal.fire({
					text: "Intente procesar nuevamente las bases EBR",
					icon: "warning"
				});
			}
		})
	});
}

document.getElementById("btnVistoBueno").addEventListener("click", function () {

    Swal.fire({
        title: "Confirmar vo.bo Bases EBR",
        text: "¿Está seguro de que los datos de las Bases EBR son correctos?",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
        reverseButtons: true // opcional, para que los botones se vean como en Bootstrap
    }).then((result) => {
        if (result.isConfirmed) {
            ejecutarVoBoBasesEBR();
        }
    });

});

async function ejecutarVoBoBasesEBR() {
    const url = `${getAbsolutePath()}voBoBasesEbr`;
    
	Swal.fire({
		title: "Procesando Vobo...",
		text: "Por favor espere",
		allowOutsideClick: false,
		allowEscapeKey: false,
		didOpen: () => {
			Swal.showLoading();
						const popup = Swal.getPopup();
			const content = popup.querySelector('.swal2-html-container');
			const title = popup.querySelector('#swal2-title');
			const loader = popup.querySelector('.swal2-loader');

			// Alinear todo vertical y horizontalmente
			popup.style.display = "flex";
			popup.style.flexDirection = "column";
			popup.style.alignItems = "center";
			popup.style.justifyContent = "center";
			
			title.style.marginTop = "30px";
			title.style.textAlign = "center";
			content.style.textAlign = "center";

			if (loader) {
				loader.style.marginTop = "10px";
			}
		}
	});

    try {
        const response = await fetch(url, { method: 'POST' });
        if (!response.ok) {
            throw new Error(`Error en la petición: ${response.status}`);
        }
        const data = await response.text();

        Swal.fire({
            title: "¡Visto Bueno exitoso!",
            text: data,
            icon: "success",
			didClose: () => {
				const nextTab = document.querySelector('#pntConf button[data-bs-toggle="tab"][data-bs-target="#historico"]');
				if (nextTab) {
					let tab = new bootstrap.Tab(nextTab);
					tab.show();
				}
				actualizarEstadoProcesoEBR(3);
			}
		});

    } catch (err) {
        Swal.fire({
            title: "¡Error!",
            text: `${err.message}, inténtelo más tarde`,
            icon: "error"
        });
    }
}

function generarBasesEBR(tipoReporte) {
	
	const urlCompleta = `${getAbsolutePath()}generarBasesEBR?tipoReporte=${Number(tipoReporte)}`;
    	
	descargarBasesEbr(urlCompleta);

}

function generarHistoricoEBR(idProceso, tipoReporte) {
	
	const urlCompleta = `${getAbsolutePath()}generarHistoricoBasesEBR?idProceso=${Number(idProceso)}&tipoReporte=${tipoReporte}`; 
    	
	descargarBasesEbr(urlCompleta);
}

function descargarBasesEbr(urlServicio) {
    $.blockUI({ message: 'Espere un momento...' });

    fetch(urlServicio, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(function(response) {
        setTimeout($.unblockUI, 500);
        
        return response.json().then(dto => {
            if (!response.ok) {
                throw new Error(dto.mensaje);
            }
            return dto;
        });       
    })
    .then(dto => {
		let baseUrl = window.location.href.substring(
			0,
			window.location.href.indexOf("STD/") + 4
		);
		let urlReporte = baseUrl + dto.data;
		let nuevaVentana = window.open(urlReporte, "_blank");
		if (nuevaVentana) nuevaVentana.focus();
		else Swal.fire("Aviso", "El navegador bloqueó la ventana emergente.", "info");
    })
    .catch(function(err) {
        setTimeout($.unblockUI, 500);
        Swal.fire({
            title: "Error",
            text: `${err.message}, intente más tarde.`,
            icon: "error",
        });
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
		const result = await response.json();

		if (!response.ok) {
			throw new Error(result.status);
		}

		return await result;
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

	let expectedNameXLSX = "tipo_empresas.xlsx";
	let expectedNameXLS = "tipo_empresas.xls";

	if (file.name !== expectedNameXLSX && file.name !== expectedNameXLS) {
		Swal.fire({
			icon: "warning",
			title: "Nombre incorrecto",
			text: `El archivo debe llamarse exactamente: ${expectedNameXLSX} o ${expectedNameXLS}`,
		});
		fileInput.value = "";
		return;
	}

	// Crear FormData y añadir archivo
	let formData = new FormData();
	formData.append("file", file);

	// Mostrar alerta de carga
	Swal.fire({
		title: "Procesando archivo...",
		text: "Por favor espera mientras se procesa.",
		allowOutsideClick: false,
		allowEscapeKey: false,
		didOpen: () => {
			Swal.showLoading();

			const popup = Swal.getPopup();
			const content = popup.querySelector('.swal2-html-container');
			const title = popup.querySelector('#swal2-title');
			const loader = popup.querySelector('.swal2-loader');

			// Alinear todo vertical y horizontalmente
			popup.style.display = "flex";
			popup.style.flexDirection = "column";
			popup.style.alignItems = "center";
			popup.style.justifyContent = "center";
			
			title.style.marginTop = "30px";
			title.style.textAlign = "center";
			content.style.textAlign = "center";

			if (loader) {
				loader.style.marginTop = "10px";
			}
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
					title: "Archivo cargado correctamente",
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
	actualizarEstadoProcesoEBR(1);
	mostrarRangoFechas();
	onclickBotonesHistoricoBases();
});