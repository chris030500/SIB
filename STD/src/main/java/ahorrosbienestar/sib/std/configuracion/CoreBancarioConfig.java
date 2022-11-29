package ahorrosbienestar.sib.std.configuracion;
import javax.sql.DataSource;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;



@Configuration
public class CoreBancarioConfig {
    
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        //dataSourceBuilder.url("mysql://10.150.102.99:3306/microfin");
        dataSourceBuilder.url("jdbc:sqlserver://10.150.102.99:3306;databaseName=microfin");
        dataSourceBuilder.username("usr_desarrollo");
        dataSourceBuilder.password("Usr_d3s4rro11085*");
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