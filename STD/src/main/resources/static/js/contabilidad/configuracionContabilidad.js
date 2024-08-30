// @ts-nocheck
/**
*
*/
 
function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
 
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
            adjuntarFiles();
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
 
document.getElementById("uploadForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevenir el envío predeterminado del formulario
 
    const formData = new FormData();
    const fileInput = document.getElementById("fileUpload");
    const idFolio = "12345"; // Aquí debes poner el valor real de idFolio
 
    // Verifica si se ha seleccionado un archivo
    if (fileInput.files.length > 0) {
        swal({
            title: "Procesando información.",
            content: {
                element: "div",
                attributes: {
                    innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
                    style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
                },
            },
            timer: 4000, // tiempo en milisegundos
            buttons: false,
            closeOnClickOutside: false,
            closeOnEsc: false,
        }).then((result) => {
            // Este código se ejecutará después de que el temporizador haya terminado
 
            for (let i = 0; i < fileInput.files.length; i++) {
                formData.append("file", fileInput.files[i]);
            }
 
            formData.append("idFolio", idFolio);
 
            fetch(getAbsolutePath() + "subirArchivo", {
                method: "POST",
                body: formData,
            })
                .then(response => response.json())
                .then(data => {
                    if (data.length > 0) {
                        swal(data[0], {
                            closeOnClickOutside: false,
                            icon: "success",
                        }).then(resp => {
                            adjuntarFiles();
                            console.log("SII")
                        });
                    } else {
                        swal("No se recibió respuesta del servidor.", {
                            closeOnClickOutside: false,
                            icon: "warning",
                        });
                    }
                })
                .catch(error => {
                    console.error("Error al subir el archivo:", error);
 
                    swal("Ocurrió un error al subir el archivo.", {
                        closeOnClickOutside: false,
                        icon: "warning",
                    });
                });
 
        });
 
    } else {
        swal("Es necesario seleccionar un Archivo.", {
            closeOnClickOutside: false,
            icon: "error",
        });
    }
 
});
 
var listarArchivos = "listaArchivos";
function adjuntarFiles() {
    url = "";
    url = getAbsolutePath() + listarArchivos;
    obtListadoArchivos(url);
    document.getElementById("fileUpload").value = "";
}
 
function obtListadoArchivos(url) {
    // Mostrar SweetAlert con spinner
    swal({
        title: "Consultando información",
        content: {
            element: "div",
            attributes: {
                innerHTML: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
                style: "display: flex; justify-content: center; align-items: center; padding: 20px;",
            },
        },
        buttons: false,
        closeOnClickOutside: false,
        closeOnEsc: false,
    });
 
    $.getJSON(url, {}, function(data) {
        // Ocultar SweetAlert después de cargar los datos
        swal.close();
 
        const fileList = document.getElementById("fileList");
        fileList.innerHTML = ""; // Limpiar la lista antes de agregar los elementos
 
        if (data.length > 0) {
            data.forEach(function(archivo) {
                const li = document.createElement("li");
                li.className = "list-group-item d-flex justify-content-between align-items-center";
 
                const fileInfo = document.createElement("div");
                fileInfo.innerHTML = `
                    <strong>${archivo.nombreArchivo}</strong><br>
                    <small><i class="fas fa-calendar-alt"></i> Creado: ${archivo.fechaCreacion}</small><br>
                    <small><i class="fas fa-edit"></i> Modificado: ${archivo.fechaModifacion}</small>
                `;
 
                const processButton = document.createElement("button");
                processButton.className = "btn btn-success btn-sm";
                processButton.innerHTML = `<i class="fas fa-cogs"></i> Procesar`;
                processButton.onclick = function() {
                    // Aquí puedes agregar la lógica para procesar el archivo
                    alert(`Procesando: ${archivo.nombreArchivo}`);
                };
 
                li.appendChild(fileInfo);
                li.appendChild(processButton);
                fileList.appendChild(li);
            });
 
        } else {
            const li = document.createElement("li");
            li.className = "list-group-item";
            li.textContent = "No hay archivos subidos.";
            fileList.appendChild(li);
        }
    }).fail(function() {
        swal("Error", "No se pudo obtener la lista de archivos.", "error");
    });
}