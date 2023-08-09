package ahorrosbienestar.sib.std.ofertasAutomaticas.index.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.service.ClasificacionBasesService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("/ofertasAutomaticas")
public class indexOfertasAutoController {

	private static final Logger logger = Logger.getLogger(indexOfertasAutoController.class);

	// Constante Nomina
	private final static int NOMINA = 1;

	// Constantes Fidelidad
	private final static int FIDELIDAD = 2;

	// Constantes Campañas VIP
	private final static int CAMPANAS_VIP = 3;
	private final static int MENSAJE_CAMPANA_VIP = 68;

	@Autowired
	ConfigService configService;

	@Autowired
	BasesService basesService;

	@Autowired
	ClasificacionBasesService clasificacionBasesService;

	@GetMapping("/index")
	public String indexOfertasAuto(Model model) throws IOException {
		logger.info("Ejecutando ofertasAutomaticas");
		boolean mensajeAuto = false;
		Optional<Bases> fidelidad = basesService.findById(FIDELIDAD);
		Optional<Bases> campanaVIP = basesService.findById(CAMPANAS_VIP);
		Configuracion mensajeCampana = configService.obtenerConfiguracion(MENSAJE_CAMPANA_VIP);

		List<Bases> bases = basesService.findAll();
		List<ClasificacionBases> clasificacionBases = clasificacionBasesService.findAll();

		model.addAttribute("dComienzoProceso", fidelidad.get().getVc_dia_inicio_proceso());
		model.addAttribute("dFinalProceso", fidelidad.get().getVc_dia_fin_proceso());
		model.addAttribute("hComienzoProceso", fidelidad.get().getVc_hora_inicio_proceso());
		model.addAttribute("hFinalProceso", fidelidad.get().getVc_hora_fin_proceso());
		model.addAttribute("mPeriodoEjecucion", fidelidad.get().getVc_periodo_ejecucion_proceso());
		model.addAttribute("rutaArchivo", fidelidad.get().getVc_ruta_proceso());

		model.addAttribute("dComienzoCampana", campanaVIP.get().getVc_dia_inicio_proceso());
		model.addAttribute("dFinalCampana", campanaVIP.get().getVc_dia_fin_proceso());
		model.addAttribute("hComienzoCampana", campanaVIP.get().getVc_hora_inicio_proceso());
		model.addAttribute("hFinalCampana", campanaVIP.get().getVc_hora_fin_proceso());
		model.addAttribute("mPeriodoCampana", campanaVIP.get().getVc_periodo_ejecucion_proceso());
		if (campanaVIP.get().getI_mensaje_proceso() == 1) {
			mensajeAuto = true;
		} else {
			mensajeAuto = false;
		}
		model.addAttribute("habilitarMensajeCampana", mensajeAuto);
		model.addAttribute("mensajeCampana", mensajeCampana.getVc_valor_propiedad());

		model.addAttribute("bases", bases);
		model.addAttribute("clasificacionBases", clasificacionBases);
		return "ofertasAutomaticas/indexOfertasAutomaticas";
	}

