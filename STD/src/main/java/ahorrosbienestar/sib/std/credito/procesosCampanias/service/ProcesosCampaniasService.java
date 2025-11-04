package ahorrosbienestar.sib.std.credito.procesosCampanias.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.procesosCampanias.dto.ProcesosCampaniasDTO;
import ahorrosbienestar.sib.std.credito.procesosCampanias.model.ProcesosCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.repository.ProcesosCampaniasRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.dto.TiposCampaniasDTO;
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
    
    public int contarProcesosExtraccionFin(int tipoCampania, int estatus) {
        return procesosCampaniasRepository.contarProcesosExtraccionFin(tipoCampania, estatus);
    }
    
    @Transactional
    public boolean actualizarProcesoCampania(ProcesosCampaniasModel procesos) {
        int filasAfectadas = procesosCampaniasRepository.actualizarProcesoCampania(
        		procesos.getPk_id_proceso_campania(),
        		procesos.getI_estatus_reg()
        );
        return filasAfectadas > 0;
    }
    
    public String consultaCodigoCam(int procesoCampania) {
        return procesosCampaniasRepository.consultaCodigoCam(procesoCampania);
    }
    
    public void ejecutarDepuracionBC(int tipoCampania, int procesoCampania) {
    	procesosCampaniasRepository.ejecutarDepuracionBC(tipoCampania, procesoCampania);
    }
    
    public void ejecutarArchivoMc(int tipoCampania) {
    	procesosCampaniasRepository.ejecutarArchivoMc(tipoCampania);
    }
    
    /*public List<ProcesosCampaniasDTO> buscarProcesosCampania(LocalDate inicio, LocalDate fin, String tipo) {
		List<Object[]> resultados = procesosCampaniasRepository.listaProcesosCampania(inicio, fin, tipo);
	    return resultados.stream()
	    		.map(r -> new ProcesosCampaniasDTO((Integer) r[0], (LocalDate) r[1], (String) r[2], (String) r[3], (LocalDate) r[4],(LocalDate) r[5], (String) r[6]))
	            .collect(Collectors.toList());
	}*/
    
    public List<ProcesosCampaniasDTO> buscarProcesosCampania(LocalDate inicio, LocalDate fin, String tipo) {
        List<Integer> listaTipos = Arrays.stream(tipo.split(","))
                                         .map(Integer::parseInt)
                                         .collect(Collectors.toList());

        List<Object[]> resultados = procesosCampaniasRepository.listaProcesosCampania(inicio, fin, listaTipos);

        return resultados.stream()
            .map(r -> new ProcesosCampaniasDTO(
                (Integer) r[0],
                toLocalDateTime(r[1]),   //  conversión segura a LocalDateTime
                (String) r[2],
                (String) r[3],
                toLocalDateTime(r[4]),   // 
                toLocalDateTime(r[5]),   // 
                (String) r[6]
            ))
            .collect(Collectors.toList());
    }

    // Conversor auxiliar
    private LocalDateTime toLocalDateTime(Object fecha) {
        if (fecha == null) return null;
        if (fecha instanceof LocalDateTime) return (LocalDateTime) fecha;
        if (fecha instanceof java.sql.Timestamp) return ((java.sql.Timestamp) fecha).toLocalDateTime();
        if (fecha instanceof java.sql.Date) return ((java.sql.Date) fecha).toLocalDate().atStartOfDay();
        return null;
    }
	
}
