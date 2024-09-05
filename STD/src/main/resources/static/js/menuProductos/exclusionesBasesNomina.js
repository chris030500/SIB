var obtenerEstados = "exclusiones/getEstados"; // Ruta de tu API para obtener los estados

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

// Llamar a la función listaEstados() cuando se cargue la página
$(document).ready(function() {
    listaEstados();
});
