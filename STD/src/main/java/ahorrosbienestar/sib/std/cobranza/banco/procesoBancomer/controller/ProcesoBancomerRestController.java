package ahorrosbienestar.sib.std.cobranza.banco.procesoBancomer.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.inject.ParamConverters.TypeValueOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import ahorrosbienestar.sib.std.autorizacion.controller.AutorizacionRestController;
import ahorrosbienestar.sib.std.cobranza.banco.procesoBancomer.dto.ProcesoBancomerDTO;
import ahorrosbienestar.sib.std.cobranza.domiciliado.model.CuentasModel;
import ahorrosbienestar.sib.std.cobranza.domiciliado.service.CuentasService;
import ahorrosbienestar.sib.std.cobranza.formatoBanorte.model.FormatoBanorteModel;
import ahorrosbienestar.sib.std.cobranza.formatoBanorte.service.FormatoBanorteService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProcesoBancomerRestController {

	@Autowired
	private FormatoBanorteService formatoBanorteService;

	@Autowired
	private CuentasService cuentasService;

	// Logger
	private static final Logger logger = Logger.getLogger(AutorizacionRestController.class);

	private static int sumaRegistros = 0;

	@RequestMapping(value = {"/datos_bancomer" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> obtenerDatosBancomer(@RequestBody List<ProcesoBancomerDTO> listaDatosBancomer) {
		Map<String, Object> response = new HashMap<>();
		List<String> lineas = new ArrayList<String>();
		List<FormatoBanorteModel> formatoBanorte = formatoBanorteService.listaDeDatos();
		List<String> cuentasNoEncontradas = new ArrayList<>();
		String mensajeError = null;

		try {
			listaDatosBancomer.forEach(bancomer -> {
				// Numero de cuenta
				String numeroCuenta = bancomer.getNumeroCuenta();
				String nuevoNC = numeroCuenta.substring(2, numeroCuenta.length());
				// Importe IVA
				String importeIVA = bancomer.getImporteIVA();
				String nuevoIVA = importeIVA.substring(7, importeIVA.length());
				// Cuenta CLABE
				CuentasModel cuentaClabeModel = cuentasService.datosCuentaClabe(nuevoNC);

				if (cuentaClabeModel == null) {
					cuentasNoEncontradas.add(bancomer.getNumeroCuenta());
				}

				logger.info("Se encontró la cuenta CLABE");
				// Referencia Servicio Emisor
				String referenciaSE = obtenerReferenciaServicioEmisor(
						cuentaClabeModel.getCliente_id() + cuentaClabeModel.getRfc_oficial());

				// Referencia Leyenda Emisor
				String referenciaLE = obtenerReferenciaLeyendaEmisor(
						"0" + cuentaClabeModel.getCredito_id() + formatoBanorte.get(3).getVc_valor());

				lineas.add(formatoBanorte.get(0).getVc_valor() + bancomer.getFechaAplicacion()
						+ formatoBanorte.get(1).getVc_valor() + referenciaSE + referenciaLE + bancomer.getImporte()
						+ cuentaClabeModel.getRfc_oficial() + bancomer.getNumeroBanco()
						+ formatoBanorte.get(2).getVc_valor() + nuevoNC + formatoBanorte.get(4).getVc_valor()
						+ formatoBanorte.get(5).getVc_valor() + nuevoIVA + formatoBanorte.get(6).getVc_valor()
						+ bancomer.getMotivoDevolucion() + bancomer.getReferenciaNumerica()
						+ formatoBanorte.get(7).getVc_valor());
			});

			// Obteniendo el encabezado;
			lineas.forEach(cuenta -> {
				// Verifica que la longitud de la cadena sea suficiente
				if (cuenta.length() >= 116) {
					// Obtiene los caracteres desde la posición 100 a la 115 (índices 99 a 114)
					String subString = cuenta.substring(99, 114);
					sumaRegistros = sumaRegistros + Integer.parseInt(subString);
				}
			});

			// Se recupera el encabezado
			String encabezado = obtenerEncabezado(formatoBanorte, String.valueOf(lineas.size()), sumaRegistros);
			lineas.add(0, encabezado);

			response.put("datos", lineas);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
            // Log cuentas no encontradas en caso de excepción
            for (String cuenta : cuentasNoEncontradas) {
                logger.info("No se encontró la cuenta CLABE: " + cuenta);
                mensajeError = "No se encontró la cuenta CLABE: " + cuenta;
            }
            response.put("error", mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public String obtenerEncabezado(List<FormatoBanorteModel> formatoBanorte, String numRegistros,
			int importeRegistros) {
		// Importe registros
		StringBuilder importeR = encabezadoImporteRegistros(String.valueOf(importeRegistros));
		// Total registros
		StringBuilder totalR = encabezadoTotalRegistros(numRegistros);
		// Fecha
		LocalDate fechaProceso = LocalDate.now();

		// Encabezado
		String crearEncabezado = formatoBanorte.get(8).getVc_valor() + formatoBanorte.get(9).getVc_valor()
				+ formatoBanorte.get(10).getVc_valor() + fechaProceso.toString().replace("-", "")
				+ formatoBanorte.get(11).getVc_valor() + totalR + importeR + totalR + importeR
				+ formatoBanorte.get(12).getVc_valor();

		int espaciosFaltantes = 117;
		// Crear una cadena de espacios en blanco
		String espacio = " ".repeat(espaciosFaltantes);
		// Concatenar los espacios en blanco a la cadena original
		String encabezado = crearEncabezado + espacio;
		logger.info(encabezado);
		return encabezado;
	}

	public StringBuilder encabezadoImporteRegistros(String importe) {
		int desiredLength = 15; // Longitud deseada

		// Calcula la cantidad de ceros que necesitas agregar
		int zerosToAdd = Math.max(0, desiredLength - importe.length());

		// Agrega los ceros necesarios a la izquierda
		StringBuilder totalRegistros = new StringBuilder();
		for (int i = 0; i < zerosToAdd; i++) {
			totalRegistros.append('0');
		}
		return totalRegistros.append(importe);
	}

	public StringBuilder encabezadoTotalRegistros(String size) {
		int desiredLength = 6; // Longitud deseada

		// Calcula la cantidad de ceros que necesitas agregar
		int zerosToAdd = Math.max(0, desiredLength - size.length());

		// Agrega los ceros necesarios a la izquierda
		StringBuilder totalRegistros = new StringBuilder();
		for (int i = 0; i < zerosToAdd; i++) {
			totalRegistros.append('0');
		}
		return totalRegistros.append(size);
	}

	public String obtenerReferenciaLeyendaEmisor(String referenciaLE) {

		// Calcular cuántos espacios faltan para llegar a 40
		int espaciosFaltantes = 40 - referenciaLE.length();

		// Crear una cadena con los espacios faltantes
		String espacios = " ".repeat(espaciosFaltantes);

		// Concatenar los espacios a la variable original
		String variableConEspacios = referenciaLE + espacios;

		return variableConEspacios;
	}

	public String obtenerReferenciaServicioEmisor(String referenciaSE) {

		// Calcular cuántos ceros agregar
		int cerosFaltantes = 19 - referenciaSE.length();

		// Rellenar con ceros hasta 6 caracteres
		StringBuilder nuevoReferenciaSE = new StringBuilder(referenciaSE);
		for (int i = 0; i < cerosFaltantes; i++) {
			nuevoReferenciaSE.insert(0, '0');
		}

		// Calcular cuántos espacios faltan para llegar a 40
		int espaciosFaltantes = 40 - nuevoReferenciaSE.length();

		// Crear una cadena con los espacios faltantes
		String espacios = " ".repeat(espaciosFaltantes);

		// Concatenar los espacios a la variable original
		String variableConEspacios = nuevoReferenciaSE + espacios;

		return variableConEspacios;
	}

}
