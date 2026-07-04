// @ts-nocheck
$(document).ready(function() {
	$("#productoTable").DataTable({
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1]
		}]
	});
});