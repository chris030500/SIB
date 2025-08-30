/**
 * 
 */
/**
 ////////////////----DES-25-0014 Campanias----////////////////
////////////////----viviana.zamudio-------------------------////////////////
 */
// Funcion para obtener la liga absoluta
function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

// Variables | validacion de la liga absoluta
var urlPrincipal = getAbsolutePath();
var newUrlBase = urlPrincipal.replace("/credito", "");
var mensajeError = "Datos incorrectos, favor de validar.";

document.getElementById("Campania").addEventListener("change", function() {
	//si seleccionan una campaña
	$.ajax({
	    url: newUrlBase + "credito/api/countExtraccion",
	    type: "GET",
	    success: function(data) {
	        
	        //desabilita todos los botones
	        // @ts-ignore
			document.querySelectorAll(".row button").forEach(btn => btn.disabled = true);
			
	        if(data === 0){
				
				//habilta el boton Extraer
				// @ts-ignore
        		document.getElementById("btnExtraer").disabled = false;
        		// @ts-ignore
        		document.getElementById("btnCancelar").disabled = false;
			}
	    },
	    error: function(err) {
	        console.error("Error al obtener el conteo:", err);
	    }
	});
});


document.getElementById("Estatus").addEventListener("change", function() {
	// @ts-ignore
	var valor = document.getElementById("Estatus").value;
	
	// @ts-ignore
	document.querySelectorAll(".row button").forEach(btn => btn.disabled = true);
	
	alert(valor);
	
	if (valor === "0") { // Buscar
		//se activa si el count de la tabla proceso no es >=1, si no hay campañas en estatus 1 y bandera 1
        // @ts-ignore
        document.getElementById("btnBuscar").disabled = false;
        //cuando le de clic al boton
        //campaña debe ser difernete de 0
        //estatus = 0
        //hacer update en base de datos para cron
        //a la hora sumarle 10 minutos
        //en desarrollo tiene una hora adelantada
        //hacer insert en procesos con los datos de la campaña, bandera = 1 y estatus = 1
    } 
    if (valor === "1") { // Extraccion
    	// @ts-ignore
    	var tipoCampania = parseInt(document.getElementById("Campania").value);
    	console.log("tipoCampania:",tipoCampania);
    	fetch(newUrlBase + "credito/api/countExtraccionTerm?tipoCampania=" + tipoCampania)
        .then(resp => {
            if (!resp.ok) {
                throw new Error("Error al activar boton generar archivo buro");
            }
            return resp.json(); // porque el backend regresa un int
        })
        .then(count => {
			console.log("Total procesos extracción:", count);
			if(count > 0){
				// @ts-ignore
        		document.getElementById("btnGenerar").disabled = false;
			}
            
            
        })
        .catch(err => {
            console.error("Error al activar boton generar archivo buro: ", err);
        });
        
    } 
    if (valor === "2") { // Generar Archivo Buró
        // @ts-ignore
        //document.getElementById("btnGenerar").disabled = false;
    } 
    if (valor === "3") { // Cargar Respuesta Buró
        // @ts-ignore
        //document.getElementById("btnCargar").disabled = false;
    } 
    if (valor === "4") { // Depuración Buró
        // @ts-ignore
        //document.getElementById("btnDepuracion").disabled = false;
    }
});

