package ahorrosbienestar.sib.std.sccb.expedientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.expedientes.model.ImagenesExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.expedientes.repository.IImagenesExpedienteAfiliacionRepository;

@Service
public class ImagenesExpedienteAfiliacionService {
	
	@Autowired
	private IImagenesExpedienteAfiliacionRepository iImagenesExpAfiliacionRepository;
	
	public List<ImagenesExpedienteAfiliacion> searchDetalleExpdienteAfiliacion(Integer idExpediente) {
		return iImagenesExpAfiliacionRepository.getDetalleExpedienteAfiliacion(idExpediente);
	}
}
