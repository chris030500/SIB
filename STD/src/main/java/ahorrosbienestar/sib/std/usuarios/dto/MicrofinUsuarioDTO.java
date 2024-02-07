package ahorrosbienestar.sib.std.usuarios.dto;

public class MicrofinUsuarioDTO {

	private int usuarioId;
	private int empleadoID;
	private String clavePuestoID;
	private String nombre;
	private String apPaterno;
	private String apMaterno;
	private String nombreCompleto;
	private String clave;
	private int estatus;
	private String correo;
	private int sucursalUsuario;
	private String semilla;
	private String rfc;

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getEmpleadoID() {
		return empleadoID;
	}

	public void setEmpleadoID(int empleadoID) {
		this.empleadoID = empleadoID;
	}

	public String getClavePuestoID() {
		return clavePuestoID;
	}

	public void setClavePuestoID(String clavePuestoID) {
		this.clavePuestoID = clavePuestoID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getSucursalUsuario() {
		return sucursalUsuario;
	}

	public void setSucursalUsuario(int sucursalUsuario) {
		this.sucursalUsuario = sucursalUsuario;
	}

	public String getSemilla() {
		return semilla;
	}

	public void setSemilla(String semilla) {
		this.semilla = semilla;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

}
