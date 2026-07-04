// @ts-nocheck
var tablaFiltro;
var tablaGestion;
var tblAlertas;

const idiomaDataTable = {
    emptyTable: "Sin información",
    search: "Buscar:",
    lengthMenu: "Mostrar _MENU_ registros",
    info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
    infoEmpty: "Mostrando 0 a 0 de 0 registros",
    infoFiltered: "(filtrado de _MAX_ registros totales)",
    zeroRecords: "No se encontraron resultados",
    paginate: {
        first: "Primero",
        last: "Último",
        next: "Siguiente",
        previous: "Anterior"
    }
};

$(document).ready(function() {

	tablaFiltro = $("#vistaFiltro").DataTable({
		language: idiomaDataTable,
		order: [[9, "desc"]],
		deferRender: true,
		paging: true,
		pageLength: 100,
		lengthMenu: [[20, 40, 100], [20, 40, 100]],
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	tablaGestion = $("#vista-seguimiento").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	tblAlertas = $("#tblAlertas").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

});