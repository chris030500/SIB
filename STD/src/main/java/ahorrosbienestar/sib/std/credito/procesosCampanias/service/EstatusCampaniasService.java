package ahorrosbienestar.sib.std.credito.procesosCampanias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.EstatusCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.repository.EstatusCampaniasRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
@AllArgsConstructor


public class EstatusCampaniasService {
	
	@Autowired
	private EstatusCampaniasRepository repository;
	
	public List<EstatusCampaniasModel> getEstatusCampaniasModel() {
        return repository.findAll();
    }
	
	/*public List<EstatusCampaniasDTO> buscarEstatus(Integer tipoCampania) {
		List<Object[]> resultados = repository.listaEstatus(tipoCampania);
	    return resultados.stream()
	    		.map(r -> new EstatusCampaniasDTO((Integer) r[0], (String) r[1]))
	            .collect(Collectors.toList());
	}*/
	
	public List<Map<String, Object>> listaEstatus(Integer tipoCampania) {
        List<Object[]> datos = repository.listaEstatus(tipoCampania);

        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Object[] fila : datos) {
            Map<String, Object> item = new HashMap<>();
            item.put("pk_id_estatus_campania", fila[0]);
            item.put("vc_estatus", fila[1]);
            respuesta.add(item);
        }
        return respuesta;
    }
}
