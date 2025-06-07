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
    //console.log(newUrlBase);
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
				
				console.log(cliente.pk_id_tipo_campania);
				idTipoCampania = cliente.pk_id_tipo_campania;
				console.log(idTipoCampania);
				
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

buscarCliente.addEventListener('input', debouncedListaClientes);

buscarCliente.addEventListener('input', function() {
	// Actualiza el valor seleccionado cada vez que se ingresa texto
	buscarCampanias();
});

const input = document.getElementById('buscarCampania');
const datalist = document.getElementById('opcionCampania');
const divDetalle = document.getElementById('detalleCampania');

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
                document.getElementById("MontoMinimo").value = data.d_monto_minimo;
                document.getElementById("MinimoVida").value = data.i_minimo_vida_credito;
                document.getElementById("CreditosLiquidados").value = data.i_creditos_liquidados;
                document.getElementById("EdadMaxima").value = data.i_edad_maxima;
                document.getElementById("EdadMinima").value = data.i_edad_minima;
                document.getElementById("MontoMaximo").value = data.d_monto_maximo;
                document.getElementById("MoraMaxima").value = data.i_mora_maxima;
                document.getElementById("PorceCubiertoCap").value = data.d_porc_cubierto_capital;
                document.getElementById("DiasGracia").value = data.i_dias_gracia;
                document.getElementById("DiasMora").value = data.i_dias_mora;
                document.getElementById("PlazoMaximoAnios").value = data.i_plazo_maximo_anios;
                document.getElementById("VigenciaBC").value = data.i_vigencia_bc;
                document.getElementById("VigenciaCampania").value = data.i_vigencia_campania;
                document.getElementById("ProdCredito").value = data.i_prod_credito_id_oferta;
                document.getElementById("PorceIncremento").value = data.d_proc_inc_oferta;
                document.getElementById("VigenciaCampaniaOferta").value = data.i_vigencia_oferta;
                document.getElementById("TasaOrdinaria").value = data.d_tasa_ordinaria_oferta;
                document.getElementById("MontoMaximoOferta").value = data.d_monto_maximo_oferta;
                //console.log(data.i_tipo_campania);
                // Puedes agregar más campos aquí si los tienes en tu modelo
                //console.log(data.vc_es_renovacion);
                cargarCatalogoTipoCampanias(data.i_tipo_campania);
                cargarCatalogoConvivenciaCamp(data.i_convivencia);
                cargarChecksEsRenovacion(data.vc_es_renovacion);
                cargarChecksEsRenovacionOferta(data.vc_es_renovacion_oferta);
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
            console.error('Error al cargar tipos campañas múltiples', err);
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
function cargarChecksEsRenovacionOferta(valorSeleccionado) {
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
}

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
	console.log("Entra a modificarTiposCampanias");
	if (document.getElementById('tipoCampanias').value == 0) {
		
		swal({
			title: "",
			text: "Debe seleccionar Tipo Campaña.",
			icon: "warning",
		});
			
	} else if (document.getElementById('tipoCampanias').value != 0) {
		
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
				
				} else if (document.getElementById('EdadMaxima').value != '') {
					
					if(esEntero(document.getElementById('EdadMaxima').value)){
						
						if (document.getElementById('EdadMinima').value == '') {
			
							swal({
								title: "",
								text: "Debe ingresar el campo Edad Minima.",
								icon: "warning",
							});
							
						} else if (document.getElementById('EdadMinima').value != '') {
							if(esEntero(document.getElementById('EdadMinima').value)){
								if (document.getElementById('MontoMinimo').value == '') {
				
									swal({
										title: "",
										text: "Debe ingresar el campo Monto Minimo.",
										icon: "warning",
									});
									
								} else if (document.getElementById('MontoMinimo').value != '') {
									
									if(esDecimalPositivo(document.getElementById('MontoMinimo').value)){
										
										if (document.getElementById('MontoMaximo').value == '') {
					
											swal({
												title: "",
												text: "Debe ingresar el campo Monto Máximo.",
												icon: "warning",
											});
											
										} else if (document.getElementById('MontoMaximo').value != '') {
											if(esDecimalPositivo(document.getElementById('MontoMaximo').value)){
												if (document.getElementById('MoraMaxima').value == '') {
						
													swal({
														title: "",
														text: "Debe ingresar el campo Mora Máxima.",
														icon: "warning",
													});
													
												} else if (document.getElementById('MoraMaxima').value != '') {
													if(esDecimalPositivo(document.getElementById('MoraMaxima').value)){
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
																																	if (document.getElementById('ProdCredito').value == '') {
																
																																		swal({
																																			title: "",
																																			text: "Debe ingresar el campo ProdCredito.",
																																			icon: "warning",
																																		});
																																		
																																	} else if (document.getElementById('ProdCredito').value != '') {
																																		
																																		if (document.getElementById('PorceIncremento').value == '') {
																
																																			swal({
																																				title: "",
																																				text: "Debe ingresar el campo PorceIncremento.",
																																				icon: "warning",
																																			});
																																			
																																		} else if (document.getElementById('PorceIncremento').value != '') {
																																			if(esEntero(document.getElementById('PorceIncremento').value)){
																																				
																																				let seleccionOferta = document.querySelector('input[name="checksEsRenovacionOferta"]:checked');
																														
																																				if (!seleccionOferta) {
																																					
																																					swal({
																																						title: "",
																																						text: "Debe seleccionar una opción en Es Renovación Oferta.",
																																						icon: "warning",
																																					});
																																					
																																				} else {
																																					
																																					if (document.getElementById('VigenciaCampaniaOferta').value == '') {
																																						swal({
																																							title: "",
																																							text: "Debe ingresar el campo VigenciaCampaniaOferta.",
																																							icon: "warning",
																																						});
																																						
																																					} else if (document.getElementById('VigenciaCampaniaOferta').value != '') {
																																						if(esEntero(document.getElementById('VigenciaCampaniaOferta').value)){
																																							
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
																																												///////////////
																																												///////////////////////
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
												}
											}else {
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
	}
}
