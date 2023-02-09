/**
 * 
 */

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

/**
 * Handle actual handling of the files.
 */
function uploadFile() {
	console.log("Lenght: " + document.getElementsByClassName("archivo").length);
	/*var d = document.getElementsByClassName("archivo");
	console.log("Archivo: " +  JSON.stringify(d));*/
	var solicitud = document.getElementById("solicitudCredito").value;
	var longitud = document.getElementsByClassName("archivo").length;
	console.log(document.getElementsByClassName("archivo"));
	console.log("Solicitud de credito: " + solicitud);
	var idSolicitud = obtenerIdSolicitud(solicitud);
	uploadFile2();
	for (i = 0; i < longitud; i++) {
		var documento = document.getElementsByClassName("archivo")[i].id;
		var n = document.getElementsByClassName("archivo")[i].files[0].name;
		console.log("documento: " + documento);
		console.log("Files: " + JSON.stringify(n));
		registrarDoctEnvio(solicitud, documento, n);
	}

}
function registrarDoctEnvio(solicitud, documento, nombreDocumento) {
	var urlBase = getAbsolutePath();
	console.log("Entro en registrarDoctEnvio");
	$.ajax({
		url: urlBase + "doctEnvio?solicitud=" + solicitud + "&documento=" + documento + "&nombreDocumento=" + nombreDocumento,
		type: "POST",
		dataType: 'json',
	})
		.then(function(result) {
			console.log("Result: " + JSON.stringify(result));
			/*console.log(result.value);
			console.log(result.name);
			console.log(result.action);*/
		});
}

function obtenerIdSolicitud(solicitud) {
	console.log("Entro en obtenerIdSolicitud");
	var urlBase = getAbsolutePath();
	$.ajax({
		url: urlBase + "solicitudEnvio/obtenerSolicitudFecha/" + solicitud,
		type: "GET",
		success: function(data) {
			console.log("Exitoso");
			console.log("Data: " + JSON.stringify(data));
			//console.log("Id solicitud: " + data[0].pk_id_solicitud_envio);
			return data[0].pk_id_solicitud_envio;
		},
		error: function() {
			console.log("Error");
		}
	});
}

function uploadFile2() {
	//var url = "YOUR-BASE-URL-HERE"; Like http://localhost:8081
	var urlBase = getAbsolutePath();
	var form = $('#form-upload')[0]; //get the form containing the files
	console.log("form: " + form.value);
	var data = new FormData(form);
	console.log("Data: " + JSON.stringify(data));
	$.ajax({
		url: urlBase + "guardarPDFServidor/upload2",
		type: "POST",
		enctype: 'multipart/form-data',
		data: data, //pass the form data
		processData: false,
		contentType: false,
		success: function(data) {
			console.log("Successfully uploaded " + data);
		},
		error: function(data) {
			console.log("Data: " + JSON.stringify(data));
			console.log("upload failed");
		}
	});
}

//Funcion para cargar los archivos a la carpeta del servidor
function processUpload(e) {
	var urlBase = getAbsolutePath();
	console.log("Entro en processUpload");
	console.log("e" + e);
	//console.log( "Nombre del archivo: " + document.getElementById('fileLoader').files[0].name);
	var oMyForm = new FormData();
	oMyForm.append("file", files[0]);

	console.log(oMyForm);
	if (document.getElementById("fileLoader").files.length === 0) {
		swal({
			icon: 'info',
			title: "No se ha ingresado un archivo, seleccione un archivo",
			showConfirmButton: false
		});
	} else {
		$.ajax({
			dataType: 'json',
			url: urlBase + "guardarPDFServidor/upload",
			data: oMyForm,
			type: "POST",
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			success: function(result) {
				swal({
					icon: 'success',
					title: "Archivo subido correctamente.",
					showConfirmButton: false
				});
			}, error: function(result) {
				if (result.status === 200) {
					swal({
						icon: 'success',
						title: "Archivo subido correctamente.",
						showConfirmButton: false
					});
				} else {
					swal({
						icon: 'warning',
						title: "No se subio el archivo."
					});
				}
			}
		});
	}

}