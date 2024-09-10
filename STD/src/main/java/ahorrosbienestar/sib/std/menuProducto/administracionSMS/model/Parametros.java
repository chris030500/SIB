package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

import java.util.List;

public class Parametros {
    private String mail;
    private String password;
    private String server;
    private String id;
    private String respuesta;
    private int proceso;
    private List<EnvioMensajes> mensajes;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}

	public List<EnvioMensajes> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<EnvioMensajes> mensajes) {
		this.mensajes = mensajes;
	}
    
}
