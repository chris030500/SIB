package ahorrosbienestar.sib.std.CRM.controller;

import static ahorrosbienestar.sib.std.comunes.Constantes.CATALOGO_CRM;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ahorrosbienestar.sib.std.CRM.model.precrmCarga;
import ahorrosbienestar.sib.std.CRM.model.precrmCargaDetalle;
import ahorrosbienestar.sib.std.CRM.model.precrmGestion;
import ahorrosbienestar.sib.std.CRM.model.precrmHistorialEstatus;
import ahorrosbienestar.sib.std.CRM.Service.precrmService;
import ahorrosbienestar.sib.std.CRM.model.precrmSospechoso;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import ahorrosbienestar.sib.std.CRM.model.precrmSospechoso;
import ahorrosbienestar.sib.std.CRM.model.precrmHistorialEstatus;


@Controller
@RequestMapping("/precrm")
public class precrmController {

    private final precrmService precrmService;


    public precrmController(
            precrmService precrmService) {

        System.out.println(
            "=========================================="
        );

        System.out.println(
            ">>> PRECRM CONTROLLER NUEVO 11-09-2026 <<<"
        );

        System.out.println(
            "=========================================="
        );

        this.precrmService = precrmService;
    }


    // =========================================================
    // MOSTRAR PRE CRM
    // DETECTAR Y PROCESAR ARCHIVO AUTOMATICAMENTE
    // =========================================================

    @GetMapping
    public String mostrarPRECRM(
            Model model,
            Principal principal) {

        model.addAttribute(
                "catalogo",
                CATALOGO_CRM
        );

        String usuario =
                principal != null
                ? principal.getName()
                : "SISTEMA";

        try {

            Path archivo =
                    precrmService
                        .obtenerArchivoMasReciente();


            if (archivo != null) {

                String nombreArchivo =
                        archivo.getFileName()
                               .toString();


                LocalDateTime fechaArchivo =
                        Files
                            .getLastModifiedTime(archivo)
                            .toInstant()
                            .atZone(
                                ZoneId.systemDefault()
                            )
                            .toLocalDateTime();


                DateTimeFormatter formato =
                        DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy HH:mm"
                        );


                model.addAttribute(
                        "archivoEncontrado",
                        true
                );

                model.addAttribute(
                        "nombreArchivo",
                        nombreArchivo
                );

                model.addAttribute(
                        "fechaArchivo",
                        fechaArchivo.format(formato)
                );


                // =============================================
                // PROCESAR Y REGISTRAR EN BD
                // =============================================

                try {

                    precrmService.procesarArchivo(
                            archivo,
                            usuario
                    );

                } catch (Exception e) {

                    model.addAttribute(
                            "mensajeProcesamiento",
                            "No fue posible procesar el archivo: "
                            + e.getMessage()
                    );

                    e.printStackTrace();
                }

            } else {

                model.addAttribute(
                        "archivoEncontrado",
                        false
                );

                model.addAttribute(
                        "mensajeArchivo",
                        "No se encontró un archivo PRECRM para procesar."
                );
            }


        } catch (Exception e) {

            model.addAttribute(
                    "archivoEncontrado",
                    false
            );

            model.addAttribute(
                    "mensajeArchivo",
                    "No fue posible consultar los archivos de PRE-CRM."
            );

            e.printStackTrace();
        }

     // =========================================================
     // RESULTADO DEL ULTIMO PROCESAMIENTO
     // =========================================================

        precrmCarga ultimaCarga =
             precrmService.obtenerUltimaCarga();


