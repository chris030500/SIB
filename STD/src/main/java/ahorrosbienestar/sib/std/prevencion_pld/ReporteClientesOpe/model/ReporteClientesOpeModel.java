package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;
 
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

 
// -------- ReporteClientesOpeModel --------
@Entity
@Table(name = "TBL_PLD_AUDITORIAS_CLIENTES_OPERACIONES")
public class ReporteClientesOpeModel {

    @Id
    @Column(name = "I_ClienteID")
    private Integer I_ClienteID;

    private String VC_estatus_cliente;

    private LocalDate DT_FechaAlta;

    private String VC_tipo_cliente;

    private Integer I_numero_cliente;

    private String VC_nombre_completo;

    private String VC_GENERO;

    private String VC_tipo_persona;

    private String VC_rfc;

    private LocalDate DT_FechaNacimiento;

    private String VC_entidad_federativa_nacimiento;

    private String VC_pais_nacimiento;

    private String VC_nacionalidad;

    private String VC_ocupacion;

    private String VC_numero_telefono;

    private String VC_Correo;

    private String VC_representante_legal;

    private String C_curp;

    private String VC_colonia;

    private String VC_calle;

    private String VC_numeroCasa;

    private String VC_num_interior;

    private String VC_cp;

    private String VC_municipio;

    private String VC_estado;

    private String VC_Peps;

    private Integer I_ActividadBMXID;

    private String VC_ActividadBMX;

    private Integer I_ActividadSCIANID;

    private String VC_nivel_riesgo;

    private BigDecimal N_volumen_creditos;

    private LocalDate DT_fecha_operacion;

    private BigDecimal N_monto_operacion;

    private String VC_destinatario_credito;

    private String VC_instrumento_monetario;

    private String VC_tipo_producto_cliente;

    private String VC_tipo_persona_operacion;

    private String VC_descripcion_movimiento;

    private String VC_referencia_movimiento;

    private String C_naturaleza_movimiento;

    private Long I_num_transaccion;

    private String VC_producto_destino;

    private String VC_nombre_producto_destino;

    private Long I_numero_contrato;

    private LocalDate DT_Fecha_ejecucion;
    
    private String VC_TIPO_OCUPACION;
    
    private String VC_LUGARDETRABAJO;
    
    private Long   I_ActividadFRID;
    
    private String VC_Actividad_FR;
    
    private Long   I_SECTORECONOMICOID;
    
    private String VC_SECTORECONOMICO;
    
    private Long   I_ActividadINEGIID;
    
    private String VC_ActividadINEGI;

    // Getters y setters

    public Integer getI_ClienteID() { return I_ClienteID; }
    public void setI_ClienteID(Integer i_ClienteID) { I_ClienteID = i_ClienteID; }

    public String getVC_estatus_cliente() { return VC_estatus_cliente; }
    public void setVC_estatus_cliente(String vC_estatus_cliente) { VC_estatus_cliente = vC_estatus_cliente; }

    public LocalDate getDT_FechaAlta() { return DT_FechaAlta; }
    public void setDT_FechaAlta(LocalDate dT_FechaAlta) { DT_FechaAlta = dT_FechaAlta; }

    public String getVC_tipo_cliente() { return VC_tipo_cliente; }
    public void setVC_tipo_cliente(String vC_tipo_cliente) { VC_tipo_cliente = vC_tipo_cliente; }

    public Integer getI_numero_cliente() { return I_numero_cliente; }
    public void setI_numero_cliente(Integer i_numero_cliente) { I_numero_cliente = i_numero_cliente; }

    public String getVC_nombre_completo() { return VC_nombre_completo; }
    public void setVC_nombre_completo(String vC_nombre_completo) { VC_nombre_completo = vC_nombre_completo; }

    public String getVC_GENERO() { return VC_GENERO; }
    public void setVC_GENERO(String vC_GENERO) { VC_GENERO = vC_GENERO; }

    public String getVC_tipo_persona() { return VC_tipo_persona; }
    public void setVC_tipo_persona(String vC_tipo_persona) { VC_tipo_persona = vC_tipo_persona; }

    public String getVC_rfc() { return VC_rfc; }
    public void setVC_rfc(String vC_rfc) { VC_rfc = vC_rfc; }

    public LocalDate getDT_FechaNacimiento() { return DT_FechaNacimiento; }
    public void setDT_FechaNacimiento(LocalDate dT_FechaNacimiento) { DT_FechaNacimiento = dT_FechaNacimiento; }

