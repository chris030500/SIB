package ahorrosbienestar.sib.std.metascomisiones.relaciones.dto;

public class ProductoRelacionDto {
    private Integer productoId;
    private Integer numeroProducto;
    private String nombreProducto;

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getNumeroProducto() {
        return numeroProducto;
    }

    public void setNumeroProducto(Integer numeroProducto) {
        this.numeroProducto = numeroProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
