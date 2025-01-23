var getPlantillasSMS = "marketing/plantillas-SMS";
var getGestionesSMS = "marketing/gestion-SMS?tipoCredito=";
var botones = "";

function getAbsolutePath() {

	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);

}

function listaPlantillas(){
	 url = getAbsolutePath() + getPlantillasSMS;
	 $.getJSON(url, {}, function(data) {
		 tblPlantillasSMS.clear().draw();
		 $.each(data.plantillas, function(idx, opt) {
			 
			console.log(opt);
		 	var botonesEditar = "";
			 	var botonesEditar = "<button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle='modal'"
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
				 opt.tipoCredito,
				 opt.mensaje, 
				 opt.userRegistra,
				 opt.fechaRegistro, 
				 opt.userModifica,
				 opt.fechaModificacion,
				 botones
			]).draw(false);
		 });
	 }); 
 }
 
 function listaGestiones(tipoCredito){
	const mesActual = new Date().getMonth(); // Índice del mes actual (0-11)
    const meses = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];
	 
	 url = getAbsolutePath() + getGestionesSMS + tipoCredito;
	 $.getJSON(url, {}, function(data) {
		 tblGestionesSMS.clear().draw();
		 $.each(data.gestiones, function(idx, opt) {
			 console.log(opt);
			 
			// Determinar si el botón debe estar deshabilitado
            const mesIndice = meses.map(m => m.toLowerCase().trim()).indexOf(opt.nomMes.toLowerCase().trim());
            
            if (mesIndice === -1) {
        		console.error("Mes no válido:", opt.nomMes);
        		return; // Saltar este registro si el mes no es válido
    		}
    		
            const deshabilitar = mesIndice < mesActual; // Deshabilitar si el mes es anterior al actual
            
		 	var botonesEditar = "";
				var botonesEditar = "<button class='btn btn-secundary bg-info text-black btn-xs me-1' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar Proceso Envío' data-bs-target='#editProcessModal' aria-controls='table-datatable' type='button'"
					+ " onclick='editProcess("
					+ opt.pkIdProceso
					+ ")' id="
					+ opt.pkIdProceso
					+ " " + (deshabilitar ? "disabled" : "")
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-pen-to-square'></i></button>";
			var botonesPausar = "";
			 	var botonesPausar = "<button class='btn btn-secundary bg-danger text-white btn-xs me-1' tabindex='0' data-bs-toggle='modal'"
					+ " title='Pausar Proceso Envío' data-bs-target='#statusProcessModal' aria-controls='table-datatable' type='button'"
					+ " onclick='modalProcess("
					+ opt.pkIdProceso + ", 4)'"
					+ " " + (deshabilitar ? "disabled" : "")
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pause-fill'></i></button>";
			var botonesReanudar = "";
			 	var botonesReanudar = "<button class='btn btn-secundary bg-success text-white btn-xs me-1' tabindex='0' data-bs-toggle='modal'"
					+ " title='Reanudar Proceso Envío' data-bs-target='#statusProcessModal' aria-controls='table-datatable' type='button'"
					+ " onclick='modalProcess("
					+ opt.pkIdProceso + ", 6)'"
					+ " " + (deshabilitar ? "disabled" : "")
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-play-fill'></i></button>";
				
			 botones = botonesEditar + botonesPausar + botonesReanudar;
			 	
			 tblGestionesSMS.row.add([
				 opt.pkIdProceso,
				 opt.nomMes, 
				 opt.mensajeSMS,
				 opt.numSmsEnviar, 
				 opt.numSmsPendientes,
				 opt.numSmsEnvios,
				 opt.estatusProceso,
				 botones
			]).draw(false);
		 });
	 }); 
 }