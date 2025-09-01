function getAbsolutePath() {
	let loc = window.location;
	let pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function mostrarSpinner() {
	const spinnerDiv = document.getElementById('spinner-container');
	
    spinnerDiv.innerHTML = '<div role="status" style="font-size: 40px;"><span class="text-primary">Consultando ... </span><div class="spinner-border text-primary" role="status" aria-hidden="true"></div>';
}

function ocultarSpinner() {
	const spinnerDiv = document.getElementById('spinner-container');
	
    spinnerDiv.innerHTML = "";
}

function listaHistoricoBases() {
	const url = `${getAbsolutePath()}historicoBasesEbr`;
	
	$.getJSON(url, {}, function(data) {

		tblHistoricoBases.clear().draw();
		mostrarSpinner();
		$.each(data.bases, function(idx, opt) {

			//console.log(opt);
			const botones = `<div class="d-inline-flex justify-content-center gap-2">
                    <button type="button" class="btn btn-success btn-xs base-cliente" title="Base Clientes"><i class="bi bi-file-earmark-excel-fill"></i></button>
                    <button type="button" class="btn btn-success btn-xs base-operaciones" title="Base Operaciones"><i class="bi bi-file-earmark-excel-fill"></i></button>
                    <button type="button" class="btn btn-success btn-xs base-remesas" title="Base Remesas"><i class="bi bi-file-earmark-excel-fill"></i></button>
                    <button type="button" class="btn btn-success btn-xs base-desembolsos" title="Base Desembolsos"><i class="bi bi-file-earmark-excel-fill"></i></button>
                </div>`;
                
			tblHistoricoBases.row.add([
				opt.pk_proceso_id,
				opt.dt_fecha_generacion,
				opt.vc_usuario,
				opt.vc_estatus_vobo,
				opt.dt_fecha_vobo,
				opt.vc_periodo,
				botones
			]).draw(false);
		});
		ocultarSpinner();
	});
}