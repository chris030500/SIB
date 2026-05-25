/**
 * js campañas temporales
 */
// @ts-nocheck

var url = "";
var tblInformacionProceso;
const opcionCampania = document.getElementById("opcionCampania");
let codigoCamp="";
var nomCampania="";

document.addEventListener("DOMContentLoaded", function() {
	iniciarFechas();
});

function iniciarFechas(){
	const hoy = new Date().toISOString().split('T')[0];
    //document.getElementById("fechaInicio").value = hoy;
    //document.getElementById("fechaFin").value = hoy;
    document.getElementById("fechaRegistro").value = hoy;
    //document.getElementById("fechaEnvioMC").value = hoy;
    
    //document.getElementById("fechaInicio").setAttribute("min", hoy);
    document.getElementById("fechaFin").setAttribute("min", hoy);
    document.getElementById("fechaRegistro").setAttribute("min", hoy);
    document.getElementById("fechaEnvioMC").setAttribute("min", hoy);
}

$(document).ready(function() {
	$("#dtosCampania").hide();
	subirArchivo();

	tblInformacionProceso = $("#tblInformacionProceso").DataTable({
		language: {
			processing: "Procesando...",
			search: "Buscar:",
			lengthMenu: "Mostrar _MENU_ registros",
			info: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_",
			infoEmpty: "Mostrando registros del 0 al 0 de un total de 0",
			infoFiltered: "(filtrado de un total de _MAX_ registros)",
			infoPostFix: "",
			loadingRecords: "Cargando...",
			zeroRecords: "No se encontraron resultados",
			emptyTable: "Ningún dato disponible en esta tabla",
			paginate: {
				first: "Primero",
				previous: "Anterior",
				next: "Siguiente",
				last: "Último"
			},
			aria: {
				sortAscending: ": Activar para ordenar la columna de manera ascendente",
				sortDescending: ": Activar para ordenar la columna de manera descendente"
			}
		},
		order: [[0, "desc"]], // Opcional: ordenar por la primera columna en orden descendente
		aoColumnDefs: [{
			bSortable: false,
			aTargets: [-1] // La última columna no es ordenable
		}]

	});
});

opcionCampania.addEventListener("input", function() {
	this.value = this.value.toUpperCase();
});

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function tamFile() {
	const tamMaxFile = 31457280;
	var fileTam = document.getElementById("file");
	fileTam.onchange = function() {
		if (this.files[0].size > tamMaxFile) {
			swal("El archivo seleccionado excede el tamaño maximo permitido (30MB)", {
				closeOnClickOutside: false,
				icon: "warning",
			}).then(resp => {
				this.value = "";
			});
		};
	};
}

