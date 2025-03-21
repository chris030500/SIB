// Variables | validacion de la liga absoluta
window.onload = function() {
	tablaEmpresas();
	listaEmpresas();
}

var urlPrincipal = "http://localhost:8091/STD/configuracionEmpresas/";

var buscarEmpresa = document.getElementById('buscarEmpresa');
var correoEmpresa = document.getElementById('correoEmpresa');
var personaContacto = document.getElementById('personaContacto');
var spinner = document.getElementById("spinnerEmpresas")

function tablaEmpresas() {
	$.ajax({
		url: urlPrincipal + "tablaEmpresas",  // Asegúrate de que esta URL sea correcta
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			// Limpiar la tabla antes de agregar los nuevos registros
			tblRegistroEmpresas.clear().draw();

			// Verificar si se recibieron datos de las empresas
			if (respuesta && Array.isArray(respuesta) && respuesta.length > 0) {
				$.each(respuesta, function(idx, empresa) {
					// Crear botones para Editar y Ver Estatus
					var btnEditar = "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal' "
						+ " title='Editar' data-bs-target='#modalEmpresa' aria-controls='table-datatable' type='button' "
						+ " onclick='llenarFormulario(" + JSON.stringify(empresa) + ")' id='btnEditar_" + empresa.id + "' data-tooltip='Edit'>"
						+ " <i class='bi bi-pencil'></i></button>";


					var btnEstatus = "<button style='background: #0dcaf0; border-color: #0dcaf0;' class='btn btn-primary' tabindex='0' data-bs-toggle='modal' "
						+ " title='Ver Estatus' data-bs-target='#' aria-controls='table-datatable' type='button' "
						+ " onclick='confirmarCambioEstatus(" + empresa.id + ", " + empresa.estatus + ")' id='" + empresa.id + "' data-tooltip='Status'>"
						+ " <i class='bi bi-unlock'></i></button>";

					// Validar los campos de las empresas para asegurar que no se corten
					var validarCorreo = empresa.correoContacto && empresa.correoContacto.length > 30 ? empresa.correoContacto.substring(0, 30) + '...' : empresa.correoContacto || 'N/A';
					var validarFrecuencia = empresa.frecuencia === 1 ? "Semanal" : empresa.frecuencia === 2 ? "Quincenal" : "Mensual";
					var validarEstatus = empresa.estatus === 1 ? "Activa" : "Inactiva";

					// Agregar una nueva fila en la tabla
					tblRegistroEmpresas.row.add([
						empresa.nombre || 'N/A',  // Valida si el campo está vacío
						validarCorreo,
						empresa.personaContacto || 'N/A',  // Valida si el campo está vacío
						validarFrecuencia,
						validarEstatus,
						btnEditar + btnEstatus
					]).draw(false);
				});
			} else {
				// Si no hay empresas, mostrar un mensaje
				console.log("No se encontraron empresas.");
			}
		},
		error: function(error) {
			console.error("Error al obtener las empresas:", error);
		}
	});
}

function listaEmpresas() {
	buscarEmpresa.disabled = true;
	spinner.style.display = "block";

	$.ajax({
		url: urlPrincipal + "lista_empresas", // Ajusta la URL si es necesario
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			var opciones = document.getElementById('opcionEmpresa');
			opciones.innerHTML = '';

			respuesta.lista.forEach(function(empresa) {
				var option = document.createElement('option');
				option.setAttribute('id', empresa.id);
				option.setAttribute('correo', empresa.correoContacto);
				option.setAttribute('contacto', empresa.personaContacto);
				option.value = empresa.nombre;
				opciones.appendChild(option);
			});

			buscarEmpresa.disabled = false;
			spinner.style.display = "none";
		},
		error: function(error) {
			console.error("Error en la petición:", error);
			spinner.style.display = "none";
		}
	});
}

buscarEmpresa.addEventListener('input', function() {
	// Obtiene todas las opciones del datalist
	var opciones = document.getElementById('opcionEmpresa').getElementsByTagName('option');

	// Busca la opción que coincide con el valor actual del input
	for (var i = 0; i < opciones.length; i++) {
		if (opciones[i].value === buscarEmpresa.value) {
			// Imprime el atributo data-value de la opción seleccionada
			correoEmpresa.value = opciones[i].getAttribute('correo');
			personaContacto.value = opciones[i].getAttribute('contacto');
			break; // Sale del bucle después de encontrar la opción
		}
	}
});

