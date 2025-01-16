// @ts-nocheck
/**
*
*/

var id = "";
var idDiv = "";
var opcion = "";
function ocultarMostrar(divEl) {
	id = $(divEl).attr("id");
	idDiv = id + 1;
	opcion = "#pant-tabs" + " #" + idDiv;
	switch ($(opcion).attr("for")) {
		case "1":
			console.log("Parametros");
			break;
		case "2":
			//obtTblReglas();
			break;
		case "3":
			//obtTblPlantillas();
			break;
		case "5":
			//obtTblClienteB();
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

function getPlantillaByTipoCredito(tipoCredito, isCampania){
	
	fetch(`/STD/marketing/obtener-plantilla?tipoCredito=${tipoCredito}&isCampania=${isCampania}`, {
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
		document.getElementById("plantilla-texto-enviar").value = texto;
	})
	.catch((error) => {
		console.error("Error al obtener las configuraciones SMS:", error);
	})
}

function actualizarPlantilla(tipoCredito, isCampania, texto) {
    const payload = {
        tipoCredito: tipoCredito,
        isCampania: isCampania,
        texto: texto,
    };

    fetch("/STD/marketing/guardar-plantilla", {
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

document.getElementById("select-tipo-credito").addEventListener("change", function () {
    const selectedValue = this.value; // Obtiene el valor de la opción seleccionada
    console.log("Opción seleccionada:", selectedValue);
    
    switch(selectedValue) {
		case "N":
			getPlantillaByTipoCredito(selectedValue, 0);
			break;
		case "O":
			getPlantillaByTipoCredito(selectedValue, 0);
			break;
		case "":
			getPlantillaByTipoCredito(selectedValue, 1);
			break;
		case "0":
			document.getElementById("plantilla-texto-enviar").value = '';
	}

});

document.getElementById("plantillasForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Evita la recarga de la página

    const tipoCredito = document.getElementById("select-tipo-credito").value;
    const texto = document.getElementById("plantilla-texto-enviar").value;

    // Determina el valor de isCampania
    const isCampania = tipoCredito === "" ? 1 : 0;

    // Validaciones
    if (!texto.trim()) {
        swal("Error", "El texto del mensaje no puede estar vacío.", "error");
        return;
    }

    if (tipoCredito === "0") {
        swal("Error", "Por favor selecciona un tipo de crédito válido.", "error");
        return;
    }

    // Llama a la función para actualizar la plantilla
    actualizarPlantilla(tipoCredito, isCampania, texto);
});