function subirArchivo() {


	$("#btnGuardarCampania").click(function(event) {
		event.preventDefault();  // <-- evita envío normal del form
		// Este código se ejecutará después de que el temporizador haya terminado
		guardarDtosTmp();
		
	});

	$("#btnGuardarFile").click(function() {
		codigoCamp="";
		console.log("Click detectado");
		if (document.getElementById("file").value.length > 0) {

			var form = $('#formArchivoCampaniaTmp')[0];
			var data = new FormData(form);

			// Opcional: deshabilitar botón para evitar doble envío
			document.getElementById('btnGuardarFile').disabled = true;
			// Si necesitas agregar el parámetro idCmpTmp desde JS, hazlo aquí:
			data.append("idCmpTmp", document.getElementById("idCampaniaSeleccionada").value);
			
			
			
			codigoCamp =  (document.getElementById("codCampaniaR").value.length >0)?document.getElementById("codCampaniaR").value:parseInt(document.getElementById("idCampaniaSeleccionada").value || "0", 10) + '-' + generarUUIDCorto();
			
			data.append("codCampania",codigoCamp)
			procesarCampaniaTmp(data)



		} else {

			swal("Es necesario seleccionar un Archivo.", {
				closeOnClickOutside: false,
				icon: "error",
			});
		}

	});
}
//validarDatos
function validarDatos() {
	let pasoValidacion = 0;

	return pasoValidacion;
}
var dtosCampaniaTmp = "registroCmpTmp";
function guardarDtosTmp() {
	const form = document.getElementById('formDatosCampania');


	if (validarFormulario(form)) {
		swal({
			title: 'Datos requeridos',
			text: 'Por favor complete todos los campos obligatorios.',
			icon: 'warning',
			button: 'Aceptar'
		});

	} else {
		const formData = new FormData(form);
		
		const recurrente = document.getElementById("checkRecurrente").checked;
		formData.set("recurrente", recurrente);
		
		codigoCamp = (document.getElementById("codCampaniaR").value.length >0)?document.getElementById("codCampaniaR").value:parseInt(document.getElementById("idCampaniaSeleccionada").value || "0", 10) + '-' + generarUUIDCorto();
		
		formData.append("codCampania", codigoCamp);
		// Opcional: deshabilitar botón para evitar doble envío
		document.getElementById('btnGuardarCampania').disabled = true;
		swal({
			title: 'Procesando solicitud',
			text: 'Por favor espera',
			allowOutsideClick: false,
			buttons: false,
			closeOnClickOutside: false,
			icon: 'info',
		});

		fetch(getAbsolutePath() + dtosCampaniaTmp, {
			method: "POST",
			body: formData
		})
			.then(response => {
				if (!response.ok) {
					throw new Error("Error en la solicitud");
				}
				return response.json(); // o .json() si el backend devuelve JSON
			})
			.then(data => {
				console.log("Respuesta del servidor:", data);

				// Leer mensaje y tipo desde el JSON recibido
				const mensaje = data.mensaje;
				const tipo = data.tipo;
				document.getElementById("codCampaniaR").value = data.codigoCampania;
				
				
				tablaProceso(data.listaProceso);
				
				
				if (data.registros && data.registros.i_estatus != null) {
					document.getElementById("idCampaniaSeleccionada").value = data.registros.pk_id_tipo_campania;
    				mostrarOcultarProceso((data.registros.i_estatus ===2 || data.registros.i_tipo_campania ===2)?0:1);
    				
    				//mostrarOcultarProceso((data.registros.i_tipo_campania ===2)?0:1);
				}

				swal("", mensaje, tipo);
				
			})
			.catch(error => {
				console.log("Error:", error);
				alert("Ocurrió un error al guardar la campaña");
			})
			.finally(() => {
				swal.close(); // cerrar el "Cargando..."
				document.getElementById('btnGuardarCampania').disabled = false;
				
				
			});
	}


}

//mostrar listado de procesos
function mostrarOcultarProceso(estatusCampaniaTemporal){
		if(estatusCampaniaTemporal === 1){
			$("#dtosCampania").show();
		}else{
			$("#dtosCampania").hide();
		}
}

function validarFormulario(camposForm) {
	let valido = false;

	const campos = [
		"nombreCampania",
		"fechaRegistro",
		"estatusCampania",
	];

	campos.forEach(function(id) {
		const campo = document.getElementById(id);

		campo.classList.remove("is-invalid");

		if (!campo.value || campo.value.trim() === "" || campo.value === "0") {
			campo.classList.add("is-invalid");
			valido = true;
		} else {
			campo.classList.remove("is-invalid");
			valido = false;
		}
	});

	return valido;
}

//archivo
var procesarArchivo = "procesarArchivo";
function procesarCampaniaTmp(data) {

	swal({
		title: 'Procesando solicitud',
		text: 'Por favor espera',
		allowOutsideClick: false,
		buttons: false,
		closeOnClickOutside: false,
		icon: 'info',
	});

	fetch(getAbsolutePath() + procesarArchivo, {
		method: "POST",
		body: data, // FormData que ya incluye el archivo u otros campos
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la respuesta del servidor");
			}
			const contentType = response.headers.get("content-type");

			//txt
			if (contentType && contentType.includes("text/plain")) {
				return response.blob().then(blob => {

					const url = window.URL.createObjectURL(blob);
					const a = document.createElement("a");
					a.href = url;
					a.download = "incidencias.txt";
					document.body.appendChild(a);
					a.click();
					a.remove();

					swal("Error Carga Archivo", "Error al cargar archivo, se genera txt con la incidencias presentadas", "error");
					
					cerrarModalArchivo();
					
					return null; 
				});
			}

			//respuesta en json
			return response.json();
		})
		.then(data => {
			if (!data) return;

			console.log("Respuesta del servidor:", data);

			// Leer mensaje y tipo desde el JSON recibido
			const mensaje = data.mensaje;
			const tipo = data.tipo;
			
			tablaProceso(data.listaProcesos);
			
			swal("", mensaje, tipo);
			
			if (tipo === "success" || tipo === "OK") {
            	cerrarModalArchivo();
       		}
			
		})
		.catch(error => {
			console.log("Error:", error);
			alert("Ocurrió un error");
		})
		.finally(() => {
			swal.close(); // cerrar el "Cargando..."
			document.getElementById('btnGuardarFile').disabled = false;
			// ajuste por incidencia
			//window.location.href = getAbsolutePath() + "campaniasTemporales";
		});
}

