document.addEventListener("DOMContentLoaded", function() {
    listaMunicipiosExclusion();
    //listaClientes();
 
});
 
// @ts-ignore
//var buscarCliente = document.getElementById('buscarCliente');
//var spinner = document.getElementById("spinnerClientes");
//var clienteValue = 0;
//var timerId;
 
// Función para realizar la llamada al servicio después de un retraso
//function debouncedListaClientes() {
    //clearTimeout(timerId); // Limpiar el temporizador anterior, si existe
    //timerId = setTimeout(listaClientes, 500); // Establecer un nuevo temporizador
//}
 
// @ts-ignore
//buscarCliente.addEventListener('input', debouncedListaClientes);
 
// @ts-ignore
//buscarCliente.addEventListener('input', function() {
    // Actualiza el valor seleccionado cada vez que se ingresa texto
    //listaClientes();
//});
//let timerId = null;
 
// @ts-ignore
/*buscarCliente.addEventListener('input', function () {
 
    clearTimeout(timerId);
 
    // @ts-ignore
    if (buscarCliente.value.length < 3) return;
 
    timerId = setTimeout(listaClientes, 500);
});*/
 
// @ts-ignore
//var buscarCliente = document.getElementById('buscarCliente');
 
// @ts-ignore
/*buscarCliente.addEventListener('input', function () {
 
    console.log('evento input');
 
    // @ts-ignore
    var buscar = buscarCliente.value;
    console.log('buscar:', buscar);
 
    $.ajax({
        url: newUrlBase + "credito/lista_clientes?valor=" + encodeURIComponent(buscar),
        type: "GET",
        dataType: "JSON",
        success: function (respuesta) {
 
            console.log('respuesta:', respuesta);
 
            var opciones = document.getElementById('opcionCliente');
            opciones.innerHTML = '';
 
            respuesta.lista.forEach(function (cliente) {
                console.log(cliente);
 
                var option = document.createElement('option');
                option.value = cliente.clienteinfo;
                opciones.appendChild(option);
            });
        },
        error: function (e) {
            console.error('error ajax', e);
        }
    });
 
});*/
 
// @ts-ignore
var buscarCliente = document.getElementById('buscarCliente');
var spinner = document.getElementById('spinnerClientes');
var opciones = document.getElementById('opcionCliente');
 
var timerId = null;
 
// @ts-ignore
buscarCliente.addEventListener('input', function () {
 
    clearTimeout(timerId);
 
    // @ts-ignore
    if (buscarCliente.value.length < 3) {
        opciones.innerHTML = '';
        return;
    }
 
    timerId = setTimeout(listaClientes, 500);
});
 
function listaClientes() {
 
    // @ts-ignore
    var buscar = buscarCliente.value.toUpperCase();
 
    spinner.style.display = 'block';
 
    $.ajax({
        url: newUrlBase + "credito/lista_clientes?valor=" + encodeURIComponent(buscar),
        type: "GET",
        dataType: "JSON",
        success: function (respuesta) {
        //console.log('respuesta:', respuesta);
            opciones.innerHTML = '';
 
            respuesta.lista.forEach(function (cliente) {
                var option = document.createElement('option');
                option.value = cliente.clienteinfo;
                option.setAttribute('data-id', cliente.i_clienteid);
                opciones.appendChild(option);
            });
 
            // CLAVE para que no desaparezca
            // @ts-ignore
            buscarCliente.blur();
            // @ts-ignore
            buscarCliente.focus();
        },
        error: function () {
            console.error('Error cargando clientes');
        },
        complete: function () {
            spinner.style.display = 'none';
        }
    });
}
 
function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
 
// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");
// @ts-ignore
let idCliente = null;
 
function ocultarMostrar(btn) {
    //console.log(newUrlBase);
    const tabs = document.querySelectorAll('#pant-tabs > div[id$="-tab1"]');
    // @ts-ignore
    tabs.forEach(tab => tab.style.display = 'none'); // Oculta todos
 
    // Muestra solo el tab relacionado con el botón
    const id = btn.id + "1"; // ej: "credenciales-tab1"
    const target = document.getElementById(id);
    if (target) {
        target.style.display = 'block';
    }
 
    // Cambia la clase 'active' en los botones
    document.querySelectorAll('#pntConf .nav-link').forEach(el => el.classList.remove('active'));
    btn.classList.add('active');
}
 
