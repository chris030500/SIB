// @ts-nocheck
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

document.addEventListener("DOMContentLoaded", function () {
    const selectReporte = document.getElementById("idReporte");
    const divCampania = document.getElementById("divCampania");
    //const divEstatus = document.getElementById("divEstatus");
    
    //funcion para llenar combo opcion multiple de campañas
    $.ajax({
        url: newUrlBase + 'credito/api/catMulTipoCampania',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Campania');
            select.empty();
            data.forEach(function(item) {
                select.append('<option value="' + item.pk_id_tipo_campania + '">' + item.vc_tipo_campania + '</option>');
            });
            
            // Marcar seleccionados
        	//select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar tipos campañas', err);
        }
    });

    // Por si quieres ambos visibles al inicio (puedes poner d-none si no)
    //divCampania.classList.remove("d-none");
    //divEstatus.classList.remove("d-none");
    
    divCampania.classList.add("d-none");
    //divEstatus.classList.add("d-none");

    selectReporte.addEventListener("change", function () {
        const valor = this.value;

        if (valor === "1") { 
            // Primer reporte → ocultar campañas
            divCampania.classList.add("d-none");
            //divEstatus.classList.remove("d-none");
        } else if (valor === "2") { 
            // Segundo reporte → ocultar estatus
            divCampania.classList.remove("d-none");
            //divEstatus.classList.add("d-none");
        } else {
            // Mostrar todo
            divCampania.classList.remove("d-none");
            //divEstatus.classList.remove("d-none");
        }
    });
});

function valVacio() {
	
	url = "";
	var result = false;
	var contador = 0;
	
	
	if(document.getElementById("FechaFin").value < document.getElementById("FechaInicio").value){
		mensajeError = "Fecha fin no debe ser menor a fecha inicio.";
		document.getElementById("FechaFin").style.borderColor = "#F70707";
		contador = contador + 1;
		result = true;
	}
	
	if(document.getElementById("FechaInicio").value > document.getElementById("FechaFin").value){
		mensajeError = "Fecha inicio no debe ser mayor a fecha fin.";
		document.getElementById("FechaInicio").style.borderColor = "#F70707";
		contador = contador + 1;
		result = true;
	}
	
	if(contador == 0){
		document.getElementById("FechaInicio").style.borderColor = "";
		document.getElementById("FechaFin").style.borderColor = "";
		// @ts-ignore
	
		result = false;
	}else{
		
		result = true;
	}
	return result;
}

