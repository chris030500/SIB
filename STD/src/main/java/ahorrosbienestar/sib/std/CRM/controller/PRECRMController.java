package ahorrosbienestar.sib.std.CRM.controller;

import static ahorrosbienestar.sib.std.comunes.Constantes.CATALOGO_CRM;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ahorrosbienestar.sib.std.CRM.model.PRECRMCarga;
import ahorrosbienestar.sib.std.CRM.model.PRECRMCargaDetalle;

import ahorrosbienestar.sib.std.CRM.Service.PRECRMService;


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

}