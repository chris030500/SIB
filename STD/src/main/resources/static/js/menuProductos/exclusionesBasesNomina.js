var obtenerEstados = "exclusiones/getEstados"; 
var obtenerMunicipios = "exclusiones/getMunicipios";
var actualizarIndicadorMunicipios = "exclusiones/actualizarIndicadorMunicipios";
var obtenerConvenios = "exclusiones/getConveniosNomina";
var obtenerConveniosActivos = "exclusiones/getConveniosNominaActivos";
var obtenerConveniosDepurar = "exclusiones/getConveniosNominaDepurar";
var actualizarIndicadorConvenios  = "exclusiones/actualizarIndicadorConveniosNomina";
var obtenerPuestosOcupacionActivos = "exclusiones/getPuestosOcupacionActivos";
var obtenerPuestosOcupacionDepurar = "exclusiones/getPuestosOcupacionDepurar";
var actualizarIndicadorPuestos = "exclusiones/actualizarIndicadorPuestos";
var obtenerSucursalesActivos = "exclusiones/getSucursalesActivos";
var obtenerSucursalesDepurar = "exclusiones/getSucursalesDepurar";
var actualizarIndicadorSucursales = "exclusiones/actualizarIndicadorSucursales";
var puestoSeleccionado = null;
var convenioSeleccionado = null;
var sucursalSeleccionado = null;
var idConvenio = null;
var idPuesto = null;
var idSucursal = null;

function listaEstados() {
    var selectEstados = $('#opcionesEstado'); // Seleccionar el elemento select por su ID

    // Limpiar opciones existentes
    selectEstados.empty();

    // Agregar opción predeterminada
    selectEstados.append('<option value="">Seleccionar estado</option>');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerEstados,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar opciones al select
            $.each(res.estados, function(index, estado) {
                var option = $('<option>', {
                    value: estado.id_estado, // ID del estado
                    text: estado.nombre_estado // Nombre del estado
                });

                // Agregar la opción al select
                selectEstados.append(option);
            });
        },
        error: function(error) {
            console.error("Error al obtener los estados: " + error);
        }
    });
}

function listaMunicipios(estadoId) {
    var selectMunicipios = $('#opcionesMunicipio'); // Seleccionar el elemento select por su ID

    // Limpiar opciones existentes
    selectMunicipios.empty();

    // Agregar opción predeterminada
    selectMunicipios.append('<option value="">Seleccionar municipio</option>');

    // Hacer la solicitud AJAX para obtener los municipios por estado
    
    $.ajax({
        url: getAbsolutePath() + obtenerMunicipios,
        type: "GET",
        dataType: "JSON",
        data: {
            idEstado: estadoId // Enviar el ID del estado seleccionado
        },
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar opciones al select
            $.each(res.municipios, function(index, municipio) {
                var option = $('<option>', {
                    value: municipio.id_municipio, // ID del municipio
                    text: municipio.nombre_municipio // Nombre del municipio
                });

                // Agregar la opción al select
                selectMunicipios.append(option);
            });
        },
        error: function(error) {
            console.error("Error al obtener los municipios: " + error);
        }
    });
}

document.getElementById('btnAgregar').addEventListener('click', function() {
    // Obtener los valores seleccionados
    const idEstado = document.getElementById('opcionesEstado').value;
    const idMunicipio = document.getElementById('opcionesMunicipio').value;
    

    // Verificar que ambos valores están seleccionados
    if (idEstado && idMunicipio) {
        // Llamar a la función para actualizar
       updateMunicipios(idEstado, idMunicipio, 1);
    } else {
        alert('Por favor, seleccione un estado y un municipio.');
    }
});


