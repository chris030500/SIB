package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_PLD_AUDITORIAS_CLIENTES_OPERACIONES")
public class ReporteClientesOpeModel {

    @Id
    @Column(name = "ClienteID")
    private Integer clienteId;

    @Column(name = "estatus_cliente")
    private String estatusCliente;

    @Column(name = "FechaAlta")
    private LocalDate fechaAlta;  

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    @Column(name = "numero_cliente")
    private Integer numeroCliente;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "tipo_persona")
    private String tipoPersona;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "FechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "entidad_federativa_nacimiento")
    private String entidadFederativaNacimiento;

    @Column(name = "pais_nacimiento")
    private String paisNacimiento;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "ocupacion")
    private String ocupacion;

    @Column(name = "numero_telefono")
    private String numeroTelefono;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "representante_legal")
    private Boolean representanteLegal;

    @Column(name = "curp")
    private String curp;

    @Column(name = "colonia")
    private String colonia;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numeroCasa")
    private String numeroCasa;

    @Column(name = "num_interior")
    private String numInterior;

    @Column(name = "cp")
    private String cp;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "estado")
    private String estado;

    @Column(name = "Peps")
    private String peps;

    @Column(name = "ActividadBMXID")
    private String actividadBMXID;

    @Column(name = "ActividadBMX")
    private String actividadBMX;

    @Column(name = "ActividadSCIANID")
    private String actividadSCIANID;

    @Column(name = "nivel_riesgo")
    private String nivelRiesgo;

    @Column(name = "monto_creditos")
    private Long montoCreditos;

    @Column(name = "tipo_ocupacion")
    private String tipoOcupacion;

    @Column(name = "lugar_trabajo")
    private String lugarTrabajo;

    @Column(name = "ActividadFRID")
    private Long actividadFRID;

    @Column(name = "Actividad_FR")
    private String actividadFR;

    @Column(name = "SECTORECONOMICO")
    private String sectorEconomico;

    @Column(name = "volumen_creditos")
    private Long volumenCreditos;

    @Column(name = "ActividadINEGI")
    private String actividadINEGI;

    @Column(name = "FechaBaja")
    private LocalDate fechaBaja;

    @Column(name = "tipo_cliente_persona")
    private String tipoClientePersona;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "fecha_operacion")
    private LocalDate fechaOperacion;

    @Column(name = "monto_operacion")
    private BigDecimal montoOperacion;

    @Column(name = "destinatario_credito")
    private String destinatarioCredito;

    @Column(name = "instrumento_monetario")
    private String instrumentoMonetario;

    @Column(name = "tipo_producto_cliente")
    private String tipoProductoCliente;

    @Column(name = "tipo_persona_operacion")
    private String tipoPersonaOperacion;

    @Column(name = "descripcion_movimiento")
    private String descripcionMovimiento;

    @Column(name = "referencia_movimiento")
    private String referenciaMovimiento;

    @Column(name = "naturaleza_movimiento")
    private String naturalezaMovimiento;

    @Column(name = "num_transaccion")
    private Long numTransaccion;

    @Column(name = "producto_destino")
    private String productoDestino;

    @Column(name = "nombre_producto_destino")
    private String nombreProductoDestino;

    @Column(name = "numero_contrato")
    private Long numeroContrato;
    
    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
	
	public String getestatusCliente() {
		return estatusCliente;
	}

	public void setestatusCliente(String estatusCliente) {
		this.estatusCliente = estatusCliente;
	}
	
	public LocalDate getfechaAlta() {
		return fechaAlta;
	}

	public void setfechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public String gettipoClientea() {
		return tipoCliente;
	}

	public void settipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
	public Integer getnumeroCliente() {
		return numeroCliente;
	}

	public void setnumeroCliente(Integer numeroCliente) {
		this.numeroCliente = numeroCliente;
	}
	
	public String getnombreCompleto() {
		return nombreCompleto;
	}

	public void setnombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getgenero() {
		return genero;
	}

	public void setgenero(String genero) {
		this.genero = genero;
	}
	
	public String gettipoPersona() {
		return tipoPersona;
	}

	public void settipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	public String getrfc() {
		return rfc;
	}

	public void setrfc(String rfc) {
		this.rfc = rfc;
	}
	
	public LocalDate getfechaNacimiento() {
		return fechaNacimiento;
	}

	public void setfechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getentidadFederativaNacimiento() {
		return entidadFederativaNacimiento;
	}

	public void setentidadFederativaNacimiento(String entidadFederativaNacimiento) {
		this.entidadFederativaNacimiento = entidadFederativaNacimiento;
	}
	
	public String getpaisNacimiento() {
		return paisNacimiento;
	}

	public void setpaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}
	
	public String getnacionalidad() {
		return nacionalidad;
	}

	public void setnacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	public String getocupacion() {
		return ocupacion;
	}

	public void setocupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}
	
	public String getnumeroTelefono() {
		return numeroTelefono;
	}

	public void setnumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	
	public String getcorreo() {
		return correo;
	}

	public void setcorreo(String correo) {
		this.correo = correo;
	}
	
	public Boolean getrepresentanteLegal() {
		return representanteLegal;
	}

	public void setrepresentanteLegal(Boolean representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	
	public String getcurp() {
		return curp;
	}

	public void setcurp(String curp) {
		this.curp = curp;
	}
	
	public String getcolonia() {
		return colonia;
	}

	public void setcolonia(String colonia) {
		this.colonia = colonia;
	}
	
	public String getcalle() {
		return calle;
	}

	public void setcalle(String calle) {
		this.calle = calle;
	}
	
	public String getnumeroCasa() {
		return numeroCasa;
	}

	public void setnumeroCasa(String numeroCasa) {
		this.numeroCasa = numeroCasa;
	}
	
	public String getnumInterior() {
		return numInterior;
	}

	public void setnumInterior(String numInterior) {
		this.numInterior = numInterior;
	}
	
	public String getcp() {
		return cp;
	}

	public void setcp(String cp) {
		this.cp = cp;
	}
	
	public String getmunicipio() {
		return municipio;
	}

	public void setmunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public String getestado() {
		return estado;
	}

	public void setestado(String estado) {
		this.estado = estado;
	}
	
	public String getpeps() {
		return peps;
	}

	public void setpeps(String peps) {
		this.peps = peps;
	}
	
	public String getactividadBMXID() {
		return actividadBMXID;
	}

	public void setactividadBMXID(String actividadBMXID) {
		this.actividadBMXID = actividadBMXID;
	}
	
	public String getactividadBMX() {
		return actividadBMX;
	}

	public void setactividadBMX(String actividadBMX) {
		this.actividadBMX = actividadBMX;
	}
	
	public String getnivelRiesgo() {
		return nivelRiesgo;
	}

	public void setnivelRiesgo(String nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}
	
	public Long getmontoCreditos() {
		return montoCreditos;
	}

	public void setmontoCreditos(Long montoCreditos) {
		this.montoCreditos = montoCreditos;
	}
	
	public String gettipoOcupacion() {
		return tipoOcupacion;
	}

	public void settipoOcupacion(String tipoOcupacion) {
		this.tipoOcupacion = tipoOcupacion;
	}
	
	public String getlugarTrabajo() {
		return lugarTrabajo;
	}

	public void setlugarTrabajo(String lugarTrabajo) {
		this.lugarTrabajo = lugarTrabajo;
	}
	
	public Long getactividadFRID() {
		return actividadFRID;
	}

	public void setactividadFRID(Long actividadFRID) {
		this.actividadFRID = actividadFRID;
	}
	
	public String getactividadFR() {
		return actividadFR;
	}

	public void setactividadFR(String actividadFR) {
		this.actividadFR = actividadFR;
	}
	
	public String getsectorEconomico() {
		return sectorEconomico;
	}

	public void setsectorEconomico(String sectorEconomico) {
		this.sectorEconomico = sectorEconomico;
	}
	
	public Long getvolumenCreditos() {
		return volumenCreditos;
	}

	public void setvolumenCreditos(Long volumenCreditos) {
		this.volumenCreditos = volumenCreditos;
	}
	
	public String getactividadINEGI() {
		return actividadINEGI;
	}

	public void setactividadINEGI(String actividadINEGI) {
		this.actividadINEGI = actividadINEGI;
	}
	
	public LocalDate getfechaBaja() {
		return fechaBaja;
	}

	public void setfechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public String gettipoClientePersona() {
		return tipoClientePersona;
	}

	public void settipoClientePersona(String tipoClientePersona) {
		this.tipoClientePersona = tipoClientePersona;
	}
	
	public String getnombre() {
		return nombre;
	}

	public void setnombre(String nombre) {
		this.nombre = nombre;
	}
	
	public LocalDate getfechaOperacion() {
		return fechaOperacion;
	}

	public void setfechaOperacion(LocalDate fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	
	public BigDecimal getmontoOperacion() {
		return montoOperacion;
	}

	public void setmontoOperacion (BigDecimal montoOperacion) {
		this.montoOperacion = montoOperacion;
	}
	
	public String getdestinatarioCredito() {
		return destinatarioCredito;
	}

	public void setdestinatarioCredito (String destinatarioCredito) {
		this.destinatarioCredito = destinatarioCredito;
	}
	
	public String getinstrumentoMonetario() {
		return instrumentoMonetario;
	}

	public void setinstrumentoMonetario (String instrumentoMonetario) {
		this.instrumentoMonetario = instrumentoMonetario;
	}
	
	public String gettipoProductoCliente() {
		return tipoProductoCliente;
	}

	public void settipoProductoCliente (String tipoProductoCliente) {
		this.tipoProductoCliente = tipoProductoCliente;
	}
	
	public String gettipoPersonaOperacion() {
		return tipoPersonaOperacion;
	}

	public void settipoPersonaOperacion (String tipoPersonaOperacion) {
		this.tipoPersonaOperacion = tipoPersonaOperacion;
	}
	
	public String getdescripcionMovimiento() {
		return descripcionMovimiento;
	}

	public void setdescripcionMovimiento (String descripcionMovimiento) {
		this.descripcionMovimiento = descripcionMovimiento;
	}
	
	public String getreferenciaMovimiento() {
		return referenciaMovimiento;
	}

	public void setreferenciaMovimiento (String referenciaMovimiento) {
		this.referenciaMovimiento = referenciaMovimiento;
	}
	
	public String getnaturalezaMovimiento() {
		return naturalezaMovimiento;
	}

	public void setnaturalezaMovimiento (String naturalezaMovimiento) {
		this.naturalezaMovimiento = naturalezaMovimiento;
	}
	
	public Long getnumTransaccion() {
		return numTransaccion;
	}

	public void setnaturalezaMovimiento (Long numTransaccion) {
		this.numTransaccion = numTransaccion;
	}
	
	public String getproductoDestino() {
		return productoDestino;
	}

	public void setproductoDestino (String productoDestino) {
		this.productoDestino = productoDestino;
	}
	
	public String getnombreProductoDestino() {
		return nombreProductoDestino;
	}

	public void setnombreProductoDestino (String nombreProductoDestino) {
		this.nombreProductoDestino = nombreProductoDestino;
	}
	
	public Long getnumeroContrato() {
		return numeroContrato;
	}

	public void setnumeroContrato (Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

    // Getters y setters generados automáticamente por el IDE

    @Override
    public String toString() {
        return "ReporteClientesOpeModel{" +
                "clienteId=" + clienteId +
                ", estatusCliente='" + estatusCliente + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", tipoCliente='" + tipoCliente + '\'' +
                ", numeroCliente=" + numeroCliente +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", genero='" + genero + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", rfc='" + rfc + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", entidadFederativaNacimiento='" + entidadFederativaNacimiento + '\'' +
                ", paisNacimiento='" + paisNacimiento + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", correo='" + correo + '\'' +
                ", representanteLegal=" + representanteLegal +
                ", curp='" + curp + '\'' +
                ", colonia='" + colonia + '\'' +
                ", calle='" + calle + '\'' +
                ", numeroCasa='" + numeroCasa + '\'' +
                ", numInterior='" + numInterior + '\'' +
                ", cp='" + cp + '\'' +
                ", municipio='" + municipio + '\'' +
                ", estado='" + estado + '\'' +
                ", peps='" + peps + '\'' +
                ", actividadBMXID='" + actividadBMXID + '\'' +
                ", actividadBMX='" + actividadBMX + '\'' +
                ", actividadSCIANID='" + actividadSCIANID + '\'' +
                ", nivelRiesgo='" + nivelRiesgo + '\'' +
                ", montoCreditos=" + montoCreditos +
                ", tipoOcupacion='" + tipoOcupacion + '\'' +
                ", lugarTrabajo='" + lugarTrabajo + '\'' +
                ", actividadFRID=" + actividadFRID +
                ", actividadFR='" + actividadFR + '\'' +
                ", sectorEconomico='" + sectorEconomico + '\'' +
                ", volumenCreditos=" + volumenCreditos +
                ", actividadINEGI='" + actividadINEGI + '\'' +
                ", fechaBaja=" + fechaBaja +
                ", tipoClientePersona='" + tipoClientePersona + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaOperacion=" + fechaOperacion +
                ", montoOperacion=" + montoOperacion +
                ", destinatarioCredito='" + destinatarioCredito + '\'' +
                ", instrumentoMonetario='" + instrumentoMonetario + '\'' +
                ", tipoProductoCliente='" + tipoProductoCliente + '\'' +
                ", tipoPersonaOperacion='" + tipoPersonaOperacion + '\'' +
                ", descripcionMovimiento='" + descripcionMovimiento + '\'' +
                ", referenciaMovimiento='" + referenciaMovimiento + '\'' +
                ", naturalezaMovimiento='" + naturalezaMovimiento + '\'' +
                ", numTransaccion=" + numTransaccion +
                ", productoDestino='" + productoDestino + '\'' +
                ", nombreProductoDestino='" + nombreProductoDestino + '\'' +
                ", numeroContrato=" + numeroContrato +
                '}';
    }
}
