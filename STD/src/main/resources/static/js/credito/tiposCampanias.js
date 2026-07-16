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
/**
 * Fin
 */
/**
* Lista clientes
*/
var buscarCliente = document.getElementById('buscarCampania');
var spinner = document.getElementById("spinnerClientes");
var clienteValue = 0;
var timerId;
var idTipoCampania = 0;

var tipoBuroRegistrado=0;
// Función para realizar la llamada al servicio después de un retraso
function debouncedListaClientes() {
	clearTimeout(timerId); // Limpiar el temporizador anterior, si existe
	timerId = setTimeout(buscarCampanias, 500); // Establecer un nuevo temporizador
}



/**
 * Fin
 */

function buscarCampanias() {
    var buscar = buscarCliente.value;
    //const divArchivo = document.getElementById("divArchivoCampania");
    //cuando ingrese el valor 0 se agrege un nuevo registro
    if (buscar.trim() === "0") {
		//document.getElementById("divArchivoCampania").style.display = "flex"; // o "block"

		//divArchivo.style.display = "block";
        divDetalle.style.display = 'block';
        divBotonAgregar.style.display = 'block';
        divBotonModificar.style.display = 'none';
        nomTipoCampania.style.display = 'block';
        //minimoVidaCreditoR.style.display = 'none';
        limpiarFormulario(); // función que deja todos los campos vacíos
        idTipoCampania = 0; // importante para distinguir creación de modificación
        cargarCatalogoTipoCampanias(0);
        cargarCatalogoConvivenciaCamp(0);
        cargarCatalogoProductosCamp(0);
        cargarChecksEsRenovacion(0);
        //cargarChecksEsRenovacionOferta(0);
        cargarChecksActiva(0);
        cargarTipoBuro(0);
        //let arraySeleccionados = data.vc_productos ? data.vc_productos.split(',') : [];
		cargarCatalogoProductos([]);
		
        return;
    }else{
	    //console.log(newUrlBase);
	    //document.getElementById("divArchivoCampania").style.display = "none";

	     //divArchivo.style.display = "none";
		$.ajax({
			url: newUrlBase + "credito/buscarCampanias?valor=" + buscar,
			type: "GET",
			dataType: "JSON",
			success: function(respuesta) {
				var opciones = document.getElementById('opcionCampania');
				opciones.innerHTML = '';
				respuesta.lista.forEach(function(cliente) {
					
					//console.log(cliente);
					var option = document.createElement('option');
					option.setAttribute('data-value', cliente.vc_tipo_campania);
					
					//console.log(cliente.pk_id_tipo_campania);
					idTipoCampania = cliente.pk_id_tipo_campania;
					//console.log(idTipoCampania);
					
					option.value = cliente.vc_tipo_campania;
					opciones.appendChild(option);
					
					option.setAttribute('data-id', cliente.pk_id_tipo_campania); // Necesario para luego usarlo
					option.value = cliente.vc_tipo_campania;
				});
				if (respuesta.lista[0]) {
					clienteValue = respuesta.lista[0].vc_tipo_campania; // Aquí asumo que deseas guardar el primer valor de i_clienteid                
				}
				//console.log(clienteValue);
				//console.log(opciones.innerHTML);
			}
		});
	}
}

function limpiarFormulario() {
    document.getElementById("Ventana").value = "";
    document.getElementById("VentanaMayor").value = "";
    document.getElementById("EdadMaxima").value = "";
    document.getElementById("EdadMinima").value = "";
    document.getElementById("MontoMinimo").value = "";
    document.getElementById("MontoMaximo").value = "";
    //document.getElementById("MoraMaxima").value = "";
    document.getElementById("PorceCubiertoCap").value = "";
    document.getElementById("PorceCubiertoCap").value = "";
    document.getElementById("DiasGracia").value = "";
    document.getElementById("DiasMora").value = "";
    document.getElementById("PlazoMaximoAnios").value = "";
    document.getElementById("MinimoVida").value = "";
    document.getElementById("MinimoVidaR").value = "";
    document.getElementById("VigenciaBC").value = "";
    document.getElementById("VigenciaCampania").value = "";
    document.getElementById("CreditosLiquidados").value = "";
    //document.getElementById("ProdCredito").value = "";
    document.getElementById("PorceIncremento").value = "";
    //document.getElementById("VigenciaCampaniaOferta").value = "";
    document.getElementById("TasaOrdinaria").value = "";
    document.getElementById("MontoMaximoOferta").value = "";
    // limpia todos los demás campos...

    //$('#tipoCampanias').val("0");
    //$('#convivenciaCamp').val("0");
    //$('#Productos').val([]);

    // Limpia radios
    //$("input[name='checksEsRenovacion']").prop("checked", false);
    //$("input[name='checksEsRenovacionOferta']").prop("checked", false);
    //$("input[name='checksActiva']").prop("checked", false);
}

buscarCliente.addEventListener('input', debouncedListaClientes);

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	buscarCampanias();
});

