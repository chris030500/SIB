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
				
				//habilita el boton Extraer
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
    var tipoCampania = parseInt(document.getElementById("Campania").value);
	// @ts-ignore
	document.querySelectorAll(".row button").forEach(btn => btn.disabled = true);
	
	if (valor === "0") { // Buscar
		//se activa si el count de la tabla proceso no es >=1, si no hay campañas en estatus 1 y bandera 1
        // @ts-ignore
        //document.getElementById("btnBuscar").disabled = false;
        //cuando le de clic al boton
        //campaña debe ser difernete de 0
        //estatus = 0
        //hacer update en base de datos para cron
        //a la hora sumarle 10 minutos
        //en desarrollo tiene una hora adelantada
        //hacer insert en procesos con los datos de la campaña, bandera = 1 y estatus = 1
    } 
    if (valor === "1") { // Extraccion
    	
    	fetch(newUrlBase + "credito/api/countExtraccionTerm?tipoCampania=" + tipoCampania + "&estatus=" + valor)
        .then(resp => {
            if (!resp.ok) {
                throw new Error("Error al activar boton generar archivo buro");
            }
            return resp.json(); // porque el backend regresa un int
        })
        .then(count => {
			//console.log("Total procesos extracción:", count);
			if(count > 0){
				//activa boton generar archivo buro
				// @ts-ignore
        		document.getElementById("btnGenerar").disabled = false;
        		// @ts-ignore
        		document.getElementById("btnCancelar").disabled = false;
			}
        })
        .catch(err => {
            console.error("Error al activar boton generar archivo buro: ", err);
        });
        
    } 
    if (valor === "2") { // Generar Archivo Buró
        
		fetch(newUrlBase + "credito/api/countExtraccionTerm?tipoCampania=" + tipoCampania + "&estatus=" + valor)
        .then(resp => {
            if (!resp.ok) {
                throw new Error("Error al activar boton cargar archivo buro");
            }
            return resp.json(); // porque el backend regresa un int
        })
        .then(count => {
			
			if(count > 0){
				//activa boton cargar archivo buro
		        // @ts-ignore
				document.getElementById("btnCargar").disabled = false;
				// @ts-ignore
				document.getElementById("btnCancelar").disabled = false;
			}
        })
        .catch(err => {
            console.error("Error al activar boton cargar archivo buro: ", err);
        });
    } 
    if (valor === "3") { // Cargar Respuesta Buró
        
        fetch(newUrlBase + "credito/api/countExtraccionTerm?tipoCampania=" + tipoCampania + "&estatus=" + valor)
        .then(resp => {
            if (!resp.ok) {
                throw new Error("Error al activar boton depuracion buro");
            }
            return resp.json(); // porque el backend regresa un int
        })
        .then(count => {
			
			if(count > 0){
				//activa boton depuracion buro
		        // @ts-ignore
				document.getElementById("btnDepuracion").disabled = false;
				// @ts-ignore
				document.getElementById("btnCancelar").disabled = false;
			}
        })
        .catch(err => {
            console.error("Error al activar boton depuracion buro: ", err);
        });
    } 
    if (valor === "4") { // Depuración Buró
        
        fetch(newUrlBase + "credito/api/countExtraccionTerm?tipoCampania=" + tipoCampania + "&estatus=" + valor)
        .then(resp => {
            if (!resp.ok) {
                throw new Error("Error al activar boton cancelar");
            }
            return resp.json(); // porque el backend regresa un int
        })
        .then(count => {
			
			if(count > 0){
				
				// @ts-ignore
				document.getElementById("btnCancelar").disabled = false;
			}
        })
        .catch(err => {
            console.error("Error al activar boton cancelar: ", err);
        });
        
    }
});

function generarUUIDCorto(longitud = 8) {
  return 'xxxxxxxxxxxxxxxx'.substring(0, longitud).replace(/x/g, () => {
    return (Math.random() * 16 | 0).toString(16);
  });
}

