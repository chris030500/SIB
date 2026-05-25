package ahorrosbienestar.sib.std.proceso_validacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TBL_CAT_PROC_VALIDACION")
public class ProcesoValidacion {
	
	@Column(name = "PK_ID_PROC_VALIDACION")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_proc_validacion_seq")
	@SequenceGenerator(name = "tbl_proc_validacion_seq", sequenceName = "tbl_proc_validacion_seq", initialValue = 1, allocationSize=1)
	private int pk_id_proc_validacion;
	
	@Column(name = "VC_DESCRIPCION", columnDefinition = "VARCHAR(45)")
	private String vc_descripcion;

	public int getPk_id_proc_validacion() {
		return pk_id_proc_validacion;
	}

	public void setPk_id_proc_validacion(int pk_id_proc_validacion) {
		this.pk_id_proc_validacion = pk_id_proc_validacion;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}
	

}
