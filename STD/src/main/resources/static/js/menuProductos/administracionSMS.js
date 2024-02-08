// @ts-nocheck
var botones="";
function getAbsolutePath() {
	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);
}

window.onload = function() {
	listaProcesos();
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/menuProductos", "");

var obtDatosMensajeSMS = "mensajes/obtSMS";
 function listaMensajes(){
	 url = getAbsolutePath()+obtDatosMensajeSMS;
	 $.getJSON(url, {}, function(data) {
		 tblMensajesGenerados.clear().draw();
		 $.each(data, function(idx, opt) {
		 var botonesActivar = "";
			 		/*var botonesActivar = " <button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' "
					+ " title='Actualizar Estatus'  type='button'"
					+ " onclick='actualizarEstatus("
					+ opt.pk_id_plantilla
					+ ")' id="
					+ opt.pk_id_plantilla
					+ " data-tooltip='Add'>"
					+ ((opt.i_activo == 1)?"<i class='bi bi-unlock-fill'></i></button>":"<i class='bi bi-lock-fill'></i></button>")
					+ "&nbsp;";*/
		 var botonesEditar = "";
			 		var botonesEditar = "<button class='btn btn-secundary bg-info text-black btn-xs' tabindex='0' data-bs-toggle='modal'"
					+ " title='Editar Registro' data-bs-target='#altaPlantilla' aria-controls='table-datatable' type='button'"
					+ " onclick='editarPlantilla("
					+ opt.pk_id_plantilla
					+ ")' id="
					+ opt.pk_id_plantilla
					+ " data-tooltip='Add'>"
					+ " <i class='fa-solid fa-pen-to-square'></i></button>";
				
			 	botones = botonesActivar+botonesEditar;
			 	
			 tblMensajesGenerados.row.add([opt.clienteID, opt.nombreCompleto, opt.nombreBase, opt.nombreClasificacion, opt.telefonoCelular, opt.descMensaje, (opt.estMensaje == 1?'ACTIVO':'INACTIVO'),botones]).draw(false);
		 });
	 }); 
 }
 
 // Función para mostrar u ocultar los campos de búsqueda según la opción seleccionada
function toggleSearchFields() {
    var filterType = document.getElementById("filterType").value;
    if (filterType === "client") {
        document.getElementById("clientSearchField").style.display = "block";
        document.getElementById("branchSearchField").style.display = "none";
        listaClientes();
        listaProcesos()
    } else if (filterType === "branch") {
        document.getElementById("clientSearchField").style.display = "none";
        document.getElementById("branchSearchField").style.display = "block";
        listaSucursales();
    } else {
        document.getElementById("clientSearchField").style.display = "none";
        document.getElementById("branchSearchField").style.display = "none";
    }
}

// Llamar a la función toggleSearchFields() cuando se cambie la opción del select
document.getElementById("filterType").addEventListener("change", toggleSearchFields);

var buscarCliente = document.getElementById('buscarCliente');
var spinner = document.getElementById("spinnerClientes");
var clienteValue;

function listaClientes() {
	buscarCliente.disabled = true;
	spinner.style.display = "block";
	var buscar = buscarCliente.value = '';
	$.ajax({
		url: urlPrincipal.replace("/operaciones", "") + "buscar_cliente_activo?valor=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionCliente');
			opciones.innerHTML = '';
			respuesta.lista.forEach(function(cliente) {
				var option = document.createElement('option');
				option.setAttribute('data-value', cliente.i_clienteid);
				option.value = cliente.vc_nombrecompleto;
				opciones.appendChild(option);
			});

			// Aquí, después de agregar las opciones, actualiza el valor seleccionado
			actualizarValorSeleccionado();
			buscarCliente.disabled = false;
			spinner.style.display = "none";
		}
	});
}

var obtDatosProcesos = "mensajes/obtProcesos"
function listaProcesos() {
	console.log("iNICIA fUNCION")
	url = getAbsolutePath()+obtDatosProcesos;
	console.log(url)
	 $.getJSON(url, {}, function(data) {
		 console.log(data)
	 }); 
}

function actualizarValorSeleccionado() {
	var opcionSeleccionada = document.querySelector('option[value="' + buscarCliente.value + '"]');
	clienteValue = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;
}

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	actualizarValorSeleccionado();
});
