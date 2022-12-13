
function buscarEmpleado(empleado) {
	//var selectUsuario = $('#selectUsuario');

	var busquedaEmpleado = document.getElementById("busquedaEmpleado").value;
	var divEmpleado = document.getElementById("datosUsuario");
	console.log("Campo busquedaEmpleado: " + busquedaEmpleado.length);
	var option = '';
	console.log("Empleado: " + empleado);
	if (busquedaEmpleado == 0) {
		console.log("Entro en if de busquedaEmpleado");
		$("select#selectEmpleado").empty();
		$("#EmpleadoId").val('');
		$("#Nombre").val('');
		$("#Puesto").val('');
		divEmpleado.style.display = "none";


	} else {
		console.log("Entro en else de busquedaEmpleado");
		$.ajax({
			url: "http://localhost:8090/empleado/listado?nombre=" + empleado,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				/*console.log(respuesta);
				console.log(respuesta.length);*/
				for (var i = 0; i < respuesta.length; i++) {
					var nombre = respuesta[i].nombreCompleto;
					var idUsuario = respuesta[i].empleadoID;
					/*console.log()
					console.log("nombre : " + nombre);
					console.log("idUsuario : " + idUsuario);*/
					option += '<option value="' + respuesta[i].empleadoID + ' " data-descripcion=' + respuesta[i].descripcion + ' >' + respuesta[i].nombreCompleto + '</option>';
					$("select#selectEmpleado").empty().append(option);
					//' data-descripcion='+ respuesta[i].descripcion+'
				}

			}
		});
	}

}

document.getElementById('selectEmpleado').onchange = function() {
	console.log("Entro en jquery");
	var selectEmpleado = document.getElementById('selectEmpleado').value;
	var divEmpleado = document.getElementById("datosUsuario");
	console.log("SelectEmpleado: " + selectEmpleado);
	if (selectEmpleado > 0) {
		console.log("Entro en mayor a 0");
		divEmpleado.style.display = "block";
	} else {
		console.log("No tiene valor");
	}
	/* Referencia al option seleccionado */
	var mOption = this.options[this.selectedIndex];
	/* Referencia a los atributos data de la opción seleccionada */
	var mData = mOption.dataset;

	/* Referencia a los input */
	var EmpleadoId = document.getElementById('EmpleadoId');
	var Nombre = document.getElementById('Nombre');
	var Puesto = document.getElementById('Puesto');


	//Asignamos el valor del ID Empleado
	EmpleadoId.value = this.value;
	//Asignamos el valor del nombre del empleado
	Nombre.value = mOption.text;
	//Asignamos el valor del puesto del empleado
	Puesto.value = mData.descripcion;  /*Se usará el valor que se muestra*/

};

$(document).ready(function($) {
	$("#edit-form").submit(function(event) {
		event.preventDefault();
		postEdit();
	});
});

function postEdit() {
	console.log("Entro en postEdit");
	var something = $('#edit-form').serialize();
	var editUrl = url + 'update';
	$.post(editUrl, something, function(data) {
		updateTable(data);
	});
	clearAndCloseModal('#basicModal');
}

function showEditModal(index) {
	console.log("index: " + index);
	var url = "http://localhost:8090/usuario/usuario/loadEntity/";

	console.log("entro en showEditModal");

	var editUrl = url + index;
	console.log("editUrl: " + editUrl);
	loadEntity(editUrl);
}

function loadEntity(url) {
	console.log("entro en loadEntity")
	console.log("url: " + url);
	$.getJSON(url, {}, function(data) {
		populateModal(data);
	});
}

// Assign the data values to the modal form.
function populateModal(data) {
	console.log("Data en populateModal: " + JSON.stringify(data));
	$('#nombre').val(data.vc_nombre_usuario);
	$('#idUsuario').val(data.pk_id_usuario);
	//$('#update-name').val(data.name);
}

