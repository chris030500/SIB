package ahorrosbienestar.sib.std.CRM.controller;

import static ahorrosbienestar.sib.std.comunes.Constantes.CATALOGO_CRM;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.CRM.Service.PRECRMService;

@Controller
@RequestMapping("/precrm")
public class PRECRMController {

    private final PRECRMService precrmService;


    public PRECRMController(PRECRMService precrmService) {
        this.precrmService = precrmService;
    }


    // =========================================================
    // MOSTRAR PANTALLA PRE CRM
    // =========================================================

    @GetMapping
    public String mostrarPRECRM(Model model) {

        model.addAttribute("catalogo", CATALOGO_CRM);

        try {

            // =====================================================
            // ARCHIVO ACTUAL EN CARPETA
            // =====================================================

            Path archivo =
                    precrmService.obtenerArchivoMasReciente();

            if (archivo != null) {

                String nombreArchivo =
                        archivo.getFileName().toString();

                LocalDateTime fechaArchivo =
                        Files.getLastModifiedTime(archivo)
                             .toInstant()
                             .atZone(ZoneId.systemDefault())
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


            // =====================================================
            // ULTIMO ARCHIVO PROCESADO
            // =====================================================

            String ultimoArchivoProcesado =
                    precrmService.obtenerUltimoArchivoProcesado();

            String ultimoProcesamiento =
                    precrmService.obtenerUltimoProcesamiento();


            model.addAttribute(
                    "ultimoArchivoProcesado",
                    ultimoArchivoProcesado
            );

            model.addAttribute(
                    "ultimoProcesamiento",
                    ultimoProcesamiento
            );


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


        return "CRM/PRECRM";
    }


    // =========================================================
    // SUBIR ARCHIVO PRE CRM
    // =========================================================

    @PostMapping("/subirArchivo")
    public String subirArchivo(
            @RequestParam("file") MultipartFile archivo,
            RedirectAttributes redirectAttributes) {

        try {

            precrmService.guardarArchivo(archivo);


            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    "El archivo se cargó correctamente."
            );


        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    e.getMessage()
            );

            e.printStackTrace();
        }


        return "redirect:/precrm";
    }

}