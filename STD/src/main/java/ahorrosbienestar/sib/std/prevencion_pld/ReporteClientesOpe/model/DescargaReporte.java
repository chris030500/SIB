package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.time.LocalTime;

@Entity
@Table(name = "TBL_PLD_DESCARGAS")
public class DescargaReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String ruta;
    private LocalDateTime fechaGeneracion;

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
}