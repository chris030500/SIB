package ahorrosbienestar.sib.std.core_bancario.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

@Configuration
public class CoreBancarioConfig {
	
	@Autowired
	private ConfigService configService;
	
	
	
	public DataSource getDataSource() {
		
		//Traemos listado de los parametros de la configuracion de la base de datos.
		List<Configuracion> c = configService.obtenerInfoBDCore();
			
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//		dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//		//dataSourceBuilder.url("mysql://10.150.102.99:3306/microfin");
//		dataSourceBuilder.url("jdbc:sqlserver://10.150.102.99:3306;databaseName=microfin");
//		dataSourceBuilder.username("usr_desarrollo");
//		dataSourceBuilder.password("Usr_d3s4rro11085*");
		dataSourceBuilder.driverClassName(c.get(0).getVc_valor_propiedad());
		dataSourceBuilder.url(c.get(1).getVc_valor_propiedad());
		dataSourceBuilder.username(c.get(2).getVc_valor_propiedad());
		dataSourceBuilder.password(c.get(3).getVc_valor_propiedad());
		return dataSourceBuilder.build();
	}
	
	@Bean()
	@Autowired()
	public JdbcTemplate getJdbcTemplate(DataSource coreBancarioDataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(coreBancarioDataSource);
		return jdbcTemplate;
	}
}
