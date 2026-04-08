package ahorrosbienestar.sib.std.credito.tiposCampanias.dto;

public class TiposCampaniasDTO {

	private int pk_id_tipo_campania;
    private String vc_tipo_campania;

    public TiposCampaniasDTO(int pk_id_tipo_campania, String vc_tipo_campania) {
        this.pk_id_tipo_campania = pk_id_tipo_campania;
        this.vc_tipo_campania = vc_tipo_campania;
    }

    public int getPk_id_tipo_campania() {
        return pk_id_tipo_campania;
    }

    public void setPk_id_tipo_campania(int pk_id_tipo_campania) {
        this.pk_id_tipo_campania = pk_id_tipo_campania;
    }

    public String getVc_tipo_campania() {
        return vc_tipo_campania;
    }

    public void setVc_tipo_campania(String vc_tipo_campania) {
        this.vc_tipo_campania = vc_tipo_campania;
    }
}
