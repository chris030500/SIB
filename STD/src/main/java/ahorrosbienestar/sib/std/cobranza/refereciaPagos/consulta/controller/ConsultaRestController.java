package ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.controller;

import java.time.LocalDate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.dto.ConsultaDTO;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.dto.DatosConsultaDTO;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model.ConsultaModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.service.ConsultaService;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model.CuentasClienteModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.service.CuentasClienteService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ConsultaRestController {

	@Autowired
	private ConsultaService consultaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	CuentasClienteService cuentasClienteService;

	// Logger
	private static final Logger logger = Logger.getLogger(ConsultaRestController.class);

	public ConsultaModel guardarConsulta(ConsultaDTO dto, CuentasClienteModel cuenta) {

		// model
		ConsultaModel model = new ConsultaModel();

		// fecha
		LocalDate fechaLocal = LocalDate.now();

		try {

			// authentication
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("Se encontró la autenticación");

			// usuario
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("Usuario que realizó la busqueda: " + usuario.getVc_nombre_usuario());

			model.setDt_fecha_registro(fechaLocal);
			model.setFk_usuario_registro(usuario);
			model.setVc_parametro_busqueda(dto.getParametroBusqueda());
			model.setVc_valor_parametro(dto.getValorParametro());
			model.setI_numero_cliente(dto.getNumeroCliente());
			model.setVc_nombre_cliente(cuenta.getVc_nombre_cliente());
			model.setI_numero_cuenta_principal_1(cuenta.getI_referencia_cta_principal_1());
			model.setI_numero_cuenta_principal_2(cuenta.getI_referencia_cta_principal_2());
			consultaService.save(model);

			return model;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error: " + e.getMessage());
			return null;
		}

	}
}
