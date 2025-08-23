/**
 * 
 */

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var urlBase = getAbsolutePath();
//console.log("urlBase: " + urlBase);


$(document).ready(function() {
	// Función que se ejecuta al hacer clic en el botón "Cerrar" del modal
	$('#modalFidelidad').on('click', '.modal-footer .btn-secondary', function() {
		// Forzar el cierre del modal antes de recargar la página
		$('#modalFidelidad').modal('hide');
		location.reload();
	});
});

function validarFechasFidelidad() {
	var dComienzoProceso = parseInt(document.getElementById('dComienzoProceso').value);
	var dFinalProceso = parseInt(document.getElementById('dFinalProceso').value);

	if (dComienzoProceso >= dFinalProceso) {
		swal({
			title: "Error, ",
			text: "El día que comienza el proceso debe ser menor al día que termina el proceso.",
			icon: "warning",
		});
		return false;
	}
	return true;
}

function validarHorasFidelidad() {
	console.log("Entro en validarHoras FIDELIDAD");
	var hComienzoProceso = document.getElementById('hComienzoProceso').value;
	var hFinalProceso = document.getElementById('hFinalProceso').value;

	console.log("hComienzoProceso: " + hComienzoProceso);
	console.log("hFinalProceso: " + hFinalProceso);

	if (hComienzoProceso === hFinalProceso) {
		console.log("Las horas son iguales");
		swal({
			title: "Advertencia",
			text: "Las horas de inicio y finalización son iguales, favor de agregar horas diferentes.",
			icon: "warning",
		});
		return false; // Indica que la validación falló
	}

	if (hComienzoProceso >= hFinalProceso) {
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

function guardarParametrosFidelidad() {
	//console.log("click en guardarParametrosFidelidad");
	var dComienzoProceso = document.getElementById('dComienzoProceso').value;
	var dFinalProceso = document.getElementById('dFinalProceso').value;
	var hComienzoProceso = document.getElementById('hComienzoProceso').value;
	var hFinalProceso = document.getElementById('hFinalProceso').value;
	var mPeriodoEjecucion = document.getElementById('mPeriodoEjecucion').value;
	var rutaArchivo = document.getElementById('rutaArchivo').value;
	var esValido = validarFechasFidelidad();
	var esValidaHora = validarHorasFidelidad();
	//console.log("esValidaHora: " + esValidaHora);
	/*console.log("dComienzoProceso: " + dComienzoProceso);
	console.log("dFinalProceso: " + dFinalProceso);
	console.log("hComienzoProceso: " + hComienzoProceso);
	console.log("hFinalProceso: " + hFinalProceso);
	console.log("mPeriodoEjecucion: " + mPeriodoEjecucion);
	console.log("rutaArchivo: " + rutaArchivo);*/

	if (dComienzoProceso == '' || dFinalProceso == '' || hComienzoProceso == '' ||
		hFinalProceso == '' || mPeriodoEjecucion == '' || rutaArchivo == '') {
		swal({
			title: "Error, ",
			text: "Existen campos vacíos, favor de validar.",
			icon: "warning",
		});
	} else {
		if (!esValido || !esValidaHora) {

		} else {

			$.ajax({
				url: urlBase + "guardarParamFidelidad?dComienzoProceso=" + encodeURIComponent(dComienzoProceso) +
					"&dFinalProceso=" + encodeURIComponent(dFinalProceso) +
					"&hComienzoProceso=" + encodeURIComponent(hComienzoProceso) +
					"&hFinalProceso=" + encodeURIComponent(hFinalProceso) +
					"&mPeriodoEjecucion=" + encodeURIComponent(mPeriodoEjecucion) +
					"&rutaArchivo=" + encodeURIComponent(rutaArchivo),
				type: "POST",
				success: function(respuesta) {
					//console.log("Respuesta: " + respuesta);
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
						text: "Se presento un error al intentar guardar la configuración del proceso de fidelidad, favor de contactar al administrador del sistema.",
						icon: "warning",
					});
				}
			});
		}

	}

}