function altaUsuario() {
	var nombreUsuario = document.getElementById('nombreUsuario').value;
	var idEmpleado = document.getElementById('EmpleadoId').value;
	var contrasena = document.getElementById('contrasena').value;
	var rol = document.getElementById('roles').value;
	console.log("Nombre Usuario: " + nombreUsuario);
	console.log("idEmpleado: " + idEmpleado);
	console.log("contrasena: " + contrasena);
	console.log("Rol: " + rol);
	if (idEmpleado == '') {
		console.log("Ingrese un colaborador");
		swal("Precaución", "Ingrese un colaborador.",
			"info");
	} else if (idEmpleado != '') {
		if (nombreUsuario == '') {
			console.log("Ingrese un usuario");
			swal("Precaución", "Ingrese un nombre de usuario.",
				"info");
		} else {
			console.log("Puede continuar");
			$.ajax({
				url: "http://localhost:8090/usuario/nombre/" + nombreUsuario,
				type: "GET",
				success: function(respuesta) {
					/*console.log("Entro en success de get");
					console.log("Respuesta: " +respuesta);*/
					console.log("Validar NombreUsuario: ");
					console.log("Respuesta: " + respuesta);
					if (respuesta == true) {
						swal("Precaución", "Usuario ya registrado, intente con otro nombre.",
							"info");
					} else {
						console.log("Usuario para registrar");
						$.ajax({
							url: "http://localhost:8090/usuario/idEmpleado/" + idEmpleado,
							type: "GET",
							success: function(respuesta) {
								if (respuesta == true) {
									swal("Precaución", "Id Empleado ya registrado, intente con otro usuario.",
										"info");
								} else {
									console.log("Puede continuar en agregar el usuario");
									$.ajax({
										url: "http://localhost:8090/usuario/guardar_usuario?EmpleadoId=" + idEmpleado + "&nombreUsuario=" + nombreUsuario + "&roles=" + rol,
										type: "POST",
										success: function() {
											console.log("Entro en success de get");
											/*console.log("Entro en success de get");
											console.log("Respuesta: " +respuesta);*/
											//return respuesta

											console.log("Busca el usuario registrado con anterioridad");
											console.log("Nombre de usuario: " + nombreUsuario);
											$.ajax({
												url: "http://localhost:8090/usuario/nombreUsuario/" + nombreUsuario,
												type: "GET",
												dataType: "JSON",
												success: function(respuesta) {
													//console.log("respuesta lenght" + respuesta.lenght);

													if (nombreUsuario == '') {
														console.log("Esta vacio el campo");
													} else {
														if (respuesta != null) {
															var data = {
																dt_vig_cont_fin: (new Date()).toISOString().split('T')[0],
																dt_vig_cont_inicio: (new Date()).toISOString().split('T')[0],
																vc_indicador_activo: 1,
																usr_historico_contrasena_id: respuesta
															};
															console.log("Data: " + JSON.stringify(data));
															console.log("Tiene información");
															console.log("Respuestas");
															console.log(respuesta);
															console.log("PK: " + respuesta.pk_id_usuario);
															$.ajax({
																headers: {
																	'Accept': 'application/json',
																	'Content-Type': 'application/json'
																},
																url: "http://localhost:8090/hist_contrasena/",
																type: "POST",
																data: JSON.stringify(data),
																dataType: "json",
																success: function(respuesta) {
																	/*console.log("Entro en success de get");
																	console.log("Respuesta: " +respuesta);*/
																	console.log("Se guardo la contraseña en historial contraseña: ");
																	swal({
																		icon: "success",
																		title: "Registro de usuario exitoso.",
																		showConfirmButton: false

																	}).then((result) => {
																		window.location.replace("http://localhost:8090/usuario/formulario");
																	});

																},
																error: function(respuesta) {
																	console.log("Entro en error");
																	swal({
																		icon: "warning",
																		title: respuesta,
																		showConfirmButton: false

																	});

																}
															});

														} else {
															console.log("No tiene información");
														}
													}

												}
											});

										},
										error: function(respuesta) {
											console.log("Entro en error");
											swal({
												icon: "warning",
												title: "No se pudo agregar el usuario",
												showConfirmButton: false

											});

										}
									});
								}

							},
							error: function(respuesta) {
								console.log("Entro en error");
								swal({
									icon: 'warning',
									title: respuesta,
									showConfirmButton: false

								});

							}
						});
					}

				},
				error: function(respuesta) {
					console.log("Entro en error");
					swal({
						icon: 'warning',
						title: respuesta,
						showConfirmButton: false

					});

				}
			});

		}
	}

}

