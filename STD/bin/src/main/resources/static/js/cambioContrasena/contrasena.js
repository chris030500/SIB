function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

//Funcion para que genere la contraseña
function generarContrasena() {
	var urlBase = getAbsolutePath();
	console.log("Entro en generarContrasena");
	var contrasenia = $('#password').val();

	//var contrasena = $('#contrasena').val();
	//let mensaje = data.value;
	console.log("Contraeña: " + contrasenia);
	//Primero validamos que la contraseña cumpla con los parametros solicitados
	$.ajax({
		url: urlBase + "mensajeContrasena?contrasena=" + contrasenia,
		type: "GET",
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
					url: urlBase + "guardar_contrasena?password=" + contrasenia,
					type: "POST",
					data: contrasenia,
					success: function() {
						console.log("Entro en guardar contraseña");
						swal({
							type: 'success',
							title: 'Contraseña cambiada correctamente',
							showConfirmButton: false
						}).then((result) => {
							window.location.replace(urlBase);
						});

					},
					error: function(xhr, status, error) {
						console.log("status: " + status);
						console.log("error: " + error);
						if (xhr.status === 400) {
							var errorMessage = xhr.responseText;
							// Mostrar el mensaje de error al usuario
							console.log(errorMessage);
							swal({
								type: 'warning',
								title: errorMessage,
								showConfirmButton: false
							}).then((result) => {
								window.location.replace(newUrlBase + "formulario");
							});
						} else {
							swal({
								type: 'warning',
								title: "Error al cambiar la contraseña",
								showConfirmButton: false
							}).then((result) => {
								window.location.replace(newUrlBase + "formulario");
							});
						}
					}
				});
			}


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