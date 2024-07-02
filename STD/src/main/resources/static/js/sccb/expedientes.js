var expOptionSelected = "";
var searchOptionSelected = "";
var url = "";

$(document).ready(function () {
	
	defaultView();
	
	$("#opcionesTipoExpediente").change(function () {
		
		expOptionSelected = $("#opcionesTipoExpediente").val();
		
		console.log(expOptionSelected);
		
		if (expOptionSelected > 0) {
			
			switch (expOptionSelected) {
				case "2":
					$("#divCredito").show();
					$("#divName").hide();
					$("#btnBuscar").show();
					$("#btnClean").show();
					break;
				case "1": 
					$("#divCredito").hide();
					$("#divName").show();
					$("#btnBuscar").show();
					$("#btnClean").show();
					break;
			}
		}
	});
	
					
	$("#visorImagenes").on("hidden.bs.modal", function(){
		$(".zoomContainer").remove();
	});
	
});

function defaultView() {
	$("#divMainTable").hide();
	$("#divTblCredito").hide();
	$("#divTblAfiliacion").hide();
	$("#divDetalleExpCredito").hide();
}

function getAbsolutePath() {
	var loc = window.location;
	var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
	return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}

function buscarNombreCredito(name) {
	
	var selectName = $("#selectCredito");
	var option = "";
	
	url = getAbsolutePath() + "getNamesExpedienteCredito?nombre=" + name;
	console.log(name);
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function(res) {
			console.log(res.nombresCredito);
			var nombresCredito = res.nombresCredito;
			console.log(nombresCredito.length);
			if (nombresCredito.length === 0) {
				swal({
					title: "Error, ",
					text: "El nombre que ingreso no es válido.",
					icon: "warning",
				});
				cleanView();
			}
			else {
				for(var i = 0; i < nombresCredito.length; i++) {
				
					option += "<option value='" + nombresCredito[i].credito_id + "'>" + nombresCredito[i].nombre_cliente+ " | " + nombresCredito[i].credito_id + "</option>";
				
					$("select#selectCredito").empty().append(option);
				}
			}
		}
	});
}

function buscarNombre(name) {
	
	var selectName = $("#selectName");
	var option = "";
	
	url = getAbsolutePath() + "getNamesExpedienteAfiliacion?nombre=" + name;
	console.log(name);
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function(res) {
			console.log(res.nombresAfiliacion);
			var nombresAfiliacion = res.nombresAfiliacion;
			console.log(nombresAfiliacion.length);
			if (nombresAfiliacion.length === 0) {
				swal({
					title: "Error, ",
					text: "El nombre que ingreso no es válido.",
					icon: "warning",
				});
				cleanView();
			}
			else {
				for(var i = 0; i < nombresAfiliacion.length; i++) {
				
					option += "<option value='" + nombresAfiliacion[i].nombre_cliente + "'>" + nombresAfiliacion[i].nombre_cliente + "</option>";
				
					$("select#selectName").empty().append(option);
				}
			}
		}
	});
}

function buscarExpedienteCredito() {
	var idCredito = $("#selectCredito").val();
	var divMainTable = $("#divMainTable");
	
	console.log(typeof(idCredito));
	
	url = getAbsolutePath() + "getExpedienteCreditoByIdCredito?idCredito=" + Number(idCredito);
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			console.log(res);
			if ($.isEmptyObject(res)) {
				console.log("Error");
				divMainTable.hide();	
			}
			else {
				divMainTable.show();
				$("#divTblCredito").css("display", "block");
				
				var idExpediente = (res.i_expediente_id != null) ? res.i_expediente_id : "";
				var tipoExpediente = (res.vc_tipo_expediente != null) ? res.vc_tipo_expediente : "";
				var fechaCreacion = (res.dt_fecha_creacion != null) ? res.dt_fecha_creacion : "";
				var idSolicitud = (res.i_solicitud_id != null) ? res.i_solicitud_id : "";
				var idCredito = (res.i_credito_id != null) ? res.i_credito_id : "";
				
				var tbody = "<tbody>"
				+ "<tr>"
				+ "<td>" + idExpediente + "</td>"
				+ "<td>" + tipoExpediente + "</td>"
				+ "<td>" + fechaCreacion + "</td>"
				+ "<td>" + idSolicitud + "</td>"
				+ "<td>" + idCredito + "</td>"
				+ "<td>ACTIVO</td>"
				+ "</tr>"
				+ "</tbody>";
				
				$("#credito").append(tbody);
			}
		}, 
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			divMainTable.hide();
			swal({
				title: "Error, ",
						text: "No se encuentra el no. de crédito.",
						icon: "warning",
			});
		}
	}); 
}