     if (ultimaCarga != null) {

    	 model.addAttribute(
    			    "totalEncontrados",
    			    ultimaCarga.getTotalEncontrados()
    			);

    			model.addAttribute(
    			    "totalCargados",
    			    ultimaCarga.getTotalCargados()
    			);

    			model.addAttribute(
    			    "totalDuplicados",
    			    ultimaCarga.getTotalDuplicados()
    			);

    			model.addAttribute(
    				    "totalErrores",
    				    ultimaCarga.getTotalErrores()
    				);


         if (ultimaCarga.getFechaProcesamiento() != null) {

             DateTimeFormatter formato =
                     DateTimeFormatter.ofPattern(
                         "dd/MM/yyyy HH:mm"
                     );

             model.addAttribute(
                 "ultimoProcesamiento",
                 ultimaCarga
                     .getFechaProcesamiento()
                     .format(formato)
             );
         }


     } else {

         model.addAttribute(
             "totalEncontrados",
             0
         );

         model.addAttribute(
             "totalCargados",
             0
         );

         model.addAttribute(
             "totalDuplicados",
             0
         );

         model.addAttribute(
             "totalErrores",
             0
         );

         model.addAttribute(
             "totalNoViables",
             0
         );
     }
     
  // =========================================================
  // DETALLE DE INCIDENCIAS
  // =========================================================

  List<precrmCargaDetalle> incidencias =
          precrmService
              .obtenerIncidenciasUltimoProcesamiento();


  model.addAttribute(
      "incidencias",
      incidencias
  );