    public String getVC_entidad_federativa_nacimiento() { return VC_entidad_federativa_nacimiento; }
    public void setVC_entidad_federativa_nacimiento(String vC_entidad_federativa_nacimiento) { VC_entidad_federativa_nacimiento = vC_entidad_federativa_nacimiento; }

    public String getVC_pais_nacimiento() { return VC_pais_nacimiento; }
    public void setVC_pais_nacimiento(String vC_pais_nacimiento) { VC_pais_nacimiento = vC_pais_nacimiento; }

    public String getVC_nacionalidad() { return VC_nacionalidad; }
    public void setVC_nacionalidad(String vC_nacionalidad) { VC_nacionalidad = vC_nacionalidad; }

    public String getVC_ocupacion() { return VC_ocupacion; }
    public void setVC_ocupacion(String vC_ocupacion) { VC_ocupacion = vC_ocupacion; }

    public String getVC_numero_telefono() { return VC_numero_telefono; }
    public void setVC_numero_telefono(String vC_numero_telefono) { VC_numero_telefono = vC_numero_telefono; }

    public String getVC_Correo() { return VC_Correo; }
    public void setVC_Correo(String vC_Correo) { VC_Correo = vC_Correo; }

    public String getVC_representante_legal() { return VC_representante_legal; }
    public void setVC_representante_legal(String vC_representante_legal) { VC_representante_legal = vC_representante_legal; }

    public String getC_curp() { return C_curp; }
    public void setC_curp(String c_curp) { C_curp = c_curp; }

    public String getVC_colonia() { return VC_colonia; }
    public void setVC_colonia(String vC_colonia) { VC_colonia = vC_colonia; }

    public String getVC_calle() { return VC_calle; }
    public void setVC_calle(String vC_calle) { VC_calle = vC_calle; }

    public String getVC_numeroCasa() { return VC_numeroCasa; }
    public void setVC_numeroCasa(String vC_numeroCasa) { VC_numeroCasa = vC_numeroCasa; }

    public String getVC_num_interior() { return VC_num_interior; }
    public void setVC_num_interior(String vC_num_interior) { VC_num_interior = vC_num_interior; }

    public String getVC_cp() { return VC_cp; }
    public void setVC_cp(String vC_cp) { VC_cp = vC_cp; }

    public String getVC_municipio() { return VC_municipio; }
    public void setVC_municipio(String vC_municipio) { VC_municipio = vC_municipio; }

    public String getVC_estado() { return VC_estado; }
    public void setVC_estado(String vC_estado) { VC_estado = vC_estado; }

    public String getVC_Peps() { return VC_Peps; }
    public void setVC_Peps(String vC_Peps) { VC_Peps = vC_Peps; }

    public Integer getI_ActividadBMXID() { return I_ActividadBMXID; }
    public void setI_ActividadBMXID(Integer i_ActividadBMXID) { I_ActividadBMXID = i_ActividadBMXID; }

    public String getVC_ActividadBMX() { return VC_ActividadBMX; }
    public void setVC_ActividadBMX(String vC_ActividadBMX) { VC_ActividadBMX = vC_ActividadBMX; }

    public Integer getI_ActividadSCIANID() { return I_ActividadSCIANID; }
    public void setI_ActividadSCIANID(Integer i_ActividadSCIANID) { I_ActividadSCIANID = i_ActividadSCIANID; }

    public String getVC_nivel_riesgo() { return VC_nivel_riesgo; }
    public void setVC_nivel_riesgo(String vC_nivel_riesgo) { VC_nivel_riesgo = vC_nivel_riesgo; }

    public BigDecimal getN_volumen_creditos() { return N_volumen_creditos; }
    public void setN_volumen_creditos(BigDecimal n_volumen_creditos) { N_volumen_creditos = n_volumen_creditos; }

    public LocalDate getDT_fecha_operacion() { return DT_fecha_operacion; }
    public void setDT_fecha_operacion(LocalDate dT_fecha_operacion) { DT_fecha_operacion = dT_fecha_operacion; }

    public BigDecimal getN_monto_operacion() { return N_monto_operacion; }
    public void setN_monto_operacion(BigDecimal n_monto_operacion) { N_monto_operacion = n_monto_operacion; }

    public String getVC_destinatario_credito() { return VC_destinatario_credito; }
    public void setVC_destinatario_credito(String vC_destinatario_credito) { VC_destinatario_credito = vC_destinatario_credito; }

