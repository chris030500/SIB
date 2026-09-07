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