//Obtener el campo numPersonas
function numMaximoParametrosContrasena(longitud) {
    var longitud = $("#longitud").val();
    var mayusculas = $("#mayusculas").val();
    var minusculas = $("#minusculas").val();
    var numeros = $("#numeros").val();
    var caracteres = $("#caracteres").val();
    
    var aux = Number(mayusculas) + Number(minusculas) + Number(numeros) + Number(caracteres);
    
    if(longitud < aux){
        //console.log("Excediste el tamaño");
        swal("Excediste la longitud de la contraseña", "Favor de verificar los parametros agregados", "warning");
        document.getElementById("mayusculas").value = "";
        document.getElementById("minusculas").value = "";
        document.getElementById("numeros").value = "";
        document.getElementById("caracteres").value = "";
    }
    //document.getElementById("longitud");
    console.log("Aux = " + aux);
    console.log(longitud);
    //console.log("Hola")
    //return longitud;
}

function buscar_ajax(cadena){
    //var selectUsuario = $('#selectUsuario');
		$.ajax({
		type: "GET",
		url: "http://localhost:8090/usuario/"+cadena,
		dataType: "JSON",
		success: function(respuesta) {
            $("#selectUsuario").empty();
			console.log(respuesta);
			console.log(respuesta.length);
			for(var i = 0; i< respuesta.length; i++){
                var nombre = respuesta[i].vc_nombre_usuario;
                console.log("nombre : " + nombre);
                //$("#selectUsuario").html(nombre);
                $("#selectUsuario").append("<option value=" + nombre + ">" + nombre + "</option>");
                //selectUsuario.append('<option value="' + nombre + '">' + nombre + '</option>');
            }
			
			//$( ".mostrar" ).append( $( "h2" ) )
	   }
	});
	}