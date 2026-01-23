package ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_DETALLE_CAMPANIA")
public class DetalleCampania {
	
	@Id
    @Column(name = "PK_CAMPANIA_ID")
    private Integer campaniaId;

    @Column(name = "VC_TIPO_CAMPANIA")
    private String tipoCampania;

    @Column(name = "VC_ESTATUS_CAMPANIA")
    private String estatusCampania;

    @JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FECHA_AUTORIZACION")
    private Date fechaAutorizacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_INICIO_CAMPANIA")
    private Date inicioCampania;

    @JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FIN_CAMPANIA")
    private Date finCampania;

    @JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FECHA_CANCELACION")
    private Date fechaCancelacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FECHA_REGISTRO")
    private Date fechaRegistro;

    @Column(name = "I_TIPO_CAMPANIA_ID")
    private Integer tipoCampaniaId;

	public Integer getCampaniaId() {
		return campaniaId;
	}

	public void setCampaniaId(Integer campaniaId) {
		this.campaniaId = campaniaId;
	}

	public String getTipoCampania() {
		return tipoCampania;
	}

	public void setTipoCampania(String tipoCampania) {
		this.tipoCampania = tipoCampania;
	}

	public String getEstatusCampania() {
		return estatusCampania;
	}

	public void setEstatusCampania(String estatusCampania) {
		this.estatusCampania = estatusCampania;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getInicioCampania() {
		return inicioCampania;
	}

	public void setInicioCampania(Date inicioCampania) {
		this.inicioCampania = inicioCampania;
	}

	public Date getFinCampania() {
		return finCampania;
	}

	public void setFinCampania(Date finCampania) {
		this.finCampania = finCampania;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getTipoCampaniaId() {
		return tipoCampaniaId;
	}

	public void setTipoCampaniaId(Integer tipoCampaniaId) {
		this.tipoCampaniaId = tipoCampaniaId;
	}
    
}

