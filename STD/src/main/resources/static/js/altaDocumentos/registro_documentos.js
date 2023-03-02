var consultaDocumento = "altaDocumento/";
var eliminar = "eliminar/";
var reactivar = "reactivar/";
var editar= "editar/";
var inicio = "index";
var separador = "/";

var idEditar = 0;

$(document).ready(function() {
	
	$("#btnAltaProductos").click(function() {
		$("#modalDocumentos").modal("show");
	});
	
	$("#btnCancelar").click(function() {
		$("#modalDocumentos").modal("hide");
		$("#editDocumentos").modal("hide");
	});
	
	$("#btnCancelarEnvio").click(function() {
		$("#editDocumentos").modal("hide");
	});
	
	
	
	$("#btnRegistrar").click(function() {
		if($("#inputDocumento").val().trim().length > 0 ){
			altaDocumentos();
		}else{
			swal({
					title:"Error",
					text: "Ingresar el nombre del Documento",
					icon: "warning",
				});
		}
		
	});
	
	$("#btnEnviar").click(function() {
		if($("#editDocumento").val().trim().length > 0 ){
			registrarEdicion();
		}else{
			swal({
					title:"Error",
					text: "Ingresar el nombre del Documento",
					icon: "warning",
				});
		}
		
	});
	
});

function altaDocumentos(){
	var urlGuardar = getAbsolutePath() + consultaDocumento + $("#inputDocumento").val().trim().replaceAll("/","|");
		//.replace("/","|")
			$.ajax({
			url: urlGuardar,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
			$("#modalDocumentos").modal("hide");
				swal({
					title: "",
					text: respuesta[0],
					icon: "success",
					confirmButtonText: 'Aceptar'
				}).then(resp => {
					window.location.href = getAbsolutePath()+inicio;
				});

			}, error: function(XMLHttpRequest, textStatus, errorThrown) {
				swal({
					title: "Error, ",
					text: "Se presento un error, intente mas tarde.",
					icon: "warning",
				});
			}
		});
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function eliminarDocumento(id){
	console.log(id);
	var urlGuardar = getAbsolutePath() + eliminar + id;
		
			$.ajax({
			url: urlGuardar,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				swal({
					title: "",
					text: respuesta[0],
					icon: "success",
					confirmButtonText: 'Aceptar'
				}).then(resp => {
					window.location.href = getAbsolutePath()+inicio;
				});

			}, error: function(XMLHttpRequest, textStatus, errorThrown) {
				swal({
					title: "Error, ",
					text: "Se presento un error, intente mas tarde.",
					icon: "warning",
				});
			}
		});
	
}

function reactivarDocumento(id){
	console.log(id);
	var urlGuardar = getAbsolutePath() + reactivar + id;
		
			$.ajax({
			url: urlGuardar,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				swal({
					title: "",
					text: respuesta[0],
					icon: "success",
					confirmButtonText: 'Aceptar'
				}).then(resp => {
					window.location.href = getAbsolutePath()+inicio;
				});

			}, error: function(XMLHttpRequest, textStatus, errorThrown) {
				swal({
					title: "Error, ",
					text: "Se presento un error, intente mas tarde.",
					icon: "warning",
				});
			}
		});
	
}
function editarDocumento(id){
	idEditar = $(id).val();
	$("#editDocumento").val($(id).parents("tr").find("td").eq(0).html())
	$("#editDocumentos").modal("show");
	
}

function registrarEdicion(){
			var urlGuardar = getAbsolutePath() + editar +idEditar+separador+$("#editDocumento").val().trim().replaceAll("/","|");
		
			$.ajax({
			url: urlGuardar,
			type: "POST",
			dataType: "JSON",
			success: function(respuesta) {
				swal({
					title: "",
					text: respuesta[0],
					icon: "success",
					confirmButtonText: 'Aceptar'
				}).then(resp => {
					$("#editDocumentos").modal("hide");
					window.location.href = getAbsolutePath()+inicio;
				});

			}, error: function(XMLHttpRequest, textStatus, errorThrown) {
				swal({
					title: "Error, ",
					text: "Se presento un error, intente mas tarde.",
					icon: "warning",
				});
			}
		});
}


