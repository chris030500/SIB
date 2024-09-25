package ahorrosbienestar.sib.std.comunes;

public class Constantes {

	// Constantes Rutas de Acceso
	public static final String RUTA_OFERTAS_AUTOMATICAS = "ofertasAutomaticas/indexOfertasAutomaticas";
	public static final String RUTA_OFERTAS_AUTOMATICAS_INDEX = "/ofertasAutomaticas/index";
	public static final String RUTA_ERROR = "errores/error_403";
	public static final String RUTA_ERROR_PERMISO = "errores/error_permisos";

	// Rutas de Seguimiento
	public static final String RUTA_SEGUIMIENTO_GESTION = "operaciones/seguimientoGestion";
	public static final String RUTA_VENTAS_REPORTE = "operaciones/reportes";
	public static final String RUTA_CONFIGURACION_VENTAS = "operaciones/configuracion";

	// Constantes de usuarios
	public static final String USUARIO_EJECUTA_GESTION = "usuarioEjecutaGestion";
	public static final String USUARIO_EJECUTA_BASE = "usuarioGeneraBases";

	// Constantes de estatus
	public static final int ACTIVO = 1;

	// Constantes tipo de Base
	public static final int FIDELIDAD = 2;
	public static final int CAMPANAS_VIP = 3;

	// Constante mensajes y propiedades
	public static final String MENSAJE_CAMPANA_VIP = "mensajeCampanasVIP";

	// constantes logger
	public static final String MENSAJE_DE_ERROR = "Error";

	// Ejemplo de constantes para tipo de Mensaje
	public static final String MENSAJE_ERROR = "error";
	public static final String MENSAJE_OK = "success";

	// Identificar de mensaje
	public static final String TEXTO_MNS = "mensaje";
	public static final String TIPO_MNS = "tipo";
	public static final String ENVIA_CORREOS = "envio";
	public static final String URL_REPORTE = "reporte";

	// Ejemplo de constantes para descripcion de Mensajes
	public static final String REGISTRO_OK = "Se Registro Correctamente.";
	public static final String ACTUALIZACION_OK = "Actualizo Registro Correctamente.";
	public static final String ACTUALIZACION_ERROR = "Error al procesar. Intente mas tarde.";
	public static final String REPORTE_OK = "Se Genero el reporte Correctamente.";
	public static final String REPORTE_KO = "Se Presento error al Generar el reporte, Intente mas tarde.";

	// Mensajes Bases de Gestion
	public static final String ASIGNACION_OK = "Termino el proceso de Asignacion Gestiones.";
	public static final String SMS_OK = "Termino el proceso de Generacion de SMS";

	// Mensajes Reglas de asignacion
	public static final String REGLA_ERROR_DUPLICADO = "Existe Registro para Tipo de Cliente y Tipo de Cartera Seleccionado.";
	public static final String REGLA_ESTATUS = "Actualizo Estatus Correctamente.";
	public static final String REGLA_ESTATUS_ACTIVO = "Solo se permite tener una Regla de Asignacion Activa.";

	// Mensajes plantillas de SMS
	public static final String PLANTILLA_DUPLICADA = "Existe Registro para la Campaña y Clasificacion seleccionada.";
	public static final String NOMBRE_PLANTILLA_DUPLICADA = "Existe nombre de plantilla registrada.";

	// Mensajes Error
	public static final String TEXTO_KO = "Error: $error, Comuniquece con el administrador del sistema.";

	// identificadores en mensaje de error
	public static final String IDENTIFICADOR_ERROR = "$error";

	// Ejemplo de constantes para descripcion de Errores
	public static final String DESC_ERROR_ESTATUS_REGLA = "Solo se permite tener una Regla de Asignacion Activa.";