function btnExtraerFn() {
	//deshabilita el boton Extraer
	// @ts-ignore
	document.getElementById("btnExtraer").disabled = true;
	
	// @ts-ignore
	let codigoCamp = parseInt(document.getElementById("Campania").value) + '-' + generarUUIDCorto();
	//console.log("UUID corto:", codigoCamp);  
	
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
        vc_codigo_cam: codigoCamp,
        
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
    //console.log(data);
    //console.log(dataParam);
    const jsonBitacora = JSON.stringify(data);
    let tabla = 'TBL_PROCESO_CAMPANIA';
    console.log(jsonBitacora);
    const dataHis = {
        vc_json_entrada: jsonBitacora,
        vc_tabla: tabla,
        // @ts-ignore
        i_user_reg: parseInt(document.getElementById("idUser").value),
        
    };
    
    //console.log(newUrlBase);
	
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
	//deshabilita el boton Generar Archivo Buro
	// @ts-ignore
	document.getElementById("btnGenerar").disabled = true;
	
	var urlServicio;
	//seleccionar el id proceso actual por tipo de campania
	//seleccionar el minimo por si hay dos que sea el primero que se hizo 
	
	// @ts-ignore
	var tipoCampania = parseInt(document.getElementById("Campania").value);
	//console.log("tipoCampania:",tipoCampania);
	
	// @ts-ignore
	//let codigoCamp = parseInt(document.getElementById("Campania").value) + '-' + generarUUIDCorto();
	
	fetch(newUrlBase + "credito/api/reporteBuroCredito?tipoCampania=" + tipoCampania)
    .then(resp => {
        if (!resp.ok) {
            throw new Error("Error al validar campania para generar archivo buro");
        }
        return resp.json(); // porque el backend regresa un int
    })
    .then(data => {
		
		const dataProceso = {
	        pk_id_proceso_campania: data,
	        i_estatus_reg: 2,
	    };
		
		//console.log("data:",data);
		//var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
		// @ts-ignore
		urlServicio = newUrlBase + "credito/rpt_archivo_buro?ProcesoCampania=" + data + "&TipoCampania=" + document.getElementById("Campania").value;
		//console.log("urlServicio:", urlServicio);
		//urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
		
		$.ajax({
		url: urlServicio,
		type: "POST",
		dataType: "JSON",
		beforeSend: function() {
			// @ts-ignore
			$.blockUI({ message: 'Espere un momento...' });
		},
		complete: function() {
			// @ts-ignore
			setTimeout($.unblockUI, 500);
		},
		success: function(respuesta) {
			
			//update TBL_PROCESO_CAMPANIA I_ESTATUS_REG = 2
			//insert TBL_PROCESO_CAMPANIA
			
			fetch(newUrlBase + 'credito/api/actualizarProcesoCampania', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(dataProceso)
		    })
		    .then(resp => {
			    if (!resp.ok) throw new Error("Error al actualizar Proceso Campania");
			    return resp.text();
			})
			.then(() => {
				
				///--
				//console.log("data:::::::::::::",data);
				fetch(newUrlBase + "credito/api/consultaCodigoCam?procesoCampania=" + data)
			    .then(resp => {
			        if (!resp.ok) {
			            throw new Error("Error al validar campania para obtener codigo campania");
			        }
			        return resp.json(); // porque el backend regresa un int
			    })
			    .then(datas => {
					
					
					const dataExtCargaBuro = {
        
				        // @ts-ignore
				        i_id_tipo_campania: parseInt(document.getElementById("Campania").value),
				        
				        // @ts-ignore
				        dt_fecha_extraccion: document.getElementById("idFecha").value,
				        i_bandera_extraccion: 2,
				        // @ts-ignore
				        dt_fecha_inicio_vigencia: document.getElementById("idFecha").value,
				        // @ts-ignore
				        dt_fecha_fin_vigencia: document.getElementById("idFecha").value,
				        // @ts-ignore
				        dt_fecha_envio_mc: document.getElementById("idFecha").value,
				        i_estatus_reg: 1,
				        //2	Generar Archivo
				        i_id_estatus_campania: 2,
				        // @ts-ignore
				        dt_fecha_registro: document.getElementById("idFecha").value,
				        // @ts-ignore
				        i_user_reg: parseInt(document.getElementById("idUser").value),
				        vc_codigo_cam:datas.codigo,
				        
				    };
					
				
					fetch(newUrlBase + 'credito/api/crearExtCargaBuro', {
					    method: 'POST',
					    headers: { 'Content-Type': 'application/json' },
					    body: JSON.stringify(dataExtCargaBuro)
					})
					.then(async resp => {
					    if (!resp.ok) {
					        const errorText = await resp.text();
					        throw new Error(errorText || "Error desconocido");
					    }
					    return resp.text();
					})
					.then(() => {
					    // 
					    var urlReporte = window.location.href.substring(0,window.location.href.indexOf("STD/")+4)+respuesta[0];
						//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
				
						//console.log(urlReporte);
						//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
						window.open(urlReporte);
						window.focus();
					    
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
				
				})
			    .catch(err => {
			        console.error("Error al validar campania para obtener codigo campania:: ", err);
			    });
				///--
				
				
				
			})
			.catch(err => {
			    //console.error("Error al actualizar Proceso Campania:", err);
			   // @ts-ignore
			   swal({
				title: "",
				text: 'Error al actualizar Proceso Campania',
			        icon: 'error'
			    });
			});
				///////////////////////
			
		}, error: function(XMLHttpRequest, textStatus, errorThrown) {
			// @ts-ignore
			swal({
				title: "Error, ",
				text: "Se presento un error al generar reporte de buro credito, intente mas tarde.",
				icon: "warning",
			});
		}
		});
	        
	        
    })
    .catch(err => {
        console.error("Error al validar campania para generar archivo buro: ", err);
    });
	
	
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
    
    //funcion para mostrar catalogo de tipos buro de credito (Adviser/Prospector)
    $.ajax({
        url: newUrlBase + 'credito/api/catTipoBuro',
        type: 'GET',
        success: function(data) {
            let select = $('#tipoArchivoBuro');
            select.empty();
            select.append('<option value="0">Seleccione</option>');

            data.forEach(function(item) {
                select.append('<option value="' + item.i_opcion + '">' + item.v_valor + '</option>');
            });
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
});

// Abrir modal CargarRespuestaBuro
  function btnCargarRespuestaBuro() {
	 
    let modal = new bootstrap.Modal(document.getElementById('modalCargarBuro'));
    modal.show();
    
  }

// Seleccionar archivo buro
  function seleccionarArchivoBuro() {
	  
    const input = document.getElementById("inputArchivo");
    const span = document.getElementById("nombreArchivo");
    // @ts-ignore
    if (input.files.length > 0) {
      // @ts-ignore
      span.textContent = input.files[0].name;
    } else {
      span.textContent = "Ningún archivo seleccionado";
    }
  }
  
 // Validar y simular subida
  function subirArchivo() {
	 
    // @ts-ignore
    const tipo = document.getElementById("tipoArchivoBuro").value;
    // @ts-ignore
    const archivo = document.getElementById("inputArchivo").files[0];

    if (!tipo) {
      	// @ts-ignore
		swal({
			title: "",
			text: 'Seleccione un tipo de archivo.',
			icon: "error",
	    
	    });
      return;
    }

    if (!archivo) {
      
      // @ts-ignore
		swal({
			title: "",
			text: 'Adjunte un archivo Excel.',
			icon: "error",
	    
	    });
      return;
    }

    //console.log("Tipo:", tipo);
    //console.log("Archivo:", archivo.name);
    
    const reader = new FileReader();
  	reader.onload = function(ev) {
    // @ts-ignore
    const data = new Uint8Array(ev.target.result);
    // @ts-ignore
    const workbook = XLSX.read(data, { type: "array" });

    const sheet = workbook.Sheets[workbook.SheetNames[0]];
    // @ts-ignore
    const rows = XLSX.utils.sheet_to_json(sheet, { header: 1 });
    let registros = [];
    if (tipo==2) {
		//Adviser
		let rfcCliente = rows.slice(3).map(fila => fila[2]);
	    // Extraer columna KB desde fila 4-Procentaje de uso maximo de Total de Créditos Abiertos
	    let valoresBK = rows.slice(3).map(fila => fila[62]); 
	    //multiplicar por 100 y redondear los decimales
	    // Extraer columna BO desde fila 4-MOP máximo de Estrategia Bienestar
	    let valoresBO = rows.slice(3).map(fila => fila[66]); 
	    // Extraer columna BP desde fila 4-BCS mínimo de Estrategia Bienestar
	    let valoresBP = rows.slice(3).map(fila => fila[67]);
	    // Extraer columna BQ desde fila 4-ICC mínimo de Estrategia Bienestar
	    let valoresBQ = rows.slice(3).map(fila => fila[68]);
	    // Extraer columna BR desde fila 4-Riesgo máximo de Estrategia Bienestar
	    let valoresBR = rows.slice(3).map(fila => fila[69]);
	    
	    // Construir array de objetos
		registros = rfcCliente.map((rfc, idx) => ({
		    vc_rfc_cliente: rfc,
		    i_tipo_buro_credito: tipo,
		    d_porc_uso_maximo_adviser: cortarDecimales(valoresBK[idx],3),
		    d_mop_maximo_adviser: valoresBO[idx],
		    d_bcs_minimo_adviser: valoresBP[idx],
		    d_icc_minimo_adviser: valoresBQ[idx],
		    vc_riesgo_maximo_adviser: valoresBR[idx],
		    d_bcs_minnimo_prospector: 0,
		    d_icc_minnimo_prospector: 0,
		    vc_riesgo_maximo_prospector: '0',
		    i_estatus_reg: 1,
		    // @ts-ignore
		    dt_fecha_registro: document.getElementById("idFecha").value,
		    // @ts-ignore
		    i_user_reg: parseInt(document.getElementById("idUser").value),
		    // @ts-ignore
		    i_tipo_campania: parseInt(document.getElementById("Campania").value),
		    
		}));
	    
	}else if(tipo==1){
		//Prospector
		let rfcClienteP = rows.slice(3).map(fila => fila[2]);
		// Extraer columna-BCS mínimo
	    let valoresBCS = rows.slice(3).map(fila => fila[13]);
	    // Extraer columna-ICC mínimo
	    let valoresICC = rows.slice(3).map(fila => fila[14]);
	    // Extraer columna-Riesgo máximo
	    let valoresRM = rows.slice(3).map(fila => fila[15]);
	    
	    // Construir array de objetos
		registros = rfcClienteP.map((rfc, idx) => ({
		    vc_rfc_cliente: rfc,
		    i_tipo_buro_credito: tipo,
		    d_porc_uso_maximo_adviser: 0,
		    d_mop_maximo_adviser: 0,
		    d_bcs_minimo_adviser: 0,
		    d_icc_minimo_adviser: 0,
		    vc_riesgo_maximo_adviser: '0',
		    d_bcs_minnimo_prospector: valoresBCS[idx],
		    d_icc_minnimo_prospector: valoresICC[idx],
		    vc_riesgo_maximo_prospector: valoresRM[idx],
		    i_estatus_reg: 1,
		    // @ts-ignore
		    dt_fecha_registro: document.getElementById("idFecha").value,
		    // @ts-ignore
		    i_user_reg: parseInt(document.getElementById("idUser").value),
		    // @ts-ignore
		    i_tipo_campania: parseInt(document.getElementById("Campania").value),
		}));
	}else{
		// @ts-ignore
		swal({
			title: "",
			text: 'Seleccione un tipo de archivo.',
			icon: "error",
	    
	    });
	    return;
	}
    //console.log("Registros a enviar:", registros);
    //console.log("Cantidad de registros construidos:", registros.length);
	//console.table(registros);
	//selecionar id proceso 
    //update TBL_PROCESO_CAMPANIA
    //insert TBL_PROCESO_CAMPANIA
    // @ts-ignore
	//let codigoCamp = parseInt(document.getElementById("Campania").value) + '-' + generarUUIDCorto();
    
    // @ts-ignore
	var tipoCampania = parseInt(document.getElementById("Campania").value);
	
	let estatusCampania = 2;
	let banderaCampania = 2;
	
	fetch(newUrlBase + "credito/api/procesoCamEstatus?tipoCampania=" + tipoCampania + "&estatusCampania=" + estatusCampania + "&banderaCampania=" + banderaCampania)
    //fetch(newUrlBase + "credito/api/reporteBuroCredito?tipoCampania=" + tipoCampania)
    .then(resp => {
        if (!resp.ok) {
            throw new Error("Error al validar campania para procesar validacion respuesta buro");
        }
        return resp.json(); // porque el backend regresa un int
    })
    .then(data => {
		
		const dataProceso = {
	        pk_id_proceso_campania: data,
	        i_estatus_reg: 2,
	    };
	    ///
	    ///
	    fetch(newUrlBase + 'credito/api/actualizarProcesoCampania', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(dataProceso)
		    })
		    .then(resp => {
			    if (!resp.ok) throw new Error("Error al actualizar Proceso Campania");
			    return resp.text();
			})
			.then(() => {
				
				///--
				fetch(newUrlBase + "credito/api/consultaCodigoCam?procesoCampania=" + data)
			    .then(resp => {
			        if (!resp.ok) {
			            throw new Error("Error al validar campania para obtener codigo campania");
			        }
			        return resp.json(); // porque el backend regresa un int
			    })
			    .then(datas => {
					const dataExtCargaBuro = {
        
				        // @ts-ignore
				        i_id_tipo_campania: parseInt(document.getElementById("Campania").value),
				        
				        // @ts-ignore
				        dt_fecha_extraccion: document.getElementById("idFecha").value,
				        i_bandera_extraccion: 2,
				        // @ts-ignore
				        dt_fecha_inicio_vigencia: document.getElementById("idFecha").value,
				        // @ts-ignore
				        dt_fecha_fin_vigencia: document.getElementById("idFecha").value,
				        // @ts-ignore
				        dt_fecha_envio_mc: document.getElementById("idFecha").value,
				        i_estatus_reg: 1,
				        //3	Carga respuesta Buro
				        i_id_estatus_campania: 3,
				        // @ts-ignore
				        dt_fecha_registro: document.getElementById("idFecha").value,
				        // @ts-ignore
				        i_user_reg: parseInt(document.getElementById("idUser").value),
				        vc_codigo_cam:datas.codigo,
				        
				    };
					/////aqui inicia
					
					fetch(newUrlBase + 'credito/api/crearExtCargaBuro', {
					    method: 'POST',
					    headers: { 'Content-Type': 'application/json' },
					    body: JSON.stringify(dataExtCargaBuro)
					})
					.then(async resp => {
					    if (!resp.ok) {
					        const errorText = await resp.text();
					        throw new Error(errorText || "Error desconocido");
					    }
					    return resp.text();
					})
					.then(() => {
					    // 
					    //
					    //
					    let estatusCampania2 = 3;
						let banderaCampania2 = 2;
					    fetch(newUrlBase + "credito/api/procesoCamEstatus?tipoCampania=" + tipoCampania + "&estatusCampania=" + estatusCampania2 + "&banderaCampania=" + banderaCampania2)
						    //fetch(newUrlBase + "credito/api/reporteBuroCredito?tipoCampania=" + tipoCampania)
						    .then(resp => {
						        if (!resp.ok) {
						            throw new Error("Error al validar campania para procesar validacion respuesta buro");
						        }
						        return resp.json(); // porque el backend regresa un int
						    })
						    .then(data2 => {
					    		//console.log("data2:", data2);
					    		// @ts-ignore
					    		//registros.i_proceso_campania = data2;
					    		
					    		registros.forEach(r => {
							        r.i_proceso_campania = data2;
							    });
					    
							    fetch(newUrlBase + 'credito/api/crearValBuroCredito', {
							        method: 'POST',
							        headers: {
							            'Content-Type': 'application/json'
							        },
							        body: JSON.stringify(registros)
							    })
							    .then(resp => {
								    if (!resp.ok) throw new Error("Error al procesar datos Tipo Buro de Credito");
								    return resp.text();
								})
								.then(() => {
									
									// @ts-ignore
									swal({
									title: "",
									text: 'Actualizado correctamente',
									icon: "success",
								    // @ts-ignore
								    }).then(() => {
										//deshabilita el boton Cargar Respuesta Buro
										// @ts-ignore
										document.getElementById("btnCargar").disabled = true;
								        //window.location.href = newUrlBase + "credito/tiposCampanias"; // Redirige después de aceptar
								    });
									///
									//mandar llamar SP de proceso de buro de credito SP_CAM_DEPURACION_BC
									/*fetch(newUrlBase + "credito/api/ejecutarDepuracionBc?tipoCampania=" + tipoCampania + "&procesoCampania=" + data2 + "&tipoBuro=" + tipo)
								    .then(resp => {
								        if (!resp.ok) {
								            throw new Error("Error en el proceso de depuracion de respuesta buro.");
								        }
								        return resp.text(); // porque el backend regresa un int
								    })
								    .then(dataSP => {
										
										// @ts-ignore
										swal({
										title: "",
										text: 'Actualizado correctamente',
										icon: "success",
									    // @ts-ignore
									    }).then(() => {
									        //window.location.href = newUrlBase + "credito/tiposCampanias"; // Redirige después de aceptar
									    });
										
									})
								    .catch(err => {
								        console.error("Error en el proceso de depuracion de respuesta buro: ", err);
								    });*/
									///termina llamada del SP
									
									
								})
								.catch(err => {
								    console.error("Error al actualizar datos Tipo Buro de Credito:", err);
								   // @ts-ignore
								   swal({
									title: "",
									text: 'Error al actualizar',
								        icon: 'error'
								    });
								});
								
							})
						    .catch(err => {
						        console.error("Error al validar campania para procesar validacion respuesta buro: ", err);
						    });
					    //
					    //
					    //
					    
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
					/////aqui termina
				})
				.catch(err => {
			        console.error("Error al validar campania para obtener codigo campania:: ", err);
			    });
				///--
				
				
				
			})
			.catch(err => {
			    //console.error("Error al actualizar Proceso Campania:", err);
			   // @ts-ignore
			   swal({
				title: "",
				text: 'Error al actualizar Proceso Campania',
			        icon: 'error'
			    });
			});
	    ///
	    ///
    
    })
    .catch(err => {
        console.error("Error al validar campania para procesar validacion respuesta buro: ", err);
    });
    
		///////////////////////
		///////////////////////
  };
  reader.readAsArrayBuffer(archivo);


    // Cerrar modal
    let modal = bootstrap.Modal.getInstance(document.getElementById('modalCargarBuro'));
    modal.hide();
     
  }

function cortarDecimales(valor, decimales) {
    let str = String(valor);                // asegurar que sea string
    let punto = str.indexOf(".");           // buscar la posición del punto
    if (punto === -1) return str;           // si no hay punto, se regresa igual
    return str.substring(0, punto + decimales + 1); // cortar hasta 3 después del punto
}

function btnDepuracionBuro() {
	//deshabilita el boton Depuracion Buro
	// @ts-ignore
	document.getElementById("btnDepuracion").disabled = true;
	
	// @ts-ignore
	var tipoCampania = parseInt(document.getElementById("Campania").value);
	
	// @ts-ignore
	var estatusCampania = parseInt(document.getElementById("Estatus").value);
	
	//let estatusCampania = 3;
	let banderaCampania = 2;
	
	fetch(newUrlBase + "credito/api/procesoCamEstatus?tipoCampania=" + tipoCampania + "&estatusCampania=" + estatusCampania + "&banderaCampania=" + banderaCampania)
    //fetch(newUrlBase + "credito/api/reporteBuroCredito?tipoCampania=" + tipoCampania)
    .then(resp => {
        if (!resp.ok) {
            throw new Error("Error al validar campania para procesar depuracion buro");
        }
        return resp.json(); // porque el backend regresa un int
    })
    .then(data => {
		
		const dataProceso = {
	        pk_id_proceso_campania: data,
	        i_estatus_reg: 2,
	    };
	    ///
	    ///
	    fetch(newUrlBase + 'credito/api/actualizarProcesoCampania', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(dataProceso)
		    })
		    .then(resp => {
			    if (!resp.ok) throw new Error("Error al actualizar Proceso Campania en depuracion buro");
			    return resp.text();
			})
			.then(() => {
				
				///--
				fetch(newUrlBase + "credito/api/consultaCodigoCam?procesoCampania=" + data)
			    .then(resp => {
			        if (!resp.ok) {
			            throw new Error("Error al validar campania para obtener codigo campania en depuracion buro");
			        }
			        return resp.json(); // porque el backend regresa un int
			    })
			    .then(datas => {
					const dataExtCargaBuro = {
        
				        // @ts-ignore
				        i_id_tipo_campania: parseInt(document.getElementById("Campania").value),
				        
				        // @ts-ignore
				        dt_fecha_extraccion: document.getElementById("idFecha").value,
				        i_bandera_extraccion: 1,
				        // @ts-ignore
				        dt_fecha_inicio_vigencia: document.getElementById("idFecha").value,
				        // @ts-ignore
				        dt_fecha_fin_vigencia: document.getElementById("idFecha").value,
				        // @ts-ignore
				        dt_fecha_envio_mc: document.getElementById("idFecha").value,
				        i_estatus_reg: 1,
				        //4	Depuracion respuesta Buro
				        i_id_estatus_campania: 4,
				        // @ts-ignore
				        dt_fecha_registro: document.getElementById("idFecha").value,
				        // @ts-ignore
				        i_user_reg: parseInt(document.getElementById("idUser").value),
				        vc_codigo_cam:datas.codigo,
				        
				    };
					/////aqui inicia
					
					fetch(newUrlBase + 'credito/api/crearExtCargaBuro', {
					    method: 'POST',
					    headers: { 'Content-Type': 'application/json' },
					    body: JSON.stringify(dataExtCargaBuro)
					})
					.then(async resp => {
					    if (!resp.ok) {
					        const errorText = await resp.text();
					        throw new Error(errorText || "Error desconocido en depuracion buro");
					    }
					    return resp.text();
					})
					.then(() => {
					    // 
					    //
					    //
						//mandar llamar SP de proceso de buro de credito SP_CAM_DEPURACION_BC
						fetch(newUrlBase + "credito/api/ejecutarDepuracionBc?tipoCampania=" + tipoCampania + "&procesoCampania=" + data)
					    .then(resp => {
					        if (!resp.ok) {
					            throw new Error("Error en el proceso de depuracion de respuesta buro en SP.");
					        }
					        return resp.text(); // porque el backend regresa un int
					    })
					    .then(dataSP => {
							
							//ejecutar primera(ultima) migracion hacia microfin y SP con las exclusiones que van hacia SIB
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
						        pk_id_configuracion: parseInt(document.getElementById("configIdCronExc").value),
						        
						        // @ts-ignore
						        vc_valor_propiedad: newCron,
						        
						    };
						    const bandera=1;
						    //datos de la bandera
						    const dataParam2 = {
						        
						        // @ts-ignore
						        pk_id_configuracion: parseInt(document.getElementById("configIdBanderaExc").value),
						        
						        // @ts-ignore
						        vc_valor_propiedad: bandera,
						        
						    };
						    fetch(newUrlBase + 'credito/api/actualizarParamExt', {
						        method: 'POST',
						        headers: { 'Content-Type': 'application/json' },
						        body: JSON.stringify(dataParam)
						    })
						    .then(async resp => {
							    if (!resp.ok) {
							        const errorText = await resp.text();
							        throw new Error(errorText || "Error desconocido en actualizarParamExt");
							    }
							    return resp.text();
							})
							.then(() => {
								//////
								fetch(newUrlBase + 'credito/api/actualizarParamExt', {
							        method: 'POST',
							        headers: { 'Content-Type': 'application/json' },
							        body: JSON.stringify(dataParam2)
							    })
							    .then(async resp => {
								    if (!resp.ok) {
								        const errorText = await resp.text();
								        throw new Error(errorText || "Error desconocido en actualizarParamExt");
								    }
								    return resp.text();
								})
								.then(() => {
									////////////
									// @ts-ignore
									swal({
									title: "",
									text: 'Actualizado correctamente',
									icon: "success",
								    // @ts-ignore
								    }).then(() => {
								        //window.location.href = newUrlBase + "credito/tiposCampanias"; // Redirige después de aceptar
								    });
									///////////
							    })
								.catch(err => {
								    console.error("Error: actualizarParamExt", err);
								    // @ts-ignore
								    Swal.fire({
								        title: "Error",
								        text: err.message,
								        icon: "error",
								        confirmButtonText: "Cerrar"
								    });
								});
								/////
						    })
							.catch(err => {
							    console.error("Error: actualizarParamExt", err);
							    // @ts-ignore
							    Swal.fire({
							        title: "Error",
							        text: err.message,
							        icon: "error",
							        confirmButtonText: "Cerrar"
							    });
							});
							
							
						})
					    .catch(err => {
					        console.error("Error en el proceso de depuracion de respuesta buro en SP: ", err);
					    });
						///termina llamada del SP
					    //
					    //
					    //
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
					/////aqui termina
				})
				.catch(err => {
			        console.error("Error al validar campania para obtener codigo campania:: ", err);
			    });
				///--
				
				
				
			})
			.catch(err => {
			    //console.error("Error al actualizar Proceso Campania:", err);
			   // @ts-ignore
			   swal({
				title: "",
				text: 'Error al actualizar Proceso Campania',
			        icon: 'error'
			    });
			});
	    ///
	    ///
    
    })
    .catch(err => {
        console.error("Error al validar campania para procesar validacion respuesta buro: ", err);
    });
	
}
