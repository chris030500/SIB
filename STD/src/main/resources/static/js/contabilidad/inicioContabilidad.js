// @ts-nocheck
/**
*
*/

var id = "";
var idDiv = "";
var opcion = "";

var tblConfiguracion;
var tblProcesoGeneradorCNF;
var tblProcesoGeneradorEPRC;
var tblConfConceptos;

$(document).ready(function() {

	adjuntarFiles();
	
	tblConfiguracion = $("#tblConfiguracion").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});
	
	tblProcesoGeneradorCNF = $("#tblProcesoGeneradorCNF").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}],
		"order": [[1, 'desc']],
		"columnDefs": [{
       		"targets": 1, // Índice de la columna de fecha
        	"type": "string" // Ordenación como string
    	}]
	});
	
	tblProcesoGeneradorEPRC = $("#tblProcesoGeneradorEPRC").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}],
		"order": [[1, 'desc']],
		"columnDefs": [{
       		"targets": 1, // Índice de la columna de fecha
        	"type": "string" // Ordenación como string
    	}]
	});
	
	tblConfConceptos = $("#tblConfConceptos").DataTable({
		"language": {
			"url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
		},
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1] /* 1st one, start by the right */
		}]
	});

});

function ocultarMostrar(divA) {
	const navOpcion =$(divA).attr('id')+1;
	
	$('.opciones').hide();
	$("#"+navOpcion).show();
	
}

document.getElementById("uploadFormFactura").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevenir el envío predeterminado del formulario
 
    const formData = new FormData();
    const fileInput = document.getElementById("fileUploadFactura");
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
 
            fetch(getAbsolutePath() + "subirArchivoFactura", {
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

document.getElementById("uploadFormComisiones").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevenir el envío predeterminado del formulario
 
    const formData = new FormData();
    const fileInput = document.getElementById("fileUploadComisiones");
    const idFolio = "12345"; // Aquí debes poner el valor real de idFolio
 
    // Verifica si se ha seleccionado un archivo
    if (fileInput.files.length > 0) {
		const fileName = fileInput.files[0].name; // Obtener el nombre del archivo
		const fileExtension = fileName.split('.').pop().toLowerCase(); // Obtener la extensión del archivo

		// Nombres de archivo permitidos
		const validFileNames = ["layout_comisiones.xls", "layout_cambio_etapa.xls", "layout_comisiones.xlsx", "layout_cambio_etapa.xlsx"];
		// Extensiones permitidas
		const validExtensions = ["xlsx", "xls"];

		// Verificar si el archivo tiene una extensión válida
		if (!validExtensions.includes(fileExtension) || !validFileNames.includes(fileName)) {
			swal("El archivo debe ser un documento Excel con extensión .xlsx o .xls y tener uno de los nombres permitidos: 'layout_comisiones', 'layout_cambio_etapa'.", {
				closeOnClickOutside: false,
				icon: "error",
			});
			return; // Salir de la función si el archivo no es válido
		}
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
 
            fetch(getAbsolutePath() + "subirArchivoComisiones", {
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
                            adjuntarFilesComisiones();
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

var listarArchivos = "listaArchivosFactura";
function adjuntarFiles() {
    url = "";
    url = getAbsolutePath() + listarArchivos;
    obtListadoArchivos(url);
    document.getElementById("fileUpload").value = "";
}

var listarArchivosComisiones = "listaArchivosComisiones";
function adjuntarFilesComisiones() {
    url = "";
    url = getAbsolutePath() + listarArchivosComisiones;
    obtListadoArchivosComisiones(url);
    document.getElementById("fileUploadComisiones").value = "";
}
 
function obtListadoArchivos(url) {
    // Mostrar SweetAlert con spinner
    bloquearPantalla(1); 

    $.getJSON(url, {}, function(data) {
        // Ocultar SweetAlert después de cargar los datos
        swal.close();

        const fileList = document.getElementById("fileListFactura");
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

function obtListadoArchivosComisiones(url) {
	
	console.log(url);
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

		const fileList = document.getElementById("fileListComisiones");
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
					// Aquí se llama al backend para procesar el archivo
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

					fetch(getAbsolutePath() + 'procesarArchivoComisiones', {
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
