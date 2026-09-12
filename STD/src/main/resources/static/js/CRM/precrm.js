// @ts-nocheck
function abrirSelectorPRECRM() {

    const input =
        document.getElementById("fileUploadPRECRM");

    input.value = "";

    input.click();
}


function subirArchivoPRECRM(input) {

    if (!input.files ||
        input.files.length === 0) {

        return;
    }


    const archivo =
        input.files[0];


    const nombre =
        archivo.name.toUpperCase();


    const patron =
        /^PRECRM_\d{8}\.XLSX$/;


    // =========================================================
    // VALIDAR NOMBRE DEL ARCHIVO
    // =========================================================

    if (!patron.test(nombre)) {

        Swal.fire({
            icon: "warning",
            title: "Archivo no válido",
            text: "El archivo debe tener el formato PRECRM_DDMMYYYY.xlsx"
        });


        input.value = "";

        return;
    }


    // =========================================================
    // CONFIRMAR CARGA
    // =========================================================

    Swal.fire({

        icon: "question",

        title: "Procesar archivo",

        text:
            "¿Deseas cargar el archivo "
            + archivo.name
            + "?",

        showCancelButton: true,

        confirmButtonText: "Sí, cargar",

        cancelButtonText: "Cancelar"

    }).then((result) => {

        if (result.isConfirmed) {

            document.getElementById(
                "formUploadPRECRM"
            ).submit();

        } else {

            input.value = "";
        }

    });
}


// =========================================================
// CONFIRMAR AVANCE DE ESTATUS DESDE DETALLE
// =========================================================

function confirmarAvanceEstatusPRECRM(boton) {


    const pkSospechoso =
        boton.getAttribute(
            "data-id"
        );


    const nombre =
        boton.getAttribute(
            "data-nombre"
        );


    const estatusActual =
        boton.getAttribute(
            "data-estatus-actual"
        );


    const siguienteEstatus =
        boton.getAttribute(
            "data-siguiente-estatus"
        );


    Swal.fire({

        icon: "question",

        title: "Avanzar de etapa",

        html:

            "<div class='text-left'>" +

                "<p>" +
                    "<strong>Sospechoso:</strong> "
                    + nombre +
                "</p>" +

                "<p>" +
                    "<strong>Estatus actual:</strong> "
                    + obtenerNombreEstatusPRECRM(
                        estatusActual
                    ) +
                "</p>" +

                "<p>" +
                    "<strong>Nuevo estatus:</strong> " +
                    "<span class='text-success'>" +
                        obtenerNombreEstatusPRECRM(
                            siguienteEstatus
                        ) +
                    "</span>" +
                "</p>" +

            "</div>",

        showCancelButton:
            true,

        confirmButtonText:
            "Sí, avanzar",

        cancelButtonText:
            "Cancelar"

    }).then((result) => {


        if (result.isConfirmed) {


            document
                .getElementById(
                    "pkSospechosoDetalle"
                )
                .value =
                pkSospechoso;


            document
                .getElementById(
                    "nuevoEstatusDetalle"
                )
                .value =
                siguienteEstatus;


            document
                .getElementById(
                    "formAvanzarEstatusDetalle"
                )
                .submit();

        }

    });

}



// =========================================================
// OBTENER SIGUIENTE ESTATUS PRE CRM
// =========================================================

function obtenerSiguienteEstatusPRECRM(
        estatusActual) {


    switch (estatusActual) {

        case "REGISTRADO":

            return "SEGUIMIENTO";


        case "SEGUIMIENTO":

            return "CITA_AGENDADA";


        case "CITA_AGENDADA":

            return "COTIZACION";


        case "COTIZACION":

            return "SOLICITUD";


        case "SOLICITUD":

            return "CONCLUIDO";


        default:

            return null;

    }

}



// =========================================================
// NOMBRE PARA MOSTRAR
// =========================================================

function obtenerNombreEstatusPRECRM(
        estatus) {


    switch (estatus) {

        case "REGISTRADO":

            return "Registrado";


        case "SEGUIMIENTO":

            return "Seguimiento";


        case "CITA_AGENDADA":

            return "Cita agendada";


        case "COTIZACION":

            return "Cotización";


        case "SOLICITUD":

            return "Solicitud";


        case "CONCLUIDO":

            return "Concluido";


        default:

            return estatus;

    }

}

// =========================================================
// ABRIR DETALLE DEL SOSPECHOSO
// =========================================================

