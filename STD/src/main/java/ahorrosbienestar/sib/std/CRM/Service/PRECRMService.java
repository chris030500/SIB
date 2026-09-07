package ahorrosbienestar.sib.std.CRM.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.CRM.dto.PRECRMRegistroDTO;

import ahorrosbienestar.sib.std.CRM.model.PRECRMCarga;
import ahorrosbienestar.sib.std.CRM.model.PRECRMCargaDetalle;

import ahorrosbienestar.sib.std.CRM.Repository.PRECRMCargaRepository;
import ahorrosbienestar.sib.std.CRM.Repository.PRECRMCargaDetalleRepository;


@Service
public class PRECRMService {


    @Value("${precrm.ruta-archivos}")
    private String rutaArchivos;


   private final PRECRMCargaRepository cargaRepository;

   private final PRECRMCargaDetalleRepository detalleRepository;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

   public PRECRMService(
	        PRECRMCargaRepository cargaRepository,
	        PRECRMCargaDetalleRepository detalleRepository) {

	    System.out.println(
	        ">>> CONSTRUYENDO PRECRMService CON AMBOS REPOSITORIES"
	    );

	    this.cargaRepository = cargaRepository;
	    this.detalleRepository = detalleRepository;
	}


    // =========================================================
    // ENCABEZADOS ESPERADOS DEL EXCEL DE FORMS
    // =========================================================

    private static final String[] ENCABEZADOS_PRECRM = {

        "Id",
        "Hora de inicio",
        "Hora de finalización",
        "Correo electrónico",
        "Nombre",
        "Sucursal Registro",
        "Nombre del prospecto (Cliente)",
        "Fecha de nacimiento",
        "Tipo",

        "Teléfono de Contacto (ingrese un número de teléfono de 10 dígitos, sin espacios, guiones ni paréntesis)",

        "Correo electrónico del contacto",
        "Fuente/Origen",
        "Estatus",
        "Producto de interes",

        "Monto de interés (Capture el monto en pesos mexicanos (MXN), sin el signo \"$\" y sin comas)",

        "Observaciones",
        "Nombre1"
    };


    // =========================================================
    // OBTENER ARCHIVO PRECRM MAS RECIENTE
    // =========================================================

    public Path obtenerArchivoMasReciente()
            throws IOException {

        Path carpeta =
                obtenerCarpetaPRECRM();


        try (Stream<Path> archivos =
                Files.list(carpeta)) {


            Optional<Path> archivoMasReciente =
                    archivos

                    .filter(Files::isRegularFile)

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

                    .max(
                        Comparator.comparing(path -> {

                            try {

                                return Files
                                        .getLastModifiedTime(path);

                            } catch (IOException e) {

                                return FileTime
                                        .fromMillis(0);
                            }
                        })
                    );


            return archivoMasReciente
                    .orElse(null);
        }
    }


    // =========================================================
    // GUARDAR ARCHIVO SELECCIONADO
    // =========================================================

