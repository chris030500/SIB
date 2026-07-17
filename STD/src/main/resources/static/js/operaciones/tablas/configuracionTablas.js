// @ts-nocheck
var vistaTablaTel;
var vistaTablaCA;
var vistaTablaCR;
var vistaTablaRel;

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

	vistaTablaTel = $("#tablaTel").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	vistaTablaCA = $("#tablaCA").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	vistaTablaCR = $("#tablaCR").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	vistaTablaRel = $("#tablaRelacion").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

});