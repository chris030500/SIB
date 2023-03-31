package ahorrosbienestar.sib.std.productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

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
		
		public Producto findByProducto(Integer id) {
			return iProductoRepository.findByProducto(id);
		}
		
		public List<Producto> findAll(){
			return iProductoRepository.findAll();
		}
		
		public List<Producto> todosProductos(){
			return iProductoRepository.todosProductos();
		}
		
		public Producto edit(Producto producto) {
			return iProductoRepository.save(producto);
		}
		
		public Producto findByIdDocumento(Integer id) {
			return iProductoRepository.findByIdProducto(id);
		}
		
		public Producto findByNoProducto(Integer producto) {
			return iProductoRepository.findByNoProducto(producto);
		}


		public int bajaProducto(Integer id_producto) {
			return iProductoRepository.bajaProducto(id_producto);
			
		}

		public int reactivar(Integer id_producto) {
			return iProductoRepository.reactivar(id_producto);
			
		}


		public int existeProducto(int idproducto) {
			return iProductoRepository.existeProducto(idproducto);
		}
		
		
		public Producto productoPorNumero(Integer numero) {
			return iProductoRepository.productoPorNumero(numero);
		}
		
		public int cambiarKycYDescripcion(String kyc, String descripcion, Integer idProducto) {
			return iProductoRepository.cambiarKycYDescripcion(kyc, descripcion, idProducto);
		}
		
		

}
