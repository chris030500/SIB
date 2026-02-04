var tblPlantillasSMS;
var tblGestionesSMS;
var tblExclusionMunicipios;

$(document).ready(function() {

	tblPlantillasSMS = $("#tblPlantillas").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] // 1st one, start by the right
		}]
	});

	tblGestionesSMS = $("#tblGestionSMS").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] // 1st one, start by the right
		}]
	});

	tblExclusionMunicipios = $("#tblExclusionMunicipios").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] // 1st one, start by the right
		}]
	});

});
