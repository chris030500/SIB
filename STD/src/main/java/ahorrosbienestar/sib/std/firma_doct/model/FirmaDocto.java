package ahorrosbienestar.sib.std.firma_doct.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;

@Entity
@Table(name = "TBL_FIRMA_DOCTO")
public class FirmaDocto {

	@Column(name = "PK_ID_FIRMA_DOCTO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_firma_docto_seq")
	@SequenceGenerator(name = "tbl_firma_docto_seq", sequenceName = "tbl_firma_docto_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_firma_docto;
	
	@ManyToOne
	@JoinColumn(name = "FK_ID_DOCTO_ENVIO")
	private DoctEnvio fk_id_docto_envio;
	
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

	public int getPk_id_firma_docto() {
		return pk_id_firma_docto;
	}

	public void setPk_id_firma_docto(int pk_id_firma_docto) {
		this.pk_id_firma_docto = pk_id_firma_docto;
	}

	public DoctEnvio getFk_id_docto_envio() {
		return fk_id_docto_envio;
	}

	public void setFk_id_docto_envio(DoctEnvio fk_id_docto_envio) {
		this.fk_id_docto_envio = fk_id_docto_envio;
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

