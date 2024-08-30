package ahorrosbienestar.sib.std.contabilidad.configuracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.contabilidad.configuracion.model.ProductosCredito;
import ahorrosbienestar.sib.std.contabilidad.configuracion.repository.IProductosCredito;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductosCreditoService {
	
	@Autowired
	private IProductosCredito iProductosCredito;
	
	public List<ProductosCredito> obtProductos(){
		return iProductosCredito.obtProductos();
	}

	public String descProducto(String productoID) {
		return iProductosCredito.descProducto(productoID);
	}

}
