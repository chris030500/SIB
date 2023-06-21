var opcMenu = "opciones/generarMenu";
$(document).ready(function() {
	pintarMenuxRol();
});

function pintarMenuxRol() {
	var url = "http://localhost:8080/STD/"+opcMenu;
	console.log("datos:"+url);
	$.getJSON(url, {}, function(data) {
		$.each(data, function(idx, opt) {
			  var menu = document.getElementById(''+opt.pk_id_menu+'');
			  menu.style.display = "block";
			  console.log("menu"+opt.pk_id_menu)
			for(var i=0; i < opt.opcMenu.length; i++){
				 var opcion = document.getElementById('O'+opt.opcMenu[i].pk_id_opcion);
				 opcion.style.display = "block";
			}
		});
	});
}


function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
