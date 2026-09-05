package ahorrosbienestar.sib.std.CRM.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PRECRMService {

    @Value("${precrm.ruta-archivos}")
    private String rutaArchivos;


    public Path obtenerArchivoMasReciente() throws IOException {

        Path carpeta = Paths.get(rutaArchivos);


        // =====================================================
        // VALIDACION DE LA RUTA
        // =====================================================

        System.out.println("======================================");
        System.out.println("RUTA PRECRM: " + rutaArchivos);
        System.out.println("PATH: " + carpeta);
        System.out.println("EXISTE: " + Files.exists(carpeta));
        System.out.println("ES DIRECTORIO: " + Files.isDirectory(carpeta));
        System.out.println("======================================");


        if (!Files.exists(carpeta)) {

            throw new IOException(
                "La carpeta configurada para PRE-CRM no existe: "
                + rutaArchivos
            );
        }


        if (!Files.isDirectory(carpeta)) {

            throw new IOException(
                "La ruta configurada para PRE-CRM no corresponde a una carpeta."
            );
        }


        // =====================================================
        // BUSQUEDA DE ARCHIVOS
        // =====================================================

        try (Stream<Path> archivos = Files.list(carpeta)) {

            Optional<Path> archivoMasReciente = archivos

                // Solo archivos, no carpetas
                .filter(Files::isRegularFile)

                // Validar nombre
                .filter(path -> {

                    String nombreOriginal =
                            path.getFileName().toString();

                    String nombre =
                            nombreOriginal.trim().toUpperCase();


                    boolean coincide =
                            nombre.startsWith("PRECRM_")
                            && nombre.endsWith(".XLSX");


                    System.out.println("--------------------------------");
                    System.out.println(
                        "Archivo encontrado en carpeta: ["
                        + nombreOriginal
                        + "]"
                    );

                    System.out.println(
                        "Nombre convertido: ["
                        + nombre
                        + "]"
                    );

                    System.out.println(
                        "Coincide con PRECRM: "
                        + coincide
                    );


                    return coincide;
                })


                // Obtener el archivo más reciente
                .max(
                    Comparator.comparing(path -> {

                        try {

                            return Files.getLastModifiedTime(path);

                        } catch (IOException e) {

                            return FileTime.fromMillis(0);
                        }

                    })
                );


            // =====================================================
            // RESULTADO
            // =====================================================

            if (archivoMasReciente.isPresent()) {

                System.out.println("======================================");
                System.out.println(
                    "ARCHIVO PRECRM SELECCIONADO: "
                    + archivoMasReciente.get().getFileName()
                );
                System.out.println("======================================");

            } else {

                System.out.println("======================================");
                System.out.println(
                    "NO SE ENCONTRO NINGUN ARCHIVO PRECRM"
                );
                System.out.println("======================================");
            }


            return archivoMasReciente.orElse(null);
        }
    }
}