	// PERMISOS DE PANTALLAS
	public static final String USUARIOS_DESBLOQUEO = "usuarioDesbloqueo";
	public static final String USUARIOS_ADMIN_OFERTAS = "usuarioAdmin";
	public static final String USUARIOS_REGLAS_ASIGNACION = "usuarioReglasAsignacion";
	public static final String USUARIOS_SMS = "usuarioSMS";
	public static final String USUARIO_EJECUCION_GESTION = "usuarioEjecutaGestion";
	public static final String USUARIO_EJECUCION_BASE = "usuarioGeneraBases";
	public static final String USUARIO_TELEFONOS = "usuariosTelefonos";
	public static final String USUARIO_COD_ACCION_TELEFONO = "usuariosCodigosAccionR";
	public static final String USUARIO_CONSULTA = "usuarioConsulta";

	// PROPIEDAD DEL CONTROLLER DEL REPORTE
	public static final String CONTROLLER_JASPER_CONF = "generarReporte";
	// NOMBRES DE REPORTES
	public static final String REPORTE_ASIGNACION_X_SUCURSAL = "rpt_asignacion_sucursal";

	// NOMBRE DE PANTALLAS
	public static final String NOMBRE_PANTALLA_OFERTAS = "Ofertas Automaticas";

	// MENSAJES DE BASES
	public static final String MENSAJE_BASE_NOMINA = "Se proceso Base Nomina.";
	public static final String MENSAJE_BASE_FIDELIDAD = "Se proceso Base Fidelidad.";
	public static final String MENSAJE_BASE_PROSPECTOR = "Se proceso Base Prospector.";
	public static final String MENSAJE_BASE_CAMPANIA_VIP = "Se proceso Base Campañas VIP.";
	public static final String MENSAJE_BASE_FIDELIZACION_NOMINA = "Se proceso Base Fidelizacion Nomina.";
	public static final String MENSAJE_BASE_FIDELIZACION_RENOVADO = "Se proceso Base Fidelizacion Renovado.";

	// TIPOS DE BUSQUEDA CITA Y VENCIDOS
	public static final int BUSQUEDA_POR_USUARIO = 1;

	// MENSAJES SEGUIMIENTOS
	public static final String ERROR_GESTION_ASIGNADA = "La gestión del cliente ya esta asignada a otro usuario y/o esta asignada a otra sucursal.";
	public static final String ERROR_SIN_REGISTROS = "La busqueda no genero registros. Verificar los parametros ingresados.";
	public static final String ERROR_PERMISOS = "No cuenta con los permisos para consultar las Gestiones.";

	// CATALOGOS DE PANTALLAS
	public static final int CATALOGO_GESTION = 14;
	public static final int CATALOGO_OPCIONES_BUSQUEDA = 15;

	// PROCESO A EJECUTAR
	public static final String PROCESO_ASIGNACION = "{call SIB.SP_PROCESOS_ASIGNACION (?)}";
	public static final String PROCESO_REASIGNACION = "{call SIB.SP_REASIG_X_FAL_SEGUIMIENTO (?)}";
	public static final String PROCESO_SMS = "{call SIB.SP_GENERAR_SMS(?)}";
	// PARAMETROS REPORTE ADJUNTO
	public static final String PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES = "?IDSUCURSAL=";

	// nombre reportes
	public static final String REPORTE_ASIGNACION = "rpt_asignacion_sucursal";
	public static final String REPORTE_REASIGNACION = "rpt_reasignacion_oferta";

	// PROCESO A EJECUTAR
	public static final String NOMBRE_REPORTE_ASIGNACION = "registros_asignados_suc";
	public static final String NOMBRE_REPORTE_REASIGNACION = "registros_reasignados_suc";

	// busqueda
	public static final int TIPO_BUSQUEDA_1 = 1;
	public static final int TIPO_BUSQUEDA_2 = 2;

	// Mensajes Ofertas Automaticas
	public static final String CLASIF_REGISTRADO = "No fue posible actualizar. Existe codigo clasificación registrado.";
	public static final String CODIGO_CLASIF_REGISTRADO = "Existe codigo clasificación registrado.";
	public static final String CLASIF_INACTIVO = "Se inactivo clasificación correctamente.";
	public static final String CLASIF_ACTIVO = "Se activo clasificación correctamente.";

