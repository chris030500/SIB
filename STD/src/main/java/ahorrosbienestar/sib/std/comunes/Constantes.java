
package ahorrosbienestar.sib.std.comunes;
 
public class Constantes {
    
    //Constantes Rutas de Acceso
    public static final String RUTA_OFERTAS_AUTOMATICAS = "ofertasAutomaticas/indexOfertasAutomaticas";
    public static final String RUTA_OFERTAS_AUTOMATICAS_INDEX = "/ofertasAutomaticas/index";
    public static final String RUTA_ERROR = "errores/error_403";
    public static final String RUTA_ERROR_PERMISO = "errores/error_permisos";
    
    
    //Rutas de Seguimiento
    public static final String RUTA_SEGUIMIENTO_GESTION = "operaciones/seguimientoGestion";
    public static final String RUTA_VENTAS_REPORTE = "operaciones/reportes";
    public static final String RUTA_CONFIGURACION_VENTAS = "operaciones/configuracion";
    
    //Constantes de usuarios
    public static final String USUARIO_EJECUTA_GESTION = "usuarioEjecutaGestion";
    public static final String USUARIO_EJECUTA_BASE = "usuarioGeneraBases";
    
    
    //Constantes de estatus
    public static final int ACTIVO = 1;
    
    //Constantes tipo de Base
    public static final int FIDELIDAD = 2;
    public static final int CAMPANAS_VIP = 3;
    
    //Constante mensajes y propiedades 
    public static final String MENSAJE_CAMPANA_VIP = "mensajeCampanasVIP";
    
    // constantes logger
    public static final String MENSAJE_DE_ERROR = "Error";
    public static final String MENSAJE_LOGGER_ERROR = "Error: ";
    
    // Ejemplo de constantes para tipo de Mensaje
    public static final String MENSAJE_ERROR = "error";
    public static final String MENSAJE_OK = "success";
    
    //Identificar de mensaje
    public static final String TEXTO_MNS = "mensaje";
    public static final String TIPO_MNS = "tipo";
    public static final String ENVIA_CORREOS = "envio";
    public static final String URL_REPORTE = "reporte";
    
    
    // Ejemplo de constantes para descripcion de Mensajes
    public static final String REGISTRO_OK = "Se Registro Correctamente.";
    public static final String ACTUALIZACION_OK = "Actualizo Registro Correctamente.";
    public static final String ACTUALIZACION_ERROR = "Error al procesar. Intente mas tarde.";
    public static final String REPORTE_OK = "Se Genero el reporte Correctamente.";
    
    //Mensajes Bases de Gestion
    public static final String ASIGNACION_OK = "Termino el proceso de Asignacion Gestiones.";
    public static final String SMS_OK = "Termino el proceso de Generacion de SMS";
    
    //Mensajes Reglas de asignacion
    public static final String REGLA_ERROR_DUPLICADO = "Existe Registro para Tipo de Cliente y Tipo de Cartera Seleccionado.";
    public static final String REGLA_ESTATUS = "Actualizo Estatus Correctamente.";
    public static final String REGLA_ESTATUS_ACTIVO = "Solo se permite tener una Regla de Asignacion Activa.";
    
  //Mensajes plantillas de SMS
    public static final String PLANTILLA_DUPLICADA = "Existe Registro para la Campaña y Clasificacion seleccionada.";
    public static final String NOMBRE_PLANTILLA_DUPLICADA = "Existe nombre de plantilla registrada.";
    
    
    //Mensajes Error
    public static final String TEXTO_KO = "Error: $error, Comuniquece con el administrador del sistema.";
    
    //identificadores en mensaje de error
    public static final String IDENTIFICADOR_ERROR = "$error";
    
    
    // Ejemplo de constantes para descripcion de Errores
    public static final String DESC_ERROR_ESTATUS_REGLA = "Solo se permite tener una Regla de Asignacion Activa.";
    
    //PERMISOS DE PANTALLAS
    public static final String USUARIOS_DESBLOQUEO = "usuarioDesbloqueo";
    public static final String USUARIOS_ADMIN_OFERTAS = "usuarioAdmin";
    public static final String USUARIOS_REGLAS_ASIGNACION = "usuarioReglasAsignacion";
    public static final String USUARIOS_SMS = "usuarioSMS";
    public static final String USUARIO_EJECUCION_GESTION = "usuarioEjecutaGestion";
    public static final String USUARIO_EJECUCION_BASE = "usuarioGeneraBases";
    public static final String USUARIO_TELEFONOS = "usuariosTelefonos";
    public static final String USUARIO_COD_ACCION_TELEFONO = "usuariosCodigosAccionR";
    public static final String USUARIO_CONSULTA = "usuarioConsulta";
    public static final String USUARIO_EXCLUSION_NOMINA = "usuarioExclusionesNomina";
    
