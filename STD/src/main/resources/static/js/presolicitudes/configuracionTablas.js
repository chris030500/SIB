// @ts-nocheck
var tblRegistroEmpresas;

$(document).ready(function() {
    tblRegistroEmpresas = $("#tblRegistroEmpresas").DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
        'aoColumnDefs': [{
            'bSortable': false,
            'aTargets': [-1]  // La última columna (Acciones) no será ordenable
        }]
    });
});
