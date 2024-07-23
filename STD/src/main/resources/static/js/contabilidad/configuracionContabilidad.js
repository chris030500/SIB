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
			console.log("layout");
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