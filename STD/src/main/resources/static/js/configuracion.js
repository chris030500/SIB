// @ts-nocheck
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

var urlBase = getAbsolutePath();


//Obtener el campo numPersonas
function numMaximoParametrosContrasena(longitud) {
	var longitud = $("#longitud").val();
	var mayusculas = $("#mayusculas").val();
	var minusculas = $("#minusculas").val();
	var numeros = $("#numeros").val();
	var caracteres = $("#caracteres").val();

	var aux = Number(mayusculas) + Number(minusculas) + Number(numeros) + Number(caracteres);

	if (longitud < aux) {
		//console.log("Excediste el tamaño");
		swal("Excediste la longitud de la contraseña", "Favor de verificar los parametros agregados", "warning");
		document.getElementById("mayusculas").value = "";
		document.getElementById("minusculas").value = "";
		document.getElementById("numeros").value = "";
		document.getElementById("caracteres").value = "";
	}
	//document.getElementById("longitud");
	console.log("Aux = " + aux);
	console.log(longitud);
	//console.log("Hola")
	//return longitud;
}


function mensaje() {
	swal({
		type: 'success',
		title: 'Configuración guardada',
		showConfirmButton: false,
		timer: 24000 // es ms (mili-segundos)
	});
}

function guardarConfSesion() {
	var intento = $('#intento').val();
	var pBloqueo = $('#pBloqueo').val();
	var pDias = $('#pDias').val();
	console.log("Intentos: " + intento);
	if (intentos = '') {
		swal({
			icon: "warning",
			title: "Agrega un valor en el campo de intentos",
			showConfirmButton: false

		});
	} else if (pBloqueo == '') {
		swal({
			icon: "warning",
			title: "Agrega un valor en el campo periodo de bloqueo",
			showConfirmButton: false

		});
	} else if (pDias == '') {
		swal({
			icon: "warning",
			title: "Agrega un valor en el campo periodo que se encontrará activa la cuenta",
			showConfirmButton: false

		});
	} else {
		console.log(urlBase);
		$.ajax({
			url: urlBase + "guardarBloqueoSesiones?intento=" + intento + "&pBloqueo=" + pBloqueo + "&pDias=" + pDias,
			type: "POST",
			success: function() {
				swal({
					icon: 'success',
					title: 'Configuración de parametros de sesión exitosa',
					showConfirmButton: false,
					timer: 24000 // es ms (mili-segundos)
				}).then((result) => {
					window.location.reload();
				});

			},
			error: function() {
				swal({
					icon: 'warning',
					title: 'No se realizo correctamente el desbloqueo',
					showConfirmButton: false
					//timer: 24000 // es ms (mili-segundos)
				});
			}
		});
	}
	/*swal({
		type: 'success',
		title: 'Configuración guardada',
		showConfirmButton: false,
		timer: 68000 // es ms (mili-segundos)
	});*/
}

//Funcion para desbloquear sesion o cuenta del usuario seleccionado
function desbloquearUsuario() {


	var usuario = $('#selectUsuario').val();
	var tipoDesbloqueo = $('input:checkbox[name=desbloqueo]:checked').val();
	console.log("desbloqueo: " + tipoDesbloqueo);
	var data = {
		desbloqueo: $('#desbloqueo').val(),
		selectUsuario: $('#selectUsuario').val()
	};

	$.ajax({
		url: urlBase + "cambioSesiones?desbloqueo=" + tipoDesbloqueo + "&selectUsuario=" + usuario,
		type: "POST",
		data: JSON.stringify(data),
		success: function() {
			swal({
				type: 'success',
				title: 'Desbloqueo correctamente',
				showConfirmButton: false,
				timer: 24000 // es ms (mili-segundos)
			}).then((result) => {
				window.location.reload();
			});

		},
		error: function() {
			swal({
				type: 'warning',
				title: 'No se realizo correctamente el desbloqueo',
				showConfirmButton: false
				//timer: 24000 // es ms (mili-segundos)
			});
		}
	});
}

