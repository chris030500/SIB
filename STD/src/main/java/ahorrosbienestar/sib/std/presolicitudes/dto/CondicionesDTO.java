package ahorrosbienestar.sib.std.presolicitudes.dto;

public class CondicionesDTO {
    
    private Double montoMin;
    private Double montoMax;
    private Integer empresa;
    private String producto;
    private Integer tipoEmp;
    private Double tasa;
    private String frecuencia;
    private String plazo;

    // Constructor
    public CondicionesDTO(Double montoMin, Double montoMax, Integer empresa, String producto, Integer tipoEmp, Double tasa, String frecuencia, String plazo) {
        this.montoMin = montoMin;
        this.montoMax = montoMax;
        this.empresa = empresa;
        this.producto = producto;
        this.tipoEmp = tipoEmp;
        this.tasa = tasa;
        this.frecuencia = frecuencia;
        this.plazo = plazo;
    }

    // Getters y Setters
    public Double getMontoMin() { return montoMin; }
    public void setMontoMin(Double montoMin) { this.montoMin = montoMin; }

    public Double getMontoMax() { return montoMax; }
    public void setMontoMax(Double montoMax) { this.montoMax = montoMax; }

    public Integer getEmpresa() { return empresa; }
    public void setEmpresa(Integer empresa) { this.empresa = empresa; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Integer getTipoEmp() { return tipoEmp; }
    public void setTipoEmp(Integer tipoEmp) { this.tipoEmp = tipoEmp; }

    public Double getTasa() { return tasa; }
    public void setTasa(Double tasa) { this.tasa = tasa; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public String getPlazo() { return plazo; }
    public void setPlazo(String plazo) { this.plazo = plazo; }
}

