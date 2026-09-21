package ahorrosbienestar.sib.std.contratos.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.contratos.model.Contrato;
import ahorrosbienestar.sib.std.contratos.repository.IReporteContratoRepository;
import ahorrosbienestar.sib.std.contratos.service.ContratoService;
import ahorrosbienestar.sib.std.core_bancario.dto.ParticipantesDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ValidaSolicitudDto;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import groovyjarjarpicocli.CommandLine.Model;

@CrossOrigin
@RestController
@RequestMapping("/contrato/report")
public class ReporteRestController {

	private static final Logger logger = Logger.getLogger(ReporteRestController.class);
	
	@Autowired
	private IReporteContratoRepository iReporteContratoRepository;
	
	@Autowired
	private ContratoService contratoService;
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private ConfigService configService;
	
	
	@GetMapping("/buscaDatos/{solicitud}")
    public List<ValidaSolicitudDto> obtSolicitud(@PathVariable int solicitud, Model model){
		try {
			
	        return coreBancarioService.validaSolicitud(solicitud);
		} catch (Exception e) {
			logger.error("Error inesperado durante la operación.", e);
		}
		return null;

    }
	
	@GetMapping("/buscaCliente/{solicitud}")
    public List<ParticipantesDto> obtParticipantes(@PathVariable int solicitud, Model model){
		try {
			
	        return coreBancarioService.traeParticipantes(solicitud);
		} catch (Exception e) {
			logger.error("Error inesperado durante la operación.", e);
		}
		return null;
    }
	
	@GetMapping("/contratoSTD/{id}/{solicitud}")
    public ResponseEntity<Resource> contratoSTD(@PathVariable int id,@PathVariable int solicitud, Model model){
		try {		
	        return this.iReporteContratoRepository.export(id,solicitud);
		} catch (Exception e) {
			logger.error("Error inesperado durante la operación.", e);
		}
		return null;
    }


	@PostMapping("/registrarDatosCliente")
	//@PostMapping("/registrarDatosCliente/{nombre}/{direccion}/{capital}/{tasaOrdinaria}/{tasaMoratoria}/{cat}/{reca}/{solicitud}")
	public ResponseEntity<?> registrarDatosCliente(
			@RequestParam(name = "nombre") String nombre,
			@RequestParam(name = "direccion") String direccion,
			@RequestParam(name = "capital") Float capital,
			@RequestParam(name = "tasaOrdinaria") Float tasaOrdinaria,
			@RequestParam(name = "tasaMoratoria") Float tasaMoratoria,
			@RequestParam(name = "cat") Float cat,
			@RequestParam(name = "reca") String reca,
			@RequestParam(name = "solicitud") Integer solicitud,
			@RequestParam(name = "fecha") String fecha,
    		Model model){
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLocalDate = LocalDate.parse(fecha, formato);
		  		
		   Contrato contrato = new Contrato();
           contrato.setVc_nombre_cliente(nombre);
           contrato.setVc_direccion(direccion);
           contrato.setD_capital_otorgado(capital);
           contrato.setI_tasa_intereses(tasaOrdinaria);
           contrato.setI_tasa_moratoria(tasaMoratoria);
           contrato.setD_cat(cat);
           contrato.setV_reca(reca);
           contrato.setI_no_solicitud(solicitud);
           contrato.setDt_fecha_registro(fechaLocalDate);
		
           return ResponseEntity.ok(contratoService.save(contrato));
	}
	
	@GetMapping("/mensajeContrato")
	public String mostrarMensaje() {
		Configuracion model = configService.obtenerConfiguracion(53);
		return model.getVc_valor_propiedad();
	}
	
}
