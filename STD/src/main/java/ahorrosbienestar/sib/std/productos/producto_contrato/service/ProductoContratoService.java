package ahorrosbienestar.sib.std.productos.producto_contrato.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.productos.producto_contrato.model.ProductoContrato;
import ahorrosbienestar.sib.std.productos.producto_contrato.repository.ProductoContratoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductoContratoService {
	
	@Autowired
	private ProductoContratoRepository productoContratoRepository;
	
	public List<ProductoContrato> listaDeProductosConContrato() {
		return productoContratoRepository.listaDeProductosConContrato();
	}

}
