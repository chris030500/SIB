//Funcion para que genere la contraseña
function generarContrasena() {
	console.log("Entro en generarContrasena");
	var contrasenia = $('#password').val();

	//var contrasena = $('#contrasena').val();
	//let mensaje = data.value;
	console.log("Contraeña: " + contrasenia);
	//Primero validamos que la contraseña cumpla con los parametros solicitados
	$.ajax({
		url: "http://localhost:8090/mensajeContrasena?contrasena=" + contrasenia,
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
					url: "http://localhost:8090/guardar_contrasena?password=" + contrasenia,
					type: "POST",
					data: contrasenia,
					success: function() {
						console.log("Entro en guardar contraseña");
						swal({
							type: 'success',
							title: 'Contraseña cambiada correctamente',
							showConfirmButton: false
						}).then((result) => {
							window.location.replace("http://localhost:8090/");
						});

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