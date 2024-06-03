package ahorrosbienestar.sib.std.sccb.consultaExpedientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ImagenesExpedienteCredito;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.repository.IImagenesExpedienteCreditoRepository;

@Service
public class ImagenesExpedienteCreditoService {
	
	@Autowired
	private IImagenesExpedienteCreditoRepository iImagenesExpCreditoRepository;
	
	public List<ImagenesExpedienteCredito> searchDetalleCredito(Integer idExpediente) {
		return iImagenesExpCreditoRepository.getDetalleExpedienteCredito(idExpediente);
	}
}
