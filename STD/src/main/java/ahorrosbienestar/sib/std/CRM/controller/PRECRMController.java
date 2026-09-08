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
import ahorrosbienestar.sib.std.CRM.model.PRECRMCarga;
import ahorrosbienestar.sib.std.CRM.model.PRECRMCargaDetalle;

import ahorrosbienestar.sib.std.CRM.Service.PRECRMService;
import ahorrosbienestar.sib.std.CRM.model.PRECRMSospechoso;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import ahorrosbienestar.sib.std.CRM.model.PRECRMSospechoso;


@Controller
@RequestMapping("/precrm")
public class PRECRMController {

    private final PRECRMService precrmService;


    public PRECRMController(
            PRECRMService precrmService) {

        System.out.println(
            ">>> CONSTRUYENDO PRECRMController"
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

     PRECRMCarga ultimaCarga =
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

  List<PRECRMCargaDetalle> incidencias =
          precrmService
              .obtenerIncidenciasUltimoProcesamiento();


  model.addAttribute(
      "incidencias",
      incidencias
  );

        return "CRM/PRECRM";
    }


 // =========================================================
 // PANTALLA SEGUIMIENTO DE SOSPECHOSOS
 // =========================================================

 @GetMapping("/seguimiento")
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

     List<PRECRMSospechoso> sospechosos =
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


     return "CRM/PRECRMSeguimiento";
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
    // Boton acciones
    // =========================================================
    
    @PostMapping("/actualizarEstatus")
    public String actualizarEstatus(

            @RequestParam("pkSospechoso")
            Long pkSospechoso,

            @RequestParam("estatus")
            String estatus,

            RedirectAttributes redirectAttributes) {


        try {

            precrmService
                .actualizarEstatusSospechoso(
                    pkSospechoso,
                    estatus
                );


            redirectAttributes
                .addFlashAttribute(
                    "mensajeExito",
                    "El estatus del sospechoso "
                    + "se actualizó correctamente."
                );


        } catch (Exception e) {

            redirectAttributes
                .addFlashAttribute(
                    "mensajeError",
                    e.getMessage()
                );
        }


        return "redirect:/precrm/seguimiento";
    }
    
    
 // =========================================================
 // DETALLE DEL SOSPECHOSO
 // ACCESO DESDE ACCESOS RAPIDOS
 // =========================================================

 @GetMapping("/detalle")
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


     return "CRM/PRECRMDetalle";
 }
 
//=========================================================
//DETALLE DEL SOSPECHOSO
//ACCESO DESDE LA TABLA
//=========================================================
 @GetMapping("/detalle/{pkSospechoso}")
 public String mostrarDetalleSospechosoSeleccionado(

         @PathVariable("pkSospechoso")
         Long pkSospechoso,

         Model model) {


     PRECRMSospechoso sospechoso =
             precrmService
                 .obtenerSospechosoPorId(
                     pkSospechoso
                 );


     String siguienteEstatus =
             precrmService
                 .obtenerSiguienteEstatusPantalla(
                     sospechoso.getEstatus()
                 );


     model.addAttribute(
         "catalogo",
         CATALOGO_CRM
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
         "sospechosos",
         precrmService
             .obtenerSospechososActivos()
     );


     return "CRM/PRECRMDetalle";
 }

}