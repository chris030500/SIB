package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.dto;

public class RelacionDTO {

	private int idCodigoAccion;
	private int idCodigoResultado;
	private int idAccionRealizar;
	private int ultimaGestion;
	private int idBloqueo;

	public int getIdCodigoAccion() {
		return idCodigoAccion;
	}

	public void setIdCodigoAccion(int idCodigoAccion) {
		this.idCodigoAccion = idCodigoAccion;
	}

	public int getIdCodigoResultado() {
		return idCodigoResultado;
	}

	public void setIdCodigoResultado(int idCodigoResultado) {
		this.idCodigoResultado = idCodigoResultado;
	}

	public int getIdAccionRealizar() {
		return idAccionRealizar;
	}

	public void setIdAccionRealizar(int idAccionRealizar) {
		this.idAccionRealizar = idAccionRealizar;
	}

	public int getUltimaGestion() {
		return ultimaGestion;
	}

	public void setUltimaGestion(int ultimaGestion) {
		this.ultimaGestion = ultimaGestion;
	}

	public int getIdBloqueo() {
		return idBloqueo;
	}

	public void setIdBloqueo(int idBloqueo) {
		this.idBloqueo = idBloqueo;
	}

}
