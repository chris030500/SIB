function getAbsolutePath() {
	let loc = window.location;
	let pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

async function listaHistoricoBases() {
    const url = `${getAbsolutePath()}historicoBasesEbr`;

    try {
        mostrarSpinner();

        const response = await fetch(url);
        
		if (!response.ok) {
			throw new Error(`Error HTTP: ${response.status}`);
		}
        
        const data = await response.json();

        tblHistoricoBases.clear().draw();

        data.bases.forEach(opt => {
            const botones = `<div class="d-inline-flex justify-content-center gap-2">
                <button class="btn btn-primary btn-xs base-cliente"><i class="bi bi-file-earmark-excel-fill"></i></button>
                <button class="btn btn-success btn-xs base-operaciones"><i class="bi bi-file-earmark-excel-fill"></i></button>
                <button class="btn btn-secondary btn-xs base-remesas"><i class="bi bi-file-earmark-excel-fill"></i></button>
                <button class="btn btn-warning btn-xs base-desembolsos"><i class="bi bi-file-earmark-excel-fill"></i></button>
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
    } catch (error) {
        console.error("Error al obtener histórico:", error);
		swal({
			title: "¡Error!",
			text: `No se pudo obtener el histórico: ${error.message}`,
			icon: "error"
		});
    } finally {
        ocultarSpinner();
    }
}

function mostrarSpinner() {
	const spinnerDiv = document.getElementById('spinner-container');
	
    spinnerDiv.innerHTML = '<div role="status" style="font-size: 40px;"><span class="text-primary">Consultando ... </span><div class="spinner-border text-primary" role="status" aria-hidden="true"></div>';
}

function ocultarSpinner() {
	const spinnerDiv = document.getElementById('spinner-container');
	
    spinnerDiv.innerHTML = "";
}