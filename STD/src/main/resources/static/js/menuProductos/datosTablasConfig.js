/**
 * 
 */
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

/**
 * Cargar tabla reglas de asignacion
 */

 var obtDatosReglasAsignacion = "reglas/obtDatosReglasAsignacion";
 function obtTblReglas(){
	 var url = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+obtDatosReglasAsignacion;
	 $.getJSON(url, {}, function(data) {
		 tblReglasAsignacion.clear().draw();
		 $.each(data, function(idx, opt) {
		 var botones = "";
			 		var btnEstatus = " <button class='btn btn-success' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Estatus' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='actualizarEstatus("
					+ opt.pk_id_regla
					+ ")' id="
					+ opt.pk_id_regla
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-arrow-repeat'></i></button>";
				
			 	botones = botones + btnEstatus;
			 	if(opt.pk_id_regla != 1){
					var btnEditarParams = " <button class='btn btn-primary' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Parametros' data-bs-target='#modalParams' aria-controls='table-datatable' type='button'"
					+ " onclick='actualizarParams("
					+ opt.pk_id_regla
					+ ")' id="
					+ opt.pk_id_regla
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-pencil-square'></i></button>";
					
					botones = botones+btnEditarParams;
				
				}
				
			 	
				
				
				
				
			 tblReglasAsignacion.row.add([opt.pk_id_regla,opt.vc_nombre_regla,(opt.i_estatus == 1?'ACTIVO':'INACTIVO'),botones]).draw(false);
		 });
	 }); 
 }
 
 function actualizarEstatus(){
	 console.log("actualizar regla.");
 }
 
 function actualizarParams(){
	  console.log("actualizar parametros.");
 }
 