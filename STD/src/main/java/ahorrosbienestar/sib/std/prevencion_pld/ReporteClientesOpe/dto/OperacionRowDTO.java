package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperacionRowDTO {
  public final Integer clienteId; public final String nombreCliente; public final String tipoPersona;
  public final LocalDate fecha; public final BigDecimal monto;
  public final String destinoBeneficiario, instrumento, tipoProducto, descripcionMov, referenciaMov, naturalezaMov;
  public final Long numTransaccion, noContrato;

  public OperacionRowDTO(Integer clienteId, String nombreCliente, String tipoPersona,
                         LocalDate fecha, BigDecimal monto,
                         String destinoBeneficiario, String instrumento, String tipoProducto,
                         String descripcionMov, String referenciaMov, String naturalezaMov,
                         Long numTransaccion, Long noContrato) {
    this.clienteId=clienteId; this.nombreCliente=nombreCliente; this.tipoPersona=tipoPersona;
    this.fecha=fecha; this.monto=monto; this.destinoBeneficiario=destinoBeneficiario; this.instrumento=instrumento;
    this.tipoProducto=tipoProducto; this.descripcionMov=descripcionMov; this.referenciaMov=referenciaMov;
    this.naturalezaMov=naturalezaMov; this.numTransaccion=numTransaccion; this.noContrato=noContrato;
  }
}

