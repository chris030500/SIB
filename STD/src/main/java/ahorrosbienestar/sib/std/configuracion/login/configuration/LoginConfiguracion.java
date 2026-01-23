package ahorrosbienestar.sib.std.configuracion.login.configuration;

import jakarta.servlet.http.HttpSessionListener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ahorrosbienestar.sib.std.configuracion.login.component.CustomHttpSessionListener;

@Configuration
public class LoginConfiguracion {
	@Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        ServletListenerRegistrationBean<HttpSessionListener> listenerBean =
                new ServletListenerRegistrationBean<>();
        listenerBean.setListener(new CustomHttpSessionListener());
        return listenerBean;
    }
}
