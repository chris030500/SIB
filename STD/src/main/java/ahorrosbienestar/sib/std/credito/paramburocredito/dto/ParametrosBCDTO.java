package ahorrosbienestar.sib.std.credito.paramburocredito.dto;

public class ParametrosBCDTO {

    private Integer idCampaniaSeleccionada;
    private Integer idRegistroParam;
	private Integer tipoEvaluacion; // "PROSPECTOR" o "ADVISER"
	private Double usoMaximo; // Porcentaje de uso máximo
	private Double mopMaximo; // MOP Máximo (puede ser opcional según el tipo)
	private Double bcsMinimo; // BCS Mínimo
	private Double iccMinimo; // ICC Mínimo
	private Integer riesgoMaximo; // valor de la opción seleccionada (i_opcion)
	
	
	public Integer getTipoEvaluacion() {
		return tipoEvaluacion;
	}
	public void setTipoEvaluacion(Integer tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}
	
	public Integer getIdRegistroParam() {
		return idRegistroParam;
	}
	public void setIdRegistroParam(Integer idRegistroParam) {
		this.idRegistroParam = idRegistroParam;
	}
	public Double getUsoMaximo() {
		return usoMaximo;
	}
	public void setUsoMaximo(Double usoMaximo) {
		this.usoMaximo = usoMaximo;
	}
	public Double getMopMaximo() {
		return mopMaximo;
	}
	public void setMopMaximo(Double mopMaximo) {
		this.mopMaximo = mopMaximo;
	}
	public Double getBcsMinimo() {
		return bcsMinimo;
	}
	public void setBcsMinimo(Double bcsMinimo) {
		this.bcsMinimo = bcsMinimo;
	}
	public Double getIccMinimo() {
		return iccMinimo;
	}
	public void setIccMinimo(Double iccMinimo) {
		this.iccMinimo = iccMinimo;
	}
	public Integer getRiesgoMaximo() {
		return riesgoMaximo;
	}
	public void setRiesgoMaximo(Integer riesgoMaximo) {
		this.riesgoMaximo = riesgoMaximo;
	}
	public Integer getIdCampaniaSeleccionada() {
		return idCampaniaSeleccionada;
	}
	public void setIdCampaniaSeleccionada(Integer idCampaniaSeleccionada) {
		this.idCampaniaSeleccionada = idCampaniaSeleccionada;
	}
	
}