function getDetalleExpedienteCredito() {
	
	var idCredito = $("#selectCredito").val();
	var divDetalleExpediente = $("#divDetalleExpediente");
	var divTitle = "<p>Detalle de Expediente de Crédito</p>";
	
	$("#titleDetalleExpediente").append(divTitle);
	
	url = getAbsolutePath() + "getDetalleExpedienteCredito?idCredito=" + Number(idCredito);
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON", 
		success: function (res) {
			if (res.imagenesCredito.length === 0) {
				console.log("Error");
				divDetalleExpediente.hide();
			}
			else {
				console.log(res.imagenesCredito);
				divDetalleExpediente.show();
				$("#divDetalleExpCredito").css("display", "block");
				var tbody = "<tbody id='tbodyDetalleExpediente'></tbody>";
				$("#detalleCredito").append(tbody);
				
				$.each(res.imagenesCredito, function(idx, opt) {
					
					var tipoRequisito = (opt.tipo_requisito != null) ? opt.tipo_requisito : "";
					var tipoComprobante = (opt.tipo_comprobante != null) ? opt.tipo_comprobante : "";
					var estatusEntregado = (opt.estatus_entregado != null) ? opt.estatus_entregado : "";
					var fechaDigitalizacion = (opt.fecha_digitalizacion != null) ? opt.fecha_digitalizacion : "";
					var validoDesde = (opt.valido_desde != null) ? opt.valido_desde : "";
					var validoHasta = (opt.valido_hasta != null) ? opt.valido_hasta : "";
					var estatusDigitalizacion = (opt.estatus_digitalizacion != null) ? opt.estatus_digitalizacion : "";
					var nombreCliente = (opt.nombre_cliente != null) ? opt.nombre_cliente : "";
					var rol = (opt.rol_participante != null) ? opt.rol_participante : "";
					var imagen = opt.imagen.replace(/\{0\}/g,"");
					var imagenUrl = imagen.replace(/\\/g, "\\\\");

					
					var rows = "<tr>" 
					+ "<td><div style='width: 200px;'>"+ tipoRequisito + "</div></td>"
					+ "<td><div style='width: 200px;'>" + tipoComprobante + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusEntregado + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoDesde + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoHasta + "</div></td>"
					+ "<td><div style='width: 70px;'>"+ estatusDigitalizacion + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaDigitalizacion + "</div></td>"
					+ "<td><div style='width: 200px;'>" + nombreCliente + "</div></td>"
					+ "<td><div style='width: 80px;'>" + rol + "</div></td>"
					+ "<td>"
					+ "<div style='width: 70px;'>"
					+ "<button type='button' class='btn btn-secondary' onclick='showImage(\"" + imagenUrl + "\")'>"
					+ "<i class='bi bi-camera'></i>"
					+ "</button>"
					+ "</div>"
					+ "</td>"
					+ "</tr>";
					
					$("#tbodyDetalleExpediente").append(rows);
				});
			}
		},
		error:  function(XMLHttpRequest, textStatus, errorThrown) {
			divDetalleExpediente.hide();
			swal({
				title: "Error, ",
						text: "No se encuentra el no. de crédito.",
						icon: "warning",
			});
		}
	});
}

function buscarExpedienteAfiliacion() {
	var name = $("#selectName").val();
	var divMainTable = $("#divMainTable");
	
	console.log(name);
	
	url = getAbsolutePath() + "getExpedienteAfiliacionByName?nombre=" + name;
	
		$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			if (res == null || res == '') {
				console.log("Error");
				divMainTable.hide();
			}
			else {
				console.log(res);
				divMainTable.show();
				$("#divTblAfiliacion").css("display", "block");
				
				var idExpediente = (res.expediente_id != null) ? res.expediente_id : "";
				var tipoExpediente = (res.tipo_expediente != null) ? res.tipo_expediente : "";
				var fechaCreacion = (res.fecha_creacion != null) ? res.fecha_creacion : "";
				
				var tbody = "<tbody>"
				+ "<tr>"
				+ "<td>" + idExpediente + "</td>"
				+ "<td>" + tipoExpediente + "</td>"
				+ "<td>" + fechaCreacion + "</td>"
				+ "<td>ACTIVO</td>"
				+ "</tr>"
				+ "</tbody>";
				
				$("#afiliacion").append(tbody);
			}
		}
	}); 
	
}

