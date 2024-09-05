// @ts-nocheck
/**
*
*/
$(document).ready(function() {
    
    let valor = $('#lista_BC').val();
    let listaObtenida = JSON.parse(valor); 
              
 	pintarTabla(listaObtenida,tblConfiguracion);
});


function submitForm(idEvento) {
    
    //obtener id boton
    const btnProcesar = $(idEvento).attr('id');
    
    const $button = $(idEvento);
    
    // Selecciona el formulario más cercano al botón clickeado
    const $form = $(idEvento).closest('form');
        
    // Obtén el ID del formulario
    const formId = $form.attr('id');
  
    // Obtén el formulario
    const form = document.getElementById(formId);
    
    // Crea un objeto FormData con el formulario
    const formData = new FormData(form);
    
    // Variable para llevar el seguimiento de si hay campos vacíos
	let enviardatos = validarDatos(formData,1);
    
	if (!enviardatos) {
        
        // Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
		Array.from(form.elements).forEach(element => element.disabled = true);
		$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
        
		// fetch para enviar una solicitud POST con los datos del formulario
		fetch(form.action, {
			method: 'POST',
			body: formData
		})
			.then(response => {
				if (!response.ok) {
					throw new Error('Error, comuniquese con el administrador o intente mas tarde.');
				}
				
				return response.json();
			})
			.then(data => {
                if(data.listaCatalogo.length > 0){
                    pintarTabla(data.listaCatalogo,tblConfiguracion);
                }
                mostrarAlertar(data.message,"success",1);
                
				console.log('Success:', data);
				// Maneja la respuesta aquí (por ejemplo, muestra un mensaje al usuario)
			})
			.catch(error => {
				console.log('Error:', error);
				 mostrarAlertar(data.message,"danger",1);
				// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
			})
			.finally(() => {
				// Ocultar el overlay con el spinner y habilitar los elementos del formulario
				$button.html('Registrar');
				Array.from(form.elements).forEach(element => element.disabled = false);
				
				Array.from(form.elements).forEach(element => {
                     element.value = 0 ; 
    			});
				
				
			});
	}
   
}

function costos(idEvento) {
    
    const $button = $(idEvento);
    
    // Selecciona el formulario más cercano al botón clickeado
    const $form = $(idEvento).closest('form');
        
    // Obtén el ID del formulario
    const formId = $form.attr('id');
  
    // Obtén el formulario
    const form = document.getElementById(formId);
    
    // Crea un objeto FormData con el formulario
    const formData = new FormData(form);
    
    // Variable para llevar el seguimiento de si hay campos vacíos
	let enviardatos = validarDatos(formData,2);
    
	if (!enviardatos) {
        $("button").prop("disabled", true);
        // Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
		$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
        
		// fetch para enviar una solicitud POST con los datos del formulario
		fetch(form.action, {
			method: 'POST',
			body: formData
		})
			.then(response => {
				if (!response.ok) {
					throw new Error('Error, comuniquese con el administrador o intente mas tarde.');
				}
				
				return response.json();
			})
			.then(data => {
                document.getElementById('costoBC').value = data.costoBC;
                document.getElementById('costoAdvicer').value = data.costoAdviser;
                crearOpcionesSelect(data.catcostounitario);
                mostrarAlertar(data.message,"success",2);
                
				console.log('Success:', data);
				// Maneja la respuesta aquí (por ejemplo, muestra un mensaje al usuario)
			})
			.catch(error => {
				console.log('Error:', error);
				 mostrarAlertar(data.message,"danger",2);
				// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
			})
			.finally(() => {
				// Ocultar el overlay con el spinner y habilitar los elementos del formulario
				$button.html('Guardar');			
				$("button").prop("disabled", false);
			});
	}
   
}

