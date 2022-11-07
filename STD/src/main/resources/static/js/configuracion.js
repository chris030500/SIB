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
	swal({
		type: 'success',
		title: 'Configuración guardada',
		showConfirmButton: false,
		timer: 68000 // es ms (mili-segundos)
	});
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
		url: "http://localhost:8090/configuracion/cambioSesiones?desbloqueo=" + tipoDesbloqueo + "&selectUsuario=" + usuario,
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
	$.ajax({
		url: "http://localhost:8090/usuario/" + cadena,
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

//Funcion para que genere la contraseña
function generarContrasena() {
	console.log("Entro en generarContrasena");
	var contrasenia = $('#contrasena').val();

	//var contrasena = $('#contrasena').val();
	//let mensaje = data.value;
	console.log("Contraeña: " + contrasenia);
	$.ajax({
		url: "http://localhost:8090/configuracion/mensajeContrasena?contrasena=" + contrasenia,
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
			}
			$.ajax({
				url: "http://localhost:8090/configuracion/guardarContrasena?contrasena=" + contrasenia,
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

				}
			});

		},
		error: function(respuesta) {
			console.log("Entro en error");
			swal({
					type: 'warning',
					title: respuesta,
					showConfirmButton: false

				});

		}
	});
}