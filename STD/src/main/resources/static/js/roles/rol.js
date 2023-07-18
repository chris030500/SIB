var altaRoles = "altaRoles?nombreRol=";
var updateRoles = "updateRoles?idRol=";
var cargarRolEditar = "cargarRol/";
var modificarRoles = "modificarRoles?idRol=";
var inicio = "index";

/**
 * relacionar opciones 
 */
var idsAnteriores = " ";
var altaRelRolOpcion = "altaRelRolOpcion?idsRolOpciones=";
var actualizarOpciones = "actualizarOpciones?idRol=";
var bajaOpciones = "bajaOpciones?idRol=";

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
	});
	
	$("#btnRelRolOpciones").click(function() {
		var ids = "";
		document.querySelectorAll('#menuRol input[type=checkbox]:checked').forEach(function(checkElement) {
			if (checkElement.id != 'idTodos') {
				ids += checkElement.id + " ";
			}
		});
		
		var listaOpciones = "";
		var listaOpcionesAnteriores = "";
		var urlGuardar = "";
		var idRolSelect =$("#idRolRel").val();
		
		
		if (ids.trim().length > 0 && idsAnteriores.trim().length == 0) {
			listaOpciones = ids.trim().split(' ');
			urlGuardar = getAbsolutePath() + altaRelRolOpcion + listaOpciones+"&idRol="+idRolSelect;

			enviarPeticion(urlGuardar);
		}else if (ids.trim().length > 0 && idsAnteriores.trim().length > 0) {
			listaOpciones = ids.trim().split(' ');
			listaOpcionesAnteriores = idsAnteriores.trim().split(' ');

			urlGuardar = getAbsolutePath() + actualizarOpciones + idRolSelect +  "&idsOpciones=" + listaOpciones + "&idsAnteriores=" + listaOpcionesAnteriores;

			enviarPeticion(urlGuardar);
		}else if (ids.trim().length == 0 && idsAnteriores.trim().length > 0) {
			listaOpcionesAnteriores = idsAnteriores.trim().split(' ');
			urlGuardar = getAbsolutePath() + bajaOpciones + idRolSelect + "&idsAnteriores=" + listaOpcionesAnteriores;
			//baja
			enviarPeticion(urlGuardar);
		}else {
			swal({
				title: "",
				text: "No ha seleccionado ninguna opcion",
				icon: "warning",
				confirmButtonText: 'Aceptar'
			})
		}
		
	});
	
	$("#btnCerrarRelRol").click(function() {
		$("#rolMenu").modal("hide");
		limpiarcheck();
	});

	eventosContadorNombreRol();
	eventosContadorDesRol();
	eventosContadorNombreRolE();
	eventosContadorDesRolE();
		
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
	
	$("#menuRol").DataTable({
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
/**Relacionar opciones de Menu */
var consultaOpciones = "consultaOpciones?idRol=";
var idsAnteriores = " ";
function consultarOpciones(idRol){
	document.querySelector('#lblTituloRol').innerText ="Opciones relacionadas al Rol:"+$(idRol).parents("tr").find("td").eq(1).html();
	$("#idRolRel").val($(idRol).attr("id"));
	
	var urlGuardar = getAbsolutePath()+consultaOpciones+$("#idRolRel").val();
	console.log("consultar datos");
	
	$.ajax({
		url: urlGuardar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			idsAnteriores = "";
			for (var i in respuesta) {
				document.querySelectorAll('#menuRol input[type=checkbox]').forEach(function(checkElement) {

					if (checkElement.id != 'idTodos') {
						if (checkElement.id == respuesta[i].fk_opcion) {
							idsAnteriores += checkElement.id + " ";
							checkElement.checked = true;
						}
					}
				});
			}
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
	
}
/**
 * 
 */

function checkTodo() {
	document.querySelectorAll('#menuRol input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = true;
	});
}

function limpiarcheck() {
	document.querySelectorAll('#menuRol input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = false;
	});
}


function checkOpciones(id){
	var idTabla = $(id).attr('id');
	console.log(""+id);
	if (id.checked) {
			document.querySelectorAll('#'+idTabla+' input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = true;
	});
	}else{
			document.querySelectorAll('#'+idTabla+' input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = false;
	});
	}
}

function checkMenu(id){
	var idTabla = $(id).parents("tr").find("table").attr("id");
	console.log(""+id);
	if (id.checked) {
			document.querySelectorAll('#'+idTabla+' input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = true;
	});
	}else{
			document.querySelectorAll('#'+idTabla+' input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = false;
	});
	}
}

function checkTodos(id){
	if (id.checked) {
		document.querySelectorAll('#menuRol input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = true;
	});
	}else{
			document.querySelectorAll('#menuRol input[type=checkbox]').forEach(function(checkElement) {
		checkElement.checked = false;
	});
	}
}

function enviarPeticion(urlGuardar) {

	$.ajax({
		url: urlGuardar,
		type: "POST",
		dataType: "JSON",
		beforeSend : function(){
			 document.getElementById('btnRelRolOpciones').disabled = true;
			 $.blockUI({ message: 'Espere un momento...' });
		},
		complete: function(){
			document.getElementById('btnRelRolOpciones').disabled = false;
			setTimeout($.unblockUI, 2000); 
		},
		success: function(respuesta) {
			$("#rolMenu").modal("hide");
			swal({
				title: "",
				text: respuesta[0],
				icon: "success",
				confirmButtonText: 'Aceptar'
			}).then(resp => {
				limpiarcheck();
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

function eventosContadorNombreRol() {
	const mensaje = document.getElementById('nombreRol');
	const contador = document.getElementById('nombreContador');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function eventosContadorDesRol() {
	const mensaje = document.getElementById('descripcionRol');
	const contador = document.getElementById('desRolContador');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function eventosContadorNombreRolE() {
	const mensaje = document.getElementById('inombreRol');
	const contador = document.getElementById('nomContadorE');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}

function eventosContadorDesRolE() {
	const mensaje = document.getElementById('inputDescripcion');
	const contador = document.getElementById('desRolContadorE');
	mensaje.addEventListener('input', function(e) {
		const target = e.target;
		const longitudMax = target.getAttribute('maxlength');
		const longitudAct = target.value.length;
		contador.innerHTML = `${longitudAct}/${longitudMax}`;
	});
}