function getDetalleExpedienteAfiliacion() {
	
	var name = $("#selectName").val();
	var divDetalleExpediente = $("#divDetalleExpediente");
	var divTitle = "<p>Detalle de Expediente de Afiliación</p>";
	
	$("#titleDetalleExpediente").append(divTitle);
	
	url = getAbsolutePath() + "getDetalleExpedienteAfiliacion?nombre=" + name;
	
	$.ajax({
		url: url,
		type: "GET",
		dataType: "JSON",
		success: function(res) {
			if (res.imagenesAfiliacion.length === 0) {
				console.log("Error");
				divDetalleExpediente.hide();
			}
			else {
				console.log(res);
				divDetalleExpediente.show();
				$("#divDetalleExpAfiliacion").css("display", "block")
				var tbody = "<tbody id='tbodyAfiliacion'></tbody>";
				$("#detalleAfiliacion").append(tbody);
				
				$.each(res.imagenesAfiliacion, function(idx, opt) {
					var tipoRequisito = (opt.vc_tipo_requisito != null) ? opt.vc_tipo_requisito : "";
					var tipoComprobante = (opt.vc_tipo_comprobante != null) ? opt.vc_tipo_comprobante : "";
					var estatusEntregado = (opt.vc_estatus_entregado != null) ? opt.vc_estatus_entregado : "";
					var fechaDigitalizacion = (opt.dt_fecha_digitalizacion != null) ? opt.dt_fecha_digitalizacion : "";
					var validoDesde = (opt.dt_valido_desde != null) ? opt.dt_valido_desde : "";
					var validoHasta = (opt.dt_valido_hasta != null) ? opt.dt_valido_hasta : "";
					var estatusDigitalizacion = (opt.vc_estatus_digitalizacion != null) ? opt.vc_estatus_digitalizacion : "";
					var imagen = opt.vc_imagen.replace(/\{0\}/g,"");
					var imagenUrl = imagen.replace(/\\/g, "\\\\");
					var rows = "<tr>"
					+ "<td><div style='width: 200px;'>" + tipoRequisito + "</div></td>"
					+ "<td><div style='width: 200px;'>" + tipoComprobante + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusEntregado + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoDesde + "</div></td>"
					+ "<td><div style='width: 100px;'>" + validoHasta + "</div></td>"
					+ "<td><div style='width: 70px;'>" + estatusDigitalizacion + "</div></td>"
					+ "<td><div style='width: 100px;'>" + fechaDigitalizacion + "</div></td>"
					+ "<td>"
					+ "<div style='width: 70px;'>"
					+ "<button type='button' class='btn btn-secondary' onclick='showImage(\"" + imagenUrl + "\")'>"
					+ "<i class='bi bi-camera'></i>"
					+ "</button>"
					+ "</div>"
					+ "</td>"
					+ "</tr>";
					
					$("#tbodyAfiliacion").append(rows);
				});
			}
			
		}
	});
}


function showImage(imagen) {
	
	url = getAbsolutePath() + "getImage?pathImage=" + encodeURIComponent(imagen);
	
	console.log(url);
	
	$.ajax({
		url: url,
		type: "GET",
		xhrFields: {
			responseType: "blob"
		},
		success: function (res) {
			console.log(res);
			$("#visorImagenes").modal("show");
			var urlImage = URL.createObjectURL(res);
			$("#imagen").attr("class", "img-fluid");
			$("#imagen").attr("src", urlImage);
			
			$("#visorImagenes").on("shown.bs.modal", function () {
				
				$("#imagen").elevateZoom({
					scrollZoom: true,
    				zoomType: "lens",
    				containLensZoom: true,
    				lensSize: 300,
    				lensFadeIn: 300,
    				lensFadeOut: 300,
    				borderSize: 2 ,
					onZoomedImageLoaded: function() {
            			// Establecer un z-index alto para el contenedor de zoom
            			$(".zoomContainer").css("z-index", "2000");
        			},
				});
			});
		},
		error: function(xhr, status, error) {
			$("#visorImagenes").modal("hide");
            console.error('Error al cargar la imagen:', error);
            swal({
				title: "Error, ",
				text: "No se encuentra disponible la imagen.",
				icon: "warning",
			});
        }
	});
    
}

function cleanView() {
	cleanTables();
	$("#creditoBusqueda").val("");
	$("#selectCredito > option").remove();
	$("#nameBusqueda").val("");
	$("#selectName > option").remove();
}

function cleanTables() {
	$("#credito > tbody").remove();
	$("#tbodyDetalleExpediente").remove();
	$("#afiliacion > tbody").remove();
	$("#tbodyAfiliacion").remove();
	$("#titleDetalleExpediente > p").remove();
	
	$("#divTblCredito").css("display", "none");
	$("#divTblAfiliacion").css("display", "none");
	$("#divDetalleExpCredito").css("display", "none");
	$("#divDetalleExpAfiliacion").css("display", "none");
	$("#divMainTable").hide();
	$("#divDetalleExpediente").hide();
}

function buscarDatos() {
	
	cleanTables();
	
	if (expOptionSelected == "1") {
		if ($("#nameBusqueda").val() != "") {
			buscarExpedienteAfiliacion();
			getDetalleExpedienteAfiliacion();
		}
		
		if ($("#nameBusqueda").val() === "") {
			
			swal({
				title: "Error, ",
				text: "Debe ingresar un nombre.",
				icon: "warning",
			});
		}
	}
	
	if (expOptionSelected == "2") {
		if ($("#creditoBusqueda").val() != "") {
			buscarExpedienteCredito();
			getDetalleExpedienteCredito();
		}
		
		if ($("#creditoBusqueda").val() === "") {
			
			swal({
				title: "Error, ",
				text: "Debe ingresar un nombre.",
				icon: "warning",
			});
		}
	}
}