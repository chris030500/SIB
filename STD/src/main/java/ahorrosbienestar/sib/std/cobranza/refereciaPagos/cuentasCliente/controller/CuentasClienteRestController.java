package ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.dto.CuentasClienteDTO;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.dto.DatosBusquedaDTO;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model.CuentasClienteModel;
import ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.service.CuentasClienteService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CuentasClienteRestController {

	@Autowired
	private CuentasClienteService clienteService;

	// Logger
	private static final Logger logger = Logger.getLogger(CuentasClienteRestController.class);

	/**
	 * Obtener las cuentas del cliente
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/obtener_cliente" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> obtenerCliente(@RequestBody DatosBusquedaDTO dto) {

		List<CuentasClienteDTO> respuesta = new ArrayList<CuentasClienteDTO>();
		List<CuentasClienteModel> listaModel = new ArrayList<CuentasClienteModel>();

		try {

			switch (dto.getOpcion()) {
			case 1:
				BigInteger valor = new BigInteger(dto.getValor());
				listaModel = clienteService.clientePorNumCliente(valor);
				respuesta = llenarDTO(listaModel);
				break;
			case 2:
				listaModel = clienteService.clientePorNomCliente(dto.getValor());
				respuesta = llenarDTO(listaModel);
				break;
			case 3:
				BigInteger numeroCredito = new BigInteger(dto.getValor());
				listaModel = clienteService.clientePorNumCredito(numeroCredito);
				respuesta = llenarDTO(listaModel);
				break;
			default:
				break;
			}

			if (respuesta.size() > 0) {
				logger.info("Busqueda: " + dto.getValor());
				logger.info("se encontró el cliente");
			} else {
				logger.info("Busqueda: " + dto.getValor());
				logger.info("no se encontró el cliente");
			}

			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			return ResponseEntity.ok(null);
		}

	}
	
	

	/**
	 * Llenar CuentasClienteDTO
	 * @param lista
	 * @return
	 */
	public List<CuentasClienteDTO> llenarDTO(List<CuentasClienteModel> lista) {

		List<CuentasClienteDTO> respuesta = new ArrayList<CuentasClienteDTO>();

		lista.forEach(model -> {
			CuentasClienteDTO cuentaDTO = new CuentasClienteDTO();
			cuentaDTO.setPk_id_cuenta_rp(String.valueOf(model.getPk_id_cuenta_rp()));
			cuentaDTO.setI_cliente_id(String.valueOf(model.getI_cliente_id()));
			cuentaDTO.setVc_nombre_cliente(model.getVc_nombre_cliente());
			cuentaDTO.setI_credito_id(String.valueOf(model.getI_credito_id()));
			cuentaDTO.setVc_producto_credito(model.getVc_producto_credito());
			cuentaDTO.setDt_fecha_ministrado(model.getDt_fecha_ministrado());
			cuentaDTO.setVc_estatus_credito(model.getVc_estatus_credito());
			cuentaDTO.setI_referencia_credito(String.valueOf(model.getI_referencia_credito()));
			cuentaDTO.setI_cuenta_aho_id(String.valueOf(model.getI_cuenta_aho_id()));
			cuentaDTO.setI_referencia_cta_principal_1(String.valueOf(model.getI_referencia_cta_principal_1()));
			cuentaDTO.setI_referencia_cta_principal_2(String.valueOf(model.getI_referencia_cta_principal_2()));
			cuentaDTO.setI_institucion_id(String.valueOf(model.getI_institucion_id()));
			cuentaDTO.setI_institucion_id(model.getVc_nombre_institucion());
			cuentaDTO.setVc_tipo_referencia(model.getVc_tipo_referencia());
			respuesta.add(cuentaDTO);
		});

		return respuesta;

	}

}
