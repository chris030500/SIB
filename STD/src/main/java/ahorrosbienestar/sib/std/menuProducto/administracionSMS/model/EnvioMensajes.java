package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

public class EnvioMensajes {

    private String telefono;
    private String mensaje;

    public EnvioMensajes() {
        // Constructor vacío necesario para la deserialización JSON
    }

    public EnvioMensajes(String telefono, String mensaje) {
        this.telefono = telefono;
        this.mensaje = mensaje;
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

    @Override
    public String toString() {
        return "EnvioMensajes [telefono=" + telefono + ", mensaje=" + mensaje + "]";
    }
}
