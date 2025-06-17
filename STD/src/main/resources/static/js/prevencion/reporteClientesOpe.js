document.getElementById("uploadFormPEP").addEventListener("submit", async function (e) {
    e.preventDefault();  // Evitar el comportamiento por defecto del formulario

    const archivo = document.getElementById("fileUploadPEP").files[0]; // Obtener el archivo
    if (!archivo) {
        alert("Selecciona un archivo");
        return;
    }

    const fd = new FormData();
    fd.append("file", archivo);  // Agregar el archivo al FormData

    try {
        const res = await fetch("http://localhost:8080/prevencion/reporteClientesOpe/uploadPEP", {
  method: "POST",
  body: fd
});

        const result = await res.text();  // Leer la respuesta del servidor
        alert(result);  // Mostrar la respuesta
    } catch (err) {
        alert("Error: " + err);  // Mostrar el error si ocurre alguno
    }
});

document.getElementById("programado").addEventListener("change", e => {
    document.getElementById("horaProgDiv").style.display = e.target.value === "true" ? "block" : "none";
});

document.getElementById("formProceso").addEventListener("submit", function(e) {
    e.preventDefault();
    const data = {
        fechaInicio: document.getElementById("fechaInicio").value,
        fechaFin: document.getElementById("fechaFin").value,
        programado: document.getElementById("programado").value === "true",
        horaEjecucion: document.getElementById("horaEjecucion").value
    };
    fetch("/prevencion/reporteClientesOpe/guardarFechas", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    }).then(r => r.text()).then(alert);
});

document.getElementById("btnProcesar").addEventListener("click", () => {
    // este id debería venir de backend o guardarse en session
    const procesoId = 1; 
    fetch(`/prevencion/reporteClientesOpe/procesar?procesoId=${procesoId}`, { method: "POST" })
        .then(r => r.text()).then(msg => {
            alert(msg);
            document.getElementById("confirmacion").style.display = "block";
        });
});

function confirmar(valor) {
    fetch(`/prevencion/reporteClientesOpe/confirmar?confirmado=${valor}`, { method: "POST" })
        .then(r => {
            if (r.redirected) window.location.href = r.url;
            else return r.text().then(alert);
        });
}

function cargarDescargas() {
    fetch("/prevencion/reporteClientesOpe/descargas")
        .then(r => r.json())
        .then(data => {
            const tbody = document.querySelector("#tablaDescargas tbody");
            tbody.innerHTML = "";
            data.forEach(row => {
                const tr = document.createElement("tr");
                tr.innerHTML = `<td>${row.nombreArchivo}</td>
                                <td>${row.fechaGeneracion}</td>
                                <td><a href='/prevencion/reporteClientesOpe/descargar/${row.id}' class='btn btn-sm btn-primary'>Descargar</a></td>`;
                tbody.appendChild(tr);
            });
        });
}

document.querySelector("a[href='#descargas']").addEventListener("click", cargarDescargas);