	// TIPOS DE CORREOS
	public static final int CORREO_BASE_NOMINA = 5;
	public static final int CORREO_BASE_CAMPANIAS_VIP = 6;
	public static final int CORREO_BASE_FID_NOM = 7;
	public static final int CORREO_BASE_FID_RENO = 8;
	public static final int CORREO_ASIGNACION = 9;
	public static final int CORREO_REASIGNACION = 10;
	public static final int CORREO_BASE_PROSPECTOR = 11;

	// Bases de procesamiento
	public static final int BASE_NOMINA = 1;
	public static final int BASE_CAMPANIAS_VIP = 3;
	public static final int BASE_PROSPECTOR = 4;
	public static final int BASE_FID_NOM = 5;
	public static final int BASE_FID_REN = 6;

	// nombre de propiedades de configuracion
	public static final String CRON_INICIAL = "cronInicialBase";
	public static final String CRON_ENVIO_CORREO_REASIGNACION = "cronEnviocorreo";

	// constantes sccb
	public static final String SCCB_SOLICITUDES_CREDITO = "sccb/solicitudesCredito";
	public static final String SCCB_HISTORIAL_COBRANZA = "sccb/historialCobranza";
	public static final String SCCB_CONSULTA_PAGARES = "sccb/consultaPagares";
	public static final String SCCB_CONSULTA_EXPEDIENTES = "sccb/expedientes";

	// indicadores Procesos
	public static final int PROCESO_MANUAL = 1;
	public static final int PROCESO_AUTOMATICO = 2;

	// enviar correos
	public static final String ENVIA_CORREO_ASIGNACION = "1";
	public static final String NO_ENVIA_CORREO_ASIGNACION = "0";

	// constantes contabilidad
	public static final String CONTABILIDAD_CONFIGURACION = "contabilidad/parametrosConfiguracion";
	public static final String CONTABILIDAD_COSTO_NETO_FINANCIADO = "contabilidad/costoNetoFinanciado";
	public static final String CONTABILIDAD_CALCULO_EPRC = "contabilidad/calculoEPRC";

	// catalogos configuracion contabilidad
	public static final int CAT_CONCEPTOS = 27;
	public static final int CAT_COSTOS = 28;
	public static final int ESTATUS_BC = 1;

	// constantes reportes de contabilidad
	public static final String REPORTE_COSTO_NETO_FINANCIADO = "rpt_costoNetoFinanciado";
	public static final String REPORTE_COSTO_NETO_FINANCIADO_HIST = "rpt_costoNetoFinanciado_hist";
	public static final String REPORTE_EPRC = "rpt_calculo_eprc";
	public static final String REPORTE_EPRC_HIST = "rpt_eprc_hist";

	// Constantes Mensajes contabilidad
	public static final String ERROR_FORMATO_NUMERICO = "Formato de número incorrecto:";
	public static final String ERROR_PARAMETROS = "Error en los parámetros:";
	public static final String ERROR_GUARDAR = "Error al guardar los datos:";
	public static final String PROCESO_CNF_OK = "Termino el procesamiento de información. Favor de Generar el reporte y validar la información. ";
	public static final String VOBO_OK = "Se registro el vo.bo de la información procesada. ";
	public static final String PRORRATEO = "Se realizo el proceso de prorrateo.";
	public static final String REPORTE_HIST = "&parametros=?proceso=";

	// Proceso a ejecutar Contabilidad
	public static final String PROCESO_COSTO_NETO_FINANCIADO = "{call SIB.SP_COSTO_NETO_FINANCIADO (?,?,?)}";
	public static final String PROCESO_VOBO_OK = "{call SIB.PROCESO_VOBO_OK (?,?,?)}";
	public static final String PROCESO_PRORRATEO = "{call SIB.PROCESO_PRORRATEO (?,?,?)}";
	public static final String PROCESO_EPRC = "{call SP_PROCESAR_CALCULO_EPRC (?,?,?)}";
	public static final String PROCESO_VOBO_OK_EPRC = "{call SIB.SP_PROCESO_VOBO_OK_EPRC (?,?,?)}";

