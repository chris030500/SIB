// @ts-nocheck
/**
*
*/
$(document).ready(function() {
    
    let valor = $('#lista_BC').val();
    let listaObtenida = JSON.parse(valor); 
              
 	pintarTablaGastos(listaObtenida,tblConfiguracion);
});


function submitFormGastos(idEvento,num) {
    
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
    let enviardatos = false;
    if (num == 1){
        enviardatos = validarDatosGastos(formData,num);
    }
	
    
	if (!enviardatos) {
        
        // Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
		Array.from(form.elements).forEach(element => element.disabled = true);
		$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
         bloquearPantalla(1);
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
                switch(num){
                    case 1:
                        if(data.listaCatalogo.length > 0){
                    		pintarTablaGastos(data.listaCatalogo,tblConfiguracion);
                		}
                        break;
                    default:
                        break; 
                }
                mostrarAlertarGastos(data.message,"success",num);
                
			})
			.catch(error => {
				 mostrarAlertarGastos("Error al mostrar información en pantalla, favor de comunicarse con el administrador del sistema.","danger",num);
				
			})
			.finally(() => {
				// habilitar los elementos del formulario
				Array.from(form.elements).forEach(element => element.disabled = false);
				 bloquearPantalla(2);
				switch(num){
                    case 1:
                        	$button.html('Registrar');
				 			const slcCostoUn = document.getElementById('slcCostoUnitario');
             	 			slcCostoUn.disabled = true;
                        break;
                 	case 3:
                         	$button.html('<i class="bi bi-diagram-3-fill"></i>');
                         break;	
           
                }
				
			
				
				/*Array.from(form.elements).forEach(element => {
                     element.value = 0 ; 
    			});*/
				
				
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
	let enviardatos = validarDatosGastos(formData,2);
    
	if (!enviardatos) {
        $("button").prop("disabled", true);
     
        // Mostrar el overlay con el spinner y deshabilitar los elementos del formulario
		$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
         bloquearPantalla(1);
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
                mostrarAlertarGastos(data.message,"success",2);
                
				console.log('Success:', data);
				// Maneja la respuesta aquí (por ejemplo, muestra un mensaje al usuario)
			})
			.catch(error => {
				 mostrarAlertarGastos(data.message,"danger",2);
				// Maneja el error aquí (por ejemplo, muestra un mensaje de error al usuario)
			})
			.finally(() => {
				// Ocultar el overlay con el spinner y habilitar los elementos del formulario
				$button.html('Guardar');			
				$("button").prop("disabled", false);
				 bloquearPantalla(2);
		
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



function pintarTablaGastos(listaDatos,tablaPintar){
    const registrosTotales = [];
    
    	
    tablaPintar.clear().draw();
	$.each(listaDatos, function(idx, opt) {
        
        var btnsActualizar = " <button class='btn btn-primary btn-sm' tabindex='0' data-bs-toggle='modal'"
					+ " title='Actualizar' data-bs-target='#' aria-controls='table-datatable' type='button'"
					+ " onclick='editarParametros(this)' id="
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

function validarDatosGastos(formData,num){
    let esVacio = false;
    let txtError = "";
	let tipoError = "";
	
    // Recorrer todos los campos del formulario
	for (let [key, value] of formData.entries()) {	
   		 if (!mayorCero(value) && key != 'gastoIde') {
            esVacio = true;
            txtError += `El campo ${key} no puede estar vacío<br>`;
    	}
        
	}
	tipoError = "warning";
	if(esVacio){
        mostrarAlertarGastos(txtError,tipoError,num);
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

function mostrarAlertarGastos(msnError,tipoError,num){
    limpiarAlerta(num);
    let contenedorMensajeGasto = '';
    switch(num){
        case 1:
            contenedorMensajeGasto = document.getElementById('alertaGastoCatalogo');
            break;
        case 2:
            contenedorMensajeGasto = document.getElementById('mensajeCostoAmortizado');
            break;
        case 3:
            contenedorMensajeGasto = document.getElementById('mensajeProrrateo');
            break;
    }
    
	// Obtener el contenedor de alertas
	//const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
	$(contenedorMensajeGasto).find('div').remove();

	// Función para añadir una alerta al contenedor
	const appendAlerGastos = (message, type) => {
    const wrapperGasto = document.createElement('div');
    wrapperGasto.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" id="cerrarAlerta"></button>',
        '</div>'
    ].join('');
    contenedorMensajeGasto.append(wrapperGasto);
	};
	
	appendAlerGastos(msnError, tipoError);

}

function limpiarFormulario(){
    // Obtén el formulario
    const form = document.getElementById('configuracionForm');
    Array.from(form.elements).forEach(element => {
                     element.value = 0 ; 
    });
    
}

function editarParametros(idBotton){
    const $button = $(idBotton);

	// Selecciona el formulario más cercano al botón clickeado
	const $form = $(idBotton).closest('form');

	// Obtén el ID del formulario
	const formId = $form.attr('id');

	// Obtén el formulario
	const form = document.getElementById(formId);

	// Captura el valor del ID del botón
	var identificador = $button.attr('id');

	// Crea el objeto de datos para enviar
	var data = {
		identificador: identificador
	};
	
	$button.html('<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando..');
	 bloquearPantalla(1);
	// Usa fetch para enviar una solicitud POST con los datos del formulario
	fetch(form.action, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data) // Stringifica el objeto de datos
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Error de comunicación. Intente mas tarde');
			}

			return response.json();
		})
		.then(data => {
            const conceptoslc = document.getElementById('slcConcepto');
            const productoslc = document.getElementById('slcProducto');
            const imontoInicial = document.getElementById('montoInicial');
            const imontoFinal = document.getElementById('montoFinal');
            const slcCostoU = document.getElementById('slcCostoUnitario');
            const gasto = document.getElementById('gastoIde');
            
            conceptoslc.selectedIndex = data.gastoDatos.concepto;
            productoslc.value = data.gastoDatos.productos;
            imontoInicial.value = data.gastoDatos.montoInicial;
            imontoFinal.value = data.gastoDatos.montoFinal;
            slcCostoU.selectedIndex = data.gastoDatos.concepto;
            slcCostoU.disabled = true;
            gasto.value = data.gastoDatos.id;
            
            
           

		})
		.catch(error => {
			console.log('Error:', error);
			mostrarAlertarGastos(data.message, "danger");
		})
		.finally(() => {
			// Ocultar el overlay con el spinner y habilitar los elementos del formulario
			$button.html('<i class="bi bi-tools"></i>');
			$('#registroCatalogo').modal('show');
			bloquearPantalla(2);
		});
}


function bloquearPantalla(num) {
	
	switch(num){
		case 1:
			 swal({
      title: "",
      content: {
        element: "div",
        attributes: {
          innerHTML: '<i class="fas fa-cog fa-spin" id="spinnerIcon"></i> Procesando Información.. ',
          style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
        },
      },
      buttons: false,
      closeOnClickOutside: false,
      closeOnEsc: false,
    });
			break;
		case 2:
			swal.close();
			break;
		default:
			swal.close();
			break;
	}

}




