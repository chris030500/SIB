package ahorrosbienestar.sib.std.menuproducto.administracionSMS.model;

public class EnvioMensajes {

    private String telefono;
    private String mensaje;
    private int idMensaje;

    public EnvioMensajes() {
        // Constructor vacío necesario para la deserialización JSON
    }

    public EnvioMensajes(String telefono, String mensaje, int idMensaje) {
        this.telefono = telefono;
        this.mensaje = mensaje;
        this.idMensaje =  idMensaje;
    }

    // Getters y setters
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	@Override
    public String toString() {
        return "EnvioMensajes [telefono=" + telefono + ", mensaje=" + mensaje + ", id=" + idMensaje +"]";
    }
}
