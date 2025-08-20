package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClienteRowDTO {
  public final String tipo, estatus; public final LocalDate fechaAlta; public final Integer clienteId;
  public final LocalDate fechaNacimiento; public final String nombre, genero, entidadNac, paisNac, nacionalidad;
  public final String telefono, correo, representante, tipoPersona, rfc, curp, peps, ocupacion;
  public final Long actividadInegiId, actividadFrId, sectorEconId;
  public final String actividadInegi, actividadFr, sectorEcon, actividadBmx;
  public final Integer actividadBmxId, actividadScianId;
  public final String nivelRiesgo, calle, numCasa, numInterior, colonia, municipio, estado, cp, tipoOcupacion, lugarTrabajo;
  public final BigDecimal volumenCreditos;

  public ClienteRowDTO(String tipo, String estatus, LocalDate fechaAlta, Integer clienteId,
                       LocalDate fechaNacimiento, String nombre, String genero, String entidadNac, String paisNac, String nacionalidad,
                       String telefono, String correo, String representante, String tipoPersona, String rfc, String curp, String peps, String ocupacion,
                       Long actividadInegiId, String actividadInegi, Integer actividadBmxId, String actividadBmx,
                       Integer actividadScianId, String nivelRiesgo, String calle, String numCasa, String numInterior,
                       String colonia, String municipio, String estado, String cp, String tipoOcupacion, String lugarTrabajo,
                       Long actividadFrId, String actividadFr, Long sectorEconId, String sectorEcon, BigDecimal volumenCreditos) {
    this.tipo=tipo; this.estatus=estatus; this.fechaAlta=fechaAlta; this.clienteId=clienteId;
    this.fechaNacimiento=fechaNacimiento; this.nombre=nombre; this.genero=genero; this.entidadNac=entidadNac; this.paisNac=paisNac; this.nacionalidad=nacionalidad;
    this.telefono=telefono; this.correo=correo; this.representante=representante; this.tipoPersona=tipoPersona; this.rfc=rfc; this.curp=curp; this.peps=peps; this.ocupacion=ocupacion;
    this.actividadInegiId=actividadInegiId; this.actividadInegi=actividadInegi; this.actividadBmxId=actividadBmxId; this.actividadBmx=actividadBmx; this.actividadScianId=actividadScianId; this.nivelRiesgo=nivelRiesgo;
    this.calle=calle; this.numCasa=numCasa; this.numInterior=numInterior; this.colonia=colonia; this.municipio=municipio; this.estado=estado; this.cp=cp;
    this.tipoOcupacion=tipoOcupacion; this.lugarTrabajo=lugarTrabajo; this.actividadFrId=actividadFrId; this.actividadFr=actividadFr;
    this.sectorEconId=sectorEconId; this.sectorEcon=sectorEcon; this.volumenCreditos=volumenCreditos;
  }
}

