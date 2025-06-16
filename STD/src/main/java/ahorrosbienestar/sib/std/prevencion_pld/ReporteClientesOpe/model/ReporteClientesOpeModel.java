package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TBL_PLD_AUDITORIAS_CLIENTES_OPERACIONES")
@Data
@NoArgsConstructor
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

	public void setclienteId(Integer parseInteger) {
		// TODO Auto-generated method stub
		
	}

	public void setEstatusCliente(String formatCellValue) {
		// TODO Auto-generated method stub
		
	}

	public void setFechaAlta(LocalDate parseDate) {
		// TODO Auto-generated method stub
		
	}

	public void setTipoCliente(String formatCellValue) {
		// TODO Auto-generated method stub
		
	}

	public void setNumeroCliente(Integer parseInteger) {
		// TODO Auto-generated method stub
		
	}

	public void setNombreCompleto(String formatCellValue) {
		// TODO Auto-generated method stub
		
	}

	public void setGenero(String formatCellValue) {
		// TODO Auto-generated method stub
		
	}

	public void setMontoOperacion(BigDecimal parseDecimal) {
		// TODO Auto-generated method stub
		
	}

}