function abrirDetalleSospechoso() {

    const selector =
        document.getElementById(
            "selectorSospechoso"
        );


    if (!selector ||
        !selector.value) {

        Swal.fire({

            icon: "warning",

            title: "Selecciona un sospechoso",

            text:
                "Debes seleccionar un sospechoso "
                + "para consultar su información."

        });

        return;
    }


	window.location.href =
	    obtenerContextPathPRECRM()
	    + "/precrm/detallesospechosos/"
	    + selector.value;
}



// =========================================================
// CONTEXT PATH
// =========================================================

function obtenerContextPathPRECRM() {

    const path =
        window.location.pathname;


    if (path.startsWith("/STD/")) {

        return "/STD";

    }


    return "";
}


// =========================================================
// CONFIRMAR REGISTRO DE TELEFONO ALTERNO
// =========================================================

function confirmarTelefonoAlternoPRECRM(boton) {

    const pkSospechoso =
        boton.getAttribute("data-id");

    const folio =
        boton.getAttribute("data-folio");

    const nombre =
        boton.getAttribute("data-nombre");

    const telefonoOriginal =
        boton.getAttribute("data-telefono");

    const sucursal =
        boton.getAttribute("data-sucursal");

    const telefonoAlterno =
        boton.getAttribute("data-telefono-alterno");


    Swal.fire({

        icon: "warning",

        title: "Agregar teléfono alterno",

        text:
            "El teléfono alterno deberá registrarse únicamente "
            + "cuando el número proporcionado originalmente sea incorrecto. "
            + "¿Desea agregar un teléfono alterno?",

        showCancelButton: true,

        confirmButtonText: "Continuar",

        cancelButtonText: "Cancelar"

    }).then((result) => {

        if (!result.isConfirmed) {
            return;
        }


        document.getElementById(
            "pkSospechosoTelefonoAlterno"
        ).value = pkSospechoso;


        document.getElementById(
            "folioTelefonoAlterno"
        ).textContent = folio || "-";


        document.getElementById(
            "nombreTelefonoAlterno"
        ).textContent = nombre || "-";


        document.getElementById(
            "telefonoOriginalAlterno"
        ).textContent = telefonoOriginal || "-";


        document.getElementById(
            "sucursalTelefonoAlterno"
        ).textContent = sucursal || "-";


        document.getElementById(
            "nuevoTelefonoAlterno"
        ).value =
            telefonoAlterno &&
            telefonoAlterno !== "null"
                ? telefonoAlterno
                : "";


        const modal =
            new bootstrap.Modal(
                document.getElementById(
                    "modalTelefonoAlternoPRECRM"
                )
            );

        modal.show();

    });

}

// =========================================================
// DATATABLE
// SEGUIMIENTO DE SOSPECHOSOS PRE CRM
// =========================================================

var tablaSeguimientoPRECRM = null;


// =========================================================
// NORMALIZAR TEXTO
// Permite que "orduno" encuentre "Orduño"
// =========================================================

function normalizarFiltroPRECRM(valor) {

    return (valor || "")
        .toString()
        .trim()
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
}


// =========================================================
// FILTRO PERSONALIZADO DATATABLE
// =========================================================

if ($.fn.dataTable) {

    $.fn.dataTable.ext.search.push(

        function(settings, data, dataIndex) {


            // =================================================
            // SOLO APLICA A NUESTRA TABLA PRE CRM
            // =================================================

            if (
                !settings.nTable
                || settings.nTable.id !== "tablaSeguimientoPRECRM"
            ) {

                return true;

            }


            // =================================================
            // VALORES CAPTURADOS EN FILTROS
            // =================================================

            var filtroFolio =
                normalizarFiltroPRECRM(
                    $("#filtroFolio").val()
                );


            var filtroNombre =
                normalizarFiltroPRECRM(
                    $("#filtroNombre").val()
                );


            var filtroTelefono =
                normalizarFiltroPRECRM(
                    $("#filtroTelefono").val()
                );


            var filtroClasificacion =
                normalizarFiltroPRECRM(
                    $("#filtroClasificacion").val()
                );


            var filtroEstatus =
                normalizarFiltroPRECRM(
                    $("#filtroEstatus").val()
                );


            var filtroFechaRegistro =
                $("#filtroFechaRegistro").val() || "";


            // =================================================
            // DATOS DE LA FILA
            // =================================================

            var folio =
                normalizarFiltroPRECRM(
                    data[0]
                );


            var nombre =
                normalizarFiltroPRECRM(
                    data[1]
                );


            var telefono =
                normalizarFiltroPRECRM(
                    data[2]
                );


            var clasificacion =
                normalizarFiltroPRECRM(
                    data[3]
                );


            var estatus =
                normalizarFiltroPRECRM(
                    data[4]
                );


            // =================================================
            // FECHA REGISTRO
            // SE OBTIENE DEL data-fecha-registro DEL TR
            // =================================================

            var fila =
                settings
                    .aoData[dataIndex]
                    .nTr;


            var fechaRegistro =
                fila
                ? (
                    fila.getAttribute(
                        "data-fecha-registro"
                    ) || ""
                  )
                : "";


            // =================================================
            // VALIDACIONES
            // =================================================

            var coincideFolio =
                filtroFolio === ""
                || folio.includes(
                    filtroFolio
                );


            var coincideNombre =
                filtroNombre === ""
                || nombre.includes(
                    filtroNombre
                );


            var coincideTelefono =
                filtroTelefono === ""
                || telefono.includes(
                    filtroTelefono
                );


            var coincideClasificacion =
                filtroClasificacion === ""
                || clasificacion ===
                   filtroClasificacion;


            var coincideEstatus =
                filtroEstatus === ""
                || estatus ===
                   filtroEstatus;


            var coincideFechaRegistro =
                filtroFechaRegistro === ""
                || fechaRegistro ===
                   filtroFechaRegistro;


            // =================================================
            // RESULTADO
            // =================================================

            return (
                coincideFolio
                && coincideNombre
                && coincideTelefono
                && coincideClasificacion
                && coincideEstatus
                && coincideFechaRegistro
            );

        }

    );

}


