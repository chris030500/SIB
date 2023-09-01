package ahorrosbienestar.sib.std.prevencion_pld.bitacora.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.prevencion_pld.bitacora.dto.BitacoraPldDTO;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.service.BitacoraPldService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BitacoraPldRestController {

	@Autowired
	private BitacoraPldService bitacoraPldService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = {"/datos_bitacora"}, method = RequestMethod.GET)
	public List<BitacoraPldDTO> listaBitacora() {
		
		List<BitacoraPldDTO> responseDTO = new ArrayList<BitacoraPldDTO>();
		List<BitacoraPldModel> bitacoraModel = bitacoraPldService.todoBitacora();
		
		bitacoraModel.forEach(bitacora -> {
			BitacoraPldDTO dto = new BitacoraPldDTO();
			dto.setNombre_archivo(bitacora.getVc_nombre_archivo());
			dto.setFecha(bitacora.getDt_fecha());
			dto.setNumero_registro(bitacora.getI_numero_registro());
			dto.setRegistros_excluidos(bitacora.getI_registros_excluidos());
			dto.setRegistro_final(bitacora.getI_registro_final());
			dto.setDuracion(bitacora.getVc_duracion());
			dto.setEstatus(bitacora.getVc_estatus());
			dto.setComentarios(bitacora.getVc_comentarios());
			dto.setUsuario(bitacora.getFk_usuario_registro().getVc_nombre_usuario());
			responseDTO.add(dto);
		});
		
		return responseDTO;
	}
	
}
