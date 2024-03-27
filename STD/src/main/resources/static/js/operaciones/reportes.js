// @ts-nocheck
/**
* Liga absoluta
*/
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/operaciones", "");

var selectBusqueda = document.getElementById("selectBusqueda");
var verSucursal = document.getElementById("verSucursal");
var verCliente = document.getElementById("verCliente");


window.onload = function() {
	//listaDeBusqueda();
	if(selectBusqueda){
		selectBusqueda.addEventListener('change', function () {
			// Obtener el valor seleccionado del input
        	var valorSeleccionado = $(this).val();
        	if(valorSeleccionado.trim().length > 0){
				formulario.style.display = "block";
				destruirListGroup('sucursal');
				destruirListGroup('general');
				destruirListGroup('estatus');
				listaValores = [];
				listaEstatus = [];
				listaSucursales = [];
        	switch(valorSeleccionado){
				case "1":
					if(verCliente){
						document.getElementById("verCliente").style.display = "none";
						document.getElementById("verCliente").style.visibility = "hidden";
					}
					break;
				case "2":
					
					if(verCliente){
						document.getElementById("verCliente").style.display = "block";
						document.getElementById("verCliente").style.visibility = "visible";						
					}
					
					
					break;
				default:
					break;
			}
        	}
			
		});
	}	
}
var buscarSucursal = document.getElementById('buscarSucursal');
buscarSucursal.addEventListener('change', function() {

			// Obtener el valor seleccionado del input
       		var valorSeleccionado = $(this).val();
        
        	if(valorSeleccionado.trim().length > 0){
				var idSelec = $('#opcionSucursal option[value="' + valorSeleccionado + '"]').attr('id');
      		
      			sucursalSeleccionadaS.value = idSelec;
      			listaClientes();
      			
      				
				if(selectBusqueda.value == 1){
      				// Actualiza el valor seleccionado cada vez que se ingresa texto
					llenarGroupSucursales(sucursalSeleccionadaS.value, 'sucursal');
				}
			}
});

var selectGestion = document.getElementById("selectGestion"); 
selectGestion.addEventListener('change', function() {
			// Obtener el valor seleccionado del input
       		var valorSeleccionado = $(this).val();
            
        	if(valorSeleccionado.trim().length > 0){
				//var idSelec = $('#selectGestion option[value="' + valorSeleccionado + '"]').attr('id');
				llenarGroupEstatus(valorSeleccionado, 'estatus');
			}
});

/**
* Fin
*/
var formulario = document.getElementById("formReporte");
var tipoBusqueda;

function listaDeBusqueda() {
	$.ajax({
		url: newUrlBase + "lista_busqueda",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			listaDeGestiones();
			var busqueda = document.getElementById("selectBusqueda");
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				busqueda.add(opcion);
			}
			busqueda.addEventListener("change", function() {
				formulario.style.display = "block";
				tipoBusqueda = busqueda.value;
				buscarSucursal.value = '';
				sucursalValue = '';
				clienteValue = '';
				if (busqueda.value == 53) {
					//document.getElementById("verSucursal").style.display = "block";
					//document.getElementById("verSucursal").style.visibility = "visible";
					document.getElementById("verCliente").style.display = "none";
					document.getElementById("verCliente").style.visibility = "hidden";
					// listaSucursales();
				} else {
					document.getElementById("verCliente").style.display = "block";
					document.getElementById("verCliente").style.visibility = "visible";
					//document.getElementById("verSucursal").style.display = "none";
					//document.getElementById("verSucursal").style.visibility = "hidden";
					listaClientes();
				}
				listaValores = [];
				destruirListGroup('general');
			});
		}, error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}


/**
 * Select de los estatus
 */

var estatusGestion;

// Función para cargar la lista de gestiones desde el servidor
function listaDeGestiones() {
	var busqueda = document.getElementById("selectGestion");
	$.ajax({
		url: newUrlBase + "lista_gestiones",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			for (var i = 0; i < respuesta.lista.length; i++) {
				const opcion = document.createElement("option");
				opcion.value = respuesta.lista[i].pk_id;
				opcion.text = respuesta.lista[i].v_valor;
				busqueda.add(opcion);
			}

			// Evento change para el select de gestiones
			busqueda.addEventListener("change", function() {
				// Al seleccionar un estatus, llenar el list-group
				// console.log(busqueda);
				llenarGroupEstatus(busqueda, 'estatus');
			});

		},
		error: function(error) {
			swal({
				closeOnClickOutside: false,
				text: "Hubo un error al momento de mostrar la información",
				icon: "warning",
			});
		}
	});
}
/**
 * Fin
 */



