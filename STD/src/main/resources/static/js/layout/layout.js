var opcMenu = "opciones/generarMenu";
$(document).ready(function() {
	//obtenerObservacion();
});

function obtenerObservacion() {
	var url = getAbsolutePath()+opcMenu;
	$.getJSON(url, {}, function(data) {
			
	
	});
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
