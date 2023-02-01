package ahorrosbienestar.sib.std.productodocumento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.productodocumento.model.RelacionDocumentos;
import ahorrosbienestar.sib.std.productodocumento.repository.IProductoDocumentoRepository;

@Service
public class ProductoDocumentoService {

private IProductoDocumentoRepository iProductoDocumentoRepository;
	
	@Autowired
	public ProductoDocumentoService(IProductoDocumentoRepository iProductoDocumentoRepository) {
		this.iProductoDocumentoRepository = iProductoDocumentoRepository;
	}
	

	public int existeDocumento(int idproducto,int idDocumento) {
		return iProductoDocumentoRepository.existeDocumento(idproducto,idDocumento);
	}
	
	public List<RelacionDocumentos> buscarDocumentos(int idproducto) {
		return iProductoDocumentoRepository.buscarDocumentos(idproducto);
	}
}
