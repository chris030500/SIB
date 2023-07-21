package ahorrosbienestar.sib.std.configuracion.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByNombre(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
        if (usuario.getI_estatus_cuenta()==0) {
            throw new LockedException("La cuenta está bloqueada");
        }
        
        // Resto de la lógica de autenticación y construcción de UserDetails
        
        UserDetails userDetails = (UserDetails) usuario;
		return userDetails;
    }
}
