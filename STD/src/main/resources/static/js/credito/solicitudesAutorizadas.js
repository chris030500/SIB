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
			//listaGestiones();
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