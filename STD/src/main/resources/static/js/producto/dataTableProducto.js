// @ts-nocheck
$(document).ready(function() {
	$("#productoTable").DataTable({
		"language": {
            url: "/vendor/datatablesNew/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
});