// =========================================================
// INICIALIZAR DATATABLE
// =========================================================

$(document).ready(function() {


    // =====================================================
    // VALIDAR QUE ESTEMOS EN LA PANTALLA DE SEGUIMIENTO
    // =====================================================

    if (
        $("#tablaSeguimientoPRECRM").length === 0
    ) {

        return;

    }


    // =====================================================
    // INICIALIZAR DATATABLE
    // =====================================================

    tablaSeguimientoPRECRM =
        $("#tablaSeguimientoPRECRM")
        .DataTable({

            // =============================================
            // PAGINACION
            // =============================================

            pageLength: 10,


            lengthMenu: [

                [10, 25, 50, -1],

                [10, 25, 50, "Todos"]

            ],


            // =============================================
            // DATATABLE PUEDE BUSCAR
            // PERO OCULTAMOS SU CAJA "BUSCAR"
            // =============================================

            searching: true,


            // =============================================
            // ORDENAMIENTO
            // =============================================

            ordering: true,


            // =============================================
            // PAGINACION
            // =============================================

            paging: true,


            // =============================================
            // INFORMACION:
            // "Mostrando 1 a 10..."
            // =============================================

            info: true,


            autoWidth: false,


            // =============================================
            // MUY IMPORTANTE
            //
            // l = Mostrar registros
            // r = Procesando
            // t = Tabla
            // i = Información
            // p = Paginación
            //
            // NO PONEMOS "f"
            // por eso NO aparece el Buscar de DataTables
            // =============================================

            dom: "lrtip",


            // =============================================
            // IDIOMA
            // =============================================

            language: {

                lengthMenu:
                    "Mostrar _MENU_ registros",

                zeroRecords:
                    "No se encontraron registros",

                info:
                    "Mostrando _START_ a _END_ de _TOTAL_ registros",

                infoEmpty:
                    "Mostrando 0 a 0 de 0 registros",

                infoFiltered:
                    "(filtrado de _MAX_ registros totales)",

                emptyTable:
                    "No hay registros disponibles",

                loadingRecords:
                    "Cargando...",

                processing:
                    "Procesando...",

                paginate: {

                    first:
                        "Primero",

                    last:
                        "Último",

                    next:
                        "Siguiente",

                    previous:
                        "Anterior"

                }

            }

        });


    // =====================================================
    // EVITAR SUBMIT DEL FORMULARIO
    // YA NO NECESITAMOS BOTON BUSCAR
    // =====================================================

    $("#formFiltrosPRECRM")
        .on(
            "submit",
            function(event) {

                event.preventDefault();

                return false;

            }
        );


    // =====================================================
    // FILTROS DE TEXTO
    // FILTRAN CONFORME SE ESCRIBE
    // =====================================================

    $(
        "#filtroFolio,"
        + "#filtroNombre,"
        + "#filtroTelefono"
    )
    .on(
        "input",
        function() {

            tablaSeguimientoPRECRM
                .draw();

        }
    );


    // =====================================================
    // SELECTS
    // =====================================================

    $(
        "#filtroClasificacion,"
        + "#filtroEstatus"
    )
    .on(
        "change",
        function() {

            tablaSeguimientoPRECRM
                .draw();

        }
    );


    // =====================================================
    // FECHA
    // =====================================================

    $("#filtroFechaRegistro")
        .on(
            "change",
            function() {

                tablaSeguimientoPRECRM
                    .draw();

            }
        );


});