    public String getVC_instrumento_monetario() { return VC_instrumento_monetario; }
    public void setVC_instrumento_monetario(String vC_instrumento_monetario) { VC_instrumento_monetario = vC_instrumento_monetario; }

    public String getVC_tipo_producto_cliente() { return VC_tipo_producto_cliente; }
    public void setVC_tipo_producto_cliente(String vC_tipo_producto_cliente) { VC_tipo_producto_cliente = vC_tipo_producto_cliente; }

    public String getVC_tipo_persona_operacion() { return VC_tipo_persona_operacion; }
    public void setVC_tipo_persona_operacion(String vC_tipo_persona_operacion) { VC_tipo_persona_operacion = vC_tipo_persona_operacion; }

    public String getVC_descripcion_movimiento() { return VC_descripcion_movimiento; }
    public void setVC_descripcion_movimiento(String vC_descripcion_movimiento) { VC_descripcion_movimiento = vC_descripcion_movimiento; }

    public String getVC_referencia_movimiento() { return VC_referencia_movimiento; }
    public void setVC_referencia_movimiento(String vC_referencia_movimiento) { VC_referencia_movimiento = vC_referencia_movimiento; }

    public String getC_naturaleza_movimiento() { return C_naturaleza_movimiento; }
    public void setC_naturaleza_movimiento(String c_naturaleza_movimiento) { C_naturaleza_movimiento = c_naturaleza_movimiento; }

    public Long getI_num_transaccion() { return I_num_transaccion; }
    public void setI_num_transaccion(Long i_num_transaccion) { I_num_transaccion = i_num_transaccion; }

    public String getVC_producto_destino() { return VC_producto_destino; }
    public void setVC_producto_destino(String vC_producto_destino) { VC_producto_destino = vC_producto_destino; }

    public String getVC_nombre_producto_destino() { return VC_nombre_producto_destino; }
    public void setVC_nombre_producto_destino(String vC_nombre_producto_destino) { VC_nombre_producto_destino = vC_nombre_producto_destino; }

    public Long getI_numero_contrato() { return I_numero_contrato; }
    public void setI_numero_contrato(Long i_numero_contrato) { I_numero_contrato = i_numero_contrato; }

    public LocalDate getDT_Fecha_ejecucion() { return DT_Fecha_ejecucion; }
    public void setDT_Fecha_ejecucion(LocalDate dT_Fecha_ejecucion) { DT_Fecha_ejecucion = dT_Fecha_ejecucion; }
    
    public String getVC_TIPO_OCUPACION() { return VC_TIPO_OCUPACION; }
    public void setVC_TIPO_OCUPACION(String VC_TIPO_OCUPACION) { this.VC_TIPO_OCUPACION = VC_TIPO_OCUPACION; }

    public String getVC_LUGARDETRABAJO() { return VC_LUGARDETRABAJO; }
    public void setVC_LUGARDETRABAJO(String VC_LUGARDETRABAJO) { this.VC_LUGARDETRABAJO = VC_LUGARDETRABAJO; }

    public Long getI_ActividadFRID() { return I_ActividadFRID; }
    public void setI_ActividadFRID(Long I_ActividadFRID) { this.I_ActividadFRID = I_ActividadFRID; }

    public String getVC_Actividad_FR() { return VC_Actividad_FR; }
    public void setVC_Actividad_FR(String VC_Actividad_FR) { this.VC_Actividad_FR = VC_Actividad_FR; }

    public Long getI_SECTORECONOMICOID() { return I_SECTORECONOMICOID; }
    public void setI_SECTORECONOMICOID(Long I_SECTORECONOMICOID) { this.I_SECTORECONOMICOID = I_SECTORECONOMICOID; }

    public String getVC_SECTORECONOMICO() { return VC_SECTORECONOMICO; }
    public void setVC_SECTORECONOMICO(String VC_SECTORECONOMICO) { this.VC_SECTORECONOMICO = VC_SECTORECONOMICO; }

    public Long getI_ActividadINEGIID() { return I_ActividadINEGIID; }
    public void setI_ActividadINEGIID(Long I_ActividadINEGIID) { this.I_ActividadINEGIID = I_ActividadINEGIID; }

    public String getVC_ActividadINEGI() { return VC_ActividadINEGI; }
    public void setVC_ActividadINEGI(String VC_ActividadINEGI) { this.VC_ActividadINEGI = VC_ActividadINEGI; }
}
