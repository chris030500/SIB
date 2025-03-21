// Variables | validacion de la liga absoluta
window.onload = function() {
	//tablaEmpresas();
	//listaEmpresas();
}

var urlPrincipal = "http://localhost:8091/STD/configuracionSolicitudes/";

// Esta función actualiza el datalist con las empresas
function listaEmpresas() {
    let buscarEmpresa = document.getElementById('buscarEmpresa');
    let opcionEmpresa = document.getElementById('opcionEmpresa');
    let spinner = document.getElementById('spinnerEmpresas');

    if (!buscarEmpresa || !opcionEmpresa || !spinner) {
        console.error("No se encontraron los elementos necesarios en el DOM.");
        return;
    }

    buscarEmpresa.disabled = true;
    spinner.style.display = "inline-block";

    $.ajax({
        url: urlPrincipal + "lista_empresas", 
        type: "GET",
        dataType: "JSON",
        success: function(respuesta) {
            opcionEmpresa.innerHTML = ''; // Limpia las opciones previas

            // Filtramos las opciones a medida que el usuario escribe
            let filtroTexto = buscarEmpresa.value.toLowerCase();
            respuesta.lista.filter(empresa => empresa.nombre.toLowerCase().includes(filtroTexto)).forEach(function(empresa) {
                let option = document.createElement('option');
                option.value = empresa.nombre; 
                option.setAttribute('data-id', empresa.id);
                opcionEmpresa.appendChild(option);
            });

            buscarEmpresa.disabled = false;
            spinner.style.display = "none";
        },
        error: function(error) {
            console.error("Error al obtener empresas:", error);
            spinner.style.display = "none";
        }
    });
}

// Esta función se actualiza para la lista de empleados con el filtro de texto
function listaEmpleados(idEmpresa) {
    let buscarEmpleado = document.getElementById('buscarEmpleado');
    let opcionEmpleado = document.getElementById('opcionEmpleado');
    let spinnerEmpleado = document.getElementById('spinnerEmpleado');

    if (!buscarEmpleado || !opcionEmpleado || !spinnerEmpleado) {
        console.error("No se encontraron los elementos necesarios en el DOM.");
        return;
    }

    buscarEmpleado.disabled = true;
    spinnerEmpleado.style.display = "inline-block";

    $.ajax({
        url: urlPrincipal + "lista_empleados?empresaEmpleado=" + idEmpresa, 
        type: "GET", 
        dataType: "JSON",
        success: function(respuesta) {
            console.log("Lista de empleados recibida:", respuesta);

            let filtroTexto = buscarEmpleado.value.toLowerCase(); // Filtro según el input
            opcionEmpleado.innerHTML = ""; // Limpiar datalist previo

            respuesta.filter(empleado => empleado.nombreCompleto.toLowerCase().includes(filtroTexto))
                .forEach(empleado => {
                    let option = document.createElement('option');
                    option.value = empleado.nombreCompleto; 
                    option.setAttribute('data-id', empleado.clienteId);
                    option.setAttribute('data-numEmp', empleado.numeroEmpleado);
                    option.setAttribute('data-puesto', empleado.tipoEmpleado);
                    opcionEmpleado.appendChild(option);
                });
        },
        error: function(error) {
            console.error("Error al obtener la lista de empleados:", error);
        },
        complete: function() {
            buscarEmpleado.disabled = false;
            spinnerEmpleado.style.display = "none";
        }
    });
}



$(document).ready(function() {
	$('#solicitudModal').on('shown.bs.modal', function() {
		listaEmpresas();
		llenarSelectPuestos();
	});
});


// Detectar cuando el usuario selecciona una opción del datalist
document.getElementById("buscarEmpresa").addEventListener("input", function() {
	var buscarEmpresaValor = this.value; // Obtiene el valor ingresado en el input

	var empresaSeleccionada = Array.from(document.querySelectorAll("#opcionEmpresa option"))
		.find(option => option.value === buscarEmpresaValor);

	if (empresaSeleccionada) {
		var datosEmpresa = empresaSeleccionada.getAttribute("data-id"); // Usamos data-id en lugar de id
		console.log("Empresa seleccionada:", datosEmpresa);
		listaEmpleados(datosEmpresa);
	}
});


function llenarSelectPuestos() {
    let selectPuesto = document.getElementById('puesto');

    if (!selectPuesto) {
        console.error("No se encontró el select de puesto en el DOM.");
        return;
    }

    // Mostrar mensaje de "Cargando..."
    selectPuesto.innerHTML = '<option value="" disabled selected>Cargando...</option>';
    selectPuesto.disabled = true;

    $.ajax({
        url: urlPrincipal + "obtener_puestos", // Endpoint para obtener los puestos
        type: "GET",
        dataType: "JSON",
        success: function(respuesta) {
            console.log("Lista de puestos recibida:", respuesta);

            // Limpiar y volver a llenar el select
            selectPuesto.innerHTML = '<option value="" disabled selected>Seleccione un puesto...</option>';
            respuesta.forEach(puesto => {
                let optionPuesto = document.createElement('option');
                optionPuesto.value = puesto.pkIdTipo; // Suponiendo que cada puesto tiene un ID
                optionPuesto.textContent = puesto.descripcion; // Suponiendo que cada puesto tiene un nombre
                selectPuesto.appendChild(optionPuesto);
            });

            selectPuesto.disabled = false;
        },
        error: function(error) {
            console.error("Error al obtener la lista de puestos:", error);
            selectPuesto.innerHTML = '<option value="" disabled selected>Error al cargar</option>';
        }
    });
}

document.getElementById('buscarEmpleado').addEventListener('change', function () {
    let buscarEmpleado = document.getElementById('buscarEmpleado');
    let opcionEmpleado = document.getElementById('opcionEmpleado');
    let inputNumEmpleado = document.getElementById('numEmpleado'); // Campo de número de empleado
    let inputPuesto = document.getElementById('puesto'); // Campo de puesto

    // Buscar la opción seleccionada
    let selectedOption = Array.from(opcionEmpleado.children).find(option => option.value === buscarEmpleado.value);

    if (selectedOption) {
        // Rellenar los campos con la información de la opción seleccionada
        inputNumEmpleado.value = selectedOption.getAttribute('data-numEmp') || "";
        inputPuesto.value = selectedOption.getAttribute('data-puesto') || "";
    } else {
        // Limpiar los campos si no hay selección válida
        inputNumEmpleado.value = "";
        inputPuesto.value = "";
    }
});