function cargarArchivoExcel() {
    /*const input = document.getElementById('inputArchivo');
    // @ts-ignore
    const archivo = input.files[0];
 
    if (!archivo) {
        alert("Por favor, selecciona un archivo.");
        return;
    }
 
    const formData = new FormData();
    formData.append("archivo", archivo);
 
    fetch(newUrlBase + "credito/cargarMunicipios", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error en la solicitud: " + response.statusText);
            }
            return response.text();
        })
        .then(data => {
            alert(data); // Mensaje del backend
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Ocurrió un error al cargar el archivo.");
        });*/
        //////////////
    const input = document.getElementById('inputArchivo');
    // @ts-ignore
    const archivo = input.files[0];
 
    if (!archivo) {
        alert("Por favor, selecciona un archivo.");
        return;
    }
    // Mostrar pantalla de carga
    // @ts-ignore
    Swal.fire({
        title: "Cargando...",
        text: "Por favor espera mientras se procesa el archivo",
        allowOutsideClick: false,   // evita cerrar con clic afuera
        allowEscapeKey: false,      // evita cerrar con ESC
        showConfirmButton: false,   // oculta el botón "Aceptar"
        didOpen: () => {
            // @ts-ignore
            Swal.showLoading();     // muestra el spinner
        }
    });
    const reader = new FileReader();
    reader.onload = function (e) {
        console.log("Entra a función reader.onload");
        // @ts-ignore
        const data = new Uint8Array(e.target.result);
        // @ts-ignore
        const workbook = XLSX.read(data, { type: "array" });
 
        // Tomamos la primera hoja
        const primeraHoja = workbook.SheetNames[0];
        const hoja = workbook.Sheets[primeraHoja];
 
        // Convertimos a JSON con todas las filas
        // @ts-ignore
        const datos = XLSX.utils.sheet_to_json(hoja, { header: 1 }); 
        // `header: 1` → genera un array de arrays (sin nombres de columnas)
 
        // Primera fila = encabezados
        const primeraFila = datos[0];
 
        // Encabezados esperados
        const encabezadosEsperados = [
            "d_codigo",
            "d_asenta",
            "d_tipo_asenta",
            "D_mnpio",
            "d_estado",
            "d_ciudad",
            "d_CP",
            "c_estado",
            "Cobertura"
        ];
 
        // Validación encabezados
        const esValidoEncabezado = JSON.stringify(primeraFila) === JSON.stringify(encabezadosEsperados);
 
        if (!esValidoEncabezado) {
            console.log("Entra a función esValidoEncabezado");
            // @ts-ignore
             Swal.close(); // Cierra el loading
            alert("El archivo no tiene los encabezados correctos.\n\nEsperados:\n" 
                + encabezadosEsperados.join(", ") 
                + "\n\nEncontrados:\n" 
                + primeraFila.join(", "));
            return; // Detenemos aquí
        }
 
        // Validar que haya al menos una fila con datos después del encabezado
        const filasDatos = datos.slice(1); // todas menos la primera
        const tieneInformacion = filasDatos.some(fila => fila.some(celda => celda !== null && celda !== "" && celda !== undefined));
 
        if (!tieneInformacion) {
            console.log("Entra a función tieneInformacion");
            // @ts-ignore
             Swal.close(); // Cierra el loading
             // @ts-ignore
             Swal.fire({
                 title: "Advertencia",
                 text: "El archivo no contiene información después del encabezado.",
                 icon: "warning"
             });
            return; //Detenemos aquí
        }
 
        // Si pasa todas las validaciones, enviamos al backend
        const formData = new FormData();
        formData.append("archivo", archivo);
    
    
 
 
    fetch(newUrlBase + "credito/cargarMunicipios", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error en la solicitud: " + response.statusText);
            }
            return response.text();
        })
        .then(data => {
             // @ts-ignore
             Swal.close(); // Cierra el loading
            alert(data); // Mensaje del backend
            listaMunicipiosExclusion();
        })
        .catch(error => {
            // @ts-ignore
            Swal.close(); // Cierra el loading
            console.error("Error:", error);
            console.error("Error real backend:", error.message);
            // @ts-ignore
            Swal.fire({
                title: "Error",
                text: "Ocurrió un error al cargar el archivo.",
                icon: "error"
            });
        });
    };
    reader.readAsArrayBuffer(archivo);
    
}
 
