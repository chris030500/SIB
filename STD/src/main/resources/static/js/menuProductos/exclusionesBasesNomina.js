var obtenerEstados = "exclusiones/getEstados"; 
var obtenerMunicipios = "exclusiones/getMunicipios";

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


$(document).ready(function() {
    listaEstados();
    
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
