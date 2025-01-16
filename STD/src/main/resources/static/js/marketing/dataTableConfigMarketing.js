var tblPlantillasSMS;

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
 
});

/*document.addEventListener("DOMContentLoaded", function () {
 	tblPlantillas = new DataTable("#tblPlantillas", {
   		language: {
      		url: "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
     	},
     	columnDefs: [
            { orderable: false, targets: -1 } // Desactiva la ordenación para la última columna
        ]
  	});
});*/