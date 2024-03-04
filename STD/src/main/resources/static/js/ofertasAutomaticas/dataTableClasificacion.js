// @ts-nocheck
/**
 * 
 */
var tblConfiguracion;
var tblclasificacionBaseTable;
var tblDetalle;
var tblAdjuntos;
var tblProcesoGestion;
var tblRegistroGestiones;
$(document).ready(function() {
	
	tblConfiguracion = $("#tblConfiguracion").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblclasificacionBaseTable = $("#tblclasificacionBaseTable").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblAdjuntos = $("#tblAdjuntos").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblDetalle = $("#tblDetalle").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	tblProcesoGestion = $("#tblProcesoGestion").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblRegistroGestiones = $("#tblRegistroGestiones").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	
});