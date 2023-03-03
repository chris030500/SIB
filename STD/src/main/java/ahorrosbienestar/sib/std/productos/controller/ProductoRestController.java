package ahorrosbienestar.sib.std.productos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.core_bancario.dto.ProductosDto;
import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.service.ProductoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductoRestController {
	
	@Autowired
	private ProductoService productoService;
	
	@RequestMapping(value = {"/unico_producto"}, method = RequestMethod.GET)
	public Boolean productoPorNum(@RequestParam("numero") Integer numero) {
		
		Producto model = productoService.productoPorNumero(numero);
		
		if (model.getFk_id_contrato() != null)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

}
