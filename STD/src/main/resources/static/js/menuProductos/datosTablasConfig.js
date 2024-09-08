// @ts-nocheck
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
	 tblReglasAsignacion.clear().draw();
	 mostrarSpinner();
	 url = getAbsolutePath()+obtDatosReglasAsignacion;
	 $.getJSON(url, {}, function(data) {
		 
		 $.each(data, function(idx, opt) {
		 var botones = "";
			 	var btnEstatus = " <button class='btn btn-warning btn-xs' tabindex='0' "
					+ " title='Actualizar Estatus' type='button'"
					+ " onclick='updateEstatusRegla("
					+ opt.pk_id_regla
					+ ")' id="
					+ opt.pk_id_regla
					+ " data-tooltip='Add'>"
					+ ((opt.i_estatus == 1)?"<i class='bi bi-toggle2-on'></i></button>":"<i class='bi bi-toggle2-off'></i></button>")
					+ "&nbsp;";
				
				
			 	botones = botones + btnEstatus;
			 	if(opt.i_params == 1){
					var btnEditarParams = " <button class='btn btn-primary btn-xs' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar Parametros' data-bs-target='#altaLiqRenovados' aria-controls='table-datatable' type='button'"
					+ " onclick='obtTblParamsAsig("
					+ opt.pk_id_regla
					+ ")' id="
					+ opt.pk_id_regla
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-gear'></i></button>";
					
					botones = botones+btnEditarParams;
				
				}
			 tblReglasAsignacion.row.add([
				 opt.vc_nombre_regla
				 ,(opt.i_usuario_mod == null?'----':opt.i_usuario_mod.vc_nombre_usuario)
				 ,(opt.dt_fecha_mod == null?'--/--/--':opt.dt_fecha_mod)
				 ,(opt.i_estatus == 1?'ACTIVO':'INACTIVO')
				 ,botones]).draw(false);
		 });
		 ocultarSpinner();
	 }); 
 }
 
 /**
 * Cargar tabla reglas de asignacion
 */

 var obtDatosPlantillaSMS = "plantillas/obtPlantillas";
 function obtTblPlantillas(){
	  tblPlantillasSMS.clear().draw();
	 mostrarSpinner();
	 url = getAbsolutePath()+obtDatosPlantillaSMS;
	 $.getJSON(url, {}, function(data) {
		
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
			 		var botonesEditar = "<button class='btn btn-primary btn-sm' tabindex='0' data-bs-toggle='modal'"
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
		  ocultarSpinner();
	 }); 
	
 }
 
 var obtDatosMensajeSMS = "mensajes/obtSMS";
 function listaMensajes(){
	 url = getAbsolutePath()+obtDatosMensajeSMS;
	 $.getJSON(url, {}, function(data) {
		 tblMensajesGenerados.clear().draw();
		 $.each(data, function(idx, opt) {
			 console.log(opt);
		 var botonesActivar = "";
			 		/*var botonesActivar = " <button class='btn btn-info bg-info text-black btn-xs' tabindex='0' "
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
			 	
			 tblMensajesGenerados.row.add([opt.clienteID, opt.nombreCompleto, opt.nombreBase, opt.opt.nombreClasificacion, opt.telefonoCelular, opt.descMensaje, (opt.estMensaje == 1?'ACTIVO':'INACTIVO'),botones]).draw(false);
		 });
	 }); 
 }
 
 

/**
 * Cargar tabla reglas de asignacion
 */
 var obtDatosClienteB = "clientes/obtClientes";
 function obtTblClienteB(){
	 
	 tblClienteBloqueados.clear().draw();
	 mostrarSpinner();
	 
	 url = getAbsolutePath()+obtDatosClienteB;
	 $.getJSON(url, {}, function(data) {
		 
		 $.each(data, function(idx, opt) {
			var botonesActivar = "";
			 		 botonesActivar = " <button class='btn btn-warning btn-xs' tabindex='0' "
					+ " title='Actualizar Estatus'  type='button'"
					+ " onclick='cambiarEstatusCliente(this)' id="
					+ opt.pk_id_bloqueo
					+ " for="
					+ opt.i_estatus
					+ " data-tooltip='Add'>"
					+ ((opt.i_estatus == 1)?"<i class='bi bi-toggle2-on'></i></button>":"<i class='bi bi-toggle2-off'></i></button>")
					+ "&nbsp;";

			 tblClienteBloqueados.row.add([opt.pk_id_bloqueo,opt.i_clienteid,opt.vc_nombre_cliente,opt.dt_fecha_bloqueo
			 ,opt.vc_motivo_bloqueo,(opt.i_usuario_desbloqueo == null?'PROCESO AUTOMATICO':opt.i_usuario_desbloqueo.vc_nombre_usuario),(opt.dt_fecha_vencimiento == null?'----/--/--':opt.dt_fecha_vencimiento),(opt.vc_motivo_desbloqueo == null?'----':opt.vc_motivo_desbloqueo)
			 ,(opt.dt_fecha_desbloqueo == null?'----/--/--':opt.dt_fecha_desbloqueo),(opt.i_usuario_desbloqueo ==null?'--':opt.i_usuario_desbloqueo.vc_nombre_usuario),(opt.i_estatus == 1?'ACTIVO':'INACTIVO'),botonesActivar]).draw(false);
			 
		 });
		 ocultarSpinner();
	 }); 
 }
 
 /**
 * Cargar tabla municipios con cobertura
 */
 var obtenerMunicipiosConCobertura = "exclusiones/getMunicipiosConCobertura";
 function listaMunicipiosConCobertura(){
	 
	 tblMunicipiosConCobertura.clear().draw();
	 mostrarSpinner();
	 
	 url = getAbsolutePath() + obtenerMunicipiosConCobertura;
	 $.getJSON(url, {}, function(data) {
		 
		 $.each(data.municipios, function(idx, municipio) {
			 
			 const date = new Date(municipio.fecha_modifico);
			 const fechaFormateada = formatDate(date);
			 
			// Crear los botones de acción según el valor de indicador_depurar
			var botonesDeshabilitar = "<button class='btn btn-xs btn-danger' tabindex='0' " +
                "title='Remover municipio' type='button' " +
                "onclick='updateMunicipios(" + municipio.id_estado + ", " + municipio.id_municipio + ", " + 0 + ")'" +
                "id=" + municipio.id_municipio +
                " for=" + municipio.indicador_depurar + ">" +
                "<i class='bi bi-dash'></i>" +
                "</button>&nbsp;";

            // Agregar la fila con los datos del municipio a la tabla
            tblMunicipiosConCobertura.row.add([
				municipio.nombre_estado,
                municipio.nombre_municipio,
                (municipio.usuario_modifico == null ? '---' : municipio.usuario_modifico), // Usuario que modificó
                (municipio.fecha_modifico == null ? '----/--/--' : fechaFormateada), // Fecha de modificación
                (municipio.indicador_anterior == null ? '-' : municipio.indicador_anterior),
                botonesDeshabilitar, // Botón para deshabilitar (indicador de depurar)
            ]).draw(false); // Redibujar la tabla
			 
		 });
		 ocultarSpinner();
	 }); 
 }
 
function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Meses desde 0
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    
    return `${day}-${month}-${year} ${hours}:${minutes}:${seconds}`;
}
 
 // Función para mostrar el spinner
function mostrarSpinner() {
    $("#spinner-container").html('<div role="status" style="font-size: 40px;"><span class="text-primary">Consultando ... </span><div class="spinner-border text-primary" role="status" aria-hidden="true"></div>');
}

// Función para ocultar el spinner
function ocultarSpinner() {
     $("#spinner-container").empty();
}
 
 