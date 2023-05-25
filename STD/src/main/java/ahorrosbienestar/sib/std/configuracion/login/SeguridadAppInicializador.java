package ahorrosbienestar.sib.std.configuracion.login;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
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


@Configuration
public class SeguridadAppInicializador extends WebSecurityConfigurerAdapter{
	
	@Autowired BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource datasource;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Aqui especificamos a que url el usuario tiene acceso
		http.
		csrf().disable().
		authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**").permitAll()
		.antMatchers("/configuracion/sesionesInactiva", "configuracion/mensajeContrasena/**").hasAnyRole("ADMIN" , "EJECUTIVO", "VALIDADOR", "CONSULTA", "AUTORIZADOR", "ARCHIVO", "PRODUCTOS","ADMIN_CSO")
		.antMatchers("/configuracion/**", "/configuracion/guardarConfContrasena/**", "/configuracion/mensajeContrasena/**", "/hist_contrasena/**", "/usuario/**").hasAnyRole("ADMIN", "EJECUTIVO", "VALIDADOR", "CONSULTA", "AUTORIZADOR", "ARCHIVO", "PRODUCTOS","ADMIN_CSO")
		.antMatchers("/documento/**").hasAnyRole("ADMIN", "ARCHIVO", "VALIDADOR")
		.antMatchers("/producto/**").hasAnyRole("ADMIN", "ARCHIVO", "PRODUCTOS")
		.antMatchers("/autorizar/**").hasAnyRole("ADMIN", "ARCHIVO", "EJECUTIVO")
		.antMatchers("/cancelacion/**").hasAnyRole("ADMIN", "VALIDADOR", "EJECUTIVO", "AUTORIZADOR")
		.antMatchers("/contrato/**").hasAnyRole("ADMIN", "EJECUTIVO", "AUTORIZADOR")
		.antMatchers("/carga_documentos/**").hasAnyRole("ADMIN", "EJECUTIVO", "VALIDADOR")
		.antMatchers("/solicitudEnvio/**").hasAnyRole("ADMIN", "EJECUTIVO", "VALIDADOR", "AUTORIZADOR")
		.antMatchers("/videoGrabacion/**").hasAnyRole("ADMIN", "EJECUTIVO", "VALIDADOR", "CONSULTA")
		.antMatchers("/folios/**").hasAnyRole("ADMIN_CSO", "CONSULTA_CSO", "ATENCION_CSO", "RESPONSABLE_CSO","REGISTRO_CSO")
		.antMatchers("/planAccion/**").hasAnyRole("ADMIN_CSO", "CONSULTA_CSO", "ATENCION_CSO", "RESPONSABLE_CSO","REGISTRO_CSO")
		.antMatchers("/seguimientoFolio/**").hasAnyRole("ADMIN_CSO", "CONSULTA_CSO", "ATENCION_CSO", "RESPONSABLE_CSO","REGISTRO_CSO")
		.antMatchers("/reporteFolio/**").hasAnyRole("ADMIN_CSO", "CONSULTA_CSO", "ATENCION_CSO", "RESPONSABLE_CSO","REGISTRO_CSO")
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
		//Forma para crear usuarios en memoria
//		PasswordEncoder encoder = passwordEncoder();
//		UserBuilder users = User.builder().passwordEncoder(password ->{
//			return encoder.encode(password);
//		});
//		
//		builder.inMemoryAuthentication()
//		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
//		.withUser(users.username("user").password("12345").roles("USER"));
	}
	
}
