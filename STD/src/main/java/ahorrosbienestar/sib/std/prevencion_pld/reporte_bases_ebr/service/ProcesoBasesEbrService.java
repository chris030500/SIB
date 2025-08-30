package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository.IProcesoBasesEbrRepository;

@Service
public class ProcesoBasesEbrService {
	
	@Autowired
	private IProcesoBasesEbrRepository procesoEbrRepository;
	
	public ProcesoBasesEbr registrarProcesoBasesEbr(int periodo, int idUsuario) {
		ProcesoBasesEbr proceso = new ProcesoBasesEbr();
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		proceso.setI_periodo_ejecucion(periodo);
		proceso.setI_usuario_ejecuto(idUsuario);
		proceso.setDt_fecha_ejecucion(currentDateTime);
		proceso.setI_estatus_vobo(null);
		proceso.setI_usuario_vobo(null);
		proceso.setDt_fecha_vobo(null);
		
		return procesoEbrRepository.save(proceso);
	}

}
