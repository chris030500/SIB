package ahorrosbienestar.sib.std.credito.campaniastemporales.dto;

public class CampaniaTemporalDTO {

	 private Integer idCampaniaSeleccionada;
	 private String nombreCampania;
	 //private String descripcionCampania;
	 private Integer vigenciaMeses;
	 //private String fechaInicio;
	 private String fechaRegistro;
	 private String fechaEnvioMC;
	 private String fechaFin;
	 private Integer estatusCampania;
	 //private Integer idTipoArchivo;
	 private Boolean recurrente = false;
	 

	  // Constructor completo
	    public CampaniaTemporalDTO(Integer idCampaniaSeleccionada,
	                               String nombreCampania,
	                               Integer vigenciaMeses,
	                               String fechaRegistro,
	                               String fechaEnvioMC,
	                               String fechaFin,
	                               Integer estatusCampania,
	                               Boolean recurrente) {
	        this.idCampaniaSeleccionada = idCampaniaSeleccionada;
	        this.nombreCampania = nombreCampania;
	        this.vigenciaMeses = vigenciaMeses;
	        this.fechaRegistro = fechaRegistro;
	        this.fechaEnvioMC = fechaEnvioMC;
	        this.fechaFin = fechaFin;
	        this.estatusCampania = estatusCampania;
	        this.recurrente = recurrente;
	    }
	 
	public Integer getIdCampaniaSeleccionada() {
		return idCampaniaSeleccionada;
	}
	public void setIdCampaniaSeleccionada(Integer idCampaniaSeleccionada) {
		this.idCampaniaSeleccionada = idCampaniaSeleccionada;
	}
	public String getNombreCampania() {
		return nombreCampania;
	}
	public void setNombreCampania(String nombreCampania) {
		this.nombreCampania = nombreCampania;
	}
	/*
	public String getDescripcionCampania() {
		return descripcionCampania;
	}
	public void setDescripcionCampania(String descripcionCampania) {
		this.descripcionCampania = descripcionCampania;
	}*/
	public Integer getVigenciaMeses() {
		return vigenciaMeses;
	}
	public void setVigenciaMeses(Integer vigenciaMeses) {
		this.vigenciaMeses = vigenciaMeses;
	}
	/*public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}*/
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		 if (fechaFin == null || fechaFin.trim().isEmpty()) {
		        this.fechaFin = "1970-01-01";
		 } else {
		    	this.fechaFin = fechaFin;
		 }
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		
		 if (fechaRegistro == null || fechaRegistro.trim().isEmpty()) {
		        this.fechaRegistro = "1970-01-01";
		 } else {
			 	this.fechaRegistro = fechaRegistro;
		 }
	}
	/*
	public Integer getIdTipoArchivo() {
		return idTipoArchivo;
	}
	public void setIdTipoArchivo(Integer idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}*/
	
	public Integer getEstatusCampania() {
		return estatusCampania;
	}
	public void setEstatusCampania(Integer estatusCampania) {
		this.estatusCampania = estatusCampania;
	}
	public String getFechaEnvioMC() {
		return fechaEnvioMC;
	}
	public void setFechaEnvioMC(String fechaEnvioMC) {
		
		
		 if (fechaEnvioMC == null || fechaEnvioMC.trim().isEmpty()) {
		        this.fechaEnvioMC = "1970-01-01";
		 } else {
			 this.fechaEnvioMC = fechaEnvioMC;
		 }
		
	}
	public Boolean getRecurrente() {
		return recurrente;
	}
	public void setRecurrente(Boolean recurrente) {
		this.recurrente = recurrente;
	}


	 
}
