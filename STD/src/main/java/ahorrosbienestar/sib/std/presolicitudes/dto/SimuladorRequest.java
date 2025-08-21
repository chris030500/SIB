package ahorrosbienestar.sib.std.presolicitudes.dto;

import java.math.BigDecimal;

public class SimuladorRequest {
 private BigDecimal monto;
 private BigDecimal tasaAnual;
 private Integer plazo;
 private String frecuencia; // '1','2','3','4','5'

 public BigDecimal getMonto() { return monto; }
 public void setMonto(BigDecimal monto) { this.monto = monto; }

 public BigDecimal getTasaAnual() { return tasaAnual; }
 public void setTasaAnual(BigDecimal tasaAnual) { this.tasaAnual = tasaAnual; }

 public Integer getPlazo() { return plazo; }
 public void setPlazo(Integer plazo) { this.plazo = plazo; }

 public String getFrecuencia() { return frecuencia; }
 public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
}
