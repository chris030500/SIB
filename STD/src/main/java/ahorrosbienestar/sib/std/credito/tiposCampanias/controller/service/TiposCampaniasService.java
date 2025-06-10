package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository.TiposCampaniasRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class TiposCampaniasService {

	@Autowired
	private TiposCampaniasRepository repository;
	public List<TiposCampaniasDTO> listaCampanias(String valor) {
		List<Object[]> resultados = repository.listaCampanias(valor);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
		//return repository.listaCampanias(valor);
	}
	
	public TiposCampaniasModel obtenerCampaniaPorId(Integer id) {
	    return repository.mostrarCampania(id);
	}
	
	@Transactional
    public boolean actualizarCampania(TiposCampaniasModel campania) {
        int filasAfectadas = repository.actualizarCampania(
            campania.getPk_id_tipo_campania(),
            campania.getI_ventana_anios(),
            campania.getI_edad_maxima(),
            campania.getI_edad_minima(),
            campania.getD_monto_minimo(),
            campania.getD_monto_maximo(),
            campania.getI_mora_maxima(),
            campania.getD_porc_cubierto_capital(),
            campania.getI_dias_gracia(),
            campania.getI_dias_mora(),
            campania.getI_plazo_maximo_anios(),
            campania.getI_minimo_vida_credito(),
            campania.getI_vigencia_bc(),
            campania.getI_vigencia_campania(),
            campania.getI_creditos_liquidados(),
            campania.getVc_es_renovacion(),
            campania.getI_convivencia(),
            campania.getVc_productos(),
            campania.getI_prod_credito_id_oferta(),
            campania.getD_porc_inc_oferta(),
            campania.getVc_es_renovacion_oferta(),
            campania.getI_vigencia_oferta(),
            campania.getD_tasa_ordinaria_oferta(),
            campania.getI_activa_oferta(),
            campania.getD_monto_maximo_oferta()
        );
        return filasAfectadas > 0;
    }
	
}
