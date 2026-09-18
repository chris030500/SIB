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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.CRM.Repository.precrmCargaDetalleRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmCargaRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmGestionRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmHistorialEstatusRepository;
import ahorrosbienestar.sib.std.CRM.model.precrmHistorialEstatus;
import ahorrosbienestar.sib.std.CRM.model.precrmGestion;
import ahorrosbienestar.sib.std.CRM.Repository.precrmSospechosoRepository;
import ahorrosbienestar.sib.std.CRM.dto.precrmRegistroDTO;
import ahorrosbienestar.sib.std.CRM.model.precrmCarga;
import ahorrosbienestar.sib.std.CRM.model.precrmCargaDetalle;
import ahorrosbienestar.sib.std.CRM.model.precrmSospechoso;
import ahorrosbienestar.sib.std.CRM.model.precrmCatalogoReporte;
import ahorrosbienestar.sib.std.CRM.model.precrmReporteDescarga;
import ahorrosbienestar.sib.std.CRM.model.precrmFuncionalidad;
import ahorrosbienestar.sib.std.CRM.model.precrmPermiso;
import ahorrosbienestar.sib.std.CRM.model.precrmUsuarioPermiso;
import ahorrosbienestar.sib.std.CRM.model.precrmSucursalConfig;
import ahorrosbienestar.sib.std.CRM.model.precrmClasificacion;
import ahorrosbienestar.sib.std.CRM.model.precrmEstatus;
import ahorrosbienestar.sib.std.CRM.model.precrmTipoGestion;

import ahorrosbienestar.sib.std.CRM.Repository.precrmCatalogoReporteRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmReporteDescargaRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmFuncionalidadRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmPermisoRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmUsuarioPermisoRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmSucursalConfigRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmClasificacionRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmEstatusRepository;
import ahorrosbienestar.sib.std.CRM.Repository.precrmTipoGestionRepository;



@Service
public class precrmService {


    @Value("${precrm.ruta-archivos}")
    private String rutaArchivos;


    private final precrmCargaRepository cargaRepository;

    private final precrmCargaDetalleRepository detalleRepository;

    private final precrmSospechosoRepository sospechosoRepository;
    
    private final precrmClasificacionRepository clasificacionRepository;

    private final precrmEstatusRepository estatusRepository;
    
    private final precrmTipoGestionRepository tipoGestionRepository;
    
    private final precrmUsuarioPermisoRepository usuarioPermisoRepository;

    @Autowired
    private precrmGestionRepository gestionRepository;
    @Autowired
    private precrmHistorialEstatusRepository historialEstatusRepository;
    @Autowired
    private precrmCatalogoReporteRepository catalogoReporteRepository;
    @Autowired
    private precrmReporteDescargaRepository reporteDescargaRepository;
    @Autowired
    private precrmFuncionalidadRepository funcionalidadRepository;
    @Autowired
    private precrmPermisoRepository permisoRepository;
    @Autowired
    private precrmSucursalConfigRepository sucursalConfigRepository;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public precrmService(
            precrmCargaRepository cargaRepository,
            precrmCargaDetalleRepository detalleRepository,
            precrmSospechosoRepository sospechosoRepository,
            precrmClasificacionRepository clasificacionRepository,
            precrmEstatusRepository estatusRepository,
            precrmTipoGestionRepository tipoGestionRepository,
            precrmUsuarioPermisoRepository usuarioPermisoRepository) {

        this.cargaRepository =
                cargaRepository;

        this.detalleRepository =
                detalleRepository;

        this.sospechosoRepository =
                sospechosoRepository;
        
        this.clasificacionRepository =
                clasificacionRepository;

        this.estatusRepository =
                estatusRepository;
        
        this.tipoGestionRepository =
                tipoGestionRepository;
        
        this.usuarioPermisoRepository =
                usuarioPermisoRepository;
        
        System.out.println(
        	    "PRECRM SERVICE CLASS = "
        	    + precrmService.class
        	        .getProtectionDomain()
        	        .getCodeSource()
        	        .getLocation()
        	);

        	System.out.println(
        	    "PRECRM SERVICE RESOURCE = "
        	    + precrmService.class
        	        .getResource(
        	            "precrmService.class"
        	        )
        	);
    }

    // =========================================================
    // ENCABEZADOS ESPERADOS DEL EXCEL
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
                                path.getFileName()
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
    // GUARDAR ARCHIVO MANUAL
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
                Paths.get(
                    nombreOriginal
                )
                .getFileName()
                .toString()
                .trim();


        String nombreValidacion =
                nombreArchivo
                    .toUpperCase();


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
    
