package ahorrosbienestar.sib.std.docto_unificado.controller;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;
import ahorrosbienestar.sib.std.docto_unificado.service.DoctoUnificadoService;
import ahorrosbienestar.sib.std.docto_unificado.service.HashService;
import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;
import ahorrosbienestar.sib.std.firma_docto_unificado.service.FirmaDoctoUnificadoService;

@RestController
public class DoctoUnificadoController {

	@Autowired
	private DoctoUnificadoService doctoUnificadoService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private HashService hashService;
	
	@GetMapping("archivos/obtenerArchivos")
	public ResponseEntity<?> obtenerArchivos(@RequestParam("soliEnvio") Integer soliEnvio){
		
		try {
		//Obtenemos la ubicacion de los archivos.
		DoctoUnificado doctoUnificado = doctoUnificadoService.findByFkIdSolicituEnvio(soliEnvio).get();
		
		//Obtenemos la url donde se alojan los documentos
		String url_base = configService.findAll().get(13).getVc_valor_propiedad();
		
		String xml = doctoUnificado.getVc_ruta_archivo_xml();
		String pdf = doctoUnificado.getVc_ruta_archivo_pdf();
		
		String archivoPDF = url_base+pdf;
		
		File fiPDF = new File(archivoPDF);
		
		//Establecemos el formato del HASH
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		String hashPdf = hashService.getFileChecksum(md, fiPDF);
		
		
		List<String> documentos = new ArrayList<>(Arrays.asList(xml, pdf, hashPdf));
		return ResponseEntity.ok(documentos);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
		
		
	}
	
}