/**
 * Llenar group de los estatus
 */
let listaEstatus = [];

function llenarGroupEstatus(valor, identificador) {
	var opcionSeleccionada = selectGestion.options[valor]; // Obtener la opción seleccionada del select
	var valorBuscador = opcionSeleccionada.value; // Valor de la opción seleccionada
	var textoOpcion = opcionSeleccionada.text; // Texto de la opción seleccionada

	// Verificar si el valor ya está en la lista
	var valorIgual = listaEstatus.some(function(elemento) {
		return elemento.id === valorBuscador;
	});

	if (!valorIgual) {
		// Agregar el nuevo elemento a la listaEstatus
		listaEstatus.push({ id: valorBuscador, nombre: textoOpcion });
		// Llamar a la función para renderizar el list-group
		renderizarGroupEstatus(identificador);
	} else {
		swal({
			text: "Ya se agregó " + textoOpcion,
			icon: "warning",
		});
	}

	// console.log(listaEstatus);
}


// Función para renderizar la lista de sucursales
function renderizarGroupEstatus(identificador) {
	var contenedorGroup = document.getElementById('group-' + `${identificador}`);

	// Limpiar el contenido del contenedor antes de agregar nuevos elementos
	contenedorGroup.innerHTML = '';

	// Iterar sobre cada objeto en el arreglo
	listaEstatus.forEach(function(elemento) {
		// Crear un nuevo elemento <a> para cada sucursal
		var nuevoElemento = document.createElement('a');
		nuevoElemento.classList.add('list-group-item');
		nuevoElemento.style.background = '#e0e0e0';

		// Crear el contenido del elemento
		nuevoElemento.innerHTML = `
            <div style="display: flex; justify-content: space-between;">
                <div>${elemento.nombre}</div>
                <button class="btn btn-danger btn-sm eliminar-${identificador}" style="background: none; border: none; color: gray;" data-id="${elemento.id}">x</button>
            </div>
        `;

		// Agregar el elemento al contenedor
		contenedorGroup.appendChild(nuevoElemento);
	});

	// Agregar controladores de eventos para los botones de eliminar
	var botonesEliminar = document.querySelectorAll('.eliminar-' + `${identificador}`);
	botonesEliminar.forEach(function(boton) {
		boton.addEventListener('click', function(event) {
			var idElemento = event.target.dataset.id;
			// Eliminar la sucursal tanto del list-group como de la variable listaValores
			listaEstatus = listaEstatus.filter(function(elemento) {
				return elemento.id !== idElemento;
			});
			// Volver a renderizar la lista
			renderizarGroupEstatus(identificador);
		});
	});
}

// Función para destruir el list-group
function destruirListEstatus(identificador) {
	var contenedorSucursales = document.getElementById('group-' + `${identificador}`);
	contenedorSucursales.innerHTML = ''; // Eliminar todos los elementos dentro del contenedor
}
/**
* Fin
*/



/**
 * Generar reporte
 */
function obtenerDatos() {

	var fechaInicio = $('#fechaInicio').val();
	var fechaFin = $('#fechaFin').val();
	switch(selectBusqueda.value){
		case "1":
			if(listaSucursales.length > 0 && fechaInicio != '' && fechaFin != ''  && listaEstatus.length > 0){
				const dto = {
				estatusGestion: listaEstatus,
				valores: listaSucursales,
				fechaInicio: fechaInicio,
					fechaFin: fechaFin,
					busqueda: selectBusqueda.value,
				}

				// console.log(dto);
				generarReporte(dto);
			}else{
				swal("Falta información por llenar", {
					icon: "warning"
				});
			}
			
			break;
		case "2":
			if(listaValores.length > 0 && fechaInicio != '' && fechaFin != ''  && listaEstatus.length > 0){
				const dto = {
				estatusGestion: listaEstatus,
				valores: listaValores,
				fechaInicio: fechaInicio,
					fechaFin: fechaFin,
					busqueda: selectBusqueda.value,
				}

				// console.log(dto);
				generarReporte(dto);
			}else{
				swal("Falta información por llenar", {
					icon: "warning"
				});
			}
			break;
		default:
				swal("Falta información por llenar", {
					icon: "warning"
				});
			break;
		
	}

}
/**
 * Fin
 */


