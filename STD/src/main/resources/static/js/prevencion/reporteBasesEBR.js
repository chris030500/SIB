document.addEventListener("DOMContentLoaded", function () {
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
});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function procesarBasesEBR() {
	
	const selectValue = document.getElementById('periodoSelect').value;
	console.log("Periodo seleccionado:", selectValue);
	
	if (Number(selectValue) === 0) {
		swal({
			title: "¡Error!",
			text: "Selecciona una opción válida.",
			icon: "warning"
		});
	} else {
		const url = `${getAbsolutePath()}ejecutarProcesoBasesEbr?periodo=${selectValue}`;

		// Mostrar el spinner
		document.getElementById("spinner").style.display = "block";

		fetch(url, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ periodo: selectValue })
		})
			.then(response => response.json())
			.then(data => {
				const idProceso = data.pk_proceso_id;

				const interval = setInterval(() => {
					obtenerProcesoBasesEbr(idProceso, interval);
				}, 3000);
			})
			.catch(err => {
				document.getElementById("spinner").style.display = "none";
				swal({
					title: "¡Error!",
					text: `Se ha generado un error ${err}, inténtelo más tarde`,
					icon: "error"
				});
			});
	}
}

function obtenerProcesoBasesEbr(idProceso, interval) {
	const rptBasesEbrDiv = document.getElementById('rptBasesEbrDiv');
	const url = `${getAbsolutePath()}obtenerProcesoBasesEbr?idProceso=${idProceso}`;
	
	fetch(url)
		.then(response => response.json())
		.then(data => {
			if (!data.i_estatus_ejecucion) {
				clearInterval(interval);
				
				document.getElementById("spinner").style.display = "none";
				rptBasesEbrDiv.style.display = "block";
				
				swal({
					title: "¡Proceso ejecutado!",
                	text: "Ya puedes generar las bases EBR en la siguiente pestaña.",
                	icon: "success",
                	timer: 2000,
                	buttons: false
            	});
			} else {
				rptBasesEbrDiv.style.display = "none";
			}
		})
		.catch(err => {`Error: ${err}`});
}

function ejecutarVoBoBasesEBR() {
	const url = `${getAbsolutePath()}voBoBasesEbr`;
	
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