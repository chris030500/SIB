// @ts-nocheck

var obtenerEstados = "exclusiones/getEstados"; 
var obtenerMunicipios = "exclusiones/getMunicipios";
var actualizarIndicadorMunicipios = "exclusiones/actualizarIndicadorMunicipios";
var obtenerConvenios = "exclusiones/getConveniosNomina";
var obtenerConveniosActivos = "exclusiones/getConveniosNominaActivos?descConvenio=";
var obtenerConveniosDepurar = "exclusiones/getConveniosNominaDepurar?descConvenio=";
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
    var selectEstados = $('#opcionesEstado');
    selectEstados.empty();
    selectEstados.append('<option value="">Seleccionar estado</option>');

    $.ajax({
        url: getAbsolutePath() + obtenerEstados,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            $.each(res.estados, function(index, estado) {
                var option = $('<option>', {
                    value: estado.id_estado,
                    text: estado.nombre_estado
                });
                selectEstados.append(option);
            });
        },
        error: function(error) {
            console.error("Error al obtener los estados: " + error);
        }
    });
}

function listaMunicipios(estadoId) {
    var selectMunicipios = $('#opcionesMunicipio');
    selectMunicipios.empty();
    selectMunicipios.append('<option value="">Seleccionar municipio</option>');

    $.ajax({
        url: getAbsolutePath() + obtenerMunicipios,
        type: "GET",
        dataType: "JSON",
        data: {
            idEstado: estadoId
        },
        success: function(res) {
            $.each(res.municipios, function(index, municipio) {
                var option = $('<option>', {
                    value: municipio.id_municipio,
                    text: municipio.nombre_municipio
                });
                selectMunicipios.append(option);
            });
        },
        error: function(error) {
            console.error("Error al obtener los municipios: " + error);
        }
    });
}

document.getElementById('btnAgregar').addEventListener('click', function() {
    const idEstado = document.getElementById('opcionesEstado').value;
    const idMunicipio = document.getElementById('opcionesMunicipio').value;

    if (idEstado && idMunicipio) {
        updateMunicipios(idEstado, idMunicipio, 0);
    } else {
        Swal.fire({
            title: '¡Alerta!',
            text: 'Por favor, seleccione un estado y un municipio.',
            icon: 'warning',
            confirmButtonText: 'Aceptar'
        });
    }
});

function updateMunicipios(idEstado, idMunicipio, indicadorDepurar) {
    var servicioURL = actualizarIndicadorMunicipios + "?idEstado=" + Number(idEstado) + "&idMunicipio=" + Number(idMunicipio) + "&indicadorDepurar=" + indicadorDepurar;

    $.ajax({
        url: getAbsolutePath() + servicioURL,
        type: "GET",
        success: function(response) {
            var mensaje = response.municipio.i_indicador_depurar === 0 ? 
                `Se ha guardado el municipio de ${response.municipio.vc_nombre} correctamente.` : 
                `Se ha deshabilitado el municipio de ${response.municipio.vc_nombre} exitosamente.`;

            if (response.municipio.i_indicador_depurar === 0) {
                showSwal('¡Guardado exitoso!', mensaje, 'success');  
            }

            if (response.municipio.i_indicador_depurar === 1) {
                showSwal('¡Eliminado exitoso!', mensaje, 'success');
            }

            $('#opcionesMunicipio').empty();
            listaEstados();
            listaMunicipiosConCobertura();
        },
        error: function(xhr, status, error) {
            showSwal('Error!', 'Hubo un problema al guardar los datos.', 'error');
        }
    });
}

function search_convenioActivo(cadena) {
    var divConveniosActivos = $('#conveniosActivos');
    divConveniosActivos.empty();

    $.ajax({
        url: getAbsolutePath() + obtenerConveniosActivos + cadena,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            var convenios = res.conveniosActivos;

            for (var i = 0; i < convenios.length; i++) {
                var div = $("<div>")
                    .addClass("convenioA border")
                    .text(convenios[i].desc_convenio)
                    .attr("convenio-id", convenios[i].id_convenio);

                div.on("click", function() {
                    $(".convenioA").removeClass("selected border-primary");
                    $(this).addClass("selected border-primary");
                    convenioSeleccionado = $(this);
                });

                divConveniosActivos.append(div);
            }
        }
    });
}

function search_convenioDepurar(cadena) {
    var divConveniosDepurar = $('#conveniosDepurar');
    divConveniosDepurar.empty();

    $.ajax({
        url: getAbsolutePath() + obtenerConveniosDepurar + cadena,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            var convenios = res.conveniosDepurar;

            for (var i = 0; i < convenios.length; i++) {
                var div = $("<div>")
                    .addClass("convenioA border")
                    .text(convenios[i].desc_convenio)
                    .attr("convenio-id", convenios[i].id_convenio);

                div.on("click", function() {
                    $(".convenioA").removeClass("selected border-primary");
                    $(this).addClass("selected border-primary");
                    convenioSeleccionado = $(this);
                });

                divConveniosDepurar.append(div);
            }
        }
    });
}