function listaMunicipiosExclusion() {
    /*url = newUrlBase + "credito/listarMunicipios";
    $.getJSON(url, {}, function(data) {
        tblExclusionMunicipios.clear().draw();
        $.each(data, function(idx, opt) {
 
            tblExclusionMunicipios.row.add([
                opt.codigo,
                opt.asentamiento,
                opt.tipoAsentamiento,
                opt.municipio,
                opt.estado,
                opt.ciudad,
                opt.codigoPostal,
                opt.codigoEstado,
                opt.cobertura]).draw(false);
        });
    });*/
    
    // @ts-ignore
    Swal.fire({
        title: 'Consultando información...',
        html: 'Espere por favor...',
        allowOutsideClick: false,
        showConfirmButton: false,
        didOpen: () => {
            // @ts-ignore
            Swal.showLoading();
        }
    });
    
    $.ajax({
        url: newUrlBase + "credito/listarMunicipios",
        type: "GET",
        dataType: "JSON",
        async: true,
        success: function(respuesta) {
            // Cierra el Swal de carga
            // @ts-ignore
            Swal.close();
    
            if (!respuesta || respuesta.length === 0) {
                // @ts-ignore
                Swal.fire({
                    text: "No se encontraron municipios.",
                    icon: "warning",
                    confirmButtonText: "Aceptar"
                });
                return;
            }
    
            // Limpia la tabla
            tblExclusionMunicipios.clear().draw();
    
            // Arreglo temporal para acumular filas
            const registrosTotales = [];
    
            $.each(respuesta, function(idx, opt) {
                registrosTotales.push([
                    opt.codigo,
                    opt.asentamiento,
                    opt.tipoAsentamiento,
                    opt.municipio,
                    opt.estado,
                    opt.ciudad,
                    opt.codigoPostal,
                    opt.codigoEstado,
                    opt.cobertura
                ]);
            });
    
            // Inserta todas las filas de golpe
            tblExclusionMunicipios.rows.add(registrosTotales).draw();
        },
        error: function(error) {
            // @ts-ignore
            Swal.close();
            // @ts-ignore
            Swal.fire({
                text: "Error al consultar municipios.",
                icon: "error",
                confirmButtonText: "Aceptar"
            });
        }
    });
    
}
 
 
 
