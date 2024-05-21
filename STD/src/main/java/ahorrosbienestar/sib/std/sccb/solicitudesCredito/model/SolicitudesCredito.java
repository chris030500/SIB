package ahorrosbienestar.sib.std.sccb.solicitudesCredito.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_SOLICITUDES")
public class SolicitudesCredito {
	@Column(name = "PK_I_ROW_GUID")
	@Id
	private int pk_i_rowguid;
	
	@Column(name = "I_SOLICITUD_ID")
	private int i_solicitud_id;
	
	@Column(name = "I_CREDITO_ID")
	private int i_credito_id;
	
	@Column(name = "VC_NOMBRE_CLIENTE", columnDefinition = "VARCHAR(150)")
	private String vc_nombre_cliente;
	
	@Column(name = "I_NO_CLIENTE")
	private int i_no_cliente;
	
	@Column(name = "VC_ROL", columnDefinition = "VARCHAR(45)")
	private String vc_rol;
	
	@Column(name = "VC_ETAPA", columnDefinition = "VARCHAR(45)")
	private String vc_etapa;
	
	@Column(name = "VC_ESTATUS", columnDefinition = "VARCHAR(45)")
	private String vc_estatus;
	
	@Column(name = "FL_MONTO")
	private float fl_monto;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_RECEPCION")
	private LocalDate dt_fecha_recepcion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_INICIO_TRAMITE")
	private LocalDate dt_fecha_inicio_tramite;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_AUTORIZACION")
	private LocalDate dt_fecha_autorizacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT-FECHA_DESEMBOLSO")
	private LocalDate dt_fecha_desembolso;
	
	@Column(name = "VC_NOMBRE_CENTRO", columnDefinition = "VARCHAR(45)")
	private String vc_nombre_centro;
	
	@Column(name = "I_CREDITO_ID_SAFI")
	private int i_credito_idSafi;
	
	@Column(name = "I_NO_CLIENTE_SAFI")
	private int i_no_clienteSafi;
}