    //PROPIEDAD DEL CONTROLLER DEL REPORTE
    public static final String CONTROLLER_JASPER_CONF = "generarReporte";
    //NOMBRES DE REPORTES
    public static final String REPORTE_ASIGNACION_X_SUCURSAL = "rpt_asignacion_sucursal";
    public static final String REPORTE_EXTRACCION_ASIGNACIONES = "rpt_extraccion_asignaciones";
    
    //NOMBRE DE PANTALLAS
    public static final String NOMBRE_PANTALLA_OFERTAS = "Ofertas Automaticas";
    
    //MENSAJES DE BASES
    public static final String MENSAJE_BASE_NOMINA = "Se proceso Base Nomina.";
    public static final String MENSAJE_BASE_FIDELIDAD = "Se proceso Base Fidelidad.";
    public static final String MENSAJE_BASE_PROSPECTOR = "Se proceso Base Prospector.";
    public static final String MENSAJE_BASE_CAMPANIA_VIP = "Se proceso Base Campañas VIP.";
    public static final String MENSAJE_BASE_FIDELIZACION_NOMINA = "Se proceso Base Fidelizacion Nomina.";
    public static final String MENSAJE_BASE_FIDELIZACION_RENOVADO = "Se proceso Base Fidelizacion Renovado.";
    
    
    //TIPOS DE BUSQUEDA CITA Y VENCIDOS
    public static final int BUSQUEDA_POR_USUARIO = 1;
    
    //MENSAJES SEGUIMIENTOS 
    public static final String ERROR_GESTION_ASIGNADA = "La gestión del cliente ya esta asignada a otro usuario y/o esta asignada a otra sucursal.";
    public static final String ERROR_SIN_REGISTROS = "La busqueda no genero registros. Verificar los parametros ingresados.";
    public static final String ERROR_PERMISOS = "No cuenta con los permisos para consultar las Gestiones.";
    
	//CATALOGOS DE PANTALLAS
	public static final int CATALOGO_GESTION = 14;
	public static final int CATALOGO_OPCIONES_BUSQUEDA = 15;
	
	
	//PROCESO A EJECUTAR
	public static final String PROCESO_ASIGNACION = "{call SIB.SP_PROCESOS_ASIGNACION (?)}";
	public static final String PROCESO_REASIGNACION = "{call SIB.SP_REASIG_X_FAL_SEGUIMIENTO (?)}";
	public static final String PROCESO_SMS = "{call SIB.SP_GENERAR_SMS(?)}";
	//PARAMETROS REPORTE ADJUNTO
	public static final String PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES = "?IDSUCURSAL=";
	
	// nombre reportes
	public static final String REPORTE_ASIGNACION = "rpt_asignacion_sucursal";
	public static final String REPORTE_REASIGNACION = "rpt_reasignacion_oferta";
	
	//PROCESO A EJECUTAR
	public static final String NOMBRE_REPORTE_ASIGNACION = "registros_asignados_suc";
	public static final String NOMBRE_REPORTE_REASIGNACION = "registros_reasignados_suc";
	
	//busqueda
	public static final int TIPO_BUSQUEDA_1 = 1;
	public static final int TIPO_BUSQUEDA_2 = 2;
	
	//Mensajes Ofertas Automaticas
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
	
	//Bases de procesamiento
	public static final int BASE_NOMINA = 1;
	public static final int BASE_CAMPANIAS_VIP = 3;
	public static final int BASE_PROSPECTOR = 4;
	public static final int BASE_FID_NOM = 5;
	public static final int BASE_FID_REN = 6;
	
	// nombre de propiedades de configuracion
	public static final String CRON_INICIAL = "cronInicialBase";
	public static final String CRON_ENVIO_CORREO_REASIGNACION = "cronEnviocorreo";
	
	//constantes sccb
	public static final String SCCB_SOLICITUDES_CREDITO = "sccb/solicitudesCredito";
	public static final String SCCB_HISTORIAL_COBRANZA = "sccb/historialCobranza";
	public static final String SCCB_CONSULTA_PAGARES = "sccb/consultaPagares";
	public static final String SCCB_CONSULTA_EXPEDIENTES = "sccb/expedientes";
	
