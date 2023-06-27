var opcMenu = "opciones/generarMenu";
$(document).ready(function() {
	pintarMenuxRol();
});

function pintarMenuxRol() {
	var url = window.location.href.substring(0,26)+opcMenu;
	console.log("datos:"+url);
	$.getJSON(url, {}, function(data) {
		$.each(data, function(idx, opt) {
			  var menu = document.getElementById(''+opt.pk_id_menu+'');
			  menu.style.display = "block";
			  
			for(var i=0; i < opt.opcMenu.length; i++){
				 var opcion = document.getElementById('M_O'+opt.opcMenu[i].pk_id_opcion);
				 opcion.style.display = "block";
			}
		});
		document.querySelectorAll("#sidebar [style='display:none;']").forEach(function(checkElement) {			
			$('#'+ checkElement.id+'').remove();
		});
	});
	

}


function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
