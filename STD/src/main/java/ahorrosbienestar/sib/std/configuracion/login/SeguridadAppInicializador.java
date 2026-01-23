package ahorrosbienestar.sib.std.configuracion.login;

import java.time.LocalDateTime;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ahorrosbienestar.sib.std.configuracion.login.component.CustomAuthenticationFailureHandler;
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
    private DataSource datasource;

    @Autowired
    private AccesoService accesoService;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try {
            http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/configuracion/sesionesInactiva",
                        "configuracion/mensajeContrasena/**",
                        "/configuracion/**",
                        "/configuracion/guardarConfContrasena/**",
                        "/configuracion/mensajeContrasena/**",
                        "/hist_contrasena/**",
                        "/usuario/**",
                        "/api/**"
                    ).permitAll()
                    .anyRequest().authenticated();

            http.formLogin()
                .successHandler((request, response, authentication) -> {
                    String username = authentication.getName();
                    Usuario usuario = usuarioService.findByNombre(username);
                    int estatus = usuario.getPk_id_usuario();

                    Acceso acceso = new Acceso();
                    acceso.setDt_fecha_registro(new Date());
                    acceso.setI_cod_accion(1);
                    acceso.setI_id_usuario(estatus);
                    acceso.setVc_descripci("Ingreso");
                    LocalDateTime locaDate = LocalDateTime.now();
                    acceso.setVc_hora_registro(locaDate.getHour() + ":" + locaDate.getMinute() + ":" + locaDate.getSecond());
                    acceso.setVc_host_remoto(request.getRemoteHost());
                    acceso.setVc_ip_remoto(request.getRemoteAddr());
                    acceso.setVc_nom_servidor(request.getServerName());
                    acceso.setVc_usuario(username);
                    accesoService.save(acceso);

                    request.getSession().setAttribute("username", username);
                    response.sendRedirect("/STD/inicio");
                })
                .loginPage("/login")
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll();

            http.logout().permitAll();
            http.exceptionHandling().accessDeniedPage("/error_403");

            http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/STD/inicio")
                .maxSessionsPreventsLogin(false);

            http.sessionManagement().invalidSessionUrl("/STD/inicio");

        } catch (Exception e) {
            System.out.println("datos" + e.getMessage());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication().dataSource(datasource).passwordEncoder(passwordEncoder()).usersByUsernameQuery(
                "SELECT u.VC_NOMBRE_USUARIO, hc.VC_CONTRASENIA, hc.VC_INDICADOR_ACTIVO " +
                "FROM TBL_CAT_USUARIOS as u " +
                "inner join TBL_HIST_CONTRASENIA as hc " +
                "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO) " +
                "WHERE u.I_ESTATUS_USUARIO = 1 and u.I_ESTATUS_SESION = 0 and u.I_ESTATUS_CUENTA " +
                "and hc.VC_INDICADOR_ACTIVO = 1 " +
                "and u.VC_NOMBRE_USUARIO = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.VC_NOMBRE_USUARIO, r.VC_NOMBRE_ROL, hc.VC_CONTRASENIA, hc.VC_INDICADOR_ACTIVO " +
                        "FROM TBL_CAT_USUARIOS as u " +
                        "inner join TBL_CAT_ROL as r " +
                        "on (u.Rol_idRol = r.PK_ID_ROL) " +
                        "inner join TBL_HIST_CONTRASENIA as hc " +
                        "on (hc.Usr_historico_contrasena_id = u.PK_ID_USUARIO) " +
                        "where hc.VC_INDICADOR_ACTIVO = 1 and  u.VC_NOMBRE_USUARIO = ?");
    }
}