/**
 * Lista sucursales
 */

var sucursalValue;

function listaSucursalesA() {
	var buscar = buscarSucursal.value;
	$.ajax({
		url: newUrlBase + "buscar_sucursal?sucursal=" + buscar,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionSucursal');
			opciones.innerHTML = '';
			respuesta.sucursal.forEach(function(sucursal) {
				var option = document.createElement('option');
				option.setAttribute('data-value', sucursal.pk_id_sucursal);
				option.value = sucursal.vc_nombresucursal;
				opciones.appendChild(option);
			});
			//
			llenarGroupBuscador(buscarSucursal.value, 'general');
		}
	});
}

/*buscarSucursal.addEventListener('change', function() {
	// Obtener el valor seleccionado del input
        var valorSeleccionado = $(this).val();
        
        if(valorSeleccionado.trim().length > 0){
			var idSelec = $('#opcionSucursal option[value="' + valorSeleccionado + '"]').attr('id');
      		
      		sucursalSeleccionadaS.value = idSelec;
      		// Actualiza el valor seleccionado cada vez que se ingresa texto
			llenarGroupBuscador(buscarSucursal.value, 'general');
		}
});*/
/**
 * Fin
 */


/**
 * Lista clientes
 */
var buscarCliente = document.getElementById('buscarCliente');
var spinner = document.getElementById("spinnerClientes");
var clienteValue;
var sucursalSeleccionadaS = document.getElementById("idSucursalInicial");
function listaClientes() {
	buscarCliente.disabled = true;
	spinner.style.display = "block";
	var buscar = sucursalSeleccionadaS.value
	$.ajax({
		url: newUrlBase + "buscar_cliente_activo_sucursal?idSucursal=" + buscar,
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
			//
			//llenarGroupBuscador(buscarCliente.value, 'general');

			buscarCliente.disabled = false;
			spinner.style.display = "none";
		}
	});
}

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	llenarGroupBuscador(buscarCliente.value, 'general');
});
/**
 * Fin
 */


/**
 * Validación de las fechas
 */
document.addEventListener("DOMContentLoaded", function() {
	const fechaInicioInput = document.getElementById("fechaInicio");
	const fechaFinInput = document.getElementById("fechaFin");
	const errorFecha = document.getElementById("errorFecha");

	fechaInicioInput.addEventListener("change", function() {
		// Limpiar el mensaje de error cuando cambia la fecha de inicio
		errorFecha.textContent = "";

		// Actualizar el atributo min del input fechaFin para no permitir fechas anteriores a la de inicio
		fechaFinInput.min = fechaInicioInput.value;
	});

	fechaFinInput.addEventListener("change", function() {
		const fechaInicio = new Date(fechaInicioInput.value);
		const fechaFin = new Date(fechaFinInput.value);

		if (fechaFin < fechaInicio) {
			errorFecha.textContent = "La fecha fin no puede ser anterior a la fecha de inicio";
			fechaFinInput.value = ""; // Limpiar el valor si es inválido
		} else {
			errorFecha.textContent = "";
		}
	});
});
/**
 * Fin
 */


/**
 * Generar reporte de gestión
 */
