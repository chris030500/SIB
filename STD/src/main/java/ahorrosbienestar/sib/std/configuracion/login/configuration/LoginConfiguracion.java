package ahorrosbienestar.sib.std.configuracion.login.configuration;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ahorrosbienestar.sib.std.configuracion.login.component.CustomHttpSessionListener;

@Configuration
public class LoginConfiguracion {
	@Bean
    public ServletListenerRegistrationBean<CustomHttpSessionListener> sessionListener(CustomHttpSessionListener customHttpSessionListener) {
        ServletListenerRegistrationBean<CustomHttpSessionListener> listenerBean =
                new ServletListenerRegistrationBean<>();
        listenerBean.setListener(customHttpSessionListener);
        return listenerBean;
    }
}
