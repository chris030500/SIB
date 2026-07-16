// @ts-nocheck
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function irLogin() {
	window.location.replace(window.location.origin + "/STD/login?error=passwordChanged");
}

//Funcion para que genere la contraseña
function generarContrasena() {
	var urlBase = getAbsolutePath();
	console.log("Entro en generarContrasena");
	var contrasenia = $('#password').val();

	console.log("Contraeña: " + contrasenia);

	$.ajax({
		url: urlBase + "mensajeContrasena?contrasena=" + contrasenia,
		type: "GET",
		success: function(respuesta) {
			console.log("Entro en success1: " + respuesta);

			if (respuesta != "Si cumple con los parametros requeridos") {
				Swal.fire({
					icon: 'warning',
					title: respuesta,
					confirmButtonText: 'Aceptar'
				});

			} else {
				$.ajax({
					url: urlBase + "guardar_contrasena?password=" + contrasenia,
					type: "POST",
					data: contrasenia,
					success: function() {
						console.log("Entro en guardar contraseña");

						Swal.fire({
							icon: 'success',
							title: 'Contraseña cambiada correctamente',
							confirmButtonText: 'Aceptar'
						}).then(() => {
							irLogin();
						});
					},
					error: function(xhr) {
						if (xhr.status === 400) {
							var errorMessage = xhr.responseText;

							Swal.fire({
								icon: 'warning',
								title: errorMessage,
								confirmButtonText: 'Aceptar'
							}).then(() => {
								irLogin();
							});
						} else {
							Swal.fire({
								icon: 'warning',
								title: "Error al cambiar la contraseña",
								confirmButtonText: 'Aceptar'
							}).then(() => {
								irLogin();
							});
						}
					}
				});
			}
		},
		error: function(respuesta) {
			console.log("Entro en error");

			Swal.fire({
				icon: 'warning',
				title: respuesta,
				confirmButtonText: 'Aceptar'
			});
		}
	});
}