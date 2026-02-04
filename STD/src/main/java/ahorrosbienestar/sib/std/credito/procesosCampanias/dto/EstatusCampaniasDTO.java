package ahorrosbienestar.sib.std.credito.procesosCampanias.dto;

public class EstatusCampaniasDTO {

	private int pk_id_estatus_campania;
    private String vc_estatus;

    public EstatusCampaniasDTO(int pk_id_estatus_campania, String vc_estatus) {
        this.pk_id_estatus_campania = pk_id_estatus_campania;
        this.vc_estatus = vc_estatus;
    }

	public int getPk_id_estatus_campania() {
		return pk_id_estatus_campania;
	}

	public void setPk_id_estatus_campania(int pk_id_estatus_campania) {
		this.pk_id_estatus_campania = pk_id_estatus_campania;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}
	
}
