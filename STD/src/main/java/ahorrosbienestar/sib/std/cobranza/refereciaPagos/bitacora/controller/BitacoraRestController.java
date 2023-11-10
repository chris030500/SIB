package ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.model.BitacoraModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.service.BitacoraService;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model.ConsultaModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model.CuentasClienteModel;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BitacoraRestController {

	@Autowired
	private BitacoraService bitacoraService;

	// Logger
	private static final Logger logger = Logger.getLogger(BitacoraRestController.class);

	public void guardarBitacora(ConsultaModel consultaModel, CuentasClienteModel cuentasClienteModel) {

		BitacoraModel model = new BitacoraModel();

		try {

			model.setFk_id_consulta_rp(consultaModel);
			model.setI_numero_credito(cuentasClienteModel.getI_credito_id());
			model.setVc_producto_credito(cuentasClienteModel.getVc_producto_credito());
			model.setI_referencia_banco_credito(cuentasClienteModel.getI_referencia_credito());
			model.setVc_nombre_banco_credito(cuentasClienteModel.getVc_nombre_institucion());
			bitacoraService.save(model);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error: " + e.getMessage());
		}
	}

}
