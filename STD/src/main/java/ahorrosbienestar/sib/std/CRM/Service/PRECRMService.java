package ahorrosbienestar.sib.std.CRM.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PRECRMService {

    @Value("${precrm.ruta-archivos}")
    private String rutaArchivos;


    // =========================================================
    // OBTENER ARCHIVO PRECRM MAS RECIENTE
    // =========================================================

    public Path obtenerArchivoMasReciente() throws IOException {

        Path carpeta = obtenerCarpetaPRECRM();

        try (Stream<Path> archivos = Files.list(carpeta)) {

            Optional<Path> archivoMasReciente = archivos

                // Solo archivos
                .filter(Files::isRegularFile)

                // Solo archivos con formato:
                // PRECRM_DDMMYYYY.xlsx
                .filter(path -> {

                    String nombre =
                            path.getFileName()
                                .toString()
                                .trim()
                                .toUpperCase();

                    return nombre.matches(
                            "PRECRM_\\d{8}\\.XLSX"
                    );
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

            return archivoMasReciente.orElse(null);
        }
    }


    // =========================================================
    // GUARDAR ARCHIVO PRECRM
    // =========================================================

    public Path guardarArchivo(
            MultipartFile archivo) throws IOException {

        // Validar que se haya seleccionado archivo
        if (archivo == null || archivo.isEmpty()) {

            throw new IOException(
                "No se seleccionó ningún archivo."
            );
        }


        String nombreOriginal =
                archivo.getOriginalFilename();


        if (nombreOriginal == null ||
            nombreOriginal.trim().isEmpty()) {

            throw new IOException(
                "No fue posible obtener el nombre del archivo."
            );
        }


        // Obtener únicamente el nombre
        String nombreArchivo =
                Paths.get(nombreOriginal)
                     .getFileName()
                     .toString()
                     .trim();


        String nombreValidacion =
                nombreArchivo.toUpperCase();


        // =====================================================
        // VALIDAR FORMATO DEL NOMBRE
        // =====================================================

        if (!nombreValidacion.matches(
                "PRECRM_\\d{8}\\.XLSX")) {

            throw new IOException(
                "El archivo debe tener el formato "
                + "PRECRM_DDMMYYYY.xlsx"
            );
        }


        Path carpeta =
                obtenerCarpetaPRECRM();


        Path destino =
                carpeta.resolve(nombreArchivo);


        // =====================================================
        // VALIDAR QUE NO EXISTA
        // =====================================================

        if (Files.exists(destino)) {

            throw new IOException(
                "El archivo "
                + nombreArchivo
                + " ya existe."
            );
        }


        // =====================================================
        // GUARDAR ARCHIVO
        // =====================================================

        Files.copy(
            archivo.getInputStream(),
            destino,
            StandardCopyOption.REPLACE_EXISTING
        );


        return destino;
    }


    // =========================================================
    // ULTIMO ARCHIVO PROCESADO
    // =========================================================

    public String obtenerUltimoArchivoProcesado() {

        /*
         * PENDIENTE:
         *
         * Este dato posteriormente se obtendrá de BD.
         *
         * Ejemplo:
         * PRECRM_06092026.xlsx
         */

        return null;
    }


    // =========================================================
    // FECHA DEL ULTIMO PROCESAMIENTO
    // =========================================================

    public String obtenerUltimoProcesamiento() {

        /*
         * PENDIENTE:
         *
         * Este dato posteriormente se obtendrá de BD.
         *
         * Ejemplo:
         * 06/09/2026 15:30
         */

        return null;
    }


    // =========================================================
    // OBTENER Y VALIDAR RUTA PRECRM
    // =========================================================

    private Path obtenerCarpetaPRECRM()
            throws IOException {

        Path carpeta =
                Paths.get(rutaArchivos);


        if (!Files.exists(carpeta)) {

            throw new IOException(
                "La ruta configurada para PRE-CRM no existe: "
                + rutaArchivos
            );
        }


        if (!Files.isDirectory(carpeta)) {

            throw new IOException(
                "La ruta configurada para PRE-CRM "
                + "no corresponde a un directorio."
            );
        }


        return carpeta;
    }
}