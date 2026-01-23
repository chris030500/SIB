package ahorrosbienestar.sib.std.firma_docto_unificado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;

@Entity
@Table(name = "TBL_FIRMA_DOCTO_UNIFICADO")
public class FirmaDoctoUnificado {
	
	@Column(name = "PK_ID_FIRMA_DOCTO_UNIFICADO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_firma_docto_unificado_seq")
	@SequenceGenerator(name = "tbl_firma_docto_unificado_seq", sequenceName = "tbl_firma_docto_unificado_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_firma_docto_unificado;
	
	@ManyToOne
	@JoinColumn(name = "FK_ID_DOCTO_UNIFICADO")
	private DoctoUnificado fk_id_docto_unificado;
	
	@Column(name = "I_NO_HOJA")
	private int i_no_hoja;
	
	@Column(name = "F_COORD_FIRMA_X1")
	private float f_coord_firma_x1;
	
	@Column(name = "F_COORD_FIRMA_X2")
	private float f_coord_firma_x2;
	
	@Column(name = "F_COORD_FIRMA_Y1")
	private float f_coord_firma_y1;
	
	@Column(name = "F_COORD_FIRMA_Y2")
	private float f_coord_firma_y2;
	
	@Column(name = "F_COORD_FIRMA_XCENTRAL")
	private float f_coord_firma_xCentral;
	
	@Column(name = "F_COORD_FIRMA_YCENTRAL")
	private float f_coord_firma_yCentral;

	public int getPk_id_firma_docto_unificado() {
		return pk_id_firma_docto_unificado;
	}

	public void setPk_id_firma_docto_unificado(int pk_id_firma_docto_unificado) {
		this.pk_id_firma_docto_unificado = pk_id_firma_docto_unificado;
	}

	public DoctoUnificado getFk_id_docto_unificado() {
		return fk_id_docto_unificado;
	}

	public void setFk_id_docto_unificado(DoctoUnificado fk_id_docto_unificado) {
		this.fk_id_docto_unificado = fk_id_docto_unificado;
	}

	public int getI_no_hoja() {
		return i_no_hoja;
	}

	public void setI_no_hoja(int i_no_hoja) {
		this.i_no_hoja = i_no_hoja;
	}

	public float getF_coord_firma_x1() {
		return f_coord_firma_x1;
	}

	public void setF_coord_firma_x1(float f_coord_firma_x1) {
		this.f_coord_firma_x1 = f_coord_firma_x1;
	}

	public float getF_coord_firma_x2() {
		return f_coord_firma_x2;
	}

	public void setF_coord_firma_x2(float f_coord_firma_x2) {
		this.f_coord_firma_x2 = f_coord_firma_x2;
	}

	public float getF_coord_firma_y1() {
		return f_coord_firma_y1;
	}

	public void setF_coord_firma_y1(float f_coord_firma_y1) {
		this.f_coord_firma_y1 = f_coord_firma_y1;
	}

	public float getF_coord_firma_y2() {
		return f_coord_firma_y2;
	}

	public void setF_coord_firma_y2(float f_coord_firma_y2) {
		this.f_coord_firma_y2 = f_coord_firma_y2;
	}

	public float getF_coord_firma_xCentral() {
		return f_coord_firma_xCentral;
	}

	public void setF_coord_firma_xCentral(float f_coord_firma_xCentral) {
		this.f_coord_firma_xCentral = f_coord_firma_xCentral;
	}

	public float getF_coord_firma_yCentral() {
		return f_coord_firma_yCentral;
	}

	public void setF_coord_firma_yCentral(float f_coord_firma_yCentral) {
		this.f_coord_firma_yCentral = f_coord_firma_yCentral;
	}
	
	

}

