// Lógica para mostrar/ocultar campos de búsqueda según la selección
$(document).ready(function () {
    $("#filterType").change(function () {
        if ($(this).val() === "client") {
            $("#searchInput").show();
            $("#searchInputBranch").hide();
        } else if ($(this).val() === "branch") {
            $("#searchInput").hide();
            $("#searchInputBranch").show();
        }
    });

    // Manejar la lógica de envío del formulario aquí...
    $("#smsFilterForm").submit(function (event) {
        // Agrega la lógica de envío de formulario según tus requerimientos
        event.preventDefault();
        alert("Formulario enviado. Agrega tu lógica aquí.");
    });
});

// Función para aplicar filtros (puedes ajustar según tus necesidades)
function applyFilters() {
    var filterType = $("#filterType").val();
    var searchInput;

    if (filterType === "client") {
        searchInput = $("#searchInput").val();
    } else if (filterType === "branch") {
        searchInput = $("#searchInputBranch").val();
    }

    // Puedes utilizar los valores de filterType y searchInput según tus necesidades
    console.log("Tipo de filtro:", filterType);
    console.log("Valor de búsqueda:", searchInput);

    // Agrega aquí la lógica para aplicar los filtros al backend o realizar otras acciones
    // ...

    // Ejemplo: Simular el envío del formulario
    $("#smsFilterForm").submit();
}
