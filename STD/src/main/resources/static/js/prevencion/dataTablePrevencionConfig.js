// @ts-nocheck
var tblHistoricoBases;

$(document).ready(function() {
	
	tblHistoricoBases = $("#tblHistoricoBases").DataTable({
		language: {
			processing: "Procesando...",
			search: "Buscar:",
			lengthMenu: "Mostrar _MENU_ registros",
			info: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
			infoEmpty: "Mostrando registros del 0 al 0 de un total de 0",
			infoFiltered: "(filtrado de un total de _MAX_ registros)",
			infoPostFix: "",
			loadingRecords: "Cargando...",
			zeroRecords: "No se encontraron resultados",
			emptyTable: "Ningún dato disponible en esta tabla",
			paginate: {
				first: "Primero",
				previous: "Anterior",
				next: "Siguiente",
				last: "Último"
			},
			aria: {
				sortAscending: ": Activar para ordenar la columna de manera ascendente",
				sortDescending: ": Activar para ordenar la columna de manera descendente"
			}
		},
		aoColumnDefs: [
			{ bSortable: false, aTargets: -1 },
			{ bVisible: false, aTargets: 0 },
			{ className: 'text-center text-nowrap', aTargets: [1, 2, 3, 4, 5, 6] }
		]
	});
});