function validarNombreUsuario(nombreUsuario) {
	$.ajax({
		url: "http://localhost:8090/usuario/nombre/" + nombreUsuario,
		type: "GET",
		success: function(respuesta) {
			/*console.log("Entro en success de get");
			console.log("Respuesta: " +respuesta);*/
			console.log("Validar NombreUsuario: ");
			console.log("Respuesta: " + respuesta);
			return respuesta

		},
		error: function(respuesta) {
			console.log("Entro en error");
			swal({
				icon: 'warning',
				title: respuesta,
				showConfirmButton: false

			});

		}
	});
}

//funcion para dar de baja al usuario (Estatus Usuario == false)
function bajaUsuario(usuario) {
	console.log("Entro en dar baja usuario");
	console.log("Usuario: " + usuario);
	$.ajax({
		url: "http://localhost:8090/usuario/baja_usuario?usuario=" + usuario,
		type: "POST",
		success: function() {
			/*console.log("Entro en success de get");
			console.log("Respuesta: " +respuesta);*/
			console.log("Validar NombreUsuario: ");
			swal({
				icon: 'success',
				title: "Baja de usuario correctamente.",
				showConfirmButton: false

			}).then((result) => {
				window.location.reload();
			});

		},
		error: function() {
			console.log("Entro en error");
			swal({
				icon: 'warning',
				title: "No se realizo la baja del usuario",
				showConfirmButton: false

			}).then((result) => {
				window.location.reload();
			});

		}
	});
}

//funcion para reacitvar el usuario (Estatus Usuario = true)
function reactivarUsuario(usuario) {
	console.log("Entro en reactivar usuario");
	console.log("Usuario: " + usuario);
	$.ajax({
		url: "http://localhost:8090/usuario/reactivar_usuario?usuario=" + usuario,
		type: "POST",
		success: function() {
			/*console.log("Entro en success de get");
			console.log("Respuesta: " +respuesta);*/
			console.log("Validar NombreUsuario: ");

			swal({
				icon: 'success',
				title: "Reactivación de usuario correctamente.",
				showConfirmButton: false

			}).then((result) => {
				window.location.reload();
			});

		},
		error: function() {
			console.log("Entro en error");
			swal({
				icon: 'warning',
				title: "No se reactivo el usuario",
				showConfirmButton: false

			}).then((result) => {
				window.location.reload();
			});

		}
	});
}

//función para editar usuarios desde el modal
function editarUsuario() {

	var idUsuario = document.getElementById('idUsuario').value;
	var nombreUsuario = document.getElementById('nombre').value;

	//Primero validamos que el nombre de usuario no exista.
	if (nombreUsuario == "") {
		console.log("Ingresa un nombre de usuario");
		swal({
			icon: 'warning',
			title: "El campo de nombre de usuario está vacío, ingresa un nombre",
			showConfirmButton: false

		});
	} else {
		console.log("Puede continuar");
		$.ajax({
			url: "http://localhost:8090/usuario/nombreUsuario/" + nombreUsuario,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				/*console.log("Entro en success de get");
				console.log("Respuesta: " +respuesta);*/
				console.log("Validar NombreUsuario: ");
				console.log("Respuesta: " + JSON.stringify(respuesta));
				if (respuesta != null) {
					console.log("El nombre de usuario ya existe");
					swal({
						icon: 'warning',
						title: "El nombre de usuario ya existe, favor de usar otro nombre",
						showConfirmButton: false

					});
				} else if (respuesta == null) {
					console.log("El nombre de usuario no existe");
					$.ajax({
						url: "http://localhost:8090/usuario/editar_usuario?usuario=" + nombreUsuario + "&idUsuario="+idUsuario,
						type: "POST",
						success: function() {
							/*console.log("Entro en success de get");
							console.log("Respuesta: " +respuesta);*/
							

							swal({
								icon: 'success',
								title: "Se cambio correctamente el nombre de usuario.",
								showConfirmButton: false

							}).then((result) => {
								window.location.reload();
							});

						},
						error: function() {
							console.log("Entro en error");
							swal({
								icon: 'warning',
								title: "No se reactivo el usuario",
								showConfirmButton: false

							}).then((result) => {
								window.location.reload();
							});

						}
					});

				}

			},
			error: function(respuesta) {
				console.log("Entro en error");
				swal({
					icon: 'warning',
					title: respuesta,
					showConfirmButton: false

				});

			}
		});
	}

}

