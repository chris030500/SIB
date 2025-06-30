package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

//import jakarta.persistence.*;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBL_HISTORIAL_REPORTES_AUDITORIAS")
public class HistorialReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REPORTE")
    private Integer id;

    @Column(name = "FECHA_GENERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;

    @Column(name = "RANGO_FECHAS")
    private String rangoFechas;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;

    @Column(name = "CONFIRMADO")
    private Boolean confirmado;

    public HistorialReporte() {}

    public HistorialReporte(Date fechaGeneracion, String rangoFechas, String usuario, String nombreArchivo, Boolean confirmado) {
        this.fechaGeneracion = fechaGeneracion;
        this.rangoFechas = rangoFechas;
        this.usuario = usuario;
        this.nombreArchivo = nombreArchivo;
        this.confirmado = confirmado;
    }

    // Getters y setters (puedes generarlos con tu IDE)
}