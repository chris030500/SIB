package ahorrosbienestar.sib.std;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@EnableEncryptableProperties
public class StdApplication implements CommandLineRunner {
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(StdApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		String contrasena = "12345";
//		
//		for(int i= 0; i<3; i++) {
//			String bcryptPassword = passwordEncoder.encode(contrasena);
//			System.out.println(bcryptPassword);
//		}
		
	}
	
	

}