function updateMunicipios(idEstado, idMunicipio, indicadorDepurar) {
    
   	var servicioURL = actualizarIndicadorMunicipios + "?idEstado=" + Number(idEstado) + "&idMunicipio=" + Number(idMunicipio) + "&indicadorDepurar=" + indicadorDepurar;
   			
   	$.ajax({
		url: getAbsolutePath() + servicioURL,
		type: "GET",
		success: function(response) {
   
            if (response.municipio.i_indicador_depurar === 1) {
                
               	swal(
					'¡Guardado exitoso!',
					"Los datos se han guardado correctamente.",
					"success"
				)

				setTimeout(function() {
					swal.close(); // Cierra el SweetAlert
				}, 2000);
                
            }
            
            if (response.municipio.i_indicador_depurar === 0) {
                
               	swal(
					'¡Eliminado exitoso!',
					"Se ha deshabilitado exitosamente",
					"success"
				)

				setTimeout(function() {
					swal.close(); // Cierra el SweetAlert
				}, 2000);
                
            }
            
			$('#opcionesMunicipio').empty();
			listaEstados();
			listaMunicipiosConCobertura();
		},
		error: function(xhr, status, error) {
			swal(
				'Error!',
				"Hubo un problema al guardar los datos.",
				"error"
			)

			setTimeout(function() {
				swal.close(); // Cierra el SweetAlert
			}, 2000);
		}
	});
}

function listaConveniosActivos() {
    var divConveniosActivos = $('#conveniosActivos');
    
    $.ajax({
        url: getAbsolutePath() + obtenerConveniosActivos,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar 
            $.each(res.conveniosActivos, function(index, convenio) {
                
                var div = $("<div>")
                .addClass("convenioA border")
                .text(convenio.desc_convenio)
                .attr("convenio-id", convenio.id_convenio);
                
                div.on("click", function() {
                    $(".convenioA").removeClass("selected"); // Remover selección previa
                    $(this).addClass("selected");  // Añadir clase selected
                    
                    // Obtener el ID del convenio seleccionado
                    var idConvenio = $(this).attr("convenio-id");
                    convenioSeleccionado = $(this);
                    
                });

               divConveniosActivos.append(div);
            });
        },
        error: function(error) {
            console.error("Error al obtener los convenios activos: " + error);
        }
    });
}

function listaConveniosDepurar() {
    var divConveniosActivos = $('#conveniosDepurar');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerConveniosDepurar,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar opciones al select
            $.each(res.conveniosDepurar, function(index, convenio) {
                
                var div = $("<div>")
                .addClass("convenioD border")
                .text(convenio.desc_convenio)
                .attr("convenio-id", convenio.id_convenio);
                
                div.on("click", function() {
                    $(".convenioD").removeClass("selected"); // Remover selección previa
                    $(this).addClass("selected");  // Añadir clase selected
                    
                    // Obtener el ID del convenio seleccionado
                    var idConvenio = $(this).attr("convenio-id");
                    convenioSeleccionado = $(this);  // Guardar el puesto seleccionado
                });

                // Agregar la opción al select
               divConveniosActivos.append(div);
            });
        },
        error: function(error) {
            console.error("Error al obtener los convenios depurar: " + error);
        }
    });
}

function updateIndicadorConvenio(idConvenio, indicadorDepurar) {
    
    var servicioURL = actualizarIndicadorConvenios + "?idConvenio=" + Number(idConvenio) + "&indicadorDepurar=" + indicadorDepurar;

			$.ajax({
				url: getAbsolutePath() + servicioURL,
				type: "GET",
				success: function(response) {
					console.log("Actualización exitosa.")
				},
				error: function(xhr, status, error) {
					console.error("Error actualizar convenio: " + error);
				}
			});
}

function listaPuestosActivos() {
    var divPuestosActivos = $('#puestosActivos');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerPuestosOcupacionActivos,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar opciones al select
            $.each(res.puestosActivos, function(index, puesto) {
                
                
               	var div = $("<div>")
               	.addClass("puestoA border")
               	.text(puesto.desc_puesto)
               	.attr("puesto-id", puesto.id_puesto);
                
                div.on("click", function() {
                    $(".puestoA").removeClass("selected"); // Remover selección previa
                    $(this).addClass("selected");  // Añadir clase selected
                    
                    // Obtener el ID del convenio seleccionado
                    var idPuesto = $(this).attr("puesto-id");
                    puestoSeleccionado = $(this);  // Guardar el puesto seleccionado
                });

                // Agregar la opción al select
               divPuestosActivos.append(div);
            });
        },
        error: function(error) {
            console.error("Error al obtener los puestos activos: " + error);
        }
    });
}

