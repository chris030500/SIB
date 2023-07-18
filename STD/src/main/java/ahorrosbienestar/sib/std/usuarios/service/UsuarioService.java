package ahorrosbienestar.sib.std.usuarios.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Service
public class UsuarioService {
	
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	public UsuarioService(IUsuarioRepository iUsuarioRepository) {
		this.iUsuarioRepository = iUsuarioRepository;
	}
	
	
	//Metodo para dar de alta usuario
	public Usuario save(Usuario usuario) {
		return iUsuarioRepository.save(usuario);
	}
	
	//Metodo para buscar por id de Usuario
	public Optional<Usuario> findById(Integer id){
		return iUsuarioRepository.findById(id);
	}
	
	
	//Metodo para buscar todos los usuarios
	public List<Usuario> findAll(){
		return iUsuarioRepository.findAll();
	}
	
	public Usuario edit(Usuario usuario) {
		return iUsuarioRepository.save(usuario);
	}
	
	//Metodo para buscar usuario por nombre de usuario
	public List<Usuario> findByNombreUsuario(String nombreUsuario){
		return iUsuarioRepository.findByNombreUsuario(nombreUsuario);
	}
	
	public List<Usuario> obtenerNombreUsuarioYIdusuario(String nombreUsuario, Integer idUsuario){
		return iUsuarioRepository.obtenerNombreUsuarioYIdusuario(nombreUsuario, idUsuario);
	}
	
	public int activarUsuarioSesion(Integer id) {
		return iUsuarioRepository.activarUsuarioSesion(id);
	}
	
	public int activarUsuarioCuenta(Integer id) {
		return iUsuarioRepository.activarUsuarioCuenta(id);
	}
	
	public int activarUsuarioEstatus(Integer id) {
		return iUsuarioRepository.activarUsuarioEstatus(id);
	}
	
	public int bajaUsuarioEstatus(Integer id) {
		return iUsuarioRepository.bajaUsuarioEstatus(id);
	}
	
	public int cambiarNombreUsuario(String usuario, Integer idUsuario, Integer idRol) {
		return iUsuarioRepository.cambiarNombreUsuario(usuario, idUsuario, idRol);
	}
	
	public Usuario findByNombre(String nombre){
		return iUsuarioRepository.findByNombre(nombre);
	}
	
	public Usuario findByNombreUsuarioBoolean(String nombre){
		return iUsuarioRepository.findByNombreUsuarioBoolean(nombre);
	}
	
	public Usuario findByIdEmpleadoBoolean(Integer idEmpleado) {
		return iUsuarioRepository.findByIdEmpleadoBoolean(idEmpleado);
	}
	
	public Optional<Usuario> findByUsuarioNombre(String nombre) {
		return iUsuarioRepository.findByUsuarioNombre(nombre);
	}
	
	public int destruirSesion(Integer usuario) {
		return iUsuarioRepository.destruirSesion(usuario);
	}
	
	public int destruirSesionUsuario() {
		return iUsuarioRepository.destruirSesionUsuario();
	}
	
	
	
	

}
