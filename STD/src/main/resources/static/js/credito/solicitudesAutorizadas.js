var id = "";
var idDiv = "";
var opcion = "";
function ocultarMostrar(divEl) {
	id = $(divEl).attr("id");
	idDiv = id + 1;
	opcion = "#pant-tabs" + " #" + idDiv;
	switch ($(opcion).attr("for")) {
		case "1":
			console.log("Credenciales calixta");
			break;
		case "2":
			listaPlantillas();
			break;
		case "3":
			listaGestiones();
			break;
		default:
			break;
	}

	mostrarOcultarDiv(idDiv);
}

function mostrarOcultarDiv(divA) {
	var opcion = document.getElementById(divA);
	if (opcion != null) {
		opcion.style.display = "block";
	}

	document.querySelectorAll('#pant-tabs div[style*="display: block;"]').forEach(function(checkElement) {
		console.log("mostrar Datos");
		if (divA != (checkElement.id)) {
			var opcion2 = document.getElementById(checkElement.id);
			if (opcion != null) {
				opcion2.style.display = "none";
			}
		} else {
			console.log(": Mostrar");
		}

	});

}

document.getElementById("credencialesForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Evita que el formulario recargue la página automáticamente

    const form = event.target; // Obtén el formulario que disparó el evento
    const editableInputs = form.querySelectorAll("input:not([readonly])"); // Inputs editables dentro del formulario

    const formData = {};
    editableInputs.forEach(input => {
        formData[input.name] = input.value; // Almacena el valor usando el atributo "name" como clave
    });

    console.log(formData); // Solo para depuración, verifica que tengas los datos correctos

    fetch(form.action, { // Usa el atributo action del formulario
        method: form.method, // Usa el atributo method del formulario
        headers: {
            "Content-Type": "application/json" // Especifica que enviarás datos en formato JSON
        },
        body: JSON.stringify(formData) // Convierte el objeto `formData` a JSON
    })
    .then(response => response.json()) // Procesa la respuesta como JSON
    .then(data => {
        console.log("Respuesta del servidor:", data);
        swal(
			"¡Guardado exitoso!",
			"Los datos se han guardado correctamente.",
			"success"
		)

		setTimeout(function() {
			swal.close(); // Cierra el SweetAlert
		}, 2000);
		location.reload();
    })
    .catch(error => {
        console.error("Hubo un problema con el fetch:", error);
        swal(
			"¡Error!",
			"Ocurrió un error al guardar las credenciales. Intenta nuevamente.",
			"error"
		)

		setTimeout(function() {
			swal.close(); // Cierra el SweetAlert
		}, 2000);
    });
});

function getAbsolutePath() {

	return window.location.href.substring(0,window.location.href.indexOf("STD/")+4);

}

function editPlantilla(idSMSConfig){
	
	var url = getAbsolutePath() + "credito/obtener-plantilla?idSMSConfig=" + idSMSConfig;
	
	fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		}
	})
	.then((response) => {
		if (!response.ok) {
			throw new Error("Error en la respuesta del servidor");
		}
		 return response.json();
	})
	.then((data) => {
		console.log("Configuracion SMS: ", data);
		const texto = data?.mensaje || "No hay texto disponible.";
		const id = data.idConfig;
		const estatus = data.activo;
		document.getElementById("plantilla-texto-enviar").value = texto;
		document.getElementById("id-plantilla-sms").value = id;
		
		if (estatus == 1) {
			document.getElementById("select-estatus-plantilla").value = 1;
		} else {
			document.getElementById("select-estatus-plantilla").value = 0;
		}
	})
	.catch((error) => {
		console.error("Error al obtener las configuraciones SMS:", error);
	})
}

document.getElementById("select-comodines-plantilla").addEventListener("change", function () {
	const selectedValue = this.value; // Obtener el valor seleccionado
    const textarea = document.getElementById('plantilla-texto-enviar');
    
    console.log(selectedValue);
   
    if (selectedValue && textarea.value.indexOf(selectedValue) === -1) {
        // Agregar el valor al textarea en la posición actual del cursor
        const cursorPos = textarea.selectionStart;
        const textBefore = textarea.value.substring(0, cursorPos);
        const textAfter = textarea.value.substring(cursorPos);
        textarea.value = `${textBefore}${selectedValue}${textAfter}`;
        
        // Restaurar el cursor después de la inserción
        textarea.selectionStart = textarea.selectionEnd = cursorPos + selectedValue.length;
    } else {
		swal("Error", "El " + selectedValue + " ya fue insertado en la plantilla.", "error");
	}
});

