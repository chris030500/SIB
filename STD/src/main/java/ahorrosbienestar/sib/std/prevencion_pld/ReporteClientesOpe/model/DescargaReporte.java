package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.*;

/**  Tabla: SIB.TBL_PLD_DESCARGAS  */
@Entity
@Table(name = "TBL_PLD_DESCARGAS")
public class DescargaReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalle")
    private Long idDetalle;

    /* ---- FK al proceso ---- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProceso", nullable = false)
    private ProcesoReporte proceso;

    @Column(name = "idCliente", nullable = false)
    private Long idCliente;

    @Column(name = "nombreCliente", length = 255, nullable = false)
    private String nombreCliente;

    /* ---------- getters & setters ---------- */
    public Long getIdDetalle()                  { return idDetalle; }
    public void setIdDetalle(Long idDetalle)    { this.idDetalle = idDetalle; }

    public ProcesoReporte getProceso()                { return proceso; }
    public void setProceso(ProcesoReporte proceso)    { this.proceso = proceso; }

    public Long getIdCliente()                 { return idCliente; }
    public void setIdCliente(Long idCliente)   { this.idCliente = idCliente; }

    public String getNombreCliente()                { return nombreCliente; }
    public void setNombreCliente(String nombre)     { this.nombreCliente = nombre; }
}
