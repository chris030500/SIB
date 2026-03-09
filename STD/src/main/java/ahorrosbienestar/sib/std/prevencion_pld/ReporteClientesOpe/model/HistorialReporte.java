package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    
    @Column(name = "DT_FechaInicio")
    private LocalDateTime fechaInicio;

    @Column(name = "DT_FechaFinal")
    private LocalDateTime fechaFin;
 
    @Column(name = "I_ANIO_GENERACION")
    private Integer añoGeneracion;
    
    @Column(name = "VC_TIPO_REPORTE")
    private String tipoReporte;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public Usuario getI_usuario() { return i_usuario; }
    public void setI_usuario(Usuario i_usuario) { this.i_usuario = i_usuario; }

    public Usuario getI_usuario_vobo() { return i_usuario_vobo; }
    public void setI_usuario_vobo(Usuario i_usuario_vobo) { this.i_usuario_vobo = i_usuario_vobo; }

    public Integer getEstatus() { return estatus; }
    public void setEstatus(Integer estatus) { this.estatus = estatus; }

    public LocalDateTime getFechaGeneracionvobo() { return fechaGeneracionvobo; }
    public void setFechaGeneracionvobo(LocalDateTime fechaGeneracionvobo) { this.fechaGeneracionvobo = fechaGeneracionvobo; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    
    public Integer getAñoGeneracion() { return añoGeneracion; }
    public void setañoGeneracion(Integer añoGeneracion) { this.añoGeneracion = añoGeneracion; }
    
    public String getTipoReporte() { return tipoReporte; }
    public void setTipoReporte(String tipoReporte) { this.tipoReporte = tipoReporte; }
}
    // Getters y Setters

    

