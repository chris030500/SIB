package ahorrosbienestar.sib.std.configuracion.login;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ahorrosbienestar.sib.std.core_bancario.dto.MenuGeneral;
import ahorrosbienestar.sib.std.core_bancario.dto.MenuxRol;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;


@Configuration
public class SeguridadAppInicializador extends WebSecurityConfigurerAdapter{
	
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private DataSource datasource;
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		try {
			List<MenuGeneral> menuGeneral = coreBancarioService.menuGeneral();
			
			//Aqui especificamos a que url el usuario tiene acceso
			http.
			csrf().disable()
			.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/images/**","/configuracion/sesionesInactiva", "configuracion/mensajeContrasena/**","/configuracion/**", "/configuracion/guardarConfContrasena/**", "/configuracion/mensajeContrasena/**", "/hist_contrasena/**", "/usuario/**").permitAll()
//			.antMatchers("/configuracion/sesionesInactiva", "configuracion/mensajeContrasena/**").hasAnyRole("ADMIN" , "EJECUTIVO", "VALIDADOR", "CONSULTA", "AUTORIZADOR", "ARCHIVO", "PRODUCTOS","ADMIN_CSO")
//			.antMatchers("/configuracion/**", "/configuracion/guardarConfContrasena/**", "/configuracion/mensajeContrasena/**", "/hist_contrasena/**", "/usuario/**").hasAnyRole("ADMIN", "EJECUTIVO", "VALIDADOR", "CONSULTA", "AUTORIZADOR", "ARCHIVO", "PRODUCTOS","ADMIN_CSO")
			.antMatchers(menuGeneral.stream().map(MenuGeneral::getVc_nombre_controller).toArray(String[]::new))
            .hasAnyRole(menuGeneral.stream().flatMap(a -> Arrays.stream(a.getVc_nombre_rol().split(","))).map(String::trim).toArray(String[]::new))
//			.antMatchers("/documento/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/producto/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/autorizar/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/cancelacion/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/contrato/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/carga_documentos/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/solicitudEnvio/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/videoGrabacion/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/folios/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/planAccion/**").hasAnyRole("ADMIN_PRUEBA")
//			.antMatchers("/seguimientoFolio/**").hasAnyRole("ADMIN_CSO", "CONSULTA_CSO", "ATENCION_CSO", "RESPONSABLE_CSO","REGISTRO_CSO")
//			.antMatchers("/reporteFolio/**").hasAnyRole("ADMIN_CSO", "CONSULTA_CSO", "ATENCION_CSO", "RESPONSABLE_CSO","REGISTRO_CSO")
			.anyRequest().authenticated()
			.and()
				.formLogin().loginPage("/login")
				.permitAll()
				.and()
				.logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");
			
	
			http
	        .sessionManagement()
	        .maximumSessions(1)
	        .expiredUrl("/")
	        .maxSessionsPreventsLogin(false)
	        .and()
	        .invalidSessionUrl("/");
			
		} catch (Exception e) {
			System.out.println("datos"+e.getMessage());
		}
	}
	
	
	//Metodo para encriptar la contraseña
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		builder.jdbcAuthentication()
		.dataSource(datasource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("SELECT u.VC_NOMBRE_USUARIO, hc.VC_CONTRASENIA, hc.VC_INDICADOR_ACTIVO FROM TBL_CAT_USUARIOS as u \r\n"
				+ "inner join TBL_HIST_CONTRASENIA as hc\r\n"
				+ "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO)\r\n"
				+ "WHERE u.I_ESTATUS_USUARIO = 1 and u.I_ESTATUS_SESION = 0 and hc.VC_INDICADOR_ACTIVO = 1 \r\n"
				+ "and u.VC_NOMBRE_USUARIO = ?")
		.authoritiesByUsernameQuery("SELECT u.VC_NOMBRE_USUARIO, r.VC_NOMBRE_ROL, hc.VC_CONTRASENIA, hc.VC_INDICADOR_ACTIVO \r\n"
				+ "FROM TBL_CAT_USUARIOS as u \r\n"
				+ "inner join TBL_CAT_ROL as r \r\n"
				+ "on (u.Rol_idRol = r.PK_ID_ROL)\r\n"
				+ "inner join TBL_HIST_CONTRASENIA as hc\r\n"
				+ "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO)\r\n"
				+ "where hc.VC_INDICADOR_ACTIVO = 1 and  u.VC_NOMBRE_USUARIO = ?");
	}
	
}
