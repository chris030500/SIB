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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
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

        this.cargaRepository =
                cargaRepository;

        this.detalleRepository =
                detalleRepository;
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

                    .filter(
                        Files::isRegularFile
                    )

                    .filter(path -> {

                        String nombre =
                                path
                                    .getFileName()
                                    .toString()
                                    .trim()
                                    .toUpperCase();


                        return nombre.matches(
                            "PRECRM_\\d{8}\\.XLSX"
                        );
                    })

                    .max(
                        Comparator.comparing(
                            path -> {

                                try {

                                    return Files
                                            .getLastModifiedTime(
                                                path
                                            );

                                } catch (IOException e) {

                                    return FileTime
                                            .fromMillis(0);
                                }
                            }
                        )
                    );


            return archivoMasReciente
                    .orElse(null);
        }
    }


    // =========================================================
    // GUARDAR ARCHIVO SELECCIONADO MANUALMENTE
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
                Paths
                    .get(nombreOriginal)
                    .getFileName()
                    .toString()
                    .trim();


        String nombreValidacion =
                nombreArchivo
                    .toUpperCase();


        // =====================================================
        // VALIDAR NOMBRE DEL ARCHIVO
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
                carpeta.resolve(
                    nombreArchivo
                );


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
                    archivo.toFile()
                )) {


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
                        filaEncabezados
                            .getCell(i);


                String encabezadoArchivo =
                        celda == null
                        ? ""
                        : formatter
                            .formatCellValue(celda)
                            .trim();


                String encabezadoEsperado =
                        ENCABEZADOS_PRECRM[i];


                if (!encabezadoEsperado
                        .equals(
                            encabezadoArchivo
                        )) {

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


        validarEstructuraArchivo(
            archivo
        );


        List<PRECRMRegistroDTO> registros =
                new ArrayList<>();


        try (Workbook workbook =
                WorkbookFactory.create(
                    archivo.toFile()
                )) {


            Sheet hoja =
                    workbook.getSheetAt(0);


            DataFormatter formatter =
                    new DataFormatter();


            /*
             * Fila 0 = encabezados.
             * Datos comienzan en fila 1.
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
                 * Apache POI inicia en 0.
                 * Excel visualmente inicia en 1.
                 */

                registro.setFilaExcel(
                    i + 1
                );


                registro.setId(
                    obtenerValorCelda(
                        fila,
                        0,
                        formatter
                    )
                );


                registro.setHoraInicio(
                    obtenerValorCelda(
                        fila,
                        1,
                        formatter
                    )
                );


                registro.setHoraFinalizacion(
                    obtenerValorCelda(
                        fila,
                        2,
                        formatter
                    )
                );


                registro.setCorreoElectronico(
                    obtenerValorCelda(
                        fila,
                        3,
                        formatter
                    )
                );


                registro.setNombre(
                    obtenerValorCelda(
                        fila,
                        4,
                        formatter
                    )
                );


                registro.setSucursalRegistro(
                    obtenerValorCelda(
                        fila,
                        5,
                        formatter
                    )
                );


                registro.setNombreProspecto(
                    obtenerValorCelda(
                        fila,
                        6,
                        formatter
                    )
                );


                registro.setFechaNacimiento(
                    obtenerValorCelda(
                        fila,
                        7,
                        formatter
                    )
                );


                registro.setTipo(
                    obtenerValorCelda(
                        fila,
                        8,
                        formatter
                    )
                );


                registro.setTelefonoContacto(
                    obtenerValorCelda(
                        fila,
                        9,
                        formatter
                    )
                );


                registro.setCorreoContacto(
                    obtenerValorCelda(
                        fila,
                        10,
                        formatter
                    )
                );


                registro.setFuenteOrigen(
                    obtenerValorCelda(
                        fila,
                        11,
                        formatter
                    )
                );


                registro.setEstatus(
                    obtenerValorCelda(
                        fila,
                        12,
                        formatter
                    )
                );


                registro.setProductoInteres(
                    obtenerValorCelda(
                        fila,
                        13,
                        formatter
                    )
                );


                registro.setMontoInteres(
                    obtenerValorCelda(
                        fila,
                        14,
                        formatter
                    )
                );


                registro.setObservaciones(
                    obtenerValorCelda(
                        fila,
                        15,
                        formatter
                    )
                );


                registro.setNombre1(
                    obtenerValorCelda(
                        fila,
                        16,
                        formatter
                    )
                );


                registros.add(
                    registro
                );
            }
        }


        return registros;
    }


    // =========================================================
    // PROCESAR ARCHIVO PRECRM
    // =========================================================

    @Transactional
    public void procesarArchivo(
            Path archivo,
            String usuario)
            throws IOException {


        System.out.println(
            "======================================"
        );

        System.out.println(
            "INICIA PROCESAMIENTO PRECRM"
        );

        System.out.println(
            "ARCHIVO: "
            + archivo.getFileName()
        );

        System.out.println(
            "USUARIO: "
            + usuario
        );

        System.out.println(
            "======================================"
        );


        try {


            String nombreArchivo =
                    archivo
                        .getFileName()
                        .toString();


            // =================================================
            // 1. VALIDAR SI EL ARCHIVO YA FUE PROCESADO
            // =================================================

            System.out.println(
                "PASO 1 - VALIDANDO SI ARCHIVO YA EXISTE"
            );


            boolean archivoYaExiste =
                    cargaRepository
                        .existsByNombreArchivo(
                            nombreArchivo
                        );


            System.out.println(
                "ARCHIVO YA EXISTE: "
                + archivoYaExiste
            );


            if (archivoYaExiste) {

                System.out.println(
                    "El archivo ya fue procesado previamente: "
                    + nombreArchivo
                );

                return;
            }


            // =================================================
            // 2. LEER EXCEL
            // =================================================

            System.out.println(
                "PASO 2 - LEYENDO EXCEL"
            );


            List<PRECRMRegistroDTO> registros =
                    leerArchivo(
                        archivo
                    );


            System.out.println(
                "PASO A - EXCEL LEIDO. REGISTROS: "
                + registros.size()
            );


            // =================================================
            // 3. FECHA DEL ARCHIVO
            // =================================================

            LocalDateTime fechaArchivo =
                    Files
                        .getLastModifiedTime(
                            archivo
                        )
                        .toInstant()
                        .atZone(
                            ZoneId.systemDefault()
                        )
                        .toLocalDateTime();


            // =================================================
            // 4. CREAR CABECERA
            // =================================================

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


            /*
             * Por el momento queda AUTOMATICA.
             * Después distinguiremos MANUAL/AUTOMATICA.
             */
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


            // =================================================
            // 5. GUARDAR CABECERA
            // =================================================

            System.out.println(
                "PASO B1 - ANTES DE INSERTAR CABECERA"
            );


            carga =
                cargaRepository
                    .saveAndFlush(
                        carga
                    );


            Long pkCarga =
                    carga.getPkCarga();


            System.out.println(
                "PASO B - CABECERA INSERTADA. PK: "
                + pkCarga
            );


            // =================================================
            // 6. CONTADORES
            // =================================================

            int totalCargados = 0;

            int totalDuplicados = 0;

            int totalErrores = 0;

            int totalNoViables = 0;


            /*
             * Se utiliza para detectar duplicados
             * dentro del mismo archivo.
             */
            Set<String> registrosArchivo =
                    new HashSet<>();


            // =================================================
            // 7. RECORRER REGISTROS
            // =================================================

            for (PRECRMRegistroDTO registro
                    : registros) {


                System.out.println(
                    "--------------------------------------"
                );


                System.out.println(
                    "PASO C - PROCESANDO FILA EXCEL: "
                    + registro.getFilaExcel()
                );


                System.out.println(
                    "HORA INICIO ORIGINAL: ["
                    + registro.getHoraInicio()
                    + "]"
                );


                System.out.println(
                    "HORA FINAL ORIGINAL: ["
                    + registro.getHoraFinalizacion()
                    + "]"
                );


                System.out.println(
                    "FECHA NACIMIENTO ORIGINAL: ["
                    + registro.getFechaNacimiento()
                    + "]"
                );


                String resultado;

                String motivo = null;


                // =============================================
                // 7.1 NO VIABLE
                // =============================================

                if (esNoViable(
                        registro
                    )) {


                    resultado =
                            "NO_VIABLE";


                    motivo =
                            "Registro marcado como No viable "
                            + "desde origen";


                    totalNoViables++;
                }


                // =============================================
                // 7.2 VALIDAR ERRORES
                // =============================================

                else {


                    String errores =
                            obtenerErroresRegistro(
                                registro
                            );


                    if (errores != null) {


                        resultado =
                                "ERROR";


                        motivo =
                                errores;


                        totalErrores++;
                    }


                    // =========================================
                    // 7.3 DUPLICADOS
                    // =========================================

                    else {


                        String claveDuplicado =
                                generarClaveDuplicado(
                                    registro
                                );


                        boolean duplicadoArchivo =
                                registrosArchivo
                                    .contains(
                                        claveDuplicado
                                    );


                        boolean duplicadoBD =
                                detalleRepository
                                    .existsByNombreProspectoIgnoreCaseAndTelefonoContactoAndResultadoCarga(

                                        registro
                                            .getNombreProspecto()
                                            .trim(),

                                        registro
                                            .getTelefonoContacto()
                                            .trim(),

                                        "CARGADO"
                                    );


                        if (duplicadoArchivo ||
                            duplicadoBD) {


                            resultado =
                                    "DUPLICADO";


                            motivo =
                                    "Prospecto duplicado por "
                                    + "Nombre + Teléfono";


                            totalDuplicados++;
                        }


                        else {


                            resultado =
                                    "CARGADO";


                            totalCargados++;


                            registrosArchivo.add(
                                claveDuplicado
                            );
                        }
                    }
                }


                System.out.println(
                    "RESULTADO FILA "
                    + registro.getFilaExcel()
                    + ": "
                    + resultado
                );


                if (motivo != null) {

                    System.out.println(
                        "MOTIVO: "
                        + motivo
                    );
                }


                // =============================================
                // 8. CREAR DETALLE
                // =============================================

                PRECRMCargaDetalle detalle =
                        new PRECRMCargaDetalle();


                detalle.setFkCarga(
                    pkCarga
                );


                detalle.setFilaExcel(
                    registro.getFilaExcel()
                );


                detalle.setIdForm(
                    registro.getId()
                );


                // =============================================
                // CONVERTIR FECHAS
                // =============================================

                LocalDateTime horaInicio =
                        convertirFechaHora(
                            registro.getHoraInicio()
                        );


                LocalDateTime horaFinalizacion =
                        convertirFechaHora(
                            registro.getHoraFinalizacion()
                        );


                LocalDate fechaNacimiento =
                        convertirFecha(
                            registro.getFechaNacimiento()
                        );


                System.out.println(
                    "HORA INICIO CONVERTIDA: "
                    + horaInicio
                );


                System.out.println(
                    "HORA FINAL CONVERTIDA: "
                    + horaFinalizacion
                );


                System.out.println(
                    "FECHA NACIMIENTO CONVERTIDA: "
                    + fechaNacimiento
                );


                detalle.setHoraInicio(
                    horaInicio
                );


                detalle.setHoraFinalizacion(
                    horaFinalizacion
                );


                detalle.setFechaNacimiento(
                    fechaNacimiento
                );


                // =============================================
                // DEMAS CAMPOS
                // =============================================

                detalle.setCorreoRegistro(
                    registro.getCorreoElectronico()
                );


                detalle.setNombreRegistro(
                    registro.getNombre()
                );


                detalle.setSucursalRegistro(
                    registro.getSucursalRegistro()
                );


                detalle.setNombreProspecto(
                    registro.getNombreProspecto()
                );


                detalle.setTipo(
                    registro.getTipo()
                );


                detalle.setTelefonoContacto(
                    registro.getTelefonoContacto()
                );


                detalle.setCorreoContacto(
                    registro.getCorreoContacto()
                );


                detalle.setFuenteOrigen(
                    registro.getFuenteOrigen()
                );


                detalle.setEstatusOrigen(
                    registro.getEstatus()
                );


                detalle.setProductoInteres(
                    registro.getProductoInteres()
                );


                detalle.setMontoInteres(
                    convertirMonto(
                        registro.getMontoInteres()
                    )
                );


                detalle.setObservaciones(
                    registro.getObservaciones()
                );


                detalle.setNombre1(
                    registro.getNombre1()
                );


                // =============================================
                // RESULTADO DEL ANALISIS
                // =============================================

                detalle.setResultadoCarga(
                    resultado
                );


                detalle.setMotivoResultado(
                    motivo
                );


                detalle.setFechaRegistro(
                    LocalDateTime.now()
                );


                // =============================================
                // 9. INSERTAR DETALLE
                // =============================================

                System.out.println(
                    "PASO D - ANTES DE INSERTAR DETALLE FILA "
                    + registro.getFilaExcel()
                );


                detalleRepository
                    .saveAndFlush(
                        detalle
                    );


                System.out.println(
                    "PASO E - DETALLE INSERTADO FILA "
                    + registro.getFilaExcel()
                );
            }


            // =================================================
            // 10. TERMINAR DETALLES
            // =================================================

            System.out.println(
                "PASO F - TODOS LOS DETALLES TERMINADOS"
            );


            // =================================================
            // 11. ACTUALIZAR CABECERA
            // =================================================

            carga.setTotalCargados(
                totalCargados
            );


            carga.setTotalDuplicados(
                totalDuplicados
            );


            carga.setTotalErrores(
                totalErrores
            );


            carga.setTotalNoViables(
                totalNoViables
            );


            carga.setEstatusProceso(
                "COMPLETADO"
            );


            cargaRepository
                .saveAndFlush(
                    carga
                );


            System.out.println(
                "PASO G - CABECERA ACTUALIZADA A COMPLETADO"
            );


            // =================================================
            // RESULTADO FINAL
            // =================================================

            System.out.println(
                "======================================"
            );


            System.out.println(
                "PROCESAMIENTO PRECRM COMPLETADO"
            );


            System.out.println(
                "Encontrados: "
                + registros.size()
            );


            System.out.println(
                "Cargados: "
                + totalCargados
            );


            System.out.println(
                "Duplicados: "
                + totalDuplicados
            );


            System.out.println(
                "Errores: "
                + totalErrores
            );


            System.out.println(
                "No viables: "
                + totalNoViables
            );


            System.out.println(
                "======================================"
            );
        }


        catch (IOException e) {


            System.err.println(
                "======================================"
            );


            System.err.println(
                "ERROR IO PROCESANDO PRECRM"
            );


            System.err.println(
                "TIPO: "
                + e.getClass().getName()
            );


            System.err.println(
                "MENSAJE: "
                + e.getMessage()
            );


            e.printStackTrace();


            System.err.println(
                "======================================"
            );


            throw e;
        }


        catch (RuntimeException e) {


            System.err.println(
                "======================================"
            );


            System.err.println(
                "ERROR GENERAL PROCESANDO PRECRM"
            );


            System.err.println(
                "TIPO: "
                + e.getClass().getName()
            );


            System.err.println(
                "MENSAJE: "
                + e.getMessage()
            );


            e.printStackTrace();


            System.err.println(
                "======================================"
            );


            throw e;
        }
    }


    // =========================================================
    // VALIDAR NO VIABLE
    // =========================================================

    private boolean esNoViable(
            PRECRMRegistroDTO registro) {


        return registro.getEstatus() != null

            && registro
                .getEstatus()
                .trim()
                .equalsIgnoreCase(
                    "No viable"
                );
    }


    // =========================================================
    // VALIDAR ERRORES DEL REGISTRO
    // =========================================================

    private String obtenerErroresRegistro(
            PRECRMRegistroDTO registro) {


        List<String> errores =
                new ArrayList<>();


        // =====================================================
        // NOMBRE DEL PROSPECTO
        // =====================================================

        if (registro.getNombreProspecto() == null ||
            registro
                .getNombreProspecto()
                .trim()
                .isEmpty()) {


            errores.add(
                "Nombre del prospecto vacío"
            );
        }


        // =====================================================
        // SUCURSAL
        // =====================================================

        if (registro.getSucursalRegistro() == null ||
            registro
                .getSucursalRegistro()
                .trim()
                .isEmpty()) {


            errores.add(
                "Sucursal de registro vacía"
            );
        }


        // =====================================================
        // TIPO
        // =====================================================

        if (registro.getTipo() == null ||
            registro
                .getTipo()
                .trim()
                .isEmpty()) {


            errores.add(
                "Tipo vacío"
            );
        }


        // =====================================================
        // ESTATUS
        // =====================================================

        if (registro.getEstatus() == null ||
            registro
                .getEstatus()
                .trim()
                .isEmpty()) {


            errores.add(
                "Estatus vacío"
            );
        }


        // =====================================================
        // TELEFONO
        // =====================================================

        if (registro.getTelefonoContacto() == null ||
            !registro
                .getTelefonoContacto()
                .trim()
                .matches("\\d{10}")) {


            errores.add(
                "Teléfono de contacto inválido"
            );
        }


        // =====================================================
        // FECHA DE NACIMIENTO
        // =====================================================

        if (registro.getFechaNacimiento() != null &&
            !registro
                .getFechaNacimiento()
                .trim()
                .isEmpty()) {


            LocalDate fechaNacimiento =
                    convertirFecha(
                        registro.getFechaNacimiento()
                    );


            if (fechaNacimiento == null) {


                errores.add(
                    "Fecha de nacimiento inválida"
                );
            }
        }


        // =====================================================
        // MONTO
        // =====================================================

        if (registro.getMontoInteres() != null &&
            !registro
                .getMontoInteres()
                .trim()
                .isEmpty()) {


            BigDecimal monto =
                    convertirMonto(
                        registro.getMontoInteres()
                    );


            if (monto == null) {


                errores.add(
                    "Monto de interés inválido"
                );
            }
        }


        if (errores.isEmpty()) {

            return null;
        }


        return String.join(
            "; ",
            errores
        );
    }


    // =========================================================
    // GENERAR CLAVE DUPLICADO
    // NOMBRE + TELEFONO
    // =========================================================

    private String generarClaveDuplicado(
            PRECRMRegistroDTO registro) {


        String nombre =
                registro
                    .getNombreProspecto()
                    .trim()
                    .replaceAll(
                        "\\s+",
                        " "
                    )
                    .toUpperCase();


        String telefono =
                registro
                    .getTelefonoContacto()
                    .trim();


        return nombre
                + "|"
                + telefono;
    }


    // =========================================================
    // ANALIZAR ARCHIVO
    // =========================================================

    public int analizarArchivo(
            Path archivo)
            throws IOException {


        List<PRECRMRegistroDTO> registros =
                leerArchivo(
                    archivo
                );


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
                    archivo.toFile()
                )) {


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

         if (columna == 1 ||
             columna == 2 ||
             columna == 7) {

             System.out.println(
                 "FECHA - FILA "
                 + (fila.getRowNum() + 1)
                 + " COLUMNA "
                 + (columna + 1)
                 + " = CELDA NULL"
             );
         }

         return "";
     }


     // =====================================================
     // DEBUG COLUMNAS DE FECHA
     // =====================================================

     if (columna == 1 ||
         columna == 2 ||
         columna == 7) {

         System.out.println(
             "FECHA - FILA "
             + (fila.getRowNum() + 1)
             + " COLUMNA "
             + (columna + 1)
             + " TIPO="
             + celda.getCellType()
             + " VALOR_FORMATTER=["
             + formatter.formatCellValue(celda)
             + "]"
         );
     }


     // =====================================================
     // SOLO VALIDAR FECHA EXCEL SI LA CELDA ES NUMERICA
     // =====================================================

     if (celda.getCellType()
             == org.apache.poi.ss.usermodel.CellType.NUMERIC
         &&
         DateUtil.isCellDateFormatted(celda)) {


         try {

             LocalDateTime fechaHora =
                     celda
                         .getLocalDateTimeCellValue();


             // =============================================
             // HORA INICIO / HORA FINAL
             // =============================================

             if (columna == 1 ||
                 columna == 2) {


                 String resultado =
                         fechaHora.format(
                             DateTimeFormatter.ofPattern(
                                 "dd/MM/yyyy HH:mm:ss"
                             )
                         );


                 System.out.println(
                     "FECHA/HORA EXCEL CONVERTIDA: "
                     + resultado
                 );


                 return resultado;
             }


             // =============================================
             // FECHA NACIMIENTO
             // =============================================

             if (columna == 7) {


                 String resultado =
                         fechaHora
                             .toLocalDate()
                             .format(
                                 DateTimeFormatter.ofPattern(
                                     "dd/MM/yyyy"
                                 )
                             );


                 System.out.println(
                     "FECHA EXCEL CONVERTIDA: "
                     + resultado
                 );


                 return resultado;
             }


         } catch (Exception e) {

             System.err.println(
                 "ERROR CONVIRTIENDO FECHA EN FILA "
                 + (fila.getRowNum() + 1)
                 + ", COLUMNA "
                 + (columna + 1)
                 + ": "
                 + e.getMessage()
             );
         }
     }


     // =====================================================
     // SI ES STRING, TEXTO O CUALQUIER OTRO TIPO
     // LO DEVOLVEMOS TAL COMO SE VE EN EXCEL
     // =====================================================

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
                            .formatCellValue(
                                celda
                            )
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

            "d/M/yyyy H:mm:ss",

            "d/M/yyyy H:mm",

            "MM/dd/yyyy HH:mm:ss",

            "MM/dd/yyyy HH:mm",

            "M/d/yyyy H:mm:ss",

            "M/d/yyyy H:mm",

            "M/d/yyyy h:mm:ss a",

            "M/d/yyyy h:mm a",

            "yyyy-MM-dd HH:mm:ss",

            "yyyy-MM-dd HH:mm",

            "yyyy-MM-dd'T'HH:mm:ss"
        };


        for (String formato : formatos) {


            try {


                return LocalDateTime.parse(
                    fecha,
                    DateTimeFormatter
                        .ofPattern(
                            formato
                        )
                );


            } catch (DateTimeParseException e) {

                // Intentar siguiente formato
            }
        }


        System.err.println(
            "NO SE PUDO CONVERTIR FECHA/HORA: ["
            + valor
            + "]"
        );


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

            "MM/dd/yyyy",
            "M/d/yyyy",

            "dd/MM/yy",
            "d/M/yy",

            "MM/dd/yy",
            "M/d/yy",

            "yyyy-MM-dd"
        };


        for (String formato : formatos) {

            try {

                return LocalDate.parse(
                    fecha,
                    DateTimeFormatter.ofPattern(
                        formato
                    )
                );

            } catch (DateTimeParseException e) {

                // Probar siguiente formato
            }
        }


        System.err.println(
            "NO SE PUDO CONVERTIR FECHA: ["
            + valor
            + "]"
        );


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
                    valor
                        .trim()
                        .replace(
                            "$",
                            ""
                        )
                        .replace(
                            ",",
                            ""
                        )
                        .replace(
                            " ",
                            ""
                        );


            return new BigDecimal(
                monto
            );


        } catch (NumberFormatException e) {


            System.err.println(
                "NO SE PUDO CONVERTIR MONTO: ["
                + valor
                + "]"
            );


            return null;
        }
    }


    // =========================================================
    // ULTIMO ARCHIVO PROCESADO
    // =========================================================

    public String obtenerUltimoArchivoProcesado() {


        /*
         * Por el momento permanece desactivado.
         * Lo reactivaremos cuando terminemos
         * el procesamiento completo.
         */

        return null;
    }


    // =========================================================
    // FECHA ULTIMO PROCESAMIENTO
    // =========================================================

    public String obtenerUltimoProcesamiento() {


        /*
         * Por el momento permanece desactivado.
         */

        return null;
    }


    // =========================================================
    // OBTENER RUTA PRECRM
    // =========================================================

    private Path obtenerCarpetaPRECRM()
            throws IOException {


        Path carpeta =
                Paths.get(
                    rutaArchivos
                );


        if (!Files.exists(
                carpeta
            )) {


            throw new IOException(
                "La ruta configurada para PRE-CRM "
                + "no existe: "
                + rutaArchivos
            );
        }


        if (!Files.isDirectory(
                carpeta
            )) {


            throw new IOException(
                "La ruta configurada para PRE-CRM "
                + "no corresponde a un directorio."
            );
        }


        return carpeta;
    }
}