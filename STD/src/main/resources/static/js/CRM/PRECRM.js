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
// CAMBIAR ESTATUS DEL SOSPECHOSO
// =========================================================

function abrirCambioEstatus(boton) {

    const pkSospechoso =
        boton.getAttribute("data-id");

    const folio =
        boton.getAttribute("data-folio");

    const nombre =
        boton.getAttribute("data-nombre");

    const estatusActual =
        boton.getAttribute("data-estatus");


    Swal.fire({

        icon: "question",

        title: "Actualizar estatus",

        html:
            "<div class='text-left'>" +

                "<p class='mb-1'>" +
                    "<strong>Folio:</strong> " +
                    folio +
                "</p>" +

                "<p class='mb-3'>" +
                    "<strong>Sospechoso:</strong> " +
                    nombre +
                "</p>" +

                "<p class='mb-1'>" +
                    "<strong>Estatus actual:</strong> " +
                    estatusActual +
                "</p>" +

            "</div>",

        input: "select",

        inputOptions: {

            "REGISTRADO":
                "Registrado",

            "SEGUIMIENTO":
                "Seguimiento",

            "CITA_AGENDADA":
                "Cita agendada",

            "COTIZACION":
                "Cotización",

            "SOLICITUD":
                "Solicitud",

            "CONCLUIDO":
                "Concluido"

        },

        inputValue:
            estatusActual,

        inputPlaceholder:
            "Seleccione un estatus",

        showCancelButton:
            true,

        confirmButtonText:
            "Guardar cambio",

        cancelButtonText:
            "Cancelar",

        inputValidator: (value) => {

            if (!value) {

                return "Debe seleccionar un estatus.";

            }

        }

    }).then((result) => {

        if (result.isConfirmed) {

            document
                .getElementById(
                    "pkSospechosoCambio"
                )
                .value =
                pkSospechoso;


            document
                .getElementById(
                    "estatusCambio"
                )
                .value =
                result.value;


            document
                .getElementById(
                    "formCambioEstatus"
                )
                .submit();

        }

    });

}