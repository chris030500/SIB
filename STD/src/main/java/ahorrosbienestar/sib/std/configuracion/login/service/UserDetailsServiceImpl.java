package ahorrosbienestar.sib.std.configuracion.login.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.contrasena.dto.HistContrasenaDto;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaDtoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HistContrasenaDtoService histContrasenaDtoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByNombre(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        if (usuario.getI_estatus_cuenta() == 0) {
            throw new LockedException("La cuenta esta bloqueada");
        }

        List<HistContrasenaDto> contrasenas = histContrasenaDtoService.obtenerUsuarioAndFecha(username);
        if (contrasenas.isEmpty()) {
            throw new UsernameNotFoundException("No se encontro contrasena activa");
        }

        String password = contrasenas.get(0).getVc_contrasenia();
        String rol = usuario.getRol_idRol() != null ? usuario.getRol_idRol().getVc_nombre_rol() : "USER";

        return new User(
            usuario.getVc_nombre_usuario(),
            password,
            Collections.singletonList(new SimpleGrantedAuthority(rol))
        );
    }
}