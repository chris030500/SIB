package ahorrosbienestar.sib.std.credito.procesosCampanias.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.ProcesosCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.repository.ProcesosCampaniasRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcesosCampaniasService {

	//private TiposCampaniasRepository tiposCampaniasRepository;
	@Autowired
    private ProcesosCampaniasRepository procesosCampaniasRepository;

    public List<ProcesosCampaniasModel> buscarCampanias(LocalDate inicio, LocalDate fin, String tipoCampania) {
        return procesosCampaniasRepository.buscarPorFiltros(inicio, fin, tipoCampania);
    }
    
    public ProcesosCampaniasModel save(ProcesosCampaniasModel model) {
		return procesosCampaniasRepository.save(model);
	}
	
    public int reporteBuroCredito(int tipoCampania) {
        return procesosCampaniasRepository.reporteBuroCredito(tipoCampania);
    }
    
    public int contarProcesosExtraccionFin(int tipoCampania) {
        return procesosCampaniasRepository.contarProcesosExtraccionFin(tipoCampania);
    }
	
}
