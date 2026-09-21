package ahorrosbienestar.sib.std.configuracion.login.component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.index.model.Acceso;
import ahorrosbienestar.sib.std.index.service.AccesoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AccesoService accesoService;
    
    @Autowired
    private ConfigService configService;

    private static final Logger logger =
            LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String username = request.getParameter("username");

        logger.warn("Fallo de autenticacion para usuario [" + username + "]: "
                + exception.getClass().getSimpleName() + " - " + exception.getMessage());

        if (exception instanceof SessionAuthenticationException) {
            request.getSession().setAttribute(
                    "error",
                    "Ya existe una sesión activa para este usuario. Cierre sesión o espere a que expire."
            );
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Usuario usuario = usuarioService.findByNombre(username);

        if (usuario == null) {
            request.getSession().setAttribute(
                    "error",
                    "Nombre de usuario o contraseña incorrecta."
            );
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (usuario.getI_estatus_cuenta() == 0) {
            request.getSession().setAttribute(
                    "error",
                    "Error en el inicio de sesión: Usuario bloqueado."
            );
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (usuario.getI_estatus_usuario() == 0) {
            request.getSession().setAttribute(
                    "error",
                    "Usuario inactivo, favor de comunicarse con el administrador del sistema."
            );
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int failedAttempts = usuario.getI_intentos_fallidos() + 1;
        usuario.setI_intentos_fallidos(failedAttempts);

        registrarAcceso(request, usuario, username, 2, "Intento de Acceso");
        
        Integer intentosMaximos = configService.obtenerIntentosMaximos();

        if (failedAttempts >= intentosMaximos) {
            usuario.setI_estatus_cuenta(0);

            registrarAcceso(request, usuario, username, 4, "Cuenta Bloqueada");

            request.getSession().setAttribute(
                    "error",
                    "La cuenta ha sido bloqueada por exceder el número de intentos permitidos."
            );

            logger.info("Cuenta bloqueada por intentos fallidos: " + username);
        } else {
            request.getSession().setAttribute(
                    "error",
                    "Nombre de usuario o contraseña incorrecta. Intento "
                            + failedAttempts + " de " + intentosMaximos + "."
            );

            logger.info("Intento fallido " + failedAttempts + " de " + intentosMaximos + " para usuario: " + username);
        }

        usuarioService.save(usuario);

        response.sendRedirect(request.getContextPath() + "/login");
    }

    private void registrarAcceso(HttpServletRequest request,
                                 Usuario usuario,
                                 String username,
                                 int codigoAccion,
                                 String descripcion) {

        Acceso acceso = new Acceso();
        acceso.setDt_fecha_registro(new Date());
        acceso.setI_cod_accion(codigoAccion);
        acceso.setI_id_usuario(usuario.getPk_id_usuario());
        acceso.setVc_descripci(descripcion);

        LocalDateTime locaDate = LocalDateTime.now();
        acceso.setVc_hora_registro(
                locaDate.getHour() + ":" + locaDate.getMinute() + ":" + locaDate.getSecond()
        );

        acceso.setVc_host_remoto(request.getRemoteHost());
        acceso.setVc_ip_remoto(request.getRemoteAddr());
        acceso.setVc_nom_servidor(request.getServerName());
        acceso.setVc_usuario(username);

        accesoService.save(acceso);
    }
}