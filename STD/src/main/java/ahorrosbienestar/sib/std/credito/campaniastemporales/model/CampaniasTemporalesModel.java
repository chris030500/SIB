package ahorrosbienestar.sib.std.credito.campaniastemporales.model;

import java.time.LocalDateTime;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_TIPO_CAMPANIAS_TEMPORALES", schema = "SIB")
public class CampaniasTemporalesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_TIPO_CAMPANIA_TMP")
    private Integer idTipoCampaniaTmp;

    @Column(name = "VC_NOMBRE_CAMPANIA", nullable = false)
    private String nombreCampania;

    @Column(name = "VC_DESC_CAMPANIA", nullable = false)
    private String descripcionCampania;

    @Column(name = "I_VIGENCIA_CAMPANIA")
    private Integer vigenciaCampania;

    @Column(name = "DT_FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;

    @Column(name = "DT_FECHA_INICIO")
    private LocalDateTime fechaInicio;

    @Column(name = "DT_FECHA_FIN")
    private LocalDateTime fechaFin;

    @Column(name = "DT_FECHA_ENVIO_MC")
    private LocalDateTime fechaEnvioMC;

    @Column(name = "I_ESTATUS")
    private Integer estatus;

    @OneToOne
    @JoinColumn(name = "I_USER_REG")
    private Usuario usuario;

	public Integer getIdTipoCampaniaTmp() {
		return idTipoCampaniaTmp;
	}

	public void setIdTipoCampaniaTmp(Integer idTipoCampaniaTmp) {
		this.idTipoCampaniaTmp = idTipoCampaniaTmp;
	}

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

	public Integer getVigenciaCampania() {
		return vigenciaCampania;
	}

	public void setVigenciaCampania(Integer vigenciaCampania) {
		this.vigenciaCampania = vigenciaCampania;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalDateTime getFechaEnvioMC() {
		return fechaEnvioMC;
	}

	public void setFechaEnvioMC(LocalDateTime fechaEnvioMC) {
		this.fechaEnvioMC = fechaEnvioMC;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
}
