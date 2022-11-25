package ahorrosbienestar.sib.std.productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.documentos.repository.IDocumentoRepository;
import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.repository.IProductoRepository;


@Service
public class ProductoService {
	
	private IProductoRepository iProductoRepository;
	
	@Autowired
	public ProductoService(IProductoRepository iProductoRepository) {
		this.iProductoRepository = iProductoRepository;
	}
	

		public Producto save(Producto usuario) {
			return iProductoRepository.save(usuario);
		}
		
		
		public Optional<Producto> findById(Integer id){
			return iProductoRepository.findById(id);
		}
		
		
		public List<Producto> findAll(){
			return iProductoRepository.findAll();
		}
		
		public Producto edit(Producto producto) {
			return iProductoRepository.save(producto);
		}
		
		public Producto findByIdDocumento(Integer id) {
			return iProductoRepository.findByIdProducto(id);
		}

}
