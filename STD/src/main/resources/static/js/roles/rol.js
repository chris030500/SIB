var altaRoles = "altaRoles?nombreRol=";
var updateRoles = "updateRoles?idRol=";
var cargarRolEditar = "cargarRol/";
var modificarRoles = "modificarRoles?idRol=";
var inicio = "index";


$(document).ready(function() {
	
	$("#btnRegistrar").click(function() {
		
		if(vacioAlta() == false){
			altaRol();
		}else{
			swal({
					title:"Error",
					text: "Registrar datos obligatorios",
					icon: "warning",
				});
		}
	});
	
	$("#btnCancelar").click(function() {
		$("#modalRol").modal("hide");
		document.getElementById("nombreRol").style.borderColor = '';
		document.getElementById("descripcionRol").style.borderColor = '';
		document.getElementById("nombreRol").value = "";
		document.getElementById("descripcionRol").value = "";
	});
	
	$("#btnModificar").click(function() {
		
		if(vacioModificar() == false){
			modificarRol();
		}else{
			swal({
					title:"Error",
					text: "Registrar datos obligatorios",
					icon: "warning",
				});
		}
	});
	
	
	
	
	$("#btnCerrar").click(function() {
		$("#editarRol").modal("hide");
		document.getElementById("inombreRol").style.borderColor = '';
		document.getElementById("inputDescripcion").style.borderColor = '';
		//document.getElementById("nombreRol").value = "";
		//document.getElementById("descripcionRol").value = "";
	});
	
	
	inicioTabla();
});

function altaRol(){
		var nombreRol = document.getElementById("nombreRol").value;
		var descripcionRol = document.getElementById("descripcionRol").value;
		var urlServicio = getAbsolutePath() + altaRoles+nombreRol+"&descripcionRol="+descripcionRol;
		$("#modalRol").modal("hide");
		
		peticion(urlServicio);
}

function modificarRol(){
		var nombreRol = document.getElementById("inombreRol").value;
		var descripcionRol = document.getElementById("inputDescripcion").value;
		var idRol = document.getElementById("idRol").value;
		var urlServicio = getAbsolutePath() + modificarRoles+idRol+"&nombreRol="+nombreRol+"&descripcionRol="+descripcionRol;
		$("#editarRol").modal("hide");
		
		peticion(urlServicio);
}


function vacioAlta(){
	var validacion = false;
	if (document.getElementById("nombreRol").value.length == 0) {
		document.getElementById("nombreRol").style.borderColor = "#F70707";
		validacion = true;
	} 
	if (document.getElementById("descripcionRol").value.length == 0) {
		document.getElementById("descripcionRol").style.borderColor = "#F70707";
		validacion = true;
	}
	
	return validacion;
}

function vacioModificar(){
	var validacion = false;
	if (document.getElementById("inombreRol").value.length == 0) {
		document.getElementById("inombreRol").style.borderColor = "#F70707";
		validacion = true;
	} 
	if (document.getElementById("inputDescripcion").value.length == 0) {
		document.getElementById("inputDescripcion").style.borderColor = "#F70707";
		validacion = true;
	}
	
	return validacion;
}


function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function inicioTabla(){
	$("#rolTable").DataTable({
		"language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
}

function inactivarRol(btnID) {
	var idRol = $(btnID).parents("tr").find("td").eq(0).html();
	var estatus = $(btnID).parents("tr").find("td").eq(3).html();
	
	Swal.fire({
        title: "¿Actualizar Estatus del Rol?",
        text: $(btnID).parents("tr").find("td").eq(1).html(),
        showCancelButton: true,
        confirmButtonText: "Sí, Actualizar",
        cancelButtonText: "Cancelar",
    })
    .then(resultado => {
        if (resultado.value) {
			actualizarEstatus(idRol,estatus);
        }
    });
}

function actualizarEstatus(idRol,estatus){
	
	var urlServicio = getAbsolutePath() + updateRoles+idRol+"&estatus="+estatus;
	peticion(urlServicio);

}
function peticion(urlServicio){
		$.ajax({
				url: urlServicio,
				type: "POST",
				dataType: "JSON",
				beforeSend: function() {
					$.blockUI({ message: 'Espere un momento...' });
				},
				complete: function() {
					setTimeout($.unblockUI, 500); 
				},
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

function modalEditarRol(index) {
	var url = getAbsolutePath() + cargarRolEditar;
	var editUrl = url + index;
	loadCambioRol(editUrl);
}

function loadCambioRol(url){
	$.getJSON(url, {}, function(data) {
		populateModalRol(data);
	});
}

function populateModalRol(data) {
	var idRol = data.pk_id_rol;
	var nombreRol = data.vc_nombre_rol;
	var descripcionRol = data.vc_descripcion_rol;
	$('#idRol').val(idRol);
	$('#inombreRol').val(nombreRol);
	inputDescripcion.value = descripcionRol;
}

