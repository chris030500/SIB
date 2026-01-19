// @ts-nocheck
var tblRegistroEmpresas;
var tablaSolicitudes;

$(document).ready(function() {
    tblRegistroEmpresas = $("#tblRegistroEmpresas").DataTable({
    language: {
        processing:     "Procesando...",
        search:         "Buscar:",
        lengthMenu:     "Mostrar _MENU_ registros",
        info:           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
        infoEmpty:      "Mostrando registros del 0 al 0 de un total de 0",
        infoFiltered:   "(filtrado de un total de _MAX_ registros)",
        infoPostFix:    "",
        loadingRecords: "Cargando...",
        zeroRecords:    "No se encontraron resultados",
        emptyTable:     "Ningún dato disponible en esta tabla",
        paginate: {
            first:      "Primero",
            previous:   "Anterior",
            next:       "Siguiente",
            last:       "Último"
        },
        aria: {
            sortAscending:  ": Activar para ordenar la columna de manera ascendente",
            sortDescending: ": Activar para ordenar la columna de manera descendente"
        }
    },
    order: [[0, "desc"]], // Opcional: ordenar por la primera columna en orden descendente
    aoColumnDefs: [{
        bSortable: false,
        aTargets: [-1] // La última columna no es ordenable
    }]
});
    
   tablaSolicitudes = $("#tablaSolicitudes").DataTable({
    language: {
        processing:     "Procesando...",
        search:         "Buscar:",
        lengthMenu:     "Mostrar _MENU_ registros",
        info:           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
        infoEmpty:      "Mostrando registros del 0 al 0 de un total de 0",
        infoFiltered:   "(filtrado de un total de _MAX_ registros)",
        infoPostFix:    "",
        loadingRecords: "Cargando...",
        zeroRecords:    "No se encontraron resultados",
        emptyTable:     "Ningún dato disponible en esta tabla",
        paginate: {
            first:      "Primero",
            previous:   "Anterior",
            next:       "Siguiente",
            last:       "Último"
        },
        aria: {
            sortAscending:  ": Activar para ordenar la columna de manera ascendente",
            sortDescending: ": Activar para ordenar la columna de manera descendente"
        }
    },
    order: [[0, "desc"]], // Cambia 0 por el índice de la columna por la que deseas ordenar
    aoColumnDefs: [{
        bSortable: false,
        aTargets: [-1] // La última columna no es ordenable
    },
        {
            targets: [5, 6], // Cambia al índice de las columnas numéricas
            render: $.fn.dataTable.render.number(',', '.', 2, '$')
            // ',' -> separador de miles, '.' -> separador decimal, 2 -> decimales
        }]
});
    
    
});

