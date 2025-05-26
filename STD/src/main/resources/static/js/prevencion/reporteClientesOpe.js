		// Simulación de datos almacenados (en un sistema real, esto vendría del backend)
let historialPEPs = [
    { nombre: 'archivo1.xlsx', fecha: '10/04/2025', hora: '09:15 a.m.', usuario: 'admin1' },
    { nombre: 'archivo2.xlsx', fecha: '11/04/2025', hora: '14:30 p.m.', usuario: 'admin2' },
    { nombre: 'archivo3.xlsx', fecha: '12/04/2025', hora: '08:45 a.m.', usuario: 'admin1' }
];

// Simular usuario autenticado
const usuarioActual = "admin3"; // En app real, esto vendría de sesión o token

function renderizarHistorial() {
    const lista = document.getElementById("listaArchivosPEP");
    lista.innerHTML = ""; // Limpiar lista

    historialPEPs.forEach(item => {
        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center";

        li.innerHTML = `
            <div>
                <strong>${item.nombre}</strong><br>
                <small>Subido por: ${item.usuario} a las ${item.hora}</small>
            </div>
            <div>
                <span class="mx-2">${item.fecha}</span>
                <span class="text-success">&#10004;</span>
            </div>
        `;
        lista.appendChild(li);
    });
}

function subirArchivoPEP() {
    const archivoInput = document.getElementById("archivoPEP");
    const mensaje = document.getElementById("mensajePEP");

    if (!archivoInput.files.length) {
        mensaje.innerHTML = '<div class="alert alert-warning">Por favor selecciona un archivo.</div>';
        return;
    }

    const archivo = archivoInput.files[0];

    if (!archivo.name.endsWith(".xlsx")) {
        mensaje.innerHTML = '<div class="alert alert-danger">El archivo debe tener formato .xlsx</div>';
        return;
    }

    const ahora = new Date();
    const fechaSubida = ahora.toLocaleDateString('es-MX', {
        day: '2-digit', month: '2-digit', year: 'numeric'
    });
    const horaSubida = ahora.toLocaleTimeString('es-MX', {
        hour: '2-digit', minute: '2-digit'
    });

    historialPEPs.unshift({
        nombre: archivo.name,
        fecha: fechaSubida,
        hora: horaSubida,
        usuario: usuarioActual
    });

    mensaje.innerHTML = '<div class="alert alert-success">Archivo cargado correctamente.</div>';
    archivoInput.value = "";
    renderizarHistorial();
}

