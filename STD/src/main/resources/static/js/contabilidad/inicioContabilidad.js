// @ts-nocheck
/**
*
*/

var id = "";
var idDiv = "";
var opcion = "";

var tblConfiguracion;
var tblProcesoGeneradorCNF;

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
	
	tblProcesoGeneradorCNF = $("#tblProcesoGeneradorCNF").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}],
		"order": [[1, 'desc']],
		"columnDefs": [{
       		"targets": 1, // Índice de la columna de fecha
        	"type": "string" // Ordenación como string
    	}]
	});

});

/*function ocultarMostrar(divEl) {
	id = $(divEl).attr("id");
	idDiv = id + 1;
	opcion = "#pant-tabs" + " #" + idDiv;
	switch ($(opcion).attr("for")) {
		case "1":
			console.log("Parametros");
			break;
		case "2":
			console.log("layout");
			break;
		default:
			break;
	}

	mostrarOcultarDiv(idDiv);
}*/

function ocultarMostrar(divA) {
	const navOpcion =$(divA).attr('id')+1;
	
	$('.opciones').hide();
	$("#"+navOpcion).show();
}


