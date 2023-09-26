/**
 * 
 */
package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.configuracion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component.ArchivoValidadorComponent;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component.ComparadorEncabezadosComponent;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.service.FidelidadService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;

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

	private static final Logger logger = Logger.getLogger(FidelidadTareaConfiguracion.class);

	private ScheduledFuture<?> cronTaskFidelidad;

	private final Object cronTaskLockFidelidad = new Object(); // Para sincronización

	private boolean isRunning = false; // Variable de bloqueo
	private boolean lastExecutionSuccessful = false;
	private int lastSuccessfulMonth = -1;

	@PostConstruct
	public void init() {

		actualizarExpresionCronFidelidad();

	}

	public void actualizarExpresionCronFidelidad() {
		System.out.println("actualizarExpresionCronFidelidad:");
		synchronized (cronTaskLockFidelidad) {
			if (cronTaskFidelidad != null) {
				cronTaskFidelidad.cancel(true);
			}

			String nuevaExpresionCron = calcularExpresionCronFidelidad();
			cronTaskFidelidad = taskSchedulerFidelidad.schedule(() -> ejecutarFidelidad(),
					new CronTrigger(nuevaExpresionCron));
		}
	}

	private String calcularExpresionCronFidelidad() {
		System.out.println("calcularExpresionCronFidelidad:");
		Bases datos = basesService.findById(2).get();
		int intervalo = Integer.parseInt(datos.getVc_periodo_ejecucion_proceso());
		int horaInicio = LocalTime.parse(datos.getVc_hora_inicio_proceso(), DateTimeFormatter.ofPattern("HH:mm"))
				.getHour();
		int horaFin = LocalTime.parse(datos.getVc_hora_fin_proceso(), DateTimeFormatter.ofPattern("HH:mm")).getHour();
		int diaInicio = Integer.parseInt(datos.getVc_dia_inicio_proceso());
		int diaFin = Integer.parseInt(datos.getVc_dia_fin_proceso());

		return "0 */" + intervalo + " " + horaInicio + "-" + horaFin + " " + diaInicio + "," + diaFin + " * ?";
		// return "0 */" + intervalo + " " + horaInicio + "-" + horaFin + " " +
		// diaInicio + " * ?";
	}

	public synchronized void ejecutarFidelidad() {
		System.out.println("ejecutarFidelidad");
		Bases datos = basesService.findById(2).get();
		String archivo = configService.obtenerConfiguracion(69).getVc_valor_propiedad();
		String urlDirectorio = datos.getVc_ruta_proceso();
		System.out.println("urlDirectorio: " + urlDirectorio);
		System.out.println("archivo: " + archivo);
		//e
		lastExecutionSuccessful = false;
		if (lastExecutionSuccessful) {
			System.out.println("ejecutarFidelidad: Ejecución anterior fallida. Salida temprana.");
			return;
		}

		if (isRunning) {
			System.out.println("ejecutarFidelidad: El proceso ya está en ejecución. Salida temprana.");
			return;
		}
		try {
			isRunning = true; // Bloquear la ejecución
			if (archivoValidador.existeArchivo(urlDirectorio, archivo)) {
				List<String> incorrectHeaders = null;
				try {
					incorrectHeaders = comparadorEncabezados.compararHeader(urlDirectorio + "/" + archivo);
					if (incorrectHeaders.isEmpty()) {
						System.out.println("El archivo y los encabezados son válidos.");
						try {
							System.out.println("Entro en try para procesarArchivoExcel");
							procesarArchivoExcel(urlDirectorio + "/" + archivo);
							// Se manda a llamar el metodo para validar REUS y Clientes SN publicidad
							comparativaREUSClienteSNPublicidad();

							lastExecutionSuccessful = true; // Marcar como exitoso

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							System.out.println("Error al cargar la informacion en BD");
							System.out.println(e.getMessage());
							e.printStackTrace();
							lastExecutionSuccessful = false;
						}

					} else {
						System.out.println("Los siguientes encabezados no coinciden:");
						for (String incorrectHeader : incorrectHeaders) {
							System.out.println("- " + incorrectHeader);
						}
						lastExecutionSuccessful = false;
						//Enviamos el metodo para borrar el archivo excel
						eliminarArchivo(urlDirectorio + "/" + archivo);
					}
				} catch (IOException e) {
					e.printStackTrace();
					lastExecutionSuccessful = false;
				}
			} else {
				System.out.println("Archivo no encontrado.");
				lastExecutionSuccessful = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			lastExecutionSuccessful = false;
		} finally {
			isRunning = false; // Desbloquear la ejecución
		}
	}

	@Transactional
	public void procesarArchivoExcel(String filePath) throws IOException, ParseException {

		System.out.println("procesarArchivoExcel");
		String nombreHoja = configService.obtenerConfiguracion(70).getVc_valor_propiedad();
		System.out.println("nombreHoja: " + nombreHoja);
		FileInputStream inputStream = new FileInputStream(filePath);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheet(nombreHoja); // Cambiar al nombre correcto de la hoja

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Cambia el formato según lo que se encuentre
																			// en la celda
		List<String> errores = new ArrayList<>();
		int filaInicial = 1;
		// for (Row row : sheet) {
		for (int i = filaInicial; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null || row.getCell(0) == null) {
				break; // Detener la iteración si la fila es nula o la celda en la columna 0 es nula
			}
			System.out.println("for de row: " + (i + 1));
			// StringBuilder error = new StringBuilder("Registro " + (i + 1) + ": ");

			try {
				double clienteIdCellValue = row.getCell(0).getNumericCellValue();
				int clienteId = (int) clienteIdCellValue; // Convertir a int
				System.out.println("clienteId: " + clienteId);
			} catch (IllegalStateException e) {
				System.out.println("Error en clienteId: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Cliente Id no es numérico");
				continue; // Ir a la siguiente fila
			}
			try {
				String nombreCliente = row.getCell(1).getStringCellValue();
				System.out.println("nombreCliente: " + nombreCliente);
			} catch (IllegalStateException e) {
				System.out.println("Error en nombreCliente: El valor no es una cadena de texto.");
				errores.add("Fila " + (i + 1) + " El campo Nombre Cliente no es una cadena de texto");
				continue; // Ir a la siguiente fila
			}

			try {
				String region = row.getCell(2).getStringCellValue();
				System.out.println("region: " + region);
			} catch (IllegalStateException e) {
				System.out.println("Error en region: El valor no es una cadena de texto.");
				errores.add("Fila " + (i + 1) + " El campo Region no es una cadena de texto");
				continue; // Ir a la siguiente fila
			}

			try {
				String nombreSucursal = row.getCell(3).getStringCellValue();
				System.out.println("nombreSucursal: " + nombreSucursal);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en nombreSucursal: El valor no es una cadena de texto.");
				errores.add("Fila " + (i + 1) + " El campo Nombre Sucursal no es una cadena de texto");
				continue; // Ir a la siguiente fila
			}

			try {
				Date fechaNacimiento = row.getCell(4).getDateCellValue();
				System.out.println("fechaNacimiento: " + dateFormat.format(fechaNacimiento));
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en fechaNacimiento: El valor no es una fecha valida.");
				errores.add("Fila " + (i + 1) + " El campo Fecha de Nacimiento no es una fecha valida ");
				continue;
			}

			try {
				double edadCellValue = row.getCell(5).getNumericCellValue();
				int edad = (int) edadCellValue;
				System.out.println("edad: " + edad);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en edad: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Edad no es numérico");
				continue;
			}

			try {
				double creditoIdCellValue = row.getCell(6).getNumericCellValue();
				int creditoId = (int) creditoIdCellValue;
				System.out.println("creditoId: " + creditoId);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en creditoId: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Credito Id no es numérico");
				continue;
			}

			try {
				String nombreProducto = row.getCell(7).getStringCellValue();
				System.out.println("nombreProducto: " + nombreProducto);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en nombreProducto: El valor no es una cadena de texto.");
				errores.add("Fila " + (i + 1) + " El campo Nombre Producto no es una cadena de texto");
				continue;
			}

			try {
				double montoOriginal = row.getCell(8).getNumericCellValue();
				System.out.println("montoOriginal: " + montoOriginal);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en montoOriginal: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Monto Original no es numérico");
				continue;
			}

			try {
				double maxDiasMoraCellValue = row.getCell(9).getNumericCellValue();
				int maxDiasMora = (int) maxDiasMoraCellValue;
				System.out.println("maxDiasMora: " + maxDiasMora);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en maxDiasMora: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Maximo Dias Mora no es numérico");
				continue;
			}

			try {
				double telCelularCellValue = row.getCell(10).getNumericCellValue();
				int telCelular = (int) telCelularCellValue;
				System.out.println("telCelular: " + telCelular);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en telCelular: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Telefono Celular no es numérico");
				continue;
			}

			try {
				double telRecadosCellValue = row.getCell(11).getNumericCellValue();
				int telRecados = (int) telRecadosCellValue;
				System.out.println("telRecados: " + telRecados);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en telRecados: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Telefono Recados no es numérico");
				continue;
			}

			try {
				double telEmpleadoCellValue = row.getCell(12).getNumericCellValue();
				int telEmpleado = (int) telEmpleadoCellValue;
				System.out.println("telEmpleado: " + telEmpleado);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en telEmpleado: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Telefono Empleado no es numérico");
				continue;
			}

			try {
				double telReferencia1CellValue = row.getCell(13).getNumericCellValue();
				int telReferencia1 = (int) telReferencia1CellValue;
				System.out.println("telReferencia1: " + telReferencia1);
			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en telReferencia1: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Telefono Referencia 1 no es numérico");
				continue;
			}

			try {
				double telReferencia2CellValue = row.getCell(14).getNumericCellValue();
				int telReferencia2 = (int) telReferencia2CellValue;
				System.out.println("telReferencia2: " + telReferencia2);

			} catch (IllegalStateException e) {
				// TODO: handle exception
				System.out.println("Error en telReferencia2: El valor no es numérico.");
				errores.add("Fila " + (i + 1) + " El campo Telefono Referencia 2 no es numérico");
				continue;
			}

			// Luego, puedes llamar a un método para insertar los datos en la base de datos
			// fidelidadService.insertarEnBD(clienteId, nombreCliente, region,
			// nombreSucursal, fechaNacimiento, edad, creditoId, nombreProducto,
			// montoOriginal, maxDiasMora, telCelular, telRecados, telEmpleado,
			// telReferencia1, telReferencia2);
		}

		System.out.println("lista errores: " + errores.isEmpty());
		if (!errores.isEmpty()) {
			// Mostrar o manejar los errores encontrados antes de insertar en BD
			System.out.println("HAY ERRORES: ");
			for (String error : errores) {
				System.out.println(error);
			}

		} else {
			// Llamar al método para insertar en BD solo si no hay errores
			System.out.println("No hay errores");
			try {
				for (int i = filaInicial; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null || row.getCell(0) == null) {
						break; // Detener la iteración si la fila es nula o la celda en la columna 0 es nula
					}
					double clienteIdCellValue = row.getCell(0).getNumericCellValue();
					int clienteId = (int) clienteIdCellValue; // Convertir a int

					String nombreCliente = row.getCell(1).getStringCellValue();

					String region = row.getCell(2).getStringCellValue();

					String nombreSucursal = row.getCell(3).getStringCellValue();

					Date fechaNacimiento = row.getCell(4).getDateCellValue();
					String fechaFormateada = dateFormat.format(fechaNacimiento); // Aquí obtienes la fecha formateada
																					// como cadena

					// Convertir la cadena formateada nuevamente a un objeto Date
					Date fechaFormateadaComoDate = dateFormat.parse(fechaFormateada);

					double edadCellValue = row.getCell(5).getNumericCellValue();
					int edad = (int) edadCellValue;

					double creditoIdCellValue = row.getCell(6).getNumericCellValue();
					int creditoId = (int) creditoIdCellValue;

					String nombreProducto = row.getCell(7).getStringCellValue();

					double montoOriginal = row.getCell(8).getNumericCellValue();

					double maxDiasMoraCellValue = row.getCell(9).getNumericCellValue();
					int maxDiasMora = (int) maxDiasMoraCellValue;

					double telCelularCellValue = row.getCell(10).getNumericCellValue();
					String telCelular = String.valueOf((int) telCelularCellValue);

					double telRecadosCellValue = row.getCell(11).getNumericCellValue();
					String telRecados = String.valueOf((int) telRecadosCellValue);

					double telEmpleadoCellValue = row.getCell(12).getNumericCellValue();
					String telEmpleado = String.valueOf((int) telEmpleadoCellValue);

					double telReferencia1CellValue = row.getCell(13).getNumericCellValue();
					String telReferencia1 = String.valueOf((int) telReferencia1CellValue);

					double telReferencia2CellValue = row.getCell(14).getNumericCellValue();
					String telReferencia2 = String.valueOf((int) telReferencia2CellValue);
					// Obtener los valores de las celdas y llamar al método insertarEnBD
					// throw new RuntimeException("Simulando error al insertar en la base de
					// datos");
					fidelidadService.insertarEnBD(clienteId, nombreCliente, region, nombreSucursal,
							fechaFormateadaComoDate, edad, creditoId, nombreProducto, montoOriginal, maxDiasMora,
							telCelular, telRecados, telEmpleado, telReferencia1, telReferencia2);
				}
			} catch (Exception e) {
				System.out.println("Error al insertar en la base de datos: " + e.getMessage());
				throw e;
			}

		}

		workbook.close();
		inputStream.close();
		//eliminarArchivo(filePath);
	}

	public void comparativaREUSClienteSNPublicidad() {
		System.out.println("ejecutar comparativaREUSClienteSNPublicidad");
		jdbcTemplate.update("CALL SIB.SP_VALIDAR_Y_ACTUALIZAR_REGISTROS_FIDELIDAD()");
	}
	
	private void eliminarArchivo(String filePath) {
	    try {
	        Path path = Paths.get(filePath);
	        Files.deleteIfExists(path);
	        System.out.println("Archivo eliminado: " + filePath);
	    } catch (IOException e) {
	        System.out.println("Error al eliminar el archivo: " + e.getMessage());
	    }
	}

	// Método para actualizar la tarea programada con una nueva expresión
	// @Scheduled(cron = "0 0 8,20 * * *") // Ejecutar diario a las 8:00 A.M y 8:00
	// P.M
	// @Scheduled(fixedDelay = 60000) // Ejecutar cada 60 segundos (ajusta el valor
	// según tus necesidades)
	//fixedDelay = 60000 
	//@Scheduled(cron = "0 0 8,20 * * *")

	@Scheduled(fixedDelay = 60000) // Ejecutar cada 60 segundos (ajusta el valor según tus necesidades)
	public void continuarEjecucion() {
		System.out.println("lastExecutionSuccessful: " + lastExecutionSuccessful);
		if (!lastExecutionSuccessful) {
			actualizarExpresionCronFidelidad(); // Continuar ejecución según el cron en caso de error
		} else {
			int currentMonth = LocalDate.now().getMonthValue();
			if (lastSuccessfulMonth != currentMonth) {
				lastSuccessfulMonth = currentMonth; // Marcar el mes exitoso
				actualizarExpresionCronFidelidad(); // Reiniciar el cron para el próximo mes
			}
		}
	}

}
