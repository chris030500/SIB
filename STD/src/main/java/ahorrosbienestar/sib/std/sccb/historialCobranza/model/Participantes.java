package ahorrosbienestar.sib.std.sccb.historialCobranza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_SCCB_PARTICIPANTES")
public class Participantes {
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_CREDITOID")
	private Integer i_credito_id;
	
	@Column(name = "I_NOCLIENTE")
	private Integer i_no_cliente;
	
	@Column(name = "I_TIPOROLPERSONAID")
	private Integer i_tipo_rol_persona_id;
	
	@Column(name = "VC_NOMBRECLIENTE")
	private String vc_nombre_cliente;
	
	@Column(name = "VC_ROLPARTICIPANTE")
	private String vc_rol_participante;
	
	@Column(name = "VC_TIPOLOGIACLIENTE")
	private String vc_tipologia_cliente;
	
	@Column(name = "I_NO_CLIENTE_SAFI")
	private Integer i_no_cliente_safi;
	
	@Column(name = "I_CREDITO_SAFI")
	private Long i_credito_safi;

	public Integer getPk_i_row_guid() {
		return pk_i_row_guid;
	}

	public void setPk_i_row_guid(Integer pk_i_row_guid) {
		this.pk_i_row_guid = pk_i_row_guid;
	}

	public Integer getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(Integer i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public Integer getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(Integer i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public Integer getI_tipo_rol_persona_id() {
		return i_tipo_rol_persona_id;
	}

	public void setI_tipo_rol_persona_id(Integer i_tipo_rol_persona_id) {
		this.i_tipo_rol_persona_id = i_tipo_rol_persona_id;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public String getVc_rol_participante() {
		return vc_rol_participante;
	}

	public void setVc_rol_participante(String vc_rol_participante) {
		this.vc_rol_participante = vc_rol_participante;
	}

	public String getVc_tipologia_cliente() {
		return vc_tipologia_cliente;
	}

	public void setVc_tipologia_cliente(String vc_tipologia_cliente) {
		this.vc_tipologia_cliente = vc_tipologia_cliente;
	}

	public Integer getI_no_cliente_safi() {
		return i_no_cliente_safi;
	}

	public void setI_no_cliente_safi(Integer i_no_cliente_safi) {
		this.i_no_cliente_safi = i_no_cliente_safi;
	}

	public Long getI_credito_safi() {
		return i_credito_safi;
	}

	public void setI_credito_safi(Long i_credito_safi) {
		this.i_credito_safi = i_credito_safi;
	}

	@Override
	public String toString() {
		return "Participantes [pk_i_row_guid=" + pk_i_row_guid + ", i_credito_id=" + i_credito_id + ", i_no_cliente="
				+ i_no_cliente + ", i_tipo_rol_persona_id=" + i_tipo_rol_persona_id + ", vc_nombre_cliente="
				+ vc_nombre_cliente + ", vc_rol_participante=" + vc_rol_participante + ", vc_tipologia_cliente="
				+ vc_tipologia_cliente + ", i_no_cliente_safi=" + i_no_cliente_safi + ", i_credito_safi="
				+ i_credito_safi + "]";
	}
	
	
}
