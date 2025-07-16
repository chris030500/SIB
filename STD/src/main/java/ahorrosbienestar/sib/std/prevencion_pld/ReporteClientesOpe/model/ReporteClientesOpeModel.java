package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;
 
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

 
// -------- ReporteClientesOpeModel --------
@Entity
@Table(name = "TBL_PLD_AUDITORIAS_CLIENTES_OPERACIONES")
public class ReporteClientesOpeModel {
 
    @Id
    @Column(name = "cliente_id")
    private Integer clienteId;
 
    private String estatusCliente;
 
    private LocalDate fechaAlta;
 
    private String tipoCliente;
 
    private Integer numeroCliente;
 
    private String nombreCompleto;
 
    private String genero;
 
    private String tipoPersona;
 
    private String rfc;
 
    private LocalDate fechaNacimiento;
 
    private String entidadFederativaNacimiento;
 
    private String paisNacimiento;
 
    private String nacionalidad;
 
    private String ocupacion;
 
    private String numeroTelefono;
 
    private String correo;
 
    private Boolean representanteLegal;
 
    private String curp;
 
    private String colonia;
 
    private String calle;
 
    private String numeroCasa;
 
    private String numInterior;
 
    private String cp;
 
    private String municipio;
 
    private String estado;
 
    private String peps;
 
    private String actividadBMXID;
 
    private String actividadBMX;
 
    private String actividadSCIANID;
 
    private String nivelRiesgo;
 
    private Long montoCreditos;
 
    private String tipoOcupacion;
 
    private String lugarTrabajo;
 
    private Long actividadFRID;
 
    private String actividadFR;
 
    private String sectorEconomico;
 
    private Long volumenCreditos;
 
    private String actividadINEGI;
 
    private LocalDate fechaBaja;
 
    private String tipoClientePersona;
 
    private String nombre;
 
    private LocalDate fechaOperacion;
 
    private BigDecimal montoOperacion;
 
    private String destinatarioCredito;
 
    private String instrumentoMonetario;
 
    private String tipoProductoCliente;
 
    private String tipoPersonaOperacion;
 
    private String descripcionMovimiento;
 
    private String referenciaMovimiento;
 
    private String naturalezaMovimiento;
 
    private Long numTransaccion;
 
    private String productoDestino;
 
    private String nombreProductoDestino;
 
    private Long numeroContrato;
 
    // --- getters y setters ---
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
 
    public String getEstatusCliente() { return estatusCliente; }
    public void setEstatusCliente(String estatusCliente) { this.estatusCliente = estatusCliente; }
 
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
 
    public String getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(String tipoCliente) { this.tipoCliente = tipoCliente; }
 
