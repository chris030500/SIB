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
	
	// Ejemplo de constantes para tipo de Mensaje
    public static final String MENSAJE_ERROR = "error";
    public static final String MENSAJE_OK = "success";
    
    //Identificar de mensaje
    public static final String TEXTO_MNS = "mensaje";
    public static final String TIPO_MNS = "tipo";
    public static final String URL_REPORTE = "reporte";
    
    
    // Ejemplo de constantes para descripcion de Mensajes
    public static final String ACTUALIZACION_OK = "Actualizo Registro Correctamente.";
    public static final String ACTUALIZACION_ERROR = "Error al procesar. Intente mas tarde.";
    public static final String REPORTE_OK = "Se Genero el reporte Correctamente.";
    
    //Mensajes Bases de Gestion
    public static final String ASIGNACION_OK = "Termino el proceso de Asignacion Gestiones.";
    public static final String SMS_OK = "Termino el proceso de Generacion de SMS";
    
    
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
    
    //PROPIEDAD DEL CONTROLLER DEL REPORTE
    public static final String CONTROLLER_JASPER_CONF = "generarReporte";
    //NOMBRES DE REPORTES
    public static final String REPORTE_ASIGNACION_X_SUCURSAL = "rpt_asignacion_sucursal";
    
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
	public static final String ERROR_GESTION_ASIGNADA = "La Gestion del Cliente ya esta Asignada a otro usuario y/o esta asignada a otra sucursal.";
	public static final String ERROR_PERMISOS = "No cuenta con los permisos para consultar las Gestiones.";
    
	//CATALOGOS DE PANTALLAS
	public static final int CATALOGO_GESTION = 14;
	public static final int CATALOGO_OPCIONES_BUSQUEDA = 15;
	
	// TIPOS DE CORREOS
	public static final int CORREO_ASIGNACION = 9;
	public static final int CORREO_REASIGNACION = 10;
	
	//PROCESO A EJECUTAR
	public static final String PROCESO_ASIGNACION = "SIB.SP_PROCESOS_ASIGNACION";
	public static final String PROCESO_REASIGNACION = "SIB.SP_REASIG_X_FAL_SEGUIMIENTO";
	public static final String PROCESO_SMS = "SIB.SP_GENERAR_SMS";
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
	
    private Constantes() {
        
    }
}
