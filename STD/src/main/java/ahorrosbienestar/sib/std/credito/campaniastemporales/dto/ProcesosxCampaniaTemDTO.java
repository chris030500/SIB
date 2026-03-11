package ahorrosbienestar.sib.std.credito.campaniastemporales.dto;


public class ProcesosxCampaniaTemDTO {

	private String codigoCampania;
    private Integer idProcesoCampania;
    private String estatus;
    private String dtFechaExtraccion;
    private String dtFechaInicio;
    private String dtFechaFin;
    private String dtFechaEnvioMC;
    private String dtFechaRegistro;
    private String nombreusuario;

    // Constructor vacío
    public ProcesosxCampaniaTemDTO() {
    }

    // Constructor con todos los campos
    public ProcesosxCampaniaTemDTO(String codigoCampania, Integer idProcesoCampania, String estatus, String dtFechaExtraccion,
                                   String dtFechaInicio, String dtFechaFin,
                                   String dtFechaEnvioMC, String dtFechaRegistro,String nombreusuario) {
        this.codigoCampania = codigoCampania;
    	this.idProcesoCampania = idProcesoCampania;
        this.estatus = estatus;
        this.dtFechaExtraccion = dtFechaExtraccion;
        this.dtFechaInicio = dtFechaInicio;
        this.dtFechaFin = dtFechaFin;
        this.dtFechaEnvioMC = dtFechaEnvioMC;
        this.dtFechaRegistro = dtFechaRegistro;
        this.nombreusuario = nombreusuario;
    }

    // Getters y Setters    
    public Integer getIdProcesoCampania() {
        return idProcesoCampania;
    }

    public String getCodigoCampania() {
		return codigoCampania;
	}

	public void setCodigoCampania(String codigoCampania) {
		this.codigoCampania = codigoCampania;
	}

	public void setIdProcesoCampania(Integer idProcesoCampania) {
        this.idProcesoCampania = idProcesoCampania;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getDtFechaExtraccion() {
        return dtFechaExtraccion;
    }

    public void setDtFechaExtraccion(String dtFechaExtraccion) {
        this.dtFechaExtraccion = dtFechaExtraccion;
    }

    public String getDtFechaInicio() {
        return dtFechaInicio;
    }

    public void setDtFechaInicio(String dtFechaInicio) {
        this.dtFechaInicio = dtFechaInicio;
    }

    public String getDtFechaFin() {
        return dtFechaFin;
    }

    public void setDtFechaFin(String dtFechaFin) {
        this.dtFechaFin = dtFechaFin;
    }

    public String getDtFechaEnvioMC() {
        return dtFechaEnvioMC;
    }

    public void setDtFechaEnvioMC(String dtFechaEnvioMC) {
        this.dtFechaEnvioMC = dtFechaEnvioMC;
    }

    public String getDtFechaRegistro() {
        return dtFechaRegistro;
    }

    public void setDtFechaRegistro(String dtFechaRegistro) {
        this.dtFechaRegistro = dtFechaRegistro;
    }
    
    public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	@Override
    public String toString() {
        return "ProcesosxCampaniaTemDTO{" +
        		" codigoCampania=" +codigoCampania + '\'' +
                " idProcesoCampania=" + idProcesoCampania + '\'' +
                ", estatus='" + estatus + '\'' +
                ", dtFechaExtraccion='" + dtFechaExtraccion + '\'' +
                ", dtFechaInicio='" + dtFechaInicio + '\'' +
                ", dtFechaFin='" + dtFechaFin + '\'' +
                ", dtFechaEnvioMC='" + dtFechaEnvioMC + '\'' +
                ", dtFechaRegistro='" + dtFechaRegistro + '\'' +
                ", nombreusuario='" + nombreusuario + '\'' +
                '}';
    }
}