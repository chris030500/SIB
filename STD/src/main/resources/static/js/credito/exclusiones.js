document.addEventListener("DOMContentLoaded", function() {
	listaMunicipiosExclusion();
	listaClientes();

});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");
// @ts-ignore
let idCliente = null;

function ocultarMostrar(btn) {
	console.log(newUrlBase);
	const tabs = document.querySelectorAll('#pant-tabs > div[id$="-tab1"]');
	// @ts-ignore
	tabs.forEach(tab => tab.style.display = 'none'); // Oculta todos

	// Muestra solo el tab relacionado con el botón
	const id = btn.id + "1"; // ej: "credenciales-tab1"
	const target = document.getElementById(id);
	if (target) {
		target.style.display = 'block';
	}

	// Cambia la clase 'active' en los botones
	document.querySelectorAll('#pntConf .nav-link').forEach(el => el.classList.remove('active'));
	btn.classList.add('active');
}

function cargarArchivoExcel() {
	const input = document.getElementById('inputArchivo');
	// @ts-ignore
	const archivo = input.files[0];

	if (!archivo) {
		alert("Por favor, selecciona un archivo.");
		return;
	}

	const formData = new FormData();
	formData.append("archivo", archivo);

	fetch(newUrlBase + "credito/cargarMunicipios", {
		method: "POST",
		body: formData
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la solicitud: " + response.statusText);
			}
			return response.text();
		})
		.then(data => {
			alert(data); // Mensaje del backend
		})
		.catch(error => {
			console.error("Error:", error);
			alert("Ocurrió un error al cargar el archivo.");
		});
}

function listaMunicipiosExclusion() {
	url = newUrlBase + "credito/listarMunicipios";
	$.getJSON(url, {}, function(data) {
		tblExclusionMunicipios.clear().draw();
		$.each(data, function(idx, opt) {

			tblExclusionMunicipios.row.add([
				opt.codigo,
				opt.asentamiento,
				opt.tipoAsentamiento,
				opt.municipio,
				opt.estado,
				opt.ciudad,
				opt.codigoPostal,
				opt.codigoEstado,
				opt.cobertura]).draw(false);
		});
	});
}

function listaClientes() {
	let buscarEmpleado = document.getElementById('buscarEmpleado');
	let opcionEmpleado = document.getElementById('opcionEmpleado');
	let spinnerEmpleado = document.getElementById('spinnerEmpleado');

	if (!buscarEmpleado || !opcionEmpleado || !spinnerEmpleado) {
		console.error("No se encontraron los elementos necesarios en el DOM.");
		return;
	}

	// @ts-ignore
	buscarEmpleado.disabled = true;
	spinnerEmpleado.style.display = "inline-block";

	$.ajax({
		url: newUrlBase + "credito/lista_clientes",
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {

			// @ts-ignore
			let filtroTexto = buscarEmpleado.value.toLowerCase(); // Filtro según el input
			opcionEmpleado.innerHTML = ""; // Limpiar datalist previo

			respuesta.filter(empleado => empleado.vc_nombrecompleto.toLowerCase().includes(filtroTexto))
				.forEach(empleado => {
					let option = document.createElement('option');
					option.value = empleado.vc_nombrecompleto;
					option.setAttribute('data-id', empleado.i_clienteid);
					opcionEmpleado.appendChild(option);
				});
		},
		error: function(error) {
			console.error("Error al obtener la lista de empleados:", error);
		},
		complete: function() {
			// @ts-ignore
			buscarEmpleado.disabled = false;
			spinnerEmpleado.style.display = "none";
		}
	});

}

$('#buscarEmpleado').on('change', function() {
	const valor = $(this).val();
	//let idCliente = null;

	$('#opcionEmpleado option').each(function() {
		if ($(this).val() === valor) {
			idCliente = $(this).data('id');
		}
	});
	
	//deshabilita el boton eliminar cliente y agregar cliente
	// @ts-ignore
	document.getElementById("btnEliminarCliente").disabled = true;
	// @ts-ignore
	document.getElementById("btnAgregarCliente").disabled = true;

	//$('#clienteIdSeleccionado').val(idCliente);
	
	//validar si el clientes esta bloqueado
	
	$.ajax({
	    url: newUrlBase + "credito/api/countClienteEliminar?idCliente="+idCliente,
	    type: "GET",
	    success: function(data) {
			
	        if(data > 0){
				
				//habilta el boton Eliminar
				// @ts-ignore
        		document.getElementById("btnEliminarCliente").disabled = false;
        		
			}else{
				//habilta el boton Agregar
				// @ts-ignore
        		document.getElementById("btnAgregarCliente").disabled = false;
			}
	    },
	    error: function(err) {
	        console.error("Error al obtener si el cliente esta eliminado:", err);
	    }
	});
	
});


