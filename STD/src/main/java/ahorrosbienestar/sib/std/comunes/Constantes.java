package ahorrosbienestar.sib.std.comunes;

public class Constantes {
	
	//Constantes Rutas de Acceso
	public static final String RUTA_OFERTAS_AUTOMATICAS = "ofertasAutomaticas/indexOfertasAutomaticas";
	public static final String RUTA_OFERTAS_AUTOMATICAS_INDEX = "/ofertasAutomaticas/index";
	
	public static final String RUTA_ERROR = "errores/error_403";
	
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
    
    
    // Ejemplo de constantes para descripcion de Mensajes
    public static final String ACTUALIZACION_OK = "Actualizo Registro Correctamente.";
    public static final String ACTUALIZACION_ERROR = "Error al procesar. Intente mas tarde.";
    
    
    
    // Ejemplo de constantes para descripcion de Errores
    public static final String DESC_ERROR_ESTATUS_REGLA = "Solo se permite tener una Regla de Asignacion Activa.";
    
    //PERMISOS DE PANTALLAS
    public static final String USUARIOS_DESBLOQUEO = "usuarioDesbloqueo";
    public static final String USUARIOS_ADMIN_OFERTAS = "usuarioAdmin";
    public static final String USUARIOS_REGLAS_ASIGNACION = "usuarioReglasAsignacion";
    public static final String USUARIOS_SMS = "usuarioSMS";
    public static final String USUARIO_EJECUCION_GESTION = "usuarioEjecutaGestion";
    public static final String USUARIO_EJECUCION_BASE = "usuarioGeneraBases";
    

    private Constantes() {
        
    }
}
