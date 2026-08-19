/**
 * 
 */
package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.configuracion;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component.ArchivoValidadorComponent;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component.ComparadorEncabezadosComponent;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.model.Fidelidad;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.service.FidelidadService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ValidarEjecutarCronBase;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.service.InformacionService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.index.service.IndexOfertasAutomaticasService;
/**
 * @author osvaldo.escamilla
 *
 */
@Configuration
@EnableScheduling
public class FidelidadTareaConfiguracion {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BasesService basesService;

	@Autowired
	ConfigService configService;

	@Autowired
	private TaskScheduler taskSchedulerFidelidad;

	@Autowired
	private ComparadorEncabezadosComponent comparadorEncabezados;

	@Autowired
	private ArchivoValidadorComponent archivoValidador;

	@Autowired
	private FidelidadService fidelidadService;
	
	@Autowired
	private EnvioCorreoService envioCorreoService;
	
	@Autowired
	private InformacionService informacionService;
	
	@Autowired
	private IndexOfertasAutomaticasService indexOfertasAutomaticasService;
		

	@Autowired
    public FidelidadTareaConfiguracion(TaskScheduler taskSchedulerFidelidad) {
        this.taskSchedulerFidelidad = taskSchedulerFidelidad;
    }

	private static final Logger logger = Logger.getLogger(FidelidadTareaConfiguracion.class);

	private boolean isRunning = false; // Variable de bloqueo
	private int contRegistro = 0;
	private int tipoCorreo = 0; 
	private String errores = "";
	private String propiedadNombreArchivo = "archivoFidelidad";
	private String propiedadNombreHoja = "hojaFidelidad";
	private int idBase = 2;
	@Value("${my.job.cron}")
	private String expresionCron;

