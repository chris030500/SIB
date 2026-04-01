(function () {
    const baseUrl = '/metas-comisiones/relaciones';

    const msgBox = document.getElementById('msgBox');
    const tabRs = document.getElementById('tab-rs');
    const tabSe = document.getElementById('tab-se');
    const tabSp = document.getElementById('tab-sp');
    const tabAu = document.getElementById('tab-au');
    const paneRs = document.getElementById('pane-rs');
    const paneSe = document.getElementById('pane-se');
    const paneSp = document.getElementById('pane-sp');
    const paneAu = document.getElementById('pane-au');

    // Tab 1
    const rsSearch = document.getElementById('rsSearch');
    const rsBoard = document.getElementById('rsBoard');
    const rsStatusFilter = document.getElementById('rsStatusFilter');
    const rsChannelFilter = document.getElementById('rsChannelFilter');
    const rsStatusButtonsWrap = document.getElementById('rsStatusButtons');
    const rsClearFilters = document.getElementById('rsClearFilters');
    const rsUndoMove = document.getElementById('rsUndoMove');
    const rsTotalRegiones = document.getElementById('rsTotalRegiones');
    const rsTotalSucursales = document.getElementById('rsTotalSucursales');
    const btnOpenRsUpload = document.getElementById('btnOpenRsUpload');
    const btnRsAddRecord = document.getElementById('btnRsAddRecord');
    const btnRsExportPdf = document.getElementById('btnRsExportPdf');
    const btnRsExportXlsx = document.getElementById('btnRsExportXlsx');

    // Tab 2
    const seSearch = document.getElementById('seSearch');
    const seRegionFilter = document.getElementById('seRegionFilter');
    const seOrigenSelect = document.getElementById('seOrigenSelect');
    const seDestinoSelect = document.getElementById('seDestinoSelect');
    const sePuestoFilter = document.getElementById('sePuestoFilter');
    const seVigenciaFilter = document.getElementById('seVigenciaFilter');
    const seInfo = document.getElementById('seInfo');
    const btnSeMoverSeleccionados = document.getElementById('btnSeMoverSeleccionados');
    const seCheckAll = document.getElementById('seCheckAll');
    const tbodyEmpleadoReasignacion = document.querySelector('#tablaEmpleadoReasignacion tbody');
    const btnOpenSeUpload = document.getElementById('btnOpenSeUpload');
    const btnSeAddRecord = document.getElementById('btnSeAddRecord');
    const btnSeExportPdf = document.getElementById('btnSeExportPdf');
    const btnSeExportXlsx = document.getElementById('btnSeExportXlsx');
    const btnSeCompareCore = document.getElementById('btnSeCompareCore');

    // Tab 3
    const spSearch = document.getElementById('spSearch');
    const spSucursalSelect = document.getElementById('spSucursalSelect');
    const spCanalFilter = document.getElementById('spCanalFilter');
    const spFuerzaFilter = document.getElementById('spFuerzaFilter');
    const spEstatusFilter = document.getElementById('spEstatusFilter');
    const spBoard = document.getElementById('spBoard');
    const btnOpenSpUpload = document.getElementById('btnOpenSpUpload');
    const btnSpAddRecord = document.getElementById('btnSpAddRecord');

    // Tab 4
    const auSearch = document.getElementById('auSearch');
    const btnAuRefresh = document.getElementById('btnAuRefresh');
    const auInfo = document.getElementById('auInfo');
    const tbodyAuditoria = document.querySelector('#tablaAuditoria tbody');
    
    // Upload modal (shared)
    const catalogUploadModalEl = document.getElementById('catalogUploadModal');
    const catalogUploadTitle = document.getElementById('catalogUploadTitle');
    const catalogUploadHelp = document.getElementById('catalogUploadHelp');
    const catalogUploadFile = document.getElementById('catalogUploadFile');
    const catalogUploadDropzone = document.getElementById('catalogUploadDropzone');
    const catalogUploadFileName = document.getElementById('catalogUploadFileName');
    const btnCatalogUploadConfirm = document.getElementById('btnCatalogUploadConfirm');

    // Modal Alta/Edicion Tab 1
    const rsRecordModalEl = document.getElementById('rsRecordModal');
    const rsRecordModalTitle = document.getElementById('rsRecordModalTitle');
    const rsRecordMode = document.getElementById('rsRecordMode');
    const rsRecordId = document.getElementById('rsRecordId');
    const rsRegionNum = document.getElementById('rsRegionNum');
    const rsRegionNom = document.getElementById('rsRegionNom');
    const rsSucursalNum = document.getElementById('rsSucursalNum');
    const rsSucursalNom = document.getElementById('rsSucursalNom');
    const rsSucursalEstatus = document.getElementById('rsSucursalEstatus');
    const rsCanales = document.getElementById('rsCanales');
    const btnRsRecordSave = document.getElementById('btnRsRecordSave');

    // Modal Alta/Edicion Tab 2
    const seRecordModalEl = document.getElementById('seRecordModal');
    const seRecordModalTitle = document.getElementById('seRecordModalTitle');
    const seRecordMode = document.getElementById('seRecordMode');
    const seRecordId = document.getElementById('seRecordId');
    const seEmpleadoNum = document.getElementById('seEmpleadoNum');
    const seEmpleadoNom = document.getElementById('seEmpleadoNom');
    const seRecordSucursalNum = document.getElementById('seRecordSucursalNum');
    const sePuestoId = document.getElementById('sePuestoId');
    const sePuestoDesc = document.getElementById('sePuestoDesc');
    const seAplicaMeta = document.getElementById('seAplicaMeta');
    const seEstado = document.getElementById('seEstado');
    const sePeriodoNum = document.getElementById('sePeriodoNum');
    const seVigenciaInicio = document.getElementById('seVigenciaInicio');
    const seVigenciaFin = document.getElementById('seVigenciaFin');
    const btnSeRecordSave = document.getElementById('btnSeRecordSave');
    const seCoreCompareModalEl = document.getElementById('seCoreCompareModal');
    const seCoreCompareSummary = document.getElementById('seCoreCompareSummary');
    const seCoreCompareCards = document.getElementById('seCoreCompareCards');
    const seCoreCompareEmpty = document.getElementById('seCoreCompareEmpty');
    const seCoreCompareInfo = document.getElementById('seCoreCompareInfo');
    const seCoreStatusFilter = document.getElementById('seCoreStatusFilter');
    const seCoreSearch = document.getElementById('seCoreSearch');
    const btnSeApplyCore = document.getElementById('btnSeApplyCore');
    const btnSeKeepExcel = document.getElementById('btnSeKeepExcel');
    const btnSeSelectVisible = document.getElementById('btnSeSelectVisible');
    const btnSeClearVisible = document.getElementById('btnSeClearVisible');

    // Modal Alta/Edicion Tab 3
    const spRecordModalEl = document.getElementById('spRecordModal');
    const spRecordModalTitle = document.getElementById('spRecordModalTitle');
    const spRecordMode = document.getElementById('spRecordMode');
    const spRecordId = document.getElementById('spRecordId');
    const spRecordSucursalNum = document.getElementById('spRecordSucursalNum');
    const spRecordPuestoId = document.getElementById('spRecordPuestoId');
    const spRecordProductoNum = document.getElementById('spRecordProductoNum');
    const spRecordProductoNom = document.getElementById('spRecordProductoNom');
    const spRecordCanal = document.getElementById('spRecordCanal');
    const spRecordFuerzaVenta = document.getElementById('spRecordFuerzaVenta');
    const spRecordPorcNuevo = document.getElementById('spRecordPorcNuevo');
    const spRecordPorcRenovado = document.getElementById('spRecordPorcRenovado');
    const spRecordVigenciaInicio = document.getElementById('spRecordVigenciaInicio');
    const spRecordVigenciaFin = document.getElementById('spRecordVigenciaFin');
    const spRecordEstatus = document.getElementById('spRecordEstatus');
    const btnSpRecordSave = document.getElementById('btnSpRecordSave');

    const state = {
        regiones: [],
        sucursalesAll: [],
        rsRows: [],
        seRows: [],
        seOrigenLoaded: null,
        rsFilterMode: 'ALL',
        rsFilterChannel: '',
        rsLastMove: null,
        rsUndoPayload: null,
        uploadContext: null,
        permissions: new Set(),
        seCompareRows: [],
        seCompareSelected: new Set()
    };

    const uploadModal = (catalogUploadModalEl && window.bootstrap && window.bootstrap.Modal)
        ? new window.bootstrap.Modal(catalogUploadModalEl)
        : null;
    const rsRecordModal = (rsRecordModalEl && window.bootstrap && window.bootstrap.Modal)
        ? new window.bootstrap.Modal(rsRecordModalEl)
        : null;
    const seRecordModal = (seRecordModalEl && window.bootstrap && window.bootstrap.Modal)
        ? new window.bootstrap.Modal(seRecordModalEl)
        : null;
    const seCoreCompareModal = (seCoreCompareModalEl && window.bootstrap && window.bootstrap.Modal)
        ? new window.bootstrap.Modal(seCoreCompareModalEl)
        : null;
    const spRecordModal = (spRecordModalEl && window.bootstrap && window.bootstrap.Modal)
        ? new window.bootstrap.Modal(spRecordModalEl)
        : null;

    function showMsg(text, type) {
        msgBox.className = 'alert alert-' + type;
        msgBox.textContent = text;
        msgBox.style.display = 'block';
        setTimeout(() => { msgBox.style.display = 'none'; }, 2600);
    }

    function hasPermission(key) {
        return state.permissions.has(key);
    }

    function setVisible(el, visible) {
        if (!el) return;
        el.style.display = visible ? '' : 'none';
    }

    async function loadPermissions() {
        const data = await getJson(baseUrl + '/funcionalidades');
        const keys = Array.isArray(data?.funcionalidades) ? data.funcionalidades : [];
        state.permissions = new Set(keys);
    }

    function activateFirstVisibleTab() {
        const defs = [
            { tab: tabRs, pane: paneRs },
            { tab: tabSe, pane: paneSe },
            { tab: tabSp, pane: paneSp },
            { tab: tabAu, pane: paneAu }
        ].filter(d => d.tab && d.pane && d.tab.style.display !== 'none');

        [tabRs, tabSe, tabSp, tabAu].forEach(tab => {
            if (tab) {
                tab.classList.remove('active');
                tab.setAttribute('aria-selected', 'false');
            }
        });
        [paneRs, paneSe, paneSp, paneAu].forEach(pane => {
            if (pane) pane.classList.remove('show', 'active');
        });

        if (!defs.length) {
            showMsg('El usuario no tiene tabs habilitados para Relaciones', 'warning');
            return;
        }

        defs[0].tab.classList.add('active');
        defs[0].tab.setAttribute('aria-selected', 'true');
        defs[0].pane.classList.add('show', 'active');
    }

    function applyPermissionsUI() {
        const canTabRs = hasPermission('RELACIONES_TAB_REGION_SUCURSAL');
        const canTabSe = hasPermission('RELACIONES_TAB_SUCURSAL_EMPLEADO');
        const canTabSp = hasPermission('RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO');
        const canTabAu = hasPermission('RELACIONES_TAB_HISTORIAL');

        setVisible(tabRs, canTabRs);
        setVisible(paneRs, canTabRs);
        setVisible(tabSe, canTabSe);
        setVisible(paneSe, canTabSe);
        setVisible(tabSp, canTabSp);
        setVisible(paneSp, canTabSp);
        setVisible(tabAu, canTabAu);
        setVisible(paneAu, canTabAu);

        setVisible(btnOpenRsUpload, hasPermission('RELACIONES_REGION_SUCURSAL_CARGAR_EXCEL'));
        setVisible(btnRsAddRecord, hasPermission('RELACIONES_REGION_SUCURSAL_AGREGAR'));
        setVisible(rsUndoMove, hasPermission('RELACIONES_REGION_SUCURSAL_MOVER'));
        setVisible(btnRsExportPdf, canTabRs);
        setVisible(btnRsExportXlsx, canTabRs);

        setVisible(btnOpenSeUpload, hasPermission('RELACIONES_SUCURSAL_EMPLEADO_CARGAR_EXCEL'));
        setVisible(btnSeAddRecord, hasPermission('RELACIONES_SUCURSAL_EMPLEADO_AGREGAR'));
        setVisible(btnSeMoverSeleccionados, hasPermission('RELACIONES_SUCURSAL_EMPLEADO_MOVER'));
        setVisible(seCheckAll, hasPermission('RELACIONES_SUCURSAL_EMPLEADO_MOVER'));
        setVisible(btnSeExportPdf, canTabSe);
        setVisible(btnSeExportXlsx, canTabSe);
        setVisible(btnSeCompareCore, canTabSe);
        setVisible(btnSeApplyCore, hasPermission('RELACIONES_SUCURSAL_EMPLEADO_EDITAR'));

        setVisible(btnOpenSpUpload, hasPermission('RELACIONES_PUESTO_PRODUCTO_CARGAR_EXCEL'));
        setVisible(btnSpAddRecord, hasPermission('RELACIONES_PUESTO_PRODUCTO_AGREGAR'));
        setVisible(btnAuRefresh, canTabAu);

        activateFirstVisibleTab();
    }

    async function askConfirm(title, text, dangerMode = false) {
        if (typeof swal === 'function') {
            try {
                const result = swal({
                    title: title,
                    text: text,
                    icon: 'warning',
                    buttons: ['Cancelar', 'Confirmar'],
                    dangerMode: dangerMode
                });
                if (result && typeof result.then === 'function') {
                    return !!(await result);
                }
            } catch (_) {
            }
        }
        return window.confirm((title || 'Confirmar') + '\n' + (text || ''));
    }

    function notifySwal(icon, title, text) {
        if (typeof swal === 'function') {
            try {
                swal({
                    title: title,
                    text: text,
                    icon: icon,
                    timer: 1800,
                    buttons: false
                });
                return;
            } catch (_) {
            }
        }
        const map = { success: 'success', error: 'danger', warning: 'warning', info: 'primary' };
        showMsg((title ? (title + ': ') : '') + (text || ''), map[icon] || 'primary');
    }

    function openUploadModal(ctx) {
        state.uploadContext = ctx;
        if (catalogUploadTitle) catalogUploadTitle.textContent = ctx.title || 'Carga de catalogo';
        if (catalogUploadHelp) catalogUploadHelp.textContent = ctx.help || 'Selecciona un archivo Excel (.xlsx / .xls).';
        if (catalogUploadFile) catalogUploadFile.value = '';
        if (catalogUploadFileName) catalogUploadFileName.textContent = 'Ningun archivo seleccionado';

        if (uploadModal) {
            uploadModal.show();
            return;
        }
        // fallback simple si bootstrap modal no esta disponible
        showMsg('No se pudo abrir modal. Verifica Bootstrap JS.', 'warning');
    }

    function fillSucursalSelect(selectEl, selectedValue) {
        if (!selectEl) return;
        const current = selectedValue ?? '';
        selectEl.innerHTML = '<option value="">Selecciona sucursal</option>';
        state.sucursalesAll.forEach(s => {
            const opt = document.createElement('option');
            opt.value = s.sucursalId;
            opt.textContent = s.sucursalId + ' - ' + s.nombreSucursal;
            selectEl.appendChild(opt);
        });
        if (current !== null && current !== undefined && String(current) !== '') {
            selectEl.value = String(current);
        }
    }

    function openRsRecordEditor(mode, row) {
        if (!rsRecordModal) return;
        if (rsRecordModalTitle) {
            rsRecordModalTitle.textContent = mode === 'edit' ? 'Editar Region - Sucursal' : 'Agregar Region / Sucursal';
        }
        if (rsRecordMode) rsRecordMode.value = mode;
        if (rsRecordId) rsRecordId.value = row?.id ?? '';
        if (rsRegionNum) rsRegionNum.value = row?.regionNum ?? '';
        if (rsRegionNom) rsRegionNom.value = row?.regionNom ?? '';
        if (rsSucursalNum) rsSucursalNum.value = row?.sucursalNum ?? '';
        if (rsSucursalNom) rsSucursalNom.value = row?.sucursalNom ?? '';
        if (rsSucursalEstatus) rsSucursalEstatus.value = (row?.sucursalEstatus || 'ACTIVA');
        if (rsCanales) rsCanales.value = (row?.canales || 'TRADICIONAL');
        rsRecordModal.show();
    }

    function openSeRecordEditor(mode, row) {
        if (!seRecordModal) return;
        fillSucursalSelect(seRecordSucursalNum, row?.sucursalNum);
        if (seRecordModalTitle) {
            seRecordModalTitle.textContent = mode === 'edit' ? 'Editar Sucursal - Empleado' : 'Agregar Sucursal - Empleado';
        }
        if (seRecordMode) seRecordMode.value = mode;
        if (seRecordId) seRecordId.value = row?.id ?? '';
        if (seEmpleadoNum) seEmpleadoNum.value = row?.empleadoNum ?? '';
        if (seEmpleadoNom) seEmpleadoNom.value = row?.empleadoNom ?? '';
        if (sePuestoId) sePuestoId.value = row?.puestoId ?? '';
        if (sePuestoDesc) sePuestoDesc.value = row?.puestoDesc ?? '';
        if (seAplicaMeta) seAplicaMeta.value = (row?.aplicaMeta || 'SI');
        if (seEstado) seEstado.value = row?.estado || 'ACTIVO';
        if (sePeriodoNum) sePeriodoNum.value = row?.periodoNum ?? '';
        if (seVigenciaInicio) seVigenciaInicio.value = row?.vigenciaInicio || '';
        if (seVigenciaFin) seVigenciaFin.value = row?.vigenciaFin || '';
        seRecordModal.show();
    }

    function openSpRecordEditor(mode, row) {
        if (!spRecordModal) return;
        fillSucursalSelect(spRecordSucursalNum, row?.sucursalNum ?? spSucursalSelect?.value ?? '');
        if (spRecordModalTitle) {
            spRecordModalTitle.textContent = mode === 'edit' ? 'Editar Sucursal - Puesto - Producto' : 'Agregar Sucursal - Puesto - Producto';
        }
        if (spRecordMode) spRecordMode.value = mode;
        if (spRecordId) spRecordId.value = row?.id ?? '';
        if (spRecordPuestoId) spRecordPuestoId.value = row?.puestoId ?? '';
        if (spRecordProductoNum) spRecordProductoNum.value = row?.productoNum ?? '';
        if (spRecordProductoNom) spRecordProductoNom.value = row?.productoNom ?? '';
        if (spRecordCanal) spRecordCanal.value = row?.canal || 'TRADICIONAL';
        if (spRecordFuerzaVenta) spRecordFuerzaVenta.value = row?.fuerzaVenta || 'SUCURSAL';
        if (spRecordPorcNuevo) spRecordPorcNuevo.value = row?.porcNuevo ?? 0;
        if (spRecordPorcRenovado) spRecordPorcRenovado.value = row?.porcRenovado ?? 0;
        if (spRecordVigenciaInicio) spRecordVigenciaInicio.value = row?.vigenciaInicio || '';
        if (spRecordVigenciaFin) spRecordVigenciaFin.value = row?.vigenciaFin || '';
        if (spRecordEstatus) spRecordEstatus.value = String(row?.estatus ?? 1);
        spRecordModal.show();
    }

    async function getJson(url) {
        const resp = await fetch(url);
        if (!resp.ok) throw new Error('Error HTTP ' + resp.status);
        return resp.json();
    }

    async function postJson(url, body) {
        const resp = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!resp.ok) throw new Error('Error HTTP ' + resp.status);
        return resp.json();
    }

    async function putJson(url, body) {
        const resp = await fetch(url, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        if (!resp.ok) throw new Error('Error HTTP ' + resp.status);
        return resp.json();
    }

    async function cargarCatalogoIndividual({ inputFile, button, fieldName, endpoint, successMsg }) {
        if (!inputFile || !inputFile.files.length) {
            showMsg('Selecciona un archivo Excel', 'warning');
            return false;
        }

        const originalText = button ? button.textContent : '';
        if (button) {
            button.disabled = true;
            button.textContent = 'Cargando...';
        }

        try {
            const formData = new FormData();
            formData.append(fieldName, inputFile.files[0]);
            const resp = await fetch(baseUrl + endpoint, { method: 'POST', body: formData });
            if (!resp.ok) throw new Error('Error HTTP ' + resp.status);
            const data = await resp.json();
            const errores = Array.isArray(data.errores) ? data.errores : [];
            if (errores.length) {
                const muestra = errores.slice(0, 3).map(e => `Fila ${e.fila}: ${e.mensaje}`).join(' | ');
                showMsg(`${successMsg} con advertencias (${errores.length}). ${muestra}`, 'warning');
            } else {
                showMsg(successMsg, 'success');
            }
            await initData();
            return true;
        } catch {
            showMsg('No se pudo cargar el archivo', 'danger');
            return false;
        } finally {
            if (button) {
                button.disabled = false;
                button.textContent = originalText;
            }
        }
    }

    function rebuildSucursalesCatalog() {
        const mapSuc = new Map();
        state.rsRows.forEach(r => {
            if (r.sucursalNum != null && !mapSuc.has(r.sucursalNum)) {
                mapSuc.set(r.sucursalNum, {
                    sucursalId: r.sucursalNum,
                    nombreSucursal: r.sucursalNom || ('SUCURSAL ' + r.sucursalNum),
                    regionNum: r.regionNum
                });
            }
        });
        state.sucursalesAll = Array.from(mapSuc.values()).sort((a, b) => a.sucursalId - b.sucursalId);
    }

    async function initData() {
        await loadPermissions();
        applyPermissionsUI();

        state.regiones = [];
        state.rsRows = [];
        state.seRows = [];
        state.seOrigenLoaded = null;
        state.sucursalesAll = [];

        const canTabRs = hasPermission('RELACIONES_TAB_REGION_SUCURSAL');
        const canTabSe = hasPermission('RELACIONES_TAB_SUCURSAL_EMPLEADO');
        const canTabSp = hasPermission('RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO');
        const canTabAu = hasPermission('RELACIONES_TAB_HISTORIAL');

        if (canTabRs) {
            state.regiones = await getJson(baseUrl + '/regiones');
            const rsData = await getJson(baseUrl + '/detalle/region-sucursal?page=1&size=5000');
            state.rsRows = rsData.items || [];
            rebuildSucursalesCatalog();
            renderRegionSucursalBoard();
            syncRsUndoButton();
        } else if (canTabSe || canTabSp) {
            const sucursales = await getJson(baseUrl + '/catalogo/sucursales');
            state.sucursalesAll = Array.isArray(sucursales) ? sucursales : [];
        }

        if (canTabSe) {
            refreshSucursalEmpleadoSelectors();
            await renderSucursalEmpleadoTable();
        }
        if (canTabSp) {
            refreshSucursalPuestoProductoSelector();
        }
        if (canTabAu) {
            await renderAuditoria();
        }
    }

    function normalizarEstatusSucursal(raw) {
        const s = String(raw || '').trim().toUpperCase();
        if (!s) return 'SIN_ESTATUS';
        if (s.includes('INACT')) return 'INACTIVA';
        if (s.includes('ACT')) return 'ACTIVA';
        return 'SIN_ESTATUS';
    }

    function siguienteEstatusSucursal(actual) {
        return actual === 'ACTIVA' ? 'INACTIVA' : 'ACTIVA';
    }

    function normalizarCanalSucursal(raw) {
        const s = String(raw || '').trim().toUpperCase();
        if (!s) return 'TRADICIONAL';
        if (s.includes('AMBOS')) return 'AMBOS';
        if (s.includes('NOMINA') || s === '2') return 'NOMINA';
        return 'TRADICIONAL';
    }

    function syncRsUndoButton() {
        if (!rsUndoMove) return;
        const enabled = !!state.rsUndoPayload;
        rsUndoMove.disabled = !enabled;
        rsUndoMove.title = enabled
            ? ('Revertir sucursal #' + state.rsUndoPayload.sucursalNum + ' a region ' + state.rsUndoPayload.fromRegion)
            : 'No hay movimientos para deshacer';
    }

    function syncRsStatusButtons() {
        if (!rsStatusButtonsWrap) return;
        const active = (rsStatusFilter?.value || 'ALL').toUpperCase();
        rsStatusButtonsWrap.querySelectorAll('.rs-status-btn').forEach(btn => {
            const status = String(btn.getAttribute('data-status') || '').toUpperCase();
            btn.classList.toggle('active', status === active);
        });
    }

    function renderRegionSucursalBoard() {
        if (!rsBoard || !hasPermission('RELACIONES_TAB_REGION_SUCURSAL')) return;
        const q = (rsSearch.value || '').trim().toLowerCase();
        const mode = (rsStatusFilter?.value || state.rsFilterMode || 'ALL').toUpperCase();
        const channelMode = (rsChannelFilter?.value || state.rsFilterChannel || '').toUpperCase();
        state.rsFilterMode = mode;
        state.rsFilterChannel = channelMode;
        syncRsStatusButtons();
        const canMove = hasPermission('RELACIONES_REGION_SUCURSAL_MOVER');
        const canEdit = hasPermission('RELACIONES_REGION_SUCURSAL_EDITAR');
        const map = new Map();
        state.rsRows.forEach(r => {
            const key = String(r.regionNum ?? 'SIN_REGION');
            if (!map.has(key)) {
                map.set(key, {
                    regionNum: r.regionNum,
                    regionNom: r.regionNom || ('REGION ' + (r.regionNum ?? 'SIN REGION')),
                    sucursales: []
                });
            }
            if (r.sucursalNum != null) {
                const estatus = normalizarEstatusSucursal(r.sucursalEstatus);
                map.get(key).sucursales.push({
                    id: r.id,
                    sucursalNum: r.sucursalNum,
                    sucursalNom: r.sucursalNom || ('SUCURSAL ' + r.sucursalNum),
                    sucursalEstatus: estatus,
                    canales: normalizarCanalSucursal(r.canales),
                    version: r.version || 1
                });
            }
        });
        const regions = Array.from(map.values()).sort((a, b) => (a.regionNum ?? 99999) - (b.regionNum ?? 99999));
        rsBoard.innerHTML = '';
        let visibleRegions = regions.length;
        let visibleSucursales = 0;
        regions.forEach(region => {
            const sucursalesFiltradas = region.sucursales.filter(s => {
                const texto = (String(s.sucursalNum) + ' ' + (s.sucursalNom || '') + ' ' + (region.regionNom || '')).toLowerCase();
                const matchText = !q || texto.includes(q);
                if (!matchText) return false;
                if (mode === 'SIN_REGION') return region.regionNum == null;
                if (mode === 'ACTIVA') return s.sucursalEstatus === 'ACTIVA';
                if (mode === 'INACTIVA') return s.sucursalEstatus === 'INACTIVA';
                if (channelMode && s.canales !== channelMode) return false;
                return true;
            });
            const col = document.createElement('div');
            col.className = 'rs-column';
            if (state.rsLastMove && region.regionNum === state.rsLastMove.toRegion) {
                col.classList.add('rs-column-hot');
            }
            col.innerHTML = '<div class="rs-column-head">' + (region.regionNum ?? '-') + ' - ' + (region.regionNom ?? '') + ' (' + sucursalesFiltradas.length + ')</div>'
                + '<div class="rs-dropzone" data-region="' + (region.regionNum ?? '') + '"></div>';
            const zone = col.querySelector('.rs-dropzone');
            if (canMove) {
                zone.addEventListener('dragover', (e) => { e.preventDefault(); zone.classList.add('drag-over'); });
                zone.addEventListener('dragleave', () => zone.classList.remove('drag-over'));
                zone.addEventListener('drop', async (e) => {
                    e.preventDefault();
                    zone.classList.remove('drag-over');
                    const raw = e.dataTransfer.getData('text/plain');
                    if (!raw) return;
                    const data = JSON.parse(raw);
                    const targetRegion = parseInt(zone.getAttribute('data-region') || '0', 10);
                    if (!targetRegion || data.regionNum === targetRegion) return;
                    try {
                        const ok = await askConfirm(
                            'Mover sucursal',
                            'Se movera la sucursal ' + data.sucursalNum + ' a la region ' + targetRegion + '. Deseas continuar?',
                            false
                        );
                        if (!ok) return;
                        await postJson(baseUrl + '/detalle/region-sucursal/relacionar', {
                            sucursalNum: data.sucursalNum,
                            regionNum: targetRegion
                        });
                        state.rsUndoPayload = (data.regionNum == null)
                            ? null
                            : {
                                sucursalNum: data.sucursalNum,
                                sucursalNom: data.sucursalNom,
                                fromRegion: data.regionNum,
                                toRegion: targetRegion
                            };
                        state.rsLastMove = {
                            sucursalNum: data.sucursalNum,
                            toRegion: targetRegion
                        };
                        notifySwal('success', 'Cambio aplicado', 'Sucursal ' + data.sucursalNum + ' movida a region ' + targetRegion);
                        await initData();
                    } catch {
                        notifySwal('error', 'Error', 'No se pudo mover la sucursal');
                    }
                });
            }
            sucursalesFiltradas.forEach(s => {
                const statusClass = s.sucursalEstatus === 'ACTIVA'
                    ? 'activa'
                    : (s.sucursalEstatus === 'INACTIVA' ? 'inactiva' : 'sin-estatus');
                const statusLabel = s.sucursalEstatus === 'ACTIVA'
                    ? 'ACTIVA'
                    : (s.sucursalEstatus === 'INACTIVA' ? 'INACTIVA' : 'SIN ESTATUS');
                const card = document.createElement('div');
                card.className = 'rs-card';
                if (state.rsLastMove
                    && state.rsLastMove.sucursalNum === s.sucursalNum
                    && state.rsLastMove.toRegion === region.regionNum) {
                    card.classList.add('rs-card-moved');
                }
                card.draggable = canMove;
                card.innerHTML =
                    '<div class="rs-card-top">' +
                    '<span class="rs-card-code">#' + s.sucursalNum + '</span>' +
                    '<span class="rs-card-status ' + statusClass + '">' + statusLabel + '</span>' +
                    '</div>' +
                    '<div class="rs-card-name">' + s.sucursalNom + '</div>' +
                    '<div class="mt-1 d-flex gap-1 flex-wrap">'
                    + '<span class="badge bg-info text-dark">' + s.canales + '</span>'
                    + '<span class="badge bg-light text-dark">v' + (s.version || 1) + '</span>'
                    + '</div>' +
                    (canEdit
                        ? '<div class="mt-1 d-flex justify-content-end gap-1">'
                            + '<button type="button" class="btn btn-sm btn-outline-secondary rs-edit-record" title="Editar registro"><i class="fas fa-pen"></i></button>'
                            + '<button type="button" class="btn btn-sm btn-outline-primary rs-toggle-status">'
                            + (s.sucursalEstatus === 'ACTIVA' ? 'Inactivar' : 'Activar')
                            + '</button></div>'
                        : '');
                if (canMove) {
                    card.addEventListener('dragstart', (e) => {
                        e.dataTransfer.setData('text/plain', JSON.stringify({
                            sucursalNum: s.sucursalNum,
                            sucursalNom: s.sucursalNom,
                            regionNum: region.regionNum
                        }));
                    });
                }
                const btnToggle = card.querySelector('.rs-toggle-status');
                const btnEdit = card.querySelector('.rs-edit-record');
                if (btnEdit) {
                    btnEdit.draggable = false;
                    btnEdit.addEventListener('dragstart', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                    btnEdit.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        openRsRecordEditor('edit', {
                            id: s.id,
                            regionNum: region.regionNum,
                            regionNom: region.regionNom,
                            sucursalNum: s.sucursalNum,
                            sucursalNom: s.sucursalNom,
                            sucursalEstatus: s.sucursalEstatus,
                            canales: s.canales
                        });
                    });
                }
                if (btnToggle) {
                    btnToggle.draggable = false;
                    btnToggle.addEventListener('dragstart', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                    });
                    btnToggle.addEventListener('mousedown', (e) => {
                        e.stopPropagation();
                    });
                    btnToggle.addEventListener('click', async (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        if (!s.id) {
                            showMsg('No se pudo identificar el registro de la sucursal', 'warning');
                            return;
                        }
                        const nuevoEstatus = siguienteEstatusSucursal(s.sucursalEstatus);
                        const ok = await askConfirm(
                            (nuevoEstatus === 'ACTIVA' ? 'Activar sucursal' : 'Inactivar sucursal'),
                            'Se cambiara el estatus de la sucursal ' + s.sucursalNum + ' a ' + nuevoEstatus + '. Deseas continuar?',
                            nuevoEstatus === 'INACTIVA'
                        );
                        if (!ok) return;
                        btnToggle.disabled = true;
                        try {
                            await putJson(baseUrl + '/detalle/region-sucursal/' + encodeURIComponent(s.id), {
                                regionNum: region.regionNum,
                                sucursalNum: s.sucursalNum,
                                regionNom: region.regionNom,
                                sucursalNom: s.sucursalNom,
                                sucursalEstatus: nuevoEstatus,
                                canales: s.canales
                            });
                            notifySwal('success', 'Estatus actualizado', 'Sucursal ' + s.sucursalNum + ' -> ' + nuevoEstatus);
                            await initData();
                        } catch {
                            notifySwal('error', 'Error', 'No se pudo actualizar el estatus de la sucursal');
                        } finally {
                            btnToggle.disabled = false;
                        }
                    });
                }
                zone.appendChild(card);
            });
            if (!zone.children.length) {
                const empty = document.createElement('div');
                empty.className = 'rs-empty';
                empty.textContent = (q || mode !== 'ALL')
                    ? 'Sin sucursales para los filtros aplicados'
                    : 'Sin sucursales asignadas en esta region';
                zone.appendChild(empty);
            }
            rsBoard.appendChild(col);
            visibleSucursales += sucursalesFiltradas.length;
        });
        if (rsTotalRegiones) rsTotalRegiones.textContent = String(visibleRegions);
        if (rsTotalSucursales) rsTotalSucursales.textContent = String(visibleSucursales);
    }

    function refreshSucursalEmpleadoSelectors() {
        if (!seOrigenSelect || !seDestinoSelect) return;
        const prevOrigen = seOrigenSelect.value;
        const prevDestino = seDestinoSelect.value;

        seOrigenSelect.innerHTML = '<option value="">Selecciona origen</option>';
        seDestinoSelect.innerHTML = '<option value="">Selecciona destino</option>';

        state.sucursalesAll.forEach(s => {
            const label = s.sucursalId + ' - ' + s.nombreSucursal;
            const origenOpt = document.createElement('option');
            origenOpt.value = s.sucursalId;
            origenOpt.textContent = label;
            seOrigenSelect.appendChild(origenOpt);

            const destinoOpt = document.createElement('option');
            destinoOpt.value = s.sucursalId;
            destinoOpt.textContent = label;
            seDestinoSelect.appendChild(destinoOpt);
        });

        if (prevOrigen) seOrigenSelect.value = prevOrigen;
        if (prevDestino) seDestinoSelect.value = prevDestino;
    }

    function buildSucursalEmpleadoQueryParams() {
        const params = new URLSearchParams();
        const q = (seSearch?.value || '').trim();
        const region = (seRegionFilter?.value || '').trim();
        const sucursal = seOrigenSelect?.value || '';
        const puesto = (sePuestoFilter?.value || '').trim();
        const vigencia = seVigenciaFilter?.value || '';

        params.append('page', '1');
        params.append('size', '5000');
        if (q) params.append('search', q);
        if (region) params.append('regionNum', region);
        if (sucursal) params.append('sucursalNum', sucursal);
        if (puesto) params.append('puestoId', puesto);
        if (vigencia) params.append('vigencia', vigencia);
        return params;
    }

    function exportSucursalEmpleado(tipo) {
        const params = buildSucursalEmpleadoQueryParams();
        params.delete('page');
        params.delete('size');
        const endpoint = tipo === 'pdf'
            ? '/detalle/empleado-sucursal/exportar-pdf'
            : '/detalle/empleado-sucursal/exportar-xlsx';
        window.open(baseUrl + endpoint + (params.toString() ? ('?' + params.toString()) : ''), '_blank');
    }

    function compareStatusBadgeClass(status) {
        return status === 'SOLO_CORE'
            ? 'bg-primary'
            : status === 'SOLO_EXCEL'
                ? 'bg-warning text-dark'
                : status === 'DIFERENTE'
                    ? 'bg-danger'
                    : 'bg-success';
    }

    function getCompareVisibleRows() {
        const status = seCoreStatusFilter?.value || 'TODOS';
        const q = (seCoreSearch?.value || '').trim().toLowerCase();
        return (Array.isArray(state.seCompareRows) ? state.seCompareRows : []).filter(item => {
            if (status !== 'TODOS' && item.estadoComparativo !== status) {
                return false;
            }
            if (!q) {
                return true;
            }
            const text = [
                item.empleadoNum,
                item.actual?.empleadoNom,
                item.actual?.sucursalNom,
                item.actual?.puestoDesc,
                item.core?.empleadoNom,
                item.core?.sucursalNom,
                item.core?.puestoDesc,
                item.resumenDiferencias
            ].join(' ').toLowerCase();
            return text.includes(q);
        });
    }

    function formatCompareGrid(row) {
        if (!row) {
            return '<div class="text-muted">Sin registro</div>';
        }
        const puestoLabel = row.puestoId
            ? (row.puestoId + ' - ' + (row.puestoDesc || '-'))
            : (row.puestoDesc || '-');
        return [
            '<div class="se-compare-grid">',
            '<strong>Nombre</strong><span>' + (row.empleadoNom || '-') + '</span>',
            '<strong>Region</strong><span>' + (row.regionNum || '-') + '</span>',
            '<strong>Sucursal</strong><span>' + (row.sucursalNum || '-') + ' - ' + (row.sucursalNom || '-') + '</span>',
            '<strong>Puesto</strong><span>' + puestoLabel + '</span>',
            '<strong>Estado</strong><span>' + (row.estado || '-') + '</span>',
            '</div>'
        ].join('');
    }

    function buildDiffChips(item) {
        const summary = (item.resumenDiferencias || '').split(',').map(v => v.trim()).filter(Boolean);
        if (!summary.length || summary[0] === 'Sin diferencias') {
            return '<span class="se-compare-chip">Sin cambios</span>';
        }
        return summary.map(v => '<span class="se-compare-chip">' + v + '</span>').join('');
    }

    function renderSeCoreCompare() {
        if (!seCoreCompareCards) return;
        const rows = getCompareVisibleRows();
        seCoreCompareCards.innerHTML = '';
        if (seCoreCompareInfo) {
            seCoreCompareInfo.textContent = 'Registros visibles: ' + rows.length + '. Selecciona solo los que realmente quieras actualizar desde core.';
        }
        if (!rows.length) {
            if (seCoreCompareEmpty) seCoreCompareEmpty.style.display = '';
            return;
        }
        if (seCoreCompareEmpty) seCoreCompareEmpty.style.display = 'none';

        rows.forEach(item => {
            const card = document.createElement('article');
            card.className = 'se-compare-card';
            const canSelect = item.estadoComparativo !== 'IGUAL';
            const checked = state.seCompareSelected.has(item.empleadoNum);
            card.innerHTML = [
                '<div class="se-compare-card-head">',
                '<div class="se-compare-identity">',
                canSelect ? ('<input type="checkbox" class="form-check-input se-compare-check" data-id="' + item.empleadoNum + '"' + (checked ? ' checked' : '') + '>') : '',
                '<div>',
                '<div class="se-compare-title">' + (item.actual?.empleadoNom || item.core?.empleadoNom || ('Empleado ' + item.empleadoNum)) + '</div>',
                '<div class="se-compare-subtitle">Empleado #' + (item.empleadoNum || '-') + '</div>',
                '</div>',
                '</div>',
                '<span class="badge ' + compareStatusBadgeClass(item.estadoComparativo) + '">' + (item.estadoComparativo || '') + '</span>',
                '</div>',
                '<div class="se-compare-diffchips">' + buildDiffChips(item) + '</div>',
                '<div class="se-compare-body">',
                '<section class="se-compare-side"><div class="se-compare-side-title">Catalogo actual</div>' + formatCompareGrid(item.actual) + '</section>',
                '<section class="se-compare-side"><div class="se-compare-side-title">Core bancario</div>' + formatCompareGrid(item.core) + '</section>',
                '</div>',
                canSelect ? '<div class="se-compare-card-actions"><button type="button" class="btn btn-sm btn-outline-primary se-compare-apply-one" data-id="' + item.empleadoNum + '"><i class="fas fa-arrows-rotate" aria-hidden="true"></i> Aplicar core</button></div>' : ''
            ].join('');

            const check = card.querySelector('.se-compare-check');
            if (check) {
                check.addEventListener('change', () => {
                    const id = Number(check.getAttribute('data-id'));
                    if (check.checked) {
                        state.seCompareSelected.add(id);
                    } else {
                        state.seCompareSelected.delete(id);
                    }
                });
            }

            const applyBtn = card.querySelector('.se-compare-apply-one');
            if (applyBtn) {
                applyBtn.addEventListener('click', async () => {
                    state.seCompareSelected = new Set([Number(applyBtn.getAttribute('data-id'))]);
                    await aplicarCoreSeleccionado();
                });
            }
            seCoreCompareCards.appendChild(card);
        });
    }

    async function abrirComparativoCoreEmpleado() {
        const params = buildSucursalEmpleadoQueryParams();
        params.delete('page');
        params.delete('size');
        params.append('soloDiferencias', 'true');
        try {
            const data = await getJson(baseUrl + '/comparativo/empleado-core?' + params.toString());
            state.seCompareRows = Array.isArray(data?.items) ? data.items : [];
            state.seCompareSelected = new Set(state.seCompareRows
                .filter(item => item.estadoComparativo !== 'IGUAL')
                .map(item => Number(item.empleadoNum)));
            const resumen = data?.resumen || {};
            if (seCoreCompareSummary) {
                seCoreCompareSummary.innerHTML =
                    '<strong>Resumen:</strong> ' +
                    'Diferentes: ' + (resumen.diferentes || 0) +
                    ' | Solo core: ' + (resumen.soloCore || 0) +
                    ' | Solo catalogo: ' + (resumen.soloExcel || 0);
            }
            if (seCoreCompareInfo) {
                seCoreCompareInfo.textContent = 'Selecciona los empleados sobre los que deseas aplicar la informacion del core. Los no seleccionados conservaran el catalogo actual.';
            }
            renderSeCoreCompare();
            if (seCoreCompareModal) seCoreCompareModal.show();
        } catch (_) {
            notifySwal('error', 'Error', 'No se pudo consultar el comparativo contra core');
        }
    }

    async function aplicarCoreSeleccionado() {
        const selectedIds = Array.from(state.seCompareSelected);
        if (!selectedIds.length) {
            return notifySwal('warning', 'Dato requerido', 'Selecciona al menos un empleado del comparativo');
        }
        const ok = await askConfirm(
            'Aplicar Core',
            'Se aplicara la informacion del core a ' + selectedIds.length + ' empleados seleccionados. Los no seleccionados conservaran el catalogo actual. Deseas continuar?',
            true
        );
        if (!ok) return;
        try {
            const result = await postJson(baseUrl + '/comparativo/empleado-core/aplicar', {
                decision: 'CORE',
                empleadoNums: selectedIds
            });
            notifySwal(
                'success',
                'Core aplicado',
                'Insertados: ' + (result.insertados || 0) + ', actualizados: ' + (result.actualizados || 0) + ', desactivados: ' + (result.desactivados || 0)
            );
            if (seCoreCompareModal) seCoreCompareModal.hide();
            await initData();
            await renderSucursalEmpleadoTable();
        } catch (_) {
            notifySwal('error', 'Error', 'No se pudo aplicar la informacion del core');
        }
    }

    async function renderSucursalEmpleadoTable() {
        if (!tbodyEmpleadoReasignacion || !hasPermission('RELACIONES_TAB_SUCURSAL_EMPLEADO')) return;
        const origen = parseInt(seOrigenSelect?.value || '0', 10) || null;
        const canMove = hasPermission('RELACIONES_SUCURSAL_EMPLEADO_MOVER');
        const canEdit = hasPermission('RELACIONES_SUCURSAL_EMPLEADO_EDITAR');
        const canHistory = hasPermission('RELACIONES_HISTORIAL_CONSULTAR');
        const params = buildSucursalEmpleadoQueryParams();

        try {
            const data = await getJson(baseUrl + '/detalle/empleado-sucursal?' + params.toString());
            state.seRows = Array.isArray(data?.items) ? data.items : [];
        } catch (_) {
            state.seRows = [];
            tbodyEmpleadoReasignacion.innerHTML = '';
            if (seInfo) seInfo.innerHTML = '<i class="fas fa-circle-info" aria-hidden="true"></i> No se pudo consultar el detalle de empleados';
            return;
        }

        const rows = state.seRows;
        tbodyEmpleadoReasignacion.innerHTML = '';
        rows.forEach(emp => {
            const tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + (canMove ? ('<input type="checkbox" class="se-check" value="' + emp.id + '">') : '') + '</td>' +
                '<td>' + (emp.empleadoNum ?? '') + '</td>' +
                '<td>' + (emp.empleadoNom ?? '') + '</td>' +
                '<td>' + (emp.puestoDesc ?? '') + '</td>' +
                '<td>' + (emp.sucursalNum ?? '') + ' - ' + (emp.sucursalNom ?? '') + '</td>' +
                '<td>' + ((emp.vigenciaInicio || '-') + ' a ' + (emp.vigenciaFin || '-')) + '</td>' +
                '<td><span class="badge ' + ((emp.vigenciaEstado || 'ACTUAL') === 'ACTUAL' ? 'bg-success' : 'bg-secondary') + '">' + (emp.vigenciaEstado || 'ACTUAL') + '</span></td>' +
                '<td class="text-nowrap">' +
                (canMove ? '<button class="btn btn-sm btn-outline-primary se-move-one"><i class="fas fa-right-left" aria-hidden="true"></i> Mover</button> ' : '') +
                (canEdit ? '<button class="btn btn-sm btn-outline-secondary se-edit-one" title="Editar"><i class="fas fa-pen" aria-hidden="true"></i></button> ' : '') +
                (canHistory ? '<button class="btn btn-sm btn-outline-info se-history-one" title="Historial"><i class="fas fa-clock-rotate-left" aria-hidden="true"></i></button>' : '') +
                '</td>';
            const moveBtn = tr.querySelector('.se-move-one');
            if (moveBtn) {
                moveBtn.addEventListener('click', async () => {
                    const dest = parseInt(seDestinoSelect?.value || '0', 10);
                    if (!dest) return notifySwal('warning', 'Dato requerido', 'Selecciona sucursal destino');
                    if (dest === emp.sucursalNum) return notifySwal('info', 'Sin cambios', 'El empleado ya esta en esa sucursal');
                    if (!origen) return notifySwal('warning', 'Dato requerido', 'Selecciona una sucursal origen');
                    try {
                        const ok = await askConfirm(
                            'Mover empleado',
                            'Se movera a ' + (emp.empleadoNom || ('empleado ' + (emp.empleadoNum || ''))) + ' a la sucursal destino seleccionada. Deseas continuar?',
                            false
                        );
                        if (!ok) return;
                        await putJson(baseUrl + '/detalle/empleado-sucursal/' + encodeURIComponent(emp.id), { sucursalNum: dest });
                        notifySwal('success', 'Cambio aplicado', 'Empleado movido correctamente');
                        await initData();
                        seOrigenSelect.value = String(origen || '');
                        seDestinoSelect.value = String(dest);
                        await renderSucursalEmpleadoTable();
                    } catch {
                        notifySwal('error', 'Error', 'No se pudo mover el empleado');
                    }
                });
            }
            const editBtn = tr.querySelector('.se-edit-one');
            if (editBtn) {
                editBtn.addEventListener('click', () => {
                    openSeRecordEditor('edit', emp);
                });
            }
            const historyBtn = tr.querySelector('.se-history-one');
            if (historyBtn) {
                historyBtn.addEventListener('click', async () => {
                    try {
                        const hist = await getJson(baseUrl + '/detalle/empleado-sucursal/' + encodeURIComponent(emp.id) + '/historial');
                        if (!Array.isArray(hist) || !hist.length) {
                            return notifySwal('info', 'Historial', 'Sin movimientos historicos para este empleado');
                        }
                        const resumen = hist.slice(0, 8).map(h =>
                            (h.fecha || '') + ' | ' + (h.evento || '') + ' | Suc:' + (h.sucursalNum || '-') + ' | Puesto:' + (h.puestoDesc || '-') +
                            ' | ' + (h.vigenciaInicio || '-') + ' a ' + (h.vigenciaFin || '-') + ' | v' + (h.version || 1)
                        ).join('\n');
                        if (typeof swal === 'function') {
                            swal({ title: 'Historial del empleado', text: resumen, icon: 'info' });
                        } else {
                            alert(resumen);
                        }
                    } catch {
                        notifySwal('error', 'Error', 'No se pudo consultar historial del empleado');
                    }
                });
            }
            tbodyEmpleadoReasignacion.appendChild(tr);
        });
        if (seInfo) {
            const info = [];
            info.push('Registros mostrados: ' + rows.length);
            if (origen) info.push('Sucursal origen: ' + origen);
            if (seVigenciaFilter?.value) info.push('Vigencia: ' + seVigenciaFilter.value);
            seInfo.innerHTML = '<i class="fas fa-users" aria-hidden="true"></i> ' + info.join(' | ');
        }
    }

    async function moverEmpleadosSeleccionados() {
        if (!hasPermission('RELACIONES_SUCURSAL_EMPLEADO_MOVER')) return;
        const dest = parseInt(seDestinoSelect?.value || '0', 10);
        if (!dest) return notifySwal('warning', 'Dato requerido', 'Selecciona sucursal destino');
        const checks = Array.from(tbodyEmpleadoReasignacion.querySelectorAll('.se-check')).filter(c => c.checked);
        if (!checks.length) return notifySwal('warning', 'Dato requerido', 'Selecciona al menos un empleado');

        const okConfirm = await askConfirm(
            'Mover seleccionados',
            'Se moveran ' + checks.length + ' empleados a la sucursal destino seleccionada. Â¿Deseas continuar?',
            false
        );
        if (!okConfirm) return;

        let ok = 0;
        for (const c of checks) {
            try {
                await putJson(baseUrl + '/detalle/empleado-sucursal/' + encodeURIComponent(c.value), { sucursalNum: dest });
                ok++;
            } catch (_) {
            }
        }
        if (ok > 0) {
            notifySwal('success', 'Proceso completado', 'Empleados movidos: ' + ok + ' de ' + checks.length);
        } else {
            notifySwal('error', 'Sin cambios', 'No se pudo mover ningun empleado');
        }
        await initData();
        seOrigenSelect.value = String(dest);
        seDestinoSelect.value = '';
        await renderSucursalEmpleadoTable();
    }

    function refreshSucursalPuestoProductoSelector() {
        if (!spSucursalSelect) return;
        const current = spSucursalSelect.value;
        spSucursalSelect.innerHTML = '<option value="">Selecciona una sucursal</option>';
        state.sucursalesAll.forEach(s => {
            const opt = document.createElement('option');
            opt.value = s.sucursalId;
            opt.textContent = s.sucursalId + ' - ' + s.nombreSucursal;
            spSucursalSelect.appendChild(opt);
        });
        if (current) spSucursalSelect.value = current;
        renderSucursalPuestoProductoBoard();
    }

        async function renderSucursalPuestoProductoBoard() {
        if (!spBoard || !hasPermission('RELACIONES_TAB_SUCURSAL_PUESTO_PRODUCTO')) return;
        const sucursalId = parseInt(spSucursalSelect?.value || '0', 10);
        const canEdit = hasPermission('RELACIONES_PUESTO_PRODUCTO_EDITAR');
        const canHistory = hasPermission('RELACIONES_HISTORIAL_CONSULTAR');
        if (!sucursalId) {
            spBoard.innerHTML = '<div class="sp-empty"><i class="fas fa-circle-info" aria-hidden="true"></i> Selecciona una sucursal para ver puestos y productos.</div>';
            return;
        }
        const q = (spSearch.value || '').trim().toLowerCase();
        const canalFilter = (spCanalFilter?.value || '').toUpperCase();
        const fuerzaFilter = (spFuerzaFilter?.value || '').toUpperCase();
        const estatusFilter = spEstatusFilter?.value || '';
        const puestos = await getJson(baseUrl + '/puestos-por-sucursal?sucursalId=' + encodeURIComponent(sucursalId));
        if (!puestos || !puestos.length) {
            spBoard.innerHTML = '<div class="sp-empty"><i class="fas fa-box-open" aria-hidden="true"></i> No hay puestos disponibles para esta sucursal.</div>';
            return;
        }
        const cols = await Promise.all(
            puestos.map(async (p) => {
                const rows = await getJson(baseUrl + '/detalle/puesto-producto-por-sucursal-puesto?sucursalId=' + encodeURIComponent(sucursalId) + '&puestoId=' + encodeURIComponent(p.clavePuestoId));
                return { puesto: p, items: rows || [] };
            })
        );
        spBoard.innerHTML = '';
        cols.forEach(c => {
            const col = document.createElement('div');
            col.className = 'rel-column';
            col.innerHTML = '<div class="rel-column-head">' + c.puesto.clavePuestoId + ' - ' + c.puesto.nombrePuesto + '</div>'
                + '<div class="rel-dropzone" data-puesto="' + c.puesto.clavePuestoId + '"></div>';
            const zone = col.querySelector('.rel-dropzone');
            if (canEdit) {
                zone.addEventListener('dragover', (e) => { e.preventDefault(); zone.classList.add('drag-over'); });
                zone.addEventListener('dragleave', () => zone.classList.remove('drag-over'));
                zone.addEventListener('drop', async (e) => {
                    e.preventDefault();
                    zone.classList.remove('drag-over');
                    const raw = e.dataTransfer.getData('text/plain');
                    if (!raw) return;
                    const data = JSON.parse(raw);
                    const targetPuesto = parseInt(zone.getAttribute('data-puesto') || '0', 10);
                    if (!targetPuesto || data.puestoId === targetPuesto) return;
                    try {
                        const ok = await askConfirm(
                            'Mover producto de puesto',
                            'Se movera el producto al puesto ' + targetPuesto + '. Deseas continuar?',
                            false
                        );
                        if (!ok) return;
                        await putJson(baseUrl + '/detalle/puesto-producto/' + encodeURIComponent(data.id), {
                            sucursalNum: sucursalId,
                            puestoId: targetPuesto,
                            productoNom: data.productoNom,
                            porcNuevo: data.porcNuevo,
                            porcRenovado: data.porcRenovado
                        });
                        notifySwal('success', 'Cambio aplicado', 'Producto movido al puesto ' + targetPuesto);
                        await renderSucursalPuestoProductoBoard();
                    } catch {
                        notifySwal('error', 'Error', 'No se pudo mover el producto');
                    }
                });
            }
            c.items
                .filter(it => {
                    const textOk = !q || ((it.productoNom || '') + ' ' + (it.productoNum || '') + ' ' + c.puesto.nombrePuesto).toLowerCase().includes(q);
                    if (!textOk) return false;
                    if (canalFilter && String(it.canal || '').toUpperCase() !== canalFilter) return false;
                    if (fuerzaFilter && String(it.fuerzaVenta || '').toUpperCase() !== fuerzaFilter) return false;
                    if (estatusFilter !== '' && String(it.estatus ?? 1) !== estatusFilter) return false;
                    return true;
                })
                .forEach(it => {
                    const estatusActivo = Number(it.estatus ?? 1) === 1;
                    const card = document.createElement('div');
                    card.className = 'rel-card';
                    card.draggable = canEdit;
                    card.innerHTML = '<div><strong>' + (it.productoNum || '') + '</strong> - ' + (it.productoNom || '') + '</div>'
                        + '<div class="mt-1 d-flex gap-1 flex-wrap">'
                        + '<span class="badge bg-info text-dark">' + (it.canal || 'TRADICIONAL') + '</span>'
                        + '<span class="badge bg-secondary">' + (it.fuerzaVenta || 'SUCURSAL') + '</span>'
                        + '<span class="badge ' + (estatusActivo ? 'bg-success' : 'bg-danger') + '">' + (estatusActivo ? 'ACTIVO' : 'INACTIVO') + '</span>'
                        + '<span class="badge bg-light text-dark">v' + (it.version || 1) + '</span>'
                        + '</div>'
                        + '<div class="mt-1"><small>% Nuevo</small><input type="number" step="0.00000001" class="form-control form-control-sm sp-nuevo" value="' + (it.porcNuevo ?? 0) + '" ' + (canEdit ? '' : 'disabled') + '></div>'
                        + '<div class="mt-1"><small>% Renovado</small><input type="number" step="0.00000001" class="form-control form-control-sm sp-ren" value="' + (it.porcRenovado ?? 0) + '" ' + (canEdit ? '' : 'disabled') + '></div>'
                        + '<div class="mt-1"><small>Vigencia</small><div class="small text-muted">' + (it.vigenciaInicio || '-') + ' a ' + (it.vigenciaFin || '-') + '</div></div>'
                        + '<div class="d-flex gap-1 mt-2">'
                        + (canEdit ? '<button class="btn btn-sm btn-outline-primary w-100 sp-save-one">Guardar %</button>' : '')
                        + (canEdit ? '<button class="btn btn-sm btn-outline-warning sp-toggle-status" title="' + (estatusActivo ? 'Inactivar' : 'Activar') + '"><i class="fas fa-power-off" aria-hidden="true"></i></button>' : '')
                        + (canHistory ? '<button class="btn btn-sm btn-outline-info sp-history-one" title="Ver historial"><i class="fas fa-clock-rotate-left" aria-hidden="true"></i></button>' : '')
                        + (canEdit ? '<button class="btn btn-sm btn-outline-secondary sp-edit-one" title="Editar registro"><i class="fas fa-pen" aria-hidden="true"></i></button>' : '')
                        + '</div>';
                    if (canEdit) {
                        card.addEventListener('dragstart', (e) => {
                            const nuevo = parseFloat(card.querySelector('.sp-nuevo').value || '0');
                            const ren = parseFloat(card.querySelector('.sp-ren').value || '0');
                            e.dataTransfer.setData('text/plain', JSON.stringify({
                                id: it.id,
                                puestoId: it.puestoId,
                                productoNom: it.productoNom,
                                porcNuevo: nuevo,
                                porcRenovado: ren
                            }));
                        });
                        card.querySelector('.sp-save-one').addEventListener('click', async () => {
                            try {
                                const ok = await askConfirm(
                                    'Guardar porcentajes',
                                    'Se guardaran los porcentajes Nuevo/Renovado para este producto. Deseas continuar?',
                                    false
                                );
                                if (!ok) return;
                                await putJson(baseUrl + '/detalle/puesto-producto/' + encodeURIComponent(it.id), {
                                    sucursalNum: sucursalId,
                                    puestoId: it.puestoId,
                                    productoNom: it.productoNom,
                                    porcNuevo: parseFloat(card.querySelector('.sp-nuevo').value || '0'),
                                    porcRenovado: parseFloat(card.querySelector('.sp-ren').value || '0'),
                                    canal: it.canal,
                                    fuerzaVenta: it.fuerzaVenta,
                                    vigenciaInicio: it.vigenciaInicio,
                                    vigenciaFin: it.vigenciaFin,
                                    estatus: it.estatus
                                });
                                notifySwal('success', 'Guardado', 'Porcentajes actualizados');
                            } catch {
                                notifySwal('error', 'Error', 'No se pudo actualizar porcentajes');
                            }
                        });
                        card.querySelector('.sp-toggle-status').addEventListener('click', async () => {
                            const nuevoEstatus = estatusActivo ? 0 : 1;
                            const ok = await askConfirm(
                                nuevoEstatus === 1 ? 'Activar registro' : 'Inactivar registro',
                                'Se actualizara el estatus del registro. Deseas continuar?',
                                nuevoEstatus === 0
                            );
                            if (!ok) return;
                            try {
                                await putJson(baseUrl + '/detalle/puesto-producto/' + encodeURIComponent(it.id), {
                                    sucursalNum: sucursalId,
                                    puestoId: it.puestoId,
                                    productoNom: it.productoNom,
                                    porcNuevo: parseFloat(card.querySelector('.sp-nuevo').value || '0'),
                                    porcRenovado: parseFloat(card.querySelector('.sp-ren').value || '0'),
                                    canal: it.canal,
                                    fuerzaVenta: it.fuerzaVenta,
                                    vigenciaInicio: it.vigenciaInicio,
                                    vigenciaFin: nuevoEstatus === 0 ? (new Date().toISOString().slice(0, 10)) : it.vigenciaFin,
                                    estatus: nuevoEstatus
                                });
                                notifySwal('success', 'Estatus actualizado', nuevoEstatus === 1 ? 'Registro activado' : 'Registro inactivado');
                                await renderSucursalPuestoProductoBoard();
                            } catch {
                                notifySwal('error', 'Error', 'No se pudo actualizar estatus');
                            }
                        });
                        card.querySelector('.sp-edit-one').addEventListener('click', () => {
                            openSpRecordEditor('edit', {
                                id: it.id,
                                sucursalNum: sucursalId,
                                puestoId: it.puestoId,
                                productoNum: it.productoNum,
                                productoNom: it.productoNom,
                                porcNuevo: it.porcNuevo,
                                porcRenovado: it.porcRenovado,
                                canal: it.canal,
                                fuerzaVenta: it.fuerzaVenta,
                                vigenciaInicio: it.vigenciaInicio,
                                vigenciaFin: it.vigenciaFin,
                                estatus: it.estatus
                            });
                        });
                    }
                    const historyBtn = card.querySelector('.sp-history-one');
                    if (historyBtn) {
                        historyBtn.addEventListener('click', async () => {
                            try {
                                const hist = await getJson(baseUrl + '/detalle/puesto-producto/' + encodeURIComponent(it.id) + '/historial');
                                if (!Array.isArray(hist) || !hist.length) {
                                    return notifySwal('info', 'Historial', 'Sin movimientos historicos para este registro');
                                }
                                const resumen = hist.slice(0, 8).map(h =>
                                    (h.fecha || '') + ' | ' + (h.evento || '') + ' | v' + (h.version || 1)
                                    + ' | N:' + (h.porcNuevo ?? 0) + ' R:' + (h.porcRenovado ?? 0)
                                    + ' | ' + (h.usuario || '')
                                ).join('\n');
                                if (typeof swal === 'function') {
                                    swal({
                                        title: 'Historial del registro',
                                        text: resumen,
                                        icon: 'info'
                                    });
                                } else {
                                    alert(resumen);
                                }
                            } catch {
                                notifySwal('error', 'Error', 'No se pudo consultar historial');
                            }
                        });
                    }
                    zone.appendChild(card);
                });
            if (!zone.children.length) {
                const empty = document.createElement('div');
                empty.className = 'text-muted small';
                empty.textContent = 'Sin productos para este puesto';
                zone.appendChild(empty);
            }
            spBoard.appendChild(col);
        });
    }

    function initTabRegionSucursal() {
        if (rsSearch) rsSearch.addEventListener('input', renderRegionSucursalBoard);
        if (rsStatusFilter) rsStatusFilter.addEventListener('change', renderRegionSucursalBoard);
        if (rsChannelFilter) rsChannelFilter.addEventListener('change', renderRegionSucursalBoard);
        if (rsStatusButtonsWrap) {
            rsStatusButtonsWrap.querySelectorAll('.rs-status-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                    const status = String(btn.getAttribute('data-status') || 'ALL').toUpperCase();
                    if (rsStatusFilter) rsStatusFilter.value = status;
                    renderRegionSucursalBoard();
                });
            });
        }
        if (rsClearFilters) {
            rsClearFilters.addEventListener('click', () => {
                if (rsSearch) rsSearch.value = '';
                if (rsStatusFilter) rsStatusFilter.value = 'ALL';
                if (rsChannelFilter) rsChannelFilter.value = '';
                renderRegionSucursalBoard();
            });
        }
        if (rsUndoMove) {
            rsUndoMove.addEventListener('click', async () => {
                const undo = state.rsUndoPayload;
                if (!undo) return;
                try {
                    await postJson(baseUrl + '/detalle/region-sucursal/relacionar', {
                        sucursalNum: undo.sucursalNum,
                        regionNum: undo.fromRegion
                    });
                    state.rsLastMove = {
                        sucursalNum: undo.sucursalNum,
                        toRegion: undo.fromRegion
                    };
                    state.rsUndoPayload = null;
                    showMsg('Movimiento revertido para sucursal ' + undo.sucursalNum, 'success');
                    await initData();
                } catch {
                    showMsg('No se pudo deshacer el movimiento', 'danger');
                }
            });
        }
        if (btnOpenRsUpload) {
            btnOpenRsUpload.addEventListener('click', () => {
                openUploadModal({
                    title: 'Carga de catalogo Region-Sucursal',
                    help: 'Sube el archivo Excel para actualizar la relacion entre regiones y sucursales.',
                    fieldName: 'sucursalesFile',
                    endpoint: '/cargar-sucursales',
                    successMsg: 'Catalogo de sucursales cargado correctamente'
                });
            });
        }
        if (btnRsAddRecord) {
            btnRsAddRecord.addEventListener('click', () => {
                openRsRecordEditor('create');
            });
        }
        if (btnRsExportXlsx) {
            btnRsExportXlsx.addEventListener('click', () => exportRegionSucursal('xlsx'));
        }
        if (btnRsExportPdf) {
            btnRsExportPdf.addEventListener('click', () => exportRegionSucursal('pdf'));
        }
    }

    function initTabSucursalEmpleado() {
        if (seSearch) seSearch.addEventListener('input', () => { renderSucursalEmpleadoTable(); });
        if (seRegionFilter) seRegionFilter.addEventListener('input', () => { renderSucursalEmpleadoTable(); });
        if (seOrigenSelect) seOrigenSelect.addEventListener('change', () => { renderSucursalEmpleadoTable(); });
        if (sePuestoFilter) sePuestoFilter.addEventListener('input', () => { renderSucursalEmpleadoTable(); });
        if (seVigenciaFilter) seVigenciaFilter.addEventListener('change', () => { renderSucursalEmpleadoTable(); });

        if (seCheckAll) {
            seCheckAll.addEventListener('change', () => {
                const checked = seCheckAll.checked;
                tbodyEmpleadoReasignacion.querySelectorAll('.se-check').forEach(c => { c.checked = checked; });
            });
        }

        if (btnSeMoverSeleccionados) {
            btnSeMoverSeleccionados.addEventListener('click', async () => {
                await moverEmpleadosSeleccionados();
            });
        }
        if (btnOpenSeUpload) {
            btnOpenSeUpload.addEventListener('click', () => {
                openUploadModal({
                    title: 'Carga de catalogo Sucursal-Empleado',
                    help: 'Sube el archivo Excel para actualizar la relacion de colaboradores por sucursal.',
                    fieldName: 'empleadosFile',
                    endpoint: '/cargar-empleados',
                    successMsg: 'Catalogo de empleados cargado correctamente'
                });
            });
        }
        if (btnSeAddRecord) {
            btnSeAddRecord.addEventListener('click', () => {
                openSeRecordEditor('create');
            });
        }
        if (btnSeCompareCore) {
            btnSeCompareCore.addEventListener('click', () => {
                abrirComparativoCoreEmpleado();
            });
        }
        if (btnSeExportXlsx) {
            btnSeExportXlsx.addEventListener('click', () => exportSucursalEmpleado('xlsx'));
        }
        if (btnSeExportPdf) {
            btnSeExportPdf.addEventListener('click', () => exportSucursalEmpleado('pdf'));
        }
        if (seCoreStatusFilter) {
            seCoreStatusFilter.addEventListener('change', renderSeCoreCompare);
        }
        if (seCoreSearch) {
            seCoreSearch.addEventListener('input', renderSeCoreCompare);
        }
        if (btnSeSelectVisible) {
            btnSeSelectVisible.addEventListener('click', () => {
                getCompareVisibleRows()
                    .filter(item => item.estadoComparativo !== 'IGUAL')
                    .forEach(item => state.seCompareSelected.add(Number(item.empleadoNum)));
                renderSeCoreCompare();
            });
        }
        if (btnSeClearVisible) {
            btnSeClearVisible.addEventListener('click', () => {
                getCompareVisibleRows().forEach(item => state.seCompareSelected.delete(Number(item.empleadoNum)));
                renderSeCoreCompare();
            });
        }
        if (btnSeApplyCore) {
            btnSeApplyCore.addEventListener('click', async () => {
                await aplicarCoreSeleccionado();
            });
        }
        if (btnSeKeepExcel) {
            btnSeKeepExcel.addEventListener('click', () => {
                notifySwal('info', 'Sin cambios', 'Se conservaron los registros actuales del catalogo');
            });
        }
    }

    function initTabSucursalPuestoProducto() {
        if (spSearch) spSearch.addEventListener('input', renderSucursalPuestoProductoBoard);
        if (spSucursalSelect) spSucursalSelect.addEventListener('change', renderSucursalPuestoProductoBoard);
        if (spCanalFilter) spCanalFilter.addEventListener('change', renderSucursalPuestoProductoBoard);
        if (spFuerzaFilter) spFuerzaFilter.addEventListener('change', renderSucursalPuestoProductoBoard);
        if (spEstatusFilter) spEstatusFilter.addEventListener('change', renderSucursalPuestoProductoBoard);
        if (btnOpenSpUpload) {
            btnOpenSpUpload.addEventListener('click', () => {
                openUploadModal({
                    title: 'Carga de catalogo Puesto-Producto',
                    help: 'Sube el archivo Excel para refrescar porcentajes Nuevo/Renovado por sucursal y puesto.',
                    fieldName: 'puestosProductosFile',
                    endpoint: '/cargar-puestos-productos',
                    successMsg: 'Catalogo de puestos-productos cargado correctamente'
                });
            });
        }
        if (btnSpAddRecord) {
            btnSpAddRecord.addEventListener('click', () => {
                openSpRecordEditor('create', { sucursalNum: spSucursalSelect?.value || '' });
            });
        }
    }

        async function renderAuditoria() {
        if (!tbodyAuditoria || !hasPermission('RELACIONES_TAB_HISTORIAL')) return;
        const q = (auSearch?.value || '').trim();
        try {
            const data = await getJson(baseUrl + '/auditoria?page=1&size=200&search=' + encodeURIComponent(q));
            const rows = data.items || [];
            tbodyAuditoria.innerHTML = '';
            rows.forEach(r => {
                const tr = document.createElement('tr');
                tr.innerHTML =
                    '<td>' + (r.fecha ?? '') + '</td>' +
                    '<td>' + (r.evento ?? '') + '</td>' +
                    '<td>' + (r.entidad ?? '') + '</td>' +
                    '<td>' + (r.entidadId ?? '') + '</td>' +
                    '<td>' + (r.usuario ?? '') + '</td>' +
                    '<td>' + (r.detalle ?? '') + '</td>';
                tbodyAuditoria.appendChild(tr);
            });
            if (!rows.length) {
                const tr = document.createElement('tr');
                tr.innerHTML = '<td colspan="6" class="text-muted">Sin registros</td>';
                tbodyAuditoria.appendChild(tr);
            }
            if (auInfo) {
                auInfo.textContent = 'Mostrando ' + rows.length + ' de ' + (data.total || rows.length) + ' registros';
            }
        } catch {
            if (auInfo) auInfo.textContent = 'No se pudo cargar historial';
        }
    }

    function initTabAuditoria() {
        if (auSearch) auSearch.addEventListener('input', () => { renderAuditoria(); });
        if (btnAuRefresh) btnAuRefresh.addEventListener('click', () => { renderAuditoria(); });
    }

    function initUploadModal() {
        if (!btnCatalogUploadConfirm) return;

        const updateSelectedFileLabel = () => {
            if (!catalogUploadFileName || !catalogUploadFile) return;
            const f = catalogUploadFile.files && catalogUploadFile.files.length ? catalogUploadFile.files[0] : null;
            catalogUploadFileName.textContent = f ? ('Archivo: ' + f.name) : 'Ningun archivo seleccionado';
        };

        const isExcelFile = (file) => {
            if (!file) return false;
            const name = String(file.name || '').toLowerCase();
            return name.endsWith('.xlsx') || name.endsWith('.xls');
        };

        if (catalogUploadFile) {
            catalogUploadFile.addEventListener('change', updateSelectedFileLabel);
        }

        if (catalogUploadDropzone) {
            const preventDefaults = (e) => {
                e.preventDefault();
                e.stopPropagation();
            };
            ['dragenter', 'dragover'].forEach(evt => {
                catalogUploadDropzone.addEventListener(evt, (e) => {
                    preventDefaults(e);
                    catalogUploadDropzone.classList.add('drag-over');
                });
            });
            ['dragleave', 'dragend', 'drop'].forEach(evt => {
                catalogUploadDropzone.addEventListener(evt, (e) => {
                    preventDefaults(e);
                    catalogUploadDropzone.classList.remove('drag-over');
                });
            });
            catalogUploadDropzone.addEventListener('drop', (e) => {
                const files = e.dataTransfer?.files;
                if (!files || !files.length) return;
                const file = files[0];
                if (!isExcelFile(file)) {
                    notifySwal('warning', 'Archivo invalido', 'Solo se permiten archivos .xlsx o .xls');
                    return;
                }
                if (!catalogUploadFile) return;
                const dt = new DataTransfer();
                dt.items.add(file);
                catalogUploadFile.files = dt.files;
                updateSelectedFileLabel();
            });
        }

        btnCatalogUploadConfirm.addEventListener('click', async () => {
            if (!state.uploadContext) {
                showMsg('No hay contexto de carga seleccionado', 'warning');
                return;
            }
            const ok = await cargarCatalogoIndividual({
                inputFile: catalogUploadFile,
                button: btnCatalogUploadConfirm,
                fieldName: state.uploadContext.fieldName,
                endpoint: state.uploadContext.endpoint,
                successMsg: state.uploadContext.successMsg
            });
            if (ok && uploadModal) uploadModal.hide();
        });
    }

    function initRecordModals() {
        if (btnRsRecordSave) {
            btnRsRecordSave.addEventListener('click', async () => {
                const mode = rsRecordMode?.value || 'create';
                const id = rsRecordId?.value;
                const payload = {
                    regionNum: rsRegionNum?.value ? parseInt(rsRegionNum.value, 10) : null,
                    regionNom: rsRegionNom?.value || null,
                    sucursalNum: rsSucursalNum?.value ? parseInt(rsSucursalNum.value, 10) : null,
                    sucursalNom: rsSucursalNom?.value || null,
                    sucursalEstatus: rsSucursalEstatus?.value || 'ACTIVA',
                    canales: rsCanales?.value || 'TRADICIONAL'
                };

                try {
                    if (mode === 'edit' && id) {
                        await putJson(baseUrl + '/detalle/region-sucursal/' + encodeURIComponent(id), payload);
                    } else {
                        if (!payload.regionNum && !payload.sucursalNum) {
                            return notifySwal('warning', 'Dato requerido', 'Captura Region o Sucursal para guardar');
                        }
                        if (payload.regionNum && !payload.sucursalNum) {
                            await postJson(baseUrl + '/detalle/region-sucursal/alta-region', {
                                regionNum: payload.regionNum,
                                regionNom: payload.regionNom
                            });
                        } else if (!payload.regionNum && payload.sucursalNum) {
                            await postJson(baseUrl + '/detalle/region-sucursal/alta-sucursal', {
                                sucursalNum: payload.sucursalNum,
                                sucursalNom: payload.sucursalNom,
                                canales: payload.canales
                            });
                        } else {
                            await postJson(baseUrl + '/detalle/region-sucursal/alta-sucursal', {
                                sucursalNum: payload.sucursalNum,
                                sucursalNom: payload.sucursalNom,
                                canales: payload.canales
                            });
                            await postJson(baseUrl + '/detalle/region-sucursal/relacionar', {
                                sucursalNum: payload.sucursalNum,
                                regionNum: payload.regionNum
                            });
                            const rows = await getJson(baseUrl + '/detalle/region-sucursal-por-sucursal?sucursalId=' + encodeURIComponent(payload.sucursalNum));
                            if (Array.isArray(rows) && rows.length) {
                                const target = rows[0];
                                await putJson(baseUrl + '/detalle/region-sucursal/' + encodeURIComponent(target.id), payload);
                            }
                        }
                    }
                    notifySwal('success', 'Guardado', 'Registro Region-Sucursal guardado correctamente');
                    if (rsRecordModal) rsRecordModal.hide();
                    await initData();
                } catch {
                    notifySwal('error', 'Error', 'No se pudo guardar el registro Region-Sucursal');
                }
            });
        }

        if (btnSeRecordSave) {
            btnSeRecordSave.addEventListener('click', async () => {
                const mode = seRecordMode?.value || 'create';
                const id = seRecordId?.value;
                const payload = {
                    empleadoNum: seEmpleadoNum?.value ? parseInt(seEmpleadoNum.value, 10) : null,
                    empleadoNom: seEmpleadoNom?.value || null,
                    sucursalNum: seRecordSucursalNum?.value ? parseInt(seRecordSucursalNum.value, 10) : null,
                    puestoId: sePuestoId?.value ? parseInt(sePuestoId.value, 10) : null,
                    puestoDesc: sePuestoDesc?.value || null,
                    aplicaMeta: seAplicaMeta?.value || 'SI',
                    estado: seEstado?.value || 'ACTIVO',
                    periodoNum: sePeriodoNum?.value ? parseInt(sePeriodoNum.value, 10) : null,
                    vigenciaInicio: seVigenciaInicio?.value || null,
                    vigenciaFin: seVigenciaFin?.value || null
                };
                if (!payload.empleadoNum || !payload.sucursalNum) {
                    return notifySwal('warning', 'Dato requerido', 'Empleado Num y Sucursal son obligatorios');
                }
                try {
                    if (mode === 'edit' && id) {
                        await putJson(baseUrl + '/detalle/empleado-sucursal/' + encodeURIComponent(id) + '/edicion', payload);
                    } else {
                        await postJson(baseUrl + '/detalle/empleado-sucursal', payload);
                    }
                    notifySwal('success', 'Guardado', 'Registro Sucursal-Empleado guardado correctamente');
                    if (seRecordModal) seRecordModal.hide();
                    await initData();
                    if (seOrigenSelect && payload.sucursalNum) {
                        seOrigenSelect.value = String(payload.sucursalNum);
                        await renderSucursalEmpleadoTable();
                    }
                } catch {
                    notifySwal('error', 'Error', 'No se pudo guardar el registro Sucursal-Empleado');
                }
            });
        }

        if (btnSpRecordSave) {
            btnSpRecordSave.addEventListener('click', async () => {
                const mode = spRecordMode?.value || 'create';
                const id = spRecordId?.value;
                const payload = {
                    sucursalNum: spRecordSucursalNum?.value ? parseInt(spRecordSucursalNum.value, 10) : null,
                    puestoId: spRecordPuestoId?.value ? parseInt(spRecordPuestoId.value, 10) : null,
                    productoNum: spRecordProductoNum?.value ? parseInt(spRecordProductoNum.value, 10) : null,
                    productoNom: spRecordProductoNom?.value || null,
                    canal: spRecordCanal?.value || 'TRADICIONAL',
                    fuerzaVenta: spRecordFuerzaVenta?.value || 'SUCURSAL',
                    porcNuevo: spRecordPorcNuevo?.value ? parseFloat(spRecordPorcNuevo.value) : 0,
                    porcRenovado: spRecordPorcRenovado?.value ? parseFloat(spRecordPorcRenovado.value) : 0,
                    vigenciaInicio: spRecordVigenciaInicio?.value || null,
                    vigenciaFin: spRecordVigenciaFin?.value || null,
                    estatus: spRecordEstatus?.value ? parseInt(spRecordEstatus.value, 10) : 1
                };
                if (!payload.sucursalNum || !payload.puestoId) {
                    return notifySwal('warning', 'Dato requerido', 'Sucursal y Puesto son obligatorios');
                }
                if (!payload.productoNum && !payload.productoNom) {
                    return notifySwal('warning', 'Dato requerido', 'Producto Num o nombre es obligatorio');
                }

                try {
                    if (mode === 'edit' && id) {
                        await putJson(baseUrl + '/detalle/puesto-producto/' + encodeURIComponent(id), payload);
                    } else {
                        await postJson(baseUrl + '/detalle/puesto-producto', payload);
                    }
                    notifySwal('success', 'Guardado', 'Registro Sucursal-Puesto-Producto guardado correctamente');
                    if (spRecordModal) spRecordModal.hide();
                    await initData();
                    if (spSucursalSelect && payload.sucursalNum) {
                        spSucursalSelect.value = String(payload.sucursalNum);
                        await renderSucursalPuestoProductoBoard();
                    }
                } catch {
                    notifySwal('error', 'Error', 'No se pudo guardar el registro Sucursal-Puesto-Producto');
                }
            });
        }
    }

    function exportRegionSucursal(tipo) {
        const params = new URLSearchParams();
        const q = (rsSearch?.value || '').trim();
        const estatus = rsStatusFilter?.value || '';
        const canal = rsChannelFilter?.value || '';
        if (q) params.append('search', q);
        if (estatus && estatus !== 'ALL' && estatus !== 'SIN_REGION') params.append('estatus', estatus);
        if (canal) params.append('canal', canal);
        const endpoint = tipo === 'pdf'
            ? '/detalle/region-sucursal/exportar-pdf'
            : '/detalle/region-sucursal/exportar-xlsx';
        window.open(baseUrl + endpoint + (params.toString() ? ('?' + params.toString()) : ''), '_blank');
    }

    (async function init() {
        initTabRegionSucursal();
        initTabSucursalEmpleado();
        initTabSucursalPuestoProducto();
        initTabAuditoria();
        initUploadModal();
        initRecordModals();

        try {
            await initData();
        } catch (e) {
            showMsg('Error al cargar datos iniciales', 'danger');
        }
    })();
})();