function listaPuestosDepurar() {
    var divPuestosDepurar = $('#puestosDepurar');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerPuestosOcupacionDepurar,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar 
            $.each(res.puestosDepurar, function(index, puesto) {
                
                var div = $("<div>")
                .addClass("puestoD border")
                .text(puesto.desc_puesto)
                .attr("puesto-id", puesto.id_puesto);
                
                // Añadir evento de clic para seleccionar el convenio
                div.on("click", function() {
                    $(".puestoD").removeClass("selected"); // Remover selección previa
                    $(this).addClass("selected");  // Añadir clase selected
                    
                    var idPuesto = $(this).attr("puesto-id");
                    puestoSeleccionado = $(this); 
                });
                
               divPuestosDepurar.append(div);
            });
        },
        error: function(error) {
            console.error("Error al obtener los puestos depurar: " + error);
        }
    });
}

function updateIndicadorPuesto(idConvenio, indicadorDepurar) {
    
    var servicioURL = actualizarIndicadorPuestos + "?idPuesto=" + Number(idConvenio) + "&indicadorDepurar=" + indicadorDepurar;

			$.ajax({
				url: getAbsolutePath() + servicioURL,
				type: "GET",
				success: function(response) {
					console.log("Actualización exitosa.")
				},
				error: function(xhr, status, error) {
					console.error("Error actualizar puesto: " + error);
				}
			});
}

function listaSucursalesActivos() {
    var divSucursalesActivos = $('#sucursalesActivos');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerSucursalesActivos,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar opciones al select
            $.each(res.sucursalesActivos, function(index, sucursal) {
                
                
               	var div = $("<div>")
               	.addClass("sucursalA border")
               	.text(sucursal.nombre_sucursal)
               	.attr("sucursal-id", sucursal.id_sucursal);
                
                div.on("click", function() {
                    $(".sucursalA").removeClass("selected"); // Remover selección previa
                    $(this).addClass("selected");  // Añadir clase selected
                    
                    // Obtener el ID del convenio seleccionado
                    var idSucursal = $(this).attr("sucursal-id");
                    sucursalSeleccionado = $(this);  // Guardar la sucursal seleccionada
                });

                // Agregar la opción al select
               divSucursalesActivos.append(div);
            });
        },
        error: function(error) {
            console.error("Error al obtener las sucursales activas: " + error);
        }
    });
}

function listaSucursalesDepurar() {
    var divSucursalesDepurar = $('#sucursalesDepurar');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerSucursalesDepurar,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar 
            $.each(res.sucursalesDepurar, function(index, sucursal) {
                
                var div = $("<div>")
                .addClass("sucursalD border")
                .text(sucursal.nombre_sucursal)
                .attr("sucursal-id", sucursal.id_sucursal);
                
                div.on("click", function() {
                    $(".sucursalD").removeClass("selected"); // Remover selección previa
                    $(this).addClass("selected");  // Añadir clase selected
                    
                    var idSucursal = $(this).attr("sucursal-id");
                    sucursalSeleccionado = $(this); 
                });
                
               divSucursalesDepurar.append(div);
            });
        },
        error: function(error) {
            console.error("Error al obtener las sucursales a depurar: " + error);
        }
    });
}

function updateIndicadorSucursal(idSucursal, indicadorDepurar) {
    
    var servicioURL = actualizarIndicadorSucursales + "?idSucursal=" + Number(idSucursal) + "&indicadorDepurar=" + indicadorDepurar;

			$.ajax({
				url: getAbsolutePath() + servicioURL,
				type: "GET",
				success: function(response) {
					console.log("Actualización exitosa.")
				},
				error: function(xhr, status, error) {
					console.error("Error actualizar sucursal: " + error);
				}
			});
}

// Evento para mover el puesto seleccionado de Activos a Depurar
$("#btnAddRightPuesto").on("click", function() {
    if (puestoSeleccionado && puestoSeleccionado.parent().attr("id") === "puestosActivos") {
        var idPuesto = puestoSeleccionado.attr('puesto-id');
        
        $("#puestosDepurar").append(puestoSeleccionado);  // Mover al contenedor de depurar
        updateIndicadorPuesto(idPuesto, 1);
        puestoSeleccionado.removeClass("selected");  // Remover la selección después de moverlo
        puestoSeleccionado = null;  // Limpiar la variable
    }
});

