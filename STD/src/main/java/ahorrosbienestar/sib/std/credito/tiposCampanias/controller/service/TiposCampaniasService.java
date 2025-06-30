package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service;

import java.util.List;
import java.util.Optional;
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
            campania.getI_tipo_campania(),
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
	
	@Transactional
	public boolean crearCampania(TiposCampaniasModel c) {
	    int filas = repository.insertarCampania(
	    	c.getVc_tipo_campania(),
	    	c.getI_tipo_campania(),
	    	c.getI_ventana_anios(),
	        c.getI_edad_maxima(),
	        c.getI_edad_minima(),
	        c.getD_monto_minimo(),
	        c.getD_monto_maximo(),
	        c.getI_mora_maxima(),
	        c.getD_porc_cubierto_capital(),
	        c.getI_dias_gracia(),
	        c.getI_dias_mora(),
	        c.getI_plazo_maximo_anios(),
	        c.getI_minimo_vida_credito(),
	        c.getI_vigencia_bc(),
	        c.getI_vigencia_campania(),
	        c.getI_creditos_liquidados(),
	        c.getVc_es_renovacion(),
	        c.getI_convivencia(),
	        c.getVc_productos(),
	        c.getI_prod_credito_id_oferta(),
	        c.getD_porc_inc_oferta(),
	        c.getVc_es_renovacion_oferta(),
	        c.getI_vigencia_oferta(),
	        c.getD_tasa_ordinaria_oferta(),
	        c.getI_activa_oferta(),
	        c.getD_monto_maximo_oferta()
	    );
	    return filas > 0;
	}
	
	public TiposCampaniasModel save(TiposCampaniasModel model) {
		return repository.save(model);
	}

	public List<TiposCampaniasDTO> buscarCampaniasActivas(String valorBusqueda) {
		List<Object[]> resultados = repository.listaCampaniasActivas(valorBusqueda);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
	}
	
	public Optional<TiposCampaniasModel> obtenerCampaniaxID(int idCampania) {
		return repository.findById(idCampania);
	}

	public List<TiposCampaniasDTO> buscarCampaniasTemporales(String valorBusqueda) {
		List<Object[]> resultados = repository.buscarCampaniasTemporales(valorBusqueda);
	    return resultados.stream()
	    		.map(r -> new TiposCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
	}
	
}
