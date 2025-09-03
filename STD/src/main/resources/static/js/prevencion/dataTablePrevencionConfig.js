var tblHistoricoBases;

$(document).ready(function() {
	
	tblHistoricoBases = $("#tblHistoricoBases").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [
			{ 'bSortable': false, 'aTargets': [-1] },
			{ 'bVisible': false, 'aTargets': [0] },
			{ 'className': 'text-center text-nowrap', 'aTargets': [1,2,3,4,5,6] }
		]
	});
});
