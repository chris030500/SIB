// @ts-nocheck
var tblRegistroEmpresas;
var tablaSolicitudes;

$(document).ready(function() {
    tblRegistroEmpresas = $("#tblRegistroEmpresas").DataTable({
        "language": {
            "url": "/json/Spanish.json"
        },
        'aoColumnDefs': [{
            'bSortable': false,
            'aTargets': [-1]  // La última columna (Acciones) no será ordenable
        }]
    });
    
     tablaSolicitudes = $("#tablaSolicitudes").DataTable({
        "language": {
            "url": "/json/Spanish.json"
        },
        'aoColumnDefs': [{
            'bSortable': false,
            'aTargets': [-1]  // La última columna (Acciones) no será ordenable
        }]
    });
    
    
});

