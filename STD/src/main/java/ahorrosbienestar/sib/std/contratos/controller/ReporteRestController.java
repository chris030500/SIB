package ahorrosbienestar.sib.std.contratos.controller;

import java.util.List;

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
	
	@Autowired
	private IReporteContratoRepository iReporteContratoRepository;
	
	@Autowired
	private ContratoService contratoService;
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@GetMapping("/buscaDatos/{solicitud}")
    public List<ValidaSolicitudDto> obtSolicitud(@PathVariable int solicitud, Model model){
		try {
			
	        return coreBancarioService.validaSolicitud(solicitud);
		} catch (Exception e) {
			System.out.print(""+e.getMessage());
		}
		return null;

    }
	
	@GetMapping("/buscaCliente/{solicitud}")
    public List<ParticipantesDto> obtParticipantes(@PathVariable int solicitud, Model model){
		try {
			
	        return coreBancarioService.traeParticipantes(solicitud);
		} catch (Exception e) {
			System.out.print(""+e.getMessage());
		}
		return null;
    }
	
	@GetMapping("/contratoSTD/{id}/{solicitud}")
    public ResponseEntity<Resource> contratoSTD(@PathVariable int id,@PathVariable int solicitud, Model model){
		try {
	        return this.iReporteContratoRepository.export(id,solicitud);
		} catch (Exception e) {
			System.out.print(""+e.getMessage());
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
    		Model model){
		  		
		   Contrato contrato = new Contrato();
           contrato.setVc_nombre_cliente(nombre);
           contrato.setVc_direccion(direccion);
           contrato.setD_capital_otorgado(capital);
           contrato.setI_tasa_intereses(tasaOrdinaria);
           contrato.setI_tasa_moratoria(tasaMoratoria);
           contrato.setD_cat(cat);
           contrato.setV_reca(reca);
           contrato.setI_no_solicitud(solicitud);
		
           return ResponseEntity.ok(contratoService.save(contrato));
	}
	
}
