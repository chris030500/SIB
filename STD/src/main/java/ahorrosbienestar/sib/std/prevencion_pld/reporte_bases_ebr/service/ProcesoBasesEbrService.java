package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.IProcesoBasesEbrRepository;

@Service
public class ProcesoBasesEbrService {
	
	@Autowired
	private IProcesoBasesEbrRepository procesoEbrRepository;
	
	public ProcesoBasesEbr registrarProcesoBasesEbr(LocalDate fechaInicio, LocalDate fechaFin, int idUsuario) {
		ProcesoBasesEbr proceso = new ProcesoBasesEbr();
		
		ZoneId zoneChihuahua = ZoneId.of("America/Chihuahua");
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneChihuahua);
		ZonedDateTime newTime = zonedDateTime.plusMinutes(5);
		
		LocalDateTime currentDateTime = zonedDateTime.toLocalDateTime();;
		
		int minute = newTime.getMinute();
		int hour = newTime.getHour();
		
		String cron = String.format("0 %d %d * * *", minute, hour);
		
		proceso.setDt_fecha_inicio(fechaInicio);
		proceso.setDt_fecha_fin(fechaFin);
		proceso.setI_usuario_ejecuto(idUsuario);
		proceso.setDt_fecha_ejecucion(currentDateTime);
		proceso.setI_estatus_ejecucion(true);
		proceso.setVc_cron(cron);
		proceso.setI_estatus_vobo(null);
		proceso.setI_usuario_vobo(null);
		proceso.setDt_fecha_vobo(null);
		
		return procesoEbrRepository.save(proceso);
	}
	
	public ProcesoBasesEbr obtenerProcesoBasesEbr(int idProceso) {
		ProcesoBasesEbr proceso = procesoEbrRepository.findById(idProceso).orElse(null);
		
		return proceso;
	}
	
	public ProcesoBasesEbr obtenerLasProcesoBasesEbr() {
		Integer idProceso = procesoEbrRepository.findMaxProceso();
		
		ProcesoBasesEbr proceso = procesoEbrRepository.findById(idProceso).orElse(null);
		
		return proceso;
	}
	
	public List<Map<String, Object>> listaHistoricoBases() {
		return procesoEbrRepository.listaHistoricoBases();
	}
}
