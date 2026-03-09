package ahorrosbienestar.sib.std.coord_doc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import ahorrosbienestar.sib.std.documentos.model.Documento;

@Entity
@Table(name = "TBL_COORD_DOC")
public class CoordenadaDocumento {
	
	@Column(name = "PK_ID_COORD_DOC")
	@Id
	private int pk_id_coord_doc;
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_COORD_DOC_SEQ")
//	@SequenceGenerator(name = "TBL_COORD_DOC_SEQ", sequenceName = "TBL_COORD_DOC_SEQ", initialValue = 1, allocationSize = 1)
	
	
	@Column(name = "F_X1")
	private float f_x1;
	@Column(name = "F_X2")
	private float f_x2;
	@Column(name = "F_Y1")
	private float f_y1;
	@Column(name = "F_Y2")
	private float f_y2;
	@Column(name = "F_CENTROX")
	private float f_centroX;
	@Column(name = "F_CENTROY")
	private float f_centroY;
	@Column(name = "I_PAGINA")
	private int i_pagina;
	
	@ManyToOne
	@JoinColumn(name = "FK_ID_DOCUMENTO")
	private Documento fk_id_documento;

	public int getPk_id_coord_doc() {
		return pk_id_coord_doc;
	}

	public void setPk_id_coord_doc(int pk_id_coord_doc) {
		this.pk_id_coord_doc = pk_id_coord_doc;
	}

	public float getF_x1() {
		return f_x1;
	}

	public void setF_x1(float f_x1) {
		this.f_x1 = f_x1;
	}

	public float getF_x2() {
		return f_x2;
	}

	public void setF_x2(float f_x2) {
		this.f_x2 = f_x2;
	}

	public float getF_y1() {
		return f_y1;
	}

	public void setF_y1(float f_y1) {
		this.f_y1 = f_y1;
	}

	public float getF_y2() {
		return f_y2;
	}

	public void setF_y2(float f_y2) {
		this.f_y2 = f_y2;
	}

	public float getF_centroX() {
		return f_centroX;
	}

	public void setF_centroX(float f_centroX) {
		this.f_centroX = f_centroX;
	}

	public float getF_centroY() {
		return f_centroY;
	}

	public void setF_centroY(float f_centroY) {
		this.f_centroY = f_centroY;
	}

	public int getI_pagina() {
		return i_pagina;
	}

	public void setI_pagina(int i_pagina) {
		this.i_pagina = i_pagina;
	}

	public Documento getFk_id_documento() {
		return fk_id_documento;
	}

	public void setFk_id_documento(Documento fk_id_documento) {
		this.fk_id_documento = fk_id_documento;
	}
	
	

}
