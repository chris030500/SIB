var getPlantillasSMS = "marketing/plantillas-SMS";
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
					+ " title='Editar Registro' data-bs-target='#altaPlantilla' aria-controls='table-datatable' type='button'"
					+ " onclick='editarPlantilla("
					+ opt.idConfig
					+ ")' id="
					+ opt.idConfig
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-pen-to-square'></i></button>";
				
			 	botones = botonesEditar;
		console.log(opt.tipoCredito);
			 	
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