const input = document.getElementById('buscarCampania');
const datalist = document.getElementById('opcionCampania');
const divDetalle = document.getElementById('detalleCampania');
const divBotonModificar = document.getElementById('botonModificar');
const divBotonAgregar = document.getElementById('botonAgregar');
const nomTipoCampania = document.getElementById('nomTipoCampania');
const minimoVidaCreditoR = document.getElementById('minimoVidaCreditoR');

input.addEventListener('input', function () {
    const valor = input.value;
    let coincidencia = false;
    let idSeleccionado = null;

    // Recorremos todas las opciones del datalist
    for (let option of datalist.options) {
        if (option.value === valor) {
            coincidencia = true;
            //idSeleccionado = option.getAttribute('data-id');  // aquí obtienes el ID
            idSeleccionado = option.getAttribute('data-id'); // ← aquí obtienes el ID real
            break;
        }
    }

    // Si hay coincidencia, mostramos el div
    if (coincidencia) {
        divDetalle.style.display = 'block';
        divBotonModificar.style.display = 'block';
        divBotonAgregar.style.display = 'none';
        nomTipoCampania.style.display = 'none';
        //minimoVidaCreditoR.style.display = 'block';
        // Llama al servicio con el ID
       // fetch(newUrlBase + 'credito/obtenerCampania?id=' + idSeleccionado)
         //   .then(response => response.json())
           // .then(data => {
             //   console.log("Datos recibidos:", data);
               // document.getElementById("Ventana").value = data.i_ventana_anios;
                //document.getElementById("EdadMaxima").value = data.i_edad_maxima;
                // Agrega más campos si necesitas
            //});
            //cargarCatalogoTipoCampanias();
            //cargarCatalogoConvivenciaCamp();
            //cargarCatalogoProductos();
            //cargarChecksEsRenovacion();
            //cargarChecksEsRenovacionOferta();
            //cargarChecksActiva();
            // Llamada al backend para traer datos sin redireccionar
        fetch(newUrlBase + 'credito/obtenerCampania?id=' + idSeleccionado)
            .then(response => response.json())
            .then(data => {
				//console.log("Datos recibidos:", data);
                document.getElementById("Ventana").value = data.i_ventana_anios;
                document.getElementById("VentanaMayor").value = data.i_ventana_anios_mayor;
                document.getElementById("MontoMinimo").value = data.d_monto_minimo;
                document.getElementById("MinimoVida").value = data.i_minimo_vida_credito;
                document.getElementById("MinimoVidaR").value = data.i_minimo_vida_credito_r;
                document.getElementById("CreditosLiquidados").value = data.i_creditos_liquidados;
                document.getElementById("EdadMaxima").value = data.i_edad_maxima;
                document.getElementById("EdadMinima").value = data.i_edad_minima;
                document.getElementById("MontoMaximo").value = data.d_monto_maximo;
                //document.getElementById("MoraMaxima").value = data.i_dias_mora;
                document.getElementById("PorceCubiertoCap").value = data.d_porc_cubierto_capital;
                document.getElementById("DiasGracia").value = data.i_dias_gracia;
                document.getElementById("DiasMora").value = data.i_dias_mora;
                document.getElementById("PlazoMaximoAnios").value = data.i_plazo_maximo_anios;
                document.getElementById("VigenciaBC").value = data.i_vigencia_bc;
                document.getElementById("VigenciaCampania").value = data.i_vigencia_campania;
                //document.getElementById("ProdCredito").value = data.i_prod_credito_id_oferta;
                document.getElementById("PorceIncremento").value = data.d_porc_inc_oferta;
                //document.getElementById("VigenciaCampaniaOferta").value = data.i_vigencia_oferta;
                document.getElementById("TasaOrdinaria").value = data.d_tasa_ordinaria_oferta;
                document.getElementById("MontoMaximoOferta").value = data.d_monto_maximo_oferta;
                //console.log(data.i_tipo_campania);
                // Puedes agregar más campos aquí si los tienes en tu modelo
                //console.log(data.vc_es_renovacion);
                cargarCatalogoTipoCampanias(data.i_tipo_campania);
                cargarCatalogoConvivenciaCamp(data.i_convivencia);
                cargarTipoBuro(data.i_tipo_buro);
              
                cargarCatalogoProductosCamp(data.i_prod_credito_id_oferta);
                cargarChecksEsRenovacion(data.vc_es_renovacion);
                //cargarChecksEsRenovacionOferta(data.vc_es_renovacion_oferta);
                
                tipoBuroRegistrado= data.i_tipo_buro;
                //console.log("vc_es_renovacion:", data.vc_es_renovacion);
                if(data.vc_es_renovacion === 'S'){
					
					minimoVidaCreditoR.style.display = 'block';
					//document.getElementById("MinimoVidaR").value = data.i_minimo_vida_credito_r;
				}else{
					
					minimoVidaCreditoR.style.display = 'none';
				}
                
                cargarChecksActiva(data.i_activa_oferta);
                let arraySeleccionados = data.vc_productos ? data.vc_productos.split(',') : [];
				cargarCatalogoProductos(arraySeleccionados);
                //cargarCatalogoProductos(data.vc_productos);
            })
            .catch(error => {
                console.error("Error al obtener detalles de la campaña:", error);
            });
            
    } else {
        divDetalle.style.display = 'none';
        divBotonModificar.style.display = 'none';
       //minimoVidaCreditoR.style.display = 'none';
    }
});