  return "CRM/precrm";
    }


 // =========================================================
 // PANTALLA SEGUIMIENTO DE SOSPECHOSOS
 // =========================================================

    @GetMapping("/seguimientosospechosos")
 public String mostrarSeguimiento(

         @RequestParam(
             required = false
         )
         String folio,

         @RequestParam(
             required = false
         )
         String nombre,

         @RequestParam(
             required = false
         )
         String telefono,

         @RequestParam(
             required = false
         )
         String clasificacion,

         @RequestParam(
             required = false
         )
         String estatus,

         @RequestParam(
             required = false
         )
         @DateTimeFormat(
             iso = DateTimeFormat.ISO.DATE
         )
         LocalDate fechaRegistro,

         Model model) {


     model.addAttribute(
         "catalogo",
         CATALOGO_CRM
     );


     // =====================================================
     // BUSCAR SOSPECHOSOS
     // =====================================================

     List<precrmSospechoso> sospechosos =
             precrmService
                 .buscarSospechosos(

                     folio,

                     nombre,

                     telefono,

                     clasificacion,

                     estatus,

                     fechaRegistro
                 );


     model.addAttribute(
         "sospechosos",
         sospechosos
     );


     // =====================================================
     // CONSERVAR FILTROS EN PANTALLA
     // =====================================================

     model.addAttribute(
         "filtroFolio",
         folio
     );


     model.addAttribute(
         "filtroNombre",
         nombre
     );


     model.addAttribute(
         "filtroTelefono",
         telefono
     );


     model.addAttribute(
         "filtroClasificacion",
         clasificacion
     );


     model.addAttribute(
         "filtroEstatus",
         estatus
     );


     model.addAttribute(
         "filtroFechaRegistro",
         fechaRegistro
     );


     // =====================================================
     // RESUMEN DEL EMBUDO
     // POR AHORA SIGUE SIENDO GENERAL
     // =====================================================

     model.addAttribute(
         "totalRegistrados",
         precrmService
             .contarSospechososPorEstatus(
                 "REGISTRADO"
             )
     );


     model.addAttribute(
         "totalSeguimiento",
         precrmService
             .contarSospechososPorEstatus(
                 "SEGUIMIENTO"
             )
     );


     model.addAttribute(
         "totalCitaAgendada",
         precrmService
             .contarSospechososPorEstatus(
                 "CITA_AGENDADA"
             )
     );


     model.addAttribute(
         "totalCotizacion",
         precrmService
             .contarSospechososPorEstatus(
                 "COTIZACION"
             )
     );


     model.addAttribute(
         "totalSolicitud",
         precrmService
             .contarSospechososPorEstatus(
                 "SOLICITUD"
             )
     );


     model.addAttribute(
         "totalConcluidos",
         precrmService
             .contarSospechososPorEstatus(
                 "CONCLUIDO"
             )
     );


     return "CRM/precrmSeguimientoSos";
 }
    // =========================================================
    // SUBIR ARCHIVO MANUALMENTE
    // =========================================================

    @PostMapping("/subirArchivo")
    public String subirArchivo(
            @RequestParam("file")
            MultipartFile archivo,
            RedirectAttributes redirectAttributes) {

        try {

            // Solo colocamos el archivo en Públicos
            precrmService.guardarArchivo(
                    archivo
            );


            redirectAttributes
                .addFlashAttribute(
                    "mensajeExito",
                    "El archivo se cargó correctamente."
                );


        } catch (Exception e) {

            redirectAttributes
                .addFlashAttribute(
                    "mensajeError",
                    e.getMessage()
                );

            e.printStackTrace();
        }


        /*
         * Al regresar a /precrm:
         *
         * Detecta el archivo.
         * Lo lee.
         * Lo procesa.
         * Lo registra en BD.
         */
        return "redirect:/precrm";
    }
    
    // =========================================================
    // ACTUALIZAR ESTATUS DESDE DETALLE DEL SOSPECHOSO
    // =========================================================
    
    @PostMapping("/detalle/actualizarEstatus")
    public String actualizarEstatusDesdeDetalle(

            @RequestParam("pkSospechoso")
            Long pkSospechoso,

            @RequestParam("estatus")
            String estatus,

            Principal principal,

            RedirectAttributes redirectAttributes) {


        try {


            String usuario =
                    principal != null
                    ? principal.getName()
                    : "SISTEMA";


            precrmService
                .actualizarEstatusSospechoso(
                    pkSospechoso,
                    estatus,
                    usuario,
                    "DETALLE"
                );


            redirectAttributes
                .addFlashAttribute(
                    "mensajeExito",
                    "El sospechoso avanzó correctamente "
                    + "a la siguiente etapa."
                );


        } catch (Exception e) {


            redirectAttributes
                .addFlashAttribute(
                    "mensajeError",
                    e.getMessage()
                );

        }


        return "redirect:/precrm/detallesospechosos/"
                + pkSospechoso;
    }
    
 // =========================================================
 // DETALLE DEL SOSPECHOSO
 // ACCESO DESDE ACCESOS RAPIDOS
 // =========================================================

    @GetMapping("/detallesospechosos")
 public String mostrarDetalleSospechoso(
         Model model) {


     model.addAttribute(
         "catalogo",
         CATALOGO_CRM
     );


     // No hay sospechoso seleccionado todavía

     model.addAttribute(
         "sospechoso",
         null
     );


     // Lista para poder seleccionar uno
     // desde el acceso rápido

     model.addAttribute(
         "sospechosos",
         precrmService
             .obtenerSospechososActivos()
     );


     return "CRM/precrmDetalleSos";
 }
 