    public void actualizarTelefonoAlterno(
            Long pkSospechoso,
            String telefonoAlterno) {

        precrmSospechoso sospechoso =
            sospechosoRepository
                .findById(pkSospechoso)
                .orElseThrow(() ->
                    new RuntimeException(
                        "No se encontró el sospechoso."
                    )
                );

        sospechoso.setTelefonoAlterno(
            telefonoAlterno
        );

        sospechosoRepository.save(
            sospechoso
        );
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


            if (filaEncabezados.getLastCellNum()
                    < ENCABEZADOS_PRECRM.length) {

                throw new IOException(
                    "El archivo PRE-CRM no contiene "
                    + "todas las columnas esperadas."
                );
            }


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
                            .formatCellValue(
                                celda
                            )
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
    // LEER ARCHIVO
    // =========================================================

    public List<precrmRegistroDTO> leerArchivo(
            Path archivo)
            throws IOException {


        validarEstructuraArchivo(
            archivo
        );


        List<precrmRegistroDTO> registros =
                new ArrayList<>();


        try (Workbook workbook =
                WorkbookFactory.create(
                    archivo.toFile()
                )) {


            Sheet hoja =
                    workbook.getSheetAt(0);


            DataFormatter formatter =
                    new DataFormatter();


            for (int i = 1;
                 i <= hoja.getLastRowNum();
                 i++) {


                Row fila =
                        hoja.getRow(i);


                if (fila == null ||
                    filaEstaVacia(fila)) {

                    continue;
                }


                precrmRegistroDTO registro =
                        new precrmRegistroDTO();


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


                registros.add(
                    registro
                );
            }
        }


        return registros;
    }


    // =========================================================
    // PROCESAR ARCHIVO
    // =========================================================

    @Transactional
    public void procesarArchivo(
            Path archivo,
            String usuario)
            throws IOException {

        
        String nombreArchivo =
                archivo.getFileName()
                       .toString();


        // =====================================================
        // 1. VALIDAR SI YA EXISTE
        // =====================================================

        boolean archivoYaExiste =
                cargaRepository
                    .existsByNombreArchivo(
                        nombreArchivo
                    );


        if (archivoYaExiste) {

            System.out.println(
                "Archivo ya procesado: "
                + nombreArchivo
            );

            return;
        }


        // =====================================================
        // 2. LEER EXCEL
        // =====================================================

        List<precrmRegistroDTO> registros =
                leerArchivo(
                    archivo
                );


        // =====================================================
        // 3. FECHA ARCHIVO
        // =====================================================

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


        // =====================================================
        // 4. CREAR CABECERA
        // =====================================================

        precrmCarga carga =
                new precrmCarga();


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


        carga =
            cargaRepository
                .saveAndFlush(
                    carga
                );


        Long pkCarga =
                carga.getPkCarga();


        // =====================================================
        // 5. CONTADORES
        // =====================================================

        int totalCargados = 0;

        int totalDuplicados = 0;

        int totalErrores = 0;

        int totalNoViables = 0;


        Set<String> registrosArchivo =
                new HashSet<>();


        // =====================================================
        // 6. RECORRER EXCEL
        // =====================================================

        for (precrmRegistroDTO registro
                : registros) {


            String resultado;

            String motivo = null;


            // =================================================
            // NO VIABLE
            // Se considera incidencia / error de procesamiento
            // y NO genera sospechoso
            // =================================================

            if (esNoViable(registro)) {

                resultado =
                        "ERROR";

                motivo =
                        "Registro no viable. "
                        + "El sospechoso no será incorporado al PRE CRM.";

                totalErrores++;
            }

            // =================================================
            // VALIDAR ERRORES
            // =================================================

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


                // =============================================
                // DUPLICADOS
                // =============================================

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


            // =================================================
            // 7. CREAR DETALLE
            // =================================================

            precrmCargaDetalle detalle =
                    new precrmCargaDetalle();


            detalle.setFkCarga(
                pkCarga
            );


            detalle.setFilaExcel(
                registro.getFilaExcel()
            );


            detalle.setIdForm(
                registro.getId()
            );


            detalle.setHoraInicio(
                convertirFechaHora(
                    registro.getHoraInicio()
                )
            );


            detalle.setHoraFinalizacion(
                convertirFechaHora(
                    registro.getHoraFinalizacion()
                )
            );


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


            detalle.setFechaNacimiento(
                convertirFecha(
                    registro.getFechaNacimiento()
                )
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


            detalle.setResultadoCarga(
                resultado
            );


            detalle.setMotivoResultado(
                motivo
            );


            detalle.setFechaRegistro(
                LocalDateTime.now()
            );


         // =================================================
         // 8. INSERTAR DETALLE
         // =================================================

         System.out.println(
             ">>> PRECRM DEBUG | FILA: "
             + registro.getFilaExcel()
             + " | ESTATUS ORIGEN: ["
             + registro.getEstatus()
             + "] | RESULTADO: ["
             + resultado
             + "] | MOTIVO: ["
             + motivo
             + "]"
         );

         detalle =
             detalleRepository
                 .saveAndFlush(
                     detalle
                 );

         System.out.println(
             ">>> PRECRM DESPUES SAVE | FILA: "
             + detalle.getFilaExcel()
             + " | RESULTADO: ["
             + detalle.getResultadoCarga()
             + "] | MOTIVO: ["
             + detalle.getMotivoResultado()
             + "]"
         );


            // =================================================
            // 9. SI ES CARGADO → CREAR SOSPECHOSO
            // =================================================

         if ("CARGADO".equals(
        	        resultado
        	    )) {

        	    insertarSospechoso(
        	        detalle
        	    );
        	}
        }


        // =====================================================
        // 10. ACTUALIZAR CABECERA
        // =====================================================

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


    // =========================================================
    // INSERTAR SOSPECHOSO
    // =========================================================

    private void insertarSospechoso(
    		precrmCargaDetalle detalle) {


        /*
         * Protección adicional.
         * Un PK_DETALLE solo puede generar
         * un sospechoso.
         */
        boolean yaExiste =
                sospechosoRepository
                    .existsByFkCargaDetalle(
                        detalle.getPkDetalle()
                    );


        if (yaExiste) {

            return;
        }


        precrmSospechoso sospechoso =
                new precrmSospechoso();


        // =====================================================
        // RELACION CON DETALLE
        // =====================================================

        sospechoso.setFkCargaDetalle(
            detalle.getPkDetalle()
        );


        // =====================================================
        // FOLIO INTERNO
        // =====================================================

        sospechoso.setFolio(
            generarFolioPRECRM(
                detalle.getPkDetalle()
            )
        );


        // =====================================================
        // DATOS DEL SOSPECHOSO
        // =====================================================

        sospechoso.setNombre(
            detalle.getNombreProspecto()
        );


        sospechoso.setFechaNacimiento(
            detalle.getFechaNacimiento()
        );


        sospechoso.setTelefonoPrincipal(
            detalle.getTelefonoContacto()
        );


        sospechoso.setCorreo(
            detalle.getCorreoContacto()
        );


        sospechoso.setClasificacion(
            detalle.getTipo()
        );


        sospechoso.setFuenteOrigen(
            detalle.getFuenteOrigen()
        );


        sospechoso.setProductoInteres(
            detalle.getProductoInteres()
        );


        sospechoso.setMontoInteres(
            detalle.getMontoInteres()
        );


        sospechoso.setObservaciones(
            detalle.getObservaciones()
        );


        sospechoso.setSucursalOrigen(
            detalle.getSucursalRegistro()
        );


        // =====================================================
        // TODO SOSPECHOSO NUEVO INICIA COMO REGISTRADO
        // =====================================================

        LocalDateTime ahora =
                LocalDateTime.now();


        sospechoso.setEstatus(
            "REGISTRADO"
        );


        sospechoso.setFechaRegistro(
            ahora
        );


        sospechoso.setFechaInicioEtapa(
            ahora
        );


        sospechoso.setFechaUltimaActualizacion(
            ahora
        );


     // REGISTRADO tiene 7 días
     // para pasar a SEGUIMIENTO
        
        sospechoso.setFechaProximaGestion(
        	    calcularFechaProximaGestion(
        	        "REGISTRADO",
        	        ahora
        	    )
        	);
        sospechoso.setDiasRestantes(
        	    obtenerDiasPorEstatus(
        	        "REGISTRADO"
        	    )
        	);


        sospechoso.setActivo(
            1
        );


        sospechoso =
            sospechosoRepository
                .saveAndFlush(
                    sospechoso
                );


        System.out.println(
            "SOSPECHOSO INSERTADO -> PK: "
            + sospechoso.getPkSospechoso()
            + " | FOLIO: "
            + sospechoso.getFolio()
        );
    }
    
 // =========================================================
 // OBTENER DIAS DISPONIBLES POR ESTATUS
 // =========================================================

 private Integer obtenerDiasPorEstatus(
         String estatus) {


     if (estatus == null) {

         return null;
     }


     switch (estatus) {

         case "REGISTRADO":

             return 7;


         case "SEGUIMIENTO":

             return 3;


         case "CITA_AGENDADA":

             return 3;


         case "COTIZACION":

             return 2;


         case "SOLICITUD":

             return 3;


         case "CONCLUIDO":

             return null;


         default:

             return null;
     }
 }


    // =========================================================
    // GENERAR FOLIO PRECRM
    // =========================================================

    private String generarFolioPRECRM(
            Long pkDetalle) {


        return String.format(
            "PRE%010d",
            pkDetalle
        );
    }


    // =========================================================
    // VALIDAR NO VIABLE
    // =========================================================

    private boolean esNoViable(
    		precrmRegistroDTO registro) {


        return registro.getEstatus() != null

            && registro
                .getEstatus()
                .trim()
                .equalsIgnoreCase(
                    "No viable"
                );
    }


    // =========================================================
    // VALIDAR ERRORES
    // =========================================================

    private String obtenerErroresRegistro(
    		precrmRegistroDTO registro) {


        List<String> errores =
                new ArrayList<>();


        // =====================================================
        // NOMBRE
        // =====================================================

        if (registro.getNombreProspecto() == null ||
            registro.getNombreProspecto()
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
            registro.getSucursalRegistro()
                    .trim()
                    .isEmpty()) {


            errores.add(
                "Sucursal de registro vacía"
            );
        }


        // =====================================================
        // TIPO / CLASIFICACION
        // =====================================================

        if (registro.getTipo() == null ||
            registro.getTipo()
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
            registro.getEstatus()
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
            !registro.getTelefonoContacto()
                     .trim()
                     .matches("\\d{10}")) {


            errores.add(
                "Teléfono de contacto inválido"
            );
        }


        // =====================================================
        // FECHA NACIMIENTO
        // =====================================================

        if (registro.getFechaNacimiento() != null &&
            !registro.getFechaNacimiento()
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
            !registro.getMontoInteres()
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
    // CLAVE DUPLICADO
    // =========================================================

    private String generarClaveDuplicado(
    		precrmRegistroDTO registro) {


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
    // OBTENER VALOR CELDA
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


        // =====================================================
        // FECHAS REALES DE EXCEL
        // =====================================================

        if (celda.getCellType()
                == CellType.NUMERIC
            &&
            DateUtil.isCellDateFormatted(
                celda
            )) {


            try {


                LocalDateTime fechaHora =
                        celda
                            .getLocalDateTimeCellValue();


                // Hora inicio / Hora finalización
                if (columna == 1 ||
                    columna == 2) {


                    return fechaHora.format(
                        DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy HH:mm:ss"
                        )
                    );
                }


                // Fecha nacimiento
                if (columna == 7) {


                    return fechaHora
                            .toLocalDate()
                            .format(
                                DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy"
                                )
                            );
                }


            } catch (Exception e) {


                System.err.println(
                    "Error convirtiendo fecha "
                    + "fila "
                    + (fila.getRowNum() + 1)
                    + ", columna "
                    + (columna + 1)
                );
            }
        }


        return formatter
                .formatCellValue(
                    celda
                )
                .trim();
    }


    // =========================================================
    // FILA VACIA
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


        for (String formato
                : formatos) {


            try {


                return LocalDateTime.parse(
                    fecha,
                    DateTimeFormatter.ofPattern(
                        formato
                    )
                );


            } catch (DateTimeParseException e) {

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

            "MM/dd/yyyy",

            "M/d/yyyy",

            "dd/MM/yy",

            "d/M/yy",

            "MM/dd/yy",

            "M/d/yy",

            "yyyy-MM-dd"
        };


        for (String formato
                : formatos) {


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

            return null;
        }
    }


    // =========================================================
    // ANALIZAR ARCHIVO
    // =========================================================

    public int analizarArchivo(
            Path archivo)
            throws IOException {


        return leerArchivo(
            archivo
        ).size();
    }


    // =========================================================
    // TOTAL REGISTROS
    // =========================================================

    public int obtenerTotalRegistros(
            Path archivo)
            throws IOException {


        return leerArchivo(
            archivo
        ).size();
    }


    // =========================================================
    // ULTIMA CARGA
    // =========================================================

    public precrmCarga obtenerUltimaCarga() {


        return cargaRepository
                .findTopByOrderByFechaProcesamientoDesc()
                .orElse(null);
    }


 // =========================================================
 // INCIDENCIAS ULTIMO PROCESAMIENTO
 // =========================================================

    public List<precrmCargaDetalle>
    obtenerIncidenciasUltimoProcesamiento() {

        precrmCarga ultimaCarga =
                obtenerUltimaCarga();

        if (ultimaCarga == null) {
            return new ArrayList<>();
        }

        List<String> resultados =
                List.of(
                    "DUPLICADO",
                    "ERROR"
                );

        return detalleRepository
                .findByFkCargaAndResultadoCargaInOrderByFilaExcelAsc(
                    ultimaCarga.getPkCarga(),
                    resultados
                );
    }


    // =========================================================
    // ULTIMO ARCHIVO PROCESADO
    // =========================================================

    public String obtenerUltimoArchivoProcesado() {


    	precrmCarga ultimaCarga =
                obtenerUltimaCarga();


        if (ultimaCarga == null) {

            return null;
        }


        return ultimaCarga
                .getNombreArchivo();
    }


    // =========================================================
    // FECHA ULTIMO PROCESAMIENTO
    // =========================================================

    public String obtenerUltimoProcesamiento() {


    	precrmCarga ultimaCarga =
                obtenerUltimaCarga();


        if (ultimaCarga == null ||
            ultimaCarga.getFechaProcesamiento() == null) {

            return null;
        }


        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
                );


        return ultimaCarga
                .getFechaProcesamiento()
                .format(
                    formato
                );
    }
    
 // =========================================================
 // OBTENER SOSPECHOSOS ACTIVOS
 // =========================================================

    public List<precrmSospechoso>
    obtenerSospechososActivos() {


List<precrmSospechoso> sospechosos =
        sospechosoRepository
            .findByActivoOrderByFechaRegistroDesc(
                1
            );


System.out.println(
    "======================================"
);

System.out.println(
    "SOSPECHOSOS RECUPERADOS DE BD"
);


for (precrmSospechoso s
        : sospechosos) {

    System.out.println(
        "FOLIO: "
        + s.getFolio()
        + " | NOMBRE: "
        + s.getNombre()
        + " | CLASIFICACION: ["
        + s.getClasificacion()
        + "] | ESTATUS: ["
        + s.getEstatus()
        + "]"
    );
}


System.out.println(
    "======================================"
);


return sospechosos;
}


 // =========================================================
 // CONTAR SOSPECHOSOS POR ESTATUS
 // =========================================================

 public long contarSospechososPorEstatus(
         String estatus) {


     return sospechosoRepository
             .countByEstatusAndActivo(
                 estatus,
                 1
             );
 }
 
 
//=========================================================
//BUSCAR SOSPECHOSOS CON FILTROS
//=========================================================

public List<precrmSospechoso> buscarSospechosos(
      String folio,
      String nombre,
      String telefono,
      String clasificacion,
      String estatus,
      LocalDate fechaRegistro) {


  // =====================================================
  // LIMPIAR STRINGS VACIOS
  // =====================================================

  folio =
      limpiarFiltro(
          folio
      );

  nombre =
      limpiarFiltro(
          nombre
      );

  telefono =
      limpiarFiltro(
          telefono
      );

  clasificacion =
      limpiarFiltro(
          clasificacion
      );

  estatus =
      limpiarFiltro(
          estatus
      );


  // =====================================================
  // RANGO DE FECHA
  // =====================================================

  LocalDateTime fechaInicio =
          null;

  LocalDateTime fechaFin =
          null;


  if (fechaRegistro != null) {

      fechaInicio =
              fechaRegistro
                  .atStartOfDay();


      fechaFin =
              fechaRegistro
                  .plusDays(1)
                  .atStartOfDay();
  }


  return sospechosoRepository
          .buscarSospechosos(

              folio,

              nombre,

              telefono,

              clasificacion,

              estatus,

              fechaInicio,

              fechaFin
          );
}


//=========================================================
//LIMPIAR FILTRO
//=========================================================

private String limpiarFiltro(
      String valor) {


  if (valor == null ||
      valor.trim().isEmpty()) {

      return null;
  }


  return valor.trim();
}


//=========================================================
//ACTUALIZAR ESTATUS DEL SOSPECHOSO
//=========================================================

@Transactional
public void actualizarEstatusSospechoso(
        Long pkSospechoso,
        String nuevoEstatus,
        String usuario,
        String origenCambio) {


	precrmSospechoso sospechoso =
            sospechosoRepository
                .findById(pkSospechoso)
                .orElseThrow(
                    () -> new IllegalArgumentException(
                        "No se encontró el sospechoso."
                    )
                );


    String estatusAnterior =
            sospechoso.getEstatus();


    // =====================================================
    // VALIDAR SIGUIENTE ESTATUS
    // =====================================================

    String estatusPermitido =
            obtenerSiguienteEstatusPantalla(
                estatusAnterior
            );


    if (estatusPermitido == null) {

        throw new IllegalArgumentException(
            "El sospechoso ya se encuentra en la etapa final."
        );
    }


    if (!estatusPermitido.equals(nuevoEstatus)) {

        throw new IllegalArgumentException(
            "El cambio de estatus solicitado no está permitido."
        );
    }


    LocalDateTime ahora =
            LocalDateTime.now();


    // =====================================================
    // GUARDAR HISTORIAL ANTES DE MODIFICAR SOSPECHOSO
    // =====================================================

    precrmHistorialEstatus historial =
            new precrmHistorialEstatus();


    historial.setFkSospechoso(
        pkSospechoso
    );


    historial.setEstatusAnterior(
        estatusAnterior
    );


    historial.setEstatusNuevo(
        nuevoEstatus
    );


    /*
     * Guardamos cuándo inició la etapa
     * que estamos terminando.
     */
    historial.setFechaInicioEtapa(
        sospechoso.getFechaInicioEtapa()
    );


    historial.setFechaCambio(
        ahora
    );


    /*
     * La fecha próxima que tenía el sospechoso
     * representa el límite de la etapa anterior.
     */
    historial.setFechaLimite(
        sospechoso.getFechaProximaGestion()
    );


    /*
     * Días objetivo de la etapa que termina.
     */
    historial.setDiasObjetivo(
        obtenerDiasPorEstatus(
            estatusAnterior
        )
    );


    historial.setUsuarioCambio(
        usuario
    );


    historial.setOrigenCambio(
        origenCambio
    );


    historial.setComentario(
        null
    );


    historialEstatusRepository
        .saveAndFlush(
            historial
        );


    // =====================================================
    // ACTUALIZAR ESTADO ACTUAL DEL SOSPECHOSO
    // =====================================================

    sospechoso.setEstatus(
        nuevoEstatus
    );


    sospechoso.setFechaInicioEtapa(
        ahora
    );


    sospechoso.setFechaUltimaActualizacion(
        ahora
    );


    sospechoso.setFechaProximaGestion(
        calcularFechaProximaGestion(
            nuevoEstatus,
            ahora
        )
    );


    sospechoso.setDiasRestantes(
        obtenerDiasPorEstatus(
            nuevoEstatus
        )
    );


    sospechosoRepository
        .saveAndFlush(
            sospechoso
        );
}

public List<precrmHistorialEstatus>
obtenerHistorialEstatusSospechoso(
    Long pkSospechoso) {


return historialEstatusRepository
    .findByFkSospechosoOrderByFechaCambioDesc(
        pkSospechoso
    );
}


//=========================================================
//CALCULAR FECHA DE PROXIMA GESTION POR ESTATUS
//=========================================================

private LocalDateTime calcularFechaProximaGestion(
     String estatus,
     LocalDateTime fechaInicioEtapa) {


 if (estatus == null ||
     fechaInicioEtapa == null) {

     return null;
 }


 switch (estatus) {


     // =================================================
     // REGISTRADO
     // Tiene 7 dias para pasar a SEGUIMIENTO
     // =================================================

     case "REGISTRADO":

         return fechaInicioEtapa
                 .plusDays(7);


     // =================================================
     // SEGUIMIENTO
     // Tiene 3 dias para pasar a CITA AGENDADA
     // =================================================

     case "SEGUIMIENTO":

         return fechaInicioEtapa
                 .plusDays(3);


     // =================================================
     // CITA AGENDADA
     // Tiene 3 dias para pasar a COTIZACION
     // =================================================

     case "CITA_AGENDADA":

         return fechaInicioEtapa
                 .plusDays(3);


     // =================================================
     // COTIZACION
     // Tiene 2 dias para pasar a SOLICITUD
     // =================================================

     case "COTIZACION":

         return fechaInicioEtapa
                 .plusDays(2);


     // =================================================
     // SOLICITUD
     // Tiene 3 dias para concluir
     // =================================================

     case "SOLICITUD":

         return fechaInicioEtapa
                 .plusDays(3);


     // =================================================
     // CONCLUIDO
     // Ya no tiene proxima gestion
     // =================================================

     case "CONCLUIDO":

         return null;


     default:

         return null;
 }
}


//=========================================================
//SIGUIENTE ESTATUS
//=========================================================

private String obtenerSiguienteEstatus(
     String estatusActual) {


 if (estatusActual == null) {

     return null;
 }


 switch (estatusActual) {

     case "REGISTRADO":
         return "SEGUIMIENTO";

     case "SEGUIMIENTO":
         return "CITA_AGENDADA";

     case "CITA_AGENDADA":
         return "COTIZACION";

     case "COTIZACION":
         return "SOLICITUD";

     case "SOLICITUD":
         return "CONCLUIDO";

     default:
         return null;
 }
}


//=========================================================
//CALCULAR PROXIMA GESTION
//=========================================================

private LocalDateTime calcularProximaGestion(
     String nuevoEstatus,
     LocalDateTime fechaActualizacion) {


 switch (nuevoEstatus) {

     case "SEGUIMIENTO":

         // Seguimiento -> Cita agendada
         // 3 días
         return fechaActualizacion
                 .plusDays(3);


     case "CITA_AGENDADA":

         // Cita -> Cotización
         // 3 días
         return fechaActualizacion
                 .plusDays(3);


     case "COTIZACION":

         // Cotización -> Solicitud
         // 2 días
         return fechaActualizacion
                 .plusDays(2);


     case "SOLICITUD":

         // Solicitud -> Concluido
         // 3 días
         return fechaActualizacion
                 .plusDays(3);


     case "CONCLUIDO":

         // Ya no existe siguiente gestión
         return null;


     default:

         return null;
 }
}


    // =========================================================
    // OBTENER RUTA
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
    
 // =========================================================
 // OBTENER DETALLE DEL SOSPECHOSO
 // =========================================================

 public precrmSospechoso obtenerSospechosoPorId(
         Long pkSospechoso) {


     return sospechosoRepository
             .findById(
                 pkSospechoso
             )
             .orElseThrow(
                 () -> new IllegalArgumentException(
                     "No se encontró el sospechoso."
                 )
             );
 }
 
//=========================================================
//OBTENER SIGUIENTE ESTATUS PARA MOSTRAR EN PANTALLA
//=========================================================

public String obtenerSiguienteEstatusPantalla(
      String estatusActual) {


  if (estatusActual == null) {

      return null;
  }


  switch (estatusActual) {

      case "REGISTRADO":
          return "SEGUIMIENTO";

      case "SEGUIMIENTO":
          return "CITA_AGENDADA";

      case "CITA_AGENDADA":
          return "COTIZACION";

      case "COTIZACION":
          return "SOLICITUD";

      case "SOLICITUD":
          return "CONCLUIDO";

      case "CONCLUIDO":
          return null;

      default:
          return null;
  }
}

//=========================================================
//GUARDAR GESTION DEL SOSPECHOSO
//=========================================================

@Transactional
public void guardarGestion(
     Long pkSospechoso,
     String tipoGestion,
     String comentario,
     LocalDateTime fechaProximoSeguimiento,
     LocalDateTime fechaCita,
     String usuario) {


 // =====================================================
 // VALIDAR SOSPECHOSO
 // =====================================================

	precrmSospechoso sospechoso =
         sospechosoRepository
             .findById(pkSospechoso)
             .orElseThrow(
                 () -> new IllegalArgumentException(
                     "No se encontró el sospechoso."
                 )
             );


 // =====================================================
 // VALIDAR TIPO DE GESTION
 // =====================================================

 if (tipoGestion == null ||
     tipoGestion.trim().isEmpty()) {

     throw new IllegalArgumentException(
         "Debe seleccionar un tipo de gestión."
     );
 }


 // =====================================================
 // VALIDAR COMENTARIO
 // =====================================================

 if (comentario == null ||
     comentario.trim().isEmpty()) {

     throw new IllegalArgumentException(
         "Debe capturar un comentario."
     );
 }


 LocalDateTime ahora =
         LocalDateTime.now();


 // =====================================================
 // CREAR GESTION
 // =====================================================

 precrmGestion gestion =
         new precrmGestion();


 gestion.setFkSospechoso(
     pkSospechoso
 );


 gestion.setTipoGestion(
     tipoGestion
 );


 gestion.setComentario(
     comentario.trim()
 );


 gestion.setFechaProximoSeguimiento(
     fechaProximoSeguimiento
 );


 // Fecha de cita solamente tiene sentido
 // en CITA_AGENDADA

 if ("CITA_AGENDADA".equals(
         sospechoso.getEstatus())) {

     gestion.setFechaCita(
         fechaCita
     );

 } else {

     gestion.setFechaCita(
         null
     );
 }


 gestion.setUsuarioGestion(
     usuario
 );


 gestion.setFechaGestion(
     ahora
 );


 gestionRepository
     .saveAndFlush(
         gestion
     );


 // =====================================================
 // ACTUALIZAR SOSPECHOSO
 // =====================================================

 sospechoso.setFechaUltimaActualizacion(
     ahora
 );


 // Si el usuario programó un seguimiento,
 // éste pasa a ser la próxima gestión.

 if (fechaProximoSeguimiento != null) {

     sospechoso.setFechaProximaGestion(
         fechaProximoSeguimiento
     );

 }


 sospechosoRepository
     .saveAndFlush(
         sospechoso
     );
}

//=========================================================
//OBTENER BITACORA DEL SOSPECHOSO
//=========================================================

public List<precrmGestion> obtenerGestionesSospechoso(
     Long pkSospechoso) {


 return gestionRepository
         .findByFkSospechosoOrderByFechaGestionDesc(
             pkSospechoso
         );
}

//=========================================================
//OBTENER CATALOGO DE REPORTES ACTIVOS
//=========================================================

public List<precrmCatalogoReporte>
obtenerCatalogoReportes() {

 return catalogoReporteRepository
     .findByActivoOrderByOrdenAsc(
         1
     );

}

//=========================================================
//OBTENER HISTORIAL DE DESCARGAS
//=========================================================

public List<precrmReporteDescarga>
obtenerHistorialDescargas() {

 return reporteDescargaRepository
     .findAllByOrderByFechaDescargaDesc();

}

//=========================================================
//OBTENER REPORTE POR ID
//=========================================================

public precrmCatalogoReporte
obtenerReportePorId(
     Long pkReporte) {

 return catalogoReporteRepository
     .findById(
         pkReporte
     )
     .orElseThrow(
         () -> new RuntimeException(
             "No se encontró el reporte seleccionado."
         )
     );

}

//=========================================================
//OBTENER REPORTE POR CLAVE
//=========================================================

public precrmCatalogoReporte
obtenerReportePorClave(
     String claveReporte) {

 precrmCatalogoReporte reporte =
     catalogoReporteRepository
         .findByClaveReporte(
             claveReporte
         );


 if (reporte == null) {

     throw new RuntimeException(
         "No se encontró la configuración "
         + "del reporte: "
         + claveReporte
     );

 }


 return reporte;

}

//=========================================================
//REGISTRAR DESCARGA EXITOSA
//=========================================================

public precrmReporteDescarga
registrarDescargaExitosa(
     Long fkReporte,
     String usuario,
     String nombreArchivo,
     Integer totalRegistros) {


 precrmReporteDescarga descarga =
     new precrmReporteDescarga();


 descarga.setFkReporte(
     fkReporte
 );


 descarga.setUsuario(
     usuario
 );


 descarga.setFechaDescarga(
     LocalDateTime.now()
 );


 descarga.setNombreArchivo(
     nombreArchivo
 );


 descarga.setEstatusDescarga(
     "EXITOSO"
 );


 descarga.setTotalRegistros(
     totalRegistros
 );


 descarga.setMensajeError(
     null
 );


 return reporteDescargaRepository
     .saveAndFlush(
         descarga
     );

}

//=========================================================
//REGISTRAR ERROR EN GENERACION DE REPORTE
//=========================================================

public precrmReporteDescarga
registrarDescargaError(
     Long fkReporte,
     String usuario,
     String nombreArchivo,
     String mensajeError) {


 precrmReporteDescarga descarga =
     new precrmReporteDescarga();


 descarga.setFkReporte(
     fkReporte
 );


 descarga.setUsuario(
     usuario
 );


 descarga.setFechaDescarga(
     LocalDateTime.now()
 );


 descarga.setNombreArchivo(
     nombreArchivo
 );


 descarga.setEstatusDescarga(
     "ERROR"
 );


 descarga.setTotalRegistros(
     0
 );


 if (
     mensajeError != null
     && mensajeError.length() > 1000
 ) {

     mensajeError =
         mensajeError.substring(
             0,
             1000
         );

 }


 descarga.setMensajeError(
     mensajeError
 );


 return reporteDescargaRepository
     .saveAndFlush(
         descarga
     );

}

//=========================================================
//OBTENER FUNCIONALIDADES ACTIVAS
//=========================================================

public List<precrmFuncionalidad>
obtenerFuncionalidadesActivas() {

 return funcionalidadRepository
     .findByActivoOrderByOrdenAsc(
         1
     );
}

//=========================================================
//OBTENER PERMISOS ACTIVOS
//=========================================================

public List<precrmPermiso>
obtenerPermisosActivos() {

 return permisoRepository
     .findByActivo(
         1
     );
}

//=========================================================
//OBTENER ASIGNACIONES ACTIVAS
//=========================================================

public List<precrmUsuarioPermiso>
obtenerAsignacionesPermisosActivas() {

 return usuarioPermisoRepository
     .findByActivo(
         1
     );
}

//=========================================================
//ASIGNAR ACCESO A USUARIO
//=========================================================

@Transactional
public precrmUsuarioPermiso
asignarPermisoUsuario(

     String usuario,
     Long fkFuncionalidad,
     Long fkPermiso,
     String usuarioAdministrador) {


 // =====================================================
 // NORMALIZAR USUARIOS
 // =====================================================

 usuario =
     normalizarUsuarioPRECRM(
         usuario
     );

 usuarioAdministrador =
     normalizarUsuarioPRECRM(
         usuarioAdministrador
     );


 // =====================================================
 // VALIDAR USUARIO
 // =====================================================

 if (
     usuario == null
     || usuario.isEmpty()
 ) {

     throw new RuntimeException(
         "Debe seleccionar un usuario."
     );
 }


 // =====================================================
 // VALIDAR FUNCIONALIDAD
 // =====================================================

 precrmFuncionalidad funcionalidad =
     funcionalidadRepository
         .findById(
             fkFuncionalidad
         )
         .orElseThrow(
             () ->
                 new RuntimeException(
                     "No se encontró la funcionalidad seleccionada."
                 )
         );


 if (
     funcionalidad.getActivo() == null
     || funcionalidad.getActivo() != 1
 ) {

     throw new RuntimeException(
         "La funcionalidad seleccionada no está activa."
     );
 }


 // =====================================================
 // VALIDAR PERMISO
 // =====================================================

 precrmPermiso permiso =
     permisoRepository
         .findById(
             fkPermiso
         )
         .orElseThrow(
             () ->
                 new RuntimeException(
                     "No se encontró el permiso de acceso."
                 )
         );


 if (
     permiso.getActivo() == null
     || permiso.getActivo() != 1
 ) {

     throw new RuntimeException(
         "El permiso de acceso no está activo."
     );
 }


 // =====================================================
 // BUSCAR SI YA EXISTE
 // =====================================================

 Optional<precrmUsuarioPermiso>
     relacionExistente =
         usuarioPermisoRepository
             .findByUsuarioAndFkFuncionalidadAndFkPermiso(

                 usuario,

                 fkFuncionalidad,

                 fkPermiso
             );


 // =====================================================
 // SI YA EXISTE
 // =====================================================

 if (relacionExistente.isPresent()) {


     precrmUsuarioPermiso relacion =
         relacionExistente.get();


     // YA ESTA ACTIVO
     if (
         relacion.getActivo() != null
         && relacion.getActivo() == 1
     ) {

         throw new RuntimeException(
             "El usuario ya tiene acceso a esta funcionalidad."
         );
     }


     // REACTIVAR ACCESO

     relacion.setActivo(
         1
     );

     relacion.setFechaModificacion(
         LocalDateTime.now()
     );

     relacion.setUsuarioModificacion(
         usuarioAdministrador
     );


     return usuarioPermisoRepository
         .saveAndFlush(
             relacion
         );
 }


 // =====================================================
 // CREAR NUEVA ASIGNACION
 // =====================================================

 precrmUsuarioPermiso nuevaRelacion =
     new precrmUsuarioPermiso();


 nuevaRelacion.setUsuario(
     usuario
 );

 nuevaRelacion.setFkFuncionalidad(
     fkFuncionalidad
 );

 nuevaRelacion.setFkPermiso(
     fkPermiso
 );

 nuevaRelacion.setActivo(
     1
 );

 nuevaRelacion.setFechaAlta(
     LocalDateTime.now()
 );

 nuevaRelacion.setUsuarioAlta(
     usuarioAdministrador
 );

 nuevaRelacion.setFechaModificacion(
     null
 );

 nuevaRelacion.setUsuarioModificacion(
     null
 );


 return usuarioPermisoRepository
     .saveAndFlush(
         nuevaRelacion
     );
}

//=========================================================
//QUITAR ACCESO DE USUARIO A FUNCIONALIDAD
//=========================================================

@Transactional
public void quitarUsuarioFuncionalidad(

     String usuario,
     Long fkFuncionalidad,
     String usuarioAdministrador) {


 usuario =
     normalizarUsuarioPRECRM(
         usuario
     );

 usuarioAdministrador =
     normalizarUsuarioPRECRM(
         usuarioAdministrador
     );


 // =====================================================
 // OBTENER RELACIONES ACTIVAS
 // =====================================================

 List<precrmUsuarioPermiso> relaciones =
     usuarioPermisoRepository
         .findByUsuarioAndFkFuncionalidadAndActivo(

             usuario,

             fkFuncionalidad,

             1
         );


 if (
     relaciones == null
     || relaciones.isEmpty()
 ) {

     throw new RuntimeException(
         "El usuario no tiene acceso activo "
         + "a esta funcionalidad."
     );
 }


 // =====================================================
 // DESACTIVAR
 // =====================================================

 for (
     precrmUsuarioPermiso relacion
     : relaciones
 ) {


     relacion.setActivo(
         0
     );

     relacion.setFechaModificacion(
         LocalDateTime.now()
     );

     relacion.setUsuarioModificacion(
         usuarioAdministrador
     );
 }


 usuarioPermisoRepository
     .saveAll(
         relaciones
     );


 usuarioPermisoRepository
     .flush();
}

//=========================================================
//VALIDAR SI USUARIO TIENE ACCESO A FUNCIONALIDAD
//=========================================================

public boolean usuarioTieneAcceso(

     String usuario,
     String claveFuncionalidad) {


 usuario =
     normalizarUsuarioPRECRM(
         usuario
     );


 if (
     usuario == null
     || usuario.isEmpty()
 ) {

     return false;
 }


 // =====================================================
 // BUSCAR FUNCIONALIDAD
 // =====================================================

 precrmFuncionalidad funcionalidad =
     funcionalidadRepository
         .findByClaveFuncionalidad(
             claveFuncionalidad
         );


 if (
     funcionalidad == null
     || funcionalidad.getActivo() == null
     || funcionalidad.getActivo() != 1
 ) {

     return false;
 }


 // =====================================================
 // BUSCAR PERMISO ACCESO
 // =====================================================

 precrmPermiso permisoAcceso =
     permisoRepository
         .findByClavePermiso(
             "ACCESO"
         );


 if (
     permisoAcceso == null
     || permisoAcceso.getActivo() == null
     || permisoAcceso.getActivo() != 1
 ) {

     return false;
 }


 // =====================================================
 // VALIDAR RELACION
 // =====================================================

 return usuarioPermisoRepository
     .existsByUsuarioAndFkFuncionalidadAndFkPermisoAndActivo(

         usuario,

         funcionalidad
             .getPkFuncionalidad(),

         permisoAcceso
             .getPkPermiso(),

         1
     );
}

//=========================================================
//NORMALIZAR USUARIO PRE CRM
//=========================================================

private String normalizarUsuarioPRECRM(
     String usuario) {


 if (usuario == null) {

     return null;
 }


 return usuario
     .trim()
     .toLowerCase();
}

//=========================================================
//OBTENER SUCURSALES DISPONIBLES PARA PRE CRM
//=========================================================

public List<Map<String, Object>>
obtenerSucursalesDisponiblesPRECRM() {

 return sucursalConfigRepository
     .obtenerSucursalesDisponibles();
}

//=========================================================
//OBTENER SUCURSALES ACTIVAS EN PRE CRM
//=========================================================

public List<Map<String, Object>>
obtenerSucursalesActivasPRECRM() {

 return sucursalConfigRepository
     .obtenerSucursalesPrecrm();
}

//=========================================================
//ACTIVAR SUCURSAL EN PRE CRM
//=========================================================

@Transactional
public precrmSucursalConfig activarSucursalPRECRM(

     Integer fkSucursal,
     String usuario) {


 // =====================================================
 // VALIDAR SUCURSAL
 // =====================================================

 if (fkSucursal == null) {

     throw new RuntimeException(
         "Debe seleccionar una sucursal."
     );
 }


 // =====================================================
 // NORMALIZAR USUARIO
 // =====================================================

 usuario =
     normalizarUsuarioPRECRM(
         usuario
     );


 if (
     usuario == null
     || usuario.isEmpty()
 ) {

     usuario =
         "sistema";
 }


 // =====================================================
 // BUSCAR SI YA EXISTE CONFIGURACION
 // =====================================================

 Optional<precrmSucursalConfig>
     configuracionExistente =
         sucursalConfigRepository
             .findByFkSucursal(
                 fkSucursal
             );


 // =====================================================
 // YA EXISTE
 // =====================================================

 if (configuracionExistente.isPresent()) {


     precrmSucursalConfig configuracion =
         configuracionExistente.get();


     // ===============================================
     // YA ESTA ACTIVA
     // ===============================================

     if (
         configuracion.getActivoPrecrm() != null
         && configuracion.getActivoPrecrm() == 1
     ) {

         return configuracion;
     }


     // ===============================================
     // REACTIVAR
     // ===============================================

     configuracion.setActivoPrecrm(
         1
     );

     configuracion.setFechaModificacion(
         LocalDateTime.now()
     );

     configuracion.setUsuarioModificacion(
         usuario
     );


     return sucursalConfigRepository
         .saveAndFlush(
             configuracion
         );
 }


 // =====================================================
 // PRIMERA VEZ QUE SE AGREGA
 // =====================================================

 precrmSucursalConfig nuevaConfiguracion =
     new precrmSucursalConfig();


 nuevaConfiguracion.setFkSucursal(
     fkSucursal
 );

 nuevaConfiguracion.setActivoPrecrm(
     1
 );

 nuevaConfiguracion.setFechaAlta(
     LocalDateTime.now()
 );

 nuevaConfiguracion.setUsuarioAlta(
     usuario
 );


 return sucursalConfigRepository
     .saveAndFlush(
         nuevaConfiguracion
     );
}

//=========================================================
//DESACTIVAR SUCURSAL DE PRE CRM
//=========================================================

@Transactional
public precrmSucursalConfig desactivarSucursalPRECRM(

     Integer fkSucursal,
     String usuario) {


 // =====================================================
 // VALIDAR SUCURSAL
 // =====================================================

 if (fkSucursal == null) {

     throw new RuntimeException(
         "Debe seleccionar una sucursal."
     );
 }


 // =====================================================
 // NORMALIZAR USUARIO
 // =====================================================

 usuario =
     normalizarUsuarioPRECRM(
         usuario
     );


 if (
     usuario == null
     || usuario.isEmpty()
 ) {

     usuario =
         "sistema";
 }


 // =====================================================
 // BUSCAR CONFIGURACION
 // =====================================================

 precrmSucursalConfig configuracion =
     sucursalConfigRepository
         .findByFkSucursal(
             fkSucursal
         )
         .orElseThrow(
             () ->
                 new RuntimeException(
                     "La sucursal seleccionada "
                     + "no se encuentra configurada en PRE CRM."
                 )
         );


 // =====================================================
 // VALIDAR SI YA ESTA INACTIVA
 // =====================================================

 if (
     configuracion.getActivoPrecrm() == null
     || configuracion.getActivoPrecrm() == 0
 ) {

     return configuracion;
 }


 // =====================================================
 // DESACTIVAR
 // =====================================================

 configuracion.setActivoPrecrm(
     0
 );

 configuracion.setFechaModificacion(
     LocalDateTime.now()
 );

 configuracion.setUsuarioModificacion(
     usuario
 );


 return sucursalConfigRepository
     .saveAndFlush(
         configuracion
     );
}

//=========================================================
//VALIDAR SI UNA SUCURSAL ESTA ACTIVA EN PRE CRM
//=========================================================

public boolean sucursalActivaPRECRM(
     Integer fkSucursal) {


 if (fkSucursal == null) {

     return false;
 }


 return sucursalConfigRepository
     .existsByFkSucursalAndActivoPrecrm(
         fkSucursal,
         1
     );
}

//=========================================================
//OBTENER CLASIFICACIONES ACTIVAS
//=========================================================

public List<precrmClasificacion>
obtenerClasificacionesActivas() {

 return clasificacionRepository
         .findByActivoOrderByOrdenAsc(
             1
         );
}


//=========================================================
//OBTENER ESTATUS ACTIVOS
//=========================================================

public List<precrmEstatus>
obtenerEstatusActivos() {

 return estatusRepository
         .findByActivoOrderByOrdenAsc(
             1
         );
}

//=========================================================
//OBTENER TIPOS DE GESTION ACTIVOS
//=========================================================

public List<precrmTipoGestion>
obtenerTiposGestionActivos() {

 return tipoGestionRepository
         .findByActivoOrderByOrdenAsc(
             1
         );
}

//=========================================================
//VALIDAR ACCESO A UNA FUNCIONALIDAD PRE CRM
//=========================================================

public boolean tieneAcceso(
     String usuario,
     String funcionalidad) {

 if (usuario == null ||
     usuario.trim().isEmpty() ||
     funcionalidad == null ||
     funcionalidad.trim().isEmpty()) {

     return false;
 }

 return usuarioPermisoRepository
         .contarPermisoActivo(
             usuario.trim(),
             funcionalidad.trim(),
             "ACCESO"
         ) > 0;
}

}