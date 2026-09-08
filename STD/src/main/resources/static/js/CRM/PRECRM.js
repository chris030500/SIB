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
        + "/precrm/detalle/"
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