// Evento para guardar la empresa en la BD
document.querySelector(".modal-footer .btn-primary").addEventListener("click", function() {
	var empresaSeleccionada = Array.from(document.querySelectorAll('#opcionEmpresa option'))
		.find(option => option.value === buscarEmpresa.value);

	if (!empresaSeleccionada) {
		Swal.fire({
			icon: 'error',
			title: '¡Error!',
			text: 'Selecciona una empresa válida.',
		});
		return;
	}

	// Obtener el correo y validarlo
	var correo = correoEmpresa.value;

	// Validar si el correo está vacío
	if (correo === "") {
		Swal.fire({
			icon: 'error',
			title: '¡Correo vacío!',
			text: 'Por favor, ingresa un correo electrónico.',
		});
		return;
	}

	// Si el correo no es válido, mostrar un alert de SweetAlert
	if (!validarCorreo(correo)) {
		Swal.fire({
			icon: 'error',
			title: '¡Correo inválido!',
			text: 'Por favor, ingresa un correo electrónico válido.',
		});
		return;
	}

	var datosEmpresa = {
		id: empresaSeleccionada.getAttribute('id'), // Se obtiene correctamente el ID
		nombre: buscarEmpresa.value,
		correoContacto: correoEmpresa.value,
		personaContacto: personaContacto.value,
		frecuencia: frecuencia.value,
		estatus: estatus.value
	};

	console.log("Enviando datos:", datosEmpresa);

	$.ajax({
		url: urlPrincipal + "guardar_empresa", // Asegúrate de que sea el endpoint correcto
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(datosEmpresa),
		success: function(response) {
			Swal.fire({
				icon: 'success',
				title: '¡Éxito!',
				text: 'La empresa se guardó correctamente.',
			});
			$('#empresaModal').modal('hide'); // Cierra el modal
			limpiarFormulario(); // Limpia los campos después de guardar
			tablaEmpresas();
		},
		error: function(error) {
			console.error("Error al guardar:", error);

			// Si el backend responde con un mensaje de error
			if (error.responseJSON && error.responseJSON.error) {
				// Mostrar el mensaje de error de la empresa ya registrada con SweetAlert
				Swal.fire({
					icon: 'error',
					title: '¡Error!',
					text: error.responseJSON.error,
				});
			} else {
				Swal.fire({
					icon: 'error',
					title: '¡Error!',
					text: 'Hubo un error al guardar la empresa.',
				});
			}
		}
	});
});

// Función para limpiar los campos después de guardar
function limpiarFormulario() {
	buscarEmpresa.value = "";
	correoEmpresa.value = "";
	personaContacto.value = "";
	frecuencia.selectedIndex = 0;
	estatus.selectedIndex = 0;
}

// Función para validar correo
function validarCorreo(correo) {
	// Expresión regular para validar formato de correo electrónico
	var regexCorreo = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	return regexCorreo.test(correo);
}

function llenarFormulario(empresa) {

	// Verificamos que la empresa no sea null o undefined
	if (!empresa) return;

	// Llenamos los campos del formulario con la información de la empresa
	$("#empresaId").val(empresa.id);
	$("#empresaNombre").val(empresa.nombre);
	$("#empresaCorreo").val(empresa.correoContacto);
	$("#empresaContacto").val(empresa.personaContacto);
	$("#empresaFrecuencia").val(empresa.frecuencia);

	// Abre el modal (por si no se abrió correctamente con data-bs-target)
	$("#modalEmpresa").modal("show");
}

