document.addEventListener("DOMContentLoaded", function () {
    listaMunicipiosExclusion();
});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");

function ocultarMostrar(btn) {
	console.log(newUrlBase);
	const tabs = document.querySelectorAll('#pant-tabs > div[id$="-tab1"]');
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

$(function () {
    const clientes = ["Juan Pérez", "María López", "Carlos Díaz", "Lucía Herrera", "Ana Torres"];

    $("#cliente").autocomplete({
        source: clientes,
        minLength: 2
    });
});



