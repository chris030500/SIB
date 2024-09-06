var obtenerEstados = "exclusiones/getEstados"; 
var obtenerMunicipios = "exclusiones/getMunicipios";
var actualizarrMunicipioConCobertura = "exclusiones/agregarMunicipioConCobertura";
var obtenerConveniosActivos = "exclusiones/getConveniosNominaActivos";
var obtenerConveniosDepurar = "exclusiones/getConveniosNominaDepurar";

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
        // Llamar a la función para actualizar, por ejemplo usando Fetch API
        agregarMunicipioConCobertura(idEstado, idMunicipio);
    } else {
        alert('Por favor, seleccione un estado y un municipio.');
    }
});


function agregarMunicipioConCobertura(idEstado, idMunicipio) {
    
    const updatedData = {
        i_indicador_depurar: 1 
    };
    
    var url = actualizarrMunicipioConCobertura + `/${idEstado}/${idMunicipio}`;

    $.ajax({
        url: getAbsolutePath() + url,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedData),
        success: function(res) {
            console.log('Actualización exitosa:', res);
        },
        error: function(error) {
            console.error('Error en la actualización:', error);
        }
    });
}

function listaConveniosActivos() {
    var divConveniosActivos = $('#conveniosActivos');

    // Hacer la solicitud AJAX para obtener los estados
    $.ajax({
        url: getAbsolutePath() + obtenerConveniosActivos,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            // Iterar sobre los datos recibidos y agregar opciones al select
            $.each(res.conveniosActivos, function(index, convenio) {
                
                var convenios = "<p>" + convenio.desc_convenio + "</p>";

                // Agregar la opción al select
               divConveniosActivos.append(convenios);
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
                
                var convenios = "<p>" + convenio.desc_convenio + "</p>";

                // Agregar la opción al select
               divConveniosActivos.append(convenios);
            });
        },
        error: function(error) {
            console.error("Error al obtener los convenios depurar: " + error);
        }
    });
}


$(document).ready(function() {
    listaEstados();
    listaConveniosActivos();
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