function actualizarPlantilla(idSMSConfig, texto, estatus) {
	
	var url = getAbsolutePath() + "credito/guardar-plantilla";
	
    const payload = {
        idSMSConfig: Number(idSMSConfig),
        texto: texto,
        estatus: Number(estatus)
    };

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Error al actualizar la plantilla");
        }
        return response.json();
    })
    .then((data) => {
        console.log("Plantilla actualizada: ", data);
        swal("¡Guardado exitoso!", "La plantilla se guardó correctamente.", "success");
       	setTimeout(function() {
			swal.close(); // Cierra el SweetAlert
		}, 2000);
		location.reload();
    })
    .catch((error) => {
        console.error("Error al actualizar la plantilla:", error);
        swal("Error", "Ocurrió un problema al guardar la plantilla. Inténtalo de nuevo.", "error");
    });
}

document.getElementById("plantillasForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Evita la recarga de la página

    const idSMSConfig = document.getElementById("id-plantilla-sms").value;
    const texto = document.getElementById("plantilla-texto-enviar").value;
    const estatus = document.getElementById("select-estatus-plantilla").value;
    
    console.log("Tipo variable estatus: " + typeof(estatus) + "\nTipo variable idSMSConfig: " + typeof(idSMSConfig) + "\nTipo variable texto: " + typeof(texto));

    // Validaciones
    if (!texto.trim()) {
        swal("Error", "El texto del mensaje no puede estar vacío.", "error");
        return;
    }

    if (estatus === "") {
        swal("Error", "Por favor seleccionar un estatus válido.", "error");
        return;
    }

    // Llama a la función para actualizar la plantilla
    actualizarPlantilla(idSMSConfig, texto, estatus);
});

function editProcess(idSMSProcess){
	
	var url = getAbsolutePath() + "credito/obtener-gestion?idSMSProcess=" + idSMSProcess;
	
	fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		}
	})
	.then((response) => {
		if (!response.ok) {
			throw new Error("Error en la respuesta del servidor");
		}
		 return response.json();
	})
	.then((data) => {
		console.log("Gestión SMS: ", data);
		const idProceso = data.idProceso;
		const idConfigMensual = data.idConfigMensual
		const mes = data.nomMes
		const mensajeSMS = data.mensaje;
		const numSMS = data.numSMS;
		const limiteSMS = data.limiteSMS;
		
		document.getElementById("id-gestion-sms").value = idProceso;
		document.getElementById("id-config-mensual").value = idConfigMensual;
		document.getElementById("mes-gestion").value = mes;
		document.getElementById("sms-gestion").value = mensajeSMS;
		document.getElementById("maximo-sms").value = numSMS;
		
		if (limiteSMS == 1) {
			document.getElementById("select-limite-sms").value = 1;
		} else {
			document.getElementById("select-limite-sms").value = 0;
		}
	})
	.catch((error) => {
		console.error("Error al obtener las configuraciones SMS:", error);
	})
}

function modalProcess(idProcess ,estatus) {
	
	const tittleModal = document.getElementById("process-modal-tittle");
	const textModal = document.getElementById("process-modal-text");
	
	if (estatus == 4) {
		tittleModal.innerText = "Pausar proceso de envío SMS";
		textModal.innerText = "¿Estás seguro de pausar el envío de SMS? \n Una vez realizando está acción se dentendrá el envío de SMS.";
		console.log(tittleModal.innerText + "\n" + textModal.value);
		getModalForm(idProcess, estatus);
	}
	
	if (estatus == 6) {
		tittleModal.innerText = "Reanudar proceso de envío de SMS";
		textModal.innerText = "¿Estás seguro de reanudar el envío de SMS? \n Una vez realizando está acción se dentonará el envío de SMS.";
		getModalForm(idProcess, estatus);
	} 
}

function actualizarProceso(idSMSProceso, estatus) {
	
	var url = getAbsolutePath() + "credito/actualizar-gestion-sms";
	
    const payload = {
        idSMSProceso: Number(idSMSProceso),
        estatus: Number(estatus)
    };

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Error al actualizar el estatus del proceso.");
        }
        return response.json();
    })
    .then((data) => {
        console.log("Proceso actualizado: ", data);
		location.reload();
    })
    .catch((error) => {
        console.error("Error al actualizar el proceso SMS:", error);
        swal("Error", "Ocurrió un problema al actualizar el proceso de envío. Inténtalo de nuevo.", "error");
    });
}

function getModalForm(idSMSProceso, estatus) {
	document.getElementById("changeStatusProcessForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Evita la recarga de la página

    // Llama a la función para actualizar la plantilla
    actualizarProceso(idSMSProceso, estatus);
});	
}