function generarReporte(dto) {
	var enviar = JSON.stringify(dto);
	$.ajax({
		url: newUrlBase + "reporte_gestion",
		data: enviar,
		type: "POST",
		dataType: "JSON",
		contentType: "application/json; charset=utf-8",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {
			//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
			var urlReporte = window.location.href.substring(0, window.location.href.indexOf("STD/") + 4) + respuesta[0];
			window.open(urlReporte);
			window.focus();
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
 * Fin
 */

/**
 * Llenar group de las sucursales
 */
let listaSucursales = [];

function llenarGroupSucursales(valor, identificador) {
	var valorIgual = false;
	var opcionSeleccionada = document.querySelector('option[id="' + valor + '"]');
	var valorBuscador = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;

	if (valorBuscador) {
		listaSucursales.forEach(function(elemento) {
			if (valorBuscador === elemento.id) {
				valorIgual = true;
				swal({
					text: "Ya se agregó " + opcionSeleccionada.value,
					icon: "warning",
				});
			}
		});

		if (!valorIgual) {
			listaSucursales.push({ id: valorBuscador, nombre: opcionSeleccionada.value });
			// Agregar la sucursal al contenedor
			renderizarGroupSucursales(identificador);
		}

		// console.log(listaValores);
	}
}

// Función para renderizar la lista de sucursales
function renderizarGroupSucursales(identificador) {
	var contenedorGroup = document.getElementById('group-' + `${identificador}`);

	// Limpiar el contenido del contenedor antes de agregar nuevos elementos
	contenedorGroup.innerHTML = '';

	// Iterar sobre cada objeto en el arreglo
	listaSucursales.forEach(function(elemento) {
		// Crear un nuevo elemento <a> para cada sucursal
		var nuevoElemento = document.createElement('a');
		nuevoElemento.classList.add('list-group-item');
		nuevoElemento.style.background = '#e0e0e0';

		// Crear el contenido del elemento
		nuevoElemento.innerHTML = `
            <div style="display: flex; justify-content: space-between;">
                <div>${elemento.nombre}</div>
                <button class="btn btn-danger btn-sm eliminar-${identificador}" style="background: none; border: none; color: gray;" data-id="${elemento.id}">x</button>
            </div>
        `;

		// Agregar el elemento al contenedor
		contenedorGroup.appendChild(nuevoElemento);
	});

	// Agregar controladores de eventos para los botones de eliminar
	var botonesEliminar = document.querySelectorAll('.eliminar-' + `${identificador}`);
	botonesEliminar.forEach(function(boton) {
		boton.addEventListener('click', function(event) {
			var idElemento = event.target.dataset.id;
			// Eliminar la sucursal tanto del list-group como de la variable listaValores
			listaSucursales = listaSucursales.filter(function(elemento) {
				return elemento.id !== idElemento;
			});
			// Volver a renderizar la lista
			renderizarGroupSucursales(identificador);
		});
	});
}


/**
 * Llenar group de los buscadores
 */
let listaValores = [];

function llenarGroupBuscador(valor, identificador) {
	var valorIgual = false;
	var opcionSeleccionada = document.querySelector('option[value="' + valor + '"]');
	var valorBuscador = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-value') : null;

	if (valorBuscador) {
		listaValores.forEach(function(elemento) {
			if (valorBuscador === elemento.id) {
				valorIgual = true;
				swal({
					text: "Ya se agregó " + opcionSeleccionada.value,
					icon: "warning",
				});
			}
		});

		if (!valorIgual) {
			listaValores.push({ id: valorBuscador, nombre: opcionSeleccionada.value });
			// Agregar la sucursal al contenedor
			renderizarGroupBuscador(identificador);
		}

		// console.log(listaValores);
	}
}


// Función para renderizar la lista de sucursales
function renderizarGroupBuscador(identificador) {
	var contenedorGroup = document.getElementById('group-' + `${identificador}`);

	// Limpiar el contenido del contenedor antes de agregar nuevos elementos
	contenedorGroup.innerHTML = '';

	// Iterar sobre cada objeto en el arreglo
	listaValores.forEach(function(elemento) {
		// Crear un nuevo elemento <a> para cada sucursal
		var nuevoElemento = document.createElement('a');
		nuevoElemento.classList.add('list-group-item');
		nuevoElemento.style.background = '#e0e0e0';

		// Crear el contenido del elemento
		nuevoElemento.innerHTML = `
            <div style="display: flex; justify-content: space-between;">
                <div>${elemento.nombre}</div>
                <button class="btn btn-danger btn-sm eliminar-${identificador}" style="background: none; border: none; color: gray;" data-id="${elemento.id}">x</button>
            </div>
        `;

		// Agregar el elemento al contenedor
		contenedorGroup.appendChild(nuevoElemento);
	});

	// Agregar controladores de eventos para los botones de eliminar
	var botonesEliminar = document.querySelectorAll('.eliminar-' + `${identificador}`);
	botonesEliminar.forEach(function(boton) {
		boton.addEventListener('click', function(event) {
			var idElemento = event.target.dataset.id;
			// Eliminar la sucursal tanto del list-group como de la variable listaValores
			listaValores = listaValores.filter(function(elemento) {
				return elemento.id !== idElemento;
			});
			// Volver a renderizar la lista
			renderizarGroupBuscador(identificador);
		});
	});
}

// Función para destruir el list-group
function destruirListGroup(identificador) {
	var contenedorSucursales = document.getElementById('group-' + `${identificador}`);
	contenedorSucursales.innerHTML = ''; // Eliminar todos los elementos dentro del contenedor
}
 /**
* Fin
*/