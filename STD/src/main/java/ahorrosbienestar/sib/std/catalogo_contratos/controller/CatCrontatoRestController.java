package ahorrosbienestar.sib.std.catalogo_contratos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.catalogo_contratos.dto.CatCrontatoDTO;
import ahorrosbienestar.sib.std.catalogo_contratos.model.CatCrontato;
import ahorrosbienestar.sib.std.catalogo_contratos.service.CatCrontatoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CatCrontatoRestController {
	
	@Autowired
	private CatCrontatoService catalogoService;
	
	
	@RequestMapping(value = {"/lista_catalogos"}, method = RequestMethod.GET)
	public List<CatCrontatoDTO> listadoDeCatalogos() {
		
		List<CatCrontatoDTO> responseDTO = null;
		
		catalogoService.listaCatalogo().forEach(model -> {
			responseDTO.add(llenarCatCatalogoDTO(model));
		});
		
		return responseDTO;
	}
	
	public CatCrontatoDTO llenarCatCatalogoDTO(CatCrontato model) {
		CatCrontatoDTO dto = new CatCrontatoDTO();
		dto.setPk_id_contrato(model.getPk_id_contrato());
		dto.setVc_contrato(model.getVc_contrato());
		dto.setVc_descripcion(model.getVc_descripcion());
		dto.setDt_fecha_publicacion(model.getDt_fecha_publicacion());
		dto.setDt_fecha_modificaion(model.getDt_fecha_modificaion());
		dto.setVc_tipo_formato(model.getVc_tipo_formato());
		dto.setVc_ruta(model.getVc_ruta());
		dto.setVc_version(model.getVc_version());
		dto.setVc_nombre_archivo(model.getVc_nombre_archivo());
		dto.setI_id_estatus(model.getI_id_estatus());
		return dto;
	}

}