    public Integer getNumeroCliente() { return numeroCliente; }
    public void setNumeroCliente(Integer numeroCliente) { this.numeroCliente = numeroCliente; }
 
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
 
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public String gettipoPersona() { return tipoPersona; }
    public void settipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }
 
    public String getrfc() { return rfc; }
    public void setrfc(String rfc) { this.rfc = rfc; }
    
    public LocalDate getfechaNacimiento() { return fechaNacimiento; }
    public void setfechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getentidadFederativaNacimiento() { return entidadFederativaNacimiento; }
    public void setentidadFederativaNacimiento(String entidadFederativaNacimiento) { this.entidadFederativaNacimiento = entidadFederativaNacimiento; }
 
    public String getpaisNacimiento() { return paisNacimiento; }
    public void setpaisNacimiento(String paisNacimiento) { this.paisNacimiento = paisNacimiento; }
    
    public String getnacionalidad() { return nacionalidad; }
    public void setpaisnacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    
    public String getocupacion() { return ocupacion; }
    public void setocupacion(String ocupacion) { this.ocupacion = ocupacion; }
    
    public String getnumeroTelefono() { return numeroTelefono; }
    public void setnumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    
    public String getcorreo() { return correo; }
    public void setcorreo(String correo) { this.correo = correo; }
    
    public Boolean getrepresentanteLegal() { return representanteLegal; }
    public void setrepresentanteLegal(Boolean representanteLegal) { this.representanteLegal = representanteLegal; }
    
    public String getcurp() { return curp; }
    public void setcurp(String curp) { this.curp = curp; }
    
    public String getcolonia() { return colonia; }
    public void setcolonia(String colonia) { this.colonia = colonia; }
    
    public String getcalle() { return calle; }
    public void setcalle(String calle) { this.calle = calle; }
    
    public String getnumeroCasa() { return numeroCasa; }
    public void setnumeroCasa(String numeroCasa) { this.numeroCasa = numeroCasa; }
    
    public String getnumInterior() { return numInterior; }
    public void setnumInterior(String numInterior) { this.numInterior = numInterior; }
    
    public String getmunicipio() { return municipio; }
    public void setmunicipio(String municipio) { this.municipio = municipio; }
    
    public String getcp() { return cp; }
    public void setcp(String cp) { this.cp = cp; }
    
    public String getestado() { return estado; }
    public void setestado(String estado) { this.estado = estado; }
    
    public String getpeps() { return peps; }
    public void setpeps(String peps) { this.peps = peps; }
    
    public String getactividadBMXID() { return actividadBMXID; }
    public void setactividadBMXID(String actividadBMXID) { this.actividadBMXID = actividadBMXID; }
    
    public String getactividadBMX() { return actividadBMX; }
    public void setactividadBMX(String actividadBMX) { this.actividadBMX = actividadBMX; }
    
    public String actividadSCIANID() { return actividadSCIANID; }
    public void actividadSCIANID(String actividadSCIANID) { this.actividadSCIANID = actividadSCIANID; }
    
    public String nivelRiesgo() { return nivelRiesgo; }
    public void nivelRiesgo(String nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }
    
    public Long montoCreditos() { return montoCreditos; }
    public void montoCreditos(Long montoCreditos) { this.montoCreditos = montoCreditos; }
    
    public String tipoOcupacion() { return tipoOcupacion; }
    public void tipoOcupacion(String tipoOcupacion) { this.tipoOcupacion = tipoOcupacion; }
    
    public String lugarTrabajo() { return lugarTrabajo; }
    public void lugarTrabajo(String lugarTrabajo) { this.lugarTrabajo = lugarTrabajo; }
    
    public Long actividadFRID() { return actividadFRID; }
    public void actividadFRID(Long actividadFRID) { this.actividadFRID = actividadFRID; }
    
    public String actividadFR() { return actividadFR; }
    public void actividadFR(String actividadFR) { this.actividadFR = actividadFR; }
    
    public String sectorEconomico() { return sectorEconomico; }
    public void sectorEconomico(String sectorEconomico) { this.sectorEconomico = sectorEconomico; }
    
    public Long volumenCreditos() { return volumenCreditos; }
    public void volumenCreditos(Long volumenCreditos) { this.volumenCreditos = volumenCreditos; }
    
    public String actividadINEGI() { return actividadINEGI; }
    public void actividadINEGI(String actividadINEGI) { this.actividadINEGI = actividadINEGI; }
    
    public LocalDate getfechaBaja() { return fechaBaja; }
    public void setfechaBaja(LocalDate fechaBaja) { this.fechaBaja = fechaBaja; }
    
    public String tipoClientePersona() { return tipoClientePersona; }
    public void tipoClientePersona(String tipoClientePersona) { this.tipoClientePersona = tipoClientePersona; }
    
    public String nombre() { return nombre; }
    public void nombre(String nombre) { this.nombre = nombre; }
    
    public BigDecimal getMontoOperacion() { return montoOperacion; }
    public void setMontoOperacion(BigDecimal montoOperacion) { this.montoOperacion = montoOperacion; }
 
    public LocalDate getFechaOperacion() { return fechaOperacion; }
    public void setFechaOperacion(LocalDate fechaOperacion) { this.fechaOperacion = fechaOperacion; }
    
    public String destinatarioCredito() { return destinatarioCredito; }
    public void destinatarioCredito(String destinatarioCredito) { this.destinatarioCredito = destinatarioCredito; }
    
    public String instrumentoMonetario() { return instrumentoMonetario; }
    public void instrumentoMonetario(String instrumentoMonetario) { this.instrumentoMonetario = instrumentoMonetario; }
    
    public String tipoProductoCliente() { return tipoProductoCliente; }
    public void tipoProductoCliente(String tipoProductoCliente) { this.tipoProductoCliente = tipoProductoCliente; }
    
    public String tipoPersonaOperacion() { return tipoPersonaOperacion; }
    public void tipoPersonaOperacion(String tipoPersonaOperacion) { this.tipoPersonaOperacion = tipoPersonaOperacion; }
    
    public String descripcionMovimiento() { return descripcionMovimiento; }
    public void descripcionMovimiento(String descripcionMovimiento) { this.descripcionMovimiento = descripcionMovimiento; }
    
    public String referenciaMovimiento() { return referenciaMovimiento; }
    public void referenciaMovimiento(String referenciaMovimiento) { this.referenciaMovimiento = referenciaMovimiento; }
    
    public String naturalezaMovimiento() { return naturalezaMovimiento; }
    public void naturalezaMovimiento(String naturalezaMovimiento) { this.naturalezaMovimiento = naturalezaMovimiento; }
    
    public Long numTransaccion() { return numTransaccion; }
    public void numTransaccion(Long numTransaccion) { this.numTransaccion = numTransaccion; }
    
    public String productoDestino() { return productoDestino; }
    public void productoDestino(String productoDestino) { this.productoDestino = productoDestino; }
    
    public String nombreProductoDestino() { return nombreProductoDestino; }
    public void nombreProductoDestino(String nombreProductoDestino) { this.nombreProductoDestino = nombreProductoDestino; }
    
    public Long numeroContrato() { return numeroContrato; }
    public void numeroContrato(Long numeroContrato) { this.numeroContrato = numeroContrato; }
}