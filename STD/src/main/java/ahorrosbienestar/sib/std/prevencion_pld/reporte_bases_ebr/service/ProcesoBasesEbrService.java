package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.IProcesoBasesEbrRepository;

@Service
public class ProcesoBasesEbrService {
	
	@Autowired
	private IProcesoBasesEbrRepository procesoEbrRepository;
	
	public ProcesoBasesEbr registrarProcesoBasesEbr(int idPeriodo, LocalDate fechaInicio, LocalDate fechaFin, int idUsuario) {
		ProcesoBasesEbr proceso = new ProcesoBasesEbr();
		
		ZoneId zoneChihuahua = ZoneId.of("America/Chihuahua");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneChihuahua);
		
		LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();
		
		proceso.setPeriodoId(idPeriodo);
		proceso.setFechaInicio(fechaInicio);
		proceso.setFechaFin(fechaFin);
		proceso.setUsuarioEjecuto(idUsuario);
		proceso.setFechaEjecucion(currentDateTime);
		proceso.setEstatusEjecucion(true);
		proceso.setEstatusVobo(null);
		proceso.setUsuarioVobo(null);
		proceso.setFechaVobo(null);
		proceso.setEstatusProceso(1);
		proceso.setFechaTermino(null);
		
		return procesoEbrRepository.save(proceso);
	}
	
	public ProcesoBasesEbr obtenerProcesoBasesEbr(int idProceso) {
		
		return procesoEbrRepository.findById(idProceso).orElse(null);
	}
	
	public ProcesoBasesEbr obtenerLasProcesoBasesEbr() {
		Integer idProceso = procesoEbrRepository.findMaxProceso(); 
		
		return procesoEbrRepository.findById(idProceso).orElse(null);
	}
	
	public List<Map<String, Object>> listaHistoricoBases() {
		return procesoEbrRepository.listaHistoricoBases();
	}
	
	@Transactional
	public void actualizarVobo(int idProceso, Boolean estatusVobo) {
	    ProcesoBasesEbr proceso = procesoEbrRepository.findById(idProceso)
	        .orElseThrow(() -> new RuntimeException("Proceso no encontrado"));
	    proceso.setEstatusVobo(false);
	}

}
