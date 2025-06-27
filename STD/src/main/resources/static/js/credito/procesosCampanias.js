// @ts-nocheck
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

function buscarProcesosCampanias() {
    alert("entra buscarProcesosCampanias");
}

//funcion para llenar combo opcion multiple de campañas
function cargarCatalogoCampañas() {
    $.ajax({
        url: newUrlBase + 'credito/api/catTiposCampanias',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Campania');
            select.empty();
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_tipo_campania + '">' + item.vc_tipo_campania + '</option>');
            });
            
            // Marcar seleccionados
        	select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar tipos campañas', err);
        }
    });
}