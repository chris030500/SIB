var getPlantillasSMS = "credito/plantillas-SMS";
var getGestionesSMS = "credito/gestion-SMS";
var botones = "";

function getAbsolutePath() {

	return window.location.href.substring(0, window.location.href.indexOf("STD/") + 4);

}

function listaPlantillas() {

	url = getAbsolutePath() + getPlantillasSMS;
	$.getJSON(url, {}, function(data) {

		tblPlantillasSMS.clear().draw();
		$.each(data.plantillas, function(idx, opt) {

			//console.log(opt);
			let botonesEditar = "<button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle='modal'"
				+ " title='Editar Plantilla' data-bs-target='#editPlantillaModal' aria-controls='table-datatable' type='button'"
				+ " onclick='editPlantilla("
				+ opt.idConfig
				+ ")' id="
				+ opt.idConfig
				+ " data-tooltip='Add'>"
				+ " <i class='fa-solid fa-pen-to-square'></i></button>";

			botones = botonesEditar;

			tblPlantillasSMS.row.add([
				opt.idConfig,
				opt.mensaje,
				opt.userRegistra,
				opt.fechaRegistro,
				opt.userModifica,
				opt.fechaModificacion,
				opt.estatus,
				botones
			]).draw(false);
		});
	});
}

function listaGestiones() {
	const mesActual = new Date().getMonth(); // Índice del mes actual (0-11)
	const meses = [
		"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
		"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
	];

	url = getAbsolutePath() + getGestionesSMS;
	$.getJSON(url, {}, function(data) {

		tblGestionesSMS.clear().draw();
		$.each(data.gestiones, function(idx, opt) {

			//console.log(opt);

			// Determinar si el botón debe estar deshabilitado
			const mesIndice = meses.map(m => m.toLowerCase().trim()).indexOf(opt.nomMes.toLowerCase().trim());

			if (mesIndice === -1) {

				console.error("Mes no válido:", opt.nomMes);
				return; // Saltar este registro si el mes no es válido
			}

			const isLastMonth = mesIndice < mesActual;
			//const isCurrentMonth = mesIndice === mesActual;
			const isNextMonth = mesIndice > mesActual;
			const deshabilitar = isLastMonth ? "disabled" : "";

			// Botón Editar (Siempre se muestra, pero deshabilitado si es mes anterior)
			let botonesEditar = `<button class='btn btn-secundary bg-info text-black btn-xs me-1' 
                tabindex='0' data-bs-toggle='modal' title='Editar Proceso Envío' 
                data-bs-target='#editProcessModal' aria-controls='table-datatable' type='button'
                onclick='editProcess(${opt.pkIdProceso})' id='${opt.pkIdProceso}'
                ${deshabilitar} data-tooltip='Add'>
                <i class='fa-solid fa-pen-to-square'></i></button>`;

			let botonesPausar = "";
			let botonesReanudar = "";

			// Si es mes anterior o actual, mostramos los botones de Pausar y Reanudar
			if (!isNextMonth) {
				botonesPausar = `<button class='btn btn-secundary bg-danger text-white btn-xs me-1' 
                    tabindex='0' data-bs-toggle='modal' title='Pausar Proceso Envío' 
                    data-bs-target='#statusProcessModal' aria-controls='table-datatable' type='button'
                    onclick='modalProcess(${opt.pkIdProceso}, 4, "${opt.nomMes}")' ${deshabilitar}
                    data-tooltip='Add'>
                    <i class='bi bi-pause-fill'></i></button>`;

				botonesReanudar = `<button class='btn btn-secundary bg-success text-white btn-xs me-1' 
                    tabindex='0' data-bs-toggle='modal' title='Reanudar Proceso Envío' 
                    data-bs-target='#statusProcessModal' aria-controls='table-datatable' type='button'
                    onclick='modalProcess(${opt.pkIdProceso}, 6, "${opt.nomMes}")' ${deshabilitar}
                    data-tooltip='Add'>
                    <i class='bi bi-play-fill'></i></button>`;
			}

			botones = botonesEditar + botonesPausar + botonesReanudar;

			tblGestionesSMS.row.add([
				opt.pkIdProceso,
				opt.nomMes,
				opt.mensajeSMS,
				opt.limiteSMS,
				opt.numSmsEnviar,
				opt.numSmsPendientes,
				opt.numSmsEnvios,
				opt.estatusProceso,
				botones
			]).draw(false);
		});
	});
}