function btnExtraerFn() {
	//deshabilita el boton Extraer
	// @ts-ignore
	document.getElementById("btnExtraer").disabled = true;
	
	//insert en procesos
	
	let banderaExtraccion = 1;
	let estatus = 1;
	const data = {
        
        // @ts-ignore
        i_id_tipo_campania: parseInt(document.getElementById("Campania").value),
        
        // @ts-ignore
        dt_fecha_extraccion: document.getElementById("idFecha").value,
        i_bandera_extraccion: banderaExtraccion,
        // @ts-ignore
        dt_fecha_inicio_vigencia: document.getElementById("idFecha").value,
        // @ts-ignore
        dt_fecha_fin_vigencia: document.getElementById("idFecha").value,
        // @ts-ignore
        dt_fecha_envio_mc: document.getElementById("idFecha").value,
        i_estatus_reg: estatus,
        i_id_estatus_campania: estatus,
        // @ts-ignore
        dt_fecha_registro: document.getElementById("idFecha").value,
        // @ts-ignore
        i_user_reg: parseInt(document.getElementById("idUser").value),
        
    };
    
    const ahora = new Date();
    ahora.setMinutes(ahora.getMinutes() + 10);
    //por ser desarrollo el tomcat tiene una hora mas
    //esta quitarla en QA y en produccion
    ahora.setHours(ahora.getHours() + 1);
    
	const hora = ahora.getHours();      // Hora (0-23)
	const minutos = ahora.getMinutes(); // Minutos (0-59)
	
	const newCron = '0 ' + minutos + ' '+ hora + ' * * 1-6';
    //datos del cron
    const dataParam = {
        
        // @ts-ignore
        pk_id_configuracion: parseInt(document.getElementById("configIdCron").value),
        
        // @ts-ignore
        vc_valor_propiedad: newCron,
        
    };
    const bandera=1;
    //datos de la bandera
    const dataParam2 = {
        
        // @ts-ignore
        pk_id_configuracion: parseInt(document.getElementById("configIdBandera").value),
        
        // @ts-ignore
        vc_valor_propiedad: bandera,
        
    };
    console.log(data);
    console.log(dataParam);
    const jsonBitacora = JSON.stringify(data);
    let tabla = 'TBL_PROCESO_CAMPANIA';
    console.log(jsonBitacora);
    const dataHis = {
        vc_json_entrada: jsonBitacora,
        vc_tabla: tabla,
        // @ts-ignore
        i_user_reg: parseInt(document.getElementById("idUser").value),
        
    };
    
    console.log(newUrlBase);
    
    /*fetch(newUrlBase + 'credito/api/crearExt', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
	})
	.then(async resp => {
	    if (!resp.ok) {
	        const errorText = await resp.text();
	        throw new Error(errorText || "Error desconocido");
	    }
	    return resp.text();
	})
	.then(() => {
	    // Aquí ya no mostramos mensaje, llamamos directo al segundo fetch
	    return fetch(newUrlBase + 'credito/api/actualizarParamExt', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify(dataParam)
	    });
	})
	.then(async resp => {
	    if (!resp.ok) {
	        const errorText = await resp.text();
	        throw new Error(errorText || "Error desconocido");
	    }
	    return resp.text();
	})
	.then(msg => {
	    // Un solo mensaje final
	    // @ts-ignore
	    Swal.fire({
	        title: "",
	        text: msg,
	        icon: "success",
	        confirmButtonText: "Aceptar"
	    });
	})
	.catch(err => {
	    console.error("Error:", err);
	    // @ts-ignore
	    Swal.fire({
	        title: "Error",
	        text: err.message,
	        icon: "error",
	        confirmButtonText: "Cerrar"
	    });
	});*/
	
	fetch(newUrlBase + 'credito/api/crearExt', {
	    method: 'POST',
	    headers: { 'Content-Type': 'application/json' },
	    body: JSON.stringify(data)
	})
	.then(async resp => {
	    if (!resp.ok) {
	        const errorText = await resp.text();
	        throw new Error(errorText || "Error desconocido");
	    }
	    return resp.text();
	})
	.then(() => {
	    // Segundo fetch
	    return fetch(newUrlBase + 'credito/api/actualizarParamExt', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify(dataParam)
	    });
	})
	.then(async resp => {
	    if (!resp.ok) {
	        const errorText = await resp.text();
	        throw new Error(errorText || "Error desconocido");
	    }
	    return resp.text();
	})
	.then(() => {
	    // Tercer fetch
	    return fetch(newUrlBase + 'credito/api/actualizarParamExt', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify(dataParam2) // datos del tercer endpoint
	    });
	})
	.then(async resp => {
	    if (!resp.ok) {
	        const errorText = await resp.text();
	        throw new Error(errorText || "Error desconocido");
	    }
	    return resp.text();
	})
	.then(msg => {
	    // Mensaje final después de los 3 fetch
	    // @ts-ignore
	    Swal.fire({
	        title: "",
	        text: msg,
	        icon: "success",
	        confirmButtonText: "Aceptar"
	    });
	})
	.catch(err => {
	    console.error("Error:", err);
	    // @ts-ignore
	    Swal.fire({
	        title: "Error",
	        text: err.message,
	        icon: "error",
	        confirmButtonText: "Cerrar"
	    });
	});
	
}


function btnGenerarArchivoBuro() {
	
}

document.addEventListener("DOMContentLoaded", function() {
	//console.log("url:", newUrlBase);
	//llena combo de campañas
    $.ajax({
        url: newUrlBase + 'credito/api/catTipoCam',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Campania');
            select.empty();
            select.append('<option value="0">Seleccione</option>');
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_tipo_campania + '">' + item.pk_id_tipo_campania + "-" + item.vc_tipo_campania + '</option>');
            });
            
            // Marcar seleccionados
        	//select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar tipos campañas', err);
        }
    });
    //llena combo de estatus
    $.ajax({
        url: newUrlBase + 'credito/api/catEstatusCam',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Estatus');
            select.empty();
            select.append('<option value="0">Seleccione</option>');
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_estatus_campania + '">' + item.vc_estatus + '</option>');
            });
            
            // Marcar seleccionados
        	//select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar estatus', err);
        }
    });
});

