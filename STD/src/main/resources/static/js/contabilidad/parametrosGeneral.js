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
    });
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