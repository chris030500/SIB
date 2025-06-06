	/* $(document).ready(function () {
    $("#btnSubirArchivo").click(function () {
        let archivo = $("#archivoPEP").prop('files')[0];
        if (archivo) {
            let formData = new FormData();
            formData.append("archivoPEP", archivo);

            $.ajax({
                url: '/ruta/del/servidor/para/subir/archivo',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function (response) {
                    $("#mensajePEP").html('Archivo subido correctamente: ' + archivo.name)
                        .removeClass('alert-danger d-none').addClass('alert-success');
                    cargarArchivosHistorial();
                },
                error: function () {
                    $("#mensajePEP").html('Error al subir el archivo')
                        .removeClass('alert-success d-none').addClass('alert-danger');
                }
            });
        } else {
            $("#mensajePEP").html('No se seleccionó ningún archivo')
                .removeClass('alert-success d-none').addClass('alert-danger');
        }
    });

    function cargarArchivosHistorial() {
        $.ajax({
            url: '/ruta/del/servidor/para/obtener/historial',
            type: 'GET',
            success: function (data) {
                let listaArchivos = $("#listaArchivosPEP");
                listaArchivos.empty();
                data.archivos.forEach(function (archivo) {
                    listaArchivos.append('<li class="list-group-item">' + archivo.nombre + '</li>');
                });
            }
        });
    }

    $("#btnProcesar").click(function () {
        let fechaInicio = String($("#fechaInicio").val());
let fechaFin = String($("#fechaFin").val());

if (new Date(fechaInicio) > new Date(fechaFin)) {
    alert("La fecha de inicio no puede ser mayor a la fecha final");
    return;
}

        $.ajax({
            url: '/ruta/del/servidor/para/procesar/reporte',
            type: 'POST',
            data: {
                fechaInicio: fechaInicio,
                fechaFin: fechaFin
            },
            success: function (response) {
                $("#btnDescargar").prop("disabled", false);
                alert("Reporte procesado correctamente");
            },
            error: function () {
                alert("Hubo un error al procesar el reporte");
            }
        });
    });

    $("#btnDescargar").click(function () {
        window.location.href = '/ruta/del/servidor/para/descargar/reporte';
    });
});*/