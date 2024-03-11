// @ts-nocheck
/**
 * 
 */
var tblReglasAsignacion;
var tblPlantillasSMS;
var tblParamsAsig;
var tblClienteBloqueados;
var tblMensajesGenerados;
$(document).ready(function() {
	
	tblReglasAsignacion = $("#tblReglasAsignacion").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblPlantillasSMS = $("#tblPlantillasSMS").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	tblParamsAsig = $("#tblParamsAsig").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	tblMensajesGenerados = $("#tblMensajesGenerados").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblClienteBloqueados = $("#tblClienteBloqueados").DataTable({
		"language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
    	},
   		"order": [[0, "desc"]],  // Ordenar por la columna 2 en sentido descendente
    	'columnDefs': [
        {
            'targets': [0],  // Índice de la columna 0
            'visible': false,  // Hacer la columna 0 oculta
            'orderable': true  // Permitir ordenar por la columna 0
        },
        {
            'bSortable': false,
            'aTargets': [-1] /* 1st one, start by the right */
        }
    	]
	});
	
});
