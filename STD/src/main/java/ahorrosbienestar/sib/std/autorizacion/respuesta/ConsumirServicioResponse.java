package ahorrosbienestar.sib.std.autorizacion.respuesta;

public class ConsumirServicioResponse {

	private String codigoRespuesta;
	private String mensajeRespuesta;

	public ConsumirServicioResponse() {
		this.codigoRespuesta = "";
		this.mensajeRespuesta = "";
	}

	public ConsumirServicioResponse(String codigoRespuesta, String mensajeRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

}
