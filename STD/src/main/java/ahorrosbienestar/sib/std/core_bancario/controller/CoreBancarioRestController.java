package ahorrosbienestar.sib.std.core_bancario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;


@RestController
public class CoreBancarioRestController {

	@Autowired
	private CoreBancarioService coreBancarioService;
	
	
	@RequestMapping(value={"/buscar_solicitud"}, method = RequestMethod.GET)
	public ResponseEntity<?>  buscarSolicitud(@RequestParam("solicitud") Integer solicitud){
		System.out.println("Buscar Solicitud: " + solicitud);
		
		return  ResponseEntity.ok(coreBancarioService.validaSolicitud(solicitud)); 
	}
	
	@RequestMapping(value={"/trae_participantes"}, method = RequestMethod.GET)
	public ResponseEntity<?>  traeParticipantes(@RequestParam("solicitud") Integer solicitud){
		System.out.println("Solicitud en participante: " + solicitud);
		
		return  ResponseEntity.ok(coreBancarioService.traeParticipantes(solicitud)); 
	}
	
	@RequestMapping(value = {"/buscar_catProducto"}, method = RequestMethod.GET)
	public int spBuscarCatProducto(@RequestParam("producto") Integer producto){
		return coreBancarioService.spBuscarCatProducto(producto);
	}
	
	@RequestMapping(value = {"/buscar_documento"}, method = RequestMethod.GET)
	public ResponseEntity<?> spBuscarDocumento(@RequestParam("producto") Integer producto){
		return ResponseEntity.ok(coreBancarioService.obtenerDoctAsociadoProd(producto));
	}
}
