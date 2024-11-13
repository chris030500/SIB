package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.model.ProductoCaptacion;
import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.repository.IProductoCaptacionRepository;

@Service
public class ProductoCaptacionService {
	
	@Autowired
	private IProductoCaptacionRepository iProductoCapatacionRepository;
	
	public List<ProductoCaptacion> getProductosCaptacion() {
		return iProductoCapatacionRepository.findAll();
	}

}
