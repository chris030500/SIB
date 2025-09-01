var tblHistoricoBases;

function peticionPost(urlServicio) {
	$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];

			window.open(urlReporte);
			window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "¡Error!",
				text: "Se presento un error, intente mas tarde.",
				icon: "error",
			});
		}
	});
}

function generarHistoricoEBR(idProceso, tipoReporte) {
	
	const urlCompleta = `${getAbsolutePath()}generarHistoricoBasesEBR?idProceso=${Number(idProceso)}&tipoReporte=${tipoReporte}`; 
    	
	peticionPost(urlCompleta);
}

$(document).ready(function() {
	
	tblHistoricoBases = $("#tblHistoricoBases").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [
			{ 'bSortable': false, 'aTargets': [-1] },
			{ 'bVisible': false, 'aTargets': [0] },
			{ 'className': 'text-center', 'aTargets': [1,2,3,4,5,6] }
		]
	});
	
	// Delegación de eventos para los botones
	$('#tblHistoricoBases tbody').on('click', 'button', function() {
		const tr = $(this).closest('tr');
		const rowData = tblHistoricoBases.row(tr).data();
		const pkId = rowData[0];

		if ($(this).hasClass('base-cliente')) {
			generarHistoricoEBR(pkId, 1);
		} else if ($(this).hasClass('base-operaciones')) {
			generarHistoricoEBR(pkId, 2);
		} else if ($(this).hasClass('base-remesas')) {
			generarHistoricoEBR(pkId, 3);
		} else if ($(this).hasClass('base-desembolsos')) {
			generarHistoricoEBR(pkId, 4);
		}
	});	
	
});
