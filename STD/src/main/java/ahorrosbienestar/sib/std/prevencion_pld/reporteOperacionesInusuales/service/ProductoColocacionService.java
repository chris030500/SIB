package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.model.ProductoColocacion;
import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.repository.IProductoColocacionRepository;

@Service
public class ProductoColocacionService {
	
	@Autowired
	private IProductoColocacionRepository iProductoColocacionRepository;
	
	public List<ProductoColocacion> getProductosColocacion() {
		return iProductoColocacionRepository.findAll();
	}

}
