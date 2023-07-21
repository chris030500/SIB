package ahorrosbienestar.sib.std.configuracion.login.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Usuario usuario = usuarioService.findByNombre(username);
        
        if (usuario != null) {
            if (usuario.getI_estatus_cuenta() == 0) {
                request.getSession().setAttribute("error", "Error en el inicio de sesión: "
                        + "Usuario bloqueado");
            } else {
                int failedAttempts = usuario.getI_intentos_fallidos();
                failedAttempts++;
                usuario.setI_intentos_fallidos(failedAttempts);

                if (failedAttempts >= 3) {
                    usuario.setI_estatus_cuenta(0);
                }

                usuarioService.save(usuario);
                request.getSession().setAttribute("error", "Error en el inicio de sesión: "
                        + "Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
            }
        } else {
        	request.getSession().setAttribute("error", null);
        }

        // Redirigir internamente a la página de inicio de sesión
        response.sendRedirect(request.getContextPath() + "/login");
    }
}