	@PostConstruct
	public void init() {
		try {
			actualizarExpresionCronFidelidad();
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
		}

	}
	
	
	public void procesarCron() {
		logger.info("Proceso de fidelidad..");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(idBase);
		
		try {
			LocalTime horaActual = LocalTime.now();

	        // Crear un formateador para mostrar solo horas y minutos
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	        // Formatear la hora actual y mostrarla
	        String horaYMinutos = horaActual.format(formatter);
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(valBase.getI_ejecutar() == 1) {
					ejecutarFidelidad();
					
					logger.info("Se ejecuto proceso Fidelidad:"+horaYMinutos);
				}else {
					logger.info("No es tiempo de ejecutar proceso Fidelidd:"+horaYMinutos);
				}
			}			
			
		} catch (Exception e) {
			logger.error("el proceso no se ejecuto: "+e.getMessage());
		}
	}

	public void actualizarExpresionCronFidelidad() {
		try {
			
			logger.info("Proceso de fidelidad..");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(idBase);
			
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(!valBase.getExpresionCron().equals(expresionCron)) {
					expresionCron = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
		            CronTrigger trigger = new CronTrigger(expresionCron);
		            // Reemplaza el trigger existente con el nuevo trigger
		            taskSchedulerFidelidad.schedule(() -> procesarCron(), trigger);
		            logger.info("Se actualizo cron Fidelidad");
				}else {
					logger.info("No se actualizo cron Fidelidad");
		        }
				
			}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
	
	public synchronized void ejecutarFidelidad() {
		logger.info("ejecutarFidelidad");
		Bases datos = basesService.findById(2).get();
		
		
		String archivo = configService.valorPropiedad(propiedadNombreArchivo).getVc_valor_propiedad(); 
				//configService.obtenerConfiguracion(69).getVc_valor_propiedad();
		String urlDirectorio = datos.getVc_ruta_proceso();
		logger.info("urlDirectorio: " + urlDirectorio);
		logger.info("archivo: " + archivo);
		
		try {
			isRunning = true; // Bloquear la ejecución
			if (archivoValidador.existeArchivo(urlDirectorio, archivo)) {
				List<String> incorrectHeaders = null;
				logger.info("Inicia proceso de lectura de archivo.");
				try {
					incorrectHeaders = comparadorEncabezados.compararHeader(urlDirectorio + archivo);
					logger.info("ruta:"+urlDirectorio+archivo);
					if (incorrectHeaders.isEmpty()) {
						logger.info("El archivo y los encabezados son válidos.");
						try {
							
							logger.info("Entro en try para procesarArchivoExcel");
							procesarArchivoExcel(urlDirectorio + "/" + archivo);
							// Se manda a llamar el metodo para validar REUS y Clientes SN publicidad
							//comparativaREUSClienteSNPublicidad();
							procesarCampaniaFidelidad();
							indexOfertasAutomaticasService.generarArchivoOA();

						} catch (ParseException e) {
							
							logger.error("Error:"+e.getMessage());
						}

					} else {
						logger.error("Los siguientes encabezados no coinciden:");
						errores = "";
						for (String incorrectHeader : incorrectHeaders) {
							errores = errores+"-"+incorrectHeader+"<br>";
							logger.error("- " + incorrectHeader);
							
						}
						tipoCorreo = 2;
						logger.info("ERROR ENCABEZADO");
						envioCorreoService.prepararCorreo(tipoCorreo,errores);
						
					}
				} catch (IOException e) {
					logger.error("Error:"+e.getMessage());
				}
			} else {
				
				logger.info("NO EXISTE ARCHIVO");
				tipoCorreo = 1; 
				envioCorreoService.prepararCorreo(tipoCorreo,errores);
				logger.info("Archivo no encontrado.");
				
			}
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			tipoCorreo = 1;
			logger.info("NO EXISTE ARCHIVO");
			envioCorreoService.prepararCorreo(tipoCorreo,errores);
			
			
		} finally {
			isRunning = false; // Desbloquear la ejecución
		}
	}

	public void procesarArchivoExcel(String filePath) throws IOException, ParseException {

	    String rowNull = "";

	    try {

	        logger.info("Inicio procesarArchivoExcel");

	        String nombreHoja = configService
	                .valorPropiedad(propiedadNombreHoja)
	                .getVc_valor_propiedad();

	        logger.info("nombreHoja: {}"+nombreHoja);

	        Date fechaRegistro = new Date();

	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        DecimalFormat decimalFormat = new DecimalFormat("#");

	        List<String> errores = new ArrayList<>();
	        List<Fidelidad> listaFidelidad = new ArrayList<>(1000);

	        int filaInicial = 1;
	        int totalFilas = 0;

	        try (FileInputStream inputStream = new FileInputStream(filePath);
	             Workbook workbook = new XSSFWorkbook(inputStream)) {

	            Sheet sheet = workbook.getSheet(nombreHoja);

	            if (sheet == null) {
	                throw new IllegalArgumentException(
	                        "No se encontró la hoja: " + nombreHoja);
	            }

	            int ultimaFila = sheet.getLastRowNum();

	            logger.info("Total de filas a procesar: {}"+ ultimaFila);

	           
	            for (int i = filaInicial; i <= ultimaFila; i++) {

	                Row row = sheet.getRow(i);

	                if (row == null || row.getCell(0) == null) {
	                    contRegistro = i;
	                    break;
	                }

	                totalFilas++;

	                if (i % 1000 == 0) {
	                    logger.info("Validando fila: {} de {}"+i + 1+ultimaFila + 1);
	                }

	                rowNull = "El renglon: " + (i + 1)
	                        + " contiene datos nulos, todos los datos son obligatorios.";

	                try {

	                    /*
	                     * Cliente ID
	                     */
	                    double clienteIdCellValue =
	                            row.getCell(0).getNumericCellValue();

	                    int clienteId = (int) clienteIdCellValue;


	                    /*
	                     * Nombre Cliente
	                     */
	                    String nombreCliente =
	                            row.getCell(1).getStringCellValue();


	                    /*
	                     * Región
	                     */
	                    String region =
	                            row.getCell(2).getStringCellValue();


	                    /*
	                     * Nombre Sucursal
	                     */
	                    String nombreSucursal =
	                            row.getCell(3).getStringCellValue();


	                    /*
	                     * Fecha nacimiento
	                     */
	                    Date fechaNacimiento =
	                            row.getCell(4).getDateCellValue();


	                    /*
	                     * Edad
	                     */
	                    double edadCellValue =
	                            row.getCell(5).getNumericCellValue();

	                    int edad = (int) edadCellValue;


	                    /*
	                     * Crédito ID
	                     */
	                    double creditoIdCellValue =
	                            row.getCell(6).getNumericCellValue();

	                    long creditoId =
	                            Long.parseLong(
	                                    decimalFormat.format(creditoIdCellValue)
	                            );


	                    /*
	                     * Nombre producto
	                     */
	                    String nombreProducto =
	                            row.getCell(7).getStringCellValue();


	                    /*
	                     * Monto original
	                     */
	                    double montoOriginal =
	                            row.getCell(8).getNumericCellValue();


	                    /*
	                     * Máximo días mora
	                     */
	                    double maxDiasMoraCellValue =
	                            row.getCell(9).getNumericCellValue();

	                    int maxDiasMora =
	                            (int) maxDiasMoraCellValue;


	                    /*
	                     * Teléfono celular
	                     */
	                    double telCelularCellValue =
	                            row.getCell(10).getNumericCellValue();

	                    String telCelular =
	                            decimalFormat.format(telCelularCellValue);


	                    /*
	                     * Teléfono recados
	                     */
	                    double telRecadosCellValue =
	                            row.getCell(11).getNumericCellValue();

	                    String telRecados =
	                            decimalFormat.format(telRecadosCellValue);


	                    /*
	                     * Teléfono empleado
	                     */
	                    double telEmpleadoCellValue =
	                            row.getCell(12).getNumericCellValue();

	                    String telEmpleado =
	                            decimalFormat.format(telEmpleadoCellValue);


	                    /*
	                     * Teléfono referencia 1
	                     */
	                    double telReferencia1CellValue =
	                            row.getCell(13).getNumericCellValue();

	                    String telReferencia1 =
	                            decimalFormat.format(telReferencia1CellValue);


	                    /*
	                     * Teléfono referencia 2
	                     */
	                    double telReferencia2CellValue =
	                            row.getCell(14).getNumericCellValue();

	                    String telReferencia2 =
	                            decimalFormat.format(telReferencia2CellValue);


	                    /*
	                     * Plazo oferta
	                     */
	                    String plazoOferta =
	                            row.getCell(15).getStringCellValue();


	                   
	                    Fidelidad registroExcel = new Fidelidad();

	                    registroExcel.setI_cliente_id(clienteId);
	                    registroExcel.setVc_nombre_completo_cliente(nombreCliente);
	                    registroExcel.setVc_region(region);
	                    registroExcel.setVc_nombre_sucurs(nombreSucursal);
	                    registroExcel.setDt_fecha_nacimiento(fechaNacimiento);
	                    registroExcel.setI_edad(edad);
	                    registroExcel.setI_credito_id(creditoId);
	                    registroExcel.setVc_nombre_producto(nombreProducto);
	                    registroExcel.setD_monto_original(montoOriginal);
	                    registroExcel.setI_max_dia_mora(maxDiasMora);
	                    registroExcel.setVc_telefono_celular(telCelular);
	                    registroExcel.setVc_telefono_recados(telRecados);
	                    registroExcel.setVc_telefono_empleado(telEmpleado);
	                    registroExcel.setVc_telefono_referencia_1(telReferencia1);
	                    registroExcel.setVc_telefono_referencia_2(telReferencia2);
	                    registroExcel.setVc_plazo_oferta(plazoOferta);
	                    registroExcel.setDt_fecha_registro(fechaRegistro);

	                    listaFidelidad.add(registroExcel);

	                } catch (IllegalStateException e) {

	                    
	                    logger.error("Error en fila {}: {}"+i + 1);

	                    errores.add("Error en fila {}: {}"+i + 1);

	                } catch (Exception e) {

	                    String mensaje =
	                            "Fila " + (i + 1)
	                            + " contiene datos inválidos: "
	                            + e.getMessage();

	                    logger.error(mensaje, e);

	                    errores.add(mensaje);
	                }
	            }

	            logger.info("Fin lectura Excel. Filas procesadas: {}. Errores: {}"+ totalFilas+ errores.size());


	            if (!errores.isEmpty()) {

	                logger.error("El archivo contiene {} errores. No se realizará inserción."+errores.size()
	                );

	                StringBuilder erroresMns = new StringBuilder();

	                for (String error : errores) {
	                    erroresMns.append("-")
	                              .append(error)
	                              .append("<br>");

	                    logger.error(error);
	                }

	                tipoCorreo = 3;

	                envioCorreoService.prepararCorreo(
	                        tipoCorreo,
	                        erroresMns.toString()
	                );

	                logger.info("Archivo rechazado por errores de validación.");

	                return;
	            }

	          
	            logger.info("No existen errores. Registros válidos: {}"+listaFidelidad.size());

	            if (!listaFidelidad.isEmpty()) {

	                for (int inicio = 0;
	                     inicio < listaFidelidad.size();
	                     inicio += 1000) {

	                    int fin = Math.min(
	                            inicio + 1000,
	                            listaFidelidad.size()
	                    );

	                    List<Fidelidad> lote =
	                            listaFidelidad.subList(inicio, fin);

	                    logger.info("Insertando registros {} - {} de {}"+inicio + 1+fin+listaFidelidad.size());

	                    fidelidadService.registrarFidelidad(
	                            new ArrayList<>(lote)
	                    );
	                }
	            }

	            listaFidelidad.clear();

	            logger.info("Proceso terminado correctamente.");

	        }

	    } catch (Exception e) {

	        logger.error("Error procesando archivo Excel", e);

	        tipoCorreo = 3;

	        envioCorreoService.prepararCorreo(
	                tipoCorreo,
	                rowNull
	        );
	    }
	}
	
	public void procesarCampaniaFidelidad() {
		
		try {
			logger.info("ejecutar CALL SIB.SP_PROCESAR_INFORMACION_CAMPANIAS(2)");
			jdbcTemplate.update("CALL SIB.SP_PROCESAR_INFORMACION_CAMPANIAS(2)");
			
			tipoCorreo = 4;
			logger.info("REGISTROS DE FIDELIDAD");
			envioCorreoService.prepararCorreo(tipoCorreo,"");
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
		}
	}
	
	private void eliminarArchivo(String filePath) {
	    try {
	        Path path = Paths.get(filePath);
	        Files.deleteIfExists(path);
	        logger.info("Archivo eliminado: " + filePath);
	    } catch (IOException e) {
	    	logger.error("Error al eliminar el archivo: " + e.getMessage());
	    }
	}




}