$("#buscarCliente").on("change", function () {
    const valor = $(this).val();
    idCliente = null;
 
    $("#opcionCliente option").each(function () {
        if ($(this).val() === valor) {
            idCliente = $(this).data("id");
            return false;
        }
    });
    // @ts-ignore
    document.getElementById("btnEliminarCliente").disabled = true;
    // @ts-ignore
    document.getElementById("btnAgregarCliente").disabled = true;
 
    // Evita enviar null, undefined o un cliente no seleccionado
    if (!idCliente) {
        return;
    }
 
    $.ajax({
        url:
            newUrlBase +
            "credito/api/countClienteEliminar?idCliente=" +
            encodeURIComponent(idCliente),
        type: "GET",
        success: function (data) {
            if (Number(data) > 0) {
                // @ts-ignore
                document.getElementById("btnEliminarCliente").disabled = false;
            } else {
                // @ts-ignore
                document.getElementById("btnAgregarCliente").disabled = false;
            }
        },
        error: function (xhr) {
            console.error(
                "Error al obtener si el cliente está eliminado:",
                xhr.responseText
            );
        }
    });
});
 
 
$('#formExclusionCliente').on('submit', function (e) {
    e.preventDefault(); // Evita el envío tradicional
 
    // Aquí capturas los datos
    const clienteId = $('#clienteIdSeleccionado').val();
    const clienteNombre = $('#buscarCliente').val();
    const motivo = $('#motivo').val();
    const observaciones = $('#observaciones').val();
    
    // @ts-ignore
    let partes = clienteNombre.split(" - ");
 
    let nombreCliente = partes[1].trim(); 
    //console.log(nombreCliente);
    
    //console.error(clienteId);
    //console.error(idCliente);
    
    
    if (!idCliente) {
        // @ts-ignore
        Swal.fire({
            title: "Advertencia",
            text: "Selecciona un cliente válido de la lista.",
            icon: "warning"
        });
        return;
    }
    const data = {
        clienteId:idCliente,
            buscarEmpleado: nombreCliente,
            motivo,
            observaciones,
    };
    //const jsonBitacora = JSON.stringify(data);
    const jsonBitacora = JSON.stringify({
        ...data,
        estatusCliente: 1
    });
    let tabla = 'TBL_EXCLUSION_CLIENTES';
    //console.log(jsonBitacora);
    const dataHis = {
        vc_json_entrada: jsonBitacora,
        vc_tabla: tabla,
        // @ts-ignore
        dt_fecha_registro:document.getElementById("idFecha").value,
        // @ts-ignore
        i_user_reg: parseInt(document.getElementById("idUser").value),
        
    };
 
    // Enviar por AJAX
    $.ajax({
        url: newUrlBase + "credito/guardarCliente",
        type: "POST",
        data: {
            clienteId:idCliente,
            buscarEmpleado: nombreCliente,
            motivo,
            observaciones
        },
        success: function () {
            //alert("Cliente excluido correctamente.");
            // @ts-ignore
            Swal.fire({
                title: "",
                text: "Cliente excluido correctamente.",
                icon: "success"
            }).then(() => {
 
            });
            fetch(newUrlBase + 'credito/api/crearHisExcCliente', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dataHis)
            })
            // @ts-ignore
            $('#formExclusionCliente')[0].reset();
            $('#clienteIdSeleccionado').val('');
            //deshabilita el boton agregar cliente
            // @ts-ignore
            document.getElementById("btnAgregarCliente").disabled = true;
        },
        error: function (xhr) {
            console.error(xhr);
            // @ts-ignore
            Swal.fire({
                title: "Error",
                text: "Ocurrió un error al guardar la exclusión.",
                icon: "error"
            });
        }
    });
});
 
function eliminarCliente() {
    // @ts-ignore
    $('#formExclusionCliente')[0].reset();
    $('#clienteIdSeleccionado').val('');
    //deshabilita el boton eliminar cliente
    // @ts-ignore
    document.getElementById("btnEliminarCliente").disabled = true;
    
    //console.log(idCliente);
    
    const data = {
        idCliente: idCliente,
        estatusCliente: 2,
    };
    //console.log(data);
    const jsonBitacora = JSON.stringify(data);
    let tabla = 'TBL_EXCLUSION_CLIENTES';
    //console.log(jsonBitacora);
    const dataHis = {
        vc_json_entrada: jsonBitacora,
        vc_tabla: tabla,
        // @ts-ignore
        dt_fecha_registro:document.getElementById("idFecha").value,
        // @ts-ignore
        i_user_reg: parseInt(document.getElementById("idUser").value),
        
    };
    
    fetch(newUrlBase + 'credito/api/actualizarExcCliente', {
    
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(resp => {
        if (!resp.ok) throw new Error("Error al actualizar exclusion cliente");
        return resp.text();
    })
    .then(() => {
        
        ///
        fetch(newUrlBase + 'credito/api/crearHisExcCliente', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataHis)
        })
        ///
        
        // @ts-ignore
        Swal.fire({
            title: "",
            text: "Actualizado correctamente",
            icon: "success"
        }).then(() => {
 
        });
    })
    .catch(err => {
        console.error("Error al actualizar exclusion cliente:", err);
        // @ts-ignore
        Swal.fire({
            title: "Error",
            text: "Error al actualizar la exclusión del cliente.",
            icon: "error"
        });
    });
    
}