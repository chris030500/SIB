// @ts-nocheck
var tablaFiltro;
var tablaGestion;
var tblAlertas;

$(document).ready(function() {
	tablaFiltro = $("#vistaFiltro").DataTable({
    "language": {
        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
    },
    "order": [[9, "desc"]],  // Ordenar por la columna 2 en sentido descendente
    "deferRender": true, // Renderizado diferido
    "paging": true, // Habilitar paginación
    "pageLength": 100, // Número de registros por página
    "lengthMenu": [[20, 40, 100], [20, 40, 100]], // Opciones de longitud de página
    'aoColumnDefs': [{
        'bSortable': false,
        'aTargets': [-1] // 1st one, start by the right
    }]
	});

	tablaGestion = $("#vista-seguimiento").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblAlertas = $("#tblAlertas").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
});