$('#formExclusionCliente').on('submit', function (e) {
    e.preventDefault(); // Evita el envío tradicional

    // Aquí capturas los datos
    const clienteId = $('#clienteIdSeleccionado').val();
    const clienteNombre = $('#buscarEmpleado').val();
    const motivo = $('#motivo').val();
    const observaciones = $('#observaciones').val();

	
	//console.error(clienteId);
	//console.error(idCliente);
	
	
    if (!idCliente) {
        alert("Selecciona un cliente válido de la lista.");
        return;
    }
    const data = {
	    clienteId:idCliente,
            buscarEmpleado: clienteNombre,
            motivo,
            observaciones,
	};
    const jsonBitacora = JSON.stringify(data);
	let tabla = 'TBL_EXCLUSION_CLIENTES';
	//console.log(jsonBitacora);
	const dataHis = {
	    vc_json_entrada: jsonBitacora,
	    vc_tabla: tabla,
	    // @ts-ignore
	    dt_fecha_registro:document.getElementById("idFecha").value,
	    // @ts-ignore
	    i_user_reg: parseInt(document.getElementById("idUser").value),
	    
	};

    // Enviar por AJAX
    $.ajax({
        url: newUrlBase + "credito/guardarCliente",
        type: "POST",
        data: {
            clienteId:idCliente,
            buscarEmpleado: clienteNombre,
            motivo,
            observaciones
        },
        success: function () {
            //alert("Cliente excluido correctamente.");
            // @ts-ignore
            swal({
			title: "",
			text: 'Cliente excluido correctamente.',
			icon: "success",
		    }).then(() => {
		        //window.location.href = newUrlBase + "credito/tiposCampanias"; // Redirige después de aceptar
		    });
		    fetch(newUrlBase + 'credito/api/crearHisExcCliente', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(dataHis)
		    })
            // @ts-ignore
            $('#formExclusionCliente')[0].reset();
            $('#clienteIdSeleccionado').val('');
            //deshabilita el boton agregar cliente
			// @ts-ignore
			document.getElementById("btnAgregarCliente").disabled = true;
        },
        error: function (xhr) {
            console.error(xhr);
            alert("Error al guardar exclusión.");
        }
    });
});

function eliminarCliente() {
	// @ts-ignore
    $('#formExclusionCliente')[0].reset();
    $('#clienteIdSeleccionado').val('');
	//deshabilita el boton eliminar cliente
	// @ts-ignore
	document.getElementById("btnEliminarCliente").disabled = true;
	
	//console.log(idCliente);
	
	const data = {
	    idCliente: idCliente,
	    estatusCliente: 0,
	};
	//console.log(data);
	const jsonBitacora = JSON.stringify(data);
	let tabla = 'TBL_EXCLUSION_CLIENTES';
	//console.log(jsonBitacora);
	const dataHis = {
	    vc_json_entrada: jsonBitacora,
	    vc_tabla: tabla,
	    // @ts-ignore
	    dt_fecha_registro:document.getElementById("idFecha").value,
	    // @ts-ignore
	    i_user_reg: parseInt(document.getElementById("idUser").value),
	    
	};
	
	fetch(newUrlBase + 'credito/api/actualizarExcCliente', {
	
	    method: 'POST',
	    headers: {
	        'Content-Type': 'application/json'
	    },
	    body: JSON.stringify(data)
	})
	.then(resp => {
	    if (!resp.ok) throw new Error("Error al actualizar exclusion cliente");
	    return resp.text();
	})
	.then(() => {
		
		///
		fetch(newUrlBase + 'credito/api/crearHisExcCliente', {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(dataHis)
	    })
		///
		
		// @ts-ignore
		swal({
		title: "",
		text: 'Actualizado correctamente',
		icon: "success",
	    }).then(() => {
	        //window.location.href = newUrlBase + "credito/tiposCampanias"; // Redirige después de aceptar
	    });
	})
	.catch(err => {
	    console.error("Error al actualizar exclusion cliente:", err);
	   // @ts-ignore
	   swal({
		title: "",
		text: 'Error al actualizar exclusion cliente',
	        icon: 'error'
	    });
	});
	
}


