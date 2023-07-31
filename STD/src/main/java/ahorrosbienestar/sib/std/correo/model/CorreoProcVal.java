package ahorrosbienestar.sib.std.correo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.proceso_validacion.model.ProcesoValidacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TBL_CORREO_PROC_VAL")
public class CorreoProcVal {
	
	@Column(name = "PK_ID_CORREO_PROC_VAL")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="correo_proc_seq")
	@SequenceGenerator(name = "correo_proc_seq", sequenceName = "correo_proc_seq", initialValue = 1, allocationSize=1)
	private int pk_id_correo_proc_val;
	
	@Column(name = "VC_CORREO", columnDefinition = "VARCHAR(45)")
	private String vc_correo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REG")
	private LocalDate dt_fecha_reg;
	
	@Column(name = "VC_IND_ACTIVO", columnDefinition = "TINYINT(1)")
	private int vc_ind_activo;
	
	@ManyToOne
	@JoinColumn(name = "FK_ID_PRO_VAL")
	private ProcesoValidacion fk_id_pro_val;

	public int getPk_id_correo_proc_val() {
		return pk_id_correo_proc_val;
	}

	public void setPk_id_correo_proc_val(int pk_id_correo_proc_val) {
		this.pk_id_correo_proc_val = pk_id_correo_proc_val;
	}

	public String getVc_correo() {
		return vc_correo;
	}

	public void setVc_correo(String vc_correo) {
		this.vc_correo = vc_correo;
	}

	public LocalDate getDt_fecha_reg() {
		return dt_fecha_reg;
	}

	public void setDt_fecha_reg(LocalDate dt_fecha_reg) {
		this.dt_fecha_reg = dt_fecha_reg;
	}

	public int getVc_ind_activo() {
		return vc_ind_activo;
	}

	public void setVc_ind_activo(int vc_ind_activo) {
		this.vc_ind_activo = vc_ind_activo;
	}

	public ProcesoValidacion getFk_id_pro_val() {
		return fk_id_pro_val;
	}

	public void setFk_id_pro_val(ProcesoValidacion fk_id_pro_val) {
		this.fk_id_pro_val = fk_id_pro_val;
	}
	
	
}
