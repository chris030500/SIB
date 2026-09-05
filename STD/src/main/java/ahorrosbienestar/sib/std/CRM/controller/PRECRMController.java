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
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.CRM.Service.PRECRMService;

@Controller
@RequestMapping("/precrm")
public class PRECRMController {

    private final PRECRMService precrmService;


    public PRECRMController(PRECRMService precrmService) {
        this.precrmService = precrmService;
    }


    @GetMapping
    public String mostrarPRECRM(Model model) {

        model.addAttribute("catalogo", CATALOGO_CRM);

        try {

            // Buscar archivo PRECRM más reciente
            Path archivo = precrmService.obtenerArchivoMasReciente();

            if (archivo != null) {

                // Nombre del archivo
                String nombreArchivo =
                        archivo.getFileName().toString();


                // Fecha de última modificación del archivo
                LocalDateTime fechaArchivo =
                        Files.getLastModifiedTime(archivo)
                             .toInstant()
                             .atZone(ZoneId.systemDefault())
                             .toLocalDateTime();


                DateTimeFormatter formato =
                        DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy HH:mm"
                        );


                // Información para la pantalla
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

        } catch (Exception e) {

            model.addAttribute(
                "archivoEncontrado",
                false
            );

            model.addAttribute(
                "mensajeArchivo",
                "Error al consultar PRE-CRM: " + e.getMessage()
            );

            e.printStackTrace();
        }


        return "CRM/PRECRM";
    }

}