// Inicializar
document.addEventListener("DOMContentLoaded", renderizarHistorial);
    
			document.addEventListener("DOMContentLoaded", function () {
    // HABILITAR / DESHABILITAR SELECT SEGÚN RADIOBUTTON
    const select = document.getElementById("selectFecha");
    const radioSi = document.getElementById("progSi");
    const radioNo = document.getElementById("progNo");
    const btnGuardarFecha = document.getElementById("btnGuardarFecha");

    function toggleSelect() {
        select.disabled = !radioSi.checked;
        btnGuardarFecha.disabled = true;
        select.selectedIndex = 0;
    }

    radioSi.addEventListener("change", toggleSelect);
    radioNo.addEventListener("change", toggleSelect);
    toggleSelect(); // Inicializa estado

    // Activar botón guardar si se selecciona opción válida
    select.addEventListener("change", () => {
        if (select.value !== "Seleccione una opción") {
            btnGuardarFecha.disabled = false;
        }
    });

    // Guardar selección de fecha y hora
    btnGuardarFecha.addEventListener("click", () => {
        const selected = select.value;
        if (selected && selected !== "Seleccione una opción") {
            alert(`Reporte programado para: ${selected}`);
            btnGuardarFecha.disabled = true;
        }
    });

    // VALIDACIÓN DE INFORMACIÓN - MOSTRAR AVISO O FORMULARIO
    const infoSi = document.getElementById("infoSi");
    const infoNo = document.getElementById("infoNo");
    const mensajeAviso = document.getElementById("mensajeAviso");
    const subirNuevoArchivo = document.getElementById("subirNuevoArchivo");

    infoSi.addEventListener("change", () => {
        if (infoSi.checked) {
            mensajeAviso.classList.remove("d-none");
            subirNuevoArchivo.classList.add("d-none");
        }
    });

    infoNo.addEventListener("change", () => {
    if (infoNo.checked) {
        mensajeAviso.classList.add("d-none");

        // Redirigir a la pestaña "Cargar archivos"
        const tabCargarArchivos = document.querySelector('#archivos-tab');
        if (tabCargarArchivos) {
            tabCargarArchivos.click();
            alert("Debe volver a cargar el archivo para continuar.");
        } else {
            console.error("No se encontró la pestaña 'Cargar archivos' con id='archivos-tab'");
        }
    }
});


    // CONFIRMACIÓN MODAL - REPORTE CORRECTO
    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));

    infoSi.addEventListener("change", () => {
        if (infoSi.checked) {
            confirmModal.show();
        }
    });

    document.getElementById("confirmarOkey").addEventListener("click", function () {
        confirmModal.hide();
        mensajeAviso.classList.remove("d-none");
        subirNuevoArchivo.classList.add("d-none");
        alert("El reporte se generó correctamente.");
    });

    document.getElementById("cancelarConfirmacion").addEventListener("click", function () {
        confirmModal.hide();
        const procesoTab = document.querySelector('#proceso-tab');
        const procesoPanel = new bootstrap.Tab(procesoTab);
        procesoPanel.show();
        document.querySelector('input[name="info_correcta"]:checked').checked = false;
    });

    // TABLA DE REGISTROS - CARGA Y MANEJO
    const tablaCuerpo = document.getElementById("tablaCuerpo");
    const totalRegistros = document.getElementById("totalRegistros");
    const btnEliminarSeleccionados = document.getElementById("btnEliminarSeleccionados");

    function actualizarTotalRegistros() {
        const total = tablaCuerpo.querySelectorAll("tr").length;
        totalRegistros.textContent = `Total de registros: ${total}`;
    }

    function verificarSeleccionados() {
        const checkboxes = tablaCuerpo.querySelectorAll('input[type="checkbox"]');
        const algunoSeleccionado = Array.from(checkboxes).some(cb => cb.checked);
        btnEliminarSeleccionados.style.display = algunoSeleccionado ? 'block' : 'none';
    }

    for (let i = 1; i <= 5; i++) {
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td><input type="checkbox" class="form-check-input checkbox-seleccion" /></td>
            <td>Cliente ${i}</td>
            <td>Marzo 2025</td>
            <td>Usuario ${i}</td>
            <td>01/03/2025</td>
            <td>03/03/2025</td>
            <td>Completado</td>
            <td><button class="btn btn-sm btn-success btn-descargar-reporte">Descargar</button></td>
        `;
        tablaCuerpo.appendChild(fila);
    }

    actualizarTotalRegistros();

    tablaCuerpo.addEventListener("change", function (e) {
        if (e.target.classList.contains("checkbox-seleccion")) {
            verificarSeleccionados();
        }
    });

    tablaCuerpo.addEventListener("click", function (e) {
        if (e.target.classList.contains("btn-descargar-reporte")) {
            const fila = e.target.closest("tr");
            const celdas = fila.querySelectorAll("td");

            const cliente = celdas[1].textContent.trim();
            const mes = celdas[2].textContent.trim();
            const usuario = celdas[3].textContent.trim();
            const fechaProceso = celdas[4].textContent.trim();
            const fechaFinal = celdas[5].textContent.trim();
            const estado = celdas[6].textContent.trim();

            const contenido = `REPORTE DEL PROCESO\n\nCliente: ${cliente}\nMes Cerrado: ${mes}\nUsuario: ${usuario}\nFecha de Proceso: ${fechaProceso}\nFecha Finalización: ${fechaFinal}\nEstado: ${estado}\n\nGenerado el: ${new Date().toLocaleString()}`;

            const blob = new Blob([contenido], { type: "text/plain;charset=utf-8" });
            const link = document.createElement("a");
            link.href = URL.createObjectURL(blob);
            link.download = `Reporte_${cliente.replace(/\s+/g, "_")}.txt`;

            document.body.appendChild(link);
            link.click();
            URL.revokeObjectURL(link.href);
            document.body.removeChild(link);
        }
    });

    
    document.getElementById("btnDescargar")?.addEventListener("click", function () {
        const contenido = "Este es un reporte simulado generado automáticamente.\nFecha: " + new Date().toLocaleString();
        const blob = new Blob([contenido], { type: "text/plain;charset=utf-8" });

        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "reporte.txt";
        link.click();
        URL.revokeObjectURL(link.href);
    });
});
// VALIDACIÓN DE FECHAS (2 años hacia atrás)
    document.getElementById('btnGuardarRango').addEventListener('click', function () {
        const fechaInicio = new Date(document.getElementById('fechaInicio').value);
        const fechaFin = new Date(document.getElementById('fechaFin').value);
        const hoy = new Date();
        const haceDosAnios = new Date();
        haceDosAnios.setFullYear(hoy.getFullYear() - 2);

        const aviso = document.getElementById('avisoRangoFechas');
        const error = document.getElementById('errorRangoFechas');

        // Ocultar ambos mensajes antes de validar
        aviso.classList.add('d-none');
        error.classList.add('d-none');

        if (isNaN(fechaInicio) || isNaN(fechaFin)) {
            error.textContent = "Debe seleccionar ambas fechas.";
            error.classList.remove('d-none');
            return;
        }

        if (fechaInicio < haceDosAnios) {
            error.textContent = "La fecha de inicio no puede ser anterior a 2 años desde hoy.";
            error.classList.remove('d-none');
        } else if (fechaFin < fechaInicio) {
            error.textContent = "La fecha final no puede ser anterior a la fecha de inicio.";
            error.classList.remove('d-none');
        } else {
            aviso.classList.remove('d-none');
            setTimeout(() => aviso.classList.add('d-none'), 6000);
        }
    });


document.getElementById('btnProcesar').addEventListener('click', function () {
    const btn = this;
    const progreso = document.getElementById('progresoTexto');
    const btnDescargar = document.getElementById('btnDescargar');
    const radios = document.querySelectorAll('input[name="info_correcta"]');

    btn.disabled = true;
    progreso.textContent = 'Procesando... 0%';

    let progresoActual = 0;
    const intervalo = setInterval(() => {
        progresoActual += 10;
        progreso.textContent = `Procesando... ${progresoActual}%`;
        if (progresoActual >= 100) {
            clearInterval(intervalo);
            progreso.textContent = 'Proceso finalizado.';
            btnDescargar.disabled = false;

            // Habilitar opciones de "Sí / No" cuando el reporte esté listo para descargar
            radios.forEach(r => r.disabled = false);
        }
    }, 300);
});

document.querySelectorAll('input[name="tipoBusqueda"]').forEach(radio => {
        radio.addEventListener('change', function () {
            const campoUsuario = document.getElementById('campoUsuario');
            const campoCliente = document.getElementById('campoCliente');

            if (this.value === 'usuario') {
                campoUsuario.classList.remove('d-none');
                campoCliente.classList.add('d-none');
            } else if (this.value === 'cliente') {
                campoCliente.classList.remove('d-none');
                campoUsuario.classList.add('d-none');
            }
        });
    });

// FUNCIÓN PARA SUBIR ARCHIVO
function subirArchivo() {
    const input = document.getElementById('formFile');
    const file = input.files[0];
    if (file) {
        const historial = document.getElementById('archivoHistorial');

        const archivoDiv = document.createElement('div');
        archivoDiv.classList.add('uploaded-file');

        const nombreArchivo = document.createElement('span');
        nombreArchivo.textContent = file.name;

        const iconoCheck = document.createElement('span');
        iconoCheck.classList.add('check-icon');
        iconoCheck.innerHTML = '✔️';

        archivoDiv.appendChild(nombreArchivo);
        archivoDiv.appendChild(iconoCheck);

        historial.appendChild(archivoDiv);
        input.value = '';
    } else {
        alert('Por favor selecciona un archivo');
    }
}