function generarReporte() {
	
	if (valVacio() == false) {
		
		const idReporte = document.getElementById("idReporte").value;
		//const idEstatus = document.getElementById("estatusCampania").value;
		//console.log("idReporte: " + idReporte);
		//console.log("idEstatus: " + idEstatus);
		
		if (idReporte === "1") { 
			//Reporte Clientes Excluidos
			var urlServicio;
			
			
			if (document.getElementById("FechaInicio").value === "" || document.getElementById("FechaFin").value === "") {
				var fechaInicioSinFecha = "2025-12-30";
				
				urlServicio = newUrlBase + "credito/reporteExclusionesInternasSinFecha?dtFechaInicio=" + fechaInicioSinFecha + "&dtFechaFin=" + fechaInicioSinFecha ;
		    	console.log("urlServicio:", urlServicio);
		    	//urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
		    	
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
					//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
		
					//console.log(urlReporte);
					//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
					window.open(urlReporte);
					window.focus();
		
				}, error: function(XMLHttpRequest, textStatus, errorThrown) {
					Swal.fire({
					    title: "Error",
					    text: "Se presento un error, intente mas tarde.",
					    icon: "warning",
					    confirmButtonText: "Aceptar"
					});
				}
				});
				
			}else{
				
				//var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
		    	//urlServicio = newUrlBase + "credito/reporteProcesosCampania?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&campania="+ids;
		    	urlServicio = newUrlBase + "credito/reporteExclusionesInternas?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value ;
		    	//console.log("urlServicio:", urlServicio);
		    	//urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
		    	
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
					//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
		
					//console.log(urlReporte);
					//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
					window.open(urlReporte);
					window.focus();
		
				}, error: function(XMLHttpRequest, textStatus, errorThrown) {
					Swal.fire({
					    title: "Error",
					    text: "Se presento un error, intente mas tarde.",
					    icon: "warning",
					    confirmButtonText: "Aceptar"
					});
				}
				});
				/*if (idEstatus === "0") {
				
					///////////////////////////////
			    	var urlServicio;
			    	//var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
			    	//urlServicio = newUrlBase + "credito/reporteProcesosCampania?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&campania="+ids;
			    	urlServicio = newUrlBase + "credito/reporteExclusionesInternas?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value ;
			    	//console.log("urlServicio:", urlServicio);
			    	//urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
			    	
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
						//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
			
						//console.log(urlReporte);
						//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
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
			    	//////////////
				
				}else{
					
					///////////////////////////////
			    	var urlServicio;
			    	//var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
			    	//urlServicio = newUrlBase + "credito/reporteProcesosCampania?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&campania="+ids;
			    	//urlServicio = newUrlBase + "credito/reporteExclusionesInternasEstatus?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&estatus=" + document.getElementById("idEstatus").value;
			    	urlServicio = newUrlBase + "credito/reporteExclusionesInternasEstatus?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&estatus=" + parseInt(idEstatus);
			    	//console.log("urlServicio:", urlServicio);
			    	//urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
			    	console.log("urlServicio: " + urlServicio);
			    	
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
						//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
			
						//console.log(urlReporte);
						//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
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
			    	//////////////
				}*/
				
			}
			
	    	
			
		}else if (idReporte === "2") { 
			
			//Reporte Bases Enviadas MC
			let seleccionProd = $('#Campania').val();

			//console.log('Seleccionados:', seleccionProd);

			//let seleccionProd = Array.from(select.selectedOptions);
			if (seleccionProd.length === 0) {
	
				Swal.fire({
				    title: "Alerta",
				    text: "Debe seleccionar al menos una campaña.",
				    icon: "warning",
				    confirmButtonText: "Aceptar"
				});
				
			}else{
				
				// Obtiene los valores seleccionados de un <select multiple>
				//const selectedCam = Array.from(select.selectedOptions).map(option => option.value);
				//let campaniasSelect = selectedCam.join(",");
				//console.log("campaniasSelect: " + campaniasSelect);
				
				let select = document.getElementById('Campania');
				let campaniasSelect = Array.from(select.selectedOptions).map(opt => opt.value);
				let campaniasString = campaniasSelect.join(',');
				//console.log('Seleccionadossss:', campaniasString);
				
				///////////////////////////////
		    	var urlServicio;
		    	//var rptOA = newUrlBase + "reporteProcesosCampania?idReporte=" + document.getElementById("idReportePLD").value;
		    	//urlServicio = newUrlBase + "credito/reporteProcesosCampania?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&campania="+ids;
		    	urlServicio = newUrlBase + "credito/reporteBasesCampaniasMc?dtFechaInicio=" + document.getElementById("FechaInicio").value + "&dtFechaFin=" + document.getElementById("FechaFin").value + "&idCampanias=" + campaniasString;
		    	
		    	//console.log("urlServicio:", urlServicio);
		    	//urlServicio = rptOA + "&dtFechaInicio=" + document.getElementById("dtFechaInicio").value + "&dtFechaFin=" + document.getElementById("dtFechaFin").value + "&nombreReporteSelect=" + document.getElementById("idReportePLD").value + "&extension=" + document.getElementById("extReportePLD").value + "&noCliente="+clienteValue;
		    	
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
					//var urlReporte = window.location.href.substring(0, 22) + respuesta[0];
		
					//console.log(urlReporte);
					//urlReporte = "http://localhost:8080/STD/exportarReporte/generarReporte?idReporte=9&parametros=?FechaInicio=2023-10-01~FechaFin=2023-10-02&tipoDocumento=.pdf&identificadorDoc=OA";
					window.open(urlReporte);
					window.focus();
		
				}, error: function(XMLHttpRequest, textStatus, errorThrown) {
					Swal.fire({
						title: "Error",
						text: "Se presento un error, intente mas tarde.",
						icon: "warning",
						confirmButtonText: "Aceptar"
					});
				}
				});
		    	//////////////
			}
			
		}else{
			
			Swal.fire({
			    title: "Alerta",
			    text: "Debe seleccionar un reporte.",
			    icon: "warning",
			    confirmButtonText: "Aceptar"
			});
		}
	} else {

		
		Swal.fire({
		    title: "Alerta",
		    text: mensajeError,
		    icon: "warning",
		    confirmButtonText: "Aceptar"
		});
	}
}