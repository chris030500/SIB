package ahorrosbienestar.sib.std.xml.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ValidityXmlService {

	private JdbcTemplate jdbcTemplate;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(ValidityXmlService.class);

	@Autowired
	public ValidityXmlService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Declaramos que SP se va a ejecutar (SP_VALIDITY_NAT)
	//Obtenemos la difencia de dias entre la fecha de cancelacion y la fecha de envio.
	public String spValidityNat(int solicitudEnvio) {
		logger.info("Ejecutando CALL SIB.SP_VALIDITY_NAT(" + solicitudEnvio + ")");
		return jdbcTemplate.queryForObject("CALL SIB.SP_VALIDITY_NAT(" + solicitudEnvio + ")", String.class);
	}

}