document.getElementById("formEmpresa").addEventListener("submit", function(event) {
	event.preventDefault(); // Evitar recarga de página

	var idEmpresa = document.getElementById("empresaId").value;
	if (!idEmpresa) {
		Swal.fire({
			icon: 'error',
			title: '¡Error!',
			text: 'No se encontró el ID de la empresa.',
		});
		return;
	}

	var datosEmpresa = {
		nombre: document.getElementById("empresaNombre").value,
		correoContacto: document.getElementById("empresaCorreo").value,
		personaContacto: document.getElementById("empresaContacto").value,
		frecuencia: document.getElementById("empresaFrecuencia").value
	};
	
	var correo = document.getElementById("empresaCorreo").value;
	
	// Si el correo no es válido, mostrar un alert de SweetAlert
	if (!validarCorreo(correo)) {
		Swal.fire({
			icon: 'error',
			title: '¡Correo inválido!',
			text: 'Por favor, ingresa un correo electrónico válido.',
		});
		return;
	}
	
	// Mostrar Sweet Alert de Cargando con un spinner
	Swal.fire({
		title: 'Actualizando...',
		html: '<div class="spinner-border text-primary" role="status"><span class="sr-only">Cargando...</span></div>',
		allowOutsideClick: false, // Evita que se cierre al hacer clic fuera
		onBeforeOpen: () => {
			Swal.showLoading(); // Muestra el icono de carga
		}
	});

	$.ajax({
		url: urlPrincipal + "actualizar_empresa/" + idEmpresa, // Ruta en el backend
		type: "PUT",
		contentType: "application/json",
		data: JSON.stringify(datosEmpresa),
		success: function(response) {
			Swal.fire({
				icon: 'success',
				title: '¡Éxito!',
				text: 'La empresa se actualizó correctamente.',
			});

			$('#modalEmpresa').modal('hide'); // Cerrar modal
			tablaEmpresas(); // Refrescar tabla
		},
		error: function(error) {
			Swal.fire({
				icon: 'error',
				title: '¡Error!',
				text: 'Hubo un problema al actualizar la empresa.',
			});
		},
		complete: function() {
			// Cierra el Sweet Alert de "Cargando..."
			Swal.close();
		}
	});
});


function confirmarCambioEstatus(id, estatusActual) {
	// Mostrar un SweetAlert de confirmación
	Swal.fire({
		title: '¿Estás seguro?',
		text: '¿Quieres cambiar el estatus de esta empresa?',
		icon: 'question',	
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Sí, cambiar estatus',
		cancelButtonText: 'Cancelar'
	}).then((result) => {
		if (result.isConfirmed) {
			// Si el usuario confirma, se procede con el cambio de estatus
			actualizarEstatus(id, estatusActual === 1 ? 0 : 1); // Cambiar de Activo a Inactivo o viceversa
		}
	});
}

function actualizarEstatus(id, nuevoEstatus) {
	// Crear los datos a enviar
	var datos = {
		id: id,
		estatus: nuevoEstatus
	};

	// Realizar la solicitud AJAX para actualizar el estatus
	$.ajax({
		url: urlPrincipal + "actualizar_estatus_empresa", // Endpoint que maneja la actualización
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(datos),
		success: function(response) {
			Swal.fire({
				icon: 'success',
				title: '¡Éxito!',
				text: 'El estatus de la empresa se actualizó correctamente.',
			});
			tablaEmpresas(); // Actualizar la tabla con el nuevo estatus
		},
		error: function(error) {
			console.error("Error al actualizar el estatus:", error);
			Swal.fire({
				icon: 'error',
				title: '¡Error!',
				text: 'Hubo un error al actualizar el estatus de la empresa.',
			});
		}
	});
}

// Mostrar/Ocultar contraseña
function togglePassword() {
    var passwordInput = document.getElementById("correoPassword");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}

// Insertar comodines en el mensaje
function insertarComodin(comodin) {
    var mensajeTexto = document.getElementById("mensajeTexto");
    
    // Obtener la posición actual del cursor
    var cursorPos = mensajeTexto.selectionStart;
    var textBefore = mensajeTexto.value.substring(0, cursorPos);
    var textAfter = mensajeTexto.value.substring(cursorPos, mensajeTexto.value.length);

    // Insertar el comodín en la posición del cursor
    mensajeTexto.value = textBefore + comodin + textAfter;
    
    // Mover el cursor al final del comodín insertado
    mensajeTexto.selectionStart = mensajeTexto.selectionEnd = cursorPos + comodin.length;
}

document.addEventListener("DOMContentLoaded", function () {
    // Event listener para abrir el modal
    $('#mensajeModal').on('show.bs.modal', function (e) {
        cargarConfiguracionCorreo();
    });
});

