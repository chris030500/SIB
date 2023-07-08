package ahorrosbienestar.sib.std.prevencion_pld.bitacora.dto;

public class BitacoraPldDTO {

	private String nombre_archivo;
	private int numero_registro;
	private int registros_excluidos;
	private int registro_final;
	private String duracion;
	private String estatus;
	private String comentarios;
	
	
	public String getNombre_archivo() {
		return nombre_archivo;
	}
	public void setNombre_archivo(String nombre_archivo) {
		this.nombre_archivo = nombre_archivo;
	}
	public int getNumero_registro() {
		return numero_registro;
	}
	public void setNumero_registro(int numero_registro) {
		this.numero_registro = numero_registro;
	}
	public int getRegistros_excluidos() {
		return registros_excluidos;
	}
	public void setRegistros_excluidos(int registros_excluidos) {
		this.registros_excluidos = registros_excluidos;
	}
	public int getRegistro_final() {
		return registro_final;
	}
	public void setRegistro_final(int registro_final) {
		this.registro_final = registro_final;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
