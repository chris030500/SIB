// @ts-nocheck
/**
 * 
 */

var tblConfiguracion;
var tblclasificacionBaseTable;
var tblDetalle;
var tblAdjuntos;
var tblProcesoGestion;
var tblRegistroGestiones;

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
	
	tblConfiguracion = $("#tblConfiguracion").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
	tblclasificacionBaseTable = $("#tblclasificacionBaseTable").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
	tblAdjuntos = $("#tblAdjuntos").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
	tblDetalle = $("#tblDetalle").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	tblProcesoGestion = $("#tblProcesoGestion").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
	tblRegistroGestiones = $("#tblRegistroGestiones").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
});