	//indicadores Procesos
	public static final int PROCESO_MANUAL = 1;
	public static final int PROCESO_AUTOMATICO = 2;
	
	//enviar correos
	public static final String ENVIA_CORREO_ASIGNACION = "1";
	public static final String NO_ENVIA_CORREO_ASIGNACION = "0";
	
	//constantes Prevención PLD
	public static final String PLD_REPORTE_OPEACIONES_INUSUALES = "prevencion/reporteOperacionesInusuales";
	public static final String PLD_REPORTE_BASES_EBR = "prevencion/reporteBasesEBR";
	public static final int PLD_CAT_TIPO_PRODUCTO = 29;
	public static final int PLD_CAT_BUSQUEDA_CLIENTE = 30;
	public static final int PLD_CONTROL_REPORTE_JASPER = 1;
	public static final int PLD_CAT_PERIODO_EJECUCION_EBR = 37; 
	
    //nombres reportes Prevención PLD
    public static final String RPT_OPERACIONES_INUSUALES_X_TIPO_PRODUCTO = "rpt_operaciones_inusuales_x_tipo_producto";
    
  	//constantes PLD ReporteClientesOpe
    public static String PLD_REPORTE_CLIENTES_OPE = "prevencion/reporteClientesOpe";
    public static String RPT_CLIENTES_OPERACIONES_AUDITOR= "rpt_Clientes_Operaciones_Auditoria";
   	public static String RPT_OPERACIONES_AUDITORIA= "rpt_Operaciones_auditoria";
    public static String RPT_OPERACIONES_HISTORICO= "rpt_Operaciones_Historico";
   	public static String RPT_CLIENTES_HISTORICO= "rpt_Clientes_Historico";
    public static final String PEPS_CLIENTES = "{call SIB.SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES (?, ?)}";
    public static final String VISTO_BUENO = "{call SIB.SP_VISTO_BUENO_AUDITORIA (?, ?, ?, ?, ?, ?)}";

	//reportes Bases EBR
    public static final String RPT_EBR_BASE_CLIENTES = "rpt_ebr_base_clientes";
    public static final String RPT_EBR_BASE_OPERACIONES = "rpt_ebr_base_operaciones";
    public static final String RPT_EBR_BASE_REMESAS = "rpt_ebr_base_remesas";
    public static final String RPT_EBR_BASE_DESEMBOLSOS = "rpt_ebr_base_desembolsos";
    public static final String RPT_HISTORICO_EBR_BASE_CLIENTES = "rpt_historico_ebr_base_clientes";
    public static final String RPT_HISTORICO_EBR_BASE_OPERACIONES = "rpt_historico_ebr_base_operaciones";
    public static final String RPT_HISTORICO_EBR_BASE_REMESAS = "rpt_historico_ebr_base_remesas";
    public static final String RPT_HISTORICO_EBR_BASE_DESEMBOLSOS = "rpt_historico_ebr_base_desembolsos";
    
	//constantes marketing
    public static final String MARKETING_CREDITOS_DESEMBOLSADOS = "marketing/creditosDesembolsados";
    public static final String HORA_INICIO_SEMANAL = "HORA_INICIO_SEMANAL_SMS";
    public static final String HORA_FIN_SEMANAL = "HORA_FIN_SEMANAL_SMS";
    public static final String HORA_INICIO_FIN_SEMANA = "HORA_INICIO_FIN_SEMANA_SMS";
    public static final String HORA_FIN_FIN_SEMANA = "HORA_FIN_FIN_SEMANA_SMS";
    public static final String RECURRENCIA_SMS = "RECURRENCIA_ENVIO_SMS";
    
    //permisos de pantallas marketing
    public static final String USUARIO_ADMIN_MARKETING = "usrAdmin";
    public static final String USUARIOS_EDITOR_MARKETING = "usrEditor";
    
    //constantes credito
    public static final String CREDITO_SOLICITUDES_AUTORIZADAS = "credito/solicitudesAutorizadas";
    public static final String CREDITO_REPORTES = "credito/reportes";
    
    //nombres reportes Crédito
    public static final String RPT_SMS_SOLICITUDES_AUTORIZADAS = "rpt_sms_solicitudes_autorizadas";
    
    //Mensajes de error de autenticación
    public static final String USUARIO_NO_AUNTENTICADO = "El usuario no está autenticado.";
    public static final String USUARIO_NO_ENCONTRADO = "No se encontró el usuario en la base de datos.";

    private Constantes() {
        
    }
}