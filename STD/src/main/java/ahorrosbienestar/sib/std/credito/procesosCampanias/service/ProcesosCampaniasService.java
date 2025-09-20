package ahorrosbienestar.sib.std.credito.procesosCampanias.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.ProcesosCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.repository.ProcesosCampaniasRepository;
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
    
    public int procesoCamEstatus(int tipoCampania, int estatusCampania, int banderaCampania) {
        return procesosCampaniasRepository.procesoCamEstatus(tipoCampania, estatusCampania, banderaCampania);
    }
    
    public int contarProcesosExtraccionFin(int tipoCampania) {
        return procesosCampaniasRepository.contarProcesosExtraccionFin(tipoCampania);
    }
    
    @Transactional
    public boolean actualizarProcesoCampania(ProcesosCampaniasModel procesos) {
        int filasAfectadas = procesosCampaniasRepository.actualizarProcesoCampania(
        		procesos.getPk_id_proceso_campania(),
        		procesos.getI_estatus_reg()
        );
        return filasAfectadas > 0;
    }
	
}
