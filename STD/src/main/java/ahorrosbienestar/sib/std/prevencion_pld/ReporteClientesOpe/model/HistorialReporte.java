package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_HISTORIAL_REPORTES_AUDITORIAS", schema = "SIB")
public class HistorialReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_PROCESO_HISTORICO")
    private Integer id;

    @Column(name = "DT_FECHA_GENERACION", nullable = false)
    private LocalDateTime fechaGeneracion;
    
    @OneToOne
    @JoinColumn(name = "I_USUARIO", updatable = true)
	private Usuario i_usuario;
    
    @OneToOne
    @JoinColumn(name = "I_USUARIO_VOBO", updatable = true)
	private Usuario i_usuario_vobo;

    @Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Integer estatus;
    
    @Column(name = "DT_FECHA_VOBO", nullable = false)
    private LocalDateTime fechaGeneracionvobo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public Usuario getI_usuario() {
		return i_usuario;
	}

	public void setI_usuario(Usuario i_usuario) {
		this.i_usuario = i_usuario;
	}

	public Usuario getI_usuario_vobo() {
		return i_usuario_vobo;
	}

	public void setI_usuario_vobo(Usuario i_usuario_vobo) {
		this.i_usuario_vobo = i_usuario_vobo;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public LocalDateTime getFechaGeneracionvobo() {
		return fechaGeneracionvobo;
	}

	public void setFechaGeneracionvobo(LocalDateTime fechaGeneracionvobo) {
		this.fechaGeneracionvobo = fechaGeneracionvobo;
	}

    // Getters y Setters

    
}
