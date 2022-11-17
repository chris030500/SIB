function agregarUsuario() {
	$.ajax({
		url: "http://localhost:8090/usuario/altaUsuario",
		type: "GET",
		success: function() {
			console.log("Entro en success de get");


		}
	});
}

function buscarEmpleado(empleado) {
	//var selectUsuario = $('#selectUsuario');

	var busquedaEmpleado = document.getElementById("busquedaEmpleado").value;
	var divEmpleado = document.getElementById("datosUsuario");
	console.log("Campo busquedaEmpleado: " + busquedaEmpleado.length);
	var option = '';
	console.log("Empleado: " + empleado);
	if (busquedaEmpleado == 0) {
		console.log("Entro en if de busquedaEmpleado");
		$("select#selectEmpleado").empty();
		$("#EmpleadoId").val('');
		$("#Nombre").val('');
		$("#Puesto").val('');
		divEmpleado.style.display = "none";


	} else {
		console.log("Entro en else de busquedaEmpleado");
		$.ajax({
			url: "http://localhost:8090/empleado/listado?nombre=" + empleado,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				/*console.log(respuesta);
				console.log(respuesta.length);*/
				for (var i = 0; i < respuesta.length; i++) {
					var nombre = respuesta[i].nombreCompleto;
					var idUsuario = respuesta[i].empleadoID;
					/*console.log()
					console.log("nombre : " + nombre);
					console.log("idUsuario : " + idUsuario);*/
					option += '<option value="' + respuesta[i].empleadoID + ' " data-descripcion=' + respuesta[i].descripcion + ' >' + respuesta[i].nombreCompleto + '</option>';
					$("select#selectEmpleado").empty().append(option);
					//' data-descripcion='+ respuesta[i].descripcion+'
				}

			}
		});
	}

}

document.getElementById('selectEmpleado').onchange = function() {
	console.log("Entro en jquery");
	var selectEmpleado = document.getElementById('selectEmpleado').value;
	var divEmpleado = document.getElementById("datosUsuario");
	console.log("SelectEmpleado: " + selectEmpleado);
	if(selectEmpleado > 0){
		console.log("Entro en mayor a 0");
		divEmpleado.style.display = "block";
	} else{
		console.log("No tiene valor");
	}
	/* Referencia al option seleccionado */
	var mOption = this.options[this.selectedIndex];
	/* Referencia a los atributos data de la opción seleccionada */
	var mData = mOption.dataset;
	
	/* Referencia a los input */
	var EmpleadoId = document.getElementById('EmpleadoId');
	var Nombre = document.getElementById('Nombre');
	var Puesto = document.getElementById('Puesto');


	//Asignamos el valor del ID Empleado
	EmpleadoId.value = this.value;
	//Asignamos el valor del nombre del empleado
	Nombre.value = mOption.text;
	//Asignamos el valor del puesto del empleado
	Puesto.value = mData.descripcion;  /*Se usará el valor que se muestra*/
	
};

$(document).ready(function($) {
	$("#edit-form").submit(function(event) {
		event.preventDefault();
		postEdit();
	});
});

function postEdit() {
	console.log("Entro en postEdit");
	var something = $('#edit-form').serialize();
	var editUrl = url + 'update';
	$.post(editUrl, something, function(data) {
		updateTable(data);
	});
	clearAndCloseModal('#basicModal');
}

function showEditModal(index) {
	console.log("index: " +index);
	var url = "http://localhost:8090/usuario/usuario/loadEntity/";
	
	console.log("entro en showEditModal");

	var editUrl = url + index;
	console.log("editUrl: " + editUrl);
	loadEntity(editUrl);
}

function loadEntity(url) {
	console.log("entro en loadEntity")
	console.log("url: " + url);
	$.getJSON(url, {}, function(data) {
		populateModal(data);
	});
}

// Assign the data values to the modal form.
function populateModal(data) {
	console.log("Data en populateModal: " + data)
	$('#nombre').val(data.vc_nombre_usuario);
	//$('#update-name').val(data.name);
}