const mapaCampanias = new Map();
let valorBusqueda = "";
var buscarCampaniasActivas = "buscarCampaniasTemporales?valorBusqueda=";
function buscarCampaniasTemporales() {
	valorBusqueda = document.getElementById("buscarCampaniaTemporales").value;
	if (valorBusqueda != 0) {
		if (mapaCampanias.has(valorBusqueda)) {
			obtDatosCampaTemporales();

		} else {
			limpiarFormulario();
			buscarCampaniasT(valorBusqueda);
		}
	} else {
		limpiarFormulario();
		iniciarFechas();
	}



}

function buscarCampaniasT(valorBusqueda) {


	fetch(getAbsolutePath() + buscarCampaniasActivas + encodeURIComponent(valorBusqueda), {
		method: "GET",
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("Error en la respuesta del servidor");
			}
			return response.json();
		})
		.then(data => {
			console.log("Respuesta recibida:", data);

			const datalist = document.getElementById("opcionCampania");
			datalist.innerHTML = ''; // limpiar anteriores
			mapaCampanias.clear();
			if (Array.isArray(data)) {
				data.forEach(campania => {
					const option = document.createElement("option");
					option.value = campania.vc_tipo_campania; // ajusta si se requiere id
					datalist.appendChild(option);
					mapaCampanias.set(campania.vc_tipo_campania, campania.pk_id_tipo_campania); // guarda el id
				});
			} else {
				swal("La respuesta no contiene una lista válida de campañas", {
					closeOnClickOutside: false,
					icon: "warning",
				});
			}
		})
		.catch(error => {
			console.error("Error en la búsqueda:", error);
			swal("Ocurrió un error al buscar campañas activas", {
				closeOnClickOutside: false,
				icon: "error",
			});
		})
		.finally(() => {
			// Por si estás usando bloqueo de UI

			setTimeout($.unblockUI, 600);
		});
}

function obtenerIdCampaniaSeleccionada() {
	const nombreSeleccionado = document.getElementById("buscarCampaniaTemporales").value;
	const idCampania = mapaCampanias.get(nombreSeleccionado);

	if (idCampania !== undefined) {
		console.log("ID de la campaña seleccionada:", idCampania);
		document.getElementById("idCampaniaSeleccionada").value = idCampania;
		return idCampania;
	} else {
		console.warn("No se encontró el ID para la campaña seleccionada");
		document.getElementById("idCampaniaSeleccionada").value = "";
		return null;
	}
}

var obtParamCampaniasTmp = "obtParamCampaniasTmp?valorCampaniaID=";
function obtDatosCampaTemporales() {

	fetch(getAbsolutePath() + obtParamCampaniasTmp + obtenerIdCampaniaSeleccionada(), {
		method: "GET",
	}).then(response => {
		if (!response.ok || response.status === 204) {
			swal("No se encontraron parámetros para esta campaña", { icon: "warning" });
			return null;
		} else {
			return response.json();
		}
	})
		.then(data => {
			if (!data) return;

			llenarFormulario(data.parametros);
			tablaProceso(data.listaProceso);
			
			document.getElementById("codCampaniaR").value = data.codCampania;
			if (data.idEstatus !== 1 && data.idEstatus !== 5 && data.idEstatus !== 6) {
    			document.getElementById("btnSubirAchivoMdl").disabled = true;
			}else{
				document.getElementById("btnSubirAchivoMdl").disabled = false;
			}

		})
		.catch(error => {
			console.log("Error en la búsqueda:", error);
			swal("Ocurrió un error al buscar campañas activas", {
				closeOnClickOutside: false,
				icon: "error",
			});
		})
		.finally(() => {
			setTimeout($.unblockUI, 600);
		});
}

