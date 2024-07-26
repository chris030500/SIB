// @ts-nocheck
/**
*
*/


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
	let enviardatos = validarDatos(formData);
    
	if (!enviardatos) {
        
        // Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
		Array.from(form.elements).forEach(element => element.disabled = true);
		$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
        
		// Usa fetch para enviar una solicitud POST con los datos del formulario
		fetch(form.action, {
			method: 'POST',
			body: formData
		})
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok');
				}
				return response.json(); // Asumiendo que el servidor responde con JSON
			})
			.then(data => {
				console.log('Success:', data);
				// Maneja la respuesta aquí (por ejemplo, muestra un mensaje al usuario)
			})
			.catch(error => {
				console.log('Error:', error);
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

function validarDatos(formData){
    let esVacio = false;
    let txtError = "";
	let tipoError = "";
    // Recorrer todos los campos del formulario
	for (let [key, value] of formData.entries()) {	
   		 if (!mayorCero(value)) {
            esVacio = true;
            txtError += `El campo ${key} no puede estar vacío<br>`;
            
       	 	// Mostrar mensaje de error (puedes personalizar esto según tus necesidades)
        	//alert(`El campo ${key} no puede estar vacío`);
        	// Puedes agregar una clase de error al campo vacío, por ejemplo:
        	//$(`#${key}`).addClass('error');
    	}
        
	}
	tipoError = "warning";
	if(esVacio){
        mostrarAlertar(txtError,tipoError);
    }else{
        limpiarAlerta();
    }
	
	return esVacio;
}

// Función para validar si un campo está vacío
function mayorCero(value) {
    return value.trim() > 0;
}

function limpiarAlerta(){
    // Obtener el contenedor de alertas
	const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
	
	// Limpiar el contenedor de alertas
    alertPlaceholder.innerHTML = '';
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

function mostrarAlertar(msnError,tipoError){
    limpiarAlerta();
    
	// Obtener el contenedor de alertas
	const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
	

	// Función para añadir una alerta al contenedor
	const appendAlert = (message, type) => {
    const wrapper = document.createElement('div');
    wrapper.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="cerrarAlerta"></button>',
        '</div>'
    ].join('');
    alertPlaceholder.append(wrapper);
	};
	
	appendAlert(msnError, tipoError);

}

