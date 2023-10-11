package ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto;

public class ProcesosBD {
	private int pk_id_proceso;
	private String nombreBase;
	private String diaInicio;
	private String diaFin;
	private String horaInicio;
	private String horaFin;
	private String rutaArchivo;
	public int getPk_id_proceso() {
		return pk_id_proceso;
	}
	public void setPk_id_proceso(int pk_id_proceso) {
		this.pk_id_proceso = pk_id_proceso;
	}
	public String getNombreBase() {
		return nombreBase;
	}
	public void setNombreBase(String nombreBase) {
		this.nombreBase = nombreBase;
	}
	public String getDiaInicio() {
		return diaInicio;
	}
	public void setDiaInicio(String diaInicio) {
		this.diaInicio = diaInicio;
	}
	public String getDiaFin() {
		return diaFin;
	}
	public void setDiaFin(String diaFin) {
		this.diaFin = diaFin;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
}