function llenarFormulario(param) {
	document.getElementById("nombreCampania").value = param[0].nombreCampania;
	//document.getElementById("descripcionCampania").value = param.vc_tipo_campania;
	document.getElementById("vigenciaMeses").value = param[0].vigenciaMeses;
	//document.getElementById("fechaInicio").value = param.dt_fecha_registro;
	document.getElementById("fechaFin").value = param[0].fechaFin;
	document.getElementById("fechaRegistro").value = param[0].fechaRegistro;
	const select = document.getElementById("estatusCampania");
	const valueToSet = param[0].estatusCampania;

	Array.from(select.options).forEach(option => {
		if (option.value == valueToSet) {
			option.selected = true;
		}
	});
	//document.getElementById("fechaEnvioMC").value = param.dt_fecha_registro;
	document.getElementById("checkRecurrente").checked = param[0].recurrente;
	
	if ((param && param[0].estatusCampania != null) || (param && param[0].recurrente != null)) {
    	mostrarOcultarProceso((param[0].estatusCampania ===2 || param[0].recurrente ===1)?0:1);
	}
	/*
	if ((param && param[0].recurrente != null)) {
    	mostrarOcultarProceso((param[0].recurrente===1)?0:1);
	}*/
	
	
	
	

}

function limpiarFormulario() {
	// hidden
	document.getElementById("idCampaniaSeleccionada").value = "";
	document.getElementById("formDatosCampania").reset();
	
	/*const hoy = new Date().toISOString().split('T')[0];
    document.getElementById("fechaInicio").value = hoy;
    document.getElementById("fechaFin").value = hoy;
    document.getElementById("fechaRegistro").value = hoy;
    document.getElementById("fechaEnvioMC").value = hoy;*/
	
	$("#dtosCampania").hide();
}

function cerrarModalArchivo() {

    bootstrap.Modal.getOrCreateInstance(
        document.getElementById("modalArchivoCampania")
    ).hide();

    document.getElementById("formArchivoCampaniaTmp").reset();
    document.getElementById("file").value = "";
}

function generarUUIDCorto(longitud = 8) {
  return 'xxxxxxxxxxxxxxxx'.substring(0, longitud).replace(/x/g, () => {
    return (Math.random() * 16 | 0).toString(16);
  });
}

var calcularFechaFin = "calcularFechaFin?fechaInicio=";
function obtFechaFin(){
	
	var vigencia = "&vigencia="+document.getElementById('vigenciaMeses').value;
	

	fetch(getAbsolutePath() + calcularFechaFin + document.getElementById('fechaEnvioMC').value+ vigencia, {
		method: "GET",
	}).then(response => {
		if (!response.ok || response.status === 204) {
			swal("Ha ocurrido un error. Consulte al administrador del sistema.", { icon: "warning" });
			return null;
		} else {
			return response.json();
		}
	})
		.then(data => {
			if (!data) return;
			
			if(data.dfin.length === 0){
				const mensaje = data.mensaje;
				const tipo = data.tipo;
				swal("", mensaje, tipo);
			}else{
				document.getElementById('fechaFin').value = data.dfin.split('T')[0];
			}

		})
		.catch(error => {
			console.log("Error en calculo de la fecha fin:", error);
			swal("Ocurrió un error al calcular la fecha fin", {
				closeOnClickOutside: false,
				icon: "error",
			});
		})
		.finally(() => {
			setTimeout($.unblockUI, 600);
		});
	
}

function tablaProceso(data){
		tblInformacionProceso.clear().draw();
		 $.each(data, function(idx, opt) {
			tblInformacionProceso.row.add([opt.idProcesoCampania+"-"+opt.codigoCampania,opt.nombreusuario,opt.dtFechaRegistro,opt.estatus]).draw(false);
		 });
}