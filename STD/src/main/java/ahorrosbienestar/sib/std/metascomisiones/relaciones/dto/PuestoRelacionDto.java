package ahorrosbienestar.sib.std.metascomisiones.relaciones.dto;

public class PuestoRelacionDto {
    private Integer clavePuestoId;
    private String nombrePuesto;

    public Integer getClavePuestoId() {
        return clavePuestoId;
    }

    public void setClavePuestoId(Integer clavePuestoId) {
        this.clavePuestoId = clavePuestoId;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }
}
