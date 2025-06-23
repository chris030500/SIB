package ahorrosbienestar.sib.std.credito.campaniastemporales.dto;

public class CampaniaTemporalDTO {

	 private String nombreCampania;
	 private String descripcionCampania;
	 private Integer vigenciaMeses;
	 private String fechaInicio;
	 private String fechaFin;
	 private String fechaRegistro;
	 private Integer idTipoArchivo;
	 private String fechaEnvioMC;
	 private Boolean recurrente;
	public String getNombreCampania() {
		return nombreCampania;
	}
	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
	public String getDescripcionCampania() {
		return descripcionCampania;
	}
	public void setDescripcionCampania(String descripcionCampania) {
		this.descripcionCampania = descripcionCampania;
	}
	public Integer getVigenciaMeses() {
		return vigenciaMeses;
	}
	public void setVigenciaMeses(Integer vigenciaMeses) {
		this.vigenciaMeses = vigenciaMeses;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Integer getIdTipoArchivo() {
		return idTipoArchivo;
	}
	public void setIdTipoArchivo(Integer idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}
	public String getFechaEnvioMC() {
		return fechaEnvioMC;
	}
	public void setFechaEnvioMC(String fechaEnvioMC) {
		this.fechaEnvioMC = fechaEnvioMC;
	}
	public Boolean getRecurrente() {
		return recurrente;
	}
	public void setRecurrente(Boolean recurrente) {
		this.recurrente = recurrente;
	}
	 
}
