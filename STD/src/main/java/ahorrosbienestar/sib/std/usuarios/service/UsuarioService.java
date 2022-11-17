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
	
	public int activarUsuarioSesion(Integer id) {
		return iUsuarioRepository.activarUsuarioSesion(id);
	}
	
	public int activarUsuarioCuenta(Integer id) {
		return iUsuarioRepository.activarUsuarioCuenta(id);
	}
	
	public Usuario findByNombre(String nombre){
		return iUsuarioRepository.findByNombre(nombre);
	}
	
//	public List<Usuario> encontrarUsuarioAndFecha(String nombre) {
//		return iUsuarioRepository.encontrarUsuarioAndFecha(nombre);
//	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Usuario us = iUsuarioRepository.findByUsuarioAndEstatusUsuario(username);
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		
//		logger.error("El usuaruario es: "+us);
//		if(us == null) {
//			logger.error("El usuaruario es: "+us);
//		}
//		
//		
//		System.out.println("Logger : "+ us);
//		for(Rol role: us.getRol_idRol()) {
//			authorities.add(new SimpleGrantedAuthority(role.getVc_nombre_rol()));
//		}
//
//		return new User(us.getVc_nombre_usuario(), "12345", rol);
//	}
	
	

}
