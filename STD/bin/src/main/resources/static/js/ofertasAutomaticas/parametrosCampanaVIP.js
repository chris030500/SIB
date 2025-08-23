/**
 * 
 */
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var urlBase = getAbsolutePath();
console.log("urlBase: " + urlBase);


$(document).ready(function() {
	// Función que se ejecuta al hacer clic en el botón "Cerrar" del modal
	$('#modalCampana').on('click', '.modal-footer .btn-secondary', function() {
		// Forzar el cierre del modal antes de recargar la página
		$('#modalCampana').modal('hide');
		location.reload();
	});
});

function validarFechas() {
	var dComienzoCampana = parseInt(document.getElementById('dComienzoCampana').value);
	var dFinalCampana = parseInt(document.getElementById('dFinalCampana').value);

	if (dComienzoCampana >= dFinalCampana) {
		swal({
			title: "Error, ",
			text: "El día que comienza el proceso debe ser menor al día que termina el proceso.",
			icon: "warning",
		});
		return false;
	}
	return true;
}

function validarHoras() {
	console.log("Entro en validarHoras");
	console.log("Entro en validarHoras");
	var hComienzoCampana = document.getElementById('hComienzoCampana').value;
	var hFinalCampana = document.getElementById('hFinalCampana').value;

	console.log("hComienzoCampana: " + hComienzoCampana);
	console.log("hFinalCampana: " + hFinalCampana);

	if (hComienzoCampana === hFinalCampana) {
		console.log("Las horas son iguales");
		swal({
			title: "Advertencia",
			text: "Las horas de inicio y finalización son iguales, favor de agregar horas diferentes.",
			icon: "warning",
		});
		return false; // Indica que la validación falló
	}

	if (hComienzoCampana >= hFinalCampana) {
		console.log("Las hora de inicio es menor");
		swal({
			title: "Error",
			text: "La hora de inicio debe ser menor que la hora de fin del proceso.",
			icon: "error",
		});
		return false; // Indica que la validación falló
	}

	return true; // Indica que la validación fue exitosa
}

function guardarParametrosCampanaVIP() {
	//console.log("click en guardarParametrosFidelidad");
	var dComienzoCampana = document.getElementById('dComienzoCampana').value;
	var dFinalCampana = document.getElementById('dFinalCampana').value;
	var hComienzoCampana = document.getElementById('hComienzoCampana').value;
	var hFinalCampana = document.getElementById('hFinalCampana').value;
	var mPeriodoCampana = document.getElementById('mPeriodoCampana').value;

	var esValidoCampana = validarFechas();
	var esValidaHoraCampana = validarHoras();
	/*console.log("dComienzoProceso: " + dComienzoProceso);
	console.log("dFinalProceso: " + dFinalProceso);
	console.log("hComienzoProceso: " + hComienzoProceso);
	console.log("hFinalProceso: " + hFinalProceso);
	console.log("mPeriodoEjecucion: " + mPeriodoEjecucion);
	console.log("rutaArchivo: " + rutaArchivo);*/

	if (dComienzoProceso == '' || dFinalProceso == '' || hComienzoProceso == '' ||
		hFinalProceso == '' || mPeriodoEjecucion == '') {
		swal({
			title: "Error, ",
			text: "Existen campos vacíos, favor de validar.",
			icon: "warning",
		});
	} else {
		if (!esValidoCampana || !esValidaHoraCampana) {

		} else {
			$.ajax({
				url: urlBase + "guardarParamCampanaVIP?dComienzoCampana=" + encodeURIComponent(dComienzoCampana) +
					"&dFinalCampana=" + encodeURIComponent(dFinalCampana) +
					"&hComienzoCampana=" + encodeURIComponent(hComienzoCampana) +
					"&hFinalCampana=" + encodeURIComponent(hFinalCampana) +
					"&mPeriodoCampana=" + encodeURIComponent(mPeriodoCampana),
				type: "POST",
				success: function(respuesta) {
					console.log("Respuesta: " + respuesta);
					swal({
						title: "Se han guardado los cambios de manera correcta",
						icon: "success",
					}).then((result) => {
						window.location.reload();
					});

				}, error: function(XMLHttpRequest, textStatus, errorThrown) {
					/*console.log("XMLHttpRequest: " + XMLHttpRequest);
					console.log("textStatus: " + textStatus);
					console.log("errorThrown: " + errorThrown);*/
					swal({
						title: "Error, ",
						text: "Se presento un error al intentar guardar la configuración del proceso de campañas VIP, favor de contactar al administrador del sistema.",
						icon: "warning",
					});
				}
			});
		}
	}


}