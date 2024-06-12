package ahorrosbienestar.sib.std.sccb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Imagenes_SCCB_2/**") // URL desde la cual se servirá la imagen
                .addResourceLocations("file://10.150.102.106/Imagenes_SCCB_2/"); // Ubicación de la imagen en la red compartida
        
        registry.addResourceHandler("/Imagenes_SCCB/**") // URL desde la cual se servirá la imagen
        .addResourceLocations("file://10.150.102.106/Imagenes_SCCB/"); // Ubicación de la imagen en la red compartida
    }
	
}
