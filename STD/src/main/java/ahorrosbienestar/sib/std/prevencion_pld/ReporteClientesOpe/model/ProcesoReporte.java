package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.*;
//import java.math.BigDecimal;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "TBL_PLD_PROCESO_REPORTE")
public class ProcesoReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean programado;
    private LocalTime horaEjecucion;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public boolean isProgramado() { return programado; }
    public void setProgramado(boolean programado) { this.programado = programado; }

    public LocalTime getHoraEjecucion() { return horaEjecucion; }
    public void setHoraEjecucion(LocalTime horaEjecucion) { this.horaEjecucion = horaEjecucion; }
}

