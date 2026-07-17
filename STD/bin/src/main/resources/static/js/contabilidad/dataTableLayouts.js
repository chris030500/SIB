// @ts-nocheck
/**
 * 
 */
var url = "";
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var tblAdjuntos;

$(document).ready(function() {

	tblAdjuntos = $("#tblAdjuntos").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});

});

function tamFile() {
	const tamMaxFile = 31457280;
	var fileTam = document.getElementById("file");
	fileTam.onchange = function() {
		if (this.files[0].size > tamMaxFile) {
			swal("El archivo seleccionado excede el tamaño maximo permitido (30MB)", {
				closeOnClickOutside: false,
				icon: "warning",
			}).then(resp => {
				this.value = "";
			});
		};
	};
}

$("#btnGuardarFile").click(function() {

	if (document.getElementById("file").value.length > 0) {
		swal({
			title: "Procesando información.",
			content: {
				element: "div",
				attributes: {
					innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
					style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
				},
			},
			timer: 4000, // tiempo en milisegundos
			buttons: false,
			closeOnClickOutside: false,
			closeOnEsc: false,
		}).then((result) => {
			// Este código se ejecutará después de que el temporizador haya terminado

			var form = $('#adjuntoArchivoOficio')[0];
			var data = new FormData(form);
			addArchivos(data)

		});

	} else {

		swal("Es necesario seleccionar un Archivo.", {
			closeOnClickOutside: false,
			icon: "error",
		});
	}

});

var agregarArchivos = "subirArchivo?idBase=2";
function addArchivos(data) {
	
	$.ajax({
		async: false,
		url: getAbsolutePath() + agregarArchivos,
		type: "POST",
		enctype: 'multipart/form-data',
		data: data, //pass the form data
		processData: false,
		contentType: false,
		complete: function() {
			swal.close();
		},
		success: function(data) {
			
			swal(data[0], {
				closeOnClickOutside: false,
				icon: "success",
			}).then(resp => {
				adjuntarFiles(2);
			});
		},
		error: function(data) {
					
			swal("El tamaño del archivo es mayor al permitirdo 30MB.", {
				closeOnClickOutside: false,
				icon: "warning",
			});
			
			
		},
		complete: function() {
			setTimeout($.unblockUI, 600);
		}
	});

}