package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto;

public class OriginacionWsResponse {

	private int status;
	private String metodo;
	private String url;
	private String responseBody;

	public OriginacionWsResponse() {
	}

	public OriginacionWsResponse(int status, String metodo, String url, String responseBody) {
		this.status = status;
		this.metodo = metodo;
		this.url = url;
		this.responseBody = responseBody;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
}