//=========================================================
//DETALLE DEL SOSPECHOSO
//ACCESO DESDE LA TABLA
//=========================================================
    @GetMapping("/detallesospechosos/{pkSospechoso}")
 public String mostrarDetalleSospechosoSeleccionado(

         @PathVariable("pkSospechoso")
         Long pkSospechoso,

         Model model) {


	 precrmSospechoso sospechoso =
		        precrmService
		            .obtenerSospechosoPorId(
		                pkSospechoso
		            );


		String siguienteEstatus =
		        precrmService
		            .obtenerSiguienteEstatusPantalla(
		                sospechoso.getEstatus()
		            );


		List<precrmGestion> gestiones =
		        precrmService
		            .obtenerGestionesSospechoso(
		                pkSospechoso
		            );
		
		List<precrmHistorialEstatus> historialEstatus =
		        precrmService
		            .obtenerHistorialEstatusSospechoso(
		                pkSospechoso
		            );


		model.addAttribute(
		    "sospechoso",
		    sospechoso
		);


		model.addAttribute(
		    "siguienteEstatus",
		    siguienteEstatus
		);


		model.addAttribute(
		    "gestiones",
		    gestiones
		);
		
		model.addAttribute(
			    "historialEstatus",
			    historialEstatus
			);


     model.addAttribute(
         "sospechosos",
         precrmService
             .obtenerSospechososActivos()
     );


     return "CRM/precrmDetalleSos";
 }
    
 // =========================================================
 // GUARDAR TELEFONO ALTERNO DEL SOSPECHOSO
 // =========================================================

 // =========================================================
 // GUARDAR TELEFONO ALTERNO DEL SOSPECHOSO
 // =========================================================

 @PostMapping("/detalle/telefonoAlterno")
 public String guardarTelefonoAlterno(
         @RequestParam("pkSospechoso")
         Long pkSospechoso,

         @RequestParam("telefonoAlterno")
         String telefonoAlterno,

         RedirectAttributes redirectAttributes) {

     try {

         // Quitar espacios al inicio y final
         telefonoAlterno =
             telefonoAlterno != null
                 ? telefonoAlterno.trim()
                 : "";


         // VALIDAR VACIO
         if (telefonoAlterno.isEmpty()) {

             redirectAttributes.addFlashAttribute(
                 "errorTelefonoAlterno",
                 "Debe ingresar un teléfono alterno."
             );

             return "redirect:/precrm/detallesospechosos/"
                     + pkSospechoso;
         }


         // VALIDAR LONGITUD
         if (telefonoAlterno.length() > 20) {

             redirectAttributes.addFlashAttribute(
                 "errorTelefonoAlterno",
                 "El teléfono alterno no puede exceder 20 caracteres."
             );

             return "redirect:/precrm/detallesospechosos/"
                     + pkSospechoso;
         }


         // GUARDAR
         precrmService.actualizarTelefonoAlterno(
             pkSospechoso,
             telefonoAlterno
         );


         redirectAttributes.addFlashAttribute(
             "exitoTelefonoAlterno",
             "Teléfono alterno registrado correctamente."
         );


     } catch (Exception e) {

         // NO MOSTRAR EL ERROR SQL COMPLETO
         redirectAttributes.addFlashAttribute(
             "errorTelefonoAlterno",
             "No fue posible guardar el teléfono alterno."
         );
     }


     return "redirect:/precrm/detallesospechosos/"
             + pkSospechoso;
 }
 
//=========================================================
//GUARDAR GESTION DEL SOSPECHOSO
//=========================================================

@PostMapping("/detalle/guardarGestion")
public String guardarGestion(

      @RequestParam("pkSospechoso")
      Long pkSospechoso,

      @RequestParam("tipoGestion")
      String tipoGestion,

      @RequestParam("comentario")
      String comentario,

      @RequestParam(
          value = "fechaProximoSeguimiento",
          required = false
      )
      @DateTimeFormat(
          iso = DateTimeFormat.ISO.DATE_TIME
      )
      LocalDateTime fechaProximoSeguimiento,

      @RequestParam(
          value = "fechaCita",
          required = false
      )
      @DateTimeFormat(
          iso = DateTimeFormat.ISO.DATE_TIME
      )
      LocalDateTime fechaCita,

      Principal principal,

      RedirectAttributes redirectAttributes) {


  try {


      String usuario =
              principal != null
              ? principal.getName()
              : "SISTEMA";


      precrmService
          .guardarGestion(

              pkSospechoso,

              tipoGestion,

              comentario,

              fechaProximoSeguimiento,

              fechaCita,

              usuario
          );


      redirectAttributes
          .addFlashAttribute(
              "mensajeExito",
              "La gestión se registró correctamente."
          );


  } catch (Exception e) {


      redirectAttributes
          .addFlashAttribute(
              "mensajeError",
              e.getMessage()
          );

  }


  return "redirect:/precrm/detallesospechosos/"
          + pkSospechoso;
}

}