package ahorrosbienestar.sib.std.sccb.expedientes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.expedientes.repository.IImagenesExpedienteCreditoRepository;

@Service
public class ImagenesExpedienteCreditoService {
	
	@Autowired
	private IImagenesExpedienteCreditoRepository iImagenesExpCreditoRepository;
	
	public List<Map<String, Object>> searchDetalleCredito(Integer idExpediente) {
		return iImagenesExpCreditoRepository.getDetalleExpedienteCredito(idExpediente);
	}
}
