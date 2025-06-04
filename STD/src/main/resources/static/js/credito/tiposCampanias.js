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
            cargarCatalogoTipoCampanias();
            cargarCatalogoConvivenciaCamp();
            cargarCatalogoProductos();
            cargarChecksEsRenovacion();
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
                
                // Puedes agregar más campos aquí si los tienes en tu modelo
                //console.log(data.i_ventana_anios);
            })
            .catch(error => {
                console.error("Error al obtener detalles de la campaña:", error);
            });
            
    } else {
        divDetalle.style.display = 'none';
    }
});


//funcion para mostrar catalogo de tipos campañas (temporal/recurrente)
function cargarCatalogoTipoCampanias() {
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
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

//funcion para mostrar catalogo de convivencia entre campañas (si/no)
function cargarCatalogoConvivenciaCamp() {
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
        },
        error: function(err) {
            console.error('Error al cargar catálogos', err);
        }
    });
}

//funcion para llenar combo opcion multiple de productos
function cargarCatalogoProductos() {
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
        },
        error: function(err) {
            console.error('Error al cargar tipos campañas múltiples', err);
        }
    });
}


//cargar Checks es renovacion
function cargarChecksEsRenovacion() {
    $.ajax({
        url: newUrlBase + 'credito/api/catEsRenovacion',
        type: 'GET',
        success: function(data) {
			console.log("Datos recibidos:", data);
            let contenedor = $('#checksEsRenovacion');
            contenedor.empty(); // Limpiar contenido anterior

            data.forEach(function(item) {
                let radio = $('<input>')
                    .attr('type', 'radio')
                    .attr('name', 'checksEsRenovacion') // mismo name para agruparlos
                    .attr('value', item.i_opcion)
                    .attr('id', 'esRenovacion_' + item.i_opcion);

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
