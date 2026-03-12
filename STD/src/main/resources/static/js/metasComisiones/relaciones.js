// @ts-nocheck
(function () {
    const baseUrl = '/metas-comisiones/relaciones';

    const regionSelect = document.getElementById('regionSelect');
    const sucursalSelect = document.getElementById('sucursalSelect');
    const colaboradorSelect = document.getElementById('colaboradorSelect');
    const puestoSelect = document.getElementById('puestoSelect');
    const tbodyAsignados = document.querySelector('#tablaAsignados tbody');
    const tbodyDisponibles = document.querySelector('#tablaDisponibles tbody');
    const msgBox = document.getElementById('msgBox');
    const btnCargarCatalogos = document.getElementById('btnCargarCatalogos');
    const sucursalesFile = document.getElementById('sucursalesFile');
    const empleadosFile = document.getElementById('empleadosFile');
    const puestosProductosFile = document.getElementById('puestosProductosFile');

    function showMsg(text, type) {
        msgBox.className = 'alert alert-' + type;
        msgBox.textContent = text;
        msgBox.style.display = 'block';
        setTimeout(() => {
            msgBox.style.display = 'none';
        }, 2500);
    }

    function cleanSelect(select, placeholder) {
        select.innerHTML = '';
        const opt = document.createElement('option');
        opt.value = '';
        opt.textContent = placeholder;
        select.appendChild(opt);
        select.value = '';
    }

    function fillSelect(select, data, valueKey, textKey, placeholder) {
        cleanSelect(select, placeholder);
        data.forEach(item => {
            const opt = document.createElement('option');
            opt.value = item[valueKey];
            opt.textContent = item[textKey];
            select.appendChild(opt);
        });
        select.disabled = false;
    }

    function resetDownstream(level) {
        if (level <= 1) {
            cleanSelect(sucursalSelect, 'Selecciona una sucursal');
            sucursalSelect.disabled = true;
        }
        if (level <= 2) {
            cleanSelect(colaboradorSelect, 'Selecciona un colaborador');
            colaboradorSelect.disabled = true;
        }
        if (level <= 3) {
            cleanSelect(puestoSelect, 'Selecciona un puesto');
            puestoSelect.disabled = true;
        }
        if (level <= 4) {
            tbodyAsignados.innerHTML = '';
            tbodyDisponibles.innerHTML = '';
        }
    }

    async function getJson(url) {
        const resp = await fetch(url);
        if (!resp.ok) {
            throw new Error('Error HTTP ' + resp.status);
        }
        return resp.json();
    }

    async function cargarRegiones() {
        const data = await getJson(baseUrl + '/regiones');
        fillSelect(regionSelect, data, 'regionId', 'nombreRegion', 'Selecciona una region');
    }

    async function cargarSucursales(regionId) {
        const data = await getJson(baseUrl + '/sucursales?regionId=' + encodeURIComponent(regionId));
        fillSelect(sucursalSelect, data, 'sucursalId', 'nombreSucursal', 'Selecciona una sucursal');
    }

    async function cargarColaboradores(sucursalId) {
        const data = await getJson(baseUrl + '/colaboradores?sucursalId=' + encodeURIComponent(sucursalId));
        fillSelect(colaboradorSelect, data, 'colaboradorId', 'nombreColaborador', 'Selecciona un colaborador');
    }

    async function cargarPuestos(colaboradorId) {
        const data = await getJson(baseUrl + '/puestos?colaboradorId=' + encodeURIComponent(colaboradorId));
        fillSelect(puestoSelect, data, 'clavePuestoId', 'nombrePuesto', 'Selecciona un puesto');
    }

    async function cargarProductos(clavePuestoId) {
        const [asignados, disponibles] = await Promise.all([
            getJson(baseUrl + '/productos?clavePuestoId=' + encodeURIComponent(clavePuestoId)),
            getJson(baseUrl + '/productos-disponibles')
        ]);

        const asignadosMap = new Set(asignados.map(x => x.productoId));
        const noAsignados = disponibles.filter(x => !asignadosMap.has(x.productoId));

        tbodyAsignados.innerHTML = '';
        tbodyDisponibles.innerHTML = '';

        asignados.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + item.productoId + '</td>' +
                '<td>' + item.numeroProducto + '</td>' +
                '<td>' + item.nombreProducto + '</td>' +
                '<td><button class="btn btn-sm btn-danger" data-id="' + item.productoId + '">Quitar</button></td>';
            tr.querySelector('button').addEventListener('click', () => desasignarProducto(clavePuestoId, item.productoId));
            tbodyAsignados.appendChild(tr);
        });

        noAsignados.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + item.productoId + '</td>' +
                '<td>' + item.numeroProducto + '</td>' +
                '<td>' + item.nombreProducto + '</td>' +
                '<td><button class="btn btn-sm btn-primary" data-id="' + item.productoId + '">Asignar</button></td>';
            tr.querySelector('button').addEventListener('click', () => asignarProducto(clavePuestoId, item.productoId));
            tbodyDisponibles.appendChild(tr);
        });
    }

    async function asignarProducto(clavePuestoId, productoId) {
        const resp = await fetch(baseUrl + '/puesto-producto', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ clavePuestoId, productoId })
        });
        if (!resp.ok) {
            showMsg('No se pudo asignar el producto', 'danger');
            return;
        }
        showMsg('Producto asignado correctamente', 'success');
        await cargarProductos(clavePuestoId);
    }

    async function desasignarProducto(clavePuestoId, productoId) {
        const resp = await fetch(baseUrl + '/puesto-producto?clavePuestoId=' + encodeURIComponent(clavePuestoId) + '&productoId=' + encodeURIComponent(productoId), {
            method: 'DELETE'
        });
        if (!resp.ok) {
            showMsg('No se pudo quitar el producto', 'danger');
            return;
        }
        showMsg('Producto desasignado correctamente', 'warning');
        await cargarProductos(clavePuestoId);
    }

    async function cargarCatalogos() {
        if (!sucursalesFile.files.length || !empleadosFile.files.length || !puestosProductosFile.files.length) {
            showMsg('Debes seleccionar los 3 archivos Excel', 'danger');
            return;
        }

        const formData = new FormData();
        formData.append('sucursalesFile', sucursalesFile.files[0]);
        formData.append('empleadosFile', empleadosFile.files[0]);
        formData.append('puestosProductosFile', puestosProductosFile.files[0]);

        btnCargarCatalogos.disabled = true;
        btnCargarCatalogos.textContent = 'Procesando...';
        try {
            const resp = await fetch(baseUrl + '/cargar-catalogos', {
                method: 'POST',
                body: formData
            });
            if (!resp.ok) {
                throw new Error('Error HTTP ' + resp.status);
            }
            const data = await resp.json();
            showMsg('Carga completada. Relaciones generadas: ' + data.relacionesGeneradas, 'success');
            resetDownstream(1);
            await cargarRegiones();
        } catch (e) {
            showMsg('No se pudo procesar la carga de catalogos', 'danger');
        } finally {
            btnCargarCatalogos.disabled = false;
            btnCargarCatalogos.textContent = 'Cargar y generar relacion';
        }
    }

    regionSelect.addEventListener('change', async () => {
        resetDownstream(1);
        if (!regionSelect.value) return;
        try {
            await cargarSucursales(regionSelect.value);
        } catch (e) {
            showMsg('Error al cargar sucursales', 'danger');
        }
    });

    sucursalSelect.addEventListener('change', async () => {
        resetDownstream(2);
        if (!sucursalSelect.value) return;
        try {
            await cargarColaboradores(sucursalSelect.value);
        } catch (e) {
            showMsg('Error al cargar colaboradores', 'danger');
        }
    });

    colaboradorSelect.addEventListener('change', async () => {
        resetDownstream(3);
        if (!colaboradorSelect.value) return;
        try {
            await cargarPuestos(colaboradorSelect.value);
        } catch (e) {
            showMsg('Error al cargar puestos', 'danger');
        }
    });

    puestoSelect.addEventListener('change', async () => {
        resetDownstream(4);
        if (!puestoSelect.value) return;
        try {
            await cargarProductos(puestoSelect.value);
        } catch (e) {
            showMsg('Error al cargar productos', 'danger');
        }
    });

    btnCargarCatalogos.addEventListener('click', async () => {
        await cargarCatalogos();
    });

    (async function init() {
        resetDownstream(1);
        try {
            await cargarRegiones();
        } catch (e) {
            showMsg('Error al cargar regiones', 'danger');
        }
    })();
})();
