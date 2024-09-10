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

                // Botón "Procesar"
                const processButton = document.createElement("button");
                processButton.className = "btn btn-success btn-sm";
                processButton.innerHTML = `<i class="fas fa-cogs"></i> Procesar`;
                processButton.onclick = function() {
                    const fileName = archivo.nombreArchivo;

                    // Mostrar una alerta de procesamiento
                    swal({
                        title: `Procesando ${fileName}`,
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

                    fetch(getAbsolutePath() + 'procesarArchivoFactura', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ nombreArchivo: fileName })
                    })
                        .then(response => response.json())
                        .then(data => {
                            swal.close();
                            if (data.success) {
                                swal("Éxito", "El archivo se procesó correctamente.", "success");
                            } else {
                                swal("Error", "Hubo un problema al procesar el archivo.", "error");
                            }
                        })
                        .catch(error => {
                            swal.close();
                            swal("Error", "No se pudo procesar el archivo.", "error");
                            console.error('Error al procesar el archivo:', error);
                        });
                };

                // Botón "Cargar PDF"
                const uploadButton = document.createElement("button");
                uploadButton.className = "btn btn-primary btn-sm";
                uploadButton.innerHTML = `<i class="fas fa-upload"></i> Cargar PDF`;
                uploadButton.onclick = function() {
				    // Crear un input de tipo archivo
				    const input = document.createElement("input");
				    input.type = "file";
				    input.accept = ".pdf";
				    
				    input.onchange = function(event) {
				        const file = event.target.files[0];
				        const formData = new FormData();
				        formData.append("pdf", file);
				        formData.append("nombreArchivo", archivo.nombreArchivo);
				
				        // Enviar el archivo al servidor
				        fetch(getAbsolutePath() + 'cargarPdf', {
				            method: 'POST',
				            body: formData // Asegúrate de usar FormData para enviar archivos
				        })
				        .then(response => response.text()) // Cambia .json() a .text() para respuestas de texto plano
				        .then(data => {
				            swal.close();
				            swal("Éxito", data, "success"); // Mostrar el mensaje tal como se recibe
				        })
				        .catch(error => {
				            swal.close();
				            swal("Error", "No se pudo procesar el archivo.", "error");
				            console.error('Error al procesar el archivo:', error);
				        });
				    };
				    
				    input.click();
				};

                // Botón "Descargar/Mostrar PDF"
                const viewButton = document.createElement("button");
                viewButton.className = "btn btn-danger btn-sm";
                viewButton.innerHTML = `<i class="fas fa-file-pdf"></i> Abrir PDF`;
                viewButton.onclick = function() {
					// Modificar la extensión del archivo de .xml a .pdf si es necesario
				    let archivoNombre = archivo.nombreArchivo;
				    if (archivoNombre.toLowerCase().endsWith('.xml')) {
				        archivoNombre = archivoNombre.slice(0, -4) + '.pdf';
				    }
					
                    // Abrir el archivo PDF en una nueva ventana
                    window.open(getAbsolutePath() + 'verPdf?nombreArchivo=' + encodeURIComponent(archivoNombre), '_blank');
                };

                li.appendChild(fileInfo);
                li.appendChild(processButton);
                li.appendChild(uploadButton);
                li.appendChild(viewButton);
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