function limpiarDivConvenio() {
    $("#conveniosDepurar").empty();
    $("#conveniosActivos").empty();
}

function updateIndicadorConvenio(idConvenio, indicadorDepurar) {
    var servicioURL = actualizarIndicadorConvenios + "?idConvenio=" + Number(idConvenio) + "&indicadorDepurar=" + indicadorDepurar;

    $.ajax({
        url: getAbsolutePath() + servicioURL,
        type: "GET",
        success: function(response) {
            var mensaje = indicadorDepurar === 1 ?
                `Se ha guardado el convenio ${response.convenio.vc_descripcion} correctamente en la lista de Convenios a Excluir.` : 
                `Se ha guardado el convenio ${response.convenio.vc_descripcion} correctamente en la lista de Convenios Activos.`;

            showSwal('¡Guardado exitoso!', mensaje, 'success');

            setTimeout(function() { 
                limpiarDivConvenio();
                search_convenioDepurar("");
                search_convenioActivo("");
            }, 2000);
        },
        error: function(xhr, status, error) {
            var mensaje = indicadorDepurar === 1 ? 
                `Ha ocurrido un error al guardar el convenio en la lista de Convenios a Excluir.` :
                `Ha ocurrido un error al guardar el convenio en la lista de Convenios Activos.`;

            showSwal('¡Error!', mensaje, 'error');
        }
    });
}

function showSwal(titulo, mensaje, tipo) {
    Swal.fire({
        title: titulo,
        text: mensaje,
        icon: tipo,
        timer: 2000,
        showConfirmButton: false
    });
}

