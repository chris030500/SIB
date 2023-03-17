package ahorrosbienestar.sib.std.catalogo_contratos.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.autorizacion.descargas.DescargaRestController;
import ahorrosbienestar.sib.std.catalogo_contratos.dto.CatContratoDTO;
import ahorrosbienestar.sib.std.catalogo_contratos.model.CatContrato;
import ahorrosbienestar.sib.std.catalogo_contratos.service.CatContratoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CatContratoRestController {
	
	@Autowired
	private CatContratoService catContratoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DescargaRestController descargaRestController;
	
	/**
	 * Método para agregar un contrato
	 * @param catContratoDTO
	 * @return
	 */
	@RequestMapping(value = {"/agregar_contrato"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody HttpStatus agregarContrato(@RequestBody CatContratoDTO catContratoDTO) {
		
		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.findByNombre(auth.getName());
		
		// Crear Fecha
		Date diaActual = new Date();
		long time = diaActual.getTime();
		java.sql.Date fecha = new java.sql.Date(time);
		
		CatContrato catContrato = new CatContrato();
		
		catContrato.setVc_contrato(catContratoDTO.getVc_contrato());
		catContrato.setVc_descripcion(catContratoDTO.getVc_descripcion());
		catContrato.setI_estatus(catContratoDTO.getI_estatus());
		catContrato.setDt_fecha_publicacion(fecha);
		catContrato.setUsr_registra(usuario.getPk_id_usuario());
		catContrato.setDt_fecha_modificacion(fecha);
		
		catContratoService.guardarContrato(catContrato);
		
		return HttpStatus.OK;
		
	}
	
	
	/**
	 * Método para obtener la lista de contratos
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = {"/lista_contratos"}, method = RequestMethod.GET)
	public List<CatContrato> catalogoDeContratos(@RequestParam("todo") Boolean todo) throws IOException {
		
		if (todo) 
			return catContratoService.listaCatalogo();
		else 
			return catContratoService.listaContratosActivos();
	}
	
	
	/**
	 * Método para actualizar el estatus del contrato
	 * @param estatus
	 * @param idContrato
	 * @return
	 */
	@RequestMapping(value = {"/actualizar_estatus"}, method = RequestMethod.GET)
	public HttpStatus actualizarEstatus(@RequestParam("idContrato") Integer idContrato,
										@RequestParam("estatus") Integer estatus) {
		
		CatContrato catContrato = catContratoService.findById(idContrato);
		catContrato.setI_estatus(estatus == 1 ? 0 : 1);
		catContratoService.guardarContrato(catContrato);
		
		return HttpStatus.OK;
	}
	
	
	/**
	 * Método para editar el contrato
	 * @param catContratoDTO
	 * @return
	 */
	@RequestMapping(value = {"/editar_contrato"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody HttpStatus editarContrato(@RequestBody CatContratoDTO catContratoDTO) {
		
		// Crear Fecha
		Date diaActual = new Date();
		long time = diaActual.getTime();
		java.sql.Date fecha = new java.sql.Date(time);
		
		CatContrato catContrato = catContratoService.findById(catContratoDTO.getPk_id_contrato());
		
		catContrato.setVc_contrato(!catContratoDTO.getVc_contrato().equals(catContrato.getVc_contrato()) ? catContratoDTO.getVc_contrato() : catContrato.getVc_contrato());
		catContrato.setVc_descripcion(!catContratoDTO.getVc_descripcion().equals(catContrato.getVc_descripcion()) ? catContratoDTO.getVc_descripcion() : catContrato.getVc_descripcion());
		catContrato.setI_estatus(catContratoDTO.getI_estatus() != catContrato.getI_estatus() ? catContratoDTO.getI_estatus() : catContrato.getI_estatus());
		catContrato.setDt_fecha_modificacion(fecha);
		
		catContratoService.guardarContrato(catContrato);
		
		return HttpStatus.OK;
		
	}
	
	
	/**
	 * Método para traer un contrato en específico
	 * @param idContrato
	 * @return
	 */
	@RequestMapping(value = {"/mostrar_contrato"}, method = RequestMethod.GET)
	@ResponseBody CatContratoDTO mostrarUnContrato(@RequestParam("idContrato") Integer idContrato) {
		
		CatContratoDTO respuesta = new CatContratoDTO();
		
		CatContrato catContrato = catContratoService.findById(idContrato);
		
		respuesta.setVc_contrato(catContrato.getVc_contrato());
		respuesta.setVc_descripcion(catContrato.getVc_descripcion());
		respuesta.setI_estatus(catContrato.getI_estatus());
		respuesta.setDt_fecha_publicacion(catContrato.getDt_fecha_publicacion());
		respuesta.setUsr_registra(catContrato.getUsr_registra());
		respuesta.setDt_fecha_modificacion(catContrato.getDt_fecha_modificacion());
		
		return respuesta;
	}


}
