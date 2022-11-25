package ahorrosbienestar.sib.std.configuracion.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TBL_CONFIGURACION")
public class Configuracion {
	@Column(name = "PK_ID_CONFIGURACION")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="configuracion_proc_seq")
	@SequenceGenerator(name = "configuracion_proc_seq", sequenceName = "configuracion_proc_seq", initialValue = 1, allocationSize=1)
	private int pk_id_configuracion;
	
	@Column(name = "VC_NOM_PROPIEDAD", columnDefinition = "VARCHAR(45)")
	private String vc_nom_propiedad;
	
	@Column(name = "VC_VALOR_PROPIEDAD", columnDefinition = "VARCHAR(60)")
	private String vc_valor_propiedad;
	
	@Column(name = "VC_DESCRIPCION_PROPIEDAD", columnDefinition = "VARCHAR(100)")
	private String vc_descripcion_propiedad;
	
	@Column(name = "I_ESTATUS_PROPIEDAD", columnDefinition = "TINYINT(1)")
	private int i_estatus_propiedad;

	public int getPk_id_configuracion() {
		return pk_id_configuracion;
	}

	public void setPk_id_configuracion(int pk_id_configuracion) {
		this.pk_id_configuracion = pk_id_configuracion;
	}

	public String getVc_nom_propiedad() {
		return vc_nom_propiedad;
	}

	public void setVc_nom_propiedad(String vc_nom_propiedad) {
		this.vc_nom_propiedad = vc_nom_propiedad;
	}

	public String getVc_valor_propiedad() {
		return vc_valor_propiedad;
	}

	public void setVc_valor_propiedad(String vc_valor_propiedad) {
		this.vc_valor_propiedad = vc_valor_propiedad;
	}

	public String getVc_descripcion_propiedad() {
		return vc_descripcion_propiedad;
	}

	public void setVc_descripcion_propiedad(String vc_descripcion_propiedad) {
		this.vc_descripcion_propiedad = vc_descripcion_propiedad;
	}

	public int getI_estatus_propiedad() {
		return i_estatus_propiedad;
	}

	public void setI_estatus_propiedad(int i_estatus_propiedad) {
		this.i_estatus_propiedad = i_estatus_propiedad;
	}
	
	
}
