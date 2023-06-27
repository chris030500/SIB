package ahorrosbienestar.sib.std.comunes;

public class Constantes {
	private static String REPORT_FOLDER= "reports";
	private static String JASPER = ".jasper";
	private static String REPORTE_PDF = "PDF";
	private static String REPORTE_EXCEL = "EXCEL";
	private static String DB_URL = "http://10.150.102.82:8080/jasperserver";
	private static String USR_NAME = "juan.trejo";
	private static String DB_PASS = "Bienestar99";
	

	
	
	public static String getDB_URL() {
		return DB_URL;
	}
	public static void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}
	public static String getUSR_NAME() {
		return USR_NAME;
	}
	public static void setUSR_NAME(String uSR_NAME) {
		USR_NAME = uSR_NAME;
	}
	public static String getDB_PASS() {
		return DB_PASS;
	}
	public static void setDB_PASS(String dB_PASS) {
		DB_PASS = dB_PASS;
	}
	public static String getREPORT_FOLDER() {
		return REPORT_FOLDER;
	}
	public static void setREPORT_FOLDER(String rEPORT_FOLDER) {
		REPORT_FOLDER = rEPORT_FOLDER;
	}
	public static String getJASPER() {
		return JASPER;
	}
	public static void setJASPER(String jASPER) {
		JASPER = jASPER;
	}
	public static String getREPORTE_PDF() {
		return REPORTE_PDF;
	}
	public static void setREPORTE_PDF(String rEPORTE_PDF) {
		REPORTE_PDF = rEPORTE_PDF;
	}
	public static String getREPORTE_EXCEL() {
		return REPORTE_EXCEL;
	}
	public static void setREPORTE_EXCEL(String rEPORTE_EXCEL) {
		REPORTE_EXCEL = rEPORTE_EXCEL;
	}
}
