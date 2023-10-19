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

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	private String titulo = ""; 
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
			System.out.print("Error:"+e.getMessage());
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
				try {
					incorrectHeaders = comparadorEncabezados.compararHeader(urlDirectorio + "/" + archivo);
					if (incorrectHeaders.isEmpty()) {
						System.out.println("El archivo y los encabezados son válidos.");
						logger.info("El archivo y los encabezados son válidos.");
						try {
							System.out.println("Entro en try para procesarArchivoExcel");
							procesarArchivoExcel(urlDirectorio + "/" + archivo);
							// Se manda a llamar el metodo para validar REUS y Clientes SN publicidad
							//comparativaREUSClienteSNPublicidad();
							procesarCampaniaFidelidad();
							indexOfertasAutomaticasService.generarArchivoOA();

						} catch (ParseException e) {
							
							logger.error("Error:"+e.getMessage());
							
							e.printStackTrace();
						}

					} else {
						System.out.println("Los siguientes encabezados no coinciden:");
						errores = "";
						for (String incorrectHeader : incorrectHeaders) {
							errores = errores+"-"+incorrectHeader+"<br>";
							logger.error("- " + incorrectHeader);
							
						}
						titulo = "ERROR ENCABEZADO";
						envioCorreoService.prepararCorreo(titulo,errores);
						
					}
				} catch (IOException e) {
					logger.error("Error:"+e.getMessage());
					e.printStackTrace();
				}
			} else {
				
				titulo = "NO EXISTE ARCHIVO"; 
				envioCorreoService.prepararCorreo(titulo,errores);
				logger.info("Archivo no encontrado.");
				
			}
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			titulo = "NO EXISTE ARCHIVO"; 
			envioCorreoService.prepararCorreo(titulo,errores);
			
			
		} finally {
			isRunning = false; // Desbloquear la ejecución
		}
	}

	@Transactional
	public void procesarArchivoExcel(String filePath) throws IOException, ParseException {
		String rowNull ="";	
		
		try {
			logger.info("procesarArchivoExcel");
			String nombreHoja = configService.valorPropiedad(propiedadNombreHoja).getVc_valor_propiedad();
			logger.info("nombreHoja: " + nombreHoja);
			FileInputStream inputStream = new FileInputStream(filePath);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(nombreHoja); // Cambiar al nombre correcto de la hoja

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Cambia el formato según lo que se encuentre
																			// en la celda
			List<String> errores = new ArrayList<>();
			int filaInicial = 1;
			
			for (int i = filaInicial; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null || row.getCell(0) == null) {
					break; // Detener la iteración si la fila es nula o la celda en la columna 0 es nula
				}
				
				logger.info("for de row: " + (i + 1));
				rowNull = "El renglon: " + (i + 1)+" contiene datos nulos, todos los datos son obligatorios.";
				
				try {
					double clienteIdCellValue = row.getCell(0).getNumericCellValue();
					int clienteId = (int) clienteIdCellValue; // Convertir a int
					System.out.println("clienteId: " + clienteId);
					logger.info("clienteId: " + clienteId);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en clienteId: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Cliente Id no es numérico");
					continue; // Ir a la siguiente fila
				}
				try {
					String nombreCliente = row.getCell(1).getStringCellValue();
					
					logger.info("nombreCliente: " + nombreCliente);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en nombreCliente: El valor no es una cadena de texto.");
					errores.add("Fila " + (i + 1) + " El campo Nombre Cliente no es una cadena de texto");
					continue; // Ir a la siguiente fila
				}

				try {
					String region = row.getCell(2).getStringCellValue();
					logger.info("region: " + region);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en region: El valor no es una cadena de texto.");
					errores.add("Fila " + (i + 1) + " El campo Region no es una cadena de texto");
					continue; // Ir a la siguiente fila
				}

				try {
					String nombreSucursal = row.getCell(3).getStringCellValue();
					logger.info("nombreSucursal: " + nombreSucursal);
				} catch (IllegalStateException e) {
					// TODO: handle exception
					logger.error("Error:"+e.getMessage());
					logger.info("Error en nombreSucursal: El valor no es una cadena de texto.");
					errores.add("Fila " + (i + 1) + " El campo Nombre Sucursal no es una cadena de texto");
					continue; // Ir a la siguiente fila
				}

				try {
					Date fechaNacimiento = row.getCell(4).getDateCellValue();
					logger.info("fechaNacimiento: " + dateFormat.format(fechaNacimiento));
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en fechaNacimiento: El valor no es una fecha valida.");
					errores.add("Fila " + (i + 1) + " El campo Fecha de Nacimiento no es una fecha valida ");
					continue;
				}

				try {
					double edadCellValue = row.getCell(5).getNumericCellValue();
					int edad = (int) edadCellValue;
					logger.info("edad: " + edad);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en edad: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Edad no es numérico");
					continue;
				}

				try {
					double creditoIdCellValue = row.getCell(6).getNumericCellValue();
					int creditoId = (int) creditoIdCellValue;
					logger.info("creditoId: " + creditoId);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en creditoId: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Credito Id no es numérico");
					continue;
				}

				try {
					String nombreProducto = row.getCell(7).getStringCellValue();
					logger.info("nombreProducto: " + nombreProducto);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en nombreProducto: El valor no es una cadena de texto.");
					errores.add("Fila " + (i + 1) + " El campo Nombre Producto no es una cadena de texto");
					continue;
				}

				try {
					double montoOriginal = row.getCell(8).getNumericCellValue();
					logger.info("montoOriginal: " + montoOriginal);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en montoOriginal: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Monto Original no es numérico");
					continue;
				}

				try {
					double maxDiasMoraCellValue = row.getCell(9).getNumericCellValue();
					int maxDiasMora = (int) maxDiasMoraCellValue;
					logger.info("maxDiasMora: " + maxDiasMora);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en maxDiasMora: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Maximo Dias Mora no es numérico");
					continue;
				}

				try {
					double telCelularCellValue = row.getCell(10).getNumericCellValue();
					int telCelular = (int) telCelularCellValue;
					logger.info("telCelular: " + telCelular);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en telCelular: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Telefono Celular no es numérico");
					continue;
				}

				try {
					double telRecadosCellValue = row.getCell(11).getNumericCellValue();
					int telRecados = (int) telRecadosCellValue;
					logger.info("telRecados: " + telRecados);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en telRecados: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Telefono Recados no es numérico");
					continue;
				}

				try {
					double telEmpleadoCellValue = row.getCell(12).getNumericCellValue();
					int telEmpleado = (int) telEmpleadoCellValue;
					logger.info("telEmpleado: " + telEmpleado);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en telEmpleado: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Telefono Empleado no es numérico");
					continue;
				}

				try {
					double telReferencia1CellValue = row.getCell(13).getNumericCellValue();
					int telReferencia1 = (int) telReferencia1CellValue;
					logger.info("telReferencia1: " + telReferencia1);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en telReferencia1: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Telefono Referencia 1 no es numérico");
					continue;
				}

				try {
					double telReferencia2CellValue = row.getCell(14).getNumericCellValue();
					int telReferencia2 = (int) telReferencia2CellValue;
					logger.info("telReferencia2: " + telReferencia2);

				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en telReferencia2: El valor no es numérico.");
					errores.add("Fila " + (i + 1) + " El campo Telefono Referencia 2 no es numérico");
					continue;
				}
				
				try {
					String plazoOferta = row.getCell(15).getStringCellValue();
					logger.info("plazoOferta: " + plazoOferta);
				} catch (IllegalStateException e) {
					logger.error("Error:"+e.getMessage());
					logger.info("Error en plazoOferta: El valor no es una cadena de texto.");
					errores.add("Fila " + (i + 1) + " El campo plazo oferta no es una cadena de texto");
					continue; // Ir a la siguiente fila
				}

			}

			logger.error("lista errores: " + errores.isEmpty());
			if (!errores.isEmpty()) {
				// Mostrar o manejar los errores encontrados antes de insertar en BD
				logger.info("HAY ERRORES: ");
				String erroresMns = "";
				for (String error : errores) {
					erroresMns = erroresMns+"-"+error+"<br>";
					System.out.println(error);
				}
				titulo = "ERROR CONTENIDO";
				envioCorreoService.prepararCorreo(titulo,erroresMns);
				logger.info("Error archivo:"+erroresMns);

			} else {
				// Llamar al método para insertar en BD solo si no hay errores
				logger.info("No hay errores");
				try {
				
					
					Date fechaRegistro = new Date();
					List<Fidelidad> listaFidelidad = new ArrayList<>();
					for (int i = filaInicial; i <= sheet.getLastRowNum(); i++) {
						Row row = sheet.getRow(i);
						if (row == null || row.getCell(0) == null) {
							contRegistro = i;
							break; // Detener la iteración si la fila es nula o la celda en la columna 0 es nula
						}
						
						DecimalFormat decimalFormat = new DecimalFormat("#");
						
						Fidelidad registroExcel = new Fidelidad();
						
						double clienteIdCellValue = row.getCell(0).getNumericCellValue();
						int clienteId = (int) clienteIdCellValue; // Convertir a int
						
						
						registroExcel.setI_cliente_id(clienteId);

						String nombreCliente = row.getCell(1).getStringCellValue();
						registroExcel.setVc_nombre_completo_cliente(nombreCliente);

						String region = row.getCell(2).getStringCellValue();
						registroExcel.setVc_region(region);

						String nombreSucursal = row.getCell(3).getStringCellValue();
						registroExcel.setVc_nombre_sucurs(nombreSucursal);

						Date fechaNacimiento = row.getCell(4).getDateCellValue();
						String fechaFormateada = dateFormat.format(fechaNacimiento); // Aquí obtienes la fecha formateada// como cadena
						// Convertir la cadena formateada nuevamente a un objeto Date
						Date fechaFormateadaComoDate = dateFormat.parse(fechaFormateada);
						registroExcel.setDt_fecha_nacimiento(fechaFormateadaComoDate);
						

						double edadCellValue = row.getCell(5).getNumericCellValue();
						int edad = (int) edadCellValue;
						registroExcel.setI_edad(edad);

						double creditoIdCellValue = row.getCell(6).getNumericCellValue();
						long creditoId = Long.parseLong(decimalFormat.format(creditoIdCellValue));
						registroExcel.setI_credito_id(creditoId);

						String nombreProducto = row.getCell(7).getStringCellValue();
						registroExcel.setVc_nombre_producto(nombreProducto);

						double montoOriginal = row.getCell(8).getNumericCellValue();
						registroExcel.setD_monto_original(montoOriginal);

						double maxDiasMoraCellValue = row.getCell(9).getNumericCellValue();
						int maxDiasMora = (int) maxDiasMoraCellValue;
						registroExcel.setI_max_dia_mora(maxDiasMora);

						
	                    //String formattedValue = decimalFormat.format(numericValue);
						
						double telCelularCellValue = row.getCell(10).getNumericCellValue();
						String telCelular = decimalFormat.format(telCelularCellValue);  
								//String.valueOf((int) telCelularCellValue);
						registroExcel.setVc_telefono_celular(telCelular);

						double telRecadosCellValue = row.getCell(11).getNumericCellValue();
						String telRecados = decimalFormat.format(telRecadosCellValue);
						registroExcel.setVc_telefono_recados(telRecados);

						double telEmpleadoCellValue = row.getCell(12).getNumericCellValue();
						String telEmpleado = decimalFormat.format(telEmpleadoCellValue);
						registroExcel.setVc_telefono_empleado(telEmpleado);

						double telReferencia1CellValue = row.getCell(13).getNumericCellValue();
						String telReferencia1 = decimalFormat.format(telReferencia1CellValue);
						registroExcel.setVc_telefono_referencia_1(telReferencia1);

						double telReferencia2CellValue = row.getCell(14).getNumericCellValue();
						String telReferencia2 = decimalFormat.format(telReferencia2CellValue);
						registroExcel.setVc_telefono_referencia_2(telReferencia2);
						
						String plazoOferta = row.getCell(15).getStringCellValue();
						registroExcel.setVc_plazo_oferta(plazoOferta);
						
						registroExcel.setDt_fecha_registro(fechaRegistro);
						
						
						listaFidelidad.add(registroExcel);
						if(listaFidelidad.size() == 1000) {
							fidelidadService.registrarFidelidad(listaFidelidad);
							listaFidelidad.clear();
						}
						
					}
					
					fidelidadService.registrarFidelidad(listaFidelidad);
					listaFidelidad.clear();
					
				} catch (Exception e) {
					
					logger.error("Error al insertar en la base de datos: " + e.getMessage());
					throw e;
				}

			}

			workbook.close();
			inputStream.close();
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			titulo = "ERROR CONTENIDO";
			envioCorreoService.prepararCorreo(titulo,rowNull);
		}
		
		

	}

	public void procesarCampaniaFidelidad() {
		
		try {
			logger.info("ejecutar CALL SIB.SP_PROCESAR_INFORMACION_CAMPANIAS(2)");
			jdbcTemplate.update("CALL SIB.SP_PROCESAR_INFORMACION_CAMPANIAS(2)");
			
			titulo = "REGISTROS DE FIDELIDAD";
			envioCorreoService.prepararCorreo(titulo,Integer.toString(contRegistro-1));
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