//funcion para mostrar catalogo de tipos campañas (temporal/recurrente)
function cargarCatalogoTipoCampanias(valorSeleccionado) {
    $.ajax({
        url: newUrlBase + 'credito/api/catTipoCampanias',
        type: 'GET',
        success: function(data) {
            let select = $('#tipoCampanias');
            select.empty();
            select.append('<option value="0">Seleccione</option>');

            data.forEach(function(item) {
                select.append('<option value="' + item.i_opcion + '">' + item.v_valor + '</option>');
            });
            //$('#tipoCampanias').val("1");
            if (valorSeleccionado) {
                select.val(valorSeleccionado);
            }
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

//funcion para mostrar catalogo de convivencia entre campañas (si/no)
function cargarCatalogoConvivenciaCamp(valorSeleccionado) {
    $.ajax({
        url: newUrlBase + 'credito/api/catConvivenciaCamp',
        type: 'GET',
        success: function(data) {
            let select = $('#convivenciaCamp');
            select.empty();
            select.append('<option value="0">Seleccione</option>');

            data.forEach(function(item) {
                select.append('<option value="' + item.i_opcion + '">' + item.v_valor + '</option>');
            });
            if (valorSeleccionado) {
                select.val(valorSeleccionado);
            }
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

//funcion para mostrar catalogo de tipos buro de credito (Adviser/Prospector)
function cargarTipoBuro(valorSeleccionado) {
    $.ajax({
        url: newUrlBase + 'credito/api/catTipoBuroCam',
        type: 'GET',
        success: function(data) {
            let select = $('#tipoArchivoBuro');
            select.empty();
            select.append('<option value="0">Seleccione</option>');

            data.forEach(function(item) {
                select.append('<option value="' + item.i_opcion + '">' + item.v_valor + '</option>');
            });
            if (valorSeleccionado) {
                select.val(valorSeleccionado);
            }
        },
        error: function(err) {
            console.error('Error al cargar catálogos tipo Buro', err);
        }
    });
}

//funcion para mostrar catalogo de productos
function cargarCatalogoProductosCamp(valorSeleccionado) {
    $.ajax({
        url: newUrlBase + 'credito/api/catProductosCamp',
        type: 'GET',
        success: function(data) {
            let select = $('#prodCredito');
            select.empty();
            select.append('<option value="0">Seleccione</option>');

            data.forEach(function(item) {
                select.append('<option value="' + item.pkIProducCreditoId + '">' + item.pkIProducCreditoId + "-" + item.vcDescripcion + '</option>');
            });
            if (valorSeleccionado) {
                select.val(valorSeleccionado);
            }
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

//funcion para llenar combo opcion multiple de productos
function cargarCatalogoProductos(valoresSeleccionados) {
    $.ajax({
        url: newUrlBase + 'credito/api/catProductos',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let select = $('#Productos');
            select.empty();
            data.forEach(function(item) {
                select.append('<option value="' + item.pkIProducCreditoId + '">' + item.vcDescripcion + '</option>');
            });
            
            // Marcar seleccionados
        	select.val(valoresSeleccionados.map(String));
            
        },
        error: function(err) {
            console.error('Error al cargar productos', err);
        }
    });
}


//cargar Checks es renovacion
function cargarChecksEsRenovacion(valorSeleccionado) {
	
	let valorSiNo ='';
	
    $.ajax({
        url: newUrlBase + 'credito/api/catEsRenovacion',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let contenedor = $('#checksEsRenovacion');
            contenedor.empty(); // Limpiar contenido anterior

            data.forEach(function(item) {
                let radio = $('<input>')
                    .attr('type', 'radio')
                    .attr('name', 'checksEsRenovacion') // mismo name para agruparlos
                    .attr('value', item.i_opcion)
                    .attr('id', 'esRenovacion_' + item.i_opcion);
                    
                if (item.i_opcion == 1){
					valorSiNo = 'S';
				}else if (item.i_opcion == 2){
					valorSiNo = 'N';
				}
                    
                    //console.log(item.i_opcion);
                    
                if (valorSeleccionado === valorSiNo) {
                radio.prop('checked', true);
                }

                let label = $('<label>')
                    .attr('for', 'esRenovacion_' + item.i_opcion)
                    .text(item.v_valor);

                contenedor.append(radio);
                contenedor.append(label);
                contenedor.append('<br>'); // Salto de línea entre opciones
            });
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

//cargar Checks es renovacionOferta
/*function cargarChecksEsRenovacionOferta(valorSeleccionado) {
    $.ajax({
        url: newUrlBase + 'credito/api/catEsRenovacionOferta',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let contenedor = $('#checksEsRenovacionOferta');
            contenedor.empty(); // Limpiar contenido anterior

            data.forEach(function(item) {
                let radio = $('<input>')
                    .attr('type', 'radio')
                    .attr('name', 'checksEsRenovacionOferta') // mismo name para agruparlos
                    .attr('value', item.i_opcion)
                    .attr('id', 'esRenovacionOferta_' + item.i_opcion);
                    
                if (valorSeleccionado != null && valorSeleccionado.toString() === item.i_opcion.toString()) {
				    radio.prop('checked', true);
				}
                
                //console.log(valorSeleccionado);
                //console.log(item.i_opcion);

                let label = $('<label>')
                    .attr('for', 'esRenovacionOferta_' + item.i_opcion)
                    .text(item.v_valor);

                contenedor.append(radio);
                contenedor.append(label);
                contenedor.append('<br>'); // Salto de línea entre opciones
            });
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}*/

//cargar Checks es Activa
function cargarChecksActiva(valorSeleccionado) {
    $.ajax({
        url: newUrlBase + 'credito/api/catActiva',
        type: 'GET',
        success: function(data) {
			//console.log("Datos recibidos:", data);
            let contenedor = $('#checksActiva');
            contenedor.empty(); // Limpiar contenido anterior

            data.forEach(function(item) {
                let radio = $('<input>')
                    .attr('type', 'radio')
                    .attr('name', 'checksActiva') // mismo name para agruparlos
                    .attr('value', item.i_opcion)
                    .attr('id', 'activa_' + item.i_opcion);
                   
               	if (valorSeleccionado === item.i_opcion) {
                radio.prop('checked', true);
                }

                let label = $('<label>')
                    .attr('for', 'activa_' + item.i_opcion)
                    .text(item.v_valor);

                contenedor.append(radio);
                contenedor.append(label);
                contenedor.append('<br>'); // Salto de línea entre opciones
            });
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

function esEntero(valor) {
    return /^\d+$/.test(valor);
}

function esDecimalPositivo(valor) {
    return /^\d+(\.\d+)?$/.test(valor);
}

var mensajeError = "Datos incorrectos, favor de validar.";

function modificarTiposCampanias(){
	
	if (document.getElementById('tipoCampanias').value == 0) {
		
		Swal.fire({
		    title: "",
		    text: "Debe seleccionar Tipo Campaña.",
		    icon: "warning"
		});
			
	} else if (document.getElementById('tipoCampanias').value != 0) {
		
		if (document.getElementById('VentanaMayor').value == '') {
				
			Swal.fire({
			    title: "",
			    text: "Debe ingresar el campo Ventana mayor que.",
			    icon: "warning"
			});
				
		} else if (document.getElementById('Ventana').value != '') {
			
			//if(esEntero(document.getElementById('VentanaMayor').value)){
		
		
			if (document.getElementById('Ventana').value == '') {
					
				swal({
					title: "",
					text: "Debe ingresar el campo Ventana.",
					icon: "warning",
				});
					
			} else if (document.getElementById('Ventana').value != '') {
				
				if(esEntero(document.getElementById('Ventana').value)){
					
					if (document.getElementById('EdadMaxima').value == '') {
			
						swal({
							title: "",
							text: "Debe ingresar el campo Edad Máxima.",
							icon: "warning",
						});
					
					} else if (document.getElementById('EdadMaxima').value != '' && document.getElementById('EdadMaxima').value > 0) {
						
						if(esEntero(document.getElementById('EdadMaxima').value)){
							
							if (document.getElementById('EdadMinima').value == '') {
				
								swal({
									title: "",
									text: "Debe ingresar el campo Edad Minima.",
									icon: "warning",
								});
								
							} else if (document.getElementById('EdadMinima').value != '' && document.getElementById('EdadMinima').value > 0) {
								if(esEntero(document.getElementById('EdadMinima').value)){
									if (document.getElementById('MontoMinimo').value == '') {
					
										swal({
											title: "",
											text: "Debe ingresar el campo Monto Minimo.",
											icon: "warning",
										});
										
									} else if (document.getElementById('MontoMinimo').value != '' && document.getElementById('MontoMinimo').value > 0) {
										
										if(esDecimalPositivo(document.getElementById('MontoMinimo').value)){
											
											if (document.getElementById('MontoMaximo').value == '') {
						
												swal({
													title: "",
													text: "Debe ingresar el campo Monto Máximo.",
													icon: "warning",
												});
												
											} else if (document.getElementById('MontoMaximo').value != '' && document.getElementById('MontoMaximo').value > 0) {
												if(esDecimalPositivo(document.getElementById('MontoMaximo').value)){
													//if (document.getElementById('MoraMaxima').value == '') {
							
														/*swal({
															title: "",
															text: "Debe ingresar el campo Mora Máxima.",
															icon: "warning",
														});*/
														
													//} else if (document.getElementById('MoraMaxima').value != '') {
														//if(esDecimalPositivo(document.getElementById('MoraMaxima').value)){
															if (document.getElementById('PorceCubiertoCap').value == '') {
								
																swal({
																	title: "",
																	text: "Debe ingresar el campo Porcentaje Cubierto Capital.",
																	icon: "warning",
																});
																
															} else if (document.getElementById('PorceCubiertoCap').value != '') {
																if(esDecimalPositivo(document.getElementById('PorceCubiertoCap').value) && document.getElementById('PorceCubiertoCap').value <= 100 && document.getElementById('PorceCubiertoCap').value > 0){
																	if (document.getElementById('DiasGracia').value == '') {
									
																		swal({
																			title: "",
																			text: "Debe ingresar el campo Días Gracia.",
																			icon: "warning",
																		});
																		
																	} else if (document.getElementById('DiasGracia').value != '') {
																		if(esEntero(document.getElementById('DiasGracia').value)){
																			if (document.getElementById('DiasMora').value == '') {
										
																				swal({
																					title: "",
																					text: "Debe ingresar el campo Días Mora.",
																					icon: "warning",
																				});
																				
																			} else if (document.getElementById('DiasMora').value != '') {
																				if(esEntero(document.getElementById('DiasMora').value)){
																					if (document.getElementById('PlazoMaximoAnios').value == '') {
											
																						swal({
																							title: "",
																							text: "Debe ingresar el campo Plazo Máximo Años.",
																							icon: "warning",
																						});
																						
																					} else if (document.getElementById('PlazoMaximoAnios').value != '') {
																						if(esEntero(document.getElementById('PlazoMaximoAnios').value) && document.getElementById('PlazoMaximoAnios').value>0){
																							if (document.getElementById('MinimoVida').value == '') {
												
																								swal({
																									title: "",
																									text: "Debe ingresar el campo Minimo Vida.",
																									icon: "warning",
																								});
																								
																							} else if (document.getElementById('MinimoVida').value != '') {
																								if(esEntero(document.getElementById('MinimoVida').value) && document.getElementById('MinimoVida').value>0){
																									if (document.getElementById('VigenciaBC').value == '') {
													
																										swal({
																											title: "",
																											text: "Debe ingresar el campo Vigencia BC.",
																											icon: "warning",
																										});
																										
																									} else if (document.getElementById('VigenciaBC').value != '') {
																										if(esEntero(document.getElementById('VigenciaBC').value)){
																											if (document.getElementById('VigenciaCampania').value == '') {
														
																												swal({
																													title: "",
																													text: "Debe ingresar el campo Vigencia Campaña.",
																													icon: "warning",
																												});
																												
																											} else if (document.getElementById('VigenciaCampania').value != '') {
																												if(esEntero(document.getElementById('VigenciaCampania').value)){
																													if (document.getElementById('CreditosLiquidados').value == '') {
															
																														swal({
																															title: "",
																															text: "Debe ingresar el campo Créditos Liquidados.",
																															icon: "warning",
																														});
																														
																													} else if (document.getElementById('CreditosLiquidados').value != '') {
																														if(esEntero(document.getElementById('CreditosLiquidados').value)){
																															let seleccion = document.querySelector('input[name="checksEsRenovacion"]:checked');
																															
																															if (!seleccion) {
																
																																swal({
																																	title: "",
																																	text: "Debe seleccionar una opción en Es Renovación.",
																																	icon: "warning",
																																});
																																
																															} else{
																																
																																if (document.getElementById('convivenciaCamp').value == 0) {
																	
																																	swal({
																																		title: "",
																																		text: "Debe seleccionar el campo Convivencia Campaña.",
																																		icon: "warning",
																																	});
																																	
																																} else if (document.getElementById('convivenciaCamp').value > 0) {
																																	let select = document.getElementById("Productos");
																																	let seleccionProd = Array.from(select.selectedOptions);
																																	//let seleccionados = document.querySelectorAll('input[name="productos"]:checked');
																																	if (seleccionProd.length === 0) {
																	
																																		swal({
																																			title: "",
																																			text: "Debe seleccionar al menos un producto.",
																																			icon: "warning",
																																		});
																																		
																																	} else{
																																		//////////////como debe ir ProdCredito?//////////////7
																																		if (document.getElementById('prodCredito').value == 0) {
																	
																																			swal({
																																				title: "",
																																				text: "Debe seleccionar el campo ProdCredito.",
																																				icon: "warning",
																																			});
																																			
																																		} else if (document.getElementById('prodCredito').value != '') {
																																			
																																			if (document.getElementById('PorceIncremento').value == '') {
																	
																																				swal({
																																					title: "",
																																					text: "Debe ingresar el campo PorceIncremento.",
																																					icon: "warning",
																																				});
																																				
																																			} else if (document.getElementById('PorceIncremento').value != '') {
																																				if(esEntero(document.getElementById('PorceIncremento').value)){
																																					
																																					//let seleccionOferta = document.querySelector('input[name="checksEsRenovacionOferta"]:checked');
																															
																																					/*if (!seleccionOferta) {
																																						
																																						swal({
																																							title: "",
																																							text: "Debe seleccionar una opción en Es Renovación Oferta.",
																																							icon: "warning",
																																						});
																																						
																																					} else {*/
																																						
																																						/*if (document.getElementById('VigenciaCampaniaOferta').value == '') {
																																							swal({
																																								title: "",
																																								text: "Debe ingresar el campo VigenciaCampaniaOferta.",
																																								icon: "warning",
																																							});
																																							
																																						} else if (document.getElementById('VigenciaCampaniaOferta').value != '') {*/
																																							//if(esEntero(document.getElementById('VigenciaCampaniaOferta').value)){
																																								
																																								if (document.getElementById('TasaOrdinaria').value == '') {
																		
																																									swal({
																																										title: "",
																																										text: "Debe ingresar el campo TasaOrdinaria.",
																																										icon: "warning",
																																									});
																																									
																																								} else if (document.getElementById('TasaOrdinaria').value != '') {
																																									
																																									if(esDecimalPositivo(document.getElementById('TasaOrdinaria').value)){
																																										
																																										let seleccionActiva = document.querySelector('input[name="checksActiva"]:checked');
																															
																																										if (!seleccionActiva) {
																			
																																											swal({
																																												title: "",
																																												text: "Debe ingresar el campo checksActiva.",
																																												icon: "warning",
																																											});
																																											
																																										} else {
																																											
																																											if (document.getElementById('MontoMaximoOferta').value == '') {
																				
																																												swal({
																																													title: "",
																																													text: "Debe ingresar el campo Monto Máximo Oferta.",
																																													icon: "warning",
																																												});
																																												
																																											} else if (document.getElementById('MontoMaximoOferta').value != '') {
																																												
																																												if(esDecimalPositivo(document.getElementById('MontoMaximoOferta').value)){
																																													///////////////////////aqui inicia el
																																													
																																													const checksEsRenovacion = document.getElementsByName("checksEsRenovacion");
																																												    let valorSiNo = "";
																																												
																																												    for (let radio of checksEsRenovacion) {
																																												        if (radio.checked) {
																																												            valorSiNo = radio.value == "1" ? "S" : "N";
																																												            break;
																																												        }
																																												    }
																																												    let minimovidaCredR = 0;
																																												    let valorMinimoVidaCredR = 0;
																																												    let banderaMinimoVidaCredito = 0;
																																												    if(valorSiNo === 'S'){
																																														console.log("entra a  es S");
																																														if (document.getElementById('MinimoVidaR').value == '') {
																																															
																																															banderaMinimoVidaCredito = 1;
																																															
																																															console.log("entra a Debe ingresar el campo Minimo Vida del Crédito Renovación(meses).");
												
																																															swal({
																																																title: "",
																																																text: "Debe ingresar el campo Minimo Vida del Crédito Renovación(meses).",
																																																icon: "warning",
																																															});
																																															
																																														} else if (document.getElementById('MinimoVidaR').value != '') {
																																															if(esEntero(document.getElementById('MinimoVidaR').value) && document.getElementById('MinimoVidaR').value>0){
																																																minimovidaCredR = 1;
																																																valorMinimoVidaCredR = parseInt(document.getElementById("MinimoVidaR").value);
																																																console.log("entra a es correcto y lo va guardar");
																																																}else{
																																																	banderaMinimoVidaCredito = 1;
																																																	console.log("entra a no es entero");
																																																	swal({
																																																		title: "",
																																																		text: mensajeError,
																																																		icon: "warning",
																																																	});
																																																}
																																														}
																																													}else{
																																														console.log("entra a  es N");
																																														minimovidaCredR = 1;
																																														valorMinimoVidaCredR = 0;
																																													}
																																													
																																													/*if(minimovidaCredR = 1){
																																														
																																													}else{
																																														swal({
																																															title: "",
																																															text: mensajeError,
																																															icon: "warning",
																																														});
																																													}*/
																																													
																																													/////////////////////////////////////////////////////////////////////////////////////////
																																													//va un if si renovacion es Si entonces validar que el otro campo este lleno
																																													if(minimovidaCredR === 1){
																																														//console.log("entra a solo deberia ser correcto o es N");
																																														const selectElement = document.getElementById("Productos");
																																														const selectedProductos = Array.from(selectElement.selectedOptions).map(option => option.value);
																																													    const productosSelect = selectedProductos.join(",");
																																													    //console.log(productosSelect);
																																														
																																														//const checksEsRenovacionOferta = document.getElementsByName("checksEsRenovacionOferta");
																																													    /*let vc_es_renovacion_oferta_checkbox = "";
																																													    for (let radio of checksEsRenovacionOferta) {
																																													        if (radio.checked) {
																																													            vc_es_renovacion_oferta_checkbox = radio.value; // será "1" o "2"
																																													            break;
																																													        }
																																													    }*/
																																														
																																														const checksActiva = document.getElementsByName("checksActiva");
																																													    let activa_checkbox = "";
																																													    for (let radio of checksActiva) {
																																													        if (radio.checked) {
																																													            activa_checkbox = radio.value; // será "1" o "2"
																																													            break;
																																													        }
																																													    }
																																													    
																																													    let nomTipoCampania = '';
																																													    if(idTipoCampania === 0){
																																															if (document.getElementById('NomTipoCampania').value == '') {
																					
																																																swal({
																																																	title: "",
																																																	text: "Debe ingresar descripción para tipo campaña.",
																																																	icon: "warning",
																																																});
																																																
																																															} else if (document.getElementById('NomTipoCampania').value != '') {
																																																nomTipoCampania = document.getElementById("NomTipoCampania").value;
																																															}
																																														}
		
																																														const data = {
																																													        pk_id_tipo_campania: idTipoCampania,
																																													        vc_tipo_campania: nomTipoCampania,
																																													        i_tipo_campania: parseInt(document.getElementById("tipoCampanias").value),
																																													        i_ventana_anios: parseInt(document.getElementById("Ventana").value),
																																													        i_ventana_anios_mayor: parseInt(document.getElementById("VentanaMayor").value),
																																													        i_edad_maxima: parseInt(document.getElementById("EdadMaxima").value),
																																													        i_edad_minima: parseInt(document.getElementById("EdadMinima").value),
																																													        d_monto_minimo: parseFloat(document.getElementById("MontoMinimo").value),
																																													        d_monto_maximo: parseFloat(document.getElementById("MontoMaximo").value),
																																													        //i_dias_mora: parseInt(document.getElementById("MoraMaxima").value),
																																													        d_porc_cubierto_capital: parseFloat(document.getElementById("PorceCubiertoCap").value),
																																													        i_dias_gracia: parseInt(document.getElementById("DiasGracia").value),
																																													        i_dias_mora: parseInt(document.getElementById("DiasMora").value),
																																													        i_plazo_maximo_anios: parseInt(document.getElementById("PlazoMaximoAnios").value),
																																													        i_minimo_vida_credito: parseInt(document.getElementById("MinimoVida").value),
																																													        i_vigencia_bc: parseInt(document.getElementById("VigenciaBC").value),
																																													        i_vigencia_campania: parseInt(document.getElementById("VigenciaCampania").value),
																																													        i_creditos_liquidados: parseInt(document.getElementById("CreditosLiquidados").value),
																																													        vc_es_renovacion:valorSiNo,
																																													        i_convivencia: parseInt(document.getElementById("convivenciaCamp").value),
																																													        vc_productos: productosSelect,
																																													        i_prod_credito_id_oferta: parseInt(document.getElementById("prodCredito").value),
																																													        d_porc_inc_oferta: parseFloat(document.getElementById("PorceIncremento").value),
																																													        
																																													        //i_vigencia_oferta: parseInt(document.getElementById("VigenciaCampaniaOferta").value),
																																													        d_tasa_ordinaria_oferta: parseInt(document.getElementById("TasaOrdinaria").value),
																																													        i_activa_oferta: parseInt(activa_checkbox),
																																													        d_monto_maximo_oferta: parseFloat(document.getElementById("MontoMaximoOferta").value),
																																													        i_minimo_vida_credito_r: valorMinimoVidaCredR,
																																													        //i_user_reg: parseInt(document.getElementById("idUser").value),
																																													        i_estatus: 1,
																																													        dt_fecha_registro:document.getElementById("idFecha").value,
																																													        i_tipo_buro: parseInt(document.getElementById("tipoArchivoBuro").value),
																																													    };
																																													    //console.log(data);
																																													    const jsonBitacora = JSON.stringify(data);
																																													    let tabla = 'TBL_TIPOS_CAMPANIA';
																																													    console.log(jsonBitacora);
																																													    const dataHis = {
																																													        vc_json_entrada: jsonBitacora,
																																													        vc_tabla: tabla,
																																													        i_user_reg: parseInt(document.getElementById("idUser").value),
																																													        
																																													    };
																																													    let endpoint = '';
																																													    //const endpoint = idTipoCampania === 0 ? 'credito/api/crear' : 'credito/api/actualizar';
																																													    if(idTipoCampania === 0){
																																															endpoint = 'credito/api/crear';
																																															delete data.pk_id_tipo_campania;
																																														}else{
																																															endpoint = 'credito/api/actualizar';
																																															delete data.vc_tipo_campania;
																																															//delete data.i_user_reg;
																																														}
																																													    //fetch(newUrlBase + 'credito/api/actualizar', {
																																														fetch(newUrlBase + endpoint, {
																																													        method: 'POST',
																																													        headers: {
																																													            'Content-Type': 'application/json'
																																													        },
																																													        body: JSON.stringify(data)
																																													    })
																																													    .then(resp => {
																																														    if (!resp.ok) throw new Error("Error al actualizar");
																																														    return resp.text();
																																														})
																																														.then(() => {
																																															
																																															///
																																															fetch(newUrlBase + 'credito/api/crearHis', {
																																														        method: 'POST',
																																														        headers: {
																																														            'Content-Type': 'application/json'
																																														        },
																																														        body: JSON.stringify(dataHis)
																																														    })
																																															///
																																															
																																															Swal.fire({
																																															    title: "",
																																															    text: "Actualizado correctamente",
																																															    icon: "success"
																																															}).then(() => {
																																															    window.location.href =
																																															        newUrlBase + "credito/tiposCampanias";
																																															});
																																														})
																																														.catch(err => {
																																														    console.error("Error al actualizar:", err);

																																														    Swal.fire({
																																														        title: "",
																																														        text: "Error al actualizar",
																																														        icon: "error"
																																														    });
																																														});
																																														///////////////////////
																																													}else{
																																														swal({
																																															title: "",
																																															text: mensajeError,
																																															icon: "warning",
																																														});
																																													}///aqui termina el ultimo else de la validaciones
																																												}else{ 
																																														swal({
																																															title: "",
																																															text: mensajeError,
																																															icon: "warning",
																																														});
																																													}
																																											}
																																										}
																																									}else{
																																										swal({
																																											title: "",
																																											text: mensajeError,
																																											icon: "warning",
																																										});
																																									}
																																									
																																								}
																																							}else{
																																								swal({
																																									title: "",
																																									text: mensajeError,
																																									icon: "warning",
																																								});
																																							}
																																							
																																							
																																						//}
																																					//}
																																					//aqui termina
																																				/*}else{
																																					swal({
																																						title: "",
																																						text: mensajeError,
																																						icon: "warning",
																																					});
																																				}*/
																																				
																																			}
																																		}
																																	}
																																}
																															}
																														}else{
																															swal({
																																title: "",
																																text: mensajeError,
																																icon: "warning",
																															});
																														}
																													}
																												}else{
																													swal({
																														title: "",
																														text: mensajeError,
																														icon: "warning",
																													});
																												}
																											}
																										}else{
																											swal({
																												title: "",
																												text: mensajeError,
																												icon: "warning",
																											});
																										}
																									}
																								}else{
																									swal({
																										title: "",
																										text: mensajeError,
																										icon: "warning",
																									});
																								}
																							}
																						}else{
																							swal({
																								title: "",
																								text: mensajeError,
																								icon: "warning",
																							});
																						}
																					}
																				}else{
																					swal({
																						title: "",
																						text: mensajeError,
																						icon: "warning",
																					});
																				}
																			}
																		}else{
																			swal({
																				title: "",
																				text: mensajeError,
																				icon: "warning",
																			});
																		}
																	}
																}else{
																	swal({
																		title: "",
																		text: mensajeError,
																		icon: "warning",
																	});
																}
															}
														}else{
															swal({
																title: "",
																text: mensajeError,
																icon: "warning",
															});
														}
													//}
												/*}else {
													swal({
														title: "",
														text: mensajeError,
														icon: "warning",
													});
												}*/
											}
										}else{
											swal({
												title: "",
												text: mensajeError,
												icon: "warning",
											});
										}
										
									}
								}else{
									swal({
										title: "",
										text: mensajeError,
										icon: "warning",
									});
								}
							}
						}else{
							swal({
								title: "",
								text: mensajeError,
								icon: "warning",
							});
						}
					}
				}else{
					swal({
						title: "",
						text: mensajeError,
						icon: "warning",
					});
				}
			}
		}
	}
}


document.addEventListener("DOMContentLoaded", function () {
    
    const checkbox = document.getElementById("checksEsRenovacion");
    const div = document.getElementById("minimoVidaCreditoR");

    checkbox.addEventListener("change", function () {
		
	const checksEsRenovacionMinimo = document.getElementsByName("checksEsRenovacion");
	let valorSiNoMinimo = "";
								
    for (let radio of checksEsRenovacionMinimo) {
        if (radio.checked) {
            valorSiNoMinimo = radio.value == "1" ? "S" : "N";
            break;
        }
    }
    
    if(valorSiNoMinimo === 'S'){
		minimoVidaCreditoR.style.display = 'block';
	}else{
		minimoVidaCreditoR.style.display = 'none';
	}
	    
    });
    
});

/**alerta al cambiar tipo buro */
function mensajeAlertaTipoBuro(tipoBuroSelec){
	
	if(parseInt(tipoBuroSelec) !== tipoBuroRegistrado && parseInt(tipoBuroSelec) > 0 ){
			
		Swal.fire({
		    title: "Alerta",
		    text: "Si el cambio se confirma, la configuración actual de tipo de buró se inactivará y se deberá configurar nuevamente.",
		    icon: "info",
		    allowOutsideClick: false,
		    timer: 10000
		});
		
	}

}