	@PostMapping("/guardarParamFidelidad")
	public String guardarParametrosFidelidad(@RequestParam("dComienzoProceso") String dComienzoProceso,
			@RequestParam("dFinalProceso") String dFinalProceso,
			@RequestParam("hComienzoProceso") String hComienzoProceso,
			@RequestParam("hFinalProceso") String hFinalProceso,
			@RequestParam("mPeriodoEjecucion") String mPeriodoEjecucion,
			@RequestParam("rutaArchivo") String rutaArchivo) {

		try {
			logger.info("Ejecutando guardarParamFidelidad");
			// Buscamos por ID en bases para validar que exista el registro
			Optional<Bases> baseFidelidadOptional = basesService.findById(FIDELIDAD);

			// Si existe algún valor realiza la actualizacion
			if (baseFidelidadOptional.isPresent()) {
				Bases baseFidelidad = baseFidelidadOptional.get();
				baseFidelidad.setVc_dia_inicio_proceso(dComienzoProceso);
				baseFidelidad.setVc_dia_fin_proceso(dFinalProceso);
				baseFidelidad.setVc_hora_inicio_proceso(hComienzoProceso);
				baseFidelidad.setVc_hora_fin_proceso(hFinalProceso);
				baseFidelidad.setVc_periodo_ejecucion_proceso(mPeriodoEjecucion);
				baseFidelidad.setVc_ruta_proceso(rutaArchivo);
				basesService.actualizarUsuario(baseFidelidad);
			}

			return "redirect:/ofertasAutomaticas/index";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@PostMapping("/guardarParamCampanaVIP")
	public String guardarParamCampanaVIP(@RequestParam("dComienzoCampana") String dComienzoCampana,
			@RequestParam("dFinalCampana") String dFinalCampana,
			@RequestParam("hComienzoCampana") String hComienzoCampana,
			@RequestParam("hFinalCampana") String hFinalCampana,
			@RequestParam("mPeriodoCampana") String mPeriodoCampana) {

		try {
			logger.info("Ejecutando guardarParamCampanaVIP");

			// Buscamos por ID en bases para validar que exista el registro
			Optional<Bases> baseCampanaOptional = basesService.findById(CAMPANAS_VIP);

			// Si existe algún valor realiza la actualizacion
			if (baseCampanaOptional.isPresent()) {
				Bases baseCampana = baseCampanaOptional.get();
				baseCampana.setVc_dia_inicio_proceso(dComienzoCampana);
				baseCampana.setVc_dia_fin_proceso(dFinalCampana);
				baseCampana.setVc_hora_inicio_proceso(hComienzoCampana);
				baseCampana.setVc_hora_fin_proceso(hFinalCampana);
				baseCampana.setVc_periodo_ejecucion_proceso(mPeriodoCampana);
				basesService.actualizarUsuario(baseCampana);
			}

			return "redirect:/ofertasAutomaticas/index";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@PostMapping("/guardarClasificacion")
	public String guardarClasificacion(@RequestParam("base") Integer base,
			@RequestParam("nomClasificacion") String nomClasificacion, @RequestParam("abrevacion") String abrevacion,
			@RequestParam("codigoClasificacion") String codigoClasificacion,
			@RequestParam("vigenciaClasificacion") Integer vigenciaClasificacion,
			@RequestParam("mensajeClasificacion") String mensajeClasificacion,
			@RequestParam("confMensaje") boolean confMensaje, 
			@RequestParam(name = "id", required = false) Integer id) {

		try {
			logger.info("Ejecutando guardarClasificacion");
			System.out.println("id: " + id);
			Integer integerValue = confMensaje ? 1 : 0;
			LocalDateTime localDateTime = LocalDateTime.now();

			Optional<Bases> tipoBase = basesService.findById(base);

			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
			// HH:mm:ss");
			System.out.println("base: " + base);
			System.out.println("nomClasificacion: " + nomClasificacion.toUpperCase());
			System.out.println("abrevacion: " + abrevacion.toUpperCase());
			System.out.println("codigoClasificacion: " + codigoClasificacion.toUpperCase());
			System.out.println("vigenciaClasificacion: " + vigenciaClasificacion);
			System.out.println("mensajeClasificacion: " + mensajeClasificacion);
			System.out.println("integerValue: " + integerValue);
			System.out.println("localDateTime: " + localDateTime);

			if (id != null) {
				System.out.println("Entro en id!=null");
				Optional<ClasificacionBases> clasificacionExistente = clasificacionBasesService.findById(id);
				System.out.println("clasificacionExistente: " + clasificacionExistente.get().getPk_id_clasificacion());
				if (clasificacionExistente.isPresent()) {
					ClasificacionBases clasificacionBases = clasificacionExistente.get();
					clasificacionBases.setVc_nombre_clasificacion(nomClasificacion.toUpperCase());
					clasificacionBases.setVc_abreviacion_clasificacion(abrevacion.toUpperCase());
					clasificacionBases.setVc_codigo_clasificacion(codigoClasificacion.toUpperCase());
					clasificacionBases.setI_vigencia_clasificacion(vigenciaClasificacion);
					clasificacionBases.setI_configuracion_mensaje(integerValue);
					clasificacionBases.setVc_mensaje_clasificacion(mensajeClasificacion);
					clasificacionBases.setFk_id_base(tipoBase.orElseThrow());

					clasificacionBasesService.actualizarClasificacion(clasificacionBases);

					System.out.println("Nombre Base: " + tipoBase.get().getVc_nombre_base());
					return "redirect:/ofertasAutomaticas/index";
				} else {
					// Manejar el escenario donde el ID no corresponde a un registro existente
					return "El ID de clasificación no es válido.";
				}
			} else {
				ClasificacionBases clasificacionBases = new ClasificacionBases();
				clasificacionBases.setVc_nombre_clasificacion(nomClasificacion.toUpperCase());
				clasificacionBases.setVc_abreviacion_clasificacion(abrevacion.toUpperCase());
				clasificacionBases.setVc_codigo_clasificacion(codigoClasificacion.toUpperCase());
				clasificacionBases.setI_vigencia_clasificacion(vigenciaClasificacion);
				clasificacionBases.setDt_fecha_registro(localDateTime);
				clasificacionBases.setI_configuracion_mensaje(integerValue);
				clasificacionBases.setI_estatus_clasificacion(1);
				clasificacionBases.setVc_mensaje_clasificacion(mensajeClasificacion);
				clasificacionBases.setFk_id_base(tipoBase.orElseThrow());

				clasificacionBasesService.save(clasificacionBases);

				System.out.println("Nombre Base: " + tipoBase.get().getVc_nombre_base());
				return "redirect:/ofertasAutomaticas/index";
			}

		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
