document.getElementById("uploadFormPEP").addEventListener("submit", function(event) {
    event.preventDefault();

    const fileInput = document.getElementById("fileUploadPEP");
    const idFolio = "12345"; // Reemplaza con el valor real si aplica

    if (fileInput.files.length > 0) {
        const fileName = fileInput.files[0].name;
        const fileExtension = fileName.split('.').pop().toLowerCase();

        // Nombres y extensiones permitidos para archivos Excel
        const validExtensions = ["xls", "xlsx"]; // Extensiones de archivos Excel

        if (!validExtensions.includes(fileExtension)) {
            swal("El archivo debe ser un documento Excel con extensión .xls o .xlsx.", {
                closeOnClickOutside: false,
                icon: "error",
            });
            return;
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            const data = new Uint8Array(e.target.result);
            const workbook = XLSX.read(data, { type: 'array' });

            // Suponiendo que los datos están en la primera hoja
            const firstSheetName = workbook.SheetNames[0];
            const worksheet = workbook.Sheets[firstSheetName];
            const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

            // Aquí puedes procesar jsonData y compararlo con la base de datos
            console.log(jsonData); // Muestra los datos en la consola

            // Aquí puedes realizar la lógica para comparar con la base de datos
            // Por ejemplo, enviar los datos a un endpoint para su comparación
            compararConBaseDeDatos(jsonData, idFolio);
        };

        reader.readAsArrayBuffer(fileInput.files[0]);
    } else {
        swal("Es necesario seleccionar un archivo.", {
            closeOnClickOutside: false,
            icon: "error",
        });
    }
});

function compararConBaseDeDatos(data, idFolio) {
    // Prepara el cuerpo del request con los datos
    const body = {
        data: data,
        idFolio: idFolio
    };

    fetch('/compararDatos', {  // Cambia esta URL a la ruta correcta de tu backend
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)  // Convierte el objeto a string JSON
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error en la respuesta del servidor: ${response.status}`);
        }
        return response.json(); // o response.text() según lo que retorne tu backend
    })
    .then(resultado => {
        // Maneja el resultado recibido del backend
        console.log('Respuesta del servidor:', resultado);
        // Aquí puedes mostrar mensajes o actualizar UI según resultado
        alert('Comparación completada.');
    })
    .catch(error => {
        console.error('Error al comparar datos con la base de datos:', error);
        alert('Ocurrió un error al comparar los datos.');
    });
}