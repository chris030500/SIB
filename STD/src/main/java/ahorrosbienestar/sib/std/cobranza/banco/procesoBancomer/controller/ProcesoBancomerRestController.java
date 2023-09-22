package ahorrosbienestar.sib.std.cobranza.banco.procesoBancomer.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	private static String cuentaQueNoExiste = "";

	@RequestMapping(value = {
			"/datos_bancomer" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<String> obtenerDatosBancomer(@RequestBody List<ProcesoBancomerDTO> listaDatosBancomer) {
		List<String> lineas = new ArrayList<String>();
		List<FormatoBanorteModel> formatoBanorte = formatoBanorteService.listaDeDatos();
		try {
			listaDatosBancomer.forEach(bancomer -> {
				// Numero de cuenta
				String numeroCuenta = bancomer.getNumeroCuenta();
				String nuevoNC = numeroCuenta.substring(2, numeroCuenta.length());
				cuentaQueNoExiste = numeroCuenta; 
				// Importe IVA
				String importeIVA = bancomer.getImporteIVA();
				String nuevoIVA = importeIVA.substring(7, importeIVA.length());
				// Cuenta CLABE
				CuentasModel cuentaClabeModel = cuentasService.datosCuentaClabe(nuevoNC);
				logger.info("Se encontró la cuenta CLABE");
				// Referencia Servicio Emisor
				String referenciaSE = obtenerReferenciaServicioEmisor(cuentaClabeModel.getCliente_id() + cuentaClabeModel.getRfc_oficial());
				
				// Referencia Leyenda Emisor
				String referenciaLE = obtenerReferenciaLeyendaEmisor("0" + cuentaClabeModel.getCredito_id() + formatoBanorte.get(3).getVc_valor());

				lineas.add(formatoBanorte.get(0).getVc_valor() 
						+ bancomer.getFechaAplicacion()
						+ formatoBanorte.get(1).getVc_valor() 
						+ referenciaSE 
						+ referenciaLE 
						+ bancomer.getImporte()
						+ cuentaClabeModel.getRfc_oficial() 
						+ bancomer.getNumeroBanco()
						+ formatoBanorte.get(2).getVc_valor() 
						+ nuevoNC 
						+ formatoBanorte.get(4).getVc_valor()
						+ formatoBanorte.get(5).getVc_valor() 
						+ nuevoIVA 
						+ formatoBanorte.get(6).getVc_valor()
						+ bancomer.getMotivoDevolucion() 
						+ bancomer.getReferenciaNumerica()
						+ formatoBanorte.get(7).getVc_valor()
						);
			});
			return lineas;
		} catch (Exception e) {
			// TODO: handle exception
			lineas.add("No se encontró la cuenta CLABE: " + cuentaQueNoExiste);
			return lineas;
		}
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
