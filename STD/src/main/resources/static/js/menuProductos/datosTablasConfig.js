/**
 * 
 */
var botones="";
function getAbsolutePath() {

	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);

}

/**
 * Cargar tabla reglas de asignacion
 */
var url = "";
 var obtDatosReglasAsignacion = "reglas/obtDatosReglasAsignacion";
 function obtTblReglas(){
	 url = getAbsolutePath()+obtDatosReglasAsignacion;
	 $.getJSON(url, {}, function(data) {
		 tblReglasAsignacion.clear().draw();
		 $.each(data, function(idx, opt) {
		 var botones = "";
			 	var btnEstatus = " <button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' "
					+ " title='Actualizar Estatus' type='button'"
					+ " onclick='updateEstatusRegla("
					+ opt.pk_id_regla
					+ ")' id="
					+ opt.pk_id_regla
					+ " data-tooltip='Add'>"
					+ ((opt.i_estatus == 1)?"<i class='fa-solid fa-toggle-on fa-lg'></i></button>":"<i class='fa-solid fa-toggle-off fa-lg'></i></button>")
					+ "&nbsp;";
				
			 	botones = botones + btnEstatus;
			 	if(opt.i_params == 1){
					var btnEditarParams = " <button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Parametros' data-bs-target='#altaLiqRenovados' aria-controls='table-datatable' type='button'"
					+ " onclick='obtTblParamsAsig("
					+ opt.pk_id_regla
					+ ")' id="
					+ opt.pk_id_regla
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-pen-to-square'></i></button>";
					
					botones = botones+btnEditarParams;
				
				}
			 tblReglasAsignacion.row.add([opt.pk_id_regla,opt.vc_nombre_regla,opt.i_dias_vigencia,(opt.i_estatus == 1?'ACTIVO':'INACTIVO'),(opt.i_usuario_mod == null?'----':opt.i_usuario_mod.vc_nombre_usuario),(opt.dt_fecha_mod == null?'--/--/--':opt.dt_fecha_mod),botones]).draw(false);
		 });
	 }); 
 }
 
 /**
 * Cargar tabla reglas de asignacion
 */
 var obtDatosPlantillaSMS = "plantillas/obtPlantillas";
 function obtTblPlantillas(){
	 url = getAbsolutePath()+obtDatosPlantillaSMS;
	 $.getJSON(url, {}, function(data) {
		 tblPlantillasSMS.clear().draw();
		 $.each(data, function(idx, opt) {
		 var botonesActivar = "";
			 		/*var botonesActivar = " <button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' "
					+ " title='Actualizar Estatus'  type='button'"
					+ " onclick='actualizarEstatus("
					+ opt.pk_id_plantilla
					+ ")' id="
					+ opt.pk_id_plantilla
					+ " data-tooltip='Add'>"
					+ ((opt.i_activo == 1)?"<i class='bi bi-unlock-fill'></i></button>":"<i class='bi bi-lock-fill'></i></button>")
					+ "&nbsp;";*/
		 var botonesEditar = "";
			 		var botonesEditar = "<button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar Registro' data-bs-target='#altaPlantilla' aria-controls='table-datatable' type='button'"
					+ " onclick='editarPlantilla("
					+ opt.pk_id_plantilla
					+ ")' id="
					+ opt.pk_id_plantilla
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-pen-to-square'></i></button>";
				
			 	botones = botonesActivar+botonesEditar;
			 	
			 tblPlantillasSMS.row.add([opt.vc_nombre_plantilla,opt.vc_contenido,opt.fk_ID_CLASIFICACION.fk_id_base.vc_nombre_base,opt.fk_ID_CLASIFICACION.vc_abreviacion_clasificacion,opt.i_usuario_registro.vc_nombre_usuario,opt.dt_fecha_registro,
			 (opt.i_usuario_mod == null?'----':opt.i_usuario_mod.vc_nombre_usuario),(opt.dt_fecha_mod == null?'--/--/--':opt.dt_fecha_mod),(opt.i_activo == 1?'ACTIVO':'INACTIVO'),botones]).draw(false);
		 });
	 }); 
 }
 
 