function listaPuestosActivos() {
    var divPuestosActivos = $('#puestosActivos');

    $.ajax({
        url: getAbsolutePath() + obtenerPuestosOcupacionActivos,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            $.each(res.puestosActivos, function(index, puesto) {
                var div = $("<div>")
                    .addClass("puestoA border")
                    .text(puesto.desc_puesto)
                    .attr("puesto-id", puesto.id_puesto);

                div.on("click", function() {
                    $(".puestoA").removeClass("selected border-primary");
                    $(this).addClass("selected border-primary");
                    puestoSeleccionado = $(this);
                });

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

    $.ajax({
        url: getAbsolutePath() + obtenerPuestosOcupacionDepurar,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            $.each(res.puestosDepurar, function(index, puesto) {
                var div = $("<div>")
                    .addClass("puestoD border")
                    .text(puesto.desc_puesto)
                    .attr("puesto-id", puesto.id_puesto);

                div.on("click", function() {
                    $(".puestoD").removeClass("selected border-primary");
                    $(this).addClass("selected border-primary");
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

function limpiarDivPuestos() {
    $("#puestosDepurar").empty();
    $("#puestosActivos").empty();
}

function updateIndicadorPuesto(idConvenio, indicadorDepurar) {
    var servicioURL = actualizarIndicadorPuestos + "?idPuesto=" + Number(idConvenio) + "&indicadorDepurar=" + indicadorDepurar;

    $.ajax({
        url: getAbsolutePath() + servicioURL,
        type: "GET",
        success: function(response) {
            var mensaje = indicadorDepurar === 1 ? 
                `Se ha guardado el puesto ${response.puesto.vc_descripcion} correctamente en la lista de Puestos a Excluir.` : 
                `Se ha guardado el puesto ${response.puesto.vc_descripcion} correctamente en la lista de Puestos Activos.`;

            showSwal('¡Guardado exitoso!', mensaje, 'success');

            setTimeout(function() {
                limpiarDivPuestos();
                listaPuestosActivos();
                listaPuestosDepurar();
            }, 2000);

        },
        error: function(xhr, status, error) {
            var mensaje = indicadorDepurar === 1 ?
                `Ha ocurrido un error al guardar el convenio en la lista de Puestos a Excluir.` :
                `Ha ocurrido un error al guardar el convenio en la lista de Puestos Activos.`;

            showSwal('¡Error!', mensaje, 'error');
        }
    });
}

function listaSucursalesActivos() {
    var divSucursalesActivos = $('#sucursalesActivos');

    $.ajax({
        url: getAbsolutePath() + obtenerSucursalesActivos,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            $.each(res.sucursalesActivos, function(index, sucursal) {
                var div = $("<div>")
                    .addClass("sucursalA border")
                    .text(sucursal.nombre_sucursal)
                    .attr("sucursal-id", sucursal.id_sucursal);

                div.on("click", function() {
                    $(".sucursalA").removeClass("selected border-primary");
                    $(this).addClass("selected border-primary");
                    sucursalSeleccionado = $(this);
                });

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

    $.ajax({
        url: getAbsolutePath() + obtenerSucursalesDepurar,
        type: "GET",
        dataType: "JSON",
        success: function(res) {
            $.each(res.sucursalesDepurar, function(index, sucursal) {
                var div = $("<div>")
                    .addClass("sucursalD border")
                    .text(sucursal.nombre_sucursal)
                    .attr("sucursal-id", sucursal.id_sucursal);

                div.on("click", function() {
                    $(".sucursalD").removeClass("selected border-primary");
                    $(this).addClass("selected border-primary");
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

function limpiarDivSucursales() {
    $("#sucursalesDepurar").empty();
    $("#sucursalesActivos").empty();
}

function updateIndicadorSucursal(idSucursal, indicadorDepurar) {
    var servicioURL = actualizarIndicadorSucursales + "?idSucursal=" + Number(idSucursal) + "&indicadorDepurar=" + indicadorDepurar;

    $.ajax({
        url: getAbsolutePath() + servicioURL,
        type: "GET",
        success: function(response) {
            var mensaje = indicadorDepurar === 1 ? 
                `Se ha guardado la sucursal ${response.sucursal.vc_nombre} correctamente en la lista de Sucursales a Excluir.` :
                `Se ha guardado la sucursal ${response.sucursal.vc_nombre} correctamente en la lista de Sucursales Activas.`;

            showSwal('¡Guardado exitoso!', mensaje, 'success');
            setTimeout(function() {
                limpiarDivSucursales();
                listaSucursalesActivos();
                listaSucursalesDepurar();
            }, 2000);
        },
        error: function(xhr, status, error) {
            var mensaje = indicadorDepurar === 1 ?
                `Ha ocurrido un error al guardar el convenio en la lista de Sucursales a Excluir.` :
                `Ha ocurrido un error al guardar el convenio en la lista de Sucursales Activas.`;

            showSwal('¡Error!', mensaje, 'error');
        }
    });
}

function swalValidarSeleccion(seccion) {
    Swal.fire({
        title: '¡Alerta!',
        text: "Debe de seleccionar un " + seccion,
        icon: 'error',
        timer: 2000,
        showConfirmButton: false
    });
}

$("#btnAddRightConvenio").on("click", function() {
    if (convenioSeleccionado && convenioSeleccionado.parent().attr("id") === "conveniosActivos") {
        var idConvenio = convenioSeleccionado.attr('convenio-id');
        updateIndicadorConvenio(idConvenio, 1);
        convenioSeleccionado.removeClass("selected border-primary");
        convenioSeleccionado = null;
    } else {
        swalValidarSeleccion("convenio.");
    }
});

$("#btnAddLeftConvenio").on("click", function() {
    if (convenioSeleccionado && convenioSeleccionado.parent().attr("id") === "conveniosDepurar") {
        var idConvenio = convenioSeleccionado.attr('convenio-id');
        updateIndicadorConvenio(idConvenio, 0);
        convenioSeleccionado.removeClass("selected border-primary");
        convenioSeleccionado = null;
    } else {
        swalValidarSeleccion("convenio.");
    }
});

$("#btnAddRightPuesto").on("click", function() {
    if (puestoSeleccionado && puestoSeleccionado.parent().attr("id") === "puestosActivos") {
        var idPuesto = puestoSeleccionado.attr('puesto-id');
        updateIndicadorPuesto(idPuesto, 1);
        puestoSeleccionado.removeClass("selected border-primary");
        puestoSeleccionado = null;
    } else {
        swalValidarSeleccion("puesto.");
    }
});

$("#btnAddLeftPuesto").on("click", function() {
    if (puestoSeleccionado && puestoSeleccionado.parent().attr("id") === "puestosDepurar") {
        var idPuesto = puestoSeleccionado.attr('puesto-id');
        updateIndicadorPuesto(idPuesto, 0);
        puestoSeleccionado.removeClass("selected border-primary");
        puestoSeleccionado = null;
    } else {
        swalValidarSeleccion("puesto.");
    }
});

$("#btnAddRightSucursal").on("click", function() {
    if (sucursalSeleccionado && sucursalSeleccionado.parent().attr("id") === "sucursalesActivos") {
        var idSucursal = sucursalSeleccionado.attr('sucursal-id');
        updateIndicadorSucursal(idSucursal, 1);
        sucursalSeleccionado.removeClass("selected border-primary");
        sucursalSeleccionado = null;
    } else {
        swalValidarSeleccion("sucursal.");
    }
});

$("#btnAddLeftSucursal").on("click", function() {
    if (sucursalSeleccionado && sucursalSeleccionado.parent().attr("id") === "sucursalesDepurar") {
        var idSucursal = sucursalSeleccionado.attr('sucursal-id');
        updateIndicadorSucursal(idSucursal, 0);
        sucursalSeleccionado.removeClass("selected border-primary");
        sucursalSeleccionado = null;
    } else {
        swalValidarSeleccion("sucursal.");
    }
});

$(document).ready(function() {
    listaEstados();
    search_convenioActivo("");
    search_convenioDepurar("");
    listaPuestosActivos();
    listaPuestosDepurar();
    listaSucursalesActivos();
    listaSucursalesDepurar();

    $('#opcionesEstado').change(function() {
        var estadoId = $(this).val();

        if (estadoId) {
            listaMunicipios(estadoId);
        } else {
            $('#opcionesMunicipio').empty().append('<option value="">Seleccionar municipio</option>');
        }
    });
});