// Función para crear y agregar las opciones al select
function crearOpcionesSelect(data) {
            const select = document.getElementById('slcCostoUnitario');
            select.innerHTML = '';
            
             // Crear y agregar la opción por defecto "Seleccione"
    		const defaultOption = document.createElement('option');
   	 		defaultOption.value = '';  // Valor vacío para la opción "Seleccione"
    		defaultOption.text = 'Seleccione'; // Texto visible en el select
    		defaultOption.disabled = true; // Deshabilitar la selección de esta opción
    		defaultOption.selected = true; // Seleccionada por defecto
    		select.appendChild(defaultOption);

            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.i_opcion;  // Valor que se envía cuando se selecciona
                option.text = item.v_valor; // Texto visible en el select
                select.appendChild(option);
            });
}

function costoUnitarioS(idConcepto){
    //obtener el id seleccionado en este select
    const $slcConcepto = $(idConcepto);
    const slcCostoU = document.getElementById('slcCostoUnitario');
    slcCostoU.selectedIndex = $slcConcepto.val();
    
}



function pintarTabla(listaDatos,tablaPintar){
    const registrosTotales = [];
    
    	
    tablaPintar.clear().draw();
	$.each(listaDatos, function(idx, opt) {
        
        var btnsActualizar = " <button class='btn btn-primary btn-sm' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='editarParametros("
					+ opt.id
					+ ")' id="
					+ opt.id
					+ " data-tooltip='Add'>"
					+ " <i class='bi bi-tools'></i></button>";
        
        registrosTotales.push([
            opt.descripcionConcepto,
            opt.descripcionProducto,
            opt.montoInicial,
            opt.montoFinal,
            (opt.estatus == 1?"Activo":"Inactivo"),
            btnsActualizar
        ]);
    });
     tablaPintar.rows.add(registrosTotales).draw();
    
}

function validarDatos(formData,num){
    let esVacio = false;
    let txtError = "";
	let tipoError = "";
	
    // Recorrer todos los campos del formulario
	for (let [key, value] of formData.entries()) {	
   		 if (!mayorCero(value)) {
            esVacio = true;
            txtError += `El campo ${key} no puede estar vacío<br>`;
    	}
        
	}
	tipoError = "warning";
	if(esVacio){
        mostrarAlertar(txtError,tipoError,num);
    }else{
        limpiarAlerta(num);
    }
	
	return esVacio;
}

// Función para validar si un campo está vacío
function mayorCero(value) {
    return value.trim() > 0;
}

function limpiarAlerta(num){
    
    switch(num){
        case 1:
   		 	// Obtener el contenedor de alertas
			const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
			// Limpiar el contenedor de alertas
    		alertPlaceholder.innerHTML = '';
            break;
        case 2:  
    		// Obtener el contenedor de alertas
			const mnsCosto = document.getElementById('mensajeCostoAmortizado');
			// Limpiar el contenedor de alertas
   			mnsCosto.innerHTML = '';
            break;
    }

}

function cerrarModal(button) {
    
    let modal = button.closest('.modal');
    let modalId = modal.getAttribute('id');
    $('#' + modalId).modal('hide');
    
}

function toggleProcessingButton(isProcessing) {
    const button = document.getElementById('processingButton');

    if (isProcessing) {
        button.disabled = true;
        button.innerHTML = `
            <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
            <span class="sr-only">Loading...</span>
        `;
    } else {
        button.disabled = false;
        button.innerHTML = `
            Enviar
        `;
    }
}

function mostrarAlertar(msnError,tipoError,num){
    limpiarAlerta(num);
    let contenedorMensaje = '';
    switch(num){
        case 1:
            contenedorMensaje = document.getElementById('liveAlertPlaceholder');
            break;
        case 2:
            contenedorMensaje = document.getElementById('mensajeCostoAmortizado');
            break;
    }
    
	// Obtener el contenedor de alertas
	
	//const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
	

	// Función para añadir una alerta al contenedor
	const appendAlert = (message, type) => {
    const wrapper = document.createElement('div');
    wrapper.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="cerrarAlerta"></button>',
        '</div>'
    ].join('');
    contenedorMensaje.append(wrapper);
	};
	
	appendAlert(msnError, tipoError);

}

function limpiarFormulario(){
    // Obtén el formulario
    const form = document.getElementById('configuracionForm');
    Array.from(form.elements).forEach(element => {
                     element.value = 0 ; 
    });
    
}
