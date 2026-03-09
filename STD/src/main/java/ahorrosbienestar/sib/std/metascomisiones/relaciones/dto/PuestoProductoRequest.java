package ahorrosbienestar.sib.std.metascomisiones.relaciones.dto;

public class PuestoProductoRequest {
    private Integer clavePuestoId;
    private Integer productoId;

    public Integer getClavePuestoId() {
        return clavePuestoId;
    }

    public void setClavePuestoId(Integer clavePuestoId) {
        this.clavePuestoId = clavePuestoId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
}
