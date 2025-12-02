package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.dto;

public class BasesEbrResponse <T> {
	private String mensaje;
	private T data;
	
	public BasesEbrResponse(String mensaje, T data) {
		this.mensaje = mensaje;
		this.data = data;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