    public Path guardarArchivo(
            MultipartFile archivo)
            throws IOException {


        if (archivo == null ||
            archivo.isEmpty()) {

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


        String nombreArchivo =
                Paths.get(nombreOriginal)
                     .getFileName()
                     .toString()
                     .trim();


        String nombreValidacion =
                nombreArchivo.toUpperCase();


        // =====================================================
        // VALIDAR NOMBRE
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


        if (Files.exists(destino)) {

            throw new IOException(
                "El archivo "
                + nombreArchivo
                + " ya existe."
            );
        }


        Files.copy(
            archivo.getInputStream(),
            destino,
            StandardCopyOption.REPLACE_EXISTING
        );


        return destino;
    }


    // =========================================================
    // VALIDAR ESTRUCTURA DEL EXCEL
    // =========================================================

    public void validarEstructuraArchivo(
            Path archivo)
            throws IOException {


        if (archivo == null ||
            !Files.exists(archivo)) {

            throw new IOException(
                "No se encontró el archivo PRE-CRM."
            );
        }


        try (Workbook workbook =
                WorkbookFactory.create(
                    archivo.toFile())) {


            if (workbook.getNumberOfSheets() == 0) {

                throw new IOException(
                    "El archivo PRE-CRM no contiene hojas."
                );
            }


            Sheet hoja =
                    workbook.getSheetAt(0);


            Row filaEncabezados =
                    hoja.getRow(0);


            if (filaEncabezados == null) {

                throw new IOException(
                    "El archivo PRE-CRM no contiene encabezados."
                );
            }


            DataFormatter formatter =
                    new DataFormatter();


            // =================================================
            // VALIDAR NUMERO DE COLUMNAS
            // =================================================

            if (filaEncabezados.getLastCellNum()
                    < ENCABEZADOS_PRECRM.length) {

                throw new IOException(
                    "El archivo PRE-CRM no contiene "
                    + "todas las columnas esperadas."
                );
            }


            // =================================================
            // VALIDAR ENCABEZADOS
            // =================================================

            for (int i = 0;
                 i < ENCABEZADOS_PRECRM.length;
                 i++) {


                Cell celda =
                        filaEncabezados.getCell(i);


                String encabezadoArchivo =
                        celda == null
                        ? ""
                        : formatter
                            .formatCellValue(celda)
                            .trim();


                String encabezadoEsperado =
                        ENCABEZADOS_PRECRM[i];


                if (!encabezadoEsperado.equals(
                        encabezadoArchivo)) {

                    throw new IOException(
                        "Formato de archivo incorrecto. "
                        + "En la columna "
                        + (i + 1)
                        + " se esperaba: '"
                        + encabezadoEsperado
                        + "', pero se encontró: '"
                        + encabezadoArchivo
                        + "'."
                    );
                }
            }
        }
    }


    // =========================================================
    // LEER INFORMACION DEL ARCHIVO
    // =========================================================

    public List<PRECRMRegistroDTO> leerArchivo(
            Path archivo)
            throws IOException {


        validarEstructuraArchivo(archivo);


        List<PRECRMRegistroDTO> registros =
                new ArrayList<>();


        try (Workbook workbook =
                WorkbookFactory.create(
                    archivo.toFile())) {


            Sheet hoja =
                    workbook.getSheetAt(0);


            DataFormatter formatter =
                    new DataFormatter();


            /*
             * Fila 0 = encabezados.
             * Empezamos en fila 1.
             */
            for (int i = 1;
                 i <= hoja.getLastRowNum();
                 i++) {


                Row fila =
                        hoja.getRow(i);


                if (fila == null ||
                    filaEstaVacia(fila)) {

                    continue;
                }


                PRECRMRegistroDTO registro =
                        new PRECRMRegistroDTO();


                /*
                 * Apache POI empieza en 0.
                 * Excel visualmente empieza en 1.
                 */
                registro.setFilaExcel(
                        i + 1
                );


                registro.setId(
                    obtenerValorCelda(
                        fila, 0, formatter
                    )
                );


                registro.setHoraInicio(
                    obtenerValorCelda(
                        fila, 1, formatter
                    )
                );


                registro.setHoraFinalizacion(
                    obtenerValorCelda(
                        fila, 2, formatter
                    )
                );


                registro.setCorreoElectronico(
                    obtenerValorCelda(
                        fila, 3, formatter
                    )
                );


                registro.setNombre(
                    obtenerValorCelda(
                        fila, 4, formatter
                    )
                );


                registro.setSucursalRegistro(
                    obtenerValorCelda(
                        fila, 5, formatter
                    )
                );


                registro.setNombreProspecto(
                    obtenerValorCelda(
                        fila, 6, formatter
                    )
                );


                registro.setFechaNacimiento(
                    obtenerValorCelda(
                        fila, 7, formatter
                    )
                );


                registro.setTipo(
                    obtenerValorCelda(
                        fila, 8, formatter
                    )
                );


                registro.setTelefonoContacto(
                    obtenerValorCelda(
                        fila, 9, formatter
                    )
                );


                registro.setCorreoContacto(
                    obtenerValorCelda(
                        fila, 10, formatter
                    )
                );


                registro.setFuenteOrigen(
                    obtenerValorCelda(
                        fila, 11, formatter
                    )
                );


                registro.setEstatus(
                    obtenerValorCelda(
                        fila, 12, formatter
                    )
                );


                registro.setProductoInteres(
                    obtenerValorCelda(
                        fila, 13, formatter
                    )
                );


                registro.setMontoInteres(
                    obtenerValorCelda(
                        fila, 14, formatter
                    )
                );


                registro.setObservaciones(
                    obtenerValorCelda(
                        fila, 15, formatter
                    )
                );


                registro.setNombre1(
                    obtenerValorCelda(
                        fila, 16, formatter
                    )
                );


                registros.add(registro);
            }
        }


        return registros;
    }


 // =========================================================
 // PROCESAR ARCHIVO - PRUEBA INSERT TBL_PRECRM_CARGA
 // =========================================================

 @Transactional
 public void procesarArchivo(
         Path archivo,
         String usuario)
         throws IOException {

     System.out.println("======================================");
     System.out.println("ENTRO A procesarArchivo()");
     System.out.println("ARCHIVO: " + archivo.getFileName());


     String nombreArchivo =
             archivo.getFileName().toString();


     // =====================================================
     // 1. VALIDAR SI YA EXISTE EN BD
     // =====================================================

     System.out.println(
         "1. ANTES DE existsByNombreArchivo()"
     );


     boolean archivoYaExiste =
             cargaRepository
                 .existsByNombreArchivo(
                     nombreArchivo
                 );


     System.out.println(
         "2. DESPUES DE existsByNombreArchivo()"
     );


     System.out.println(
         "RESULTADO: " + archivoYaExiste
     );


     if (archivoYaExiste) {

         System.out.println(
             "El archivo ya fue registrado previamente."
         );

         System.out.println(
             "FIN DEL PROCESO - NO SE INSERTA NUEVAMENTE"
         );

         return;
     }


     // =====================================================
     // 2. LEER EXCEL
     // =====================================================

     System.out.println(
         "3. Leyendo archivo Excel..."
     );


     List<PRECRMRegistroDTO> registros =
             leerArchivo(archivo);


     System.out.println(
         "4. Excel leído correctamente."
     );


     System.out.println(
         "TOTAL ENCONTRADOS: "
         + registros.size()
     );


     // =====================================================
     // 3. OBTENER FECHA DEL ARCHIVO
     // =====================================================

     LocalDateTime fechaArchivo =
             Files
                 .getLastModifiedTime(archivo)
                 .toInstant()
                 .atZone(
                     ZoneId.systemDefault()
                 )
                 .toLocalDateTime();


     // =====================================================
     // 4. CREAR CABECERA DE CARGA
     // =====================================================

     PRECRMCarga carga =
             new PRECRMCarga();


     carga.setNombreArchivo(
             nombreArchivo
     );


     carga.setFechaArchivo(
             fechaArchivo
     );


     carga.setFechaProcesamiento(
             LocalDateTime.now()
     );


     carga.setTipoCarga(
             "AUTOMATICA"
     );


     carga.setEstatusProceso(
             "EN_PROCESO"
     );


     carga.setTotalEncontrados(
             registros.size()
     );


     carga.setTotalCargados(0);

     carga.setTotalDuplicados(0);

     carga.setTotalErrores(0);

     carga.setTotalNoViables(0);


     carga.setUsuarioProceso(
             usuario
     );


     carga.setFechaRegistro(
             LocalDateTime.now()
     );


     // =====================================================
     // 5. PROBAR INSERT EN TBL_PRECRM_CARGA
     // =====================================================

     System.out.println(
         "5. ANTES DE cargaRepository.saveAndFlush()"
     );


     try {

         carga =
             cargaRepository.saveAndFlush(
                 carga
             );


         System.out.println(
             "6. DESPUES DE cargaRepository.saveAndFlush()"
         );


         System.out.println(
             "PK_CARGA GENERADA: "
             + carga.getPkCarga()
         );


         System.out.println(
             "ARCHIVO INSERTADO CORRECTAMENTE "
             + "EN TBL_PRECRM_CARGA"
         );


     } catch (Exception e) {

         System.err.println(
             "============================================"
         );


         System.err.println(
             "ERROR INSERTANDO TBL_PRECRM_CARGA"
         );


         System.err.println(
             "TIPO ERROR: "
             + e.getClass().getName()
         );


         System.err.println(
             "MENSAJE: "
             + e.getMessage()
         );


         Throwable causa =
                 e.getCause();


         while (causa != null) {

             System.err.println(
                 "CAUSA: "
                 + causa.getClass().getName()
             );


             System.err.println(
                 "MENSAJE CAUSA: "
                 + causa.getMessage()
             );


             causa =
                 causa.getCause();
         }


         System.err.println(
             "============================================"
         );


         throw e;
     }


     System.out.println(
         "===== FIN PRUEBA TBL_PRECRM_CARGA ====="
     );

     System.out.println(
         "======================================"
     );

 } // <-- ESTA LLAVE CIERRA procesarArchivo()

    // =========================================================
    // ANALIZAR ARCHIVO
    // =========================================================

    public int analizarArchivo(
            Path archivo)
            throws IOException {


        List<PRECRMRegistroDTO> registros =
                leerArchivo(archivo);


        return registros.size();
    }


    // =========================================================
    // OBTENER TOTAL DE REGISTROS
    // =========================================================

    public int obtenerTotalRegistros(
            Path archivo)
            throws IOException {


        try (Workbook workbook =
                WorkbookFactory.create(
                    archivo.toFile())) {


            Sheet hoja =
                    workbook.getSheetAt(0);


            int totalRegistros = 0;


            for (int i = 1;
                 i <= hoja.getLastRowNum();
                 i++) {


                Row fila =
                        hoja.getRow(i);


                if (fila != null &&
                    !filaEstaVacia(fila)) {

                    totalRegistros++;
                }
            }


            return totalRegistros;
        }
    }


    // =========================================================
    // OBTENER VALOR DE CELDA
    // =========================================================

    private String obtenerValorCelda(
            Row fila,
            int columna,
            DataFormatter formatter) {


        Cell celda =
                fila.getCell(
                    columna,
                    Row.MissingCellPolicy
                       .RETURN_BLANK_AS_NULL
                );


        if (celda == null) {

            return "";
        }


        return formatter
                .formatCellValue(celda)
                .trim();
    }


    // =========================================================
    // VALIDAR FILA VACIA
    // =========================================================

    private boolean filaEstaVacia(
            Row fila) {


        DataFormatter formatter =
                new DataFormatter();


        for (int i = 0;
             i < ENCABEZADOS_PRECRM.length;
             i++) {


            Cell celda =
                    fila.getCell(i);


            if (celda != null) {

                String valor =
                        formatter
                            .formatCellValue(celda)
                            .trim();


                if (!valor.isEmpty()) {

                    return false;
                }
            }
        }


        return true;
    }


    // =========================================================
    // CONVERTIR FECHA Y HORA
    // =========================================================

    private LocalDateTime convertirFechaHora(
            String valor) {


        if (valor == null ||
            valor.trim().isEmpty()) {

            return null;
        }


        String fecha =
                valor.trim();


        String[] formatos = {

            "dd/MM/yyyy HH:mm:ss",
            "dd/MM/yyyy HH:mm",
            "d/M/yyyy HH:mm:ss",
            "d/M/yyyy HH:mm",
            "MM/dd/yyyy HH:mm:ss",
            "MM/dd/yyyy HH:mm"
        };


        for (String formato : formatos) {

            try {

                return LocalDateTime.parse(
                    fecha,
                    DateTimeFormatter.ofPattern(
                        formato
                    )
                );

            } catch (
                DateTimeParseException e) {

                // Probar siguiente formato
            }
        }


        return null;
    }


    // =========================================================
    // CONVERTIR FECHA
    // =========================================================

    private LocalDate convertirFecha(
            String valor) {


        if (valor == null ||
            valor.trim().isEmpty()) {

            return null;
        }


        String fecha =
                valor.trim();


        String[] formatos = {

            "dd/MM/yyyy",
            "d/M/yyyy",
            "MM/dd/yyyy"
        };


        for (String formato : formatos) {

            try {

                return LocalDate.parse(
                    fecha,
                    DateTimeFormatter.ofPattern(
                        formato
                    )
                );

            } catch (
                DateTimeParseException e) {

                // Probar siguiente formato
            }
        }


        return null;
    }


    // =========================================================
    // CONVERTIR MONTO
    // =========================================================

    private BigDecimal convertirMonto(
            String valor) {


        if (valor == null ||
            valor.trim().isEmpty()) {

            return null;
        }


        try {

            String monto =
                    valor.trim()
                         .replace("$", "")
                         .replace(",", "")
                         .replace(" ", "");


            return new BigDecimal(
                    monto
            );


        } catch (NumberFormatException e) {

            return null;
        }
    }


    // =========================================================
    // ULTIMO ARCHIVO PROCESADO
    // =========================================================

    public String obtenerUltimoArchivoProcesado() {


       /* Optional<PRECRMCarga> ultimaCarga =
                cargaRepository
                    .findTopByOrderByFechaProcesamientoDesc();*/


        /*if (ultimaCarga.isPresent()) {

            return ultimaCarga
                    .get()
                    .getNombreArchivo();
        }*/


        return null;
    }


    // =========================================================
    // FECHA ULTIMO PROCESAMIENTO
    // =========================================================

    public String obtenerUltimoProcesamiento() {


     /*   Optional<PRECRMCarga> ultimaCarga =
                cargaRepository
                    .findTopByOrderByFechaProcesamientoDesc();*/


       /* if (ultimaCarga.isPresent() &&
            ultimaCarga.get()
                       .getFechaProcesamiento()
                       != null) {


            DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy HH:mm"
                    );


            return ultimaCarga
                    .get()
                    .getFechaProcesamiento()
                    .format(formato);
        }*/


        return null;
    }


    // =========================================================
    // OBTENER RUTA PRECRM
    // =========================================================

    private Path obtenerCarpetaPRECRM()
            throws IOException {


        Path carpeta =
                Paths.get(rutaArchivos);


        if (!Files.exists(carpeta)) {

            throw new IOException(
                "La ruta configurada para PRE-CRM "
                + "no existe: "
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