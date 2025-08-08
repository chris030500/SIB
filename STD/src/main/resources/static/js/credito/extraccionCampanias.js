/**
 * 
 */
/**
 ////////////////----DES-25-0014 Campanias----////////////////
////////////////----viviana.zamudio-------------------------////////////////
 */
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");
var mensajeError = "Datos incorrectos, favor de validar.";


document.addEventListener("DOMContentLoaded", function() {
	//console.log("url:", newUrlBase);
	//llena combo de campañas
    $.ajax({
        url: newUrlBase + 'credito/api/catTipoCam',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Campania');
            select.empty();
            select.append('<option value="0">Seleccione</option>');
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_tipo_campania + '">' + item.pk_id_tipo_campania + "-" + item.vc_tipo_campania + '</option>');
            });
            
            // Marcar seleccionados
        	//select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar tipos campañas', err);
        }
    });
    //llena combo de estatus
    $.ajax({
        url: newUrlBase + 'credito/api/catEstatusCam',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Estatus');
            select.empty();
            select.append('<option value="0">Seleccione</option>');
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_estatus_campania + '">' + item.vc_estatus + '</option>');
            });
            
            // Marcar seleccionados
        	//select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar estatus', err);
        }
    });
});