//Funcion para buscar nombre de usuario
function buscar_ajax(cadena) {
	//var selectUsuario = $('#selectUsuario');
	var selectUsuario = document.getElementById("selectUsuario");
	var option = '';
	var newUrlBase = urlBase.replace("configuracion/", "");
	$.ajax({
		url: newUrlBase + "usuario/" + cadena,
		type: "GET",
		dataType: "JSON",
		success: function(respuesta) {
			console.log(respuesta);
			console.log(respuesta.length);
			for (var i = 0; i < respuesta.length; i++) {
				var nombre = respuesta[i].vc_nombre_usuario;
				var idUsuario = respuesta[i].pk_id_usuario;
				console.log()
				console.log("nombre : " + nombre);
				console.log("idUsuario : " + idUsuario);
				option += '<option value="' + respuesta[i].pk_id_usuario + '">' + respuesta[i].vc_nombre_usuario + '</option>';
				$("select#selectUsuario").empty().append(option);

			}

		}
	});
}

//Funcion para desmarcar una opción del combo de checkbox sobre el desbloqueo de sesion y cuenta
function desmarcar() {

	var sesion = document.getElementById("desbloquearSesion");
	var cuenta = document.getElementById("desbloquearCuenta");
	sesion.onclick = function() {
		if (sesion.checked != false) {
			cuenta.checked = null;
		}
	}
	cuenta.onclick = function() {
		if (cuenta.checked != false) {
			sesion.checked = null;
		}
	}
}

function generarContrasenaold() {
	console.log("Entro en generarContrasena");
	var contrasenia = $('#contrasena').val();
	if (contrasenia != '') {
		console.log("La contraseña contine info");
		$.ajax({
			url: urlBase + "guardarContrasena?contrasena=" + contrasenia,
			type: "POST",
			success: function() {
				swal({
					type: 'success',
					title: 'Contraseña por default guardada',
					showConfirmButton: false,
					timer: 24000 // es ms (mili-segundos)
				}).then((result) => {
					window.location.reload();
				});

			},
			error: function(respuesta) {
				console.log("Respuesta: " + JSON.stringify(respuesta));
				swal({
					type: 'warning',
					title: 'No se guardo la nueva contraseña por default',
					showConfirmButton: false
					//timer: 24000 // es ms (mili-segundos)
				});
			}
		});

	} else {
		console.log("La contraseña es vacía");
		swal({
			icon: 'warning',
			title: 'No se ha ingresado una contraseña, favor de ingresar una',
			showConfirmButton: false
			//timer: 24000 // es ms (mili-segundos)
		});
	}
}

//Funcion para que genere la contraseña
function generarContrasena() {
	console.log("Entro en generarContrasena 1");
	var contrasenia = $('#contrasena').val();

	//var contrasena = $('#contrasena').val();
	//let mensaje = data.value;
	console.log("Contraeña: " + contrasenia);
	$.ajax({
		url: urlBase + "mensajeContrasena?contrasena=" + contrasenia,
		type: "GET",
		data: contrasenia,
		success: function(respuesta) {
			console.log("Entro en success1: " + respuesta);
			if (respuesta != "Si cumple con los parametros requeridos") {
				swal({
					type: 'warning',
					title: respuesta,
					showConfirmButton: false

				});
			} else if (respuesta == "Si cumple con los parametros requeridos") {
				$.ajax({
					url: urlBase + "guardarContrasena?contrasena=" + contrasenia,
					type: "POST",
					data: contrasenia,
					success: function() {
						console.log("Entro en guardar contraseña");
						swal({
							type: 'success',
							title: 'Contraseña cambiada correctamente',
							showConfirmButton: false
						}).then((result) => {
							window.location.reload();
						});

					},
					error: function(xhr, status, error) {
						if (xhr.status === 400) {
							var errorMessage = xhr.responseText;
							// Mostrar el mensaje de error al usuario
							console.log(errorMessage);
							swal({
								type: 'warning',
								title: errorMessage,
								showConfirmButton: false
							}).then((result) => {
								window.location.reload();
							});
						} else {
							// Manejar otros errores de la solicitud AJAX
							swal({
								type: 'warning',
								title: "Error al cambiar la contraseña",
								showConfirmButton: false
							}).then((result) => {
								window.location.reload();
							});
						}
					}
				});
			}


		},
		error: function(respuesta) {
			console.log("Entro en error");
			console.log("Error: " + JSON.stringify(respuesta));
			swal({
				type: 'warning',
				title: respuesta,
				showConfirmButton: false

			});

		}
	});
}