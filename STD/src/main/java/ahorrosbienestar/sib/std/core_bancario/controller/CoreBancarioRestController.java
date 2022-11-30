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
<<<<<<< HEAD

	@RequestMapping(value={"/lista_productos"}, method = RequestMethod.GET)
	public ResponseEntity<?>  listaProductos(@RequestParam("solicitud") Integer solicitud){
		System.out.println("Lista: " + solicitud);
		
		return  ResponseEntity.ok(coreBancarioService.listaProductos(solicitud)); 
	}
	
=======
	
	@RequestMapping(value = {"/buscar_catProducto"}, method = RequestMethod.GET)
	public int spBuscarCatProducto(@RequestParam("producto") Integer producto){
		return coreBancarioService.spBuscarCatProducto(producto);
	}
>>>>>>> branch 'Pruebas' of http://damian.cossio@10.150.102.63:8080/gitblit/r/SIB.git
}
