document.getElementById('formArchivoPeps').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData();
    const archivo = document.getElementById('archivoPeps').files[0];

    if (!archivo || !archivo.name.includes(`peps_${new Date().getFullYear()}_`)) {
        alert("El archivo debe llamarse peps_<añoactual>_.xlsx");
        return;
    }

    formData.append("archivoPeps", archivo);

    fetch('/prevencion/cargarArchivoPeps', {
        method: 'POST',
        body: formData
    }).then(response => response.json())
      .then(data => {
          document.getElementById("resultadoCargaPeps").innerText = data.message;
      }).catch(error => {
          document.getElementById("resultadoCargaPeps").innerText = "Error al cargar archivo.";
      });
});
