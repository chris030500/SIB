/* -------- SUBIR ARCHIVO -------- */
document.getElementById("uploadFormPEP").addEventListener("submit", async e => {
  e.preventDefault();
  const file = document.getElementById("fileUploadPEP").files[0];
  if (!file) { alert("Selecciona un archivo"); return; }

  const fd = new FormData();
  fd.append("file", file);           //  << nombre EXACTO que espera el controller

  const res  = await fetch(e.target.action, { method: "POST", body: fd });
  const text = await res.text();
  alert(text);
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

/* -------- AJUSTE EN TABLA DESCARGAS (id correcto) -------- */
function cargarDescargas() {
  fetch("/prevencion/reporteClientesOpe/descargas")
    .then(r => r.json())
    .then(data => {
      const tbody = document.getElementById("tablaReportesGenerados");   // ← id real
      tbody.innerHTML = "";
      data.forEach(row => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${row.fechaGeneracion}</td>
          <td>${row.rangoFechas||""}</td>
          <td>${row.usuario||""}</td>
          <td>${row.nombreArchivo}</td>
          <td>${row.confirmado?"Sí":"No"}</td>
          <td><a class='btn btn-sm btn-primary'
                 href='/prevencion/reporteClientesOpe/descargar/${row.id}'>Descargar</a></td>`;
        tbody.appendChild(tr);
      });
    });
}
document.getElementById("descargas-tab")
        .addEventListener("click", cargarDescargas);