function cargarConfiguracionCorreo() {
    $.ajax({
        url: urlPrincipal + "carga_configuracion-correo", // URL del backend
        type: "GET", // Tipo de solicitud (GET para obtener datos)
        success: function(data) {
            if (data) {
                // Si hay datos, llenar los campos del formulario con los datos obtenidos
                document.getElementById("correoRemitente").value = data.correoRemitente || "";
                document.getElementById("correoPassword").value = data.password || "";
                document.getElementById("mensajeTexto").value = data.mensaje || "";
                
                // Convertir la expresión CRON a formato de hora
                if (data.cronExpresion) {
                    const horaCron = data.cronExpresion;  // La expresión CRON (ejemplo: "0 30 14 * * ?")
                    const cronParts = horaCron.split(" ");  // Dividimos la expresión CRON por espacios
                    
                    // Asegurarse que la expresión CRON tiene al menos tres partes (minutos, horas y el resto)
                    if (cronParts.length >= 3) {
                        let horas = cronParts[2];  // Tercera parte es la hora
                        let minutos = cronParts[1];  // Segunda parte son los minutos

                        // Usar padStart para asegurar que la hora y los minutos tengan dos dígitos
                        horas = horas.padStart(2, '0');
                        minutos = minutos.padStart(2, '0');

                        // Asignamos la hora al campo de entrada, en formato HH:mm
                        const horaFormateada = `${horas}:${minutos}`;
                        console.log(horaFormateada); // Log para depuración
                        document.getElementById("horaCron").value = horaFormateada || "";
                    }
                }
            } else {
                // Si no se obtienen datos, limpiar los campos
                document.getElementById("correoRemitente").value = "";
                document.getElementById("correoPassword").value = "";
                document.getElementById("mensajeTexto").value = "";
                document.getElementById("horaCron").value = ""; // Limpiar la hora
            }
        },
        error: function(error) {
            console.error("Error al obtener la configuración de correo:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Hubo un problema al cargar la configuración de correo.',
            });
        }
    });
}




function actualizarConfiguracionCorreo() {
    // Obtener los datos del formulario
    const correoRemitente = document.getElementById('correoRemitente').value;
    const correoPassword = document.getElementById('correoPassword').value;
    const mensajeTexto = document.getElementById('mensajeTexto').value;
    const horaCron = document.getElementById('horaCron').value; // Capturar la hora seleccionada
    
    // Verificar si la horaCron tiene un valor válido
    if (!horaCron) {
        Swal.fire({
            title: 'Error',
            text: 'Debe ingresar un horario válido para la ejecución del cron.',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        });
        return;
    }

    // Separar la hora y los minutos (ejemplo: "09:00" => "09" y "00")
    const [hora, minutos] = horaCron.split(':');
    
    if (hora && minutos) {
		
		const cronHora = parseInt(hora, 10) < 10 ? hora.replace(/^0+/, '') : hora;
		const cronMinutos = parseInt(minutos, 10) < 10 ? minutos.slice(1) : minutos;
		
        // Crear la expresión CRON
        const cronExpresion = `0 ${cronMinutos} ${cronHora} * * ?`; // Expresión cron corregida

        console.log("Expresión cron:", cronExpresion);

        // Crear el objeto con los datos a actualizar
        const datos = {
            correoRemitente: correoRemitente,
            password: correoPassword,
            mensaje: mensajeTexto,
            cronExpresion: cronExpresion // Añadimos la expresión cron al objeto
        };

        console.log('Datos enviados:', datos);

        // Mostrar el SweetAlert de "Actualizando..." con spinner
        Swal.fire({
            title: 'Actualizando...',
            text: 'Por favor, espera mientras se guardan los cambios.',
            allowOutsideClick: false,
            allowEscapeKey: false,
            showConfirmButton: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        // Enviar los datos al servidor usando fetch (PUT request)
        fetch(urlPrincipal + 'actualizar', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos) // Convertir los datos a JSON
        })
        .then(response => {
            console.log('Respuesta del servidor:', response);
            return response.json().then(data => ({ status: response.status, body: data })); // Convertimos a JSON
        })
        .then(({ status, body }) => {
            console.log('JSON recibido:', body);
            if (status === 200) {
                Swal.fire({
                    title: '¡Éxito!',
                    text: 'Configuración de correo actualizada con éxito',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                }).then(() => {
                    cargarConfiguracionCorreo(); // Llamar a esta función para cargar la configuración actualizada
                });
            } else {
                Swal.fire({
                    title: 'Error',
                    text: body.message || 'No se pudo actualizar la configuración',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
            }
        })
        .catch(error => {
            console.error('Error al actualizar la configuración:', error);
            Swal.fire({
                title: 'Error de comunicación',
                text: 'Hubo un error en la comunicación con el servidor',
                icon: 'error',
                confirmButtonText: 'Aceptar'
            });
        });
    } else {
        Swal.fire({
            title: 'Error',
            text: 'El formato de la hora es incorrecto. Asegúrese de ingresar un valor en formato HH:mm.',
            icon: 'error',
            confirmButtonText: 'Aceptar'
        });
    }
}












