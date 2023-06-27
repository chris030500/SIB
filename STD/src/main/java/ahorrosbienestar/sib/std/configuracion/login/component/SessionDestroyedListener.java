package ahorrosbienestar.sib.std.configuracion.login.component;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

public class SessionDestroyedListener implements ApplicationListener<HttpSessionDestroyedEvent>{

	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
		// TODO Auto-generated method stub
		String username = (String) event.getSession().getAttribute("SPRING_SECURITY_LAST_USERNAME");
        // Aquí también puedes realizar la lógica para cambiar el estado del usuario en base de datos
        // utilizando el nombre de usuario obtenido

        System.out.println("Session destroyed onApplicationEvent. User: " + username);
	}

}
