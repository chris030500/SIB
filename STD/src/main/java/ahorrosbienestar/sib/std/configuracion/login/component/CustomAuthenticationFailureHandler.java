package ahorrosbienestar.sib.std.configuracion.login.component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.index.model.Acceso;
import ahorrosbienestar.sib.std.index.service.AccesoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AccesoService accesoService;

	private static final Logger logger = Logger.getLogger(CustomAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String username = request.getParameter("username");
		Usuario usuario = usuarioService.findByNombre(username);

		if (usuario != null) {
			

			if (usuario.getI_estatus_cuenta() == 0) {
				request.getSession().setAttribute("error", "Error en el inicio de sesión: " + "Usuario bloqueado");
				logger.info("Error en el inicio de sesión, Usuario bloqueado");
			}
			if (usuario.getI_estatus_usuario() == 0) {
				request.getSession().setAttribute("error",
						"Usuario inactivo, favor de comunicarse con el administrador del sistema");
				logger.info("Error en el inicio de sesión, Usuario inactivo");
			} else {
				
				

				if (usuario.getI_intentos_fallidos() <= 2) {				
					int failedAttempts = usuario.getI_intentos_fallidos();
					failedAttempts++;
					usuario.setI_intentos_fallidos(failedAttempts);
					//System.out.println("failedAttempts: " + failedAttempts);
					if (failedAttempts >= 2) {
						//La cuenta se bloquea
						usuario.setI_estatus_cuenta(0);
						// Obteniendo  id_usuario
						int estatus = usuario.getPk_id_usuario();
						// Seteando valores al modelo Acceso
						Acceso acceso = new Acceso();
						acceso.setDt_fecha_registro(new Date());
						acceso.setI_cod_accion(4);
						acceso.setI_id_usuario(estatus);
						acceso.setVc_descripci("Cuenta Bloqueada");
						LocalDateTime locaDate = LocalDateTime.now();
						acceso.setVc_hora_registro(locaDate.getHour()  + ":"+ locaDate.getMinute() +":"+locaDate.getSecond());
						acceso.setVc_host_remoto(request.getRemoteHost());
						acceso.setVc_ip_remoto(request.getRemoteAddr());
						acceso.setVc_nom_servidor(request.getServerName());
						acceso.setVc_usuario(username);
						//Guardanto los valores de acceso en la base de datos
						accesoService.save(acceso);
						System.out.println("Cuenta Bloqueada registrado en bitacora correctamente");
					}
					
					usuarioService.save(usuario);
					request.getSession().setAttribute("error", "Error en el inicio de sesión: "
							+ "Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
					logger.info("Error en el inicio de sesión, Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
				
					// Obteniendo  id_usuario
					int estatus = usuario.getPk_id_usuario();
					// Seteando valores al modelo Acceso
					Acceso acceso = new Acceso();
					acceso.setDt_fecha_registro(new Date());
					acceso.setI_cod_accion(2);
					acceso.setI_id_usuario(estatus);
					acceso.setVc_descripci("Intento de Acceso");
					LocalDateTime locaDate = LocalDateTime.now();
					acceso.setVc_hora_registro(locaDate.getHour()  + ":"+ locaDate.getMinute() +":"+locaDate.getSecond());
					acceso.setVc_host_remoto(request.getRemoteHost());
					acceso.setVc_ip_remoto(request.getRemoteAddr());
					acceso.setVc_nom_servidor(request.getServerName());
					acceso.setVc_usuario(username);
					//Guardanto los valores de acceso en la base de datos
					accesoService.save(acceso);
					System.out.println("Intento de Acceso registrado en bitacora correctamente");
				}

			}
		} else {
			request.getSession().setAttribute("error", null);
			logger.info(usuario);
		}

		// Redirigir internamente a la página de inicio de sesión
		response.sendRedirect(request.getContextPath() + "/login");
	}
	
}