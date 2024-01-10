/**
 * 
 */
var tblReglasAsignacion;
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
		
});