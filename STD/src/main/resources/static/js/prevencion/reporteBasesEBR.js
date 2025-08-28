function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function generarBasesEBR(tipoReporte) {
	let urlCompleta = '';
	
	urlCompleta = getAbsolutePath() + "generarBasesEBR?tipoReporte=" + Number(tipoReporte); 
    	
	peticionPost(urlCompleta);

}

function peticionPost(urlServicio) {
	$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {

			var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];

			window.open(urlReporte);
			window.focus();

		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal({
				title: "Error, ",
				text: "Se presento un error, intente mas tarde.",
				icon: "warning",
			});
		}
	});
}