package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**  Tabla: SIB.TBL_PLD_PROCESO_REPORTE  */
@Entity
@Table(name = "TBL_PLD_PROCESO_REPORTE")
public class ProcesoReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProceso")
    private Long idProceso;

    @Column(name = "usuarioCarga", length = 100, nullable = false)
    private String usuarioCarga;

    @Column(name = "fechaCarga", nullable = false)
    private LocalDateTime fechaCarga = LocalDateTime.now();   // se asigna automáticamente

    /** 1 = activo, 0 = inactivo */
    @Column(name = "estatus", nullable = false)
    private Boolean estatus = true;

    /* ---- Relación con TBL_PLD_DESCARGAS ---- */
    @OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DescargaReporte> descargas = new ArrayList<>();

    /* ---------- getters & setters ---------- */
    public Long getIdProceso()               { return idProceso; }
    public void setIdProceso(Long idProceso) { this.idProceso = idProceso; }

    public String getUsuarioCarga()                 { return usuarioCarga; }
    public void setUsuarioCarga(String usuarioCarga){ this.usuarioCarga = usuarioCarga; }

    public LocalDateTime getFechaCarga()            { return fechaCarga; }
    public void setFechaCarga(LocalDateTime fecha)  { this.fechaCarga = fecha; }

    public Boolean getEstatus()         { return estatus; }
    public void setEstatus(Boolean estatus){ this.estatus = estatus; }

    public List<DescargaReporte> getDescargas() { return descargas; }
    public void setDescargas(List<DescargaReporte> descargas) { this.descargas = descargas; }
}
