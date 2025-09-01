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
			swal({
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

function procesarBasesEBR(fechaInicio, fechaFin) {

	const url = `${getAbsolutePath()}ejecutarProcesoBasesEbr?fechaInicio=${fechaInicio}&fechaFin${fechaFin}`;

	const spinnerModal = new bootstrap.Modal(document.getElementById('spinnerModal'), {
		backdrop: 'static',
		keyboard: false
	});

	spinnerModal.show();

	fetch(url, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ fechaInicio: fechaInicio, fechaFin: fechaFin })
	})
		.then(response => response.json())
		.then(data => {
			const idProceso = data.pk_proceso_id;

			const interval = setInterval(() => {
				obtenerProcesoBasesEbr(idProceso, interval, spinnerModal);
			}, 3000);
		})
		.catch(err => {
			spinnerModal.hide();
			swal({
				title: "¡Error!",
				text: `Se ha generado un error ${err}, inténtelo más tarde`,
				icon: "error"
			});
		});
}

function obtenerProcesoBasesEbr(idProceso, interval, modal) {
	
	const url = `${getAbsolutePath()}obtenerProcesoBasesEbr?idProceso=${idProceso}`;
	
	fetch(url)
		.then(response => response.json())
		.then(data => {
			if (!data.i_estatus_ejecucion) {
				clearInterval(interval);
				
				modal.hide();
				
				swal({
					title: "¡Proceso ejecutado!",
                	text: "Ya puedes generar las bases EBR en la siguiente pestaña.",
                	icon: "success",
                	timer: 2000,
                	buttons: false
            	}).then(() => {
					location.reload();
				});
			}
		})
		.catch(err => {console.log(`Error: ${err}`)});
}

function mostrarAlertUltimoProceso() {
	const msgProceso = document.getElementById('msgProcesoEbr');
	const msgVobo = document.getElementById('msgVoboBasesEbr');
	
	const url = `${getAbsolutePath()}obtenerLastProcesoBasesEbr`;
	
	fetch(url)
		.then(response => response.json())
		.then(data => {
			if (data) {
				const rango = `${data.dt_fecha_inicio} - ${data.dt_fecha_fin}`;
				msgProceso.innerText = `Último periodo de generación de información EBR: ${rango}`;
				msgProceso.classList.remove('d-none');
				
				msgVobo.classList.remove('alert-info', 'alert-warning');
				if (data.i_estatus_vobo) {
					msgVobo.innerText = `Vobo de las bases EBR del periodo: ${rango}`;
					msgVobo.classList.add('alert-info');
				} else {
					msgVobo.innerText = `Aún queda pendiente el vobo de las bases EBR del periodo: ${rango}`;
					msgVobo.classList.add('alert-warning');
				}
				 msgVobo.classList.remove('d-none');
			}
		})
		.catch(err => {console.log(`Error: ${err}`)});
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
				swal({
					text: "Intente procesar nuevamente las bases EBR",
					icon: "warning",
					timer: 2000,
					buttons: false
				});
			}
		})
	});
}

function ejecutarVoBoBasesEBR() {
	const url = `${getAbsolutePath()}voBoBasesEbr`;
	
	const voboModalEl = document.getElementById('voboModal');
	const voboModal = bootstrap.Modal.getInstance(voboModalEl);
	if (voboModal) {
		voboModal.hide();
	}
	
	fetch(url, {
		method: 'POST'
	})
	.then(response => {
		if (!response.ok) {
			throw new Error('Error en la petición: ' + response.status);
		}
		return response.text();
	})
	.then(data => {
		swal({
			title: "¡Visto Bueno exitoso!",
			text: data,
			icon: "success",
			timer: 2000,
			buttons: false
		}).then(() => {
			mostrarAlertUltimoProceso();
		});
	})
	.catch(err => {
		swal({
			title: "¡Error!",
			text: `Se ha generado un error ${err}, inténtelo más tarde`,
			icon: "error"
		});		
	});
}

function generarBasesEBR(tipoReporte) {
	let urlCompleta = '';
	
	urlCompleta = getAbsolutePath() + "generarBasesEBR?tipoReporte=" + Number(tipoReporte); 
    	
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
			swal({
				title: "¡Error!",
				text: "Se presento un error, intente mas tarde.",
				icon: "error",
			});
		}
	});
}

document.addEventListener("DOMContentLoaded", function () {
	mostrarTabs();
	mostrarVoboDiv();
	mostrarRangoFechas();
	mostrarAlertUltimoProceso();
});