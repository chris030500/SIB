package ahorrosbienestar.sib.std.configuracion.login;

import java.time.LocalDateTime;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import ahorrosbienestar.sib.std.configuracion.login.component.CustomAuthenticationFailureHandler;
import ahorrosbienestar.sib.std.configuracion.login.service.LoginService;
import ahorrosbienestar.sib.std.index.model.Acceso;
import ahorrosbienestar.sib.std.index.service.AccesoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

import java.util.Calendar;


@Configuration
@EnableWebSecurity
public class SeguridadAppInicializador {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private HistContrasenaService histContrasenaService;
    
    @Autowired
    private ConfigService configService;

    @Autowired
    private DataSource datasource;

    @Autowired
    private AccesoService accesoService;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    
    @Autowired
    private LoginService loginService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/configuracion/sesionesInactiva",
                    "/configuracion/mensajeContrasena/**",
                    "/configuracion/**",
                    "/configuracion/guardarConfContrasena/**",
                    "/hist_contrasena/**",
                    "/usuario/**",
                    "/api/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
            		.successHandler((request, response, authentication) -> {


            			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            			String fechaActual = dateFormat.format(Calendar.getInstance().getTime());

            			LocalDate localDate = LocalDate.parse(fechaActual);

            			int diasVigencia = Integer.parseInt(
            			        configService.findAll().get(1).getVc_valor_propiedad()
            			);

            			LocalDate fechaVencimiento = localDate.plusDays(diasVigencia);

            		    String username = authentication.getName();

            		    Usuario usuario = usuarioService.findByNombre(username);

            		    HistContrasena historico =
            		            histContrasenaService.obtenerContrasenaActiva(
            		                    usuario.getPk_id_usuario()
            		            );

            		    // CAMBIO OBLIGATORIO
            		    if (historico != null
            		            && historico.getDt_vig_cont_fin() != null
            		            && LocalDate.now().isAfter(historico.getDt_vig_cont_fin())) {

            		        request.getSession().setAttribute("username", username);

            		        response.sendRedirect(
            		                request.getContextPath() + "/cambio_contrasena"
            		        );

            		        return;
            		    }

            		    // VALIDAR SI YA TENÍA SESIÓN ACTIVA
            		    Integer sesionActiva = loginService.obtenerSesionActiva(username);

            		    if (sesionActiva != null && sesionActiva == 1) {

            		        usuario.setI_estatus_cuenta(0);

            		        usuarioService.save(usuario);

            		        SecurityContextHolder.clearContext();

            		        request.getSession().invalidate();

            		        response.sendRedirect(
            		                request.getContextPath() + "/login?error=usuarioBloqueado"
            		        );

            		        return;
            		    }

            		    // RESETEAR INTENTOS FALLIDOS
            		    usuario.setI_intentos_fallidos(0);

            		    usuarioService.save(usuario);

            		    // BITÁCORA
            		    Acceso acceso = new Acceso();

            		    acceso.setDt_fecha_registro(new Date());

            		    acceso.setI_cod_accion(1);

            		    acceso.setI_id_usuario(usuario.getPk_id_usuario());

            		    acceso.setVc_descripci("Ingreso");

            		    LocalDateTime locaDate = LocalDateTime.now();

            		    acceso.setVc_hora_registro(
            		            locaDate.getHour() + ":" +
            		            locaDate.getMinute() + ":" +
            		            locaDate.getSecond()
            		    );

            		    acceso.setVc_host_remoto(request.getRemoteHost());

            		    acceso.setVc_ip_remoto(request.getRemoteAddr());

            		    acceso.setVc_nom_servidor(request.getServerName());

            		    acceso.setVc_usuario(username);

            		    accesoService.save(acceso);

            		    // ACTIVAR SESIÓN
            		    loginService.spActivarSesion(username);

            		    request.getSession().setAttribute("username", username);

            		    request.getSession().setMaxInactiveInterval(35 * 60);

            		    response.sendRedirect(request.getContextPath() + "/inicio");
            		})
                .loginPage("/login")
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
            )
            .logout(logout -> logout
            	    .logoutUrl("/cerrar")
            	    .logoutSuccessUrl("/login?logout")
            	    .invalidateHttpSession(true)
            	    .clearAuthentication(true)
            	    .deleteCookies("JSESSIONID")
            	    .permitAll()
            	)
            	.exceptionHandling(ex -> ex.accessDeniedPage("/error_403"))
            	.sessionManagement(session -> session
            		    .invalidSessionUrl("/login")
            		    .maximumSessions(1)
            		    .expiredUrl("/login?expired")
            		    .maxSessionsPreventsLogin(false)
            		);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() {
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null) {
                    return false;
                }

                String stored = encodedPassword.trim();
                if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
                    return super.matches(rawPassword, stored);
                }

                // Compatibilidad para contraseñas legadas guardadas en texto plano.
                return stored.contentEquals(rawPassword);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
        manager.setUsersByUsernameQuery(
            "SELECT u.VC_NOMBRE_USUARIO, TRIM(hc.VC_CONTRASENIA), TRUE FROM TBL_CAT_USUARIOS as u \r\n"
                + "inner join TBL_HIST_CONTRASENIA as hc\r\n"
                + "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO)\r\n"
                + "WHERE u.I_ESTATUS_USUARIO = 1 and u.I_ESTATUS_CUENTA = 1 "
                + "and hc.VC_INDICADOR_ACTIVO = 1 \r\n"
                + "and UPPER(TRIM(u.VC_NOMBRE_USUARIO)) = UPPER(TRIM(?)) \r\n"
                + "ORDER BY hc.PK_ID_PARAMETROS DESC \r\n"
                + "LIMIT 1"
        );
        manager.setAuthoritiesByUsernameQuery(
            "SELECT u.VC_NOMBRE_USUARIO, CONCAT('ROLE_', r.VC_NOMBRE_ROL) \r\n"
            + "FROM TBL_CAT_USUARIOS as u \r\n"
            + "inner join TBL_CAT_ROL as r \r\n"
            + "on (u.Rol_idRol = r.PK_ID_ROL)\r\n"
            + "where u.I_ESTATUS_USUARIO = 1 and u.I_ESTATUS_CUENTA = 1 \r\n"
            + "and UPPER(TRIM(u.VC_NOMBRE_USUARIO)) = UPPER(TRIM(?))"
        );
        return manager;
    }
}