// Evento para mover el puesto seleccionado de Depurar a Activos
$("#btnAddLeftPuesto").on("click", function() {
    if (puestoSeleccionado && puestoSeleccionado.parent().attr("id") === "puestosDepurar") {
        var idPuesto = puestoSeleccionado.attr('puesto-id');
        
        $("#puestosActivos").append(puestoSeleccionado);  // Mover al contenedor de activos
        updateIndicadorPuesto(idPuesto, 0);
        puestoSeleccionado.removeClass("selected");  // Remover la selección después de moverlo
        puestoSeleccionado = null;  // Limpiar la variable
    }
});

// Evento para mover el convenio seleccionado de Activos a Depurar
$("#btnAddRightConvenio").on("click", function() {
    if (convenioSeleccionado && convenioSeleccionado.parent().attr("id") === "conveniosActivos") {
        var idConvenio = convenioSeleccionado.attr('convenio-id');
        
        $("#conveniosDepurar").append(convenioSeleccionado);  // Mover al contenedor de depurar
        updateIndicadorConvenio(idConvenio, 1);
        convenioSeleccionado.removeClass("selected");  // Remover la selección después de moverlo
        convenioSeleccionado = null;  // Limpiar la variable
    }
});

// Evento para mover el convenio seleccionado de Depurar a Activos
$("#btnAddLeftConvenio").on("click", function() {
    if (convenioSeleccionado && convenioSeleccionado.parent().attr("id") === "conveniosDepurar") {
        var idConvenio = convenioSeleccionado.attr('convenio-id');
        
        $("#conveniosActivos").append(convenioSeleccionado);  // Mover al contenedor de activos
         updateIndicadorConvenio(idConvenio, 0);
        convenioSeleccionado.removeClass("selected");  // Remover la selección después de moverlo
        convenioSeleccionado = null;  // Limpiar la variable
    }
});

// Evento para mover la sucursal seleccionada de Activos a Depurar
$("#btnAddRightSucursal").on("click", function() {
    if (sucursalSeleccionado && sucursalSeleccionado.parent().attr("id") === "sucursalesActivos") {
        var idSucursal = sucursalSeleccionado.attr('sucursal-id');
        
        $("#sucursalesDepurar").append(sucursalSeleccionado);  // Mover al contenedor de depurar
        updateIndicadorSucursal(idSucursal, 1);
        sucursalSeleccionado.removeClass("selected");  // Remover la selección después de moverlo
       	sucursalSeleccionado = null;  // Limpiar la variable
    }
});

// Evento para mover la sucursal seleccionada de Depurar a Activos
$("#btnAddLeftSucursal").on("click", function() {
    if (sucursalSeleccionado && sucursalSeleccionado.parent().attr("id") === "sucursalesDepurar") {
        var idSucursal = sucursalSeleccionado.attr('sucursal-id');
        
        $("#sucursalesActivos").append(sucursalSeleccionado);  // Mover al contenedor de activos
        updateIndicadorSucursal(idSucursal, 0);
        sucursalSeleccionado.removeClass("selected");  // Remover la selección después de moverlo
        sucursalSeleccionado = null;  // Limpiar la variable
    }
});


$(document).ready(function() {
    listaEstados();
    listaConveniosActivos();
    listaConveniosDepurar();
    listaPuestosActivos();
    listaPuestosDepurar();
    listaSucursalesActivos();
    listaConveniosDepurar();
    
     // Escuchar el evento de cambio en el select de estados
    $('#opcionesEstado').change(function() {
        var estadoId = $(this).val(); // Obtener el ID del estado seleccionado

        if (estadoId) {
            listaMunicipios(estadoId); // Cargar los municipios basados en el estado seleccionado
        } else {
            // Si no hay estado seleccionado, limpiar el select de municipios
            $('#opcionesMunicipio').empty().append('<option value="">Seleccionar municipio</option>');
        }
    });
});
