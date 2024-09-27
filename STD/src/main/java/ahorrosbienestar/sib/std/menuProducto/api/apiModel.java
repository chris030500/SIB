package ahorrosbienestar.sib.std.menuProducto.api;

public class apiModel {
    private String response;
    private String extraParam;
    private boolean success;  // Nuevo campo para indicar el estado

    // Getters y Setters
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