	// Garantias
	public static final String SQL_INSERTAR_GARANTIAS = "INSERT INTO TBL_GARANTIA_EPRC (I_NumCredito, VC_TipoGarantia, DC_MontoGarantia, VC_CalidadGarantia, DT_FechaCorte, I_IdProceso) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String SQL_SELECCIONAR_CONTROL_GARANTIAS = "SELECT * FROM TBL_CONTROL_GARANTIAS WHERE DT_FechaCorte = ?";
	public static final String SQL_INSERTAR_CONTROL_GARANTIAS = "INSERT INTO TBL_CONTROL_GARANTIAS (PK_IdProcesoComision, I_NumReg, DT_FechaProceso, I_Usuario, VC_Estatus, DT_FechaCorte) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String SQL_ACTUALIZAR_ESTATUS_CONTROL_GARANTIAS = "UPDATE TBL_CONTROL_GARANTIAS SET VC_Estatus = 'I' WHERE PK_IdProcesoComision= ?";
	public static final String SQL_OBTENER_SIGUIENTE_ID_PROCESO_GARANTIAS = "SELECT COALESCE(MAX(PK_IdProcesoComision), 0) + 1 FROM TBL_CONTROL_GARANTIAS";

	// Etapas
	public static final String INSERT_CAMBIO_ETAPA = "INSERT INTO TBL_CAMBIO_ETAPA (I_CREDITOID, I_TIPO_ETAPA, DT_FECHA_CORTE, I_IDPROCESO) VALUES (?, ?, ?, ?)";
	public static final String SELECT_CONTROL_ETAPA = "SELECT * FROM TBL_CONTROL_ETAPA WHERE DT_FechaCorte = ?";
	public static final String INSERT_CONTROL_ETAPA = "INSERT INTO TBL_CONTROL_ETAPA (PK_IdProcesoComision, I_NumReg, DT_FechaProceso, I_Usuario, VC_Estatus, DT_FechaCorte) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_CONTROL_ETAPA = "UPDATE TBL_CONTROL_ETAPA SET VC_Estatus = 'I' WHERE PK_IdProcesoComision = ?";
	public static final String SELECT_NEXT_ID_PROCESO = "SELECT COALESCE(MAX(PK_IdProcesoComision), 0) + 1 FROM TBL_CONTROL_ETAPA";

	// Facturas
	public static final String INSERT_DETALLE_FACTURA = "INSERT INTO TBL_DETALLE_FACTURA (PK_I_Folio, DT_Fecha_Emision, DC_Descuento, DC_Total, VC_Emisor_RFC, VC_Emisor_Nombre, VC_Receptor_RFC, VC_Receptor_Nombre) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String INSERT_FACTURA = "INSERT INTO TBL_FACTURA (DC_Cantidad, VC_Concepto, VC_Descripcion, VC_Oi, VC_Um, DC_Precio_Unit, DC_Sub_Total, DC_Descuento, DC_Total, FK_Folio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// Comisiones
	public static final String INSERTAR_COMISIONES = "INSERT INTO TBL_COMISIONES_CREDITOS (I_CREDITO, D_Costo_Comisiones, D_Ingresos_Comisiones, DT_FECHA_CORTE, I_IdProceso) VALUES (?, ?, ?, ?, ?)";
	public static final String SELECT_CONTROL_COMISIONES = "SELECT * FROM TBL_CONTROL_COMISIONES WHERE DT_FechaCorte = ?";
	public static final String INSERTAR_CONTROL_COMISIONES = "INSERT INTO TBL_CONTROL_COMISIONES (PK_IdProcesoComision, I_NumReg, DT_FechaProceso, I_Usuario, VC_Estatus, DT_FechaCorte) VALUES (?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_CONTROL_COMISIONES = "UPDATE TBL_CONTROL_COMISIONES SET VC_Estatus = 'I' WHERE PK_IdProcesoComision = ?";
	public static final String OBTENER_Siguiente_ID_PROCESO_COMISIONES = "SELECT COALESCE(MAX(PK_IdProcesoComision), 0) + 1 FROM TBL_CONTROL_COMISIONES";

	private Constantes() {

	}
}
