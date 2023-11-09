package ahorrosbienestar.sib.std.configuracion.login;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ahorrosbienestar.sib.std.configuracion.login.component.CustomAuthenticationFailureHandler;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.core_bancario.dto.MenuGeneral;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.index.model.Acceso;
import ahorrosbienestar.sib.std.index.service.AccesoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;



@Configuration
@EnableWebSecurity
public class SeguridadAppInicializador extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DataSource datasource;

	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private AccesoService accesoService;
	
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		try {
			// Aqui especificamos a que url el usuario tiene acceso
			http.
			csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/images/**","/configuracion/sesionesInactiva", "configuracion/mensajeContrasena/**","/configuracion/**", "/configuracion/guardarConfContrasena/**", "/configuracion/mensajeContrasena/**", "/hist_contrasena/**", "/usuario/**").permitAll();
			/*
			 * List<MenuGeneral> menuGeneral = coreBancarioService.menuGeneral();
			 * if(menuGeneral.size() > 0) { for (MenuGeneral permisos : menuGeneral) {
			 * http.csrf().disable() .authorizeRequests()
			 * .antMatchers(permisos.getVc_nombre_controller().split(","))
			 * .hasAnyRole(permisos.getVc_nombre_rol()); } }
			 */
	        http.csrf().disable()
			.authorizeRequests()
	        .anyRequest().authenticated().and()
			.formLogin().successHandler((request, response, authentication) -> {
					// Obtiene el nombre de usuario
					String username = authentication.getName();
					// Obteniendo  informacion del usuario con una consulta por por username
					Usuario usuario = usuarioService.findByNombre(username);
					int estatus = usuario.getPk_id_usuario();
					// Setenado valores al modelo Acceso
					Acceso acceso = new Acceso();
					acceso.setDt_fecha_registro(new Date());
					acceso.setI_cod_accion(1);
					acceso.setI_id_usuario(estatus);
					acceso.setVc_descripci("Ingreso");
					LocalDateTime locaDate = LocalDateTime.now();
					acceso.setVc_hora_registro(locaDate.getHour()  + ":"+ locaDate.getMinute() +":"+locaDate.getSecond());
					acceso.setVc_host_remoto(request.getRemoteHost());
					acceso.setVc_ip_remoto(request.getRemoteAddr());
					acceso.setVc_nom_servidor(request.getServerName());
					acceso.setVc_usuario(username);
					//Guardanto los valores de acceso en la base de datos
					accesoService.save(acceso);
					System.out.println("Acceso registrado en bitacora correctamente");

					// Almacena el nombre de usuario en la sesión
					request.getSession().setAttribute("username", username);

					// Redirecciona a la página principal u otra página deseada
					response.sendRedirect("/STD/inicio");
				})
				.loginPage("/login")
				.failureHandler(customAuthenticationFailureHandler)
				.permitAll()
				.and()
				.logout()
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedPage("/error_403");

		http.sessionManagement()
		.maximumSessions(1)
		.expiredUrl("/STD/inicio")
		.maxSessionsPreventsLogin(false)
		.and()
		.invalidSessionUrl("/STD/inicio");

			
		} catch (Exception e) {
			System.out.println("datos"+e.getMessage());
		}
	}

	// Metodo para encriptar la contraseña
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {

		builder.jdbcAuthentication().dataSource(datasource).passwordEncoder(passwordEncoder).usersByUsernameQuery(
				"SELECT u.VC_NOMBRE_USUARIO, hc.VC_CONTRASENIA, hc.VC_INDICADOR_ACTIVO FROM TBL_CAT_USUARIOS as u \r\n"
						+ "inner join TBL_HIST_CONTRASENIA as hc\r\n"
						+ "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO)\r\n"
						+ "WHERE u.I_ESTATUS_USUARIO = 1 and u.I_ESTATUS_SESION = 0 and u.I_ESTATUS_CUENTA "
						+ "and hc.VC_INDICADOR_ACTIVO = 1 \r\n"
						+ "and u.VC_NOMBRE_USUARIO = ?")
				.authoritiesByUsernameQuery(
						"SELECT u.VC_NOMBRE_USUARIO, r.VC_NOMBRE_ROL, hc.VC_CONTRASENIA, hc.VC_INDICADOR_ACTIVO \r\n"
								+ "FROM TBL_CAT_USUARIOS as u \r\n" + "inner join TBL_CAT_ROL as r \r\n"
								+ "on (u.Rol_idRol = r.PK_ID_ROL)\r\n" + "inner join TBL_HIST_CONTRASENIA as hc\r\n"
								+ "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO)\r\n"
								+ "where hc.VC_INDICADOR_ACTIVO = 1 and  u.VC_NOMBRE_USUARIO = ?");

	}

	
}
