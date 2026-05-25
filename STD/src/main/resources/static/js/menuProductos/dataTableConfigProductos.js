// @ts-nocheck

var tblReglasAsignacion;
var tblPlantillasSMS;
var tblParamsAsig;
var tblClienteBloqueados;
var tblMensajesGenerados;
var tblMunicipiosConCobertura;

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
	
	tblReglasAsignacion = $("#tblReglasAsignacion").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
	tblPlantillasSMS = $("#tblPlantillasSMS").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	tblParamsAsig = $("#tblParamsAsig").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});

	tblMensajesGenerados = $("#tblMensajesGenerados").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
	tblClienteBloqueados = $("#tblClienteBloqueados").DataTable({
		language: idiomaDataTable,
		order: [[0, "desc"]],
		columnDefs: [
			{
				targets: [0],
				visible: false,
				orderable: true
			},
			{
				bSortable: false,
				aTargets: [-1]
			}
		]
	});
	
	tblMunicipiosConCobertura = $("#tblMunicipiosConCobertura").DataTable({
		language: idiomaDataTable,
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1]
		}]
	});
	
});